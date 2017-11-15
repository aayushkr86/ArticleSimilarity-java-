package com.model;



public class CleanReviewUIModel {

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

	public String getCleanArticle() {
		return cleanArticle;
	}

	public void setCleanArticle(String cleanArticle) {
		this.cleanArticle = cleanArticle;
	}

	private String cleanArticle;
	
	private String articleName;

}
