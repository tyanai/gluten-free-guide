<%@ page language="java" 
         import="org.celiac.api.GlutenFreeAPI, org.celiac.web.bean.*,org.celiac.datatype.*, java.util.*" 
         errorPage="loginError.jsp" %>
<jsp:useBean id="User" scope="session" class="org.celiac.web.bean.User">
  <jsp:setProperty name="User" property="*"/>
</jsp:useBean>
<%User.setTempUser();%>
<%@ include file="../include/header.jsp" %>  

<SCRIPT LANGUAGE="JavaScript">		

	function processBack()
	{
		alert("GGG");
	  window.history.back(1);	  
	}
	
	



</script>

<body bgcolor="#ffffff" LEFTMARGIN="10" RIGHTMARGIN="10"
      link="#3366cc" vlink="#9999cc" alink="#0000cc">
      
<p align="center">
		
<table bgcolor= "#f5f5e1" width="50%" border="1">
<td valign="center"><img src="../images/celiac_logo.jpg"></td>
		
<td>
<%

String FIRST_NAME = (String)session.getAttribute("FIRST_NAME");
 

String LAST_NAME = (String)session.getAttribute("LAST_NAME");

String HISHUR_PASSWORD = (String)session.getAttribute("HISHUR_PASSWORD");

String PASSWORD = (String)session.getAttribute("PASSWORD");

String USER_ID = (String)session.getAttribute("USER_ID");

String CELL_NUM = (String)session.getAttribute("CELL_NUM");

String EMAIL = (String)session.getAttribute("EMAIL");

String EMAIL_ISHUR = (String)session.getAttribute("EMAIL_ISHUR");

String CELIAC_MEMBER_ID = (String)session.getAttribute("CELIAC_MEMBER_ID");

String USER_TZ = (String)session.getAttribute("USER_TZ");



