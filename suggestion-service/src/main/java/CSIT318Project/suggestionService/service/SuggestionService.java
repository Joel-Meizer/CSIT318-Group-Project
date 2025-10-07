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

    SuggestionService(SuggestionRepository suggestionRepository, RestTemplate restTemplate,
                ApplicationEventPublisher applicationEventPublisher) {
        this.suggestionRepository = suggestionRepository;
        this.restTemplate = restTemplate;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public List<SuggestionDTO> getAllSuggestions() {
        return suggestionRepository.findAll().stream()
                .map(suggestion -> {
                    SuggestionDTO suggestionDTO = new SuggestionDTO();
                    return suggestionDTO;
                }).collect(Collectors.toList());
    }

    public SuggestionDTO getSuggestion(String suggestionId) {
        Suggestion suggestion = suggestionRepository.findById(suggestionId).orElseThrow(RuntimeException::new);
        return new SuggestionDTO(suggestion.getEducationalResources());
    }

    public SuggestionDTO generateSuggestionForUserPreferences(int userId) {
        SuggestionDTO suggestion = new SuggestionDTO();
        return suggestion;
    }

    public SuggestionDTO generateSuggestionFromOrderHistory(int userId) {
        SuggestionDTO suggestion = new SuggestionDTO();
        return suggestion;
    }

    public SuggestionDTO generateSuggestionWithManualInputs(SuggestionGenerateModel inputModel) {
        SuggestionDTO suggestion = new SuggestionDTO();
        return suggestion;
    }
}
