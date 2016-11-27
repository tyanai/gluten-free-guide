package org.celiac.datatype;

public class IndexRow{

	private String product;
	private String category;
	private String company;
	private String glutenFree;
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getGlutenFree() {
		return glutenFree;
	}
	public void setGlutenFree(String glutenFree) {
		this.glutenFree = glutenFree;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
}
