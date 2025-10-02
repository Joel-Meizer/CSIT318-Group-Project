package CSIT318Project.guideService.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class RelatedSection {

	private String sectionName;
	private String summary;

	public RelatedSection() {
	}

	public RelatedSection(String sectionName, String summary) {
		this.sectionName = sectionName;
		this.summary = summary;
	}

	// Getters
	public String getSectionName() {
		return sectionName;
	}

	public String getSummary() {
		return summary;
	}
}
