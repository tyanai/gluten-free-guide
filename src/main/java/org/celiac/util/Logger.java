package org.celiac.util;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;





public class Logger {

	// static BufferedWriter out = null;
	
	//private static java.util.logging.Logger logger = java.util.logging.Logger.getLogger("AAA");
	//private static org.slf4j.Logger logger = LoggerFactory.getLogger(Logger.class);
        private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(Logger.class);
	
	
	static {
		try {

			System.out.println("Log4JInitServlet is initializing log4j");
			String log4jLocation = PropertiesLoader.getSystemPropertiesLocation();   //("log4j-properties-location");
			
			

			if (log4jLocation == null) {
				System.out.println("*** No log4j-properties-location init param, so initializing log4j with BasicConfigurator");
				BasicConfigurator.configure();
			} else {
				
				
				try {		
					Properties prop = new Properties();
					InputStream is = Logger.class.getResourceAsStream(log4jLocation+"/log4j.properties"); 
					prop.load(is);
					PropertyConfigurator.configure(prop);
				} catch (IOException e) {
					System.err.println("*** " + log4jLocation + "/log4j.properties file not found, so initializing log4j with BasicConfigurator");
					e.printStackTrace();
					BasicConfigurator.configure();
				}
				
			
			}
			/*
			OutputStream fout = new FileOutputStream(PropertiesLoader.getProperties("application.log"));
			
			Handler handler =  new StreamHandler(new PrintStream(System.err, true, "Cp1255"), new LogFormatter());
			Handler fileHandler = new StreamHandler(fout, new LogFormatter());
			fileHandler.setEncoding("ISO8859_8");
			handler.setEncoding("ISO8859_8");
			
			fileHandler.setLevel(Level.FINEST);
			handler.setLevel(Level.FINEST);
			//logger.setLevel(Level.FINEST);
			//logger.addHandler(fileHandler);
			//logger.setUseParentHandlers(false);
			//logger.addHandler(handler);
			*/
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	

	static Date date = new Date();

	static Object[] args = new Object[1];

	private final static String format = "{0,date} {0,time}";

	final static MessageFormat formatter = new MessageFormat(format);

	static {
		//logger.getParent().getHandlers()[0].setFormatter(new LogFormatter());
	}

	private Logger() {
		//Kept empty to avoid construction
	}

	static public void error(String message, Throwable t) {
		logger.error( message, t);
	}

	static public void error(String message) {
		logger.error(message);
	}

	static public void warning(String message) {
		logger.warn( message);
	}

	static public void info(String message) {
		logger.info(message);
	}

	static public void debug(String message) {
		logger.debug(message);
		
	}

	static public void print(String output) {
		/*try {

			out.write(output + "\n");
			out.flush();
			System.out.println(output);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		logger.info( output);
	}
}
