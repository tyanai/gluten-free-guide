<%@ page language="java" 
         import="org.celiac.api.GlutenFreeAPI, org.celiac.web.bean.*, java.util.*, org.celiac.business.GFS, org.celiac.business.GFSByCategoryAndCompanyImpl, org.celiac.datatype.*, org.celiac.util.database.*, org.celiac.job.*" 
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

function checkform()
{
	if (document.jobForm.name.value == '')
	{
		// something is wrong
		alert('שם המשימה ריק.  אנא הגדר שם כדי להמשיך');
		return false;
	}
	
		
	/*	
	var str=document.jobForm.name.value;
	alert(str);
  var pos=str.IndexOf("\"")
	if (pos>=0)
	{
			// something else is wrong
		alert('נמצאו גרשיים בשם המשימה. אנא הסר גרשיים להמשך');
		return false;

  }
	
	*/	
  
	// If the script gets this far through all of your fields
	// without problems, it's ok and you can submit the form

	return true;
}

	
function processSelectionType()
{
	  // Get the index of th selection.
	  var theUrl = "manageJob.jsp?choice=new&tmpTypeCounter=" + document.jobForm.typeT.value;
	  //alert(theUrl);
	  window.open(theUrl,"_self");
	 	  	  
}

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

try{

request.setCharacterEncoding("utf-8");
 response.setContentType("text/html;charset=UTF-8");
 
String choice = null; 
String tmpTypeCounter = "-1";
String jobName = null;
JobDetails jobDetails = null;


int tmpCount = 0;

 
 
if (request.getParameter("choice") != null){
		choice = (String)request.getParameter("choice");
		
}
 
if (request.getParameter("tmpTypeCounter") != null){
		tmpTypeCounter = (String)request.getParameter("tmpTypeCounter");
		
}

if (request.getParameter("jobName") != null){
		jobName = (String)request.getParameter("jobName");
		}
 
 %>

<html>

<head><title>Image Managment</title></head>

<body>



	<form action="<%=siteRoot%>/JobEditor" method="post" name="jobForm" id="productForm" onSubmit="return checkform()">
		 
		
        
        
        

        
        
<TABLE align="center" name=selectTable BORDER="0" BORDERCOLOR="#D8D8D8" BGCOLOR="#FFFFFF" style="width: 100%" CELLSPACING="3" CELLPADDING="3">
	
	 <% if ("new".equals(choice)) { %>
    <tr><td colspan="1" nowrap style="width: 100%" class="headerUnderLineHeb">הוספת משימה מתוזמנת</td></tr>
   	 <% } else  if ("delete".equals(choice)) { %>
   	 <tr><td colspan="1" nowrap style="width: 100%" class="headerUnderLineHeb">מחיקת משימה מתוזמנת</td></tr>
     <% } else  if ("update".equals(choice)) { %>
     <tr><td colspan="1" nowrap style="width: 100%" class="headerUnderLineHeb">עדכון משימה מתוזמנת</td></tr>
     <% } %>
     
<tr>
<td>
<table align="center" name=selectTable BORDER="3" BORDERCOLOR="#D8D8D8" BGCOLOR="#FFFFFF" style="width: 100%" CELLSPACING="3" CELLPADDING="0">
<tr>
<td  align="center" colspan="1"   class="headerUnderLine4">קוד משימה</td>
<td  align="center" colspan="1"  class="headerUnderLine4">פרטי משימה</td>
<td  align="center" colspan="1"   class="headerUnderLine4">שם משימה</td>
</tr>
<TR>		
<% 
Job[] jobs = null;
if ("new".equals(choice)) { 


try{

 jobs = JobFactory.getJobs();

}catch (Exception e){
	e.printStackTrace();
}
%>

<td  align="center" colspan="1"   class="headerUnderLine3Heb">
	 <select name="typeT" class="textfieldHeb" onchange="processSelectionType();">	
	 <%
	 String tmpType = "";
	 String tmpDetails = "";
	 String tmpText = "";
	 if (tmpTypeCounter.equals("-1")) {%> <OPTION VALUE="" selected="selected">Select <%}%>
	 <%
	
	 
	 for (int i=0;i<jobs.length;i++){
	 tmpType = jobs[i].getJobPropertieDetails().getTYPE();
	 %>
	 <OPTION VALUE="<%=i%>" <%if ((""+tmpTypeCounter).equals(""+i)){%>selected="selected"<%}%> ><%=tmpType%>					
	 <%}%>
	 </SELECT>
</td>
<td  align="center" colspan="1"  class="headerUnderLine3Heb"> 
<%if (!tmpTypeCounter.equals("-1")) {
tmpCount = new Integer(tmpTypeCounter).intValue();
%>
<%=jobs[tmpCount].getJobPropertieDetails().getDETAILS()%>

</td>
<input type="hidden" name="details" value="<%=jobs[tmpCount].getJobPropertieDetails().getDETAILS()%>">
<input type="hidden" name="choice" value="<%=choice%>">
<input type="hidden" name="type" value="<%=jobs[tmpCount].getJobPropertieDetails().getTYPE()%>">
<td  align="center" colspan="1"  class="headerUnderLine3Heb"><input type="text" value="" name="name" ></TD>
<%}%>	
<%}%>

<% if ("update".equals(choice)) { 
jobDetails = DBQueryFactory.getDBHandler().getJob(jobName);
%>
<td  align="center" colspan="1"   class="headerUnderLine3Heb"><%=jobDetails.getTYPE()%></td>
<td  align="center" colspan="1"   class="headerUnderLine3Heb"><%=jobDetails.getDETAILS()%></td>
<td  align="center" colspan="1"   class="headerUnderLine3Heb"><%=jobDetails.getNAME()%></td>
<input type="hidden" name="type" value="<%=jobDetails.getTYPE()%>">
<input type="hidden" name="details" value="<%=jobDetails.getDETAILS()%>">
<input type="hidden" name="name" value="<%=jobDetails.getNAME()%>">
<input type="hidden" name="choice" value="<%=choice%>">
<%}%>


</tr>
</table>
</td>
</tr>	







<%if (!tmpTypeCounter.equals("-1") || ("update".equals(choice))   )  {%>
<tr>
<td>
<table align="center" name=selectTable BORDER="3" BORDERCOLOR="#D8D8D8" BGCOLOR="#FFFFFF" style="width: 100%" CELLSPACING="3" CELLPADDING="0">
<tr>
<td  align="center" colspan="1"   class="headerUnderLine4">דקה</td>
<td  align="center" colspan="1"  class="headerUnderLine4">שעה</td>
<td  align="center" colspan="1"   class="headerUnderLine4">יום בחודש</td>
<td  align="center" colspan="1"   class="headerUnderLine4">חודש</td>
<td  align="center" colspan="1"   class="headerUnderLine4">יום בשבוע</td>
</tr>
<TR>			
	
<%

String jobMinute="0";
String jobHour="10";
String jobDayOfMonth = "?";
String jobMonth = "1-12";
String jobDayOfWeek = "1-7";
String arg1 = "";
String arg2 = "";
String arg3 = "";
String arg4 = "";
String text = "";
String active = "";
String repeat = "";

if ("update".equals(choice)) { 
	 jobMinute=jobDetails.getMINUTE();
	 jobHour=jobDetails.getHOUR();
	 jobDayOfMonth = jobDetails.getDAY_OF_MONTH();
	 jobMonth = jobDetails.getMONTH();
	 jobDayOfWeek = jobDetails.getDAY_OF_WEEK();
	 
	 arg1 = jobDetails.getTYPE_ARG_1();
	 arg2 = jobDetails.getTYPE_ARG_2();
	 arg3 = jobDetails.getTYPE_ARG_3();
	 arg4 = jobDetails.getTYPE_ARG_4();
	 
	 text=jobDetails.getTEXT();
	 active=jobDetails.getACTIVE();
	 repeat=jobDetails.getREPEAT();

}%>
	
	
<td  align="center" colspan="1"  class="headerUnderLine3Heb"><input type="text" value="<%=jobMinute%>" name="jobMinute" ></TD>
<td  align="center" colspan="1"  class="headerUnderLine3Heb"><input type="text" value="<%=jobHour%>" name="jobHour" ></TD>
<td  align="center" colspan="1"  class="headerUnderLine3Heb"><input type="text" value="<%=jobDayOfMonth%>" name="jobDayOfMonth" ></TD>
<td  align="center" colspan="1"  class="headerUnderLine3Heb"><input type="text" value="<%=jobMonth%>" name="jobMonth" ></TD>
<td  align="center" colspan="1"  class="headerUnderLine3Heb"><input type="text" value="<%=jobDayOfWeek%>" name="jobDayOfWeek" ></TD>
</tr>
</table>
</td>
</tr>	

<tr>
<td>
<table align="center" name=selectTable BORDER="3" BORDERCOLOR="#D8D8D8" BGCOLOR="#FFFFFF" style="width: 100%" CELLSPACING="3" CELLPADDING="0">
<tr>
<td  align="center" colspan="1"   class="headerUnderLine4">ארגומנט 1</td>
<td  align="center" colspan="1"  class="headerUnderLine4">ארגומנט 2</td>
<td  align="center" colspan="1"   class="headerUnderLine4">ארגומנט 3</td>
<td  align="center" colspan="1"   class="headerUnderLine4">ארגומנט 4</td>
</tr>
<TR>			
<td  align="center" colspan="1"  class="headerUnderLine3Heb"><input type="text" value="<%=arg1%>" name="argument1" ></TD>
<td  align="center" colspan="1"  class="headerUnderLine3Heb"><input type="text" value="<%=arg2%>" name="argument2" ></TD>
<td  align="center" colspan="1"  class="headerUnderLine3Heb"><input type="text" value="<%=arg3%>" name="argument3" ></TD>
<td  align="center" colspan="1"  class="headerUnderLine3Heb"><input type="text" value="<%=arg4%>" name="argument4" ></TD>

</tr>
</table>
</td>
</tr>	

<tr>
<td>
<table align="center" name=selectTable BORDER="3" BORDERCOLOR="#D8D8D8" BGCOLOR="#FFFFFF" style="width: 100%" CELLSPACING="3" CELLPADDING="0">
<tr>
<td  align="center" colspan="1"   class="headerUnderLine4">אקטיבי</td>
<td  align="center" colspan="1"  class="headerUnderLine4">חד פעמי</td>
</tr>
<% if ("new".equals(choice)) { %>
<TR>			
<td  align="center" colspan="1"  class="headerUnderLine3Heb">
	 <SELECT name="active" class="textfieldHeb">	
	 <OPTION VALUE="yes" selected="selected">כן		
	 <OPTION VALUE="no">לא			
	 </SELECT>
</TD>
<td  align="center" colspan="1"  class="headerUnderLine3Heb">
	 <SELECT name="repeat" class="textfieldHeb">	
	 <OPTION VALUE="yes">כן		
	 <OPTION VALUE="no" selected="selected">לא			
	 </SELECT>
</TD>
</tr>
<% } else if ("update".equals(choice)) { %>
<TR>			
<td  align="center" colspan="1"  class="headerUnderLine3Heb">
	 <SELECT name="active" class="textfieldHeb">	
	 <OPTION VALUE="yes"<%if ("yes".equals(active)){%>selected="selected" <%}%>>כן		
	 <OPTION VALUE="no"<%if ("no".equals(active)){%>selected="selected" <%}%>>לא			
	 </SELECT>
</TD>
<td  align="center" colspan="1"  class="headerUnderLine3Heb">
	 <SELECT name="repeat" class="textfieldHeb">	
	 <OPTION VALUE="yes"<%if ("yes".equals(repeat)){%>selected="selected" <%}%>>כן		
	 <OPTION VALUE="no"<%if ("no".equals(repeat)){%>selected="selected" <%}%>>לא			
	 </SELECT>
</TD>
</tr>
<%}%>
</table>
</td>
</tr>	

<tr>
<td>
<table align="center" name=selectTable BORDER="3" BORDERCOLOR="#D8D8D8" BGCOLOR="#FFFFFF" style="width: 100%" CELLSPACING="3" CELLPADDING="0">
<tr>
<td  align="right" colspan="1"   class="headerUnderLine4">טקסט המשימה</td>
</tr>
<TR>			
 

<% if ("update".equals(choice)) { %>
<td  align="right" colspan="1"  class="headerUnderLine3Heb">
<%=jobDetails.getTEXT()%>
</td>

<%} else {%>
<td  align="right" colspan="1"  class="headerUnderLine3Heb">
<%=jobs[tmpCount].getJobPropertieDetails().getTEXT()%>
</td>

<%}%>

</tr>
</table>
</td>
</tr>	



<tr>
<td>
<table align="center" name=selectTable BORDER="0" BORDERCOLOR="#D8D8D8" BGCOLOR="#FFFFFF" style="width: 100%" CELLSPACING="3" CELLPADDING="0">

<TR>			
<td  align="right" colspan="1"  >
				<% 
							String continueButton = null;
						  if ("new".equals(choice)) continueButton = "הוסף";
						  if ("update".equals(choice)) continueButton = "עדכן";
						  if ("delete".equals(choice)) continueButton = "מחק";
					%>
				<input type="submit"  name="Submit" value="<%=continueButton%>" style="width: 100%" class="button" enabled onSubmit=;  onMouseOver="this.style.background =  '#d5e0e6';" onMouseOut="this.style.background = '#e4e4e4';" >
</td>

</form>	
<form ACTION="#" NAME="testform" onSubmit="closeThisWindow()">
<td  align="right" colspan="1"  >
								<input type="submit"  name="Submit" value="סיום" style="width: 100%" class="button" enabled onSubmit=;  onMouseOver="this.style.background =  '#d5e0e6';" onMouseOut="this.style.background = '#e4e4e4';" >

</td>
</form>



</tr>
</table>
</td>
</tr>	



<%}%>


				
				
	
<%
} catch (Exception ff){
	ff.printStackTrace();
}
	%>	
			
		

		
	
		
</body>

</html>



<%@ include file="../include/buttom.jsp" %>    





