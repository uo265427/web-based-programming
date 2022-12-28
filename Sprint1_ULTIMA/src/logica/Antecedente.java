/**
 * 
 */
package logica;

/**
 * @author María
 *
 */
public class Antecedente {

	
	private String codAntecedente;
	private String nombreAntecedente;
	
	
	
	
	/**
	 * Constructor  para la clase antecedente
	 * 
	 * @param codAntecedente
	 * @param nombreAntecedente
	 */
	public Antecedente(String codAntecedente, String nombreAntecedente) {
		this.codAntecedente = codAntecedente;
		this.nombreAntecedente = nombreAntecedente;
	}




	/**
	 * @return the codAntecedente
	 */
	public String getCodAntecedente() {
		return codAntecedente;
	}




	/**
	 * @return the nombreAntecedente
	 */
	public String getNombreAntecedente() {
		return nombreAntecedente;
	}
	
}
