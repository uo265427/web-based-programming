package logica.servicios;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import logica.AsignaAntecedente;
import logica.AsignaCausa;
import logica.AsignaDiagnostico;
import logica.AsignaEnfermPrev;
import logica.AsignaPreinscripcion;
import logica.AsignaProcedimiento;
import logica.AsignaVacuna;
import logica.Paciente;
import logica.empleados.Enfermero;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

public class HistorialToPDF {

	private ParserBaseDeDatos pbd;
	private List<AsignaDiagnostico> ad;
	private List<AsignaPreinscripcion> pres;
	private List<AsignaVacuna> vacuna;
	private List<AsignaCausa> causa;
	private List<AsignaEnfermPrev> enfermedad;
	private List<AsignaProcedimiento> procedimiento;
	private List<AsignaAntecedente> antecedente;

	public HistorialToPDF() {
		super();
		this.pres = new ArrayList<AsignaPreinscripcion>();
		this.pbd = new ParserBaseDeDatos();
		this.ad = new ArrayList<AsignaDiagnostico>();
		this.vacuna = new ArrayList<AsignaVacuna>();
		this.causa = new ArrayList<AsignaCausa>();
		this.enfermedad= new ArrayList<AsignaEnfermPrev>();
		this.procedimiento= new ArrayList<AsignaProcedimiento>();
		this.antecedente = new ArrayList<AsignaAntecedente>();
		


	}

	public void createPDF(Paciente p) throws JRException, FileNotFoundException {

		try {
			pres = pbd.asignaPrescripcionesHistorial(p.getHistorial());
			ad = pbd.asignaDiagnosticoHistorial(p.getHistorial());
			vacuna = pbd.asignaVacunaHistorial(p.getHistorial());
			causa = pbd.asignaCausaHistorial(p.getHistorial());
			enfermedad= pbd.listarenfermedadesPAsignadas(p.getHistorial());
			procedimiento=pbd.listarProcedimientosAsignados(p.getHistorial());
			antecedente= pbd.listarAntecedentesAsignados(p.getHistorial());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Lista a coleccion de jasper
		JRBeanCollectionDataSource prescripciones = new JRBeanCollectionDataSource(pres);
		JRBeanCollectionDataSource causas = new JRBeanCollectionDataSource(causa);
		JRBeanCollectionDataSource diagnosticos = new JRBeanCollectionDataSource(ad);
		JRBeanCollectionDataSource vacunas = new JRBeanCollectionDataSource(vacuna);
		JRBeanCollectionDataSource enfermedades = new JRBeanCollectionDataSource(enfermedad);
		JRBeanCollectionDataSource procedimientos = new JRBeanCollectionDataSource(procedimiento);
		JRBeanCollectionDataSource antecedentes = new JRBeanCollectionDataSource(antecedente);
		

		// Map con los parametros
		final Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("Paciente", p);
		parameters.put("CollectionBeanParameter", prescripciones);
		parameters.put("Causa", causas);
		parameters.put("Vacuna", vacunas);
		parameters.put("Diagnostico", diagnosticos);
		parameters.put("Antecedente", antecedentes);
		parameters.put("Procedimiento", procedimientos);
		parameters.put("Enfermedad", enfermedades);

		// leemos el jrcml
		InputStream input = new FileInputStream(new File("historial2.jrxml"));
		JasperDesign jasperDesign = JRXmlLoader.load(input);

		// COMPILASMOS EL JASPER XRML
		JasperReport jr = JasperCompileManager.compileReport(jasperDesign);

		// Sacamso el objeto a pdf
		JasperPrint jp = JasperFillManager.fillReport(jr, parameters, new JREmptyDataSource());
		JasperExportManager.exportReportToPdfStream(jp, new FileOutputStream("historial/"+p.getHistorial() + "Historial.pdf"));
		// FileOutputStream output= new FileOutputStream(new
		// File(p.getHistorial()+"receta.pdf"));

	}
}
