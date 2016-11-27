<jsp:useBean id="User" scope="session" class="org.celiac.web.bean.User">
  <jsp:setProperty name="User" property="*"/>
</jsp:useBean>

<%
String choice = (String)request.getParameter("Login");

	
  String display = null;
  
  if (choice.equals("Login")){
  	try{
  		String user_id = (String)request.getParameter("user_id");
  		String user_pass = (String)request.getParameter("user_pass");
  		session.setAttribute("user_id",user_id);
  		session.setAttribute("user_pass",user_pass);
  		display = "loginWap.jsp";
  	}catch(Exception  e){
  	
  		throw e;
  
  	}
  }
  /*
  else if(choice.equals("New User")){
  		try{
  			display = "newUser.jsp";
  		}catch(Exception  e){
  			throw e;
  
  		}
  }else if(choice.equals("  Oops, lost it  ")){
  		try{
  			display = "lostIdentity.jsp";
  		}catch(Exception  e){
  			throw e;
  
  		}
  }
   
   */
  
%>

<jsp:forward page="<%= display %>"/>