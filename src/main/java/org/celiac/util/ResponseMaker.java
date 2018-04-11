package org.celiac.util;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.celiac.datatype.IndexRow;
import org.celiac.util.database.DBQueryFactory;


public class ResponseMaker {

	public static String HITResponse(IndexRow indexRow) {

		String companyDate = "";
		try{
			companyDate = DBQueryFactory.getDBHandler().getDateofCompany(indexRow.getCompany());
		} catch (Exception e){
			e.printStackTrace();
		}
		
		String template = TemplateReader.getTemplate("exact_match.template");
		String[] words = new String[3];
		//words[0] = indexRow.getCategory();
		words[0] = indexRow.getCompany() + " " + companyDate;
		words[1] = indexRow.getProduct();
		words[2] = TemplateReader.getHebrewMapWord(indexRow.getGlutenFree());
		//words[4] = TemplateReader.getHebrewMapWord("MEDUYAK");

		return OutputStringUtil.treatOutput(words, template);

	}

	public static String MULTIPLEResponse(IndexRow indexRow) {

		String companyDate = "";
		try{
			companyDate = DBQueryFactory.getDBHandler().getDateofCompany(indexRow.getCompany());
		} catch (Exception e){
			e.printStackTrace();
		}
		
		String template = TemplateReader.getTemplate("multiple_match.template");
		String[] words = new String[3];
		//words[0] = indexRow.getCategory();
		words[0] = indexRow.getCompany() + " " + companyDate;
		words[1] = indexRow.getCategory();
		words[2] = TemplateReader.getHebrewMapWord(indexRow.getGlutenFree());
		
		//Find iligale products
		//gfNOByCompanyAndCategory
		if (!indexRow.getGlutenFree().equals("NO")) {
			try{
			String whatIsNotAllowed = DBQueryFactory.getDBHandler().gfNOByCompanyAndCategory(indexRow.getCompany(), indexRow.getCategory());
			
			if (whatIsNotAllowed != null) words[2] = words[2] + "\n" + TemplateReader.getHebrewMapWord("RESHIMA") + "\n" + whatIsNotAllowed;
			}catch (Exception e){
				e.printStackTrace();
				Logger.print("FATAL: Fail to find what products are NO for MULTIPLE");
			}
		}

		return OutputStringUtil.treatOutput(words, template);

	}

	public static String multipleCompaniesResponse(Set<String> companyList, String product) {

		// Logger.print("Company list size: " + companyList.size());
		String template = TemplateReader.getTemplate("many_companies.template");
		String[] words = new String[3];
		words[0] = product + ".";
		words[1] = null;
		Iterator<String> itr = companyList.iterator();
		int counter = 0 ;
		String addsOn = "";
		while (itr.hasNext()) {
			if (words[1] == null)
				words[1] = itr.next();
			else
				words[1] = words[1] + "," + (String) itr.next();
			
			counter++;
			int maxCompanyPerProduct = new Integer(PropertiesLoader.getProperties("max.shared.company.per.search")).intValue();
			if (counter > maxCompanyPerProduct) {
				addsOn = "\n" + TemplateReader.getHebrewMapWord("MAYBE_MORE_COMPANY");
				break;
			}
		}
		
		words[2] = addsOn;

		return OutputStringUtil.treatOutput(words, template);

	}

	public static String multipleCompaniesByCategoryResponse(Set<String> companyList, String category) {

		// Logger.print("Company list size: " + companyList.size());
		String template = TemplateReader.getTemplate("many_companies_by_category.template");
		String[] words = new String[2];
		words[0] = category;
		words[1] = null;
		Iterator<String> itr = companyList.iterator();
		while (itr.hasNext()) {
			if (words[1] == null)
				words[1] = itr.next();
			else
				words[1] = words[1] + "," + (String) itr.next();
		}

		return OutputStringUtil.treatOutput(words, template);

	}

	public static String multipleMultipleResponse(Set<String> categoryList) {

		// Logger.print("Category list size: " + categoryList.size());
		String template = TemplateReader.getTemplate("many_multiple.template");
		String[] words = new String[2];
		words[0] = "";
		words[1] = null;
		Iterator<String> itr = categoryList.iterator();
		while (itr.hasNext()) {
			if (words[1] == null)
				words[1] = itr.next();
			else
				words[1] = words[1] + "," + (String) itr.next();
		}

		return OutputStringUtil.treatOutput(words, template);

	}

	public static String multipleProductResponse(String company, int numOfProducts, Set<IndexRow> productList) {

		String companyDate = "";
		try{
			companyDate = DBQueryFactory.getDBHandler().getDateofCompany(company);
		} catch (Exception e){
			e.printStackTrace();
		}
		
		// check if not too many products are to be shown:
		int maxProduct = Integer.parseInt(PropertiesLoader.getProperties("max.non.shared.product").trim());

		String template = null;
		if (numOfProducts > maxProduct)
			template = TemplateReader.getTemplate("too_many_products.template");
		else
			template = TemplateReader.getTemplate("many_products.template");

		String[] words = new String[3];
		words[0] = "" + numOfProducts;
		words[1] = company + " " + companyDate;
		words[2] = null;
		Iterator<IndexRow> itr = productList.iterator();
		if ((numOfProducts > maxProduct)) {
			Set<String> tmp = new HashSet<String>();
			while (itr.hasNext()) tmp.add(((IndexRow)itr.next()).getCategory());
			Iterator<String> categoryIter = tmp.iterator();
			while (categoryIter.hasNext()) {
				if (words[2] == null)
					words[2] = ((String) categoryIter.next());
				else
					words[2] = words[2] + "," + ((String) categoryIter.next());
			}
		} else {
			while (itr.hasNext()) {
				if (words[2] == null)
					words[2] = ((IndexRow) itr.next()).getProduct();
				else
					words[2] = words[2] + "," + ((IndexRow) itr.next()).getProduct();
			}
		}
		return OutputStringUtil.treatOutput(words, template);

	}

