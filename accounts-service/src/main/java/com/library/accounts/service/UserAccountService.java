package com.library.accounts.service;

import com.library.accounts.model.*;
import com.library.accounts.repository.*;
import com.library.accounts.dto.UserPreferenceModel;
import com.library.accounts.model.UserPreferences;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UserAccountService {

    private final UserAccountRepository userRepo;
    private final MembershipRepository membershipRepo;

    public UserAccountService(UserAccountRepository userRepo, MembershipRepository membershipRepo) {
        this.userRepo = userRepo;
        this.membershipRepo = membershipRepo;
    }

    public List<UserAccount> listAll() {
        return userRepo.findAll();
    }

    public Optional<UserAccount> findById(Long id) {
        return userRepo.findById(id);
    }

    @Transactional
    public UserAccount createUser(String email, String firstName, String lastName, MembershipType type, int months) {
        if (userRepo.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }

        LocalDate start = LocalDate.now();
        LocalDate end = start.plusMonths(months);

        Membership membership = new Membership(type, start, end, true);
        membership = membershipRepo.save(membership);

        UserAccount user = new UserAccount(email, firstName, lastName);
        user.setMembership(membership);
        return userRepo.save(user);
    }

    @Transactional
    public UserAccount updateUser(Long id, String firstName, String lastName) {
        UserAccount user = userRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.setFirstName(firstName);
        user.setLastName(lastName);
        return userRepo.save(user);
    }

    @Transactional
    public void cancelMembership(Long userId) {
        UserAccount user = userRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Membership m = user.getMembership();
        if (m != null) {
            m.setActive(false);
            m.setEndDate(LocalDate.now());
            membershipRepo.save(m);
        }
    }

    @Transactional
    public UserAccount updateUserPreferences(Long id, UserPreferenceModel userPreferenceModel) {
        UserAccount user = userRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        UserPreferences preferences = new UserPreferences(
                userPreferenceModel.getGenres(),
                userPreferenceModel.getKnowledgeLevel(),
                userPreferenceModel.getKnowledgeType()
        );

        user.setUserPreferences(preferences);
        return userRepo.save(user);
    }

}

