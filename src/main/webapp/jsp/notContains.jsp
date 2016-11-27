<%@ page language="java" 
         import="org.celiac.api.GlutenFreeAPI, org.celiac.web.bean.*, java.util.*, org.celiac.business.GFS, org.celiac.business.GFSByCategoryAndCompanyImpl, org.celiac.datatype.*" 
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

<SCRIPT LANGUAGE="JavaScript">		

	
	

	
	function processSelectionCategory2()
	{
	  // Get the index of th selection.
	  var theUrl = "smsSimulator.jsp?theCategory2=" + document.query_form3.category2.value;
	  window.open(theUrl,"_self");
	 	  	  
	}
	
	
	
	
	
</SCRIPT>


<%
request.setCharacterEncoding("utf-8");
 response.setContentType("text/html;charset=UTF-8");

java.util.HashMap companyHash = null;

 java.util.Set<org.celiac.datatype.IndexRow> categSet = null;


String categorySelect2 = null;
String theCategory2 = "";




if (request.getParameter("categorySelect2") != null){
		categorySelect2 = (String)request.getParameter("categorySelect2");
		
}

if (request.getParameter("theCategory2") != null){
		theCategory2 = request.getParameter("theCategory2");
		
} else if (request.getParameter("category2") != null){
		theCategory2 = request.getParameter("category2");	
}


	




//  ***********************************************



boolean foundMoreThenOnePerProduct = true;

String companyDate = "";

