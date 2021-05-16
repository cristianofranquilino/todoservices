package br.com.todoservices.api;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import br.com.todoservices.base.utils.PropertyUtils;
import br.com.todoservices.entities.model.Login;

public class MailApi {
	
	public static void main(String[] args) throws Exception {
		
		String assunto = "2do - Esqueceu a senha";
		String conteudoHtml = getConteudo("forgot.html");
		
		send("cristiano.franquilino@gmail.com", assunto, conteudoHtml);
		
	}
	
	private static String getConteudo(String htmlName) throws Exception{
		return new String(Files.readAllBytes(Paths.get("templates/" + htmlName)));
	}
	
	public static void sendForgotPass(Login loginUsuario) throws Exception{
		
		String assunto = "2do - Esqueceu a senha";
		String conteudoHtml = getConteudo("forgot.html");
		
		conteudoHtml = conteudoHtml.replace("[nome]", loginUsuario.getUsuario().getNome());
		conteudoHtml = conteudoHtml.replace("[senha]", loginUsuario.getSenha());
		
		System.out.println(conteudoHtml);
		
		send(loginUsuario.getEmail(), assunto, conteudoHtml);
	}
	
	private static ResourceBundle getConfigurations(){
		return PropertyUtils.getResource("email");
	}
	
	private static void send(String to, String assunto, String corpo) {

		ResourceBundle configurations = getConfigurations();
		
		String host = configurations.getString("host").trim(); 
		String port = configurations.getString("port").trim();
		String from = configurations.getString("from").trim();
		String pass = configurations.getString("pass").trim();
		
		Properties props = System.getProperties();
		
		props.put("mail.smtp.starttls.enable", "true"); // added this line
		props.put("mail.smtp.port", port);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.socketFactory.port", port);
	    props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	    props.put("mail.smtp.socketFactory.fallback", "false");
	    
	    props.setProperty("mail.smtp.quitwait", "false");
	    props.setProperty("mail.transport.protocol", "smtp");
		props.setProperty("mail.host", host);
		
		// Get the default Session object.
		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, pass);
            }
          });

		try {
		    // Create a default MimeMessage object.
		    MimeMessage message = new MimeMessage(session);

		    // Set sender
		    message.setFrom(new InternetAddress(from));

		    // Set recipient
		    message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

		    // Set Subject: header field
		    message.setSubject(assunto);

		    // set content and define type
		    message.setContent(corpo, "text/html; charset=utf-8");

		    Transport transport = session.getTransport("smtp");
		    transport.connect(host, from, pass);
		    transport.sendMessage(message, message.getAllRecipients());
		    transport.close();
		    System.out.println("enviado.");
		  } catch (MessagingException mex) {
		    System.out.println(mex.getLocalizedMessage());
		}
	}
}
	
	/*
	public void sendEmail(String fromEmailAddr, String toEmailAddr,String subject, String emailBody) {  

	    String host = "xxx";    
	    final String user = "user";
	    final String password = "password";

	    // Get system properties
	    Properties properties = new Properties();

	    // Setup mail server
	     properties.put("mail.smtp.host", host);
	     properties.put("mail.smtp.port", "25");


	     // Get the default Session object.
	     Session session = Session.getDefaultInstance(properties, null);

	    try{
	         // Create a default MimeMessage object.
	         MimeMessage message = new MimeMessage(session);

	         // Set From: header field of the header.
	         message.setFrom(new InternetAddress(fromEmailAddr));

	         // Set To: header field of the header.
	         message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmailAddr));

	         // Set Subject: header field
	         message.setSubject(subject);

	         // Now set the actual message
	         message.setText(emailBody);

	         // Send message
	         Transport.send(message);
	         System.out.println("Sent message successfully....");
	      }catch (MessagingException mex) {
	         mex.printStackTrace();
	      }
	}
	*/
	

