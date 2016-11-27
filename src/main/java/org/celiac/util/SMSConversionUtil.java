package org.celiac.util;

import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;

//import org.celiac.api.UnicodeFormatter;

public class SMSConversionUtil {
	
	
	public static List<String> cutString(String input, int _maxLen) {
		int maxLen = _maxLen;
		List<String> response = new LinkedList<String>();
		int i = 0;
		while (i < input.length()) {
			if (i + maxLen >= input.length()) {
				maxLen = input.length() - i;
			}
			response.add(new String(input.substring(i, i+maxLen)));
			i+=maxLen;
		}
		return response;
	}
	
	 public static String getBytes05String(byte[] array) {
		 StringBuilder result = new StringBuilder();
	      for (byte value:array) {
	    	    short absValue = (short)((256 + value)%256);
	    	    if (absValue >= 128) {
	    	    	absValue -= 0x10; 
	    	    	result.append("05");
	    	    }
	    	    else {
	    	    	result.append("00");
	    	    }
	    	    
	    	  	//result.append(UnicodeFormatter.byteToHex((byte)absValue).toUpperCase());
	      }
	      return result.toString();
	   }
	 
	private static int toNum(byte _ch) {
		byte ch = _ch;
		if (ch > '9') {
			ch -= 'A';
			ch += (byte)10;
		}
		else {
			ch -= '0';
		}
		return ch;
	}
	
	public static String smsToString(String sms) {
		byte[] result = new byte[sms.length()];
		byte[] bytes = sms.getBytes();
		int lastVal = 0;
		int k = 0;
		for (int i=0;i<bytes.length;i+=2) {
			int firstChar = toNum(bytes[i]);
			int secChar = toNum(bytes[i+1]);
			int val = (firstChar)*16+secChar;
			
			if (val >= 20) {
				if (lastVal == 5) {
					val += 16;
				}
				result[k++] = (byte)val;
			}
			else {
				lastVal = val;
			}
		}
		try {
			return new String(result,0,k,"Cp1255");
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}
}
