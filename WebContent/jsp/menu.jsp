<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="<%=request.getContextPath()%>/css/styles.css"
	rel="stylesheet" type="text/css">
</head>
<body>
	<div id='cssmenu'>
		<ul>
			<li class='active '><a
				href="<%=request.getContextPath()%>/jsp/welcome.jsp"><span>Home</span></a></li>
			<li class='has-sub '><a href='#'>Articles<span></span></a>
				<ul>
					<li><a
						href='<%=request.getContextPath()%>/jsp/reviewsubmission.jsp'><span>
								Article Submission</span></a></li>
					<li><a
						href='<%=request.getContextPath()%>/jsp/viewreviews.jsp'><span>
								Article Collection </span></a></li>

					<li><a
						href='<%=request.getContextPath()%>/jsp/documentsubmission.jsp'><span>
								Article Online </span></a></li>
				</ul></li>

			<li class='has-sub '><a href='#'>Stopword Analysis<span></span></a>
				<ul>
					<li class='active '><a
						href="<%=request.getContextPath()%>/jsp/addStopword.jsp"><span>Add
								Stopword</span></a></li>
					<li class='active '><a
						href="<%=request.getContextPath()%>/jsp/viewstopwords.jsp"><span>View
								Stopword</span></a></li>
					<li class='active '><a
						href="<%=request.getContextPath()%>/jsp/removeStopword.jsp"><span>Remove
								Stopword</span></a></li>
				</ul></li>

			<li class='has-sub '><a href='#'>Cleaning and Tokens<span></span></a>
				<ul>
					<li class='has-sub '><a
						href="<%=request.getContextPath()%>/jsp/datacleaning.jsp"><span>Clean
								Data</span></a></li>
					<li class='has-sub '><a
						href="<%=request.getContextPath()%>/jsp/viewcleanreviews.jsp"><span>View
								Clean Data</span></a></li>
					<li class='has-sub '><a
						href="<%=request.getContextPath()%>/jsp/tokenization.jsp"><span>Tokenization</span></a></li>
					<li class='has-sub '><a
						href="<%=request.getContextPath()%>/jsp/viewtokens.jsp"><span>View
								Tokens</span></a></li>
				</ul></li>

			<li class='has-sub '><a href='#'>KeyPharse<span></span></a>
				<ul>
					<li class='active '><a
						href="<%=request.getContextPath()%>/jsp/addkeyphrase.jsp"><span>Add
								Key Phrase</span></a></li>
					<li class='active '><a
						href="<%=request.getContextPath()%>/jsp/viewkeyphrases.jsp"><span>
								View Key Phrase</span></a></li>
					<li class='active '><a
						href="<%=request.getContextPath()%>/jsp/removekeyphrase.jsp"><span>Remove
								Key Phrase</span></a></li>
				</ul></li>

			<li class='has-sub '><a href='#'>PhraseG<span></span></a>
				<ul>
					<li class='active '><a
						href="<%=request.getContextPath()%>/jsp/addPhraseG.jsp"><span>Add
								PhraseG</span></a></li>
					<li class='active '><a
						href="<%=request.getContextPath()%>/jsp/viewPhraseG.jsp"><span>
								View PhraseG</span></a></li>
					<li class='active '><a
						href="<%=request.getContextPath()%>/jsp/removePhraseG.jsp"><span>Remove
								PhraseG</span></a></li>
				</ul></li>

			<li class='has-sub '><a href='#'>Generate Phrases<span></span></a>
				<ul>
					<li class='active '><a
						href="<%=request.getContextPath()%>/review/genPhrases.do"><span>Generate
								Phrases </span></a></li>
					<li class='active '><a
						href="<%=request.getContextPath()%>/jsp/viewPhrases.jsp"><span>
								View Phrases</span></a></li>

					<li class='active '><a
						href="<%=request.getContextPath()%>/review/genAdjective.do"><span>Generate
								Adjective</span></a></li>

					<li class='active '><a
						href="<%=request.getContextPath()%>/jsp/viewAdjectives.jsp"><span>
								View Adjectives</span></a></li>

				</ul></li>

			<li class='has-sub '><a href='#'>Generate Structures<span></span></a>
				<ul>
					<li class='active '><a
						href="<%=request.getContextPath()%>/review/genStructure1.do"><span>Generate
								Structure1 </span></a></li>
					<li class='active '><a
						href="<%=request.getContextPath()%>/jsp/viewStructure1.jsp"><span>
								View Structure1</span></a></li>
					<li class='active '><a
						href="<%=request.getContextPath()%>/review/genStructure2.do"><span>Generate
								Structure2 </span></a></li>
					<li class='active '><a
						href="<%=request.getContextPath()%>/jsp/viewStructure2.jsp"><span>
								View Structure2</span></a></li>
					<li class='active '><a
						href="<%=request.getContextPath()%>/review/genStructure3.do"><span>Generate
								Structure3 </span></a></li>
					<li class='active '><a
						href="<%=request.getContextPath()%>/jsp/viewStructure3.jsp"><span>
								View Structure3</span></a></li>
				</ul></li>
				
				<li class='has-sub '><a href='#'><span>View NLP Adjectives</span></a>
				<ul>
				<li class='active '><a
						href="<%=request.getContextPath()%>/jsp/viewnlpadjectives.jsp"><span>
								View NLP Adjectives</span></a></li>
				</ul></li>


			<li class='has-sub '><a href='#'>Frequency Computation<span></span></a>
				<ul>
					<li class='active '><a
						href="<%=request.getContextPath()%>/review/genFreq.do"><span>DO
								Frequency </span></a></li>
					<li class='active '><a
						href="<%=request.getContextPath()%>/jsp/viewFreqComputation.jsp"><span>
								View Freq</span></a></li>
				</ul></li>
				
				<li class='has-sub '><a href='#'><span>Compare Articles</span></a>
				<ul>
				<li class='active '><a
						href="<%=request.getContextPath()%>/jsp/articleSimilar.jsp"><span>
								Article Similarity Algorithm</span></a></li>
				</ul></li>
		</ul>





	</div>
</body>
</html>