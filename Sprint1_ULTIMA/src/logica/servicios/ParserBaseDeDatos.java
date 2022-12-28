package logica.servicios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import conexion.Conexion;
import logica.Accion;
import logica.AccionEmpleado;
import logica.Acompañante;
import logica.Administrativo;
import logica.Antecedente;
import logica.AsignaAntecedente;
import logica.AsignaCausa;
import logica.AsignaDiagnostico;
import logica.AsignaEnfermPrev;
import logica.AsignaEquipo;
import logica.AsignaPreinscripcion;
import logica.AsignaProcedimiento;
import logica.AsignaVacuna;
import logica.Causas;
import logica.Cita;
import logica.Correo;
import logica.Diagnostico;
import logica.Equipo;
import logica.HistorialMedico;
import logica.Paciente;
import logica.Preinscripcion;
import logica.Procedimiento;
import logica.Vacaciones;
import logica.Vacuna;
import logica.empleados.Empleado;
import logica.empleados.Enfermero;
import logica.empleados.Medico;
import logica.servicios.DtoCita;
import logica.servicios.DtoMapper;
import oracle.jdbc.proxy.annotation.GetDelegate;

public class ParserBaseDeDatos {

	private final static String MEDICO_LISTALL = "SELECT * FROM empleado, medico WHERE empleado.CODEMPLEADO = medico.CODMEDICO";
	private final static String ADD_CITA = "INSERT INTO CITA(CODCITA, CODPACIENTE,CODMEDICO,HINICIO,HFIN,FECHA,UBICACION,URGENCIA)"
			+ " VALUES(?,?,?,?,?,?,?,?)";
	private final static String CHECK_CITA_CODE = "select codcita FROM cita where codcita=?;";
	private final static String CHECK_COMPANYCOD_CODE = "select codacompañante FROM acompañantepaciente where codacompañante=?;";
	private final static String CHECK_PACIENTE_CODE = "select codpaciente FROM paciente where codpaciente=?;";
	private final static String CHECK_NUMERO_HISTORIAL = "select nhistorial FROM historial where nhistorial=?;";
	private final static String CHECK_NUMEQUIPO = "select * FROM equipo where numequipo=?;";
	private final static String CHECK_COD_MED = "select * FROM medico where codmedico=?;";
	private final static String CHECK_COD_ENFERMERO = "select * FROM enfermero where codenfermero=?;";

	private final static String CHECK_MEDICO_CITA = "select * from cita c,medico m where m.codmedico=c.codmedico and m.codmedico= ? and ?    between c.hinicio  and c.Hfin and  ?    between c.hinicio  and c.Hfin; ";

	private final static String CHECK_MEDICO_JORNADA = "select * FROM medico m,empleado e where m.codmedico=e.codempleado and m.codmedico= ? and ? between e.hinicio and e.hfin and ? between e.hinicio and e.hfin;";

	private final static String SET_URGENTE = "UPDATE cita SET urgente=? WHERE codcita=? and codpaciente=?";
	private final static String ENFERMERO_LISTALL = "SELECT * FROM empleado, enfermero WHERE empleado.CODEMPLEADO = enfermero.CODENFERMERO";
	private final static String UPDATE_DATOS_CONTACTO = "UPDATE paciente SET movil=?, email=?, info=? WHERE codpaciente=?";

	private final static String GET_CITAS = "select * from cita c, medico m ,empleado e,paciente p where m.codmedico= e.codempleado and p.codpaciente=c.codpaciente and  m.codmedico=c.codmedico and c.codmedico=?;";
	private final static String GET_CITAS_ENFERMERO="select * from cita c, enfermero m ,empleado e,paciente p where m.codenfermero= e.codempleado and p.codpaciente=c.codpaciente and  m.codenfermero=c.codmedico and c.codmedico=?;";
	private final static String GET_CITAS_DATE = "select * from cita c, medico m ,empleado e,paciente p where m.codmedico= e.codempleado and m.codmedico=c.codmedico and c.codpaciente= p.codpaciente and c.fecha=?;";
	private final static String GET_CITAS_DATE_MED = "select * from cita c, medico m ,empleado e,paciente p where m.codmedico= e.codempleado and m.codmedico=c.codmedico and c.codpaciente= p.codpaciente and c.codmedico=? and c.fecha=?;";
	private final static String GET_PACIENTE_CITA = "select * from paciente p, cita c where p.codpaciente= c.codpaciente  and c.codcita=?;";
	private final static String GET_PACIENTE_CITA_MEDICO = "select * from paciente p, cita c, empleado e,medico m where p.codpaciente= c.codpaciente and e.codempleado=m.codmedico and c.codmedico= e.codempleado  and c.codcita=?;";
	private final static String GET_CITA = "select * from cita c where c.fecha>=? ;";
	private final static String GET_CITAS_EMPLEADOS = "select * from  empleado where codempleado=?;";
	private final static String GET_CITAS_MEDICO = "select * from  medico m,empleado e where m.codmedico = e.codempleado and  codmedico=?;";
	
	private final static String GET_CITAS_ENF= "select * from  enfermero en, empleado e where en.codenfermero = e.codempleado and codenfermero=?;";
	private final static String GET_CITA_FECHA_HISTORIAL = "select * from cita c, medico m ,empleado e,paciente p where m.codmedico= e.codempleado and m.codmedico=c.codmedico and c.codpaciente= p.codpaciente and c.fecha=? and p.nhistorial =?";
	private final static String GET_CITA_FECHA_HISTORIAL_MED = "select * from cita c, medico m ,empleado e,paciente p where m.codmedico= e.codempleado and m.codmedico=c.codmedico and c.codpaciente= p.codpaciente and c.fecha=? and p.nhistorial =? and c.codmedico=?";
	private final static String GET_ADMINISTRATIVO = "Select * from administrativo where codAdmin=? and pass=?";
	private final static String GET_GERENTE = "Select * from GERENTE where codgerente=? and pass=?";
	private final static String GET_PACIENTE = "Select * from paciente where codpaciente =  ?";
	private final static String GET_AUDITOR = "Select * from auditor where codauditor=? and pass=?";
	private final static String GET_MEDICO = "Select * from medico m,empleado e  where m.codmedico=e.codempleado and  m.codmedico=? and e.pass=?"; 
	private final static String GET_MEDICO_NOMBRE = "Select * from empleado where codempleado=?";
	private final static String GET_MEDICO_CODIGO = "Select * from empleado where nombre=?";
	
	private final static String VER_CITA = "SELECT * FROM cita where codpaciente=?";

	private final static String VER_HORA_CITA = "SELECT hinicio FROM cita where codpaciente=?";

	private final static String VER_SALA_CITA = "SELECT ubicacion FROM cita where hinicio=? and fecha=? and ubicacion=?";

	private final static String VER_HISTORIAL = "SELECT * FROM historial where nHistorial =?";

	private final static String VER_VACUNAS = "SELECT nombreVacuna, fecha FROM vacuna where codvacuna  = ?";

	private final static String VER_ASIGNA_ENFERM = "SELECT nombreEnfermedad, fecha FROM asignaenfermedadprevia where historial  = ?";

	private final static String VER_ENFERM = "SELECT nombreEnfermedad from enfermedadprevia";

	private final static String VER_ASIGNA_CAUSAS = "SELECT nombreCausa, fecha FROM asignacausa where historial  = ?";
	
	private final static String VER_CAUSAS_ASIGNADAS = "SELECT *  FROM asignacausa where historial  = ?";
	private final static String VER_CAUSAS = "SELECT nombreCausa from causa";

	private final static String VER_NOMBRE_CAUSA = "SELECT  * FROM causa";
	
	private final static String VER_NOMBRE_ANTECEDENTE = "SELECT nombreAntecedente FROM antecedente";

	private final static String MODIFICAR_UBICACION_CITA = "UPDATE cita SET ubicacion=? where codpaciente=?";

	private final static String INSERT_CAUSAS = "INSERT into causa (nombrecausa) values (?)";
	
	private final static String INSERT_ANTECEDENTES = "INSERT into antecedente (codAntecedente, nombreAntecedente) values (?, ?)";

	private final static String INSERT_ASIGNA_CAUSA = "INSERT into asignacausa (codasigcausa, nombrecausa, historial, codempleado, fecha, hora) values(?,?,?,?,?,?)";

	private final static String ACTUALIZAR_CITA = "UPDATE cita set hinicioempleado = ?, hfinempleado = ?, acudio = ?, noAcudio = ? where codcita = ?";

	private final static String UPDATE_JORNADA = "UPDATE empleado SET hinicio=?, hfin=?, dinicio=?, dfin=?, djornada=? WHERE codempleado=?";

	private final static String ADD_ASIGNA_PPREINSCRIPCION = "INSERT INTO ASIGNAPRESCRIPCION (CODASIGPRESCRIPCION, NOMBREPRESCRIPCION, NHISTORIAL, CODEMPLEADO, CANTIDAD, INTERVALO, DURACION, INSTRUCCIONES, FECHA, HORA ) VALUES(?,?,?,?,?,?,?,?,?,?)";

	private final static String ADD_ASIGNA_ANTECEDENTE = "INSERT INTO ASIGNAANTECEDENTE (CODASIGANTECEDENTE, NOMBREANTECEDENTE, CODANTECEDENTE, NHISTORIAL, CODEMPLEADO, FECHA, HORA) VALUES (?,?,?,?,?,?,?)";
	
	private final static String ADD_ASIGNA_DIAGNOSTICO = "INSERT INTO ASIGNADIAGNOSTICO (CODASIGDIAGNOSTICO,NOMBREDIAGNOSTICO, CODDIAGNOSTICO, HISTORIAL, CODEMPLEADO, FECHA, HORA) VALUES (?,?,?,?,?,?,?)";
	private final static String ADD_ASIGNA_PROCEDIMIENTO = "INSERT INTO ASIGNAPROCEDIMIENTO (CODASIGPROCEDIMIENTO, NOMBREPROCEDIMIENTO, CODPROCEDIMIENTO, NHISTORIAL, CODEMPLEADO,FECHA, HORA) VALUES (?,?,?,?,?,?,?)";

	private final static String ADD_PREINSCRIPCION = "INSERT INTO PRESCRIPCION (NOMBREPRESCRIPCION, MEDICAMENTO)"
			+ " VALUES(?,?)";
	private final static String ADD_DIAGNOSTICO = "INSERT INTO DIAGNOSTICO (CODDIAGNOSTICO, NOMBREDIAGNOSTICO)" + " VALUES(?, ?)";
	
	private final static String ADD_PROCEDIMIENTO = "INSERT INTO PROCEDIMIENTO (CODPROCEDIMIENTO, NOMBREPROCEDIMIENTO)" + " VALUES(?,?)";

	private final static String ADD_ANTECEDENTE = "INSERT INTO ANTECEDENTE (CODANTECEDENTE, NOMBREANTECEDENTE)" + " VALUES (?, ?)";
	
	private final static String ADD_CORREO = "INSERT INTO CORREO (CODCORREO, CODMEDICODESTINO, CODMEDICOORIGEN, ASUNTO, MENSAJE, FECHA, HORA) VALUES (?,?,?,?,?,?,?)";

	private final static String LIST_PREINSCRIPCIONES = "Select * from prescripcion";
	private final static String LIST_PROCEDIMIENTOS = "Select * from procedimiento";
	private final static String LIST_ANTECEDENTES = "Select * from antecedente";
	private final static String LIST_DIAGNOSTICOS = "Select * from diagnostico";

	private final static String GET_CITA_HISTORIAL = "select * from cita c,paciente p,historial h where c.codpaciente=p.codpaciente and p.nhistorial=h.nhistorial and h.nhistorial=?";
	private final static String GET_CITA_HISTORIAL_MED = "select * from cita c,paciente p,historial h where c.codpaciente=p.codpaciente and p.nhistorial= h.nhistorial and h.nhistorial=? and c.codmedico=?";
	private final static String DELETE_CITA = "delete from cita where codcita=?;";
	private final static String DELETE_CORREO = "delete from correo where codcorreo=?";
	private final static String FIND_MED_BY_NAME = "select *  from medico m,empleado e where e.codempleado=m.codmedico and  e.nombre like ?|| '%' ;";
	private final static String FIND_MED_BY_SURNAME = "select *  from medico m,empleado e where e.codempleado=m.codmedico and  e.apellido like ? || '%' ;";
	private final static String FIND_ENFE_BY_NAME = "select *  from enfermero en,empleado e where e.codempleado=en.codenfermero and  e.nombre like ?|| '%' ;";
	private final static String FIND_ENFE_BY_SURNAME = "select *  from enfermero en,empleado e where e.codempleado=en.codenfermero and  e.apellido like ? || '%' ;";
	private final static String FIND_MED_BY_NAME_SURNAME = "select *  from medico m,empleado e where e.codempleado=m.codmedico and e.nombre= like ?|| '%' and  e.apellido like ? || '%';";

	private final static String UPDATE_CITA = "UPDATE cita set hinicio = ?, hfin = ?, fecha = ? ,codmedico=?,ubicacion =?, urgencia=? where codcita=? and codpaciente=? and codmedico =?";

	private final static String VER_PREINSCRIPCIONES_ASIGNADAS = "SELECT * FROM asignaprescripcion where nhistorial = ?";
	
	private final static String VER_ASIGNA_PREINSCRIPCIONES = "SELECT * FROM asignaprescripcion where nhistorial = ? and fecha=?";
	private final static String VER_ASIGNA_PREINSCRIPCION = "SELECT * FROM asignaprescripcion where nhistorial = ?";

	private final static String VER_ASIGNA_EPREVIA = "SELECT * FROM asignaenfermedadprevia where historial = ?";
	
	private final static String VER_ASIGNA_PROCEDIMIENTOS = "SELECT * FROM asignaprocedimiento where nhistorial = ?";
	private final static String VER_ASIGNA_ANTECEDENTES = "SELECT * FROM asignaantecedente where nhistorial = ?";

	private final static String VER_VACUNAS_ASIGNADAS = "SELECT * FROM asignavacuna where historial = ?";

	private final static String VER_DIAGNOSTICOS_ASIGNADOS = "SELECT * FROM asignadiagnostico where historial = ?";
	
	private final static String VER_ANTECEDENTES_ASIGNADOS = "SELECT * FROM asignaAntecedente where nhistorial = ?";

	private final static String ADD_VACACIONES = "INSERT INTO VACACIONES (CODVACACIONES, CODEMPLEADO, CODADMIN, DINICIO, DFINAL)"
			+ " VALUES(?,?,?,?,?)";
	private final static String LIST_VACUNAS = "Select * from vacuna";
	private final static String FIND_PACIENTE_BY_NAME = "Select * from paciente where nombre like ? || '%'";
	private final static String FIND_PACIENTE_BY_SURNAME = "Select * from paciente where apellido like ? || '%'";
	private final static String FIND_DIAGNOSTICO = "Select * from diagnostico where coddiagnostico = ?";

	private final static String ADD_ASIGNA_VACUNA = "INSERT INTO ASIGNAVACUNA (codasigvac, nombrevacuna, historial, codempleado,FECHA, HORA) VALUES (?,?,?,?,?,?)";
	private final static String HISTORIAL_CITA = "select nhistorial from cita c,paciente p where c.codpaciente=p.codpaciente and c.codCita=? and c.codpaciente=?";
	//,codmedico   and c.codmedico=?
	private final static String CITA_CODCITA = "select * from cita  where codCita=? and codpaciente=? ";
	private final static String PACIENTE_HISTORIAL = "select * from paciente  where nhistorial=? ";

