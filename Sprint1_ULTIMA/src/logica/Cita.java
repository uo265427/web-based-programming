/**
 * 
 */
package logica;

import java.sql.SQLException;
import java.sql.Time;
import java.util.Date;
import java.util.Random;

import logica.servicios.ParserBaseDeDatos;

/**
 * @author L-21
 *
 */
public class Cita {

	private String CodCita;
	private String codPaciente;
	private String codMed;
	private Time hInicio;
	private Time hFin;
	private Date date;
	private String ubicacion;
	private boolean  acudio;
	private boolean noAcudio;
	private boolean urgente;
	private Time hInicioR;
	private Time hFinR;
	private String numequipo;
	
	
	private ParserBaseDeDatos pbd=new ParserBaseDeDatos();
	
	public Cita(String codPaciente, String codMed, Time hInicio, Time hFin,Date date, String ubicacion,
			boolean acudio, boolean noAcudio, boolean urgente, Time hInicioR, Time hFinR) throws SQLException {
		super();
		
		this.codPaciente = codPaciente;
		this.codMed = codMed;
		this.hInicio = hInicio;
		this.hFin = hFin;
		this.date=date;
		this.ubicacion = ubicacion;
		this.acudio	 = acudio;
		this.urgente = urgente;
		this.noAcudio = noAcudio;
		this.hInicioR = hInicioR;
		this.hFinR = hFinR;

		
		generateRandomCodCita();
	}
	
	public Cita(String codPaciente, String codMed, Time hInicio, Time hFin,Date date,String ubicacion, boolean selected) throws SQLException {
		this.codPaciente = codPaciente;
		this.codMed = codMed;
		this.urgente = selected;
		this.hInicio = hInicio;
		this.hFin = hFin;
		this.date=date;
		this.ubicacion=ubicacion;
		generateRandomCodCita();
	}

	public Cita (String codcita ,String codPaciente, String codMed, Time hInicio, Time hFin, java.sql.Date date,String ubicacion,boolean urgencia){
			this.CodCita=codcita;
			this.codPaciente = codPaciente;
			this.codMed = codMed;
			this.urgente = urgencia;
			this.hInicio = hInicio;
			this.hFin = hFin;
			this.date=date;
			this.ubicacion=ubicacion;
	}
	
	public Cita (String codcita ,String codPaciente, Time hInicio, Time hFin, java.sql.Date date,String ubicacion,boolean urgencia, String numequipo){
		this.CodCita=codcita;
		this.codPaciente = codPaciente;
		this.urgente = urgencia;
		this.hInicio = hInicio;
		this.hFin = hFin;
		this.date=date;
		this.ubicacion=ubicacion;
		this.numequipo = numequipo;
}

	public Cita (Time hInicio, Date date,String ubicacion){
		this.hInicio = hInicio;
		this.date=date;
		this.ubicacion=ubicacion;
}

	public Cita(String codPaciente, Time hInicio, Time hFin,Date date,String ubicacion, boolean selected, String numequipo) throws SQLException {
		this.codPaciente = codPaciente;
		this.urgente = selected;
		this.hInicio = hInicio;
		this.hFin = hFin;
		this.date=date;
		this.ubicacion=ubicacion;
		this.numequipo = numequipo;
		generateRandomCodCita();
		
	}
	
	
	
	public Cita(String codcita,String codPaciente, Time hInicio, Time hFin,Date date,String ubicacion, boolean selected, String numequipo) {
		this.CodCita=codcita;
		this.codPaciente = codPaciente;
		this.urgente = selected;
		this.hInicio = hInicio;
		this.hFin = hFin;
		this.date=date;
		this.ubicacion=ubicacion;
		this.numequipo = numequipo;
	}

	public String getNumequipo() {
		return numequipo;
	}

	public void setNumequipo(String numequipo) {
		this.numequipo = numequipo;
	}

	/**
	 * @param codPaciente the codPaciente to set
	 */
	public void setCodPaciente(String codPaciente) {
		this.codPaciente = codPaciente;
	}

	/**
	 * @param codMed the codMed to set
	 */
	public void setCodMed(String codMed) {
		this.codMed = codMed;
	}

	/**
	 * @param hInicio the hInicio to set
	 */
	public void sethInicio(Time hInicio) {
		this.hInicio = hInicio;
	}

	/**
	 * @param hFin the hFin to set
	 */
	public void sethFin(Time hFin) {
		this.hFin = hFin;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @param ubicacion the ubicacion to set
	 */
	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	/**
	 * @param noAcudio the noAcudio to set
	 */
	public void setNoAcudio(boolean noAcudio) {
		this.noAcudio = noAcudio;
	}

	/**
	 * @param urgente the urgente to set
	 */
	public void setUrgente(boolean urgente) {
		this.urgente = urgente;
	}

	/**
	 * @param hInicioR the hInicioR to set
	 */
	public void sethInicioR(Time hInicioR) {
		this.hInicioR = hInicioR;
	}

	/**
	 * @param hFinR the hFinR to set
	 */
	public void sethFinR(Time hFinR) {
		this.hFinR = hFinR;
	}

	/**
	 * @param pbd the pbd to set
	 */
	public void setPbd(ParserBaseDeDatos pbd) {
		this.pbd = pbd;
	}

	/**
	 * @return the noAcudio
	 */
	public boolean isNoAcudio() {
		return noAcudio;
	}

	/**
	 * @return the hInicioR
	 */
	public Time gethInicioR() {
		return hInicioR;
	}

	/**
	 * @return the hFinR
	 */
	public Time gethFinR() {
		return hFinR;
	}

	/**
	 * @return the pbd
	 */
	public ParserBaseDeDatos getPbd() {
		return pbd;
	}

	private void setCodCita(String codCita) {
		CodCita = codCita;
	}

	public void generateRandomCodCita() throws SQLException {
		String cod;
		do {
		 cod=new Random().nextInt(100000)+"";
		}while(pbd.checkCode(cod));
		
		
		setCodCita(cod);
		
		
	}
	public String getCodPaciente() {
		return codPaciente;
	}

	public String getCodMed() {
		return codMed;
	}

	public Time gethInicio() {
		return hInicio;
	}

	public Time gethFin() {
		return hFin;
	}

	public String getUbicacion() {
		return ubicacion;
	}

	

	public boolean isUrgente() {
		return urgente;
	}

	public String getCodCita() {
		return CodCita;
	}

	public Date getDate() {
		return date;
	}

	public boolean isAcudio() {
		return acudio;
	}

	public void setAcudio(boolean acudio) {
		this.acudio = acudio;
	}

	@Override
	public String toString(){
		return  CodCita +"Código paciente " + codPaciente + " fecha " + date ;
	}
	
	
	
	
	
}
