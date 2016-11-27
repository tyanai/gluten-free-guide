<%@ page language="java" 
         import="org.celiac.api.GlutenFreeAPI, org.celiac.web.bean.*, java.util.*" 
         errorPage="loginError.jsp" %>
<jsp:useBean id="User" scope="session" class="org.celiac.web.bean.User">
  <jsp:setProperty name="User" property="*"/>
</jsp:useBean>
<%@ include file="../include/header.jsp" %>    


<head>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!--<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=ISO-8859-1">-->
<link href="../include/theStyle.css" rel="stylesheet" type="text/css">
</head>


	

<%
request.setCharacterEncoding("utf-8");
 response.setContentType("text/html;charset=UTF-8");




String sms = "";
String smsMessage = "";

if (request.getParameter("smsMessage") != null){
		sms = (String)request.getParameter("smsMessage");
		
}





StringBuffer sb = new StringBuffer();



if (!sms.equals("")) {
String empty = "";

		if ((sms == null) || empty.equals(sms)) {
			sms = "<em>(no sms specified)</em>";
		}
		
		sb.append("<html><p align=right>");
		try {
			String theOutput = null;
			
			theOutput = new GlutenFreeAPI().search(sms, User.getUser().getUSER_ID(), true);

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
      link="#3366cc" vlink="#9999cc" alink="#0000cc">



<table WIDTH="500" height="90%" border="0" cellpadding="5">
	<tr valign="top">
		<td>
		<table width="100%"  height="90%" border="1">
		<td valign="top" nowrap bgcolor="#f5f5e1" class="textSmall">
		<%@ include file="../include/upperMenu.jsp" %>
		</td>
		</table>
</td>
		<td WIDTH="500" >
			<table border="0"   cellpadding="1" cellspacing="1" class="frameTableT">
			
			
			<form name="query_form1" action="cellSimulator.jsp" method="POST">
				
					
    <tr><td colspan="1" nowrap WIDTH="500" class="headerUnderLineHeb">:<%=org.celiac.util.TemplateReader.getHebrewMapWord("FREE_TEXT")%></td></tr>
   
    

        
        
<tr><td>
        
        
<TABLE name=selectTable BORDER="3" BORDERCOLOR="#D8D8D8" BGCOLOR="#FFFFFF" style="width: 100%" CELLSPACING="1" CELLPADDING="3">

<TR ALIGN="CENTER">
<%
String tmpChoise=sms;
if (tmpChoise == null) tmpChoise ="";


%>
<TD  colspan="1"  style="width: 100%" class="headerUnderLine3Heb"><input type="text" style="width: 100%" class="textfieldHeb" name="smsMessage" value="<%=tmpChoise%>" ></TD>


</TR>


</TABLE>
  </tr></td>     
        
  <tr><td>
          <input type="submit"  name="Submit" value="<%=org.celiac.util.TemplateReader.getHebrewMapWord("HAMSHECH")%>" style="width: 100%" class="button" enabled onMouseOver="this.style.background =  '#d5e0e6';" onMouseOut="this.style.background = '#e4e4e4';" >
          <br>
          </font></div>
        </td>
      </tr> 
      
    </td>
  </tr>
  </tr>
   <tr>
  	<td>
  	<br>
  </td>
	</tr>
	</form>
	
	
			
			<%if (!sms.equals("")){ %>
			<tr>
				<td>
					
			<table width="100%" border="1" CELLSPACING="1" CELLPADDING="3">
			
      		
      		<tr><td colspan="1"  style="width: 100%" class="headerUnderLine2Heb"><%=sb%></td></tr>
      		
      		
  		
  		</table>
  	</td>
  </tr>
  <%}%>
  	</table>
			<br>
				</td>
	</tr>
</table>
	






<%@ include file="../include/buttom.jsp" %>    