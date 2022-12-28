/**
 * 
 */
package logica.asignar;

import java.sql.Date;
import java.sql.Time;

import logica.EnfermedadPrevia;
import logica.HistorialMedico;
import logica.empleados.Empleado;

/**
 * @author María
 *
 */
public class AsignaEPrev extends Asigna{
	
	private EnfermedadPrevia ePrevia;
	
	/**
	 * Constructor
	 * @param historial
	 * @param empleado
	 * @param codAsignacion
	 * @param fecha
	 * @param hora
	 */
	public AsignaEPrev(HistorialMedico historial, Empleado empleado, String codAsignacion, Date fecha, Time hora, EnfermedadPrevia ePrevia) {
		super(historial, empleado, codAsignacion, fecha, hora);
		this.ePrevia = ePrevia;
	}
}
