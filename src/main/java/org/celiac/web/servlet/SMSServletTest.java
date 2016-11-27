package org.celiac.web.servlet;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SMSServletTest extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 322069108081580770L;
	@Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException
    {
    	
    	
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("utf-8");
        request.setCharacterEncoding("utf-8");
        
        
        
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Request Parameters Example</title>");
        out.println("</head>");
        out.println("<body>");
        
        out.println("<P>");
        out.print("<form action=\"");
        out.print("sms\" ");
        out.println("method=POST>");
        out.println("SMS Message:");
        out.println("<input type=text size=20 name=message>");
        out.println("<br>");
        out.println("Cell Phone:");
        out.println("<input type=text size=20 name=cellPhone>");
        out.println("<br>");
        out.println("<input type=submit>");
        out.println("</form>");
        out.println("</body>");
        out.println("</html>");
    }
	@Override
    public void doPost(HttpServletRequest request, HttpServletResponse res)
    throws IOException, ServletException
    {
        doGet(request, res);
    }
}

