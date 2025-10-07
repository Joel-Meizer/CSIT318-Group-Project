package CSIT318Project.suggestionService.model;

import org.springframework.data.domain.AbstractAggregateRoot;

import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
public class Suggestion extends AbstractAggregateRoot<Suggestion> {

    @Id
    @GeneratedValue
    private UUID suggestionId;

    @Column
    private String summary;

    @ElementCollection
    private List<EducationalResource> educationalResources;

    public Suggestion() {
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public List<EducationalResource> getEducationalResources() {
        return educationalResources;
    }

    public void setSuggestedResources(List<EducationalResource> educationalResources) {
        this.educationalResources = educationalResources;
    }

    @Override
    public String toString() {
        StringBuilder rb = new StringBuilder();
        StringBuilder sb = new StringBuilder();

        if(!educationalResources.isEmpty()) {
            rb.append("Suggested Resources:\n");
            int counter = 1;
            for(EducationalResource resource : educationalResources) {
                rb.append("Resource " + counter + ":\n").append(resource.toString());
                counter++;
            }
        } else {
            rb.append("No suggested resources");
        }

        sb.append("Summary: ").append(summary).append("\n");
        sb.append(rb.toString()).append("\n");

        return sb.toString();
    }
}
