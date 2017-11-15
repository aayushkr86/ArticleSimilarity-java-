package com.model;

import java.sql.Blob;

public class ReviewModelObj {

	private String reviewDetails;

	public void setReviewDetails(String reviewDetails) {
		this.reviewDetails = reviewDetails;
	}

	public String getReviewDetails() {
		return reviewDetails;
	}

	public void setReviewDetailsBlob(Blob reviewDetailsBlob) {
		this.reviewDetailsBlob = reviewDetailsBlob;
	}

	public Blob getReviewDetailsBlob() {
		return reviewDetailsBlob;
	}

	public String getArticleName() {
		return articleName;
	}

	public void setArticleName(String articleName) {
		this.articleName = articleName;
	}

	private Blob reviewDetailsBlob;
	
	
	private String articleName;

}
