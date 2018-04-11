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



<SCRIPT LANGUAGE="JavaScript">		

	function processAddNewUser()
	{
	  
	  document.manage_form1.choise.value="addNewUser";
	 
	 	  	  
	}
	
	function processAddBasicUser()
	{
	  
	  document.manage_form1.choise.value="addBasicUser";
	 
	 	  	  
	}
	
	function processAddFullUser()
	{
	  
	  document.manage_form1.choise.value="addFullUser";
	 
	 	  	  
	}
	
	function processFindAUser()
	{
		document.manage_form1.choise.value="findAUser";
	 	  	  
	}
	
	function processFindAllUser()
	{
		document.manage_form1.choise.value="findAllUser";
	 	  	  
	}
	
	function processAllUser()
	{
		document.manage_form1.choise.value="AllUser";
	 	  	  
	}
	
		function processSendUsersEmail()
	{
		document.manage_form1.choise.value="sendUsersEmail";
	 	  	  
	}
	
	
	
	function processSearchTZUser()
	{
		document.manage_form1.choise.value="findTZUser";
	 	  	  
	}
	
	function processSearchEmailUser()
	{
		document.manage_form1.choise.value="findEmailUser";
	 	  	  
	}
	
	function processSearchIDUser()
	{
		
		document.manage_form1.choise.value="findIDUser";
	 	  	  
	}
	
	function fullUScript()
	{
		var nameOfAction = fullUScript.arguments[0];
		
		//alert(nameOfAction + "Update" + document.manage_form3.fullU.id);
		document.manage_form3.choiseForFullUpdate.value=nameOfAction + "Update" + document.manage_form3.fullU.id;
	 
	 	  
	}
	
	
	
	
	
	

	
	
	
</SCRIPT>
</head>



<%



request.setCharacterEncoding("utf-8");
 response.setContentType("text/html;charset=UTF-8");
 GFUser aUser = (GFUser)session.getAttribute("A_USER");
 
 if (aUser == null) aUser = new GFUser();

String FIRST_NAME=aUser.getFIRST_NAME();
if (FIRST_NAME == null) FIRST_NAME = "";
String LAST_NAME=aUser.getLAST_NAME();
if (LAST_NAME == null) LAST_NAME = "";
String HISHUR_PASSWORD=aUser.getPASSWORD();
if (HISHUR_PASSWORD == null) HISHUR_PASSWORD = "";
String PASSWORD=aUser.getPASSWORD();
if (PASSWORD == null) PASSWORD = "";
String USER_ID=aUser.getUSER_ID();
if (USER_ID == null) USER_ID = "";
String CELL_NUM=aUser.getCELL_NUM();
if (CELL_NUM == null) CELL_NUM = "";
String EMAIL=aUser.getEMAIL();
if (EMAIL == null) EMAIL = "";
String EMAIL_ISHUR=aUser.getEMAIL();
if (EMAIL_ISHUR == null) EMAIL_ISHUR = "";
String CELIAC_MEMBER_ID = aUser.getCELIAC_MEMBER_ID();
if (CELIAC_MEMBER_ID == null) CELIAC_MEMBER_ID = "";
String USER_TZ = aUser.getUSER_TZ();
if (USER_TZ == null) USER_TZ = "";
Date exp_date = aUser.getEXPIRATION_DATE();
String EXPIRATION_DATE =   null;
if (exp_date == null) EXPIRATION_DATE = "";
else EXPIRATION_DATE = new java.text.SimpleDateFormat("d/M/yyyy").format(aUser.getEXPIRATION_DATE());
String LAST_UPDATE_WAS_PURCHASE = aUser.getDID_BUY_THE_BOOK();
if (LAST_UPDATE_WAS_PURCHASE == null) LAST_UPDATE_WAS_PURCHASE = "";
String PERMISSIONS = aUser.getPERMISSIONS();

String USER_EMAIL = aUser.getEMAIL();
if (USER_EMAIL == null) USER_EMAIL = "";


session.setAttribute("A_USER",null);



String errorMessage = (String)session.getAttribute("gfsmsNewUserError");
if (errorMessage == null) errorMessage = "";

session.setAttribute("gfsmsNewUserError",null);


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

int pageSize=25;
int lastPageWas=0;
if (request.getParameter("lastPageWas") != null){
		lastPageWas = new Integer((String)request.getParameter("lastPageWas")).intValue();
}


