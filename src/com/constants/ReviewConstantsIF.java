package com.constants;

public interface ReviewConstantsIF {

	interface SQLS {

		/* For Reviews */
		public static final String RETRIVE_REVIEWS_SQL = "RETRIVE_REVIEWS_SQL";
		public static final String INSERT_ARTICLE_SQL = "INSERT_ARTICLE_SQL";

		/* For Stopwords */
		public static final String INSERT_STOPWORD_SQL = "INSERT_STOPWORD_SQL";
		public static final String RETRIVE_STOPWORDS_FULL_SQL = "RETRIVE_STOPWORDS_FULL_SQL";
		public static final String RETRIVE_STOPWORDS_SQL = "RETRIVE_STOPWORDS_SQL";
		public static final String REMOVE_STOPWORD_SQL = "REMOVE_STOPWORD_SQL";

		/* Tokenization */
		public static final String RETRIVE_ALLTOKENS_SQL = "RETRIVE_ALLTOKENS_SQL";
		public static final String DELETE_ALLTOKENS_SQL = "DELETE_ALLTOKENS_SQL";
		public static final String INSERT_TOKENS_SQL = "INSERT_TOKENS_SQL";

		/* Clean Reviews */
		public static final String RETRIVE_ALLCLEAN_ARTICLES_SQL = "RETRIVE_ALLCLEAN_ARTICLES_SQL";
		public static final String DELETE_ALL_CLEAN_ARTICLES_SQL = "DELETE_ALL_CLEAN_ARTICLES_SQL";
		public static final String INSERT_CLEANDETAILS_SQL = "INSERT_CLEANDETAILS_SQL";

		/* Key Phrase */
		public static final String INSERT_KEYPHRASE_SQL = "INSERT_KEYPHRASE_SQL";
		public static final String RETRIVE_KEYPHRASE_WHERE_TYPE_SQL = "RETRIVE_KEYPHRASE_WHERE_TYPE_SQL";
		public static final String RETRIVE_KEYPHRASE_FULL_SQL = "RETRIVE_KEYPHRASE_FULL_SQL";
		public static final String REMOVE_KEYPHRASE_WHERE_KEYPHRASE_AND_TYPE_SQL = "REMOVE_KEYPHRASE_WHERE_KEYPHRASE_AND_TYPE_SQL";

		/* Phrase G */

		public static final String INSERT_PHRASEG_SQL = "INSERT_PHRASEG_SQL";
		public static final String RETRIVE_PHRASEG_WHERE_TYPE_NOTYPE_SQL = "RETRIVE_PHRASEG_WHERE_TYPE_NOTYPE_SQL";
		public static final String RETRIVE_PHRASEG_FULL_SQL = "RETRIVE_PHRASEG_FULL_SQL";
		public static final String REMOVE_PHRASEG_WHERE_PHRASEG_AND_TYPE_AND_NOTYPE_SQL = "REMOVE_PHRASEG_WHERE_PHRASEG_AND_TYPE_AND_NOTYPE_SQL";

		/* Phrase */

		public static final String DELETE_ALL_PHRASES_SQL = "DELETE_ALL_PHRASES_SQL";
		public static final String RETRIVE_ONLY_ALLTOKENS_SQL = "RETRIVE_ONLY_ALLTOKENS_SQL";
		public static final String RETRIVE_ONLY_ALLKEYPHRASES_SQL = "RETRIVE_ONLY_ALLKEYPHRASES_SQL";
		public static final String RETRIVE_ONLY_ALL_ADJECTIVES_SQL = "RETRIVE_ONLY_ALL_ADJECTIVES_SQL";
		public static final String INSERT_PHRASE_SQL = "INSERT_PHRASE_SQL";
		public static final String RETRIVE_ALL_PHRASES_SQL = "RETRIVE_ALL_PHRASES_SQL";

		/* Adjectives */
		public static final String DELETE_ALL_ADJECTIVES_SQL = "DELETE_ALL_ADJECTIVES_SQL";
		public static final String INSERT_ADJECTIVE_SQL = "INSERT_ADJECTIVE_SQL";
		public static final String RETRIVE_ADJECTIVES_FULL_SQL = "RETRIVE_ADJECTIVES_FULL_SQL";