	private final static String VACUNAS_PERSONA = "SELECT * FROM asignaVacuna WHERE historial=?";
	private final static String GET_ACOMPAÑANTE = "SELECT * FROM acompañantePaciente WHERE codpaciente=?";

	
	private final static String VER_CORREOS = "SELECT * FROM correo where codMedicoDestino = ?";
	
	//ACCIONES
	private final static String LISTAR_NACCIONES = "SELECT * FROM accion";
	
	private final static String LISTAR_NACCIONES_EMPLEADO = "SELECT * FROM accionempleado";
	


//ACCIONES
	private final static String NOMBRE_APELLIDO_EMPLEADO = "SELECT nombre, apellido FROM empleado where codempleado = ?";

	
	private final static String INSERT_ACCION = "INSERT into accion (naccion, codadmin, fecha, hora, nombreaccion) values (?, ?, ?, ?, ?)";

	private final static String INSERT_ACCION_EMPLEADO = "INSERT into accionempleado (naccion, codempleado, fecha, hora, nombreaccion) values (?, ?, ?, ?, ?)";
	
	
	private final static String GET_ACCIONES_NAME_DATE_ADM = "select * from accion where codadmin = ? and fecha >= ? and fecha <= ?";	
	
	private final static String GET_ACCIONESEMPLEADO_NAME_DATE_ADM = "select * from accionempleado where codempleado = ? and fecha >= ? and fecha <= ?";
	
	private final static String INSERT_PACIENTE = "INSERT into paciente (codpaciente, nombre, apellido, movil, email,info,nhistorial) values (?, ?, ?, ?, ?,?,?)";
	private final static String INSERT_ACOMPAÑANTE = "INSERT into acompañantepaciente (codacompañante,codpaciente, nombre, apellido, movil, correo) values (?, ?, ?, ?, ?,?)";
	private final static String INSERT_HISTORIAL = "INSERT into historial (nhistorial) values (?);";
	
	
private final static String GET_ACCIONES_DATE_ADM = "select * from accion where fecha >= ? and fecha <= ?";
	
	private final static String GET_ACCIONESEMPLEADO_DATE_ADM = "select * from accionempleado where fecha >= ? and fecha <= ?";
	
	private final static String GET_ACCIONES_NAME_ADM = "select * from accion where codadmin = ?";	
	
	private final static String GET_ACCIONESEMPLEADO_NAME = "select * from accionempleado where codempleado = ?";	
	
	private final static String INSERT_EQUIPO = "INSERT into equipo (numequipo) values (?)";
	
	private final static String INSERT_ASIGNA_EQUIPO = "INSERT into asignaequipo (codequipo, numequipo, codempleado) values (?,?,?)";
	
	private final static String LISTAR_EQUIPOS = "SELECT * FROM equipo";
	
	private final static String LISTAR_EQUIPOS_NOMBRE = "SELECT * FROM equipo where numequipo=?";
	
	
	private final static String GET_EQUIPO = "SELECT * FROM equipo where numequipo=?";
	
	private final static String GET_PACIENTE_NUMEQUIPO = "SELECT * FROM paciente where codpaciente=?";
	
	private final static String FILTRAR_EMPLEADO_NOMBRE = "SELECT * FROM empleado WHERE nombre LIKE '%?%'";
	private final static String FILTRAR_EMPLEADO_APELLIDO = "SELECT * FROM empleado WHERE apellido LIKE '%?%'";
	
	private final static String GET_ASIGNAEQUIPO_NAME = "select * from asignaequipo where numequipo = ?";
	
	private final static String GET_CITA_NUMEQUIP = "select * from cita where numequipo = ?";
	
	private final static String GET_ASIGNAEQUIPO_COD_MED = "select * from asignaequipo where codempleado = ?";
	
	private final static String ADD_CITA_EQUIPO = "INSERT INTO CITA(CODCITA,CODPACIENTE,HINICIO,HFIN,FECHA,UBICACION,URGENCIA,NUMEQUIPO)"
			+ " VALUES(?,?,?,?,?,?,?,?)";
	
	private final static String GET_CITA_EQUIPOD = "select * from cita c where c.fecha=? ;";
	
	private final static String ADMIN_NAME = "select * from Administrativo a where a.codadmin=? ;";
	
	private final static String GET_DIAGNOSTICOS_DATE= "SELECT count(nombrediagnostico) as numero FROM asignadiagnostico where nombrediagnostico=? and fecha >= ? and fecha <= ?";

	private final static String VER_NUMERO_DIAGNOSTICOS_ASIGNADOS = "SELECT count(nombrediagnostico) as numero FROM asignadiagnostico where nombrediagnostico=?";
	
	private final static String GET_CITA_HISTORIAL_FECHA = "select * from cita c,paciente p,historial h where c.codpaciente=p.codpaciente and p.nhistorial=h.nhistorial and h.nhistorial=? and c.fecha=?";
	
	
	private final static String ADD_EMPLEADO = "INSERT INTO empleado VALUES(?,?,?,?,?,?,?,?,?)";
	private final static String ADD_MEDICO = "INSERT INTO medico VALUES(?)";
	private final static String ADD_ENFERMERO = "INSERT INTO enfermero VALUES(?)";
	
	private final static String VER_DIAGNOSTICOS = "select nombrediagnostico, count(*) as contador from asignadiagnostico group by nombrediagnostico";
	

	
	private final static String VER_CODDIAGNOSTICOS_FECHA =  "select nombrediagnostico, count(*) as contador from asignadiagnostico where fecha>=? and fecha <=? group by nombrediagnostico";
	
	private final static String VER_CODDIAGNOSTICOS = "SELECT coddiagnostico FROM diagnostico where nombrediagnostico=?";
	
	private final static String VER_ASIGNA_DIAGNOSTICO = "select nombrediagnostico from asignadiagnostico";
	
	private final static String VER_ASIGNA_DIAGNOSTICO_FECHA = "select nombrediagnostico from asignadiagnostico where fecha>=? and fecha <=?";
	//FUNCIONES
	private final static String GET_CITAS_DATE_MED_ACTUAL = "select * from cita c, medico m ,empleado e,paciente p where m.codmedico= e.codempleado and m.codmedico=c.codmedico and c.codpaciente= p.codpaciente and c.codmedico=? and c.fecha>=?;";
	
	
	
	public List<Paciente> buscarPaciente(String buscando) throws SQLException {
		List<Paciente> pacientes = new ArrayList<Paciente>();
		Connection con = new Conexion().getConnectionJDBC();
		Statement st = con.createStatement();
//ResultSet rs=st.executeQuery("select *  from paciente where codpaciente like "+buscando+ "%''?%');");
		ResultSet rs = st.executeQuery("select *  from paciente");

		while (rs.next()) {

			pacientes.add(new Paciente(rs.getString("codpaciente"), rs.getString("nombre"), rs.getString("apellido"),
					rs.getInt("movil"), rs.getString("email"), rs.getString("info"), rs.getString("nhistorial")));

		}

//CERRAR EN ESTE ORDEN
		rs.close();
		st.close();
		con.close();
		return pacientes;
	}

	public List<Paciente> buscarPacienteNombre(String nombre) throws SQLException {
		List<Paciente> pacientes = new ArrayList<Paciente>();
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(FIND_PACIENTE_BY_NAME);
		pst.setString(1, nombre);
		ResultSet rs = pst.executeQuery();

		while (rs.next()) {

			pacientes.add(new Paciente(rs.getString("codpaciente"), rs.getString("nombre"), rs.getString("apellido"),
					rs.getInt("movil"), rs.getString("email"), rs.getString("info"), rs.getString("nhistorial")));

		}

//CERRAR EN ESTE ORDEN
		rs.close();
		pst.close();
		con.close();
		return pacientes;
	}

	public List<Paciente> buscarPacienteApellido(String apellido) throws SQLException {
		List<Paciente> pacientes = new ArrayList<Paciente>();
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(FIND_PACIENTE_BY_SURNAME);
		pst.setString(1, apellido);
		ResultSet rs = pst.executeQuery();

		while (rs.next()) {

			pacientes.add(new Paciente(rs.getString("codpaciente"), rs.getString("nombre"), rs.getString("apellido"),
					rs.getInt("movil"), rs.getString("email"), rs.getString("info"), rs.getString("nhistorial")));

		}

//CERRAR EN ESTE ORDEN
		rs.close();
		pst.close();
		con.close();
		return pacientes;
	}

	public List<Medico> buscarMedico(String buscando) throws SQLException {
		List<Medico> medicos = new ArrayList<Medico>();
		Connection con = new Conexion().getConnectionJDBC();
		Statement st = con.createStatement();
		// ResultSet rs=st.executeQuery("select * from paciente where codpaciente like
		// "+buscando+ "%''?%');");
		ResultSet rs = st.executeQuery(MEDICO_LISTALL);

		while (rs.next()) {
			medicos.add(new Medico(rs.getString("codmedico"), rs.getString(2), rs.getString(3), rs.getString(4),
					rs.getTime(5), rs.getTime(6), rs.getDate(7), rs.getDate(8), rs.getString(9)));

		}

//CERRAR EN ESTE ORDEN
		rs.close();
		st.close();
		con.close();
		return medicos;
	}

	public void crearCita(Cita cita) throws SQLException {
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(ADD_CITA);

		String codCita = cita.getCodCita();
		String codPaciente = cita.getCodPaciente();
		String codMed = cita.getCodMed();
		Time hInicio = cita.gethInicio();
		Time hFin = cita.gethFin();
		Date date = (Date) cita.getDate();
		String ubicacion = cita.getUbicacion();
		boolean urgente = cita.isUrgente();

		pst.setString(1, codCita);
		pst.setString(2, codPaciente);
		pst.setString(3, codMed);
		pst.setTime(4, hInicio);
		pst.setTime(5, hFin);
		pst.setDate(6, date);
		pst.setString(7, ubicacion);
		System.err.println(urgente);
		pst.setBoolean(8, urgente);

		pst.executeUpdate();

		pst.close();
		con.close();

	}

	public boolean checkCodePaciente(String cod) throws SQLException {
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(CHECK_PACIENTE_CODE);
		pst.setString(1, cod);
		ResultSet rs = pst.executeQuery();

		boolean res = rs.next();

		rs.close();
		pst.close();
		con.close();
		return res;

	}

	public boolean checkCodeHistorial(String cod) throws SQLException {
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(CHECK_NUMERO_HISTORIAL);
		pst.setString(1, cod);
		ResultSet rs = pst.executeQuery();

		boolean res = rs.next();

		rs.close();
		pst.close();
		con.close();
		return res;

	}

	public boolean checkCode(String cod) throws SQLException {
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(CHECK_CITA_CODE);
		pst.setString(1, cod);
		ResultSet rs = pst.executeQuery();

		boolean res = rs.next();

		rs.close();
		pst.close();
		con.close();
		return res;

	}
	

	public boolean checkCodEnfermero(String cod) throws SQLException {
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(CHECK_COD_ENFERMERO);
		pst.setString(1, cod);
		ResultSet rs = pst.executeQuery();

		boolean res = rs.next();

		rs.close();
		pst.close();
		con.close();
		return res;

	}
	
	public boolean checkCodEquipo(String cod) throws SQLException {
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(CHECK_NUMEQUIPO);
		pst.setString(1, cod);
		ResultSet rs = pst.executeQuery();

		boolean res = rs.next();

		rs.close();
		pst.close();
		con.close();
		return res;

	}
	
	
	public boolean checkCodeMedico(String cod) throws SQLException {
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(CHECK_COD_MED);
		pst.setString(1, cod);
		ResultSet rs = pst.executeQuery();

		boolean res = rs.next();

		rs.close();
		pst.close();
		con.close();
		return res;

	}

	/**
	 * Comprueba que un medico no tengo ninguna cita a la hora de una uqe se quiera
	 * crear
	 * 
	 * @param cod
	 * @return devuelve true si exite la cita,false en caso contrario
	 * @throws SQLException
	 */

	public boolean checkMedicoCita(String codmedico, Time inicio, Time fin) throws SQLException {
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(CHECK_MEDICO_CITA);
		pst.setString(1, codmedico);
		pst.setTime(2, inicio);
		pst.setTime(3, fin);
		ResultSet rs = pst.executeQuery();

		boolean res = rs.next();

		rs.close();
		pst.close();
		con.close();
		return res;

	}

	/**
	 * Comprueba que un medico esat dentro de su jornada laboral
	 * 
	 * @param cod
	 * @return devuelve true si exite la cita,false en caso contrario
	 * @throws SQLException
	 */

	public boolean checkMedicoJornada(String codmedico, Time inicio, Time fin) throws SQLException {
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(CHECK_MEDICO_JORNADA);
		pst.setString(1, codmedico);
		pst.setTime(2, inicio);
		pst.setTime(3, fin);
		ResultSet rs = pst.executeQuery();

		boolean res = rs.next();

		rs.close();
		pst.close();
		con.close();
		return res;

	}

	/**
	 * 
	 * Actualiza los datos de contacto del paciente si al actualizar la cita los
	 * cambia
	 * 
	 * @param telefono
	 * @param correo
	 * @param codpaciente
	 * @throws SQLException
	 */
	public static void updateDatosContacto(int telefono, String correo, String info, String codpaciente)
			throws SQLException {
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(UPDATE_DATOS_CONTACTO);

		pst.setInt(1, telefono);
		pst.setString(2, correo);
		pst.setString(3, info);
		pst.setString(4, codpaciente);

		pst.execute();

		pst.close();
		con.close();
	}

	/**
	 * 
	 * @param telefono
	 * @param correo
	 * @param codpaciente
	 * @throws SQLException
	 */
	public void updateUrgencia(boolean urgente, String codcita, String codpaciente) throws SQLException {
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(SET_URGENTE);

		pst.setBoolean(1, urgente);
		pst.setString(2, codcita);
		pst.setString(3, codpaciente);

		ResultSet rs = pst.executeQuery();

		rs.next();

		rs.close();
		pst.close();
		con.close();
	}

//----------------------A PARTIR DE AQUÃ� VENTANA MEDICO CITA-------------------------------------------------------------------

	public List<Cita> devolvercitasMedico(String codmedico) throws SQLException {
		List<Cita> citas = new ArrayList<Cita>();
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement st = con.prepareStatement(GET_CITAS);
		boolean res = false;
		st.setString(1, codmedico);

		ResultSet rs = st.executeQuery();

		while (rs.next()) {
			if (rs.getByte("urgencia") == 1)
				res = true;

			citas.add(new Cita(rs.getString("codcita"), rs.getString("codpaciente"), rs.getString("codmedico"),
					rs.getTime("hinicio"), rs.getTime("hfin"), rs.getDate("fecha"), rs.getString("ubicacion"), res));

		}

//CERRAR EN ESTE ORDEN
		rs.close();
		st.close();
		con.close();
		return citas;
	}
	
	public List<Cita> devolvercitasMedicoPorFechaActual(String codmedico) throws SQLException {
		List<Cita> citas = new ArrayList<Cita>();
		Connection con = new Conexion().getConnectionJDBC();
		java.util.Date fecha = new java.util.Date();
		java.sql.Date sDate = new java.sql.Date(fecha.getTime());
		PreparedStatement pst = con.prepareStatement(GET_CITAS_DATE_MED_ACTUAL);
		boolean res = false;
		pst.setString(1, codmedico);
		pst.setDate(2, sDate);

		ResultSet rs = pst.executeQuery();

		while (rs.next()) {
			if (rs.getByte("urgencia") == 0)
				res = true;

			citas.add(new Cita(rs.getString("codcita"), rs.getString("codpaciente"), rs.getString("codmedico"),
					rs.getTime("hinicio"), rs.getTime("hfin"), rs.getDate("fecha"), rs.getString("ubicacion"), res));

		}

//CERRAR EN ESTE ORDEN
		rs.close();
		pst.close();
		con.close();
		return citas;
	}
	
