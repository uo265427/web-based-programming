/**
 * 
 */
package logica;

/**
 * @author María
 *
 */
public class Preinscripcion {

	

	
	private String nombre; // PrimaryKey
	private boolean medicamento; // Si es medicamento o no
	

	
	
	
	
	/**
	 * Constructor
	 * @param medicamento
	 * @param descripcionNoMedicamento
	 * @param cantidad
	 * @param intervalo
	 * @param duracion
	 * @param instrucciones
	 */
	public Preinscripcion(String nombre, boolean medicamento) {
		
		this.nombre = nombre;
		this.medicamento = medicamento;
	}






	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}






	/**
	 * @return the medicamento
	 */
	public boolean isMedicamento() {
		return medicamento;
	}






	@Override
	public String toString() {
		return nombre ;
	} 

	
	
	
}
