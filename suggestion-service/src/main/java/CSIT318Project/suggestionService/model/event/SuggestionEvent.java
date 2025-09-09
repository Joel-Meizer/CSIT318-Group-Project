package CSIT318Project.suggestionService.model.event;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class SuggestionEvent {
    @Id
    @GeneratedValue
    private long id;
    @Column
    private String eventName;
    @Column
    private String summary;
    @OneToMany(cascade = CascadeType.ALL)
    private List<EducationalResource> suggestedResources;

    public SuggestionEvent() {
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
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

    @Override
    public String toString() {
        return "";
    }
}
