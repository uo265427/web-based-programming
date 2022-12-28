package logica;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Email {

	public static void enviarCorreo(String correoRemitente, String contrase�aRemitente, String correoRecibe, Paciente pacienteCita, Cita cita) {
		Properties prop = System.getProperties();
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.user", correoRemitente);
		prop.put("mail.smtp.clave", contrase�aRemitente);
		prop.put("mail.smtp.auth", true);
		prop.put("mail.smtp.starttls.enable", "true");
		prop.put("mail.smtp.port", "587");
		
		Session se = Session.getDefaultInstance(prop);
		MimeMessage message = new MimeMessage(se);
		
		try {
			message.setFrom(new InternetAddress(correoRecibe));
			message.addRecipients(Message.RecipientType.TO, correoRecibe);
			message.setSubject("Cita urgente");
			message.setText("El paciente " + pacienteCita.getNombre() + pacienteCita.getApellido() + " tiene una cita urgente el dia " + cita.getDate() + " en la sala " + cita.getUbicacion());
			Transport trans = se.getTransport("smtp");
			trans.connect("smtp.gmail.com", correoRemitente, contrase�aRemitente);
			trans.sendMessage(message, message.getAllRecipients());
			trans.close();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void enviarEDO(String correoRemitente, String contrase�aRemitente, String correoRecibe, Paciente pacienteCita, Cita cita, String enfermedad) {
		Properties prop = System.getProperties();
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.user", correoRemitente);
		prop.put("mail.smtp.clave", contrase�aRemitente);
		prop.put("mail.smtp.auth", true);
		prop.put("mail.smtp.starttls.enable", "true");
		prop.put("mail.smtp.port", "587");
		
		Session se = Session.getDefaultInstance(prop);
		MimeMessage message = new MimeMessage(se);
		
		try {
			message.setFrom(new InternetAddress(correoRecibe));
			message.addRecipients(Message.RecipientType.TO, correoRecibe);
			message.setSubject("URGENTE: ENFERMEDAD EDO");
			message.setText("Se le notifica que se ha encontrado la enfermedad de declaraci�n obligatoria " + enfermedad + " el d�a " + cita.getDate() + " a las " + 
									cita.gethInicio() + " en el paciente " + pacienteCita.getNombre() + " " + pacienteCita.getApellido() + ", con n�mero de historial " +
									pacienteCita.getHistorial() + ". Por favor avisese de inmediato a la organizaci�n competente.");
			Transport trans = se.getTransport("smtp");
			trans.connect("smtp.gmail.com", correoRemitente, contrase�aRemitente);
			trans.sendMessage(message, message.getAllRecipients());
			trans.close();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
