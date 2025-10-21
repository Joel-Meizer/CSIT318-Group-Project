package CSIT318Project.guideService.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class RelatedSection {

	private String name;
	@Column(columnDefinition = "TEXT")
	private String summary;

	public RelatedSection() {
	}

	public RelatedSection(String sectionName, String summary) {
		this.name = sectionName;
		this.summary = summary;
	}

	// Getters
	public String getName() {
		return name;
	}

	public String getSummary() {
		return summary;
	}
}
