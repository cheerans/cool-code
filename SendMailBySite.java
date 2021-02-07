import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class SendMailBySite {
 public static void main(String[] args) {

   String host="mail.fmsolutions.tech";
   final String user="customerservice@fmsolutions.tech";//change accordingly
   final String password="7dRR5bmKoCQH";//change accordingly
  
   String to="cheeran@hotmail.com";//change accordingly

   //Get the session object
   Properties props = new Properties();
   props.put("mail.smtp.host",host);
   props.put("mail.smtp.auth", "true");

   // enable authentication 
   props.put("mail.smtp.auth", "true");  

   // SSL Port 
   props.put("mail.smtp.port", "465");  
   // SSL Factory 
   props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); 
 
   System.out.println("Sending email ...");
   Session session = Session.getDefaultInstance(props,
    new javax.mail.Authenticator() {
      protected PasswordAuthentication getPasswordAuthentication() {
	return new PasswordAuthentication(user,password);
      }
   });

    //Compose the message
   try {
     MimeMessage message = new MimeMessage(session);
     message.setFrom(new InternetAddress(user));
     message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
     message.setSubject("javatpoint");
     message.setText("This is simple program of sending email using JavaMail API");
     
     //send the message
     Transport.send(message);
     System.out.println("Email Sent Successfully...");
    } catch (MessagingException e) {
	e.printStackTrace();
    }
  }
}
