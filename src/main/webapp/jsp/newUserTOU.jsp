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
      
<p align="center">
		
<table bgcolor= "#f5f5e1" width="50%" border="1">
<td valign="center"><img src="../images/celiac_logo.jpg"></td>
		
<td>
<%

GFUser savedUser = null;
boolean userNotSaved = true;
if (session.getAttribute("userAlreadySaved") != null){
		savedUser = (GFUser)session.getAttribute("userAlreadySaved");
		userNotSaved = false;
		session.setAttribute("FIRST_NAME",savedUser.getFIRST_NAME());
		session.setAttribute("LAST_NAME",savedUser.getLAST_NAME());
		session.setAttribute("PASSWORD",savedUser.getPASSWORD());
		session.setAttribute("USER_ID",savedUser.getUSER_ID());
		session.setAttribute("CELL_NUM",savedUser.getCELL_NUM());
		session.setAttribute("EMAIL",savedUser.getEMAIL());
		session.setAttribute("EXPIRATION_DATE",savedUser.getEXPIRATION_DATE());
    session.setAttribute("DID_BUY_THE_BOOK",savedUser.getDID_BUY_THE_BOOK());
    session.setAttribute("CELIAC_MEMBER_ID",savedUser.getCELIAC_MEMBER_ID());
    session.setAttribute("USER_TZ",savedUser.getUSER_TZ());
    session.setAttribute("HISHUR_PASSWORD",savedUser.getPASSWORD());
    session.setAttribute("EMAIL_ISHUR",savedUser.getEMAIL());
    
  
}

String FIRST_NAME="";
if (request.getParameter("FIRST_NAME") != null){
		FIRST_NAME = (String)request.getParameter("FIRST_NAME");
		session.setAttribute("FIRST_NAME",FIRST_NAME);
} 

String LAST_NAME="";
if (request.getParameter("LAST_NAME") != null){
		LAST_NAME = (String)request.getParameter("LAST_NAME");
		session.setAttribute("LAST_NAME",LAST_NAME);
} 

String HISHUR_PASSWORD="";
if (request.getParameter("HISHUR_PASSWORD") != null){
		HISHUR_PASSWORD = (String)request.getParameter("HISHUR_PASSWORD");
		session.setAttribute("HISHUR_PASSWORD",HISHUR_PASSWORD);
}	

String PASSWORD="";
if (request.getParameter("PASSWORD") != null){
		PASSWORD = (String)request.getParameter("PASSWORD");
		session.setAttribute("PASSWORD",PASSWORD);
}		

String USER_ID="";
if (request.getParameter("USER_ID") != null){
		USER_ID = (String)request.getParameter("USER_ID");
		session.setAttribute("USER_ID",USER_ID);
}		

String CELL_NUM="";
if (request.getParameter("CELL_NUM") != null){
		CELL_NUM = (String)request.getParameter("CELL_NUM");
		session.setAttribute("CELL_NUM",CELL_NUM);
}		

String EMAIL="";
if (request.getParameter("EMAIL") != null){
		EMAIL = (String)request.getParameter("EMAIL");
		session.setAttribute("EMAIL",EMAIL);
}		

String EMAIL_ISHUR="";
if (request.getParameter("EMAIL_ISHUR") != null){
		EMAIL_ISHUR = (String)request.getParameter("EMAIL_ISHUR");
		session.setAttribute("EMAIL_ISHUR",EMAIL_ISHUR);
}		

String CELIAC_MEMBER_ID = (String)session.getAttribute("CELIAC_MEMBER_ID");

String USER_TZ = (String)session.getAttribute("USER_TZ");	


String ASHER_PIRETY_HISHTAMSHUT="";
if (request.getParameter("ASHER_PIRETY_HISHTAMSHUT") != null){
		ASHER_PIRETY_HISHTAMSHUT = (String)request.getParameter("ASHER_PIRETY_HISHTAMSHUT");
		
}

String LO_MEHASHER_PIRTEY_HISHTAMSHUT="";
if (request.getParameter("LO_MEHASHER_PIRTEY_HISHTAMSHUT") != null){
		LO_MEHASHER_PIRTEY_HISHTAMSHUT = (String)request.getParameter("LO_MEHASHER_PIRTEY_HISHTAMSHUT");
		
}		

String errorMessage = (String)session.getAttribute("gfsmsNewUserError");
if (errorMessage == null) errorMessage = "";

session.setAttribute("gfsmsNewUserError",null);


