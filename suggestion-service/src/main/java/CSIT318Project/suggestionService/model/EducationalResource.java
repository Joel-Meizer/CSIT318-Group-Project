package CSIT318Project.suggestionService.model;
import CSIT318Project.suggestionService.Enums.KnowledgeLevel;
import CSIT318Project.suggestionService.Enums.KnowledgeType;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.domain.AbstractAggregateRoot;
import jakarta.persistence.*;
import java.util.Date;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class EducationalResource {

    @Id
    private String resourceId;
    private String title;
    private String description;
    private String[] authors;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date publicationDate;
    private String genre;
    private String url;
    private KnowledgeLevel knowledgeLevel;
    private KnowledgeType knowledgeType;
    private String content;

    public EducationalResource() {}

    public void setResourceId(String resourceId) { this.resourceId = resourceId; }

    public String getResourceId() { return resourceId; }

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

    public KnowledgeType getKnowledgeType() { return knowledgeType; }

    public void setKnowledgeType(KnowledgeType knowledgeType) { this.knowledgeType = knowledgeType; }

    public String getContent() { return content; }

    public void setContent(String content) { this.content = content; }

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