		/* Structure1 */
		public static final String INSERT_STRUCTURE1_SQL = "INSERT_STRUCTURE1_SQL";
		public static final String DELETE_ALL_STRUCTURE1_SQL = "DELETE_ALL_STRUCTURE1_SQL";
		public static final String RETRIVE_STRUCTURE1_FULL_SQL = "RETRIVE_STRUCTURE1_FULL_SQL";
		public static final String RETRIVE_STRUCTURE2_FULL_SQL = "RETRIVE_STRUCTURE2_FULL_SQL";
		public static final String RETRIVE_STRUCTURE3_FULL_SQL = "RETRIVE_STRUCTURE3_FULL_SQL";
		public static final String DELETE_ALL_STRUCTURE2_SQL = "DELETE_ALL_STRUCTURE2_SQL";
		public static final String INSERT_STRUCTURE2_SQL = "INSERT_STRUCTURE2_SQL";
		public static final String DELETE_ALL_STRUCTURE3_SQL = "DELETE_ALL_STRUCTURE3_SQL";

		/* Frequency */
		public static final String RETRIVE_ALL_PHRASES_ONLY_SQL = "RETRIVE_ALL_PHRASES_ONLY_SQL";
		public static final String INSERT_STRUCTURE3_SQL = "INSERT_STRUCTURE3_SQL";
		public static final String DELETE_ALL_FREQ_SQL = "DELETE_ALL_FREQ_SQL";
		public static final String RETRIVE_TYPES_ONLY_SQL = "RETRIVE_TYPES_ONLY_SQL";
		public static final String RETRIVE_ALL_STRUCTURE1_ONLY_SQL = "RETRIVE_ALL_STRUCTURE1_ONLY_SQL";
		public static final String RETRIVE_ALL_STRUCTURE2_ONLY_SQL = "RETRIVE_ALL_STRUCTURE2_ONLY_SQL";
		public static final String RETRIVE_ALL_STRUCTURE3_ONLY_SQL = "RETRIVE_ALL_STRUCTURE3_ONLY_SQL";
		public static final String RETRIVE_ALL_PHARSEG_ONLY_WHERE_TYPE_SQL = "RETRIVE_ALL_PHARSEG_ONLY_WHERE_TYPE_SQL";
		public static final String INSERT_FREQCOMPUTATION_SQL = "INSERT_FREQCOMPUTATION_SQL";
		public static final String RETRIVE_FREQCOMPUTATION_SQL = "RETRIVE_FREQCOMPUTATION_SQL";
		public static final String RETRIVE_DINSTINCT_TOKENS_FOR_ARTICLENAME_SQL = "RETRIVE_DINSTINCT_TOKENS_FOR_ARTICLENAME_SQL";
		public static final String RETRIVE_DINSTINCT_KEYPHRASES_FOR_TOKENS_AND_TYPE_SQL = "RETRIVE_DINSTINCT_KEYPHRASES_FOR_TOKENS_AND_TYPE_SQL";
		public static final String SELECT_COUNT_FOR_TOKENNAME_ARTICLENAME_SQL = "SELECT_COUNT_FOR_TOKENNAME_ARTICLENAME_SQL";
		public static final String SELECT_COUNT_FOR_ARTICLENAME_SQL = "SELECT_COUNT_FOR_ARTICLENAME_SQL";
		public static final String RETRIVE_FREQCOMPUTATION_WHERE_ARTICLENAME_SQL = "RETRIVE_FREQCOMPUTATION_WHERE_ARTICLENAME_SQL";
		public static final String INSERT_ADJECTIVES_NLP_SQL = "INSERT_ADJECTIVES_NLP_SQL";
		public static final String RETRIVE_ADJECTIVESNLP_SQL = "RETRIVE_ADJECTIVESNLP_SQL";
		public static final String RETRIVE_ADJECTIVESNLP_FULL_SQL = "RETRIVE_ADJECTIVESNLP_FULL_SQL";
		public static final String RETRIVE_FREQCOMPUTATION_WHERE_ARTICLENAME_AND_TYPE_SQL = "RETRIVE_FREQCOMPUTATION_WHERE_ARTICLENAME_AND_TYPE_SQL";

	}

