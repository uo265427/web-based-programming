package logica;

import java.sql.Time;
import java.util.Date;

public class AccionEmpleado {

	public String naccion;
	public String codempleado;
	public String mensajeAccion;
	private Date date;
	private Time hora;
	
	
	public AccionEmpleado(String naccion, String codempleado,  Date date, Time hora, String mensajeAccion) {
		this.naccion = naccion;
		this.codempleado = codempleado;
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
	public String getCodempleado() {
		return codempleado;
	}
	public void setCodempleado(String codempleado) {
		this.codempleado = codempleado;
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
		return "Accion [naccion=" + naccion + ", codempleado=" + codempleado + ", mensajeAccion=" + mensajeAccion + "]";
	}
	
	
	
}
