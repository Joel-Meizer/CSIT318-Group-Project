package com.library.accounts.model;

import com.library.accounts.enums.*;

import java.util.Date;
import jakarta.persistence.Embeddable;

@Embeddable
public class UserPreferenceModel {
    public String userPreferenceString = null;
    public String genre = null;
    public KnowledgeLevel knowledgeLevel = null;
    public KnowledgeType knowledgeType = null;
}