/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.utils;

import com.movilidad.model.NotificacionCorreoConf;
import com.movilidad.model.NotificacionTemplate;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.apache.commons.lang.text.StrSubstitutor;

/**
 *
 * @author Luis Alberto Velez
 *
 *
 *
 */
public class SendMails {

    /**
     *
     * @param config this param is a map type, where it´s possible take several
     * values, like this: host for smtp.gmail.com, port 25 by default, from for
     * the email who send this, connect is for the account witch you established
     * the connection with the server and finally password is obviously
     * @param mailProperties this map has email body data
     * @param asunto takes subject to send
     * @param cuerpo takes the value for body of message
     * @param destinatario takes the value for Recipient
     * @param alias alis for name who sending email
     * @param adjuntos filenames for sending
     *
     */
    private static void send(Map<String, String> config, Map <String,String> mailProperties,
            String asunto, String cuerpo,
            String destinatario,
            String alias,
            List<String> adjuntos) {
        String emails = "";
        try {
            // se obtiene el objeto Session. La configuración es para
            // una cuenta de gmail.
            Properties props = new Properties();
            props.setProperty("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", config.get("host"));
            props.setProperty("mail.smtp.starttls.enable", "true");
            props.setProperty("mail.smtp.port", config.get("port"));
            props.setProperty("mail.smtp.auth", "true");
//            props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

            Session session = Session.getDefaultInstance(props, null);
            // session.setDebug(true);

            // Se compone la parte del texto
            BodyPart texto = new MimeBodyPart();
//            texto.setText(cuerpo);

            String htmlEmail = "";
            StrSubstitutor strSub = null;
            InputStream is = new FileInputStream(config.get("template"));
            htmlEmail = SendMails.fileToString(
                    //                    SendMails.class.getResourceAsStream("MailTemplate.html"),
                    is,
                    "utf-8");
            strSub = new StrSubstitutor(mailProperties);
            htmlEmail = strSub.replace(htmlEmail);

            // Una MultiParte para agrupar texto e imagen.
            MimeMultipart multiParte = new MimeMultipart();
//            multiParte.addBodyPart(adjunto);

            if (adjuntos != null) {
                for (String filePath : adjuntos) {
                    MimeBodyPart attachPart = new MimeBodyPart();

                    try {
                        attachPart.attachFile(filePath);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                    multiParte.addBodyPart(attachPart);
                }
            }
            // Se compone el correo, dando to, from, subject y el
            // contenido.
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(config.get("from"), alias));
            InternetAddress[] myToList = InternetAddress.parse(destinatario);
            message.setRecipients(Message.RecipientType.TO, myToList);

            if (config.get("mailBcc") != null) {
                InternetAddress[] myBccList = InternetAddress.parse(config.get("mailBcc"));
                if (myBccList != null) {
                    message.setRecipients(Message.RecipientType.BCC, myBccList);
                }
            }

//            message.setRecipients(Message.RecipientType.BCC, myBccList);
            message.setSubject(asunto);
            texto.setContent(htmlEmail, "text/html; charset=utf-8");
            multiParte.addBodyPart(texto);

            message.setContent(multiParte);

            // Se envia el correo.
            Transport t = session.getTransport("smtp");
            t.connect(config.get("from"), config.get("password"));
            t.sendMessage(message, message.getAllRecipients());
            t.close();
        } catch (Exception e) {
//            System.out.println("Emails : " + emails);
            e.printStackTrace();
        }
    }

    /**
     * Lee el contenido textual desde un stream de entrada
     *
     * @param input Stream de entrada
     * @param encoding Codificación
     * @return El contenido del stream de entrada
     * @throws IOException Cualquier excepción de Entrada/Salida
     */
    public static String fileToString(InputStream input, String encoding) throws IOException {
        StringWriter sw = new StringWriter();
        InputStreamReader in = new InputStreamReader(input, encoding);

        char[] buffer = new char[1024 * 2];
        int n = 0;
        while (-1 != (n = in.read(buffer))) {
            sw.write(buffer, 0, n);
        }
        return sw.toString();
    }

    /**
     * Método encargado de cargar el un mapa con la data necesaria para enviar
     * un correo. La data es consultada en la base de datos.
     *
     * @return Mapa cargado con la data necesaria.
     */
    public static Map getMailParams(NotificacionCorreoConf conf, NotificacionTemplate template) {
        Map mapa = new HashMap();
        mapa.put("host", conf.getSmtpServer());
        mapa.put("mailBcc", conf.getCc1());
        mapa.put("from", conf.getEmail());
        mapa.put("port", conf.getPuerto().toString());
        mapa.put("password", conf.getPassword());
        mapa.put("template", template.getPath());
        return mapa;
    }

    public static void sendEmail(Map<String, String> config, Map mailProperties,
            String asunto, String cuerpo,
            String destinatario,
            String alias,
            List<String> adjuntos) {
        Runnable iniMail = () -> {
            send(config, mailProperties, asunto, cuerpo, destinatario, alias, adjuntos);
        };
        Thread newTharead = new Thread(iniMail);
        newTharead.start();
    }
}
