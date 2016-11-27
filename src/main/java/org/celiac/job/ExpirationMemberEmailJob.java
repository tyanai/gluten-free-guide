package org.celiac.job;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.celiac.datatype.GFUser;
import org.celiac.datatype.JobDetails;
import org.celiac.util.Logger;
import org.celiac.util.Pop3Email;


public class ExpirationMemberEmailJob extends JobBase implements Job {
	
	public static final String jobCode = "EME";
	private static JobDetails JobPropertieDetails = null;
	private static JobTemplateReader jobTemplate = null;
	
	
	
	public JobDetails getJobPropertieDetails(){
		
		if (jobTemplate == null){
			jobTemplate = new JobTemplateReader("expirationMemberEmail.job");
		}
		
		if (JobPropertieDetails == null){
			JobPropertieDetails = new JobDetails();
			JobPropertieDetails.setDETAILS(jobTemplate.getProperties("details"));
			JobPropertieDetails.setTEXT(jobTemplate.getProperties("text"));
			JobPropertieDetails.setTYPE(ExpirationMemberEmailJob.jobCode);
			
		}
		return JobPropertieDetails;
		
	}
	
	
	 /**
     * Quartz requires a public empty constructor so that the
     * scheduler can instantiate the class whenever it needs.
     */
    public ExpirationMemberEmailJob() {
    }

    
	public void run(){
		Logger.info(jobCode + " says: " + this.getJobDetails().getNAME() + " code: " + this.getJobDetails().getTYPE() + " isActive: " + this.getJobDetails().getACTIVE() + " - executing at " + new Date());
		
		//looking for all user who have an expiration in arg 1 day
		Set<GFUser> listOfUsers = getExpiredUsers(this.getJobDetails().getTYPE_ARG_1());
		if (listOfUsers == null) return;
		
		Map<String, String> ignoreMap = new HashMap<String, String>();
		StringTokenizer strToken = new StringTokenizer(this.getJobDetails().getTYPE_ARG_2(),",");
		String tmp = null;
		while (strToken.hasMoreElements()) {
			tmp = strToken.nextToken().trim();
			ignoreMap.put(tmp, tmp);
		}
		
		
		
		Iterator<GFUser> users = listOfUsers.iterator();
		GFUser tmpUser = null;
		while (users.hasNext()){
			tmpUser = (GFUser)users.next();
						
			if (ignoreMap.containsKey(tmpUser.getCELIAC_MEMBER_ID())) {
				Logger.info("Job ExpirationMemberEmail Ignoring user " + tmpUser.getCELIAC_MEMBER_ID());
				continue;
			}
			//setting the message for each user
			String tmpMessage = getMessageSet(this.getJobDetails().getTEXT(),tmpUser);
			
			Logger.info("Going to send message to email.  Message is: " + tmpMessage);
			
			//sending email to the user
			try{
				//new Pop3Email().sendMessageForJob(tmpUser.getEMAIL(), "תזכורת לחידוש חברות", tmpMessage, null, tmpUser.getFIRST_NAME() + " " + tmpUser.getLAST_NAME());
				new Pop3Email().sendMessageForJob("office@celiac.org.il", "תזכורת לחידוש חברות", tmpMessage, null, tmpUser.getFIRST_NAME() + " " + tmpUser.getLAST_NAME());
			} catch (Exception e){
				Logger.error("Failed to send email for job " + this.getJobDetails().getNAME(), e);
			}
			
			
		}
		
	}
	
	
	public String getMessageSet(String message, GFUser aUser){
		
		String output = message;
		output = output.replaceFirst("#", aUser.getFIRST_NAME());
		output = output.replaceFirst("#", aUser.getLAST_NAME());
		output = output.replaceFirst("#", this.getHebrewRepresentationForDate(aUser.getEXPIRATION_DATE(), "newStart"));
		output = output.replaceFirst("#", this.getHebrewRepresentationForDate(aUser.getEXPIRATION_DATE(), "newStart"));
		output = output.replaceFirst("#", this.getHebrewRepresentationForDate(aUser.getEXPIRATION_DATE(), "newExp"));
		//output = output.replaceFirst("#", "8");
		
		//output = output.replaceFirst("#", new Date().toLocaleString());
		//output = output.replaceFirst("#", listOfUsers.size()+"");
		
				
		return output;
	}

}
