package com.library.accounts.model;

import com.library.accounts.enums.KnowledgeLevel;
import com.library.accounts.enums.KnowledgeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.util.List;

@Embeddable
public class UserPreferences {

    @ElementCollection
    private List<String> preferredGenres;

    @Enumerated(EnumType.STRING)
    private KnowledgeLevel knowledgeLevel;

    @Enumerated(EnumType.STRING)
    private KnowledgeType knowledgeType;

    public UserPreferences() {}

    public UserPreferences(List<String> preferredGenres, KnowledgeLevel knowledgeLevel, KnowledgeType knowledgeType) {
        this.preferredGenres = preferredGenres;
        this.knowledgeLevel = knowledgeLevel;
        this.knowledgeType = knowledgeType;
    }

    public List<String> getPreferredGenres() { return preferredGenres; }
    public void setPreferredGenres(List<String> preferredGenres) { this.preferredGenres = preferredGenres; }

    public KnowledgeLevel getKnowledgeLevel() { return knowledgeLevel; }
    public void setKnowledgeLevel(KnowledgeLevel knowledgeLevel) { this.knowledgeLevel = knowledgeLevel; }

    public KnowledgeType getKnowledgeType() { return knowledgeType; }
    public void setKnowledgeType(KnowledgeType knowledgeType) { this.knowledgeType = knowledgeType; }
}

