<%@ page language="java" 
         import="org.celiac.api.GlutenFreeAPI, org.celiac.web.bean.*,org.celiac.datatype.*, java.util.*" 
         errorPage="loginError.jsp" %>
<jsp:useBean id="User" scope="session" class="org.celiac.web.bean.User">
  <jsp:setProperty name="User" property="*"/>
</jsp:useBean>
<%@ include file="../include/header.jsp" %>    
<% if (!(User.getUser().getPERMISSIONS().equals("admin")) && !(User.getUser().getPERMISSIONS().equals("mitnadev"))) throw new Exception("User was not login. Please login."); %>

<head>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!--<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=ISO-8859-1">-->
<link href="../include/theStyle.css" rel="stylesheet" type="text/css">



<SCRIPT LANGUAGE="JavaScript">		

	
	
	function processFindAllUser()
	{
		document.manage_form1.choise.value="findAllUser";
	 	  	  
	}
	
	
	
	
	
	
	

	
	
	
</SCRIPT>
</head>



<%

String aChoice = null;
if (request.getParameter("choice") != null){
		aChoice = (String)request.getParameter("choice");
		if ("delete".equals(aChoice)) {
			try{
				//System.out.println("The name is " + jobDetails.getNAME());
				org.celiac.job.JobUtil jUtil = new org.celiac.job.JobUtil();
				jUtil.deleteSchedulerJob((String) request.getParameter("name"));
				org.celiac.util.database.DBQueryFactory.getDBHandler().deleteJob((String) request.getParameter("name"));
			}catch (Exception t){
				org.celiac.util.Logger.error("Might be a problem deleting job " + (String) request.getParameter("name"),t);
			}
		}
}


			



request.setCharacterEncoding("utf-8");
 response.setContentType("text/html;charset=UTF-8");
 GFUser aUser = (GFUser)session.getAttribute("A_USER");
 String siteRoot = request.getContextPath();
 if (aUser == null) aUser = new GFUser();

String FIRST_NAME=aUser.getFIRST_NAME();
if (FIRST_NAME == null) FIRST_NAME = "";
String LAST_NAME=aUser.getLAST_NAME();
if (LAST_NAME == null) LAST_NAME = "";
String HISHUR_PASSWORD=aUser.getPASSWORD();
if (HISHUR_PASSWORD == null) HISHUR_PASSWORD = "";
String PASSWORD=aUser.getPASSWORD();
if (PASSWORD == null) PASSWORD = "";
String USER_ID=aUser.getUSER_ID();
if (USER_ID == null) USER_ID = "";
String CELL_NUM=aUser.getCELL_NUM();
if (CELL_NUM == null) CELL_NUM = "";
String EMAIL=aUser.getEMAIL();
if (EMAIL == null) EMAIL = "";
String EMAIL_ISHUR=aUser.getEMAIL();
if (EMAIL_ISHUR == null) EMAIL_ISHUR = "";
String CELIAC_MEMBER_ID = aUser.getCELIAC_MEMBER_ID();
if (CELIAC_MEMBER_ID == null) CELIAC_MEMBER_ID = "";
String USER_TZ = aUser.getUSER_TZ();
if (USER_TZ == null) USER_TZ = "";
Date exp_date = aUser.getEXPIRATION_DATE();
String EXPIRATION_DATE =   null;
if (exp_date == null) EXPIRATION_DATE = "";
else EXPIRATION_DATE = new java.text.SimpleDateFormat("d/M/yyyy").format(aUser.getEXPIRATION_DATE());
String LAST_UPDATE_WAS_PURCHASE = aUser.getDID_BUY_THE_BOOK();
if (LAST_UPDATE_WAS_PURCHASE == null) LAST_UPDATE_WAS_PURCHASE = "";
String PERMISSIONS = aUser.getPERMISSIONS();




session.setAttribute("A_USER",null);



String errorMessage = (String)session.getAttribute("gfsmsNewUserError");
if (errorMessage == null) errorMessage = "";

session.setAttribute("gfsmsNewUserError",null);


%>



<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 3.2//EN">
<html>
<head>
<!--<meta http-equiv="Content-Type" content="text/html; charset=windows-1255">-->
<link href="/include/theStyle.css" rel="stylesheet" type="text/css">
<meta name="Description" content="" />
<meta name="Keywords" content="" />
<script type="text/javascript" src="../include/ezcalendar.js"></script>
<link rel="stylesheet" type="text/css" href="../include/ezcalendar.css" />
<title>Gluten Free Search Engine</title>
</head>

<body bgcolor="#ffffff" LEFTMARGIN="10" RIGHTMARGIN="10"
      link="#3366cc" vlink="#9999cc" alink="#0000cc">

<%


String action = null;
if (request.getParameter("choise") != null){
		action = request.getParameter("choise");
		
}

