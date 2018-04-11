package org.celiac;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.cxf.jaxrs.client.WebClient;
import org.celiac.datatype.ListDao;
import org.celiac.datatype.StringDao;
import org.apache.commons.codec.binary.Base64;

public class MyRestClient {

   public static void main(String[] args) throws Exception{
	   //System.out.println(new Date().getTime());
           WebClient client = WebClient.create("http://localhost:8080/gfguide","faf","asfa",null);
	  //WebClient client = WebClient.create("http://www.gfguide.info:6880","asfaf","asfafa",null);
	   
	   File file = new File("C:\\Users\\tyanai\\Desktop\\guideusers.xlsx");
            InputStream in = new FileInputStream(file);
            StringDao output = client.path("/rest/upload_users").accept("application/octet-stream").post(Entity.entity(in, MediaType.APPLICATION_OCTET_STREAM),StringDao.class);
          
	   
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
