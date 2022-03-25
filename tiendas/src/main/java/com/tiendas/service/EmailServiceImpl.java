package com.tiendas.service;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.tiendas.bean.EmailBean;

@Service
public class EmailServiceImpl implements IEmailService {

	
	@Autowired
	private Environment env;
	
	public boolean enviarEmail(EmailBean emailBean) {
		Properties props = new Properties();
		props.setProperty("mail.smtp.host", env.getProperty("app.email.server.host"));
		props.setProperty("mail.smtp.starttls.enable", "false");
		props.setProperty("mail.smtp.port", env.getProperty("app.email.server.port"));
		props.put("mail.smtp.starttls.enable", "true");
		props.setProperty("mail.transport.protocol", "smtp");
		props.put("mail.smtp.ssl.protocols", "TLSv1.2");
		
		Session session = Session.getDefaultInstance(props,null);
		MimeMessage mensaje = new MimeMessage(session); //Aqui voy a crear todo el correo que quiero enviar
		try {
			mensaje.setFrom(new InternetAddress(env.getProperty("app.email.from"))); //Emisor
			mensaje.addRecipient(Message.RecipientType.TO, new InternetAddress(emailBean.getsEmailTo())); //Receptor
			mensaje.setSubject(emailBean.getsSubject());
			MimeBodyPart mensajeCuerpo = new MimeBodyPart(); //Cuerpo del correo a enviar
			String htmlMensaje = "<html>" +
                    "<head>" +
                    "<meta charset=\"utf-8\">" +
                    "<title>Tiendas| Mensaje de Activación</title>"+
                    "</head>" +
                    "<body style=\"font-family: Helvetica, Arial, sans-serif; background-color:#eeeeee; margin:0; padding:0; color:#000000;\">" +
                    "<table width=\"100%\" bgcolor=\"#fff\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">" +
                    "<tbody>" +
                    "<tr>" +
                    "<td>" +
                    "<table cellpadding=\"0\" cellspacing=\"0\" width=\"600\" border=\"0\" align=\"center\">" +
                    "<tbody>" +
                    "<tr>" +
                    "<td>" +
                    "<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">" +
                    "<tbody>" +
                    "<tr>" +
                    "<p style=\"margin:20px 20px 20px; text-align:left; font-size:16px; line-height:22px; color:#000000; font-family: verdana\">" +
                    "Buen día,&nbsp;<p style=\"margin:20px 20px 20px; text-align:left; font-size:18px; line-height:22px; color:#000000; font-family: verdana\">" + emailBean.getNombreSolicitante() +
                    "</p>" +
                    "<p style=\"margin:20px 20px 20px; text-align:left; font-size:16px; line-height:22px; color:#000000; font-family: verdana\">" +
                    "Para realizar la activación de su cuenta debe dar click en el siguiente link: " +
                    "</p>" +
                    "<br>" +
                    "<p style=\"margin:20px 20px 20px; text-align:left; font-size:16px; line-height:22px; color:#000000; font-family: verdana\">" +
                    env.getProperty("app.url.activacion") + emailBean.getsEmailTo() + 
                    "</p>" +
                    "<br>" +
                    "<p style=\"margin:20px 20px 20px; text-align:left; font-size:16px; line-height:22px; color:#000000; font-family: verdana\">" +
                    "Para cualquier consulta puede contactarnos al:" +                
                    "</p>" +
                    "<p style=\"margin:20px 20px 20px; text-align:left; font-size:16px; line-height:22px; color:#000000; font-family: verdana\">" +
                    "Celular: 989 555 343" +
                    "</p>" +
                    "<p style=\"margin:20px 20px 20px; text-align:left; font-size:16px; line-height:22px; color:#000000; font-family: verdana\">" +
                    "<a href=\"mailto:zeeke18@gmail.com\">Administrador</a>" +
                    "</p>" +
                    "</tbody>" +
                    "</table>" +
                    "</tbody>" +
                    "</table>" +
                    "</tbody>" +
                    "</body>" +
                    "</html>";
			mensajeCuerpo.setText(htmlMensaje,"UTF-8","HTML");
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(mensajeCuerpo);
			mensaje.setContent(multipart);
			
			Transport t = session.getTransport("smtp");
			t.connect(env.getProperty("app.email.user"), env.getProperty("app.email.password"));
			t.sendMessage(mensaje, mensaje.getAllRecipients());
			t.close();
			return true;
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean enviarEmailRecuperarContrasenha(EmailBean emailBean) {
		Properties props = new Properties();
		props.setProperty("mail.smtp.host", env.getProperty("app.email.server.host"));
		props.setProperty("mail.smtp.starttls.enable", "false");
		props.setProperty("mail.smtp.port", env.getProperty("app.email.server.port"));
		props.put("mail.smtp.starttls.enable", "true");
		props.setProperty("mail.transport.protocol", "smtp");
		props.put("mail.smtp.ssl.protocols", "TLSv1.2");
		
		Session session = Session.getDefaultInstance(props,null);
		MimeMessage mensaje = new MimeMessage(session); //Aqui voy a crear todo el correo que quiero enviar
		try {
			mensaje.setFrom(new InternetAddress(env.getProperty("app.email.from"))); //Emisor
			mensaje.addRecipient(Message.RecipientType.TO, new InternetAddress(emailBean.getsEmailTo())); //Receptor
			mensaje.setSubject(emailBean.getsSubject());
			MimeBodyPart mensajeCuerpo = new MimeBodyPart(); //Cuerpo del correo a enviar
			String htmlMensaje = "<html>" +
                    "<head>" +
                    "<meta charset=\"utf-8\">" +
                    "<title>Isilweb| Recuperar Contraseña</title>"+
                    "</head>" +
                    "<body style=\"font-family: Helvetica, Arial, sans-serif; background-color:#eeeeee; margin:0; padding:0; color:#000000;\">" +
                    "<table width=\"100%\" bgcolor=\"#fff\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">" +
                    "<tbody>" +
                    "<tr>" +
                    "<td>" +
                    "<table cellpadding=\"0\" cellspacing=\"0\" width=\"600\" border=\"0\" align=\"center\">" +
                    "<tbody>" +
                    "<tr>" +
                    "<td>" +
                    "<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">" +
                    "<tbody>" +
                    "<tr>" +
                    "<p style=\"margin:20px 20px 20px; text-align:left; font-size:16px; line-height:22px; color:#000000; font-family: verdana\">" +
                    "Buen día,&nbsp;<p style=\"margin:20px 20px 20px; text-align:left; font-size:18px; line-height:22px; color:#000000; font-family: verdana\">" + emailBean.getNombreSolicitante() +
                    "</p>" +
                    "<p style=\"margin:20px 20px 20px; text-align:left; font-size:16px; line-height:22px; color:#000000; font-family: verdana\">" +
                    "Se ha recibido su solicitud de recuperar su contraseña, para modificar la contraseña deve dar click en el sigueinte link: " +
                    "</p>" +
                    "<br>" +
                    "<p style=\"margin:20px 20px 20px; text-align:left; font-size:16px; line-height:22px; color:#000000; font-family: verdana\">" +
                    env.getProperty("app.url.recuperacion") + emailBean.getsEmailTo()+
                    "</p>" +
                    "<br>" +
                    "<p style=\"margin:20px 20px 20px; text-align:left; font-size:16px; line-height:22px; color:#000000; font-family: verdana\">" +
                    "Para cualquier consulta puede contactarnos al:" +                
                    "</p>" +
                    "<p style=\"margin:20px 20px 20px; text-align:left; font-size:16px; line-height:22px; color:#000000; font-family: verdana\">" +
                    "Celular: 123 456 789" +
                    "</p>" +
                    "<p style=\"margin:20px 20px 20px; text-align:left; font-size:16px; line-height:22px; color:#000000; font-family: verdana\">" +
                    "<a href=\"mailto:zeeke18@gmail.com\">Administrador</a>" +
                    "</p>" +
                    "</tbody>" +
                    "</table>" +
                    "</tbody>" +
                    "</table>" +
                    "</tbody>" +
                    "</body>" +
                    "</html>";
			mensajeCuerpo.setText(htmlMensaje,"UTF-8","HTML");
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(mensajeCuerpo);
			mensaje.setContent(multipart);
			
			Transport t = session.getTransport("smtp");
			t.connect(env.getProperty("app.email.user"), env.getProperty("app.email.password"));
			t.sendMessage(mensaje, mensaje.getAllRecipients());
			t.close();
			return true;
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean enviarEmailAperturaTienda(EmailBean emailBean) {
		Properties props = new Properties();
		props.setProperty("mail.smtp.host", env.getProperty("app.email.server.host"));
		props.setProperty("mail.smtp.starttls.enable", "false");
		props.setProperty("mail.smtp.port", env.getProperty("app.email.server.port"));
		props.put("mail.smtp.starttls.enable", "true");
		props.setProperty("mail.transport.protocol", "smtp");
		props.put("mail.smtp.ssl.protocols", "TLSv1.2");
		
		Session session = Session.getDefaultInstance(props,null);
		MimeMessage mensaje = new MimeMessage(session); //Aqui voy a crear todo el correo que quiero enviar
		try {
			mensaje.setFrom(new InternetAddress(env.getProperty("app.email.from"))); //Emisor
			mensaje.addRecipient(Message.RecipientType.TO, new InternetAddress(emailBean.getsEmailTo())); //Receptor
			mensaje.setSubject(emailBean.getsSubject());
			MimeBodyPart mensajeCuerpo = new MimeBodyPart(); //Cuerpo del correo a enviar
			String htmlMensaje = "<html>" +
                    "<head>" +
                    "<meta charset=\"utf-8\">" +
                    "<title>Apertura de Tienda</title>"+
                    "</head>" +
                    "<body style=\"font-family: Helvetica, Arial, sans-serif; background-color:#eeeeee; margin:0; padding:0; color:#000000;\">" +
                    "<table width=\"100%\" bgcolor=\"#fff\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">" +
                    "<tbody>" +
                    "<tr>" +
                    "<td>" +
                    "<table cellpadding=\"0\" cellspacing=\"0\" width=\"600\" border=\"0\" align=\"center\">" +
                    "<tbody>" +
                    "<tr>" +
                    "<td>" +
                    "<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">" +
                    "<tbody>" +
                    "<tr>" +
                    "<p style=\"margin:20px 20px 20px; text-align:left; font-size:16px; line-height:22px; color:#000000; font-family: verdana\">" +
                    "Buen día,&nbsp;<p style=\"margin:20px 20px 20px; text-align:left; font-size:18px; line-height:22px; color:#000000; font-family: verdana\">" + emailBean.getNombreSolicitante() +
                    "</p>" +
                    "<p style=\"margin:20px 20px 20px; text-align:left; font-size:16px; line-height:22px; color:#000000; font-family: verdana\">" +
                    "Creemo que importante comunicartela apertura de una nueva tienda" +
                    "</p>" +
                    "<br>" + 
                    "<p style=\"margin:20px 20px 20px; text-align:left; font-size:16px; line-height:22px; color:#000000; font-family: verdana\">" +
                    "Para cualquier consulta puede contactarnos al:" +                
                    "</p>" +
                    "<p style=\"margin:20px 20px 20px; text-align:left; font-size:16px; line-height:22px; color:#000000; font-family: verdana\">" +
                    "Celular: 123 456 789" +
                    "</p>" +
                    "<p style=\"margin:20px 20px 20px; text-align:left; font-size:16px; line-height:22px; color:#000000; font-family: verdana\">" +
                    "<a href=\"mailto:zeeke18@gmail.com\">Administrador</a>" +
                    "</p>" +
                    "</tbody>" +
                    "</table>" +
                    "</tbody>" +
                    "</table>" +
                    "</tbody>" +
                    "</body>" +
                    "</html>";
			mensajeCuerpo.setText(htmlMensaje,"UTF-8","HTML");
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(mensajeCuerpo);
			mensaje.setContent(multipart);
			
			Transport t = session.getTransport("smtp");
			t.connect(env.getProperty("app.email.user"), env.getProperty("app.email.password"));
			t.sendMessage(mensaje, mensaje.getAllRecipients());
			t.close();
			return true;
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
}
