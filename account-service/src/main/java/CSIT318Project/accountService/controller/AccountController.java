package CSIT318Project.accountService.controller;

import CSIT318Project.accountService.service.AccountService;
import CSIT318Project.accountService.service.dto.BookDTO;
import CSIT318Project.accountService.service.dto.LibraryDTO;
import CSIT318Project.accountService.model.UserPreferenceModel;
import CSIT318Project.accountService.Enums.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class AccountController {
    private final AccountService accountService;

    AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/user/{userId}/userPreferences")
    UserPreferenceModel allBooks(@PathVariable UUID userId) {
        UserPreferenceModel model = new UserPreferenceModel();
        model.genre = "I have been programming for 10 years, I am looking for expert level Machine Learning resources";
        return model;
    }
}