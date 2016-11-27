package org.celiac.util.database;

import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

import org.celiac.datatype.DiffData;
import org.celiac.datatype.GFUser;
import org.celiac.datatype.IndexRow;
import org.celiac.datatype.JobDetails;
import org.celiac.datatype.StatisticsData;

public interface GFQuery {

	Set<IndexRow> productByName(String text) throws Exception;
	Set<IndexRow> productByCompany(String text,boolean checkForMultiple) throws Exception;
	Set<IndexRow> productByNameAndCompany(String aProduct, String aCompany) throws Exception;
	Set<IndexRow> productByNameAndCompanyAndCategory(String aProduct, String aCompany, String aCategory) throws Exception;
	Set<IndexRow> companyByCategory(String aCategory) throws Exception;
	Set<IndexRow> companyByCategoryAndCompany(String aCategory, String aCompany) throws Exception;
	boolean isACompany(String company) throws Exception;
	String likeACompany(String company) throws Exception;
	GFUser getUser(String cellNum) throws Exception;
	GFUser getUserByUserID(String userId) throws Exception;
	String gfNOByCompanyAndCategory(String company,String category) throws Exception ;
	Set<String> listOfCompany() throws Exception;
	void insertUser(GFUser theUser) throws Exception;
	void insertFullUser(GFUser theUser) throws Exception;
	void updateUser(GFUser theUser) throws Exception;
	void updateBasicUser(GFUser gfUser) throws Exception;
	String getDateofCompany(String company) throws Exception ;
	StatisticsData[] getStatisticsData(Date start, Date end) throws Exception ;
	void updateStatsticsData(StatisticsData data)  throws Exception ;
	GFUser getUserByGeneric(String userId, String type) throws Exception;
	Map<String, String> getCodeDescriptionList() throws Exception;
	Set<String> getCompanyList() throws Exception;
	Set<String> getProductByCompanyList(String text) throws Exception ;
	Set<String> getProducts() throws Exception ;
	Set<String> getCategoryByProductAndCompanyList(String product, String company) throws Exception;
	Set<String> getCategoryList() throws Exception ;
	Set<String> getCompanyByCategoryList(String text) throws Exception ;
	Set<String> getProductByCategoryAndCompanyList(String category, String company) throws Exception;
	void deleteUser(GFUser theUser) throws Exception ;
	Set<GFUser> getAllUserDetails() throws Exception ;
	Set<IndexRow> containGF(String category) throws Exception;
	Set<IndexRow> notContainGF(String category) throws Exception;
	SortedSet<String> getCategoryListThatContainsGluten() throws Exception;
    SortedSet<String> getCategoryListThatDoNotContainsGluten() throws Exception;
    public void insertImage(String product, String company, String category, byte[] image) throws Exception;
    public byte[] getSmallImage(String product, String company, String category) throws Exception;
	public byte[] getOrigImage(String product, String company, String category) throws Exception;
	public void updateImage(String product, String company, String category, byte[] image) throws Exception ;
	public void deleteImage(String product, String company, String category) throws Exception ;
	public void deleteJob(String jobName) throws Exception;
	public void updateJob(JobDetails details) throws Exception ;
	public void addJob(JobDetails details) throws Exception ;
	public Set<JobDetails> getJobs() throws Exception;
	public JobDetails getJob(String jobName) throws Exception;
	public Set<GFUser> getAllMembersExpirationsWhoRegisterToGFGuide() throws Exception;
	public Set<DiffData> getDiff(String query, String operation) throws Exception;
	public void updateDiff(Set<DiffData> gfUser) throws Exception ;
	public Set<DiffData> getDiffByDates(Date fromDate, String numberOfdays, String numberOfMonths, String numberOfYears) throws Exception;
	public GFUser getUserByEmail(String input) throws Exception;
}
