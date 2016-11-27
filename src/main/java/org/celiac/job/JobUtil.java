package org.celiac.job;

import java.util.Date;

import org.celiac.datatype.JobDetails;
import org.celiac.util.Logger;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;

public class JobUtil {

	public void updateSchedulerJob(JobDetails jDetails) {
		deleteSchedulerJob(jDetails.getNAME());
		addSchedulerJob(jDetails);
	}

	public void deleteSchedulerJob(String name) {
		JobDetails jDetails = null;
		try {

			jDetails = org.celiac.util.database.DBQueryFactory.getDBHandler().getJob(name);

			JobDetail quartzJob = JobFactory.sf.getScheduler().getJobDetail(jDetails.getNAME(), jDetails.getTYPE());
			Logger.info("Got the Job for delete : " + quartzJob);
			if (quartzJob == null) {
				Logger.info(jDetails.getNAME() + " was not in scheduler, hence will not be delete for scheduler.");
				return;
			}
			JobFactory.sf.getScheduler().deleteJob(jDetails.getNAME(), jDetails.getTYPE());
			Logger.info(jDetails.getNAME() + " has been deleted.");
		} catch (Exception e) {
			Logger.error("Failed to delete job '" + jDetails.getNAME() + "'", e);
		}
	}

	public void addSchedulerJob(JobDetails jDetails) {
		JobDetail job = null;
		CronTrigger trigger = null;
		Date ft = null;

		if ("no".equals(jDetails.getACTIVE()))
			return;
		try {
			job = new JobDetail(jDetails.getNAME(), jDetails.getTYPE(), Class.forName(JobFactory.getJobClassName(jDetails.getTYPE())));
			trigger = new CronTrigger(jDetails.getTYPE() + "-" + jDetails.getNAME(), jDetails.getTYPE(), jDetails.getNAME(), jDetails.getTYPE(), "0 " + jDetails.getMINUTE() + " " + jDetails.getHOUR() + " " + jDetails.getDAY_OF_MONTH() + " " + jDetails.getMONTH() + " " + jDetails.getDAY_OF_WEEK());
			JobFactory.sf.getScheduler().addJob(job, true);
			ft = JobFactory.sf.getScheduler().scheduleJob(trigger);
			Logger.info(job.getFullName() + " has been scheduled to run at: " + ft + " and repeat based on expression: " + trigger.getCronExpression());
		} catch (Exception e) {
			Logger.error("Failed to initialize job '" + jDetails.getNAME() + "'", e);
		}
	}

}
