package com.model;

import java.util.List;

public class CompareArticleVO {
	
	

	public double getUnionSum() {
		return unionSum;
	}


	public void setUnionSum(double unionSum) {
		this.unionSum = unionSum;
	}


	public double getIntersectionSum() {
		return intersectionSum;
	}


	public void setIntersectionSum(double intersectionSum) {
		this.intersectionSum = intersectionSum;
	}


	public List<TextFreqComputeVO> getTextFreqLeftArticle() {
		return textFreqLeftArticle;
	}


	public void setTextFreqLeftArticle(List<TextFreqComputeVO> textFreqLeftArticle) {
		this.textFreqLeftArticle = textFreqLeftArticle;
	}


	public List<TextFreqComputeVO> getTextFreqRArticle() {
		return textFreqRArticle;
	}


	public void setTextFreqRArticle(List<TextFreqComputeVO> textFreqRArticle) {
		this.textFreqRArticle = textFreqRArticle;
	}


	public double getSimilarityMeasure() {
		return similarityMeasure;
	}


	public void setSimilarityMeasure(double similarityMeasure) {
		this.similarityMeasure = similarityMeasure;
	}


	public List<FreqComputation> getLeftArticleFreqInfo() {
		return leftArticleFreqInfo;
	}


	public void setLeftArticleFreqInfo(List<FreqComputation> leftArticleFreqInfo) {
		this.leftArticleFreqInfo = leftArticleFreqInfo;
	}


	public List<FreqComputation> getRightArticleFreqInfo() {
		return rightArticleFreqInfo;
	}


	public void setRightArticleFreqInfo(List<FreqComputation> rightArticleFreqInfo) {
		this.rightArticleFreqInfo = rightArticleFreqInfo;
	}


	public List<String> getRightArticleKeyPhrases() {
		return rightArticleKeyPhrases;
	}


	public void setRightArticleKeyPhrases(List<String> rightArticleKeyPhrases) {
		this.rightArticleKeyPhrases = rightArticleKeyPhrases;
	}


	public List<String> getLeftArticleKeyPhrases() {
		return leftArticleKeyPhrases;
	}


	public void setLeftArticleKeyPhrases(List<String> leftArticleKeyPhrases) {
		this.leftArticleKeyPhrases = leftArticleKeyPhrases;
	}


	public List<String> getUnionSet() {
		return unionSet;
	}


	public void setUnionSet(List<String> unionSet) {
		this.unionSet = unionSet;
	}


	public List<String> getInstersectionSet() {
		return instersectionSet;
	}


	public void setInstersectionSet(List<String> instersectionSet) {
		this.instersectionSet = instersectionSet;
	}


	public boolean isStatus() {
		return status;
	}


	public void setStatus(boolean status) {
		this.status = status;
	}




	public List<String> getMsgList() {
		return msgList;
	}


	public void setMsgList(List<String> msgList) {
		this.msgList = msgList;
	}




	public boolean isSimilarityStatus() {
		return similarityStatus;
	}


	public void setSimilarityStatus(boolean similarityStatus) {
		this.similarityStatus = similarityStatus;
	}




	public String getSimilarityMeasureMsg() {
		return similarityMeasureMsg;
	}


	public void setSimilarityMeasureMsg(String similarityMeasureMsg) {
		this.similarityMeasureMsg = similarityMeasureMsg;
	}




	private List<String> rightArticleKeyPhrases;
	
	private List<String> leftArticleKeyPhrases;
	
	
	private double unionSum;
	
	private double intersectionSum;
	
	private List<TextFreqComputeVO> textFreqLeftArticle;
	
	private List<TextFreqComputeVO> textFreqRArticle;
	
	private double similarityMeasure;
	
	private List<FreqComputation> leftArticleFreqInfo;
	
	
	private List<FreqComputation> rightArticleFreqInfo;
	
	
	private List<String> unionSet;
	
	
	private List<String> instersectionSet;
	
	
	private boolean status;
	
	
	private List<String> msgList;
	
	
	private boolean similarityStatus;
	
	private String similarityMeasureMsg;
	
	

}
