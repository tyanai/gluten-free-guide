package org.celiac.util;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class ActivityLogger {
	
	//static BufferedWriter out = null;
	static OutputStreamWriter out = null;
	
	static{
		try{
			
			OutputStream fout= new FileOutputStream(PropertiesLoader.getProperties("activity.log"),true);
	        OutputStream bout= new BufferedOutputStream(fout);
	        out = new OutputStreamWriter(bout, "ISO8859_8");	      
	       // System.setOut(new PrintStream(System.out, false, "Cp1255"));
		}catch (Exception e){
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	static public void print (String output){
		try{
		
		
			
	    out.write(output + "\n");
	    out.flush();	
		//System.out.println(output);
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}
