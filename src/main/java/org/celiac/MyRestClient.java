package org.celiac;

import org.apache.cxf.jaxrs.client.WebClient;
import org.celiac.datatype.ListDao;
import org.celiac.datatype.StringDao;
import org.apache.commons.codec.binary.Base64;

public class MyRestClient {

   public static void main(String[] args) throws Exception{
	   //System.out.println(new Date().getTime());
      //WebClient client = WebClient.create("http://localhost:9090/gfguide","tal","b1234",null);
	  WebClient client = WebClient.create("http://www.gfguide.info:6880","tal","b1234",null);
	   
	   
	   
	 /*
	  
      //ListDao response = client.path("/rest/categories/MULTIPLE/צבר").accept("application/json").get(ListDao.class);
      ListDao response = client.path("/rest/categories/MULTIPLE/צבר").accept("application/json").get(ListDao.class);
      
     
      for (StringDao s : response.getItems()) {
          System.out.println(s.getValue());
      }
      
      
      
      
      StringDao response2 = client.replacePath("/rest/smallimage/WESTONS OLD ROSIE/WESTONS/משקאות חריפים - סיידר").accept("application/json").get(StringDao.class);
      if ("EMPTY".equals(response2.getValue())){} // do nothing;
      else {
    	  BASE64Decoder enc = new BASE64Decoder();
		  byte[] image = enc.decodeBuffer(response2.getValue());
    	  java.io.OutputStream o = new java.io.FileOutputStream("C:/GF/img.jpg");
		  o.write(image);
		  o.flush();
		  o.close();
      }
      System.out.append(response2.getValue());
      */
      
      
   }
   
}
