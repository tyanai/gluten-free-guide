<%@ page language="java" 
         import="org.celiac.api.GlutenFreeAPI, org.celiac.web.bean.*, java.util.*" 
         errorPage="loginError.jsp" %>
<jsp:useBean id="User" scope="session" class="org.celiac.web.bean.User">
  <jsp:setProperty name="User" property="*"/>
</jsp:useBean>
<%User.setTempUser();%>
<%@ include file="../include/header.jsp" %>  



<body bgcolor="#ffffff" LEFTMARGIN="10" RIGHTMARGIN="10"
      link="#3366cc" vlink="#9999cc" alink="#0000cc">
      
<p align="center">

<table bgcolor= "#f5f5e1" width="50%" border="1" style="max-width: 400px;">
<td valign="center"><img src="../images/celiac_logo.jpg"></td>
		
<td>
<%

String errorMessage = (String)session.getAttribute("gfsmsNewUserError");
if (errorMessage == null) errorMessage = "";
session.setAttribute("gfsmsNewUserError",null);
	
		

String CELIAC_MEMBER_ID="";
if (request.getParameter("CELIAC_MEMBER_ID") != null){
		CELIAC_MEMBER_ID = (String)request.getParameter("CELIAC_MEMBER_ID");
		
}else if (session.getAttribute("CELIAC_MEMBER_ID") != null){
	  CELIAC_MEMBER_ID = (String)session.getAttribute("CELIAC_MEMBER_ID");
}

String USER_TZ="";
if (request.getParameter("USER_TZ") != null){
		USER_TZ = (String)request.getParameter("USER_TZ");
		
}		else if (session.getAttribute("USER_TZ") != null){
	  USER_TZ = (String)session.getAttribute("USER_TZ");
}


%>

	<table width="100%" border="0" cellpadding="5">
	<tr valign="top"><td valign="top">
	
			
		  <%if (!("").equals(errorMessage)){%>
			<tr><td align="right">	
			<font color=#ff0000><b><%=errorMessage%><b></font>
			<br>
			<br>
		</td></tr>
		 <%}%>
			
			<form name="result" action="newUserCon.jsp" method="POST">
				<tr>
					<td colspan="6" nowrap class="headerUnderLine2Heb"><%=org.celiac.util.TemplateReader.getHebrewMapWord("MISHTAMESH_HADASH")%></td>
				</tr>
				<tr></tr>
				
				<tr class="textfieldHeb" >			
                                    <td height="20" nowrap ><strong><%=org.celiac.util.TemplateReader.getHebrewMapWord("CELIAC_MEMBER_ID")%>:</strong></br> <p style="font-size:11px;">(<%=org.celiac.util.TemplateReader.getHebrewMapWord("CELIAC_MEMBER_HELP_LINK")%> <strong><a href="http://www.celiac.org.il/userareas?stage=friends" target="_blank"><%=org.celiac.util.TemplateReader.getHebrewMapWord("CLICK_HERE_LINK")%></a></strong>)</p></td>
					<tr><td><input type="text" class="textBoxB" name="CELIAC_MEMBER_ID" value="<%=CELIAC_MEMBER_ID%>"></td></tr>
				</tr><tr></tr><tr></tr><tr></tr><tr></tr>
				
				<tr class="textfieldHeb" >			
					<td height="20" nowrap><strong><%=org.celiac.util.TemplateReader.getHebrewMapWord("USER_TZ")%>:</strong></td>
					<tr><td><input type="text" class="textBoxB" name="USER_TZ" value="<%=USER_TZ%>"></td></tr>
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
				<tr><td><input name="submit" type="submit" id="submit" value="<%=org.celiac.util.TemplateReader.getHebrewMapWord("HAMSHECH")%>"  style="width: 100%" class="button" enabled onMouseOver="this.style.background =  '#d5e0e6';" onMouseOut="this.style.background = '#e4e4e4';" ></td></tr>
			</td></tr>
					</table>
			</form>	
			</table>
			<br>
		

<%@ include file="../include/buttom.jsp" %>    