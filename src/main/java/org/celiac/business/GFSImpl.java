package org.celiac.business;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

import org.celiac.datatype.IndexRow;
import org.celiac.util.PropertiesLoader;
import org.celiac.util.ResponseMaker;
import org.celiac.util.database.DBQueryFactory;
import org.celiac.util.database.GFQuery;

public abstract class GFSImpl {

	protected GFQuery query = DBQueryFactory.getDBHandler();
	protected Set<IndexRow> result = null;
	protected IndexRow indexRow = null;
	protected String lastCompanyFound = null;

	protected String treatByProductResult(Set<IndexRow> theQueryResult, String smsText,String product) {
		
		Set<IndexRow> result1 =  theQueryResult;
		
		// Check if empty:
		if (result1.isEmpty())
			return null;

		// Check how many results returned:
		if (result1.size() == 1) {
			// FOUND HIT!!!
			return ResponseMaker.HITResponse((IndexRow) (result1.iterator().next()));
		}
		
		

		// If you got here, result size is bigger then 1
		Iterator<IndexRow> itr = null;

		// check if the company has an exact match and not a 'like' match
		result1 = findMatchCompanies(result1, smsText,false);
		

		// check if all companies are the same:
		itr = result1.iterator();

		Set<String> listOfCompanies = new HashSet<String>();
		boolean foundManyCompanies = false;
		while (itr.hasNext()) {
			indexRow = (IndexRow) itr.next();
			if (lastCompanyFound == null) {
				lastCompanyFound = indexRow.getCompany();
			} else if (!lastCompanyFound.equals(indexRow.getCompany())) {
				// If you got here this is because we have different companies
				// for the same product
				foundManyCompanies = true;
			}
			lastCompanyFound = indexRow.getCompany();
			listOfCompanies.add(lastCompanyFound);
		}

		if (foundManyCompanies)
			return ResponseMaker.multipleCompaniesResponse(listOfCompanies, smsText);

		// If got down till here, this is the same company with multiple
		// product.
		// Before asking the user to choose the product again, lets try to see
		// maybe the number of total
		// product is less then X and they are all sharing the same category and recommandation
		// then return all of those.

		String tryShare = trySharedProduct(result1, null, result1.size(), lastCompanyFound,product);
		if (tryShare != null)
			return tryShare;

		return ResponseMaker.multipleProductResponse(lastCompanyFound, result1.size(), result1);
	}

	protected String treatMultipleResult(Set<IndexRow> theQueryResult, String smsText, String company) throws Exception{

		
		
		// Logger.print("FOUND MULTIPLE " + result.size());
		if (theQueryResult.isEmpty())
			return null;
		
		Set<IndexRow> result1 =  theQueryResult;
		
		
		result1 = findMatchCompanies(result1, company,DBQueryFactory.getDBHandler().isACompany(company));
		if (result1 == null) return null;
		
		
		// Check how many results returned (there are many categories):
		if (result1.size() == 1) {
			// FOUND MULTIPLE Match!!!
			indexRow = (IndexRow) (result1.iterator().next());
			return ResponseMaker.MULTIPLEResponse(indexRow);
		}
		

		//Tal Yanai - to check if the category and rec is the same then this will be treated as one multiple for the looked category
		// There are many categories:
		Set<String> listOfCategories = new HashSet<String>();
		Iterator<IndexRow> itr = null;
		itr = result1.iterator();
		//If the category is EXACT as the one in the MULTIPLE search the assumption is that it is exact 1
		//First find the Category
		String tmpCategory = null;
		try{
			tmpCategory = smsText.substring(smsText.lastIndexOf(".")+1, smsText.length()).trim();
		}catch (Exception e){
			
		}
		
		while (itr.hasNext()) {
			indexRow = itr.next();
			
			//If the category is EXACT as the one in the MULTIPLE search the assumption is that it is exact 1
			//System.out.println("---->  The category is: " + tmpCategory);
			if (indexRow.getCategory().trim().equals(tmpCategory)){
				return ResponseMaker.MULTIPLEResponse(indexRow);
			}
			
			listOfCategories.add(indexRow.getCategory());
		}
		return ResponseMaker.multipleMultipleResponse(listOfCategories);
	}

