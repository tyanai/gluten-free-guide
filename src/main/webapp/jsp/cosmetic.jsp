<%@ page language="java" 
         import="org.celiac.api.GlutenFreeAPI, org.celiac.web.bean.*, java.util.*, org.celiac.business.GFS, org.celiac.business.GFSByCategoryAndCompanyImpl, org.celiac.datatype.*" 
         errorPage="loginError.jsp" %>
         
         
         
         
         
<jsp:useBean id="User" scope="session" class="org.celiac.web.bean.User">
  <jsp:setProperty name="User" property="*"/>
</jsp:useBean>

<%
try{
if (User.getUser() == null) throw new Exception("User was not login. Please login.");
%>


<body onload="document.forms[0].submit()">
<form action="http://www.celiac.org.il/u_logonc.asp" method="post">

<input type="hidden" name="user" value="<%=User.getUser().getCELIAC_MEMBER_ID()%>" >
<input type="hidden" name="pass" value="<%=User.getUser().getUSER_TZ()%>" >
<input type="hidden" name="from_url" value="3" >


</form>
</body>













<%
}catch (Exception e){

	String theErrorOut = e.getMessage();
	session.setAttribute("gfsmsLoginError",theErrorOut);
	response.sendRedirect("../index.jsp");
}
%>