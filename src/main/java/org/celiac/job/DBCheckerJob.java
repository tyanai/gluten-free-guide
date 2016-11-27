package org.celiac.job;

import java.util.Date;

import org.celiac.util.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;



public class DBCheckerJob implements org.quartz.Job {
	
	public static final String jobCode = "DBCheck";
	
	
	
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
        run();
        
    }
	
	
	
	 /**
     * Quartz requires a public empty constructor so that the
     * scheduler can instantiate the class whenever it needs.
     */
    public DBCheckerJob() {
    }

    
	public void run(){
		Logger.info(jobCode + " says: Database checker isActive: - executing at " + new Date());
		
		
		
		if (!GFGuideCronTrigger.DBisUpForJobs) {
			Logger.info("Database checker: DB was down when last Job init occured.  Trying again.");
			try{
				GFGuideCronTrigger GFGCT = new GFGuideCronTrigger();
				GFGCT.run();
			}catch (Exception e){
				Logger.error("Looks like DB is still down - will try again in 10 minutes.", e);
			}
			return;
		}
		
		
		
	}
	
	
	
	
	
}
