<%@page import="com.constants.ReviewConstantsIF"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="org.springframework.web.servlet.ModelAndView"%>
<%@page import="com.model.AJAXResponse"%>
<%@page import="com.model.Message"%>
<%@page import="java.util.List"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Online Submission</title>

<script type="text/javascript">

validateWebUrl=function()
{
	var webUrl1=document.getElementById('webUrl').value;

	if(webUrl1<=0)
	{
		alert('Please Enter a Value for Website Url');
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
	AJAXResponse ajaxResponse=(AJAXResponse) request.getAttribute(ReviewConstantsIF.Keys.OBJ);
if(null==ajaxResponse)
{
	
}
else
{
	boolean status=ajaxResponse.isStatus();
%>
<%
	if(!status)
	{
		List<Message> msg=ajaxResponse.getEbErrors();
%>
<%
		for(int i=0;i<msg.size();i++)
		{
			Message tempMsg=msg.get(i);
	%>
	
	<div class="errMsg"><%= tempMsg.getMsg()%></div>
	
<%		}
	
	}
}
%>


<form action="<%=request.getContextPath()%>/review/webSubmission.do" method="post"  >
	<table>
		<tr>
			<td><label>Web Site Url:</label></td>
			<td><input name="webUrl" id="webUrl" class="text" type="text"  size="100" maxlength="100" /></td>
		</tr>
		<tr>
			<td><label>Article Name:</label></td>
			<td><input name="articleName" id="articleName" class="text" type="text"  size="100" maxlength="100" /></td>
		</tr>
		<tr>
			<td><input type="submit" class="btn" value="Submit" onclick="validateWebUrl()" /></td>
		</tr>
	</table>
</form>
</body>
</html>