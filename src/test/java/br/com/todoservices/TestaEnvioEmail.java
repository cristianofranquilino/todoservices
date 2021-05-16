package br.com.todoservices;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.tools.development.testing.LocalMailServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class TestaEnvioEmail {

	private final LocalServiceTestHelper helper =
		    new LocalServiceTestHelper(new LocalMailServiceTestConfig());
	
	@Before
	public void setUp() {
	    helper.setUp();
	}

	@After
	public void tearDown() {
	    helper.tearDown();
	}
	
	@Test
	public void testaEnvioEmailPadrao(){
		Properties props = new Properties();
        /** Parâmetros de conexão com servidor Gmail */
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getDefaultInstance(props,
                    new javax.mail.Authenticator() {
                         protected PasswordAuthentication getPasswordAuthentication() 
                         {
                               return new PasswordAuthentication("cristiano.franquilino@gmail.com", "Cristhug2703");
                         }
                    });
        /** Ativa Debug para sessão */
        session.setDebug(true);
        try {

              Message message = new MimeMessage(session);
              message.setFrom(new InternetAddress("cristiano.franquilino@gmail.com")); //Remetente


              Address[] toUser = InternetAddress //Destinatário(s)
                         .parse("cristiano.franquilino@gmail.com, cristiano@2doservices.com.br, cristiano_franquilino@hotmail.com");  
              message.setRecipients(Message.RecipientType.TO, toUser);
              message.setSubject("Enviando email com JavaMail");//Assunto
              message.setText("Enviei este email utilizando JavaMail com minha conta GMail!");
              /**Método para enviar a mensagem criada*/
              Transport.send(message);
              System.out.println("Feito!!!");
         } catch (MessagingException e) {
              throw new RuntimeException(e);
        }
	}
	
	
}
