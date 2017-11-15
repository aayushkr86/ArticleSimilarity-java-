package com.dao.inter;

import java.util.List;
import java.util.Set;

import com.model.AdjectiveVO;
import com.model.CleanReviewModel;
import com.model.FreqComputation;
import com.model.KeyPhraseModel;
import com.model.PhraseGModel;
import com.model.PhraseVO;
import com.model.ReviewModel;
import com.model.ReviewModelObj;
import com.model.StatusInfo;
import com.model.StopWordsVO;
import com.model.StructureVO;
import com.model.TokenVO;

public interface ArticleSimilarityDAOIF {

	public StatusInfo insertReview(ReviewModel reviewModel);

	public List<ReviewModel> retriveAllReviews();

	StatusInfo insertStopWord(String stopWord);

	List<String> retriveStopWordsOnly();

	List<StopWordsVO> retriveStopWords();

	public StatusInfo removeStopword(String stopWord);

	public List<TokenVO> retriveAllTokens();

	public List<CleanReviewModel> retriveCleanReviews();

	public StatusInfo deleteAllCleanReviews();

	public StatusInfo insertCleanDetails(CleanReviewModel cleanReview);

	public StatusInfo insertToken(TokenVO tokenVO);

	public StatusInfo deleteAllTokens();

	public List<String> retriveKeyPhraseOnly(String type);

	public StatusInfo insertKeyPhrase(String keyphrase, String type);

	public List<KeyPhraseModel> retriveKeyPhrases();

	public StatusInfo removeKeyPhrase(String keyphrase, String type);

	StatusInfo insertPhraseG(PhraseGModel pharaseGModel);

	List<PhraseGModel> retrivePhraseGPhrases();

	StatusInfo removePhraseGForPhraseGTypeNoType(PhraseGModel phraseGModel);

	List<String> retrivePhraseGWhereTypeAndNoType(PhraseGModel pharaseGModel);

	public StatusInfo deleteAllPhrases();

	public List<String> retriveTokensOnly();

	public List<String> retriveAllKeyPhraseOnly();

	public List<String> retriveAllPhraseGOnly();

	public StatusInfo insertPhrasesList(Set<String> phrases);

	public List<PhraseVO> retrivePhraseList();

	public StatusInfo deleteAllAdjectives();

	public StatusInfo insertAdjectives(List<String> adjectiveList);

	public List<AdjectiveVO> retriveAllFullAdjectives();

	public List<String> retriveAllAdjectives();

	public StatusInfo insertStruture1(List<String> structureList);

	public List<StructureVO> retriveStructure1List();

	public StatusInfo deleteAllStructure1();

	public List<StructureVO> retriveStructure2List();

	public List<StructureVO> retriveStructure3List();

	public StatusInfo deleteAllStructure2();

	public StatusInfo insertStruture2(List<String> structureList);

	public List<String> retrivePhraseListOnly();

	public StatusInfo deleteAllStructure3();

	public StatusInfo insertStruture3(List<String> structureList);

	public StatusInfo deleteAllFreq();

	public List<String> retriveDiffrentConcepts();

	public List<String> retriveAllKeyPhraseOnlyForConcept(String type);

	public List<String> retriveAllStructure1Only();

	public List<String> retriveAllStructure2Only();

	public List<String> retriveAllStructure3Only();

	public List<String> retrivePhraseGWhereTYPEISNOT(String type);

	public StatusInfo insertFreqComputation(FreqComputation freqComputation);

	public List<FreqComputation> viewFreq();

	public List<String> retriveDistincetTokensForArticleName(
			String articleNameLeft);

	public List<String> retriveKeyPhraseOnlyForTokensAndType(
			List<String> tokenNamesLeft, String typeCombo);

	public int retriveCountForTokenAndArticleName(String word,
			String articleNameLeft);

	public int findNoOfTokensForArticleName(String articleNameLeft);

	public List<FreqComputation> retriveFreqComputationForArticleName(
			String articleNameLeft);

	public StatusInfo insertAdjectiveNLP(String str1);

	public List<String> retriveAdjectivesForNLP();

	public List<AdjectiveVO> retriveAllFullNLPAdjectives();

	public List<FreqComputation> retriveFreqComputationForArticleNameAndType(
			String articleNameLeft, String typeCombo);     

}
