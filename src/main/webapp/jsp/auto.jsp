<%@ page language="java" 
         import="org.celiac.api.GlutenFreeAPI, org.celiac.web.bean.*, java.util.*, org.celiac.business.GFS, org.celiac.business.GFSByCategoryAndCompanyImpl,org.celiac.datatype.*, org.celiac.util.database.*" 
         errorPage="loginError.jsp" %>
         
         
         
         
         
<jsp:useBean id="User" scope="session" class="org.celiac.web.bean.User">
  <jsp:setProperty name="User" property="*"/>
</jsp:useBean>
<%@ include file="../include/header.jsp" %>    

<%
String siteRoot = request.getContextPath();
%>

<head>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!--<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=ISO-8859-1">-->


<link rel="stylesheet" href="../include/jquery.ui.all.css">
<script src="../include/jquery-1.5.1.js"></script>
<script src="../include/jquery.ui.core.js"></script>
<script src="../include/jquery.ui.widget.js"></script>
<script src="../include/jquery.ui.position.js"></script>
<script src="../include/jquery.ui.autocomplete.js"></script>
<link rel="stylesheet" href="../include/demos.css">
<link href="../include/theStyle.css" rel="stylesheet" type="text/css">
<style>
	.ui-autocomplete-loading { background: white url('../images/ui-anim_basic_16x16.gif') right center no-repeat; }
</style>
	
	
	
	
	
</head>

<SCRIPT LANGUAGE="JavaScript">		

	function processSelectionCompany()
	{
	  // Get the index of th selection.
	  var theUrl = "smsSimulator.jsp?theCompany=" + document.query_form2.company.value;
	  window.open(theUrl,"_self");
	 	  	  
	}
	
	function processSelectionProduct()
	{
	  // Get the index of th selection.
	  var theUrl = "smsSimulator.jsp?theCompany=" + document.query_form2.company.value + "&theProduct=" + document.query_form2.product.value;
	  window.open(theUrl,"_self");
	 	  	  
	}
	
	function processSelectionCategory2()
	{
	  // Get the index of th selection.
	  var theUrl = "smsSimulator.jsp?theCategory2=" + document.query_form3.category2.value;
	  window.open(theUrl,"_self");
	 	  	  
	}
	
	function processSelectionCompany2()
	{
	  // Get the index of th selection.
	  var theUrl = "smsSimulator.jsp?theCompany2=" + document.query_form3.company2.value + "&theCategory2=" + document.query_form3.category2.value;
	  window.open(theUrl,"_self");
	 	  	  
	}
	
	
	
	
</SCRIPT>


<%
request.setCharacterEncoding("utf-8");
 response.setContentType("text/html;charset=UTF-8");

java.util.HashMap companyHash = null;
java.util.HashMap categoryHash = null;
java.util.HashMap productHash = null;

String companySelect = null;
String productSelect = null;
String categorySelect = null;

String companySelect3=null;
String productSelect3=null;



String sms = "";
String theCompany = "";
String theProduct= "";
String theCategory = "";
String smsMessage = "";

if (request.getParameter("smsMessage") != null){
		sms = (String)request.getParameter("smsMessage");
		
}



if (request.getParameter("companySelect") != null){
		companySelect = (String)request.getParameter("companySelect");
		
}

if (request.getParameter("productSelect") != null){
		productSelect = (String)request.getParameter("productSelect");
		
}

if (request.getParameter("productSelect3") != null){
		productSelect3 = (String)request.getParameter("productSelect3");
		if ("".equals(productSelect3)) productSelect3 = null;
		
}
if (request.getParameter("companySelect3") != null){
		companySelect3 = (String)request.getParameter("companySelect3");
		if ("".equals(companySelect3)) companySelect3 = null;
}

if (request.getParameter("categorySelect") != null){
		categorySelect = (String)request.getParameter("categorySelect");
		
}

if (request.getParameter("theCompany") != null){
		theCompany = request.getParameter("theCompany");
		
} else if (request.getParameter("company") != null){
		theCompany = request.getParameter("company");	
}

if (request.getParameter("theProduct") != null){
		theProduct = request.getParameter("theProduct");
		
} else if (request.getParameter("product") != null){
		theProduct = request.getParameter("product");	
}

