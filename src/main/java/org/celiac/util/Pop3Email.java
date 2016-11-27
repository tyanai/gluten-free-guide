package org.celiac.util;

import java.io.File;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.celiac.datatype.GFUser;


public class Pop3Email {

	private static String SMTP_HOST_NAME = null;

	private static String SMTP_PORT = null;

	private static String emailFromAddress = null;

	//private static String SSL_FACTORY = null;

	static String user = null;

	static String password = null;

	private static String emailDebug = null;

	private static String emailAuth = null;

	private static String socketFallBack = null;
	 
	private static String replyTo = null;

	static {
		try {
			loadEmailInfo();
		} catch (Exception e) {
			try {
				Logger.error("Failed to load email messages files", e);
			} catch (Exception ee) {
				// To Do
			}
		}
	}

	private static void loadEmailInfo() throws Exception {

		try {
			SMTP_HOST_NAME = PropertiesLoader.getProperties("smtpHostName").trim();
			SMTP_PORT = PropertiesLoader.getProperties("outgoingSmtpServerPort").trim();
			//SSL_FACTORY = PropertiesLoader.getProperties("sslFactory").trim();
			emailFromAddress = PropertiesLoader.getProperties("emailFrom");
			user = PropertiesLoader.getProperties("emailUser").trim();
			password = PropertiesLoader.getProperties("emailPassword").trim();
			emailDebug = PropertiesLoader.getProperties("emailDebug").trim();
			emailAuth = PropertiesLoader.getProperties("smtpAuth").trim();
			socketFallBack = PropertiesLoader.getProperties("socketFallBack").trim();
			replyTo = PropertiesLoader.getProperties("replyTo").trim();

		} catch (Exception e) {
			Logger.error("Failed to load email message information from config.xml", e);
		}
	}

	public static void postMen(String sendTo, String emailSubjectTxt, String emailMsgTxt) throws Exception {

		//Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());

		new Pop3Email().sendSSLMessage(sendTo, emailSubjectTxt, emailMsgTxt);

	}
	
	public static void postMen(String sendTo, String emailSubjectTxt, String emailMsgTxt, Set<GFUser> usersSet) throws Exception {

		//Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());

		new Pop3Email().sendSSLMessage(sendTo, emailSubjectTxt, emailMsgTxt, usersSet);

	}

	public void sendSSLMessage(String recipient, String subject, String message) throws Exception, MessagingException {
		sendSSLMessage( recipient,  subject,  message,null);
	}
	
	public void sendSSLMessage(String recipient, String subject, String aMessage, Set<GFUser> usersSet) throws Exception, MessagingException {
		
		
		boolean debug = ("true".equals(emailDebug));
		String message = aMessage;
		Properties props = new Properties();
		props.put("mail.smtp.host", SMTP_HOST_NAME);
		props.put("mail.smtp.auth", emailAuth);
		props.put("mail.debug", emailDebug);
		props.put("mail.smtp.port", SMTP_PORT);
		props.put("mail.smtp.socketFactory.port", SMTP_PORT);
		//props.put("mail.smtp.socketFactory.class", SSL_FACTORY);
		props.put("mail.smtp.socketFactory.fallback", socketFallBack);

		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(user, password);
			}
		});

		session.setDebug(debug);

		Message msg = new MimeMessage(session);
		InternetAddress addressFrom = new InternetAddress(emailFromAddress);
		addressFrom.setPersonal(org.celiac.util.TemplateReader.getHebrewMapWord("MADRISH_GLUTEN_INTERNET"), "UTF-8");
		msg.setFrom(addressFrom);
		
		InternetAddress[] replyToAddress = new InternetAddress[1];
		replyToAddress[0] = new InternetAddress(replyTo);
		msg.setReplyTo(replyToAddress);

		InternetAddress[] addressTo = null ;
		InternetAddress[] addressBcc = null;
		
		
		addressTo = new InternetAddress[1];
		addressTo[0] = new InternetAddress(recipient);
		addressTo[0].setPersonal(org.celiac.util.TemplateReader.getHebrewMapWord("MEMBERS_2"), "UTF-8");
		msg.addRecipients(Message.RecipientType.TO, addressTo);
		
		
		if (usersSet != null){
			String emailAddress = null;
			addressBcc = new InternetAddress[usersSet.size()];
			Iterator<GFUser> iteOfUser = usersSet.iterator();
			int i=0;
			while (iteOfUser.hasNext()){
				
				emailAddress = ((GFUser)iteOfUser.next()).getEMAIL();
				
				if (!(emailAddress.indexOf("@") > 0)) emailAddress = emailAddress + "ddd@ddd.com";
				if (emailAddress.indexOf("@") == 0) emailAddress = "ddd@ddd.com";
					
				if ((emailAddress == null) || "".equals(emailAddress.trim())){
					addressBcc[i++] = new InternetAddress("");
				} else {
					addressBcc[i++] = new InternetAddress(emailAddress.trim());
				}
				
			}
			msg.addRecipients(Message.RecipientType.BCC, addressBcc);
			//message = "<html DIR=\"RTL\">" + message + "</html>";
			message = "<html DIR=\"RTL\"><div dir=\"rtl\">" + message + "</div></html>";
			while(message.indexOf("\n") >= 0){
				message = message.replaceAll("\n", "<br>");
			}
			
		}

		
		

		// Setting the Subject and Content Type
		msg.setSubject(subject);

		msg.setContent(message, "text/html; charset=\"UTF-8\"");

		Transport.send(msg);
	}
	
	
	
	
	public File getFile(String fileName) throws Exception {

		File file = new File(PropertiesLoader.getProperties("attachments.location") + "/" + fileName);
		
		return file;

	}
	