if (!theCategory2.equals("")) {

			categorySelect2 = (String)(org.celiac.util.database.DBQueryFactory.getDBHandler().getCategoryListThatDoNotContainsGluten().toArray()[new Integer(theCategory2).intValue() - 1]);
      categSet = org.celiac.util.database.DBQueryFactory.getDBHandler().notContainGF(categorySelect2);
      //companyDate = org.celiac.util.database.DBQueryFactory.getDBHandler().getDateofCompany(companySelect2);
      if (companyDate == null) companyDate = "";
     
 
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
			
			
			<form name="query_form1" action="notContains.jsp" method="POST">
					<tr><td colspan="1" nowrap class="headerHeb"><%=User.getUser().getFIRST_NAME() + " " + User.getUser().getLAST_NAME()%> <%=org.celiac.util.TemplateReader.getHebrewMapWord("SHALOM")%>,</td></tr>
					<tr><td colspan="1" nowrap class="headerHeb"><%=org.celiac.util.TemplateReader.getHebrewMapWord("LEFANECHA_WITHOUT_GLUTEN")%></td></tr>
					<tr><td colspan="1" nowrap class="headerHeb"><font color="#FF0000">אין ברשימות אלה ע"מ לתת איזו שהיא אינדיקציה לגבי תכולת הגלוטן במוצרים.</font></td></tr>
					<tr><td colspan="1" nowrap class="headerHeb"><font color="#FF0000">ע"מ לדעת אם מוצר מהרשימות לעיל מכיל גלוטן, אנא השתמש ב"בדיקת מוצרים" שבתפריט הימני.</font></td></tr>
					
					
			
				
				<tr bgcolor="#FFFFFF">
					
				</tr>
				<tr>
				<tr>
				<tr>
					

  <tr>
   
	
	
      
     <tr>
    <tr><td colspan="1" nowrap style="width: 100%" class="headerUnderLineHeb"><%=org.celiac.util.TemplateReader.getHebrewMapWord("CATEGORIA_WITHOUT_GLUTEN")%></td></tr>
   
    

        
        
<tr><td>
        
        
<TABLE name=selectTable BORDER="3" BORDERCOLOR="#D8D8D8" BGCOLOR="#FFFFFF" style="width: 100%" CELLSPACING="1" CELLPADDING="3">

<TH  colspan="1"  style="width: 100%" class="headerUnderLine4"><%=org.celiac.util.TemplateReader.getHebrewMapWord("CATEGORY")%></TH>

<TR ALIGN="LEFT">

<TD  colspan="1"  style="width: 100%" class="headerUnderLine3">
        <div align="right">
        <font face="Verdana, Arial, Helvetica, sans-serif">
        	 
         
          <select name="category2" class="textfieldHeb" onchange="processSelectionCategory2();">
          	
          	<%if (theCategory2.equals("")) {%> <OPTION VALUE="" selected="selected">Select <%}%>
          	<%
          	
          	
          	java.util.Iterator<String> itr2 = null;
						itr2 = org.celiac.util.database.DBQueryFactory.getDBHandler().getCategoryListThatDoNotContainsGluten().iterator();
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
        

</TR>


</TABLE>
  </tr></td>     
        
        <tr><td>
    
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


</table/>

			
			<%if ((foundMoreThenOnePerProduct && !theCategory2.equals(""))){ %>
			<table border="0"   cellpadding="1" cellspacing="1" class="frameTableT">
			<tr>
				<td>
					<br>
			<table ALIGN="LEFT" width="100%" border="0" CELLSPACING="1" CELLPADDING="3">
				
			
				
			
				
      	<% if (foundMoreThenOnePerProduct){ %>
      	
      	<tr>
  
  	    <td colspan="1"  style="width: 100%">
  		    <TABLE name=selectTable BORDER="2" BORDERCOLOR="#D8D8D8" BGCOLOR="#FFFFFF" style="width: 100%" CELLSPACING="1" CELLPADDING="3">
  			    <TH  colspan="1"  style="width: 8%" class="headerUnderLineForMultipleTrue"><%=categorySelect2%></TH>	
  		    </TABLE>
	 	    </td></tr>
       <tr><td colspan="1"  style="width: 100%">
      	<TABLE name="mul" BORDER="0" width="100%" BORDERCOLOR="#FFFFFF" BGCOLOR="#FFFFFF"  CELLSPACING="5" CELLPADDING="0">
      	<%
      	
      	
      	
      	companyHash = new java.util.HashMap();
			  Iterator<org.celiac.datatype.IndexRow> itr5 = categSet.iterator();
			  org.celiac.datatype.IndexRow recCode = null;
			  
			  boolean color = false;
			  
			  
			  
			  while (itr5.hasNext()) {
				  recCode = (org.celiac.datatype.IndexRow)itr5.next();
				  
				  if (companyHash.get(recCode.getCompany()) == null) {
				  	Set<String> compProducts = new HashSet<String>();
				  	compProducts.add(recCode.getProduct());
				  	companyHash.put(recCode.getCompany(),compProducts);
				  } else {
				  	Set<String> compProducts = (Set<String>)companyHash.get(recCode.getCompany());
				  	compProducts.add(recCode.getProduct());
				  	companyHash.put(recCode.getCompany(),compProducts);
				  }
				  
				}
				 
			
				Iterator itr6 = companyHash.keySet().iterator();
				String compName = null;
				
				while (itr6.hasNext()) {
					
				compName = (String)itr6.next();
				
				  
				  %>
				  <TD valign="top">
				  	<TABLE name="mul" BORDER="2" width="100%" BORDERCOLOR="#d5e0e6" BGCOLOR="#FFFFFF"  CELLSPACING="0" CELLPADDING="0">
				  		<TD>
				  <% if (color) {
				  	color = false;
				  	%>
				  	<TABLE name="mulProd" BORDER="0" nowrap width="100%" BORDERCOLOR="#d5e0e6" BGCOLOR="#f5f5e1"  CELLSPACING="3" CELLPADDING="3">
						<TH  colspan="1"  nowrap class="headerUnderLine4"><%=compName%></TH>
						
				  <%}else {
				     color = true;
				     %>
				  	<TABLE name="mulProd" BORDER="0" nowrap width="100%" BORDERCOLOR="#d5e0e6" BGCOLOR="#FFFFFF"  CELLSPACING="3" CELLPADDING="3">
				  		<TH  colspan="1" nowrap class="headerUnderLine4"><%=compName%></TH>
				  <%}%>
				 
				
				 
				    <%
				    
				    
				   
				  Iterator itr7 = ((Set<String>)(companyHash.get(compName))).iterator();
				  while (itr7.hasNext()){
				  
				  %>
				 
				  <TR >
				  	<% if (!color) { %>
					<TD  colspan="1" nowrap style="width: 100%" class="headerUnderLineForMultipleTrue">
					<% } else { %>
					<TD  colspan="1" nowrap style="width: 100%" class="headerUnderLineForMultipleFalse">
						<%}%>
       	  <font face="Verdana, Arial, Helvetica, sans-serif">
       	  	<%String thisIsTheProduct = (String)itr7.next();
       	  	 if ("MULTIPLE".equals(thisIsTheProduct)) thisIsTheProduct = org.celiac.util.TemplateReader.getHebrewMapWord("KLAL") + " " + categorySelect2;
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