/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.labkit.transmission.notify;

/**
 *
 * @author Vidhyadharan Deivamani (vidhya) - it.vidhyadharan@gmail.com
 */
public class CommonConstants {
 
    /**
     * {
     *   "method":"torrent-get",
     *   "arguments": 
     *               {
     *                  "fields": [ "id", "name", "totalSize","status","desiredAvailable"]
     *               }
     * }
     */
  final public static String TRANSMISSION_REQUEST = "{\"method\":\"torrent-get\",\n" +
"  \"arguments\": {\"fields\": [ \"id\", \"name\", \"totalSize\",\"status\",\"desiredAvailable\" ]}\n" +
"}";
  final public static String TRANSMISSION_SESSION_ID="X-Transmission-Session-Id";
  final public static String TRANSMISSION_HOST_RPC="transmission.host.rpc";
  final public static String TRANSMISSION_RPC_USERNAME="transmission.username";
  final public static String TRANSMISSION_RPC_PASSWORD="transmission.password";
  final public static String VERSION="version";
  final public static String TRANSMISSION_PI_NOTIFY_DB="transmission.pi.notify.db";
  
  /**LOCAL PROPERTIES*/
  final public static String PI_NOTIFY_MAIL_SUBJECT="notifyclient.mail.subject";
  final public static String PI_NOTIFY_MAIL_BODY="notifyclient.mail.body";
  
  final public static String MAIL_USERNAME="user.mail.username";
  final public static String MAIL_PASSWORD="user.mail.password";
  
  final public static String MAIL_SMTP_AUTH="mail.smtp.auth";
  final public static String MAIL_SMTP_STARTTLS_ENABLE="mail.smtp.starttls.enable";
  final public static String MAIL_SMTP_HOST="mail.smtp.host";
  final public static String MAIL_SMTP_PORT="mail.smtp.port";
  
  
}
