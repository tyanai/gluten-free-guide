package org.celiac.business;


public class GFSByNameImpl extends GFSImpl implements GFS {

	public String search(String smsText) throws Exception {

		result = query.productByName(smsText);
		
		//Check for exact and not part of
		result = this.findExactProductMatch(result, smsText);

		return treatByProductResult(result, smsText,smsText);

	}
	
	

}