	public List<Cita> devolvercitasMedicoFe(String codmedico) throws SQLException {
		List<Cita> citas = new ArrayList<Cita>();
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement st = con.prepareStatement(GET_CITAS);
		boolean res = false;
		st.setString(1, codmedico);

		ResultSet rs = st.executeQuery();

		while (rs.next()) {
			if (rs.getByte("urgencia") == 1)
				res = true;

			citas.add(new Cita(rs.getString("codcita"), rs.getString("codpaciente"), rs.getString("codmedico"),
					rs.getTime("hinicio"), rs.getTime("hfin"), rs.getDate("fecha"), rs.getString("ubicacion"), res));

		}

//CERRAR EN ESTE ORDEN
		rs.close();
		st.close();
		con.close();
		return citas;
	}
	
	
	public List<Cita> devolvercitasEnfermero(String codmedico) throws SQLException {
		List<Cita> citas = new ArrayList<Cita>();
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement st = con.prepareStatement(GET_CITAS_ENFERMERO);
		boolean res = false;
		st.setString(1, codmedico);

		ResultSet rs = st.executeQuery();

		while (rs.next()) {
			if (rs.getByte("urgencia") == 1)
				res = true;

			citas.add(new Cita(rs.getString("codcita"), rs.getString("codpaciente"), rs.getString("codmedico"),
					rs.getTime("hinicio"), rs.getTime("hfin"), rs.getDate("fecha"), rs.getString("ubicacion"), res));

		}

//CERRAR EN ESTE ORDEN
		rs.close();
		st.close();
		con.close();
		return citas;
	}

	public Paciente devolverPacientes(String codcita) throws SQLException {
		Paciente pacientes = null;
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(GET_PACIENTE_CITA);
		@SuppressWarnings("unused")
		boolean res = false;
		pst.setString(1, codcita);
		ResultSet rs = pst.executeQuery();

		while (rs.next()) {

			pacientes = new Paciente(rs.getString("codpaciente"), rs.getString("nombre"), rs.getString("apellido"),
					rs.getInt("movil"), rs.getString("email"), rs.getString("info"), rs.getString("nhistorial"));

		}

//CERRAR EN ESTE ORDEN
		rs.close();
		pst.close();
		con.close();
		return pacientes;
	}
	
	
	public Paciente devolverPacientesMedico(String codcita) throws SQLException {
		Paciente pacientes = null;
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(GET_PACIENTE_CITA_MEDICO);
		@SuppressWarnings("unused")
		boolean res = false;
		pst.setString(1, codcita);
		ResultSet rs = pst.executeQuery();

		while (rs.next()) {

			pacientes = new Paciente(rs.getString("codpaciente"), rs.getString("nombre"), rs.getString("apellido"),
					rs.getInt("movil"), rs.getString("email"), rs.getString("info"), rs.getString("nhistorial"));

		}

//CERRAR EN ESTE ORDEN
		rs.close();
		pst.close();
		con.close();
		return pacientes;
	}
	public Paciente devolverPacientesEquipo(String numequipo) throws SQLException {
		Paciente pacientes = null;
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(GET_PACIENTE_NUMEQUIPO);
		@SuppressWarnings("unused")
		boolean res = false;
		pst.setString(1, numequipo);
		ResultSet rs = pst.executeQuery();

		while (rs.next()) {

			pacientes = new Paciente(rs.getString("codpaciente"), rs.getString("nombre"), rs.getString("apellido"),
					rs.getInt("movil"), rs.getString("email"), rs.getString("info"), rs.getString("nhistorial"));

		}

//CERRAR EN ESTE ORDEN
		rs.close();
		pst.close();
		con.close();
		return pacientes;
	}

	public List<Cita> devolvercitasHistorialFechas(String codcita, java.util.Date date2) throws SQLException {
		List<Cita> citas = new ArrayList<Cita>();
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(GET_CITA_HISTORIAL_FECHA);
		boolean res = false;
		java.sql.Date date = new java.sql.Date(date2.getTime());
		pst.setString(1, codcita);
		pst.setDate(2, date);
		
		ResultSet rs = pst.executeQuery();

		while (rs.next()) {
			if (rs.getByte("urgencia") == 1)
				res = true;
			if(rs.getString("codmedico")!=null) {
				citas.add(new Cita(rs.getString("codcita"), rs.getString("codpaciente"), rs.getString("codmedico"),
						rs.getTime("hinicio"), rs.getTime("hfin"), rs.getDate("fecha"), rs.getString("ubicacion"), res));
			}
			else {
				citas.add(new Cita(rs.getString("codcita"), rs.getString("codpaciente"),
						rs.getTime("hinicio"), rs.getTime("hfin"), rs.getDate("fecha"), rs.getString("ubicacion"), res, rs.getString("numequipo")));
			}

		}

//CERRAR EN ESTE ORDEN
		rs.close();
		pst.close();
		con.close();
		return citas;
	}

	public Empleado devolverEmpleado(String codempleado) throws SQLException {
		Empleado empleado = null;
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(GET_CITAS_EMPLEADOS);
		pst.setString(1, codempleado);
		ResultSet rs = pst.executeQuery();

		while (rs.next()) {

			empleado = new Empleado(rs.getString("codempleado"), rs.getString("nombre"), rs.getString("apellido"),
					rs.getString("pass"), rs.getTime("hinicio"), rs.getTime("hfin"), rs.getDate("dinicio"),
					rs.getDate("dfin"), rs.getString("djornada"));

		}

//CERRAR EN ESTE ORDEN
		rs.close();
		pst.close();
		con.close();
		return empleado;
	}

	public List<Medico> devolverMedicoNombre(String name) throws SQLException {
		List<Medico> medicos = new ArrayList<Medico>();
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(FIND_MED_BY_NAME);
		@SuppressWarnings("unused")
		boolean res = false;
		pst.setString(1, name);
		ResultSet rs = pst.executeQuery();

		while (rs.next()) {

			medicos.add(new Medico(rs.getString("codmedico"), rs.getString("nombre"), rs.getString("apellido"),
					rs.getString("pass"), rs.getTime("hinicio"), rs.getTime("hfin"), rs.getDate("dinicio"),
					rs.getDate("dfin"), rs.getString("djornada")));

		}

//CERRAR EN ESTE ORDEN
		rs.close();
		pst.close();
		con.close();
		return medicos;
	}

	public List<Medico> devolverMedicoApellido(String apellido) throws SQLException {
		List<Medico> medicos = new ArrayList<Medico>();
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(FIND_MED_BY_SURNAME);
		@SuppressWarnings("unused")
		boolean res = false;
		pst.setString(1, apellido);
		ResultSet rs = pst.executeQuery();

		while (rs.next()) {

			medicos.add(new Medico(rs.getString("codmedico"), rs.getString("nombre"), rs.getString("apellido"),
					rs.getString("pass"), rs.getTime("hinicio"), rs.getTime("hfin"), rs.getDate("dinicio"),
					rs.getDate("dfin"), rs.getString("djornada")));

		}

//CERRAR EN ESTE ORDEN
		rs.close();
		pst.close();
		con.close();
		return medicos;
	}

	public List<Medico> devolverMedicoNombreApellido(String name, String apellido) throws SQLException {
		List<Medico> medicos = new ArrayList<Medico>();
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(FIND_MED_BY_NAME_SURNAME);
		@SuppressWarnings("unused")
		boolean res = false;
		pst.setString(1, name);
		pst.setString(2, apellido);
		ResultSet rs = pst.executeQuery();

		while (rs.next()) {

			medicos.add(new Medico(rs.getString("codmedico"), rs.getString("nombre"), rs.getString("apellido"),
					rs.getString("pass"), rs.getTime("hinicio"), rs.getTime("hfin"), rs.getDate("dinicio"),
					rs.getDate("dfin"), rs.getString("djornada")));

		}

//CERRAR EN ESTE ORDEN
		rs.close();
		pst.close();
		con.close();
		return medicos;
	}

	public List<Cita> devolvercitasMedicoPorFecha(Date sDate, String codmedico) throws SQLException {
		List<Cita> citas = new ArrayList<Cita>();
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(GET_CITAS_DATE_MED);
		boolean res = false;
		pst.setString(1, codmedico);
		pst.setDate(2, sDate);

		ResultSet rs = pst.executeQuery();

		while (rs.next()) {
			if (rs.getByte("urgencia") == 0)
				res = true;

			citas.add(new Cita(rs.getString("codcita"), rs.getString("codpaciente"), rs.getString("codmedico"),
					rs.getTime("hinicio"), rs.getTime("hfin"), rs.getDate("fecha"), rs.getString("ubicacion"), res));

		}

//CERRAR EN ESTE ORDEN
		rs.close();
		pst.close();
		con.close();
		return citas;
	}

	public List<Cita> devolvercitasHistorial(String codhistorial) throws SQLException {
		List<Cita> citas = new ArrayList<Cita>();
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(GET_CITA_HISTORIAL);
		boolean res = false;
		pst.setString(1, codhistorial);

		ResultSet rs = pst.executeQuery();

		while (rs.next()) {
			if (rs.getByte("urgencia") == 0)
				res = true;

			if(rs.getString("codmedico")!=null) {
				citas.add(new Cita(rs.getString("codcita"), rs.getString("codpaciente"), rs.getString("codmedico"),
						rs.getTime("hinicio"), rs.getTime("hfin"), rs.getDate("fecha"), rs.getString("ubicacion"), res));
			}
			else {
				citas.add(new Cita(rs.getString("codcita"), rs.getString("codpaciente"),
						rs.getTime("hinicio"), rs.getTime("hfin"), rs.getDate("fecha"), rs.getString("ubicacion"), res, rs.getString("numequipo")));
			}

		}

//CERRAR EN ESTE ORDEN
		rs.close();
		pst.close();
		con.close();
		return citas;
	}

	public List<Cita> devolvercitasHistorialMed(String codhistorial, String codMedico) throws SQLException {
		List<Cita> citas = new ArrayList<Cita>();
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(GET_CITA_HISTORIAL_MED);
		boolean res = false;
		pst.setString(1, codhistorial);
		pst.setString(2, codMedico);

		ResultSet rs = pst.executeQuery();

		while (rs.next()) {
			if (rs.getByte("urgencia") == 0)
				res = true;

			citas.add(new Cita(rs.getString("codcita"), rs.getString("codpaciente"), rs.getString("codmedico"),
					rs.getTime("hinicio"), rs.getTime("hfin"), rs.getDate("fecha"), rs.getString("ubicacion"), res));

		}

//CERRAR EN ESTE ORDEN
		rs.close();
		pst.close();
		con.close();
		return citas;
	}

	public List<Cita> devolverCitas() throws SQLException {
		List<Cita> citas = new ArrayList<Cita>();
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(GET_CITA);
		boolean res = false;
		java.util.Date fecha = new java.util.Date();
		java.sql.Date date = new java.sql.Date(fecha.getTime());
		pst.setDate(1, date);
		ResultSet rs = pst.executeQuery();

		while (rs.next()) {
			if (rs.getByte("urgencia") == 1)
				res = true;
			else
				res = false;

			if(rs.getString("codmedico")!=null) {
				citas.add(new Cita(rs.getString("codcita"), rs.getString("codpaciente"), rs.getString("codmedico"),
						rs.getTime("hinicio"), rs.getTime("hfin"), rs.getDate("fecha"), rs.getString("ubicacion"), res));
			}
			else {
				citas.add(new Cita(rs.getString("codcita"), rs.getString("codpaciente"),
						rs.getTime("hinicio"), rs.getTime("hfin"), rs.getDate("fecha"), rs.getString("ubicacion"), res, rs.getString("numequipo")));
			}

		}

//CERRAR EN ESTE ORDEN
		rs.close();
		pst.close();
		con.close();
		return citas;
	}

	public List<Cita> devolvercitasPorFecha(Date sDate) throws SQLException {
		List<Cita> citas = new ArrayList<Cita>();
		Connection con = new Conexion().getConnectionJDBC();
		//PreparedStatement pst = con.prepareStatement(GET_CITAS_DATE);
		PreparedStatement pst = con.prepareStatement(GET_CITA_EQUIPOD);
		boolean res = false;
		pst.setDate(1, sDate);

		ResultSet rs = pst.executeQuery();

		while (rs.next()) {
			if (rs.getByte("urgencia") == 1)
				res = true;
			if(rs.getString("codmedico")!=null) {
				citas.add(new Cita(rs.getString("codcita"), rs.getString("codpaciente"), rs.getString("codmedico"),
						rs.getTime("hinicio"), rs.getTime("hfin"), rs.getDate("fecha"), rs.getString("ubicacion"), res));
			}
			else {
				citas.add(new Cita(rs.getString("codcita"), rs.getString("codpaciente"),
						rs.getTime("hinicio"), rs.getTime("hfin"), rs.getDate("fecha"), rs.getString("ubicacion"), res, rs.getString("numequipo")));
			}

		}

//CERRAR EN ESTE ORDEN
		rs.close();
		pst.close();
		con.close();
		return citas;
	}

	// ---------------------ventana inicio-----------------------------

	public boolean buscarAdministrativo(String text,String pass) throws SQLException {
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(GET_ADMINISTRATIVO);
		pst.setString(1, text);
		pst.setString(2, pass);
		ResultSet rs = pst.executeQuery();

		boolean res = rs.next();

		rs.close();
		pst.close();
		con.close();
		return res;

	}
	public boolean buscarGerente(String text,String pass) throws SQLException {
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(GET_GERENTE);
		pst.setString(1, text);
		pst.setString(2, pass);
		ResultSet rs = pst.executeQuery();

		boolean res = rs.next();

		rs.close();
		pst.close();
		con.close();
		return res;

	}
	public boolean buscarAuditor(String text,String pass) throws SQLException {
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(GET_AUDITOR);
		pst.setString(1, text);
		pst.setString(2, pass);
		ResultSet rs = pst.executeQuery();

		boolean res = rs.next();

		rs.close();
		pst.close();
		con.close();
		return res;

	}

	public boolean buscarMedicoCod(String text,String pass) throws SQLException {
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(GET_MEDICO);
		pst.setString(1, text);
		pst.setString(2, pass);
		ResultSet rs = pst.executeQuery();

		boolean res = rs.next();

		rs.close();
		pst.close();
		con.close();
		return res;

	}

	public Medico buscarEmpleadoCodigo(String text) throws SQLException {
		Medico m=null;
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(GET_CITAS_EMPLEADOS);
		
		pst.setString(1, text);
		ResultSet rs = pst.executeQuery();

		if( rs.next()) {
			m= new Medico(rs.getString("codempleado"), rs.getString("nombre"), rs.getString("apellido"),
					rs.getString("pass"), rs.getTime("hinicio"), rs.getTime("hfin"), rs.getDate("dinicio"),
					rs.getDate("dfin"), rs.getString("djornada"));
		}
		 

		rs.close();
		pst.close();
		con.close();
		return m;

	}
	
	
	
