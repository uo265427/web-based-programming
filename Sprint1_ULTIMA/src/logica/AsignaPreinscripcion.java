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
public class AsignaPreinscripcion {

	private String codigoAsignaPreinscripcion; // PrimaryKey
	private String codigoHistorial;
	private String codigoPreinscripcion; // nombre de la preinscripcion
	private String codempleado;
	// Si es un medicamento
	private int cantidad;
	private int intervalo; // En horas
	private int duracion; // En dias
	
	private String instrucciones;

	private Date fecha;
	private Time hora;
	
	/**
	 * Constructor
	 * @param codigoAsignaPreinscripcion
	 * @param codigoHistorial
	 * @param codigoPreinscripcion
	 * @param cantidad
	 * @param intervalo
	 * @param duracion
	 * @param instrucciones
	 */
	public AsignaPreinscripcion(String codigoAsignaPreinscripcion, String codigoHistorial,String codempleado, String codigoPreinscripcion,
			int cantidad, int intervalo, int duracion, String instrucciones, Date fecha, Time hora) {
		this.codigoAsignaPreinscripcion = codigoAsignaPreinscripcion;
		this.codigoHistorial = codigoHistorial;
		this.codempleado=codempleado;
		this.codigoPreinscripcion = codigoPreinscripcion;
		this.cantidad = cantidad;
		this.intervalo = intervalo;
		this.duracion = duracion;
		this.instrucciones = instrucciones;
		this.fecha = fecha;
		this.hora = hora;
	}

	/**
	 * @return the codigoAsignaPreinscripcion
	 */
	public String getCodigoAsignaPreinscripcion() {
		return codigoAsignaPreinscripcion;
	}

	/**
	 * @return the codigoHistorial
	 */
	public String getCodigoHistorial() {
		return codigoHistorial;
	}

	/**
	 * @return the codigoPreinscripcion
	 */
	public String getCodigoPreinscripcion() {
		return codigoPreinscripcion;
	}

	/**
	 * @return the cantidad
	 */
	public int getCantidad() {
		return cantidad;
	}

	/**
	 * @return the intervalo
	 */
	public int getIntervalo() {
		return intervalo;
	}

	/**
	 * @return the duracion
	 */
	public int getDuracion() {
		return duracion;
	}

	/**
	 * @return the instrucciones
	 */
	public String getInstrucciones() {
		return instrucciones;
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

	public String getCodempleado() {
		return codempleado;
	}
	
	
	
	
	
}