boolean updateActionFull = false;
if (action != null){
		if ("findTZUserUpdateFullU".equals(action)) {
			action = "findTZUser";
			updateActionFull = true;
		}
		if ("findIDUserUpdateFullU".equals(action)) {
			action = "findIDUser";
			updateActionFull = true;
		}
		if ("findEmailUserUpdateFullU".equals(action)) {
			action = "findEmailUser";
			updateActionFull = true;
		}
		
}



%>



<table WIDTH="500" height="90%" border="0" cellpadding="5">
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
			<table border="0"  WIDTH="650"  cellpadding="1" cellspacing="1" class="frameTableT">
		
			
			<form name="manage_form1" action="manageUser.jsp" method="POST">
					
					
			<input type="hidden"  name="choise" >
				
				
   
   
    

        
        
<tr><td>
        
<TABLE>      
<tr style="width: 500"><td colspan="1" nowrap style="width: 650" class="headerUnderLineHeb"><%=org.celiac.util.TemplateReader.getHebrewMapWord("MANAGE_USERS")%>:</td></tr>

<tr><td>
<TABLE name=selectTable BORDER="2" BORDERCOLOR="#D8D8D8" BGCOLOR="#FFFFFF" style="width: 650" CELLSPACING="1" CELLPADDING="3">

<TH  colspan="1"  style="width: 33%" class="headerUnderLine4"><input type="submit"  name="Submit" value="<%=org.celiac.util.TemplateReader.getHebrewMapWord("ADD_NEW_USER")%>" onClick="processAddNewUser();" style="width: 100%"  ></TH>
<TH  colspan="1"  style="width: 33%" class="headerUnderLine4"><input type="submit"  name="Submit" value="<%=org.celiac.util.TemplateReader.getHebrewMapWord("SEARCH_USER")%>" style="width: 100%"  onClick="processFindAUser();"></TH>
<TH  colspan="1"  style="width: 34%" class="headerUnderLine4"><input type="submit"  name="Submit" value="<%=org.celiac.util.TemplateReader.getHebrewMapWord("ALL_USERS")%>" style="width: 100%"  onClick="processAllUser();"></TH>

	<%
	if ((action != null) && (("addNewUser").equals(action) || ("addFullUser").equals(action) || ("addBasicUser").equals(action))){
	%>
	<tr>

<TH  colspan="1"  style="width: 25%" class="headerUnderLine4"><input type="submit"  name="Submit" value="<%=org.celiac.util.TemplateReader.getHebrewMapWord("ADD_BASIC_USER")%>" onClick="processAddBasicUser();" style="width: 100%" ></TH>
 <tr>
		<TH  colspan="1"  style="width: 25%" class="headerUnderLine4"><input type="submit"  name="Submit" value="<%=org.celiac.util.TemplateReader.getHebrewMapWord("ADD_FULL_USER")%>" style="width: 100%"  onClick="processAddFullUser();"></TH>
	</tr>
<%}%>
<%
	if ((action != null) && (("findAUser").equals(action) || ("findTZUser").equals(action) || ("findEmailUser").equals(action) || ("findIDUser").equals(action) || ("findEmailUserUpdateFullU").equals(action) || ("findIDUserUpdateFullU").equals(action) || ("findIDUserUpdateFullU").equals(action)) ){
	%>
	<tr>
		<td>
<TH  colspan="1"  style="width: 25%" class="headerUnderLine4"><input type="submit"  name="Submit" value="<%=org.celiac.util.TemplateReader.getHebrewMapWord("SEARCH_BASED_ON_TZ")%>" onClick="processSearchTZUser();" style="width: 100%" ></TH>
<tr><td>
		<TH  colspan="1"  style="width: 25%" class="headerUnderLine4"><input type="submit"  name="Submit" value="<%=org.celiac.util.TemplateReader.getHebrewMapWord("SEARCH_BASED_ON_MEMBER_ID")%>" style="width: 100%"  onClick="processSearchIDUser();"></TH>
	</tr>
	<tr><td>
		<TH  colspan="1"  style="width: 25%" class="headerUnderLine4"><input type="submit"  name="Submit" value="<%=org.celiac.util.TemplateReader.getHebrewMapWord("SEARCH_BASED_ON_EMAIL")%>" style="width: 100%"  onClick="processSearchEmailUser();"></TH>
	</tr>
<%}%>

