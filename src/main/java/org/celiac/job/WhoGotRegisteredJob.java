package org.celiac.job;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import org.celiac.datatype.GFUser;
import org.celiac.datatype.JobDetails;
import org.celiac.util.Logger;
import org.celiac.util.Pop3Email;


public class WhoGotRegisteredJob extends JobBase implements Job {
	
	public static final String jobCode = "WGR";
	private static JobDetails JobPropertieDetails = null;
	private static JobTemplateReader jobTemplate = null;
	
	
	
	public JobDetails getJobPropertieDetails(){
		
		
		if (jobTemplate == null){
			jobTemplate = new JobTemplateReader("whoGotRegistered.job");
		}
	
		if (JobPropertieDetails == null){
			
			JobPropertieDetails = new JobDetails();
			JobPropertieDetails.setDETAILS(jobTemplate.getProperties("details"));
			JobPropertieDetails.setTEXT(jobTemplate.getProperties("text"));
			JobPropertieDetails.setTYPE(WhoGotRegisteredJob.jobCode);
			
		}
		
		return JobPropertieDetails;
		
	}
	
	
	 /**
     * Quartz requires a public empty constructor so that the
     * scheduler can instantiate the class whenever it needs.
     */
    public WhoGotRegisteredJob() {
    }

    
	public void run(){
		Logger.info(jobCode + " says: " + this.getJobDetails().getNAME() + " code: " + this.getJobDetails().getTYPE() + " isActive: " + this.getJobDetails().getACTIVE() + " - executing at " + new Date());
		
		//looking for all user who have an expiration in arg 1 day
		Set<GFUser> listOfUsers = getRegisteredUsers(this.getJobDetails().getTYPE_ARG_1());
		if (listOfUsers == null) return;
		
		//setting the message for those users
		String tmpMessage = getMessageSet(this.getJobDetails().getTEXT(),listOfUsers);
		
		if (tmpMessage == null) {
			Logger.info("WhoGotRegisteredJob: no new user were found. Exit.");
			return;
		}
		Logger.info("Going to send message to email.  Message is: " + tmpMessage);
		
		//sending email to arg2
		try{
			new Pop3Email().sendMessageForJob(this.getJobDetails().getTYPE_ARG_2(), "הודעה על נרשמים חדשים", tmpMessage, null, "מנהל הדומיין");
		} catch (Exception e){
			Logger.error("Failed to send email for job " + this.getJobDetails().getNAME(), e);
		}
	}
	
	public String getMessageSet(String message, Set<GFUser> listOfUsers){
		
		
		
		String output = message;
		output = output.replaceFirst("#", "מנהל");
		output = output.replaceFirst("#", "GFGuide");
		output = output.replaceFirst("#", this.getJobDetails().getTYPE_ARG_1());
			
				
		String usersString = "";
		Iterator<GFUser> users = listOfUsers.iterator();
		GFUser tmpUser = null;
		while (users.hasNext()){
			tmpUser = (GFUser)users.next();
			usersString = usersString + "(" + tmpUser.getCELIAC_MEMBER_ID() + ")  " + tmpUser.getFIRST_NAME() + " " + tmpUser.getLAST_NAME() + " " + tmpUser.getEMAIL() + "  " + " <br>";
			
		}
		output = output.replaceFirst("#", usersString);
		//don't send anything in case no user was found.
		if ("".equals(usersString)) return null;
		
		return output;
	}
	
	
	public Set<GFUser> getRegisteredUsers(String numOfDays){
		long aDay = 24*60*60*1000;
		long numOfDaysImMS = 0;
		try{
			numOfDaysImMS = new Integer(numOfDays).intValue()*aDay;
		}catch (Exception e){
			Logger.error("Failed to set argument 1 on WhoGotRegistered.Job. Argument is '" + numOfDays + "'",e);
			return null;
		}
		
		Set<GFUser> allUsers = null;
		try{
			allUsers = org.celiac.util.database.DBQueryFactory.getDBHandler().getAllMembersExpirationsWhoRegisterToGFGuide();
		}catch (Exception e){
			Logger.error("Failed to mark effective new users", e);
			return null;
		}
		
		long today = new Date().getTime();
		long backDate = today - numOfDaysImMS;
		Set<GFUser> output = new LinkedHashSet<GFUser>();
		GFUser tmpUser = null;
		try{
			
			Iterator<GFUser> users = allUsers.iterator();
			Date effDate = null;
			while (users.hasNext()){
				tmpUser = (GFUser)users.next();
				effDate = tmpUser.getEFFECTIVE_DATE();
				if ((effDate.getTime() - backDate > 0)){
					
					output.add(tmpUser);
				
				}
			}
		}catch (Exception e){
			Logger.error("Failed to mark effective new users", e);
		}
		return output;
		
	}
	
}