	public Enfermero buscarEnfermeroCodigo(String text) throws SQLException {
		Enfermero enfermero=null;
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(GET_CITAS_ENF);
		
		pst.setString(1, text);
		ResultSet rs = pst.executeQuery();

		if( rs.next()) {
			enfermero= new Enfermero(rs.getString("codenfermero"), rs.getString("nombre"), rs.getString("apellido"),
					rs.getString("pass"), rs.getTime("hinicio"), rs.getTime("hfin"), rs.getDate("dinicio"),
					rs.getDate("dfin"), rs.getString("djornada"));
		}
		 

		rs.close();
		pst.close();
		con.close();
		return enfermero;

	}
	
	public Medico buscarMedicoCodigo(String text) throws SQLException {
		Medico medico=null;
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(GET_CITAS_MEDICO);
		
		pst.setString(1, text);
		ResultSet rs = pst.executeQuery();

		if( rs.next()) {
			medico= new Medico(rs.getString("codmedico"), rs.getString("nombre"), rs.getString("apellido"),
					rs.getString("pass"), rs.getTime("hinicio"), rs.getTime("hfin"), rs.getDate("dinicio"),
					rs.getDate("dfin"), rs.getString("djornada"));
		}
		 

		rs.close();
		pst.close();
		con.close();
		return medico;

	}


//--------------

	public List<Empleado> buscarEmpleados() throws SQLException {
		List<Empleado> empleados = new ArrayList<Empleado>();
		Connection con = new Conexion().getConnectionJDBC();
		Statement stM = con.createStatement();

		ResultSet rsM = stM.executeQuery(MEDICO_LISTALL);

		while (rsM.next()) {
			// int inicio=rs.findColumn("dinicio");
			// int fin=rs.findColumn("dfin");
			Medico e = new Medico(rsM.getString(1), rsM.getString(2), rsM.getString(3), rsM.getString(4),
					rsM.getTime(5), rsM.getTime(6), rsM.getDate(7), rsM.getDate(8), rsM.getString(9));
			empleados.add(DtoMapper.toDto(e));
			// medicos.add(new Medico( rs.getString(1)));
		}

		// System.out.println(medicos);

		// CERRAR EN ESTE ORDEN
		rsM.close();
		stM.close();

		Statement stE = con.createStatement();

		ResultSet rsE = stE.executeQuery(ENFERMERO_LISTALL);

		while (rsE.next()) {
			// int inicio=rs.findColumn("dinicio");
			// int fin=rs.findColumn("dfin");
			Enfermero e = new Enfermero(rsE.getString(1), rsE.getString(2), rsE.getString(3), rsE.getString(4), rsE.getTime(5), rsE.getTime(6),
					rsE.getDate(7), rsE.getDate(8), rsE.getString(9));
			empleados.add(DtoMapper.toDto(e));
			// medicos.add(new Medico( rs.getString(1)));
		}

		// System.out.println(medicos);

		// CERRAR EN ESTE ORDEN
		rsE.close();
		stE.close();

		con.close();
		return empleados;
	}

	public Date verHoraCita(String cod) throws SQLException {
		Date date = null;
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(VER_HORA_CITA);
		pst.setString(1, cod);
		ResultSet rs = pst.executeQuery();

		if (rs.next()) {
			date = rs.getDate("dinicio");
		}

		rs.close();
		pst.close();
		con.close();
		return date;
	}
	/*
	 * public Cita verCita(String cod) throws SQLException { Cita cita = null;
	 * Connection con = new Conexion().getConnectionJDBC(); PreparedStatement
	 * pst=con.prepareStatement(VER_CITA); pst.setString(1,cod); ResultSet
	 * rs=pst.executeQuery();
	 * 
	 * if(rs.next()) { cita = new Cita(rs.getString(1), rs.getString(2)
	 * ,rs.getString(3), rs.getTime(4), rs.getTime(5), rs.getDate(6),
	 * rs.getString(7), rs.getBoolean(8)); }
	 * 
	 * 
	 * rs.close(); pst.close(); con.close(); return cita;
	 * 
	 * }
	 * 
	 */

	public String devolverCodPaciente(String nombre, String apellido) throws SQLException {
		List<Paciente> pacientes = buscarPaciente("");
		Paciente paciente = null;
		for (int i = 0; i < pacientes.size(); i++) {
			if (pacientes.get(i).getNombre().equals(nombre) && pacientes.get(i).getApellido().equals(apellido)) {
				paciente = pacientes.get(i);
			}
		}
		return paciente.getCodePaciente();
	}

	public Paciente devolverPaciente(String nombre, String apellido, String codPaciente) throws SQLException {
		List<Paciente> pacientes = buscarPaciente("");
		Paciente paciente = null;
		for (int i = 0; i < pacientes.size(); i++) {
			if (pacientes.get(i).getNombre().equals(nombre) && pacientes.get(i).getApellido().equals(apellido)
					&& pacientes.get(i).getCodePaciente().equals(codPaciente)) {
				paciente = pacientes.get(i);
			}
		}
		return paciente;
	}

	public void actualizarUbicacionCita(String ubicacionCita, String codpaciente) throws SQLException {
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(MODIFICAR_UBICACION_CITA);
		pst.setString(1, ubicacionCita);
		pst.setString(2, codpaciente);

		pst.close();
		con.close();
	}

	public HistorialMedico verHistorial(String cod) throws SQLException {
		HistorialMedico historial = null;
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(VER_HISTORIAL);
		pst.setString(1, cod);
		ResultSet rs = pst.executeQuery();

		if (rs.next()) {
			historial = new HistorialMedico(rs.getString(1));
		}

		boolean res = rs.next();

		rs.close();
		pst.close();
		con.close();
		return historial;

	}

	public String verNombreVacuna(String cod) throws SQLException {
		String nombreVacuna = "";
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(VER_VACUNAS);
		pst.setString(1, cod);
		ResultSet rs = pst.executeQuery();

		if (rs.next()) {
			nombreVacuna = rs.getString(1);
		}

		boolean res = rs.next();

		rs.close();
		pst.close();
		con.close();
		return nombreVacuna;
	}

//	public String verNombreEnfermedadPrevias(String cod) throws SQLException {
//		String nombreEnfermPrevia = null;
//		Connection con = new Conexion().getConnectionJDBC();
//		PreparedStatement pst=con.prepareStatement(VER_ENFERM_PREVIAS);
//		pst.setString(1,cod);
//		ResultSet rs=pst.executeQuery();
//		
//		if(rs.next()) {
//			nombreEnfermPrevia = rs.getString(1);
//		}
//		
//		
//		boolean res= rs.next();
//		
//		rs.close();
//		pst.close();
//		con.close();
//		return nombreEnfermPrevia;
//		}

	public int BorrarCita(String codcita) throws SQLException {
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(DELETE_CITA);
		pst.setString(1, codcita);

		int res = pst.executeUpdate();

		pst.close();
		con.close();
		return res;
	}

	/**
	 * MÃ©todo para actualizar la cita cuando la modifica el mÃ©dico
	 * 
	 * @param b
	 * @param cita
	 * @throws SQLException
	 */
	public void actualizarCita(Time hInicioEmpleado, Time hFinEmpleado, boolean acudio, boolean noAcudio, String codigo)
			throws SQLException {
		Connection con = new Conexion().getConnectionJDBC(); // Esto siempre asi
		PreparedStatement pst = con.prepareStatement(ACTUALIZAR_CITA);

		pst.setTime(1, hInicioEmpleado);
		pst.setTime(2, hFinEmpleado);
		pst.setBoolean(3, acudio);
		pst.setBoolean(4, noAcudio);
		pst.setString(5, codigo);

		pst.executeUpdate();

		pst.close();

		con.close(); // Esto siempre al final
	}

	/**
	 * Actualiza los datos de la jornada de los empleados
	 * 
	 * @param hInicio
	 * @param hFin
	 * @param dInicio
	 * @param dFin
	 * @param jornada
	 * @param codigo
	 * @throws SQLException
	 */
	public void updateJornada(Time hInicio, Time hFin, Date dInicio, Date dFin, String jornada, String codigo)
			throws SQLException {
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(UPDATE_JORNADA);

		pst.setTime(1, hInicio);
		pst.setTime(2, hFin);
		pst.setDate(3, dInicio);
		pst.setDate(4, dFin);
		pst.setString(5, jornada);
		pst.setString(6, codigo);

		pst.executeUpdate();

		pst.close();
		con.close();
	}

	public void updateCita(Cita cita, String codmedico) throws SQLException {
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(UPDATE_CITA);

		java.sql.Date date = new java.sql.Date(cita.getDate().getTime());

		pst.setTime(1, cita.gethInicio());
		pst.setTime(2, cita.gethFin());
		pst.setDate(3, date);
		pst.setString(4, cita.getCodMed());
		pst.setString(5, cita.getUbicacion());
		pst.setBoolean(6, cita.isUrgente());
		pst.setString(7, cita.getCodCita());
		pst.setString(8, cita.getCodPaciente());
		pst.setString(9, codmedico);

		pst.executeUpdate();

		pst.close();
		con.close();
	}

	public List<String> buscarVacunas(String buscando) throws SQLException {
		List<String> nombresVacunas = new ArrayList<String>();
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement st = con.prepareStatement(VER_VACUNAS);
		st.setString(1, buscando);

		ResultSet rs = st.executeQuery();

		while (rs.next()) {
			nombresVacunas.add(rs.getString(1) + "\t" + rs.getDate(2));
		}

		// CERRAR EN ESTE ORDEN
		rs.close();
		st.close();
		con.close();
		return nombresVacunas;
	}

	public List<String> buscarEnfermPrevias(String nHistorial) throws SQLException {
		List<String> nombresEnfPrev = new ArrayList<String>();
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement st = con.prepareStatement(VER_ASIGNA_ENFERM);
		st.setString(1, nHistorial);
		ResultSet rs = st.executeQuery();

		while (rs.next()) {
			nombresEnfPrev.add(rs.getString(1) + "\t" + rs.getDate(2));
		}

		// CERRAR EN ESTE ORDEN
		rs.close();
		st.close();
		con.close();
		return nombresEnfPrev;
	}

	public List<String> buscarCausas(String nHistorial) throws SQLException {
		List<String> nombresCausas = new ArrayList<String>();
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement st = con.prepareStatement(VER_ASIGNA_CAUSAS);
		st.setString(1, nHistorial);
		ResultSet rs = st.executeQuery();

		while (rs.next()) {
			nombresCausas.add(rs.getString(1) + "\t" + rs.getDate(2));
		}
		for (int i = 0; i < nombresCausas.size(); i++) {
			System.out.print(nombresCausas.get(i));
		}

		// CERRAR EN ESTE ORDEN
		rs.close();
		st.close();
		con.close();
		return nombresCausas;
	}

	public void actualizarAsignaCausa(String causas, String nombre, String historial, Date fecha, Time hora,
			String codempelado) throws SQLException {
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(INSERT_ASIGNA_CAUSA);
		pst.setString(1, causas);
		pst.setString(2, nombre);
		pst.setString(3, historial);
		pst.setString(4, codempelado);
		pst.setDate(5, fecha);
		pst.setTime(6, hora);

		pst.executeUpdate();

		pst.close();
		con.close();

	}

	public void actualizarCausa(String nombre) throws SQLException {
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(INSERT_CAUSAS);
		pst.setString(1, nombre);

		pst.executeUpdate();

		pst.close();
		con.close();

	}

	public String verCausas(String cod) throws SQLException {
		String infoCausas = "";
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(VER_CAUSAS);
		pst.setString(1, cod);
		ResultSet rs = pst.executeQuery();

		if (rs.next()) {
			infoCausas = rs.getString(1);
		}

		boolean res = rs.next();

		rs.close();
		pst.close();
		con.close();
		return infoCausas;
	}

	public List<Cita> devolvercitas(String codPaciente) throws SQLException {
		List<Cita> citas = new ArrayList<Cita>();
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement st = con.prepareStatement(VER_CITA);

		st.setString(1, codPaciente);
		ResultSet rs = st.executeQuery();

		while (rs.next()) {

			citas.add(DtoCita.cita(rs));

		}

//CERRAR EN ESTE ORDEN
		rs.close();
		st.close();
		con.close();
		return citas;
	}

	/**
	 * MÃ©todo para guardar una nueva asigna preinscripcion
	 * 
	 * @param ap
	 */
	public void nuevaAsignaPreinscripcion(AsignaPreinscripcion ap) throws SQLException {
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(ADD_ASIGNA_PPREINSCRIPCION);

		String codAsignaPrescripcion = ap.getCodigoAsignaPreinscripcion();
		String nombrePrescripcion = ap.getCodigoPreinscripcion();
		String nHistorial = ap.getCodigoHistorial();
		int cantidad = ap.getCantidad();
		int intervalo = ap.getIntervalo();
		int duracion = ap.getDuracion();
		String instrucciones = ap.getInstrucciones();
		java.sql.Date fecha = new java.sql.Date(ap.getFecha().getTime());

		Time hour = new Time(fecha.getTime());

		pst.setString(1, codAsignaPrescripcion);
		pst.setString(2, nombrePrescripcion);
		pst.setString(3, nHistorial);
		pst.setString(4, ap.getCodempleado());
		pst.setInt(5, cantidad);
		pst.setInt(6, intervalo);
		pst.setInt(7, duracion);
		pst.setString(8, instrucciones);
		pst.setDate(9, fecha);
		pst.setTime(10, hour);

		pst.executeUpdate();

		pst.close();
		con.close();

	}

	/**
	 * MÃ©todo para guardar una nueva preinscripcion
	 * 
	 * @param p
	 * @throws SQLException
	 */
	public void nuevaPreinscripcion(Preinscripcion p) throws SQLException {
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(ADD_PREINSCRIPCION);

		String nombrePrescripcion = p.getNombre();
		boolean medicamento = p.isMedicamento();

		pst.setString(1, nombrePrescripcion);
		pst.setBoolean(2, medicamento);

		pst.executeUpdate();
		pst.close();
		con.close();
	}

	public List<Preinscripcion> listarPrescripciones() throws SQLException {

		List<Preinscripcion> prescripciones = new ArrayList<Preinscripcion>(); // Creo la lista que voy a devolver

		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(LIST_PREINSCRIPCIONES);

		ResultSet rs = pst.executeQuery(); // Creo el resultSet

		while (rs.next()) {

			prescripciones.add(new Preinscripcion(rs.getString("nombrePrescripcion"), rs.getBoolean("medicamento")));
		}

		rs.close();
		pst.close();
		con.close();

		return prescripciones;
	}

	/**
	 * MÃ©todo para buscar todas las preinscripciones que se le han asignado a un
	 * paciente en concreto
	 * 
	 * @param historial
	 * @return
	 * @throws SQLException
	 */
	public List<String> buscarPreinscripcionesAsignadas(String nhistorial) throws SQLException {
		List<String> nombrePreinscripciones = new ArrayList<String>();

		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(VER_PREINSCRIPCIONES_ASIGNADAS);

		pst.setString(1, nhistorial); // busco por n de historial

		ResultSet rs = pst.executeQuery(); // Creo el resultSet

		while (rs.next()) {
			nombrePreinscripciones
					.add(rs.getString(2) + "	Cantidad: " + rs.getString(5) + "	Intervalo(dias): " + rs.getString(6)
							+ "	Duracion(horas): " + rs.getString(7) + "	Instrucciones: " + rs.getString(8));
		}

		// CERRAR EN ESTE ORDEN
		rs.close();
		pst.close();
		con.close();
		return nombrePreinscripciones;
	}
	
	
	
