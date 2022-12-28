package logica;

import java.sql.Time;
import java.util.Date;

public class AsignaEnfermPrev {
	private String codEnfermPrev;
	private String nombreEnfermPrev;
	private String nHistorial;
	private String codEmpleado;
	private Date date;
	private Time hora;
	
	
	public AsignaEnfermPrev(String codEnfermPrev, String nombreEnfermPrev, String nHistorial, String codEmpleado, Date date,
			Time hora) {
		this.codEnfermPrev = codEnfermPrev;
		this.nombreEnfermPrev = nombreEnfermPrev;
		this.nHistorial = nHistorial;
		this.codEmpleado = codEmpleado;
		this.date = date;
		this.hora = hora;
	}


	public String getCodEnfermPrev() {
		return codEnfermPrev;
	}

	public void setCodEnfermPrev(String codEnfermPrev) {
		this.codEnfermPrev = codEnfermPrev;
	}

	public String getNombreEnfermPrev() {
		return nombreEnfermPrev;
	}
	
	
	public void setNombreEnfermPrev(String nombreEnfermPrev) {
		this.nombreEnfermPrev = nombreEnfermPrev;
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
