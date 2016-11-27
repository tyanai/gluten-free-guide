<%@ page language="java" 
         import="org.celiac.api.GlutenFreeAPI, org.celiac.web.bean.*,org.celiac.datatype.*, java.util.*" 
         errorPage="loginError.jsp" %>
<jsp:useBean id="User" scope="session" class="org.celiac.web.bean.User">
  <jsp:setProperty name="User" property="*"/>
</jsp:useBean>
<%@ include file="../include/header.jsp" %>    
<% if (!(User.getUser().getPERMISSIONS().equals("admin")) && !(User.getUser().getPERMISSIONS().equals("mitnadev"))) throw new Exception("User was not login. Please login."); %>


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


<%

String choiseForFullUpdate=null;
if (request.getParameter("choiseForFullUpdate") != null){
		choiseForFullUpdate = (String)request.getParameter("choiseForFullUpdate");
		
} 

String choise = null;
if (request.getParameter("choise") != null){
		choise = (String)request.getParameter("choise");
		
} 

String FIRST_NAME = null;
if (request.getParameter("FIRST_NAME") != null){
		FIRST_NAME = (String)request.getParameter("FIRST_NAME");
		
} 

String LAST_NAME = null;
if (request.getParameter("LAST_NAME") != null){
		LAST_NAME = (String)request.getParameter("LAST_NAME");
		
} 

String HISHUR_PASSWORD = null;
if (request.getParameter("HISHUR_PASSWORD") != null){
		HISHUR_PASSWORD = (String)request.getParameter("HISHUR_PASSWORD");
		
}	

String PASSWORD = null;
if (request.getParameter("PASSWORD") != null){
		PASSWORD = (String)request.getParameter("PASSWORD");
		
}		

String USER_ID = null;
if (request.getParameter("USER_ID") != null){
		USER_ID = (String)request.getParameter("USER_ID");
		
}	

String USER_EMAIL = null;
if (request.getParameter("USER_EMAIL") != null){
	USER_EMAIL = (String)request.getParameter("USER_EMAIL");
		
}	



String CELL_NUM = null;
if (request.getParameter("CELL_NUM") != null){
		CELL_NUM = (String)request.getParameter("CELL_NUM");
		
}		

String EMAIL = null;
if (request.getParameter("EMAIL") != null){
		EMAIL = (String)request.getParameter("EMAIL");
		
}		

String EMAIL_ISHUR = null;
if (request.getParameter("EMAIL_ISHUR") != null){
		EMAIL_ISHUR = (String)request.getParameter("EMAIL_ISHUR");
		
}	

String CELIAC_MEMBER_ID = null;
if (request.getParameter("CELIAC_MEMBER_ID") != null){
		CELIAC_MEMBER_ID = (String)request.getParameter("CELIAC_MEMBER_ID");
		CELIAC_MEMBER_ID = User.cutLeadingZero(CELIAC_MEMBER_ID);
		
}	

String USER_TZ = null;
if (request.getParameter("USER_TZ") != null){
		USER_TZ = (String)request.getParameter("USER_TZ");
		
}	

String EXPIRATION_DATE = null;
Date expDate = null;
if (request.getParameter("EXPIRATION_DATE") != null){
		EXPIRATION_DATE = (String)request.getParameter("EXPIRATION_DATE");
		expDate = User.getDateFromString(EXPIRATION_DATE);
		
}	

String LAST_UPDATE_WAS_PURCHASE = null;
if (request.getParameter("LAST_UPDATE_WAS_PURCHASE") != null){
		LAST_UPDATE_WAS_PURCHASE = (String)request.getParameter("LAST_UPDATE_WAS_PURCHASE");
		
}	

