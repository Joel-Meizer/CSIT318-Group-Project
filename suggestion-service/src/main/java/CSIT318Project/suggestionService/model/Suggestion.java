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

    @OneToMany(cascade = CascadeType.ALL)
    private List<EducationalResource> suggestedResources;

    public Suggestion() {
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public List<EducationalResource> getSuggestedResources() {
        return suggestedResources;
    }

    public void setSuggestedResources(List<EducationalResource> suggestedResources) {
        this.suggestedResources = suggestedResources;
    }

    //@Override
    //public String toString() {
    //    return "";
    //}

    //public Suggestion generateSuggestion(PreferenceModel preferenceModel) {
        // TODO
    //}
}
