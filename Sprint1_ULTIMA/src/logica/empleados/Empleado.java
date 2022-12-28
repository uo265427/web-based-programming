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

public  class Empleado {

	
	public String codeEmpleado;
	public String pass;
	public String nombre;
	public String apellido;
	public Time hInicio;
	public Time hFin;
	public Date dInicio;
	public Date dFin;
	public String dJornada;
	
	
	
	/**
	 * Costructor
	 * @param codeEmpleado
	 * @param pass
	 * @param hInicio2
	 * @param hFin2
	 * @param dInicio
	 * @param dFin
	 * @param dJornada
	 */
	public Empleado(String codeEmpleado,String nombre,String apellido, String pass, Time hInicio2, Time hFin2, Date dInicio, Date dFin, String dJornada) {
		
		this.codeEmpleado = codeEmpleado;
		this.nombre=nombre;
		this.apellido=apellido;
		this.pass = pass;
		this.hInicio = hInicio2;
		this.hFin = hFin2;
		this.dInicio = dInicio;
		this.dFin = dFin;
		this.dJornada = dJornada;
	}
	
	public Empleado(String nombre,String apellido) {
		
		this.nombre=nombre;
		this.apellido=apellido;
	}



	public String getCodeEmpleado() {
		return codeEmpleado;
	}



	public String getPass() {
		return pass;
	}



	public String getNombre() {
		return nombre;
	}



	public String getApellido() {
		return apellido;
	}



	public Time gethInicio() {
		return hInicio;
	}



	public Time gethFin() {
		return hFin;
	}



	public Date getdInicio() {
		return dInicio;
	}



	public Date getdFin() {
		return dFin;
	}



	public String getdJornada() {
		return dJornada;
	}



	public Empleado(String codeEmpleado) {
		this.codeEmpleado = codeEmpleado;
	}
	
	@Override
	public String toString() {
		return nombre +"  " + apellido ;
	}
	
	

}
