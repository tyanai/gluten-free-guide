package org.celiac.util;

import java.util.HashMap;

import org.celiac.datatype.StatisticsData;
import org.celiac.util.database.DBQueryFactory;

public class MessageCounter {

	private static HashMap<String, Integer> statistics = new HashMap<String, Integer>();
	static {
		statistics.put("1", new Integer(0));
		statistics.put("2", new Integer(0));
		statistics.put("3", new Integer(0));
		statistics.put("4", new Integer(0));
		statistics.put("5", new Integer(0));
		statistics.put("webUserCount", new Integer(0));
	}

	final static int numOfStatisticInMemory = new Integer(PropertiesLoader.getProperties("num.of.statistic.in.memory").trim())
			.intValue();
	final static int numOfMinutesToHoldStatisticInMemory = new Integer(PropertiesLoader.getProperties(
			"num.of.minutes.to.hold.statistic.in.memory").trim()).intValue() * 60 * 1000;

	private static long lastTimeOfPersistancy = 0;

	public static void setReturned(int returned) {
		switch (returned) {
		case 1:
			setCount("1");
			break;
		case 2:
			setCount("2");
			break;
		case 3:
			setCount("3");
			break;
		case 4:
			setCount("4");
			break;
		case 5:
			setCount("5");
			break;
		default:
			break;
		}
	}

	public static void setWebUserCount() {
		setCount("webUserCount");
	}

	public static void setCount(String theCounterIdentity) {

		// Check count
		if (((Integer) statistics.get(theCounterIdentity)).intValue() > numOfStatisticInMemory) {
			synchronized (MessageCounter.class) {
				if (((Integer) statistics.get(theCounterIdentity)).intValue() > numOfStatisticInMemory) {
					// Persist
					persistCountAll();
					// Reset and add 1
					statistics.put(theCounterIdentity, new Integer(1));
					return;
				}

			}
		}

		if (lastTimeOfPersistancy == 0
				|| (new java.util.Date().getTime() - lastTimeOfPersistancy) > numOfMinutesToHoldStatisticInMemory) {
			synchronized (MessageCounter.class) {
				if (lastTimeOfPersistancy == 0
						|| (new java.util.Date().getTime() - lastTimeOfPersistancy) > numOfMinutesToHoldStatisticInMemory) {
					// Persist all
					persistCountAll();
					// add 1 to the relevant
					statistics.put(theCounterIdentity, new Integer(1));

					// set the new time:
					lastTimeOfPersistancy = new java.util.Date().getTime();
					return;
				}
			}

		}

		synchronized (MessageCounter.class) {
			int tmp = ((Integer) statistics.get(theCounterIdentity)).intValue();
			tmp++;
			statistics.put(theCounterIdentity, new Integer(tmp));
		}

	}

	private static void persistCountAll() {

		StatisticsData statisticsData = new StatisticsData();
		statisticsData.setReturned1(((Integer) statistics.get("1")).intValue());
		statisticsData.setReturned2(((Integer) statistics.get("2")).intValue());
		statisticsData.setReturned3(((Integer) statistics.get("3")).intValue());
		statisticsData.setReturned4(((Integer) statistics.get("4")).intValue());
		statisticsData.setReturned5(((Integer) statistics.get("5")).intValue());
		statisticsData.setWebUserCount(((Integer) statistics.get("webUserCount")).intValue());
		try{
			DBQueryFactory.getDBHandler().updateStatsticsData(statisticsData);
		} catch (Exception e){
			Logger.error("Failed to update statistics");
		}

		statistics.put("1", new Integer(0));
		statistics.put("2", new Integer(0));
		statistics.put("3", new Integer(0));
		statistics.put("4", new Integer(0));
		statistics.put("5", new Integer(0));
		statistics.put("webUserCount", new Integer(0));
	}

}
