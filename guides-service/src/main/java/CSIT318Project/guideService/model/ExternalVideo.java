package CSIT318Project.guideService.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class ExternalVideo {

	private String url;
	private String title;

	public ExternalVideo() {
	}

	public ExternalVideo(String url, String title) {
		this.url = url;
		this.title = title;
	}

	// Getters
	public String getUrl() {
		return url;
	}

	public String getTitle() {
		return title;
	}
}
