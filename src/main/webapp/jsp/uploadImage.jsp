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

<SCRIPT TYPE="text/javascript">

function closeThisWindow()
{
	window.close();
	if (window.opener && !window.opener.closed) {
		window.opener.location.reload();
	} 
}

</SCRIPT>
        
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

<head><title>Image Managment</title></head>



<table height="100%" width="100%" align="center" valign="top" border="0">
	<tr ><td height="25">

			
					
		

<body>
	<form action="<%=siteRoot%>/UploadImage" method="post" enctype="multipart/form-data" name="productForm" id="productForm">
		 <tr>
    <tr><td colspan="1" nowrap style="width: 100%" class="headerUnderLineHeb">ניהול תמונה עבור מוצר:</td></tr>
   
    
        
        
        
<tr><td>
        
        
<TABLE align="center" name=selectTable BORDER="0" BORDERCOLOR="#D8D8D8" BGCOLOR="#FFFFFF" style="width: 100%" CELLSPACING="3" CELLPADDING="3">
<tr>
<td>
<table align="center" name=selectTable BORDER="3" BORDERCOLOR="#D8D8D8" BGCOLOR="#FFFFFF" style="width: 100%" CELLSPACING="3" CELLPADDING="0">
<tr>
<td  align="center" colspan="1"  style="width: 33%" class="headerUnderLine4"><%=org.celiac.util.TemplateReader.getHebrewMapWord("COMPANY")%></td>
<td  align="center" colspan="1"  style="width: 33%" class="headerUnderLine4"><%=org.celiac.util.TemplateReader.getHebrewMapWord("PRODUCT")%></td>
<td  align="center" colspan="1"  style="width: 20%" class="headerUnderLine4"><%=org.celiac.util.TemplateReader.getHebrewMapWord("CATEGORY")%></td>
</tr>
<TR>		
<td  align="center" colspan="1"  style="width: 33%" class="headerUnderLine3Heb"><%=companySelect%></td>
<td  align="center" colspan="1"  style="width: 33%" class="headerUnderLine3Heb"><%=productSelect%></td>
<td  align="center" colspan="1"  style="width: 20%" class="headerUnderLine3Heb"><%=categorySelect%></td>
</tr>
</table>
</td>
</tr>	

<tr>
				<td>
					<br>
			<table height="100%" width="100%" border="1" CELLSPACING="1" CELLPADDING="3">
				
      	  <!-- Image -->	
      		<tr>      			
      		<td colspan="1"  class="headerUnderLine2Heb"><%=recommand%></td>  		
      		
      		
      		<%
      			
      		
      			      			
      			byte[] image = null;
      			image = DBQueryFactory.getDBHandler().getSmallImage(productSelect, companySelect, categorySelect);
      			boolean hasImage = false;
      			int border = 0;
      			if (image != null ) {
      			
      	
      				hasImage = true;
      				border = 1;
      				session.setAttribute("productSelect",productSelect);
      				session.setAttribute("companySelect",companySelect);
      				session.setAttribute("categorySelect",categorySelect);
      			} else {
      				
      				session.setAttribute("productSelect",null);
      				session.setAttribute("companySelect",null);
      				session.setAttribute("categorySelect",null);
      			}
      			
      			
      			%>
      		
      		<td align="center" class="headerUnderLine2Heb" width="100" height="100%" >
      			<table align="center" height="100%" border="0" CELLSPACING="0" CELLPADDING="0">
      			<tr>
      				<td valign="center" align="center" height="100%">
      					<table  border="<%=border%>" CELLSPACING="0" CELLPADDING="0">	
      						<tr>
      						<% if (hasImage){ %>
      							<!--<td align="center" ><img src="/gfguide/GetImage?imageSize=small"></td>-->
      							<td align="center" ><img src="<%=siteRoot%>/GetImage?imageSize=small"/></td>
      							
      						<%} else {%>
      						  <td align="center" ></td>
      						<%}%>
      						
      						
      						
      					</tr>
      				  </table>
      				</td>
      		  </tr>	
      		  	
      	
      		  	
      			<tr>	      				
      			<td valign="bottom" width="100" >
      				
      				
      				<table width="100%" border="0" CELLSPACING="2" CELLPADDING="0">	
      
      
      			
      					<tr><td>
      				
      					<%if (!hasImage) { %>
      						
      						      						
      						<form action="<%=siteRoot%>/UploadImage" method="post" enctype="multipart/form-data" name="productForm" id="productForm">
      						<tr>
										<td align="right">
											<input type="file" name="image" id="image">
										<td>
		  						</tr>	
      						<td width="100%"><input type="submit"  name="Submit" value="הוסף תמונה" style="width: 100%" class="button" enabled onMouseOver="this.style.background =  '#d5e0e6';" onMouseOut="this.style.background = '#e4e4e4';" ></td>
      						<input type="hidden" name="productSelect" value="<%=productSelect%>">
      						<input type="hidden" name="companySelect" value="<%=companySelect%>">
      						<input type="hidden" name="categorySelect" value="<%=categorySelect%>">
      						<input type="hidden" name="typeOfInsert" value="new">
      						<%
      							session.setAttribute("recommand",recommand);
      						%>
      						</form>
      					<%}else{%>
      					
      						<form action="<%=siteRoot%>/UploadImage" method="post" enctype="multipart/form-data" name="productForm" id="productForm">
      						<tr>
										<td align="right">
											<input type="file" name="image" id="image">
										<td>
		  						</tr>	
      						<td width="100%"><input type="submit"  name="Submit" value="עדכן" style="width: 100%" class="button" enabled onMouseOver="this.style.background =  '#d5e0e6';" onMouseOut="this.style.background = '#e4e4e4';" ></td>
      						<input type="hidden" name="productSelect" value="<%=productSelect%>">
      						<input type="hidden" name="companySelect" value="<%=companySelect%>">
      						<input type="hidden" name="categorySelect" value="<%=categorySelect%>">
      						<input type="hidden" name="typeOfInsert" value="update">
      						<%
      							session.setAttribute("recommand",recommand);
      						%>
      						</form>
      						<form action="<%=siteRoot%>/UploadImage" method="post" enctype="multipart/form-data" name="productForm" id="productForm">
      						
      						<td width="100%"><input type="submit"  name="Submit" value="מחק" style="width: 100%" class="button" enabled onMouseOver="this.style.background =  '#d5e0e6';" onMouseOut="this.style.background = '#e4e4e4';" ></td>
      						<input type="hidden" name="productSelect" value="<%=productSelect%>">
      						<input type="hidden" name="companySelect" value="<%=companySelect%>">
      						<input type="hidden" name="categorySelect" value="<%=categorySelect%>">
      						<input type="hidden" name="typeOfInsert" value="delete">
      						<%
      							session.setAttribute("recommand",recommand);
      						%>
      						</form>
      					<%}%>
      						</td></tr>
      		  	</table>
      			</td>
      		
      			
      			</tr>
      		</table>
      		</td>
      		</tr>
      		
      		
      </table>
      <br>
      	</td>
  	</tr>				
				
				
	

			
		

		</table>
		
		
		<form ACTION="#" NAME="testform" onSubmit="closeThisWindow()">
			<tr>
			
				<td align="right">
				<input type="submit"  name="Submit" value="<%=org.celiac.util.TemplateReader.getHebrewMapWord("HAMSHECH")%>" style="width: 100%" class="button" enabled onSubmit=;  onMouseOver="this.style.background =  '#d5e0e6';" onMouseOut="this.style.background = '#e4e4e4';" >
			</td>
			</tr>
	</form>
</body>

</html>



<%@ include file="../include/buttom.jsp" %>    





