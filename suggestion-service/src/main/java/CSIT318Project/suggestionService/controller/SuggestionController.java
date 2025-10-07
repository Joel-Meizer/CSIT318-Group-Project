package CSIT318Project.suggestionService.controller;

import CSIT318Project.suggestionService.Enums.KnowledgeType;
import CSIT318Project.suggestionService.model.SuggestionGenerateModel;
import CSIT318Project.suggestionService.service.SuggestionService;
import CSIT318Project.suggestionService.service.dto.SuggestionDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    SuggestionDTO findSuggestion(@PathVariable String id) {
        return suggestionService.getSuggestion(id);
    }

    @PostMapping("/suggestions/generate/{userId}/userPreferences")
    SuggestionDTO generateSuggestionForUserPreferences(@PathVariable int userId) {
        return suggestionService.generateSuggestionForUserPreferences(userId);
    }

    @PostMapping("/suggestions/generate/{userId}/orderHistory")
    SuggestionDTO generateSuggestionFromOrderHistory(@PathVariable int userId) {
        return suggestionService.generateSuggestionFromOrderHistory(userId);
    }

    @PostMapping("/suggestions/generate")
    SuggestionDTO generateSuggestionWithManualInputs(@RequestBody SuggestionGenerateModel inputModel) {
        return suggestionService.generateSuggestionWithManualInputs(inputModel);
    }
}