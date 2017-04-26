/*
  File:				GoogleMail.java
  Author:			djekujieng
  Date:				04/23/2016

  Description:		Send email using GMail SMTP server.
*/
package net.sf.memoranda;

import com.sun.mail.smtp.SMTPTransport;
import java.security.Security;
import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
  Class: 		GoogleMail	
  
  Description:	Send e-mail using Google's GMAIL SMTP server
*/
public class GoogleMail {
    private GoogleMail() {
    }


    /**
     * Send email using GMail SMTP server.
     *
     * @param username GMail username
     * @param password GMail password
     * @param recipientEmail TO recipient
     * @param title title of the message
     * @param message message to be sent
     * @throws AddressException if the email address parse failed
     * @throws MessagingException if the connection is dead or not in the connected state or if the message is not a MimeMessage
     */


    public static boolean IsValidEmail(final String e) {
        if (e == null) {
            return false;
        }
        String regexp = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regexp);
        java.util.regex.Matcher matcher = pattern.matcher(e);
        return matcher.matches();
    }
	

	/**
     * Method: 	Send()
	 * Inputs: 	String username, String password, String recipientEmail, 
	 * 			String title, String message
	 * Returns: none
	 * 
	 * Description: This function will send an e-mail using Google's GMAIL SMTP server. 

	 *
	 * @param username the username
	 * @param password the password
	 * @param recipientEmail the recipient email
	 * @param title the title
	 * @param message the message
	 * @throws AddressException the address exception
	 * @throws MessagingException the messaging exception
	 */   

    /**
     * Send email using GMail SMTP server.
     *
     * @param username **GMail username**
     * @param password **GMail password**
     * @param recipientEmail **TO recipient**
     * @param title **Title of the message**
     * @param message **Message to be sent**
     * @throws AddressException if the email address parse failed
     * @throws MessagingException if the connection is dead or not in the connected state or if the message is not a MimeMessage
     */
    public static void Send(final String username, final String password, String recipientEmail, String title, String message) throws AddressException, MessagingException {
        GoogleMail.Send(username, password, recipientEmail, "", title, message);
    }

    
	/**
     * Method: 	Send() - Overloaded, with ccEmail.
	 * Inputs: 	String username, String password, String recipientEmail, 
	 * 			String ccEmail, String title, String message
	 * Returns: none (void)
	 * 
	 * Description: This function will send an e-mail using Google's GMAIL SMTP server. 
	 * 
     */
    public static void Send(final String username, final String password, String recipientEmail, String ccEmail, String title, String message) throws AddressException, MessagingException {
        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

        // Get a Properties object
        Properties props = System.getProperties();
        props.setProperty("mail.smtps.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        props.setProperty("mail.smtps.auth", "true");

        /**
         * If set to false, the QUIT command is sent and the connection is immediately closed. 
		 * If set to true (the default), causes the transport to wait for the response to the 
		 * QUIT command.
		 *
		 * ref :   http://java.sun.com/products/javamail/javadocs/com/sun/mail/smtp/package-summary.html
		 *
         *         http://forum.java.sun.com/thread.jspa?threadID=5205249
         *         smtpsend.java - demo program from javamail
        */
        props.put("mail.smtps.quitwait", "false");

        Session session = Session.getInstance(props, null);

        // -- Create a new message --
        final MimeMessage msg = new MimeMessage(session);

        // -- Set the FROM and TO fields --
        msg.setFrom(new InternetAddress(username + "@gmail.com"));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail, false));

        if (ccEmail.length() > 0) {
            msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(ccEmail, false));
        }

        msg.setSubject(title);
        msg.setText(message, "utf-8");
        msg.setSentDate(new Date());

        SMTPTransport t = (SMTPTransport)session.getTransport("smtps");

        t.connect("smtp.gmail.com", username, password);
        t.sendMessage(msg, msg.getAllRecipients());
        t.close();
    }
}

