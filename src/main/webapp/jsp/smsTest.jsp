<%@ page language="java" 
         import="org.celiac.web.bean.*, java.util.*" 
         errorPage="loginError.jsp" %>
                

<html>
<jsp:useBean id="User" scope="session" class="org.celiac.web.bean.User">
  <jsp:setProperty name="User" property="*"/>
</jsp:useBean>
<%@ include file="../include/header.jsp" %>    
<% if (!(User.getUser().getPERMISSIONS().equals("admin"))) throw new Exception("User was not login. Please login."); %>
<head>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link href="../include/theStyle.css" rel="stylesheet" type="text/css">
</head>


<%
request.setCharacterEncoding("utf-8");
 response.setContentType("text/html;charset=UTF-8");
try{

%>    

<%

String uploadStatus = null;
try{

uploadStatus = (String)session.getAttribute("uploadStatus");

}catch (Exception e){
}

%>

<table width="100%" height="90%" border="0" cellpadding="5">
	<tr valign="top">
		<td>
		<table width="100%" height="90%" border="1">
		<td valign="top" nowrap bgcolor="#f5f5e1" class="textSmall">
		<%@ include file="../include/upperMenu.jsp" %>
		</td>
		</table>
</td>
		<td width="100%">
			<table border="0" cellpadding="1" cellspacing="1" class="frameTableT">
			
			
			<form name="test_form" action="../sms" method="POST">
					
					<tr><td colspan="1" nowrap class="titleBigHeb">Gluten Free SMS Test</td></tr>
					
			
				
				<tr bgcolor="#FFFFFF">
					
				</tr>
				<tr>
				<tr>
				<tr>
					

  <tr>
    <tr><td colspan="1" nowrap class="headerUnderLine">Please set the params as got from SMS vendor:</td></tr>
    
    
    <td colspan="0" >
        <div align="center">
        <font face="Verdana, Arial, Helvetica, sans-serif">
        	 <tr>
          <td colspan="1"  style="width: 100%" class="headerUnderLine4">
        SMS Message:
          <input type="text" class="textfield" name="message" >
        </td>
      </tr>
      <tr>
          <td colspan="1"  style="width: 100%" class="headerUnderLine4">
        Cell Phone:
          <input type="text" class="textfield" name="cellPhone" >
        </td>
      </tr>
          <tr>
          <td>
          <input type="submit"  name="  Submit  " value="submit" class="textfield" enabled >
          </font></div>
        </td>
      </tr>
    </td>
  </tr>
  </tr>
</form>
					
			
			</table>
			<br>
		</td>
	</tr>
</table>



<%}catch(Exception e){
	e.printStackTrace();
}%>

<%@ include file="../include/buttom.jsp" %>  