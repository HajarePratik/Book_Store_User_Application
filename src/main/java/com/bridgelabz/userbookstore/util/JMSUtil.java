package com.bridgelabz.userbookstore.util;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

@Component
public class JMSUtil {

	
	public static void sendEmail(String toEmail, String subject, String body) {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		
	    String emailid = System.getenv("email");
	    mailSender.setUsername(emailid);
	 
	    String password = System.getenv("password");
	    mailSender.setPassword(password);
	    
		Properties props = mailSender.getJavaMailProperties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		Authenticator auth = new Authenticator() 
		{
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(emailid,password);
			}
		};
		Session session = Session.getInstance(props,auth);
		try
		{
			MimeMessage message = new MimeMessage(session);
			message.setFrom(emailid);
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
			message.setSubject(subject);
			message.setText(body);
			Transport.send(message);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			System.out.println("exception occured when sending the mail");
		}
	}
}
