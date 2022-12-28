package logica;

public class AsignaEquipo {

	public String codequipo;
	public String numequipo;
	public String codempleado;
	
	public AsignaEquipo(String codequipo, String numequipo, String codempleado) {
		this.codequipo = codequipo;
		this.numequipo = numequipo;
		this.codempleado = codempleado;
	}

	public String getCodequipo() {
		return codequipo;
	}

	public void setCodequipo(String codequipo) {
		this.codequipo = codequipo;
	}

	public String getNumequipo() {
		return numequipo;
	}

	public void setNumequipo(String numequipo) {
		this.numequipo = numequipo;
	}

	public String getCodempleado() {
		return codempleado;
	}

	public void setCodempleado(String codempleado) {
		this.codempleado = codempleado;
	}
	
	
}
