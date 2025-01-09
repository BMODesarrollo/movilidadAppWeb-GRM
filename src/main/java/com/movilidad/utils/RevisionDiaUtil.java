package com.movilidad.utils;

import com.movilidad.model.CableRevisionDiaRta;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.apache.commons.lang.text.StrSubstitutor;

/**
 *
 * @author solucionesit
 */
public class RevisionDiaUtil {

	/**
	 * Realiza el envío de correo de revisiones en las que se reporten valores por fuera de los limites parametrizados
	 * @param config this param is a map type, where it´s possible take several values, like this: host for smtp.gmail.com, port 25 by default, from for the email who send this, connect is for the account witch you established the connection with the server and finally password is obviously
	 * @param mailProperties this map has email body data
	 * @param asunto takes subject to send
	 * @param cuerpo takes the value for body of message
	 * @param destinatario takes the value for Recipient
	 * @param alias alis for name who sending email
	 * @param respuestas listados de items diligenciados que sobrepasan ó están por debajo del limite de valores
	 *
	 */
	public static void sendEmail(Map<String, String> config, Map mailProperties,
			String asunto, String cuerpo,
			String destinatario,
			String alias,
			List<CableRevisionDiaRta> respuestas) {
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

			Session session = Session.getDefaultInstance(props, null);
			// session.setDebug(true);

			// Se compone la parte del texto
			BodyPart texto = new MimeBodyPart();

			String htmlEmail = "<!DOCTYPE html>\n"
					+ "<html>\n"
					+ "  <head>\n"
					+ "    <!--Import Google Icon Font-->\n"
					+ "    <link\n"
					+ "      href=\"https://fonts.googleapis.com/icon?family=Material+Icons\"\n"
					+ "      rel=\"stylesheet\"\n"
					+ "    />\n"
					+ "    <!--Import materialize.css-->\n"
					+ "    <link\n"
					+ "      rel=\"stylesheet\"\n"
					+ "      href=\"https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css\"\n"
					+ "      media=\"screen,projection\"\n"
					+ "    />\n"
					+ "    <!--Let browser know website is optimized for mobile-->\n"
					+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\n"
					+ "  </head>\n"
					+ "  <body>\n"
					+ "    <table style=\"width: 80%\" align=\"center\">\n"
					+ "      <thead>\n"
					+ "        <tr>\n"
					+ "          <td><img src=\"" + mailProperties.get("logo") + "\" alt=\"\" width=\"104\" height=\"60\" /></td>\n"
					+ "          <td style=\"text-align: center\">\n"
					+ "            <h3>\n"
					+ "              <strong\n"
					+ "                ><span style=\"font-size: large\"\n"
					+ "                  >NOTIFICACION NOVEDAD GENERADA</span\n"
					+ "                ></strong\n"
					+ "              >\n"
					+ "            </h3>\n"
					+ "          </td>\n"
					+ "        </tr>\n"
					+ "      </thead>\n"
					+ "      <tbody>\n"
					+ "        <tr>\n"
					+ "          <td style=\"width: 30%; text-align: left\"><strong>PROCESO</strong></td>\n"
					+ "          <td style=\"width: 70%; text-align: left\"><strong>ESTADO</strong></td>\n"
					+ "        </tr>\n"
					+ "        <tr>\n"
					+ "          <td\n"
					+ "            style=\"width: 30%; text-align: left\"\n"
					+ "            class=\"white-text light-blue darken-4\"\n"
					+ "          >\n"
					+ "            <strong>Fecha novedad:&nbsp;</strong>\n"
					+ "          </td>\n"
					+ "          <td style=\"width: 70%; text-align: left\">" + mailProperties.get("fecha") + "</td>\n"
					+ "        </tr>\n"
					+ "        <tr>\n"
					+ "          <td\n"
					+ "            style=\"width: 30%; text-align: left\"\n"
					+ "            class=\"white-text light-blue darken-4\"\n"
					+ "          >\n"
					+ "            <strong>Estación :&nbsp;</strong>\n"
					+ "          </td>\n"
					+ "          <td style=\"width: 70%; text-align: left\">" + mailProperties.get("estacion") + "</td>\n"
					+ "        </tr>\n"
					+ "        <tr>\n"
					+ "          <td\n"
					+ "            style=\"width: 30%; text-align: left\"\n"
					+ "            class=\"white-text light-blue darken-4\"\n"
					+ "          >\n"
					+ "            <strong>Generada por :&nbsp;</strong>\n"
					+ "          </td>\n"
					+ "          <td style=\"width: 70%; text-align: left\">" + mailProperties.get("username") + "</td>\n"
					+ "        </tr>\n"
					+ "        <tr>\n"
					+ "          <td\n"
					+ "            style=\"width: 30%; text-align: left\"\n"
					+ "            class=\"white-text light-blue darken-4\"\n"
					+ "          >\n"
					+ "            <strong>Generada :&nbsp;</strong>\n"
					+ "          </td>\n"
					+ "          <td style=\"width: 70%; text-align: left\">" + mailProperties.get("generada") + "</td>\n"
					+ "        </tr>\n"
					+ "      </tbody>\n"
					+ "    </table>\n"
					+ "\n"
					+ "    <div align=\"center\" style=\"margin-top: 35px;\">\n"
					+ "      <h3 class=\"flow-text\">INFORMACIÓN ADICIONAL</h3>\n"
					+ "    </div>\n"
					+ "\n"
					+ "    <div align=\"center\" style=\"margin-bottom: 25px;\">\n"
					+ "      <table style=\"width: 80%\" class=\"responsive-table\">\n"
					+ "        <thead>\n"
					+ "          <tr>\n"
					+ "            <th style=\"text-align: left\">Equipo</th>\n"
					+ "            <th style=\"text-align: left\">Actividad</th>\n"
					+ "            <th style=\"text-align: left\">Hora</th>\n"
					+ "            <th style=\"text-align: left\">Limite Inferior</th>\n"
					+ "            <th style=\"text-align: left\">Limite Superior</th>\n"
					+ "            <th style=\"text-align: left\">Vr Registrado</th>\n"
					+ "          </tr>\n"
					+ "        </thead>\n"
					+ "        <tbody>\n";
			for (CableRevisionDiaRta item : respuestas) {
				htmlEmail += "          <tr>\n"
						+ "            <td>" + item.getIdCableRevisionEstacion().getIdCableRevisionEquipo().getNombre() + "</td>\n"
						+ "            <td>" + item.getIdCableRevisionEstacion().getIdCableRevisionActividad().getNombre() + "</td>\n"
						+ "            <td>" + item.getIdCableRevisionDiaHorario().getHora() + "</td>\n"
						+ "            <td>" + item.getIdCableRevisionEstacion().getIdCableRevisionActividad().getLimiteInferior() + "</td>\n"
						+ "            <td>" + item.getIdCableRevisionEstacion().getIdCableRevisionActividad().getLimiteSuperior() + "</td>\n"
						+ "            <td style=\"color: red; font-weight: bold\">" + item.getRespuesta() + "</td>\n"
						+ "          </tr>\n";
			}
			htmlEmail += "        </tbody>\n"
					+ "      </table>\n"
					+ "    </div>\n"
					+ "    <script src=\"https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js\"></script>\n"
					+ "  </body>\n"
					+ "</html>";

			// Una MultiParte para agrupar texto e imagen.
			MimeMultipart multiParte = new MimeMultipart();
//            multiParte.addBodyPart(adjunto);

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
			e.printStackTrace();
		}
	}
}
