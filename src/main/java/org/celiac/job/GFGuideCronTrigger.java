/* 
 * Copyright 2005 - 2009 Terracotta, Inc. 
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not 
 * use this file except in compliance with the License. You may obtain a copy 
 * of the License at 
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0 
 *   
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the 
 * License for the specific language governing permissions and limitations 
 * under the License.
 * 
 */

package org.celiac.job;

import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import org.celiac.datatype.JobDetails;
import org.celiac.util.Logger;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;

/**
 * This Example will demonstrate all of the basics of scheduling capabilities of
 * Quartz using Cron Triggers.
 * 
 * @author Bill Kratzer
 */
public class GFGuideCronTrigger {

	public static boolean DBisUpForJobs = false;
	public static boolean DBJobCheckerIsUp = false;
	public static boolean schedulerStarted = false;
	JobDetail job = null;
	CronTrigger trigger = null;
	Date ft = null;

	public void run() throws Exception {

		if (!DBJobCheckerIsUp) {
			Logger.info("------- Scheduling DBChecker ----------------");
			job = new JobDetail("DBChecker", "DBChecker", org.celiac.job.DBCheckerJob.class);
			trigger = new CronTrigger("DBChecker" + "-" + "DBChecker", "DBChecker", "DBChecker", "DBChecker", "0 0/10 * * * ?");
			JobFactory.sf.getScheduler().addJob(job, true);
			ft = JobFactory.sf.getScheduler().scheduleJob(trigger);
			DBJobCheckerIsUp = true;
			Logger.info("------- Scheduling DBChecker End. ----------------");
		}

		Logger.info("------- Scheduling Jobs ----------------");

		JobUtil jobUtil = new JobUtil();
		try {
			Set<JobDetails> theAllJobs = org.celiac.util.database.DBQueryFactory.getDBHandler().getJobs();
			if (theAllJobs != null) {
				DBisUpForJobs = true;
				JobDetails jobDetails = null;
				Iterator<JobDetails> iterForJobs = theAllJobs.iterator();

				while (iterForJobs.hasNext()) {
					jobDetails = (JobDetails) iterForJobs.next();
					jobUtil.addSchedulerJob(jobDetails);

				}
				Logger.info("------- Scheduling Jobs Complete --------");
			} else {
				Logger.error("Looks like DB is still down - the DBChecker will try again in 10 minutes.");
			}

		} catch (Exception e) {
			Logger.error("Looks like DB is still down - the DBChecker will try again in 10 minutes.", e);
		}

		if (!schedulerStarted) {
			Logger.info("------- Starting Scheduler ----------------");

			// All of the jobs have been added to the scheduler, but none of the
			// jobs
			// will run until the scheduler has been started
			JobFactory.sf.getScheduler().start();
			schedulerStarted = true;
			Logger.info("------- Started Scheduler -----------------");
		}

	}

	public static void main(String[] args) throws Exception {

		GFGuideCronTrigger trigger = new GFGuideCronTrigger();
		trigger.run();
	}

}
