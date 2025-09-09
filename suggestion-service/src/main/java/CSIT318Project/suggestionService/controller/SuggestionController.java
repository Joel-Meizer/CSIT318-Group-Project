package CSIT318Project.suggestionService.controller;

import CSIT318Project.suggestionService.service.SuggestionService;
import CSIT318Project.suggestionService.service.dto.SuggestionDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

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
}