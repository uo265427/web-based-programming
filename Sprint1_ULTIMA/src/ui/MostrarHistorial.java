package ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import logica.Antecedente;
import logica.AsignaAntecedente;
import logica.AsignaDiagnostico;
import logica.AsignaEnfermPrev;
import logica.AsignaPreinscripcion;
import logica.AsignaProcedimiento;
import logica.AsignaVacuna;
import logica.Cita;
import logica.HistorialMedico;
import logica.Paciente;
import logica.Preinscripcion;
import logica.asignar.AsignaEPrev;
import logica.servicios.HistorialToPDF;
import logica.servicios.ParserBaseDeDatos;
import logica.servicios.Printer;
import net.sf.jasperreports.engine.JRException;
import ui.medico.AnadirAntecedente;
import ui.medico.ModeloNoEditable;

import java.awt.CardLayout;
import java.awt.Color;

import javax.swing.JTabbedPane;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.GridLayout;
import java.awt.Toolkit;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import java.awt.FlowLayout;

public class MostrarHistorial extends JDialog {

	
	private ModeloNoEditable modeloTablaAntecedentes;
	private ModeloNoEditable modeloTablaProcedimientos;
	private ModeloNoEditable modeloTablaDiagnosticos;
	private ModeloNoEditable modeloTablaPrescripciones;
	private ModeloNoEditable modeloTablaVacunas;
	private ModeloNoEditable modeloTablaEP;


	
	
	private JPanel contentPane;
	private JPanel panelBotones;
	private JTabbedPane panelPestañas;
	private JPanel panelCausas;
	private JPanel panelEnfermedPrevia;
	private JPanel panelVacunas;
	private JPanel panelPreinscripciones;
	
	private DefaultListModel<Preinscripcion> modeloListaM;
	
	List<AsignaAntecedente> antecedentesAsignados;
	List<AsignaProcedimiento> procedimientosAsignados;
	List<AsignaDiagnostico> diagnosticosAsignados;
	List<AsignaPreinscripcion> prescripcionesAsignados;
	List<AsignaVacuna> vacunasAsignados;
	List<AsignaEnfermPrev> enfermedadesPAsignadas;


	private HistorialMedico hm;
	private ParserBaseDeDatos pbd = new ParserBaseDeDatos();
	private String codempleado;
	
	
	private JScrollPane scrollPaneCausas;
	private JTextArea textAreaCausas;
	private JScrollPane scrollPaneEnfermPrevia;
	private JScrollPane scrollPaneVacunas;
	private JScrollPane scrollPanePreinscripciones;
	private JPanel panelDiagnosticos;
	private JScrollPane scrollPaneDiagnosticos;
	private JButton btnNewButton;
	private JButton btnNewButton_1;
	private JPanel panelProcedimientos;
	private JScrollPane scrollPaneProcedimientos;
	private JPanel panelAntecedentes;
	private JScrollPane scrollPaneAntecedentes;
	private JTextArea textAreaAntecedentes;
	private JTable tableAntecedentes;
	private JTable tableProcedimientos;
	private JTable tableDiagnosticos;
	private JTable tablePrescripciones;
	private JTable tableVacunas;
	private JTable tableEP;
	private JPanel pnNuevoAntecedente;
	private JButton btnNuevoAntecedente;

