package org.celiac.datatype;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class DiffDataCollection {

	
	public Date fromDate = null;
	public Set<DiffData> productRemoved = new LinkedHashSet<DiffData>();
	public Set<DiffData> productAdded = new LinkedHashSet<DiffData>();
	public Set<DiffData> productChanged = new LinkedHashSet<DiffData>();
	public HashMap<String,Date> companyRemoved = new HashMap<String,Date>();
	public HashMap<String,Date> companyAdded = new HashMap<String,Date>();
	
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Set<DiffData> getProductRemoved() {
		return productRemoved;
	}
	public void setProductRemoved(Set<DiffData> productRemoved) {
		this.productRemoved = productRemoved;
	}
	public Set<DiffData> getProductAdded() {
		return productAdded;
	}
	public void setProductAdded(Set<DiffData> productAdded) {
		this.productAdded = productAdded;
	}
	public Set<DiffData> getProductChanged() {
		return productChanged;
	}
	public void setProductChanged(Set<DiffData> productChanged) {
		this.productChanged = productChanged;
	}
	public HashMap<String, Date> getCompanyRemoved() {
		return companyRemoved;
	}
	public void setCompanyRemoved(HashMap<String, Date> companyRemoved) {
		this.companyRemoved = companyRemoved;
	}
	public HashMap<String, Date> getCompanyAdded() {
		return companyAdded;
	}
	public void setCompanyAdded(HashMap<String, Date> companyAdded) {
		this.companyAdded = companyAdded;
	}
	
	public boolean isEmpty(){
		if (productRemoved.size() == 0 && productAdded.size() == 0 && productChanged.size() == 0 && companyRemoved.size() == 0 && companyAdded.size() == 0) return true;
		else return false;
	}
	
	
	
}
