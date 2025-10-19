package CSIT318Project.guideService.model.dto;

import java.util.List;

import CSIT318Project.guideService.model.ExternalVideo;
import CSIT318Project.guideService.model.RelatedSection;

public class GuideDTO {

	private String summary;
	private List<RelatedSection> relatedSections;
	private List<ExternalVideo> externalVideos;

	public GuideDTO() {
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
