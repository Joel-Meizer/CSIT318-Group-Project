package com.library.accounts.controller;

import com.library.accounts.model.*;
import com.library.accounts.dto.*;
import com.library.accounts.service.UserAccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserAccountController {

    private final UserAccountService service;

    public UserAccountController(UserAccountService service) {
        this.service = service;
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

    @GetMapping("/api/users/{id}/membership")
    public ResponseEntity<Boolean> checkMembership(@PathVariable Long id) {
        return service.findById(id)
                .map(user -> ResponseEntity.ok(user.getMembership().isActive()))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/cancel-membership")
    public ResponseEntity<Void> cancelMembership(@PathVariable Long id) {
        try {
            service.cancelMembership(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.notFound().build();
        }
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

    @GetMapping("/{id}/userPreferences")
    public ResponseEntity<UserPreferenceModel> getUserPreferences(@PathVariable Long id) {
        return service.findById(id)
                .map(user -> ResponseEntity.ok(user.getUserPreferences()))
                .orElse(ResponseEntity.notFound().build());
    }
}

