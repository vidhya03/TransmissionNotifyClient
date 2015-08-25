package com.labkit.transmission.notify.mail;

/**
 *
 * @author Vidhyadharan Deivamani (vidhya) - it.vidhyadharan@gmail.com
 */
import static com.labkit.transmission.notify.CommonConstants.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMailTLS {
private final static Logger LOGGER = Logger.getLogger(SendMailTLS.class.getName()); 

    public static void sendMail(Properties props) throws RuntimeException {
        final String username = System.getProperty(MAIL_USERNAME, props.getProperty(MAIL_USERNAME));
        final String password = System.getProperty(MAIL_PASSWORD, props.getProperty(MAIL_PASSWORD));

        Properties mailProperty = new Properties();
        mailProperty.put(MAIL_SMTP_AUTH,
                System.getProperty(MAIL_SMTP_AUTH ,props.getProperty(MAIL_SMTP_AUTH)));
        mailProperty.put(MAIL_SMTP_STARTTLS_ENABLE,
                System.getProperty(MAIL_SMTP_STARTTLS_ENABLE,props.getProperty(MAIL_SMTP_STARTTLS_ENABLE)));
        mailProperty.put(MAIL_SMTP_HOST,
                System.getProperty(MAIL_SMTP_HOST, props.getProperty(MAIL_SMTP_HOST)));
        mailProperty.put(MAIL_SMTP_PORT,
                System.getProperty(MAIL_SMTP_PORT, props.getProperty(MAIL_SMTP_PORT)));

        Session session = Session.getInstance(mailProperty,
                new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(System.getProperty("user.mail.from",props.getProperty("user.mail.from"))));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(System.getProperty("user.mail.to",props.getProperty("user.mail.to"))));
            
            message.setSubject(props.getProperty(PI_NOTIFY_MAIL_SUBJECT,"Download completed successfully"));
            message.setText(props.getProperty(PI_NOTIFY_MAIL_BODY,"Default subject - please set notifyclient.subject property")+
                    "\r\n Cheers,\r\n RaspberryPI vidhya's kit"
             );

            Transport.send(message);

            System.out.println("Successfully sent");

        } catch (MessagingException e) {
           LOGGER.log(Level.SEVERE, "Message sending failed", e);
            throw new RuntimeException(e);
            
        }
    }
}
