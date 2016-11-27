package org.celiac.job;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;

import org.celiac.util.Logger;
import org.celiac.util.PropertiesLoader;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

public class JobFactory {

	static Map<String, String> jobCodeClassMap = new HashMap<String, String>();
	static Job[] jobs = null;
	public static SchedulerFactory sf = null;

	static {
		try{
		Logger.info("Loading jobs properties from files");	
		
		// First we must get a reference to a scheduler
    	sf = new StdSchedulerFactory();
    	
		
		Properties prop = new Properties();
		
		InputStream is = PropertiesLoader.class
				.getResourceAsStream(JobTemplateReader
						.getSystemPropertiesLocation() + "/jobs.properties");
		
		try {
			prop.load(is);
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		StringTokenizer strToken = new StringTokenizer(
				(String) prop.get("jobClasses"), ",");
		
		String tmpJobCode = null;
		String tmpClassName = null;
		jobs = new Job[strToken.countTokens()];
		int i=0;
		
		while (strToken.hasMoreElements()) {
			
			tmpClassName = strToken.nextToken().trim();
			try{
				
				jobs[i] = (Job)Class.forName(tmpClassName).newInstance();
			}catch (Exception e){
				e.printStackTrace();
			}
			
			tmpJobCode = jobs[i].getJobPropertieDetails().getTYPE();
			
			jobCodeClassMap.put(tmpJobCode, tmpClassName);
			
			i++;
		}
		
		}catch (Exception ee){
			Logger.error("Failed to create JobFactory instance",ee);
		}

	}

	public static Job getJobHandler(String jobCode) throws Exception {

		String jobClass = (String) jobCodeClassMap.get(jobCode);
		if (jobClass != null)
			return (Job) Class.forName(jobClass).newInstance();
		else
			return null;

	}
	
	public static Job[] getJobs(){
		return jobs;
	}
	
	public static String getJobClassName(String jobCode){
		return (String) jobCodeClassMap.get(jobCode);
	}

}
