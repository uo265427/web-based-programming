package logica;

import java.sql.SQLException;
import java.util.Random;

import javax.swing.JTextField;

import logica.servicios.ParserBaseDeDatos;

public class Acompañante {

	private String codAcompañante;
	private String codPaciente;
	private String nombre;
	private String apellido;
	private int movil;
	private String email;
	private ParserBaseDeDatos pbd;
	
	
	public Acompañante(String codAcompañante, String codPaciente, String nombre, String apellido, int movil, String email) {
		super();
		this.codAcompañante = codAcompañante;
		this.codPaciente = codPaciente;
		this.nombre = nombre;
		this.apellido = apellido;
		this.movil = movil;
		this.email=email;
	}
	
	public Acompañante(  String nombre, String apellido, int movil, String email) {
		super();
		pbd=new ParserBaseDeDatos();
		try {
			this.codAcompañante =generateRandomCod();
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
		}while(pbd.checkCodeAcompañante(cod));
		
		
		return cod;
		
		
	}

	public String getCodAcompañante() {
		return codAcompañante;
	}


	public void setCodAcompañante(String codAcompañante) {
		this.codAcompañante = codAcompañante;
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
		return "Acompañante "+  nombre +" "+ apellido + "  "+ movil ;
	}
	
	
}
