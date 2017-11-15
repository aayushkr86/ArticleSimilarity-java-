package com.model;

import java.io.Serializable;

public class PhraseGModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	private String type;

	public String getNoType() {
		return noType;
	}

	public void setNoType(String noType) {
		this.noType = noType;
	}

	public String getPhraseG() {
		return phraseG;
	}

	public void setPhraseG(String phraseG) {
		this.phraseG = phraseG;
	}



	public int getPhraseGId() {
		return phraseGId;
	}

	public void setPhraseGId(int phraseGId) {
		this.phraseGId = phraseGId;
	}

	private String noType;

	private String phraseG;

	private int phraseGId;

}
