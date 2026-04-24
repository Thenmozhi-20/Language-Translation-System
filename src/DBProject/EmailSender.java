package DBProject;

import java.util.Properties ;
import jakarta.mail.*;
import jakarta.mail.internet.*;

public class EmailSender {
	public static void sendEmail(String recipientEmail,String subject,String content)
	{
		String senderEmail = "thenmozhi565then@gmail.com" ;
		String senderPassword = "ijod rlom ijeb fpmg";
		
		Properties props = new Properties();
		props.put("mail.smtp.auth","true");
		props.put("mail.smtp.starttls.enable","true");
		props.put("mail.smtp.host","smtp.gmail.com");
		props.put("mail.smtp.port","587");
		
		Session  session = Session.getInstance(props,new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(senderEmail,senderPassword);
			}
		});
		
		try
		{
			Message msg = new MimeMessage(session);
			
			msg.setFrom(new InternetAddress(senderEmail));
			
			msg.setRecipients(Message.RecipientType.TO,InternetAddress.parse(recipientEmail));
			
			msg.setSubject(subject);
			
			msg.setText(content);
			
			Transport.send(msg);
		}
		catch(Exception e)
		{
			System.out.println("Email Sending Failed! \nError :"+ e.getMessage());
		}
	}
	
	public static void sendEmailtoRecoveryPassword(String recipientEmail,String subject,String content)
	{
		String senderEmail = "thenmozhi565then@gmail.com" ;
		String senderPassword = "ijod rlom ijeb fpmg";
		
		Properties props = new Properties();
		props.put("mail.smtp.auth","true");
		props.put("mail.smtp.starttls.enable","true");
		props.put("mail.smtp.host","smtp.gmail.com");
		props.put("mail.smtp.port","587");
		
		Session  session = Session.getInstance(props,new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(senderEmail,senderPassword);
			}
		});
		
		try
		{
			Message msg = new MimeMessage(session);
			
			msg.setFrom(new InternetAddress(senderEmail));
			
			msg.setRecipients(Message.RecipientType.TO,InternetAddress.parse(recipientEmail));
			
			msg.setSubject(subject);
			
			msg.setText(content);
			
			Transport.send(msg);
		}
		catch(Exception e)
		{
			System.out.println("Email Sending Failed! \nError :"+ e.getMessage());
		}
	}
}
