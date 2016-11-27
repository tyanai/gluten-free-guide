package org.celiac.datatype;

import java.util.Date;

public class DiffData {

	
	private Date date = null;
	private String product = null;
	private String company = null;
	private String category = null;
	private String glutenFree = null;
	private String glutenFreeOld = "NOT-SET";
	private String operation = null;
	
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getGlutenFree() {
		return glutenFree;
	}
	public void setGlutenFree(String glutenFree) {
		this.glutenFree = glutenFree;
	}
	public String getGlutenFreeOld() {
		return glutenFreeOld;
	}
	public void setGlutenFreeOld(String glutenFreeOld) {
		this.glutenFreeOld = glutenFreeOld;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	
}
