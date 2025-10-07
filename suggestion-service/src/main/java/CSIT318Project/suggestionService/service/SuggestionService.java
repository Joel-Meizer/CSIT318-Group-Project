package CSIT318Project.suggestionService.service;

import CSIT318Project.suggestionService.model.EducationalResource;
import CSIT318Project.suggestionService.model.Suggestion;
import CSIT318Project.suggestionService.model.SuggestionGenerateModel;
import CSIT318Project.suggestionService.repository.SuggestionRepository;
import CSIT318Project.suggestionService.service.dto.SuggestionDTO;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SuggestionService {

    private final SuggestionRepository suggestionRepository;
    private final RestTemplate restTemplate;
    private final ApplicationEventPublisher applicationEventPublisher;

    SuggestionService(SuggestionRepository suggestionRepository, RestTemplate restTemplate, ApplicationEventPublisher applicationEventPublisher) {
        this.suggestionRepository = suggestionRepository;
        this.restTemplate = restTemplate;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public List<SuggestionDTO> getAllSuggestions() {
        return suggestionRepository.findAll().stream().map(suggestion -> {
            SuggestionDTO suggestionDTO = new SuggestionDTO();
            return suggestionDTO;
        }).collect(Collectors.toList());
    }

    public SuggestionDTO getSuggestion(String suggestionId) {
        Suggestion suggestion = suggestionRepository.findById(suggestionId).orElseThrow(RuntimeException::new);
        return new SuggestionDTO(
            suggestion.getSummary(),
            suggestion.getEducationalResources()
        );
    }

    public SuggestionDTO generateSuggestionForUserPreferences(int userId) {
        // API Call to the users account via their id, collect the user preferences
        // Provide information from the user to the agentic element to collect me some educational resources to bundle into the SuggestionDTO

        // Can be triggered via events OR API calls to this endpoint '/suggestions/generate/{userId}/userPreferences'
        return new SuggestionDTO();
    }

    public SuggestionDTO generateSuggestionFromOrderHistory(int userId) {
        // API Call to the users account via their id, collect the order history
        // Provide information from the user to the agentic element to collect me some educational resources to bundle into the SuggestionDTO

        // Can be triggered via events OR API calls to this endpoint '/suggestions/generate/{userId}/orderHistory'
        return new SuggestionDTO();
    }

    public SuggestionDTO generateSuggestionWithManualInputs(SuggestionGenerateModel inputModel) {
        // Manual search method to POST a body of data (for the accepted input type)
        // Then provide a manual selection of suggested resources based on the inputs provided
        return new SuggestionDTO();
    }
}
