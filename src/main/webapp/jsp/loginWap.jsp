<%@ page language="java" 
         import="org.celiac.web.bean.*, java.util.*" 
         errorPage="loginError.jsp" %>
         
<jsp:useBean id="User" scope="session" class="org.celiac.web.bean.User">
  <jsp:setProperty name="User" property="*"/>
</jsp:useBean>

<%
String display = null;

String user_id = (String)session.getAttribute("user_id");
String user_pass = (String)session.getAttribute("user_pass");

if (("").equals(user_id)) user_id = "###";
	
	
boolean goodUser = false;
try{
   goodUser = User.loadUser(user_id,user_pass);
}catch (Exception e){}
	
java.util.Calendar cal = java.util.Calendar.getInstance();
cal.add(java.util.Calendar.MONTH, -1);	//Adding 1 month to current date
long theTime = cal.getTimeInMillis();
	
if (!goodUser) {
	session.setAttribute("gfsmsLoginError",org.celiac.util.TemplateReader.getHebrewMapWord("USER_OR_PASS_INCORRECT"));
	display = "../index.jsp";
} else if (User.theUser.getEXPIRATION_DATE() == null){
	session.setAttribute("gfsmsLoginError", org.celiac.util.TemplateReader.getHebrewMapWord("CELIAC_MEMBER_ID") + " " + org.celiac.util.TemplateReader.getHebrewMapWord("LO_KAYAM"));
	display = "../index.jsp";
} else if (User.theUser.getACKNOWLEDGE_USE().equals("FALSE")){
  session.setAttribute("userAlreadySaved",User.theUser);
  display = "newUserTOU.jsp";
} else if ((User.theUser.getEXPIRATION_DATE().getTime() < theTime) && (!User.maybeUserIsNoExpire()) ){
	session.setAttribute("gfsmsLoginError",org.celiac.util.TemplateReader.getHebrewMapWord("USER_EXPIRED"));
	display = "../index.jsp";
}

else {	
	session.setAttribute("gfsmsLoginError",null);
	display = "jsp/wap.jsp";
}


response.sendRedirect(display);
%>
