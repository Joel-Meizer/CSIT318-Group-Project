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

	// Getters and Setters
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
