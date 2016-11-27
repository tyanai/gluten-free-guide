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


public class DummyExpirationEmailJob extends JobBase implements Job {
	
	public static final String jobCode = "DEE";
	private static JobDetails JobPropertieDetails = null;
	private static JobTemplateReader jobTemplate = null;
	
	
	
	public JobDetails getJobPropertieDetails(){
		
		
		if (jobTemplate == null){
			jobTemplate = new JobTemplateReader("dummyExpirationEmail.job");
		}
	
		if (JobPropertieDetails == null){
			
			JobPropertieDetails = new JobDetails();
			JobPropertieDetails.setDETAILS(jobTemplate.getProperties("details"));
			JobPropertieDetails.setTEXT(jobTemplate.getProperties("text"));
			JobPropertieDetails.setTYPE(DummyExpirationEmailJob.jobCode);
			
		}
		
		return JobPropertieDetails;
		
	}
	
	
	 /**
     * Quartz requires a public empty constructor so that the
     * scheduler can instantiate the class whenever it needs.
     */
    public DummyExpirationEmailJob() {
    }

    
	public void run(){
		Logger.info(jobCode + " says: " + this.getJobDetails().getNAME() + " code: " + this.getJobDetails().getTYPE() + " isActive: " + this.getJobDetails().getACTIVE() + " - executing at " + new Date());
		
		//looking for all user who have an expiration in arg 1 day
		Set<GFUser> listOfUsers = getExpiredUsers(this.getJobDetails().getTYPE_ARG_1());
		if (listOfUsers == null) return;
		
		//setting the message for those users
		String tmpMessage = getMessageSet(this.getJobDetails().getTEXT(),listOfUsers);
		
		if (tmpMessage == null) {
			Logger.info("DummyExpirationEmailJob: no expired user were found. Exit.");
			return;
		}
		Logger.info("Going to send message to email.  Message is: " + tmpMessage);
		
		//sending email to arg2
		try{
			new Pop3Email().sendMessageForJob(this.getJobDetails().getTYPE_ARG_3(), "התראה לפני פקיעת חשבון", tmpMessage, null, "מנהל הדומיין");
		} catch (Exception e){
			Logger.error("Failed to send email for job " + this.getJobDetails().getNAME(), e);
		}
	}
	
	@SuppressWarnings("deprecation")
	public String getMessageSet(String message, Set<GFUser> listOfUsers){
		
		Map<String, String> ignoreMap = new HashMap<String, String>();
		StringTokenizer strToken = new StringTokenizer(this.getJobDetails().getTYPE_ARG_2(),",");
		String tmp = null;
		while (strToken.hasMoreElements()) {
			tmp = strToken.nextToken().trim();
			ignoreMap.put(tmp, tmp);
		}
		
		
		String output = message;
		output = output.replaceFirst("#", "מנהל");
		output = output.replaceFirst("#", "GFGuide");
		output = output.replaceFirst("#", this.getJobDetails().getTYPE_ARG_1());
		//output = output.replaceFirst("#", "8");
		
		output = output.replaceFirst("#", new Date().toLocaleString());
		output = output.replaceFirst("#", listOfUsers.size()+"");
		
		String usersString = "";
		Iterator<GFUser> users = listOfUsers.iterator();
		GFUser tmpUser = null;
		while (users.hasNext()){
			tmpUser = (GFUser)users.next();
			if (ignoreMap.containsKey(tmpUser.getCELIAC_MEMBER_ID())) {
				Logger.info("Job DummyExpirationEmail Ignoring user " + tmpUser.getCELIAC_MEMBER_ID());
				usersString = usersString + tmpUser.getFIRST_NAME() + " " + tmpUser.getLAST_NAME() + " " + tmpUser.getEMAIL() + "  - מתעלם על פי הגדרה -" + " <br>";
			} else {
				usersString = usersString + tmpUser.getFIRST_NAME() + " " + tmpUser.getLAST_NAME() + " " + tmpUser.getEMAIL() + "  " + " <br>";
			}
		}
		output = output.replaceFirst("#", usersString);
		//don't send anything in case no user was found.
		if ("".equals(usersString)) return null;
		
		return output;
	}
	
	
	
}