	protected String treatByCategoryResult(Set<IndexRow> theQueryResult, String smsText, String company) throws Exception{

		// Check if empty:
		if (theQueryResult.isEmpty())
			return null;

		Set<IndexRow> result1 =  theQueryResult;
		
		result1 = findMatchCompanies(result1, company,DBQueryFactory.getDBHandler().isACompany(company));
		if (result1 == null) return null;
		
		// Check how many results returned:
		if (result1.size() == 1) {
			IndexRow indRow = (IndexRow) (result1.iterator().next());
			
			if (indRow.getProduct().equals("MULTIPLE")){
				return ResponseMaker.MULTIPLEResponse(indRow);
			} else {
				return ResponseMaker.HITResponse((IndexRow) (result1.iterator().next()));
			}
		}

		// If you got here, result size is bigger then 1
		Iterator<IndexRow> itr = null;

		// check if the product has an exact match and not a 'like' match
		//itr = result1.iterator();
		/*
		while (itr.hasNext()) {
			indexRow = (IndexRow) itr.next();
			if (smsText.trim().equals(indexRow.getCategory())) {
				// In this case we will assume user had meant for this product
				exactMatchIndexRow = indexRow;
				exactMatchCounter++;
			}
		}

		if (exactMatchCounter == 1){
			IndexRow indRow = (IndexRow) (result1.iterator().next());
			if (indRow.getProduct().equals("MULTIPLE")){
				return ResponseMaker.MULTIPLEResponse(indRow);
			} else {
				return ResponseMaker.HITResponse(exactMatchIndexRow);
			}
		}
			*/

		// check if all companies are the same:
		itr = result1.iterator();
		Set<String> listOfCompanies = new HashSet<String>();
		boolean foundManyCompanies = false;
		while (itr.hasNext()) {
			indexRow = (IndexRow) itr.next();
			if (lastCompanyFound == null) {
				lastCompanyFound = indexRow.getCompany();
			} else if (!lastCompanyFound.equals(indexRow.getCompany())) {
				// If you got here this is because we have different companies
				// for the same product
				foundManyCompanies = true;
			}
			lastCompanyFound = indexRow.getCompany();
			listOfCompanies.add(lastCompanyFound);
		}

		if (foundManyCompanies)
			return ResponseMaker.multipleCompaniesByCategoryResponse(listOfCompanies, smsText.trim());

		return null;
	}

	/**
	 * 
	 * @return null in case no sharing, otherwise return only the SET of those
	 *         who share
	 */
	private Set<IndexRow> areAllProductsShareTheSameObservation(Set<IndexRow> input, String categoryInput, String productInput) {

		Set<IndexRow> output = new HashSet<IndexRow>();
		int maxProducts = Integer.parseInt(PropertiesLoader.getProperties("max.shared.product.per.company"));
		String product = null;
		String gluten_free = null;
		String category = null;
		String lastCategorySet = null;
		String lastGlutenFreeSet = null;
		

		Iterator<IndexRow> itr = input.iterator();
		IndexRow tempIdnexRow = null;
		int counter = 0;
		
		
		//First extract out an exact match - if one can be found
		while (itr.hasNext()) {
			tempIdnexRow = ((IndexRow) itr.next());
			product = tempIdnexRow.getProduct();
			gluten_free = tempIdnexRow.getGlutenFree();
			category = tempIdnexRow.getCategory();
			if (product.trim().equals(productInput)){
				//Add that exact item and remove it from the list so it will not be picked up later.
				output.add(tempIdnexRow);
				lastGlutenFreeSet = gluten_free;
				category = tempIdnexRow.getCategory();
				itr.remove();
			}
		}
		
		itr = input.iterator();
		while (itr.hasNext()) {
			tempIdnexRow = ((IndexRow) itr.next());
			product = tempIdnexRow.getProduct();
			gluten_free = tempIdnexRow.getGlutenFree();
			category = tempIdnexRow.getCategory();

			// MULTIPLE gets different treatment
			if (product.equals("MULTIPLE"))
				return null;

			if (lastGlutenFreeSet == null)
				lastGlutenFreeSet = gluten_free;
			else if (!lastGlutenFreeSet.equals(gluten_free))
				continue; // We can collect only one type

			// If counter bigger then max list, user will have to fine his
			// product name
			if (counter++ > maxProducts)
				continue;

			// Check the category, if supplied collect only from that type,
			// otherwise in case ther is
			// more then 1 category return null. User will have to re-define.
			if (!(categoryInput == null) && (category.indexOf(categoryInput) > -1)) {
				output.add(tempIdnexRow);
			} else {
				if (lastCategorySet == null)
					lastCategorySet = category;
				else if (!lastCategorySet.equals(category))
					return null; // More then 1 category is not allowed
				output.add(tempIdnexRow);

			}

		}

		return output;
	}

