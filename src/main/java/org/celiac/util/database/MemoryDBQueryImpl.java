package org.celiac.util.database;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

import org.celiac.datatype.DiffData;
import org.celiac.datatype.GFUser;
import org.celiac.datatype.IndexRow;
import org.celiac.datatype.JobDetails;
import org.celiac.datatype.StatisticsData;
import org.celiac.util.Logger;

public class MemoryDBQueryImpl implements GFQuery {

	List<IndexRow> db = null;

	public Set<IndexRow> productByName(String text) throws Exception {

		db = DBInMemory.getDB();

		Set<IndexRow> nameList = new HashSet<IndexRow>();

		Iterator<IndexRow> iter = db.iterator();

		while (iter.hasNext()) {
			IndexRow indexRow = iter.next();
			if (indexRow.getProduct().trim().indexOf(text.trim()) > -1)
				nameList.add(copyIndexRow(indexRow));
		}

		return nameList;

	}
	
	public Set<IndexRow> productByCompany(String text,boolean checkForMultiple) throws Exception {

		db = DBInMemory.getDB();

		Set<IndexRow> nameList = new HashSet<IndexRow>();

		Iterator<IndexRow> iter = db.iterator();

		while (iter.hasNext()) {
			IndexRow indexRow = iter.next();
			if (indexRow.getCompany().trim().indexOf(text.trim()) > -1){
				if (indexRow.getProduct().equals("MULTIPLE")){
					if (checkForMultiple) nameList.add(copyIndexRow(indexRow));
					else continue;
				}else nameList.add(copyIndexRow(indexRow));
			}
				
		}

		return nameList;

	}
	
	public Set<String> listOfCompany() throws Exception {

		db = DBInMemory.getDB();

		Set<String> nameList = new HashSet<String>();

		Iterator<IndexRow> iter = db.iterator();

		while (iter.hasNext()) {
						
			nameList.add(iter.next().getCompany());
			
		}

		return nameList;

	}
	
	public Set<IndexRow> containGF(String category) throws Exception {
		return null;

}
	
	//Get all the product that are not for eating from this category
	public String gfNOByCompanyAndCategory(String company,String category) throws Exception {

		db = DBInMemory.getDB();
		String output = null;

		Iterator<IndexRow> iter = db.iterator();

		while (iter.hasNext()) {
			IndexRow indexRow = iter.next();
			if ((indexRow.getCompany().trim().equals(company)) && ((indexRow.getCategory().trim().equals(category)))){
				if (indexRow.getProduct().equals("MULTIPLE") || !indexRow.getGlutenFree().equals("NO")){
					//do nothing				
				}else {
					if (output == null) output = indexRow.getProduct();
					else output = output + "," + indexRow.getProduct();
				}
			}
				
		}
		
		

		return output;

	}
	
	public boolean isACompany(String company) throws Exception {

		db = DBInMemory.getDB();

		Iterator<IndexRow> iter = db.iterator();

		while (iter.hasNext()) {
			IndexRow indexRow = iter.next();
			if ( indexRow.getCompany().trim().equals(company.trim()) ){
				return true;
			}
		}

		return false;

	}
	
	public String likeACompany(String company) throws Exception {

		db = DBInMemory.getDB();

		Iterator<IndexRow> iter = db.iterator();

		while (iter.hasNext()) {
			IndexRow indexRow = iter.next();
			if ( indexRow.getCompany().trim().indexOf(company.trim()) > -1 ){
				return indexRow.getCompany();
			}
		}

		return company;

	}

	public Set<IndexRow> productByNameAndCompany(String aProduct, String aCompany)
			throws Exception {

		db = DBInMemory.getDB();
		//Logger.print("DB Size: " + db.size());
		Set<IndexRow> nameList = new HashSet<IndexRow>();

		Iterator<IndexRow> iter = db.iterator();
		IndexRow indexRow = null;
		while (iter.hasNext()) {
			indexRow = iter.next();
			if ((indexRow.getProduct().trim().indexOf(aProduct.trim()) > -1)
					&& (indexRow.getCompany().trim().indexOf(aCompany.trim()) > -1))
				nameList.add(copyIndexRow(indexRow));

		}

		return nameList;

	}

	public Set<IndexRow> productByNameAndCompanyAndCategory(String aProduct,
			String aCompany, String aCategory) throws Exception {

		db = DBInMemory.getDB();

		Set<IndexRow> nameList = new HashSet<IndexRow>();

		Iterator<IndexRow> iter = db.iterator();

		while (iter.hasNext()) {
			IndexRow indexRow = iter.next();
			if ((indexRow.getProduct().trim().indexOf(aProduct.trim()) > -1)
					&& (indexRow.getCompany().trim().indexOf(aCompany.trim()) > -1)
					&& (indexRow.getCategory().trim().indexOf(aCategory.trim()) > -1))
				nameList.add(copyIndexRow(indexRow));
		}

		return nameList;

	}

	public Set<IndexRow> companyByCategory(String aCategory) throws Exception {

		db = DBInMemory.getDB();

		Set<IndexRow> nameList = new HashSet<IndexRow>();

		Iterator<IndexRow> iter = db.iterator();

		while (iter.hasNext()) {
			IndexRow indexRow = (IndexRow) iter.next();
			if (indexRow.getCategory().trim().indexOf(aCategory.trim()) > -1)
				nameList.add(copyIndexRow(indexRow));
		}

		return nameList;

	}

