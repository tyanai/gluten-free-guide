package org.celiac.business;



import org.celiac.util.database.DBQueryFactory;

public class GFSByCompanyImpl extends GFSImpl implements GFS {

	public String search(String company) throws Exception {

		result = DBQueryFactory.getDBHandler().productByCompany(company,false);
		
		return treatByProductResult(result, company,"NO_PRODUCT");
		
		/*
		String tryShare = trySharedProduct(result, null, result.size(), );
		if (tryShare != null) return tryShare;
		
		String output =  treatByCategoryResult(result, company);
		if (output != null) return output;
		// If got down till here, this is the same company with multiple
		// product.
		return ResponseMaker.oneCompanyByCategoryResponse(lastCompanyFound,
				result.size(), company.trim(),result);
				*/
	}
	
	@Override
	public String searchMULTIPLE(String smsText) throws Exception {
			
			String compnay = smsText;
			

				//look for MULTIPLE
				result = query.productByNameAndCompany("MULTIPLE",compnay);
				return treatMultipleResult(result, smsText,compnay.trim());
				
			
			
			
		}


}
