package net.petafuel.fuelifints.dataaccess.dataobjects.gateway;


import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Arrays;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class email
{

	private final Session session;

	public email(String smtphost, final String username, final String password)
	{
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.host", smtphost);
		session = Session.getInstance(props,
				new javax.mail.Authenticator()
				{
					protected PasswordAuthentication getPasswordAuthentication()
					{
						return new PasswordAuthentication(username, password);
					}
				});
	}

	public void sendMail(String absender, String empfaenger, String subject, String text) throws MessagingException
	{
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(absender));
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(empfaenger));
		message.setSubject(subject);
		message.setText(text);
		Transport.send(message);
	}
}