	/**
	 * Create the frame.
	 * @throws SQLException 
	 */
	public MostrarHistorial(HistorialMedico hm, String codempleado) throws SQLException {
		setIconImage(Toolkit.getDefaultToolkit().getImage(AnadirAntecedentesHistorial.class.getResource("/img/logop.jpg")));
		setTitle("Historial m\u00E9dico");
		this.hm = hm;
		this.codempleado = codempleado;
		
		
		this.antecedentesAsignados = pbd.listarAntecedentesAsignados(hm.getHistorial());
		this.procedimientosAsignados = pbd.listarProcedimientosAsignados(hm.getHistorial());
		this.diagnosticosAsignados = pbd.asignaDiagnosticoHistorial(hm.getHistorial());
		this.prescripcionesAsignados = pbd.listarPrescripcionesAsignadas(hm.getHistorial());
		this.vacunasAsignados = pbd.asignaVacunaHistorial(hm.getHistorial());
		this.enfermedadesPAsignadas = pbd.listarenfermedadesPAsignadas(hm.getHistorial());
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 732, 385);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.add(getPanelPestañas(), BorderLayout.CENTER);
		contentPane.add(getPanelBotones(), BorderLayout.SOUTH);
	}

	private JPanel getPanelBotones() {
		if (panelBotones == null) {
			panelBotones = new JPanel();
			panelBotones.add(getBtnNewButton());
			panelBotones.add(getBtnNewButton_1());
		}
		return panelBotones;
	}
	private JTabbedPane getPanelPestañas() throws SQLException {
		if (panelPestañas == null) {
			panelPestañas = new JTabbedPane(JTabbedPane.TOP);
			
			panelPestañas.addTab("Diagnosticos", null, getPanelDiagnosticos(), null);
			panelPestañas.addTab("Antecedentes", null, getPanelAntecedentes(), null);
			panelPestañas.addTab("Causas", null, getPanelCausas(), null);
			panelPestañas.addTab("Enferm previas", null, getPanelEnfermedPrevia(), null);
			panelPestañas.addTab("Preinscripciones", null, getPanelPreinscripciones(), null);
			panelPestañas.addTab("Procedimientos", null, getPanelProcedimientos(), null);
			panelPestañas.addTab("Vacunas", null, getPanelVacunas(), null);

			
			
			
		}
		return panelPestañas;
	}
	private JPanel getPanelCausas() throws SQLException{
		if (panelCausas == null) {
			panelCausas = new JPanel();
			panelCausas.setLayout(new GridLayout(0, 1, 0, 0));
			panelCausas.add(getScrollPaneCausas());
		}
		return panelCausas;
	}
	private JPanel getPanelEnfermedPrevia() throws SQLException{
		if (panelEnfermedPrevia == null) {
			panelEnfermedPrevia = new JPanel();
			panelEnfermedPrevia.setLayout(new BorderLayout(0, 0));
			panelEnfermedPrevia.add(getScrollPaneEnfermPrevia());
			//panelEnfermedPrevia.add(getTableEP(), BorderLayout.NORTH);
		}
		return panelEnfermedPrevia;
	}
	private JPanel getPanelVacunas() throws SQLException {
		if (panelVacunas == null) {
			panelVacunas = new JPanel();
			panelVacunas.setLayout(new BorderLayout(0, 0));
			panelVacunas.add(getScrollPaneVacunas());
			//panelVacunas.add(getTableVacunas(), BorderLayout.NORTH);
		}
		return panelVacunas;
	}
	
	
	private JScrollPane getScrollPaneCausas() throws SQLException{
		if (scrollPaneCausas == null) {
			scrollPaneCausas = new JScrollPane();
			scrollPaneCausas.setViewportView(getTextAreaCausas());
		}
		return scrollPaneCausas;
	}
	
	
	public String darCausas() throws SQLException {
		String causa = "";
		List<String> causas = new ArrayList<>();
		causas = pbd.buscarCausas(hm.getHistorial());
		for(String c: causas) {
			causa += c + "\n";
		}
		return causa;
	}
	
	private JTextArea getTextAreaCausas() throws SQLException{
		if (textAreaCausas == null) {
			textAreaCausas = new JTextArea();
			textAreaCausas.setEditable(false);
			textAreaCausas.setText(darCausas());
		}
		return textAreaCausas;
	}
	private JScrollPane getScrollPaneEnfermPrevia() throws SQLException{
		if (scrollPaneEnfermPrevia == null) {
			scrollPaneEnfermPrevia = new JScrollPane();
			
			scrollPaneEnfermPrevia.setViewportView(getTableEP());
		}
		return scrollPaneEnfermPrevia;
	}
	
	
	public String darEnfermPrevias() throws SQLException {
		String enfermPrev = "";
		List<String> enfermPrevs = new ArrayList<>();
		enfermPrevs = pbd.buscarEnfermPrevias(hm.getHistorial());
		for(String e: enfermPrevs) {
			enfermPrev += e + "\n";
		}
		return enfermPrev;
	}
	private JScrollPane getScrollPaneVacunas() throws SQLException {
		if (scrollPaneVacunas == null) {
			scrollPaneVacunas = new JScrollPane();
			
			scrollPaneVacunas.setViewportView(getTableVacunas());
		}
		return scrollPaneVacunas;
	}
	
	/**
	 * Método que me muestra las vacunas que se le han asignado al paciente
	 * @return
	 * @throws SQLException
	 */
	public String mostrarVacunas() throws SQLException {
		
		String vacunas = "";
		
		List<String> nombreVacunas = new ArrayList<>();
		nombreVacunas = pbd.buscarVacunasAsignadas(hm.getHistorial());
		for(String str : nombreVacunas) {
			vacunas += str + "\n";
		}
		return vacunas;
	}
	private JPanel getPanelPreinscripciones() throws SQLException {
		if (panelPreinscripciones == null) {
			panelPreinscripciones = new JPanel();
			panelPreinscripciones.setLayout(new BorderLayout(0, 0));
			panelPreinscripciones.add(getScrollPanePreinscripciones());
			//panelPreinscripciones.add(getTablePrescripciones(), BorderLayout.NORTH);
		}
		return panelPreinscripciones;
	}
	private JScrollPane getScrollPanePreinscripciones() throws SQLException {
		if (scrollPanePreinscripciones == null) {
			scrollPanePreinscripciones = new JScrollPane();
			
			scrollPanePreinscripciones.setViewportView(getTablePrescripciones());
		}
		return scrollPanePreinscripciones;
	}

	/**
	 * Método para rellenar con las preinscripciones que le han asignado hasta ahora al paciente
	 * @return
	 * @throws SQLException 
	 */
	private String ponerPreinscripciones() throws SQLException {
		String preinscripciones = "";
		
		List<String> nombrePreinscripciones = new ArrayList<>();
		nombrePreinscripciones = pbd.buscarPreinscripcionesAsignadas(hm.getHistorial());
		for(String str : nombrePreinscripciones) {
			preinscripciones += str + "\n";
		}
		return preinscripciones;
	}
	private JPanel getPanelDiagnosticos() throws SQLException {
		if (panelDiagnosticos == null) {
			panelDiagnosticos = new JPanel();
			panelDiagnosticos.setLayout(new BorderLayout(0, 0));
			panelDiagnosticos.add(getScrollPaneDiagnosticos());
			//panelDiagnosticos.add(getTableDiagnosticos(), BorderLayout.NORTH);
		}
		return panelDiagnosticos;
	}
	private JScrollPane getScrollPaneDiagnosticos() throws SQLException {
		if (scrollPaneDiagnosticos == null) {
			scrollPaneDiagnosticos = new JScrollPane();
			
			scrollPaneDiagnosticos.setViewportView(getTableDiagnosticos());

		}
		return scrollPaneDiagnosticos;
	}

	/**
	 * Método para pintar en el historial los diagnosticos que se le han asignado a un paciente
	 * 
	 * @return
	 * @throws SQLException 
	 */
	private String ponerDiagnosticos() throws SQLException {
		String diagnosticos = "";
		
		List<String> nombreDiagnosticos = new ArrayList<>();
		nombreDiagnosticos = pbd.buscarDiagnosticosAsignados(hm.getHistorial());
		for(String str : nombreDiagnosticos) {
			diagnosticos += str + "\n";
		}
		return diagnosticos;
	}
	
	
	
	
	private JButton getBtnNewButton() {
		if (btnNewButton == null) {
			btnNewButton = new JButton("Descargar Historial");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					descargarHistorial();
				}
			});
		}
		return btnNewButton;
	}
	
	void descargarHistorial() {
		
		HistorialToPDF h=  new HistorialToPDF();
		ParserBaseDeDatos pbd = new ParserBaseDeDatos();
		Paciente paciente;
		try {
			paciente = pbd.pacienteHistorial(hm.getHistorial());
			h.createPDF(paciente);
		} catch (SQLException | FileNotFoundException | JRException e) {
			
			e.printStackTrace();
		}
		
		JOptionPane.showMessageDialog(null, "Se ha generado su historial con éxito");
		
		
		
	}
	private JButton getBtnNewButton_1() {
		if (btnNewButton_1 == null) {
			btnNewButton_1 = new JButton("");
			//btnNewButton_1.setIcon(new ImageIcon("C:\\Users\\santi\\Downloads\\interface+multimedia+print+printer+icon-1320185667007730348_24.png"));
			btnNewButton_1.setIcon(new ImageIcon(MostrarHistorial.class.getResource("/img/imoresora.png")));
			btnNewButton_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					Printer printer= new Printer();
					try {
						File archivo = new File("historial/"+hm.getHistorial()+"Historial.pdf");
						if (!archivo.exists()) {
							descargarHistorial();
						}
						printer.print("historial/"+hm.getHistorial()+"Historial.pdf");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			});
		}
		return btnNewButton_1;
	}
	private JPanel getPanelProcedimientos() {
		if (panelProcedimientos == null) {
			panelProcedimientos = new JPanel();
			panelProcedimientos.setLayout(new BorderLayout(0, 0));
			panelProcedimientos.add(getScrollPaneProcedimientos());
			//panelProcedimientos.add(getTableProcedimientos(), BorderLayout.NORTH);
			//panelProcedimientos.add(getTextAreaProcedimientos());
		}
		return panelProcedimientos;
	}
	private JScrollPane getScrollPaneProcedimientos() {
		if (scrollPaneProcedimientos == null) {
			scrollPaneProcedimientos = new JScrollPane();
			
			scrollPaneProcedimientos.setViewportView(getTableProcedimientos());

		}
		return scrollPaneProcedimientos;
	}

	/**
	 * Método que me muestra todos los procedimientos que se le han asignado al paciente
	 * @return
	 * @throws SQLException 
	 */
	private String ponerProcedimientos() throws SQLException {
		String procedimientos = "";
		
		List<String> nombreProcedimientos = new ArrayList<>();
		nombreProcedimientos = pbd.mostrarProcedimientosAsignados(hm.getHistorial());
		for(String str : nombreProcedimientos) {
			procedimientos += str + "\n";
		}
		return procedimientos;
	}
	private JPanel getPanelAntecedentes() {
		if (panelAntecedentes == null) {
			panelAntecedentes = new JPanel();
			panelAntecedentes.setLayout(new BorderLayout(0, 0));
			panelAntecedentes.add(getScrollPaneAntecedentes());
			panelAntecedentes.add(getPnNuevoAntecedente(), BorderLayout.SOUTH);
			//panelAntecedentes.add(getTableAntecedentes(), BorderLayout.NORTH);
			//panelAntecedentes.add(getTextAreaAntecedentes());
		}
		return panelAntecedentes;
	}
	private JScrollPane getScrollPaneAntecedentes() {
		if (scrollPaneAntecedentes == null) {
			scrollPaneAntecedentes = new JScrollPane();
			
			scrollPaneAntecedentes.setViewportView(getTableAntecedentes());

		}
		return scrollPaneAntecedentes;
	}
	private JTextArea getTextAreaAntecedentes() {
		if (textAreaAntecedentes == null) {
			textAreaAntecedentes = new JTextArea();

			try {
				textAreaAntecedentes.setText(ponerAntecedentes());
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return textAreaAntecedentes;
	}

	
	/**
	 * Método que pone los antecedentes en el historial
	 * @return
	 * @throws SQLException 
	 */
	private String ponerAntecedentes() throws SQLException {
		String antecedentes = "";
		
		List<String> nombreAntecedentes = new ArrayList<>();
		nombreAntecedentes = pbd.buscarAntecedentesAsignados(hm.getHistorial());
		for(String str : nombreAntecedentes) {
			antecedentes += str + "\n";
		}
		return antecedentes;
	}
	private JTable getTableAntecedentes() {
		if (tableAntecedentes == null) {
			String[] nombreColumnas= {"Antecedente","Fecha", "Empleado"};
			modeloTablaAntecedentes= new ModeloNoEditable(nombreColumnas,0);
			tableAntecedentes = new JTable(modeloTablaAntecedentes);
			tableAntecedentes.getTableHeader().setReorderingAllowed(false);//Evita que se pueda mpver las columnas
			tableAntecedentes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			tableAntecedentes.getTableHeader().setBackground(Color.LIGHT_GRAY);
			TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tableAntecedentes.getModel());
			tableAntecedentes.setRowSorter(sorter);
			
			List<RowSorter.SortKey> sortKeys = new ArrayList<>();
			sortKeys.add(new RowSorter.SortKey(4, SortOrder.ASCENDING));
			sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
			
			//sorter.setSortKeys(sortKeys);
			
			try {
				añadirFilasAntecedentes();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return tableAntecedentes;
	}

	
	/**
	 * Método para rellenar la tabla de los antecedentes
	 * @throws SQLException 
	 */
	private void añadirFilasAntecedentes() throws SQLException {
		borrarModeloTablaAntecedentes(); // Borramos todo antes de volver a pintar
		
		Object[] nuevaFila=new Object[3]; // 3 son las columnas
				
		
			for (AsignaAntecedente a : antecedentesAsignados) {
				nuevaFila[0] = a.getNombreAntecedente(); // El nombre del antecedendente
				nuevaFila[1] = a.getFecha(); // La fecha en la que se le asigno
				//nuevaFila[2] = a.getCodEmpleado(); // El empleado que se lo asigno
				nuevaFila[2] = pbd.buscarEmpleadoPorCodigo(a.getCodEmpleado());

				
				modeloTablaAntecedentes.addRow(nuevaFila); // Añado la fila
			}		
	}

	
	/**
	 * Método que me borra toda la tabla de antecedentes
	 */
	private void borrarModeloTablaAntecedentes() {
		int filas = modeloTablaAntecedentes.getRowCount();
		for (int i = 0; i < filas; i++) {
			modeloTablaAntecedentes.removeRow(0);			
		}	
		
	}
	private JTable getTableProcedimientos() {
		if (tableProcedimientos == null) {
			String[] nombreColumnas= {"Procedimiento","Fecha", "Empleado"};
			modeloTablaProcedimientos= new ModeloNoEditable(nombreColumnas,0);
			tableProcedimientos = new JTable(modeloTablaProcedimientos);
			tableProcedimientos.getTableHeader().setReorderingAllowed(false);//Evita que se pueda mpver las columnas
			tableProcedimientos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			tableProcedimientos.getTableHeader().setBackground(Color.LIGHT_GRAY);
			TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tableProcedimientos.getModel());
			tableProcedimientos.setRowSorter(sorter);
			
			List<RowSorter.SortKey> sortKeys = new ArrayList<>();
			sortKeys.add(new RowSorter.SortKey(4, SortOrder.ASCENDING));
			sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
			
			//sorter.setSortKeys(sortKeys);
			
			try {
				añadirFilasProcedimientos();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return tableProcedimientos;
	}

	
	/**
	 * Método para añadir las filas a la tabla procedimientos
	 * @throws SQLException 
	 */
	private void añadirFilasProcedimientos() throws SQLException {
		borrarModeloTablaProcedimientos(); // Borramos todo antes de volver a pintar
		
		Object[] nuevaFila=new Object[3]; // 3 son las columnas
				
		
			for (AsignaProcedimiento a : procedimientosAsignados) {
				nuevaFila[0] = a.getNombreProcedimiento(); // El nombre del antecedendente
				nuevaFila[1] = a.getFecha(); // La fecha en la que se le asigno
				//nuevaFila[2] = a.getCodEmpleado(); // El empleado que se lo asigno
				nuevaFila[2] = pbd.buscarEmpleadoPorCodigo(a.getCodEmpleado());


				
				modeloTablaProcedimientos.addRow(nuevaFila); // Añado la fila
			}	
	
		
	}

	
	/**
	 * Método para borrar todos los procedimientos de la tabla
	 */
	private void borrarModeloTablaProcedimientos() {
		int filas = modeloTablaProcedimientos.getRowCount();
		for (int i = 0; i < filas; i++) {
			modeloTablaProcedimientos.removeRow(0);			
		}	
		
	}
	private JTable getTableDiagnosticos() {
		if (tableDiagnosticos == null) {
			String[] nombreColumnas= {"Código","Diagnóstico", "Fecha", "Empleado"};
			modeloTablaDiagnosticos= new ModeloNoEditable(nombreColumnas,0);
			tableDiagnosticos = new JTable(modeloTablaDiagnosticos);
			tableDiagnosticos.getTableHeader().setReorderingAllowed(false);//Evita que se pueda mpver las columnas
			tableDiagnosticos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			tableDiagnosticos.getTableHeader().setBackground(Color.LIGHT_GRAY);
			TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tableDiagnosticos.getModel());
			tableDiagnosticos.setRowSorter(sorter);
			
			List<RowSorter.SortKey> sortKeys = new ArrayList<>();
			sortKeys.add(new RowSorter.SortKey(4, SortOrder.ASCENDING));
			sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
			
			
			try {
				añadirFilasDiagnosticos();
			} catch (SQLException e) {
				e.printStackTrace();
			}		
		}
		return tableDiagnosticos;
	}

	
	/**
	 * Método que me pinta las filas de la tabla de los diagnosticos
	 * @throws SQLException 
	 */
	private void añadirFilasDiagnosticos() throws SQLException {
		
		borrarModeloTablaDiagnosticos(); // Borramos todo antes de volver a pintar
		
		Object[] nuevaFila=new Object[4]; // 4 son las columnas
				
		
			for (AsignaDiagnostico a : diagnosticosAsignados) {
				nuevaFila[0] = a.getnDiagnostico();
				nuevaFila[1] = a.getNombreDiagnostico(); // El nombre del antecedendente
				nuevaFila[2] = a.getFecha(); // La fecha en la que se le asigno
				//nuevaFila[3] = a.getCodMedico(); // El empleado que se lo asigno
				nuevaFila[3] = pbd.buscarEmpleadoPorCodigo(a.getCodMedico());

				
				modeloTablaDiagnosticos.addRow(nuevaFila); // Añado la fila
			}
	}

	
	/**
	 * Método para borrar la tabla de los diagnosticos
	 */
	private void borrarModeloTablaDiagnosticos() {
		int filas = modeloTablaDiagnosticos.getRowCount();
		for (int i = 0; i < filas; i++) {
			modeloTablaDiagnosticos.removeRow(0);			
		}			
	}
	private JTable getTablePrescripciones() {
		if (tablePrescripciones == null) {
			String[] nombreColumnas= {"Prescripción","Cantidad", "Intervalo (días)", "Duración (horas)", "Instrucciones"};
			modeloTablaPrescripciones= new ModeloNoEditable(nombreColumnas,0);
			tablePrescripciones = new JTable(modeloTablaPrescripciones);
			tablePrescripciones.getTableHeader().setReorderingAllowed(false);//Evita que se pueda mpver las columnas
			tablePrescripciones.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			tablePrescripciones.getTableHeader().setBackground(Color.LIGHT_GRAY);
			TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tablePrescripciones.getModel());
			tablePrescripciones.setRowSorter(sorter);
			
			List<RowSorter.SortKey> sortKeys = new ArrayList<>();
			sortKeys.add(new RowSorter.SortKey(4, SortOrder.ASCENDING));
			sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
			
			
			añadirFilasPrescripciones();	
		}
		return tablePrescripciones;
	}

	
	/**
	 * Método para añadir las prescripciones a la tabla
	 */
	private void añadirFilasPrescripciones() {
		borrarModeloTablaPrescripciones(); // Borramos todo antes de volver a pintar
		
		Object[] nuevaFila=new Object[5]; // 5 son las columnas
				
		
			for (AsignaPreinscripcion a : prescripcionesAsignados) {
				
				
				nuevaFila[0] = a.getCodigoPreinscripcion(); // El nombre del antecedendente
				nuevaFila[1] = "" + a.getCantidad(); 
				nuevaFila[2] = "" + a.getIntervalo(); 
				nuevaFila[3] = "" + a.getDuracion();
				nuevaFila[4] = "" + a.getInstrucciones();

				
				modeloTablaPrescripciones.addRow(nuevaFila); // Añado la fila
			}	
		
	}

	
	/**
	 * Método para borrar todas las prescripciones
	 */
	private void borrarModeloTablaPrescripciones() {
		int filas = modeloTablaPrescripciones.getRowCount();
		for (int i = 0; i < filas; i++) {
			modeloTablaPrescripciones.removeRow(0);			
		}			
	}
	private JTable getTableVacunas() {
		if (tableVacunas == null) {
			String[] nombreColumnas= {"Vacuna","Empleado", "Fecha"};
			modeloTablaVacunas= new ModeloNoEditable(nombreColumnas,0);
			tableVacunas = new JTable(modeloTablaVacunas);
			tableVacunas.getTableHeader().setReorderingAllowed(false);//Evita que se pueda mpver las columnas
			tableVacunas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			tableVacunas.getTableHeader().setBackground(Color.LIGHT_GRAY);
			TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tableVacunas.getModel());
			tableVacunas.setRowSorter(sorter);
			
			List<RowSorter.SortKey> sortKeys = new ArrayList<>();
			sortKeys.add(new RowSorter.SortKey(4, SortOrder.ASCENDING));
			sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
			
			
			try {
				añadirFilasVacunas();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return tableVacunas;
	}
	
	
	
	
	/**
	 * Método para rellenar la tabla de los antecedentes
	 * @throws SQLException 
	 */
	private void añadirFilasVacunas() throws SQLException {
		borrarModeloTablaVacunas(); // Borramos todo antes de volver a pintar
		
		Object[] nuevaFila=new Object[3]; // 3 son las columnas
				
		
			for (AsignaVacuna a : vacunasAsignados) {
				nuevaFila[0] = a.getNombreVacuna(); 
				//nuevaFila[1] = a.getCodEmpleado(); 
				nuevaFila[1] = pbd.buscarEmpleadoPorCodigo(a.getCodEmpleado());

				nuevaFila[2] = a.getDate(); 


				
				modeloTablaVacunas.addRow(nuevaFila); // Añado la fila
			}		
	}

	
	/**
	 * Borrar todas las filas de la tabla
	 */
	private void borrarModeloTablaVacunas() {
		int filas = modeloTablaVacunas.getRowCount();
		for (int i = 0; i < filas; i++) {
			modeloTablaVacunas.removeRow(0);			
		}		
	}
	private JTable getTableEP() throws SQLException {
		if (tableEP == null) {
			String[] nombreColumnas= {"Enferemedades previas","Empleado", "Fecha"};
			modeloTablaEP= new ModeloNoEditable(nombreColumnas,0);
			tableEP = new JTable(modeloTablaEP);
			tableEP.getTableHeader().setReorderingAllowed(false);//Evita que se pueda mpver las columnas
			tableEP.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			tableEP.getTableHeader().setBackground(Color.LIGHT_GRAY);
			TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tableEP.getModel());
			tableEP.setRowSorter(sorter);
			
			List<RowSorter.SortKey> sortKeys = new ArrayList<>();
			sortKeys.add(new RowSorter.SortKey(4, SortOrder.ASCENDING));
			sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
			
			
			añadirFilasEP();
		}
		return tableEP;
	}
	
	
	
	/**
	 * Método para rellenar la tabla de los EP
	 * @throws SQLException 
	 */
	private void añadirFilasEP() throws SQLException {
		borrarModeloTablaEP(); // Borramos todo antes de volver a pintar
		
		Object[] nuevaFila=new Object[3]; // 3 son las columnas
				
		
			for (AsignaEnfermPrev a : enfermedadesPAsignadas) {
				nuevaFila[0] = a.getNombreEnfermPrev(); // El nombre del antecedendente
				nuevaFila[1] = pbd.buscarEmpleadoPorCodigo(a.getCodEmpleado());
				//nuevaFila[1] = a.getCodEmpleado(); // El empleado que se lo asigno    
				nuevaFila[2] = a.getDate(); // La fecha en la que se le asigno

				
				modeloTablaEP.addRow(nuevaFila); // Añado la fila
			}		
	}

	
	
	/**
	 * para borrar toda la tabla de enfermedades previas
	 */
	private void borrarModeloTablaEP() {
		int filas = modeloTablaEP.getRowCount();
		for (int i = 0; i < filas; i++) {
			modeloTablaEP.removeRow(0);			
		}	
		
	}

	private JPanel getPnNuevoAntecedente() {
		if (pnNuevoAntecedente == null) {
			pnNuevoAntecedente = new JPanel();
			FlowLayout flowLayout = (FlowLayout) pnNuevoAntecedente.getLayout();
			flowLayout.setAlignment(FlowLayout.RIGHT);
			pnNuevoAntecedente.add(getBtnNuevoAntecedente());
		}
		return pnNuevoAntecedente;
	}
	private JButton getBtnNuevoAntecedente() {
		if (btnNuevoAntecedente == null) {
			btnNuevoAntecedente = new JButton("A\u00F1adir nuevo antecedente");
			btnNuevoAntecedente.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					nuevoAntecedente();
				}
			});
		}
		return btnNuevoAntecedente;
	}

	
	/**
	 * Método que me abre un nuevo antecedente
	 */
	protected void nuevoAntecedente() {
		AnadirAntecedentesHistorial vah = new AnadirAntecedentesHistorial(this, hm, codempleado);
		
		vah.setLocationRelativeTo(this);
		vah.setResizable(true);
		vah.setModal(true); // hasta que no se cierre una ventana no se puede abrir otra
		vah.setVisible(true);
		
	}

	
	/**
	 * Método que repinta la tabla de los antecedentes
	 * @throws SQLException 
	 */
	public void repintarTablaAntecedentes() throws SQLException {
		this.antecedentesAsignados = pbd.listarAntecedentesAsignados(hm.getHistorial());
		añadirFilasAntecedentes();
	}
}
