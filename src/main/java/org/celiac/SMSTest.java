package org.celiac;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;




public class SMSTest {
	
	static InputStreamReader in = null;
	
	public static void main(String[] args) {
		try {
			
			try{
			
			//Ping.main(args);
			
			}catch (Exception e){
				// Test
				    System.out.println("Got the exception - Exiting.");
				System.exit(1);
			}
			
	        //Pop3Email.postMen(PropertiesLoader.getProperties("emailFrom"), "Multi Test", "Bla\n\n ����, ����\n\n ����.",org.celiac.util.database.DBQueryFactory.getDBHandler().getAllUserDetails());			
		
			//java.util.Calendar cal = java.util.Calendar.getInstance();
			//cal.add(java.util.Calendar.MONTH, -1);	//Adding 1 month to current date
			//cal.getTimeInMillis();
			
			
			//User user = new User();
			//GFUser fff = new GFUser();
				
			//DBQueryFactory.getDBHandler().getUserByGeneric("1", "CELIAC_MEMBER_ID");
			//user.loadUser("tal", "b1234");
			//GFUser fff = user.theUser;
			//GFUser gfUser = new GFUser();
			/*
			gfUser.setFIRST_NAME("d");
			gfUser.setLAST_NAME("d");
			gfUser.setPASSWORD("d");
			gfUser.setUSER_ID("d");
			gfUser.setCELL_NUM("d");
			gfUser.setEMAIL("d");
			*/
			//gfUser.setCELIAC_MEMBER_ID("test1");
			//gfUser.setUSER_TZ("test1");
			//gfUser.setACKNOWLEDGE_USE("TRUE");
			//gfUser.setPERMISSIONS("sim");
			//gfUser.setLAST_LOGIN_DATE(new java.util.Date());
			//gfUser.setEFFECTIVE_DATE(new java.util.Date());
			//gfUser.setEXPIRATION_DATE(new java.util.Date());
			//gfUser.setDID_BUY_THE_BOOK("false");
			//user.insertUser(gfUser);
			//user.loadUser("d","d");
			//"GlutenFreeDB.xls"
			
			
			//System.out.println("GlutenFreeDB.xls");
			//HashMap ff = new HashMap();
			
			/*
			while (itr.hasNext()) {
				String eee = new String(itr.next().getBytes(),"ISO8859_8");
				System.out.println(eee);
			}
			
			System.out.println("start");
			File imageTest = new File("c:/imageTest/ccc2.jpg");
			byte[] testByte = getBytesFromFile(imageTest);
			
			ImageUtil imageUtil = new ImageUtil();
			
			
			byte[] smallImage = imageUtil.resizeImageAsJPG(testByte,80);
			byte[] mediumImage = imageUtil.resizeImageAsJPG(testByte,520);
			File someFile = new File("c:/imageTest/shrinkMedium.jpg");
	        FileOutputStream fos = new FileOutputStream(someFile);
	        fos.write(smallImage);
	        fos.flush();
	        fos.close();
	        
	        System.out.println("end");
			//DBQueryFactory.getDBHandler().insertImage("product2", "company1", "category1", null);
			//DBQueryFactory.getDBHandler().getSmallImage("product2", "company1", "category1");
			
			Job[] jobs = JobFactory.getJobs();
			for (int i=0 ; i<jobs.length ; i++){
				System.out.println("Job " + i + " " + jobs[i].getJobPropertieDetails().getTYPE());
				System.out.println("Job " + i + " " + jobs[i].getJobPropertieDetails().getTEXT());
			}
			*/
			/*
				DummyExpirationEmailJob fff = new DummyExpirationEmailJob();
				Set<GFUser> expUsers = fff.getExpiredUsers("8");
				Iterator<GFUser> users = expUsers.iterator();
				Date expDate = null;
				GFUser tmpUser = null;
				while (users.hasNext()){
					tmpUser = (GFUser)users.next();
					expDate = tmpUser.getEXPIRATION_DATE();
					Logger.info(tmpUser.getFIRST_NAME() + " " + tmpUser.getLAST_NAME() + " (" + tmpUser.getEMAIL() + ") Expire on " + expDate);
				}
				
				String tmpMessage = fff.getMessageSet(fff.getJobPropertieDetails().getTEXT(),expUsers);
				Logger.info(tmpMessage);
				
				//sending email to arg2
				try{
					new Pop3Email().sendMessageForJob("tal@yanaiu.org.il", "����� ���� ����� �����", tmpMessage, null, "���� �������");
				} catch (Exception e){
					Logger.error("Failed to send email for job " , e);
				}

			*/
			/*
			
			String sample = "12/31/2010";
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	        Date date = sdf.parse(sample);
			
			Locale locale = new Locale("iw", "IL");
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			
			//Expiration date String
			String expirationDate = SimpleDateFormat.getDateInstance(SimpleDateFormat.LONG, locale).format(date).toUpperCase();
			
			//New Start date String
			calendar.add(Calendar.DAY_OF_YEAR,1);
			String newStartDate = SimpleDateFormat.getDateInstance(SimpleDateFormat.LONG, locale).format(calendar.getTime()).toUpperCase();
			
			//New Expiration date:
			calendar.add(Calendar.MONTH,11);
			int lastDate = calendar.getActualMaximum(Calendar.DATE);
			calendar.set(Calendar.DAY_OF_MONTH, lastDate);
			String newExpDate = SimpleDateFormat.getDateInstance(SimpleDateFormat.LONG, locale).format(calendar.getTime()).toUpperCase();
			
			
			
			
			System.out.println(expirationDate);
			System.out.println(newStartDate);
			System.out.println(newExpDate);
			*/
			
			//Date aDate = org.celiac.datatype.UsersTable.MySqlStringToDate("2011-04-10");
			//System.out.println(aDate);
			
			/*
			int firstDate = calendar.getActualMinimum(Calendar.DATE);
			
			
			
			System.out.println(" " + SimpleDateFormat.getDateInstance(SimpleDateFormat.LONG, locale).format(today).toUpperCase());
			
			
			
			
			calendar.add(Calendar.YEAR,11);
			int lastDate = calendar.getActualMaximum(Calendar.DATE);
			System.out.println("Date     : " + calendar.getTime());
			System.out.println("Last Date: " + lastDate);
			*/
			
			
			// the encoder is a web resource protected using BASIC HTTP Authentication
		   //final String urlString = "http://localhost:9090/gfguide/rest/categories/����/���";
			//final String urlString = "http://localhost:9090/gfguide/rest/categories/PINA%20COLADA/BEVELAND";
		    //String urlString = "http://localhost:9090/gfguide/rest/manufactures";///�����";// + URLEncoder.encode("�����", "UTF-8");
			//String urlString = "http://localhost:9090/gfguide/rest/manufactures/����";
			
			
			/* 
		    final String userName = "tal";
		    final String password = "b1234";

		    // open url connection
		    URL url = new URL( urlString );
		    HttpURLConnection con = (HttpURLConnection) url.openConnection();
		    con.setRequestProperty("Content-Type", "text/plain; charset=UTF-8");
		    con.setRequestProperty("Expect", "100-continue");
		    // set up url connection to get retrieve information back
		    con.setRequestMethod( "GET" );
		    con.setDoInput( true );

		    // stuff the Authorization request header
		    byte[] encodedPassword = ( userName + ":" + password ).getBytes();
		    BASE64Encoder encoder = new BASE64Encoder();
		    con.setRequestProperty( "Authorization",
		                            "Basic " + encoder.encode( encodedPassword ) );
		    
		    
		    InputStream is = con.getInputStream();
		    StringBuffer buf = new StringBuffer();
		    int c;
		    while( ( c = is.read() ) != -1 ) {
		      buf.append( (char) c );
		    }
		    con.disconnect();

		    //byte[] decode = new sun.misc.BASE64Decoder().decodeBuffer(s);
		    
		    // output the information
		    System.out.println( buf );
		    */
		    
		    

			
			
			//List<String> countries = db.getProductData(query);
			//String countries = db.getProductData("���");
			
			//System.out.println(countries);
		    
		    System.out.println("\n");
		      System.out.println("<html lang=\"en\" dir=\"RTL\">\n");
		      System.out.println("\n");
		      System.out.println("<head>\n");
		      System.out.println("\t<meta charset=\"utf-8\">\n");
		      System.out.println("\t<title>jQuery UI Autocomplete - Remote datasource</title>\n");
		      System.out.println("\t<link rel=\"stylesheet\" href=\"../include/jquery.ui.all.css\">\n");
		      System.out.println("<script src=\"../include/jquery-1.5.1.js\"></script>\n");
		      System.out.println("<script src=\"../include/jquery.ui.core.js\"></script>\n");
		      System.out.println("<script src=\"../include/jquery.ui.widget.js\"></script>\n");
		      System.out.println("<script src=\"../include/jquery.ui.position.js\"></script>\n");
		      System.out.println("<script src=\"../include/jquery.ui.autocomplete.js\"></script>\n");
		      System.out.println("<link rel=\"stylesheet\" href=\"../include/demos.css\">\n");
		      System.out.println("\n");
		      System.out.println("\t<style>\n");
		      System.out.println("\t.ui-autocomplete-loading { background: white url('../images/ui-anim_basic_16x16.gif') right center no-repeat; }\n");
		      System.out.println("\t</style>\n");
		      System.out.println("\t\n");
		      System.out.println("</head>\n");
		      System.out.println("<body>\n");
		      System.out.println("\n");
		      System.out.println("\n");
		      System.out.println("\n");
		      System.out.println("<TD  colspan=\"1\"  style=\"width: 50%\" ><input type=\"text\" value=\"\" id=\"productSelect3\" name=\"productSelect3\" >\n");
		      System.out.println("\t<script>\n");
		      System.out.println("\t\t$(function() { \n");
		      System.out.println("\t\t\t$(\"#productSelect3\").autocomplete\n");
		      System.out.println("        ({ \n");
		      System.out.println("            source: function( request, response )\n");
		      System.out.println("            {                      \n");
		      System.out.println("                $.ajax(\n");
		      System.out.println("                { \n");
		      System.out.println("                    url: \"getProductData.jsp\",\n");
		      System.out.println("                    data: {\n");
		      System.out.println("                            term: request.term, \n");
		      System.out.println("                          },        \n");
		      System.out.println("                    type: \"POST\",  \n");
		      System.out.println("                    dataType: \"json\",                                                                                                                           \n");
		      System.out.println("                    success: function( data ) \n");
		      System.out.println("                    {\n");
		      System.out.println("                        response( $.map( data, function( item ) \n");
		      System.out.println("                        {\n");
		      System.out.println("                            return{\n");
		      System.out.println("                                    label: item.label,\n");
		      System.out.println("                                    value: item.value,\n");
		      System.out.println("                                   }\n");
		      System.out.println("                        }));\n");
		      System.out.println("                    }\n");
		      System.out.println("                });                \n");
		      System.out.println("            },\n");
		      System.out.println("            minLength: 3\n");
		      System.out.println("        });\n");
		      System.out.println("\t\t});\n");
		      System.out.println("\t</script>\n");
		      System.out.println("\t\n");
		      System.out.println("\t</TD>\n");
		      System.out.println("\n");
		      System.out.println("\n");
		      System.out.println("\n");
		      System.out.println("\n");
		      System.out.println("\n");
		      System.out.println("\n");
		      System.out.println("\n");
		      System.out.println("</body>\n");
		      System.out.println("</html>\n");
		      System.out.println("\n");
		      System.out.println("  ");
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public static byte[] getBytesFromFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);
    
        // Get the size of the file
        long length = file.length();
    
        if (length > Integer.MAX_VALUE) {
            // File is too large
        }
    
        // Create the byte array to hold the data
        byte[] bytes = new byte[(int)length];
    
        // Read in the bytes
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
               && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
            offset += numRead;
        }
    
        // Ensure all the bytes have been read in
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file "+file.getName());
        }
    
        // Close the input stream and return bytes
        is.close();
        return bytes;
    }

}
