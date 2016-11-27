package org.celiac.web.servlet;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SMSServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1968228330899077025L;
	@Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException
    {
    	String theSMSInput = "";
    	String theCellPhoneNumber = "";
    	
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("utf-8");
        request.setCharacterEncoding("utf-8");
        
        theSMSInput = (String)request.getParameter("message");
        theCellPhoneNumber = (String)request.getParameter("cellPhone");
        
        PrintWriter out = response.getWriter();
        
        out.println("Parameters in this request:<br>");
        if (theCellPhoneNumber != null || theSMSInput != null) {
            out.println("SMS Sent:");
            out.println(" = " + theSMSInput + "<br>");
            out.println("Cell Phone number Sent:");
            out.println(" = " + theCellPhoneNumber);
        } else {
            out.println("No Parameters, Please enter some");
        }
        
    }
	@Override
    public void doPost(HttpServletRequest request, HttpServletResponse res)
    throws IOException, ServletException
    {
        doGet(request, res);
    }
}

