package org.celiac.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.celiac.api.GlutenFreeAPI;

public class SMSSimulator extends HttpServlet {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1970531365329760430L;
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		try {
			response.setContentType("text/html; charset=windows-1255");

			response.setCharacterEncoding("utf-8");
			request.setCharacterEncoding("utf-8");

			PrintWriter out = response.getWriter();
			out.write("\r\n");
			out.write("\r\n");
			out.write("\r\n");
			out.write("<head>\r\n");
			out.write("\r\n");
			out.write("<META HTTP-EQUIV=\"Content-Type\" CONTENT=\"text/html; charset=ISO-8859-1\">\r\n");
			out.write("<link href=\"../include/pp.css\" rel=\"stylesheet\" type=\"text/css\">\r\n");
			out.write("</head>\r\n");
			out.write("\r\n");
			out.write("\r\n");

			request.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset=UTF-8");

			String sms = "";
			//String sms2 = "";
			if (request.getParameter("user_id") != null) {
				//sms = new String(((String) request.getParameter("user_id")).getBytes(), "windows-1255");
				sms = (String)request.getParameter("user_id");
				//sms2 = sms;
			}

			out.write("\r\n");
			out.write("\r\n");
			out.write("<br>\r\n");

			StringBuffer sb = new StringBuffer();
			if (!sms.equals("")) {
				String empty = "";

				if ((sms == null) || empty.equals(sms)) {
					sms = "<em>(no sms specified)</em>";
				}

				sb.append("<html><p align=right>");
				try {
					String theOutput = null;

					theOutput = new GlutenFreeAPI().search(sms, null, false);

					if (theOutput != null) {
						int totalCharacters = theOutput.length();
						theOutput = theOutput.replaceAll("\n", "<br>");
						theOutput = theOutput + "<br><br>Character count: " + totalCharacters;
					} else {
						theOutput = "No Match Found";
					}
					sb.append(theOutput);
				} catch (Exception e) {
					e.printStackTrace();
				}
				sb.append("<br>");
				sb.append(" ");
				sb.append("</p></html>");
			}

			out.write("\r\n");
			out.write("\r\n");
			out.write("\r\n");
			out.write("\r\n");
			out.write("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 3.2//EN\">\r\n");
			out.write("<html>\r\n");
			out.write("<head>\r\n");
			out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=windows-1255\">\r\n");
			out.write("<link href=\"/include/pp.css\" rel=\"stylesheet\" type=\"text/css\">\r\n");
			out.write("<title>Gluten Free Search Engine</title>\r\n");
			out.write("</head>\r\n");
			out.write("\r\n");
			out.write("<body bgcolor=\"#ffffff\" LEFTMARGIN=\"10\" RIGHTMARGIN=\"10\"\r\n");
			out.write("      link=\"#3366cc\" vlink=\"#9999cc\" alink=\"#0000cc\"\r\n");
			out.write("      onload=\"document.login_form.user_id.focus()\" > \r\n");
			out.write("\r\n");
			out.write("\r\n");
			out.write("<p align=\"center\">\r\n");
			out.write("<table width=\"50%\" border=\"0\">\r\n");
			out.write("<tr>\r\n");
			out.write("<td>\r\n");
			out.write("<td><img src=\"images/logo_login.jpg\"></td>\r\n");
			out.write("\t\t<td align=\"center\"></td>\t\t\t\t\r\n");
			out.write("\t\t<td>&nbsp;</td>\t\r\n");
			out.write("</td>\r\n");
			out.write("<td>\r\n");
			out.write("<table width=\"100%\" border=\"0\">\r\n");
			out.write("\t<tr>\r\n");
			out.write("\t<tr>\r\n");
			out.write("\t<tr>\r\n");
			out.write("\t<tr>\r\n");
			out.write("\t<tr>\t\t\t\t\t\r\n");
			out.write("\t<tr>\r\n");
			out
					.write("\t\t<td align=\"left\" ><img border=\"0\" src=\"images/logo2.gif\" width=\"225\" height=\"41\"></a></td>\r\n");
			out.write("\t\t<td>\r\n");
			out.write("\t\t<td>\t\t\r\n");
			out.write("\t\t\r\n");
			out.write("\t\t\r\n");
			out.write("\t</tr>\r\n");
			out.write("\t\r\n");
			out.write("\t<tr>\r\n");
			out.write("\t\t<td colspan=\"2\"><hr></td>\r\n");
			out.write("\t</tr>\r\n");
			out.write("\r\n");
			out.write("</table>\r\n");
			out.write("<form name=\"login_form\" action=\"simulator\" method=\"POST\">\r\n");
			out.write("\t\r\n");
			out.write("\t<table width=\"100%\" border=\"0\">\r\n");
			out.write("   \t\t<tr>\r\n");
			out.write("\t\t    <td width=\"100%\" class=\"textSmall\">\r\n");
			out.write("\t\t\t\tSMS:<br>\r\n");
			out.write("\t\t\t\t<input type=\"text\" class=\"textfield\" name=\"user_id\" >\r\n");
			out.write("\t\t\t\t<br><br>\r\n");
			out.write("\t\t\t\t\r\n");
			out
					.write("\t\t\t\t<input name=\"Send\" type=\"submit\" id=\"SMS\" value=\"Send\"  class=\"textfield\" enabled>\t\t\t\r\n");
			out.write("\t\t\t\t\r\n");
			out.write("\t\t\t</td>\r\n");
			out.write("\t\t</tr>\r\n");
			out.write("\t\t<tr>\r\n");
			out.write("\t\t\t<tr>\r\n");
			out.write("\t\t\r\n");
			out.write("\t\t</tr>\r\n");
			out.write("\t\r\n");
			out.write("\t\t</tr>\r\n");
			out.write("    </table>\r\n");
			out.write("\t<tr>\r\n");
			out.write("\t\t<td>\r\n");
			out.write("\t\t\t");
			if (!sms.equals("")) {
				out.write("\r\n");
				out.write("\t\t<td>\r\n");
				out.write("\t\t\t\t");
				out.print(sb);
				out.write("\r\n");
				out.write("\t\t</td>\r\n");
				out.write("\t\t");
			}
			out.write("\r\n");
			out.write("\t\t</td>\r\n");
			out.write("</tr>\t\r\n");
			out.write("</form>\r\n");
			out.write("\r\n");
			out.write("</td>\r\n");
			out.write("</tr>\r\n");
			out.write("\r\n");
			out.write("</table>\r\n");
			out.write("\r\n");
			out.write("</body>\r\n");
			out.write("\r\n");
			out.write("</html>");
		} catch (Throwable t) {
			// do nothing
		}
	}
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse res) throws IOException, ServletException {
		doGet(request, res);
	}
}
