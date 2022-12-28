package logica.servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import logica.empleados.Empleado;
import logica.empleados.Enfermero;
import logica.empleados.Medico;



public class DtoMapper {
	
	public static Medico toDto(Medico arg) {
		Medico m = new Medico(arg.getCodeEmpleado(), arg.getNombre(), arg.getApellido(), arg.getPass(), arg.gethInicio(), arg.gethFin(), arg.getdInicio(), arg.getdFin(), arg.getdJornada());
		return m;
		
	}

	public static Enfermero toDto(Enfermero arg) {
		Enfermero e = new Enfermero(arg.getCodeEmpleado(), arg.getNombre(), arg.getApellido(), arg.getPass(), arg.gethInicio(), arg.gethFin(), arg.getdInicio(), arg.getdFin(), arg.getdJornada());
		return e;
	}
}
