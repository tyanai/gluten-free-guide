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
      
<p align="center">
		
<table bgcolor= "#f5f5e1" width="50%" border="1">
<td valign="center"><img src="../images/celiac_logo.jpg"></td>
		
<td>
<%

String FIRST_NAME="";
if (request.getParameter("FIRST_NAME") != null){
		FIRST_NAME = (String)request.getParameter("FIRST_NAME").trim();
		session.setAttribute("FIRST_NAME",FIRST_NAME);
} else if (session.getAttribute("FIRST_NAME") != null){
	  FIRST_NAME = (String)session.getAttribute("FIRST_NAME");
}

String LAST_NAME="";
if (request.getParameter("LAST_NAME") != null){
		LAST_NAME = (String)request.getParameter("LAST_NAME").trim();
		session.setAttribute("LAST_NAME",LAST_NAME);
} else if (session.getAttribute("LAST_NAME") != null){
	  LAST_NAME = (String)session.getAttribute("LAST_NAME");
}

String HISHUR_PASSWORD="";
if (request.getParameter("HISHUR_PASSWORD") != null){
		HISHUR_PASSWORD = (String)request.getParameter("HISHUR_PASSWORD").trim();
		session.setAttribute("HISHUR_PASSWORD",HISHUR_PASSWORD);
}	else if (session.getAttribute("HISHUR_PASSWORD") != null){
	  HISHUR_PASSWORD = (String)session.getAttribute("HISHUR_PASSWORD");
}	

String PASSWORD="";
if (request.getParameter("PASSWORD") != null){
		PASSWORD = (String)request.getParameter("PASSWORD").trim();
		session.setAttribute("PASSWORD",PASSWORD);
}		else if (session.getAttribute("PASSWORD") != null){
	  PASSWORD = (String)session.getAttribute("PASSWORD");
}

String USER_ID="";
if (request.getParameter("USER_ID") != null){
		USER_ID = (String)request.getParameter("USER_ID").trim();
		session.setAttribute("USER_ID",USER_ID);
}		else if (session.getAttribute("USER_ID") != null){
	  USER_ID = (String)session.getAttribute("USER_ID");
}

String CELL_NUM="";
if (request.getParameter("CELL_NUM") != null){
		CELL_NUM = (String)request.getParameter("CELL_NUM").trim();
		session.setAttribute("CELL_NUM",CELL_NUM);
}		else if (session.getAttribute("CELL_NUM") != null){
	  CELL_NUM = (String)session.getAttribute("CELL_NUM");
}

String EMAIL="";
if (request.getParameter("EMAIL") != null){
		EMAIL = (String)request.getParameter("EMAIL").trim();
		session.setAttribute("EMAIL",EMAIL);
}		else if (session.getAttribute("EMAIL") != null){
	  EMAIL = (String)session.getAttribute("EMAIL");
}

String EMAIL_ISHUR="";
if (request.getParameter("EMAIL_ISHUR") != null){
		EMAIL_ISHUR = (String)request.getParameter("EMAIL_ISHUR").trim();
		session.setAttribute("EMAIL_ISHUR",EMAIL_ISHUR);
}		else if (session.getAttribute("EMAIL_ISHUR") != null){
	  EMAIL_ISHUR = (String)session.getAttribute("EMAIL_ISHUR");
}

String CELIAC_MEMBER_ID=null;
if (request.getParameter("CELIAC_MEMBER_ID") != null){
		CELIAC_MEMBER_ID = (String)request.getParameter("CELIAC_MEMBER_ID").trim();
		CELIAC_MEMBER_ID = User.cutLeadingZero(CELIAC_MEMBER_ID);
		session.setAttribute("CELIAC_MEMBER_ID",CELIAC_MEMBER_ID);
} else if (session.getAttribute("CELIAC_MEMBER_ID") != null){
	  CELIAC_MEMBER_ID = (String)session.getAttribute("CELIAC_MEMBER_ID");
	  CELIAC_MEMBER_ID = User.cutLeadingZero(CELIAC_MEMBER_ID);
}

String USER_TZ=null;
if (request.getParameter("USER_TZ") != null){
		USER_TZ = (String)request.getParameter("USER_TZ").trim();
		session.setAttribute("USER_TZ",USER_TZ);
}		else if (session.getAttribute("USER_TZ") != null){
	  USER_TZ = (String)session.getAttribute("USER_TZ");
}