if ("MULTIPLE".equals(theProduct)) theProduct = request.getParameter("product");


if (request.getParameter("theCategory") != null){
		theCategory = request.getParameter("theCategory");
		
} else if (request.getParameter("category") != null){
		theCategory = request.getParameter("category");	
}


if (request.getParameter("category") != null){
		theCategory = (String)request.getParameter("category");
		categoryHash = (java.util.HashMap)session.getAttribute("userCategoryHash");
		if (categoryHash != null){
		  categorySelect = (String)categoryHash.get(theCategory);
		}
}

//  *************** by category ******************

String companySelect2 = null;
String productSelect2= null;
String categorySelect2 = null;



String theCompany2 = "";
String theProduct2= "";
String theCategory2 = "";





if (request.getParameter("companySelect2") != null){
		companySelect2 = (String)request.getParameter("companySelect2");
		
}

if (request.getParameter("productSelect2") != null){
		productSelect2 = (String)request.getParameter("productSelect2");
		
}

if (request.getParameter("categorySelect2") != null){
		categorySelect2 = (String)request.getParameter("categorySelect2");
		
}

if (request.getParameter("theCategory2") != null){
		theCategory2 = request.getParameter("theCategory2");
		
} else if (request.getParameter("category2") != null){
		theCategory2 = request.getParameter("category2");	
}

if (request.getParameter("theCompany2") != null){
		theCompany2 = request.getParameter("theCompany2");
		
} else if (request.getParameter("company2") != null){
		theCompany2 = request.getParameter("company2");	
}

if (request.getParameter("theProduct2") != null){
		theProduct2 = request.getParameter("theProduct2");
		
} else if (request.getParameter("product2") != null){
		theProduct2 = request.getParameter("product2");	
}




if (request.getParameter("product2") != null){
		theProduct2 = (String)request.getParameter("product2");
		productHash = (java.util.HashMap)session.getAttribute("userProductHash");
		if (productHash != null){
		  productSelect2 = (String)productHash.get(theProduct2);
		}
		if ("MULTIPLE".equals(theProduct2)) theProduct2 = request.getParameter("product2");
}




//  ***********************************************

if (!theProduct.equals("")) {
			companySelect = (String)(org.celiac.util.database.DBQueryFactory.getDBHandler().getCompanyList().toArray()[new Integer(theCompany).intValue() - 1]);
      productSelect = (String)(org.celiac.util.database.DBQueryFactory.getDBHandler().getProductByCompanyList(companySelect).toArray()[new Integer(theProduct).intValue() - 1]); 	
      java.util.Set<String> categSet = org.celiac.util.database.DBQueryFactory.getDBHandler().getCategoryByProductAndCompanyList(productSelect,companySelect);
  if (categSet.size() == 1){
    categorySelect = (String)(categSet.iterator().next());
  }
}

boolean foundMoreThenOnePerProduct = true;
String companyForMultiple = null;
String categoryForMultiple = null;
String companyDate = "";

if (!theCompany2.equals("")) {
			categorySelect2 = (String)(org.celiac.util.database.DBQueryFactory.getDBHandler().getCategoryList().toArray()[new Integer(theCategory2).intValue() - 1]);
      companySelect2 = (String)(org.celiac.util.database.DBQueryFactory.getDBHandler().getCompanyByCategoryList(categorySelect2).toArray()[new Integer(theCompany2).intValue() - 1]); 	
      categoryForMultiple = categorySelect2;
      companyForMultiple = companySelect2;
      java.util.Set<String> categSet = org.celiac.util.database.DBQueryFactory.getDBHandler().getProductByCategoryAndCompanyList(categorySelect2,companySelect2);
      companyDate = org.celiac.util.database.DBQueryFactory.getDBHandler().getDateofCompany(companySelect2);
      if (companyDate == null) companyDate = "";
  if (categSet.size() == 1){
    productSelect2 = (String)(categSet.iterator().next());
    foundMoreThenOnePerProduct = false;
  }
}









//   ************************************************



StringBuffer sb = new StringBuffer();