	public List<AsignaPreinscripcion> asignaPrescricpionesFechaHistorial(String nhistorial) throws SQLException {
		List<AsignaPreinscripcion> preinscripciones = new ArrayList<AsignaPreinscripcion>();

		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(VER_ASIGNA_PREINSCRIPCIONES);

		pst.setString(1, nhistorial); 
		java.sql.Date fecha = new java.sql.Date(new java.util.Date().getTime());
		pst.setDate(2,fecha ); 

		ResultSet rs = pst.executeQuery(); 

		while (rs.next()) {
			preinscripciones.add(new AsignaPreinscripcion(rs.getString("codasigprescripcion"), rs.getString("nhistorial"),
					rs.getString("codempleado"),rs.getString("nombreprescripcion"),
				rs.getInt("cantidad"),rs.getInt("intervalo"),rs.getInt("duracion"),rs.getString("instrucciones"),
				rs.getDate("fecha"),rs.getTime("hora")));
					
			
		}

		// CERRAR EN ESTE ORDEN
		rs.close();
		pst.close();
		con.close();
		return preinscripciones;
	}

	public List<AsignaPreinscripcion> asignaPrescripcionesHistorial(String nhistorial) throws SQLException {
		List<AsignaPreinscripcion> preinscripciones = new ArrayList<AsignaPreinscripcion>();

		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(VER_PREINSCRIPCIONES_ASIGNADAS);

		pst.setString(1, nhistorial); 

		ResultSet rs = pst.executeQuery(); 

		while (rs.next()) {
			preinscripciones.add(new AsignaPreinscripcion(rs.getString("codasigprescripcion"), rs.getString("nhistorial"),
					rs.getString("codempleado"),rs.getString("nombreprescripcion"),
				rs.getInt("cantidad"),rs.getInt("intervalo"),rs.getInt("duracion"),rs.getString("instrucciones"),
				rs.getDate("fecha"),rs.getTime("hora")));
					
			
		}

		// CERRAR EN ESTE ORDEN
		rs.close();
		pst.close();
		con.close();
		return preinscripciones;
	}
	public List<AsignaDiagnostico> asignaDiagnosticoHistorial(String nhistorial) throws SQLException {
		List<AsignaDiagnostico> diagnsoticos = new ArrayList<AsignaDiagnostico>();

		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(VER_DIAGNOSTICOS_ASIGNADOS);

		pst.setString(1, nhistorial); 

		ResultSet rs = pst.executeQuery(); 

		while (rs.next()) {
			diagnsoticos.add(new AsignaDiagnostico(rs.getString("codAsigDiagnostico"), rs.getString("nombreDiagnostico"),
					rs.getString("coddiagnostico"),rs.getString("historial"),
				rs.getString("codempleado"),rs.getDate("fecha"),rs.getTime("hora")));
					
			
		}

		// CERRAR EN ESTE ORDEN
		rs.close();
		pst.close();
		con.close();
		return diagnsoticos;
	}
	public List<AsignaVacuna> asignaVacunaHistorial(String nhistorial) throws SQLException {
		List<AsignaVacuna> vacuna = new ArrayList<AsignaVacuna>();

		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(VER_VACUNAS_ASIGNADAS);

		pst.setString(1, nhistorial); 

		ResultSet rs = pst.executeQuery(); 

		while (rs.next()) {
			vacuna.add(new AsignaVacuna(rs.getString("codAsigVac"), rs.getString("nombreVacuna"),rs.getString("historial"),
				rs.getString("codempleado"),rs.getDate("fecha"),rs.getTime("hora")));
					
			
		}

		// CERRAR EN ESTE ORDEN
		rs.close();
		pst.close();
		con.close();
		return vacuna;
	}
	public List<AsignaCausa> asignaCausaHistorial(String nhistorial) throws SQLException {
		List<AsignaCausa> causa = new ArrayList<AsignaCausa>();

		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(VER_CAUSAS_ASIGNADAS);

		pst.setString(1, nhistorial); 

		ResultSet rs = pst.executeQuery(); 

		while (rs.next()) {
			causa.add(new AsignaCausa(rs.getString("codAsigCausa"), rs.getString("nombreCausa"),rs.getString("historial"),
				rs.getString("codempleado"),rs.getDate("fecha"),rs.getTime("hora")));
					
			
		}

		// CERRAR EN ESTE ORDEN
		rs.close();
		pst.close();
		con.close();
		return causa;
	}
	
	
	public List<AsignaCausa> asignaProcedimientoHistorial(String nhistorial) throws SQLException {
		List<AsignaCausa> causa = new ArrayList<AsignaCausa>();

		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(VER_CAUSAS_ASIGNADAS);

		pst.setString(1, nhistorial); 

		ResultSet rs = pst.executeQuery(); 

		while (rs.next()) {
			causa.add(new AsignaCausa(rs.getString("codAsigCausa"), rs.getString("nombreCausa"),rs.getString("historial"),
				rs.getString("codempleado"),rs.getDate("fecha"),rs.getTime("hora")));
					
			
		}

		// CERRAR EN ESTE ORDEN
		rs.close();
		pst.close();
		con.close();
		return causa;
	}
	
	
	
	public List<Causas> buscarNombreTodasCausas() throws SQLException {
		List<Causas> nombresCausas = new ArrayList<Causas>();
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement st = con.prepareStatement(VER_NOMBRE_CAUSA);
		ResultSet rs = st.executeQuery();

		while (rs.next()) {
			nombresCausas.add(new Causas(rs.getString(1)));
		}

		// CERRAR EN ESTE ORDEN
		rs.close();
		st.close();
		con.close();
		return nombresCausas;
	}

//AÃ‘ADIR QUERY VACACIONES
	public void asignarVacaciones(Vacaciones vacaciones) throws SQLException {
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(ADD_VACACIONES);

		String codVac = vacaciones.getCodVad();
		String codEmpleado = vacaciones.getCodMed();
		String codAdmin = vacaciones.getCodAdmin();
		Date dateInicio = (Date) vacaciones.getdInicio();
		Date dateFinal = (Date) vacaciones.getdFinal();

		pst.setString(1, codVac);
		pst.setString(2, codEmpleado);
		pst.setString(3, codAdmin);
		pst.setDate(4, dateInicio);
		pst.setDate(5, dateFinal);

		pst.executeUpdate();

		pst.close();
		con.close();
	}

	/**
	 * MÃ©todo para listar todas las vacunas que tenemos en la base de datos
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<Vacuna> listarVacunas() throws SQLException {
		List<Vacuna> vacunas = new ArrayList<Vacuna>(); // Creo la lista que voy a devolver

		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(LIST_VACUNAS);

		ResultSet rs = pst.executeQuery(); // Creo el resultSet

		while (rs.next()) {
			vacunas.add(new Vacuna(rs.getString("nombreVacuna")));
		}

		rs.close();
		pst.close();
		con.close();

		return vacunas;
	}

	/**
	 * MÃ©todo para guardar las vacunas que se le han asignado a un paciente
	 * 
	 * @param av
	 * @throws SQLException
	 */
	public void nuevaAsignaVacuna(AsignaVacuna av) throws SQLException {
		Connection con = new Conexion().getConnectionJDBC();

		PreparedStatement pst = con.prepareStatement(ADD_ASIGNA_VACUNA);

		String codasigvac = av.getCodVacuna();
		String nombreVacuna = av.getNombreVacuna();
		String historial = av.getCodHistorial();
		String codEmpleado = av.getCodEmpleado();
		java.sql.Date fecha = new java.sql.Date(av.getDate().getTime());
		Time hour = new Time(fecha.getTime());

		pst.setString(1, codasigvac);
		pst.setString(2, nombreVacuna);
		pst.setString(3, historial);
		pst.setString(4, codEmpleado);
		pst.setDate(5, fecha);
		pst.setTime(6, hour);

		pst.executeUpdate();
		pst.close();
		con.close();

	}

	/**
	 * MÃ©todo para sacar todas las vacunas que se le han asignado a un paciente
	 * 
	 * @param historial
	 * @return
	 * @throws SQLException
	 */
	public List<String> buscarVacunasAsignadas(String historial) throws SQLException {
		List<String> nombreVacunas = new ArrayList<String>();

		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst1 = con.prepareStatement(GET_MEDICO_NOMBRE);
		PreparedStatement pst = con.prepareStatement(VER_VACUNAS_ASIGNADAS);

		pst.setString(1, historial); // busco por n de historial

		ResultSet rs = pst.executeQuery(); // Creo el resultSet

		while (rs.next()) {
			pst1.setString(1, rs.getString("codempleado"));
			ResultSet rs1 = pst1.executeQuery();
			while (rs1.next()) {

			nombreVacunas.add(rs.getString(2) + "	Fecha: " + rs.getString(5) + "	Hora: " + rs.getString(6)
					+ "		Empleado: " + rs1.getString("nombre"));
			}
			
			rs1.close();
		}

		// CERRAR EN ESTE ORDEN
		rs.close();
		pst.close();
		pst1.close();
		con.close();
		return nombreVacunas;
		
		

		}

	/**
	 * MÃ©todo que me devuelve una lista con todos los diagnosticos que tengo en la
	 * bd
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<Diagnostico> listarDiagnosticos() throws SQLException {
		List<Diagnostico> diagnosticos = new ArrayList<Diagnostico>(); // Creo la lista que voy a devolver

		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(LIST_DIAGNOSTICOS);

		ResultSet rs = pst.executeQuery(); // Creo el resultSet

		while (rs.next()) {

			diagnosticos.add(new Diagnostico(rs.getString("codDiagnostico"), rs.getString("nombreDiagnostico")));
		}

		rs.close();
		pst.close();
		con.close();

		return diagnosticos;
	}

	/**
	 * MÃ©todo para guardar todos los diagnosticos que se le han asignaso a un
	 * paciente
	 * 
	 * @param ad
	 * @throws SQLException
	 */
	public void nuevaAsignaDiagnostico(AsignaDiagnostico ad) throws SQLException {
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(ADD_ASIGNA_DIAGNOSTICO);

		String codAsigDiagnostico = ad.getCodAsigDiagnostico();
		String nombreDiagnostico = ad.getNombreDiagnostico();
		String codDiagnostico = ad.getnDiagnostico();
		String historial = ad.getnHistorial();
		String codempleado = ad.getCodMedico();
		java.sql.Date fecha = new java.sql.Date(ad.getFecha().getTime());
		Time hora = new Time(fecha.getTime());

		pst.setString(1, codAsigDiagnostico);
		pst.setString(2, nombreDiagnostico);
		pst.setString(3, codDiagnostico);
		pst.setString(4, historial);
		pst.setString(5, codempleado);
		pst.setDate(6, fecha);
		pst.setTime(7, hora);

		pst.executeUpdate();

		pst.close();
		con.close();

	}

	/**
	 * Buscar los diagnosticos que se le han asignado a un paciente
	 * 
	 * @param historial
	 * @return
	 * @throws SQLException
	 */
	public List<String> buscarDiagnosticosAsignados(String historial) throws SQLException {
		List<String> nombreDiagnosticos = new ArrayList<String>();

		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst1 = con.prepareStatement(GET_MEDICO_NOMBRE);
		PreparedStatement pst = con.prepareStatement(VER_DIAGNOSTICOS_ASIGNADOS);

		pst.setString(1, historial); // busco por n de historial

		ResultSet rs = pst.executeQuery(); // Creo el resultSet

		while (rs.next()) {
			pst1.setString(1, rs.getString("codempleado"));
			ResultSet rs1 = pst1.executeQuery();
			while (rs1.next()) {
				nombreDiagnosticos.add(rs.getString(2) + "\t" +"Fecha: " + rs.getDate(6) + "\t" +"Hora: " + rs.getTime(7)
					+ "\t" + "\t" + "Empleado: " + rs1.getString("nombre"));
			}
			
			rs1.close();
		}

		// CERRAR EN ESTE ORDEN
		
		rs.close();
		pst.close();
		pst1.close();
		con.close();
		return nombreDiagnosticos;
		
		
	
	}

	public List<Cita> devolvercitasHistorialFechasMedico(String historial, java.util.Date fecha, String codmedico)
			throws SQLException {
		List<Cita> citas = new ArrayList<Cita>();
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(GET_CITA_FECHA_HISTORIAL_MED);
		boolean res = false;
		java.sql.Date date = new java.sql.Date(fecha.getTime());
		pst.setDate(1, date);
		pst.setString(2, historial);
		pst.setString(3, codmedico);
		ResultSet rs = pst.executeQuery();

		while (rs.next()) {
			if (rs.getByte("urgencia") == 1)
				res = true;
			citas.add(new Cita(rs.getString("codcita"), rs.getString("codpaciente"), rs.getString("codmedico"),
					rs.getTime("hinicio"), rs.getTime("hfin"), rs.getDate("fecha"), rs.getString("ubicacion"), res));

		}

//CERRAR EN ESTE ORDEN
		rs.close();
		pst.close();
		con.close();
		return citas;
	}

	public HistorialMedico HistorialCita(String codcita, String codPaciente) throws SQLException {
		HistorialMedico hs = null;
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(HISTORIAL_CITA);

		pst.setString(1, codcita);
		pst.setString(2, codPaciente);
		
		//pst.setString(3, codMedico);
		ResultSet rs = pst.executeQuery();
		if (rs.next()) {

			hs = new HistorialMedico(rs.getString(1));

		}
		rs.close();
		pst.close();
		con.close();
		return hs;
	}

	public Cita citaCod(String codcita, String codPaciente) throws SQLException {
		Cita cita = null;
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(CITA_CODCITA);

		pst.setString(1, codcita);
		pst.setString(2, codPaciente);
		ResultSet rs = pst.executeQuery();
		if (rs.next()) {

			cita = DtoCita.cita(rs);

		}
		rs.close();
		pst.close();
		con.close();
		return cita;
	}

	public Paciente  pacienteHistorial(String historial) throws SQLException {
		Paciente p = null;
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(PACIENTE_HISTORIAL);
		pst.setString(1, historial);
		ResultSet rs = pst.executeQuery();
		if (rs.next()) {

			p=(new Paciente(rs.getString("codpaciente"), rs.getString("nombre"), rs.getString("apellido"),
					rs.getInt("movil"), rs.getString("email"), rs.getString("info"), rs.getString("nhistorial")));
		}
		rs.close();
		pst.close();
		con.close();
		return p;
	}
	

	/*
	 * SI ESTAS LEYENDO ESTO, ES PORQUE TE DA ERROR PORQUE CAMBIE EN VACUNAS_PERSONA NHISTORIAL POR HISTORIAL
	 * Y CODVACUNA POR NOMBREVACUNA AQUI ABAJO
	 * 																					-Raul
	 */
	public List<AsignaVacuna> verVacunasPaciente(String nHistorial) throws SQLException {
		List<AsignaVacuna> listaVacunas = new ArrayList<AsignaVacuna>();

		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(VACUNAS_PERSONA);
		pst.setString(1, nHistorial);
		ResultSet rs = pst.executeQuery();
		while (rs.next()) {
			AsignaVacuna v = new AsignaVacuna(rs.getString("codasigvac"), rs.getString("nombreVacuna"), rs.getString("historial"), rs.getString("codempleado"),
					rs.getDate("fecha"), rs.getTime("hora"));
			listaVacunas.add(v);
		}

		rs.close();
		pst.close();
		con.close();

		return listaVacunas;
	}

	public Acompañante getAcompañante(String codAcompañante) throws SQLException {
		Acompañante acompañante = null;
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(GET_ACOMPAÑANTE);
		pst.setString(1, codAcompañante);
		ResultSet rs = pst.executeQuery();
		if (rs.next()) {
			acompañante = new Acompañante(rs.getString("codacompañante"), rs.getString("codpaciente"),
					rs.getString("nombre"), rs.getString("apellido"), rs.getInt("movil"), rs.getString("correo"));
		}

		rs.close();
		pst.close();
		con.close();

		return acompañante;
	}
	
	
	
