package com.model;

public class CleanReviewModel {

	private int cleanId;

	public int getCleanId() {
		return cleanId;
	}

	public void setCleanId(int cleanId) {
		this.cleanId = cleanId;
	}

	public String getArticleName() {
		return articleName;
	}

	public void setArticleName(String articleName) {
		this.articleName = articleName;
	}

	public String getCleanArtilce() {
		return cleanArtilce;
	}

	public void setCleanArtilce(String cleanArtilce) {
		this.cleanArtilce = cleanArtilce;
	}

	private String cleanArtilce;

	private String articleName;

}
