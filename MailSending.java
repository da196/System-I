package com.meeting;

import java.util.Date;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


public class MailSending {
	  //private static File errorLogs ;
	 // private static String errorLogFile;
	   public static int  sendMail(String email, String link,final String senderMail,final  String senderPassword, String smtpHost, String smtpPort,
			   String msgSubject, String msgHeader, String msgBody, String msgBodyEnd) {
		   int status = 0;
		  // errorLogFile = Utility.getPath();
		   //errorLogs = new File(errorLogFile);
	       // SMTP info
	       String host = smtpHost;
	       String port = smtpPort;
	       String mailFrom = senderMail;
	       String password = senderPassword;
	      
	       // message info
	       String mailTo = email;
	       String subject = msgSubject;
	       //String imageUrl ="E:\\TCRA 2019-2020\\APPLICATION\\OTAS\\OTAS\\OTAS\\WebContent\\images\\tcra2.png";
	      // String imageUrl = "/usr/libexec/tomcat9/webapps/OTAS/images/tcra2.png";
	       //String imageUrl = "/var/otas_images/tcra2.png";
	       
	       StringBuffer body = new StringBuffer("<html><head><style>.button {background-color: #4CAF50; border: none;  color: white;  padding: 15px 32px;  text-align: center;  text-decoration: none;  display: inline-block;  font-size: 16px;  margin: 4px 2px;  cursor: pointer; border-radius: 10%;}</style></head><br>");
	       body.append("<body style=\"background-color:#DCDCDC\"><center>");
	       body.append("<br>THE UNITED REPUBLIC OF TANZANIA<br><b>TANZANIA COMMUNICATIONS REGULATORY AUTHORITY</b><br><b>ISO 9001:2015 CERTIFIED</b><br><br>");
	      // body.append("<img src=\"cid:image\" style=\"width:60px; height:50px;\"/><br>THE UNITED REPUBLIC OF TANZANIA<br><b>TANZANIA COMMUNICATIONS REGULATORY AUTHORITY</b><br><b>ISO 9001:2015 CERTIFIED</b><br><br>");
	       body.append("<div style=\"background-color:#FFFFFF; width:60%; height:auto; padding:1%\"><div style=\"text-align:left\">");
	       body.append(""+ msgHeader +" <br><br>"+ msgBody +" <br> <br>");
	      // body.append("<img src=\"cid:image2\" width=\"15%\" height=\"15%\" /><br>");
	       //body.append("<button style=\"background-color: #4CAF50; border: none; color: white; padding: 15px 32px; text-align: center; text-decoration: none; display: inline-block; font-size: 16px;  margin: 4px 2px;  cursor: pointer;  border-radius: 10%;\"><a style=\"color:black\" href=\""+ link+"\">Click here</a></button><br><br>");
	       body.append(""+ msgBodyEnd +"<br></div></div><br>");
	       body.append("<footer style=\"background-color:#FFFFFF; width:50%; height:auto%; padding:1%\">");
	       body.append("Mawasiliano Towers,20 Sam Nujoma Road,<br>P.O Box 474,14414 Dar Es Salaam. <br>");
	       body.append("&copy;  Tanzania Communications Regulatory Authority <script>document.write(new Date().getFullYear())</script>. All Rights Reserved.");
	       
	       body.append("</footer></center></body></html>");

	   	// sets SMTP server properties
		   Properties properties = new Properties();
		   properties.put("mail.smtp.host", host);
		   properties.put("mail.smtp.port", port);
		   properties.put("mail.smtp.auth", "true");
		   properties.put("mail.smtp.starttls.enable", "true");
		   properties.put("mail.user", mailFrom);
		   properties.put("mail.password", password);
		
		   // creates a new session with an authenticator
		   Authenticator auth = new Authenticator() {
		       public PasswordAuthentication getPasswordAuthentication() {
		           return new PasswordAuthentication(mailFrom, password);
		       }
		   };
		   Session session = Session.getInstance(properties, auth);
		try {
		   // creates a new e-mail message
		   Message msg = new MimeMessage(session);
		
		   msg.setFrom(new InternetAddress(mailFrom));
		   //InternetAddress[] toAddresses = { new InternetAddress(toAddress) };
		   msg.setRecipients(Message.RecipientType.TO,InternetAddress.parse(mailTo));
		  // msg.setRecipients(Message.RecipientType.TO, toAddresses);
		   msg.setSubject(subject);
		   msg.setSentDate(new Date());
		
		   // creates message part
		   MimeBodyPart messageBodyPart = new MimeBodyPart();
		   messageBodyPart.setContent(body.toString(), "text/html");
		
		   // creates multi-part
		   Multipart multipart = new MimeMultipart();
		   multipart.addBodyPart(messageBodyPart);
					
		   msg.setContent(multipart);
		
		   Transport.send(msg);
		   status =1;
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return status;
	   
	   }
	   
	   public static void send(String host, String port,final String userName, final String password, String toAddress,String subject, String htmlBody)
	           throws AddressException, MessagingException {
		   	// sets SMTP server properties
			   Properties properties = new Properties();
			   properties.put("mail.smtp.host", host);
			   properties.put("mail.smtp.port", port);
			   properties.put("mail.smtp.auth", "true");
			   properties.put("mail.smtp.starttls.enable", "true");
			   properties.put("mail.user", userName);
			   properties.put("mail.password", password);
			
			   // creates a new session with an authenticator
			   Authenticator auth = new Authenticator() {
			       public PasswordAuthentication getPasswordAuthentication() {
			           return new PasswordAuthentication(userName, password);
			       }
			   };
			   Session session = Session.getInstance(properties, auth);
			
			   // creates a new e-mail message
			   Message msg = new MimeMessage(session);
			
			   msg.setFrom(new InternetAddress(userName));
			   //InternetAddress[] toAddresses = { new InternetAddress(toAddress) };
			   msg.setRecipients(Message.RecipientType.TO,InternetAddress.parse(toAddress));
			  // msg.setRecipients(Message.RecipientType.TO, toAddresses);
			   msg.setSubject(subject);
			   msg.setSentDate(new Date());
			
			   // creates message part
			   MimeBodyPart messageBodyPart = new MimeBodyPart();
			   messageBodyPart.setContent(htmlBody, "text/html");
			
			   // creates multi-part
			   Multipart multipart = new MimeMultipart();
			   multipart.addBodyPart(messageBodyPart);
						
			   msg.setContent(multipart);
			
			   Transport.send(msg);
			   
	}

}
