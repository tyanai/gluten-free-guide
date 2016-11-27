package org.celiac.datatype;

public class CompanyTable {

	private String[] CATAGORY = null;
	private String[] PRODUCT = null;
	private String[] GLUTEN_FREE = null;
	public String[] getCATAGORY() {
		return CATAGORY;
	}
	public void setCATAGORY(String[] catagory) {
		CATAGORY = catagory;
	}
	public String[] getGLUTEN_FREE() {
		return GLUTEN_FREE;
	}
	public void setGLUTEN_FREE(String[] gluten_free) {
		GLUTEN_FREE = gluten_free;
	}
	public String[] getPRODUCT() {
		return PRODUCT;
	}
	public void setPRODUCT(String[] product) {
		PRODUCT = product;
	}
	
}
