package com.controller.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.constants.ReviewConstantsIF;
import com.controller.inter.ArticleSimilarityControllerIF;
import com.delegate.inter.ArticleSimilarityDelegateIF;
import com.model.AJAXResponse;
import com.model.AdjectiveVO;
import com.model.ArticleNamesVO;
import com.model.ArticleTypesVO;
import com.model.CleanReviewUIModel;
import com.model.CompareArticleVO;
import com.model.ComparisionInputModel;
import com.model.FreqComputation;
import com.model.KeyPhraseModel;
import com.model.Message;
import com.model.PhraseGModel;
import com.model.PhraseVO;
import com.model.ReviewDetailModel;
import com.model.ReviewModel;
import com.model.StatusInfo;
import com.model.StopWordsVO;
import com.model.StructureVO;
import com.model.TokenVO;
import com.model.WebSiteUrlModel;

@Controller
public class ArticleSimilarityControllerImpl implements
		ArticleSimilarityControllerIF {

	@Autowired
	private ArticleSimilarityDelegateIF articleDelegate;

	public void setArticleDelegate(ArticleSimilarityDelegateIF articleDelegate) {
		this.articleDelegate = articleDelegate;
	}

	@Override
	@RequestMapping(value = "/storereview.do", method = { RequestMethod.POST,
			RequestMethod.GET })
	public @ResponseBody AJAXResponse reviewAnalyzerStore(
			@RequestBody ReviewModel reviewModel) {
		AJAXResponse ajaxRes = null;
		try {
			ajaxRes = new AJAXResponse();
			StatusInfo statusInfo = new StatusInfo();
			statusInfo = articleDelegate.storeReviews(reviewModel);
			if (statusInfo.isStatus()) {
				ajaxRes.setStatus(true);
				ajaxRes.setMessage(ReviewConstantsIF.Message.ARTICLE_STORED_SUCESSFULLY);
				return ajaxRes;
			} else {
				ajaxRes = new AJAXResponse();
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message msg = new Message();
				msg.setMsg(ReviewConstantsIF.Message.ARTICLE_FAILED);
				ebErrors.add(msg);
				ajaxRes.setEbErrors(ebErrors);
				return ajaxRes;
			}

		} catch (Exception e) {
			ajaxRes = new AJAXResponse();
			List<Message> ebErrors = new ArrayList<Message>();
			ajaxRes.setStatus(false);
			Message msg = new Message();
			msg.setMsg(ReviewConstantsIF.Message.MESSAGE_INTERNAL);
			ebErrors.add(msg);
			ajaxRes.setEbErrors(ebErrors);
			return ajaxRes;
		}
	}

	@Override
	@RequestMapping(value = "/retriveAllReviews.do", method = {
			RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse obtainAllReviews() {
		AJAXResponse ajaxRes = null;

		try {
			ajaxRes = new AJAXResponse();

			List<ReviewDetailModel> reviewDetailList = articleDelegate
					.retriveReviews();

			if (!reviewDetailList.isEmpty() && !(null == reviewDetailList)
					&& !(reviewDetailList.size() == 0)) {
				ajaxRes.setModel(reviewDetailList);
				ajaxRes.setStatus(true);
				ajaxRes.setMessage(ReviewConstantsIF.Message.ARTICLES_RETRIVE_SUCESSFUL);
				return ajaxRes;
			} else {
				ajaxRes = new AJAXResponse();
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message msg = new Message();
				msg.setMsg(ReviewConstantsIF.Message.NO_ARTCILES_FOUND);
				ebErrors.add(msg);
				ajaxRes.setEbErrors(ebErrors);
				return ajaxRes;
			}

		} catch (Exception e) {
			ajaxRes = new AJAXResponse();
			List<Message> ebErrors = new ArrayList<Message>();
			ajaxRes.setStatus(false);
			Message msg = new Message();
			msg.setMsg(ReviewConstantsIF.Message.MESSAGE_INTERNAL);
			ebErrors.add(msg);
			ajaxRes.setEbErrors(ebErrors);
			return ajaxRes;
		}
	}

	@RequestMapping(value = "/webSubmission.do", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView storeWebSiteInfo(HttpServletRequest request,
			WebSiteUrlModel webSiteModel) {

		ModelAndView mv = null;
		AJAXResponse ajaxRes = null;
		try {
			ajaxRes = validateWebSiteUrl(webSiteModel);

			if (!ajaxRes.isStatus()) {
				return new ModelAndView(
						ReviewConstantsIF.Views.WEBDATA_SUBMIT_INPUT,
						ReviewConstantsIF.Keys.OBJ, ajaxRes);
			}

			boolean status = articleDelegate.storeWebSiteData(webSiteModel);
			if (!status) {
				List<Message> ebErrors = new ArrayList<Message>();

				Message webSiteUrlMsg = new Message();
				webSiteUrlMsg
						.setMsg(ReviewConstantsIF.Message.MESSAGE_INTERNAL);
				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				ajaxRes.setStatus(false);
				return new ModelAndView(
						ReviewConstantsIF.Views.WEBDATA_SUBMIT_INPUT,
						ReviewConstantsIF.Keys.OBJ, ajaxRes);
			}
			if (status) {
				mv = new ModelAndView();
				ajaxRes.setStatus(true);
				ajaxRes.setMessage(ReviewConstantsIF.Message.STORE_WEBDATA_SUCESS);
				return new ModelAndView(
						ReviewConstantsIF.Views.VIEW_ADMIN_SUCESS_PAGE,
						ReviewConstantsIF.Keys.OBJ, ajaxRes);
			}

		} catch (Exception e) {
			List<Message> ebErrors = new ArrayList<Message>();
			ajaxRes.setStatus(false);
			Message webSiteUrlMsg = new Message();
			webSiteUrlMsg.setMsg(ReviewConstantsIF.Message.MESSAGE_INTERNAL);

			ebErrors.add(webSiteUrlMsg);
			ajaxRes.setEbErrors(ebErrors);
			return new ModelAndView(
					ReviewConstantsIF.Views.WEBDATA_SUBMIT_INPUT,
					ReviewConstantsIF.Keys.OBJ, ajaxRes);
		}
		return mv;
	}

	private AJAXResponse validateWebSiteUrl(WebSiteUrlModel webSiteModel) {

		AJAXResponse ajax = new AJAXResponse();
		ajax.setStatus(true);
		List<Message> messageList = new ArrayList<Message>();
		if (null == webSiteModel) {

			Message webSiteUrlMsg = new Message();
			webSiteUrlMsg.setMsg(ReviewConstantsIF.Message.WEBSITE_URL + "  "
					+ ReviewConstantsIF.Message.NULL_ERROR_MSG);
			messageList.add(webSiteUrlMsg);

			ajax.setEbErrors(messageList);
			ajax.setStatus(false);
			return ajax;
		}

		if (doNullCheck(webSiteModel.getWebUrl())) {

			Message webSiteUrlMsg = new Message();
			webSiteUrlMsg.setMsg(ReviewConstantsIF.Message.WEBSITE_URL + "  "
					+ ReviewConstantsIF.Message.NULL_ERROR_MSG);
			messageList.add(webSiteUrlMsg);
			ajax.setEbErrors(messageList);
			ajax.setStatus(false);
			return ajax;
		}

		if (doNullCheck(webSiteModel.getArticleName())) {

			Message webSiteUrlMsg = new Message();
			webSiteUrlMsg.setMsg(ReviewConstantsIF.Message.ARTICLE_NAME_EMPTY);
			messageList.add(webSiteUrlMsg);
			ajax.setEbErrors(messageList);
			ajax.setStatus(false);
			return ajax;
		}

		return ajax;
	}

	@Override
	@RequestMapping(value = "/addStopword.do", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView addStopWord(String stopWord) {
		ModelAndView mv = null;
		AJAXResponse ajaxRes = null;
		try {
			ajaxRes = new AJAXResponse();
			if (null == stopWord || stopWord.isEmpty()) {
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message webSiteUrlMsg = new Message();
				webSiteUrlMsg.setMsg(ReviewConstantsIF.Message.EMPTY_STOPWORD);
				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return new ModelAndView(ReviewConstantsIF.Views.STOPWORD_INPUT,
						ReviewConstantsIF.Keys.OBJ, ajaxRes);

			}
			StatusInfo statusInfo = articleDelegate.addStopword(stopWord);
			if (!statusInfo.isStatus()) {
				ajaxRes = new AJAXResponse();
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message webSiteUrlMsg = new Message();
				webSiteUrlMsg
						.setMsg(ReviewConstantsIF.Message.STOPWORD_ADD_FAILED);

				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return new ModelAndView(ReviewConstantsIF.Views.STOPWORD_INPUT,
						ReviewConstantsIF.Keys.OBJ, ajaxRes);

			} else {
				ajaxRes = new AJAXResponse();
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message webSiteUrlMsg = new Message();
				webSiteUrlMsg
						.setMsg(ReviewConstantsIF.Message.STOPWORD_ADD_SUCESS);

				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return new ModelAndView(
						ReviewConstantsIF.Views.VIEW_ADMIN_SUCESS_PAGE,
						ReviewConstantsIF.Keys.OBJ, ajaxRes);
			}

		} catch (Exception e) {
			ajaxRes = new AJAXResponse();
			List<Message> ebErrors = new ArrayList<Message>();
			ajaxRes.setStatus(false);
			Message webSiteUrlMsg = new Message();
			webSiteUrlMsg.setMsg(ReviewConstantsIF.Message.INTERNAL_ERROR);

			ebErrors.add(webSiteUrlMsg);
			ajaxRes.setEbErrors(ebErrors);
			return new ModelAndView(ReviewConstantsIF.Views.STOPWORD_INPUT,
					ReviewConstantsIF.Keys.OBJ, ajaxRes);
		}
	}

	@Override
	@RequestMapping(value = "/viewStopwords.do", method = { RequestMethod.POST,
			RequestMethod.GET })
	public @ResponseBody AJAXResponse viewStopWords(HttpServletRequest request) {
		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			List<StopWordsVO> keyWordList = articleDelegate.retriveStopWords();
			if (null == keyWordList) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message();
				msg.setMsg(ReviewConstantsIF.Message.EMPTY_STOPWORDS);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			ajaxResponse.setModel(keyWordList);
			ajaxResponse.setMessage(ReviewConstantsIF.Message.STOPWORD_SUCESS);
			return ajaxResponse;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			Message msg = new Message();
			msg.setMsg(ReviewConstantsIF.Message.INTERNAL_ERROR);
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;
		}
	}

	private boolean doNullCheck(String obj) {
		return (null == obj || obj.length() == 0 || obj.isEmpty()) ? true
				: false;
	}

	@Override
	@RequestMapping(value = "/removeStopword.do", method = {
			RequestMethod.POST, RequestMethod.GET })
	public ModelAndView removeStopWord(String stopWord) {
		ModelAndView mv = null;
		AJAXResponse ajaxRes = null;
		try {
			ajaxRes = new AJAXResponse();
			if (null == stopWord || stopWord.isEmpty()) {
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message webSiteUrlMsg = new Message();
				webSiteUrlMsg.setMsg(ReviewConstantsIF.Message.EMPTY_STOPWORD);
				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return new ModelAndView(
						ReviewConstantsIF.Views.REMOVESTOPWORD_INPUT,
						ReviewConstantsIF.Keys.OBJ, ajaxRes);

			}
			StatusInfo statusInfo = articleDelegate.removeStopword(stopWord);
			if (!statusInfo.isStatus()) {
				ajaxRes = new AJAXResponse();
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message webSiteUrlMsg = new Message();
				webSiteUrlMsg
						.setMsg(ReviewConstantsIF.Message.STOPWORD_REMOVE_FAILED);

				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return new ModelAndView(
						ReviewConstantsIF.Views.REMOVESTOPWORD_INPUT,
						ReviewConstantsIF.Keys.OBJ, ajaxRes);

			} else {
				ajaxRes = new AJAXResponse();
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message webSiteUrlMsg = new Message();
				webSiteUrlMsg
						.setMsg(ReviewConstantsIF.Message.STOPWORD_REMOVE_SUCESS);

				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return new ModelAndView(
						ReviewConstantsIF.Views.VIEW_ADMIN_SUCESS_PAGE,
						ReviewConstantsIF.Keys.OBJ, ajaxRes);
			}

		} catch (Exception e) {
			ajaxRes = new AJAXResponse();
			List<Message> ebErrors = new ArrayList<Message>();
			ajaxRes.setStatus(false);
			Message webSiteUrlMsg = new Message();
			webSiteUrlMsg.setMsg(ReviewConstantsIF.Message.INTERNAL_ERROR);

			ebErrors.add(webSiteUrlMsg);
			ajaxRes.setEbErrors(ebErrors);
			return new ModelAndView(
					ReviewConstantsIF.Views.REMOVESTOPWORD_INPUT,
					ReviewConstantsIF.Keys.OBJ, ajaxRes);
		}
	}

	@RequestMapping(value = "/cleandata.do", method = { RequestMethod.POST,
			RequestMethod.GET })
	@Override
	public ModelAndView doDataCleaning(HttpServletRequest request) {
		try {

			StatusInfo statusInfo = articleDelegate.doDataCleaning();

			if (!statusInfo.isStatus()) {
				AJAXResponse ajax = new AJAXResponse();
				ajax.setStatus(false);
				Message msg = new Message();
				msg.setMsg(statusInfo.getErrMsg());
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajax.setEbErrors(ebErrors);
				return new ModelAndView(
						ReviewConstantsIF.Views.VIEW_ADMIN_ERROR_PAGE,
						ReviewConstantsIF.Keys.OBJ, ajax);
			}
			AJAXResponse ajax = new AJAXResponse();
			ajax.setStatus(false);
			Message msg = new Message();
			msg.setMsg(ReviewConstantsIF.Message.CLEANREVIEWS_SUCESS);
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajax.setEbErrors(ebErrors);

			return new ModelAndView(
					ReviewConstantsIF.Views.VIEW_ADMIN_SUCESS_PAGE,
					ReviewConstantsIF.Keys.OBJ, ajax);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			AJAXResponse ajax = new AJAXResponse();
			ajax.setStatus(false);
			Message msg = new Message();
			msg.setMsg(ReviewConstantsIF.Message.INTERNAL_ERROR);
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajax.setEbErrors(ebErrors);
			return new ModelAndView(
					ReviewConstantsIF.Views.VIEW_ADMIN_ERROR_PAGE,
					ReviewConstantsIF.Keys.OBJ, ajax);
		}
	}

	@Override
	@RequestMapping(value = "/viewCleanReviews.do", method = {
			RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse viewCleanReviews(
			HttpServletRequest request) {
		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			List<CleanReviewUIModel> reviewList = articleDelegate
					.retriveCleanReviewList();
			if (null == reviewList) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message();
				msg.setMsg(ReviewConstantsIF.Message.EMPTY_REVIEWSLIST);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			ajaxResponse.setModel(reviewList);
			ajaxResponse
					.setMessage(ReviewConstantsIF.Message.REVIEWS_FETCH_SUCESS);
			return ajaxResponse;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			Message msg = new Message();
			msg.setMsg(ReviewConstantsIF.Message.INTERNAL_ERROR);
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;
		}
	}

	@Override
	@RequestMapping(value = "/doTokens.do", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView doTokens(HttpServletRequest request) {
		try {

			StatusInfo statusInfo = articleDelegate.doTokens();

			if (!statusInfo.isStatus()) {
				AJAXResponse ajax = new AJAXResponse();
				ajax.setStatus(false);
				Message msg = new Message();
				msg.setMsg(statusInfo.getErrMsg());
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajax.setEbErrors(ebErrors);
				return new ModelAndView(
						ReviewConstantsIF.Views.VIEW_ADMIN_ERROR_PAGE,
						ReviewConstantsIF.Keys.OBJ, ajax);
			}
			AJAXResponse ajax = new AJAXResponse();
			ajax.setStatus(false);
			Message msg = new Message();
			msg.setMsg(ReviewConstantsIF.Message.TOKENS_SUCESS);
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajax.setEbErrors(ebErrors);

			return new ModelAndView(
					ReviewConstantsIF.Views.VIEW_ADMIN_SUCESS_PAGE,
					ReviewConstantsIF.Keys.OBJ, ajax);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			AJAXResponse ajax = new AJAXResponse();
			ajax.setStatus(false);
			Message msg = new Message();
			msg.setMsg(ReviewConstantsIF.Message.INTERNAL_ERROR);
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajax.setEbErrors(ebErrors);
			return new ModelAndView(
					ReviewConstantsIF.Views.VIEW_ADMIN_ERROR_PAGE,
					ReviewConstantsIF.Keys.OBJ, ajax);
		}
	}

	@Override
	@RequestMapping(value = "/viewTokens.do", method = { RequestMethod.POST,
			RequestMethod.GET })
	public @ResponseBody AJAXResponse viewTokens(HttpServletRequest request) {
		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			List<TokenVO> tokenList = articleDelegate.retriveTokenList();
			if (null == tokenList) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message();
				msg.setMsg(ReviewConstantsIF.Message.EMPTY_TOKENS);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			ajaxResponse.setModel(tokenList);
			ajaxResponse
					.setMessage(ReviewConstantsIF.Message.TOKENRETRIVAL_SUCESS);
			return ajaxResponse;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			Message msg = new Message();
			msg.setMsg(ReviewConstantsIF.Message.INTERNAL_ERROR);
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;
		}
	}

	@Override
	@RequestMapping(value = "/addKeyPhrase.do", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView addKeyPhrase(@RequestParam String keyphrase,
			@RequestParam String type) {
		ModelAndView mv = null;
		AJAXResponse ajaxRes = null;
		try {
			ajaxRes = new AJAXResponse();
			if (null == keyphrase || keyphrase.isEmpty()) {
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message webSiteUrlMsg = new Message();
				webSiteUrlMsg.setMsg(ReviewConstantsIF.Message.EMPTY_KEYPHRASE);
				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return new ModelAndView(
						ReviewConstantsIF.Views.KEYPHRASEADD_INPUT,
						ReviewConstantsIF.Keys.OBJ, ajaxRes);

			}
			StatusInfo statusInfo = articleDelegate.addKeyPhrase(keyphrase,
					type);
			if (!statusInfo.isStatus()) {
				ajaxRes = new AJAXResponse();
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message webSiteUrlMsg = new Message();
				webSiteUrlMsg
						.setMsg(ReviewConstantsIF.Message.KEYPHRASE_ADD_FAILED);

				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return new ModelAndView(
						ReviewConstantsIF.Views.KEYPHRASEADD_INPUT,
						ReviewConstantsIF.Keys.OBJ, ajaxRes);

			} else {
				ajaxRes = new AJAXResponse();
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message webSiteUrlMsg = new Message();
				webSiteUrlMsg
						.setMsg(ReviewConstantsIF.Message.KEYPHRASE_ADD_SUCESS);

				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return new ModelAndView(
						ReviewConstantsIF.Views.VIEW_ADMIN_SUCESS_PAGE,
						ReviewConstantsIF.Keys.OBJ, ajaxRes);
			}

		} catch (Exception e) {
			ajaxRes = new AJAXResponse();
			List<Message> ebErrors = new ArrayList<Message>();
			ajaxRes.setStatus(false);
			Message webSiteUrlMsg = new Message();
			webSiteUrlMsg.setMsg(ReviewConstantsIF.Message.INTERNAL_ERROR);

			ebErrors.add(webSiteUrlMsg);
			ajaxRes.setEbErrors(ebErrors);
			return new ModelAndView(ReviewConstantsIF.Views.STOPWORD_INPUT,
					ReviewConstantsIF.Keys.OBJ, ajaxRes);
		}
	}

	@Override
	@RequestMapping(value = "/viewKeyPhrases.do", method = {
			RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse viewKeyPhrases(HttpServletRequest request) {
		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			List<KeyPhraseModel> keyWordList = articleDelegate
					.retriveKeyPhrases();
			if (null == keyWordList) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message();
				msg.setMsg(ReviewConstantsIF.Message.EMPTY_KEYPHRASE);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			ajaxResponse.setModel(keyWordList);
			ajaxResponse
					.setMessage(ReviewConstantsIF.Message.KEYPHRASE_RETRIVE_SUCESS);
			return ajaxResponse;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			Message msg = new Message();
			msg.setMsg(ReviewConstantsIF.Message.INTERNAL_ERROR);
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;
		}
	}

	@Override
	@RequestMapping(value = "/removeKeyPhrase.do", method = {
			RequestMethod.POST, RequestMethod.GET })
	public ModelAndView removeKeyPhrase(@RequestParam String keyphrase,
			@RequestParam String type) {
		ModelAndView mv = null;
		AJAXResponse ajaxRes = null;
		try {
			ajaxRes = new AJAXResponse();
			if (null == keyphrase || keyphrase.isEmpty()) {
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message webSiteUrlMsg = new Message();
				webSiteUrlMsg.setMsg(ReviewConstantsIF.Message.EMPTY_KEYPHRASE);
				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return new ModelAndView(
						ReviewConstantsIF.Views.REMOVE_KEYPHRASE_INPUT,
						ReviewConstantsIF.Keys.OBJ, ajaxRes);

			}
			StatusInfo statusInfo = articleDelegate.removeKeyPhrase(keyphrase,
					type);
			if (!statusInfo.isStatus()) {
				ajaxRes = new AJAXResponse();
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message webSiteUrlMsg = new Message();
				webSiteUrlMsg
						.setMsg(ReviewConstantsIF.Message.KEYPHRASE_REMOVE_FAILED);

				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return new ModelAndView(
						ReviewConstantsIF.Views.REMOVE_KEYPHRASE_INPUT,
						ReviewConstantsIF.Keys.OBJ, ajaxRes);

			} else {
				ajaxRes = new AJAXResponse();
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(true);
				Message webSiteUrlMsg = new Message();
				webSiteUrlMsg
						.setMsg(ReviewConstantsIF.Message.KEYPHRASE_REMOVE_SUCESS);

				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return new ModelAndView(
						ReviewConstantsIF.Views.VIEW_ADMIN_SUCESS_PAGE,
						ReviewConstantsIF.Keys.OBJ, ajaxRes);
			}

		} catch (Exception e) {
			ajaxRes = new AJAXResponse();
			List<Message> ebErrors = new ArrayList<Message>();
			ajaxRes.setStatus(false);
			Message webSiteUrlMsg = new Message();
			webSiteUrlMsg.setMsg(ReviewConstantsIF.Message.INTERNAL_ERROR);

			ebErrors.add(webSiteUrlMsg);
			ajaxRes.setEbErrors(ebErrors);
			return new ModelAndView(
					ReviewConstantsIF.Views.REMOVE_KEYPHRASE_INPUT,
					ReviewConstantsIF.Keys.OBJ, ajaxRes);
		}
	}

	@Override
	@RequestMapping(value = "/addPhraseG.do", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView addPhraseG(@ModelAttribute PhraseGModel phraseGModel) {
		ModelAndView mv = null;
		AJAXResponse ajaxRes = null;
		try {
			ajaxRes = new AJAXResponse();
			if (null == phraseGModel.getPhraseG()
					|| phraseGModel.getPhraseG().isEmpty()) {
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message webSiteUrlMsg = new Message();
				webSiteUrlMsg.setMsg(ReviewConstantsIF.Message.EMPTY_PHRASEG);
				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return new ModelAndView(
						ReviewConstantsIF.Views.PHRASEG_ADD_INPUT,
						ReviewConstantsIF.Keys.OBJ, ajaxRes);

			}

			if (phraseGModel.getNoType().equals(phraseGModel.getType())) {

				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message webSiteUrlMsg = new Message();
				webSiteUrlMsg
						.setMsg(ReviewConstantsIF.Message.NOTYPE_TYPE_CANNOT_BE_SAME);
				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return new ModelAndView(
						ReviewConstantsIF.Views.PHRASEG_ADD_INPUT,
						ReviewConstantsIF.Keys.OBJ, ajaxRes);
			}

			StatusInfo statusInfo = articleDelegate.addPhraseG(phraseGModel);
			if (!statusInfo.isStatus()) {
				ajaxRes = new AJAXResponse();
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message webSiteUrlMsg = new Message();
				webSiteUrlMsg
						.setMsg(ReviewConstantsIF.Message.PHRASEG_ADD_FAILED);

				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return new ModelAndView(
						ReviewConstantsIF.Views.PHRASEG_ADD_INPUT,
						ReviewConstantsIF.Keys.OBJ, ajaxRes);

			} else {
				ajaxRes = new AJAXResponse();
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message webSiteUrlMsg = new Message();
				webSiteUrlMsg
						.setMsg(ReviewConstantsIF.Message.PHRASEG_ADD_SUCESS);

				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return new ModelAndView(
						ReviewConstantsIF.Views.VIEW_ADMIN_SUCESS_PAGE,
						ReviewConstantsIF.Keys.OBJ, ajaxRes);
			}

		} catch (Exception e) {
			ajaxRes = new AJAXResponse();
			List<Message> ebErrors = new ArrayList<Message>();
			ajaxRes.setStatus(false);
			Message webSiteUrlMsg = new Message();
			webSiteUrlMsg.setMsg(ReviewConstantsIF.Message.INTERNAL_ERROR);

			ebErrors.add(webSiteUrlMsg);
			ajaxRes.setEbErrors(ebErrors);
			return new ModelAndView(ReviewConstantsIF.Views.PHRASEG_ADD_INPUT,
					ReviewConstantsIF.Keys.OBJ, ajaxRes);
		}
	}

	@Override
	@RequestMapping(value = "/removePhraseG.do", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView removePhraseG(@ModelAttribute PhraseGModel phraseGModel) {
		ModelAndView mv = null;
		AJAXResponse ajaxRes = null;
		try {
			ajaxRes = new AJAXResponse();
			if (null == phraseGModel.getPhraseG()
					|| phraseGModel.getPhraseG().isEmpty()) {
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message webSiteUrlMsg = new Message();
				webSiteUrlMsg.setMsg(ReviewConstantsIF.Message.EMPTY_PHRASEG);
				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return new ModelAndView(
						ReviewConstantsIF.Views.REMOVE_PHRASEG_INPUT,
						ReviewConstantsIF.Keys.OBJ, ajaxRes);

			}
			StatusInfo statusInfo = articleDelegate.removePhraseG(phraseGModel);
			if (!statusInfo.isStatus()) {
				ajaxRes = new AJAXResponse();
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message webSiteUrlMsg = new Message();
				webSiteUrlMsg
						.setMsg(ReviewConstantsIF.Message.PHRASEG_REMOVE_FAILED);

				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return new ModelAndView(
						ReviewConstantsIF.Views.REMOVE_PHRASEG_INPUT,
						ReviewConstantsIF.Keys.OBJ, ajaxRes);

			} else {
				ajaxRes = new AJAXResponse();
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(true);
				Message webSiteUrlMsg = new Message();
				webSiteUrlMsg
						.setMsg(ReviewConstantsIF.Message.PHRASEG_REMOVE_SUCESS);

				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return new ModelAndView(
						ReviewConstantsIF.Views.VIEW_ADMIN_SUCESS_PAGE,
						ReviewConstantsIF.Keys.OBJ, ajaxRes);
			}

		} catch (Exception e) {
			ajaxRes = new AJAXResponse();
			List<Message> ebErrors = new ArrayList<Message>();
			ajaxRes.setStatus(false);
			Message webSiteUrlMsg = new Message();
			webSiteUrlMsg.setMsg(ReviewConstantsIF.Message.INTERNAL_ERROR);

			ebErrors.add(webSiteUrlMsg);
			ajaxRes.setEbErrors(ebErrors);
			return new ModelAndView(
					ReviewConstantsIF.Views.REMOVE_PHRASEG_INPUT,
					ReviewConstantsIF.Keys.OBJ, ajaxRes);
		}
	}

	@Override
	@RequestMapping(value = "/viewPhraseG.do", method = { RequestMethod.POST,
			RequestMethod.GET })
	public @ResponseBody AJAXResponse viewPhraseG(HttpServletRequest request) {
		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			List<PhraseGModel> keyWordList = articleDelegate.retrivePhraseG();
			if (null == keyWordList) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message();
				msg.setMsg(ReviewConstantsIF.Message.EMPTY_PHRASEG);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			ajaxResponse.setModel(keyWordList);
			ajaxResponse
					.setMessage(ReviewConstantsIF.Message.PHRASEG_RETRIVE_SUCESS);
			return ajaxResponse;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			Message msg = new Message();
			msg.setMsg(ReviewConstantsIF.Message.INTERNAL_ERROR);
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;
		}
	}

	@Override
	@RequestMapping(value = "/genPhrases.do", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView generatePhrases() {
		ModelAndView mv = null;
		AJAXResponse ajaxRes = null;
		try {
			ajaxRes = new AJAXResponse();
			StatusInfo statusInfo = articleDelegate.genPhrases();
			if (!statusInfo.isStatus()) {
				ajaxRes = new AJAXResponse();
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message webSiteUrlMsg = new Message();
				webSiteUrlMsg
						.setMsg(ReviewConstantsIF.Message.PHRASES_GENERATION_FAILED);
				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return new ModelAndView(ReviewConstantsIF.Views.FAILURE_PAGE,
						ReviewConstantsIF.Keys.OBJ, ajaxRes);

			} else {
				ajaxRes = new AJAXResponse();
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message webSiteUrlMsg = new Message();
				webSiteUrlMsg
						.setMsg(ReviewConstantsIF.Message.PHRASES_GEN_SUCESS);

				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return new ModelAndView(
						ReviewConstantsIF.Views.VIEW_ADMIN_SUCESS_PAGE,
						ReviewConstantsIF.Keys.OBJ, ajaxRes);
			}

		} catch (Exception e) {
			ajaxRes = new AJAXResponse();
			List<Message> ebErrors = new ArrayList<Message>();
			ajaxRes.setStatus(false);
			Message webSiteUrlMsg = new Message();
			webSiteUrlMsg.setMsg(ReviewConstantsIF.Message.INTERNAL_ERROR);

			ebErrors.add(webSiteUrlMsg);
			ajaxRes.setEbErrors(ebErrors);
			return new ModelAndView(ReviewConstantsIF.Views.FAILURE_PAGE,
					ReviewConstantsIF.Keys.OBJ, ajaxRes);
		}
	}

	@Override
	@RequestMapping(value = "/viewPhrases.do", method = { RequestMethod.POST,
			RequestMethod.GET })
	public @ResponseBody AJAXResponse viewPhrases(HttpServletRequest request) {
		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			List<PhraseVO> tokenList = articleDelegate.retrivePhrasesList();
			if (null == tokenList) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message();
				msg.setMsg(ReviewConstantsIF.Message.EMPTY_PHRASES);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			ajaxResponse.setModel(tokenList);
			ajaxResponse
					.setMessage(ReviewConstantsIF.Message.PHRASES_RETRIVAL_SUCESS);
			return ajaxResponse;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			Message msg = new Message();
			msg.setMsg(ReviewConstantsIF.Message.INTERNAL_ERROR);
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;
		}
	}

	@Override
	@RequestMapping(value = "/genAdjective.do", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView generateAdjectives() {
		ModelAndView mv = null;
		AJAXResponse ajaxRes = null;
		try {
			ajaxRes = new AJAXResponse();
			StatusInfo statusInfo = articleDelegate.genAdjectives();
			if (!statusInfo.isStatus()) {
				ajaxRes = new AJAXResponse();
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message webSiteUrlMsg = new Message();
				webSiteUrlMsg
						.setMsg(ReviewConstantsIF.Message.ADJECTIVE_GENERATION_FAILED);
				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return new ModelAndView(ReviewConstantsIF.Views.FAILURE_PAGE,
						ReviewConstantsIF.Keys.OBJ, ajaxRes);

			} else {
				ajaxRes = new AJAXResponse();
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message webSiteUrlMsg = new Message();
				webSiteUrlMsg
						.setMsg(ReviewConstantsIF.Message.ADJECTIVE_GEN_SUCESS);

				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return new ModelAndView(
						ReviewConstantsIF.Views.VIEW_ADMIN_SUCESS_PAGE,
						ReviewConstantsIF.Keys.OBJ, ajaxRes);
			}

		} catch (Exception e) {
			ajaxRes = new AJAXResponse();
			List<Message> ebErrors = new ArrayList<Message>();
			ajaxRes.setStatus(false);
			Message webSiteUrlMsg = new Message();
			webSiteUrlMsg.setMsg(ReviewConstantsIF.Message.INTERNAL_ERROR);

			ebErrors.add(webSiteUrlMsg);
			ajaxRes.setEbErrors(ebErrors);
			return new ModelAndView(ReviewConstantsIF.Views.FAILURE_PAGE,
					ReviewConstantsIF.Keys.OBJ, ajaxRes);
		}
	}

	@Override
	@RequestMapping(value = "/viewAdjectives.do", method = {
			RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse viewAdjectives(HttpServletRequest request) {
		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			List<AdjectiveVO> adjectiveList = articleDelegate
					.retriveAdjectives();
			if (null == adjectiveList) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message();
				msg.setMsg(ReviewConstantsIF.Message.EMPTY_STOPWORDS);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			ajaxResponse.setModel(adjectiveList);
			ajaxResponse.setMessage(ReviewConstantsIF.Message.STOPWORD_SUCESS);
			return ajaxResponse;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			Message msg = new Message();
			msg.setMsg(ReviewConstantsIF.Message.INTERNAL_ERROR);
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;
		}
	}

	@Override
	@RequestMapping(value = "/genStructure1.do", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView generateStructure1() {
		AJAXResponse ajaxRes = null;
		try {
			ajaxRes = new AJAXResponse();
			StatusInfo statusInfo = articleDelegate.genStructure1();
			if (!statusInfo.isStatus()) {
				ajaxRes = new AJAXResponse();
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message webSiteUrlMsg = new Message();
				webSiteUrlMsg
						.setMsg(ReviewConstantsIF.Message.STRUCTURE1_GENERATION_FAILED);
				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return new ModelAndView(ReviewConstantsIF.Views.FAILURE_PAGE,
						ReviewConstantsIF.Keys.OBJ, ajaxRes);
			} else {
				ajaxRes = new AJAXResponse();
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message webSiteUrlMsg = new Message();
				webSiteUrlMsg
						.setMsg(ReviewConstantsIF.Message.STRUCTURE1_GEN_SUCESS);

				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return new ModelAndView(
						ReviewConstantsIF.Views.VIEW_ADMIN_SUCESS_PAGE,
						ReviewConstantsIF.Keys.OBJ, ajaxRes);
			}

		} catch (Exception e) {
			ajaxRes = new AJAXResponse();
			List<Message> ebErrors = new ArrayList<Message>();
			ajaxRes.setStatus(false);
			Message webSiteUrlMsg = new Message();
			webSiteUrlMsg.setMsg(ReviewConstantsIF.Message.INTERNAL_ERROR);

			ebErrors.add(webSiteUrlMsg);
			ajaxRes.setEbErrors(ebErrors);
			return new ModelAndView(ReviewConstantsIF.Views.FAILURE_PAGE,
					ReviewConstantsIF.Keys.OBJ, ajaxRes);
		}
	}

	@Override
	@RequestMapping(value = "/viewStructure1.do", method = {
			RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse viewStrcuture1(HttpServletRequest request) {
		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			List<StructureVO> strutureVOList = articleDelegate
					.retriveStructure1List();
			if (null == strutureVOList) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message();
				msg.setMsg(ReviewConstantsIF.Message.EMPTY_STRUCTURE1);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			ajaxResponse.setModel(strutureVOList);
			ajaxResponse
					.setMessage(ReviewConstantsIF.Message.STRUCTURE1_RETRIVAL_SUCESS);
			return ajaxResponse;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			Message msg = new Message();
			msg.setMsg(ReviewConstantsIF.Message.INTERNAL_ERROR);
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;
		}
	}

	@Override
	@RequestMapping(value = "/viewStructure2.do", method = {
			RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse viewStrcuture2(HttpServletRequest request) {
		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			List<StructureVO> strutureVOList = articleDelegate
					.retriveStructure2List();
			if (null == strutureVOList) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message();
				msg.setMsg(ReviewConstantsIF.Message.EMPTY_STRUCTURE2);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			ajaxResponse.setModel(strutureVOList);
			ajaxResponse
					.setMessage(ReviewConstantsIF.Message.STRUCTURE2_RETRIVAL_SUCESS);
			return ajaxResponse;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			Message msg = new Message();
			msg.setMsg(ReviewConstantsIF.Message.INTERNAL_ERROR);
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;
		}
	}

	@Override
	@RequestMapping(value = "/viewStructure3.do", method = {
			RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse viewStrcuture3(HttpServletRequest request) {
		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			List<StructureVO> strutureVOList = articleDelegate
					.retriveStructure3List();
			if (null == strutureVOList) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message();
				msg.setMsg(ReviewConstantsIF.Message.EMPTY_STRUCTURE1);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			ajaxResponse.setModel(strutureVOList);
			ajaxResponse
					.setMessage(ReviewConstantsIF.Message.STRUCTURE1_RETRIVAL_SUCESS);
			return ajaxResponse;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			Message msg = new Message();
			msg.setMsg(ReviewConstantsIF.Message.INTERNAL_ERROR);
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;
		}
	}

	@Override
	@RequestMapping(value = "/genStructure2.do", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView generateStructure2() {
		AJAXResponse ajaxRes = null;
		try {
			ajaxRes = new AJAXResponse();
			StatusInfo statusInfo = articleDelegate.genStructure2();
			if (!statusInfo.isStatus()) {
				ajaxRes = new AJAXResponse();
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message webSiteUrlMsg = new Message();
				webSiteUrlMsg
						.setMsg(ReviewConstantsIF.Message.STRUCTURE2_GENERATION_FAILED);
				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return new ModelAndView(ReviewConstantsIF.Views.FAILURE_PAGE,
						ReviewConstantsIF.Keys.OBJ, ajaxRes);
			} else {
				ajaxRes = new AJAXResponse();
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message webSiteUrlMsg = new Message();
				webSiteUrlMsg
						.setMsg(ReviewConstantsIF.Message.STRUCTURE2_GEN_SUCESS);

				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return new ModelAndView(
						ReviewConstantsIF.Views.VIEW_ADMIN_SUCESS_PAGE,
						ReviewConstantsIF.Keys.OBJ, ajaxRes);
			}

		} catch (Exception e) {
			ajaxRes = new AJAXResponse();
			List<Message> ebErrors = new ArrayList<Message>();
			ajaxRes.setStatus(false);
			Message webSiteUrlMsg = new Message();
			webSiteUrlMsg.setMsg(ReviewConstantsIF.Message.INTERNAL_ERROR);

			ebErrors.add(webSiteUrlMsg);
			ajaxRes.setEbErrors(ebErrors);
			return new ModelAndView(ReviewConstantsIF.Views.FAILURE_PAGE,
					ReviewConstantsIF.Keys.OBJ, ajaxRes);
		}
	}

	@Override
	@RequestMapping(value = "/genStructure3.do", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView generateStructure3() {
		AJAXResponse ajaxRes = null;
		try {
			ajaxRes = new AJAXResponse();
			StatusInfo statusInfo = articleDelegate.genStructure3();
			if (!statusInfo.isStatus()) {
				ajaxRes = new AJAXResponse();
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message webSiteUrlMsg = new Message();
				webSiteUrlMsg
						.setMsg(ReviewConstantsIF.Message.STRUCTURE3_GENERATION_FAILED);
				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return new ModelAndView(ReviewConstantsIF.Views.FAILURE_PAGE,
						ReviewConstantsIF.Keys.OBJ, ajaxRes);
			} else {
				ajaxRes = new AJAXResponse();
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message webSiteUrlMsg = new Message();
				webSiteUrlMsg
						.setMsg(ReviewConstantsIF.Message.STRUCTURE3_GEN_SUCESS);

				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return new ModelAndView(
						ReviewConstantsIF.Views.VIEW_ADMIN_SUCESS_PAGE,
						ReviewConstantsIF.Keys.OBJ, ajaxRes);
			}

		} catch (Exception e) {
			ajaxRes = new AJAXResponse();
			List<Message> ebErrors = new ArrayList<Message>();
			ajaxRes.setStatus(false);
			Message webSiteUrlMsg = new Message();
			webSiteUrlMsg.setMsg(ReviewConstantsIF.Message.INTERNAL_ERROR);

			ebErrors.add(webSiteUrlMsg);
			ajaxRes.setEbErrors(ebErrors);
			return new ModelAndView(ReviewConstantsIF.Views.FAILURE_PAGE,
					ReviewConstantsIF.Keys.OBJ, ajaxRes);
		}
	}

	@Override
	@RequestMapping(value = "/genFreq.do", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView generateFreq() {
		AJAXResponse ajaxRes = null;
		try {
			ajaxRes = new AJAXResponse();
			StatusInfo statusInfo = articleDelegate.generateFreq();
			if (!statusInfo.isStatus()) {
				ajaxRes = new AJAXResponse();
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message webSiteUrlMsg = new Message();
				webSiteUrlMsg.setMsg(statusInfo.getErrMsg());
				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return new ModelAndView(ReviewConstantsIF.Views.FAILURE_PAGE,
						ReviewConstantsIF.Keys.OBJ, ajaxRes);
			} else {
				ajaxRes = new AJAXResponse();
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message webSiteUrlMsg = new Message();
				webSiteUrlMsg.setMsg(ReviewConstantsIF.Message.FREQ_GEN_SUCESS);

				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return new ModelAndView(
						ReviewConstantsIF.Views.VIEW_ADMIN_SUCESS_PAGE,
						ReviewConstantsIF.Keys.OBJ, ajaxRes);
			}

		} catch (Exception e) {
			ajaxRes = new AJAXResponse();
			List<Message> ebErrors = new ArrayList<Message>();
			ajaxRes.setStatus(false);
			Message webSiteUrlMsg = new Message();
			webSiteUrlMsg.setMsg(ReviewConstantsIF.Message.INTERNAL_ERROR);

			ebErrors.add(webSiteUrlMsg);
			ajaxRes.setEbErrors(ebErrors);
			return new ModelAndView(ReviewConstantsIF.Views.FAILURE_PAGE,
					ReviewConstantsIF.Keys.OBJ, ajaxRes);
		}
	}

	@Override
	@RequestMapping(value = "/viewFreq.do", method = { RequestMethod.POST,
			RequestMethod.GET })
	public @ResponseBody AJAXResponse viewFreq(HttpServletRequest request) {
		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			List<FreqComputation> strutureVOList = articleDelegate.viewFreq();
			if (null == strutureVOList) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message();
				msg.setMsg(ReviewConstantsIF.Message.EMPTY_STRUCTURE1);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			ajaxResponse.setModel(strutureVOList);
			ajaxResponse
					.setMessage(ReviewConstantsIF.Message.STRUCTURE1_RETRIVAL_SUCESS);
			return ajaxResponse;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			Message msg = new Message();
			msg.setMsg(ReviewConstantsIF.Message.INTERNAL_ERROR);
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;
		}
	}

	@Override
	@RequestMapping(value = "/retriveAllArticleNames.do", method = {
			RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse retriveAllArticleNames(
			HttpServletRequest request) {
		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			List<ArticleNamesVO> articleNames = articleDelegate
					.retriveAllArticleNames();
			if (null == articleNames) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message();
				msg.setMsg(ReviewConstantsIF.Message.EMPTY_ARTICLENAMES);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			ajaxResponse.setModel(articleNames);
			ajaxResponse
					.setMessage(ReviewConstantsIF.Message.ARTICLENAMES_SUCESS);
			return ajaxResponse;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			Message msg = new Message();
			msg.setMsg(ReviewConstantsIF.Message.INTERNAL_ERROR);
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;
		}
	}

	// /typeStore.do

	@Override
	@RequestMapping(value = "/typeStore.do", method = { RequestMethod.POST,
			RequestMethod.GET })
	public @ResponseBody AJAXResponse retriveTypes(HttpServletRequest request) {
		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			List<ArticleTypesVO> articleNames = articleDelegate.retriveTypes();
			if (null == articleNames) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message();
				msg.setMsg(ReviewConstantsIF.Message.EMPTY_ARTICLENAMES);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			ajaxResponse.setModel(articleNames);
			ajaxResponse
					.setMessage(ReviewConstantsIF.Message.ARTICLENAMES_SUCESS);
			return ajaxResponse;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			Message msg = new Message();
			msg.setMsg(ReviewConstantsIF.Message.INTERNAL_ERROR);
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;
		}
	}

	@Override
	@RequestMapping(value = "/compareArticle.do", method = {
			RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse compareArticles(
			HttpServletRequest request,
			@RequestBody ComparisionInputModel comparisionModel) {
		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			if (comparisionModel.getArticleNameLeft().equals(
					comparisionModel.getArticleNameRight())) {

				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message();
				msg.setMsg(ReviewConstantsIF.Message.BOTH_ARTICLENAMES_CANNOT_BE_SAME);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;

			}

			StatusInfo statusInfo = articleDelegate
					.compareArticles(comparisionModel);

			if (!statusInfo.isStatus()) {

				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message();
				msg.setMsg(ReviewConstantsIF.Message.COULD_NOT_MEASURE_SIMILARITY);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;

			}

			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);

			CompareArticleVO compareArticlesVO = (CompareArticleVO) statusInfo
					.getObject();

			ajaxResponse.setModel(compareArticlesVO);
			ajaxResponse
					.setMessage(ReviewConstantsIF.Message.COMPARINGARTICLE_SUCESS);
			return ajaxResponse;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			Message msg = new Message();
			msg.setMsg(ReviewConstantsIF.Message.INTERNAL_ERROR);
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;
		}
	}

	@Override
	@RequestMapping(value = "/viewNLPAdjectives.do", method = {
			RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse viewNLPAdjectives(
			HttpServletRequest request) {
		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			List<AdjectiveVO> adjectiveList = articleDelegate
					.retriveNLPAdjectives();
			if (null == adjectiveList) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message();
				msg.setMsg(ReviewConstantsIF.Message.EMPTY_STOPWORDS);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			ajaxResponse.setModel(adjectiveList);
			ajaxResponse.setMessage(ReviewConstantsIF.Message.STOPWORD_SUCESS);
			return ajaxResponse;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			Message msg = new Message();
			msg.setMsg(ReviewConstantsIF.Message.INTERNAL_ERROR);
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;
		}
	}
}