int pageSize=25;
int lastPageWas=0;
if (request.getParameter("lastPageWas") != null){
		lastPageWas = new Integer((String)request.getParameter("lastPageWas")).intValue();
}




%>



<table WIDTH="500" height="90%" border="0" cellpadding="5">
	<tr valign="top">
		<td>
		<table width="100%" height="90%" border="1">
		<td valign="top" nowrap bgcolor="#f5f5e1" class="textSmall">
		<%@ include file="../include/upperMenu.jsp" %>
		</td>
		</table>
</td>
<td></td>
		<td WIDTH="100%" >
			<table border="0"  WIDTH="650"  cellpadding="1" cellspacing="1" class="frameTableT">
		
			
			<form name="manage_form1" action="manageUser.jsp" method="POST">
					
					
			<input type="hidden"  name="choise" >
				
				
   
   
    

        
        
<tr><td>
        
<TABLE>      
<tr style="width: 500"><td colspan="1" nowrap style="width: 650" class="headerUnderLineHeb">ניהול משימות:</td></tr>

</TABLE>
  
  </tr>
</td>
  
	
	
	
	
	</form>
	
	
	<%if (!("").equals(errorMessage)){%>
			<tr><td align="right">	
			<font color=#ff0000><b><%=errorMessage%><b></font>
			<br>
			<br>
		</td></tr>
		 <%}%>
	
	
	

	
	
	 <%
	 
	 		Set<JobDetails> theAllJobs = org.celiac.util.database.DBQueryFactory.getDBHandler().getJobs();
	 		
	 		
	 %>
	 
	 <tr>
  
  	<td width="100%"  >
  		<TABLE name=selectTable BORDER="2" BORDERCOLOR="#D8D8D8" BGCOLOR="#FFFFFF" style="width: 100%" CELLSPACING="1" CELLPADDING="3">
  			<TH  colspan="1"  style="width: 8%" class="headerUnderLineForMultipleTrue">סה"כ משימות מתוזמנות במדריך = <%=theAllJobs.size()%></TH>	
  		</TABLE>
	 	</td></tr>
	 
	    <tr>
  
  	<td width="100%"  >
  		<TABLE name=selectTable BORDER="2" BORDERCOLOR="#D8D8D8" BGCOLOR="#FFFFFF" style="width: 100%" CELLSPACING="1" CELLPADDING="3">
  		<TH  colspan="1"  style="width: 8%" ></TH>	
  		<TH  colspan="1"  style="width: 18%" class="headerUnderLine4">אקטיבי</TH>			
  		<TH  colspan="1"  style="width: 8%" class="headerUnderLine4">שם משימה</TH>		
			<TH  colspan="1"  style="width: 14%" class="headerUnderLine4">קוד משימה</TH>
			<TH  colspan="1"  style="width: 14%" class="headerUnderLine4">פרטים</TH>
			<TH  colspan="1"  style="width: 14%" class="headerUnderLine4">ארגומנט 1</TH>	
			<TH  colspan="1"  style="width: 14%" class="headerUnderLine4">ארגומנט 2</TH>
			<TH  colspan="1"  style="width: 14%" class="headerUnderLine4">ארגומנט 3</TH>
			<TH  colspan="1"  style="width: 14%" class="headerUnderLine4">ארגומנט 4</TH>		
			<TH  colspan="1"  style="width: 14%" class="headerUnderLine4">חד פעמי</TH>		
			<TH  colspan="1"  style="width: 14%" class="headerUnderLine4">דקה</TH>	
			<TH  colspan="1"  style="width: 14%" class="headerUnderLine4">שעה</TH>	
			<TH  colspan="1"  style="width: 14%" class="headerUnderLine4">יום בחודש</TH>	
			<TH  colspan="1"  style="width: 14%" class="headerUnderLine4">חודש</TH>	
			<TH  colspan="1"  style="width: 14%" class="headerUnderLine4">יום בשבוע</TH>	
			<TH  colspan="2"  style="width: 14%" ></TH>	
			
			
			
				
			
			<%
			
			
			JobDetails jobDetails = null;
			Iterator iterForJobs = theAllJobs.iterator();
			String yesNo = "לא";
			String colorForActive = "";
			String colorForOneTime = "";
			int i=0;
			while (iterForJobs.hasNext()){
			jobDetails = (JobDetails)iterForJobs.next();
			
			i++;
			if (i < (lastPageWas * pageSize) + 1) continue;
			
			if (i == (lastPageWas * pageSize) + pageSize + 1) {
					%><input type="hidden"  name="lastPageWas" value="<%=""+(lastPageWas+1)%>" ><%
					
					break;
			}
			%>
			
			<TR ALIGN="LEFT">
			<TD ALIGN="RIGHT" colspan="1"  nowrap style="width: 8%" class="headerUnderLine4"><%=i%></TD>
			<% if ("yes".equals(jobDetails.getACTIVE())) {
				  yesNo = "כן"; 
				  colorForActive = "headerUnderLine3HebGreen";
			
				} else {
				  yesNo = "לא";
				  colorForActive = "headerUnderLine3HebRed";
				}
			%>
			<TD ALIGN="CENTER" colspan="1" nowrap style="width: 8%" class="<%=colorForActive%>"><%=yesNo%></TD>
			<TD ALIGN="RIGHT" colspan="1" nowrap  style="width: 14%" class="headerUnderLine3"><%=jobDetails.getNAME()%></TD>
			<TD ALIGN="CENTER" colspan="1" nowrap style="width: 14%" class="headerUnderLine3"><%=jobDetails.getTYPE()%></TD>
			<TD ALIGN="RIGHT" colspan="1" nowrap style="width: 18%" class="headerUnderLine3"><%=jobDetails.getDETAILS()%></TD>
			<TD ALIGN="CENTER" colspan="1"  style="width: 24%" class="headerUnderLine3"><%=jobDetails.getTYPE_ARG_1()%></TD>
			<TD ALIGN="CENTER" colspan="1"  style="width: 24%" class="headerUnderLine3"><%=jobDetails.getTYPE_ARG_2()%></TD>
			<TD ALIGN="CENTER" colspan="1"  style="width: 24%" class="headerUnderLine3"><%=jobDetails.getTYPE_ARG_3()%></TD>
			<TD ALIGN="CENTER" colspan="1"  style="width: 24%" class="headerUnderLine3"><%=jobDetails.getTYPE_ARG_4()%></TD>
			<% if ("yes".equals(jobDetails.getREPEAT())) {
				yesNo = "כן";  
				colorForOneTime = "headerUnderLine3HebYellow";
			} else {
				yesNo = "לא";
				colorForOneTime = "headerUnderLine3HebGreen";
			}
			%>
			<TD ALIGN="CENTER" colspan="1" nowrap style="width: 24%" class="<%=colorForOneTime%>"><%=yesNo%></TD>
			<TD ALIGN="CENTER" colspan="1" nowrap style="width: 24%" class="headerUnderLine3"><%=jobDetails.getMINUTE()%></TD>
			<TD ALIGN="CENTER" colspan="1" nowrap style="width: 24%" class="headerUnderLine3"><%=jobDetails.getHOUR()%></TD>
			<TD ALIGN="CENTER" colspan="1" nowrap style="width: 24%" class="headerUnderLine3"><%=jobDetails.getDAY_OF_MONTH()%></TD>
			<TD ALIGN="CENTER" colspan="1" nowrap style="width: 24%" class="headerUnderLine3"><%=jobDetails.getMONTH()%></TD>
			<TD ALIGN="CENTER" colspan="1" nowrap style="width: 24%" class="headerUnderLine3"><%=jobDetails.getDAY_OF_WEEK()%></TD>
			 <form name="find_users" action="manageJob.jsp?choice=update" method="POST" target="foo" onSubmit="window.open('', 'foo', 'width=900,height=700,status=yes,resizable=yes,scrollbars=yes')">
			 <td><input name="submit" type="submit" id="submit" value="עדכון"  style="width: 100%" class="button" enabled onMouseOver="this.style.background =  '#d5e0e6';" onMouseOut="this.style.background = '#e4e4e4';"></td>
			<input type="hidden" name="jobName" value="<%=jobDetails.getNAME()%>">
			</form>
			<form name="find_users" action="manageJobs.jsp" method="POST">
			  <td><input name="submit" type="submit" id="submit" value="מחיקה"  style="width: 100%" class="button" enabled onMouseOver="this.style.background =  '#d5e0e6';" onMouseOut="this.style.background = '#e4e4e4';"></td>
			  <input type="hidden" name="name" value="<%=jobDetails.getNAME()%>">
			  <input type="hidden" name="choice" value="delete">
			 </form>
			
			
			<%}%>

</TR>
																						
		</TABLE>
  	</td></tr>
  	<form name="find_users" action="manageJob.jsp?choice=new" method="POST" target="foo" onSubmit="window.open('', 'foo', 'width=900,height=700,status=yes,resizable=yes,scrollbars=yes')">
  	<tr><td >
  	<table width="50" border="0" cellpadding="1">
				<tr>
					  <td><input name="submit" type="submit" id="submit" value="הוספה"  style="width: 100%" class="button" enabled onMouseOver="this.style.background =  '#d5e0e6';" onMouseOut="this.style.background = '#e4e4e4';"></td>
				</tr>
			</table>
		</td></tr>
	</form>
	
	</tr>
</table>
	
	
	
	
	
	
		 				
				
				
		   




<%@ include file="../include/buttom.jsp" %>    