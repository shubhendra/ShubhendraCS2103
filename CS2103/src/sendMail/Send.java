package sendMail;

import java.io.UnsupportedEncodingException;
import java.util.Properties;  
import java.util.logging.Level;  
import java.util.logging.Logger;  
import javax.mail.Authenticator;  
import javax.mail.Message;  
import javax.mail.MessagingException;  
import javax.mail.PasswordAuthentication;  
import javax.mail.Session;  
import javax.mail.Transport;  
import javax.mail.internet.InternetAddress;  
import javax.mail.internet.MimeMessage;  
public class Send {
	private String SMTP_HOST = "smtp.gmail.com";
	private String FROM_ADDRESS="jid.troubleshoot@gmail.com";
	private String PASSWORD="jotitdown";
	private String FROM_NAME="Nirav Gandhi";
	public boolean sendMail(String recipients,String subject,String message)
	{
		try
		{
		Properties props = new Properties();  
        props.put("mail.smtp.host", SMTP_HOST);  
        props.put("mail.smtp.auth", "true");  
        props.put("mail.debug", "false");  
        props.put("mail.smtp.ssl.enable", "true"); 
        Session session=Session.getInstance(props,new SocialAuth());
        Message msg = new MimeMessage(session);  
        
        InternetAddress from = new InternetAddress(FROM_ADDRESS, FROM_NAME);  
        msg.setFrom(from);  
        InternetAddress toAddress=new InternetAddress(recipients);
        msg.setRecipient(Message.RecipientType.TO, toAddress);
        msg.setSubject(subject);  
        msg.setContent(message,"text/html" );  
        Transport.send(msg);  
        return true;
		}
		catch(UnsupportedEncodingException ex)
		{
			return false;
		}
		catch(MessagingException e)
		{
			return false;
		}
	}
	class SocialAuth extends Authenticator
	{
		protected PasswordAuthentication getPasswordAuthentication(){
			return new PasswordAuthentication(FROM_ADDRESS,PASSWORD);
		}
	}
}
