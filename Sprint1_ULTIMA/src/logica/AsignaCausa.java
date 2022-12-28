package logica;

import java.sql.Time;
import java.util.Date;

public class AsignaCausa {
	private String codAsigCausa;
	private String nombreCausa;
	private String nHistorial;
	private String codEmpleado;
	private Date date;
	private Time hora;
	
	
	public AsignaCausa(String codAsigCausa, String nombreCausa, String nHistorial, String codEmpleado, Date date,
			Time hora) {
		this.codAsigCausa = codAsigCausa;
		this.nombreCausa = nombreCausa;
		this.nHistorial = nHistorial;
		this.codEmpleado = codEmpleado;
		this.date = date;
		this.hora = hora;
	}


	public String getCodAsigCausa() {
		return codAsigCausa;
	}

	public void setCodAsigCausa(String codAsigCausa) {
		this.codAsigCausa = codAsigCausa;
	}

	public String getNombreCausa() {
		return nombreCausa;
	}

	public void setNombreCausa(String nombreCausa) {
		this.nombreCausa = nombreCausa;
	}

	public String getnHistorial() {
		return nHistorial;
	}

	public void setnHistorial(String nHistorial) {
		this.nHistorial = nHistorial;
	}

	public String getCodEmpleado() {
		return codEmpleado;
	}

	public void setCodEmpleado(String codEmpleado) {
		this.codEmpleado = codEmpleado;
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
	
	
	
}
