package org.celiac.util;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import org.celiac.util.database.DBQueryFactory;

import org.celiac.datatype.*;

public class GuideDiff  {
	
	Set<DiffData> diffData = new LinkedHashSet<DiffData>();
	String query = null;
	
	public void createDiffReport() throws Exception{
		// products that where removed
		 query = "SELECT distinct a.product, a.company, a.category, a.gluten_free FROM product_index a, temp_product_index b where a.product not in (select b.product from temp_product_index b where b.product = a.product and a.company = b.company and b.category = a.category)";
		 diffData.addAll(DBQueryFactory.getDBHandler().getDiff(query, "PRODUCT_REMOVE"));
		
		// products that where added
		 query = "SELECT distinct a.product, a.company, a.category, a.gluten_free FROM temp_product_index a, product_index b where a.product not in (select b.product from product_index b where b.product = a.product and a.company = b.company and b.category = a.category)";
		 diffData.addAll(DBQueryFactory.getDBHandler().getDiff(query, "PRODUCT_ADDED"));
		
		// product that where changed
		 query = "SELECT a.product, a.company, a.category, a.gluten_free, b.gluten_free FROM temp_product_index a, product_index b where a.product in (select b.product from product_index b where b.product = a.product and a.company = b.company and b.category = a.category and a.gluten_free != b.gluten_free) and b.product = a.product and a.company = b.company and b.category = a.category";
		 diffData.addAll(DBQueryFactory.getDBHandler().getDiff(query, "CHANGE"));
		 
		// company that where removed
		 query = "SELECT product, company, category, gluten_free FROM product_index where company not in (select company from temp_product_index)";
		 diffData.addAll(DBQueryFactory.getDBHandler().getDiff(query, "COMPANY_REMOVE"));
			
		// company that where added
		 query = "SELECT product, company, category, gluten_free FROM temp_product_index where company not in (select company from product_index)";
		 diffData.addAll(DBQueryFactory.getDBHandler().getDiff(query, "COMPANY_ADDED"));
		 
		 System.out.println("Done collect diff");
		 
	}
	
	public void updateDiffReport(){
		
		try{
			DBQueryFactory.getDBHandler().updateDiff(diffData);
			new LinkedHashSet<DiffData>();
		}catch (Exception e){
			diffData = new LinkedHashSet<DiffData>();
			Logger.error("Failed update productDiff table with diff." , e);
		}
	}
	
	public DiffDataCollection getDiffReport(Date fromDate, String numberOfdays,
			String numberOfMonths, String numberOfYears) throws Exception{
		
		DiffDataCollection diffDataCollection = new DiffDataCollection();
		Set<DiffData> resultDiffData = DBQueryFactory.getDBHandler().getDiffByDates(fromDate, numberOfdays, numberOfMonths, numberOfYears);
		
		Iterator<DiffData> iter = resultDiffData.iterator();
		DiffData tmpDiffData = null;
		
		while (iter.hasNext()) {

			tmpDiffData = iter.next();
			if (tmpDiffData.getOperation().equals("COMPANY_ADDED")) diffDataCollection.companyAdded.put(tmpDiffData.getCompany(), tmpDiffData.getDate());   
			else if (tmpDiffData.getOperation().equals("COMPANY_REMOVE")) diffDataCollection.companyRemoved.put(tmpDiffData.getCompany(), tmpDiffData.getDate());   
			else if (tmpDiffData.getOperation().equals("CHANGE")) diffDataCollection.productChanged.add(tmpDiffData);
			//else if (tmpDiffData.getOperation().equals("PRODUCT_ADDED")) diffDataCollection.productAdded.add(tmpDiffData);
			//else if (tmpDiffData.getOperation().equals("PRODUCT_REMOVE")) diffDataCollection.productRemoved.add(tmpDiffData);
		
		}
		
		iter = resultDiffData.iterator();
				
		while (iter.hasNext()) {

			tmpDiffData = iter.next();
			if (tmpDiffData.getOperation().equals("PRODUCT_ADDED")) {
				if (diffDataCollection.companyAdded.containsKey(tmpDiffData.getCompany())) {} // do nothing
				else diffDataCollection.productAdded.add(tmpDiffData);
			}
			else if (tmpDiffData.getOperation().equals("PRODUCT_REMOVE")) {
				if (diffDataCollection.companyRemoved.containsKey(tmpDiffData.getCompany())) {} // do nothing
				else diffDataCollection.productRemoved.add(tmpDiffData);
			}
		
		}
		
		
		//Set the date on the response
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		Date start = null;
		Date end = new Date();
		if (numberOfdays != null) {
			int days = new Integer(numberOfdays).intValue();
			cal.add(Calendar.DAY_OF_MONTH, days);
			start = cal.getTime();
			//start = new Date(end.getTime() - 1000 * 60 * 60 * 24 * new Integer(numberOfdays).intValue());
		} else if (numberOfMonths != null) {
			int months = new Integer(numberOfMonths).intValue();
			cal.add(Calendar.MONTH, months);
			start = cal.getTime();
		} else if (numberOfYears != null) {
			int years = new Integer(numberOfYears).intValue();
			cal.add(Calendar.YEAR, years);
			start = cal.getTime();
		} else if (fromDate != null) {
			start = fromDate;
		}
		
		diffDataCollection.setFromDate(start);
		return diffDataCollection;
		
	}
	
	
	
	

}