<%
	if ((action != null) && (("AllUser").equals(action) || ("findAllUser").equals(action) || ("sendUsersEmail").equals(action)) ){
	%>
	<tr>
		<td><td>
<TH  colspan="1"  style="width: 25%" class="headerUnderLine4"><input type="submit"  name="Submit" value="<%=org.celiac.util.TemplateReader.getHebrewMapWord("FIND_ALL_USERS")%>" onClick="processFindAllUser();" style="width: 100%" ></TH>
<tr><td><td>
		<TH  colspan="1"  style="width: 25%" class="headerUnderLine4"><input type="submit"  name="Submit" value="<%=org.celiac.util.TemplateReader.getHebrewMapWord("SEND_EMAIL_ALL")%>" style="width: 100%"  onClick="processSendUsersEmail();"></TH>
	</tr>
<%}%>

</TABLE>
</td></tr>
</TABLE>
  
  </tr>
</td>
   <tr>
  	<td>
  	<br>
  </td>
	</tr>
	
	
	
	
	</form>
	
	
	<%if (!("").equals(errorMessage)){%>
			<tr><td align="right">	
			<font color=#ff0000><b><%=errorMessage%><b></font>
			<br>
			<br>
		</td></tr>
		 <%}%>
	
	
	
   <%
	 if ((action != null) && (("findAllUser").equals(action))){
	 		Set<GFUser> theAllUsers = org.celiac.util.database.DBQueryFactory.getDBHandler().getAllUserDetails();
	 		
	 		
	 %>
	 <form name="find_users" action="manageUser.jsp?choise=findAllUser" method="POST">
	 <tr>
  
  	<td width="650"  >
  		<TABLE name=selectTable BORDER="2" BORDERCOLOR="#D8D8D8" BGCOLOR="#FFFFFF" style="width: 100%" CELLSPACING="1" CELLPADDING="3">
  			<TH  colspan="1"  style="width: 8%" class="headerUnderLineForMultipleTrue"><%=org.celiac.util.TemplateReader.getHebrewMapWord("TOTAL_USERS")%> = <%=theAllUsers.size()%></TH>	
  		</TABLE>
	 	</td></tr>
	 
	    <tr>
  
  	<td width="650"  >
  		<TABLE name=selectTable BORDER="2" BORDERCOLOR="#D8D8D8" BGCOLOR="#FFFFFF" style="width: 100%" CELLSPACING="1" CELLPADDING="3">
  		<TH  colspan="1"  style="width: 8%" ></TH>	
  		<TH  colspan="1"  style="width: 8%" class="headerUnderLine4"><%=org.celiac.util.TemplateReader.getHebrewMapWord("FRAIND_NUM")%></TH>		
			<TH  colspan="1"  style="width: 14%" class="headerUnderLine4"><%=org.celiac.util.TemplateReader.getHebrewMapWord("FIRST_NAME")%></TH>
			<TH  colspan="1"  style="width: 14%" class="headerUnderLine4"><%=org.celiac.util.TemplateReader.getHebrewMapWord("LAST_NAME")%></TH>			
			<TH  colspan="1"  style="width: 18%" class="headerUnderLine4"><%=org.celiac.util.TemplateReader.getHebrewMapWord("USER_ID")%></TH>			
			<TH  colspan="1"  style="width: 18%" class="headerUnderLine4"><%=org.celiac.util.TemplateReader.getHebrewMapWord("CELL_NUM")%></TH>
			<TH  colspan="1"  style="width: 24%" class="headerUnderLine4"><%=org.celiac.util.TemplateReader.getHebrewMapWord("EMAIL")%></TH>
				
			
			<%
			
			
			GFUser userGFUsers = null;
			Iterator iterForUsers = theAllUsers.iterator();
			int i=0;
			while (iterForUsers.hasNext()){
			userGFUsers = (GFUser)iterForUsers.next();
			
			i++;
			if (i < (lastPageWas * pageSize) + 1) continue;
			
			if (i == (lastPageWas * pageSize) + pageSize + 1) {
					%><input type="hidden"  name="lastPageWas" value="<%=""+(lastPageWas+1)%>" ><%
					
					break;
			}
			%>
			
			<TR ALIGN="LEFT">
			<TD ALIGN="RIGHT" colspan="1"  nowrap style="width: 8%" class="headerUnderLine4"><%=i%></TD>
			<TD ALIGN="CENTER" colspan="1" nowrap style="width: 8%" class="headerUnderLine3"><%=userGFUsers.getCELIAC_MEMBER_ID()%></TD>
			<TD ALIGN="RIGHT" colspan="1" nowrap  style="width: 14%" class="headerUnderLine3"><%=userGFUsers.getFIRST_NAME()%></TD>
			<TD ALIGN="RIGHT" colspan="1" nowrap style="width: 14%" class="headerUnderLine3"><%=userGFUsers.getLAST_NAME()%></TD>
			<TD colspan="1" nowrap style="width: 18%" class="headerUnderLine3"><%=userGFUsers.getUSER_ID()%></TD>
			<TD colspan="1" nowrap style="width: 18%" class="headerUnderLine3"><%=userGFUsers.getCELL_NUM()%></TD>
			<TD colspan="1" nowrap style="width: 24%" class="headerUnderLine3"><%=userGFUsers.getEMAIL()%></TD>
			
			
			<%}%>

</TR>
																						
		</TABLE>
  	</td></tr>
  	<tr><td >
  	<table width="100%" border="0" cellpadding="1">
				<tr>
					  <td><input name="submit" type="submit" id="submit" value="Get More"  style="width: 100%" class="button" enabled onMouseOver="this.style.background =  '#d5e0e6';" onMouseOut="this.style.background = '#e4e4e4';"></td>
				</tr>
			</table>
		</td></tr>
	</form>
	 <%}%>	
	
	
	
	
	<%
	 if ((action != null) && (("sendUsersEmail").equals(action))){
	 		Set<GFUser> theAllUsers = org.celiac.util.database.DBQueryFactory.getDBHandler().getAllUserDetails();
	 		String BODY = (String)session.getAttribute("BODY");
	 		String SUBJECT = (String)session.getAttribute("SUBJECT");
	 		if (SUBJECT == null) SUBJECT = "";
	 		if (BODY == null) BODY = "";
	 		session.setAttribute("BODY",null);
	 		session.setAttribute("SUBJECT",null);
	 		
	 %>
	 <form name="manage_email" action="manageUser2.jsp?choise=sendUsersEmail" method="POST">
	 <tr>
  
  	<td width="650"  >
  		<TABLE name=selectTable BORDER="2" BORDERCOLOR="#D8D8D8" BGCOLOR="#FFFFFF" style="width: 650" CELLSPACING="1" CELLPADDING="3">
  			<TH  colspan="1"  style="width: 8%" class="headerUnderLineForMultipleTrue"><%=org.celiac.util.TemplateReader.getHebrewMapWord("SEND_EMAIL_ALL")%></TH>	
  		</TABLE>
	 	</td></tr>
	 
	    <tr>
  
  	 <td width="650"  >
  		<TABLE name=selectTable BORDER="2" BORDERCOLOR="#D8D8D8" BGCOLOR="#FFFFFF" style="width: 100%" CELLSPACING="1" CELLPADDING="3">
  			<TH  colspan="1"  style="width: 4%" class="headerUnderLine4"><%=org.celiac.util.TemplateReader.getHebrewMapWord("SUBJECT")%>:</TH>		
  		<TH  colspan="1"  style="width: 20%" ><input type="text" style="width: 100%" class="textBoxBHebForEmail" name="SUBJECT" value="<%=SUBJECT%>"></TH>
  		
				
																						
		</TABLE>
  	</td></tr>
  	
  	
  	 <tr>
  
  	 <td width="650"  >
  		<TABLE name=selectTable BORDER="2" BORDERCOLOR="#D8D8D8" BGCOLOR="#FFFFFF" style="width: 100%" CELLSPACING="1" CELLPADDING="3">
  		<TH  colspan="1"  style="width: 20%" ><textarea style="width: 100%" rows="17" name="userNote"  wrap="off"  ><%=BODY%></textarea></TH>															
		</TABLE>
  	</td></tr>
  	<tr><td >
  	<table width="650" border="0" cellpadding="1">
				<tr>
					  <td><input name="submit" type="submit" id="submit" value="<%=org.celiac.util.TemplateReader.getHebrewMapWord("SEND_EMAIL")%>"  style="width: 100%" class="button" enabled onMouseOver="this.style.background =  '#d5e0e6';" onMouseOut="this.style.background = '#e4e4e4';"></td>
				</tr>
			</table>
		</td></tr>
	 </form>
	 <%}%>	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	 <%
	 if ((action != null) && (("addBasicUser").equals(action) || ("addFullUser").equals(action))){
	 %>
	   
	   
	   
	    <tr>
  
  	<td width="100%"  >
			<table align="center" border="0"   cellpadding="1" cellspacing="1" class="frameTableT">
  	<form width="100%" name="manage_form2" action="manageUser2.jsp?choise<%="="+action%>" method="POST">
  		    <% if (("addBasicUser").equals(action)){ %>
					<tr><td colspan="1" nowrap class="titleBigHeb">:<%=org.celiac.util.TemplateReader.getHebrewMapWord("ADD_BASIC_USER")%></td></tr>
					<%}%>
					 <% if (("addFullUser").equals(action)){ %>
					<tr><td colspan="1" nowrap class="titleBigHeb">:<%=org.celiac.util.TemplateReader.getHebrewMapWord("ADD_FULL_USER")%></td></tr>
					<%}%>
			
					
				<tr class="textfieldHeb" >			
					<td height="20" nowrap ><strong>:<%=org.celiac.util.TemplateReader.getHebrewMapWord("CELIAC_MEMBER_ID")%></strong></td>
					<tr><td><input type="text" class="textBoxB" name="CELIAC_MEMBER_ID" value="<%=CELIAC_MEMBER_ID%>"></td></tr>
				</tr><tr></tr><tr></tr><tr></tr><tr></tr>
				
				<tr class="textfieldHeb" >			
					<td height="20" nowrap><strong>:<%=org.celiac.util.TemplateReader.getHebrewMapWord("USER_TZ")%></strong></td>
					<tr><td><input type="text" class="textBoxB" name="USER_TZ" value="<%=USER_TZ%>"></td></tr>
				</tr><tr></tr><tr></tr><tr></tr><tr></tr>
					
				<tr class="textfieldHeb" >			
					<td height="20" nowrap ><strong>:<%=org.celiac.util.TemplateReader.getHebrewMapWord("LAST_UPDATE_WAS_PURCHASE")%></strong></td>
					<tr><td><select name="LAST_UPDATE_WAS_PURCHASE" class="textBoxBHeb">
          	
          	<% if ((LAST_UPDATE_WAS_PURCHASE != null) && LAST_UPDATE_WAS_PURCHASE.equals("true")) { %>
          	<OPTION VALUE="false" >FALSE
          	<OPTION VALUE="true" selected="selected">TRUE
         
          <%}else{%>
          <OPTION VALUE="false" selected="selected">FALSE
          	<OPTION VALUE="true" >TRUE
          <%}%>
          	
		 				
					</select></td></tr>
				</tr><tr></tr><tr></tr><tr></tr><tr></tr>
				
				<tr class="textfieldHeb" >			
					<td height="20" nowrap><strong>:<%=org.celiac.util.TemplateReader.getHebrewMapWord("EXPIRATION_DATE")%></strong></td>
					<tr><td align="right"><input type="text"  class="textfield" name="EXPIRATION_DATE"  id="EXPIRATION_DATE" value="<%=EXPIRATION_DATE%>" READONLY><a colspan="1"  class="headerUnderLine" href="javascript: showCalendar('EXPIRATION_DATE')">Show calender</a></td></tr>
				</tr><tr></tr><tr></tr><tr></tr><tr></tr>
				
				<%
				if ((action != null) && ("addFullUser").equals(action)){
			  %>
	
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
					
				
					
					
	      <%}%>
				
				<tr bgcolor="#FFFFFF">
					
					
					
				</tr>
				<tr>
				<tr>
				<tr>
					<table width="100%" border="0" cellpadding="5">
				<tr>
						<td><input type="button" value="<%=org.celiac.util.TemplateReader.getHebrewMapWord("HAZOR")%>"  style="width: 100%" onClick="history.go(-1);" class="button" enabled onMouseOver="this.style.background =  '#d5e0e6';" onMouseOut="this.style.background = '#e4e4e4';" ></td>
					  <td><input name="submit" type="submit" id="submit" value="<%=org.celiac.util.TemplateReader.getHebrewMapWord("ADD_NEW_USER")%>"  style="width: 100%" class="button" enabled onMouseOver="this.style.background =  '#d5e0e6';" onMouseOut="this.style.background = '#e4e4e4';"></td>
				</tr>
			</table>
			</form>	
  	</table>
		</td>
  	
  	
  </td>
	</tr>
	 
	 <%}%>
	
	
    <%
	 if ((action != null) && (("findTZUser").equals(action) || ("findIDUser").equals(action) || ("findIDUserUpdateFullU").equals(action) || ("findEmailUser").equals(action) || ("findEmailUserUpdateFullU").equals(action) || ("findTZUserUpdateFullU").equals(action))){
	 %>
	   
	   
	   
	    <tr>
  
  	<td width="100%"  >
			<table align="center" border="0"   cellpadding="1" cellspacing="1" class="frameTableT">
  	<form width="100%" name="manage_form2" action="manageUser2.jsp?choise<%="="+action%>" method="POST">
  		    <% if ( ("findTZUser").equals(action) || ("findTZUserUpdateFullU").equals(action)){ %>
					<tr><td colspan="1" nowrap class="titleBigHeb">:<%=org.celiac.util.TemplateReader.getHebrewMapWord("SEARCH_BASED_ON_TZ")%></td></tr>
					<%}%>
					 <% if (("findIDUser").equals(action) || ("findIDUserUpdateFullU").equals(action)){ %>
					<tr><td colspan="1" nowrap class="titleBigHeb">:<%=org.celiac.util.TemplateReader.getHebrewMapWord("SEARCH_BASED_ON_MEMBER_ID")%></td></tr>
					<%}%>
					 <% if (("findEmailUser").equals(action) || ("findEmailUserUpdateFullU").equals(action)){ %>
					<tr><td colspan="1" nowrap class="titleBigHeb">:<%=org.celiac.util.TemplateReader.getHebrewMapWord("SEARCH_BASED_ON_EMAIL")%></td></tr>
					<%}%>
					
					
			
					 <% if (("findIDUser").equals(action) || ("findIDUserUpdateFullU").equals(action) ){ %>
				<tr class="textfieldHeb" >			
					<td height="20" nowrap ><strong>:<%=org.celiac.util.TemplateReader.getHebrewMapWord("CELIAC_MEMBER_ID")%></strong></td>
					<tr><td><input type="text" class="textBoxB" name="CELIAC_MEMBER_ID" value="<%=CELIAC_MEMBER_ID%>"></td></tr>
				</tr><tr></tr><tr></tr><tr></tr><tr></tr>
					<%}%>
				
				 <% if (("findTZUser").equals(action) || ("findTZUserUpdateFullU").equals(action)){ %>
				<tr class="textfieldHeb" >			
					<td height="20" nowrap><strong>:<%=org.celiac.util.TemplateReader.getHebrewMapWord("USER_TZ")%></strong></td>
					<tr><td><input type="text" class="textBoxB" name="USER_TZ" value="<%=USER_TZ%>"></td></tr>
				</tr><tr></tr><tr></tr><tr></tr><tr></tr>
				<%}%>
				
				 <% if (("findEmailUser").equals(action)){ %>
				<tr class="textfieldHeb" >			
					<td height="20" nowrap><strong>:<%=org.celiac.util.TemplateReader.getHebrewMapWord("EMAIL")%></strong></td>
					<tr><td><input type="text" class="textBoxB" name="USER_EMAIL" value="<%=USER_EMAIL%>"></td></tr>
				</tr><tr></tr><tr></tr><tr></tr><tr></tr>
				<%}%>
				
				<tr>
				<tr>
				<tr>
					<table width="100%" border="0" cellpadding="5">
				<tr>
						<td><input type="button" value="<%=org.celiac.util.TemplateReader.getHebrewMapWord("HAZOR")%>"  style="width: 100%" onClick="history.go(-1);" class="button" enabled onMouseOver="this.style.background =  '#d5e0e6';" onMouseOut="this.style.background = '#e4e4e4';" ></td>
					  <td><input name="submit" type="submit" id="submit" value="<%=org.celiac.util.TemplateReader.getHebrewMapWord("SEARCH_USER")%>"  style="width: 100%" class="button" enabled onMouseOver="this.style.background =  '#d5e0e6';" onMouseOut="this.style.background = '#e4e4e4';"></td>
				</tr>
			</table>
			</form>	
			
			<% if (aUser.getCELIAC_MEMBER_ID() != null){ %>
			
			<tr><td>
			
			<table align="center" border="0"   cellpadding="1" cellspacing="1" class="frameTableT">
				<form width="100%" name="manage_form3" action="manageUser2.jsp?choise<%="="+action%>Update" method="POST">
  		  <input type="hidden"  name="choiseForFullUpdate" >				
  		  
				<tr class="textfieldHeb" >			
					<td height="20" nowrap ><strong>:<%=org.celiac.util.TemplateReader.getHebrewMapWord("CELIAC_MEMBER_ID")%></strong></td>
					<tr><td><input type="text" class="textBoxBGrayed" name="CELIAC_MEMBER_ID" value="<%=CELIAC_MEMBER_ID%>" READONLY></td></tr>
				</tr><tr></tr><tr></tr><tr></tr><tr></tr>
				
				<tr class="textfieldHeb" >			
					<td height="20" nowrap><strong>:<%=org.celiac.util.TemplateReader.getHebrewMapWord("USER_TZ")%></strong></td>
					<tr><td><input type="text" class="textBoxBGrayed" name="USER_TZ" value="<%=USER_TZ%>" READONLY></td></tr>
				</tr><tr></tr><tr></tr><tr></tr><tr></tr>
				<% if (User.getUser().getPERMISSIONS().equals("admin")){ %>
				<tr class="textfieldHeb" >			
					<td height="20" nowrap ><strong>:<%=org.celiac.util.TemplateReader.getHebrewMapWord("LAST_UPDATE_WAS_PURCHASE")%></strong></td>
					<tr><td><select name="LAST_UPDATE_WAS_PURCHASE" class="textBoxBHeb">
          	
          	<% if ((LAST_UPDATE_WAS_PURCHASE != null) && LAST_UPDATE_WAS_PURCHASE.equals("true")) { %>
          	<OPTION VALUE="false" >FALSE
          	<OPTION VALUE="true" selected="selected">TRUE
         
          <%}else{%>
          <OPTION VALUE="false" selected="selected">FALSE
          	<OPTION VALUE="true" >TRUE
          <%}%>
          
          	</select></td></tr>
				   </tr><tr></tr><tr></tr><tr></tr><tr></tr>
				
				<tr class="textfieldHeb" >			
					<td height="20" nowrap><strong>:<%=org.celiac.util.TemplateReader.getHebrewMapWord("EXPIRATION_DATE")%></strong></td>
					<tr><td align="right"><input type="text"  class="textfield" name="EXPIRATION_DATE"  id="EXPIRATION_DATE" value="<%=EXPIRATION_DATE%>" READONLY><a colspan="1"  class="headerUnderLine" href="javascript: showCalendar('EXPIRATION_DATE')">Show calender</a></td></tr>
				</tr><tr></tr><tr></tr><tr></tr><tr></tr>
				
				<%}%>
          
          <% if (User.getUser().getPERMISSIONS().equals("mitnadev")){ %>
          <tr class="textfieldHeb" >			
					<td height="20" nowrap ><strong>:<%=org.celiac.util.TemplateReader.getHebrewMapWord("LAST_UPDATE_WAS_PURCHASE")%></strong></td>
					<tr><td><input type="text" class="textBoxBGrayed" name="LAST_UPDATE_WAS_PURCHASE" value="<%=LAST_UPDATE_WAS_PURCHASE%>" READONLY></td></tr>
				</tr><tr></tr><tr></tr><tr></tr><tr></tr>
				
				<tr class="textfieldHeb" >			
					<td height="20" nowrap><strong>:<%=org.celiac.util.TemplateReader.getHebrewMapWord("EXPIRATION_DATE")%></strong></td>
					<tr><td><input type="text" class="textBoxBGrayed" name="EXPIRATION_DATE" value="<%=EXPIRATION_DATE%>" READONLY></td></tr>
				</tr><tr></tr><tr></tr><tr></tr><tr></tr>
					<tr class="textfieldHeb" >			
					<td height="20" nowrap><strong>:<%=org.celiac.util.TemplateReader.getHebrewMapWord("TYPE_OF_USER")%></strong></td>
					<tr><td><input type="text" class="textBoxBGrayed" name="PERMISSIONS" value="<%=PERMISSIONS%>" READONLY></td></tr>
				</tr><tr></tr><tr></tr><tr></tr><tr></tr>
					<%}%>
					
					
		 				
				
				
		    <% if ((PERMISSIONS != null) || (updateActionFull)){ %>
	
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
	      
	      <% if (User.getUser().getPERMISSIONS().equals("admin")){ %>
	      <tr class="textfieldHeb" >			
					<td height="20" nowrap ><strong>:<%=org.celiac.util.TemplateReader.getHebrewMapWord("TYPE_OF_USER")%></strong></td>
					<tr><td><select name="PERMISSIONS" class="textBoxBHeb">
          	
          	<% if ((PERMISSIONS != null) && PERMISSIONS.equals("admin")) { %>
          	<OPTION VALUE="sim" ><%=org.celiac.util.TemplateReader.getHebrewMapWord("USER")%>
          	<OPTION VALUE="mitnadev" ><%=org.celiac.util.TemplateReader.getHebrewMapWord("MITNADEV")%>
          	<OPTION VALUE="admin" selected="selected"><%=org.celiac.util.TemplateReader.getHebrewMapWord("ADMIN")%>
         
         
         
          <%}else if ((PERMISSIONS != null) && PERMISSIONS.equals("mitnadev")) { %>
            <OPTION VALUE="sim" ><%=org.celiac.util.TemplateReader.getHebrewMapWord("USER")%>
          	<OPTION VALUE="mitnadev" selected="selected"><%=org.celiac.util.TemplateReader.getHebrewMapWord("MITNADEV")%>
          	<OPTION VALUE="admin" ><%=org.celiac.util.TemplateReader.getHebrewMapWord("ADMIN")%>
                    
         <%}else if ((PERMISSIONS != null) && PERMISSIONS.equals("uploader")) { %>
            <OPTION VALUE="uploader" >uploader
          	
                    
          <%} else{%>
            <OPTION VALUE="sim" selected="selected"><%=org.celiac.util.TemplateReader.getHebrewMapWord("USER")%>
          	<OPTION VALUE="mitnadev" ><%=org.celiac.util.TemplateReader.getHebrewMapWord("MITNADEV")%>
          	<OPTION VALUE="admin" ><%=org.celiac.util.TemplateReader.getHebrewMapWord("ADMIN")%>
          <%}%>
          	
		 				
					</select></td></tr>
				</tr><tr></tr><tr></tr><tr></tr><tr></tr>
				<%}}%>
				
				<tr>
				<tr>
				<tr>
					<table width="100%" border="0" cellpadding="5">
				<tr>
						<td><input type="button" value="<%=org.celiac.util.TemplateReader.getHebrewMapWord("HAZOR")%>"  style="width: 100%" onClick="history.go(-1);" class="button" enabled onMouseOver="this.style.background =  '#d5e0e6';" onMouseOut="this.style.background = '#e4e4e4';" ></td>
					  <td><input name="submit" type="submit" id="submit" value="<%=org.celiac.util.TemplateReader.getHebrewMapWord("HADKEN")%>"  style="width: 100%" class="button" enabled onMouseOver="this.style.background =  '#d5e0e6';" onMouseOut="this.style.background = '#e4e4e4';"></td>
					  <% if (PERMISSIONS == null && !updateActionFull){ %>
					  <td><input name="fullU" type="submit" id="FullU" value="<%=org.celiac.util.TemplateReader.getHebrewMapWord("ADD_FULL_USER")%>"  style="width: 100%" class="button" enabled onClick="fullUScript('<%=action%>');" onMouseOver="this.style.background =  '#d5e0e6';" onMouseOut="this.style.background = '#e4e4e4';"></td>
					  <%}%>
					  <% if (PERMISSIONS != null && !updateActionFull){ %>
					  <td><input name="fullU" type="submit" id="Delete" value="<%=org.celiac.util.TemplateReader.getHebrewMapWord("DELETE_USER")%>"  style="width: 100%" class="button" enabled onClick="fullUScript('<%=action%>');" onMouseOver="this.style.background =  '#d5e0e6';" onMouseOut="this.style.background = '#e4e4e4';"></td>
					  <%}%>
				</tr>
			</table>
			</form>	
			</td></tr>
			<%}%>
			
  	</table>
		</td>
  	
  </td>
	</tr>
	 
	 <%}%>

  	</table>
			<br>
		</td>
	</tr>
</table>





<%@ include file="../include/buttom.jsp" %>    