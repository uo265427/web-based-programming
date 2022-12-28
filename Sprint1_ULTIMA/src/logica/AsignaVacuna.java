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
public class AsignaVacuna {

	
	private String codVacuna; // Lo que me identifica la vacuna
	private String nombreVacuna;
	private String codEmpleado;
	private String codHistorial;
	
	private Date date;
	private Time time;
	
	
	
	
	/**
	 * Constructor para la clase AsignaVacuna
	 * 
	 * @param codVacuna
	 * @param codEmpleado
	 * @param codHistorial
	 * @param date
	 * @param time
	 */
	public AsignaVacuna(String codVacuna, String nombreVacuna, String codHistorial, String codEmpleado, Date date, Time time) {
		this.codVacuna = codVacuna;
		this.nombreVacuna = nombreVacuna;
		this.codEmpleado = codEmpleado;
		this.codHistorial = codHistorial;
		this.date = date;
		this.time = time;
	}




	/**
	 * @return the nombreVacuna
	 */
	public String getNombreVacuna() {
		return nombreVacuna;
	}




	/**
	 * @return the codVacuna
	 */
	public String getCodVacuna() {
		return codVacuna;
	}




	/**
	 * @return the codEmpleado
	 */
	public String getCodEmpleado() {
		return codEmpleado;
	}




	/**
	 * @return the codHistorial
	 */
	public String getCodHistorial() {
		return codHistorial;
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
