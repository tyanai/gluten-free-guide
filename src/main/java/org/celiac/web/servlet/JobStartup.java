package org.celiac.web.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.celiac.job.GFGuideCronTrigger;
import org.celiac.util.Logger;

public class JobStartup extends HttpServlet {
	
	private static final long serialVersionUID = -1591569077427927772L;

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}
	
	 public void init(ServletConfig config) throws ServletException { 
	
		 	 
		 
		 Logger.info("************ - LOADED - ******************");
		 GFGuideCronTrigger trigger = new GFGuideCronTrigger();
	     try{
	    	 trigger.run();
	     } catch (Exception e){
	    	 e.printStackTrace();
	     }
	 } 
}
