package com.service.impl;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.constants.ReviewConstantsIF;
import com.dao.inter.ArticleSimilarityDAOIF;
import com.model.AdjectiveVO;
import com.model.ArticleNamesVO;
import com.model.ArticleTypesVO;
import com.model.CleanReviewModel;
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
import com.model.TextFreqComputeVO;
import com.model.TokenVO;
import com.model.WebSiteDataVO;
import com.model.WebSiteUrlModel;
import com.service.inter.ArticleSimilarityServiceIF;

public class ArticleSimilarityServiceImpl implements ArticleSimilarityServiceIF {

	@Autowired
	private ArticleSimilarityDAOIF articleDao;

	public void setArticleDao(ArticleSimilarityDAOIF articleDao) {
		this.articleDao = articleDao;
	}

	@Override
	public StatusInfo storeReviews(ReviewModel reviewModel) {

		StatusInfo statusInfo = null;
		try {

			statusInfo = articleDao.insertReview(reviewModel);

		} catch (Exception e) {
			statusInfo = new StatusInfo();
			statusInfo.setStatus(false);
			statusInfo.setExceptionMsg(e.getMessage());
			return statusInfo;
		}
		return statusInfo;
	}

	/*
	 * public static void main(String s[]) { try {
	 * ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
	 * "review.xml"); ReviewServiceIF reviewServiceIF = (ReviewServiceIF) ctx
	 * .getBean("reviewServiceBean");
	 * 
	 * List<PolarityModel> rerviewModel=reviewServiceIF.retrivePolarity(1);
	 * 
	 * for(PolarityModel rerviewModelTemp:rerviewModel) {
	 * System.out.println("VALUE"+rerviewModelTemp.getNegativeRating()); }
	 * 
	 * 
	 * } catch (Exception e) { e.printStackTrace();
	 * System.out.println("EXCEPTION--->" + e); } }
	 */

	@Override
	public List<ReviewDetailModel> obtainAllReviews() {

		List<ReviewDetailModel> reviewModelList = null;
		try {

			List<ReviewModel> reviewModelListTemp = articleDao
					.retriveAllReviews();
			if (null == reviewModelListTemp || reviewModelListTemp.isEmpty()
					|| reviewModelListTemp.size() == 0) {

				return null;

			}
			reviewModelList = new ArrayList<ReviewDetailModel>();
			for (ReviewModel reviewModelTemp : reviewModelListTemp) {

				ReviewDetailModel reviewDetailModel = new ReviewDetailModel();

				reviewDetailModel.setArticleDesc(reviewModelTemp
						.getArticleDesc());
				reviewDetailModel.setArticleName(reviewModelTemp
						.getArticleName());

				reviewModelList.add(reviewDetailModel);

			}

			return reviewModelList;

		} catch (Exception e) {
			System.out.println("Exception e" + e.getMessage());

		}
		return reviewModelList;

	}

	private WebSiteDataVO readWebSiteData(String webUrl) {

		WebSiteDataVO webSiteVO = null;

		try {
			String webSiteData = getUrlDataAsString(webUrl);
			if (!doNullCheck(webSiteData)) {
				webSiteVO = new WebSiteDataVO();
				webSiteVO.setWebSiteData(webSiteData);
				webSiteVO.setWebUrl(webUrl);
			}
		} catch (Exception e) {
			return null;
		}
		return webSiteVO;

	}

	private boolean doNullCheck(String obj) {
		return (null == obj || obj.length() == 0 || obj.isEmpty()) ? true
				: false;
	}

	private static final String SPACE = " ";

	private String getDataFromHtml(String locationOfHtml) {
		FileReader reader;

		String dataFromHtml = null;

		try {
			reader = new FileReader(locationOfHtml);
			dataFromHtml = extractText(reader);
		} catch (FileNotFoundException e) {
			System.out.println("File is Not Found:" + e);
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IO Exception" + e);
			e.printStackTrace();
		}

		return dataFromHtml;
	}

	private List<String> getHtmlElementsId(String locationOfHtml) {

		return null;

	}

