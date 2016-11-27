package org.celiac.job;


import java.util.Date;

import org.celiac.datatype.JobDetails;
import org.celiac.util.Logger;


public class UnregisterMembersEmailJob  extends JobBase  implements Job {
	
	public static final String jobCode = "UMN";
	private static JobDetails JobPropertieDetails = null;
	private static JobTemplateReader jobTemplate = null;
	
	
	
	public  JobDetails getJobPropertieDetails(){
		
		if (jobTemplate == null){
			jobTemplate = new JobTemplateReader("unregisterMembersEmail.job");
		}
		
		if (JobPropertieDetails == null){
			JobPropertieDetails = new JobDetails();
			JobPropertieDetails.setDETAILS(jobTemplate.getProperties("details"));
			JobPropertieDetails.setTEXT(jobTemplate.getProperties("text"));
			JobPropertieDetails.setTYPE(UnregisterMembersEmailJob.jobCode);
			
		}
		return JobPropertieDetails;
		
	}
	
	 /**
     * Quartz requires a public empty constructor so that the
     * scheduler can instantiate the class whenever it needs.
     */
    public UnregisterMembersEmailJob() {
    }

    public void run(){
    	Logger.info(jobCode + " says: " + this.getJobDetails().getNAME() + " code: " + this.getJobDetails().getTYPE() + " isActive: " + this.getJobDetails().getACTIVE() + " - executing at " + new Date());
    	
    }

    
	

}
