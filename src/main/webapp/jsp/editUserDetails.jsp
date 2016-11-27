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





String FIRST_NAME=User.theUser.getFIRST_NAME();
if (request.getParameter("FIRST_NAME") != null){
		FIRST_NAME = (String)request.getParameter("FIRST_NAME");
		session.setAttribute("FIRST_NAME",FIRST_NAME);
} 

String LAST_NAME=User.theUser.getLAST_NAME();
if (request.getParameter("LAST_NAME") != null){
		LAST_NAME = (String)request.getParameter("LAST_NAME");
		session.setAttribute("LAST_NAME",LAST_NAME);
} 

String HISHUR_PASSWORD=User.theUser.getPASSWORD();
if (request.getParameter("HISHUR_PASSWORD") != null){
		HISHUR_PASSWORD = (String)request.getParameter("HISHUR_PASSWORD");
		session.setAttribute("HISHUR_PASSWORD",HISHUR_PASSWORD);
}	

String PASSWORD=User.theUser.getPASSWORD();
if (request.getParameter("PASSWORD") != null){
		PASSWORD = (String)request.getParameter("PASSWORD");
		session.setAttribute("PASSWORD",PASSWORD);
}		

String USER_ID=User.theUser.getUSER_ID();
if (request.getParameter("USER_ID") != null){
		USER_ID = (String)request.getParameter("USER_ID");
		session.setAttribute("USER_ID",USER_ID);
}		

String CELL_NUM=User.theUser.getCELL_NUM();
if (request.getParameter("CELL_NUM") != null){
		CELL_NUM = (String)request.getParameter("CELL_NUM");
		session.setAttribute("CELL_NUM",CELL_NUM);
}		

String EMAIL=User.theUser.getEMAIL();
if (request.getParameter("EMAIL") != null){
		EMAIL = (String)request.getParameter("EMAIL");
		session.setAttribute("EMAIL",EMAIL);
}		

String EMAIL_ISHUR=User.theUser.getEMAIL();
if (request.getParameter("EMAIL_ISHUR") != null){
		EMAIL_ISHUR = (String)request.getParameter("EMAIL_ISHUR");
		session.setAttribute("EMAIL_ISHUR",EMAIL_ISHUR);
}		

String CELIAC_MEMBER_ID = User.theUser.getCELIAC_MEMBER_ID();
String USER_TZ = User.theUser.getUSER_TZ();
String EXPIRATION_DATE = new java.text.SimpleDateFormat("d/M/yyyy").format(User.theUser.getEXPIRATION_DATE());
String LAST_UPDATE_WAS_PURCHASE = User.theUser.getDID_BUY_THE_BOOK();







String errorMessage = (String)session.getAttribute("gfsmsNewUserError");
if (errorMessage == null) errorMessage = "";

session.setAttribute("gfsmsNewUserError",null);

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
	<tr valign="top" >
		<td>
		<table width="100%" height="90%" border="1">
		<td valign="top" nowrap bgcolor="#f5f5e1" class="textSmall">
		<%@ include file="../include/upperMenu.jsp" %>
		</td>
		</table>
