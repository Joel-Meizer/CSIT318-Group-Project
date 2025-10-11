package CSIT318Project.suggestionService.service;

import CSIT318Project.suggestionService.Enums.KnowledgeLevel;
import CSIT318Project.suggestionService.Enums.KnowledgeType;
import CSIT318Project.suggestionService.agentic.SuggestionAgent;
import CSIT318Project.suggestionService.model.EducationalResource;
import CSIT318Project.suggestionService.model.Inheritors.Book;
import CSIT318Project.suggestionService.model.Suggestion;
import CSIT318Project.suggestionService.model.SuggestionGenerateModel;
import CSIT318Project.suggestionService.model.UserPreferenceModel;
import CSIT318Project.suggestionService.repository.EducationalResourceRepository;
import CSIT318Project.suggestionService.repository.SuggestionRepository;
import CSIT318Project.suggestionService.service.dto.SuggestionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

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

    SuggestionService(SuggestionRepository suggestionRepository) {
        this.suggestionRepository = suggestionRepository;
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

    public SuggestionDTO generateSuggestionForUserPreferences(int userId) {
        // API Call to the users account via their id, collect the user preferences
        // Provide information from the user to the agentic element to collect me some educational resources to bundle into the SuggestionDTO

        SuggestionGenerateModel model = suggestionAgent.generateFromPreferences(
                "Generate a SuggestionGenerateModel based on a UserPreferenceModel",
                new UserPreferenceModel(),
                educationalResourceRepository.findAll(),
                educationalResourceRepository.findDistinctGenres(),
                Arrays.asList(KnowledgeLevel.values()),
                Arrays.asList(KnowledgeType.values())
        ).content();

        // Post Process the model in case we get back fields we dont want
        model.title = null;
        model.description = null;
        model.authors = null;
        model.publicationDate = null;
        model.source = null;
        model.url = null;

        return SaveSuggestionCreateDTO("Collected suggested resources based on user preferences", educationalResourceRepository.findAll(buildSpecification(model)));
    }

    public SuggestionDTO generateSuggestionFromOrderHistory(UUID userId) {
        // API Call to the users account via their id, collect the order history
        // Provide information from the user to the agentic element to collect me some educational resources to bundle into the SuggestionDTO

        List< EducationalResource> previouslyOrdered = new ArrayList<EducationalResource>();

        SuggestionGenerateModel model = suggestionAgent.generateFromOrderHistory(
                "Generate a SuggestionGenerateModel based on previouslyOrdered ensuring if a search model is produced, we only provide validGenres, validLevels and validTypes",
                previouslyOrdered,
                educationalResourceRepository.findAll(),
                educationalResourceRepository.findDistinctGenres(),
                Arrays.asList(KnowledgeLevel.values()),
                Arrays.asList(KnowledgeType.values())
        ).content();

        // Post Process the model in case we get back fields we dont want
        model.title = null;
        model.description = null;
        model.authors = null;
        model.publicationDate = null;
        model.source = null;
        model.url = null;

        return SaveSuggestionCreateDTO("Collected suggested resources based on order history", educationalResourceRepository.findAll(buildSpecification(model)));
    }

    public SuggestionDTO generateSuggestionWithManualInputs(SuggestionGenerateModel inputModel) {
        return SaveSuggestionCreateDTO("Collected resources based on manual inputs", educationalResourceRepository.findAll(buildSpecification(inputModel)));
    }

    private SuggestionDTO SaveSuggestionCreateDTO(String summary, List<EducationalResource> matchedResources) {
        Suggestion generatedSuggestion = new Suggestion(
                summary,
                matchedResources
        );

        suggestionRepository.save(generatedSuggestion);

        SuggestionDTO dto = new SuggestionDTO();
        dto.setId(generatedSuggestion.getId());
        dto.setSummary(summary);
        dto.setEducationResources(matchedResources);
        return dto;
    }

    public Specification<EducationalResource> buildSpecification(SuggestionGenerateModel input) {
        return (root, query, criteria) -> {
            List<Predicate> searchPredicates = new ArrayList<>();

            if (input.title != null) {
                searchPredicates.add(criteria.like(criteria.lower(root.get("title")), "%" + input.title.toLowerCase() + "%"));
            }
            if (input.description != null) {
                searchPredicates.add(criteria.like(criteria.lower(root.get("description")), "%" + input.description.toLowerCase() + "%"));
            }
            if (input.authors != null) {
                searchPredicates.add(criteria.isMember(input.authors, root.get("authors")));
            }
            if (input.publicationDate != null) {
                searchPredicates.add(criteria.greaterThan(root.get("publicationDate"), input.publicationDate));
            }
            if (input.genre != null) {
                searchPredicates.add(criteria.equal(criteria.lower(root.get("genre")), input.genre.toLowerCase()));
            }
            if (input.url != null) {
                searchPredicates.add(criteria.equal(root.get("url"), input.url));
            }
            if (input.knowledgeLevel != null) {
                searchPredicates.add(criteria.equal(root.get("knowledgeLevel"), input.knowledgeLevel));
            }

            return criteria.and(searchPredicates.toArray(new Predicate[0]));
        };
    }

}
