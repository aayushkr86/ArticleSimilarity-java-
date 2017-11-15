<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="org.springframework.web.servlet.ModelAndView"%>
<%@page import="java.util.List"%>
<%@page import="com.model.AJAXResponse"%>
<%@page import="com.model.Message"%>

<%@page import="com.constants.ReviewConstantsIF"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Key Phrase</title>
<script type="text/javascript">
validateKeyPhrase = function() {
		var stopWord = document.getElementById('keyphrase').value;

		if (stopWord <= 0) {
			alert("Please Enter a Value for Keyphrase");
			return false;
		}
	}
</script>
</head>
<body>
<body background="<%=request.getContextPath()%>/images/background.jpg">
	<jsp:include page="/jsp/customer.jsp"></jsp:include>
	<jsp:include page="/jsp/menu.jsp"></jsp:include>

	<%
		AJAXResponse ajaxResponse = (AJAXResponse) request
				.getAttribute(ReviewConstantsIF.Keys.OBJ);
		if (null == ajaxResponse) {

		} else {
			boolean status = ajaxResponse.isStatus();
	%>
	<%
		if (!status) {
				List<Message> msg = ajaxResponse.getEbErrors();
	%>
	<%
		for (int i = 0; i < msg.size(); i++) {
					Message tempMsg = msg.get(i);
	%>

	<div class="errMsg"><%=tempMsg.getMsg()%></div>

	<%
		}

			}
		}
	%>


	<form action="<%=request.getContextPath()%>/review/addKeyPhrase.do"
		method="post">


		<table>
			<tr>
				<td><label>Enter Key Phrase:</label></td>
				<td><input name="keyphrase" id="keyphrase" type="text"
					size="50" maxlength="50" /></td>
			</tr>
			<tr>
				<td><label>Select Concept:</label></td>
				<td><select name="type">
						<option value="mobile">Mobile</option>
						<option value="tablet">Tablet</option>
						<option value="telivision">Television</option>
				</select></td>
			</tr>
			<tr>
				<td><input type="submit" value="Add Key Phrase"
					onclick="validateKeyPhrase()" /></td>
			</tr>

		</table>
	</form>
</body>
</html>