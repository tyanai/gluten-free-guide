package org.celiac.datatype;

import java.util.Date;

public class StatisticsData {

	private int returned1 = 0;
	private int returned2 = 0;
	private int returned3 = 0;
	private int returned4 = 0;
	private int returned5 = 0;
	private int webUserCount = 0;
	private Date date = null;
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getReturned1() {
		return returned1;
	}
	public void setReturned1(int returned1) {
		this.returned1 = returned1;
	}
	public int getReturned2() {
		return returned2;
	}
	public void setReturned2(int returned2) {
		this.returned2 = returned2;
	}
	public int getReturned3() {
		return returned3;
	}
	public void setReturned3(int returned3) {
		this.returned3 = returned3;
	}
	public int getReturned4() {
		return returned4;
	}
	public void setReturned4(int returned4) {
		this.returned4 = returned4;
	}
	public int getReturned5() {
		return returned5;
	}
	public void setReturned5(int returned5) {
		this.returned5 = returned5;
	}
	public int getWebUserCount() {
		return webUserCount;
	}
	public void setWebUserCount(int webUserCount) {
		this.webUserCount = webUserCount;
	}
	
}
