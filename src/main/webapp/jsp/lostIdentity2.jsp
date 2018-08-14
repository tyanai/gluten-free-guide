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
</SCRIPT>

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


/*
String CELIAC_MEMBER_ID=null;
if (request.getParameter("CELIAC_MEMBER_ID") != null){
		CELIAC_MEMBER_ID = (String)request.getParameter("CELIAC_MEMBER_ID").trim();
		CELIAC_MEMBER_ID = User.cutLeadingZero(CELIAC_MEMBER_ID);
		session.setAttribute("CELIAC_MEMBER_ID",CELIAC_MEMBER_ID);
} else if (session.getAttribute("CELIAC_MEMBER_ID") != null){
	  CELIAC_MEMBER_ID = (String)session.getAttribute("CELIAC_MEMBER_ID");
	  CELIAC_MEMBER_ID = User.cutLeadingZero(CELIAC_MEMBER_ID);
}
*/

String USER_TZ=null;
if (request.getParameter("USER_TZ") != null){
		USER_TZ = (String)request.getParameter("USER_TZ").trim();
		session.setAttribute("USER_TZ",USER_TZ);
}		else if (session.getAttribute("USER_TZ") != null){
	  USER_TZ = (String)session.getAttribute("USER_TZ");
}

try{

    /*
if (CELIAC_MEMBER_ID == null || ("").equals(CELIAC_MEMBER_ID)) {

   throw new Exception(org.celiac.util.TemplateReader.getHebrewMapWord("ANA_SAPEK") + " " + org.celiac.util.TemplateReader.getHebrewMapWord("CELIAC_MEMBER_ID") );
   
}
*/

if (USER_TZ == null || ("").equals(USER_TZ)) {

   throw new Exception(org.celiac.util.TemplateReader.getHebrewMapWord("ANA_SAPEK") + " " + org.celiac.util.TemplateReader.getHebrewMapWord("USER_TZ") );
   
}

//GFUser gfUser = User.getUser(CELIAC_MEMBER_ID.trim());
GFUser gfUser = User.getUserByTZ(USER_TZ.trim());
if (gfUser == null) throw new Exception(org.celiac.util.TemplateReader.getHebrewMapWord("USER_TZ") + " " + org.celiac.util.TemplateReader.getHebrewMapWord("LO_KAYAM") );
//if (!(gfUser.getUSER_TZ().equals(USER_TZ))) throw new Exception(org.celiac.util.TemplateReader.getHebrewMapWord("USER_TZ") + " " + org.celiac.util.TemplateReader.getHebrewMapWord("LO_TOHAMIM") );
if (gfUser.getEXPIRATION_DATE().getTime() < new java.util.Date().getTime()) {
	if (!User.maybeUserIsNoExpire()){
  	java.text.DateFormat df = new java.text.SimpleDateFormat("d/M/yyyy"); 
		throw new Exception(org.celiac.util.TemplateReader.getHebrewMapWord("USER_TZ") + ": '" + USER_TZ + "' " + org.celiac.util.TemplateReader.getHebrewMapWord("LO_BETOKEF") + ": " + df.format(gfUser.getEXPIRATION_DATE())  );
	}
}
//if (gfUser.getDID_BUY_THE_BOOK().equals("false")) throw new Exception(org.celiac.util.TemplateReader.getHebrewMapWord("ONLY_BUY_BOOK")  );

if (gfUser != null){
		
		if (gfUser.getPASSWORD() == null) throw new Exception(org.celiac.util.TemplateReader.getHebrewMapWord("LI_STILL_NOT_REGISTERED")  );
		
		//send email
		User.sendWelcomeLetter(gfUser);
		throw new Exception(org.celiac.util.TemplateReader.getHebrewMapWord("LI_IS_REGISTERED") + "<br>" + gfUser.getEMAIL()  );
		
}


String errorMessage = (String)session.getAttribute("gfsmsNewUserError");
if (errorMessage == null) errorMessage = "";
session.setAttribute("gfsmsNewUserError",null);

%>

	<table width="100%" border="0" cellpadding="5">
	<tr valign="top">
		<td width="100%">
			<table border="0" cellpadding="1" cellspacing="1" class="frameTableT">
		

			 <%if (!("").equals(errorMessage)){%>
			<tr><td align="right">	
			<font color=#ff0000><b><%=errorMessage%><b></font>
			<br>
			<br>
		</td></tr>
		 <%}%>
			
			<form name="result" action="../login.jsp" method="POST">
				<tr>
					<td colspan="6" nowrap class="headerUnderLine2Heb"><%=org.celiac.util.TemplateReader.getHebrewMapWord("LOST_IDENTITY")%></td>
				</tr>
				<tr></tr>
				
								
							
	
				
				<tr bgcolor="#FFFFFF">
					
									
				</tr>
				<tr>
				<tr>
				<tr>
					<table width="100%" border="0" cellpadding="5">
			
			</table>
			</form>	
		</td>
			</tr>
			</table>
			<br>
		</td>
	</tr>
</table>
	

</td>
</tr>
</table>
</body>
</html>
<%
}catch (Exception e){

	String theErrorOut = e.getMessage();
	session.setAttribute("gfsmsNewUserError",theErrorOut);
	response.sendRedirect("lostIdentity.jsp");
}
%>
<%@ include file="../include/buttom.jsp" %>    