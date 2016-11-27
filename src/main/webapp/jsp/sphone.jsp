<%@ page language="java" 
         import="org.celiac.api.GlutenFreeAPI, org.celiac.web.bean.*, java.util.*" 
         errorPage="loginError.jsp" %>
<jsp:useBean id="User" scope="session" class="org.celiac.web.bean.User">
  <jsp:setProperty name="User" property="*"/>
</jsp:useBean>
<html DIR="RTL">
<head>
<%
try{
if (User.getUser() == null) throw new Exception("User was not login. Please login.");
%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link href="../include/theStyle.css" rel="stylesheet" type="text/css">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="REFRESH" CONTENT="600">
<title>GF-Guide</title>
</head>

<SCRIPT LANGUAGE="JavaScript">		

	function processSelectionCompany()
	{
	  // Get the index of th selection.
	  var theUrl = "sphone.jsp?theCompany=" + document.query_form2.company.value;
	  window.open(theUrl,"_self");
	 	  	  
	}
	
	function processSelectionProduct()
	{
	  // Get the index of th selection.
	  var theUrl = "sphone.jsp?theCompany=" + document.query_form2.company.value + "&theProduct=" + document.query_form2.product.value;
	  window.open(theUrl,"_self");
	 	  	  
	}
	
	function processSelectionCategory2()
	{
	  // Get the index of th selection.
	  var theUrl = "sphone.jsp?theCategory2=" + document.query_form3.category2.value;
	  window.open(theUrl,"_self");
	 	  	  
	}
	
	function processSelectionCompany2()
	{
	  // Get the index of th selection.
	  var theUrl = "sphone.jsp?theCompany2=" + document.query_form3.company2.value + "&theCategory2=" + document.query_form3.category2.value;
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

if (!theCompany2.equals("")) {
			categorySelect2 = (String)(org.celiac.util.database.DBQueryFactory.getDBHandler().getCategoryList().toArray()[new Integer(theCategory2).intValue() - 1]);
      companySelect2 = (String)(org.celiac.util.database.DBQueryFactory.getDBHandler().getCompanyByCategoryList(categorySelect2).toArray()[new Integer(theCompany2).intValue() - 1]); 	
      java.util.Set<String> categSet = org.celiac.util.database.DBQueryFactory.getDBHandler().getProductByCategoryAndCompanyList(categorySelect2,companySelect2);
  if (categSet.size() == 1){
    productSelect2 = (String)(categSet.iterator().next());
  }
}




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
			
			theOutput = new GlutenFreeAPI().search(sms, "FROM-CELL", true);

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
</head>

<body bgcolor="#ffffff" LEFTMARGIN="10" RIGHTMARGIN="10"
      link="#3366cc" vlink="#9999cc" alink="#0000cc">




			
			<table border="0"   cellpadding="1" cellspacing="0" class="frameTableT">
			
					<tr><td colspan="1" nowrap class="headerHeb"><%=User.getUser().getFIRST_NAME() + " " + User.getUser().getLAST_NAME()%> <%=org.celiac.util.TemplateReader.getHebrewMapWord("SHALOM")%></td></tr>
		

        
        

	
	<form name="query_form2" action="sphone.jsp" method="POST">
 
    <tr><td colspan="1" nowrap style="width: 100%" class="headerUnderLineHeb"><%=org.celiac.util.TemplateReader.getHebrewMapWord("DROP_DOWN_BY_COMPANY")%></td></tr>
   
    
        
        
        
<tr><td>
        
        
<TABLE name=selectTable BORDER="2" BORDERCOLOR="#D8D8D8" BGCOLOR="#FFFFFF" style="width: 100%" CELLSPACING="0" CELLPADDING="0">


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
        
        
        
<%if (!theCompany.equals("")) {%>     
<tr>    
<TD colspan="1"  style="width: 100%" class="headerUnderLine3">
        
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
						if ("MULTIPLE".equals(tmpProduct.trim())) multipleProduct = "מוצרים אחרים";
						else multipleProduct = tmpProduct;
						if ((""+tmpProductCounter).equals(""+theProduct)) productSelect = tmpProduct;
						
						%>
						<OPTION  VALUE="<%="" + tmpProductCounter%>" <%if ((""+tmpProductCounter).equals(""+theProduct)){%>selected="selected"<%}%> ><%=multipleProduct%>					
						<%}%>
		 				
					</SELECT>
          
          
        
        </TD>
</tr><%}%>
<%if (!theProduct.equals("")) {%>
<tr>
<TD colspan="1"  style="width: 100%" class="headerUnderLine3">
        
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
           
            
     
        
      </TD></tr>
      <%
        session.setAttribute("userCategoryHash",categoryHash); 
        	} else {session.setAttribute("userCategoryHash",null);}
        %>




</TABLE>
  </tr></td>     
        
        <tr><td>
        	<input type="hidden" value="<%=companySelect%>" name="companySelect" >
        	<input type="hidden" value="<%=productSelect%>" name="productSelect" >
          <input type="submit"  name="Submit" value="<%=org.celiac.util.TemplateReader.getHebrewMapWord("HAMSHECH")%>" style="width: 100%" class="button" enabled onMouseOver="this.style.background =  '#d5e0e6';" onMouseOut="this.style.background = '#e4e4e4';" >
          <br>
          </font></div>
        </td>
      </tr>
      
    </form>
    
    <%if (!sms.equals("") && !theProduct.equals("")){ %>
			<tr><td>
			<table width="100%" border="1" CELLSPACING="1" CELLPADDING="3">
			
      		
      		<tr><td colspan="1"  style="width: 100%" class="headerUnderLine2Heb"><%=sb%></td></tr>
      		
      	
  		</table>
  	</td></tr>
  		<%}%>
    
    
      <form name="query_form3" action="sphone.jsp" method="POST">
      
      
      <tr>
  	<td>
  	<br>
  </td>
	</tr>
      
  
    <tr><td colspan="1" nowrap style="width: 100%" class="headerUnderLineHeb"><%=org.celiac.util.TemplateReader.getHebrewMapWord("DROP_DOWN_BY_CATEGORY")%></td></tr>
   
    

        
        
<tr><td>
        
        
<TABLE name=selectTable BORDER="3" BORDERCOLOR="#D8D8D8" BGCOLOR="#FFFFFF" style="width: 100%" CELLSPACING="0" CELLPADDING="0">

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
        <%if (!theCategory2.equals("")) {%>
<tr>   
<TD colspan="1"  style="width: 100%" class="headerUnderLine3">
        
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
           
            
     
      
        </TD>
               
        
      </tr>  <%
        }
        %>
        <%if (!theCompany2.equals("")) {%>
        <tr>
				<TD colspan="1"  style="width: 100%" class="headerUnderLine3">
        
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
						if ("MULTIPLE".equals(tmpProduct2.trim())) multipleProduct2 = "מוצרים אחרים";
						else multipleProduct2 = tmpProduct2;
						
						
						%>
						<OPTION  VALUE="<%="" + tmpProductCounter2%>" <%if ((""+tmpProductCounter2).equals(""+theProduct2)){%>selected="selected"<%}%> ><%=multipleProduct2%>					
						<%}%>
		 				
					</SELECT>
          
          
        
       </TD></tr>
        <%session.setAttribute("userProductHash",productHash); 
        } else {session.setAttribute("userProductHash",null);}%>

