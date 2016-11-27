package org.celiac.datatype;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class QueryDao {

	private String product;
	private String category;
	private String manufactor;

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getManufactor() {
		return manufactor;
	}

	public void setManufactor(String manufactor) {
		this.manufactor = manufactor;
	}

}
