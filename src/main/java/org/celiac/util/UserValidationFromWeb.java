package org.celiac.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class UserValidationFromWeb {

	public static boolean isUserValid(String user, String tz){
		
		
		try {
	        // Construct data
	        String data = URLEncoder.encode("User", "UTF-8") + "=" + URLEncoder.encode(user, "UTF-8");
	        data += "&" + URLEncoder.encode("Pass", "UTF-8") + "=" + URLEncoder.encode(tz, "UTF-8");
	        
	    
	        // Send data
	        URL url = new URL("http://www.celiac.org.il/u_logonc.asp");
	        URLConnection conn = url.openConnection();
	        conn.setDoOutput(true);
	        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
	        wr.write(data);
	        wr.flush();
	    
	        // Get the response
	        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        String line;
	        while ((line = rd.readLine()) != null) {
	            if (line.indexOf("MM_findObj") > -1) return true;
	        }
	        wr.close();
	        rd.close();
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
		
		return false;
	}
	
}
