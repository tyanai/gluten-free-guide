package org.celiac.web.servlet;


import java.io.IOException;
import java.util.StringTokenizer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GFB extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1968228330899077025L;
	@Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException
    {
    	String user = null;
    	String password = null;
    	String tmp = null;
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("utf-8");
        request.setCharacterEncoding("utf-8");
        
        
        tmp = (String)request.getParameter("user");
        StringTokenizer strToken = new StringTokenizer(tmp,"-");
        user = strToken.nextToken();
        password = strToken.nextToken();
        
        request.getSession().setAttribute("user_id",user);
        request.getSession().setAttribute("user_pass",password);
        
        
        String nextJSP = "/jsp/loginWapB.jsp";
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
        dispatcher.forward(request,response);
        
      
        
    }
	@Override
    public void doPost(HttpServletRequest request, HttpServletResponse res)
    throws IOException, ServletException
    {
        doGet(request, res);
    }
}

