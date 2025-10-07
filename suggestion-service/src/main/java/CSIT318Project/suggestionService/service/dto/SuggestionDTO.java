package CSIT318Project.suggestionService.service.dto;

import CSIT318Project.suggestionService.model.EducationalResource;

import java.util.List;

public class SuggestionDTO {

    public String summary;
    public List<EducationalResource> educationalResourceList;

    public SuggestionDTO () {}

    public SuggestionDTO(List<EducationalResource> educationalResources) {
        this.educationalResourceList = educationalResources;
    }

    public void setEducationResources(List<EducationalResource> suggestedResources) {
        this.educationalResourceList = suggestedResources;
    }
}