if ((companySelect3 != null) && (productSelect3 != null) ) {
	sms = productSelect3 + "." + companySelect3 ;
	smsMessage = sms;
} else if ((companySelect3 != null)){
	sms = "general." + companySelect3;
	smsMessage = sms;
} else if ((productSelect3 != null)){
	sms = productSelect3;
	smsMessage = sms;
} else {

if ((companySelect != null) && (productSelect != null) && (categorySelect != null)) {
	sms = productSelect + "." + companySelect + "." + categorySelect;
	if (productSelect.trim().equals("MULTIPLE")) sms = "generic" + "." + companySelect + "." + categorySelect;
	smsMessage = sms;
}

if ((companySelect2 != null) && (productSelect2 != null) && (categorySelect2 != null)) {
	sms = productSelect2 + "." + companySelect2 + "." + categorySelect2;
	if (productSelect2.trim().equals("MULTIPLE")) sms = "generic" + "." + companySelect2 + "." + categorySelect2;
	smsMessage = sms;
}
}



if (!sms.equals("")) {
String empty = "";

		if ((sms == null) || empty.equals(sms)) {
			sms = "<em>(no sms specified)</em>";
		}
		
		sb.append("<html><p align=right>");
		try {
			String theOutput = null;
			
			theOutput = new GlutenFreeAPI().search(sms, User.getUser().getUSER_ID(), true);

			if (theOutput != null) {
				int totalCharacters = theOutput.length();
				theOutput = theOutput.replaceAll("\n", "<br>");
				//theOutput = theOutput + "<br><br>Character count: "  + totalCharacters;
			} else {
				theOutput = "No Match Found";
			}
			sb.append(theOutput);
		} catch (Exception e) {
			e.printStackTrace();
		}
		sb.append("<br>");
		sb.append(" ");
		sb.append("</p></html>");
		}
%>



<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 3.2//EN">
<html>
<head>
<!--<meta http-equiv="Content-Type" content="text/html; charset=windows-1255">-->
<link href="/include/theStyle.css" rel="stylesheet" type="text/css">
<title>Gluten Free Search Engine</title>
</head>

<body bgcolor="#FFFFFF" LEFTMARGIN="10" RIGHTMARGIN="10"
      link="#3366cc" vlink="#9999cc" alink="#0000cc">



<table  WIDTH="500" height="90%"  border="0" cellpadding="5">
	<tr valign="top">
		<td>
		<table  width="100%" height="90%"  border="1">
		<td valign="top" nowrap bgcolor="#f5f5e1" class="textSmall" >
		<%@ include file="../include/upperMenu.jsp" %>
		</td>
	</table>
</td>
<td></td>
		<td WIDTH="500">
			
			<table border="0"   cellpadding="1" cellspacing="1" class="frameTableT">
			
			
			<form name="query_form1" action="smsSimulator.jsp" method="POST">
					<tr><td colspan="1" nowrap class="headerHeb"><%=User.getUser().getFIRST_NAME() + " " + User.getUser().getLAST_NAME()%> <%=org.celiac.util.TemplateReader.getHebrewMapWord("SHALOM")%>,</td></tr>
					<tr><td colspan="1" nowrap class="headerHeb"><%=org.celiac.util.TemplateReader.getHebrewMapWord("BEHEFSHARUTCHA")%>:</td></tr>
					
					
			
				
				<tr bgcolor="#FFFFFF">
					
				</tr>
				<tr>
				<tr>
				<tr>
					

  <tr>
   
	
	
      
     <tr>
    <tr><td colspan="1" nowrap WIDTH="500" class="headerUnderLineHeb"><%=org.celiac.util.TemplateReader.getHebrewMapWord("BY_PRODUCT_AND_COMPANY")%>:</td></tr>
   
    

        
        
<tr><td>
        
        
<TABLE name=selectTable BORDER="3" BORDERCOLOR="#D8D8D8" BGCOLOR="#FFFFFF" style="width: 100%" CELLSPACING="1" CELLPADDING="3">

<TH  colspan="1"  style="width: 50%" class="headerUnderLine4"><%=org.celiac.util.TemplateReader.getHebrewMapWord("PRODUCT")%></TH>
<TH  colspan="1"  style="width: 50%" class="headerUnderLine4"><%=org.celiac.util.TemplateReader.getHebrewMapWord("COMPANY")%></TH>


