/**
 * 
 */
package logica;

import java.sql.SQLException;
import java.util.Random;

import logica.servicios.ParserBaseDeDatos;

/**
 * @author María
 *
 */
public class Paciente {

	private ParserBaseDeDatos pbd;
	private String codePaciente;
	private String nombre;
	private String apellido;
	private int movil;
	private String email;
	private String info;
	private String historial;
	/**
	 * @param codePaciente
	 * @param nombre
	 * @param apellido
	 * @param movil
	 * @param email
	 * @param info
	 * @param historial
	 */
	public Paciente(String codePaciente, String nombre, String apellido, int movil, String email, String info, String historial) {
		
		this.codePaciente = codePaciente;
		this.nombre = nombre;
		this.apellido = apellido;
		this.movil = movil;
		this.email = email;
		this.info = info;
		this.historial = historial;
	}
	
	
	public Paciente( String nombre, String apellido, int movil, String email, String historial) {
		pbd=new ParserBaseDeDatos();

		try {
			this.codePaciente = generaCodPaciente();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.nombre = nombre;
		this.apellido = apellido;
		this.movil = movil;
		this.email = email;
		this.historial = historial;
	}

	public String generaCodPaciente() throws SQLException {
		String cod="";
		do {
			 cod= new Random().nextInt(100000)+"";
			}while(pbd.checkCodePaciente(cod));
		return cod;
				
	}
	public String getEmail() {
		return email;
	}


	public String getInfo() {
		return info;
	}


	public String getHistorial() {
		return historial;
	}


	public String getCodePaciente() {
		return codePaciente;
	}


	public void setCodePaciente(String codePaciente) {
		this.codePaciente = codePaciente;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getApellido() {
		return apellido;
	}


	public void setApellido(String apellido) {
		this.apellido = apellido;
	}


	@Override
	public String toString() {
		return " Paciente: "+   nombre +" "+ apellido + "  Nº historial; " + historial;
	}


	public int getMovil() {
		return movil;
	}
	
}
