package logica;

import java.util.Date;

public class Vacaciones {

	public String codVad;
	public String codMed;
	public String codAdmin;
	public Date dInicio;
	public Date dFinal;
	
	public Vacaciones(String codVad, String codMed, String codAdmin, Date dInicio, Date dFinal) {
		this.codVad = codVad;
		this.codMed = codMed;
		this.codAdmin = codAdmin;
		this.dInicio= dInicio;
		this.dFinal= dFinal;
	}
	

	public String getCodVad() {
		return codVad;
	}



	public void setCodVad(String codVad) {
		this.codVad = codVad;
	}



	public String getCodMed() {
		return codMed;
	}

	public void setCodMed(String codMed) {
		this.codMed = codMed;
	}

	public Date getdInicio() {
		return dInicio;
	}

	public void setdInicio(Date dInicio) {
		this.dInicio = dInicio;
	}

	public Date getdFinal() {
		return dFinal;
	}

	public void setdFinal(Date dFinal) {
		this.dFinal = dFinal;
	}

	public String getCodAdmin() {
		return codAdmin;
	}

	public void setCodAdmin(String codAdmin) {
		this.codAdmin = codAdmin;
	}
	
	
	
	
}
