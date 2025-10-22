package com.library.accounts.controller;

import com.library.accounts.model.*;
import com.library.accounts.dto.*;
import com.library.accounts.repository.*;
import com.library.accounts.dto.UserPreferenceModel;
import com.library.accounts.model.UserPreferences;
import com.library.accounts.service.UserAccountService;
import com.library.accounts.service.UserEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/users")
public class UserAccountController {

    private final UserAccountService service;

    private final UserAccountRepository userAccountRepository;
    private final UserEventPublisher eventPublisher;

    public UserAccountController(UserAccountService service, UserAccountRepository repo, UserEventPublisher publisher) {
        this.service = service;
        this.userAccountRepository = repo;
        this.eventPublisher = publisher;
    }


    @PostMapping("/{id}/cancel-membership")
    public ResponseEntity<String> cancelMembership(@PathVariable Long id) {
        Optional<UserAccount> userOpt = userAccountRepository.findById(id);
        if (userOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        UserAccount user = userOpt.get();
        user.getMembership().setActive(false);
        userAccountRepository.save(user);

        eventPublisher.publishMembershipCancelled(id);
        return ResponseEntity.ok("Membership cancelled for user ID " + id);
    }

    @GetMapping
    public List<UserAccount> listAll() {
        return service.listAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserAccount> getUser(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UserAccount> createUser(@RequestBody CreateUserRequest req) {
        UserAccount created = service.createUser(
                req.email,
                req.firstName,
                req.lastName,
                req.membershipType,
                req.membershipMonths
        );
        return ResponseEntity.created(URI.create("/api/users/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserAccount> updateUser(@PathVariable Long id, @RequestBody UpdateUserRequest req) {
        try {
            return ResponseEntity.ok(service.updateUser(id, req.firstName, req.lastName));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/membership")
    public ResponseEntity<Boolean> checkMembership(@PathVariable Long id) {
        return service.findById(id)
                .map(user -> ResponseEntity.ok(user.getMembership().isActive()))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/preferences")
    public ResponseEntity<UserPreferenceModel> getUserPreferences(@PathVariable Long id) {
        return userAccountRepository.findById(id)
                .<ResponseEntity<UserPreferenceModel>>map(user -> {
                    if (user.getUserPreferences() == null) {
                        return ResponseEntity.notFound().build();
                    }
                    UserPreferences prefs = user.getUserPreferences();
                    UserPreferenceModel dto = new UserPreferenceModel(
                            prefs.getPreferenceString(),
                            prefs.getPreferredGenres(),
                            prefs.getKnowledgeLevel(),
                            prefs.getKnowledgeType()
                    );
                    return ResponseEntity.ok(dto);
                })
                .orElse(ResponseEntity.notFound().build());
    }


    @GetMapping("/{id}/research-goal")
    public ResponseEntity<ResearchGoalDTO> getUserResearchGoal(@PathVariable Long id) {
        return userAccountRepository.findById(id)
                .map(user -> ResponseEntity.ok(new ResearchGoalDTO(user.getResearchGoal())))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/research-goal")
    public ResponseEntity<ResearchGoalDTO> updateResearchGoal(
            @PathVariable Long id,
            @RequestBody ResearchGoalDTO newGoal) {

        return userAccountRepository.findById(id)
                .map(user -> {
                    user.setResearchGoal(newGoal.getResearchGoal());
                    userAccountRepository.save(user);
                    return ResponseEntity.ok(new ResearchGoalDTO(user.getResearchGoal()));
                })
                .orElse(ResponseEntity.notFound().build());
    }


    @PatchMapping("/{id}/update-preferences")
    public ResponseEntity<Void> updateUserPreferences(@PathVariable Long id, @RequestBody UserPreferenceModel pref) {
        try {
            service.updateUserPreferences(id, pref);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}

