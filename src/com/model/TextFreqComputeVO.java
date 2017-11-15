package com.model;

public class TextFreqComputeVO {
	
	private String keyPhraseName;
	
	public String getKeyPhraseName() {
		return keyPhraseName;
	}

	public void setKeyPhraseName(String keyPhraseName) {
		this.keyPhraseName = keyPhraseName;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getFreq() {
		return freq;
	}

	public void setFreq(int freq) {
		this.freq = freq;
	}

	public double getTextFreq() {
		return textFreq;
	}

	public void setTextFreq(double textFreq) {
		this.textFreq = textFreq;
	}

	private int count;
	
	private int freq;
	
	private double textFreq;

}
