<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 3.2//EN">
<html DIR="RTL">
<head>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
request.setCharacterEncoding("utf-8");
 response.setContentType("text/html;charset=UTF-8");
%>
<link href="include/theStyle.css" rel="stylesheet" type="text/css">
<link rel="shortcut icon" href="./images/favicon.ico" >
<title>GFGuide - מדריך למזון נטול גלוטן באינטרנט - עמותת צליאק בישראל</title>
<meta name="keywords" content="גלוטן, נ"ג, צליאק,מדריך,מוצרים,נטול גלוטן,ללא גלוטן," />
<meta name="description" content="מתן אפשרות לחולי צליאק לבדוק האם מוצרים המשווקים בישראל מכילים גלוטן. המדריך מתוחזק ע"י עמותת צליאק בישראל ומעודכן באופן שוטף." />
</head>

<%
request.setCharacterEncoding("utf-8");
 response.setContentType("text/html;charset=UTF-8");
%>
<body bgcolor="#ffffff" LEFTMARGIN="10" RIGHTMARGIN="10"
      link="#3366cc" vlink="#9999cc" alink="#0000cc"
      onload="document.login_form.user_id.focus();saveVersionDB('old')" > 
<%
String errorMessage = (String)session.getAttribute("gfsmsLoginError");
if (errorMessage == null) errorMessage = "";
else session.setAttribute("gfsmsLoginError",null);
%>

<table height="100%" width="100%" align="center" border="0">




<tr valign="top"><td height="25">
	

<table  width="100%" height="25"  border="0">
	<tr>
			<td align="right"><a href="http://www.celiac.org.il/" target="_blank"><img src="images/celiac_logo.gif" BORDER=0></td>
		<td   align="left">
		   <table width="100%"  align="left"  border="0" cellpadding="0" cellspacing="1">
		   
		  <td align="center" width="30%"><a class="textfieldHeb" href="app"  >אתר חדש</td>
		  <td align="center" width="30%"><a class="textfieldHeb" href="jsp/takanon.htm" target="_blank" >תקנון</td>
		   <td align="center" width="30%"><a class="textfieldHeb"  href="http://www.celiac.org.il" target="_blank">צור קשר</a></td>
		   
		   </table>
		</td>
	
		
		
		
		
	</tr>
	<tr>
		<td colspan="2"><hr ></td>
	</tr>
</table>















</td></tr>



<tr><td align="center">
	<br>
<br>
<br>
<table bgcolor= "#f5f5e1" width="30%" border="1" >
	
<tr>
<td align="center"><img src="images/celiac_logo.jpg" ></td>
<td>
			
<form name="login_form" action="jsp/checkLogin.jsp" method="POST">
	
	<table align="center" width="100%" border="0" >
		<%if (!("").equals(errorMessage)){%>
		  <td align="center">
		  <font color=#ff0000><b><%=errorMessage%><b></font>
		 
			<%}%>
			<tr><td align="center">
			<table align="center" width="162" border="0" >
				<tr><td align="center">
			<tr>
		    <td  align="center" width="100%" class="textfieldHeb">
				:<%=org.celiac.util.TemplateReader.getHebrewMapWord("USER_ID")%><br>
				<input type="text" style="width: 150" class="textfield" name="user_id">
				<br>
				:<%=org.celiac.util.TemplateReader.getHebrewMapWord("PASSWORD")%><br>
				<input type="password" style="width: 150" class="textfield" name="user_pass">
				<br>
			</td>
		</tr>
		<tr >
				<td>
				<input name="Login" type="submit" id="Login" value="<%=org.celiac.util.TemplateReader.getHebrewMapWord("HASHER")%>"  style="width: 150" class="button" enabled onMouseOver="this.style.background = '#d5e0e6';" onMouseOut="this.style.background = '#e4e4e4';">
				<br>
				</td>
		</tr>
		
			
		<tr>
			<td align="right" >
				<a class="textfieldHeb"  href="jsp/newUser.jsp"><%=org.celiac.util.TemplateReader.getHebrewMapWord("MISHTAMESH_HADASH")%></a>
			</td>
		</tr>
		<tr>
				<td align="right" >
				<a class="textfieldHeb"  href="jsp/lostIdentity.jsp"><%=org.celiac.util.TemplateReader.getHebrewMapWord("SHACHACH_SISMA")%></a>
			</td>
			</td></tr>
 	</table>
		</td></tr>	
			</tr>
			
			
				
  </table>

</form>

</td>
</tr>

</table>
</td></tr>

<tr><td align="center" valign="center">
<TABLE name=selectTable BORDER="3"  BGCOLOR="#FFFFFF"    CELLSPACING="1" CELLPADDING="0">

<TR valign="bottom" ALIGN="CENTER">

<TD  align="center" colspan="1"  class="headerUnderLine3Heb">
<font color="brown">עמותת צליאק בישראל</font><br>
טל: 03-6781481<br>
הכתובת למשלוח דואר
 <br>
ת.ד.1623 רמת גן - מיקוד 52116<br>
E-mail: <a href="mailto:office@celiac.org.il">office@celiac.org.il</a><br>
<a href="http://www.celiac.org.il" target="_blank">http://www.celiac.org.il</a> 
</TD>



</TR>
</table>
</td></tr>

	<tr><td valign="bottom" align="center">

<table   height="25" cellpadding="0" cellspacing="0" border="0" bgcolor="#f5f5e1">
	<tr height="2 ><td colspan="100%"></td></tr>
	<tr valign="bottom" >
		<br><br>
		<td class="headerNotUnderLine" align="left" width="30%" nowrap="nowrap"><a href="mailto:tal@yanai.org.il">Created by Tal Yanai</a></td>
		<td class="headerNotUnderLine" width="50%"></td>
		<td class="headerNotUnderLine" align="right" nowrap="nowrap">כל הזכויות שמורות לעמותת צליאק בישראל <!-- © --> 2008</td>
		<td valign="bottom" class="headerNotUnderLine" width="10%"></td>

		
	</tr>
</table>
</td></tr>
</td>
</tr>
</table>
<script type="text/javascript">
var gaJsHost = (("https:" == document.location.protocol) ? "https://ssl." : "http://www.");
document.write(unescape("%3Cscript src='" + gaJsHost + "google-analytics.com/ga.js' type='text/javascript'%3E%3C/script%3E"));
</script>
<script type="text/javascript">
var pageTracker = _gat._getTracker("UA-5180341-1");
pageTracker._trackPageview();
</script>
<script>
saveVersionDB = function (value) {

    	if (typeof window.localStorage !== 'undefined') {
        	window.localStorage.setItem('gfguide_version',  value);
    	}
	
}

loadTouchDB = function () {
    if (typeof window.localStorage !== 'undefined') {
		var loadObject = window.localStorage.getItem('touched_version');
		if (!loadObject){
			return false;
		} else {	
			return loadObject;
		}
    } else {
    	return false;
    }
}
</script>
</body>

</html>