<TR ALIGN="CENTER">
<%
String tmpcmpsel=companySelect3;
String tmpprdsel=productSelect3;
if (tmpcmpsel == null) tmpcmpsel ="";
if (tmpprdsel == null) tmpprdsel ="";

%>
<TD  colspan="1"  style="width: 50%" ><input type="text" value="<%=tmpprdsel%>" id="productSelect3" name="productSelect3" ><script>$(function() { $( "#productSelect3" ).autocomplete({ source: "getProductData.jsp", minLength: 3,});});</script></TD>
<TD  colspan="1"  style="width: 50%" ><input type="text" value="<%=tmpcmpsel%>" id="companySelect3" name="companySelect3" ><script>$(function() { $( "#companySelect3" ).autocomplete({ source: "getCompanyData.jsp", minLength: 3,});});</script></TD>



</TR>


</TABLE>
  </tr></td>     
        
  <tr><td>
          <input type="submit"  name="Submit" value="<%=org.celiac.util.TemplateReader.getHebrewMapWord("HAMSHECH")%>" style="width: 100%" class="button" enabled onMouseOver="this.style.background =  '#d5e0e6';" onMouseOut="this.style.background = '#e4e4e4';" >
          
          </font></div>
        </td>
      </tr> 
      
    </td>
  </tr>
  </tr>
  	<%if (!sms.equals("") && (!tmpcmpsel.equals("") || !tmpprdsel.equals("")) ){ %>
			<tr>
				<td>
					<br>
			<table width="100%" border="1" CELLSPACING="1" CELLPADDING="3">
				
      		
      		<tr><td colspan="1"  style="width: 100%" class="headerUnderLine2Heb"><%=sb%></td></tr>
      		
      </table>
      	</td>
  	</tr>
  		<%}%>
   <tr>
  	<td>
  	<br>
  </td>
	</tr>
	</form>
	

	
	<form name="query_form2" action="smsSimulator.jsp" method="POST">
  <tr>
    <tr><td colspan="1" nowrap style="width: 100%" class="headerUnderLineHeb"><%=org.celiac.util.TemplateReader.getHebrewMapWord("DROP_DOWN_BY_COMPANY")%>:</td></tr>
   
    
        
        
        
<tr><td>
        
        
<TABLE name=selectTable BORDER="3" BORDERCOLOR="#D8D8D8" BGCOLOR="#FFFFFF" style="width: 100%" CELLSPACING="1" CELLPADDING="3">

<TH  colspan="1"  style="width: 100%" class="headerUnderLine4"><%=org.celiac.util.TemplateReader.getHebrewMapWord("COMPANY")%></TH>
<TH  colspan="1"  style="width: 100%" class="headerUnderLine4"><%=org.celiac.util.TemplateReader.getHebrewMapWord("PRODUCT")%></TH>
<TH  colspan="1"  style="width: 100%" class="headerUnderLine4"><%=org.celiac.util.TemplateReader.getHebrewMapWord("CATEGORY")%></TH>

<TR ALIGN="LEFT">

<TD  colspan="1"  style="width: 100%" class="headerUnderLine3">
        <div align="right">
        <font face="Verdana, Arial, Helvetica, sans-serif">
        	 
         
          <select name="company" class="textfieldHeb" onchange="processSelectionCompany();">
          	
          	<%if (theCompany.equals("")) {%> <OPTION VALUE="" selected="selected">Select <%}%>
          	<%
          	
          	
          	
          	
          	java.util.Iterator<String> itr = null;
						itr = org.celiac.util.database.DBQueryFactory.getDBHandler().getCompanyList().iterator();
						String tmpCompany = "";
						int tmpCompanyCounter = 0;
						while (itr.hasNext()) {
						tmpCompanyCounter++;
						tmpCompany = itr.next();
						//companyHash.put(""+tmpCompanyCounter,tmpCompany);
						if ((""+tmpCompanyCounter).equals(""+theCompany)) companySelect = tmpCompany;
						
						%>
						<OPTION VALUE="<%="" + tmpCompanyCounter%>" <%if ((""+tmpCompanyCounter).equals(""+theCompany)){%>selected="selected"<%}%> ><%=tmpCompany%>					
						<%}%>
		 				
					</SELECT>
          
          
        </TD>
        
         
        
        
        
