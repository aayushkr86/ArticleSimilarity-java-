package com.model;

import java.io.Serializable;

public class AdjectiveVO  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int adjectiveId;

	public int getAdjectiveId() {
		return adjectiveId;
	}

	public void setAdjectiveId(int adjectiveId) {
		this.adjectiveId = adjectiveId;
	}

	public String getAdjective() {
		return adjective;
	}

	public void setAdjective(String adjective) {
		this.adjective = adjective;
	}

	private String adjective;

}
