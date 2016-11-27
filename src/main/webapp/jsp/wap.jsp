<%@ page language="java" 
         import="org.celiac.api.GlutenFreeAPI, org.celiac.web.bean.*, java.util.*" 
         errorPage="loginError.jsp" %>
<jsp:useBean id="User" scope="session" class="org.celiac.web.bean.User">
  <jsp:setProperty name="User" property="*"/>
</jsp:useBean>
<html DIR="RTL">
<head>
<%
try{
if (User.getUser() == null) throw new Exception("User was not login. Please login.");
%>
<%@ page contentType="text/html; charset=UTF-8"%>
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="REFRESH" CONTENT="600">
<link href="../include/theStyle.css" rel="stylesheet" type="text/css">
<link rel="shortcut icon" href="../images/favicon.ico" >
<title>GF-Guide</title>
</head>


<head>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!--<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=ISO-8859-1">-->
<link href="../include/theStyle.css" rel="stylesheet" type="text/css">
</head>


<%
request.setCharacterEncoding("utf-8");
 response.setContentType("text/html;charset=UTF-8");

String sms = "";

if (request.getParameter("smsMessage") != null){
		sms = (String)request.getParameter("smsMessage");
		
}



%>

<%
StringBuffer sb = new StringBuffer();
if (!sms.equals("")) {
String empty = "";

		if ((sms == null) || empty.equals(sms)) {
			sms = "<em>(no sms specified)</em>";
		}
		
		sb.append("<html><p align=right>");
		try {
			String theOutput = null;
			
			theOutput = new GlutenFreeAPI().search(sms, "FROM-CELL", true);

			if (theOutput != null) {
				int totalCharacters = theOutput.length();
				theOutput = theOutput.replaceAll("\n", "<br>");
				
			} else {
				theOutput = "No Match Found";
			}
			sb.append(theOutput);
		} catch (Exception e) {
			e.printStackTrace();
		}
		sb.append("<br>");
		sb.append(" ");
		sb.append("</p></html>");
		}
%>



<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 3.2//EN">
<html>
<head>
<!--<meta http-equiv="Content-Type" content="text/html; charset=windows-1255">-->
<link href="/include/theStyle.css" rel="stylesheet" type="text/css">
<title>Gluten Free Search Engine</title>
</head>

<body bgcolor="#ffffff" LEFTMARGIN="10" RIGHTMARGIN="10"
      link="#3366cc" vlink="#9999cc" alink="#0000cc"
      onload="document.sim_form.smsMessage.focus()" > 



<table width="100%" border="0" cellpadding="5">
	<tr valign="top">
		
		<td width="100%" >
			<table width="100%" border="0"   cellpadding="1" cellspacing="1" class="frameTableT">
			
			
			<form width="100%" name="sim_form" action="wap.jsp" method="POST">
					
					<tr><td colspan="1" nowrap class="headerHeb"><%=User.getUser().getFIRST_NAME() + " " + User.getUser().getLAST_NAME()%> <%=org.celiac.util.TemplateReader.getHebrewMapWord("SHALOM")%></td></tr>
					
			
    <!--<tr><td colspan="1" nowrap style="width: 100%" class="headerUnderLine">Input:</td></tr>-->
    
    
    <td colspan="0" >
        <div align="center">
        <font face="Verdana, Arial, Helvetica, sans-serif">
        	 <tr>
          <td>
          	    <%
String tmpChoise=sms;
if (tmpChoise == null) tmpChoise ="";


%>
          <input type="text" style="width: 100%" class="textfieldHeb" name="smsMessage" value="<%=tmpChoise%>">
        </td>
      </tr>
  
          <tr>
          <td>
          <input type="submit"  style="width: 100%" name="Submit" value="<%=org.celiac.util.TemplateReader.getHebrewMapWord("HAMSHECH")%>" style="width: 100%" class="button" enabled >
          </font></div>
        </td>
      </tr>
      
    </td>
  </tr>
  </tr>
</form>
			</table>
			<%if (!sms.equals("")){ %>
			<table width="100%" border="0" cellpadding="5">
				<tr>
      	<td>
      		
      		<tr><td colspan="1"  style="width: 100%" class="headerUnderLine2Heb"><%=sb%></td></tr>
      		
      		
      		</td>
  		</tr>
  		<%}%>
  	</table>
			<br>
		</td>
	</tr>
</table>





</table>


</body>

</html>

<%
}catch (Exception e){

	String theErrorOut = e.getMessage();
	session.setAttribute("gfsmsLoginError",theErrorOut);
	response.sendRedirect("../index.jsp");
}
%>