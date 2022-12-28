/**
 * 
 */
package logica;

import java.sql.SQLException;
import java.util.Random;

import logica.servicios.ParserBaseDeDatos;

/**
 * @author María
 *
 */
public class HistorialMedico {

	private ParserBaseDeDatos pbd;
	
	private String nhistorial;

	
	/**
	 * Constructor
	 * @param historial
	 */
	public HistorialMedico(String nhistorial) {
		this.nhistorial = nhistorial;
	}



	public HistorialMedico() {
		pbd=new ParserBaseDeDatos();
		try {
			this.nhistorial = generaHistorial()+"";
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}



	public String getHistorial() {
		return nhistorial;
	}

public String generaHistorial() throws SQLException {
	String cod="";
		do {
			 cod= new Random().nextInt(10000)+"";
			}while(pbd.checkCodeHistorial(cod));
		return cod;
				
			
}


	public void setHistorial(String nhistorial) {
		this.nhistorial = nhistorial;
	}	
	
	
}
