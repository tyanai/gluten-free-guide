package org.celiac.web.rest;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.ext.Provider;

@Provider
public class CORSFilter implements Filter  {

 
   
   public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}
   
   FilterConfig filterConfig = null;
   

@Override
public void destroy() {
	// TODO Auto-generated method stub
	
}

@Override
public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
	
	
		HttpServletResponse httpServlet = (HttpServletResponse) resp;
		httpServlet.setHeader("Access-Control-Allow-Origin", "*");
		httpServlet.setHeader("Access-Control-Allow-Credentials", "true");
		httpServlet.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
		httpServlet.setHeader("Access-Control-Max-Age", "1209600");
		httpServlet.setHeader("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
		chain.doFilter(req, resp);
	
	
	
	/*
	httpServlet.addHeader("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
	httpServlet.addHeader("Access-Control-Allow-Credentials", "true");
	httpServlet.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
	httpServlet.addHeader("Access-Control-Max-Age", "1209600");
	*/
	
	
}

}
