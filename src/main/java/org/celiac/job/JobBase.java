package org.celiac.job;



import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;

import org.celiac.datatype.GFUser;
import org.celiac.datatype.JobDetails;
import org.celiac.util.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public abstract class JobBase {
	private JobDetails jobDetails = null;
	
	public final static String hebJan = "ינואר";
	public final static String hebFeb = "פברואר";
	public final static String hebMar = "מרץ";
	public final static String hebApr = "אפריל";
	public final static String hebMay = "מאי";
	public final static String hebJun = "יוני";
	public final static String hebJul = "יולי";
	public final static String hebAug = "אוגוסט";
	public final static String hebSep = "ספטמבר";
	public final static String hebOct = "אוקטובר";
	public final static String hebNov = "נובמבר";
	public final static String hebDec = "דצמבר";
	
	
	
	
	
	public String getHebrewRepresentationForDate(Date date, String type) {
		
		
	
		Locale locale = new Locale("iw", "IL");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		
		//Expiration date String
		String expirationDate = SimpleDateFormat.getDateInstance(SimpleDateFormat.LONG, locale).format(date).toUpperCase();
		
		//New Start date String
		calendar.add(Calendar.DAY_OF_YEAR,1);
		String newStartDate = SimpleDateFormat.getDateInstance(SimpleDateFormat.LONG, locale).format(calendar.getTime()).toUpperCase();
		
		//New Expiration date:
		calendar.add(Calendar.MONTH,11);
		int lastDate = calendar.getActualMaximum(Calendar.DATE);
		calendar.set(Calendar.DAY_OF_MONTH, lastDate);
		String newExpDate = SimpleDateFormat.getDateInstance(SimpleDateFormat.LONG, locale).format(calendar.getTime()).toUpperCase();
		
		if ("lastExp".equals(type)) return expirationDate;
		else if ("newStart".equals(type)) return newStartDate;
		else if ("newExp".equals(type)) return newExpDate;
		else return null;
		
		}
	
		
	

	public JobDetails getJobDetails() {
		return jobDetails;
	}
	
	

	public void loadJobDetails(String jobCode) {
		if (this.jobDetails != null) return;
		try{
			this.jobDetails = org.celiac.util.database.DBQueryFactory.getDBHandler().getJob(jobCode);
        }catch (Exception e){
        	e.printStackTrace();
        }
        
	}
	
	/**
     * <p>
     * Called by the <code>{@link org.quartz.Scheduler}</code> when a
     * <code>{@link org.quartz.Trigger}</code> fires that is associated with
     * the <code>Job</code>.
     * </p>
     * 
     * @throws JobExecutionException
     *             if there is an exception while executing the job.
     */
    public void execute(JobExecutionContext context)
        throws JobExecutionException {

        // This job simply prints out its job name and the
        // date and time that it is running
        String name = context.getJobDetail().getName();
        loadJobDetails(name);
        run();
        if (this.jobDetails.getREPEAT().equals("yes")) oneTime(context);
        
    }
    
    public abstract void run();

	
	public void oneTime(JobExecutionContext context) throws JobExecutionException {
		this.jobDetails.setACTIVE("no");
		try{
			Logger.info("deleting job: " + getJobDetails().getNAME());
			context.getScheduler().deleteJob(this.getJobDetails().getNAME(), this.getJobDetails().getTYPE());
			org.celiac.util.database.DBQueryFactory.getDBHandler().updateJob(this.jobDetails);
		} catch (Exception e){
			e.printStackTrace();
		}
	
	}
	
	
	public Set<GFUser> getExpiredUsers(String numOfDays){
		long aDay = 24*60*60*1000;
		long numOfDaysImMS = 0;
		try{
			numOfDaysImMS = new Integer(numOfDays).intValue()*aDay;
		}catch (Exception e){
			Logger.error("Failed to set argument 1 on dummyExpirationEmail.job. Argument is '" + numOfDays + "'",e);
			return null;
		}
		long today = new Date().getTime();
		long futureDate = today + numOfDaysImMS;
		Set<GFUser> output = new LinkedHashSet<GFUser>();
		GFUser tmpUser = null;
		try{
			Set<GFUser> allUsers = org.celiac.util.database.DBQueryFactory.getDBHandler().getAllMembersExpirationsWhoRegisterToGFGuide();
			Iterator<GFUser> users = allUsers.iterator();
			Date expDate = null;
			while (users.hasNext()){
				tmpUser = (GFUser)users.next();
				expDate = tmpUser.getEXPIRATION_DATE();
				if ((expDate.getTime() - futureDate > 0)  && (expDate.getTime() - futureDate < aDay)){
					
					output.add(tmpUser);
				
				}
			}
		}catch (Exception e){
			Logger.error("Failed to mark expiration users", e);
		}
		return output;
		
	}
	
	
}
