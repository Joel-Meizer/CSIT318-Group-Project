package com.library.accounts.model;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.library.accounts.model.UserPreferences;
import com.library.accounts.enums.KnowledgeLevel;
import com.library.accounts.enums.KnowledgeType;


@Entity
@Table(name = "users")
public class UserAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    private String firstName;
    private String lastName;


    private String researchGoal;

    public String getResearchGoal() {
        return researchGoal;
    }

    public void setResearchGoal(String researchGoal) {
        this.researchGoal = researchGoal;
    }


    private Instant createdAt = Instant.now();

    @ElementCollection
    private List<String> orderedResources = new ArrayList<>();

    public List<String> getOrderedResources() {
        return orderedResources;
    }

    public void addOrderedResource(String resourceId) {
        this.orderedResources.add(resourceId);
    }

    @Embedded
    private UserPreferences userPreferences;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "membership_id")
    private Membership membership;


    public UserAccount() {}

    public UserAccount(String email, String firstName, String lastName) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Long getId() { return id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public Membership getMembership() { return membership; }
    public void setMembership(Membership membership) { this.membership = membership; }

    public UserPreferences getUserPreferences() { return userPreferences; }
    public void setUserPreferences(UserPreferences userPreferences) { this.userPreferences = userPreferences; }

}

