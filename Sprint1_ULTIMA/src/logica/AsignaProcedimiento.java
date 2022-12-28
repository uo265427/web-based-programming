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
public class AsignaProcedimiento {

	
	private String codAsigProcedimiento;
	private String nombreProcedimiento;
	private String codigoProcedimiento; // El identificador del diagnostico
	private String nHistorial; // El número de historial del paciente a quien le hemos asignado el diagnostico
	private String codEmpleado;
	private Date fecha;
	private Time hora;
	
	
	
	
	/**
	 * Constructor para la clase Asigna procedimiento
	 * 
	 * @param codAsigProcedimiento
	 * @param nombreProcedimiento
	 * @param codigoProcedimiento
	 * @param nHistorial
	 * @param codMedico
	 * @param fecha
	 * @param hora
	 */
	public AsignaProcedimiento(String codAsigProcedimiento, String nombreProcedimiento, String codigoProcedimiento,
			String nHistorial, String codEmpleado, Date fecha, Time hora) {
		this.codAsigProcedimiento = codAsigProcedimiento;
		this.nombreProcedimiento = nombreProcedimiento;
		this.codigoProcedimiento = codigoProcedimiento;
		this.nHistorial = nHistorial;
		this.codEmpleado = codEmpleado;
		this.fecha = fecha;
		this.hora = hora;
	}




	/**
	 * @return the codAsigProcedimiento
	 */
	public String getCodAsigProcedimiento() {
		return codAsigProcedimiento;
	}




	/**
	 * @return the nombreProcedimiento
	 */
	public String getNombreProcedimiento() {
		return nombreProcedimiento;
	}




	/**
	 * @return the codigoProcedimiento
	 */
	public String getCodigoProcedimiento() {
		return codigoProcedimiento;
	}




	/**
	 * @return the nHistorial
	 */
	public String getnHistorial() {
		return nHistorial;
	}




	/**
	 * @return the codMedico
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
