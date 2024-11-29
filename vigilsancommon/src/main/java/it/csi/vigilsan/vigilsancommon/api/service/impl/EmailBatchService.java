/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.service.impl;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import it.csi.vigilsan.vigilsanutil.generic.services.log.AuditableApiServiceImpl;

@Service
public class EmailBatchService extends AuditableApiServiceImpl {

	@Value("${email.batch.host:default}")
	private String host;
	@Value("${email.batch.port:default}")
	private String port;
	@Value("${email.batch.username:default}")
	private String username;
	@Value("${email.batch.password:default}")
	private String password;

	private static String genericText = """
			<!doctype html>
			<html>
			<head>

			  <meta charset="utf-8">
			  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
			  <title>Mail Salute Piemonte - Regione Piemonte</title>

			  <style>
			    body{
			      background-color:#ececec;
			      margin:0;
			      padding:0;
			      font-family:'Open sans',Helvetica, Arial, Lucida Grande, sans-serif;
			      font-size:18px
			    }
			    table{
			      border-collapse:collapse;
			      max-width:600px;
			      margin:0 auto;
			      table-layout:fixed;
			      font-size:18px
			    }
			    thead td{
			      padding:15px;
			      width:50%;
			    }
			    tbody td{
			      background-color:#ffffff;
			      padding:0;
			    }
			    .content {
			      padding: 10px;
			    }
			    .portale
			    {
			    color: #fff;
			    font-size: 26px;
			    font-weight: bold;
			    }
			    h1{
			      margin:0;
			      font-size:20px;
			    }
			    .bg-grigio{
			      background-color:#eee;
			      font-size:18px;
			      text-align:center;
			      margin:0;
			      padding:20px 20px;
			    }
			    .bg-grigio p{
			      font-size:24px;
			      /*padding:20px 20px;*/
			    }
			    p{
			      line-height: 1.5em;
			      font-size:18px;
			    }
			    a.send,
			    a.send:hover,
			    a.send:active,
			    a.send:visited,
			    a.send:link,
			    a.send:focus{
			      padding:10px 30px;
			      margin:20px 15%;
			      display:block;
			      font-size: 20px;
			      line-height: 1.5;
			      border-radius: 5px;
			      color: #fff;
			      background-color: #005ca9;
			      border-color: #005ca9;
			      font-weight: 400;
			      text-align: center;
			      white-space: nowrap;
			      vertical-align: middle;
			      transition: color .15s ease-in-out,background-color .15s ease-in-out,border-color .15s ease-in-out,box-shadow .15s ease-in-out;
			      cursor:pointer;
			      text-decoration: none;
			      font-weight: bold;
			    }
			    a.send:hover {background-color: #114172}
			    hr{
			      border-top:0 none;
			      margin-top:20px;
			    }
			    h2{
			      font-size:20px;
			    }
			    .alert {
			      position: relative;
			      border: 1px solid transparent;
			      padding: 20px 10px;
			      font-size: 18px;
			    }
			    .alert-primary {
			      color: #000;
			      background-color: #d0e8eb;
			      border-color: #b8daff;
			      font-weight: bold;
			    }
			  </style>
			</head>
			<body>

			<table border="0">
			  <thead>
			  <tr>
			    <td style="background-color:#006cb4">
			      <span class="portale">Piattaforma regionale residenzialità</span>
			    </td>
			  </tr>
			<!--  <td style="background-color:#0381d6">
			      Titolo del Servizio che ha generato il messaggio
			      <h1  style="color:#ffffff">Notificatore contatto digitale</h1>
			  </td>-->

			      <!--/Titolo del Servizio che ha generato il messaggio -->


			  </thead>
			  <tbody>
			  <tr>
			    <td colspan="2">
			      <div class="bg-grigio">
			        <p>""";
	private static String genericText2 = """
			      </p>
			    </div>
			  </td>
			</tr>
			<tr>
			  <td colspan="2" align="center">
			    <div class="content">""";

	private static String genericText3 = """
			      </div>
			      <hr />
			    </td>
			  </tr>

			  <tr>
			    <td colspan="2">
			      <img width="100" height="36" style="margin:10px auto;display:block;" src="http://www.sistemapiemonte.it/ris/salute/template-email/img/logo-regione.png" alt="" />
			    </td>
			  </tr>
			  </tbody>
			</table>

			</body>
			</html>
			""";
//    <p style="font-size:12px;text-align:center;">Questo messaggio ti è stato inviato perchè sei iscritto al sistema di notifiche via mail <br/>di Salute Piemonte. <br>
//    Se non vuoi ricevere questo tipo di mail, accedi a <a href="https://sansol.isan.csi.it/la-mia-salute">Salute Piemonte</a><br/> per modificare le preferenze di notifica.</p>


	public boolean sendMail(Integer sId, String text, String destinatario, String subject) throws MessagingException {

		Properties props = System.getProperties();
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.auth", "true");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
			message.setSubject(subject);
			message.setContent(new StringBuilder(genericText).append(subject).append(genericText2).append(text)
					.append(genericText3).toString(), "text/html");

			// send the message
			Transport.send(message);

		} catch (MessagingException e) {

			logError(sId, "sendMail", "MessagingException: ", e);
			throw e;
		}

		return true;

	}
}
