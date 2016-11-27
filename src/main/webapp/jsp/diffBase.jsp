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



<%
request.setCharacterEncoding("utf-8");
 response.setContentType("text/html;charset=UTF-8");



String categorySelect2 = null;
String theCategory2 = "";
org.celiac.datatype.DiffDataCollection diffDataCollection = null;
java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");




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
	
	  
	  org.celiac.util.GuideDiff guideDiff = new org.celiac.util.GuideDiff();
	  try{
	  if (theCategory2.startsWith("DAY")) diffDataCollection = guideDiff.getDiffReport(null,theCategory2.substring(3),null,null);
	  else if (theCategory2.startsWith("MONTH")) diffDataCollection = guideDiff.getDiffReport(null,null,theCategory2.substring(5),null);
	  else if (theCategory2.startsWith("YEAR")) diffDataCollection = guideDiff.getDiffReport(null,null,null,theCategory2.substring(4));
	  else if (theCategory2.startsWith("DATE")) diffDataCollection = guideDiff.getDiffReport( new java.text.SimpleDateFormat("dd/mm/yyy").parse(theCategory2.substring(5)),null,null,null);
	  }catch (Exception e){
		  e.printStackTrace();
	  }
	       
 System.out.println(diffDataCollection.companyAdded.size());
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



<table  height="90%"  border="0" cellpadding="5">
	<tr valign="top">
		<td>
		<table  width="100%" height="90%"  border="1">
		<td valign="top" nowrap bgcolor="#f5f5e1" class="textSmall" >
		<%@ include file="../include/upperMenu.jsp" %>
		</td>
	</table>
</td>
<td></td>
		<td >
			
			<table border="0"   cellpadding="1" cellspacing="1" class="frameTableT">
			
			
			<form name="query_form1" action="diffBase.jsp" method="POST">
					<tr><td colspan="1" nowrap class="headerHeb"><%=User.getUser().getFIRST_NAME() + " " + User.getUser().getLAST_NAME()%> <%=org.celiac.util.TemplateReader.getHebrewMapWord("SHALOM")%>,</td></tr>
					<tr><td colspan="1" nowrap class="headerHeb"><%=org.celiac.util.TemplateReader.getHebrewMapWord("DIFF_HEADER")%></td></tr>
					
					
			
				
				<tr bgcolor="#FFFFFF">
					
				</tr>
				<tr>
				<tr>
				<tr>
					

  <tr>
   
	
	
      
     <tr>
    <tr><td colspan="1" nowrap style="width: 100%" class="headerUnderLineHeb"><%=org.celiac.util.TemplateReader.getHebrewMapWord("DIFF_DATE_SELECT")%></td></tr>
   
    

        
        
<tr><td>
        
        
<TABLE name=selectTable BORDER="3" BORDERCOLOR="#D8D8D8" BGCOLOR="#FFFFFF" style="width: 100%" CELLSPACING="1" CELLPADDING="3">

<TH  colspan="1"  style="width: 100%" class="headerUnderLine4"><%=org.celiac.util.TemplateReader.getHebrewMapWord("DIFF_DATE")%></TH>

<TR ALIGN="LEFT">

