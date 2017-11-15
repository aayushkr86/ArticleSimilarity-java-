package com.delegate.impl;

import java.util.List;

import com.delegate.inter.ArticleSimilarityDelegateIF;
import com.model.AdjectiveVO;
import com.model.ArticleNamesVO;
import com.model.ArticleTypesVO;
import com.model.CleanReviewUIModel;
import com.model.ComparisionInputModel;
import com.model.FreqComputation;
import com.model.KeyPhraseModel;
import com.model.PhraseGModel;
import com.model.PhraseVO;
import com.model.ReviewDetailModel;
import com.model.ReviewModel;
import com.model.StatusInfo;
import com.model.StopWordsVO;
import com.model.StructureVO;
import com.model.TokenVO;
import com.model.WebSiteUrlModel;
import com.service.inter.ArticleSimilarityServiceIF;

public class ArticleSimilarityDelegateImpl implements ArticleSimilarityDelegateIF {

	private ArticleSimilarityServiceIF articleService; 

	public void setArticleService(ArticleSimilarityServiceIF articleService) {
		this.articleService = articleService;
	}



	@Override
	public StatusInfo storeReviews(ReviewModel reviewModel) {
		return articleService.storeReviews(reviewModel);
	}



	@Override
	public List<ReviewDetailModel> retriveReviews() {

		return articleService.obtainAllReviews();

	}

	@Override
	public boolean storeWebSiteData(WebSiteUrlModel webSiteModel) {
		return articleService.storeWebSiteData(webSiteModel);
	}

	@Override
	public StatusInfo addStopword(String stopWord) {
		return articleService.addStopword(stopWord);
	}

	@Override
	public List<StopWordsVO> retriveStopWords() {
		return articleService.retriveStopWords();
	}

	@Override
	public StatusInfo doDataCleaning() {
		return articleService.doDataCleaning();
	}

	@Override
	public List<CleanReviewUIModel> retriveCleanReviewList() {
		return articleService.retriveCleanReviewList();
	}

	@Override
	public StatusInfo doTokens() {
		return articleService.doTokens();
	}

	@Override
	public List<TokenVO> retriveTokenList() {
		return articleService.retriveTokenList();
	}

	@Override
	public StatusInfo removeStopword(String stopWord) {
		return articleService.removeStopword(stopWord);
	}

	@Override
	public StatusInfo addKeyPhrase(String keyphrase, String type) {
		return articleService.addKeyPhrase(keyphrase, type);
	}

	@Override
	public List<KeyPhraseModel> retriveKeyPhrases() {
		return articleService.retriveKeyPhrases();
	}

	@Override
	public StatusInfo removeKeyPhrase(String keyphrase, String type) {
		return articleService.removeKeyPhrase(keyphrase, type);
	}

	@Override
	public StatusInfo addPhraseG(PhraseGModel phraseGModel) {
		return articleService.addPhraseG(phraseGModel);
	}

	@Override
	public StatusInfo removePhraseG(PhraseGModel phraseGModel) {
		return articleService.removePhraseG(phraseGModel);
	}

	@Override
	public List<PhraseGModel> retrivePhraseG() {
		return articleService.retrivePhraseG();
	}

	@Override
	public StatusInfo genPhrases() {
		return articleService.genPhrases();
	}

	@Override
	public List<PhraseVO> retrivePhrasesList() {
		return articleService.retrivePhrasesList();
	}

	@Override
	public StatusInfo genAdjectives() {
		return articleService.genAdjectives();
	}

	@Override
	public List<AdjectiveVO> retriveAdjectives() {
		return articleService.retriveAdjectives();
	}

	@Override
	public StatusInfo genStructure1() {
		return articleService.genStructure1();
	}

	@Override
	public List<StructureVO> retriveStructure1List() {
		return articleService.retriveStructure1List();
	}

	@Override
	public List<StructureVO> retriveStructure2List() {
		return articleService.retriveStructure2List();
	}

	@Override
	public List<StructureVO> retriveStructure3List() {
		return articleService.retriveStructure3List();
	}

	@Override
	public StatusInfo genStructure2() {
		return articleService.genStructure2();
	}

	@Override
	public StatusInfo genStructure3() {
		return articleService.genStructure3();
	}

	@Override
	public StatusInfo generateFreq() {
		return articleService.generateFreq();
	}

	@Override
	public List<FreqComputation> viewFreq() {
		return articleService.viewFreq();
	}

	@Override
	public List<ArticleNamesVO> retriveAllArticleNames() {
		return articleService.retriveAllArticleNames();
	}

	@Override
	public List<ArticleTypesVO> retriveTypes() {
		return articleService.retriveTypes();
	}

	@Override
	public StatusInfo compareArticles(
			ComparisionInputModel comparisionModel) {
		return articleService.compareArticles(comparisionModel);
	}

	@Override
	public List<AdjectiveVO> retriveNLPAdjectives() {
		return articleService.retriveNLPAdjectives();
	}

}
