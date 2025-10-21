package CSIT318Project.guideService.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Column;

@Embeddable
public class ExternalVideo {

	private String url;
	private String title;
	@Column(columnDefinition = "TEXT")
	private String description;

	public ExternalVideo() {
	}

	public ExternalVideo(String url, String title, String description) {
		this.url = url;
		this.title = title;
		this.description = description;
	}

	// Getters
	public String getUrl() {
		return url;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}
}
