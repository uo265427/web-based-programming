/**
 * 
 */
package logica;

import java.sql.Time;
import java.util.Date;

/**
 * @author María
 *
 */
public class Correo {

	private String codCorreo;
	private String codMedicoDestino;
	private String codMedicoOrigen;
	private String asunto;
	private String mensaje;
	private Date date;
	private Time time;
	
	
	
	/**
	 * Constructor para la clase correo
	 * 
	 * @param codMedicoDestino
	 * @param codMedicoOrigen
	 * @param asunto
	 * @param mensaje
	 * @param date
	 * @param time
	 */
	public Correo(String codCorreo, String codMedicoDestino, String codMedicoOrigen, String asunto, String mensaje, Date date, Time time) {
		this.codCorreo = codCorreo;
		this.codMedicoDestino = codMedicoDestino;
		this.codMedicoOrigen = codMedicoOrigen;
		this.asunto = asunto;
		this.mensaje = mensaje;
		this.date = date;
		this.time = time;
	}


	

	/**
	 * @return the codCorreo
	 */
	public String getCodCorreo() {
		return codCorreo;
	}




	/**
	 * @return the codMedicoDestino
	 */
	public String getCodMedicoDestino() {
		return codMedicoDestino;
	}



	/**
	 * @return the codMedicoOrigen
	 */
	public String getCodMedicoOrigen() {
		return codMedicoOrigen;
	}



	/**
	 * @return the asunto
	 */
	public String getAsunto() {
		return asunto;
	}



	/**
	 * @return the mensaje
	 */
	public String getMensaje() {
		return mensaje;
	}



	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}



	/**
	 * @return the time
	 */
	public Time getTime() {
		return time;
	}
	
	
	
}
