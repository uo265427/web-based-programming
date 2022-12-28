package logica;

import java.sql.Time;
import java.util.Date;

public class Accion {

	public String naccion;
	public String codaccion;
	public String mensajeAccion;
	private Date date;
	private Time hora;
	
	
	public Accion(String naccion, String codaccion,  Date date, Time hora, String mensajeAccion) {
		this.naccion = naccion;
		this.codaccion = codaccion;
		this.mensajeAccion = mensajeAccion;
		this.date = date;
		this.hora = hora;
	}
	
	public String getNaccion() {
		return naccion;
	}
	public void setNaccion(String naccion) {
		this.naccion = naccion;
	}
	public String getCodaccion() {
		return codaccion;
	}
	public void setCodaccion(String codaccion) {
		this.codaccion = codaccion;
	}
	public String getMensajeAccion() {
		return mensajeAccion;
	}
	public void setMensajeAccion(String mensajeAccion) {
		this.mensajeAccion = mensajeAccion;
	}

	
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Time getHora() {
		return hora;
	}

	public void setHora(Time hora) {
		this.hora = hora;
	}

	@Override
	public String toString() {
		return "Accion [naccion=" + naccion + ", codaccion=" + codaccion + ", mensajeAccion=" + mensajeAccion + "]";
	}
	
	
	
}
