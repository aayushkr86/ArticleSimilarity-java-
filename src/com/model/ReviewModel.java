package com.model;

import java.io.Serializable;

public class ReviewModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String getArticleName() {
		return articleName;
	}

	public void setArticleName(String articleName) {
		this.articleName = articleName;
	}

	public String getArticleDesc() {
		return articleDesc;
	}

	public void setArticleDesc(String articleDesc) {
		this.articleDesc = articleDesc;
	}

	private String articleName;
	
	private String articleDesc;
	
	

}