try{

if (CELIAC_MEMBER_ID == null || ("").equals(CELIAC_MEMBER_ID)) {

   throw new Exception(org.celiac.util.TemplateReader.getHebrewMapWord("ANA_SAPEK") + " " + org.celiac.util.TemplateReader.getHebrewMapWord("CELIAC_MEMBER_ID") );
   
}

if (USER_TZ == null || ("").equals(USER_TZ)) {

   throw new Exception(org.celiac.util.TemplateReader.getHebrewMapWord("ANA_SAPEK") + " " + org.celiac.util.TemplateReader.getHebrewMapWord("USER_TZ") );
   
}

User.validateUser(CELIAC_MEMBER_ID.trim(),USER_TZ.trim());

GFUser gfUser = User.getUser(CELIAC_MEMBER_ID.trim());
if (gfUser == null) throw new Exception(org.celiac.util.TemplateReader.getHebrewMapWord("CELIAC_MEMBER_ID") + " " + org.celiac.util.TemplateReader.getHebrewMapWord("LO_KAYAM") );
if (!(gfUser.getUSER_TZ().equals(USER_TZ.trim()))) throw new Exception(org.celiac.util.TemplateReader.getHebrewMapWord("USER_TZ") + " " + org.celiac.util.TemplateReader.getHebrewMapWord("LO_TOHAMIM"));
if (gfUser.getPASSWORD() != null)  throw new Exception(org.celiac.util.TemplateReader.getHebrewMapWord("MISHTAMESH_KAYAM") + " " + CELIAC_MEMBER_ID + ": '" + gfUser.getUSER_ID()+ "'" );
if (gfUser.getEXPIRATION_DATE().getTime() < new java.util.Date().getTime()) {
  if (!User.maybeUserIsNoExpire()){
    java.text.DateFormat df = new java.text.SimpleDateFormat("d/M/yyyy"); 
	  throw new Exception(org.celiac.util.TemplateReader.getHebrewMapWord("CELIAC_MEMBER_ID") + ": '" + CELIAC_MEMBER_ID + "' " + org.celiac.util.TemplateReader.getHebrewMapWord("LO_BETOKEF") + ": " + df.format(gfUser.getEXPIRATION_DATE())  );
	}
}
//if (gfUser.getDID_BUY_THE_BOOK().equals("false")) throw new Exception(org.celiac.util.TemplateReader.getHebrewMapWord("ONLY_BUY_BOOK")  );
session.setAttribute("EXPIRATION_DATE",gfUser.getEXPIRATION_DATE());
session.setAttribute("DID_BUY_THE_BOOK",gfUser.getDID_BUY_THE_BOOK());


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
			
			<form name="result" action="newUserTOU.jsp" method="POST">
				<tr>
					<td colspan="6" nowrap class="headerUnderLine2Heb"><%=org.celiac.util.TemplateReader.getHebrewMapWord("MISHTAMESH_HADASH")%></td>
				</tr>
				<tr></tr>
				
				<tr class="textfieldHeb" >			
					<td height="20" nowrap ><strong>:<%=org.celiac.util.TemplateReader.getHebrewMapWord("FIRST_NAME")%></strong></td>
					<tr><td><input type="text" class="textBoxBHeb" name="FIRST_NAME" value="<%=FIRST_NAME%>"></td></tr>
				</tr><tr></tr><tr></tr><tr></tr><tr></tr>
				<tr class="textfieldHeb" >			
					<td height="20" nowrap><strong>:<%=org.celiac.util.TemplateReader.getHebrewMapWord("LAST_NAME")%></strong></td>
					<tr><td><input type="text" class="textBoxBHeb" name="LAST_NAME" value="<%=LAST_NAME%>"></td></tr>
				</tr><tr></tr><tr></tr><tr></tr><tr></tr>
					
				<tr class="textfieldHeb" >			
					<td height="20" nowrap><strong>:<%=org.celiac.util.TemplateReader.getHebrewMapWord("EMAIL")%></strong></td>
					<tr><td><input type="text" class="textBoxB" name="EMAIL" value="<%=EMAIL%>"></td></tr>
				</tr><tr></tr><tr></tr><tr></tr><tr></tr>
					<tr class="textfieldHeb" >			
					<td height="20" nowrap><strong>:<%=org.celiac.util.TemplateReader.getHebrewMapWord("HASHER")%> <%=org.celiac.util.TemplateReader.getHebrewMapWord("EMAIL")%></strong></td>
					<tr><td><input type="text" class="textBoxB" name="EMAIL_ISHUR" value="<%=EMAIL_ISHUR%>"></td></tr>
				</tr><tr></tr><tr></tr><tr></tr><tr></tr>
				<tr class="textfieldHeb" >			
					<td height="20" nowrap><strong>:<%=org.celiac.util.TemplateReader.getHebrewMapWord("CELL_NUM")%></strong></td>
					<tr><td><input type="text" class="textBoxB" name="CELL_NUM" value="<%=CELL_NUM%>"></td></tr>
				</tr><tr></tr><tr></tr><tr></tr><tr></tr>
				<tr class="textfieldHeb" >			
					<td height="20" nowrap><strong>:<%=org.celiac.util.TemplateReader.getHebrewMapWord("USER_ID")%></strong></td>
					<tr><td><input type="text" class="textBoxB" name="USER_ID" value="<%=USER_ID%>"></td></tr>
				</tr><tr></tr><tr></tr><tr></tr><tr></tr>
				<tr class="textfieldHeb" >			
					<td height="20" nowrap><strong>:<%=org.celiac.util.TemplateReader.getHebrewMapWord("PASSWORD")%></strong></td>
					<tr><td><input type="text" class="textBoxB" name="PASSWORD" value="<%=PASSWORD%>"></td></tr>
				</tr><tr></tr><tr></tr><tr></tr><tr></tr>
				<tr class="textfieldHeb" >			
					<td height="20" nowrap><strong>:<%=org.celiac.util.TemplateReader.getHebrewMapWord("HISHUR_PASSWORD")%></strong></td>
					<tr><td><input type="text" class="textBoxB" name="HISHUR_PASSWORD" value="<%=HISHUR_PASSWORD%>"></td></tr>
				</tr><tr></tr><tr></tr><tr></tr><tr></tr>
				<tr class="textfieldHeb" >			
					<td height="20" nowrap ><strong>:<%=org.celiac.util.TemplateReader.getHebrewMapWord("CELIAC_MEMBER_ID")%></strong></td>
					<tr><td><input type="text" class="textBoxBGrayed" name="CELIAC_MEMBER_ID" value="<%=CELIAC_MEMBER_ID%>" READONLY></td></tr>
				</tr><tr></tr><tr></tr><tr></tr><tr></tr>
				
				<tr class="textfieldHeb" >			
					<td height="20" nowrap><strong>:<%=org.celiac.util.TemplateReader.getHebrewMapWord("USER_TZ")%></strong></td>
					<tr><td><input type="text" class="textBoxBGrayed" name="USER_TZ" value="<%=USER_TZ%>" READONLY></td></tr>
				</tr><tr></tr><tr></tr><tr></tr><tr></tr>
					
	
				
				<tr bgcolor="#FFFFFF">
					
					<tr>
						<td colspan="6" nowrap class="headerUnderLine2Heb"><%=org.celiac.util.TemplateReader.getHebrewMapWord("COL_HASADOT_CHOVA")%></td>
					</tr>
					
				</tr>
				<tr>
				<tr>
				<tr>
					<table width="100%" border="0" cellpadding="5">
				<tr>
						<td><input type="button" value="<%=org.celiac.util.TemplateReader.getHebrewMapWord("HAZOR")%>"  style="width: 100%" onClick="history.go(-1);" class="button" enabled onMouseOver="this.style.background =  '#d5e0e6';" onMouseOut="this.style.background = '#e4e4e4';" ></td>
					  <td><input name="submit" type="submit" id="submit" value="<%=org.celiac.util.TemplateReader.getHebrewMapWord("HAMSHECH")%>"  style="width: 100%" class="button" enabled onMouseOver="this.style.background =  '#d5e0e6';" onMouseOut="this.style.background = '#e4e4e4';" ></td>
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
	response.sendRedirect("newUser.jsp");
}
%>
<%@ include file="../include/buttom.jsp" %>    