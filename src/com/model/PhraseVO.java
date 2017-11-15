package com.model;

import java.io.Serializable;

public class PhraseVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public int getPhraseId() {
		return phraseId;
	}

	public void setPhraseId(int phraseId) {
		this.phraseId = phraseId;
	}

	public String getPhrase() {
		return phrase;
	}

	public void setPhrase(String phrase) {
		this.phrase = phrase;
	}

	private int phraseId;

	private String phrase;

}
