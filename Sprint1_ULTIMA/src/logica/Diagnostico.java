/**
 * 
 */
package logica;

/**
 * @author María
 *
 */
public class Diagnostico {
	
	private String numeroDiagnostico; // Numero identificador del diagnostico PRIMARY KEY
	private String nombre; // El nombre del diagnostico
	
	
	
	/**
	 * @param numeroDiagnostico
	 * @param nombre
	 */
	public Diagnostico(String numeroDiagnostico, String nombre) {
		this.numeroDiagnostico = numeroDiagnostico;
		this.nombre = nombre;
	}



	/**
	 * @return the numeroDiagnostico
	 */
	public String getNumeroDiagnostico() {
		return numeroDiagnostico;
	}



	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	
	
}
