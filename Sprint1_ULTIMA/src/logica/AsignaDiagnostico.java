/**
 * 
 */
package logica;


import java.sql.Time;
import java.util.Date;

/**
 * @author María
 *
 */
public class AsignaDiagnostico {

	private String codAsigDiagnostico;
	private String nombreDiagnostico;
	private String nDiagnostico; // El identificador del diagnostico
	private String nHistorial; // El número de historial del paciente a quien le hemos asignado el diagnostico
	private String codMedico;
	private Date fecha;
	private Time hora;
	private int cantidad;
	
	
	

	/**
	 * Constructor para la clase Asigna Diagnostico
	 * 
	 * @param nHistorial
	 * @param nDiagnostico
	 * @param codMedico
	 * @param fecha
	 * @param hora
	 */
	public AsignaDiagnostico(String codAsigDiagnostico, String nombreDiagnostico, String nDiagnostico, String nHistorial, String codMedico, Date fecha, Time hora) {
		this.codAsigDiagnostico = codAsigDiagnostico;
		this.nombreDiagnostico = nombreDiagnostico;
		this.nHistorial = nHistorial;
		this.nDiagnostico = nDiagnostico;
		this.codMedico = codMedico;
		this.fecha = fecha;
		this.hora = hora;
	}	
	
	public AsignaDiagnostico(String nombreDiagnostico, int cantidad) {
		this.nombreDiagnostico = nombreDiagnostico;
		this.cantidad = cantidad;
	}	

	


	
	
	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	/**
	 * @return the nombreDiagnostico
	 */
	public String getNombreDiagnostico() {
		return nombreDiagnostico;
	}





	/**
	 * @return the codAsigDiagnostico
	 */
	public String getCodAsigDiagnostico() {
		return codAsigDiagnostico;
	}





	/**
	 * @return the nHistorial
	 */
	public String getnHistorial() {
		return nHistorial;
	}



	/**
	 * @return the nDiagnostico
	 */
	public String getnDiagnostico() {
		return nDiagnostico;
	}



	/**
	 * @return the codMedico
	 */
	public String getCodMedico() {
		return codMedico;
	}



	/**
	 * @return the fecha
	 */
	public Date getFecha() {
		return fecha;
	}



	/**
	 * @return the hora
	 */
	public Time getHora() {
		return hora;
	}
	
	
	
	
}
