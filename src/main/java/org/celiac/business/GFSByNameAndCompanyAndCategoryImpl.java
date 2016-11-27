package org.celiac.business;

import java.util.StringTokenizer;

public class GFSByNameAndCompanyAndCategoryImpl extends GFSImpl implements GFS{
	
	public String search(String smsText) throws Exception {
		
		StringTokenizer strToken = new StringTokenizer(smsText,".");
		String product = strToken.nextToken().trim();
		String compnay = strToken.nextToken().trim();
		String category = strToken.nextToken().trim();
		
		
		
		result = query.productByNameAndCompanyAndCategory(product,compnay,category);
		
		return treatByProductResult(result, smsText,product);
		
		
		
		
	}
	
@Override
public String searchMULTIPLE(String smsText) throws Exception {
		
		StringTokenizer strToken = new StringTokenizer(smsText,".");
		strToken.nextToken();
		String compnay = strToken.nextToken().trim();
		String category = strToken.nextToken().trim();
		
		
			result = query.productByNameAndCompanyAndCategory("MULTIPLE",compnay,category);
			return treatMultipleResult(result, smsText, compnay );
			
		
		
		
		
	}

}
