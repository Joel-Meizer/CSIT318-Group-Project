package CSIT318Project.guideService.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class RelatedSection {

	private int startLine;
	private int endLine;

	public RelatedSection() {
	}

	public RelatedSection(int startLine, int endLine) {
		this.startLine = startLine;
		this.endLine = endLine;
	}

	// Getters and Setters
	public int getStartLine() {
		return startLine;
	}

	public void setStartLine(int startLine) {
		this.startLine = startLine;
	}

	public int getEndLine() {
		return endLine;
	}

	public void setEndLine(int endLine) {
		this.endLine = endLine;
	}
}
