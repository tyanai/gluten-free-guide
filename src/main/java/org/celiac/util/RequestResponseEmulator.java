package org.celiac.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class RequestResponseEmulator {

	public String getResponse(String url) throws Exception {
		URL theUrl = new URL(url);
		URLConnection yc = theUrl.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
		String inputLine;
		String output = "";

		while ((inputLine = in.readLine()) != null){
			if(inputLine.contains("αξαδ")) System.out.println("True");
			output = output + inputLine + "\n";
		}
		
		in.close();

		return output;
	}

	

	// http://gfsms.info/sms?message=asdf&cellPhone=12345

	public static void main(String argv[]) throws Exception {

		RequestResponseEmulator requestResponseEmulator = new RequestResponseEmulator();
		String output = requestResponseEmulator.getResponse(argv[0]);
		//Logger.print(output);
		System.out.println(output);

	}

}
