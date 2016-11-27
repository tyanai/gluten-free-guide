package org.celiac.web.servlet;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.celiac.web.bean.User;

public class CustomerS extends HttpServlet {

	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 322069108081480770L;
	
	
	@Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException
    {
    	
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("utf-8");
        request.setCharacterEncoding("utf-8");
        
        
        /*
        tmp = (String)request.getParameter("user");
        StringTokenizer strToken = new StringTokenizer(tmp,"-");
        user = strToken.nextToken();
        password = strToken.nextToken();
        
        request.getSession().setAttribute("user_id",user);
        request.getSession().setAttribute("user_pass",password);
        
        
        String nextJSP = "/jsp/loginWapB.jsp";
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
        dispatcher.forward(request,response);
        
        */
        
        HttpSession session = request.getSession(true);
        User theUser =  (User)session.getAttribute("User");
        if (theUser == null)  System.out.println("no user found yet");
        else System.out.println("The user name is: '" + theUser.getUser().getFIRST_NAME() + "'");
        
        
    }
	@Override
    public void doPost(HttpServletRequest request, HttpServletResponse res)
    throws IOException, ServletException
    {
        doGet(request, res);
    }
}

