/**
 * 
 */
package logica;

/**
 * @author María
 *
 */
public class Administrativo {

	
	private String pass;
	private String codAdmin;
	private String Nombre;
	private String Apelliod;
	
	
	
	public Administrativo(String pass, String codAdmin, String nombre, String apelliod) {
		super();
		this.pass = pass;
		this.codAdmin = codAdmin;
		Nombre = nombre;
		Apelliod = apelliod;
	}



	public String getPass() {
		return pass;
	}



	public String getCodAdmin() {
		return codAdmin;
	}



	public String getNombre() {
		return Nombre;
	}



	public String getApelliod() {
		return Apelliod;
	}
	
	
	
	
	//comentario
}
