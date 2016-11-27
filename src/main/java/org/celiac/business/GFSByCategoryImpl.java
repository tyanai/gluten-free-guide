package org.celiac.business;

import org.celiac.util.ResponseMaker;
import org.celiac.util.database.DBQueryFactory;

public class GFSByCategoryImpl extends GFSImpl implements GFS {

	public String search(String smsText) throws Exception {

		result = DBQueryFactory.getDBHandler().companyByCategory(smsText);

		String output = treatByCategoryResult(result, smsText, "DONT KNOW THE COMPANY NAME");
		if (output != null)
			return output;
		// If got down till here, this is the same company with multiple
		// product.
		// If got down till here, this is the same company with multiple
		// product.
		// Before asking the user to choose the product again, lets try to see
		// maybe the number of total
		// product is less then X and they are all sharing the same category
		// then return all of those.
		if (result.size() > 0) {
			String tryShare = trySharedProduct(result, smsText, result.size(), lastCompanyFound,"NO_PRODUCT");
			if (tryShare != null)
				return tryShare;

			return ResponseMaker.oneCompanyByCategoryResponse(lastCompanyFound, result.size(), smsText.trim(), result);
		}
		return null;
	}

}