	public Set<IndexRow> companyByCategoryAndCompany(String aCategory, String aCompany)
			throws Exception {

		db = DBInMemory.getDB();

		Set<IndexRow> nameList = new HashSet<IndexRow>();

		Iterator<IndexRow> iter = db.iterator();

		while (iter.hasNext()) {
			IndexRow indexRow = iter.next();
			if ((indexRow.getCategory().trim().indexOf(aCategory.trim()) > -1)
					&& (indexRow.getCompany().trim().indexOf(aCompany.trim()) > -1))
				nameList.add(copyIndexRow(indexRow));
		}

		return nameList;

	}

	private IndexRow copyIndexRow(IndexRow input) {

		IndexRow output = new IndexRow();

		output = new IndexRow();
		output.setProduct(input.getProduct().trim());
		output.setCompany(input.getCompany().trim());
		output.setCategory(input.getCategory().trim());
		output.setGlutenFree(input.getGlutenFree().trim());

		return output;
	}
	
	public GFUser getUserByUserID(String userId) throws Exception {
		List<GFUser> userDb = DBInMemory.getUsersDB();

		GFUser gfUser = null;


		try {
			
			Iterator<GFUser> iter = userDb.iterator();

			while (iter.hasNext()) {
				gfUser = iter.next();
				if (gfUser.getUSER_ID().equals(userId)) return gfUser;
			}
			
			
		} catch (Exception e) {
			Logger.print("Exception: " + e.getMessage());
			e.printStackTrace();
		} 

		return null;
		
	}
	
	public GFUser getUser(String cellNum) throws Exception {
		
		String cell = cellNum;
		if (cell.startsWith("972")) cell = "0" + cell.substring(3);
		else cell = "0" + cell;
		
		List<GFUser> userDb = DBInMemory.getUsersDB();

		GFUser gfUser = null;


		try {
			
			Iterator<GFUser> iter = userDb.iterator();

			while (iter.hasNext()) {
				gfUser = iter.next();
				if (gfUser.getCELL_NUM().equals(cell)) return gfUser;
			}
			
			
		} catch (Exception e) {
			Logger.print("Exception: " + e.getMessage());
			e.printStackTrace();
		} 

		return null;
	}
	
	public void insertUser(GFUser theUser) throws Exception {
		//To Do
	}
	public void updateUser(GFUser theUser) throws Exception{
		//To Do
	}
	
	public String getDateofCompany(String company) throws Exception {
		return null;
		//To do
	}
	
	public StatisticsData[] getStatisticsData(Date start, Date end) throws Exception {
		return null;
	}
	public void updateStatsticsData(StatisticsData data)  throws Exception {
		//To do
	}
	
	public SortedSet<String> getCompanyList() throws Exception{
		//To do
		return null;
	}
	public SortedSet<String> getProductByCompanyList(String text) throws Exception {
		//To do
		return null;
	}
	public SortedSet<String> getCategoryByProductAndCompanyList(String product, String company) throws Exception{
		//To do
		return null;
	}
	public SortedSet<String> getCategoryList() throws Exception {
		//To do
		return null;
	}
	public SortedSet<String> getCompanyByCategoryList(String text) throws Exception {
		//To do
		return null;
	}
	public SortedSet<String> getProductByCategoryAndCompanyList(String category, String company) throws Exception{
		//To do
		return null;
	}
	
	public GFUser getUserByGeneric(String userId, String type) throws Exception {
		return null;
	}
	
	public void insertFullUser(GFUser theUser) throws Exception{
		//To Do
	}
	
	public void updateBasicUser(GFUser gfUser) throws Exception {
		//To Do
	}
	
	public Map<String, String> getCodeDescriptionList() throws Exception {
		return null;
	}
	
	public void deleteUser(GFUser theUser) throws Exception {
		//To Do
	}
	
	public Set<GFUser> getAllUserDetails() throws Exception{
		return null;
	}
	
	public Set<IndexRow> notContainGF(String category) throws Exception {
		return null;
	}
	public SortedSet<String> getCategoryListThatContainsGluten() throws Exception {
		return null;
	}
	public SortedSet<String> getCategoryListThatDoNotContainsGluten() throws Exception {
		return null;
	}
	
	public void insertImage(String product, String company, String category, byte[] image) throws Exception {
		
	}
	
	public byte[] getSmallImage(String product, String company, String category) throws Exception {
		return null;
	}
	
	public byte[] getOrigImage(String product, String company, String category) throws Exception {
		return null;
	}

	public void updateImage(String product, String company, String category, byte[] image) throws Exception {
	
	}

	public void deleteImage(String product, String company, String category) throws Exception {
	
	}
	
	public void deleteJob(String jobName) throws Exception{}
	public void updateJob(JobDetails details) throws Exception {}
	public void addJob(JobDetails details) throws Exception {}
	public Set<JobDetails> getJobs() throws Exception{ return null;}
	public JobDetails getJob(String jobName) throws Exception { return null;}
	public Set<GFUser> getAllMembersExpirationsWhoRegisterToGFGuide() throws Exception{ return null;}
	public Set<String> getProducts() throws Exception { return null;}
	public Set<DiffData> getDiff(String query, String operation) throws Exception { return null;}
	public void updateDiff(Set<DiffData> gfUser) throws Exception {}
	public Set<DiffData> getDiffByDates(Date fromDate, String numberOfdays,String numberOfMonths, String numberOfYears) throws Exception {return null;}
	public GFUser getUserByEmail(String input) throws Exception {return null;}
	

}
