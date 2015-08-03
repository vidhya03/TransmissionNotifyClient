package com.labkit.transmission.notify;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

/**
 *
 * @author VDE
 */
public class TransmissionNotifyClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String user = "transmission", passwordTrans = "transmission";
        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(user, passwordTrans);
        Client client = ClientBuilder.newClient()
                .register(feature)
                .register(ResponseFilterTransmissionPi.class);

        Response firstResponse = client.target("http://localhost:9091/transmission/rpc")
                .request().post(Entity.json("{\"method\":\"session-get\"}"));
        String transmission$Session = firstResponse.getHeaderString("X-Transmission-Session-Id");
        System.out.println("transmission$Session = " + transmission$Session);

        final String username = "it.vidhyadharan@gmail.com";
        final String password = "easyujwvqtisypkm";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("PITorrent Notification <it.vidhyadharan@gmail.com>"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("vde@softwareag.com"));
            message.setSubject("Download Completed some text and its name");
            message.setText("Hi,"
                    + "\n\n The content Bat man and the searching team has been downloaded successfully ");

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }

}
