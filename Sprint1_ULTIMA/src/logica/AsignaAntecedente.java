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
public class AsignaAntecedente {

	
	private String codAsigAntecedente;
	private String nombreAntecedente;
	private String codAntecedente; // El identificador del diagnostico
	private String nHistorial; // El número de historial del paciente a quien le hemos asignado el diagnostico
	private String codEmpleado;
	private Date fecha;
	private Time hora;
	
	
	
	/**
	 * 
	 * Constructor para la clase AsignaAntecedente
	 * 
	 * @param codAsigAntecedente
	 * @param nombreAntecedente
	 * @param codAntecedente
	 * @param nHistorial
	 * @param codEmpleado
	 * @param fecha
	 * @param hora
	 */
	public AsignaAntecedente(String codAsigAntecedente, String nombreAntecedente, String codAntecedente,
			String nHistorial, String codEmpleado, Date fecha, Time hora) {
		this.codAsigAntecedente = codAsigAntecedente;
		this.nombreAntecedente = nombreAntecedente;
		this.codAntecedente = codAntecedente;
		this.nHistorial = nHistorial;
		this.codEmpleado = codEmpleado;
		this.fecha = fecha;
		this.hora = hora;
	}



	/**
	 * @return the codAsigAntecedente
	 */
	public String getCodAsigAntecedente() {
		return codAsigAntecedente;
	}



	/**
	 * @return the nombreAntecedente
	 */
	public String getNombreAntecedente() {
		return nombreAntecedente;
	}



	/**
	 * @return the codAntecedente
	 */
	public String getCodAntecedente() {
		return codAntecedente;
	}



	/**
	 * @return the nHistorial
	 */
	public String getnHistorial() {
		return nHistorial;
	}



	/**
	 * @return the codEmpleado
	 */
	public String getCodEmpleado() {
		return codEmpleado;
	}



	/**
	 * @return the fecha
	 */
	public Date getFecha() {
		return fecha;
	}



	/**
	 * @return the hora
	 */
	public Time getHora() {
		return hora;
	}

	
}
