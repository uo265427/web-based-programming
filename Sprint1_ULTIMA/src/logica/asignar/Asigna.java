/**
 * 
 */
package logica.asignar;

import java.sql.Date;
import java.sql.Time;

import logica.HistorialMedico;
import logica.empleados.Empleado;

/**
 * @author María
 *
 */
public abstract class Asigna {

	
	private HistorialMedico historial;
	private Empleado empleado;
	private String codAsignacion;
	private Date fecha;
	private Time hora;
	
	
	
	/**
	 * Constructor
	 * @param historial
	 * @param empleado
	 * @param codAsignacion
	 * @param fecha
	 * @param hora
	 */
	public Asigna(HistorialMedico historial, Empleado empleado, String codAsignacion, Date fecha, Time hora) {
		this.historial = historial;
		this.empleado = empleado;
		this.codAsignacion = codAsignacion;
		this.fecha = fecha;
		this.hora = hora;
	}
}