<TD colspan="1"  style="width: 100%" class="headerUnderLine3"><%if (!theCompany.equals("")) {%>
        
        <div align="right">
        <font face="Verdana, Arial, Helvetica, sans-serif">
        	
          <select name="product" class="textfieldHeb" onchange="processSelectionProduct();">
          	
          	<%if (theProduct.equals("")) {%> <OPTION VALUE="" selected="selected">Select <%}%>
          	<%
          	java.util.Iterator<String> itrProduct = null;
						itrProduct = org.celiac.util.database.DBQueryFactory.getDBHandler().getProductByCompanyList(companySelect).iterator();
						String tmpProduct = "";
						int tmpProductCounter = 0;
						String multipleProduct = "";
						while (itrProduct.hasNext()) {
						tmpProductCounter++;
						tmpProduct = itrProduct.next();
						 
						
						if ("MULTIPLE".equals(tmpProduct.trim())) {
						    if (itrProduct.hasNext())  multipleProduct = "מוצרים אחרים";
							else multipleProduct = "כלל המוצרים";
						}
						else multipleProduct = tmpProduct;

						if ((""+tmpProductCounter).equals(""+theProduct)) productSelect = tmpProduct;
						
						%>
						<OPTION  VALUE="<%="" + tmpProductCounter%>" <%if ((""+tmpProductCounter).equals(""+theProduct)){%>selected="selected"<%}%> ><%=multipleProduct%>					
						<%}%>
		 				
					</SELECT>
          
          
        
        <%}%></TD>
<TD colspan="1"  style="width: 100%" class="headerUnderLine3"><%if (!theProduct.equals("")) {%>
        
        <div align="right">
        <font face="Verdana, Arial, Helvetica, sans-serif">
        
          <select name="category" class="textfieldHeb" onchange="document.query_form2.Submit.focus()">
          	
          	
          	<%
          	java.util.Iterator<String> itrCategory = null;
						itrCategory = org.celiac.util.database.DBQueryFactory.getDBHandler().getCategoryByProductAndCompanyList(productSelect,companySelect).iterator();
						String tmpCategory = "";
						int tmpCategoryCounter = 0;
						categoryHash = new java.util.HashMap();
						while (itrCategory.hasNext()) {
						tmpCategoryCounter++;
						tmpCategory = itrCategory.next();
						categoryHash.put("" + tmpCategoryCounter,tmpCategory);
						
						%>
						<OPTION VALUE="<%="" + tmpCategoryCounter%>" <%if ((""+tmpCategoryCounter).equals(""+theCategory)){%>selected="selected"<%}%> ><%=tmpCategory%>					
						<%}%>
		 				
					</SELECT>
           
            
     
        <%
        session.setAttribute("userCategoryHash",categoryHash); 
        	} else {session.setAttribute("userCategoryHash",null);}
        %>
        </TD>

</TR>


