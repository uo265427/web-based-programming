package logica.servicios;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import logica.AsignaPreinscripcion;
import logica.Paciente;
import logica.empleados.Medico;
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

public class PrescripcionesToPDF {
	
	Medico medico = null;
	List<AsignaPreinscripcion> pres=new ArrayList<AsignaPreinscripcion>();
	
	ParserBaseDeDatos pbd=new ParserBaseDeDatos();
	public void createPDF(Paciente p) throws JRException, FileNotFoundException {
		
	try {
		pres=pbd.asignaPrescricpionesFechaHistorial(p.getHistorial());
		if (pres.size() !=0) {
			medico = pbd.buscarMedicoCodigo(pres.get(0).getCodempleado());
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	InputStream stream = PrescripcionesToPDF.class.getResourceAsStream("../resource/prescripciones.jrxml");
	
	//Lista a coleccion de jasper
	JRBeanCollectionDataSource prescripciones= new JRBeanCollectionDataSource(pres);
	
	//Map con los parametros
	final Map<String,Object> parameters = new HashMap<String,Object>();
	parameters.put("Paciente", p);
	parameters.put("CollectionBeanParameter", prescripciones);
	parameters.put("Medico", medico);
	//leemos el jrcml 

	InputStream input =new FileInputStream(new File("prescripciones.jrxml"));
	JasperDesign jasperDesign =JRXmlLoader.load(input);
	
	//COMPILASMOS EL JASPER XRML
	JasperReport jr = JasperCompileManager.compileReport(jasperDesign);
	
	
	//Sacamso el objeto a pdf
	JasperPrint jp =JasperFillManager.fillReport(jr, parameters,new JREmptyDataSource());
	JasperExportManager.exportReportToPdfStream(jp, new FileOutputStream("Recetas/"+p.getHistorial()+"receta.pdf"));

	//FileOutputStream output= new FileOutputStream(new File(p.getHistorial()+"receta.pdf"));
	
	
	}
	
	
	
}
