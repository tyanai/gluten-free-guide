<%@ page language="java" 
         import="org.celiac.api.GlutenFreeAPI, org.celiac.web.bean.*, java.util.*, org.celiac.business.GFS, org.celiac.business.GFSByCategoryAndCompanyImpl, org.celiac.datatype.*, org.celiac.util.database.*" 
         errorPage="loginError.jsp" %>
         
         
         
         
         
<jsp:useBean id="User" scope="session" class="org.celiac.web.bean.User">
  <jsp:setProperty name="User" property="*"/>
</jsp:useBean>

<html DIR="RTL">

<head>
	
<%
boolean showPirsomot = false;
try{
String siteRoot = request.getContextPath();
if (User.getUser() == null) throw new Exception("User was not login. Please login.");
%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"%>

<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="REFRESH" CONTENT="600">
<link rel="shortcut icon" href="../images/favicon.ico" >
<link href="<%=siteRoot%>/include/theStyle.css" rel="stylesheet" type="text/css">


        
</head>

<%
request.setCharacterEncoding("utf-8");
 response.setContentType("text/html;charset=UTF-8");
 
 
String companySelect = null;
String productSelect = null;
String categorySelect = null;
StringBuffer recommand = null;
 
if (request.getParameter("companySelect") != null){
		companySelect = (String)request.getParameter("companySelect");
		
}

if (request.getParameter("productSelect") != null){
		productSelect = (String)request.getParameter("productSelect"); 
}

if (request.getParameter("categorySelect") != null){
		categorySelect = (String)request.getParameter("categorySelect"); 
}

 
recommand  = (StringBuffer)session.getAttribute("recommand");
 
 %>

<html>

<head><title><%=productSelect%></title></head>



<table  align="center" valign="top" border="0">


<body>
 
    
 
<tr>
        
        

      	
      	
      			
      				<td valign="center" align="center" >
      					<table  border="1" CELLSPACING="0" CELLPADDING="0">	
      						<tr>
      						
      							<!--<td align="center" ><img src="/gfguide/GetImage?imageSize=small"></td>-->
      							<td align="center" ><img src="<%=siteRoot%>/GetImage?imageSize=large"/></td>
      							
      					
      					  						
      						
      						
      					</tr>
      				  </table>
      				</td>
      		  </tr>	
      		  	
      	
      		  	
      		
      		
      		
     
      	</td>
  	</tr>				
				
				
	

			
		

		</table>
		
		
</body>

</html>



<%@ include file="../include/buttom.jsp" %>    


