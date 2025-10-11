package CSIT318Project.suggestionService.service.dto;

import CSIT318Project.suggestionService.model.EducationalResource;

import java.util.List;
import java.util.UUID;

public class SuggestionDTO {

    public UUID id;
    public String summary;
    public List<EducationalResource> educationalResourceList;

    public SuggestionDTO () {}

    public SuggestionDTO(UUID id, String summary, List<EducationalResource> educationalResources) {
        this.id = id;
        this.summary = summary;
        this.educationalResourceList = educationalResources;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setEducationResources(List<EducationalResource> suggestedResources) {
        this.educationalResourceList = suggestedResources;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
