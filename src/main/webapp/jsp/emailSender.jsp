<%@ page language="java" 
         import="org.celiac.api.GlutenFreeAPI, org.celiac.web.bean.*,org.celiac.datatype.*, java.util.*" 
         errorPage="loginError.jsp" %>
<jsp:useBean id="User" scope="session" class="org.celiac.web.bean.User">
  <jsp:setProperty name="User" property="*"/>
</jsp:useBean>
<%@ include file="../include/header.jsp" %>    
<% if (!(User.getUser().getPERMISSIONS().equals("admin")) && !(User.getUser().getPERMISSIONS().equals("mitnadev"))) throw new Exception("User was not login. Please login."); %>

<head>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!--<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=ISO-8859-1">-->
<link href="../include/theStyle.css" rel="stylesheet" type="text/css">

<script type="text/javascript" src="../ckeditor/ckeditor.js"></script>



</head>



<%



request.setCharacterEncoding("utf-8");
 response.setContentType("text/html;charset=UTF-8");
 GFUser aUser = (GFUser)session.getAttribute("A_USER");
 
 

String errorMessage = (String)session.getAttribute("gfsmsEmailSender");
if (errorMessage == null) errorMessage = "";

session.setAttribute("gfsmsEmailSender",null);


%>



<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 3.2//EN">
<html>
<head>
<!--<meta http-equiv="Content-Type" content="text/html; charset=windows-1255">-->
<link href="/include/theStyle.css" rel="stylesheet" type="text/css">
<meta name="Description" content="" />
<meta name="Keywords" content="" />
<script type="text/javascript" src="../include/ezcalendar.js"></script>
<link rel="stylesheet" type="text/css" href="../include/ezcalendar.css" />
<title>Gluten Free Search Engine</title>
</head>

<body bgcolor="#ffffff" LEFTMARGIN="10" RIGHTMARGIN="10"
      link="#3366cc" vlink="#9999cc" alink="#0000cc">

<%


String action = null;
if (request.getParameter("choise") != null){
		action = request.getParameter("choise");
		
}

%>



<table WIDTH="100%" height="90%" border="0padding="5">
	<tr valign="top">
		<td>
		<table width="100%" height="90%" border="1">
		<td valign="top" nowrap bgcolor="#f5f5e1" class="textSmall">
		<%@ include file="../include/upperMenu.jsp" %>
		</td>
		</table>
</td>
<td></td>
		<td WIDTH="100%" >
			<table border="0"  WIDTH="100%"  cellpadding="1" cellspacing="1" class="frameTableT">
		
			
		    
        
<tr><td>
        
<TABLE >     
<tr style="width: 100%"><td colspan="1" nowrap style="width: 100%" class="headerUnderLineHeb">שליחת אימייל - ממשק כללי</td></tr>



</TABLE>
  
  </tr>