	private String extractText(Reader reader) throws IOException {
		StringBuilder sb = new StringBuilder();
		BufferedReader br = new BufferedReader(reader);
		String line;
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}
		String textOnly = Jsoup.parse(sb.toString()).text();
		return textOnly;
	}

	private List<String> getUrls(String urlToConnect) {

		List<String> urls = null;

		if (urlToConnect != null) {
			urls = new ArrayList<String>();

			urls = extractLinks(urlToConnect);
		}
		return urls;
	}

	private List<String> extractLinks(String url) {
		final ArrayList<String> result = new ArrayList<String>();

		try {

			Document doc;
			doc = Jsoup.connect(url).get();
			// doc = Jsoup.parse(url);
			Elements links = doc.select("a");

			System.out.println("LINKS-->" + doc);

			// Elements imports = doc.select("link[href]");

			System.out.println("LINKS INFO--->" + links);

			// href ...
			for (Element link : links) {

				String linkHref = links.attr("href"); // "http://example.com/"
				String linkText = link.text();

				System.out.println("linkText" + linkText);

				result.add(linkText);
			}

			/*
			 * // js, css, ... for (Element link : imports) {
			 * result.add(link.attr("abs:href")); }
			 */

		} catch (Exception e) {
			System.out.println("EXCEPTION--->" + e);
		}
		return result;
	}

	public static String getTextDivData(String url) {

		StringBuilder stringBuilder = new StringBuilder();
		try {
			Document doc;
			doc = Jsoup.connect(url).get();
			Elements links = doc.select("div");
			for (Element link : links) {

				String linkText = link.text();
				stringBuilder.append(linkText);
				stringBuilder.append(SPACE);
			}
		} catch (Exception e) {
			System.out.println("EXCEPTION--->" + e);
		}
		return stringBuilder.toString();

	}

	public static String getTextTagData(String url) {
		final ArrayList<String> result = new ArrayList<String>();

		StringBuilder stringBuilder = new StringBuilder();
		try {
			Document doc;
			doc = Jsoup.connect(url).get();
			Elements links = doc.select("text");
			for (Element link : links) {

				String linkText = link.text();
				stringBuilder.append(linkText);
				stringBuilder.append(SPACE);
			}
		} catch (Exception e) {
			System.out.println("EXCEPTION--->" + e);
		}
		return stringBuilder.toString();

	}

	public static String getUrlDataAsString(String url) {
		StringBuilder stringBuilder = new StringBuilder();

		try {

			Document doc;
			doc = Jsoup.connect(url).get();
			// doc = Jsoup.parse(url);
			Elements links = doc.select("a");

			System.out.println("LINKS-->" + doc);

			// Elements imports = doc.select("link[href]");
			// href ...
			for (Element link : links) {

				String linkText = link.text();

				stringBuilder.append(SPACE);

				stringBuilder.append(linkText);

				stringBuilder.append(SPACE);

			}

			// Elements
			String divData = getTextDivData(url);

			if (divData != null && !divData.isEmpty()) {
				stringBuilder.append(divData);

				stringBuilder.append(SPACE);
			}

			// TET DATA

			/*
			 * String textData=getTextTagData(url);
			 * 
			 * if(textData!=null && !textData.isEmpty()) {
			 * 
			 * stringBuilder.append(textData);
			 * 
			 * stringBuilder.append(SPACE);
			 * 
			 * }
			 */

		} catch (Exception e) {
			System.out.println("EXCEPTION--->" + e);
		}

		System.out.println("USER" + stringBuilder.toString());

		return stringBuilder.toString();

	}

	public static String getUrlDataAsStringTest(String url) {
		StringBuilder stringBuilder = new StringBuilder();

		try {

			Document doc;
			doc = Jsoup.connect(url).get();
			// doc = Jsoup.parse(url);
			Elements links = doc.select("a");

			// Elements imports = doc.select("link[href]");
			// href ...
			for (Element link : links) {

				String linkText = link.text();

				stringBuilder.append(SPACE);

				stringBuilder.append(linkText);

				stringBuilder.append(SPACE);

			}

			// Elements
			String divData = getTextDivData(url);

			System.out.println("DIV =" + divData);

			stringBuilder.append(divData);

			stringBuilder.append(SPACE);

			String textData = getTextTagData(url);

			stringBuilder.append(textData);

			stringBuilder.append(SPACE);

		} catch (Exception e) {
			System.out.println("EXCEPTION--->" + e);
		}

		System.out.println("USER" + stringBuilder.toString());

		return stringBuilder.toString();

	}

	private boolean checkWordPresent(String word, String document) {

		boolean flag = false;
		String completeData = getDataFromHtml(document);

		String[] dataChunks = completeData.split(" ");

		List<String> wordList = new ArrayList<String>();

		for (String wordTemp : dataChunks) {
			wordList.add(wordTemp);
		}

		if (wordList.contains(word)) {
			flag = true;
			return flag;
		}

		return flag;
	}

	@Override
	public boolean storeWebSiteData(WebSiteUrlModel webSiteModel) {
		boolean webDataStorage = false;
		try

		{
			WebSiteDataVO webSiteDataVO = null;

			/* Reading the Web Site Data from Url1 */
			webSiteDataVO = readWebSiteData(webSiteModel.getWebUrl());
			if (null == webSiteDataVO) {
				return false;
			}

			ReviewModel reviewModel = new ReviewModel();

			if (webSiteDataVO.getWebSiteData() != null
					&& !webSiteDataVO.getWebSiteData().isEmpty()) {

				int webDataVONewLen = webSiteDataVO.getWebSiteData().length();

				String webSiteData = webSiteDataVO.getWebSiteData();
				if (webDataVONewLen > 2000) {

					webSiteData = webSiteData.substring(1, 2000);

				}

				reviewModel.setArticleDesc(webSiteData);
				reviewModel.setArticleName(webSiteModel.getArticleName());

				StatusInfo webDataStorage1 = articleDao
						.insertReview(reviewModel);

				if (!webDataStorage1.isStatus()) {
					return false;
				} else {
					return true;
				}
			} else {
				return false;
			}

		} catch (Exception e) {
			System.out.println("Exception" + e);
			return false;
		}

	}

	@Override
	public StatusInfo addStopword(String stopWord) {
		StatusInfo statusInfo = null;
		try {
			statusInfo = new StatusInfo();

			List<String> keyWordList = articleDao.retriveStopWordsOnly();

			if (null == keyWordList || keyWordList.isEmpty()) {
				statusInfo = articleDao.insertStopWord(stopWord);
				statusInfo.setStatus(true);
				return statusInfo;
			}

			if (keyWordList.contains(stopWord)) {
				statusInfo.setErrMsg(ReviewConstantsIF.Message.STOPWORD_EXIST);
				statusInfo.setStatus(false);
				return statusInfo;
			} else {
				statusInfo = articleDao.insertStopWord(stopWord);
				statusInfo.setStatus(true);
				return statusInfo;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			statusInfo = new StatusInfo();
			statusInfo.setErrMsg(e.getMessage());
			statusInfo.setStatus(false);
			return statusInfo;
		}
	}

	@Override
	public List<StopWordsVO> retriveStopWords() {
		List<StopWordsVO> stopWordList = null;
		try {
			stopWordList = articleDao.retriveStopWords();
			if (null == stopWordList) {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			return null;
		}
		return stopWordList;
	}

	@Override
	public StatusInfo doDataCleaning() {
		StatusInfo statusInfo = null;
		try {

			statusInfo = new StatusInfo();

			// Remove All Clean Reviews
			statusInfo = articleDao.deleteAllCleanReviews();
			if (!statusInfo.isStatus()) {
				return statusInfo;
			}

			List<ReviewDetailModel> reviewList = obtainAllReviews();

			List<String> stopWordsList = articleDao.retriveStopWordsOnly();

			for (ReviewDetailModel reviewModelObj : reviewList) {

				String reviewDetails = reviewModelObj.getArticleDesc();
				String str = reviewDetails.replaceAll("\\s", " ");

				String webSiteDataCleanTemp = str.replaceAll(
						ReviewConstantsIF.Keys.STOPWORDS_SYMBOL,
						ReviewConstantsIF.Keys.SPACE);

				StringTokenizer tok1 = new StringTokenizer(webSiteDataCleanTemp);
				StringBuilder buffer = new StringBuilder();
				String str1 = null;
				while (tok1.hasMoreTokens()) {
					str1 = (String) tok1.nextElement();
					str1 = str1.toLowerCase();

					if (null == str1 || str1.isEmpty() || str1.length() <= 0
							|| str1.trim().length() == 0) {
						continue;
					}
					if (str1 != null) {
						str1 = str1.trim();
					}
					if (stopWordsList.contains(str1)) {
						continue;
					} else {
						str1 = str1.replaceAll(
								ReviewConstantsIF.Keys.STOPWORDS_SYMBOL,
								ReviewConstantsIF.Keys.SPACE);
						str1 = str1.trim();
						if (str1.length() > 0) {
							buffer.append(ReviewConstantsIF.Keys.SPACE);
							buffer.append(str1);
						}
						System.out.println("BUFFER" + buffer);
					}
				}

				// Now Create an Object of Clean Review

				CleanReviewModel cleanReview = new CleanReviewModel();
				cleanReview.setArticleName(reviewModelObj.getArticleName());
				// Blob cleanData = covertFromStringToBlob(buffer.toString());
				cleanReview.setCleanArtilce(buffer.toString());

				statusInfo = articleDao.insertCleanDetails(cleanReview);

				if (!statusInfo.isStatus()) {
					statusInfo.setStatus(false);
					statusInfo
							.setErrMsg(ReviewConstantsIF.Message.CLEANMODEL_FAILED);
					return statusInfo;
				}

			}
			statusInfo.setStatus(true);
			return statusInfo;
			// end of for Loop

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception " + e.getMessage());
			statusInfo = new StatusInfo();
			statusInfo.setStatus(false);
			statusInfo.setErrMsg(e.getMessage());
			return statusInfo;

		}
	}

	private Blob covertFromStringToBlob(String reviewDetails) {

		byte[] byteContent = reviewDetails.getBytes();
		Blob blob = null;
		try {
			blob = new SerialBlob(byteContent);
		} catch (SerialException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return blob;
	}

	@Override
	public List<CleanReviewUIModel> retriveCleanReviewList() {
		List<CleanReviewUIModel> cleanReviewUIList = null;
		try {

			List<CleanReviewModel> cleanReviewModelList = articleDao
					.retriveCleanReviews();

			if (null == cleanReviewModelList) {
				return null;
			}

			cleanReviewUIList = new ArrayList<CleanReviewUIModel>();

			for (CleanReviewModel cleanReviewModelTemp : cleanReviewModelList) {
				CleanReviewUIModel cleanReviewUIModel = new CleanReviewUIModel();
				cleanReviewUIModel
						.setCleanId(cleanReviewModelTemp.getCleanId());
				/*
				 * cleanReviewUIModel
				 * .setCleanArticle(convertFromBlobToString(cleanReviewModelTemp
				 * .getCleanArtilce()));
				 */
				cleanReviewUIModel.setCleanArticle(cleanReviewModelTemp
						.getCleanArtilce());

				cleanReviewUIModel.setArticleName(cleanReviewModelTemp
						.getArticleName());
				cleanReviewUIList.add(cleanReviewUIModel);
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			return null;
		}
		return cleanReviewUIList;
	}

	@Override
	public StatusInfo doTokens() {
		StatusInfo statusInfo = null;
		try {

			statusInfo = new StatusInfo();

			// Remove All Tokens
			statusInfo = articleDao.deleteAllTokens();
			if (!statusInfo.isStatus()) {
				return statusInfo;
			}

			List<CleanReviewUIModel> cleanReviewUIModelList = retriveCleanReviewList();

			if (null == cleanReviewUIModelList
					|| cleanReviewUIModelList.isEmpty()) {
				statusInfo.setStatus(false);
				statusInfo
						.setErrMsg(ReviewConstantsIF.Message.CLEANREVIEWS_EMPTY);
				return statusInfo;
			}

			for (CleanReviewUIModel reviewModelObj : cleanReviewUIModelList) {

				StringTokenizer tok1 = new StringTokenizer(
						reviewModelObj.getCleanArticle(),
						ReviewConstantsIF.Keys.SPACE);
				String tokenName;
				while (tok1.hasMoreTokens()) {
					tokenName = tok1.nextToken();
					tokenName = tokenName.toLowerCase();
					TokenVO tokenVO = new TokenVO();
					tokenVO.setTokenName(tokenName);
					tokenVO.setArticleName(reviewModelObj.getArticleName());
					statusInfo = articleDao.insertToken(tokenVO);
					if (!statusInfo.isStatus()) {
						statusInfo.setStatus(false);
						statusInfo
								.setErrMsg(ReviewConstantsIF.Message.INSERT_TOKENS_FAILED);
						return statusInfo;
					}

				}
				// end of for Loop
			}
			statusInfo.setStatus(true);
			return statusInfo;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception " + e.getMessage());
			statusInfo = new StatusInfo();
			statusInfo.setStatus(false);
			statusInfo.setErrMsg(e.getMessage());
			return statusInfo;

		}
	}

	@Override
	public List<TokenVO> retriveTokenList() {
		List<TokenVO> tokenVOList = null;
		try {
			tokenVOList = articleDao.retriveAllTokens();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception " + e.getMessage());
			return null;
		}
		return tokenVOList;
	}

	@Override
	public StatusInfo removeStopword(String stopWord) {

		StatusInfo statusInfo = null;
		try {
			statusInfo = new StatusInfo();

			List<String> keyWordList = articleDao.retriveStopWordsOnly();

			if (!keyWordList.contains(stopWord)) {
				statusInfo
						.setErrMsg(ReviewConstantsIF.Message.NO_STOPWORD_EXIST);
				statusInfo.setStatus(false);
				return statusInfo;
			} else {
				statusInfo = articleDao.removeStopword(stopWord);
				statusInfo.setStatus(true);
				return statusInfo;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			statusInfo = new StatusInfo();
			statusInfo.setErrMsg(e.getMessage());
			statusInfo.setStatus(false);
			return statusInfo;
		}

	}

	private String convertFromBlobToString(Blob blob) {
		System.out.println("BLOB---" + blob);

		byte[] bdata = null;
		try {
			bdata = blob.getBytes(1, (int) blob.length());

			System.out.println("BDATA" + bdata);

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
		}
		String text = new String(bdata);
		System.out.println("TEXT---" + text);
		return text;
	}

	@Override
	public StatusInfo addKeyPhrase(String keyphrase, String type) {
		StatusInfo statusInfo = null;
		try {
			statusInfo = new StatusInfo();

			List<String> keyWordList = articleDao.retriveKeyPhraseOnly(type);

			if (null == keyWordList || keyWordList.isEmpty()) {
				statusInfo = articleDao.insertKeyPhrase(keyphrase, type);
				statusInfo.setStatus(true);
				return statusInfo;
			}

			if (keyWordList.contains(keyphrase)) {
				statusInfo.setErrMsg(ReviewConstantsIF.Message.KEYPHRASE_EXIST);
				statusInfo.setStatus(false);
				return statusInfo;
			} else {
				statusInfo = articleDao.insertKeyPhrase(keyphrase, type);
				statusInfo.setStatus(true);
				return statusInfo;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			statusInfo = new StatusInfo();
			statusInfo.setErrMsg(e.getMessage());
			statusInfo.setStatus(false);
			return statusInfo;
		}
	}

	@Override
	public List<KeyPhraseModel> retriveKeyPhrases() {
		List<KeyPhraseModel> keyPhraseList = null;
		try {
			keyPhraseList = articleDao.retriveKeyPhrases();
			if (null == keyPhraseList) {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			return null;
		}
		return keyPhraseList;

	}

	@Override
	public StatusInfo removeKeyPhrase(String keyphrase, String type) {

		StatusInfo statusInfo = null;
		try {
			statusInfo = new StatusInfo();

			List<String> keyWordList = articleDao.retriveKeyPhraseOnly(type);

			if (!keyWordList.contains(keyphrase)) {
				statusInfo
						.setErrMsg(ReviewConstantsIF.Message.KEYPHRASE_NOT_EXIST);
				statusInfo.setStatus(false);
				return statusInfo;
			} else {
				statusInfo = articleDao.removeKeyPhrase(keyphrase, type);
				statusInfo.setStatus(true);
				return statusInfo;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			statusInfo = new StatusInfo();
			statusInfo.setErrMsg(e.getMessage());
			statusInfo.setStatus(false);
			return statusInfo;
		}
	}

	@Override
	public StatusInfo addPhraseG(PhraseGModel phraseGModel) {
		StatusInfo statusInfo = null;
		try {
			statusInfo = new StatusInfo();

			List<String> keyWordList = articleDao
					.retrivePhraseGWhereTypeAndNoType(phraseGModel);

			if (null == keyWordList || keyWordList.isEmpty()) {
				statusInfo = articleDao.insertPhraseG(phraseGModel);
				statusInfo.setStatus(true);
				return statusInfo;
			}

			if (keyWordList.contains(phraseGModel.getPhraseG())) {
				statusInfo.setErrMsg(ReviewConstantsIF.Message.PHRASEG_EXIST);
				statusInfo.setStatus(false);
				return statusInfo;
			} else {
				statusInfo = articleDao.insertPhraseG(phraseGModel);
				statusInfo.setStatus(true);
				return statusInfo;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			statusInfo = new StatusInfo();
			statusInfo.setErrMsg(e.getMessage());
			statusInfo.setStatus(false);
			return statusInfo;
		}
	}

	@Override
	public StatusInfo removePhraseG(PhraseGModel phraseGModel) {
		StatusInfo statusInfo = null;
		try {
			statusInfo = new StatusInfo();

			List<String> keyWordList = articleDao
					.retrivePhraseGWhereTypeAndNoType(phraseGModel);

			if (!keyWordList.contains(phraseGModel.getPhraseG())) {
				statusInfo
						.setErrMsg(ReviewConstantsIF.Message.PHRASEG_NOT_EXIST);
				statusInfo.setStatus(false);
				return statusInfo;
			} else {
				statusInfo = articleDao
						.removePhraseGForPhraseGTypeNoType(phraseGModel);
				statusInfo.setStatus(true);
				return statusInfo;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			statusInfo = new StatusInfo();
			statusInfo.setErrMsg(e.getMessage());
			statusInfo.setStatus(false);
			return statusInfo;
		}
	}

	@Override
	public List<PhraseGModel> retrivePhraseG() {
		List<PhraseGModel> phraseGList = null;
		try {
			phraseGList = articleDao.retrivePhraseGPhrases();
			if (null == phraseGList) {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			return null;
		}
		return phraseGList;
	}

	@Override
	public StatusInfo genPhrases() {
		StatusInfo statusInfo = null;
		try {

			statusInfo = new StatusInfo();

			// Remove All Phrases
			statusInfo = articleDao.deleteAllPhrases();
			if (!statusInfo.isStatus()) {
				statusInfo.setStatus(false);
				statusInfo
						.setErrMsg(ReviewConstantsIF.Message.COULD_NOT_REMOVE_PHRASES);
				return statusInfo;
			}

			// Obtain the List of Tokens

			List<String> tokens = articleDao.retriveTokensOnly();

			if (null == tokens || tokens.isEmpty()) {

				statusInfo.setStatus(false);
				statusInfo.setErrMsg(ReviewConstantsIF.Message.TOKENS_EMPTY);
				return statusInfo;
			}

			// Obtain the List of Stopwords

			List<String> stopwords = articleDao.retriveStopWordsOnly();

			if (null == stopwords || stopwords.isEmpty()) {
				statusInfo.setStatus(false);
				statusInfo.setErrMsg(ReviewConstantsIF.Message.STOPWORDS_EMPTY);
				return statusInfo;
			}

			// Obtain the List of Key Phrases

			List<String> keyPhrasesList = articleDao.retriveAllKeyPhraseOnly();

			if (null == keyPhrasesList || keyPhrasesList.isEmpty()) {
				statusInfo.setStatus(false);
				statusInfo
						.setErrMsg(ReviewConstantsIF.Message.KEYPHRASES_EMPTY);
				return statusInfo;
			}

			// Obtain the List of PhraseG

			List<String> phraseGList = articleDao.retriveAllPhraseGOnly();

			if (null == phraseGList || phraseGList.isEmpty()) {
				statusInfo.setStatus(false);
				statusInfo.setErrMsg(ReviewConstantsIF.Message.PHRASEG_EMPTY);
				return statusInfo;
			}

			// List of Phrases

			Set<String> phrases = new HashSet<String>();

			// Obtain the List of Adjectives

			List<String> adjectiveList = articleDao.retriveAllAdjectives();

			for (String token : tokens) {

				if (!stopwords.contains(token) && !phraseGList.contains(token)
						&& !keyPhrasesList.contains(token)) {

					if (adjectiveList != null) {

						if (!adjectiveList.contains(token)) {
							phrases.add(token);
						}

					} else {
						phrases.add(token);
					}
				}

			}

			if (null == phrases || phrases.isEmpty()) {
				statusInfo.setStatus(false);
				statusInfo
						.setErrMsg(ReviewConstantsIF.Message.NO_PHRASES_ARE_FOUND);
				return statusInfo;
			}

			statusInfo = articleDao.insertPhrasesList(phrases);

			if (!statusInfo.isStatus()) {
				statusInfo.setStatus(false);
				statusInfo
						.setErrMsg(ReviewConstantsIF.Message.COULD_NOT_INSERT_PHRASES);
				return statusInfo;
			}

			statusInfo.setStatus(true);
			return statusInfo;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception " + e.getMessage());
			statusInfo = new StatusInfo();
			statusInfo.setStatus(false);
			statusInfo.setErrMsg(e.getMessage());
			return statusInfo;

		}
	}

	@Override
	public List<PhraseVO> retrivePhrasesList() {
		List<PhraseVO> phraseList = null;
		try {
			phraseList = articleDao.retrivePhraseList();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception " + e.getMessage());
			return null;
		}
		return phraseList;
	}

	@Override
	public StatusInfo genAdjectives() {

		StatusInfo statusInfo = new StatusInfo();

		try {
			List<ReviewDetailModel> reviewList = obtainAllReviews();

			List<String> adjectiveList = new ArrayList<String>();

			statusInfo = articleDao.deleteAllAdjectives();

			if (reviewList != null && !reviewList.isEmpty()) {

				for (ReviewDetailModel reviewDetailModel : reviewList) {

					StringTokenizer tok1 = new StringTokenizer(
							reviewDetailModel.getArticleDesc(),
							ReviewConstantsIF.Keys.SPACE);
					String tokenName;
					while (tok1.hasMoreTokens()) {
						tokenName = tok1.nextToken();
						tokenName = tokenName.toLowerCase();

						// Check Token is Adjective using NLP
						List<String> adjectiveTemp = obtainAdjectivesUsingNLP(tokenName);
						if (adjectiveTemp != null && !adjectiveTemp.isEmpty()) {
							adjectiveList.addAll(adjectiveTemp);
						}
					}
				}
			} else {
				statusInfo = new StatusInfo();
				statusInfo.setStatus(false);
				statusInfo
						.setErrMsg(ReviewConstantsIF.Message.REVIEW_LIST_EMPTY);
				return statusInfo;

			}
			if (adjectiveList != null && !adjectiveList.isEmpty()) {

				statusInfo = articleDao.insertAdjectives(adjectiveList);

				if (!statusInfo.isStatus()) {

					statusInfo = new StatusInfo();
					statusInfo.setStatus(false);
					statusInfo
							.setErrMsg(ReviewConstantsIF.Message.INSERTING_ADJECTIVES_FAILED);
					return statusInfo;
				} else {
					statusInfo = new StatusInfo();
					statusInfo.setStatus(true);
					return statusInfo;
				}

			}

		} catch (Exception e) {

			e.printStackTrace();
			System.out.println("Exception " + e.getMessage());
			statusInfo = new StatusInfo();
			statusInfo.setStatus(false);
			statusInfo.setErrMsg(e.getMessage());
			return statusInfo;

		}
		return statusInfo;

	}

	private List<String> obtainAdjectivesUsingNLP(String tokenName) {

		List<String> nlpList = new ArrayList<String>();
		try {

			List<String> adjectiveList = articleDao.retriveAdjectivesForNLP();

			nlpList = new ArrayList<String>();

			String lowerCaseWord = tokenName.toLowerCase();

			if (adjectiveList.contains(lowerCaseWord)) {

				nlpList.add(lowerCaseWord);
			}

		} catch (Exception e) {
			System.out.println("Exception " + e.getMessage());
		}

		return nlpList;

	}

	@Override
	public List<AdjectiveVO> retriveAdjectives() {

		List<AdjectiveVO> adjectiveVOList = null;
		try {
			adjectiveVOList = articleDao.retriveAllFullAdjectives();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception " + e.getMessage());
			return null;
		}
		return adjectiveVOList;

	}

	@Override
	public StatusInfo genStructure1() {
		StatusInfo statusInfo = new StatusInfo();

		try {

			statusInfo = articleDao.deleteAllStructure1();

			if (!statusInfo.isStatus()) {
				statusInfo = new StatusInfo();
				statusInfo.setStatus(false);
				statusInfo
						.setErrMsg(ReviewConstantsIF.Message.DELETE_STRUCTURE3_FAILED);
				return statusInfo;

			}

			List<String> structureList = new ArrayList<String>();

			List<String> keyPhrasesOnly = articleDao.retriveAllKeyPhraseOnly();

			if (null == keyPhrasesOnly || keyPhrasesOnly.isEmpty()) {

				statusInfo = new StatusInfo();
				statusInfo.setStatus(false);
				statusInfo
						.setErrMsg(ReviewConstantsIF.Message.NO_KEYPHRASES_FOUND);
				return statusInfo;
			} else {

				for (String keyPhrase : keyPhrasesOnly) {
					for (String keyPhrase2 : keyPhrasesOnly) {
						StringBuilder sb = new StringBuilder(keyPhrase);
						sb.append(ReviewConstantsIF.CONSTANTS.SPACE);
						sb.append(keyPhrase2);

						structureList.add(sb.toString());
					}
				}

			}

			if (structureList != null && !structureList.isEmpty()) {

				// Structure1 List
				statusInfo = articleDao.insertStruture1(structureList);

				if (!statusInfo.isStatus()) {

					statusInfo = new StatusInfo();
					statusInfo.setStatus(false);
					statusInfo
							.setErrMsg(ReviewConstantsIF.Message.NO_INSERTION_STRUCTURE1);
					return statusInfo;
				}

			} else {

				statusInfo = new StatusInfo();
				statusInfo.setStatus(false);
				statusInfo
						.setErrMsg(ReviewConstantsIF.Message.NO_STRUCTURE1_FOUND);
				return statusInfo;

			}

		} catch (Exception e) {

			e.printStackTrace();
			System.out.println("Exception " + e.getMessage());
			statusInfo = new StatusInfo();
			statusInfo.setStatus(false);
			statusInfo.setErrMsg(e.getMessage());
			return statusInfo;

		}
		return statusInfo;
	}

	@Override
	public List<StructureVO> retriveStructure1List() {
		List<StructureVO> structure1List = null;
		try {
			structure1List = articleDao.retriveStructure1List();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception " + e.getMessage());
			return null;
		}
		return structure1List;
	}

	@Override
	public List<StructureVO> retriveStructure2List() {
		List<StructureVO> structure2List = null;
		try {
			structure2List = articleDao.retriveStructure2List();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception " + e.getMessage());
			return null;
		}
		return structure2List;
	}

	@Override
	public List<StructureVO> retriveStructure3List() {
		List<StructureVO> structure3List = null;
		try {
			structure3List = articleDao.retriveStructure3List();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception " + e.getMessage());
			return null;
		}
		return structure3List;
	}

	@Override
	public StatusInfo genStructure2() {
		StatusInfo statusInfo = new StatusInfo();

		try {

			statusInfo = articleDao.deleteAllStructure2();

			if (!statusInfo.isStatus()) {
				statusInfo = new StatusInfo();
				statusInfo.setStatus(false);
				statusInfo
						.setErrMsg(ReviewConstantsIF.Message.DELETE_STRUCTURE3_FAILED);
				return statusInfo;

			}

			List<String> structureList = new ArrayList<String>();

			List<String> adjectivesOnly = articleDao.retriveAllAdjectives();

			List<String> keyPhrasesOnly = articleDao.retriveAllKeyPhraseOnly();

			if (null == keyPhrasesOnly || keyPhrasesOnly.isEmpty()) {

				statusInfo = new StatusInfo();
				statusInfo.setStatus(false);
				statusInfo
						.setErrMsg(ReviewConstantsIF.Message.NO_KEYPHRASES_FOUND);
				return statusInfo;
			}

			if (null == adjectivesOnly || adjectivesOnly.isEmpty()) {

				statusInfo = new StatusInfo();
				statusInfo.setStatus(false);
				statusInfo
						.setErrMsg(ReviewConstantsIF.Message.NO_ADJECTIVES_FOUND);
				return statusInfo;
			}

			for (String keyPhrase : keyPhrasesOnly) {
				for (String adjectives : adjectivesOnly) {
					StringBuilder sb = new StringBuilder(keyPhrase);
					sb.append(ReviewConstantsIF.CONSTANTS.SPACE);
					sb.append(adjectives);

					structureList.add(sb.toString());
				}
			}

			if (structureList != null && !structureList.isEmpty()) {

				// Structure1 List
				statusInfo = articleDao.insertStruture2(structureList);

				if (!statusInfo.isStatus()) {

					statusInfo = new StatusInfo();
					statusInfo.setStatus(false);
					statusInfo
							.setErrMsg(ReviewConstantsIF.Message.NO_INSERTION_STRUCTURE2);
					return statusInfo;
				}

			} else {

				statusInfo = new StatusInfo();
				statusInfo.setStatus(false);
				statusInfo
						.setErrMsg(ReviewConstantsIF.Message.NO_STRUCTURE2_FOUND);
				return statusInfo;

			}

		} catch (Exception e) {

			e.printStackTrace();
			System.out.println("Exception " + e.getMessage());
			statusInfo = new StatusInfo();
			statusInfo.setStatus(false);
			statusInfo.setErrMsg(e.getMessage());
			return statusInfo;

		}
		return statusInfo;
	}

	@Override
	public StatusInfo genStructure3() {
		StatusInfo statusInfo = new StatusInfo();

		try {

			statusInfo = articleDao.deleteAllStructure3();

			if (!statusInfo.isStatus()) {
				statusInfo = new StatusInfo();
				statusInfo.setStatus(false);
				statusInfo
						.setErrMsg(ReviewConstantsIF.Message.DELETE_STRUCTURE3_FAILED);
				return statusInfo;

			}

			List<String> structureList = new ArrayList<String>();

			List<String> phraseListOnly = articleDao.retrivePhraseListOnly();

			if (null == phraseListOnly || phraseListOnly.isEmpty()) {

				statusInfo = new StatusInfo();
				statusInfo.setStatus(false);
				statusInfo
						.setErrMsg(ReviewConstantsIF.Message.NO_PHRASELIST_FOUND);
				return statusInfo;
			}

			List<String> keyPhrasesOnly = articleDao.retriveAllKeyPhraseOnly();

			if (null == keyPhrasesOnly || keyPhrasesOnly.isEmpty()) {

				statusInfo = new StatusInfo();
				statusInfo.setStatus(false);
				statusInfo
						.setErrMsg(ReviewConstantsIF.Message.NO_KEYPHRASES_FOUND);
				return statusInfo;
			}

			for (String keyPhrase : keyPhrasesOnly) {
				for (String phrase : phraseListOnly) {
					StringBuilder sb = new StringBuilder(keyPhrase);
					sb.append(ReviewConstantsIF.CONSTANTS.SPACE);
					sb.append(phrase);
					structureList.add(sb.toString());
				}
			}

			if (structureList != null && !structureList.isEmpty()) {

				// Structure1 List
				statusInfo = articleDao.insertStruture3(structureList);

				if (!statusInfo.isStatus()) {

					statusInfo = new StatusInfo();
					statusInfo.setStatus(false);
					statusInfo
							.setErrMsg(ReviewConstantsIF.Message.NO_INSERTION_STRUCTURE3);
					return statusInfo;
				}

			} else {

				statusInfo = new StatusInfo();
				statusInfo.setStatus(false);
				statusInfo
						.setErrMsg(ReviewConstantsIF.Message.NO_STRUCTURE3_FOUND);
				return statusInfo;

			}

		} catch (Exception e) {

			e.printStackTrace();
			System.out.println("Exception " + e.getMessage());
			statusInfo = new StatusInfo();
			statusInfo.setStatus(false);
			statusInfo.setErrMsg(e.getMessage());
			return statusInfo;

		}
		return statusInfo;
	}

	@Override
	public StatusInfo generateFreq() {
		StatusInfo statusInfo = new StatusInfo();

		try {

			statusInfo = articleDao.deleteAllFreq();

			if (!statusInfo.isStatus()) {
				statusInfo = new StatusInfo();
				statusInfo.setStatus(false);
				statusInfo
						.setErrMsg(ReviewConstantsIF.Message.DELETE_FREQ_FAILED);
				return statusInfo;

			}

			List<String> types = articleDao.retriveDiffrentConcepts();

			if (null == types || types.isEmpty()) {
				statusInfo = new StatusInfo();
				statusInfo.setStatus(false);
				statusInfo
						.setErrMsg(ReviewConstantsIF.Message.RETRIVE_DIFFRENT_CONCEPTS_NONE);
				return statusInfo;
			}

			List<String> phrases = articleDao.retrivePhraseListOnly();

			if (null == phrases || phrases.isEmpty()) {
				statusInfo = new StatusInfo();
				statusInfo.setStatus(false);
				statusInfo
						.setErrMsg(ReviewConstantsIF.Message.RETRIVE_DIFFRENT_PHRASES_NONE);
				return statusInfo;
			}

			List<String> adjectives = articleDao.retriveAllAdjectives();

			List<String> structure1 = articleDao.retriveAllStructure1Only();

			if (null == structure1 || structure1.isEmpty()) {
				statusInfo = new StatusInfo();
				statusInfo.setStatus(false);
				statusInfo
						.setErrMsg(ReviewConstantsIF.Message.RETRIVE_DIFFRENT_STRUCTURE1_NONE);
				return statusInfo;
			}

			List<String> structure2 = articleDao.retriveAllStructure2Only();

			/*
			 * if (null == structure2 || structure2.isEmpty()) { statusInfo =
			 * new StatusInfo(); statusInfo.setStatus(false); statusInfo
			 * .setErrMsg
			 * (ReviewConstantsIF.Message.RETRIVE_DIFFRENT_STRUCTURE2_NONE);
			 * return statusInfo; }
			 */

			List<String> structure3 = articleDao.retriveAllStructure3Only();

			if (null == structure3 || structure3.isEmpty()) {
				statusInfo = new StatusInfo();
				statusInfo.setStatus(false);
				statusInfo
						.setErrMsg(ReviewConstantsIF.Message.RETRIVE_DIFFRENT_STRUCTURE3_NONE);
				return statusInfo;
			}

			for (String type : types) {

				List<String> keyPhrasesOnly = articleDao
						.retriveAllKeyPhraseOnlyForConcept(type);

				List<String> pharseGList = articleDao
						.retrivePhraseGWhereTYPEISNOT(type);

				/*
				 * if (null == keyPhrasesOnly || keyPhrasesOnly.isEmpty()) {
				 * 
				 * statusInfo = new StatusInfo(); statusInfo.setStatus(false);
				 * statusInfo
				 * .setErrMsg(ReviewConstantsIF.Message.NO_KEYPHRASES_FOUND
				 * +"For Concept = "+type); return statusInfo; }
				 */

				List<ReviewDetailModel> reviewList = obtainAllReviews();

				if (null == reviewList || reviewList.isEmpty()) {

					statusInfo = new StatusInfo();
					statusInfo.setStatus(false);
					statusInfo
							.setErrMsg(ReviewConstantsIF.Message.NO_REVIEWSMODEL_FOUND);
					return statusInfo;
				}

				for (ReviewDetailModel reviewModelObj : reviewList) {

					String reviewDetails = reviewModelObj.getArticleDesc();

					StringTokenizer tok1 = new StringTokenizer(reviewDetails,
							".");

					String str1 = null;

					int sentenceId = 1;

					while (tok1.hasMoreTokens()) {
						str1 = (String) tok1.nextElement();
						str1 = str1.toLowerCase();

						if (null == str1 || str1.isEmpty()
								|| str1.length() <= 0
								|| str1.trim().length() == 0) {
							continue;
						}
						if (str1 != null) {
							str1 = str1.trim();

							int freqKeyPhrase = countNoOfWords(str1,
									keyPhrasesOnly);
							int freqPhraseG = countNoOfWords(str1, pharseGList);
							int freqAdjective = countNoOfWords(str1, adjectives);
							int freqPhrase = countNoOfWords(str1, phrases);

							int freqStructure1 = countNoOfWords(str1,
									structure1);
							int freqStructure2 = countNoOfWords(str1,
									structure2);
							int freqStructure3 = countNoOfWords(str1,
									structure3);

							FreqComputation freqComputation = new FreqComputation();
							freqComputation.setArticleName(reviewModelObj
									.getArticleName());
							freqComputation.setFreqAdjective(freqAdjective);
							freqComputation.setFreqKeyPhrase(freqKeyPhrase);
							freqComputation.setFreqPhrase(freqPhrase);
							freqComputation.setFreqStructure1(freqStructure1);
							freqComputation.setFreqPhraseG(freqPhraseG);
							freqComputation.setFreqStructure1(freqStructure1);
							freqComputation.setFreqStructure2(freqStructure2);
							freqComputation.setFreqStructure3(freqStructure3);
							freqComputation.setSentenceId(sentenceId);
							freqComputation.setType(type);

							// Insert into the Frequency Computation
							statusInfo = articleDao
									.insertFreqComputation(freqComputation);

							if (!statusInfo.isStatus()) {
								statusInfo = new StatusInfo();
								statusInfo.setStatus(false);
								statusInfo
										.setErrMsg(ReviewConstantsIF.Message.INSERT_FREQ_FAILED);
								return statusInfo;
							}

							sentenceId = sentenceId + 1;

						}

					}

				}

			}

			statusInfo = new StatusInfo();
			statusInfo.setStatus(true);

		} catch (Exception e) {

			e.printStackTrace();
			System.out.println("Exception " + e.getMessage());
			statusInfo = new StatusInfo();
			statusInfo.setStatus(false);
			statusInfo.setErrMsg(e.getMessage());
			return statusInfo;

		}
		return statusInfo;
	}

	private int countNoOfWords(String str1, List<String> words) {
		int count = 0;
		if (words != null && !words.isEmpty()) {
			for (String word : words) {
				if (str1.contains(word)) {
					count = count + 1;
				}
			}
		}
		return count;
	}

	@Override
	public List<FreqComputation> viewFreq() {
		List<FreqComputation> freqComputationList = null;
		try {
			freqComputationList = articleDao.viewFreq();
			if (null == freqComputationList) {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			return null;
		}
		return freqComputationList;
	}

	@Override
	public List<ArticleNamesVO> retriveAllArticleNames() {
		List<ArticleNamesVO> articleNamesVOList = null;
		try {
			List<ReviewModel> reviewModelList = articleDao.retriveAllReviews();

			if (null == reviewModelList || reviewModelList.isEmpty()) {
				return null;
			}

			articleNamesVOList = new ArrayList<ArticleNamesVO>();

			for (ReviewModel reviewModel : reviewModelList) {

				ArticleNamesVO articleNamesVO = new ArticleNamesVO();

				articleNamesVO.setArticleName(reviewModel.getArticleName());
				articleNamesVOList.add(articleNamesVO);
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception " + e.getMessage());
			return null;
		}
		return articleNamesVOList;
	}

	@Override
	public List<ArticleTypesVO> retriveTypes() {
		List<ArticleTypesVO> typeVOList = null;
		try {
			List<String> concepts = articleDao.retriveDiffrentConcepts();

			if (null == concepts || concepts.isEmpty()) {
				return null;
			}

			typeVOList = new ArrayList<ArticleTypesVO>();

			for (String concept : concepts) {

				ArticleTypesVO typesVo = new ArticleTypesVO();

				typesVo.setTypeName(concept);

				typeVOList.add(typesVo);
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception " + e.getMessage());
			return null;
		}
		return typeVOList;
	}

	@Override
	public StatusInfo compareArticles(ComparisionInputModel comparisionModel) {

		StatusInfo statusInfo = new StatusInfo();

		List<CompareArticleVO> compareArticleList = null;

		try {

			CompareArticleVO compareArticleVO = new CompareArticleVO();
			List<String> tokenNamesLeft = articleDao
					.retriveDistincetTokensForArticleName(comparisionModel
							.getArticleNameLeft());

			if (null == tokenNamesLeft || tokenNamesLeft.isEmpty()) {
				statusInfo.setStatus(false);
				statusInfo
						.setErrMsg(ReviewConstantsIF.Message.NO_TOKENS_FOR_ARTICLE_LEFT
								+ comparisionModel.getArticleNameLeft());
				return statusInfo;
			}

			List<String> tokenNamesRight = articleDao
					.retriveDistincetTokensForArticleName(comparisionModel
							.getArticleNameRight());

			if (null == tokenNamesRight || tokenNamesRight.isEmpty()) {
				statusInfo.setStatus(false);
				statusInfo
						.setErrMsg(ReviewConstantsIF.Message.NO_TOKENS_FOR_ARTICLE_RIGHT
								+ comparisionModel.getArticleNameRight());
				return statusInfo;
			}

			// Now Find the Key Phrase for Article

			List<String> keyPhrasesForLeftArticle = articleDao
					.retriveKeyPhraseOnlyForTokensAndType(tokenNamesLeft,
							comparisionModel.getTypeCombo());

			compareArticleVO.setLeftArticleKeyPhrases(keyPhrasesForLeftArticle);

			if (null == keyPhrasesForLeftArticle
					|| keyPhrasesForLeftArticle.isEmpty()) {
				statusInfo.setStatus(false);
				statusInfo
						.setErrMsg(ReviewConstantsIF.Message.NO_KEYPHRASES_FOR_LEFT_ARTICLE
								+ comparisionModel.getArticleNameLeft());
				return statusInfo;
			}

			// Now Find the Key Phrase for Article

			List<String> keyPhrasesForRightArticle = articleDao
					.retriveKeyPhraseOnlyForTokensAndType(tokenNamesRight,
							comparisionModel.getTypeCombo());

			compareArticleVO
					.setRightArticleKeyPhrases(keyPhrasesForRightArticle);

			if (null == keyPhrasesForRightArticle
					|| keyPhrasesForRightArticle.isEmpty()) {
				statusInfo.setStatus(false);
				statusInfo
						.setErrMsg(ReviewConstantsIF.Message.NO_KEYPHRASES_FOR_RIGHT_ARTICLE
								+ comparisionModel.getArticleNameRight());
				return statusInfo;
			}

			//

			// Now Find the Intersection between the two sets
			List<String> rightArticleKeyPhrase = new ArrayList<String>();
			rightArticleKeyPhrase.addAll(keyPhrasesForRightArticle);

			rightArticleKeyPhrase.retainAll(keyPhrasesForLeftArticle);

			// Find the Union Between teh Two Articles

			List<String> unionList = new ArrayList<String>();

			unionList.addAll(keyPhrasesForRightArticle);
			unionList.addAll(keyPhrasesForLeftArticle);

			Set<String> keyPhraseUnionSet = new HashSet<String>();

			for (String keyPhraseUnion : unionList) {
				keyPhraseUnionSet.add(keyPhraseUnion);
			}

			// Now Store in the Key Phrase Union List

			List<String> keyPhraseUnion = new ArrayList<String>();

			for (String keyPhrase : keyPhraseUnionSet) {

				keyPhraseUnion.add(keyPhrase);

			}

			// Adding the Key Phrase Union Set

			compareArticleVO.setUnionSet(keyPhraseUnion);

			Set<String> keyPhraseInterSet = new HashSet<String>();

			for (String word : rightArticleKeyPhrase) {

				keyPhraseInterSet.add(word);
			}

			// Now Store in the Key Phrase Intersection List

			List<String> keyPhraseIntersection = new ArrayList<String>();

			for (String keyPhrase : keyPhraseInterSet) {
				keyPhraseIntersection.add(keyPhrase);
			}

			compareArticleVO.setInstersectionSet(keyPhraseIntersection);

			List<String> msgList = new ArrayList<String>();

			if (null == keyPhraseIntersection
					|| keyPhraseIntersection.isEmpty()) {
				msgList.add(ReviewConstantsIF.Message.KEY_PHRASE_INTERSECTION_SET_EMPTY);
			}

			if (null == keyPhraseUnion || keyPhraseUnion.isEmpty()) {
				msgList.add(ReviewConstantsIF.Message.KEY_PHRASE_UNION_SET_EMPTY);
			}

			//

			List<TextFreqComputeVO> leftArticleTextFreq = new ArrayList<TextFreqComputeVO>();

			List<TextFreqComputeVO> rightArticleTextFreq = new ArrayList<TextFreqComputeVO>();

			double unionSum = 0;

			for (String word : keyPhraseUnion) {

				TextFreqComputeVO textComputeVO = new TextFreqComputeVO();

				int countLeft = articleDao.retriveCountForTokenAndArticleName(
						word, comparisionModel.getArticleNameLeft());

				int denomLeftArticle = articleDao
						.findNoOfTokensForArticleName(comparisionModel
								.getArticleNameLeft());

				textComputeVO.setKeyPhraseName(word);
				textComputeVO.setFreq(countLeft);
				textComputeVO.setCount(denomLeftArticle);

				double textFreqLeft = (double) ((double) countLeft / (double) denomLeftArticle);
				textComputeVO.setTextFreq(textFreqLeft);

				leftArticleTextFreq.add(textComputeVO);

				TextFreqComputeVO textComputeVO1 = new TextFreqComputeVO();

				textComputeVO1.setKeyPhraseName(word);

				int countRight = articleDao.retriveCountForTokenAndArticleName(
						word, comparisionModel.getArticleNameRight());
				textComputeVO1.setFreq(countRight);

				int denomRightArticle = articleDao
						.findNoOfTokensForArticleName(comparisionModel
								.getArticleNameRight());

				textComputeVO1.setCount(denomRightArticle);

				double textFreqRight = (double) ((double) countRight / (double) denomRightArticle);
				textComputeVO1.setTextFreq(textFreqRight);

				rightArticleTextFreq.add(textComputeVO1);

				if (textFreqLeft >= textFreqRight) {

					unionSum = unionSum + textFreqLeft;

				} else {
					unionSum = unionSum + textFreqRight;
				}

			}

			compareArticleVO.setUnionSum(unionSum);

			// Find the Intersection Sum
			double intersectionSum = 0;

			for (String word : keyPhraseIntersection) {

				TextFreqComputeVO textComputeVO = new TextFreqComputeVO();

				int countLeft = articleDao.retriveCountForTokenAndArticleName(
						word, comparisionModel.getArticleNameLeft());

				int denomLeftArticle = articleDao
						.findNoOfTokensForArticleName(comparisionModel
								.getArticleNameLeft());

				textComputeVO.setKeyPhraseName(word);
				textComputeVO.setFreq(countLeft);
				textComputeVO.setCount(denomLeftArticle);

				double textFreqLeft = (double) ((double) countLeft / (double) denomLeftArticle);
				textComputeVO.setTextFreq(textFreqLeft);

				leftArticleTextFreq.add(textComputeVO);

				TextFreqComputeVO textComputeVO1 = new TextFreqComputeVO();

				textComputeVO1.setKeyPhraseName(word);

				int countRight = articleDao.retriveCountForTokenAndArticleName(
						word, comparisionModel.getArticleNameRight());
				textComputeVO1.setFreq(countRight);

				int denomRightArticle = articleDao
						.findNoOfTokensForArticleName(comparisionModel
								.getArticleNameRight());

				textComputeVO1.setCount(denomRightArticle);

				double textFreqRight = (double) ((double) countRight / (double) denomRightArticle);
				textComputeVO1.setTextFreq(textFreqRight);

				rightArticleTextFreq.add(textComputeVO1);

				if (textFreqLeft <= textFreqRight) {

					intersectionSum = intersectionSum + textFreqLeft;

				} else {
					intersectionSum = intersectionSum + textFreqRight;
				}

			}
			compareArticleVO.setIntersectionSum(intersectionSum);

			compareArticleVO.setTextFreqRArticle(rightArticleTextFreq);

			compareArticleVO.setTextFreqLeftArticle(leftArticleTextFreq);

			double similarityMeasure = (double) ((double) intersectionSum / (double) unionSum);

			compareArticleVO.setSimilarityMeasure(similarityMeasure);

			if (similarityMeasure >= 0.5) {
				compareArticleVO.setSimilarityStatus(true);
				compareArticleVO
						.setSimilarityMeasureMsg(ReviewConstantsIF.Message.BOTH_ARTICLENAMES_ARE_SIMILAR);
			} else {
				compareArticleVO.setSimilarityStatus(false);
				compareArticleVO
						.setSimilarityMeasureMsg(ReviewConstantsIF.Message.BOTH_ARTICLENAMES_ARE_NOTSIMILAR);
			}

			// Now getting All the Content Information

			List<FreqComputation> leftArticleFreqInfo = articleDao
					.retriveFreqComputationForArticleNameAndType(
							comparisionModel.getArticleNameLeft(),
							comparisionModel.getTypeCombo());

			compareArticleVO.setLeftArticleFreqInfo(leftArticleFreqInfo);

			List<FreqComputation> rightArticleFreqInfo = articleDao
					.retriveFreqComputationForArticleNameAndType(
							comparisionModel.getArticleNameRight(),
							comparisionModel.getTypeCombo());

			compareArticleVO.setRightArticleFreqInfo(rightArticleFreqInfo);

			statusInfo.setObject(compareArticleVO);

			statusInfo.setStatus(true);

		} catch (Exception e) {
			e.printStackTrace();
			statusInfo = new StatusInfo();
			statusInfo.setStatus(false);
			statusInfo.setErrMsg(e.getMessage());
			return statusInfo;
		}
		return statusInfo;

	}

	public void storeAdjectives(String fileLocation) {

		boolean intial = false;
		BufferedReader reader1;
		try {
			reader1 = new BufferedReader(new InputStreamReader(
					new FileInputStream(fileLocation)));
			String line1;
			line1 = reader1.readLine();
			while (line1 != null) {
				System.out.println("Line1" + line1);
				StringTokenizer tok1 = new StringTokenizer(line1, " ");
				String str1;

				while (tok1.hasMoreTokens()) {
					str1 = tok1.nextToken();
					articleDao.insertAdjectiveNLP(str1);
				}
				line1 = reader1.readLine();

				reader1.close();
			}
			intial = false;
		} catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public static void main(String s[]) {

		try {
			ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
					"review.xml");
			ArticleSimilarityServiceIF reviewServiceIF = (ArticleSimilarityServiceIF) ctx
					.getBean("reviewServiceBean");

			reviewServiceIF
					.storeAdjectives("F:\\ARTICLE_SIMILARITY\\CODE\\ArticleSimilarPro\\src\\adjectives.txt");

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION--->" + e);
		}

	}

	@Override
	public List<AdjectiveVO> retriveNLPAdjectives() {
		List<AdjectiveVO> adjectiveVOList = null;
		try {
			adjectiveVOList = articleDao.retriveAllFullNLPAdjectives();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception " + e.getMessage());
			return null;
		}
		return adjectiveVOList;
	}

}
