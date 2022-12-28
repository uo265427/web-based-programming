/**
 * 
 */
package logica.asignar;

import java.sql.Date;
import java.sql.Time;

import logica.HistorialMedico;
import logica.Vacuna;
import logica.empleados.Empleado;

/**
 * @author María
 *
 */
public class AsignaVacuna extends Asigna{

	private Vacuna vacuna;
	
	public AsignaVacuna(HistorialMedico historial, Empleado empleado, String codAsignacion, Date fecha, Time hora, Vacuna vacuna) {
		super(historial, empleado, codAsignacion, fecha, hora);
		this.vacuna = vacuna;
	}

}
