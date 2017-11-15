package com.controller.inter;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.model.AJAXResponse;
import com.model.ComparisionInputModel;
import com.model.PhraseGModel;
import com.model.ReviewModel;

public interface ArticleSimilarityControllerIF {

	public @ResponseBody AJAXResponse reviewAnalyzerStore(
			@RequestBody ReviewModel reviewModel);

	public @ResponseBody AJAXResponse obtainAllReviews();

	ModelAndView addStopWord(String stopWord);

	AJAXResponse viewStopWords(HttpServletRequest request);

	ModelAndView removeStopWord(String stopWord);

	AJAXResponse viewTokens(HttpServletRequest request);

	ModelAndView doTokens(HttpServletRequest request);

	AJAXResponse viewCleanReviews(HttpServletRequest request);

	ModelAndView doDataCleaning(HttpServletRequest request);

	ModelAndView addKeyPhrase(String stopWord, String type);

	AJAXResponse viewKeyPhrases(HttpServletRequest request);

	ModelAndView removeKeyPhrase(String keyphrase, String type);

	ModelAndView addPhraseG(PhraseGModel phraseGModel);

	AJAXResponse viewPhraseG(HttpServletRequest request);

	ModelAndView removePhraseG(PhraseGModel phraseGModel);

	ModelAndView generatePhrases();

	AJAXResponse viewPhrases(HttpServletRequest request);

	ModelAndView generateAdjectives();

	AJAXResponse viewAdjectives(HttpServletRequest request);

	ModelAndView generateStructure1();

	AJAXResponse viewStrcuture1(HttpServletRequest request);

	AJAXResponse viewStrcuture2(HttpServletRequest request);

	AJAXResponse viewStrcuture3(HttpServletRequest request);

	ModelAndView generateStructure2();

	ModelAndView generateStructure3();

	ModelAndView generateFreq();

	AJAXResponse viewFreq(HttpServletRequest request);

	AJAXResponse retriveAllArticleNames(HttpServletRequest request);

	AJAXResponse retriveTypes(HttpServletRequest request);

	AJAXResponse compareArticles(HttpServletRequest request,@RequestBody ComparisionInputModel comparisionInputModel);

	AJAXResponse viewNLPAdjectives(HttpServletRequest request);

	
}
