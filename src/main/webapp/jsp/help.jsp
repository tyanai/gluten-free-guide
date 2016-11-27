<%@ page language="java" 
         import="org.celiac.api.GlutenFreeAPI, org.celiac.web.bean.*, java.util.*" 
         errorPage="loginError.jsp" %>
<jsp:useBean id="User" scope="session" class="org.celiac.web.bean.User">
  <jsp:setProperty name="User" property="*"/>
</jsp:useBean>
<%@ include file="../include/header.jsp" %>    


<head>

<script type="text/javascript">

/***********************************************
* Bookmark site script- © Dynamic Drive DHTML code library (www.dynamicdrive.com)
* This notice MUST stay intact for legal use
* Visit Dynamic Drive at http://www.dynamicdrive.com/ for full source code
***********************************************/

/* Modified to support Opera */
function bookmarksite(title,url){
if (window.sidebar) // firefox
	window.sidebar.addPanel(title, url, "");
else if(window.opera && window.print){ // opera
	var elem = document.createElement('a');
	elem.setAttribute('href',url);
	elem.setAttribute('title',title);
	elem.setAttribute('rel','sidebar');
	elem.click();
} 
else if(document.all)// ie
	window.external.AddFavorite(url, title);
}
</script>
	
	
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!--<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=ISO-8859-1">-->
<link href="../include/theStyle.css" rel="stylesheet" type="text/css">
</head>



<%
request.setCharacterEncoding("utf-8");
 response.setContentType("text/html;charset=UTF-8");

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
	<tr valign="top">
		<td>
		<table width="100%" height="90%" border="1">
		<td valign="top" nowrap bgcolor="#f5f5e1" class="textSmall" >
		<%@ include file="../include/upperMenu.jsp" %>
		</td>
		</table>
</td>
<td></td>
		<td WIDTH="600" >
			<table border="0"   cellpadding="1" cellspacing="1" class="frameTableT">
			
					<tr><td colspan="1" nowrap class="headerHeb"><%=org.celiac.util.TemplateReader.getHebrewMapWord("HELP")%>:</td></tr>
					
					
			
			
    

        
        
<tr><td>
        
        
<TABLE name=selectTable BORDER="3" BORDERCOLOR="#D8D8D8" BGCOLOR="#FFFFFF" WIDTH="600" CELLSPACING="1" CELLPADDING="3">

<TH  colspan="1"  style="width: 50%" class="headerUnderLine4"><%=org.celiac.util.TemplateReader.getHebrewMapWord("CELL_USE")%></TH>
<TH  colspan="1"  style="width: 50%" class="headerUnderLine4"><%=org.celiac.util.TemplateReader.getHebrewMapWord("PITEY_HISTAMSHUT")%></TH>

<TR ALIGN="CENTER">

<TD  colspan="1"  style="width: 50%" class="headerUnderLine3Heb"><a href="../attachments/cell_user.pdf" target="_blank">Download</a></TD>
<TD  colspan="1"  style="width: 50%" class="headerUnderLine3Heb"><a href="takanon.htm" target="_blank">View</a></TD>


</TR>


</TABLE>
  </tr></td>     
        
 <tr><td>
        
        
<TABLE name=selectTable BORDER="3" BORDERCOLOR="#D8D8D8" BGCOLOR="#FFFFFF" style="width: 100%" CELLSPACING="1" CELLPADDING="3">

<TH  colspan="1"  style="width: 30%" class="headerUnderLine4"><%=org.celiac.util.TemplateReader.getHebrewMapWord("YOUR_CELL_ADDRESS")%></TH>
<TH  colspan="1"  style="width: 70%" class="headerUnderLine4"><%=org.celiac.util.PropertiesLoader.getProperties("gf.site")%>/gfa?user=<%=User.theUser.getUSER_ID()%>-<%=User.theUser.getPASSWORD()%></TH>




</TABLE>
  </tr></td>     
  
  <tr><td>
        
         
<TABLE name=selectTable BORDER="3" BORDERCOLOR="#D8D8D8" BGCOLOR="#FFFFFF" style="width: 100%" CELLSPACING="1" CELLPADDING="3">

<TH  colspan="1"  style="width: 30%" class="headerUnderLine4"><%=org.celiac.util.TemplateReader.getHebrewMapWord("YOUR_ADVANCE_CELL_ADDREDD")%></TH>
<TH  colspan="1"  style="width: 70%" class="headerUnderLine4"><%=org.celiac.util.PropertiesLoader.getProperties("gf.site")%>/gfb?user=<%=User.theUser.getUSER_ID()%>-<%=User.theUser.getPASSWORD()%></TH>




</TABLE>
  </tr></td>    

<tr><td>
	<br>
	<br>
</td></tr>



<td></td>
		
			<table border="0"   cellpadding="1" cellspacing="1" class="frameTableT">
			
					<tr><td colspan="1" nowrap class="headerHeb">ח ד ש !</td></tr>
					
		   
<tr><td>

<tr><td>
        
        
<TABLE name=selectTable BORDER="3" BORDERCOLOR="#D8D8D8" BGCOLOR="#FFFFFF" WIDTH="600" CELLSPACING="1" CELLPADDING="3">

<TH  colspan="1"  style="width: 50%" class="headerUnderLine4"><%=org.celiac.util.TemplateReader.getHebrewMapWord("ADD_TO_FAVORITES")%></TH>
<TH  colspan="1"  style="width: 50%" class="headerUnderLine4">הוספה למועדפים עבור דפדפן אופרה בלבד</TH>

<TR ALIGN="CENTER">
<TD  colspan="1"  style="width: 50%" class="headerUnderLine3Heb"><a href="javascript:bookmarksite('GFGuide', '<%=org.celiac.util.PropertiesLoader.getProperties("gf.site")%>/jsp/checkLogin.jsp?user_id=<%=User.theUser.getUSER_ID()%>&user_pass=<%=User.theUser.getPASSWORD()%>')"><%=org.celiac.util.TemplateReader.getHebrewMapWord("ADD_TO_BROWSER")%></a></TD>
<TD  colspan="1"  style="width: 50%" class="headerUnderLine3Heb"><a href = "<%=org.celiac.util.PropertiesLoader.getProperties("gf.site")%>/jsp/checkLogin.jsp?user_id=<%=User.theUser.getUSER_ID()%>&user_pass=<%=User.theUser.getPASSWORD()%>" title="GFGuide" rel="sidebar"><%=org.celiac.util.TemplateReader.getHebrewMapWord("ADD_TO_BROWSER")%></a></TD>
																																	

</TR>


</TABLE>
  </tr></td>     




   

  		</table>
  	</td>
  </tr>
  	</table>
			<br>
		





<%@ include file="../include/buttom.jsp" %>    