	interface CONSTANTS {

		public static final String SPACE = "  ";

	}

	interface Message {

		public static final String MESSAGE_INTERNAL = "An internal has Ocuured. Please Contact System Administrator";
		public static final String ARTICLE_STORED_SUCESSFULLY = "Article Stored Sucessfully";
		public static final String ARTICLES_RETRIVE_SUCESSFUL = "Retrival of Articles are sucessful";
		public static final String NO_ARTCILES_FOUND = "No Articles Found";
		public static final String ARTICLE_FAILED = "Article Submission Failed";
		public static final String STORE_WEBDATA_SUCESS = "Article From Web Saved Sucessfully";
		public static final String WEBSITE_URL = "Web Site URL";
		public static final String NULL_ERROR_MSG = "cannot be Null";
		public static final String ARTICLE_NAME_EMPTY = "Article Name Cannot be Empty";

		/* Messages related to Stopwords */
		public static final String STOPWORD_REMOVE_FAILED = "Stopword Removal Failed";
		public static final String NO_STOPWORD_EXIST = "Stopword does not exist";
		public static final String STOPWORD_REMOVE_SUCESS = "Stopwords removed sucessfully";
		public static final String EMPTY_STOPWORD = "Stopword Cannot be Empty";
		public static final String STOPWORD_EXIST = "Stopword Already Exist";
		public static final String STOPWORD_ADD_FAILED = "Failed to Add Stop Word";
		public static final String STOPWORD_ADD_SUCESS = "Stop Word Added Sucessfully";
		public static final String EMPTY_STOPWORDS = "Stop Words are Empty";
		public static final String STOPWORD_SUCESS = "Retrival of Stop Words is sucessful";

		public static final String VIEW_ADMIN_SUCESS_PAGE = "adminsucess";
		public static final String VIEW_ADMIN_ERROR_PAGE = "adminerror";

		public static final String INTERNAL_ERROR = "Please Contact System Adminitrator. An Internal Error has Ocuurred";

		/* Data Cleaning and Tokenization Output */
		public static final String CLEANREVIEWS_SUCESS = "Clean of Articles is Sucessful";
		public static final String TOKENS_SUCESS = "Tokenization Process has been completed Sucessfully";
		public static final String CLEANREVIEWS_EMPTY = "Clean Articles are Empty";
		public static final String INSERT_TOKENS_FAILED = "Insertion of Tokens has Failed";
		public static final String EMPTY_TOKENS = "Tokens Are Empty";
		public static final String TOKENRETRIVAL_SUCESS = "Retrival of Tokens is Sucessful";
		public static final String EMPTY_REVIEWSLIST = "Empty Matrix has been Obtained";
		public static final String REVIEWS_FETCH_SUCESS = "Articles have been Fetched Sucessfully";
		public static final String CLEANMODEL_FAILED = "Cleaning of Articles has Failed";

		/* Key Phrase Messages */
		public static final String EMPTY_KEYPHRASE = "Key Phrase Cannot be Empty";
		public static final String KEYPHRASE_ADD_FAILED = "Key Phrase Could not be Added";
		public static final String KEYPHRASE_ADD_SUCESS = "Key Phrase Added Sucessfully";
		public static final String KEYPHRASE_EXIST = "Key Phrase Already exist";
		public static final String KEYPHRASE_RETRIVE_SUCESS = "Key Phrase Retrived Sucessfully";
		public static final String KEYPHRASE_REMOVE_SUCESS = "Key Phrase Removal is Sucessful";
		public static final String KEYPHRASE_REMOVE_FAILED = "Key Phrase Removal Failed";
		public static final String KEYPHRASE_NOT_EXIST = "Key Phrase Does not Exist";

		/* Phrase G */

