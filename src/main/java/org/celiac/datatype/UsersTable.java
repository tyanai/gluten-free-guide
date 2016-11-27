package org.celiac.datatype;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;

public class UsersTable {

	
	
	public static Date MySqlStringToDate(String date){
		
		if (date == null) return null;
		
		StringTokenizer strToken = new StringTokenizer(date,"-");
		
		int year = new Integer(strToken.nextToken().trim()).intValue();
		int month = new Integer(strToken.nextToken().trim()).intValue();
		int day = new Integer(strToken.nextToken().trim()).intValue();
		
		Calendar currentDate = new GregorianCalendar();
		currentDate.set(year,month-1,day);
		
		return currentDate.getTime();	
	}
	
	
}
