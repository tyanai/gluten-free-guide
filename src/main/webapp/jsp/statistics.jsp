<%@ page language="java" 
         import="org.celiac.datatype.StatisticsData, org.celiac.web.bean.*, java.util.*" 
         errorPage="loginError.jsp" %>
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

String start = "";
String end = "";
boolean empty = false;

if (request.getParameter("fromDate") != null){
		start = (String)request.getParameter("fromDate");	
} else {
		start = "";	
		empty = true;
}

if (request.getParameter("toDate") != null){
	end = (String)request.getParameter("toDate");	
} else {
	end = "";	
	empty = true;
}

%>

<%
StatisticsData[] statisticsData = null;
if (!start.equals("") && !end.equals("") && !empty) {
		
		try {	
			statisticsData = User.getStatistics(start,end);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		}
%>



<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<link href="../include/theStyle.css" rel="stylesheet" type="text/css">
  <meta name="Description" content="" />
	<meta name="Keywords" content="" />
	<script type="text/javascript" src="../include/ezcalendar.js"></script>
	<link rel="stylesheet" type="text/css" href="../include/ezcalendar.css" />
<title>Gluten Free Search Engine</title>
</head>

<body bgcolor="#ffffff" LEFTMARGIN="10" RIGHTMARGIN="10"
      link="#3366cc" vlink="#9999cc" alink="#0000cc">
      <!--onload="document.sim_form.smsMessage.focus()" --> 



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
		<td width="100%" >
			<table border="0"   cellpadding="1" cellspacing="1" class="frameTableT">
			<form name="sim_form" action="statistics.jsp" method="POST">
					
			<tr><td colspan="1" nowrap class="titleBigHeb">Gluten Free SMS Statistics</td></tr>
		
				
				<tr bgcolor="#FFFFFF">
					
				</tr>
				<tr>
				<tr>
				<tr>
					

 
    <tr><td nowrap  class="headerUnderLine">From Date:</td>   	
      <td colspan="1"   class="headerUnderLine"><input type="text"  class="textfield" name="fromDate" id="fromDate"  value="<%=start%>" READONLY> <a colspan="1" class="headerUnderLine" href="javascript: showCalendar('fromDate')">Show calender</a></td>
    </tr>
    
    <tr><td nowrap  class="headerUnderLine">To Date:</td>   	
     <td colspan="1"   class="headerUnderLine"><input type="text"  class="textfield" name="toDate" id="toDate"  value="<%=end%>" READONLY> <a colspan="1" class="headerUnderLine" href="javascript: showCalendar('toDate')">Show calender</a></td>
    </tr>
  </tr>
    <tr>
    	<tr>
    		<tr>
  
      
          <tr>
          <td>
          <input type="submit"  name="  Submit  " value="Send" style="width: 100%" class="button" enabled onMouseOver="this.style.background =  '#d5e0e6';" onMouseOut="this.style.background = '#e4e4e4';"  >
          </font></div>
        </td>
      </tr>
      <tr>
      	<tr>
      		<tr>
      
    </td>
  </tr>
  </tr>
</form>
</table>
			<%if (!empty){ %>
			
			<%
			if ( (statisticsData == null) || (statisticsData.length == 1 && statisticsData[0].getDate() == null))   {
			%>
				<TABLE BORDER="3" BORDERCOLOR="#D8D8D8" BGCOLOR="#FFFFFF" WIDTH="500" CELLSPACING="1" CELLPADDING="3">
				<CAPTION style="width: 100%" class="headerUnderLine3"><B>No Statistics was found for dates starting <%=start%> ending <%=end%> </B></CAPTION>
			</TABLE>
			
			<%}else{
				
				int returnTotal1 = 0;
				int returnTotal2 = 0;
				int returnTotal3 = 0;
				int returnTotal4 = 0;
				int returnTotal5 = 0;
				int returnTotalWebUser = 0;
				
				
				%>
      			
      			<TABLE BORDER="3" BORDERCOLOR="#D8D8D8" BGCOLOR="#FFFFFF" WIDTH="500" CELLSPACING="1" CELLPADDING="3">
<CAPTION style="width: 100%" class="headerUnderLine3"><B>Information For Dates: </B></CAPTION>
<TH WIDTH="5%"></TH>
<TH  colspan="1"  style="width: 100%" class="headerUnderLine4">Date</TH>
<TH  colspan="1"  style="width: 100%" class="headerUnderLine4">1 Unit</TH>
<TH  colspan="1"  style="width: 100%" class="headerUnderLine4">2 Units</TH>
<TH  colspan="1"  style="width: 100%" class="headerUnderLine4">3 Units</TH>
<TH  colspan="1"  style="width: 100%" class="headerUnderLine4">4 Units</TH>
<TH  colspan="1"  style="width: 100%" class="headerUnderLine4">5 Units</TH>
<TH  colspan="1"  style="width: 100%" class="headerUnderLine4">סה"כ שאילתות</TH>
<TH  colspan="1"  style="width: 100%" class="headerUnderLine4">Web User</TH>

<%for (int i=0;i<statisticsData.length;i++){
				 returnTotal1 = returnTotal1 + statisticsData[i].getReturned1();
				 returnTotal2 = returnTotal2 + statisticsData[i].getReturned2();
				 returnTotal3 = returnTotal3 + statisticsData[i].getReturned3();
				 returnTotal4 = returnTotal4 + statisticsData[i].getReturned4();
				 returnTotal5 = returnTotal5 + statisticsData[i].getReturned5();
				 returnTotalWebUser = returnTotalWebUser + statisticsData[i].getWebUserCount();
%>
<TR ALIGN="CENTER">
<TD colspan="1"  class="headerUnderLine4"><%=i+1%></TD>
<TD colspan="1"  style="width: 100%" class="headerUnderLine3"><%=statisticsData[i].getDate()%></TD>
<TD colspan="1"  style="width: 100%" class="headerUnderLine3"><%=statisticsData[i].getReturned1()%></TD>
<TD colspan="1"  style="width: 100%" class="headerUnderLine3"><%=statisticsData[i].getReturned2()%></TD>
<TD colspan="1"  style="width: 100%" class="headerUnderLine3"><%=statisticsData[i].getReturned3()%></TD>
<TD colspan="1"  style="width: 100%" class="headerUnderLine3"><%=statisticsData[i].getReturned4()%></TD>
<TD colspan="1"  style="width: 100%" class="headerUnderLine3"><%=statisticsData[i].getReturned5()%></TD>
<TD colspan="1"  style="width: 100%" class="headerUnderLine4"><%=statisticsData[i].getReturned5()+statisticsData[i].getReturned4()+statisticsData[i].getReturned3()+statisticsData[i].getReturned2()+statisticsData[i].getReturned1()%></TD>
<TD colspan="1"  style="width: 100%" class="headerUnderLine3"><%=statisticsData[i].getWebUserCount()%></TD>

</TR>
<%}%>
<TR ALIGN="CENTER">
<TD BGCOLOR="#FFFFFF"><FONT SIZE="4"></FONT></TD>
<TD colspan="1"  style="width: 100%" class="headerUnderLine4">Total</TD>
<TD colspan="1"  style="width: 100%" class="headerUnderLine4"><%=returnTotal1%></TD>
<TD colspan="1"  style="width: 100%" class="headerUnderLine4"><%=returnTotal2%></TD>
<TD colspan="1"  style="width: 100%" class="headerUnderLine4"><%=returnTotal3%></TD>
<TD colspan="1"  style="width: 100%" class="headerUnderLine4"><%=returnTotal4%></TD>
<TD colspan="1"  style="width: 100%" class="headerUnderLine4"><%=returnTotal5%></TD>
<TD colspan="1"  style="width: 100%" class="headerUnderLine4"><%=returnTotal1+returnTotal2+returnTotal3+returnTotal4+returnTotal5%></TD>
<TD colspan="1"  style="width: 100%" class="headerUnderLine4"><%=returnTotalWebUser%></TD>

</TR>

</TABLE>
      			
      			
  		<%}}%>
  	
			<br>
		</td>
	</tr>
</table>





<%@ include file="../include/buttom.jsp" %>    