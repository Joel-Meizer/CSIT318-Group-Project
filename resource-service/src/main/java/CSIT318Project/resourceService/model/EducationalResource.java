package CSIT318Project.resourceService.model;
import CSIT318Project.resourceService.Enums.KnowledgeLevel;
import CSIT318Project.resourceService.Enums.KnowledgeType;
import org.springframework.data.domain.AbstractAggregateRoot;
import jakarta.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.JOINED) // Enables subclassing like Paper, Book, etc.
public abstract class EducationalResource extends AbstractAggregateRoot<EducationalResource> {

    @Id
    @GeneratedValue
    private UUID resourceId;

    @Column
    private String title;

    @Column
    private String description;

    @ElementCollection
    private String[] authors;

    @Column
    private Date publicationDate;

    @Column
    private String genre;

    @Column
    private String url;

    @Column
    private KnowledgeLevel knowledgeLevel;

    public void EducationalResource() {
    }

    public String getResourceId() { return resourceId.toString(); }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public String[] getAuthors() { return authors; }

    public void setAuthors(String[] authors) { this.authors = authors; }

    public Date getPublicationDate() { return publicationDate; }

    public void setPublicationDate(Date publicationDate) { this.publicationDate = publicationDate; }

    public String getGenre() { return genre; }

    public void setGenre(String genre) { this.genre = genre; }

    public String getUrl() { return url; }

    public void setUrl(String url) { this.url = url; }

    public KnowledgeLevel getKnowledgeLevel() { return knowledgeLevel; }

    public void setKnowledgeLevel(KnowledgeLevel knowledgeLevel) { this.knowledgeLevel = knowledgeLevel; }

    public abstract KnowledgeType getKnowledgeType();

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Suggestion Details:\n");
        sb.append("Resource ID: ").append(resourceId).append("\n");
        sb.append("Title: ").append(title).append("\n");
        sb.append("Description: ").append(description).append("\n");
        sb.append("Authors: ").append(authors != null ? String.join(", ", authors) : "None").append("\n");
        sb.append("Publication Date: ").append(publicationDate != null ? publicationDate.toString() : "Not set").append("\n");
        sb.append("Genre: ").append(genre).append("\n");
        sb.append("URL: ").append(url).append("\n");
        sb.append("Knowledge Level: ").append(knowledgeLevel != null ? knowledgeLevel.toString() : "Undefined").append("\n");
        return sb.toString();
    }
}