	public Administrativo getAdmin(String codAdmin) throws SQLException {
		Administrativo admin = null;
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(ADMIN_NAME);
		pst.setString(1, codAdmin);
		ResultSet rs = pst.executeQuery();
		if (rs.next()) {
			admin = new Administrativo(rs.getString("pass"), rs.getString("codadmin"),rs.getString("nombre"), rs.getString("apellido"));
		}

		rs.close();
		pst.close();
		con.close();

		return admin;
	}

	public boolean buscarSalasIguales(Time timeInicio, Date sDate, String sala) throws SQLException {
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(VER_SALA_CITA);
		pst.setTime(1, timeInicio);
		pst.setDate(2, sDate);
		pst.setString(3, sala);
		ResultSet rs = pst.executeQuery();

		boolean res = rs.next();

		rs.close();
		pst.close();
		con.close();
		return res;

	}

//ACCIONES

	public List<String> calcularNAcciones() throws SQLException {
		List<String> listaAcciones = new ArrayList<String>();

		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(LISTAR_NACCIONES);
		ResultSet rs = pst.executeQuery();
		if (rs.next()) {
			listaAcciones.add(rs.getString(1));
		}

		rs.close();
		pst.close();
		con.close();

		return listaAcciones;

	}

	public void guardarAccion(Accion a) throws SQLException {
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(INSERT_ACCION);

		java.sql.Date fecha = new java.sql.Date(a.getDate().getTime());
		Time hour = new Time(fecha.getTime());
		pst.setString(1, a.getNaccion());
		pst.setString(2, a.getCodaccion());
		pst.setDate(3, fecha);
		pst.setTime(4, hour);
		pst.setString(5, a.getMensajeAccion());

		pst.executeUpdate();

		pst.close();
		con.close();

	}
	
	public List<Accion> devolverAccionesAdminPorFecha(Date dinicio, Date dfin) throws SQLException {
		List<Accion> acciones = new ArrayList<Accion>();
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst=con.prepareStatement(GET_ACCIONES_DATE_ADM);
		pst.setDate(1, dinicio);
		pst.setDate(2, dfin);
		ResultSet rs = pst.executeQuery();
		
		
	while(rs.next()) {
		acciones.add(new Accion(rs.getString(1),rs.getString(2),rs.getDate(3),rs.getTime(4), rs.getString(5)));
	}
	
	//CERRAR EN ESTE ORDEN
	rs.close();
	pst.close();
	con.close();
	return acciones;
}
	
	public List<AccionEmpleado> devolverAccionesEmpleadoPorFecha(Date dinicio, Date dfin) throws SQLException {
		List<AccionEmpleado> acciones = new ArrayList<AccionEmpleado>();
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst=con.prepareStatement(GET_ACCIONES_DATE_ADM);
		pst.setDate(1, dinicio);
		pst.setDate(2, dfin);
		ResultSet rs = pst.executeQuery();
		
		
	while(rs.next()) {
		acciones.add(new AccionEmpleado(rs.getString(1),rs.getString(2),rs.getDate(3),rs.getTime(4), rs.getString(5)));
	}
	
	//CERRAR EN ESTE ORDEN
	rs.close();
	pst.close();
	con.close();
	return acciones;
}
	
	public List<Accion> devolverAccionesAdmin() throws SQLException {
		List<Accion> acciones = new ArrayList<Accion>();
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst=con.prepareStatement(LISTAR_NACCIONES);
		ResultSet rs = pst.executeQuery();
		
	while(rs.next()) {
		acciones.add(new Accion(rs.getString(1),rs.getString(2),rs.getDate(3),rs.getTime(4), rs.getString(5)));
	}
	
	//CERRAR EN ESTE ORDEN
	rs.close();
	pst.close();
	con.close();
	return acciones;
	}
	
	public List<AccionEmpleado> devolverAccionesEmlpeado() throws SQLException {
		List<AccionEmpleado> acciones = new ArrayList<AccionEmpleado>();
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst=con.prepareStatement(LISTAR_NACCIONES_EMPLEADO);
		ResultSet rs = pst.executeQuery();
		
	while(rs.next()) {
		acciones.add(new AccionEmpleado(rs.getString(1),rs.getString(2),rs.getDate(3),rs.getTime(4), rs.getString(5)));
	}
	
	
	
	//CERRAR EN ESTE ORDEN
	rs.close();
	pst.close();
	con.close();
	return acciones;
}

	public void guardarAccionEmpleado(AccionEmpleado a) throws SQLException {
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(INSERT_ACCION_EMPLEADO);

		java.sql.Date fecha = new java.sql.Date(a.getDate().getTime());
		Time hour = new Time(fecha.getTime());
		pst.setString(1, a.getNaccion());
		pst.setString(2, a.getCodempleado());
		pst.setDate(3, fecha);
		pst.setTime(4, hour);
		pst.setString(5, a.getMensajeAccion());

		pst.executeUpdate();

		pst.close();
		con.close();

	}

	public void crearHistorial(String codHistorial) throws SQLException {
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(INSERT_HISTORIAL);
		pst.setString(1, codHistorial);

		pst.executeUpdate();

		pst.close();
		con.close();

	}

	public void crearPaciente(Paciente p) throws SQLException {
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(INSERT_PACIENTE);
		pst.setString(1, p.getCodePaciente());
		pst.setString(2, p.getNombre());
		pst.setString(3, p.getApellido());
		pst.setInt(4, p.getMovil());
		pst.setString(5, p.getEmail());
		pst.setString(6, p.getInfo());
		pst.setString(7, p.getHistorial());

		pst.executeUpdate();

		pst.close();
		con.close();

	}
	public void crearAcompañante(Acompañante acom) throws SQLException {
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(INSERT_ACOMPAÑANTE);
		pst.setString(1, acom.getCodAcompañante());
		pst.setString(2, acom.getCodPaciente());
		pst.setString(3, acom.getNombre());
		pst.setString(4, acom.getApellido());
		pst.setInt(5,acom.getMovil());
		pst.setString(6, acom.getEmail());
		
		pst.executeUpdate();

		pst.close();
		con.close();

	}
	public boolean checkCodeAcompañante(String cod) throws SQLException {

		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(CHECK_COMPANYCOD_CODE);
		pst.setString(1, cod);
		ResultSet rs = pst.executeQuery();

		boolean res = rs.next();

		rs.close();
		pst.close();
		con.close();
		return res;

	}

	
	
	/**
	 * MÃ©todo para sacar todos los correos que tiene un mÃ©dico
	 * @return
	 * @throws SQLException 
	 */
	public List<Correo> buscarCorreos(String codMedicoDestino) throws SQLException {
		List<Correo> correos = new ArrayList<Correo>();

		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(VER_CORREOS);

		pst.setString(1, codMedicoDestino); // busco por codigo mÃ©dico que tiene que recibir los correos
		
		ResultSet rs = pst.executeQuery(); // Creo el resultSet

		while (rs.next()) {

			String codCorreo = rs.getString(1);
			String codMedicoOrigen = rs.getString(3);
			String asunto = rs.getString(4);
			String mensaje = rs.getString(5);
			Date date = rs.getDate(6);
			Time time = rs.getTime(7);
			
			Correo correo = new Correo(codCorreo, codMedicoDestino, codMedicoOrigen, asunto, mensaje, date, time); // Creo mi objeto
			
			correos.add(correo); // Añado el correo a mi lista
		}
		


		// CERRAR EN ESTE ORDEN
		rs.close();
		pst.close();
		con.close();
		
		return correos;
	}
	
	
	
	
	
	
	public String buscarNombreMedico (String codMedico) throws SQLException {
		String nombreMedico = null;
		
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(GET_MEDICO_NOMBRE);
		
		pst.setString(1, codMedico);
		
		ResultSet rs = pst.executeQuery();
		
		
		if (rs.next()) {
			nombreMedico = rs.getString("nombre");
		}
		
		

		rs.close();
		pst.close();
		con.close();
		
		return nombreMedico;	
	}

	public List<Accion> devolverAccionesAdminNombre(String nombre) throws SQLException {
		List<Accion> acciones = new ArrayList<Accion>();
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst=con.prepareStatement(GET_ACCIONES_NAME_ADM);
		pst.setString(1, nombre);
		ResultSet rs = pst.executeQuery();
		
		
	while(rs.next()) {
		acciones.add(new Accion(rs.getString(1),rs.getString(2),rs.getDate(3),rs.getTime(4), rs.getString(5)));
	}
	
	//CERRAR EN ESTE ORDEN
	rs.close();
	pst.close();
	con.close();
	return acciones;
	}
	
	public List<AccionEmpleado> devolverAccionesEmpleadoNombre(String nombre) throws SQLException {
		List<AccionEmpleado> acciones = new ArrayList<AccionEmpleado>();
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst=con.prepareStatement(GET_ACCIONESEMPLEADO_NAME);
		pst.setString(1, nombre);
		ResultSet rs = pst.executeQuery();
		
		
	while(rs.next()) {
		acciones.add(new AccionEmpleado(rs.getString(1),rs.getString(2),rs.getDate(3),rs.getTime(4), rs.getString(5)));
	}
	
	//CERRAR EN ESTE ORDEN
	rs.close();
	pst.close();
	con.close();
	return acciones;
	}
	
	public List<Accion> devolverAccionesAdminFechaNombre(String name, Date dinicio, Date dfin) throws SQLException {
		List<Accion> acciones = new ArrayList<Accion>();
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst=con.prepareStatement(GET_ACCIONES_NAME_DATE_ADM);
		pst.setString(1, name);
		pst.setDate(2, dinicio);
		pst.setDate(3, dfin);
		ResultSet rs = pst.executeQuery();
		
		
	while(rs.next()) {
		acciones.add(new Accion(rs.getString(1),rs.getString(2),rs.getDate(3),rs.getTime(4), rs.getString(5)));
	}
	
	//CERRAR EN ESTE ORDEN
	rs.close();
	pst.close();
	con.close();
	return acciones;
}
	
	public List<AccionEmpleado> devolverAccionesEmpleadoFechaNombre(String name, Date dinicio, Date dfin) throws SQLException {
		List<AccionEmpleado> acciones = new ArrayList<AccionEmpleado>();
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst=con.prepareStatement(GET_ACCIONESEMPLEADO_NAME_DATE_ADM);
		pst.setString(1, name);
		pst.setDate(2, dinicio);
		pst.setDate(3, dfin);
		ResultSet rs = pst.executeQuery();
		
		
	while(rs.next()) {
		acciones.add(new AccionEmpleado(rs.getString(1),rs.getString(2),rs.getDate(3),rs.getTime(4), rs.getString(5)));
	}
	
	//CERRAR EN ESTE ORDEN
	rs.close();
	pst.close();
	con.close();
	return acciones;
}
	
	
	/**
	 * MÃ©todo que me guarda un correo nuevo en la base de datos
	 * @param nuevoCorreo
	 * @throws SQLException 
	 */
	public void crearCorreo(Correo nuevoCorreo) throws SQLException {
		
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(ADD_CORREO);
		
		String codCorreo = nuevoCorreo.getCodCorreo();
		String codMedicoDestino = nuevoCorreo.getCodMedicoDestino();
		String codMedicoOrigen = nuevoCorreo.getCodMedicoOrigen();
		String asunto = nuevoCorreo.getAsunto();
		String mensaje = nuevoCorreo.getMensaje();
		java.sql.Date fecha = new java.sql.Date(nuevoCorreo.getDate().getTime());
		Time hora = new Time(fecha.getTime());
		

		
		pst.setString(1, codCorreo);
		pst.setString(2, codMedicoDestino);
		pst.setString(3, codMedicoOrigen);
		pst.setString(4, asunto);
		pst.setString(5, mensaje);
		pst.setDate(6, fecha);
		pst.setTime(7, hora);

		pst.executeUpdate();

		pst.close();
		con.close();
		
	}
	
	/**
	 * MÃ©todo para buscar el cÃ³digo de un mÃ©dico a partir de su nombre
	 * @param codMedico
	 * @return
	 * @throws SQLException
	 */
	public String buscarCodigoMedico(String nombre) throws SQLException {
		String codEmpleado = null;
		
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(GET_MEDICO_CODIGO);
		
		pst.setString(1, nombre);
		
		ResultSet rs = pst.executeQuery();
		
		
		if (rs.next()) {
			codEmpleado = rs.getString("codEmpleado");
		}
		
		

		rs.close();
		pst.close();
		con.close();
		
		return codEmpleado;	
	}

	
	/**
	 * MÃ©todo para eliminar un correo de la lista de correos
	 * @param correo
	 * @throws SQLException 
	 */
	public void eliminarMensaje(Correo correo) throws SQLException {
		String codCorreo = correo.getCodCorreo(); // El cÃ³digo del correo que queremos eliminar
		
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(DELETE_CORREO);
		
		pst.setString(1, codCorreo);
		
		pst.executeUpdate();		
		pst.close();
		con.close();
		
	}
	
	public void nuevoEquipo(String numequipo) throws SQLException {
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(INSERT_EQUIPO);

		pst.setString(1, numequipo);

		pst.executeUpdate();

		pst.close();
		con.close();
	}
	
	public List<Equipo> calcularEquipos() throws SQLException {
		List<Equipo> listaEquipos = new ArrayList<Equipo>();

		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(LISTAR_EQUIPOS);
		ResultSet rs = pst.executeQuery();
		while (rs.next()) {
			listaEquipos.add(new Equipo(rs.getString(1)));
		}

		rs.close();
		pst.close();
		con.close();

		return listaEquipos;

	}
	
	public List<Equipo> devolverEquipoNombre(String nombre) throws SQLException {
		List<Equipo> listaEquipos = new ArrayList<Equipo>();

		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(LISTAR_EQUIPOS_NOMBRE);
		pst.setString(1, nombre);
		ResultSet rs = pst.executeQuery();
		while (rs.next()) {
			listaEquipos.add(new Equipo(rs.getString(1)));
		}

		rs.close();
		pst.close();
		con.close();

		return listaEquipos;

	}

	public void asignarEquipo(String codequipo, String numEquipo, String codEmpleado) throws SQLException {
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(INSERT_ASIGNA_EQUIPO);
		
		pst.setString(1, codequipo);
		pst.setString(2, numEquipo);
		pst.setString(3, codEmpleado);

		pst.executeUpdate();

		pst.close();
		con.close();
		
	}
	
	public List<Enfermero> listarEnfermero() throws SQLException{
		List<Enfermero> enfermeros = new ArrayList<Enfermero>();
		Connection con = new Conexion().getConnectionJDBC();
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(ENFERMERO_LISTALL);

		while (rs.next()) {
			Enfermero e = new Enfermero(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getTime(5), rs.getTime(6),
					rs.getDate(7), rs.getDate(8), rs.getString(9));
			enfermeros.add(e);
			
		}

		rs.close();
		st.close();
		con.close();
		return enfermeros;
	}
	
