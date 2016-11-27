package org.celiac.datatype;



public class CodeDesc implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -236708672426503842L;
	/**
	 * 
	 */
	
	private String CODE = null;
	private String DESC = null;
	
	public String getCODE() {
		return CODE;
	}
	public void setCODE(String code) {
		CODE = code;
	}
	public String getDESC() {
		return DESC;
	}
	public void setDESC(String desc) {
		DESC = desc;
	}
	
}