		public static final String EMPTY_PHRASEG = "PhraseG Cannot be Empty";
		public static final String NOTYPE_TYPE_CANNOT_BE_SAME = "No Type and Type Cannot be Same";
		public static final String PHRASEG_ADD_FAILED = "Failed to Add Phrase G";
		public static final String PHRASEG_ADD_SUCESS = "PhraseG Added Sucessfully";
		public static final String PHRASEG_EXIST = "PhraseG Exist";
		public static final String PHRASEG_REMOVE_FAILED = "PhraseG Removal Failed";
		public static final String PHRASEG_REMOVE_SUCESS = "PhraseG Removal is sucessful";
		public static final String PHRASEG_NOT_EXIST = "PhraseG does not exist";
		public static final String PHRASEG_RETRIVE_SUCESS = "PhraseG Retrival Sucess";
		public static final String PHRASES_GENERATION_FAILED = "Phrases Generation Has Failed";
		public static final String PHRASES_GEN_SUCESS = "Phrases Generation is Sucessful";
		public static final String NO_CLEANARTICLES_FOUND = "No Clean Articles have been Found";

		/* Messages for Phrases */

		public static final String COULD_NOT_REMOVE_PHRASES = "Could not Remove Phrases";
		public static final String TOKENS_EMPTY = "No Tokens Found For Processing";
		public static final String STOPWORDS_EMPTY = "Stopwords Cannot be Empty";
		public static final String KEYPHRASES_EMPTY = "Key Phrases Cannot be Empty";
		public static final String PHRASEG_EMPTY = "Phrase G Cannot be Empty";
		public static final String NO_PHRASES_ARE_FOUND = "No Phrases are found";
		public static final String COULD_NOT_INSERT_PHRASES = "Could not Insert Phrases";
		public static final String EMPTY_PHRASES = "No Phrases Found";
		public static final String PHRASES_RETRIVAL_SUCESS = "Phrases Retrival is Sucessful";
		public static final String ADJECTIVE_GENERATION_FAILED = "Generation of Adjectives have Failed";
		public static final String ADJECTIVE_GEN_SUCESS = "Adjective Generation is Sucessful";
		public static final String REVIEW_LIST_EMPTY = "Review List is Empty";
		public static final String INSERTING_ADJECTIVES_FAILED = "Insertion of Adjectives have Failed";
		public static final String NO_ADJECTIVES_FOUND_IN_ARTICLE = "No Adjectives Found in Article";
		public static final String NO_KEYPHRASES_FOUND = "No Key Phrases Found";

		/* Strusture Messages */

		public static final String NO_STRUCTURE1_FOUND = "No Structure1 Keywords have been Found";
		public static final String STRUCTURE1_GENERATION_FAILED = "Structure1 generation Failed";
		public static final String STRUCTURE1_GEN_SUCESS = "Structure1 Generation is sucessful";
		public static final String NO_INSERTION_STRUCTURE1 = "Structure1 Could not be Inserted";
		public static final String STRUCTURE1_RETRIVAL_SUCESS = "Structure1 Retrival is Sucessful";
		public static final String EMPTY_STRUCTURE1 = "Structure1 is Empty";
		public static final String EMPTY_STRUCTURE2 = "Structure2 is Empty";
		public static final String EMPTY_STRUCTURE3 = "Structure3 is Empty";
		public static final String STRUCTURE2_RETRIVAL_SUCESS = "Structure2 Retrival is Sucessful";
		public static final String STRUCTURE3_RETRIVAL_SUCESS = "Structure3 Retrival is Sucessful";
		public static final String STRUCTURE2_GENERATION_FAILED = "Struture2 Generation Failed";
		public static final String STRUCTURE2_GEN_SUCESS = "Structure2 Generation is Sucessful";
		public static final String NO_INSERTION_STRUCTURE2 = "Struture2 Could not be Inserted";
		public static final String STRUCTURE3_GENERATION_FAILED = "Structure3 generation Failed";
		public static final String STRUCTURE3_GEN_SUCESS = "Structure3 Generation is Sucessful";
		public static final String DELETE_STRUCTURE2_FAILED = "Deletion of Struture2 has Failed";
		public static final String NO_INSERTION_STRUCTURE3 = "Insertion of Struture3 has Failed";
		public static final String NO_STRUCTURE3_FOUND = "Structure3 has Failed Sucessfully";
		public static final String DELETE_STRUCTURE3_FAILED = "Deletion of Structure3 Failed";
		public static final String NO_ADJECTIVES_FOUND = "No Adjectives Found";
		public static final String NO_STRUCTURE2_FOUND = "No Structure2 Found";
		public static final String NO_PHRASELIST_FOUND = "No Phrase List Found";
		public static final String FREQ_GENERATION_FAILED = "Frequency Generation has Failed";
		public static final String FREQ_GEN_SUCESS = "Frequency Generation is Sucessful";
		public static final String DELETE_FREQ_FAILED = "Deletion of Frequency has Failed";
		public static final String DELETE_TYPE_FAILED = "No Concepts Found";
		public static final String NO_REVIEWSMODEL_FOUND = "No Articles Found";
		public static final String RETRIVE_DIFFRENT_CONCEPTS_NONE = "Retrival of Concepts is Empty";
		public static final String RETRIVE_DIFFRENT_PHRASES_NONE = "Retrival of Diffrent Phrases is Empty";
		public static final String RETRIVE_DIFFRENT_ADJECTIVES_NONE = "Retrival of Diffrent Adjectives is Empty";
		public static final String RETRIVE_DIFFRENT_STRUCTURE1_NONE = "Retrival of Structure1 has Failed";
		public static final String RETRIVE_DIFFRENT_STRUCTURE2_NONE = "Retrival of Structure2 has Failed";
		public static final String RETRIVE_DIFFRENT_STRUCTURE3_NONE = "Retrival of Structure3 has Failed";