	public List<Enfermero> filtrarNombreEnfermero(String cadena) throws SQLException{
		List<Enfermero> enfermeros = new ArrayList<Enfermero>();
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(FILTRAR_EMPLEADO_NOMBRE);
		pst.setString(1, cadena);
		ResultSet rs = pst.executeQuery();
		
		while (rs.next()) {
			Enfermero e = new Enfermero(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getTime(5), rs.getTime(6),
					rs.getDate(7), rs.getDate(8), rs.getString(9));
			enfermeros.add(e);
			
		}

		rs.close();
		pst.close();
		con.close();
		return enfermeros;
	}
	
	
	public List<Enfermero> buscarNombreEnfermero(String cadena) throws SQLException{
		List<Enfermero> enfermeros = new ArrayList<Enfermero>();
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(FIND_ENFE_BY_NAME);
		pst.setString(1, cadena);
		ResultSet rs = pst.executeQuery();
		
		while (rs.next()) {
			enfermeros.add(new Enfermero(rs.getString("codenfermero"), rs.getString("nombre"), rs.getString("apellido"),
					rs.getString("pass"), rs.getTime("hinicio"), rs.getTime("hfin"), rs.getDate("dinicio"),
					rs.getDate("dfin"), rs.getString("djornada")));
			
			
		}

		rs.close();
		pst.close();
		con.close();
		return enfermeros;
	}
	public List<Enfermero> buscarApellidoEnfermero(String cadena) throws SQLException{
		List<Enfermero> enfermeros = new ArrayList<Enfermero>();
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(FIND_ENFE_BY_SURNAME);
		pst.setString(1, cadena);
		ResultSet rs = pst.executeQuery();
		
		while (rs.next()) {
			enfermeros.add(new Enfermero(rs.getString("codenfermero"), rs.getString("nombre"), rs.getString("apellido"),
					rs.getString("pass"), rs.getTime("hinicio"), rs.getTime("hfin"), rs.getDate("dinicio"),
					rs.getDate("dfin"), rs.getString("djornada")));
			
			
		}

		rs.close();
		pst.close();
		con.close();
		return enfermeros;
	}
	
	
	
	
	
	
	public List<Enfermero> filtrarApellidoEnfermero(String cadena) throws SQLException{
		List<Enfermero> enfermeros = new ArrayList<Enfermero>();
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(FILTRAR_EMPLEADO_APELLIDO);
		pst.setString(1, cadena);
		ResultSet rs = pst.executeQuery();
		
		while (rs.next()) {
			Enfermero e = new Enfermero(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getTime(5), rs.getTime(6),
					rs.getDate(7), rs.getDate(8), rs.getString(9));
			enfermeros.add(e);
			
		}

		rs.close();
		pst.close();
		con.close();
		return enfermeros;
	}
	
	public List<Equipo> buscarEquipo(String buscando) throws SQLException {
		List<Equipo> equipos = new ArrayList<Equipo>();
		Connection con = new Conexion().getConnectionJDBC();
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(LISTAR_EQUIPOS);

		while (rs.next()) {
			equipos.add(new Equipo(rs.getString("numequipo")));

		}

//CERRAR EN ESTE ORDEN
		rs.close();
		st.close();
		con.close();
		return equipos;
	}
	
	public List<AsignaEquipo> buscarAsignaEquipo(String numequipo) throws SQLException {
		List<AsignaEquipo> asignaequipo = new ArrayList<AsignaEquipo>();
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst=con.prepareStatement(GET_ASIGNAEQUIPO_NAME);
		pst.setString(1, numequipo);
		ResultSet rs = pst.executeQuery();
		
		
	while(rs.next()) {
		asignaequipo.add(new AsignaEquipo(rs.getString("codequipo"),rs.getString("numequipo"),rs.getString("codempleado")));
	}
	
	//CERRAR EN ESTE ORDEN
	rs.close();
	pst.close();
	con.close();
	return asignaequipo;
	}
	
	public List<AsignaEquipo> buscarAsignaEquipoPorCodMed(String codmed) throws SQLException {
		List<AsignaEquipo> asignaequipo = new ArrayList<AsignaEquipo>();
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst=con.prepareStatement(GET_ASIGNAEQUIPO_COD_MED);
		pst.setString(1, codmed);
		ResultSet rs = pst.executeQuery();
		
		
	while(rs.next()) {
		asignaequipo.add(new AsignaEquipo(rs.getString("codequipo"),rs.getString("numequipo"),rs.getString("codempleado")));
	}
	
	//CERRAR EN ESTE ORDEN
	rs.close();
	pst.close();
	con.close();
	return asignaequipo;
	}
	
	public void crearCitaEquipo(Cita cita) throws SQLException {
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(ADD_CITA_EQUIPO);

		String codCita = cita.getCodCita();
		String numequipo = cita.getNumequipo();
		String codPac = cita.getCodPaciente();
		Time hInicio = cita.gethInicio();
		Time hFin = cita.gethFin();
		Date date = (Date) cita.getDate();
		String ubicacion = cita.getUbicacion();
		boolean urgente = cita.isUrgente();

		pst.setString(1, codCita);
		pst.setString(2, codPac);
		pst.setTime(3, hInicio);
		pst.setTime(4, hFin);
		pst.setDate(5, date);
		pst.setString(6, ubicacion);
		pst.setBoolean(7, urgente);
		pst.setString(8, numequipo);

		pst.executeUpdate();

		pst.close();
		con.close();
	}
	
	public Equipo devolverEquipo(String numequipo) throws SQLException {
		Equipo equipo = null;
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(GET_EQUIPO);
		pst.setString(1, numequipo);
		ResultSet rs = pst.executeQuery();

		while (rs.next()) {

			equipo = new Equipo(rs.getString("numequipo"));

		}

//CERRAR EN ESTE ORDEN
		rs.close();
		pst.close();
		con.close();
		return equipo;
	}
	
	public List<Cita> devolverCitasConEquipo() throws SQLException {
		List<Cita> citas = new ArrayList<Cita>();
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(GET_CITA);
		boolean res = false;
		java.util.Date fecha = new java.util.Date();
		java.sql.Date date = new java.sql.Date(fecha.getTime());
		pst.setDate(1, date);
		ResultSet rs = pst.executeQuery();

		while (rs.next()) {
			if (rs.getByte("urgencia") == 1)
				res = true;
			else
				res = false;

			citas.add(new Cita(rs.getString("codcita"), rs.getString("codpaciente"),
					rs.getTime("hinicio"), rs.getTime("hfin"), rs.getDate("fecha"), rs.getString("ubicacion"), res, rs.getString("numequipo")));

		}

//CERRAR EN ESTE ORDEN
		rs.close();
		pst.close();
		con.close();
		return citas;
	}
	
	public List<Cita> devolvercitasEquipoPorFecha(Date sDate) throws SQLException {
		List<Cita> citas = new ArrayList<Cita>();
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(GET_CITA_EQUIPOD);
		boolean res = false;
		pst.setDate(1, sDate);

		ResultSet rs = pst.executeQuery();

		while (rs.next()) {
			if (rs.getByte("urgencia") == 1)
				res = true;

			citas.add(new Cita(rs.getString("codcita"), rs.getString("codpaciente"),
			rs.getTime("hinicio"), rs.getTime("hfin"), rs.getDate("fecha"), rs.getString("ubicacion"), res, rs.getString("numequipo")));

		}

//CERRAR EN ESTE ORDEN
		rs.close();
		pst.close();
		con.close();
		return citas;
	}
	
	
	public List<Cita> devolverCitasEquipoNumEquip(String numequipo) throws SQLException {
		List<Cita> citas = new ArrayList<Cita>();
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement st = con.prepareStatement(GET_CITA_NUMEQUIP);
		boolean res = false;
		st.setString(1, numequipo);

		ResultSet rs = st.executeQuery();

		while (rs.next()) {
			if (rs.getByte("urgencia") == 1)
				res = true;

			citas.add(new Cita(rs.getString("codcita"), rs.getString("codpaciente"),
					rs.getTime("hinicio"), rs.getTime("hfin"), rs.getDate("fecha"), rs.getString("ubicacion"), res, rs.getString("numequipo")));

		}

//CERRAR EN ESTE ORDEN
		rs.close();
		st.close();
		con.close();
		return citas;
	}

	public List<Cita> devolverCitasConEquipoHist(String codhistorial) throws SQLException {
		List<Cita> citas = new ArrayList<Cita>();
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(GET_CITA_HISTORIAL);
		boolean res = false;
		pst.setString(1, codhistorial);

		ResultSet rs = pst.executeQuery();

		while (rs.next()) {
			if (rs.getByte("urgencia") == 0)
				res = true;
			
			citas.add(new Cita(rs.getString("codcita"), rs.getString("codpaciente"),
			rs.getTime("hinicio"), rs.getTime("hfin"), rs.getDate("fecha"), rs.getString("ubicacion"), res, rs.getString("numequipo")));

		}

//CERRAR EN ESTE ORDEN
		rs.close();
		pst.close();
		con.close();
		return citas;
	}

	public int listarDiagnosticosAsignadosPorFecha(String nombre, Date sDateIn, Date sDateFin) throws SQLException {
		int cantidad = 0;

		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(GET_DIAGNOSTICOS_DATE);
		pst.setString(1, nombre);
		pst.setDate(2, sDateIn);
		pst.setDate(3, sDateFin);
		ResultSet rs = pst.executeQuery(); // Creo el resultSet
		
		rs.next();
		cantidad = rs.getInt("numero");

		// CERRAR EN ESTE ORDEN
		rs.close();
		pst.close();
		con.close();
		return cantidad;
	}

	public int listarDiagnosticosAsignados(String nombre) throws SQLException {
		int cantidad = 0;

		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(VER_NUMERO_DIAGNOSTICOS_ASIGNADOS);
		pst.setString(1, nombre);
		ResultSet rs = pst.executeQuery(); // Creo el resultSet
		
		rs.next();
		cantidad = rs.getInt("numero");

		// CERRAR EN ESTE ORDEN
		rs.close();
		pst.close();
		con.close();
		return cantidad;
	}

