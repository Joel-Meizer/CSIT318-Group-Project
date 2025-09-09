package CSIT318Project.suggestionService.model;

import CSIT318Project.suggestionService.model.event.BookEvent;
import org.springframework.data.domain.AbstractAggregateRoot;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
public class EducationalResource extends AbstractAggregateRoot<Suggestion> {

    @Id
    @GeneratedValue
    private UUID suggestionId;

    @Column
    private String summary;

    @Column
    private List<EducationalResources> suggestedResources;

    public Suggestion() {
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public List<EducationalResources> getSuggestedResources() {
        return suggestedResources;
    }

    public void setSuggestedResources(List<EducationalResources> suggestedResources) {
        this.suggestedResources = suggestedResources;
    }

    @Override
    public String toString() {
        return "";
    }

    public Suggestion generateSuggestion(PreferenceModel preferenceModel) {
        // TODO
    }
}
