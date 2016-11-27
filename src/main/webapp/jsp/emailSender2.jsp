<%@ page language="java" 
         import="org.celiac.api.GlutenFreeAPI, org.celiac.web.bean.*,org.celiac.datatype.*, java.util.*" 
         errorPage="loginError.jsp" %>
<jsp:useBean id="User" scope="session" class="org.celiac.web.bean.User">
  <jsp:setProperty name="User" property="*"/>
</jsp:useBean>
<%@ include file="../include/header.jsp" %>    
<% if (!(User.getUser().getPERMISSIONS().equals("admin")) && !(User.getUser().getPERMISSIONS().equals("mitnadev"))) throw new Exception("User was not login. Please login."); %>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%

request.setCharacterEncoding("utf-8");
 response.setContentType("text/html;charset=UTF-8");


String SUBJECT = null;
if (request.getParameter("SUBJECT") != null){
		SUBJECT = (String)request.getParameter("SUBJECT");
		
}	

String TO_EMAIL = null;
if (request.getParameter("TO_EMAIL") != null){
		TO_EMAIL = (String)request.getParameter("TO_EMAIL");
		
}	

String BCC_EMAIL = null;
if (request.getParameter("BCC_EMAIL") != null){
		BCC_EMAIL = (String)request.getParameter("BCC_EMAIL");
		
}	

String BODY = null;
if (request.getParameter("userNote") != null){
		BODY = (String)request.getParameter("userNote");
		
}	


String theErrorOut = "ההודעה נשלחה בהצלחה!";

try{


	session.setAttribute("BCC_EMAIL",BCC_EMAIL);
	session.setAttribute("TO_EMAIL",TO_EMAIL);
	session.setAttribute("SUBJECT",SUBJECT);
	session.setAttribute("BODY",BODY);
	
	
  Set<GFUser> theAllUsers = org.celiac.util.database.DBQueryFactory.getDBHandler().getAllUserDetails();
  new org.celiac.util.Pop3Email().sendMessageForJob(TO_EMAIL, SUBJECT, BODY,null,"");
  


}catch (Exception e){

	theErrorOut = e.getMessage();
		
}
session.setAttribute("gfsmsEmailSender",theErrorOut);
response.sendRedirect("emailSender.jsp");
%>

</body>
</html>
<%@ include file="../include/buttom.jsp" %>    