try{

if (FIRST_NAME == null || ("").equals(FIRST_NAME)) {
   throw new Exception(org.celiac.util.TemplateReader.getHebrewMapWord("ANA_SAPEK") + " " + org.celiac.util.TemplateReader.getHebrewMapWord("FIRST_NAME") );
}

if (LAST_NAME == null || ("").equals(LAST_NAME)) {
   throw new Exception(org.celiac.util.TemplateReader.getHebrewMapWord("ANA_SAPEK") + " " + org.celiac.util.TemplateReader.getHebrewMapWord("LAST_NAME") ); 
}

if (HISHUR_PASSWORD == null || ("").equals(HISHUR_PASSWORD)) {
   throw new Exception(org.celiac.util.TemplateReader.getHebrewMapWord("ANA_SAPEK") + " " + org.celiac.util.TemplateReader.getHebrewMapWord("HISHUR_PASSWORD") ); 
}
if (PASSWORD == null || ("").equals(PASSWORD)) {
   throw new Exception(org.celiac.util.TemplateReader.getHebrewMapWord("ANA_SAPEK") + " " + org.celiac.util.TemplateReader.getHebrewMapWord("PASSWORD") ); 
}
if (USER_ID == null || ("").equals(USER_ID)) {
   throw new Exception(org.celiac.util.TemplateReader.getHebrewMapWord("ANA_SAPEK") + " " + org.celiac.util.TemplateReader.getHebrewMapWord("USER_ID") ); 
}
if (CELL_NUM == null || ("").equals(CELL_NUM)) {
   throw new Exception(org.celiac.util.TemplateReader.getHebrewMapWord("ANA_SAPEK") + " " + org.celiac.util.TemplateReader.getHebrewMapWord("CELL_NUM") ); 
}
if (EMAIL == null || ("").equals(EMAIL)) {
   throw new Exception(org.celiac.util.TemplateReader.getHebrewMapWord("ANA_SAPEK") + " " + org.celiac.util.TemplateReader.getHebrewMapWord("EMAIL") ); 
}

if (EMAIL_ISHUR == null || ("").equals(EMAIL_ISHUR)) {
   throw new Exception(org.celiac.util.TemplateReader.getHebrewMapWord("ANA_SAPEK") + " " + org.celiac.util.TemplateReader.getHebrewMapWord("HASHER") + " "  + org.celiac.util.TemplateReader.getHebrewMapWord("EMAIL") ); 
}

if (CELIAC_MEMBER_ID == null || ("").equals(CELIAC_MEMBER_ID)) {
   throw new Exception(org.celiac.util.TemplateReader.getHebrewMapWord("ANA_SAPEK") + " " + org.celiac.util.TemplateReader.getHebrewMapWord("CELIAC_MEMBER_ID") );
}

if (USER_TZ == null || ("").equals(USER_TZ)) {
   throw new Exception(org.celiac.util.TemplateReader.getHebrewMapWord("ANA_SAPEK") + " " + org.celiac.util.TemplateReader.getHebrewMapWord("USER_TZ") );
}

GFUser gfUser = new GFUser();
gfUser.setFIRST_NAME(FIRST_NAME.trim());
gfUser.setLAST_NAME(LAST_NAME.trim());
gfUser.setPASSWORD(PASSWORD.trim());
gfUser.setUSER_ID(USER_ID.trim());
gfUser.setCELL_NUM(CELL_NUM.trim());
gfUser.setEMAIL(EMAIL.trim());
gfUser.setCELIAC_MEMBER_ID(CELIAC_MEMBER_ID.trim());
gfUser.setUSER_TZ(USER_TZ.trim());
gfUser.setACKNOWLEDGE_USE("TRUE");
gfUser.setPERMISSIONS("sim");
gfUser.setLAST_LOGIN_DATE(new java.util.Date());
gfUser.setEFFECTIVE_DATE(new java.util.Date());
gfUser.setEXPIRATION_DATE((java.util.Date)session.getAttribute("EXPIRATION_DATE"));
gfUser.setDID_BUY_THE_BOOK((String)session.getAttribute("DID_BUY_THE_BOOK"));
User.updateUser(gfUser);
User.sendWelcomeLetter(gfUser);
User.loadUser(USER_ID,PASSWORD);
    session.setAttribute("FIRST_NAME",null);
		session.setAttribute("LAST_NAME",null);
		session.setAttribute("PASSWORD",null);
		session.setAttribute("USER_ID",null);
		session.setAttribute("CELL_NUM",null);
		session.setAttribute("EMAIL",null);
		session.setAttribute("EXPIRATION_DATE",null);
    session.setAttribute("DID_BUY_THE_BOOK",null);
    session.setAttribute("CELIAC_MEMBER_ID",null);
    session.setAttribute("USER_TZ",null);
    session.setAttribute("userAlreadySaved",null);



String errorMessage = (String)session.getAttribute("gfsmsNewUserError");
if (errorMessage == null) errorMessage = "";

session.setAttribute("gfsmsNewUserError",null);

%>

	<table width="100%" border="0" cellpadding="5">
	<tr valign="top">
	
			
		  <%if (!("").equals(errorMessage)){%>
			<tr><td align="right">	
			<font color=#ff0000><b><%=errorMessage%><b></font>
			<br>
			<br>
		</td></tr>
			<%}%>
			<form width="50%" name="result" action="smsSimulator.jsp" method="POST">
				<tr>
					<td width="50%" colspan="6" nowrap class="headerUnderLine2Heb"><%=org.celiac.util.TemplateReader.getHebrewMapWord("NEW_USER_SUCCESS")%></td>
				</tr>
															
				<tr bgcolor="#FFFFFF">
					
					
				</tr>
				<tr>
				<tr>
				<tr>
					<table align="center" width="50%" border="0" cellpadding="5">
				<tr>
					  <td><input name="submit" type="submit" id="submit" value="<%=org.celiac.util.TemplateReader.getHebrewMapWord("HAMSHECH")%>"  style="width: 100%" class="button" enabled onMouseOver="this.style.background =  '#d5e0e6';" onMouseOut="this.style.background = '#e4e4e4';"></td>
				</tr>
			</table>
			</form>	
		</td>
			</tr>
			</table>
			<br>

<%
}catch (Exception e){

	String theErrorOut = e.getMessage();
	session.setAttribute("gfsmsNewUserError",theErrorOut);
	response.sendRedirect("newUserTOU.jsp");
}
%>
<%@ include file="../include/buttom.jsp" %>    