<TD  colspan="1"  style="width: 100%" class="headerUnderLine3">
        <div align="right">
        <font face="Verdana, Arial, Helvetica, sans-serif">
        	 
         
          <select name="category2" class="textfieldHeb" ">
          	
          	<%
          	categorySelect2 = theCategory2;
          	if (theCategory2.equals("")) {%> 
          		<OPTION VALUE="" selected="selected">בחר תאריך 
          	<%}%>
          						
						
						<OPTION VALUE="DAY0" <%if (("DAY0").equals(""+theCategory2)){%>selected="selected"<%}%> >היום
						<OPTION VALUE="DAY-1" <%if (("DAY-1").equals(""+theCategory2)){%>selected="selected"<%}%> >אתמול
						<OPTION VALUE="DAY-7" <%if (("DAY-7").equals(""+theCategory2)){%>selected="selected"<%}%> >לפני שבוע	
						<OPTION VALUE="DAY-14" <%if (("DAY-14").equals(""+theCategory2)){%>selected="selected"<%}%> >לפני שבועיים
						<OPTION VALUE="MONTH-1" <%if (("MONTH-1").equals(""+theCategory2)){%>selected="selected"<%}%> >לפני חודש	
						<OPTION VALUE="MONTH-2" <%if (("MONTH-2").equals(""+theCategory2)){%>selected="selected"<%}%> >לפני חודשיים
						<OPTION VALUE="MONTH-3" <%if (("MONTH-3").equals(""+theCategory2)){%>selected="selected"<%}%> >לפני שלושה חודשים		
						<OPTION VALUE="MONTH-6" <%if (("MONTH-6").equals(""+theCategory2)){%>selected="selected"<%}%> >לפני חצי שנה
						<OPTION VALUE="YEAR-1" <%if (("YEAR-1").equals(""+theCategory2)){%>selected="selected"<%}%> >לפני שנה
						<OPTION VALUE="YEAR-2" <%if (("YEAR-2").equals(""+theCategory2)){%>selected="selected"<%}%> >לפני שנתיים
					
		 				
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

			
			
			<table border="0"   cellpadding="1" cellspacing="1" class="frameTableT">
			<tr>
				<td>
					<br>
			<table ALIGN="LEFT" width="100%" border="0" CELLSPACING="1" CELLPADDING="3">
				
			<%if ((foundMoreThenOnePerProduct && !theCategory2.equals(""))){ %>
				
			
				
      	<% if (diffDataCollection.isEmpty()){%>
      	<tr>
  
  	    <td colspan="1"  style="width: 100%">
  		    <TABLE name=selectTable BORDER="2" BORDERCOLOR="#D8D8D8" BGCOLOR="#FFFFFF" style="width: 100%" CELLSPACING="1" CELLPADDING="3">
  			    <TH  colspan="1"  style="width: 8%" class="headerUnderLineForMultipleTrue">לא נמצאו עידכונים במדריך החל מ- <%=sdf.format(diffDataCollection.getFromDate())%></TH>	
  		    </TABLE>
	 	    </td></tr>
	 	    
	 	    
	   <tr><td>
	   <%}else{ %> 
      		
      	      	
      	<tr>
  
  	    <td colspan="1"  style="width: 100%">
  		    <TABLE name=selectTable BORDER="2" BORDERCOLOR="#D8D8D8" BGCOLOR="#FFFFFF" style="width: 100%" CELLSPACING="1" CELLPADDING="3">
  			    <TH  colspan="1"  style="width: 8%" class="headerUnderLineForMultipleTrue">עידכונים במדריך החל מ- <%=sdf.format(diffDataCollection.getFromDate())%></TH>	
  		    </TABLE>
	 	    </td></tr>
	 	    
	 	    
	   <tr><td>
	   <TABLE name=selectTable BORDER="3" BORDERCOLOR="#D8D8D8" BGCOLOR="#FFFFFF" style="width: 100%" CELLSPACING="1" CELLPADDING="3">
<tr><td class="headerUnderLine4">סיכום:</td></tr>



<%if (diffDataCollection.companyAdded.size() > 0) { %>
<TR ALIGN="RIGHT"><TD  colspan="1"  style="width: 100%" class="textfieldHeb"><div align="right"><font face="Verdana, Arial, Helvetica, sans-serif">נוספו <%=diffDataCollection.companyAdded.size()%> חברות חדשות.</TD></TR>
<%}%> 
<%if (diffDataCollection.companyRemoved.size() > 0) { %>
<TR ALIGN="RIGHT"><TD  colspan="1"  style="width: 100%" class="textfieldHeb"><div align="right"><font face="Verdana, Arial, Helvetica, sans-serif">הוסרו <%=diffDataCollection.companyRemoved.size()%> חברות מהמדריך.</TD></TR>
<%}%> 
<%if (diffDataCollection.productChanged.size() > 0) { %>
<TR ALIGN="RIGHT"><TD  colspan="1"  style="width: 100%" class="textfieldHeb"><div align="right"><font face="Verdana, Arial, Helvetica, sans-serif">שונתה המלצה ב <%=diffDataCollection.productChanged.size()%> מוצרים קיימים במדריך.</TD></TR>
<%}%> 
<%if (diffDataCollection.productAdded.size() > 0) { %>
<TR ALIGN="RIGHT"><TD  colspan="1"  style="width: 100%" class="textfieldHeb"><div align="right"><font face="Verdana, Arial, Helvetica, sans-serif">נוספו <%=diffDataCollection.productAdded.size()%> מוצרים חדשים  לחברות אשר כבר קיימות במדריך.</TD></TR>
<%}%> 
<%if (diffDataCollection.productRemoved.size() > 0) { %>
<TR ALIGN="RIGHT"><TD  colspan="1"  style="width: 100%" class="textfieldHeb"><div align="right"><font face="Verdana, Arial, Helvetica, sans-serif">הוסרו <%=diffDataCollection.productRemoved.size()%> מוצרים  מחברות אשר כבר קיימות במדריך.</TD></TR>
<%}%> 

</TR>


</TABLE>
	   </td></tr>	    
	 
	 
 <tr><td><br><br></td></tr>
 
  		<tr><td>
	   <TABLE name=selectTable BORDER="1" BORDERCOLOR="#D8D8D8" BGCOLOR="#FFFFFF" style="width: 100%" CELLSPACING="1" CELLPADDING="3">
		<tr><td class="headerUnderLine4">פירוט:</td></tr>
		
 	 	    
	 	<%if (diffDataCollection.companyAdded.size() > 0) { %>    
       <tr ALIGN="RIGHT"><td colspan="1"  style="width: 100%"><TABLE name=selectTable BORDER="2" BORDERCOLOR="#D8D8D8" BGCOLOR="#FFFFFF" style="width: 100%" CELLSPACING="1" CELLPADDING="3"><TH  ALIGN="RIGHT" colspan="1"  style="width: 8%" class="headerUnderLineForMultipleTrue">חברות חדשות שנוספו:</TH></TABLE></td></tr>
       <tr><td colspan="1"  style="width: 100%"><TABLE name=selectTable BORDER="2" BORDERCOLOR="#D8D8D8" BGCOLOR="#FFFFFF" style="width: 100%" CELLSPACING="1" CELLPADDING="3">
       
       <tr><td class="headerUnderLineForMultipleTrue" colspan="1"  style="width: 50%">שם החברה</td><td class="headerUnderLineForMultipleTrue" colspan="1"  style="width: 50%">תאריך הוספה</td> </tr>
       
       <%
        Iterator iter = diffDataCollection.companyAdded.entrySet().iterator();
        String compName = null;
        Date compDate = null;
       	while (iter.hasNext()) {
       		java.util.Map.Entry<String, Date> pairs = (java.util.Map.Entry)iter.next();
       		compName = (String)pairs.getKey();
       		compDate =(Date)pairs.getValue();
       	 %>
       	 <tr><td class="textfieldHeb" colspan="0"  style="width: 50%"><%=compName%></td><td class="textfieldHeb" colspan="1"  style="width: 50%"><%=sdf.format(compDate)%></td> </tr>
       	 <%
       	}
        
       %>
      
       </TABLE></td></tr>
        <tr><td><br></td></tr>
  		<%}%> 
  		
  		
  		
  		<%if (diffDataCollection.companyRemoved.size() > 0) { %>    
       <tr ALIGN="RIGHT"><td colspan="1"  style="width: 100%"><TABLE name=selectTable BORDER="2" BORDERCOLOR="#D8D8D8" BGCOLOR="#FFFFFF" style="width: 100%" CELLSPACING="1" CELLPADDING="3"><TH  ALIGN="RIGHT" colspan="1"  style="width: 8%" class="headerUnderLineForMultipleTrue">חברות שהוסרו מהמדריך:</TH></TABLE></td></tr>
       <tr><td colspan="1"  style="width: 100%"><TABLE name=selectTable BORDER="2" BORDERCOLOR="#D8D8D8" BGCOLOR="#FFFFFF" style="width: 100%" CELLSPACING="1" CELLPADDING="3">
       
       <tr><td class="headerUnderLineForMultipleTrue" colspan="1"  style="width: 50%">שם החברה</td><td class="headerUnderLineForMultipleTrue" colspan="1"  style="width: 50%">תאריך הסרה</td> </tr>
       
       <%
        Iterator iter = diffDataCollection.companyRemoved.entrySet().iterator();
        String compName = null;
        Date compDate = null;
       	while (iter.hasNext()) {
       		java.util.Map.Entry<String, Date> pairs = (java.util.Map.Entry)iter.next();
       		compName = (String)pairs.getKey();
       		compDate =(Date)pairs.getValue();
       	 %>
       	 <tr><td class="textfieldHeb" colspan="0"  style="width: 50%"><%=compName%></td><td class="textfieldHeb" colspan="1"  style="width: 50%"><%=sdf.format(compDate)%></td> </tr>
       	 <%
       	}
        
       %>
       
       </TABLE></td></tr>
       <tr><td><br></td></tr>
  		<%}%> 
  				
  		
  		<%if (diffDataCollection.productChanged.size() > 0) { %>    
       <tr ALIGN="RIGHT"><td colspan="1"  width="1000"><TABLE name=selectTable BORDER="2" BORDERCOLOR="#D8D8D8" BGCOLOR="#FFFFFF" width="1000" CELLSPACING="1" CELLPADDING="3"><TH  ALIGN="RIGHT" colspan="1"  style="width: 8%" class="headerUnderLineForMultipleTrue">מוצרים קיימים בהם שונתה ההמלצה:</TH></TABLE></td></tr>
       <tr><td colspan="1" ><TABLE width="1000" name=selectTable BORDER="2" BORDERCOLOR="#D8D8D8" BGCOLOR="#FFFFFF"  CELLSPACING="1" CELLPADDING="3">
       
       <tr><td class="headerUnderLineForMultipleTrue" colspan="1"  width="180">חברה</td><td class="headerUnderLineForMultipleTrue" colspan="1"  width="180">מוצר</td><td class="headerUnderLineForMultipleTrue" colspan="1"  width="180">קטגוריה</td><td class="headerUnderLineForMultipleTrue" colspan="1"  width="180">המלצה חדשה</td><td class="headerUnderLineForMultipleTrue" colspan="1"  width="180">המלצה ישנה</td><td class="headerUnderLineForMultipleTrue" colspan="1"  width="100">תאריך שינוי</td> </tr>
       
       <%
        Iterator<DiffData> iter = diffDataCollection.productChanged.iterator();
        DiffData tmpDiffData = null;
       	while (iter.hasNext()) {
       		tmpDiffData = iter.next();
       		
       	 %>
       	 <tr><td class="textfieldHeb" colspan="1"  width="180"><%=tmpDiffData.getCompany()%></td><td class="textfieldHeb" colspan="0"  width="180"><%=tmpDiffData.getProduct()%></td><td class="textfieldHeb" colspan="0"  width="180"><%=tmpDiffData.getCategory()%></td><td class="textfieldHeb" colspan="1"  width="180"><%=org.celiac.util.TemplateReader.getHebrewMapWord(tmpDiffData.getGlutenFree())%></td><td class="textfieldHeb" colspan="0"  width="180"><%=org.celiac.util.TemplateReader.getHebrewMapWord(tmpDiffData.getGlutenFreeOld())%></td><td class="textfieldHeb" colspan="1"  width="100"><%=sdf.format(tmpDiffData.getDate())%></td> </tr>
       	 <%
       	}
        
       %>
       
       </TABLE></td></tr>
       <tr><td><br></td></tr>
  		<%}%> 
  		
  		<%if (diffDataCollection.productAdded.size() > 0) { %>    
       <tr ALIGN="RIGHT"><td colspan="1"  width="1000"><TABLE name=selectTable BORDER="2" BORDERCOLOR="#D8D8D8" BGCOLOR="#FFFFFF" width="1000" CELLSPACING="1" CELLPADDING="3"><TH  ALIGN="RIGHT" colspan="1"  style="width: 8%" class="headerUnderLineForMultipleTrue">מוצרים חדשים אשר נוספו למדריך:</TH></TABLE></td></tr>
       <tr><td colspan="1" ><TABLE width="1000" name=selectTable BORDER="2" BORDERCOLOR="#D8D8D8" BGCOLOR="#FFFFFF"  CELLSPACING="1" CELLPADDING="3">
       
       <tr><td class="headerUnderLineForMultipleTrue" colspan="1"  width="180">חברה</td><td class="headerUnderLineForMultipleTrue" colspan="1"  width="180">מוצר</td><td class="headerUnderLineForMultipleTrue" colspan="1"  width="180">קטגוריה</td><td class="headerUnderLineForMultipleTrue" colspan="1"  width="180">המלצה חדשה</td><td class="headerUnderLineForMultipleTrue" colspan="1"  width="100">תאריך הוספה</td> </tr>
       
       <%
        Iterator<DiffData> iter = diffDataCollection.productAdded.iterator();
        DiffData tmpDiffData = null;
       	while (iter.hasNext()) {
       		tmpDiffData = iter.next();
       		
       	 %>
       	 <tr><td class="textfieldHeb" colspan="1"  width="180"><%=tmpDiffData.getCompany()%></td><td class="textfieldHeb" colspan="0"  width="180"><%=tmpDiffData.getProduct()%></td><td class="textfieldHeb" colspan="0"  width="180"><%=tmpDiffData.getCategory()%></td><td class="textfieldHeb" colspan="1"  width="180"><%=org.celiac.util.TemplateReader.getHebrewMapWord(tmpDiffData.getGlutenFree())%></td><td class="textfieldHeb" colspan="1"  width="100"><%=sdf.format(tmpDiffData.getDate())%></td> </tr>
       	 <%
       	}
        
       %>
       
       </TABLE></td></tr>
       <tr><td><br></td></tr>
  		<%}%> 
      	
      	<%if (diffDataCollection.productRemoved.size() > 0) { %>    
      <tr ALIGN="RIGHT"><td colspan="1"  width="1000"><TABLE name=selectTable BORDER="2" BORDERCOLOR="#D8D8D8" BGCOLOR="#FFFFFF" width="1000" CELLSPACING="1" CELLPADDING="3"><TH  ALIGN="RIGHT" colspan="1"  style="width: 8%" class="headerUnderLineForMultipleTrue">מוצרים שהוסרו מהמדריך:</TH></TABLE></td></tr>
       <tr><td colspan="1" ><TABLE width="1000" name=selectTable BORDER="2" BORDERCOLOR="#D8D8D8" BGCOLOR="#FFFFFF"  CELLSPACING="1" CELLPADDING="3">
       
       <tr><td class="headerUnderLineForMultipleTrue" colspan="1"  width="180">חברה</td><td class="headerUnderLineForMultipleTrue" colspan="1"  width="180">מוצר</td><td class="headerUnderLineForMultipleTrue" colspan="1"  width="180">קטגוריה</td><td class="headerUnderLineForMultipleTrue" colspan="1"  width="100">תאריך הסרה</td> </tr>
       
       <%
        Iterator<DiffData> iter = diffDataCollection.productRemoved.iterator();
        DiffData tmpDiffData = null;
       	while (iter.hasNext()) {
       		tmpDiffData = iter.next();
       		
       	 %>
       	 <tr><td class="textfieldHeb" colspan="1"  width="180"><%=tmpDiffData.getCompany()%></td><td class="textfieldHeb" colspan="0"  width="180"><%=tmpDiffData.getProduct()%></td><td class="textfieldHeb" colspan="0"  width="180"><%=tmpDiffData.getCategory()%></td><td class="textfieldHeb" colspan="1"  width="100"><%=sdf.format(tmpDiffData.getDate())%></td> </tr>
       	 <%
       	}
        
       %>
       
       </TABLE></td></tr>
       <tr><td><br></td></tr>
  		<%}%> 
      	
      	
      	
      		
   
  		
  		
  
  	
  	<%} }%>
  	   </table>
      
  	
  	
  	
  	
  		
  		</td>
  	</tr>
  	 </TABLE></td></tr>
  	 </table>
			<br>
		</td>
	</tr>
</table>








<%@ include file="../include/buttom.jsp" %>    