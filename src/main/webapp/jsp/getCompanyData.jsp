<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="org.celiac.util.AutoCompleteSearch"%>
<%
response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("utf-8");
        request.setCharacterEncoding("utf-8");
	AutoCompleteSearch db = new AutoCompleteSearch();

	String query = request.getParameter("term");
	
	String countries = db.getCompanyData(query);
	out.println(countries);
%>