package CSIT318Project.suggestionService.service;

import CSIT318Project.suggestionService.Enums.KnowledgeLevel;
import CSIT318Project.suggestionService.Enums.KnowledgeType;
import CSIT318Project.suggestionService.agentic.SuggestionAgent;
import CSIT318Project.suggestionService.model.EducationalResource;
import CSIT318Project.suggestionService.model.Suggestion;
import CSIT318Project.suggestionService.model.SuggestionGenerateModel;
import CSIT318Project.suggestionService.model.UserPreferenceModel;
import CSIT318Project.suggestionService.repository.EducationalResourceRepository;
import CSIT318Project.suggestionService.repository.SuggestionRepository;
import CSIT318Project.suggestionService.service.dto.SuggestionDTO;
import CSIT318Project.suggestionService.service.dto.SuggestedResourcesResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.*;

import jakarta.persistence.criteria.Predicate;
import java.util.stream.Collectors;

@Service
public class SuggestionService {

    private final SuggestionRepository suggestionRepository;
    @Autowired
    private EducationalResourceRepository educationalResourceRepository;
    @Autowired
    private SuggestionAgent suggestionAgent;

    private final HttpWebClient httpWebClient;

    SuggestionService(SuggestionRepository suggestionRepository, HttpWebClient httpWebClient) {
        this.suggestionRepository = suggestionRepository;
        this.httpWebClient = httpWebClient;
    }

    public List<SuggestionDTO> getAllSuggestions() {
        return suggestionRepository.findAll().stream()
            .map(suggestion -> {
                SuggestionDTO dto = new SuggestionDTO();
                dto.setId(suggestion.getId());
                dto.setSummary(suggestion.getSummary());
                dto.setEducationResources(suggestion.getEducationalResources());
                return dto;
            })
            .collect(Collectors.toList());
    }

    public SuggestionDTO getSuggestion(UUID suggestionId) {
        Suggestion suggestion = suggestionRepository.findById(suggestionId).orElseThrow(RuntimeException::new);
        return new SuggestionDTO(
            suggestion.getId(),
            suggestion.getSummary(),
            suggestion.getEducationalResources()
        );
    }

    public SuggestionDTO generateSuggestionForUserPreferences(UUID userId) {
        UserPreferenceModel userPreferenceModel = httpWebClient.GetRESTAsync(String.format("http://localhost:8080/user/%s/userPreferences", userId), UserPreferenceModel.class);

        StringBuilder userMessageBuilder = new StringBuilder();
        userMessageBuilder.append("Here are the user's learning preferences. Please generate a List of EducationalResources based on them.\n\n");
        userMessageBuilder.append("The response SuggestiongenerateModel must match with at least 1 EducationalResource.\n\n");
        userMessageBuilder.append(userPreferenceModel.userPreferenceString).append("\n\n");

        if (userPreferenceModel.genre != null) {
            userMessageBuilder.append("My favourite genres are ").append(userPreferenceModel.genre).append(".\n");
        }

        if (userPreferenceModel.knowledgeLevel != null) {
            userMessageBuilder.append("I don't want any EducationalResources that are a higher knowledge level than ").append(userPreferenceModel.knowledgeLevel.name()).append(".\n");
        }

        String userMessage = userMessageBuilder.toString();

        SuggestedResourcesResponseDTO suggestedResources = suggestionAgent.generateFromPreferences(
                userMessage,
                educationalResourceRepository.findAll()
        ).content();

        for(EducationalResource resource : suggestedResources.resources) {
            System.out.println(resource.toString());
        }

        return SaveSuggestionCreateDTO("Collected suggested resources based on user preferences", suggestedResources.resources);
    }

    public SuggestionDTO generateSuggestionFromOrderHistory(UUID userId) {
        String response = httpWebClient.GetRESTAsync(String.format("http://localhost:8080/user/%s/orderHistory", userId));
        List<EducationalResource> previouslyOrdered;

        ObjectMapper mapper = new ObjectMapper();
        try {
            previouslyOrdered = mapper.readValue(
                response,
                new TypeReference<List<EducationalResource>>() {}
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse JSON response", e);
        }

        StringBuilder userMessageBuilder = new StringBuilder();
        userMessageBuilder.append("Here are the user's previously ordered items. Please generate a List of EducationalResources based on them.\n\n");
        userMessageBuilder.append("The response SuggestiongenerateModel must match with at least 1 EducationalResource.\n\n");

        userMessageBuilder.append("Below are my previously ordered items:\n\n");
        for(EducationalResource resource : previouslyOrdered) {
            userMessageBuilder.append(resource.toString());
        }

        String userMessage = userMessageBuilder.toString();

        SuggestedResourcesResponseDTO suggestedResources = suggestionAgent.generateFromOrderHistory(
                userMessage,
                previouslyOrdered,
                educationalResourceRepository.findAll()
        ).content();

        return SaveSuggestionCreateDTO("Collected suggested resources based on order history", suggestedResources.resources);
    }

    public SuggestionDTO generateSuggestionWithManualInputs(SuggestionGenerateModel inputModel) {
        return SaveSuggestionCreateDTO("Collected resources based on manual inputs", educationalResourceRepository.findAll(buildSpecification(inputModel)));
    }

    private SuggestionDTO SaveSuggestionCreateDTO(String summary, List<EducationalResource> matchedResources) {
        Suggestion generatedSuggestion = new Suggestion(
                summary,
                matchedResources
        );

        educationalResourceRepository.saveAll(matchedResources);
        suggestionRepository.save(generatedSuggestion);

        SuggestionDTO dto = new SuggestionDTO();
        dto.setId(generatedSuggestion.getId());
        dto.setSummary(summary);
        dto.setEducationResources(matchedResources);
        return dto;
    }

    public Specification<EducationalResource> buildSpecification(SuggestionGenerateModel input) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (input.title != null) {
                predicates.add(cb.like(cb.lower(root.get("title")), "%" + input.title.toLowerCase() + "%"));
            }
            if (input.description != null) {
                predicates.add(cb.like(cb.lower(root.get("description")), "%" + input.description.toLowerCase() + "%"));
            }
            if (input.authors != null) {
                predicates.add(cb.isMember(input.authors, root.get("authors")));
            }
            if (input.publicationDate != null) {
                predicates.add(cb.greaterThan(root.get("publicationDate"), input.publicationDate));
            }
            if (input.genre != null) {
                predicates.add(cb.equal(cb.lower(root.get("genre")), input.genre.toLowerCase()));
            }
            if (input.url != null) {
                predicates.add(cb.equal(root.get("url"), input.url));
            }
            if (input.knowledgeLevel != null) {
                predicates.add(cb.equal(root.get("knowledgeLevel"), input.knowledgeLevel));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
