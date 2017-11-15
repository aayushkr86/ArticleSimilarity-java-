package com.model;

import java.io.Serializable;

public class KeyPhraseModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String keyPhrase;

	public String getKeyPhrase() {
		return keyPhrase;
	}

	public void setKeyPhrase(String keyPhrase) {
		this.keyPhrase = keyPhrase;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getKeyPhraseId() {
		return keyPhraseId;
	}

	public void setKeyPhraseId(int keyPhraseId) {
		this.keyPhraseId = keyPhraseId;
	}

	private String type;

	private int keyPhraseId;

}
