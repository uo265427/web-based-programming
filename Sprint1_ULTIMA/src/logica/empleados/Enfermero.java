/**
 * 
 */
package logica.empleados;

import java.sql.Time;
import java.sql.Date;

/**
 * @author María
 *
 */
public class Enfermero extends Empleado{

	
	/**
	 * Constructor
	 * @param codeEmpleado
	 * @param pass
	 * @param hInicio
	 * @param hFin
	 * @param dInicio
	 * @param dFin
	 * @param dJornada
	 */
	public Enfermero(String codeEmpleado, String nombre, String apellido, String pass, Time hInicio, Time hFin, Date dInicio, Date dFin, String dJornada) {
		super(codeEmpleado,nombre, apellido, pass, hInicio, hFin, dInicio, dFin, dJornada);	
	}
	
	@Override
	public String toString() {
		return "Enf/enfa" + nombre + " " + apellido;
	}

}
