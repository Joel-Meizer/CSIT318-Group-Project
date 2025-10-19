package com.library.accounts.dto;

import com.library.accounts.enums.KnowledgeLevel;
import com.library.accounts.enums.KnowledgeType;
import java.util.List;

public class UserPreferenceModel {

    private String userPreferenceString;
    private List<String> genres;
    private KnowledgeLevel knowledgeLevel;
    private KnowledgeType knowledgeType;

    public UserPreferenceModel() {}

    public UserPreferenceModel(String userPreferenceString, List<String> genres, KnowledgeLevel knowledgeLevel, KnowledgeType knowledgeType) {
        this.userPreferenceString = userPreferenceString;
        this.genres = genres;
        this.knowledgeLevel = knowledgeLevel;
        this.knowledgeType = knowledgeType;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public KnowledgeLevel getKnowledgeLevel() {
        return knowledgeLevel;
    }

    public void setKnowledgeLevel(KnowledgeLevel knowledgeLevel) {
        this.knowledgeLevel = knowledgeLevel;
    }

    public KnowledgeType getKnowledgeType() {
        return knowledgeType;
    }

    public void setKnowledgeType(KnowledgeType knowledgeType) {
        this.knowledgeType = knowledgeType;
    }

    public String getUserPreferenceString() { return this.userPreferenceString; }

    public void setUserPreferenceString(String userPreferenceString) { this.userPreferenceString = userPreferenceString; }
}