	protected String trySharedProduct(Set<IndexRow> resultInput, String category, int numberOfOriginalProduct, String company, String product) {
		Set<IndexRow> multipleChance = areAllProductsShareTheSameObservation(resultInput, category, product);
		if (multipleChance != null) {
			return ResponseMaker.multipleProductResponseThatShare(company, numberOfOriginalProduct, multipleChance);
		}
		return null;
	}
	
	protected Set<IndexRow> findMatchCompanies(Set<IndexRow> input,String company,boolean mustFindExactMatch){
		
		Iterator<IndexRow> itr = null;
		Set<IndexRow> output = input;
		
		// check if the company has an exact match and not a 'like' match
		itr = input.iterator();
		boolean foundExactMatchOnCompany = false;
		String companyToMatch = null;
		while (itr.hasNext()) {
			indexRow = (IndexRow) itr.next();
			if (company.trim().indexOf(indexRow.getCompany().trim()) > -1) {
				// In this case we will assume user had meant for this company
				foundExactMatchOnCompany = true;
				companyToMatch = indexRow.getCompany().trim();
			} else {
				if (foundExactMatchOnCompany) {
					//Founded an exact match company - Take out only those that match the company
					IndexRow forMatchComp = null;
					Iterator<IndexRow> it = input.iterator();
					output = new HashSet<IndexRow>();
					while (it.hasNext()){
						forMatchComp = (IndexRow)it.next();
						if ((companyToMatch != null) && forMatchComp.getCompany().trim().equals(companyToMatch.trim())){
							output.add(forMatchComp);
						}
					}
					break;
				}
			}
		}
		
		if (!foundExactMatchOnCompany && mustFindExactMatch) return null;
		
		return output;
	}
	
protected Set<IndexRow> findExactProductMatch(Set<IndexRow> input,String product){
		
		Iterator<IndexRow> itr = null;
		Set<IndexRow> output = new HashSet<IndexRow>();
		
		// check if the product has an exact match and not a 'like' match
		itr = input.iterator();
		boolean foundExactMatchOnProduct = false;
		boolean foundATrue = false;
		String tmpString = null;
		while (itr.hasNext()) {
			indexRow = (IndexRow) itr.next();
			tmpString = " " + product.trim() + " ";
			if (indexRow.getProduct().indexOf(tmpString) > -1) foundATrue = true;
			tmpString = " " + product.trim();
			if ((indexRow.getProduct().indexOf(tmpString) > -1) && ( (indexRow.getProduct().indexOf(tmpString) + tmpString.length()) == indexRow.getProduct().length() )) foundATrue = true;
			tmpString = product.trim() + " ";
			if (indexRow.getProduct().indexOf(tmpString) == 0) foundATrue = true;
			tmpString = product.trim();
			if (indexRow.getProduct().equals(tmpString)) foundATrue = true;
			
			if (foundATrue) {
				// In this case we will assume user had meant for this company
				foundExactMatchOnProduct = true;
				output.add(indexRow);
				foundATrue = false;
			} 
		}
		
		if (!foundExactMatchOnProduct) return input;
		
		return output;
	}

	
	
	
	public String searchMULTIPLE(String smsText) throws Exception {
		return null;
	}
	
	public Map<String,SortedSet<String>> searchForAllBasedOnCategory(String aCompany, String aCategory) throws Exception {
		return null;
	}

}
