package com.labkit.transmission.notify;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import static com.labkit.transmission.notify.CommonConstants.*;
import com.labkit.transmission.notify.entity.Torrents;
import com.labkit.transmission.notify.entity.TransmissionResponse;
import com.labkit.transmission.notify.mail.SendMailTLS;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.Files.newOutputStream;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
/**
 *
 * @author Vidhyadharan Deivamani (vidhya) - it.vidhyadharan@gmail.com
 */
public class TransmissionNotifyClient {
    
private final static Logger LOGGER = Logger.getLogger(TransmissionNotifyClient.class.getName()); 
private final static String _VERSION="1.beta.1";
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    String property = System.getProperty(VERSION);
    if(property !=null){
        System.out.println("Transmission Client Email notify version "+_VERSION);
        System.exit(0);
    }
        Properties props = readProperties();
        String user = System.getProperty(TRANSMISSION_RPC_USERNAME, props.getProperty(TRANSMISSION_RPC_USERNAME)),
                passwordTrans = System.getProperty(TRANSMISSION_RPC_PASSWORD, props.getProperty(TRANSMISSION_RPC_PASSWORD));
        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(user, passwordTrans);
        Client client = ClientBuilder.newClient()
                .register(feature) /* For debug enable the below line*/ 
//                .register(ResponseFilterTransmissionPi.class)
                .register(JacksonJsonProvider.class)
                ;

        
        /**
         * Without session , response will be 409 along with valid session id
         */
        String transmission$Session$Id = TransmissionRequest(props,client, "").getHeaderString(TRANSMISSION_SESSION_ID);
        
         LOGGER.log(Level.INFO, "Received the Session Id {0}", transmission$Session$Id);
         LOGGER.log(Level.INFO, "Passing the session Id  {0} to get the valid response", transmission$Session$Id);
        /**
         * Feedback the session id for valid response
         */
        Response transmissionResponse = TransmissionRequest(props,client, transmission$Session$Id);
        TransmissionResponse entity = transmissionResponse.readEntity(TransmissionResponse.class);
        
        ArrayList<Torrents> torrentList = new ArrayList<>();
        
        Properties dbProps = getDbProperties(props);
        for (Torrents torrents : entity.getArguments().getTorrents()) {
            if(isCompleted(torrents) && !checkIfAlreadyNotified(torrents,dbProps)){
                    contructMailBody(torrents,props);
                    dbProps.put(Integer.toString(torrents.hashCode()),torrents.toString());
                    torrentList.add(torrents);
            }
        }
        /**
         * If no torrents then clear reset the DB file
         */
        if(entity.getArguments().getTorrents().isEmpty()){
            dbProps.clear();
            saveDb(props,dbProps);
        }
        /**
         * Send email if not empty
         */
        if(!torrentList.isEmpty())
        {
           SendMailTLS.sendMail(props);
           saveDb(props,dbProps);
        }else{
            LOGGER.info("No update");
        }
        
    }

    private static boolean isCompleted(Torrents torrents) {
        return torrents.getDesiredAvailable()== 0 && torrents.getStatus()==6;
    }

    private static void contructMailBody(Torrents torrents,Properties props) {
    String body = props.getProperty(PI_NOTIFY_MAIL_BODY, "Hi,\r\n Following items are completed\r\n");
//    String subject = props.getProperty(PI_NOTIFY_MAIL_SUBJECT, "");
     body = body.concat(torrents.getName()+"\r\n");     
     props.put(PI_NOTIFY_MAIL_BODY, body);        
    }

    private static Properties readProperties() {
        //Creating properties files from Java program
        Properties props = new Properties();
 
        // The path of the properties file
//        Path propertyFile = get("src/com/labkit/transmission/notify", "config.properties");
//        Path propertyFile = Paths.get(               );
        
        try (InputStream txtInStream = 
                TransmissionNotifyClient.class.getResourceAsStream("config.properties");
             ) {
            props.load(txtInStream);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error in reading properties ", e);
        }
        return props;
    }

    private static Response TransmissionRequest(Properties props,Client client, String session$Id) {
        /**
         * This request will return 409 along with X-Transmission-Session-Id
         * header
         */
        LOGGER.log(Level.INFO, "Connecting to {0} with session$Id {1}" 
                ,new Object[]{System.getProperty(TRANSMISSION_HOST_RPC, props.getProperty(TRANSMISSION_HOST_RPC)),session$Id});
        Response response = client.target(System.getProperty(TRANSMISSION_HOST_RPC, props.getProperty(TRANSMISSION_HOST_RPC)))
                .request(MediaType.APPLICATION_JSON).header(TRANSMISSION_SESSION_ID, session$Id)
                .post(Entity.json(TRANSMISSION_REQUEST));
        return response;
    }

    private static boolean checkIfAlreadyNotified(Torrents torrents,Properties dbProps) {
           return dbProps.getProperty(""+torrents.hashCode())!=null;
    }

    private static Properties getDbProperties(Properties props) {
        
        final String fileName = System.getProperty(TRANSMISSION_PI_NOTIFY_DB, props.getProperty(TRANSMISSION_PI_NOTIFY_DB));
        final String UserHome = System.getProperty( "user.home" );
        Properties db = new Properties();
         Path path = Paths.get(UserHome,fileName);
         if(Files.exists(path)){
            try {
                db.load(Files.newInputStream(path));
            } catch (IOException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
         }
         return db;
    }

    private static void saveDb(Properties props,Properties dbProps) {
        
        final String fileName = System.getProperty(TRANSMISSION_PI_NOTIFY_DB, props.getProperty(TRANSMISSION_PI_NOTIFY_DB));
        final String UserHome = System.getProperty( "user.home" );
        Properties db = new Properties();
        db.putAll(dbProps);
         Path path = Paths.get(UserHome,fileName);
             try {
                 if(!Files.exists(path)){
                     path = Files.createFile(path);
                 }
                 OutputStream newdbPropsOutStream = newOutputStream(path);
                 db.store(newdbPropsOutStream, "Torrent mail sent list");
            } catch (IOException ex) {
                Logger.getLogger(TransmissionNotifyClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        
       
    }

}
