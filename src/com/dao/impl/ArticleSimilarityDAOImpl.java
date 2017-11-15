package com.dao.impl;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.constants.ReviewConstantsIF;
import com.dao.inter.ArticleSimilarityDAOIF;
import com.model.AdjectiveVO;
import com.model.CleanReviewModel;
import com.model.FreqComputation;
import com.model.KeyPhraseModel;
import com.model.PhraseGModel;
import com.model.PhraseVO;
import com.model.ReviewModel;
import com.model.StatusInfo;
import com.model.StopWordsVO;
import com.model.StructureVO;
import com.model.TokenVO;

public class ArticleSimilarityDAOImpl implements ArticleSimilarityDAOIF {

	protected SimpleJdbcTemplate template;
	protected NamedParameterJdbcTemplate namedJdbcTemplate;
	private DataSource dataSource;
	@Autowired
	protected MessageSource sqlProperties;
	protected JdbcTemplate jdbcTemplate;

	/**
	 * 
	 */
	public void init() {
		template = new SimpleJdbcTemplate(dataSource);
		namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	protected boolean update(String sqlKey, Map<String, Object> map) {
		System.out.println("Class-->RoutingDaoImpl:Method-->update");
		String sql = sqlProperties.getMessage(sqlKey, null, null);
		System.out.println("SQL" + sql);
		boolean value = false;
		try {
			namedJdbcTemplate.update(sql, map);
			value = true;
		} catch (Exception e) {
			System.out.println("Exception" + e.getMessage());
		}
		return value;
	}

	/**
	 * @param sqlKey
	 * @param map
	 * @return true if sucessfully updated
	 */
	protected boolean insert(String sqlKey, Map<String, Object> map) {
		System.out.println("Class-->RoutingDaoImpl:Method-->update");
		String sql = sqlProperties.getMessage(sqlKey, null, null);
		System.out.println("SQL" + sql);
		boolean value = false;
		try {
			namedJdbcTemplate.update(sql, map);
			value = true;
		} catch (Exception e) {
			System.out.println("Exception" + e.getMessage());
		}
		return value;
	}

	/**
	 * @param sqlQuery
	 * @param sqlKey
	 * @param map
	 * @return true if sucessfully updated
	 */
	protected boolean insertBasedOnQuery(String sqlQuery,
			Map<String, Object> map) {
		System.out.println("Class-->RoutingDaoImpl:Method-->update");
		boolean insertQuery = false;
		try {
			namedJdbcTemplate.update(sqlQuery, map);
			insertQuery = true;
		} catch (Exception e) {
			System.out.println("Exception" + e.getMessage());
		}
		return insertQuery;
	}

	/**
	 * @param <T>
	 * @param sqlKey
	 * @param paramMap
	 * @param rowMapper
	 * @return Object
	 */
	protected <T> T executeForObject(String sqlKey,
			Map<String, ? extends Object> paramMap, RowMapper<T> rowMapper) {
		String sql = sqlProperties.getMessage(sqlKey, null, null);
		return namedJdbcTemplate.queryForObject(sql, paramMap, rowMapper);
	}

	protected <T> T executeForObjectUsingQuery(String sql,
			Map<String, ? extends Object> paramMap, RowMapper<T> rowMapper) {
		return namedJdbcTemplate.queryForObject(sql, paramMap, rowMapper);
	}

	/**
	 * @param sqlKey
	 * @param params
	 * @param whereClause
	 * @return int once the statement gets executed
	 */
	protected int executeForInt(String sqlKey, Map<String, String> params,
			String whereClause) {
		String sql = sqlProperties.getMessage(sqlKey, null, null);
		sql = sql.concat(whereClause);
		System.out.println(sql);

		return namedJdbcTemplate.queryForInt(sql, params);
	}

	/**
	 * @param sqlKey
	 * @param params
	 * @return List of String objects
	 */
	protected List<String> executeForListOfString(String sqlKey,
			Map<String, Object> params) {
		String sql = sqlProperties.getMessage(sqlKey, null, null);
		System.out.println(sql);
		System.out.println(params);

		return namedJdbcTemplate.queryForList(sql, params, String.class);
	}

	/**
	 * @param sqlKey
	 * @param params
	 * @return List of integer values
	 */
	protected List<Integer> executeForListOfInt(String sqlKey,
			Map<String, Object> params) {
		String sql = sqlProperties.getMessage(sqlKey, null, null);
		System.out.println(sql);
		System.out.println(params);

		return namedJdbcTemplate.queryForList(sql, params, Integer.class);
	}

	/**
	 * @return template
	 */
	public SimpleJdbcTemplate getTemplate() {
		return template;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	/**
	 * @param dataSource
	 */
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * @return Named Parameter JDBC Template
	 */
	public NamedParameterJdbcTemplate getNamedJdbcTemplate() {
		return namedJdbcTemplate;
	}

	/**
	 * @return the SQL properties
	 */
	public MessageSource getSqlProperties() {
		return sqlProperties;
	}

	/**
	 * @param sqlProperties
	 */
	public void setSqlProperties(MessageSource sqlProperties) {
		this.sqlProperties = sqlProperties;
	}

	@Override
	public StatusInfo insertReview(ReviewModel reviewModel) {

		StatusInfo statusInfo = new StatusInfo();
		try {
			String sqlKey = ReviewConstantsIF.SQLS.INSERT_ARTICLE_SQL;
			String sql = sqlProperties.getMessage(sqlKey, null, null);
			jdbcTemplate.update(
					sql,
					new Object[] { reviewModel.getArticleName(),
							reviewModel.getArticleDesc() }, new int[] {
							Types.VARCHAR, Types.VARCHAR });
			statusInfo.setStatus(true);

		} catch (Exception e) {
			System.out.println("Exception is:");
			System.out.println(e.getMessage());
			e.printStackTrace();
			statusInfo.setErrMsg(ReviewConstantsIF.Message.MESSAGE_INTERNAL);
			statusInfo.setExceptionMsg(e.getMessage());
			statusInfo.setStatus(false);
			return statusInfo;

		}
		return statusInfo;
	}

	@Override
	public List<ReviewModel> retriveAllReviews() {

		try {

			String sql = sqlProperties.getMessage(
					ReviewConstantsIF.SQLS.RETRIVE_REVIEWS_SQL, null, null);
			return jdbcTemplate.query(sql, new ReviewModelVOMapper());
		} catch (Exception e) {
			System.out.println("Exception" + e);
			return null;
		}
	}

	private final class ReviewModelVOMapper implements RowMapper<ReviewModel> {

		public ReviewModel mapRow(ResultSet rs, int arg1) throws SQLException {

			ReviewModel reviewModel = new ReviewModel();

			reviewModel.setArticleDesc(rs.getString("ARTICLEDESC"));
			reviewModel.setArticleName(rs.getString("ARTICLENAME"));

			return reviewModel;

		}

	}

	@Override
	public StatusInfo insertStopWord(String stopWord) {
		StatusInfo insertStopWordStatus = null;
		try {
			insertStopWordStatus = new StatusInfo();
			String sql = sqlProperties.getMessage(
					ReviewConstantsIF.SQLS.INSERT_STOPWORD_SQL, null, null);
			System.out.println("SQL----" + sql);

			jdbcTemplate.update(sql, new Object[] { stopWord },
					new int[] { Types.VARCHAR });
			insertStopWordStatus.setStatus(true);
			return insertStopWordStatus;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			insertStopWordStatus = new StatusInfo();
			insertStopWordStatus.setErrMsg(e.getMessage());
			insertStopWordStatus.setStatus(false);
			return insertStopWordStatus;

		}
	}

	@Override
	public List<StopWordsVO> retriveStopWords() {
		List<StopWordsVO> stopWordList = null;
		try {
			String sql = sqlProperties.getMessage(
					ReviewConstantsIF.SQLS.RETRIVE_STOPWORDS_FULL_SQL, null,
					null);
			return jdbcTemplate.query(sql, new StopWordsVOMapper());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	private final class StopWordsVOMapper implements RowMapper<StopWordsVO> {

		public StopWordsVO mapRow(ResultSet rs, int arg1) throws SQLException {
			StopWordsVO webSiteDataVO = new StopWordsVO();
			webSiteDataVO.setStopWordId(rs
					.getInt(ReviewConstantsIF.DatabaseColumns.STOPWORDID_COL));
			webSiteDataVO.setStopWord(rs
					.getString(ReviewConstantsIF.DatabaseColumns.STOPWORD_COL));
			return webSiteDataVO;

		}

	}

	@Override
	public List<String> retriveStopWordsOnly() {
		try {
			String sql = sqlProperties.getMessage(
					ReviewConstantsIF.SQLS.RETRIVE_STOPWORDS_SQL, null, null);
			return jdbcTemplate.queryForList(sql, String.class);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	@Override
	public StatusInfo removeStopword(String stopWord) {
		StatusInfo statusInfo = null;

		try {
			statusInfo = new StatusInfo();
			String sql = sqlProperties.getMessage(
					ReviewConstantsIF.SQLS.REMOVE_STOPWORD_SQL, null, null);
			jdbcTemplate.update(sql, stopWord);
			statusInfo.setStatus(true);
			return statusInfo;
		} catch (Exception e) {
			statusInfo = new StatusInfo();
			statusInfo.setStatus(false);
			statusInfo.setErrMsg(e.getMessage());
			System.out.println("Exception" + e);
			e.printStackTrace();
			return statusInfo;
		}
	}

	@Override
	public List<TokenVO> retriveAllTokens() {
		try {
			String sql = sqlProperties.getMessage(
					ReviewConstantsIF.SQLS.RETRIVE_ALLTOKENS_SQL, null, null);
			return jdbcTemplate.query(sql, new TokenMapper());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	private final class TokenMapper implements RowMapper<TokenVO> {

		public TokenVO mapRow(ResultSet rs, int arg1) throws SQLException {
			TokenVO tokenVO = new TokenVO();

			tokenVO.setTokenId(rs
					.getInt(ReviewConstantsIF.DatabaseColumns.TOKENID_COL));
			tokenVO.setTokenName(rs
					.getString(ReviewConstantsIF.DatabaseColumns.TOKENNAME_COL));

			tokenVO.setArticleName(rs
					.getString(ReviewConstantsIF.DatabaseColumns.ARTICLENAME_COL));

			return tokenVO;
		}

	}

	@Override
	public List<CleanReviewModel> retriveCleanReviews() {
		try {
			String sql = sqlProperties.getMessage(
					ReviewConstantsIF.SQLS.RETRIVE_ALLCLEAN_ARTICLES_SQL, null,
					null);
			return jdbcTemplate.query(sql, new CleanReviewModelMapper());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	private final class CleanReviewModelMapper implements
			RowMapper<CleanReviewModel> {

		public CleanReviewModel mapRow(ResultSet rs, int arg1)
				throws SQLException {
			CleanReviewModel cleanReviewModel = new CleanReviewModel();
			cleanReviewModel.setCleanId(rs
					.getInt(ReviewConstantsIF.DatabaseColumns.CLEANID_COL));
			cleanReviewModel
					.setCleanArtilce(rs
							.getString(ReviewConstantsIF.DatabaseColumns.CLEANARTICLE_COL));

			cleanReviewModel
					.setArticleName(rs
							.getString(ReviewConstantsIF.DatabaseColumns.ARTICLENAME_COL));

			return cleanReviewModel;
		}

	}

	@Override
	public StatusInfo deleteAllCleanReviews() {
		StatusInfo statusInfo = null;
		try {

			statusInfo = new StatusInfo();
			String sql = sqlProperties.getMessage(
					ReviewConstantsIF.SQLS.DELETE_ALL_CLEAN_ARTICLES_SQL, null,
					null);
			jdbcTemplate.update(sql);
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
	public StatusInfo insertCleanDetails(CleanReviewModel cleanReview) {
		StatusInfo cleanDetailStatus = null;
		try {
			cleanDetailStatus = new StatusInfo();
			String sql = sqlProperties.getMessage(
					ReviewConstantsIF.SQLS.INSERT_CLEANDETAILS_SQL, null, null);

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put(ReviewConstantsIF.DatabaseColumnsKeys.ARTICLENAME_KEY,
					cleanReview.getArticleName());
			paramMap.put(
					ReviewConstantsIF.DatabaseColumnsKeys.CLEANARTICLE_KEY,
					cleanReview.getCleanArtilce());

			namedJdbcTemplate.update(sql, paramMap);

			cleanDetailStatus.setStatus(true);
			return cleanDetailStatus;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			cleanDetailStatus = new StatusInfo();
			cleanDetailStatus.setErrMsg(e.getMessage());
			cleanDetailStatus.setStatus(false);
			return cleanDetailStatus;

		}
	}

	@Override
	public StatusInfo insertToken(TokenVO tokenVO) {
		StatusInfo insertTokenStatus = null;
		try {
			insertTokenStatus = new StatusInfo();
			String sql = sqlProperties.getMessage(
					ReviewConstantsIF.SQLS.INSERT_TOKENS_SQL, null, null);

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put(ReviewConstantsIF.DatabaseColumnsKeys.ARTICLENAME_KEY,
					tokenVO.getArticleName());
			paramMap.put(ReviewConstantsIF.DatabaseColumnsKeys.TOKENNAME_KEY,
					tokenVO.getTokenName());

			namedJdbcTemplate.update(sql, paramMap);

			insertTokenStatus.setStatus(true);
			return insertTokenStatus;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			insertTokenStatus = new StatusInfo();
			insertTokenStatus.setErrMsg(e.getMessage());
			insertTokenStatus.setStatus(false);
			return insertTokenStatus;

		}
	}

	@Override
	public StatusInfo deleteAllTokens() {
		StatusInfo statusInfo = null;

		try {
			statusInfo = new StatusInfo();
			String sql = sqlProperties.getMessage(
					ReviewConstantsIF.SQLS.DELETE_ALLTOKENS_SQL, null, null);
			jdbcTemplate.update(sql);
			statusInfo.setStatus(true);
			return statusInfo;
		} catch (Exception e) {
			statusInfo = new StatusInfo();
			statusInfo.setStatus(false);
			statusInfo.setErrMsg(e.getMessage());
			System.out.println("Exception" + e);
			e.printStackTrace();
			return statusInfo;
		}
	}

	@Override
	public List<String> retriveKeyPhraseOnly(String type) {
		try {
			String sql = sqlProperties.getMessage(
					ReviewConstantsIF.SQLS.RETRIVE_KEYPHRASE_WHERE_TYPE_SQL,
					null, null);

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put(ReviewConstantsIF.DatabaseColumnsKeys.TYPE_KEY, type);

			return namedJdbcTemplate.query(sql, paramMap,
					new KeyPhraseOnlyMapper());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	private final class KeyPhraseOnlyMapper implements RowMapper<String> {

		public String mapRow(ResultSet rs, int arg1) throws SQLException {

			return rs
					.getString(ReviewConstantsIF.DatabaseColumns.KEYPHARSE_COL);

		}

	}

	@Override
	public StatusInfo insertKeyPhrase(String keyphrase, String type) {
		StatusInfo keyPhraseStatus = null;
		try {
			keyPhraseStatus = new StatusInfo();
			String sql = sqlProperties.getMessage(
					ReviewConstantsIF.SQLS.INSERT_KEYPHRASE_SQL, null, null);

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put(ReviewConstantsIF.DatabaseColumnsKeys.KEYPHARSE_KEY,
					keyphrase);
			paramMap.put(ReviewConstantsIF.DatabaseColumnsKeys.TYPE_KEY, type);

			namedJdbcTemplate.update(sql, paramMap);

			keyPhraseStatus.setStatus(true);
			return keyPhraseStatus;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			keyPhraseStatus = new StatusInfo();
			keyPhraseStatus.setErrMsg(e.getMessage());
			keyPhraseStatus.setStatus(false);
			return keyPhraseStatus;

		}
	}

	@Override
	public List<KeyPhraseModel> retriveKeyPhrases() {
		List<KeyPhraseModel> keyPhraseModelList = null;
		try {
			String sql = sqlProperties.getMessage(
					ReviewConstantsIF.SQLS.RETRIVE_KEYPHRASE_FULL_SQL, null,
					null);

			Map<String, Object> paramMap = null;

			return namedJdbcTemplate.query(sql, paramMap,
					new KeyPhraseVOMapper());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	private final class KeyPhraseVOMapper implements RowMapper<KeyPhraseModel> {

		public KeyPhraseModel mapRow(ResultSet rs, int arg1)
				throws SQLException {
			KeyPhraseModel keyPhraseModel = new KeyPhraseModel();

			keyPhraseModel
					.setKeyPhrase(rs
							.getString(ReviewConstantsIF.DatabaseColumns.KEYPHARSE_COL));

			keyPhraseModel.setKeyPhraseId(rs
					.getInt(ReviewConstantsIF.DatabaseColumns.KEYPHRASEID_COL));

			keyPhraseModel.setType(rs
					.getString(ReviewConstantsIF.DatabaseColumns.TYPE_COL));

			return keyPhraseModel;
		}

	}

	@Override
	public StatusInfo removeKeyPhrase(String keyphrase, String type) {
		StatusInfo statusInfo = null;

		try {
			statusInfo = new StatusInfo();
			String sql = sqlProperties
					.getMessage(
							ReviewConstantsIF.SQLS.REMOVE_KEYPHRASE_WHERE_KEYPHRASE_AND_TYPE_SQL,
							null, null);

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put(ReviewConstantsIF.DatabaseColumnsKeys.KEYPHARSE_KEY,
					keyphrase);
			paramMap.put(ReviewConstantsIF.DatabaseColumnsKeys.TYPE_KEY, type);

			namedJdbcTemplate.update(sql, paramMap);
			statusInfo.setStatus(true);
			return statusInfo;
		} catch (Exception e) {
			statusInfo = new StatusInfo();
			statusInfo.setStatus(false);
			statusInfo.setErrMsg(e.getMessage());
			System.out.println("Exception" + e);
			e.printStackTrace();
			return statusInfo;
		}
	}

	@Override
	public StatusInfo insertPhraseG(PhraseGModel pharaseGModel) {
		StatusInfo phraseGStatus = null;
		try {
			phraseGStatus = new StatusInfo();
			String sql = sqlProperties.getMessage(
					ReviewConstantsIF.SQLS.INSERT_PHRASEG_SQL, null, null);

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put(ReviewConstantsIF.DatabaseColumnsKeys.NOTTYPE_KEY,
					pharaseGModel.getNoType());
			paramMap.put(ReviewConstantsIF.DatabaseColumnsKeys.TYPE_KEY,
					pharaseGModel.getType());
			paramMap.put(ReviewConstantsIF.DatabaseColumnsKeys.PHASEG_KEY,
					pharaseGModel.getPhraseG());

			namedJdbcTemplate.update(sql, paramMap);

			phraseGStatus.setStatus(true);
			return phraseGStatus;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			phraseGStatus = new StatusInfo();
			phraseGStatus.setErrMsg(e.getMessage());
			phraseGStatus.setStatus(false);
			return phraseGStatus;

		}
	}

	@Override
	public List<String> retrivePhraseGWhereTypeAndNoType(
			PhraseGModel pharaseGModel) {

		try {
			String sql = sqlProperties
					.getMessage(
							ReviewConstantsIF.SQLS.RETRIVE_PHRASEG_WHERE_TYPE_NOTYPE_SQL,
							null, null);

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put(ReviewConstantsIF.DatabaseColumnsKeys.TYPE_KEY,
					pharaseGModel.getType());
			paramMap.put(ReviewConstantsIF.DatabaseColumnsKeys.NOTTYPE_KEY,
					pharaseGModel.getNoType());

			return namedJdbcTemplate.query(sql, paramMap,
					new PhraseGNewOnlyMapper());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	private final class PhraseGNewOnlyMapper implements RowMapper<String> {

		public String mapRow(ResultSet rs, int arg1) throws SQLException {

			return rs.getString(ReviewConstantsIF.DatabaseColumns.PHASEG_COL);

		}

	}

	@Override
	public List<PhraseGModel> retrivePhraseGPhrases() {
		List<PhraseGModel> pharseGModelList = null;
		try {
			String sql = sqlProperties
					.getMessage(
							ReviewConstantsIF.SQLS.RETRIVE_PHRASEG_FULL_SQL,
							null, null);

			Map<String, Object> paramMap = null;

			return namedJdbcTemplate
					.query(sql, paramMap, new PhraseGVOMapper());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return pharseGModelList;
		}
	}

	private final class PhraseGVOMapper implements RowMapper<PhraseGModel> {

		public PhraseGModel mapRow(ResultSet rs, int arg1) throws SQLException {
			PhraseGModel phraseGModel = new PhraseGModel();

			phraseGModel.setType(rs
					.getString(ReviewConstantsIF.DatabaseColumns.TYPE_COL));
			phraseGModel.setPhraseG(rs
					.getString(ReviewConstantsIF.DatabaseColumns.PHASEG_COL));
			phraseGModel.setPhraseGId(rs
					.getInt(ReviewConstantsIF.DatabaseColumns.PHARSEGID_COL));
			phraseGModel.setNoType(rs
					.getString(ReviewConstantsIF.DatabaseColumns.NOTTYPE_COL));

			return phraseGModel;
		}

	}

	// REMOVE_PHRASEG_WHERE_PHRASEG_AND_TYPE_AND_NOTYPE_SQL
	@Override
	public StatusInfo removePhraseGForPhraseGTypeNoType(
			PhraseGModel phraseGModel) {
		StatusInfo statusInfo = null;

		try {
			statusInfo = new StatusInfo();
			String sql = sqlProperties
					.getMessage(
							ReviewConstantsIF.SQLS.REMOVE_PHRASEG_WHERE_PHRASEG_AND_TYPE_AND_NOTYPE_SQL,
							null, null);

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put(ReviewConstantsIF.DatabaseColumnsKeys.PHASEG_KEY,
					phraseGModel.getPhraseG());
			paramMap.put(ReviewConstantsIF.DatabaseColumnsKeys.TYPE_KEY,
					phraseGModel.getType());
			paramMap.put(ReviewConstantsIF.DatabaseColumnsKeys.NOTTYPE_KEY,
					phraseGModel.getNoType());

			namedJdbcTemplate.update(sql, paramMap);
			statusInfo.setStatus(true);
			return statusInfo;
		} catch (Exception e) {
			statusInfo = new StatusInfo();
			statusInfo.setStatus(false);
			statusInfo.setErrMsg(e.getMessage());
			System.out.println("Exception" + e);
			e.printStackTrace();
			return statusInfo;
		}
	}

	@Override
	public StatusInfo deleteAllPhrases() {
		StatusInfo statusInfo = null;
		try {

			statusInfo = new StatusInfo();
			String sql = sqlProperties.getMessage(
					ReviewConstantsIF.SQLS.DELETE_ALL_PHRASES_SQL, null, null);

			Map<String, Object> paramMap = null;

			namedJdbcTemplate.update(sql, paramMap);
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
	public List<String> retriveTokensOnly() {
		try {
			String sql = sqlProperties.getMessage(
					ReviewConstantsIF.SQLS.RETRIVE_ONLY_ALLTOKENS_SQL, null,
					null);
			return jdbcTemplate.queryForList(sql, String.class);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<String> retriveAllKeyPhraseOnly() {
		try {
			String sql = sqlProperties.getMessage(
					ReviewConstantsIF.SQLS.RETRIVE_ONLY_ALLKEYPHRASES_SQL,
					null, null);
			return jdbcTemplate.queryForList(sql, String.class);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<String> retriveAllPhraseGOnly() {
		try {
			String sql = sqlProperties.getMessage(
					ReviewConstantsIF.SQLS.RETRIVE_ONLY_ALLKEYPHRASES_SQL,
					null, null);
			return jdbcTemplate.queryForList(sql, String.class);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<String> retriveAllAdjectives() {
		try {
			String sql = sqlProperties.getMessage(
					ReviewConstantsIF.SQLS.RETRIVE_ONLY_ALL_ADJECTIVES_SQL,
					null, null);
			return jdbcTemplate.queryForList(sql, String.class);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	@Override
	public StatusInfo insertPhrasesList(Set<String> phrases) {
		StatusInfo insertStopWordStatus = null;
		try {
			insertStopWordStatus = new StatusInfo();
			String sql = sqlProperties.getMessage(
					ReviewConstantsIF.SQLS.INSERT_PHRASE_SQL, null, null);

			for (String phraString : phrases) {
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put(ReviewConstantsIF.DatabaseColumnsKeys.PHRASE_KEY,
						phraString);

				namedJdbcTemplate.update(sql, paramMap);

			}

			insertStopWordStatus.setStatus(true);
			return insertStopWordStatus;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			insertStopWordStatus = new StatusInfo();
			insertStopWordStatus.setErrMsg(e.getMessage());
			insertStopWordStatus.setStatus(false);
			return insertStopWordStatus;

		}
	}

	@Override
	public List<PhraseVO> retrivePhraseList() {
		try {
			String sql = sqlProperties.getMessage(
					ReviewConstantsIF.SQLS.RETRIVE_ALL_PHRASES_SQL, null, null);

			Map<String, Object> paramMap = null;

			return namedJdbcTemplate.query(sql, paramMap,
					new PhraseListMapper());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	private final class PhraseListMapper implements RowMapper<PhraseVO> {

		public PhraseVO mapRow(ResultSet rs, int arg1) throws SQLException {

			PhraseVO reviewModel = new PhraseVO();

			reviewModel.setPhrase(rs
					.getString(ReviewConstantsIF.DatabaseColumns.PHRASE_COL));
			reviewModel.setPhraseId(rs
					.getInt(ReviewConstantsIF.DatabaseColumns.PHRASEID_COL));

			return reviewModel;

		}

	}

	@Override
	public StatusInfo deleteAllAdjectives() {
		StatusInfo statusInfo = null;

		try {
			statusInfo = new StatusInfo();
			String sql = sqlProperties.getMessage(
					ReviewConstantsIF.SQLS.DELETE_ALL_ADJECTIVES_SQL, null,
					null);
			jdbcTemplate.update(sql);
			statusInfo.setStatus(true);
			return statusInfo;
		} catch (Exception e) {
			statusInfo = new StatusInfo();
			statusInfo.setStatus(false);
			statusInfo.setErrMsg(e.getMessage());
			System.out.println("Exception" + e);
			e.printStackTrace();
			return statusInfo;
		}
	}

	@Override
	public StatusInfo insertAdjectives(List<String> adjectiveList) {
		StatusInfo adjectiveStatus = null;
		try {
			adjectiveStatus = new StatusInfo();
			String sql = sqlProperties.getMessage(
					ReviewConstantsIF.SQLS.INSERT_ADJECTIVE_SQL, null, null);

			if (adjectiveList != null && !adjectiveList.isEmpty()) {

				for (String adjective : adjectiveList) {
					Map<String, Object> paramMap = new HashMap<String, Object>();
					paramMap.put(
							ReviewConstantsIF.DatabaseColumnsKeys.ADJECTIVE_KEY,
							adjective);
					namedJdbcTemplate.update(sql, paramMap);
				}

			}

			adjectiveStatus.setStatus(true);
			return adjectiveStatus;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			adjectiveStatus = new StatusInfo();
			adjectiveStatus.setErrMsg(e.getMessage());
			adjectiveStatus.setStatus(false);
			return adjectiveStatus;

		}
	}

	@Override
	public List<AdjectiveVO> retriveAllFullAdjectives() {
		List<AdjectiveVO> stopWordList = null;
		try {
			String sql = sqlProperties.getMessage(
					ReviewConstantsIF.SQLS.RETRIVE_ADJECTIVES_FULL_SQL, null,
					null);

			Map<String, Object> paramMap = null;

			return namedJdbcTemplate.query(sql, paramMap,
					new AdjectiveVOMapper());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	private final class AdjectiveVOMapper implements RowMapper<AdjectiveVO> {

		public AdjectiveVO mapRow(ResultSet rs, int arg1) throws SQLException {
			AdjectiveVO adjectiveVO = new AdjectiveVO();
			adjectiveVO
					.setAdjective(rs
							.getString(ReviewConstantsIF.DatabaseColumns.ADJECTIVE_COL));
			adjectiveVO
					.setAdjectiveId((rs
							.getInt(ReviewConstantsIF.DatabaseColumns.ADJECTIVEID_COL)));
			return adjectiveVO;

		}

	}

	@Override
	public StatusInfo insertStruture1(List<String> structureList) {

		StatusInfo adjectiveStatus = null;
		try {
			adjectiveStatus = new StatusInfo();
			String sql = sqlProperties.getMessage(
					ReviewConstantsIF.SQLS.INSERT_STRUCTURE1_SQL, null, null);

			if (structureList != null && !structureList.isEmpty()) {

				for (String structure1 : structureList) {
					Map<String, Object> paramMap = new HashMap<String, Object>();
					paramMap.put(
							ReviewConstantsIF.DatabaseColumnsKeys.STRUCTURE_KEY,
							structure1);
					namedJdbcTemplate.update(sql, paramMap);
				}

			}

			adjectiveStatus.setStatus(true);
			return adjectiveStatus;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			adjectiveStatus = new StatusInfo();
			adjectiveStatus.setErrMsg(e.getMessage());
			adjectiveStatus.setStatus(false);
			return adjectiveStatus;

		}

	}

	@Override
	public List<StructureVO> retriveStructure1List() {
		List<StructureVO> structure1List = null;
		try {
			String sql = sqlProperties.getMessage(
					ReviewConstantsIF.SQLS.RETRIVE_STRUCTURE1_FULL_SQL, null,
					null);
			return jdbcTemplate.query(sql, new Structure1VOMapper());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	private final class Structure1VOMapper implements RowMapper<StructureVO> {

		public StructureVO mapRow(ResultSet rs, int arg1) throws SQLException {
			StructureVO strutureVO = new StructureVO();
			strutureVO.setStructureId(rs
					.getInt(ReviewConstantsIF.DatabaseColumns.STRUCTUREID_COL));
			strutureVO
					.setStructure(rs
							.getString(ReviewConstantsIF.DatabaseColumns.STRUCTURE_COL));
			return strutureVO;

		}

	}

	@Override
	public StatusInfo deleteAllStructure1() {
		StatusInfo statusInfo = null;

		try {
			statusInfo = new StatusInfo();
			String sql = sqlProperties.getMessage(
					ReviewConstantsIF.SQLS.DELETE_ALL_STRUCTURE1_SQL, null,
					null);
			jdbcTemplate.update(sql);
			statusInfo.setStatus(true);
			return statusInfo;
		} catch (Exception e) {
			statusInfo = new StatusInfo();
			statusInfo.setStatus(false);
			statusInfo.setErrMsg(e.getMessage());
			System.out.println("Exception" + e);
			e.printStackTrace();
			return statusInfo;
		}
	}

	@Override
	public List<StructureVO> retriveStructure2List() {
		List<StructureVO> structure2List = null;
		try {
			String sql = sqlProperties.getMessage(
					ReviewConstantsIF.SQLS.RETRIVE_STRUCTURE2_FULL_SQL, null,
					null);
			return jdbcTemplate.query(sql, new Structure1VOMapper());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<StructureVO> retriveStructure3List() {
		List<StructureVO> structure3List = null;
		try {
			String sql = sqlProperties.getMessage(
					ReviewConstantsIF.SQLS.RETRIVE_STRUCTURE3_FULL_SQL, null,
					null);
			return jdbcTemplate.query(sql, new Structure1VOMapper());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	@Override
	public StatusInfo deleteAllStructure2() {
		StatusInfo statusInfo = null;

		try {
			statusInfo = new StatusInfo();
			String sql = sqlProperties.getMessage(
					ReviewConstantsIF.SQLS.DELETE_ALL_STRUCTURE2_SQL, null,
					null);
			jdbcTemplate.update(sql);
			statusInfo.setStatus(true);
			return statusInfo;
		} catch (Exception e) {
			statusInfo = new StatusInfo();
			statusInfo.setStatus(false);
			statusInfo.setErrMsg(e.getMessage());
			System.out.println("Exception" + e);
			e.printStackTrace();
			return statusInfo;
		}
	}

	@Override
	public StatusInfo insertStruture2(List<String> structureList) {
		StatusInfo adjectiveStatus = null;
		try {
			adjectiveStatus = new StatusInfo();
			String sql = sqlProperties.getMessage(
					ReviewConstantsIF.SQLS.INSERT_STRUCTURE2_SQL, null, null);

			if (structureList != null && !structureList.isEmpty()) {

				for (String structure1 : structureList) {
					Map<String, Object> paramMap = new HashMap<String, Object>();
					paramMap.put(
							ReviewConstantsIF.DatabaseColumnsKeys.STRUCTURE_KEY,
							structure1);
					namedJdbcTemplate.update(sql, paramMap);
				}

			}

			adjectiveStatus.setStatus(true);
			return adjectiveStatus;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			adjectiveStatus = new StatusInfo();
			adjectiveStatus.setErrMsg(e.getMessage());
			adjectiveStatus.setStatus(false);
			return adjectiveStatus;

		}
	}

	@Override
	public List<String> retrivePhraseListOnly() {
		try {
			String sql = sqlProperties.getMessage(
					ReviewConstantsIF.SQLS.RETRIVE_ALL_PHRASES_ONLY_SQL, null,
					null);

			Map<String, Object> paramMap = null;

			return jdbcTemplate.queryForList(sql, String.class);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	@Override
	public StatusInfo deleteAllStructure3() {
		StatusInfo statusInfo = null;

		try {
			statusInfo = new StatusInfo();
			String sql = sqlProperties.getMessage(
					ReviewConstantsIF.SQLS.DELETE_ALL_STRUCTURE3_SQL, null,
					null);
			jdbcTemplate.update(sql);
			statusInfo.setStatus(true);
			return statusInfo;
		} catch (Exception e) {
			statusInfo = new StatusInfo();
			statusInfo.setStatus(false);
			statusInfo.setErrMsg(e.getMessage());
			System.out.println("Exception" + e);
			e.printStackTrace();
			return statusInfo;
		}
	}

	@Override
	public StatusInfo insertStruture3(List<String> structureList) {
		StatusInfo structure3Status = null;
		try {
			structure3Status = new StatusInfo();
			String sql = sqlProperties.getMessage(
					ReviewConstantsIF.SQLS.INSERT_STRUCTURE3_SQL, null, null);

			if (structureList != null && !structureList.isEmpty()) {

				for (String structure1 : structureList) {
					Map<String, Object> paramMap = new HashMap<String, Object>();
					paramMap.put(
							ReviewConstantsIF.DatabaseColumnsKeys.STRUCTURE_KEY,
							structure1);
					namedJdbcTemplate.update(sql, paramMap);
				}

			}

			structure3Status.setStatus(true);
			return structure3Status;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			structure3Status = new StatusInfo();
			structure3Status.setErrMsg(e.getMessage());
			structure3Status.setStatus(false);
			return structure3Status;

		}
	}

	@Override
	public StatusInfo deleteAllFreq() {
		StatusInfo statusInfo = null;

		try {
			statusInfo = new StatusInfo();
			String sql = sqlProperties.getMessage(
					ReviewConstantsIF.SQLS.DELETE_ALL_FREQ_SQL, null, null);
			jdbcTemplate.update(sql);
			statusInfo.setStatus(true);
			return statusInfo;
		} catch (Exception e) {
			statusInfo = new StatusInfo();
			statusInfo.setStatus(false);
			statusInfo.setErrMsg(e.getMessage());
			System.out.println("Exception" + e);
			e.printStackTrace();
			return statusInfo;
		}
	}

	@Override
	public List<String> retriveDiffrentConcepts() {
		try {
			String sql = sqlProperties.getMessage(
					ReviewConstantsIF.SQLS.RETRIVE_TYPES_ONLY_SQL, null, null);
			return jdbcTemplate.queryForList(sql, String.class);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<String> retriveAllKeyPhraseOnlyForConcept(String type) {
		try {
			String sql = sqlProperties.getMessage(
					ReviewConstantsIF.SQLS.RETRIVE_KEYPHRASE_WHERE_TYPE_SQL,
					null, null);

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put(ReviewConstantsIF.DatabaseColumnsKeys.TYPE_KEY, type);

			return namedJdbcTemplate.query(sql, paramMap,
					new KeyPhraseOnlyMapper());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<String> retriveAllStructure1Only() {
		try {
			String sql = sqlProperties.getMessage(
					ReviewConstantsIF.SQLS.RETRIVE_ALL_STRUCTURE1_ONLY_SQL,
					null, null);
			return jdbcTemplate.queryForList(sql, String.class);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<String> retriveAllStructure2Only() {
		try {
			String sql = sqlProperties.getMessage(
					ReviewConstantsIF.SQLS.RETRIVE_ALL_STRUCTURE2_ONLY_SQL,
					null, null);
			return jdbcTemplate.queryForList(sql, String.class);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<String> retriveAllStructure3Only() {
		try {
			String sql = sqlProperties.getMessage(
					ReviewConstantsIF.SQLS.RETRIVE_ALL_STRUCTURE3_ONLY_SQL,
					null, null);
			return jdbcTemplate.queryForList(sql, String.class);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<String> retrivePhraseGWhereTYPEISNOT(String type) {
		try {
			String sql = sqlProperties
					.getMessage(
							ReviewConstantsIF.SQLS.RETRIVE_ALL_PHARSEG_ONLY_WHERE_TYPE_SQL,
							null, null);
			
			Map<String,Object> map = new HashMap<String, Object>();
			map.put(ReviewConstantsIF.DatabaseColumnsKeys.TYPE_KEY, type);
			
			return namedJdbcTemplate.query(sql,map, new PhraseGonlyMapper());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}
	
	private final class PhraseGonlyMapper implements RowMapper{

		public String mapRow(ResultSet rs, int arg1) throws SQLException {
	
			return rs.getString(ReviewConstantsIF.DatabaseColumns.PHASEG_COL);
		}

	}

	@Override
	public StatusInfo insertFreqComputation(FreqComputation freqComputation) {
		StatusInfo cleanDetailStatus = null;
		try {
			cleanDetailStatus = new StatusInfo();
			String sql = sqlProperties.getMessage(
					ReviewConstantsIF.SQLS.INSERT_FREQCOMPUTATION_SQL, null,
					null);

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put(ReviewConstantsIF.DatabaseColumnsKeys.ARTICLENAME_KEY,
					freqComputation.getArticleName());
			paramMap.put(
					ReviewConstantsIF.DatabaseColumnsKeys.FREQADJECTIVE_KEY,
					freqComputation.getFreqAdjective());
			paramMap.put(
					ReviewConstantsIF.DatabaseColumnsKeys.FREQKEYPHARSE_KEY,
					freqComputation.getFreqKeyPhrase());
			paramMap.put(ReviewConstantsIF.DatabaseColumnsKeys.FREQPHRASE_KEY,
					freqComputation.getFreqPhrase());
			paramMap.put(ReviewConstantsIF.DatabaseColumnsKeys.FREQPHRASEG_KEY,
					freqComputation.getFreqPhraseG());
			paramMap.put(
					ReviewConstantsIF.DatabaseColumnsKeys.FREQSTRUCTURE1_KEY,
					freqComputation.getFreqStructure1());
			paramMap.put(
					ReviewConstantsIF.DatabaseColumnsKeys.FREQSTRUCTURE2_KEY,
					freqComputation.getFreqStructure2());
			paramMap.put(
					ReviewConstantsIF.DatabaseColumnsKeys.FREQSTRUCTURE3_KEY,
					freqComputation.getFreqStructure3());
			paramMap.put(ReviewConstantsIF.DatabaseColumnsKeys.SENTENCEID_KEY,
					freqComputation.getSentenceId());
			paramMap.put(ReviewConstantsIF.DatabaseColumnsKeys.TYPE_KEY,
					freqComputation.getType());

			namedJdbcTemplate.update(sql, paramMap);

			cleanDetailStatus.setStatus(true);
			return cleanDetailStatus;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			cleanDetailStatus = new StatusInfo();
			cleanDetailStatus.setErrMsg(e.getMessage());
			cleanDetailStatus.setStatus(false);
			return cleanDetailStatus;

		}
	}

	@Override
	public List<FreqComputation> viewFreq() {
		try {

			String sql = sqlProperties.getMessage(
					ReviewConstantsIF.SQLS.RETRIVE_FREQCOMPUTATION_SQL, null,
					null);
			return jdbcTemplate.query(sql, new FreqComputationVOMapper());
		} catch (Exception e) {
			System.out.println("Exception" + e);
			return null;
		}
	}

	private final class FreqComputationVOMapper implements
			RowMapper<FreqComputation> {

		public FreqComputation mapRow(ResultSet rs, int arg1)
				throws SQLException {

			FreqComputation freqComputation = new FreqComputation();

			freqComputation
					.setArticleName(rs
							.getString(ReviewConstantsIF.DatabaseColumns.ARTICLENAME_COL));
			freqComputation
					.setFreqAdjective(rs
							.getInt(ReviewConstantsIF.DatabaseColumns.FREQADJECTIVE_COL));
			freqComputation.setFreqId(rs
					.getInt(ReviewConstantsIF.DatabaseColumns.FREQID_COL));
			freqComputation
					.setFreqKeyPhrase(rs
							.getInt(ReviewConstantsIF.DatabaseColumns.FREQKEYPHARSE_COL));
			freqComputation.setFreqPhrase(rs
					.getInt(ReviewConstantsIF.DatabaseColumns.FREQPHRASE_COL));
			freqComputation.setFreqPhraseG(rs
					.getInt(ReviewConstantsIF.DatabaseColumns.FREQPHRASEG_COL));
			freqComputation
					.setFreqStructure1(rs
							.getInt(ReviewConstantsIF.DatabaseColumns.FREQSTRUCTURE1_COL));
			freqComputation
					.setFreqStructure2(rs
							.getInt(ReviewConstantsIF.DatabaseColumns.FREQSTRUCTURE2_COL));
			freqComputation
					.setFreqStructure3(rs
							.getInt(ReviewConstantsIF.DatabaseColumns.FREQSTRUCTURE3_COL));
			freqComputation.setSentenceId(rs
					.getInt(ReviewConstantsIF.DatabaseColumns.SENTENCEID_COL));
			freqComputation.setType(rs
					.getString(ReviewConstantsIF.DatabaseColumns.TYPE_COL));
			return freqComputation;

		}

	}

	@Override
	public List<String> retriveDistincetTokensForArticleName(
			String articleNameLeft) {
		try {
			String sql = sqlProperties
					.getMessage(
							ReviewConstantsIF.SQLS.RETRIVE_DINSTINCT_TOKENS_FOR_ARTICLENAME_SQL,
							null, null);

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put(ReviewConstantsIF.DatabaseColumnsKeys.ARTICLENAME_KEY,
					articleNameLeft);

			return namedJdbcTemplate
					.query(sql, paramMap, new TokenOnlyMapper());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	private final class TokenOnlyMapper implements RowMapper<String> {

		public String mapRow(ResultSet rs, int arg1) throws SQLException {
			return rs
					.getString(ReviewConstantsIF.DatabaseColumns.TOKENNAME_COL);
		}

	}

	@Override
	public List<String> retriveKeyPhraseOnlyForTokensAndType(
			List<String> tokenNamesLeft, String typeCombo) {
		try {
			String sql = sqlProperties
					.getMessage(
							ReviewConstantsIF.SQLS.RETRIVE_DINSTINCT_KEYPHRASES_FOR_TOKENS_AND_TYPE_SQL,
							null, null);

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put(ReviewConstantsIF.DatabaseColumnsKeys.TYPE_KEY,
					typeCombo);
			paramMap.put(
					ReviewConstantsIF.DatabaseColumnsKeys.KEYPHARSELIST_KEY,
					tokenNamesLeft);

			return namedJdbcTemplate.query(sql, paramMap,
					new KeyPharseOnlyInMapper());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	private final class KeyPharseOnlyInMapper implements RowMapper<String> {

		public String mapRow(ResultSet rs, int arg1) throws SQLException {
			return rs
					.getString(ReviewConstantsIF.DatabaseColumns.KEYPHARSE_COL);
		}

	}

	@Override
	public int retriveCountForTokenAndArticleName(String word,
			String articleNameLeft) {

		int count = 0;
		try {

			String sql = sqlProperties
					.getMessage(
							ReviewConstantsIF.SQLS.SELECT_COUNT_FOR_TOKENNAME_ARTICLENAME_SQL,
							null, null);

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put(ReviewConstantsIF.DatabaseColumnsKeys.TOKENNAME_KEY,
					word);
			paramMap.put(ReviewConstantsIF.DatabaseColumnsKeys.ARTICLENAME_KEY,
					articleNameLeft);

			return namedJdbcTemplate.queryForInt(sql, paramMap);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());

		}

		return count;
	}

	@Override
	public int findNoOfTokensForArticleName(String articleNameLeft) {
		int count = 0;
		try {

			String sql = sqlProperties.getMessage(
					ReviewConstantsIF.SQLS.SELECT_COUNT_FOR_ARTICLENAME_SQL,
					null, null);

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put(ReviewConstantsIF.DatabaseColumnsKeys.ARTICLENAME_KEY,
					articleNameLeft);

			return namedJdbcTemplate.queryForInt(sql, paramMap);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());

		}

		return count;
	}

	@Override
	public List<FreqComputation> retriveFreqComputationForArticleName(
			String articleNameLeft) {
		try {

			String sql = sqlProperties
					.getMessage(
							ReviewConstantsIF.SQLS.RETRIVE_FREQCOMPUTATION_WHERE_ARTICLENAME_SQL,
							null, null);

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put(ReviewConstantsIF.DatabaseColumnsKeys.ARTICLENAME_KEY,
					articleNameLeft);

			return namedJdbcTemplate.query(sql, paramMap,
					new FreqComputationVOMapper());
		} catch (Exception e) {
			System.out.println("Exception" + e);
			return null;
		}
	}

	@Override
	public StatusInfo insertAdjectiveNLP(String adjective) {
		StatusInfo insertStopWordStatus = null;
		try {
			insertStopWordStatus = new StatusInfo();
			String sql = sqlProperties.getMessage(
					ReviewConstantsIF.SQLS.INSERT_ADJECTIVES_NLP_SQL, null,
					null);
			System.out.println("SQL----" + sql);

			jdbcTemplate.update(sql, new Object[] { adjective },
					new int[] { Types.VARCHAR });
			insertStopWordStatus.setStatus(true);
			return insertStopWordStatus;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			insertStopWordStatus = new StatusInfo();
			insertStopWordStatus.setErrMsg(e.getMessage());
			insertStopWordStatus.setStatus(false);
			return insertStopWordStatus;

		}
	}

	public List<String> retriveAdjectivesForNLP() {
		try {
			String sql = sqlProperties.getMessage(
					ReviewConstantsIF.SQLS.RETRIVE_ADJECTIVESNLP_SQL, null, null);
			return jdbcTemplate.queryForList(sql, String.class);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<AdjectiveVO> retriveAllFullNLPAdjectives() {
		List<AdjectiveVO> stopWordList = null;
		try {
			String sql = sqlProperties.getMessage(
					ReviewConstantsIF.SQLS.RETRIVE_ADJECTIVESNLP_FULL_SQL, null,
					null);

			Map<String, Object> paramMap = null;

			return namedJdbcTemplate.query(sql, paramMap,
					new AdjectiveVOMapper());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<FreqComputation> retriveFreqComputationForArticleNameAndType(
			String articleNameLeft, String typeCombo) {
		try {

			String sql = sqlProperties
					.getMessage(
							ReviewConstantsIF.SQLS.RETRIVE_FREQCOMPUTATION_WHERE_ARTICLENAME_AND_TYPE_SQL,
							null, null);

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put(ReviewConstantsIF.DatabaseColumnsKeys.ARTICLENAME_KEY,
					articleNameLeft);
			paramMap.put(ReviewConstantsIF.DatabaseColumnsKeys.TYPE_KEY,
					typeCombo);

			return namedJdbcTemplate.query(sql, paramMap,
					new FreqComputationVOMapper());
		} catch (Exception e) {
			System.out.println("Exception" + e);
			return null;
		}
	}
}
