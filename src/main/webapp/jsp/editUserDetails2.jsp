<%@ page language="java" 
         import="org.celiac.api.GlutenFreeAPI, org.celiac.web.bean.*,org.celiac.datatype.*, java.util.*" 
         errorPage="loginError.jsp" %>
<jsp:useBean id="User" scope="session" class="org.celiac.web.bean.User">
  <jsp:setProperty name="User" property="*"/>
</jsp:useBean>
<%@ include file="../include/header.jsp" %>  

<SCRIPT LANGUAGE="JavaScript">		

	function processBack()
	{
		alert("GGG");
	  window.history.back(1);	  
	}
	
	function Disab(val) {
		frm=document.result
		if(val=="enabl")
		{frm.submit.disabled=false;frm.notAcceptTerm.checked = false}
		if(val=="disabl")
		{frm.submit.disabled=true;frm.acceptTerm.checked = false;alert("<%=org.celiac.util.TemplateReader.getHebrewMapWord("PIRTEY_SIMISH_HAYAV")%>");}
}



</script>

<body bgcolor="#ffffff" LEFTMARGIN="10" RIGHTMARGIN="10"
      link="#3366cc" vlink="#9999cc" alink="#0000cc">
      
<p align="left">
<table width="50%" border="0">
<td valign="top"><img src="../images/celiac_logo.jpg"></td>
		<td align="center"></td>				
		<td>&nbsp;</td>	
</td>
<td>
<%

String FIRST_NAME="";
if (request.getParameter("FIRST_NAME") != null){
		FIRST_NAME = (String)request.getParameter("FIRST_NAME");
		session.setAttribute("FIRST_NAME",FIRST_NAME);
} else if (session.getAttribute("FIRST_NAME") != null){
	  FIRST_NAME = (String)session.getAttribute("FIRST_NAME");
}

String LAST_NAME="";
if (request.getParameter("LAST_NAME") != null){
		LAST_NAME = (String)request.getParameter("LAST_NAME");
		session.setAttribute("LAST_NAME",LAST_NAME);
} else if (session.getAttribute("LAST_NAME") != null){
	  LAST_NAME = (String)session.getAttribute("LAST_NAME");
}

String HISHUR_PASSWORD="";
if (request.getParameter("HISHUR_PASSWORD") != null){
		HISHUR_PASSWORD = (String)request.getParameter("HISHUR_PASSWORD");
		session.setAttribute("HISHUR_PASSWORD",HISHUR_PASSWORD);
}	else if (session.getAttribute("HISHUR_PASSWORD") != null){
	  HISHUR_PASSWORD = (String)session.getAttribute("HISHUR_PASSWORD");
}	

String PASSWORD="";
if (request.getParameter("PASSWORD") != null){
		PASSWORD = (String)request.getParameter("PASSWORD");
		session.setAttribute("PASSWORD",PASSWORD);
}		else if (session.getAttribute("PASSWORD") != null){
	  PASSWORD = (String)session.getAttribute("PASSWORD");
}

String USER_ID="";
if (request.getParameter("USER_ID") != null){
		USER_ID = (String)request.getParameter("USER_ID");
		session.setAttribute("USER_ID",USER_ID);
}		else if (session.getAttribute("USER_ID") != null){
	  USER_ID = (String)session.getAttribute("USER_ID");
}

String CELL_NUM="";
if (request.getParameter("CELL_NUM") != null){
		CELL_NUM = (String)request.getParameter("CELL_NUM");
		session.setAttribute("CELL_NUM",CELL_NUM);
}		else if (session.getAttribute("CELL_NUM") != null){
	  CELL_NUM = (String)session.getAttribute("CELL_NUM");
}

String EMAIL="";
if (request.getParameter("EMAIL") != null){
		EMAIL = (String)request.getParameter("EMAIL");
		session.setAttribute("EMAIL",EMAIL);
}		else if (session.getAttribute("EMAIL") != null){
	  EMAIL = (String)session.getAttribute("EMAIL");
}

String EMAIL_ISHUR="";
if (request.getParameter("EMAIL_ISHUR") != null){
		EMAIL_ISHUR = (String)request.getParameter("EMAIL_ISHUR");
		session.setAttribute("EMAIL_ISHUR",EMAIL_ISHUR);
}		else if (session.getAttribute("EMAIL_ISHUR") != null){
	  EMAIL_ISHUR = (String)session.getAttribute("EMAIL_ISHUR");
}




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

PASSWORD = PASSWORD.trim();
USER_ID = USER_ID.trim();
if ( (PASSWORD.indexOf(" ") > -1) || (USER_ID.indexOf(" ") > -1) ) throw new Exception(org.celiac.util.TemplateReader.getHebrewMapWord("ONE_WORD"));



GFUser gfUser = org.celiac.util.database.DBQueryFactory.getDBHandler().getUserByUserID(USER_ID.trim());
if ((gfUser != null) && (!USER_ID.trim().equals(User.theUser.getUSER_ID()))) throw new Exception(org.celiac.util.TemplateReader.getHebrewMapWord("SHEM_MISHTAMESH_KAYAM") + " '" + USER_ID + "'" );
if (!EMAIL.trim().equals(EMAIL_ISHUR.trim())) throw new Exception(org.celiac.util.TemplateReader.getHebrewMapWord("EMAIL_LO_TOHEM"));
if (!PASSWORD.trim().equals(HISHUR_PASSWORD.trim())) throw new Exception(org.celiac.util.TemplateReader.getHebrewMapWord("SISMA_LO_TOHAMIM"));

if (!User.validateString(PASSWORD)) throw new Exception(org.celiac.util.TemplateReader.getHebrewMapWord("NO_SPACIAL_CHARS")+"<br>\\,%^!*)-(#~");
if (!User.validateString(USER_ID)) throw new Exception(org.celiac.util.TemplateReader.getHebrewMapWord("NO_SPACIAL_CHARS")+"<br>\\,%^!*)-(#~");

try{
			javax.mail.internet.InternetAddress tempAddr = new javax.mail.internet.InternetAddress(EMAIL);
			tempAddr.validate();
}catch (Exception e2){
			throw new Exception(org.celiac.util.TemplateReader.getHebrewMapWord("NOT_A_VALID_EMAIL"));
}


//if (!User.validateString(CELL_NUM)) throw new Exception(org.celiac.util.TemplateReader.getHebrewMapWord("NO_SPACIAL_CHARS")+"<br>\\,%^!*)-(#~");
//if (!User.validateString(EMAIL)) throw new Exception(org.celiac.util.TemplateReader.getHebrewMapWord("NO_SPACIAL_CHARS")+"<br>\\,%^!*)-(#~");


  gfUser = User.theUser;
  
  
	gfUser.setFIRST_NAME(FIRST_NAME.trim());
  gfUser.setLAST_NAME(LAST_NAME.trim());
  gfUser.setPASSWORD(PASSWORD.trim());
  gfUser.setUSER_ID(USER_ID.trim());
  gfUser.setCELL_NUM(CELL_NUM.trim());
  gfUser.setEMAIL(EMAIL.trim());
  User.updateUser(gfUser);
  
  if (true) throw new Exception(org.celiac.util.TemplateReader.getHebrewMapWord("SHINUIM_NISHMERU"));

%>


	

</td>
</tr>
</table>
</body>
</html>
<%
}catch (Exception e){

	String theErrorOut = e.getMessage();
	session.setAttribute("gfsmsNewUserError",theErrorOut);
	response.sendRedirect("editUserDetails.jsp");
}
%>
<%@ include file="../include/buttom.jsp" %>    