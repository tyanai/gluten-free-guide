package org.celiac.job;

import org.celiac.datatype.JobDetails;

public interface Job extends org.quartz.Job {
	
	public JobDetails getJobPropertieDetails();
}