try{

if (userNotSaved){

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

PASSWORD = PASSWORD.trim();
USER_ID = USER_ID.trim();
if ( (PASSWORD.indexOf(" ") > -1) || (USER_ID.indexOf(" ") > -1) ) throw new Exception(org.celiac.util.TemplateReader.getHebrewMapWord("ONE_WORD"));

GFUser gfUser = org.celiac.util.database.DBQueryFactory.getDBHandler().getUserByUserID(USER_ID.trim());
if (gfUser != null) throw new Exception(org.celiac.util.TemplateReader.getHebrewMapWord("SHEM_MISHTAMESH_KAYAM") + " '" + USER_ID + "'" );
if (!EMAIL.trim().equals(EMAIL_ISHUR.trim())) throw new Exception(org.celiac.util.TemplateReader.getHebrewMapWord("EMAIL_LO_TOHEM"));
if (!PASSWORD.trim().equals(HISHUR_PASSWORD.trim())) throw new Exception(org.celiac.util.TemplateReader.getHebrewMapWord("SISMA_LO_TOHAMIM"));

if (!User.validateString(PASSWORD)) throw new Exception(org.celiac.util.TemplateReader.getHebrewMapWord("NO_SPACIAL_CHARS")+"<br>\\,%^!*)-(#~<BR><b>"+ PASSWORD +"<b>");
if (!User.validateString(USER_ID)) throw new Exception(org.celiac.util.TemplateReader.getHebrewMapWord("NO_SPACIAL_CHARS")+"<br>\\,%^!*)-(#~<BR><b>"+ USER_ID +"");

try{
			javax.mail.internet.InternetAddress tempAddr = new javax.mail.internet.InternetAddress(EMAIL);
			tempAddr.validate();
}catch (Exception e2){
			throw new Exception(org.celiac.util.TemplateReader.getHebrewMapWord("NOT_A_VALID_EMAIL"));
}

//if (!User.validateString(CELL_NUM)) throw new Exception(org.celiac.util.TemplateReader.getHebrewMapWord("NO_SPACIAL_CHARS")+"<br>\\,%^!*)-(#~<BR><b>"+ CELL_NUM +"</b>");
//if (!User.validateString(EMAIL)) throw new Exception(org.celiac.util.TemplateReader.getHebrewMapWord("NO_SPACIAL_CHARS")+"<br>\\,%^!*)-(#~<BR><b>"+ EMAIL +"<b>");





}

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
			
			<form width="100%" name="result" action="endNewUser.jsp" method="POST">
				<tr>
					<td width="100%" colspan="6" nowrap class="headerUnderLine2Heb"><%=org.celiac.util.TemplateReader.getHebrewMapWord("PITEY_HISTAMSHUT")%></td>
				</tr>
				<tr></tr>
				<tr bgcolor="#FFFFFF" class="text" >			
					<tr><td><IFRAME  src='takanon.htm' height="450" width="600" ></IFRAME></td></tr>
				</tr><tr></tr><tr></tr><tr></tr><tr></tr>
				
				<tr>
					<td width="100%" colspan="6" nowrap class="headerUnderLine2Heb">
					  <input onClick="Disab(this.value)" type="radio" name="acceptTerm" value="enabl"> &nbsp;&nbsp;&nbsp <%=org.celiac.util.TemplateReader.getHebrewMapWord("ASHER_PIRETY_HISHTAMSHUT")%>
				</td>
			  </tr>
			  <tr>
			    <td width="100%" colspan="6" nowrap class="headerUnderLine2Heb">
					 <input onClick="Disab(this.value)" type="radio" name="notAcceptTerm" value="disabl"> &nbsp;&nbsp;&nbsp; <%=org.celiac.util.TemplateReader.getHebrewMapWord("LO_MEHASHER_PIRTEY_HISHTAMSHUT")%> 
					</td>
				</tr>
				
							
	
	
				
				<tr bgcolor="#FFFFFF">
					
					
					
				</tr>
				<tr>
				<tr>
				<tr>
					<table width="100%" border="0" cellpadding="5">
				<tr>
						<td><input type="button" value="<%=org.celiac.util.TemplateReader.getHebrewMapWord("HAZOR")%>"  style="width: 100%" onClick="history.go(-1);" class="button" enabled onMouseOver="this.style.background =  '#d5e0e6';" onMouseOut="this.style.background = '#e4e4e4';" ></td>
					  <td><input name="submit" type="submit" id="submit" value="<%=org.celiac.util.TemplateReader.getHebrewMapWord("HAMSHECH")%>"  style="width: 100%" class="button" disabled onMouseOver="this.style.background =  '#d5e0e6';" onMouseOut="this.style.background = '#e4e4e4';"></td>
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
	response.sendRedirect("newUserCon.jsp");
}
%>
<%@ include file="../include/buttom.jsp" %>    