</td>
   <tr>
  	<td>
  	<br>
  </td>
	</tr>
	

	
	
	<%if (!("").equals(errorMessage)){%>
			<tr><td align="right">	
			<font color=#ff0000><b><%=errorMessage%><b></font>
			<br>
			<br>
		</td></tr>
		 <%}%>
	
	
	
 
	
	
	
	
	<%
	 if (true){
	 		Set<GFUser> theAllUsers = org.celiac.util.database.DBQueryFactory.getDBHandler().getAllUserDetails();
	 		String BODY = (String)session.getAttribute("BODY");
	 		String SUBJECT = (String)session.getAttribute("SUBJECT");
	 		String TO_EMAIL = (String)session.getAttribute("TO_EMAIL");
	 		String BCC_EMAIL = (String)session.getAttribute("BCC_EMAIL");
	 		if (SUBJECT == null) SUBJECT = "";
	 		if (BODY == null) BODY = "";
	 		if (BCC_EMAIL == null) BCC_EMAIL = "";
	 		if (TO_EMAIL == null) TO_EMAIL = "";
	 		session.setAttribute("BODY",null);
	 		session.setAttribute("SUBJECT",null);
	 		session.setAttribute("TO_EMAIL",null);
	 		session.setAttribute("BCC_EMAIL",null);
	 		
	 %>
	 <form name="manage_email" action="emailSender2.jsp" onSubmit="onPost();" method="POST">
	
	  <tr>
  
  	 <td width="100%"  >
  		<TABLE name=selectTable BORDER="2" BORDERCOLOR="#D8D8D8" BGCOLOR="#FFFFFF" style="width: 100%" CELLSPACING="1" CELLPADDING="3">	
  		<TH  colspan="1"  style="width: 20%" ><input type="text" style="width: 100%" class="textBoxB" name="TO_EMAIL" value="<%=TO_EMAIL%>"></TH>
  			<TH  colspan="1"  style="width: 4%" class="headerUnderLine4">To:</TH>	
				
																						
		</TABLE>
  	</td></tr>
  	
  	<tr>
  
  	 <td width="100%"  >
  		<TABLE name=selectTable BORDER="2" BORDERCOLOR="#D8D8D8" BGCOLOR="#FFFFFF" style="width: 100%" CELLSPACING="1" CELLPADDING="3">	
  		<TH  colspan="1"  style="width: 20%" ><input type="text" style="width: 100%" class="textBoxB" name="BCC_EMAIL" value="<%=BCC_EMAIL%>"></TH>
  			<TH  colspan="1"  style="width: 4%" class="headerUnderLine4">Bcc:</TH>	
				
																						
		</TABLE>
  	</td></tr> 
  	
  	
  	<tr>
  
  	 <td align="center" width="100%"  >
  		<TABLE  name=selectTable BORDER="2" BORDERCOLOR="#D8D8D8" BGCOLOR="#FFFFFF" style="width: 100%" CELLSPACING="1" CELLPADDING="3">	
  		
  		<tr>
  		<td width="100" colspan="1" nowrap class="headerUnderLine2Heb">חברי GFGGuide בתוקף:</td>
  		<td width="100" colspan="1" nowrap class="headerUnderLine2Heb">חברי GFGGuide  שאינם בתוקף:</td>
  		<td width="100" colspan="1" nowrap class="headerUnderLine2Heb">כלל חברי העמותה שאינם ב GFGuide:</td>
  		<td width="100" colspan="1" nowrap class="headerUnderLine2Heb">כלל רשומי העמותה שאינם ב GFGuide ואינם חברים בתוקף:</td>
  		</tr>
  		<tr>
  		<td align="center" width="100" colspan="1" nowrap class="headerUnderLine2"><input type="checkbox" style="width: 100" class="textBoxB" name="1e" value="1e"  /></td>
  		<td align="center" width="100" colspan="1" nowrap class="headerUnderLine2"><input type="checkbox" style="width: 100" class="textBoxB" name="2e" value="2e"  /></td>
  		<td align="center" width="100" colspan="1" nowrap class="headerUnderLine2"><input type="checkbox" style="width: 100" class="textBoxB" name="3e" value="3e"  /></td>
  		<td align="center" width="100" colspan="1" nowrap class="headerUnderLine2"><input type="checkbox" style="width: 100" class="textBoxB" name="4e" value="4e"  /></td>
  		</tr>
  				
				
																						
		</TABLE>
  	</td></tr> 
  	
	    <tr>
  
  	 <td width="100%"  >
  		<TABLE name=selectTable BORDER="2" BORDERCOLOR="#D8D8D8" BGCOLOR="#FFFFFF" style="width: 100%" CELLSPACING="1" CELLPADDING="3">
  			<TH  colspan="1"  style="width: 4%" class="headerUnderLine4"><%=org.celiac.util.TemplateReader.getHebrewMapWord("SUBJECT")%>:</TH>		
  		<TH  colspan="1"  style="width: 20%" ><input type="text" style="width: 100%" class="textBoxBHebForEmail" name="SUBJECT" value="<%=SUBJECT%>"></TH>
  		
				
																						
		</TABLE>
  	</td></tr>
  	
  	
  	 <tr>
  
  	 <td width="100%" >
  		<TABLE name=selectTable BORDER="2" BORDERCOLOR="#D8D8D8" BGCOLOR="#FFFFFF" style="width: 100%" CELLSPACING="1" CELLPADDING="3">
  		<tr><td>
  			<p>
  		<textarea cols="80" id="userNote" name="userNote" rows="30"><%=BODY%></textarea>

			<script type="text/javascript">

		

				CKEDITOR.replace( 'userNote' );



		

			</script>
		</p>
  	</td></tr>													
		</TABLE>
  	</td></tr>
  	<tr><td >
  	<table width="100%" border="0" cellpadding="1">
				<tr>
					  <td><input name="submit" type="submit" id="submit" value="<%=org.celiac.util.TemplateReader.getHebrewMapWord("SEND_EMAIL")%>"  style="width: 100%" class="button" enabled onMouseOver="this.style.background =  '#d5e0e6';" onMouseOut="this.style.background = '#e4e4e4';"></td>
				</tr>
			</table>
		</td></tr>
	 </form>
	 <%}%>	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

  	</table>
			<br>
		</td>
	</tr>
</table>





<%@ include file="../include/buttom.jsp" %>    