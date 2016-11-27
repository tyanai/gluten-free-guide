package org.celiac.business;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedSet;
import java.util.StringTokenizer;
import java.util.TreeSet;

import org.celiac.datatype.IndexRow;
import org.celiac.util.ResponseMaker;
import org.celiac.util.database.DBQueryFactory;

public class GFSByCategoryAndCompanyImpl extends GFSImpl implements GFS {

	public String search(String smsText) throws Exception {

		StringTokenizer strToken = new StringTokenizer(smsText, ".");
		String category = null;
		String company = null;

		category = strToken.nextToken().trim();
		company = strToken.nextToken().trim();

		result = DBQueryFactory.getDBHandler().companyByCategoryAndCompany(category, company);

		
		
		String output = treatByCategoryResult(result, smsText, company);
		if (output != null)
			return output;

		// If got down till here, this is the same company with multiple
		// product.
		// Before asking the user to choose the product again, lets try to see
		// maybe the number of total
		// product is less then X and they are all sharing the same category
		// then return all of those.
	
		
		
		
		if (result.size() > 0) {
			String rCompany = DBQueryFactory.getDBHandler().likeACompany(company);
			String tryShare = trySharedProduct(result, category, result.size(), rCompany,"NO_PRODUCT");
			if (tryShare != null)
				return tryShare;

			return ResponseMaker.oneCompanyByCategoryAndCompanyResponse(rCompany, result.size(), category, result);
		}
		return null;

	}
	
	public Map<String,SortedSet<String>> searchForAllBasedOnCategory(String aCompany, String aCategory) throws Exception {

		String category = aCategory;
		String company = aCompany;

		

		result = DBQueryFactory.getDBHandler().companyByCategoryAndCompany(category, company);
		
		//Before doing the above lets check if this is a web style or a cell style
		Map<String,SortedSet<String>> reccomendationCodes = new HashMap<String,SortedSet<String>>();
		Iterator<IndexRow> itr = null;

		itr = result.iterator();
		SortedSet<String> listOfProduct = null;
		
		String product = null;
		String recCode = null;
		String tempCompany = null;
		while (itr.hasNext()) {
			indexRow = (IndexRow) itr.next();
			product = indexRow.getProduct();
			recCode = indexRow.getGlutenFree();
			tempCompany = indexRow.getCompany();  // New...
			
			if (tempCompany.trim().length() != company.trim().length()) continue;
			
			listOfProduct = (SortedSet<String>)reccomendationCodes.get(recCode);
			if (listOfProduct == null) {
				listOfProduct = new TreeSet<String>();
			} 
			listOfProduct.add(product);
			reccomendationCodes.put(recCode, listOfProduct);
		}
		
		
		
		
		return reccomendationCodes;

	}


}
