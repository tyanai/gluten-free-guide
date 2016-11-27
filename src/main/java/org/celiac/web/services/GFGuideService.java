package org.celiac.web.services;

import org.celiac.api.GlutenFreeAPI;

public class GFGuideService {

	GlutenFreeAPI gfAPI = new GlutenFreeAPI();
    
	public String search(String _sms, String cell, boolean validateCellNum) {
        return gfAPI.search(_sms, cell, validateCellNum);
    }

    
	
}