		/* Insert Freq Failed */
		public static final String INSERT_FREQ_FAILED = "Insertion of Frequency has Failed";
		public static final String EMPTY_ARTICLENAMES = "Article Names cannot be Empty";
		public static final String ARTICLENAMES_SUCESS = "Article Names retrival is Sucessful";
		public static final String EMPTY_COMPAREARTICLE_RESULT = "Comparision of Articles Could not be Done at this point";
		public static final String COMPARINGARTICLE_SUCESS = "Comparision of Articles is Sucessful";
		public static final String BOTH_ARTICLENAMES_CANNOT_BE_SAME = "Both the Articles are the Same";
		public static final String KEY_PHRASE_ARTICLE_NAME_EMPTY = "Key Phrase is Empty for Article Name = ";
		public static final String NO_TOKENS_FOR_ARTICLE_LEFT = "No Tokens have been Found for Article Name = ";
		public static final String NO_TOKENS_FOR_ARTICLE_RIGHT = "No Tokens Found for Article Right = ";
		public static final String NO_KEYPHRASES_FOR_LEFT_ARTICLE = "No Key Phrases for Left Article = ";
		public static final String NO_KEYPHRASES_FOR_RIGHT_ARTICLE = "No Key Phrases for Right Article = ";
		public static final String KEY_PHRASE_UNION_SET_EMPTY = "Key Pharse Union List is Empty";
		public static final String BOTH_ARTICLENAMES_ARE_SIMILAR = "Both Articles are Similar";
		public static final String BOTH_ARTICLENAMES_ARE_NOTSIMILAR = "Both Articles are not Similar";
		public static final String COULD_NOT_MEASURE_SIMILARITY = "Could not measure Similarity";
		
		String KEY_PHRASE_INTERSECTION_SET_EMPTY = "Key Phrase Intersection List is Empty";

	}

	interface Views {

		public static final String WEBDATA_SUBMIT_INPUT = "documentsubmission";
		public static final String SUCESS_PAGE = "sucess";

		public static final String STOPWORD_INPUT = "addStopword";
		public static final String REMOVESTOPWORD_INPUT = "removeStopword";

		public static final String VIEW_ADMIN_SUCESS_PAGE = "adminsucess";
		public static final String VIEW_ADMIN_ERROR_PAGE = "adminerror";

		/* Pages for KeyPhrase */

		public static final String KEYPHRASEADD_INPUT = "addkeyphrase";
		public static final String REMOVE_KEYPHRASE_INPUT = "removekeyphrase";

		/* Pages for PhraseG */

