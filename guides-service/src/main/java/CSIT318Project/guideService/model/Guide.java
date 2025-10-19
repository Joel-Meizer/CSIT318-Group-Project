package CSIT318Project.guideService.model;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;

@Entity
public class Guide {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	@Column
	private UUID resourceId;
	@Column
	private String researchGoal;
	@Column(columnDefinition = "TEXT")
	private String summary;

	@ElementCollection
	@CollectionTable(name = "related_sections", joinColumns = @JoinColumn(name = "learning_guide_id"))
	private List<RelatedSection> relatedSections;

	@ElementCollection
	@CollectionTable(name = "external_videos", joinColumns = @JoinColumn(name = "learning_guide_id"))
	private List<ExternalVideo> externalVideos;

	public Guide() {
	}

	public UUID getId() {
		return id;
	}

	// Getters and Setters
	public UUID getResourceId() {
		return resourceId;
	}

	public void setResourceId(UUID resourceId) {
		this.resourceId = resourceId;
	}

	public String getResearchGoal() {
		return researchGoal;
	}

	public void setResearchGoal(String researchGoal) {
		this.researchGoal = researchGoal;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public List<RelatedSection> getRelatedSections() {
		return relatedSections;
	}

	public void setRelatedSections(List<RelatedSection> relatedSections) {
		this.relatedSections = relatedSections;
	}

	public List<ExternalVideo> getExternalVideos() {
		return externalVideos;
	}

	public void setExternalVideos(List<ExternalVideo> externalVideos) {
		this.externalVideos = externalVideos;
	}
}