	public List<Cita> devolverCitasConEquipoHistFecha(String codhistorial, Date date) throws SQLException {
		List<Cita> citas = new ArrayList<Cita>();
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(GET_CITA_HISTORIAL_FECHA);
		boolean res = false;
		pst.setString(1, codhistorial);
		pst.setDate(2, date);

		ResultSet rs = pst.executeQuery();

		while (rs.next()) {
			if (rs.getByte("urgencia") == 0)
				res = true;
			
			citas.add(new Cita(rs.getString("codcita"), rs.getString("codpaciente"),
			rs.getTime("hinicio"), rs.getTime("hfin"), rs.getDate("fecha"), rs.getString("ubicacion"), res, rs.getString("numequipo")));

		}

//CERRAR EN ESTE ORDEN
		rs.close();
		pst.close();
		con.close();
		return citas;
	}
	
	
	/**
	 * MÃ©todo para guardar un nuevo diagnostico
	 * 
	 * @param p
	 * @throws SQLException
	 */
	public void nuevoDiagnostico(Diagnostico d) throws SQLException {
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(ADD_DIAGNOSTICO);

		String coddiagnostico = d.getNumeroDiagnostico();
		String nombrediagnostico = d.getNombre();

		pst.setString(1, coddiagnostico);
		pst.setString(2, nombrediagnostico);

		pst.executeUpdate();
		pst.close();
		con.close();
	}
	
	
	/**
	 * MÃ©todo para saber si se han cargado los diagnÃ³sticos del cie10 o no
	 * @return
	 * @throws SQLException 
	 */
	public boolean diagnosticosCargados() throws SQLException {
//		Connection con = new Conexion().getConnectionJDBC();
//		PreparedStatement pst = con.prepareStatement(FIND_DIAGNOSTICO);
//		
//		String codigoDiagnostico = "A000"; // Busco por ejemplo el primer diagnÃ³stico, que si estÃ¡ cargado ya me lo dice
//		pst.setString(1, codigoDiagnostico);
//		ResultSet rs = pst.executeQuery();
//
//		boolean res = rs.next();
//		
//		rs.close();
//		pst.close();
//		con.close();
//		return res;
		
		
		List<Diagnostico> diagnosticos = listarDiagnosticos();
		if (diagnosticos.size() != 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	
	public void addEmpleado(Empleado empleado, String tipo) throws SQLException {
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(ADD_EMPLEADO);
		
		pst.setString(1, empleado.getCodeEmpleado());
		pst.setString(2, empleado.getNombre());
		pst.setString(3, empleado.getApellido());
		pst.setString(4, empleado.getPass());
		pst.setTime(5, empleado.gethInicio());
		pst.setTime(6, empleado.gethFin());
		pst.setDate(7, empleado.getdInicio());
		pst.setDate(8, empleado.getdFin());
		pst.setString(9, empleado.getdJornada());
		
		pst.executeUpdate();
		
		pst.close();
		
		PreparedStatement pst2 = null;
		
		if(tipo.equals("Médico")) {
			pst2 = con.prepareStatement(ADD_MEDICO);
			pst2.setString(1, empleado.getCodeEmpleado());
		}
		else if(tipo.equals("Enfermero")) {
			pst2 = con.prepareStatement(ADD_ENFERMERO);
			pst2.setString(1, empleado.getCodeEmpleado());
		}
		
		pst2.executeUpdate();
		
		pst2.close();
		
		con.close();
	}
	
	
	/**
	 * MÃ©todo para cargar los procedimientos de la base de datos
	 * @return
	 * @throws SQLException 
	 */
	public List<Procedimiento> cargarProcedimientos() throws SQLException{
		List<Procedimiento> procedimientos = new ArrayList<Procedimiento>(); // Creo la lista que voy a devolver

		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(LIST_PROCEDIMIENTOS);

		ResultSet rs = pst.executeQuery(); // Creo el resultSet

		while (rs.next()) {

			procedimientos.add(new Procedimiento(rs.getString("codProcedimiento"), rs.getString("nombreProcedimiento")));
		}

		rs.close();
		pst.close();
		con.close();

		return procedimientos;
	}
	
	
	
	/**
	 * MÃ©todo que me crea una nueva asignaPreinscripcion y me lo guarda
	 * @param ap
	 * @throws SQLException 
	 */
	public void nuevoAsignaProcedimiento(AsignaProcedimiento ap) throws SQLException {
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(ADD_ASIGNA_PROCEDIMIENTO);

		String codAsigProcedimiento = ap.getCodAsigProcedimiento();
		String nombreProcedimiento = ap.getNombreProcedimiento();
		String codProcedimiento = ap.getCodigoProcedimiento();
		String nHistorial = ap.getnHistorial();
		String codempleado = ap.getCodEmpleado();
		java.sql.Date fecha = new java.sql.Date(ap.getFecha().getTime());
		Time hora = new Time(fecha.getTime());

		pst.setString(1, codAsigProcedimiento);
		pst.setString(2, nombreProcedimiento);
		pst.setString(3, codProcedimiento);
		pst.setString(4, nHistorial);
		pst.setString(5, codempleado);
		pst.setDate(6, fecha);
		pst.setTime(7, hora);

		pst.executeUpdate();

		pst.close();
		con.close();
	}
	
	
	
	/**
	 * MÃ©todo para buscar todos los procedimientos que tiene asignado un numero de historial que se le pasa por parÃ¡metro
	 * 
	 * @param historial
	 * @return
	 * @throws SQLException
	 */
	//public List<AsignaProcedimiento> mostrarProcedimientosAsignados(String historial) throws SQLException{
	public List<String> mostrarProcedimientosAsignados(String historial) throws SQLException{

//		List<AsignaProcedimiento> aProcedimientos = new ArrayList<AsignaProcedimiento>();
//		
//		Connection con = new Conexion().getConnectionJDBC();
//		PreparedStatement pst = con.prepareStatement(VER_ASIGNA_PROCEDIMIENTOS);
//
//		pst.setString(1, historial); 
//
//		ResultSet rs = pst.executeQuery(); 
//
//		while (rs.next()) {
//			aProcedimientos.add("Nombre: " + rs.getString("nombreprocedimiento") + 
//					rs.getString("codprocedimiento"),rs.getString("nhistorial"),
//				rs.getString("codempleado"), rs.getDate("fecha"),rs.getTime("hora")));			
//		}
//
//		// CERRAR EN ESTE ORDEN
//		rs.close();
//		pst.close();
//		con.close();
//		return aProcedimientos;
		
		
		List<String> nombreProcedimientos = new ArrayList<String>();

		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst1 = con.prepareStatement(GET_MEDICO_NOMBRE);
		PreparedStatement pst = con.prepareStatement(VER_ASIGNA_PROCEDIMIENTOS);

		pst.setString(1, historial); // busco por n de historial

		
		ResultSet rs = pst.executeQuery(); // Creo el resultSet

		while (rs.next()) {
			pst1.setString(1, rs.getString("codempleado"));
			ResultSet rs1 = pst1.executeQuery();
			while (rs1.next()) {
				
				nombreProcedimientos.add(rs.getString(2) + "\t" + "\t" + "Fecha: " + rs.getString(6) + "\t" + "Hora: " + rs.getString(7)
										+ "\t" + "\t"+ "Empleado: " + rs1.getString("nombre"));
			}
			
			rs1.close();
		}

		// CERRAR EN ESTE ORDEN
		rs.close();
		pst.close();
		pst1.close();
		con.close();
		
		return nombreProcedimientos;
	}
	
	
	
	
	/**
	 * MÃ©todo para guardar un nuevo procedimiento
	 * 
	 * @param p
	 * @throws SQLException
	 */
	public void nuevoProcedimiento(Procedimiento p) throws SQLException {
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(ADD_PROCEDIMIENTO);

		String codprocedimiento = p.getNumero();
		String nombreprocedimiento = p.getNombre();

		pst.setString(1, codprocedimiento);
		pst.setString(2, nombreprocedimiento);

		pst.executeUpdate();
		pst.close();
		con.close();
	}

	
	
	/**
	 * MÃ©todo para saber si los procedimientos del cie10 estÃ¡n cargados en la base de datos
	 * @return
	 * @throws SQLException 
	 */
	public boolean procedimientosCargados() throws SQLException {
		List<Procedimiento> procedimientos = cargarProcedimientos();
		if (procedimientos.size() != 0) {
			return true;
		}
		else {
			return false;
		}
	}

	
	/**
	 * MÃ©todo para cargar los antecedentes de la base de datos
	 * @return
	 * @throws SQLException 
	 */
	public List<Antecedente> cargarAntecedentes() throws SQLException {
		List<Antecedente> antecedentes = new ArrayList<Antecedente>(); // Creo la lista que voy a devolver

		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(LIST_ANTECEDENTES);

		ResultSet rs = pst.executeQuery(); // Creo el resultSet

		while (rs.next()) {
			antecedentes.add(new Antecedente(rs.getString("codAntecedente"), rs.getString("nombreAntecedente")));
		}

		rs.close();
		pst.close();
		con.close();

		return antecedentes;
	}

	
	
	/**
	 * MÃ©todo para actualizar los antecedentes
	 * @param antecedentes
	 * @throws SQLException 
	 */
	public void actualizarAntecedente(String antecedentes) throws SQLException {
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(INSERT_ANTECEDENTES);
		
		Random r = new Random();
		String codAntecedente = "" + r.nextInt(99999);
		pst.setString(1, codAntecedente);
		pst.setString(2, antecedentes);

		pst.executeUpdate();

		pst.close();
		con.close();		
	}

	
	/**
	 * Me busca el nombre de todos los antecedentes
	 * @return
	 * @throws SQLException 
	 */
	public List<String> buscarNombreTodosAntecedentes() throws SQLException {
		List<String> nombresAntecedentes = new ArrayList<String>();
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement st = con.prepareStatement(VER_NOMBRE_ANTECEDENTE);
		ResultSet rs = st.executeQuery();

		while (rs.next()) {
			nombresAntecedentes.add(rs.getString(1));
		}

		// CERRAR EN ESTE ORDEN
		rs.close();
		st.close();
		con.close();
		return nombresAntecedentes;
	}

	
	/**
	 * MÃ©todo que asigna un nuevo antecedente
	 * @param aa
	 * @throws SQLException 
	 */
	public void nuevaAsignaAntecedente(AsignaAntecedente aa) throws SQLException {
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(ADD_ASIGNA_ANTECEDENTE);

		String codAsigAntecedente = aa.getCodAsigAntecedente();
		String nombreAntecedente = aa.getNombreAntecedente();
		String codAntecedente = aa.getCodAntecedente();
		String historial = aa.getnHistorial();
		String codempleado = aa.getCodEmpleado();
		java.sql.Date fecha = new java.sql.Date(aa.getFecha().getTime());
		Time hora = new Time(fecha.getTime());

		pst.setString(1, codAsigAntecedente);
		pst.setString(2, nombreAntecedente);
		pst.setString(3, codAntecedente);
		pst.setString(4, historial);
		pst.setString(5, codempleado);
		pst.setDate(6, fecha);
		pst.setTime(7, hora);

		pst.executeUpdate();

		pst.close();
		con.close();
		
	}

	
	/**
	 * MÃ©todo que me devuelve todos los nombres de los antecedentes asignados a un paciente que se pasa por parÃ¡metro
	 * @param historial
	 * @return
	 * @throws SQLException 
	 */
	public List<String> buscarAntecedentesAsignados(String historial) throws SQLException {
		List<String> nombreAntecedentes = new ArrayList<String>();

		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst1 = con.prepareStatement(GET_MEDICO_NOMBRE);
		PreparedStatement pst = con.prepareStatement(VER_ANTECEDENTES_ASIGNADOS);

		pst.setString(1, historial); // busco por n de historial

		ResultSet rs = pst.executeQuery(); // Creo el resultSet

		while (rs.next()) {
			pst1.setString(1, rs.getString("codempleado"));
			ResultSet rs1 = pst1.executeQuery();
			while (rs1.next()) {
				nombreAntecedentes.add(rs.getString(2) + "\t" +"Fecha: " + rs.getDate(6) + "\t" +"Hora: " + rs.getTime(7)
					+ "\t" + "\t" + "Empleado: " + rs1.getString("nombre"));
			}
			
			rs1.close();
		}

		// CERRAR EN ESTE ORDEN
		
		rs.close();
		pst.close();
		pst1.close();
		con.close();
		return nombreAntecedentes;
		
		
	}

	public void nuevoAntecedenteB(Antecedente antecedente) throws SQLException {
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(ADD_ANTECEDENTE);

		String codAntecedente = antecedente.getCodAntecedente();
		String nombreAntecedente = antecedente.getNombreAntecedente();

		pst.setString(1, codAntecedente);
		pst.setString(2, nombreAntecedente);

		pst.executeUpdate();
		pst.close();
		con.close();
	}

	
	/**
	 * MÃ©todo para saber si un paciente tiene asignado algÃºn antecedente
	 * @param codPaciente
	 * @return
	 * @throws SQLException 
	 */
	public boolean tieneAntecedentes(String historial) throws SQLException {
		List<String> antecedentes = buscarAntecedentesAsignados(historial);
		if (antecedentes.size() != 0) {
			return true;
		}
		else {
			return false;
		}
	}

	
	/**
	 * MÃ©todo que me lista los antecedentes asignados a un paciente que se le pasa por parÃ¡metro
	 * @param paciente
	 * @return
	 * @throws SQLException 
	 */
	public List<AsignaAntecedente> listarAntecedentesAsignados(String nhistorial) throws SQLException {
		List<AsignaAntecedente> antecedentes = new ArrayList<AsignaAntecedente>();

		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(VER_ASIGNA_ANTECEDENTES);

		pst.setString(1, nhistorial); 

		ResultSet rs = pst.executeQuery(); 

		while (rs.next()) {
			antecedentes.add(new AsignaAntecedente(rs.getString("codAsigAntecedente"), rs.getString("nombreAntecedente"), rs.getString("codAntecedente"), 
					rs.getString("nHistorial"), rs.getString("codEmpleado"), rs.getDate("fecha"), rs.getTime("hora")));
			
		}

		// CERRAR EN ESTE ORDEN
		rs.close();
		pst.close();
		con.close();
		return antecedentes;
	}
	
	
	
	
	public List<AsignaProcedimiento> listarProcedimientosAsignados(String historial) throws SQLException{

		List<AsignaProcedimiento> aProcedimientos = new ArrayList<AsignaProcedimiento>();
		
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(VER_ASIGNA_PROCEDIMIENTOS);

		pst.setString(1, historial); 

		ResultSet rs = pst.executeQuery(); 

		while (rs.next()) {
			aProcedimientos.add(new AsignaProcedimiento(rs.getString("codasigprocedimiento"), rs.getString("nombreProcedimiento"), rs.getString("codProcedimiento"), rs.getString("nhistorial"), rs.getString("codempleado"), rs.getDate("fecha"), rs.getTime("hora")));	
		}

		// CERRAR EN ESTE ORDEN
		rs.close();
		pst.close();
		con.close();
		return aProcedimientos;
	}

	
	
	/**
	 * MÃ©todo para listar todas las preinscripciones asignadas
	 * @param historial
	 * @return
	 * @throws SQLException 
	 */
	public List<AsignaPreinscripcion> listarPrescripcionesAsignadas(String historial) throws SQLException {
		List<AsignaPreinscripcion> aPrescripciones = new ArrayList<AsignaPreinscripcion>();
		
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(VER_ASIGNA_PREINSCRIPCION);

		pst.setString(1, historial); 

		ResultSet rs = pst.executeQuery(); 

		while (rs.next()) {
			aPrescripciones.add(new AsignaPreinscripcion(rs.getString("codasigprescripcion"), rs.getString("nombrePrescripcion"), rs.getString("nHistorial"), rs.getString("codempleado"), rs.getInt("cantidad"), rs.getInt("intervalo"), rs.getInt("duracion"), rs.getString("instrucciones"), rs.getDate("fecha"), rs.getTime("hora")));	
		}

		// CERRAR EN ESTE ORDEN
		rs.close();
		pst.close();
		con.close();
		return aPrescripciones;
	}
	
	
	
	/**
	 * MÃ©todo para listar todas las preinscripciones asignadas
	 * @param historial
	 * @return
	 * @throws SQLException 
	 */
	public List<AsignaEnfermPrev> listarenfermedadesPAsignadas(String historial) throws SQLException {
		List<AsignaEnfermPrev> enferemedades = new ArrayList<AsignaEnfermPrev>();
		
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(VER_ASIGNA_EPREVIA);

		pst.setString(1, historial); 

		ResultSet rs = pst.executeQuery(); 

		while (rs.next()) {
			enferemedades.add(new AsignaEnfermPrev(rs.getString("codasigenf"), rs.getString("nombreEnfermedad"), rs.getString("historial"), rs.getString("codempleado") ,rs.getDate("fecha"), rs.getTime("hora")));	
		}

		// CERRAR EN ESTE ORDEN
		rs.close();
		pst.close();
		con.close();
		return enferemedades;
	}
	
	public List<AsignaDiagnostico> asignaDiagnostico() throws SQLException {
		List<AsignaDiagnostico> diagnsoticos = new ArrayList<AsignaDiagnostico>();

		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(VER_DIAGNOSTICOS);
		
		ResultSet rs = pst.executeQuery(); 

		while (rs.next()) {
			diagnsoticos.add(new AsignaDiagnostico(rs.getString("nombrediagnostico"), rs.getInt("contador")));
					
			
		}

		// CERRAR EN ESTE ORDEN
		rs.close();
		pst.close();
		con.close();
		return diagnsoticos;
	}
	
	public List<AsignaDiagnostico> buscarCodDiagnosticoPorFechas(Date sDateIn, Date sDateFin) throws SQLException {
		List<AsignaDiagnostico> diagnosticos = new ArrayList<AsignaDiagnostico>();

		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(VER_CODDIAGNOSTICOS_FECHA);
		pst.setDate(1, sDateIn);
		pst.setDate(2, sDateFin);
		ResultSet rs = pst.executeQuery(); 

		while (rs.next()) {
			diagnosticos.add(new AsignaDiagnostico(rs.getString("nombrediagnostico"), rs.getInt("contador")));
		}

		// CERRAR EN ESTE ORDEN
		rs.close();
		pst.close();
		con.close();
		return diagnosticos;
	}
	
	public String buscarCodDiagnostico(String nombreDiagnostico) throws SQLException {
		String diagnsoticos = "";

		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(VER_CODDIAGNOSTICOS);
		pst.setString(1, nombreDiagnostico);
		ResultSet rs = pst.executeQuery(); 

		if (rs.next()) {
			diagnsoticos = rs.getString(1);
		}

		// CERRAR EN ESTE ORDEN
		rs.close();
		pst.close();
		con.close();
		return diagnsoticos;
	}
	
	
	
	/**
	 * MÃ©todo que me devuelve el paciente buscando su nÃºemro de historial
	 * @param codigo
	 * @return
	 * @throws SQLException
	 */
	public Paciente buscarPacienteConHistorial(String codigo) throws SQLException {
		Paciente m=null;
		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(GET_PACIENTE);
		
		pst.setString(1, codigo);
		ResultSet rs = pst.executeQuery();

		if( rs.next()) {
			m= new Paciente(rs.getString("codpaciente"), rs.getString("nombre"), rs.getString("apellido"),
					rs.getInt("movil"), rs.getString("email"), rs.getString("info"), rs.getString("nhistorial"));
		}
		 

		rs.close();
		pst.close();
		con.close();
		return m;

	}
	
	
	
	/**
	 * MÃ©todo que me devuelve el nombre y apellidos del mÃ©dico que le paso el cÃ³digo por parÃ¡metro
	 * @param codigo
	 * @return
	 * @throws SQLException 
	 */
	public String buscarEmpleadoPorCodigo(String codigo) throws SQLException {
		String nombre = null;
		String apellido = null;

		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(GET_MEDICO_NOMBRE);

		pst.setString(1, codigo); // busco por codigo
		ResultSet rs = pst.executeQuery(); // Creo el resultSet

		
		if (rs.next()) {
			nombre = rs.getString("nombre");
			apellido = rs.getString("apellido");
		}
		
		
		String nombreCompleto = nombre + " " + apellido;
		

		rs.close();
		pst.close();
		con.close();
		
		return nombreCompleto;	
	}
	
	public int calcularAsignDiagFecha(Date sDateIn, Date sDateFin) throws SQLException {
		List<String> diagnosticos = new ArrayList<String>();

		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(VER_ASIGNA_DIAGNOSTICO_FECHA);
		pst.setDate(1, sDateIn);
		pst.setDate(2, sDateFin);
		ResultSet rs = pst.executeQuery(); 

		while (rs.next()) {
			diagnosticos.add(rs.getString("nombrediagnostico"));
		}

		// CERRAR EN ESTE ORDEN
		rs.close();
		pst.close();
		con.close();
		return diagnosticos.size();
	}
	
	public int calcularAsignDiag() throws SQLException {
		List<String> diagnosticos = new ArrayList<String>();

		Connection con = new Conexion().getConnectionJDBC();
		PreparedStatement pst = con.prepareStatement(VER_ASIGNA_DIAGNOSTICO);
		ResultSet rs = pst.executeQuery(); 

		while (rs.next()) {
			diagnosticos.add(rs.getString("nombrediagnostico"));
		}

		// CERRAR EN ESTE ORDEN
		rs.close();
		pst.close();
		con.close();
		return diagnosticos.size();
	}
	
	
	
	
	
}
