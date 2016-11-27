<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="org.celiac.util.AutoCompleteSearch"%>
<%
response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("utf-8");
        request.setCharacterEncoding("utf-8");
	AutoCompleteSearch db = new AutoCompleteSearch();

	String query = request.getParameter("term");
	
	//List<String> countries = db.getProductData(query);
	String countries = db.getProductData(query);
	out.println(countries);
	//System.out.println(countries);
  /*
	Iterator<String> iterator = countries.iterator();
	while(iterator.hasNext()) {
		String country = (String)iterator.next();
		out.println(country);
		System.out.println(country);
	}
	*/
%>