		public static final String PHRASEG_ADD_INPUT = "addPhraseG";
		public static final String REMOVE_PHRASEG_INPUT = "removePhraseG";

		/* Pages for Pharses Generation */

		public static final String FAILURE_PAGE = "error";

	}

	interface Keys {
		public static final String OBJ = "obj";
		public static final int ADMIN_TYPE = 5;
		public static final String STOPWORDS_SYMBOL = "[^a-zA-Z]+";
		public static final String SPACE = "  ";
	}

	interface DatabaseColumns {

		public static final String REVIEWID_COL = "REVIEWID";

		public static final String REVIEWDETAILS_COL = "REVIEWDETAILS";

		public static final String STOPWORDID_COL = "STOPWORDID";

		public static final String STOPWORD_COL = "STOPWORD";

		public static final String CLEANID_COL = "CLEANID";

		public static final String TOKENID_COL = "TOKENID";

		public static final String TOKENNAME_COL = "TOKENNAME";

		public static final String FREQID_COL = "FREQID";

		public static final String FREQ_COL = "FREQ";

		public static final String ARTICLENAME_COL = "ARTICLENAME";

		public static final String TYPE_COL = "TYPE";

		public static final String KEYPHARSE_COL = "KEYPHARSE";

		public static final String KEYPHRASEID_COL = "KEYPHARSEID";

		public static final String PHRASE_COL = "PHRASE";

		public static final String PHRASEID_COL = "PHRASEID";

		public static final String ADJECTIVE_COL = "ADJECTIVE";

		public static final String ADJECTIVEID_COL = "ADJECTIVEID";

		public static final String STRUCTUREID_COL = "STRUCTUREID";

		public static final String STRUCTURE_COL = "STRUCTURE";

		String CLEANARTICLE_COL = "CLEANARTICLE";

		/* PhraseG */

		String PHARSEGID_COL = "PHARSEGID";
		String PHASEG_COL = "PHASEG";
		String NOTTYPE_COL = "NOTYPE";

		/* Columns for Freq Computation */

		String SENTENCEID_COL = "SENTENCEID";
		String FREQKEYPHARSE_COL = "FREQKEYPHARSE";
		String FREQPHRASE_COL = "FREQPHRASE";
		String FREQSTRUCTURE3_COL = "FREQSTRUCTURE3";
		String FREQADJECTIVE_COL = "FREQADJECTIVE";
		String FREQPHRASEG_COL = "FREQPHRASEG";
		String FREQSTRUCTURE1_COL = "FREQSTRUCTURE1";
		String FREQSTRUCTURE2_COL = "FREQSTRUCTURE2";

	}

	interface DatabaseColumnsKeys {

		String ARTICLENAME_KEY = "ARTICLENAME";
		String CLEANARTICLE_KEY = "CLEANARTICLE";
		String TOKENNAME_KEY = "TOKENNAME";
		String KEYPHARSE_KEY = "KEYPHARSE";
		String TYPE_KEY = "TYPE";

		/* PhraseG */

		String PHARSEGID_KEY = "PHARSEGID";
		String PHASEG_KEY = "PHASEG";
		String NOTTYPE_KEY = "NOTYPE";

		/* Columns for Freq Computation KEy*/

		String SENTENCEID_KEY = "SENTENCEID";
		String FREQKEYPHARSE_KEY = "FREQKEYPHARSE";
		String FREQPHRASE_KEY = "FREQPHRASE";
		String FREQSTRUCTURE3_KEY = "FREQSTRUCTURE3";
		String FREQADJECTIVE_KEY = "FREQADJECTIVE";
		String FREQPHRASEG_KEY = "FREQPHRASEG";
		String FREQSTRUCTURE1_KEY = "FREQSTRUCTURE1";
		String FREQSTRUCTURE2_KEY = "FREQSTRUCTURE2";
		/* Phrase */

		String PHRASE_KEY = "PHRASE";
		String ADJECTIVE_KEY = "ADJECTIVE";

		/* Struture1, Structure2 and Structure3 */

		String STRUCTURE_KEY = "STRUCTURE";
		String KEYPHARSELIST_KEY = "KEYPHARSELIST";

	}

}