</TABLE>
  </tr></td>     
        
        <tr><td>
        	<input type="hidden" value="<%=companySelect%>" name="companySelect" >
        	<input type="hidden" value="<%=productSelect%>" name="productSelect" >
          <input type="submit"  name="Submit" value="<%=org.celiac.util.TemplateReader.getHebrewMapWord("HAMSHECH")%>" style="width: 100%" class="button" enabled onMouseOver="this.style.background =  '#d5e0e6';" onMouseOut="this.style.background = '#e4e4e4';" >
         
          </font></div>
        </td>
      </tr>
      
    </form>
    	<%if (!sms.equals("") && !theProduct.equals("")){ %>
			<tr>
				<td>
					<br>
			<table height="100%" width="100%" border="1" CELLSPACING="1" CELLPADDING="3">
				
      	  <!-- Image -->	
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
      	  
      	  
      		<tr>      			
      		<td colspan="1"  class="headerUnderLine2Heb"><%=sb%></td>  		
      		
      			<%if (!(!(User.getUser().getPERMISSIONS().equals("admin")) && !hasImage)) {%> 
      		
      		<td class="headerUnderLine2Heb" width="100" height="100%" >
      			<table height="100%" border="0" CELLSPACING="0" CELLPADDING="0">
      			<tr>
      				<td valign="center">
      					<table width="100%" border="<%=border%>" CELLSPACING="2" CELLPADDING="5">	
      						<tr>
      							<% if (hasImage){ %>
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
      					<%if ((User.getUser().getPERMISSIONS().equals("admin"))){%> 
      						<tr>
      						
      						<form action="uploadImage.jsp" method="post"  target="foo" onSubmit="window.open('', 'foo', 'width=600,height=550,status=yes,resizable=yes,scrollbars=yes')">
      						<td width="100%"><input type="submit"  name="Submit" value="ניהול תמונה" style="width: 100%" class="button" enabled onMouseOver="this.style.background =  '#d5e0e6';" onMouseOut="this.style.background = '#e4e4e4';" ></td>
      						<input type="hidden" name="productSelect" value="<%=productSelect%>">
      						<input type="hidden" name="companySelect" value="<%=companySelect%>">
      						<input type="hidden" name="categorySelect" value="<%=categorySelect%>">
      						<%
      							session.setAttribute("recommand",sb);
      						%>
      						
      						</form>
      					<%}
      					
      					if (hasImage){
      					
      						%>
      						<form action="largeImage.jsp" method="post"  target="foo" onSubmit="window.open('', 'foo', 'width=570,height=570,status=yes,resizable=yes,scrollbars=yes')">
      						<input type="hidden" name="productSelect" value="<%=productSelect%>">
      						<input type="hidden" name="companySelect" value="<%=companySelect%>">
      						<input type="hidden" name="categorySelect" value="<%=categorySelect%>">
      						<td width="50%"><input type="submit"  name="Submit" value="הגדלה" style="width: 100%" class="button" enabled onMouseOver="this.style.background =  '#d5e0e6';" onMouseOut="this.style.background = '#e4e4e4';" ></td>
      					</form>
      						
      					<%}%>
      						</tr>
      		  	</table>
      			</td>
      		
      			
      			</tr>
      		</table>
      		</td>
      		
      		<%}%>
      		</tr>
      		
      		
      </table>
      <br>
      	</td>
  	</tr>
  		<%}%>
    
    
    
      <form name="query_form3" action="smsSimulator.jsp" method="POST">
      
      
      <tr>
  	<td>
  	<br>
  </td>
	</tr>
      
     <tr>
    <tr><td colspan="1" nowrap style="width: 100%" class="headerUnderLineHeb"><%=org.celiac.util.TemplateReader.getHebrewMapWord("DROP_DOWN_BY_CATEGORY")%>:</td></tr>
   
    

        
        
<tr><td>
        
        
<TABLE name=selectTable BORDER="3" BORDERCOLOR="#D8D8D8" BGCOLOR="#FFFFFF" style="width: 100%" CELLSPACING="1" CELLPADDING="3">

<TH  colspan="1"  style="width: 100%" class="headerUnderLine4"><%=org.celiac.util.TemplateReader.getHebrewMapWord("CATEGORY")%></TH>
<TH  colspan="1"  style="width: 100%" class="headerUnderLine4"><%=org.celiac.util.TemplateReader.getHebrewMapWord("COMPANY")%></TH>
<TH  colspan="1"  style="width: 100%" class="headerUnderLine4"><%=org.celiac.util.TemplateReader.getHebrewMapWord("PRODUCT")%></TH>

<TR ALIGN="LEFT">

<TD  colspan="1"  style="width: 100%" class="headerUnderLine3">
        <div align="right">
        <font face="Verdana, Arial, Helvetica, sans-serif">
        	 
         
          <select name="category2" class="textfieldHeb" onchange="processSelectionCategory2();">
          	
          	<%if (theCategory2.equals("")) {%> <OPTION VALUE="" selected="selected">Select <%}%>
          	<%
          	
          	
          	java.util.Iterator<String> itr2 = null;
						itr2 = org.celiac.util.database.DBQueryFactory.getDBHandler().getCategoryList().iterator();
						String tmpCategory2 = "";
						int tmpCategoryCounter2 = 0;
						while (itr2.hasNext()) {
						tmpCategoryCounter2++;
						tmpCategory2 = itr2.next();
						
						if ((""+tmpCategoryCounter2).equals(""+theCategory2)) categorySelect2 = tmpCategory2;
						
						%>
						<OPTION VALUE="<%="" + tmpCategoryCounter2%>" <%if ((""+tmpCategoryCounter2).equals(""+theCategory2)){%>selected="selected"<%}%> ><%=tmpCategory2%>					
						<%}%>
		 				
					</SELECT>
          
          
        </TD>
        
<TD colspan="1"  style="width: 100%" class="headerUnderLine3"><%if (!theCategory2.equals("")) {%>
        
        <div align="right">
        <font face="Verdana, Arial, Helvetica, sans-serif">
        
          <select name="company2" class="textfieldHeb" onchange="processSelectionCompany2();">
          	
          	<%if (theCompany2.equals("")) {%> <OPTION VALUE="" selected="selected">Select <%}%>
          	<%
          	java.util.Iterator<String> itrCompany2 = null;
						itrCompany2 = org.celiac.util.database.DBQueryFactory.getDBHandler().getCompanyByCategoryList(categorySelect2).iterator();
						String tmpCompany2 = "";
						int tmpCompanyCounter2 = 0;
						
						while (itrCompany2.hasNext()) {
						tmpCompanyCounter2++;
						tmpCompany2 = itrCompany2.next();
						if ((""+tmpCompanyCounter2).equals(""+theCompany2)) companySelect2 = tmpCompany2;
						
						%>
						<OPTION VALUE="<%="" + tmpCompanyCounter2%>" <%if ((""+tmpCompanyCounter2).equals(""+theCompany2)){%>selected="selected"<%}%> ><%=tmpCompany2%>					
						<%}%>
		 				
					</SELECT>
           
            
     
        <%
        }
        %>
        </TD>
               
        
        
				<TD colspan="1"  style="width: 100%" class="headerUnderLine3"><%if (!theCompany2.equals("")) {%>
        
        <div align="right">
        <font face="Verdana, Arial, Helvetica, sans-serif">
        	
          <select name="product2" class="textfieldHeb" onchange="document.query_form3.Submit.focus()" >
          	
          	
          	<%
          	java.util.Iterator<String> itrProduct2 = null;
						itrProduct2 = org.celiac.util.database.DBQueryFactory.getDBHandler().getProductByCategoryAndCompanyList(categorySelect2,companySelect2).iterator();
						String tmpProduct2 = "";
						int tmpProductCounter2 = 0;
						String multipleProduct2 = "";
						productHash = new java.util.HashMap();
						while (itrProduct2.hasNext()) {
						tmpProductCounter2++;
						tmpProduct2 = itrProduct2.next();
						productHash.put("" + tmpProductCounter2,tmpProduct2);
						
						if ("MULTIPLE".equals(tmpProduct2.trim())) {
						    if (itrProduct2.hasNext())  multipleProduct2 = "מוצרים אחרים";
							else multipleProduct2 = "כלל המוצרים";
						}
						else multipleProduct2 = tmpProduct2;
						
						
						%>
						<OPTION  VALUE="<%="" + tmpProductCounter2%>" <%if ((""+tmpProductCounter2).equals(""+theProduct2)){%>selected="selected"<%}%> ><%=multipleProduct2%>					
						<%}%>
		 				
					</SELECT>
          
          
        
        <%session.setAttribute("userProductHash",productHash); 
        	} else {session.setAttribute("userProductHash",null);}%></TD>

</TR>


</TABLE>
  </tr></td>     
        
        <tr><td>
        	<input type="hidden" value="<%=companySelect2%>" name="companySelect2" >
        	<input type="hidden" value="<%=categorySelect2%>" name="categorySelect2" >
          <input type="submit"  name="Submit" value="<%=org.celiac.util.TemplateReader.getHebrewMapWord("HAMSHECH")%>" style="width: 100%" class="button" enabled onMouseOver="this.style.background =  '#d5e0e6';" onMouseOut="this.style.background = '#e4e4e4';" >
         
          </font></div>
        </td>
      </tr>
      
    </td>
  </tr>
  </tr>
      
      
    </td>
  </tr>
  </tr>
  
  
</form>
			
			<%if ((!sms.equals("") && !theCompany2.equals("")) || (foundMoreThenOnePerProduct && !theCompany2.equals(""))){ %>
			<tr>
				<td>
					<br>
			<table ALIGN="LEFT" width="100%" border="0" CELLSPACING="1" CELLPADDING="3">
				
				<% if (!("".equals(sb.toString().trim()))){%>
				<tr><td>
				<table ALIGN="LEFT" width="100%" border="2" CELLSPACING="1" CELLPADDING="3">
				<tr><td colspan="1"  style="width: 100%" class="headerUnderLine2Heb"><%=sb%></td></tr>
			 </table>
			</td></tr>
			 <tr><td></td></tr>
				<%}%>
				
			
				
      	<% if (foundMoreThenOnePerProduct){ %>
      	<tr>
  
  	    <td colspan="1"  style="width: 100%">
  		    <TABLE name=selectTable BORDER="2" BORDERCOLOR="#D8D8D8" BGCOLOR="#FFFFFF" style="width: 100%" CELLSPACING="1" CELLPADDING="3">
  			    <TH  colspan="1"  style="width: 8%" class="headerUnderLineForMultipleTrue"><%=companyForMultiple%> (<%=companyDate%>)</TH>	
  		    </TABLE>
	 	    </td></tr>
       <tr><td colspan="1"  style="width: 100%">
      	<TABLE name="mul" BORDER="0" width="100%" BORDERCOLOR="#FFFFFF" BGCOLOR="#FFFFFF"  CELLSPACING="5" CELLPADDING="0">
      	<%
      	
      	GFS gfs = new GFSByCategoryAndCompanyImpl();
			  Map<String,SortedSet<String>> codePerProd = gfs.searchForAllBasedOnCategory(companyForMultiple,categoryForMultiple);	
			  Iterator<String> itr5 = codePerProd.keySet().iterator();
			  Iterator<String> productIter = null;
			  SortedSet<String> listOfProduct = null;
			
			  String recCode = null;
			  boolean color = false;
			  while (itr5.hasNext()) {
				  recCode = (String)itr5.next();
				  listOfProduct = (SortedSet<String>)codePerProd.get(recCode);
				  productIter = listOfProduct.iterator();
				  %>
				  <TD valign="top">
				  	<TABLE name="mul" BORDER="2" width="100%" BORDERCOLOR="#d5e0e6" BGCOLOR="#FFFFFF"  CELLSPACING="0" CELLPADDING="0">
				  		<TD>
				  <% if (color) {
				  	color = false;
				  	%>
				  	<TABLE name="mulProd" BORDER="0" width="100%" BORDERCOLOR="#d5e0e6" BGCOLOR="#f5f5e1"  CELLSPACING="3" CELLPADDING="3">
						<TH  colspan="1"  class="headerUnderLine4"><%=org.celiac.util.TemplateReader.getHebrewMapWord(recCode)%></TH>
				  <%}else {
				     color = true;
				     %>
				  	<TABLE name="mulProd" BORDER="0" width="100%" BORDERCOLOR="#d5e0e6" BGCOLOR="#FFFFFF"  CELLSPACING="3" CELLPADDING="3">
						<TH  colspan="1"  class="headerUnderLine4"><%=org.celiac.util.TemplateReader.getHebrewMapWord(recCode)%></TH>
				  <%}%>
				  <%
				  while (productIter.hasNext()){
				  %>
				  <TR >
				  	<% if (!color) { %>
					<TD  colspan="1"  style="width: 100%" class="headerUnderLineForMultipleTrue">
					<% } else { %>
					<TD  colspan="1"  style="width: 100%" class="headerUnderLineForMultipleFalse">
						<%}%>
       	  <font face="Verdana, Arial, Helvetica, sans-serif">
       	  	<%String thisIsTheProduct = (String)productIter.next();
       	  	 if ("MULTIPLE".equals(thisIsTheProduct)) thisIsTheProduct = org.celiac.util.TemplateReader.getHebrewMapWord("KLAL") + " " + categoryForMultiple;
       	  	%>
					  <%=thisIsTheProduct%>
					</TD>
				  <%}%>
				
				</TABLE>
			</td>
				  </TABLE>
				 
				</TD>
				<%
				
			  }
      	
      	%>
      		
      		
      		
      		
      		
      	</TABLE>
      </td></tr>
      	<%} %>
      	
      	
      	
      		
      </table>
      	</td>
  	</tr>
  		<%}%>
  		
  
  	</table>
			<br>
		</td>
	</tr>
</table>








<%@ include file="../include/buttom.jsp" %>    