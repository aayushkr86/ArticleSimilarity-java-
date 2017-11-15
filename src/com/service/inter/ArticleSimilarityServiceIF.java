package com.service.inter;

import java.util.List;

import com.model.AdjectiveVO;
import com.model.ArticleNamesVO;
import com.model.ArticleTypesVO;
import com.model.CleanReviewUIModel;
import com.model.CompareArticleVO;
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

public interface ArticleSimilarityServiceIF {

	public StatusInfo storeReviews(ReviewModel reviewModel);

	public List<ReviewDetailModel> obtainAllReviews();

	public boolean storeWebSiteData(WebSiteUrlModel webSiteModel);

	List<StopWordsVO> retriveStopWords();

	StatusInfo addStopword(String stopWord);

	public StatusInfo doDataCleaning();

	public List<CleanReviewUIModel> retriveCleanReviewList();

	public StatusInfo doTokens();

	public List<TokenVO> retriveTokenList();

	StatusInfo removeStopword(String stopWord);

	public StatusInfo addKeyPhrase(String keyphrase, String type);

	public List<KeyPhraseModel> retriveKeyPhrases();

	public StatusInfo removeKeyPhrase(String keyphrase, String type);

	public StatusInfo addPhraseG(PhraseGModel phraseGModel);

	public StatusInfo removePhraseG(PhraseGModel phraseGModel);

	public List<PhraseGModel> retrivePhraseG();

	public StatusInfo genPhrases();

	public List<PhraseVO> retrivePhrasesList();

	public StatusInfo genAdjectives();

	public List<AdjectiveVO> retriveAdjectives();

	public StatusInfo genStructure1();

	public List<StructureVO> retriveStructure1List();

	public List<StructureVO> retriveStructure2List();
	
	public List<StructureVO> retriveStructure3List();

	public StatusInfo genStructure2();

	public StatusInfo genStructure3();

	public StatusInfo generateFreq();

	public List<FreqComputation> viewFreq();

	public List<ArticleNamesVO> retriveAllArticleNames();

	public List<ArticleTypesVO> retriveTypes();

	public StatusInfo compareArticles(
			ComparisionInputModel comparisionModel);

	public void storeAdjectives(String string);

	public List<AdjectiveVO> retriveNLPAdjectives();   

}
