package org.celiac.web.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.celiac.datatype.JobDetails;
import org.celiac.job.Job;
import org.celiac.job.JobFactory;
import org.celiac.job.JobUtil;
import org.celiac.util.Logger;
import org.celiac.util.database.DBQueryFactory;

public class JobEditor extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JobUtil jobUtil = new JobUtil();

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {

			response.setCharacterEncoding("utf-8");
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset=UTF-8");

			JobDetails jobDetails = new JobDetails();
			String choice = (String) request.getParameter("choice");

			if ("delete".equals(choice)) {
				jobDetails.setNAME((String) request.getParameter("name"));

			} else {

				jobDetails.setMINUTE((String) request.getParameter("jobMinute"));
				jobDetails.setHOUR((String) request.getParameter("jobHour"));
				jobDetails.setDAY_OF_MONTH((String) request.getParameter("jobDayOfMonth"));
				jobDetails.setMONTH((String) request.getParameter("jobMonth"));
				jobDetails.setDAY_OF_WEEK((String) request.getParameter("jobDayOfWeek"));
				jobDetails.setTYPE_ARG_1((String) request.getParameter("argument1"));
				jobDetails.setTYPE_ARG_2((String) request.getParameter("argument2"));
				jobDetails.setTYPE_ARG_3((String) request.getParameter("argument3"));
				jobDetails.setTYPE_ARG_4((String) request.getParameter("argument4"));
				jobDetails.setACTIVE((String) request.getParameter("active"));
				jobDetails.setREPEAT((String) request.getParameter("repeat"));
				jobDetails.setTYPE((String) request.getParameter("type"));
				jobDetails.setNAME((String) request.getParameter("name"));

				Job tmpJob = JobFactory.getJobHandler(jobDetails.getTYPE());

				jobDetails.setDETAILS(tmpJob.getJobPropertieDetails().getDETAILS());
				jobDetails.setTEXT(tmpJob.getJobPropertieDetails().getTEXT());
			}

			if ("new".equals(choice)) {
				try {
					DBQueryFactory.getDBHandler().addJob(jobDetails);
					jobUtil.addSchedulerJob(jobDetails);
				} catch (Exception e) {
					Logger.error("Failed to add new job at JobEditor.java", e);
				}
			} else if ("update".equals(choice)) {
				try {
					DBQueryFactory.getDBHandler().updateJob(jobDetails);
					jobUtil.updateSchedulerJob(jobDetails);
				} catch (Exception e) {
					Logger.error("Failed to update job " + jobDetails.getNAME() + " at JobEditor.java", e);
				}
			}

			String nextJSP = null;
			if ("new".equals(choice))
				nextJSP = "/jsp/manageJob.jsp?" + "choice=update&jobName=" + jobDetails.getNAME();
			if ("update".equals(choice))
				nextJSP = "/jsp/manageJob.jsp?" + "choice=update&jobName=" + jobDetails.getNAME();

			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
			dispatcher.forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.toString());
		}

	}
}
