package org.celiac.business;

import java.util.StringTokenizer;

public class GFSByNameAndCompanyImpl extends GFSImpl  implements GFS{
	
	public String search(String smsText) throws Exception {
		
		StringTokenizer strToken = new StringTokenizer(smsText,".");
		String product = strToken.nextToken().trim();
		String compnay = strToken.nextToken().trim();
		
		
		result = query.productByNameAndCompany(product,compnay);
			
		
		return treatByProductResult(result, smsText,product);
		
		
		
	}
	
@Override
public String searchMULTIPLE(String smsText) throws Exception {
		
		StringTokenizer strToken = new StringTokenizer(smsText,".");
		strToken.nextToken();
		String compnay = strToken.nextToken().trim();
		

			//look for MULTIPLE
			result = query.productByNameAndCompany("MULTIPLE",compnay);
			return treatMultipleResult(result, smsText,compnay.trim());
			
		
		
		
	}


}
