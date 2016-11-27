<%@ page language="java" 
         import="org.celiac.web.bean.*, java.util.*" 
         errorPage="loginError.jsp" %>
                

<html>
<jsp:useBean id="User" scope="session" class="org.celiac.web.bean.User">
  <jsp:setProperty name="User" property="*"/>
</jsp:useBean>
<%@ include file="../include/header.jsp" %>    

<head>

 <script src='../include/upload.js'> </script>
        <script src='../dwr/interface/UploadMonitor.js'> </script>
        <script src='../dwr/engine.js'> </script>
        <script src='../dwr/util.js'> </script>
        <style type="text/css">
            body { font: 8px Arial, Verdana,  Helvetica, sans-serif, Miriam Transparent; FONT-WEIGHT: BOLD; }
            #progressBar { padding-top: 5px; }
            #progressBar2 { padding-top: 5px; }
            #progressBar3 { padding-top: 5px; }
            #progressBarBox { width: 100%; height: 10px; border: 1px inset; background: #eee;}
            #progressBarBoxContent { width: 0; height: 10px; border-right: 1px solid #2F6681; background: #2F6681; }
            #progressBarBoxContent2 { width: 0; height: 10px; border-right: 1px solid #2F6681; background: #2F6681; }
            #progressBarBoxContent3 { width: 0; height: 10px; border-right: 1px solid #2F6681; background: #2F6681; }
            
            
            
        </style>

</head>


<% if (!(User.getUser().getPERMISSIONS().equals("admin"))) throw new Exception("User was not login. Please login."); %>