</TR>


</TABLE>
  </tr></td>     
        
        <tr><td>
        	<input type="hidden" value="<%=companySelect2%>" name="companySelect2" >
        	<input type="hidden" value="<%=categorySelect2%>" name="categorySelect2" >
          <input type="submit"  name="Submit" value="<%=org.celiac.util.TemplateReader.getHebrewMapWord("HAMSHECH")%>" style="width: 100%" class="button" enabled onMouseOver="this.style.background =  '#d5e0e6';" onMouseOut="this.style.background = '#e4e4e4';" >
          <br>
          </font></div>
        </td>
      </tr>
      
    </td>
  </tr>
  </tr>
      
      
    </td>
  </tr>
 
  
  
</form>
			
			<%if (!sms.equals("") && !theCompany2.equals("")){ %>
			<tr><td>
			<table width="100%" border="1" CELLSPACING="1" CELLPADDING="3">
				
      		
      		<tr><td colspan="1"  style="width: 100%" class="headerUnderLine2Heb"><%=sb%></td></tr>
      		
      		
      
  		</table>
  	</td></tr>
  		<%}%>
  
  	</table>
	





</body>

</html>

<%
}catch (Exception e){

	String theErrorOut = e.getMessage();
	session.setAttribute("gfsmsLoginError",theErrorOut);
	response.sendRedirect("../index.jsp");
}
%>