String PERMISSIONS = null;
if (request.getParameter("PERMISSIONS") != null){
		PERMISSIONS = (String)request.getParameter("PERMISSIONS");
		
}	
try{


if (choise.equals("sendUsersEmail")){
	String SUBJECT = (String)request.getParameter("SUBJECT");
	String BODY = (String)request.getParameter("userNote");
	session.setAttribute("SUBJECT",SUBJECT);
	session.setAttribute("BODY",BODY);
  Set<GFUser> theAllUsers = org.celiac.util.database.DBQueryFactory.getDBHandler().getAllUserDetails();
  org.celiac.util.Pop3Email.postMen(org.celiac.util.PropertiesLoader.getProperties("emailFrom"), SUBJECT, BODY,theAllUsers);
  throw new Exception (org.celiac.util.TemplateReader.getHebrewMapWord("EMAIL_WAS_SENT_SECCESS"));

}




GFUser theCandidateUser = new GFUser();



theCandidateUser.setCELL_NUM(CELL_NUM);
theCandidateUser.setFIRST_NAME(FIRST_NAME);
theCandidateUser.setLAST_NAME(LAST_NAME);
theCandidateUser.setEFFECTIVE_DATE(new Date());
if (EXPIRATION_DATE!=null) theCandidateUser.setEXPIRATION_DATE(expDate);
theCandidateUser.setUSER_ID(USER_ID);
theCandidateUser.setPASSWORD(PASSWORD);
if (PERMISSIONS == null) theCandidateUser.setPERMISSIONS("sim");
else theCandidateUser.setPERMISSIONS(PERMISSIONS);
theCandidateUser.setLAST_LOGIN_DATE(new Date());
theCandidateUser.setACKNOWLEDGE_USE("FALSE");
theCandidateUser.setEMAIL(EMAIL);
theCandidateUser.setCELIAC_MEMBER_ID(CELIAC_MEMBER_ID);
theCandidateUser.setUSER_TZ(USER_TZ);
theCandidateUser.setDID_BUY_THE_BOOK(LAST_UPDATE_WAS_PURCHASE);

boolean basicUpdate = false;
boolean fullUpdate = false;
if ((choise.equals("findEmailUserUpdate")) || (choise.equals("findTZUserUpdate") || choise.equals("findIDUserUpdate")) && (USER_ID != null)) fullUpdate = true;
//if ((choise.equals("findTZUserUpdate") || choise.equals("findIDUserUpdate")) && (USER_ID == null)) basicUpdate = true;
//if ((choise.equals("findEmailUserUpdate") || choise.equals("findEmailUserUpdate")) && (USER_ID == null)) basicUpdate = true;



if (choise.equals("findIDUser") || "findIDUserUpdateFullU".equals(choiseForFullUpdate) ){
	 if (CELIAC_MEMBER_ID == null || ("").equals(CELIAC_MEMBER_ID)) {
       throw new Exception(org.celiac.util.TemplateReader.getHebrewMapWord("ANA_SAPEK") + " " + org.celiac.util.TemplateReader.getHebrewMapWord("CELIAC_MEMBER_ID") );
	 }
   GFUser searchGFUser = User.getUser(CELIAC_MEMBER_ID.trim());
   if (searchGFUser == null) throw new Exception(org.celiac.util.TemplateReader.getHebrewMapWord("RECORED_NOT_FOUND") );
   
   if (("admin").equals(searchGFUser.getPERMISSIONS())) {
       if (!User.getUser().getPERMISSIONS().equals("admin")) throw new Exception(org.celiac.util.TemplateReader.getHebrewMapWord("RECORED_NOT_FOUND"));
   }

 
   session.setAttribute("A_USER",searchGFUser);
   
  
   throw new Exception();
   
}

if ("findTZUserUpdateDelete".equals(choiseForFullUpdate) || "findIDUserUpdateDelete".equals(choiseForFullUpdate) || "findEmailUserUpdateDelete".equals(choiseForFullUpdate)){

	 if (USER_TZ == null || ("").equals(USER_TZ)) {
       throw new Exception(org.celiac.util.TemplateReader.getHebrewMapWord("ANA_SAPEK") + " " + org.celiac.util.TemplateReader.getHebrewMapWord("USER_TZ") );
	 }
   GFUser searchGFUser = org.celiac.util.database.DBQueryFactory.getDBHandler().getUserByGeneric(USER_TZ.trim(), "USER_TZ");
   if (searchGFUser == null) throw new Exception(org.celiac.util.TemplateReader.getHebrewMapWord("RECORED_NOT_FOUND") );
   if (("admin").equals(searchGFUser.getPERMISSIONS())) {
      if (!User.getUser().getPERMISSIONS().equals("admin")) throw new Exception(org.celiac.util.TemplateReader.getHebrewMapWord("RECORED_NOT_FOUND"));
   }
   
   User.deleteUser(searchGFUser);
   searchGFUser = org.celiac.util.database.DBQueryFactory.getDBHandler().getUserByGeneric(USER_TZ.trim(), "USER_TZ");
   session.setAttribute("A_USER",searchGFUser);
   throw new Exception(org.celiac.util.TemplateReader.getHebrewMapWord("USER_DELETED_SUCSESFULY"));
		
}




if (choise.equals("findTZUser") || "findTZUserUpdateFullU".equals(choiseForFullUpdate) ){
	 if (USER_TZ == null || ("").equals(USER_TZ)) {
       throw new Exception(org.celiac.util.TemplateReader.getHebrewMapWord("ANA_SAPEK") + " " + org.celiac.util.TemplateReader.getHebrewMapWord("USER_TZ") );
	 }
   GFUser searchGFUser = org.celiac.util.database.DBQueryFactory.getDBHandler().getUserByGeneric(USER_TZ.trim(), "USER_TZ");
   if (searchGFUser == null) throw new Exception(org.celiac.util.TemplateReader.getHebrewMapWord("RECORED_NOT_FOUND") );
   if (("admin").equals(searchGFUser.getPERMISSIONS())) {
      if (!User.getUser().getPERMISSIONS().equals("admin")) throw new Exception(org.celiac.util.TemplateReader.getHebrewMapWord("RECORED_NOT_FOUND"));
   }
   session.setAttribute("A_USER",searchGFUser);
   
   
	
   throw new Exception();
   
}

if (choise.equals("findEmailUser") || "findEmailUserUpdateFullU".equals(choiseForFullUpdate) ){
	 if (USER_EMAIL == null || ("").equals(USER_EMAIL)) {
      throw new Exception(org.celiac.util.TemplateReader.getHebrewMapWord("ANA_SAPEK") + " " + org.celiac.util.TemplateReader.getHebrewMapWord("EMAIL") );
	 }
	 GFUser searchGFUser = null;
	try{
  		 searchGFUser = org.celiac.util.database.DBQueryFactory.getDBHandler().getUserByEmail(USER_EMAIL.trim());
	}catch (Exception e){
		e.printStackTrace();
	}
 
  if (searchGFUser == null) throw new Exception(org.celiac.util.TemplateReader.getHebrewMapWord("RECORED_NOT_FOUND") );

  if (("admin").equals(searchGFUser.getPERMISSIONS())) {
     if (!User.getUser().getPERMISSIONS().equals("admin")) throw new Exception(org.celiac.util.TemplateReader.getHebrewMapWord("RECORED_NOT_FOUND"));
  }
  session.setAttribute("A_USER",searchGFUser);
  
  
	
  throw new Exception();
  
}

session.setAttribute("A_USER",theCandidateUser);

if (choise.equals("addFullUser") || fullUpdate ){
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
}

if (choise.equals("addBasicUser") || choise.equals("addFullUser") || fullUpdate || basicUpdate ){
if (CELIAC_MEMBER_ID == null || ("").equals(CELIAC_MEMBER_ID)) {
   throw new Exception(org.celiac.util.TemplateReader.getHebrewMapWord("ANA_SAPEK") + " " + org.celiac.util.TemplateReader.getHebrewMapWord("CELIAC_MEMBER_ID") );
}
if (USER_TZ == null || ("").equals(USER_TZ)) {
   throw new Exception(org.celiac.util.TemplateReader.getHebrewMapWord("ANA_SAPEK") + " " + org.celiac.util.TemplateReader.getHebrewMapWord("USER_TZ") );
}
if (EXPIRATION_DATE == null || ("").equals(EXPIRATION_DATE)) {
   throw new Exception(org.celiac.util.TemplateReader.getHebrewMapWord("ANA_SAPEK") + " " + org.celiac.util.TemplateReader.getHebrewMapWord("EXPIRATION_DATE") );
}
if (LAST_UPDATE_WAS_PURCHASE == null || ("").equals(LAST_UPDATE_WAS_PURCHASE)) {
   throw new Exception(org.celiac.util.TemplateReader.getHebrewMapWord("ANA_SAPEK") + " " + org.celiac.util.TemplateReader.getHebrewMapWord("LAST_UPDATE_WAS_PURCHASE") );
}
}




if (choise.equals("addFullUser") || fullUpdate){
GFUser gfUser = org.celiac.util.database.DBQueryFactory.getDBHandler().getUserByUserID(USER_ID.trim());
if (gfUser != null && !fullUpdate) throw new Exception(org.celiac.util.TemplateReader.getHebrewMapWord("SHEM_MISHTAMESH_KAYAM") + " '" + USER_ID + "'" );
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
}
if (choise.equals("addBasicUser") || choise.equals("addFullUser") || basicUpdate || fullUpdate ){

if (!User.validateString(CELIAC_MEMBER_ID)) throw new Exception(org.celiac.util.TemplateReader.getHebrewMapWord("NO_SPACIAL_CHARS")+"<br>\\,%^!*)-(#~");
if (!User.validateString(USER_TZ)) throw new Exception(org.celiac.util.TemplateReader.getHebrewMapWord("NO_SPACIAL_CHARS")+"<br>\\,%^!*)-(#~");
if (!fullUpdate && !basicUpdate) {
GFUser gfUser = User.getUser(CELIAC_MEMBER_ID.trim());
if (gfUser != null) throw new Exception(org.celiac.util.TemplateReader.getHebrewMapWord("MEMBER_ID_MISHTAMESH_KAYAM"));
gfUser = org.celiac.util.database.DBQueryFactory.getDBHandler().getUserByGeneric(USER_TZ.trim(), "USER_TZ");
if (gfUser != null) throw new Exception(org.celiac.util.TemplateReader.getHebrewMapWord("TZ_ID_MISHTAMESH_KAYAM"));
}
}

if (choise.equals("addFullUser")){
	User.insertFullUser(theCandidateUser);
	throw new Exception(org.celiac.util.TemplateReader.getHebrewMapWord("USER_WAS_CREATED"));
}
if (choise.equals("addBasicUser")){
	User.insertUser(theCandidateUser);
	throw new Exception(org.celiac.util.TemplateReader.getHebrewMapWord("USER_WAS_CREATED"));
}

if (choise.equals("findTZUserUpdate") || choise.equals("findEmailUserUpdate") || choise.equals("findIDUserUpdate")){
  GFUser gfUser = User.getUser(CELIAC_MEMBER_ID.trim());
  
  
  //gfUser.setCELIAC_MEMBER_ID(CELIAC_MEMBER_ID.trim());
  //gfUser.setUSER_TZ(USER_TZ.trim());
  gfUser.setEXPIRATION_DATE(expDate);
  gfUser.setDID_BUY_THE_BOOK(LAST_UPDATE_WAS_PURCHASE);
  
  if (fullUpdate){
  
	gfUser.setFIRST_NAME(FIRST_NAME.trim());
  gfUser.setLAST_NAME(LAST_NAME.trim());
  gfUser.setPASSWORD(PASSWORD.trim());
  gfUser.setUSER_ID(USER_ID.trim());
  gfUser.setCELL_NUM(CELL_NUM.trim());
  gfUser.setEMAIL(EMAIL.trim());
  gfUser.setPERMISSIONS(PERMISSIONS); 
  if (gfUser.getEFFECTIVE_DATE() == null ) gfUser.setEFFECTIVE_DATE(new Date());
  if (gfUser.getLAST_LOGIN_DATE() == null ) gfUser.setLAST_LOGIN_DATE(new Date());
  if (gfUser.getACKNOWLEDGE_USE() == null ) gfUser.setACKNOWLEDGE_USE("FALSE");
  

  User.updateUser(gfUser);
	} else {
	User.updateBasicUser(gfUser);
	}
	
	session.setAttribute("A_USER",User.getUser(CELIAC_MEMBER_ID.trim()));
	if (choise.equals("findTZUserUpdate")) choise = "findTZUser";
	if (choise.equals("findIDUserUpdate")) choise = "findIDUser";
	if (choise.equals("findEmailUserUpdate")) choise = "findEmailUser";
	
	throw new Exception(org.celiac.util.TemplateReader.getHebrewMapWord("USER_UPDATED_SUCSESFULY"));
}



if (true) throw new Exception(org.celiac.util.TemplateReader.getHebrewMapWord("USER_WAS_CREATED"));


String errorMessage = (String)session.getAttribute("gfsmsNewUserError");
if (errorMessage == null) errorMessage = "";

session.setAttribute("gfsmsNewUserError",null);

%>

	
</body>
</html>
<%
}catch (Exception e){

	String theErrorOut = e.getMessage();
	session.setAttribute("gfsmsNewUserError",theErrorOut);
	if (choise.equals("findTZUserUpdate")) choise = "findTZUser";
	if (choise.equals("findIDUserUpdate")) choise = "findIDUser";
	if (choise.equals("findEmailUserUpdate")) choise = "findEmailUser";
	if ("findTZUserUpdateFullU".equals(choiseForFullUpdate)) choise = "findTZUserUpdateFullU";
	if ("findIDUserUpdateFullU".equals(choiseForFullUpdate)) choise = "findIDUserUpdateFullU";
	if ("findEmailUserUpdateFullU".equals(choiseForFullUpdate)) choise = "findEmailUserUpdateFullU";

	
	response.sendRedirect("manageUser.jsp?choise="+choise);
}
%>
<%@ include file="../include/buttom.jsp" %>    