	public static String multipleProductResponseThatShare(String company, int numOfProducts, Set<IndexRow> productList) {

		String companyDate = "";
		try{
			companyDate = DBQueryFactory.getDBHandler().getDateofCompany(company);
		} catch (Exception e){
			e.printStackTrace();
		}
		
		IndexRow tmpIndex = null;
		String template = TemplateReader.getTemplate("exact_match_many_products.template");
		String[] words = new String[3];
		// words[0] = "" + numOfProducts;
		words[0] = company + " " + companyDate;
		words[1] = null;
		Iterator<IndexRow> itr = productList.iterator();
		// productList.size()
		String theProduct = null;
		while (itr.hasNext()) {
			tmpIndex = ((IndexRow) itr.next());
			//words[0] = tmpIndex.getCategory();
			words[2] = TemplateReader.getHebrewMapWord(tmpIndex.getGlutenFree());
			//words[4] = TemplateReader.getHebrewMapWord("MEDUYAK");
			theProduct = tmpIndex.getProduct();
			// Check if product is MULTIPLE
			if (theProduct.equals("MULTIPLE")) {
				theProduct = TemplateReader.getHebrewMapWord("KLAL") + " " + tmpIndex.getCategory();
			}

			if (words[1] == null)
				words[1] = theProduct;
			else
				words[1] = words[1] + "," + theProduct;
		}

		// Maybe there are more products;
		if (productList.size() < numOfProducts)
			words[2] = words[2] + "\n" + TemplateReader.getHebrewMapWord("MAYBE_MORE_PRODUCT");

		return OutputStringUtil.treatOutput(words, template);

	}

	public static String oneCompanyByCategoryResponse(String company, int numOfProducts, String category, Set<IndexRow> productList) {

		//String companyDate = "";
		try{
			//companyDate = DBQueryFactory.getDBHandler().getDateofCompany(company);
		} catch (Exception e){
			e.printStackTrace();
		}
		
		String template = TemplateReader.getTemplate("one_company_by_category.template");
		String[] words = new String[4];
		words[0] = "" + numOfProducts;
		words[1] = category;
		words[2] = company;
		words[3] = null;
		Iterator<IndexRow> itr = productList.iterator();
		while (itr.hasNext()) {
			if (words[3] == null)
				words[3] = ((IndexRow) itr.next()).getProduct();
			else
				words[3] = words[3] + "," + ((IndexRow) itr.next()).getProduct();
		}
		return OutputStringUtil.treatOutput(words, template);
	}

	public static String oneCompanyByCategoryAndCompanyResponse(String company, int numOfProducts, String category,
			Set<IndexRow> productList) {

		String companyDate = "";
		try{
			companyDate = DBQueryFactory.getDBHandler().getDateofCompany(company);
		} catch (Exception e){
			e.printStackTrace();
		}
		
		String template = TemplateReader.getTemplate("one_company_by_category_and_company.template");
		String[] words = new String[4];
		words[0] = "" + numOfProducts;
		words[1] = category;
		words[2] = company + " " + companyDate;
		words[3] = null;
		Iterator<IndexRow> itr = productList.iterator();
		while (itr.hasNext()) {
			if (words[3] == null)
				words[3] = ((IndexRow) itr.next()).getProduct();
			else
				words[3] = words[3] + "," + ((IndexRow) itr.next()).getProduct();
		}
		return OutputStringUtil.treatOutput(words, template);

	}

	public static String noMatchFoundResponse(String _product, String _company, String _category) {
		String product = _product;
		String company = _company;
		String category = _category;
		if (product == null)
			product = TemplateReader.getHebrewMapWord("NOT_SUPPLIED");
		if (company == null)
			company = TemplateReader.getHebrewMapWord("NOT_SUPPLIED");
		if (category == null)
			category = TemplateReader.getHebrewMapWord("NOT_SUPPLIED");

		String template = TemplateReader.getTemplate("no_match_found.template");
		String[] words = new String[4];
		words[0] = "";
		words[1] = product;
		words[2] = company;
		words[3] = category;
		return OutputStringUtil.treatOutput(words, template);

	}
	
	public static String needUserAcknowledgment() {

		String template = TemplateReader.getTemplate("first_time.template");
		String[] words = new String[1];
		words[0] = "";
		

		return OutputStringUtil.treatOutput(words, template);

	}
	
	public static String userAcknowledged() {

		String template = TemplateReader.getTemplate("second_time.template");
		String[] words = new String[2];
		words[0] = "";
		words[1] = "";
		

		return OutputStringUtil.treatOutput(words, template);

	}
	
	public static String getWelcomeLetter(String user, String password, String firstName, String lastName) {

		
		String site = PropertiesLoader.getProperties("gf.site");
		String template = TemplateReader.getTemplate("welcome_letter.template");
		String[] words = new String[5];
		words[0] = firstName + " " + lastName;
		words[1] = site;
		words[2] = user;
		words[3] = password;
		//words[4] = site + "/gfa?user=" + user + "-" + password;
                words[4] = site + "/app";
		
		return OutputStringUtil.treatOutput(words, template);
	}


}
