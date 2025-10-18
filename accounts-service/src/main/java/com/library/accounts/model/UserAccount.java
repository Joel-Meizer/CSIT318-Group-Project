package com.library.accounts.model;

import jakarta.persistence.*;
import java.time.Instant;

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

    @ElementCollection
    private List<String> preferredGenres;

    @Enumerated(EnumType.STRING)
    private KnowledgeLevel knowledgeLevel;

    @Enumerated(EnumType.STRING)
    private KnowledgeType knowledgeType;

    private String researchGoal;

    public String getResearchGoal() {
        return researchGoal;
    }

    public void setResearchGoal(String researchGoal) {
        this.researchGoal = researchGoal;
    }


    private Instant createdAt = Instant.now();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "membership_id")
    private Membership membership;

    @Embedded
    private UserPreferenceModel userPreferences;

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

    public UserPreferenceModel getUserPreferences() { return userPreferences; }
    public void setUserPreferences(UserPreferenceModel userPreferences) { this.userPreferences = userPreferences; }
}