public void sendMessageForJob(String recipient, String subject, String aMessage, Set<GFUser> usersSet, String recipientFullName) throws Exception, MessagingException {
		
		
		boolean debug = ("true".equals(emailDebug));
		String message = aMessage;
		Properties props = new Properties();
		props.put("mail.smtp.host", SMTP_HOST_NAME);
		props.put("mail.smtp.auth", emailAuth);
		props.put("mail.debug", emailDebug);
		props.put("mail.smtp.port", SMTP_PORT);
		props.put("mail.smtp.socketFactory.port", SMTP_PORT);
		//props.put("mail.smtp.socketFactory.class", SSL_FACTORY);
		props.put("mail.smtp.socketFactory.fallback", socketFallBack);

		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(user, password);
			}
		});
 
		session.setDebug(debug);

		Message msg = new MimeMessage(session);
		InternetAddress addressFrom = new InternetAddress(emailFromAddress);
		addressFrom.setPersonal(org.celiac.util.TemplateReader.getHebrewMapWord("AMUTA"), "UTF-8");
		msg.setFrom(addressFrom);
		
		InternetAddress[] replyToAddress = new InternetAddress[1];
		replyToAddress[0] = new InternetAddress(replyTo);
		msg.setReplyTo(replyToAddress);

		InternetAddress[] addressTo = null ;
		InternetAddress[] addressBcc = null;
		
		
		
		
		
		if (usersSet != null){
			addressTo = new InternetAddress[1];
			addressTo[0] = new InternetAddress(recipient);
			addressTo[0].setPersonal(org.celiac.util.TemplateReader.getHebrewMapWord("MEMBERS_1"), "UTF-8");
			msg.addRecipients(Message.RecipientType.TO, addressTo);
			
			String emailAddress = null;
			addressBcc = new InternetAddress[usersSet.size()];
			Iterator<GFUser> iteOfUser = usersSet.iterator();
			int i=0;
			while (iteOfUser.hasNext()){
				
				emailAddress = ((GFUser)iteOfUser.next()).getEMAIL();
				
				if (!(emailAddress.indexOf("@") > 0)) emailAddress = emailAddress + "ddd@ddd.com";
				if (emailAddress.indexOf("@") == 0) emailAddress = "ddd@ddd.com";
					
				if ((emailAddress == null) || "".equals(emailAddress.trim())){
					addressBcc[i++] = new InternetAddress("");
				} else {
					addressBcc[i++] = new InternetAddress(emailAddress.trim());
				}
				
			}
			msg.addRecipients(Message.RecipientType.BCC, addressBcc);
			
			
		} else {
			//It an indevidual user
			addressTo = new InternetAddress[1];
			addressTo[0] = new InternetAddress(recipient);
			addressTo[0].setPersonal(recipientFullName, "UTF-8");
			msg.addRecipients(Message.RecipientType.TO, addressTo);
			
			
		}

		/*
		message = "<html DIR=\"RTL\"><div dir=\"rtl\">" + message + "</div></html>";
		while(message.indexOf("\n") >= 0){
			message = message.replaceAll("\n", "<br>");
		}
		*/
		Logger.info(message);

		// Setting the Subject and Content Type
		msg.setSubject(subject);

		msg.setContent(message, "text/html; charset=\"UTF-8\"");

		Transport.send(msg);
	}
}