<%try{%>    
<%@ page contentType="text/html; charset=UTF-8"%>
<%

String uploadMerStatus = null;
String uploadUserStatus = null;
String uploadCodeDescStatus = null;
String messageStatus = null;
try{

uploadMerStatus = (String)session.getAttribute("uploadMerStatus");
uploadUserStatus = (String)session.getAttribute("uploadUserStatus");
uploadCodeDescStatus = (String)session.getAttribute("uploadCodeDescStatus");
messageStatus = (String)session.getAttribute("messageStatus");

session.setAttribute("uploadMerStatus",null);
session.setAttribute("uploadUserStatus",null);
session.setAttribute("uploadCodeDescStatus",null);
session.setAttribute("messageStatus",null);

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
<td></td>
		<td width="100%">
			<table border="0" cellpadding="1" cellspacing="1" class="frameTableT">
				<tr><td colspan="1" nowrap class="headerUnderLine">Gluten Free DB upload</td></tr>
					
			      <tr><td><br></td></tr>
				<tr><td>
			<TABLE name=selectTable BORDER="3" BORDERCOLOR="#D8D8D8" BGCOLOR="#FFFFFF" style="width: 100%" CELLSPACING="1" CELLPADDING="3">
			<form name="mer_form" ENCTYPE="multipart/form-data" ACTION="../jsp/dataLoader.jsp" METHOD=POST onsubmit="startProgress()">
			
					
					<% if (uploadMerStatus == null){
} else if (uploadMerStatus.equals("FAIL")) {%>

<tr><td colspan="1" nowrap class="errorB">Upload failed.</td></tr>
<tr><td colspan="1" nowrap class="errorB"><%=messageStatus%></td></tr>

<%} else {%>
<tr><td colspan="1" nowrap class="resultMessage">Upload of input file <%=uploadMerStatus%> was success.</td></tr>
<%}%>

  <tr>
  
  	<tr><td colspan="1" nowrap class="titleBigHeb">Merchandise</td></tr>
    <tr><td colspan="1" nowrap class="headerUnderLine">Please select the Merchandise Excel input file:</td></tr>
    
    <tr><td> <input name="userfile1" id="userfile1" type="file" size="40"> </td>
   
        <div align="center">
        <font face="Verdana, Arial, Helvetica, sans-serif">
          <tr>
          <td>
          <input type="submit"  name="Submit" id="uploadbutton" value="Upload Merchandise" style="width: 100%" class="button" enabled onMouseOver="this.style.background =  '#d5e0e6';" onMouseOut="this.style.background = '#e4e4e4';"  >
          
          </font></div>
          <div id="progressBar" style="display: none;">

            <div id="theMeter">
                <div style="font-weight: bold" align="center"  id="progressBarText"></div>

                <div id="progressBarBox">
                    <div id="progressBarBoxContent"></div>
                </div>
            </div>
        </div>
        </td>
      </tr>
    
  </tr>

  </tr>

</form>
</TABLE>
</td></tr>
<tr><td><br></td></tr>
	<tr><td>
			<TABLE name=selectTable BORDER="3" BORDERCOLOR="#D8D8D8" BGCOLOR="#FFFFFF" style="width: 100%" CELLSPACING="1" CELLPADDING="3">
				
				
				
				
				
				
<form name="users_form" ENCTYPE="multipart/form-data" ACTION="../jsp/dataLoaderUser.jsp" METHOD=POST onsubmit="startProgress2()">
			
					
					
					<% if (uploadUserStatus == null){
} else if (uploadUserStatus.equals("FAIL")) {%>

<tr><td colspan="1" nowrap class="errorB">Upload failed.</td></tr>
<tr><td colspan="1" nowrap class="errorB"><%=messageStatus%></td></tr>

<%} else {%>
<tr><td colspan="1" nowrap class="resultMessage">Upload of input file <%=uploadUserStatus%> was success.</td></tr>
<%}%>

  
  <tr>
  	<tr><td colspan="1" nowrap class="titleBigHeb">Users</td></tr>
    <tr><td colspan="1" nowrap class="headerUnderLine">Please select the Users Excel input file:</td></tr>
    
    <tr><td> <input name="userfile2" id="userfile2" type="file" size="40"> </td>
    
        <div align="center">
        <font face="Verdana, Arial, Helvetica, sans-serif">
          <tr>
          <td>
          <input type="submit"  name="Submit" id="uploadbutton2" value="Upload Users" style="width: 100%" class="button" enabled onMouseOver="this.style.background =  '#d5e0e6';" onMouseOut="this.style.background = '#e4e4e4';"  >
          </font></div>
          <div id="progressBar2" style="display: none;">

            <div id="theMeter">
                <div style="font-weight: bold" align="center"  id="progressBarText2"></div>

                <div id="progressBarBox">
                    <div id="progressBarBoxContent2"></div>
                </div>
            </div>
        </div>
        </td>
      </tr>
   
  </tr>
  </tr>
</form>
</table>
</td></tr>
<tr><td><br></td></tr>
	<tr><td>
			<TABLE name=selectTable BORDER="3" BORDERCOLOR="#D8D8D8" BGCOLOR="#FFFFFF" style="width: 100%" CELLSPACING="1" CELLPADDING="3">
<form name="users_form3" ENCTYPE="multipart/form-data" ACTION="../jsp/dataLoaderCodeDesc.jsp" METHOD=POST onsubmit="startProgress3()">
			
					
					
					<% if (uploadCodeDescStatus == null){
} else if (uploadCodeDescStatus.equals("FAIL")) {%>

<tr><td colspan="1" nowrap class="errorB">Upload failed.</td></tr>
<tr><td colspan="1" nowrap class="errorB"><%=messageStatus%></td></tr>

<%} else {%>
<tr><td colspan="1" nowrap class="resultMessage">Upload of input file <%=uploadCodeDescStatus%> was success.</td></tr>
<%}%>

  
  <tr>
  	<tr><td colspan="1" nowrap class="titleBigHeb">Code Description</td></tr>
    <tr><td colspan="1" nowrap class="headerUnderLine">Please select the Code Description Excel input file:</td></tr>
    
    <tr><td> <input name="userfile3" id="userfile3" type="file" size="40"> </td>
   
        <div align="center">
        <font face="Verdana, Arial, Helvetica, sans-serif">
          <tr>
          <td>
          <input type="submit"  name="Submit" id="uploadbutton3" value="Upload Code Description" style="width: 100%" class="button" enabled onMouseOver="this.style.background =  '#d5e0e6';" onMouseOut="this.style.background = '#e4e4e4';"  >
          </font></div>
          <div id="progressBar3" style="display: none;">

            <div id="theMeter">
                <div style="font-weight: bold" align="center"  id="progressBarText3"></div>

                <div id="progressBarBox">
                    <div id="progressBarBoxContent3"></div>
                </div>
            </div>
        </div>
        </td>
      </tr>
    
  </tr>
  </tr>
</form>
</table>
</td></tr>
					
			
			</table>
			<br>
		</td>
	</tr>
</table>


<% if (uploadMerStatus == null || uploadUserStatus == null || uploadCodeDescStatus == null){
} else if (uploadMerStatus.equals("FAIL")) {%>

<p align="left"><i><b><font size="4" color="#FFFFFF">Upload failed. Please contact your support team, or try again.</font></b></i></p>

<%} else {%>
<p align="left"><i><b><font size="4" color="#FFFFFF">Upload of input file <%=uploadMerStatus%> was success.</font></b></i></p>
<p align="left"><i><b><font size="4" color="#FFFFFF">You will be notify with an email once the job will be ended.</font></b></i></p>
<%}%>


<%}catch(Exception e){
	e.printStackTrace();
}%>

<%@ include file="../include/buttom.jsp" %>  