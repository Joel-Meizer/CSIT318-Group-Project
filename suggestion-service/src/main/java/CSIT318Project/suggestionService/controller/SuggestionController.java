package CSIT318Project.suggestionService.controller;
import CSIT318Project.suggestionService.model.SuggestionGenerateModel;
import CSIT318Project.suggestionService.service.SuggestionService;
import CSIT318Project.suggestionService.service.dto.SuggestionDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class SuggestionController {
    private final SuggestionService suggestionService;

    SuggestionController(SuggestionService suggestionService) {
        this.suggestionService = suggestionService;
    }

    @GetMapping("/suggestions")
    List<SuggestionDTO> allSuggestions() {
        return suggestionService.getAllSuggestions();
    }

    @GetMapping("/suggestions/{id}")
    SuggestionDTO findSuggestion(@PathVariable UUID id) {
        return suggestionService.getSuggestion(id);
    }

    @PostMapping("/suggestions/generate/{userId}/userPreferences")
    SuggestionDTO generateSuggestionForUserPreferences(@PathVariable UUID userId) {
        return suggestionService.generateSuggestionForUserPreferences(userId);
    }

    @PostMapping("/suggestions/generate/{userId}/orderHistory")
    SuggestionDTO generateSuggestionFromOrderHistory(@PathVariable UUID userId) {
        return suggestionService.generateSuggestionFromOrderHistory(userId);
    }

    @PostMapping("/suggestions/generate")
    SuggestionDTO generateSuggestionWithManualInputs(@RequestBody SuggestionGenerateModel inputModel) {
        return suggestionService.generateSuggestionWithManualInputs(inputModel);
    }
}