package logica;

import java.sql.Date;
import java.sql.Time;

public class Causas {
	
	private String nombreCausas;
	
	
	public Causas(String nombreCausas) {
		this.nombreCausas = nombreCausas;
	}
	
	
	public String getNombreVacuna() {
		return nombreCausas;
	}
	
	
	public void setNombreCausas(String nombreCausas) {
		this.nombreCausas = nombreCausas;
	}
	
	@Override
	public String toString() {
		return getNombreVacuna();
	}
	
	
}
