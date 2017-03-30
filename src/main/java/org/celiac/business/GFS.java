package org.celiac.business;

import java.util.Map;
import java.util.SortedSet;

public interface GFS {

	String search(String smsText) throws  Exception;
	String searchMULTIPLE(String smsText) throws Exception;
	Map<String,SortedSet<String>> searchForAllBasedOnCategory(String aCompany, String aCategory) throws Exception ;
	
}