</td>
<td ></td>
		<td WIDTH="500" >
			<table  border="0"   cellpadding="1" cellspacing="1" class="frameTableT">
			 <%if (!("").equals(errorMessage)){%>
			<tr><td align="right">	
			<font color=#ff0000><b><%=errorMessage%><b></font>
			<br>
			<br>
		</td></tr>
		 <%}%>
			
			<form width="100%" name="result" action="editUserDetails2.jsp" method="POST">
					<tr><td colspan="1" nowrap class="titleBigHeb"><%=org.celiac.util.TemplateReader.getHebrewMapWord("EDIT_USER_DETAILS")%>:</td></tr>
			<tr class="textfieldHeb" >			
					<td height="20" nowrap ><strong><%=org.celiac.util.TemplateReader.getHebrewMapWord("FIRST_NAME")%>:</strong></td>
					<tr><td><input type="text" class="textBoxBHeb" name="FIRST_NAME" value="<%=FIRST_NAME%>"></td></tr>
				</tr><tr></tr><tr></tr><tr></tr><tr></tr>
				<tr class="textfieldHeb" >			
					<td height="20" nowrap><strong><%=org.celiac.util.TemplateReader.getHebrewMapWord("LAST_NAME")%>:</strong></td>
					<tr><td><input type="text" class="textBoxBHeb" name="LAST_NAME" value="<%=LAST_NAME%>"></td></tr>
				</tr><tr></tr><tr></tr><tr></tr><tr></tr>
					
				<tr class="textfieldHeb" >			
					<td height="20" nowrap><strong><%=org.celiac.util.TemplateReader.getHebrewMapWord("EMAIL")%>:</strong></td>
					<tr><td><input type="text" class="textBoxB" name="EMAIL" value="<%=EMAIL%>"></td></tr>
				</tr><tr></tr><tr></tr><tr></tr><tr></tr>
					<tr class="textfieldHeb" >			
					<td height="20" nowrap><strong><%=org.celiac.util.TemplateReader.getHebrewMapWord("HASHER")%> <%=org.celiac.util.TemplateReader.getHebrewMapWord("EMAIL")%>:</strong></td>
					<tr><td><input type="text" class="textBoxB" name="EMAIL_ISHUR" value="<%=EMAIL_ISHUR%>"></td></tr>
				</tr><tr></tr><tr></tr><tr></tr><tr></tr>
				<tr class="textfieldHeb" >			
					<td height="20" nowrap><strong><%=org.celiac.util.TemplateReader.getHebrewMapWord("CELL_NUM")%>:</strong></td>
					<tr><td><input type="text" class="textBoxB" name="CELL_NUM" value="<%=CELL_NUM%>"></td></tr>
				</tr><tr></tr><tr></tr><tr></tr><tr></tr>
				<tr class="textfieldHeb" >			
					<td height="20" nowrap><strong><%=org.celiac.util.TemplateReader.getHebrewMapWord("USER_ID")%>:</strong></td>
					<tr><td><input type="text" class="textBoxB" name="USER_ID" value="<%=USER_ID%>"></td></tr>
				</tr><tr></tr><tr></tr><tr></tr><tr></tr>
				<tr class="textfieldHeb" >			
					<td height="20" nowrap><strong><%=org.celiac.util.TemplateReader.getHebrewMapWord("PASSWORD")%>:</strong></td>
					<tr><td><input type="text" class="textBoxB" name="PASSWORD" value="<%=PASSWORD%>"></td></tr>
				</tr><tr></tr><tr></tr><tr></tr><tr></tr>
				<tr class="textfieldHeb" >			
					<td height="20" nowrap><strong><%=org.celiac.util.TemplateReader.getHebrewMapWord("HISHUR_PASSWORD")%>:</strong></td>
					<tr><td><input type="text" class="textBoxB" name="HISHUR_PASSWORD" value="<%=HISHUR_PASSWORD%>"></td></tr>
				</tr><tr></tr><tr></tr><tr></tr><tr></tr>
					
				<tr class="textfieldHeb" >			
					<td height="20" nowrap ><strong><%=org.celiac.util.TemplateReader.getHebrewMapWord("CELIAC_MEMBER_ID")%>:</strong></td>
					<tr><td><input type="text" class="textBoxBGrayed" name="CELIAC_MEMBER_ID" value="<%=CELIAC_MEMBER_ID%>" READONLY></td></tr>
				</tr><tr></tr><tr></tr><tr></tr><tr></tr>
				
				<tr class="textfieldHeb" >			
					<td height="20" nowrap><strong><%=org.celiac.util.TemplateReader.getHebrewMapWord("USER_TZ")%>:</strong></td>
					<tr><td><input type="text" class="textBoxBGrayed" name="USER_TZ" value="<%=USER_TZ%>" READONLY></td></tr>
				</tr><tr></tr><tr></tr><tr></tr><tr></tr>
					
				<tr class="textfieldHeb" >			
					<td height="20" nowrap ><strong><%=org.celiac.util.TemplateReader.getHebrewMapWord("LAST_UPDATE_WAS_PURCHASE")%>:</strong></td>
					<tr><td><input type="text" class="textBoxBGrayed" name="LAST_UPDATE_WAS_PURCHASE" value="<%=LAST_UPDATE_WAS_PURCHASE%>" READONLY></td></tr>
				</tr><tr></tr><tr></tr><tr></tr><tr></tr>
				
				<tr class="textfieldHeb" >			
					<td height="20" nowrap><strong><%=org.celiac.util.TemplateReader.getHebrewMapWord("EXPIRATION_DATE")%>:</strong></td>
					<tr><td><input type="text" class="textBoxBGrayed" name="EXPIRATION_DATE" value="<%=EXPIRATION_DATE%>" READONLY></td></tr>
				</tr><tr></tr><tr></tr><tr></tr><tr></tr>
				
							
	
	
				
				<tr bgcolor="#FFFFFF">
					
					
					
				</tr>
				<tr>
				<tr>
				<tr>
					<table width="100%" border="0" cellpadding="5">
				<tr>
						<td><input type="button" value="<%=org.celiac.util.TemplateReader.getHebrewMapWord("HAZOR")%>"  style="width: 100%" onClick="history.go(-1);" class="button" enabled onMouseOver="this.style.background =  '#d5e0e6';" onMouseOut="this.style.background = '#e4e4e4';" ></td>
					  <td><input name="submit" type="submit" id="submit" value="<%=org.celiac.util.TemplateReader.getHebrewMapWord("SHMOR_SHIMUIM")%>"  style="width: 100%" class="button" enabled onMouseOver="this.style.background =  '#d5e0e6';" onMouseOut="this.style.background = '#e4e4e4';"></td>
				</tr>
			</table>
			</form>	
	
	
			
		
  	</table>
			<br>
	




<%@ include file="../include/buttom.jsp" %>    