package org.celiac.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;


import org.celiac.util.database.DBQueryFactory;
import org.json.simple.JSONValue;

public class AutoCompleteSearch {

	private static ArrayList<String> companies = null;
	private static ArrayList<String> products = null;
	
	
	public AutoCompleteSearch() throws Exception {
		loadAll();
	}
	
	/*
	public List<String> getCompanyData(String query) {
		
		
		String company = null;
		query = query.toLowerCase();
		List<String> matched = new ArrayList<String>();
		
		//if (query.length() < 2) return matched;
		
		for(int i=0; i<companies.size(); i++) {
			company = companies.get(i).toLowerCase();
			if(company.indexOf(query) > 0) {
				matched.add(companies.get(i));
			}
		}
		return matched;
	}
	*/
 public String getCompanyData(String query) {
		
		Map<String,String> m1 = null;
		List<Map<String,String>>  l1 = new LinkedList<Map<String,String>>();
		
		String company = null;
		//System.out.println("The query is as follow: " + query);
		
		query = query.toLowerCase();
		
		
		for(int i=0; i<companies.size(); i++) {
			company = companies.get(i).toLowerCase();
			if(company.indexOf(query) > -1) {
				m1 = new LinkedHashMap<String,String>();
				m1.put("id",company);
				m1.put("label",company);
				m1.put("value",company);
				l1.add(m1);
			}
		}
		
		String jsonString = JSONValue.toJSONString(l1);
		//System.out.println(jsonString);
		return jsonString;
	
		
	}
	
	public String getProductData(String query) {
		
		Map<String,String> m1 = null;
		List<Map<String,String>>  l1 = new LinkedList<Map<String,String>>();
		
		String product = null;
		//System.out.println("The query is as follow: " + query);
		
		query = query.toLowerCase();
		
		
		for(int i=0; i<products.size(); i++) {
			product = products.get(i).toLowerCase();
			if(product.indexOf(query) > -1) {
				m1 = new LinkedHashMap<String,String>();
				m1.put("id",product);
				m1.put("label",product);
				m1.put("value",product);
				l1.add(m1);
			}
		}
		
		String jsonString = JSONValue.toJSONString(l1);
		//System.out.println(jsonString);
		return jsonString;
	
		
	}
	
public Set<String> search(String product, String manufacture) {
		
		Set<String>  l1 = new TreeSet<String>();
		
		String temp = null;
		
		if (product != null) {
			product = product.toLowerCase();
			for(int i=0; i<products.size(); i++) {
				temp = products.get(i).toLowerCase();
				if(temp.indexOf(product) > -1) {
					l1.add(temp);
				}
			}
		} else if (manufacture != null) {
			manufacture = manufacture.toLowerCase();
			for(int i=0; i<companies.size(); i++) {
				temp = companies.get(i).toLowerCase();
				if(temp.indexOf(manufacture) > -1) {
					l1.add(temp);
				}
			}
		}	
		
		return l1;
	
		
	}
	
	
	
	
	private static void loadAll() throws Exception {
		
		if (companies != null) return;
		
		Set<String> temp = null;
		Iterator<String> iter = null;
		
		
		// Load companies
		companies = new ArrayList<String>();
		temp = DBQueryFactory.getDBHandler().getCompanyList();
		iter = temp.iterator();

		while (iter.hasNext()) {
			companies.add((String)iter.next());
		}
		
		
		// Load products
		products = new ArrayList<String>();
		temp = DBQueryFactory.getDBHandler().getProducts();
		iter = temp.iterator();

		while (iter.hasNext()) {
			products.add((String)iter.next());
		}
		
	}
	
	public static void  reload() throws Exception{
		companies = null;
		products = null;
		loadAll();
		
	}
	
	
	
}
