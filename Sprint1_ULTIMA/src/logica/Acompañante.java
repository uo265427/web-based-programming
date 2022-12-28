package logica;

import java.sql.SQLException;
import java.util.Random;

import javax.swing.JTextField;

import logica.servicios.ParserBaseDeDatos;

public class Acompa�ante {

	private String codAcompa�ante;
	private String codPaciente;
	private String nombre;
	private String apellido;
	private int movil;
	private String email;
	private ParserBaseDeDatos pbd;
	
	
	public Acompa�ante(String codAcompa�ante, String codPaciente, String nombre, String apellido, int movil, String email) {
		super();
		this.codAcompa�ante = codAcompa�ante;
		this.codPaciente = codPaciente;
		this.nombre = nombre;
		this.apellido = apellido;
		this.movil = movil;
		this.email=email;
	}
	
	public Acompa�ante(  String nombre, String apellido, int movil, String email) {
		super();
		pbd=new ParserBaseDeDatos();
		try {
			this.codAcompa�ante =generateRandomCod();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.nombre = nombre;
		this.apellido = apellido;
		this.movil = movil;
		this.email=email;
	}



	

	
	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}
	private String generateRandomCod() throws SQLException {
		String cod;
		do {
		 cod=new Random().nextInt(100000)+"";
		}while(pbd.checkCodeAcompa�ante(cod));
		
		
		return cod;
		
		
	}

	public String getCodAcompa�ante() {
		return codAcompa�ante;
	}


	public void setCodAcompa�ante(String codAcompa�ante) {
		this.codAcompa�ante = codAcompa�ante;
	}


	public String getCodPaciente() {
		return codPaciente;
	}


	public void setCodPaciente(String codPaciente) {
		this.codPaciente = codPaciente;
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


	public int getMovil() {
		return movil;
	}


	public void setMovil(int movil) {
		this.movil = movil;
	}

	@Override
	public String toString() {
		return "Acompa�ante "+  nombre +" "+ apellido + "  "+ movil ;
	}
	
	
}
