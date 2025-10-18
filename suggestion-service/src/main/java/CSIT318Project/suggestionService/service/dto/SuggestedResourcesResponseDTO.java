package CSIT318Project.suggestionService.service.dto;

import CSIT318Project.suggestionService.model.EducationalResource;
import java.util.List;

public class SuggestedResourcesResponseDTO {
    public List<EducationalResource> resources;

    public SuggestedResourcesResponseDTO() {}

    public List<EducationalResource> getResources() { return resources; }
    public void setResources(List<EducationalResource> resources) { this.resources = resources; }
}
