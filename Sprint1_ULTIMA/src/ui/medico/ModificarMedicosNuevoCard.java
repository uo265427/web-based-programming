package ui.medico;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import logica.AccionEmpleado;
import logica.Antecedente;
import logica.AsignaAntecedente;
import logica.AsignaDiagnostico;
import logica.AsignaPreinscripcion;
import logica.AsignaProcedimiento;
import logica.AsignaVacuna;
import logica.Causas;
import logica.Cita;
import logica.Diagnostico;
import logica.Email;
import logica.HistorialMedico;
import logica.Paciente;
import logica.Preinscripcion;
import logica.Procedimiento;
import logica.Vacuna;
import logica.servicios.ParserBaseDeDatos;
import logica.servicios.PrescripcionesToPDF;
import logica.servicios.Printer;
import net.sf.jasperreports.engine.JRException;
import ui.AnadirAntecedentesHistorial;
import ui.MostrarHistorial;

import javax.swing.ButtonGroup;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.BoxLayout;
import java.awt.Rectangle;
import javax.swing.ScrollPaneConstants;
import javax.swing.JCheckBox;

public class ModificarMedicosNuevoCard extends JDialog {

	private boolean listo = true;
	private boolean listoAntecedentes = true;
	private boolean tablaPrescripcionesLista = false;
	private boolean tablaAntecedentesLista = false;
	private boolean tablaVacunasLista = false;
	private boolean tablaDiagnosticosLista = false;
	private boolean tablaProcedimientosLista = false;
	
	DefaultListModel modeloLista = new DefaultListModel();
	private ModeloNoEditable modeloTablaPrescripciones;
	private ModeloNoEditable modeloTablaAntecedentes;
	private ModeloNoEditable modeloTablaVacunas;
	private ModeloNoEditable modeloTablaDiagnosticos;
	private ModeloNoEditable modeloTablaProcedimientos;
	
	
	private JPanel contentPane;
	private JPanel panelSur;
	private JButton button;
	private JButton button_1;
	private JPanel panelCentro;
	private JTabbedPane panelPestañas;
	private JPanel panelCausas;
	private JPanel panelVacunas;

	private ParserBaseDeDatos pbd=new ParserBaseDeDatos();
	private Cita cita; // El paciente del que estamos modificando la cita
	private Preinscripcion preinscripcion; // La preinscripcion
	private Antecedente antecedente; // El antecedente
	private List<Preinscripcion> preinscripciones; // Las preinscripciones que tenemos en la base de datos
	private List<Vacuna> vacunas; // Las bacuans que tenemos en la base de datos
	private List<Diagnostico> diagnosticos; // Los diagnosticos que tenemos en la base de datos
	private List<Procedimiento> procedimientos; // Los procedimientos que tenemos en la base de datos
	private List<Antecedente> antecedentes; // Los antecedentes que tenemos en la base de datos
	private List<Preinscripcion> preinscripcionesPaciente = new ArrayList<Preinscripcion>();
	//private List<Antecedente> antecedentesPaciente = new ArrayList<Antecedente>();
	private List<AsignaPreinscripcion> asignaPreinscripcionesPaciente = new ArrayList<AsignaPreinscripcion>();
	private List<AsignaVacuna> asignaVacunasPaciente = new ArrayList<AsignaVacuna>();
	private List <AsignaDiagnostico> asignaDiagnosticosPaciente = new ArrayList<AsignaDiagnostico>();
	private List <AsignaProcedimiento> asignaProcedimientosPaciente = new ArrayList<AsignaProcedimiento>();
	private List<AsignaAntecedente> asignaAntecedentesPaciente = new ArrayList<AsignaAntecedente>();
	private Paciente paciente;
	private Time horaInicioCita; // La hora que le puede asignar el medico 
	private Time horaFinCita; // La hora que le puede asignar el médico
	private JLabel lblAcudi;
	private Causas causa;
	private List<Causas> causas;
	private JPanel pnDatosHoraAcudio;
	private JPanel pnIz;
	private JLabel label;
	private JPanel pnDch;
	private JTextField txtName;
	private JPanel pnDatosPaciente;
	private JPanel pnHistorial;
	private JPanel pnHoraEntrada;
	private JPanel pnHoraSalida;
	private JPanel panel_2;
	private JLabel lblNombre;
	private JButton btnSeleccionar;
	private JButton btnAñadirNuevo;
	private JComboBox cbCausas;

	private List<String> nombresAntecedentes;
	private ModificarMedicosNuevoCard mm;
	private JPanel pnPreinscripciones;
	private JPanel pnIzq;
	private JPanel pnDcha;
	private JPanel pnCentro;
	private JLabel lblNombrePreinscripcion;
	private JButton btnNueva;
	private JLabel lblNCantidad;
	private JLabel lblDuracion;
	private JLabel lblIntervalo;
	private JLabel lblInstrucciones;
	private JComboBox<String> cbNombre;
	private JPanel pnVacío;
	private JSpinner spinnerCantidad;
	private JSpinner spinnerDuracion;
	private JSpinner spinnerIntervalo;
	private JScrollPane scrollPane;
	private JTextArea textArea;
	private JPanel pnResumenP;
	private JPanel pnBorrarPreinscripcion;
	private JButton btnBorrarPrescripcion;
	private JPanel pnResumenPreinscripciones;
	private JLabel lblResumenPreinscripciones;
	private JList list;
	private JScrollPane scrollPane_2;
	private JButton btnAadirPreinscripcin;
	private JTable tablePrescripciones;
	private JPanel pnAcudio;
	private JRadioButton rdbtnAcudio;
	private JRadioButton rdbtnNoAcudio;
	private Component horizontalStrut;
	private JScrollPane scrollPTablaPrescripciones;
	private JPanel pnDiagnosticos;
	private JComboBox<String> cbDiagnosticos;
	private JButton btnDiagnosticar;
	private JLabel lblHoraInicio;
	private JPanel pnVacio3;
	private boolean causaSeleccionada;
	private JPanel pnHoraInicio;
	private JPanel pnVacio;
	private JPanel pnVacio2;
	private JSpinner spinnerHInicioR;
	private JPanel pnHoraInicioF;
	private JPanel pnGuardarHInicio;
	private JPanel pnModificarHInicio;
	private JButton btnModificar;
	private JSpinner spinnerHInicioNueva;
	private JButton btnGuardarInicio;
	private JLabel lblHorasalida;
	private JPanel pnVacio5;
	private JPanel pnHoraFin;
	private JPanel pnVacio6;
	private JSpinner spinnerHFinR;
	private JPanel pnVacio7;
	private JPanel pnHoraFinF;
	private JPanel pnModificarHoraFinF;
	private JButton btnModificarFin;
	private JSpinner spinnerHFinNueva;
	private JPanel pnGuardarHFin;
	private JButton btnGuardarFin;
	private JLabel label_1;
	private JTextField txtApellido;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JButton btnMostrarHistorial;
	private JPanel pnVacio11;
	private JPanel pnVacio8;
	private JPanel pnMostrarHistorialVacunas;
	private JPanel pnVacio9;
	private JPanel pnMostrar;
	private JPanel pnVacio10;
	private JPanel pnVacio12;
	private JPanel pnSeleccionAcudio;
	private JPanel pnVacio13;
	private JTextField txtFiltrarDiagnosticos;
	private JButton btnFiltrarDiagnosticos;
	private JButton btnCalendarioVacunas;
	private JButton btnNewButton;
	private JPanel panel_4;
	private JLabel lblNewLabel;
	private JTextField txtNombreCausa;
	private JButton btnFiltrar;
	private JButton buttonpres;
	private JButton btnprintpres;

	private JPanel pnProcedimientos;
	private JTextField txtfFiltrarProcedimientos;
	private JButton btnFiltrarProcedimientos;
	private JComboBox<String> cbProcedimientos;
	private JButton btnProceder;

	private JPanel pnAntecedentes;
	private JPanel panel_1;
	private JPanel panel_3;
	private JPanel panel_5;
	private JButton btnNewButton_1;
	private JPanel pnCentroA;
	private JPanel pnDchaA;
	private JPanel pnResumenA;
	private JPanel pnBorrarAntecedente;
	private JPanel pnResumenAntecedentes;
	private JScrollPane scrollPTablaAntecedentes;
	private JTable tableAntecedentes;
	private JButton btnBorrarAntecedente;
	private JLabel lblResumenAntecedentes;
	private JPanel pnAccionesAntecedentes;
	private JPanel pnCbAntecedentes;
	private JPanel pnAnadirAntecedente;
	private JPanel pnVacío28;
	private JPanel pnVerAntecedentes;
	private JComboBox<String> cbAntecedentes;
	private JButton btnAsignarAntecedente;
	private JButton btnVerAnterioresAntecedentes;
	private JTextField txtFiltrarAntecedentes;
	private JButton btnFiltrarAntecedentes;
	private JPanel pnNuevoAntecedente;
	private JButton btnNuevoAntecedente;
	private JPanel pnCentroV;
	private JPanel pnDchaV;
	private JPanel pnAccionesVacunas;
	private JPanel pnVacio29;
	private JPanel pnCbVacunas;
	private JPanel pnAnadirVacuna;
	private JPanel pnVacio30;
	private JPanel pnVacio31;
	private JTextField textfFiltrarVacunas;
	private JButton btnFiltrarVacunas;
	private JComboBox<String> cbVacunas;
	private JButton btnAsignarVacuna;
	private JPanel pnResumenV;
	private JPanel pnBorrarVacuna;
	private JPanel pnResumenVacunas;
	private JScrollPane scrollPTablaVacunas;
	private JTable tableVacunas;
	private JButton btnBorrarVacuna;
	private JLabel lblResumenVacunas;
	private JPanel pnCentroD;
	private JPanel pnDchaD;
	private JPanel pnAccionesDiagnosticos;
	private JPanel pnVacio32;
	private JPanel pnCbDiagnosticos;
	private JPanel pnAnadirDiagnostico;
	private JPanel pnVacio33;
	private JPanel pnVacio34;
	private JPanel pnResumenD;
	private JPanel pnBorrarDiagnostico;
	private JPanel pnResumenDiagnosticos;
	private JScrollPane scrollPTablaDiagnosticos;
	private JTable tableDiagnosticos;
	private JButton btnBorrarDiagnostico;
	private JLabel lblResumenDiagnosticos;
	private JPanel panelCentroPr;
	private JPanel pnDchaPr;
	private JPanel pnAccionesProcedimientos;
	private JPanel pnVacio35;
	private JPanel pnCbProcedimientos;
	private JPanel pnAnadirProcedimiento;
	private JPanel pnVacio36;
	private JPanel pnVacio37;
	private JPanel pnResumenPr;
	private JPanel pnBorrarProcedimiento;
	private JPanel pnResumenProcedimientos;
	private JScrollPane scrollPTablaProcedimientos;
	private JTable tableProcedimientos;
	private JButton btnBorrarProcedimiento;
	private JLabel lblResumenProcedimientos;

	private JPanel pnVacio27;
	
	//panel causas
	private JPanel panelIzqCausas;
	private JPanel panelDchaCausas;
	private JLabel lblCausasSelecc;
	private JPanel panel;
	private JPanel panelElimCausa;
	private JButton btnElimCausa;
	private JScrollPane scrollPaneCausa;
	private JTable tableCausa;
	private ModeloNoEditable modeloTablaCausas;
	private boolean tablaCausaBool = false;
	private List<Causas> listaCausasSeleccionadas;
	private JPanel panelMedioCausas;
	private JButton btnAnadirCausa;
	private JTextField txtFiltrarCausa;
	private JButton btnFiltroCausa;
	private Component verticalStrutCausa;
	private Component verticalStrutCausa3;
	private Component verticalStructCausa3;
	private JLabel lblNombreCausa;
	private JLabel lblFiltrarCausa;
	private Component verticalStructCausa4;
	private Component verticalStructCausa5;
	private JButton btnSeleccionarCausa;
	private List<Causas> nombresCausas;
	private String codmedico;
	private JCheckBox chckbxEdo;
	
	/**
	 * Create the frame.
	 * @throws SQLException 
	 */
	public ModificarMedicosNuevoCard(String codmedico, Paciente paciente, Cita cita) throws SQLException {
		setIconImage(Toolkit.getDefaultToolkit().getImage(AnadirAntecedentesHistorial.class.getResource("/img/logop.jpg")));
		this.codmedico = codmedico;
		setTitle("Atender Consulta");
		mm = this;
		this.paciente = paciente;
		this.cita = cita;
		ponerCausas();
		causaSeleccionada = false;

		listaCausasSeleccionadas = new ArrayList<Causas>();
		preinscripciones = pbd.listarPrescripciones();
		vacunas = pbd.listarVacunas();
		diagnosticos = pbd.listarDiagnosticos();
		procedimientos = pbd.cargarProcedimientos();
		antecedentes = pbd.cargarAntecedentes();

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1300, 691);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.add(getPanelSur(), BorderLayout.SOUTH);
		contentPane.add(getPanelCentro(), BorderLayout.CENTER);
	}

	private JPanel getPanelSur() {
		if (panelSur == null) {
			panelSur = new JPanel();
			panelSur.setBackground(SystemColor.menu);
			panelSur.add(getButton());
			panelSur.add(getButton_1());
			
			panelSur.add(getButtonPrescricpcion());
			panelSur.add(getButtonPrintPres());
		}
		return panelSur;
	}
	private JButton getButton() {
		if (button == null) {
			button = new JButton("Guardar");
			button.setFont(new Font("Tahoma", Font.PLAIN, 16));
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						guardar();
						consultaAtendida();
						//dispose();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
		}
		return button;
	}
	private JButton getButtonPrescricpcion() {
		if (buttonpres == null) {
			buttonpres = new JButton("Descargar prescripción");
			buttonpres.setFont(new Font("Tahoma", Font.PLAIN, 16));
			buttonpres.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					descargarPrescricpiones();
				}
			});
			
		}
			return buttonpres;
	}
	
	private void consultaAtendida() {
			JOptionPane.showMessageDialog(null, "Los cambios de la consulta han sido guardados");
	}
	
	private void descargarPrescricpiones() {
		PrescripcionesToPDF pres= new PrescripcionesToPDF();
		try {
			pres.createPDF(paciente);
		} catch (FileNotFoundException | JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JOptionPane.showMessageDialog(null, "Se ha generado la receta correctamente");
	}
	
	private JButton getButtonPrintPres() {
		if (btnprintpres == null) {
			btnprintpres = new JButton();
			btnprintpres.setText("Prescripci\u00F3n");
			//btnprintpres.setIcon(new ImageIcon("C:\\Users\\Alba\\Downloads\\interface+multimedia+print+printer+icon-1320185667007730348_24.png"));
			btnprintpres.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					imprimirPrescripciones();
				}
			});
			btnprintpres.setFont(new Font("Tahoma", Font.PLAIN, 16));
			btnprintpres.setIcon(new ImageIcon(ModificarMedicosNuevoCard.class.getResource("/img/imoresora.png")));
		}
			return btnprintpres;
	}
	
	void imprimirPrescripciones(){
		Printer printer= new Printer();
		try {
			File archivo = new File("Recetas/"+paciente.getHistorial()+"receta.pdf");
			if (!archivo.exists()) {
				descargarPrescricpiones();
			}
			printer.print("Recetas/"+paciente.getHistorial()+"receta.pdf");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
	}
	
	
	
	
	private JButton getButton_1() {
		if (button_1 == null) {
			button_1 = new JButton("Cancelar");
			button_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
			button_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
		}
		return button_1;
	}
	private JPanel getPanelCentro() throws SQLException {
		if (panelCentro == null) {
			panelCentro = new JPanel();
			panelCentro.setLayout(new GridLayout(0, 1, 0, 0));
			panelCentro.add(getPnDatosHoraAcudio());
			panelCentro.add(getPanelPestañas());
		}
		return panelCentro;
	}

	private JTabbedPane getPanelPestañas() throws SQLException {
		if (panelPestañas == null) {
			panelPestañas = new JTabbedPane(JTabbedPane.TOP);
			
			panelPestañas.addTab("Diagnosticos", null, getPnDiagnosticos(), null);
			panelPestañas.addTab("Antecedentes", null, getPnAntecedentes(), null);
			panelPestañas.addTab("Causas", null, getPanelCausas(), null);
			panelPestañas.addTab("Prescripciones", null, getPnPreinscripciones(), null);
			panelPestañas.addTab("Procedimientos", null, getPnProcedimientos(), null);
			panelPestañas.addTab("Vacunas", null, getPanelVacunas(), null);
		
		}
		return panelPestañas;
	}
	private JPanel getPanelCausas() throws SQLException{
		if (panelCausas == null) {
			panelCausas = new JPanel();
			panelCausas.setLayout(new GridLayout(1, 0, 0, 0));
			panelCausas.add(getPanelIzqCausas());
			panelCausas.add(getPanelMedioCausas());
			panelCausas.add(getPanelDchaCausas());
		}
		return panelCausas;
	}
	
	// nuevo causas
	
	private JPanel getPanelIzqCausas() {
		if (panelIzqCausas == null) {
			panelIzqCausas = new JPanel();
			panelIzqCausas.setLayout(new GridLayout(0, 1, 0, 0));
			panelIzqCausas.add(getVerticalStrutCausa3());
			panelIzqCausas.add(getVerticalStructCausa3());
			panelIzqCausas.add(getLblNombreCausa());
			panelIzqCausas.add(getLblFiltrarCausa());
			panelIzqCausas.add(getVerticalStructCausa4());
			panelIzqCausas.add(getVerticalStructCausa5());
		}
		return panelIzqCausas;
	}
	private JPanel getPanelDchaCausas() {
		if (panelDchaCausas == null) {
			panelDchaCausas = new JPanel();
			panelDchaCausas.setLayout(new BorderLayout(0, 0));
			panelDchaCausas.add(getLblCausasSelecc(), BorderLayout.NORTH);
			panelDchaCausas.add(getPanelTablaCausa(), BorderLayout.CENTER);
			panelDchaCausas.add(getPanelElimCausa(), BorderLayout.SOUTH);
		}
		return panelDchaCausas;
	}
	private JLabel getLblCausasSelecc() {
		if (lblCausasSelecc == null) {
			lblCausasSelecc = new JLabel("Causas seleccionadas");
		}
		return lblCausasSelecc;
	}
	private JPanel getPanelTablaCausa() {
		if (panel == null) {
			panel = new JPanel();
			panel.setLayout(new GridLayout(0, 1, 0, 0));
			panel.add(getScrollPaneCausa());
		}
		return panel;
	}
	private JPanel getPanelElimCausa() {
		if (panelElimCausa == null) {
			panelElimCausa = new JPanel();
			panelElimCausa.add(getBtnElimCausa());
		}
		return panelElimCausa;
	}
	private JButton getBtnElimCausa() {
		if (btnElimCausa == null) {
			btnElimCausa = new JButton("Borrar causa");
			btnElimCausa.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					eliminarCausa();
				}
			});
		}
		return btnElimCausa;
	}
	
	private void eliminarCausa() {
		int fila=tableCausa.getSelectedRow();
		
		if (fila != -1) {
			int res = JOptionPane.showConfirmDialog(null, "¿Está seguro de que desea borrar la causa?","Mensaje de confirmación",JOptionPane.YES_NO_OPTION);
			if(res == JOptionPane.YES_OPTION) {	
					
					listaCausasSeleccionadas.remove(tableCausa.getSelectedRow());
					
					añadirFilasCausas();
			
			}						
		}		
	}
	
	private JScrollPane getScrollPaneCausa() {
		if (scrollPaneCausa == null) {
			scrollPaneCausa = new JScrollPane();
			scrollPaneCausa.setViewportView(getTableCausa());
		}
		return scrollPaneCausa;
	}
	private JTable getTableCausa() {
		if (tableCausa == null) {
			tableCausa = new JTable();
			String[] nombreColumnas= {"Nombre"};
			modeloTablaCausas= new ModeloNoEditable(nombreColumnas,0);
			tableCausa = new JTable(modeloTablaCausas);
			tableCausa.getTableHeader().setReorderingAllowed(false);//Evita que se pueda mpver las columnas
			tableCausa.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			tableCausa.getTableHeader().setBackground(Color.LIGHT_GRAY);
			TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tableCausa.getModel());
			tableCausa.setRowSorter(sorter);
			
			
			añadirFilasCausas();
		}
		return tableCausa;
	}

	private void añadirFilasCausas() {
		borrarModeloTablaCausas(); // Borramos todo antes de volver a pintar
		
		Object[] nuevaFila=new Object[1]; // 5 son las columnas
				
		
		if (tablaCausaBool) {
			for (Causas c : listaCausasSeleccionadas) {
				nuevaFila[0] = c.getNombreVacuna(); // El nombre de la preinscripcion
				
				modeloTablaCausas.addRow(nuevaFila); // Añado la fila
			}
		}
		
	}

	private void borrarModeloTablaCausas() {
		int filas = modeloTablaCausas.getRowCount();
		for (int i = 0; i < filas; i++) {
			modeloTablaCausas.removeRow(0);			
		}	
		
	}
	private JPanel getPanelMedioCausas() {
		if (panelMedioCausas == null) {
			panelMedioCausas = new JPanel();
			panelMedioCausas.setLayout(new GridLayout(0, 1, 0, 0));
			panelMedioCausas.add(getVerticalStrutCausa());
			panelMedioCausas.add(getBtnAnadirCausa());
			panelMedioCausas.add(getCbCausas());
			panelMedioCausas.add(getTxtFiltrarCausa());
			panelMedioCausas.add(getBtnFiltroCausa());
			panelMedioCausas.add(getBtnSeleccionarCausa());
		}
		return panelMedioCausas;
	}
	private JButton getBtnAnadirCausa() {
		if (btnAnadirCausa == null) {
			btnAnadirCausa = new JButton("Crear nueva causa");
			btnAnadirCausa.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					AnadirCausas ac = new AnadirCausas(mm);
					ac.setLocationRelativeTo(null);
					ac.setResizable(true);
					ac.setModal(true); // hasta que no se cierre una ventana no se puede abrir otra
					ac.setVisible(true);
				}
			});
		}
		return btnAnadirCausa;
	}
	private JComboBox<Causas> getCbCausas() {
		if (cbCausas == null) {
			cbCausas = new JComboBox<Causas>();
			rellenarCBCausas();
			cbCausas.setSelectedIndex(0);
		}
		return cbCausas;
	}
	
	public void rellenarCBCausas() {
		for (int i = 0; i < nombresCausas.size(); i++) {
			cbCausas.insertItemAt(nombresCausas.get(i).toString(), i);
		}
	}
	
	public void ponerCausas() throws SQLException {
		nombresCausas = new ArrayList<>() ;
		List<Causas> causas = pbd.buscarNombreTodasCausas();
		for(int i =0; i< causas.size(); i++) {
			nombresCausas.add(causas.get(i));
		}
	}
	
	private JTextField getTxtFiltrarCausa() {
		if (txtFiltrarCausa == null) {
			txtFiltrarCausa = new JTextField();
			txtFiltrarCausa.setColumns(10);
		}
		return txtFiltrarCausa;
	}
	private JButton getBtnFiltroCausa() {
		if (btnFiltroCausa == null) {
			btnFiltroCausa = new JButton("Filtrar");
			btnFiltroCausa.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(txtFiltrarCausa.getText().equals(""))
						JOptionPane.showMessageDialog(null, "Por favor introduce un valor");
					else {	
						filtrarPorNombreCausas(txtFiltrarCausa.getText());
				}
				}
			});
		}
		return btnFiltroCausa;
	}
	protected void filtrarPorNombre(String name) {
		for(int i=0; i < nombresCausas.size(); i++) {
			if(nombresCausas.get(i).getNombreVacuna().equals(name)) {
				cbCausas.setSelectedIndex(i);
			}
		}
	}

	private Component getVerticalStrutCausa() {
		if (verticalStrutCausa == null) {
			verticalStrutCausa = Box.createVerticalStrut(20);
		}
		return verticalStrutCausa;
	}
	private Component getVerticalStrutCausa3() {
		if (verticalStrutCausa3 == null) {
			verticalStrutCausa3 = Box.createVerticalStrut(20);
		}
		return verticalStrutCausa3;
	}
	private Component getVerticalStructCausa3() {
		if (verticalStructCausa3 == null) {
			verticalStructCausa3 = Box.createVerticalStrut(20);
		}
		return verticalStructCausa3;
	}
	private JLabel getLblNombreCausa() {
		if (lblNombreCausa == null) {
			lblNombreCausa = new JLabel("Nombre");
		}
		return lblNombreCausa;
	}
	private JLabel getLblFiltrarCausa() {
		if (lblFiltrarCausa == null) {
			lblFiltrarCausa = new JLabel("Filtrar por nombre");
		}
		return lblFiltrarCausa;
	}
	private Component getVerticalStructCausa4() {
		if (verticalStructCausa4 == null) {
			verticalStructCausa4 = Box.createVerticalStrut(20);
		}
		return verticalStructCausa4;
	}
	private Component getVerticalStructCausa5() {
		if (verticalStructCausa5 == null) {
			verticalStructCausa5 = Box.createVerticalStrut(20);
		}
		return verticalStructCausa5;
	}
	private JButton getBtnSeleccionarCausa() {
		if (btnSeleccionarCausa == null) {
			btnSeleccionarCausa = new JButton("Seleccionar causa");
			btnSeleccionarCausa.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					añadirCausa();
				}
			});
		}
		return btnSeleccionarCausa;
	}

	protected void añadirCausa() {
		int indiceSeleciconado = cbCausas.getSelectedIndex(); // Lo que está seleccionado en el comboBox
		Causas c = null; // La preinscripcion
			

		// Buscamos la preinscripcion que hay seleccionada en el comboBox
		int contador = 0;
		for (Causas causa : nombresCausas) {
			if (indiceSeleciconado == contador) {
				c = causa;
			}
			contador = contador + 1;
		}
		listaCausasSeleccionadas.add(c);
		
		if (tablaCausaBool == false) { // Para que no casque al pintar la tabla de los diagnosticos
			tablaCausaBool = true;
		}
		
		añadirFilasCausas(); // Añadimos a la tabla que nos muestra los diagnosticos que ya le hemos asignado
		
		cbCausas.setSelectedIndex(0);
		
	}
	
	private void guardarCausas() throws SQLException {
		String nHistorial = "" + mm.getPaciente().getHistorial();
		Date fecha = new Date();	
		Time hora = new Time(new Date().getTime());
		
		java.sql.Date sDate = new java.sql.Date(fecha.getTime());
		
		Random r = new Random();
		if(!listaCausasSeleccionadas.isEmpty()) {
			for(Causas c: listaCausasSeleccionadas) {
				String nombreCausas = c.getNombreVacuna();
				String codcausa = "" + r.nextInt(300);
				pbd.actualizarAsignaCausa(codcausa,nombreCausas, nHistorial, sDate, hora, codmedico);
				guardarAccionCausa(c.toString());
			}
		}
		
	}
	
	private JPanel getPanelVacunas() throws SQLException {
		if (panelVacunas == null) {
			panelVacunas = new JPanel();
			panelVacunas.setLayout(new BorderLayout(0, 0));
			panelVacunas.add(getPnCentroV(), BorderLayout.CENTER);
			panelVacunas.add(getPnDchaV(), BorderLayout.EAST);
			
		}
		return panelVacunas;
	}


	
	private JPanel getPnPreinscripciones() {
		if (pnPreinscripciones == null) {
			pnPreinscripciones = new JPanel();
			pnPreinscripciones.setLayout(new GridLayout(0, 3, 0, 0));
			pnPreinscripciones.add(getPnIzq());
			pnPreinscripciones.add(getPnCentro());
			pnPreinscripciones.add(getPnDcha());
		}
		return pnPreinscripciones;
	}

	// LOGICA

	public Causas getCausa() {
		return causa;
	}

	public void setCausa(Causas causa) {
		this.causa = causa;
	}

	public Cita getCita() {
		return cita;
	}

	public void setCita(Cita cita) {
		this.cita = cita;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}
	private JPanel getPnDatosHoraAcudio() {
		if (pnDatosHoraAcudio == null) {
			pnDatosHoraAcudio = new JPanel();
			pnDatosHoraAcudio.setLayout(new GridLayout(0, 2, 0, 0));
			pnDatosHoraAcudio.add(getPanIz_1());
			pnDatosHoraAcudio.add(getPanDe_1());
		}
		return pnDatosHoraAcudio;
	}
	private JPanel getPanIz_1() {
		if (pnIz == null) {
			pnIz = new JPanel();
			pnIz.setLayout(new GridLayout(0, 1, 0, 0));
			pnIz.add(getPnDatosPaciente());
			pnIz.add(getPnHoraEntrada());
			pnIz.add(getPnVacio11());
		}
		return pnIz;
	}
	private JLabel getLabel_4() {
		if (label == null) {
			label = new JLabel("Nombre:");
			label.setBounds(10, 21, 86, 26);
		}
		return label;
	}
	
	
	private JLabel getLblAcudi_1() {
		if (lblAcudi == null) {
			lblAcudi = new JLabel("Acudi\u00F3");
		}
		return lblAcudi;
	} 
	private JPanel getPanDe_1() {
		if (pnDch == null) {
			pnDch = new JPanel();
			pnDch.setLayout(new GridLayout(0, 1, 0, 0));
			pnDch.add(getPnHistorial());
			pnDch.add(getPnHoraSalida());
			pnDch.add(getPnAcudio());
		}
		return pnDch;
	}
	private JTextField getTextField_2() {
		if (txtName == null) {
			txtName = new JTextField();
			txtName.setBounds(74, 14, 203, 41);
			txtName.setText((String) null);
			txtName.setEditable(false);
			txtName.setColumns(10);
			txtName.setText(paciente.getNombre());
		}
		return txtName;
	}
	private JPanel getPnDatosPaciente() {
		if (pnDatosPaciente == null) {
			pnDatosPaciente = new JPanel();
			pnDatosPaciente.setBorder(new TitledBorder(null, "Paciente: ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			pnDatosPaciente.setLayout(null);
			pnDatosPaciente.add(getLabel_4());
			pnDatosPaciente.add(getTextField_2());
			pnDatosPaciente.add(getLabel_1());
			pnDatosPaciente.add(getTxtApellido());
		}
		return pnDatosPaciente;
	}
	private JPanel getPnHistorial() {
		if (pnHistorial == null) {
			pnHistorial = new JPanel();
			pnHistorial.setLayout(new GridLayout(0, 2, 0, 0));
			pnHistorial.add(getPanel_5_1());
			pnHistorial.add(getPanel_5_2());
		}
		return pnHistorial;
	}
	private JPanel getPnHoraEntrada() {
		if (pnHoraEntrada == null) {
			pnHoraEntrada = new JPanel();
			pnHoraEntrada.setBorder(new TitledBorder(null, "Hora inicio:", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			pnHoraEntrada.setLayout(new GridLayout(2, 2, 0, 0));
			pnHoraEntrada.add(getPnHoraInicio());
			pnHoraEntrada.add(getPnVacio());
			pnHoraEntrada.add(getPnHoraInicioF());
			pnHoraEntrada.add(getPanel_7_1());
		}
		return pnHoraEntrada;
	}
	private JPanel getPnHoraSalida() {
		if (pnHoraSalida == null) {
			pnHoraSalida = new JPanel();
			pnHoraSalida.setBorder(new TitledBorder(null, "Hora salida:", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			pnHoraSalida.setLayout(new GridLayout(0, 2, 0, 0));
			pnHoraSalida.add(getPnHoraFin());
			pnHoraSalida.add(getPnVacio7());
			pnHoraSalida.add(getPnHoraFinF());
			pnHoraSalida.add(getPnGuardarHFin());
		}
		return pnHoraSalida;
	}
	private JPanel getPanel_2() {
		if (panel_2 == null) {
			panel_2 = new JPanel();
			panel_2.setLayout(new GridLayout(0, 4, 0, 0));
			panel_2.add(getLabel_4_1());
			panel_2.add(getCbCausas());
			panel_2.add(getBtnSeleccionar());
			panel_2.add(getBtnAñadirNuevo());
		}
		return panel_2;
	}
	private JLabel getLabel_4_1() {
		if (lblNombre == null) {
			lblNombre = new JLabel("Nombre");
		}
		return lblNombre;
	}
	private JButton getBtnSeleccionar() {
		if (btnSeleccionar == null) {
			btnSeleccionar = new JButton("Seleccionar");
			btnSeleccionar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setCausaSeleccionada(true);
				}
			});
		}
		return btnSeleccionar;
	}
	private JButton getBtnAñadirNuevo() {
		if (btnAñadirNuevo == null) {
			btnAñadirNuevo = new JButton("A\u00F1adir nueva");
			btnAñadirNuevo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					AnadirCausas ac = new AnadirCausas(mm);
					ac.setLocationRelativeTo(null);
					ac.setResizable(true);
					ac.setModal(true); // hasta que no se cierre una ventana no se puede abrir otra
					ac.setVisible(true);
				}
			});
		}
		return btnAñadirNuevo;
	}

	public void vaciarCBCausas() {
		cbCausas.removeAllItems();
	}


	private JPanel getPnIzq() {
		if (pnIzq == null) {
			pnIzq = new JPanel();
			pnIzq.setLayout(new GridLayout(7, 1, 0, 0));
			pnIzq.add(getPnVacío());
			pnIzq.add(getLabel_4_2());
			pnIzq.add(getLabel_4_3());
			pnIzq.add(getLabel_4_4());
			pnIzq.add(getLabel_4_5());
			pnIzq.add(getLabel_4_6());
		}
		return pnIzq;
	}
	private JPanel getPnDcha() {
		if (pnDcha == null) {
			pnDcha = new JPanel();
			pnDcha.setLayout(new BorderLayout(0, 0));
			pnDcha.add(getPnResumenP());
			pnDcha.add(getPnBorrarPreinscripcion());
			pnDcha.add(getPnResumenP(), BorderLayout.CENTER);
			pnDcha.add(getPnBorrarPreinscripcion(), BorderLayout.SOUTH);
			pnDcha.add(getPnResumenPreinscripciones(), BorderLayout.NORTH);
		
		}
		return pnDcha;
	}
	private JPanel getPnCentro() {
		if (pnCentro == null) {
			pnCentro = new JPanel();
			pnCentro.setLayout(new GridLayout(7, 1, 0, 0));
			pnCentro.add(getBtnNueva());
			pnCentro.add(getCbNombre());
			pnCentro.add(getSpinnerCantidad());
			pnCentro.add(getSpinnerDuracion());
			pnCentro.add(getSpinnerIntervalo());
			pnCentro.add(getScrollPane());
			pnCentro.add(getBtnAadirPreinscripcin());
//			pnCentro.add(getTextArea());
		}
		return pnCentro;
	}
	private JLabel getLabel_4_2() {
		if (lblNombrePreinscripcion == null) {
			lblNombrePreinscripcion = new JLabel("Nombre:");
		}
		return lblNombrePreinscripcion;
	}
	private JButton getBtnNueva() {
		if (btnNueva == null) {
			btnNueva = new JButton("Crear nueva prescripci\u00F3n");
			btnNueva.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					crearPreinscripción();
				}
			});
		}
		return btnNueva;
	}
	
	/**
	 * Método para crear una nueva preinscripción que no había antes
	 */
	protected void crearPreinscripción() {
		AnadirPreinscripcion ventanaPreinscripcion = new AnadirPreinscripcion(this);
		
		ventanaPreinscripcion.setLocationRelativeTo(this);
		ventanaPreinscripcion.setResizable(true);
		ventanaPreinscripcion.setModal(true); // hasta que no se cierre una ventana no se puede abrir otra
		ventanaPreinscripcion.setVisible(true);
		
	}
	
	/**
	 * Método que me permite guardar la preinscripcion
	 * @param preinscripcion the preinscripcion to set
	 */
	public void setPreinscripcion(Preinscripcion preinscripcion) {
		this.preinscripcion = preinscripcion;
	}

	private JLabel getLabel_4_3() {
		if (lblNCantidad == null) {
			lblNCantidad = new JLabel("Cantidad:");
		}
		return lblNCantidad;
	}
	private JLabel getLabel_4_4() {
		if (lblDuracion == null) {
			lblDuracion = new JLabel("Duraci\u00F3n (en d\u00EDas):");
		}
		return lblDuracion;
	}
	private JLabel getLabel_4_5() {
		if (lblIntervalo == null) {
			lblIntervalo = new JLabel("Intervalo (en horas):");
		}
		return lblIntervalo;
	}
	private JLabel getLabel_4_6() {
		if (lblInstrucciones == null) {
			lblInstrucciones = new JLabel("Instrucciones:");
		}
		return lblInstrucciones;
	}
	private JComboBox getCbNombre() {
		if (cbNombre == null) {
			cbNombre = new JComboBox();
			cbNombre.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					cambiarEnabled();
				}

			});
			

			String[] nombrePreinscripciones = new String[preinscripciones.size()];
			for (int i = 0; i< preinscripciones.size(); i++) {
				nombrePreinscripciones[i] = preinscripciones.get(i).getNombre();
			}
			
			cbNombre.setModel(new DefaultComboBoxModel<String>(nombrePreinscripciones));				

			
		}
		return cbNombre;
	}
	private JPanel getPnVacío() {
		if (pnVacío == null) {
			pnVacío = new JPanel();
		}
		return pnVacío;
	}
	private JSpinner getSpinnerCantidad() {
		if (spinnerCantidad == null) {
			spinnerCantidad = new JSpinner();
			spinnerCantidad.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
			
			if(comprobarSiEsMedicamento()) { // Si la preinscripción es un medicamento
				if(!spinnerCantidad.isEnabled()) { // Si no estaba enabled
					spinnerCantidad.setEnabled(true);
				}
			}
			else if(comprobarSiEsMedicamento() == false) {
				if (spinnerCantidad.isEnabled()) {
					spinnerCantidad.setEnabled(false);
				}
			}
			
		}
		return spinnerCantidad;
	}


	private JSpinner getSpinnerDuracion() {
		if (spinnerDuracion == null) {
			spinnerDuracion = new JSpinner();
			spinnerDuracion.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
			
			if(comprobarSiEsMedicamento()) { // Si la preinscripción es un medicamento
				if(!spinnerDuracion.isEnabled()) { // Si no estaba enabled
					spinnerDuracion.setEnabled(true);
				}
			}
			else if(comprobarSiEsMedicamento() == false) {
				if(spinnerDuracion.isEnabled()) {
					spinnerDuracion.setEnabled(false);
				}
			}
		}
		return spinnerDuracion;
	}
	private JSpinner getSpinnerIntervalo() {
		if (spinnerIntervalo == null) {
			spinnerIntervalo = new JSpinner();
			spinnerIntervalo.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
			
			if(comprobarSiEsMedicamento()) { // Si la preinscripción es un medicamento
				if(!spinnerIntervalo.isEnabled()) { // Si no estaba enabled
					spinnerIntervalo.setEnabled(true);
				}
			}
			else if(comprobarSiEsMedicamento() == false) {
				if (spinnerIntervalo.isEnabled()) {
					spinnerIntervalo.setEnabled(false);	
				}
			}
		}
		return spinnerIntervalo;
	}
	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(getTextArea());
		}
		return scrollPane;
	}
	private JTextArea getTextArea() {
		if (textArea == null) {
			textArea = new JTextArea();
			textArea.setLineWrap(true);
			textArea.setWrapStyleWord(true);
		}
		return textArea;
	}
	private JPanel getPnResumenP() {
		if (pnResumenP == null) {
			pnResumenP = new JPanel();
			pnResumenP.setLayout(new GridLayout(0, 1, 0, 0));
			//pnResumen.add(getTable());
			pnResumenP.add(getScrollPTablaPrescripciones());
			//pnResumen.add(getList());
			//pnResumen.add(getTextArea_1());
		}
		return pnResumenP;
	}
	private JPanel getPnBorrarPreinscripcion() {
		if (pnBorrarPreinscripcion == null) {
			pnBorrarPreinscripcion = new JPanel();
			FlowLayout flowLayout = (FlowLayout) pnBorrarPreinscripcion.getLayout();
			flowLayout.setAlignment(FlowLayout.RIGHT);
			pnBorrarPreinscripcion.add(getBtnBorrarPrescripcion());
		}
		return pnBorrarPreinscripcion;
	}
	private JButton getBtnBorrarPrescripcion() {
		if (btnBorrarPrescripcion == null) {
			btnBorrarPrescripcion = new JButton("Borrar prescripci\u00F3n");
			btnBorrarPrescripcion.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int fila=tablePrescripciones.getSelectedRow();
					
					if (fila != -1) {
						int res = JOptionPane.showConfirmDialog(null, "¿Está seguro de que desea borrar la preinscripción?","Mensaje de confirmación",JOptionPane.YES_NO_OPTION);
						if(res == JOptionPane.YES_OPTION) {	
								
								preinscripcionesPaciente.remove(tablePrescripciones.getSelectedRow());
								asignaPreinscripcionesPaciente.remove(tablePrescripciones.getSelectedRow());
								
								añadirFilasPrescripciones();
						
						}						
					}		
				}
			});
		}
		return btnBorrarPrescripcion;
	}
	
	


	private JPanel getPnResumenPreinscripciones() {
		if (pnResumenPreinscripciones == null) {
			pnResumenPreinscripciones = new JPanel();
			FlowLayout flowLayout = (FlowLayout) pnResumenPreinscripciones.getLayout();
			flowLayout.setAlignment(FlowLayout.LEFT);
			pnResumenPreinscripciones.add(getLabel_4_7());
		}
		return pnResumenPreinscripciones;
	}
	private JLabel getLabel_4_7() {
		if (lblResumenPreinscripciones == null) {
			lblResumenPreinscripciones = new JLabel("Prescripciones seleccionadas:");
		}
		return lblResumenPreinscripciones;
	}

	private JList getList() {
		if (list == null) {
			list = new JList();			
		}
		return list;
	}
	private JButton getBtnAadirPreinscripcin() {
		if (btnAadirPreinscripcin == null) {
			btnAadirPreinscripcin = new JButton("A\u00F1adir preinscripci\u00F3n");
			btnAadirPreinscripcin.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					anadirPreinscripcionPaciente();

				}
			});
		}
		return btnAadirPreinscripcin;
	}

	/**
	 * Método para actualizar el elemento que se muestra en el comboBox de Preinscripciones
	 */
	public void actualizar() {
		
		try {
			preinscripciones = pbd.listarPrescripciones(); // Volvemos a cargar las preinscripciones de la base de datos después de haber añadido
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		listo = false;
		cbNombre.removeAllItems(); // Vacío todo el combo box
		
		// Cargo otra vez las preinscripciones en el cb
		String[] nombrePreinscripciones = new String[preinscripciones.size()];
		for (int i = 0; i< preinscripciones.size(); i++) {
			nombrePreinscripciones[i] = preinscripciones.get(i).getNombre();
		}
		
		cbNombre.setModel(new DefaultComboBoxModel<String>(nombrePreinscripciones));				

		listo = true;
		
		int contador = 0;
		for (Preinscripcion p : preinscripciones) {
			if (p.getNombre().equals(preinscripcion.getNombre())) {
				cbNombre.setSelectedIndex(contador);
				comprobarSiEsMedicamento(); // Comprobamos si es un medicamento para cambiar los enabled de los spinner
			}
			contador = contador + 1;
		}
	}	
		/**
		 * Método que me comprueba si la preinscripción que hay seleccionada en el comboBox es de tipo medicamento o no
		 */
	private boolean comprobarSiEsMedicamento() {
		
		int indiceSeleciconado = getCbNombre().getSelectedIndex();
		Preinscripcion p = null;
			

		int contador = 0;
		for (Preinscripcion pre : preinscripciones) {
			if (indiceSeleciconado == contador) {
				p = pre;
			}
			contador = contador + 1;
		}
		if(p != null) {
			if (p.isMedicamento()) { // Si era medicamento
				return true;
			}
		}
		return false; // Si no era medicamento
	}
	
	
	/**
	 * Método que para cuando cambio el comboBox me compruebe si tiene que cambiar el enabled de los spinner por ser/no un medicamento lo que
	 * tiene seleccionado
	 */
	private void cambiarEnabled() {
		if (listo) { // Para que no casque mientras está cambiando la ventana
			boolean isMedicamento = comprobarSiEsMedicamento(); // Compruebo si es un medicamento
			
			
			
			if(isMedicamento) { // Si la preinscripción es un medicamento
				if(!spinnerCantidad.isEnabled()) { // Si no estaba enabled
					spinnerCantidad.setEnabled(true);
				}
				if(!spinnerDuracion.isEnabled()) { 
					spinnerDuracion.setEnabled(true);
				}
				if(!spinnerIntervalo.isEnabled()) { 
					spinnerIntervalo.setEnabled(true);
				}
				
			}
			else if(isMedicamento == false) {
				if (spinnerCantidad.isEnabled()) {
					spinnerCantidad.setEnabled(false);
				}
				if (spinnerDuracion.isEnabled()) {
					spinnerDuracion.setEnabled(false);
				}
				if (spinnerIntervalo.isEnabled()) {
					spinnerIntervalo.setEnabled(false);
				}
			}			
		}
		
		
	}
	
	
	/**
	 * Método para añadir la preinscripcion al paciente
	 */
	private void anadirPreinscripcionPaciente() {
						
		int indiceSeleciconado = getCbNombre().getSelectedIndex(); // Lo que está seleccionado en el comboBox
		Preinscripcion p = null; // La preinscripcion
			

		// Buscamos la preinscripcion que hay seleccionada en el comboBox
		int contador = 0;
		for (Preinscripcion pre : preinscripciones) {
			if (indiceSeleciconado == contador) {
				p = pre;
			}
			contador = contador + 1;
		}
		
		preinscripcionesPaciente.add(p); // Añadimos la preinscripcion
		crearAsignaPreinscripcion(p);
				
		if (tablaPrescripcionesLista == false) { // Para que no casque al pintar la tabla de las preinscripciones
			tablaPrescripcionesLista = true;
		}
		
		añadirFilasPrescripciones(); // Añadimos a la tabla que nos muestra las preinscripciones que ya le hemos asignado
		
		limpiarPanelPrescripciones(); // Para ponerlo todo de 0
		
	}
	


	/**
	 * Método que me crea un asigna preinscripcion y me lo añade a la lista de preinscripciones que va a tener el paciente
	 * @param p
	 */
	private void crearAsignaPreinscripcion(Preinscripcion p) {
		
		int indiceSeleciconado = getCbNombre().getSelectedIndex();
			

		int contador = 0;
		for (Preinscripcion pre : preinscripciones) {
			if (indiceSeleciconado == contador) {
				preinscripcion = pre;
			}
			contador = contador + 1;
		}
		
		
		
		// El codigo de la preinscripcion
		Random r = new Random();
		String codAsignaPreinscripcion = "" + r.nextInt(800);
		
		// El código del historial del paciente
		String codigoHistorial = paciente.getHistorial();
		String codigoPreinscripcion = preinscripcion.getNombre();
		
		// El codigo del empleado (medico)
		String codempleado = codmedico;
		//String codempleado = "1a";
		
		
		// Si es medicamento o no
		boolean medicamento = preinscripcion.isMedicamento();

		// Las dosis (SOLO SI ES MEDICAMENTO, sino se queda a 0 por defecto)
		int cantidad = 0;
		int intervalo = 0;
		int duracion = 0;
		
		if (medicamento) {
			cantidad = (int) getSpinnerCantidad().getValue();
			duracion= (int) getSpinnerDuracion().getValue();
			intervalo = (int)getSpinnerIntervalo().getValue();	
		}
		
		// Las instrucciones para tomar la preinscripcion
		String instrucciones = getTextArea().getText();	

		// Fecha y hora actuales del sistema
		Date fecha = new Date();	
		Time hora = new Time(new Date().getTime());
		
		AsignaPreinscripcion ap = new AsignaPreinscripcion(codAsignaPreinscripcion, codigoHistorial, codempleado, codigoPreinscripcion, cantidad, 
									intervalo, duracion, instrucciones, fecha, hora);
		
		
		
		
		asignaPreinscripcionesPaciente.add(ap); // Añado la asignacion de la preinscripcion a una lista que tengo
	}

	
	private JTable getTablePrescripciones() {
		if (tablePrescripciones == null) {
			
			String[] nombreColumnas= {"Nombre","Medicamento","Cantidad","Duracion","Intervalo"};
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
			
			sorter.setSortKeys(sortKeys);
			
			añadirFilasPrescripciones();
		}
		return tablePrescripciones;
	}

	/**
	 * Método para añadir filas a la tabla de las preinscripciones
	 * @param b
	 */
	private void añadirFilasPrescripciones() {
		borrarModeloTablaPrescripciones(); // Borramos todo antes de volver a pintar
	
		Object[] nuevaFila=new Object[5]; // 5 son las columnas
				
		
		if (tablaPrescripcionesLista) {
			for (AsignaPreinscripcion a : asignaPreinscripcionesPaciente) {
				nuevaFila[0] = a.getCodigoPreinscripcion(); // El nombre de la preinscripcion
				
				boolean medicamento = false;
				for (Preinscripcion p : preinscripcionesPaciente) {
					if (p.getNombre().equals(a.getCodigoPreinscripcion())) { // Si es la preinscripcion
						
						if (p.isMedicamento()) { // Si la preinscripcion es un medicamento
							nuevaFila[1] = "Si";
							medicamento = true;
						}
						else if (!p.isMedicamento()) { // Si NO es un medicamento
							nuevaFila[1] = "No";
						}	 
					}
				}
				
				if (medicamento) { // Si es un medicamento le ponemos las demás características
					nuevaFila[2] = "" + a.getCantidad();
					nuevaFila[3] = "" + a.getDuracion();
					nuevaFila[4] = "" + a.getIntervalo();
				}
				else if (!medicamento){ // Si no es un medicamento
					nuevaFila[2] = "-";
					nuevaFila[3] = "-";
					nuevaFila[4] = "-";
				}
				
				
				modeloTablaPrescripciones.addRow(nuevaFila); // Añado la fila
				
			}
			
			
		}
		
	}

	
	/**
	 * Método para borrar todas las filas de tabla
	 */
	private void borrarModeloTablaPrescripciones() {
		int filas = modeloTablaPrescripciones.getRowCount();
		for (int i = 0; i < filas; i++) {
			modeloTablaPrescripciones.removeRow(0);			
		}		
	}
	
	
	
	/**
	 * Método que me guarda lo que he modificado de la cita
	 * @throws SQLException 
	 */
	private void guardar() throws SQLException {
		guardarPreinscripciones();
		guardarCausas();
		guardarVacunas();
		guardarDiagnosticos();
		guardarProcedimientos();
		guardarAntecedentes();
		guardarInfoCita(); // Método que me guarda si acabó y si cambio la hora, la guarda también
	}










	private JPanel getPnAcudio() {
		if (pnAcudio == null) {
			pnAcudio = new JPanel();
			pnAcudio.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Acudi\u00F3: ", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
			pnAcudio.setLayout(new GridLayout(3, 1, 0, 0));
			pnAcudio.add(getPanel_5());
			pnAcudio.add(getPanel_6());
			pnAcudio.add(getPanel_7());
		}
		return pnAcudio;
	}
	private JRadioButton getRdbtnAcudio() {
		if (rdbtnAcudio == null) {
			rdbtnAcudio = new JRadioButton("Acudio");
			rdbtnAcudio.setFont(new Font("Tahoma", Font.PLAIN, 13));
			rdbtnAcudio.setBounds(248, 5, 67, 25);
			buttonGroup.add(rdbtnAcudio);
		}
		return rdbtnAcudio;
	}
	private JRadioButton getRdbtnNoAcudio() {
		if (rdbtnNoAcudio == null) {
			rdbtnNoAcudio = new JRadioButton("No acudio");
			rdbtnNoAcudio.setBounds(376, 5, 85, 25);
			buttonGroup.add(rdbtnNoAcudio);
		}
		return rdbtnNoAcudio;
	}
	private Component getHorizontalStrut() {
		if (horizontalStrut == null) {
			horizontalStrut = Box.createHorizontalStrut(60);
			horizontalStrut.setBounds(319, 17, 60, 12);
		}
		return horizontalStrut;
	}
	

	
	private void guardarAccionCausa(String causas) throws SQLException {
		List<AccionEmpleado> devolverAccionesAdmin = pbd.devolverAccionesEmlpeado();
		int numeroAccion = 1;
		if(devolverAccionesAdmin.size()>0) {
			numeroAccion = devolverAccionesAdmin.size() + 1;
		}
		String naccion = "" +numeroAccion;
		
		String nombrePaciente = paciente.getNombre();
		String apellidoPaciente= paciente.getApellido();
		
		String codMed = codmedico;
		
		String nombre =pbd.devolverEmpleado(codMed).getNombre();
		String apellido =pbd.devolverEmpleado(codMed).getApellido();
		
		Date fecha = new Date();	
		Time hora = new Time(new Date().getTime());	
		
		String mensajeAccion = "El médico " + nombre + " " +apellido  + " ha asignado al paciente " + nombrePaciente + " " 
		+ apellidoPaciente + " la siguiente causa" + causas;
				
		AccionEmpleado a = new AccionEmpleado(naccion, codMed,  fecha, hora, mensajeAccion);
		pbd.guardarAccionEmpleado(a);
		
	} 



	private JScrollPane getScrollPTablaPrescripciones() {
		if (scrollPTablaPrescripciones == null) {
			scrollPTablaPrescripciones = new JScrollPane();
			
			scrollPTablaPrescripciones.setViewportView(getTablePrescripciones());
		}
		return scrollPTablaPrescripciones;
	}
	
	
	
	/**
	 * Método para añadir la vacuna temporalmente al paciente
	 */
	private void anadirVacuna() {
		int indice = cbVacunas.getSelectedIndex(); // el índice que hay seleccionado en el cb
		Vacuna vacuna = null;
		
		// Buscamos la vacuna que hay seleccionada en el cb
		int contador = 0;
		for(Vacuna v : vacunas) {
			if (indice == contador) {
				vacuna = v;
			}
			contador = contador + 1;
		}
		
		Random r = new Random();
		String codVacuna = "" + r.nextInt(99999);
		
		String nombreVacuna = vacuna.getNombreVacuna();
		String codHistorial = paciente.getHistorial();
		String codEmpleado = codmedico;		
		Date fecha = new Date();	
		Time hora = new Time(new Date().getTime());		
		
		
		AsignaVacuna av = new AsignaVacuna(codVacuna, nombreVacuna, codHistorial, codEmpleado, fecha, hora);
		
		asignaVacunasPaciente.add(av);
		
		
		if (tablaVacunasLista == false) { // Para que no casque al pintar la tabla de las vacunas
			tablaVacunasLista = true;
		}
		
		añadirFilasVacuna(); // Añadimos a la tabla que nos muestra las vacunas que ya le hemos asignado
		
		limpiarPanelVacunas(); // Para ponerlo todo de 0
	}
	
	


	/**
	 * Método para asignar definitivamente las vacunas al paciente
	 * @throws SQLException 
	 */
	private void guardarVacunas() throws SQLException {
		
		if (!asignaVacunasPaciente.isEmpty()) {
			// Que le hayamos asignado alguna vacuna
			guardarAccionVacunas();
			for (AsignaVacuna av : asignaVacunasPaciente) { // Voy guardando cada una de las vacunas que le he asignado
				pbd.nuevaAsignaVacuna(av);
			}
		}
	}
	
	private void guardarAccionVacunas() throws SQLException {
		List<AccionEmpleado> devolverAccionesAdmin = pbd.devolverAccionesEmlpeado();
		int numeroAccion = 1;
		if(devolverAccionesAdmin.size()>0) {
			numeroAccion = devolverAccionesAdmin.size() + 1;
		}
		String naccion = "" +numeroAccion;
		
		String nombrePaciente = paciente.getNombre();
		String apellidoPaciente= paciente.getApellido();
		String codMed = codmedico;
		
		String nombre =pbd.devolverEmpleado(codMed).getNombre();
		String apellido =pbd.devolverEmpleado(codMed).getApellido();
		
		Date fecha = new Date();	
		Time hora = new Time(new Date().getTime());	
		
		String mensajePreinscripciones = "";
		for(int i =0;i <asignaVacunasPaciente.size();i++ ) {
			mensajePreinscripciones += asignaVacunasPaciente.get(i).getCodVacuna() + ", ";
		}
		String mensajeAccion = "El médico " + nombre + " " +apellido  + " ha asignado al paciente " + nombrePaciente + " " 
		+ apellidoPaciente + " la siguiente vacuna" + mensajePreinscripciones;
				
		AccionEmpleado a = new AccionEmpleado(naccion, codMed,  fecha, hora, mensajeAccion);
		pbd.guardarAccionEmpleado(a);
		
	}
	/**
	 * Método para guardar las preinscripciones que se le han asignado a un paciente
	 * @throws SQLException 
	 */
	private void guardarPreinscripciones() throws SQLException {
		// Guardo las preinscripciones que le he asignado al paciente
		if (!asignaPreinscripcionesPaciente.isEmpty()) { // Que le hayamos asignado algo
			guardarAccionPreins();
			for (AsignaPreinscripcion ap : asignaPreinscripcionesPaciente) { // Voy guardando cada una de las preinscripciones que le he asignado
				pbd.nuevaAsignaPreinscripcion(ap);
			}
		}		
	}
	
	
	private void guardarAccionPreins() throws SQLException {
		List<AccionEmpleado> devolverAccionesAdmin = pbd.devolverAccionesEmlpeado();
		int numeroAccion = 1;
		if(devolverAccionesAdmin.size()>0) {
			numeroAccion = devolverAccionesAdmin.size() + 1;
		}
		String naccion = "" + numeroAccion;
		System.out.println("Numero acciones " + naccion);
		
		String nombrePaciente = paciente.getNombre();
		String apellidoPaciente= paciente.getApellido();
		String codMed = codmedico;
		
		String nombre =pbd.devolverEmpleado(codMed).getNombre();
		String apellido =pbd.devolverEmpleado(codMed).getApellido();
		
		Date fecha = new Date();	
		Time hora = new Time(new Date().getTime());	
		
		String mensajePreinscripciones = "";
		for(int i =0;i <asignaPreinscripcionesPaciente.size();i++ ) {
			mensajePreinscripciones += asignaPreinscripcionesPaciente.get(i).getCodigoPreinscripcion() + ", ";
		}
		String mensajeAccion = "El médico " + nombre + " " +apellido  + " ha asignado al paciente " + nombrePaciente + " " 
		+ apellidoPaciente + " la siguiente preinscripción" + mensajePreinscripciones;
				
		AccionEmpleado a = new AccionEmpleado(naccion, codMed,  fecha, hora, mensajeAccion);
		pbd.guardarAccionEmpleado(a);
		
	} 
	private JPanel getPnDiagnosticos() {
		if (pnDiagnosticos == null) {
			pnDiagnosticos = new JPanel();
			pnDiagnosticos.setLayout(new BorderLayout(0, 0));
			pnDiagnosticos.add(getPnCentroD(), BorderLayout.CENTER);
			pnDiagnosticos.add(getPnDchaD(), BorderLayout.EAST);
		
		}
		return pnDiagnosticos;
	}
	private JComboBox<String> getCbDiagnosticos() {
		if (cbDiagnosticos == null) {
			cbDiagnosticos = new JComboBox();
			cbDiagnosticos.setBounds(10, 32, 780, 21);
			
			String[] nombreDiagnosticos = new String[diagnosticos.size()];
			for (int i = 0; i < diagnosticos.size(); i++) {
				nombreDiagnosticos[i] = diagnosticos.get(i).getNombre();
			}
			
			cbDiagnosticos.setModel(new DefaultComboBoxModel<String>(nombreDiagnosticos));
		}
		return cbDiagnosticos;
	}
	private JButton getBtnDiagnosticar() {
		if (btnDiagnosticar == null) {
			btnDiagnosticar = new JButton("Diagnosticar");
			btnDiagnosticar.setBounds(648, 10, 144, 21);
			btnDiagnosticar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					comprobarEDO();
					anadirDiagnostico();
					restaurarCbDiagnosticos();
				}
			});
		}
		return btnDiagnosticar;
	}



	/**
	 * Método para asignar un diagnóstico nuevo a un paciente
	 */
	protected void anadirDiagnostico() {
		int indice = cbDiagnosticos.getSelectedIndex(); // el índice que hay seleccionado en el cb
		Diagnostico diagnostico = null;
		
		// Buscamos la vacuna que hay seleccionada en el cb
		int contador = 0;
		for(Diagnostico d : diagnosticos) {
			if (indice == contador) {
				diagnostico = d;
			}
			contador = contador + 1;
		}
		
		Random r = new Random();
		String codAsigDiagnostico = "" + r.nextInt(9999999); // El código es aleatorio
		
		String nombreDiagnostico = diagnostico.getNombre();
		String nHistorial = paciente.getHistorial(); // El número de historial del paciente a quien le hemos asignado el diagnostico
		String nDiagnostico = diagnostico.getNumeroDiagnostico(); // El identificador del diagnostico
		String codMedico = codmedico;
		Date fecha = new Date();	
		Time hora = new Time(new Date().getTime());
		AsignaDiagnostico ad = new AsignaDiagnostico(codAsigDiagnostico, nombreDiagnostico, nDiagnostico, nHistorial, codMedico, fecha, hora);

		asignaDiagnosticosPaciente.add(ad);
		
		
		
		
		if (tablaDiagnosticosLista == false) { // Para que no casque al pintar la tabla de los diagnosticos
			tablaDiagnosticosLista = true;
		}
		
		añadirFilasDiagnostico(); // Añadimos a la tabla que nos muestra los diagnosticos que ya le hemos asignado
		
		limpiarPanelDiagnosticos(); // Para ponerlo todo de 0
		
	}
	
	
	
	


	/**
	 * Método para guardar definitivamente todos los diagnósticos que se le han asignado al paciente
	 * @throws SQLException 
	 */
	private void guardarDiagnosticos() throws SQLException {
		if (!asignaDiagnosticosPaciente.isEmpty()) { // Que le hayamos asignado algun diagnostico
			guardarAccionDiagnosticos();
			for (AsignaDiagnostico ad : asignaDiagnosticosPaciente) { // Voy guardando cada uno de los diagnosticos
				pbd.nuevaAsignaDiagnostico(ad);
			}
		}	
	}
	private void guardarAccionDiagnosticos() throws SQLException {
		List<AccionEmpleado> devolverAccionesAdmin = pbd.devolverAccionesEmlpeado();
		int numeroAccion = 1;
		if(devolverAccionesAdmin.size()>0) {
			numeroAccion = devolverAccionesAdmin.size() + 1;
		}
		String naccion = "" + numeroAccion;
		
		String nombrePaciente = paciente.getNombre();
		String apellidoPaciente= paciente.getApellido();
		String codMed = codmedico;
		
		String nombre =pbd.devolverEmpleado(codMed).getNombre();
		String apellido =pbd.devolverEmpleado(codMed).getApellido();
		
		Date fecha = new Date();	
		Time hora = new Time(new Date().getTime());	
		
		String mensajePreinscripciones = "";
		for(int i =0;i <asignaDiagnosticosPaciente.size();i++ ) {
			mensajePreinscripciones += asignaDiagnosticosPaciente.get(i).getnHistorial() + ", ";
		}
		String mensajeAccion = "El médico " + nombre + " " +apellido  + " ha asignado al paciente " + nombrePaciente + " " 
		+ apellidoPaciente + " la siguiente preinscripción" + mensajePreinscripciones;
				
		AccionEmpleado a = new AccionEmpleado(naccion, codMed,  fecha, hora, mensajeAccion);
		pbd.guardarAccionEmpleado(a);
		
	}
	public boolean isCausaSeleccionada() {
		return causaSeleccionada;
	}

	public void setCausaSeleccionada(boolean causaSeleccionada) {
		this.causaSeleccionada = causaSeleccionada;
	}
	
	
	/**
	 * Método para restaurar todos los valores por defecto de la preinscripcion cuando añado una
	 */
	private void limpiarPanelPrescripciones() {
		
		cbNombre.setSelectedIndex(0);
		spinnerCantidad.setValue(1);
		spinnerDuracion.setValue(1);
		spinnerIntervalo.setValue(0);
		textArea.setText("");
	}
	
	/**
	 * 	Método para poner el valor del cb en su valor de inicio
	 */
	protected void restaurarCb() {
		cbVacunas.setSelectedIndex(0);
		
	}
	
	
	/**
	 * Método para poner el valor del cb en su valor de inicio
	 */
	protected void restaurarCbDiagnosticos() {
		cbDiagnosticos.setSelectedIndex(0);	
		txtFiltrarDiagnosticos.setText("");
		if (btnFiltrarDiagnosticos.isEnabled()) {
			btnFiltrarDiagnosticos.setEnabled(false);
		}
		
		if(chckbxEdo.isSelected()) {
			chckbxEdo.setSelected(false);
		}
	}
	private JPanel getPnHoraInicio() {
		if (pnHoraInicio == null) {
			pnHoraInicio = new JPanel();
			pnHoraInicio.setLayout(new GridLayout(0, 2, 0, 0));
			pnHoraInicio.add(getPnVacio2());
			pnHoraInicio.add(getSpinnerHInicioR());
		}
		return pnHoraInicio;
	}
	private JPanel getPnVacio() {
		if (pnVacio == null) {
			pnVacio = new JPanel();
		}
		return pnVacio;
	}
	private JPanel getPnVacio2() {
		if (pnVacio2 == null) {
			pnVacio2 = new JPanel();
		}
		return pnVacio2;
	}
	private JSpinner getSpinnerHInicioR() {
		if (spinnerHInicioR == null) {
			spinnerHInicioR = new JSpinner(new SpinnerDateModel());
						
			spinnerHInicioR.setFont(new Font("Tahoma", Font.PLAIN, 13));
			
			JSpinner.DateEditor de_timeSpinnerInicio = new JSpinner.DateEditor(spinnerHInicioR, "HH:mm");
			spinnerHInicioR.setEditor(de_timeSpinnerInicio);
			
			spinnerHInicioR.setValue(cita.gethInicio()); // Le ponemos la hora que tenía asignada del administrador
			
			return spinnerHInicioR;			
		}
		return spinnerHInicioR;
	}
	private JPanel getPnHoraInicioF() {
		if (pnHoraInicioF == null) {
			pnHoraInicioF = new JPanel();
			pnHoraInicioF.setLayout(new GridLayout(1, 0, 0, 0));
			pnHoraInicioF.add(getPnModificarHInicio());
			pnHoraInicioF.add(getSpinnerHInicioNueva());
		}
		return pnHoraInicioF;
	}
	private JPanel getPanel_7_1() {
		if (pnGuardarHInicio == null) {
			pnGuardarHInicio = new JPanel();
			pnGuardarHInicio.setLayout(new GridLayout(0, 2, 0, 0));
			pnGuardarHInicio.add(getBtnGuardarInicio());
		}
		return pnGuardarHInicio;
	}
	private JPanel getPnModificarHInicio() {
		if (pnModificarHInicio == null) {
			pnModificarHInicio = new JPanel();
			pnModificarHInicio.add(getBtnModificar());
		}
		return pnModificarHInicio;
	}
	private JButton getBtnModificar() {
		if (btnModificar == null) {
			btnModificar = new JButton("Modificar");
			btnModificar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					activarModificarHoraInicio();
				}
			});
		}
		return btnModificar;
	}
	
	
	/**
	 * Método que me activa para cambiar la hora de inicio
	 */
	private void activarModificarHoraInicio() {
		if (!spinnerHInicioNueva.isEnabled()) { // Primero comprobamos que no está ya enabled
			spinnerHInicioNueva.setEnabled(true);
		}	
		if (btnModificar.isEnabled()) {
			btnModificar.setEnabled(false);
		}
	}
	
	
	
	private JSpinner getSpinnerHInicioNueva() {
		if (spinnerHInicioNueva == null) {
			spinnerHInicioNueva = new JSpinner(new SpinnerDateModel());
			spinnerHInicioNueva.setEnabled(false);
			spinnerHInicioNueva.setFont(new Font("Tahoma", Font.PLAIN, 13));
			
			JSpinner.DateEditor de_timeSpinnerInicio = new JSpinner.DateEditor(spinnerHInicioNueva, "HH:mm");
			spinnerHInicioNueva.setEditor(de_timeSpinnerInicio);
			
			// La hora y fecha actuales
			Date fecha = new Date();	
			Time hora = new Time(new Date().getTime());
			
			spinnerHInicioNueva.setValue(hora); // Le ponemos la hora actual del sistema
			
			return spinnerHInicioNueva;
		}
		return spinnerHInicioNueva;
	}
	private JButton getBtnGuardarInicio() {
		if (btnGuardarInicio == null) {
			btnGuardarInicio = new JButton("Guardar");
			btnGuardarInicio.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					guardarHoraInicioCita();
				}
			});
		}
		return btnGuardarInicio;
	}
	private JPanel getPnHoraFin() {
		if (pnHoraFin == null) {
			pnHoraFin = new JPanel();
			pnHoraFin.setLayout(new GridLayout(0, 2, 0, 0));
			pnHoraFin.add(getPnVacio6());
			pnHoraFin.add(getSpinnerHFinR());
		}
		return pnHoraFin;
	}
	private JPanel getPnVacio6() {
		if (pnVacio6 == null) {
			pnVacio6 = new JPanel();
		}
		return pnVacio6;
	}
	private JSpinner getSpinnerHFinR() {
		if (spinnerHFinR == null) {
			spinnerHFinR = new JSpinner(new SpinnerDateModel());
					
			spinnerHFinR.setFont(new Font("Tahoma", Font.PLAIN, 13));
			
			JSpinner.DateEditor de_timeSpinnerInicio = new JSpinner.DateEditor(spinnerHFinR, "HH:mm");
			spinnerHFinR.setEditor(de_timeSpinnerInicio);
			
			spinnerHFinR.setValue(cita.gethFin()); // Le ponemos la hora que tenía asignada del administrador
			
			return spinnerHFinR;
			
		}
		return spinnerHFinR;
	}
	private JPanel getPnVacio7() {
		if (pnVacio7 == null) {
			pnVacio7 = new JPanel();
		}
		return pnVacio7;
	}
	private JPanel getPnHoraFinF() {
		if (pnHoraFinF == null) {
			pnHoraFinF = new JPanel();
			pnHoraFinF.setLayout(new GridLayout(1, 0, 0, 0));
			pnHoraFinF.add(getPnModificarHoraFinF());
			pnHoraFinF.add(getSpinnerHFinNueva());
		}
		return pnHoraFinF;
	}
	private JPanel getPnModificarHoraFinF() {
		if (pnModificarHoraFinF == null) {
			pnModificarHoraFinF = new JPanel();
			pnModificarHoraFinF.add(getBtnModificarFin());
		}
		return pnModificarHoraFinF;
	}
	private JButton getBtnModificarFin() {
		if (btnModificarFin == null) {
			btnModificarFin = new JButton("Modificar");
			btnModificarFin.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					activarModificarHoraFin();
				}
			});
		}
		return btnModificarFin;
	}
	
	
	/**
	 * Método para activar que pueda cambiar la hora de fin
	 */
	protected void activarModificarHoraFin() {
		
		if (!spinnerHFinNueva.isEnabled()) { // Si no estaba anteriormente enabled
			spinnerHFinNueva.setEnabled(true);
		}
		
		if (btnModificarFin.isEnabled()) {
			btnModificarFin.setEnabled(false);
		}
	}

	private JSpinner getSpinnerHFinNueva() {
		if (spinnerHFinNueva == null) {
			spinnerHFinNueva = new JSpinner(new SpinnerDateModel());
			spinnerHFinNueva.setEnabled(false);
			spinnerHFinNueva.setFont(new Font("Tahoma", Font.PLAIN, 13));
			
			JSpinner.DateEditor de_timeSpinnerInicio = new JSpinner.DateEditor(spinnerHFinNueva, "HH:mm");
			spinnerHFinNueva.setEditor(de_timeSpinnerInicio);
			
			// La hora y fecha actuales
			Date fecha = new Date();	
			Time hora = new Time(new Date().getTime());
			
			spinnerHFinNueva.setValue(hora); // Le ponemos la hora actual del sistema
			
			return spinnerHFinNueva;
		}
		return spinnerHFinNueva;
	}
	private JPanel getPnGuardarHFin() {
		if (pnGuardarHFin == null) {
			pnGuardarHFin = new JPanel();
			pnGuardarHFin.setLayout(new GridLayout(0, 2, 0, 0));
			pnGuardarHFin.add(getBtnGuardarFin());
		}
		return pnGuardarHFin;
	}
	private JButton getBtnGuardarFin() {
		if (btnGuardarFin == null) {
			btnGuardarFin = new JButton("Guardar");
			btnGuardarFin.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					guardarHoraFinCita();
				}
			});
		}
		return btnGuardarFin;
	}
	
	

	/**
	 * Método que me guarda localmente la hora de inicio de la cita que ha querido poner el médico
	 */
	protected void guardarHoraInicioCita() {
		if (spinnerHInicioNueva.isEnabled()) { // Si le ha dado a modificar la hora
			Date dateInicio = (Date) spinnerHInicioNueva.getValue();
			Time horaDeInicio = new Time(dateInicio.getTime());
			
			horaInicioCita = horaDeInicio;
		}
		
	}
	
	/**
	 * Método que me guarda localmente la hora de fin de la cita que ha querido poner el médico
	 */
	protected void guardarHoraFinCita() {
		if (spinnerHFinNueva.isEnabled()) { // Si le ha dado a modificar la hora
			Date dateInicio = (Date) spinnerHFinNueva.getValue();
			Time horaDeInicio = new Time(dateInicio.getTime());
			
			horaFinCita = horaDeInicio;
		}
		
	}

	
	/**
	 * Método que me guarda las información de la cita si se ha actualizado (si acudió o no y la hora de entrada y salida si la hubiese
	 * @throws SQLException 
	 */
	private void guardarInfoCita() throws SQLException {
		boolean acudio = false;
		boolean noAcudio = false;
		if(rdbtnAcudio.isSelected()) {
			acudio = true;
		}
		if(rdbtnNoAcudio.isSelected()) {
			noAcudio = true;
		}
		
		// Guardamos en la base de datos
		pbd.actualizarCita(horaInicioCita, horaFinCita, acudio, noAcudio, cita.getCodCita());
		
	}

	private JLabel getLabel_1() {
		if (label_1 == null) {
			label_1 = new JLabel("Apellidos:");
			label_1.setBounds(315, 21, 68, 26);
		}
		return label_1;
	}
	private JTextField getTxtApellido() {
		if (txtApellido == null) {
			txtApellido = new JTextField();
			txtApellido.setText((String) null);
			txtApellido.setEditable(false);
			txtApellido.setColumns(10);
			txtApellido.setBounds(381, 13, 203, 41);
			txtApellido.setText(paciente.getApellido());
		}
		return txtApellido;
	}
	private JButton getBtnMostrarHistorial() {
		if (btnMostrarHistorial == null) {
			btnMostrarHistorial = new JButton("Mostrar historial");
			btnMostrarHistorial.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						mostrarHistorial();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
		}
		return btnMostrarHistorial;
	}
	
	private void mostrarHistorial() throws SQLException {
		HistorialMedico hm = pbd.HistorialCita(cita.getCodCita(),paciente.getCodePaciente());
		MostrarHistorial mh = new MostrarHistorial(hm, codmedico);
		mh.setLocationRelativeTo(null);
		mh.setResizable(true);
		mh.setModal(true); // hasta que no se cierre una ventana no se puede abrir otra
		mh.setVisible(true);
	}
	private JPanel getPnVacio11() {
		if (pnVacio11 == null) {
			pnVacio11 = new JPanel();
		}
		return pnVacio11;
	}
	private JPanel getPanel_5_1() {
		if (pnVacio8 == null) {
			pnVacio8 = new JPanel();
		}
		return pnVacio8;
	}
	private JPanel getPanel_5_2() {
		if (pnMostrarHistorialVacunas == null) {
			pnMostrarHistorialVacunas = new JPanel();
			pnMostrarHistorialVacunas.setLayout(new GridLayout(3, 1, 0, 0));
			pnMostrarHistorialVacunas.add(getPanel_5_3());
			pnMostrarHistorialVacunas.add(getPanel_5_4());
			pnMostrarHistorialVacunas.add(getPanel_5_5());
		}
		return pnMostrarHistorialVacunas;
	}
	private JPanel getPanel_5_3() {
		if (pnVacio9 == null) {
			pnVacio9 = new JPanel();
		}
		return pnVacio9;
	}
	private JPanel getPanel_5_4() {
		if (pnMostrar == null) {
			pnMostrar = new JPanel();
			pnMostrar.add(getBtnMostrarHistorial());
			pnMostrar.add(getBtnCalendarioVacunas());
		}
		return pnMostrar;
	}
	private JPanel getPanel_5_5() {
		if (pnVacio10 == null) {
			pnVacio10 = new JPanel();
		}
		return pnVacio10;
	}
	private JPanel getPanel_5() {
		if (pnVacio12 == null) {
			pnVacio12 = new JPanel();
		}
		return pnVacio12;
	}
	private JPanel getPanel_6() {
		if (pnSeleccionAcudio == null) {
			pnSeleccionAcudio = new JPanel();
			pnSeleccionAcudio.setLayout(null);
			pnSeleccionAcudio.add(getRdbtnAcudio());
			pnSeleccionAcudio.add(getHorizontalStrut());
			pnSeleccionAcudio.add(getRdbtnNoAcudio());
		}
		return pnSeleccionAcudio;
	}
	private JPanel getPanel_7() {
		if (pnVacio13 == null) {
			pnVacio13 = new JPanel();
		}
		return pnVacio13;
	}
	private JTextField getTxtFiltrarDiagnosticos() {
		if (txtFiltrarDiagnosticos == null) {
			txtFiltrarDiagnosticos = new JTextField();
			txtFiltrarDiagnosticos.setBounds(135, 6, 200, 28);
			txtFiltrarDiagnosticos.setToolTipText("Introduzca el nombre del diagn\u00F3stico para buscarlo");
			txtFiltrarDiagnosticos.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					activarBotonFiltrarDiagnosticos();
				}
			});
			txtFiltrarDiagnosticos.setColumns(10);
		}
		return txtFiltrarDiagnosticos;
	}

	private JButton getBtnFiltrarDiagnosticos() {
		if (btnFiltrarDiagnosticos == null) {
			btnFiltrarDiagnosticos = new JButton("Buscar");
			btnFiltrarDiagnosticos.setBounds(352, 9, 76, 21);
			btnFiltrarDiagnosticos.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					buscarDiagnostico();
				}
			});
			btnFiltrarDiagnosticos.setEnabled(false);
		}
		return btnFiltrarDiagnosticos;
	}
	
	


	/**
	 * Método para activar el buscador de los diagnosticos
	 */
	protected void activarBotonFiltrarDiagnosticos() {
		if (!btnFiltrarDiagnosticos.isEnabled()) { // Si no estaba ya activado
			btnFiltrarDiagnosticos.setEnabled(true);
		}
		
	}
	private JButton getBtnCalendarioVacunas() {
		if (btnCalendarioVacunas == null) {
			btnCalendarioVacunas = new JButton("Calendario vacunas");
			btnCalendarioVacunas.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					verCalendarioVacunas();
				}
			});
		}
		return btnCalendarioVacunas;
	}

	/**
	 *Método para ver el calendario de vacunas del paciente
	 */
	protected void verCalendarioVacunas() {
		CalendarioVacunas cv = new CalendarioVacunas(paciente);
		cv.setLocationRelativeTo(null);
		cv.setResizable(true);
		cv.setModal(true); // hasta que no se cierre una ventana no se puede abrir otra
		cv.setVisible(true);	
	}
	
	
	/**
	 * Método que me busca si hay el diagnostico que quiere el médico. 
	 * 		- Si lo hay, lo pone en el comboBox automáticamente
	 * 		- Si no lo hay, muestra un mensaje al usuario diciendole que no existe
	 */
	protected void buscarDiagnostico() {
		if (!getTxtFiltrarDiagnosticos().getText().equals("")) { // Si hay algo escrito en el campo de texto
			String buscador = getTxtFiltrarDiagnosticos().getText().toLowerCase(); // Lo que ha buscado (lo pasamos a minuscula)
			boolean encontrado = false; // Para saber si encontró o no el diagnóstico buscado
			
			for(int i = 0; i < diagnosticos.size(); i++) {
				if (diagnosticos.get(i).getNombre().toLowerCase().equals(buscador)) { // Si lo que está buscando lo hay en la lista de diagnosticos
					cbDiagnosticos.setSelectedIndex(i); // Lo mostramos en el cb
					encontrado = true; // lo encontró
				}
			}
			
			if (!encontrado) { // Si no encontró el diagnostico
				JOptionPane.showMessageDialog(null, "No hemos podido encontrar su diagnóstico en este momento");
			}
		}
		else {
			JOptionPane.showMessageDialog(null, "No ha introducido nada en el buscador");		
		}
		
	}
	private void guardarAccionHist() throws SQLException {
		List<AccionEmpleado> devolverAccionesAdmin = pbd.devolverAccionesEmlpeado();
		int numeroAccion = 1;
		if(devolverAccionesAdmin.size()>0) {
			numeroAccion = devolverAccionesAdmin.size() + 1;
		}
		String naccion = "" +numeroAccion;
		//String naccion = "" + (numeroAcciones.size() + 1);
		System.out.println("Numero acciones " + naccion);
		
		String nombrePaciente = paciente.getNombre();
		String apellidoPaciente= paciente.getApellido();
		String codMed = codmedico;
		
		String nombre =pbd.devolverEmpleado(codMed).getNombre();
		String apellido =pbd.devolverEmpleado(codMed).getApellido();
		
		Date fecha = new Date();	
		Time hora = new Time(new Date().getTime());	
		
		
		String mensajeAccion = "El médico " + nombre + " " +apellido  + " ha visto el historial del paciente " + nombrePaciente + " " + apellidoPaciente;
		
		AccionEmpleado a = new AccionEmpleado(naccion, codMed,  fecha, hora, mensajeAccion);
		
		pbd.guardarAccionEmpleado(a);
		
	}
	private void filtrarPorNombreCausas(String name) {
		for(int i=0; i < nombresCausas.size(); i++) {
			if(nombresCausas.get(i).equals(name)) {
				cbCausas.setSelectedIndex(i);
			}
		}
	}
	
	
	
	private JPanel getPanel_4() {
		if (panel_4 == null) {
			panel_4 = new JPanel();
			panel_4.setLayout(null);
			panel_4.add(getLblNewLabel());
			panel_4.add(getTxtNombreCausa());
			panel_4.add(getBtnFiltrar());
		}
		return panel_4;
	}
	
	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("Filtrar causa");
			lblNewLabel.setBounds(11, 26, 77, 24);
		}
		return lblNewLabel;
	}
	private JTextField getTxtNombreCausa() {
		if (txtNombreCausa == null) {
			txtNombreCausa = new JTextField();
			txtNombreCausa.setBounds(98, 24, 197, 29);
			txtNombreCausa.setColumns(10);
		}
		return txtNombreCausa;
	}
	private JButton getBtnFiltrar() {
		if (btnFiltrar == null) {
			btnFiltrar = new JButton("Filtrar");
			btnFiltrar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(txtNombreCausa.getText().equals(""))
						JOptionPane.showMessageDialog(null, "Por favor introduce un valor");
					else {	
						filtrarPorNombre(txtNombreCausa.getText());
				}
				}
			});
			btnFiltrar.setBounds(316, 27, 89, 23);
		}
		return btnFiltrar;
	}

	private JPanel getPnProcedimientos() {
		if (pnProcedimientos == null) {
			pnProcedimientos = new JPanel();
			pnProcedimientos.setLayout(new BorderLayout(0, 0));
			pnProcedimientos.add(getPanelCentroPr(), BorderLayout.CENTER);
			pnProcedimientos.add(getPnDchaPr(), BorderLayout.EAST);
		}
		return pnProcedimientos;
	}
	private JTextField getTxtfFiltrarProcedimientos() {
		if (txtfFiltrarProcedimientos == null) {
			txtfFiltrarProcedimientos = new JTextField();
			txtfFiltrarProcedimientos.setBounds(134, 6, 202, 28);
			txtfFiltrarProcedimientos.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					activarBotonFiltrarProcedimientos();
				}
			});
			txtfFiltrarProcedimientos.setColumns(10);
		}
		return txtfFiltrarProcedimientos;
	}


	private JButton getBtnFiltrarProcedimientos() {
		if (btnFiltrarProcedimientos == null) {
			btnFiltrarProcedimientos = new JButton("Buscar");
			btnFiltrarProcedimientos.setBounds(346, 9, 83, 21);
			btnFiltrarProcedimientos.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					buscarProcedimiento();
				}
			});
			btnFiltrarProcedimientos.setEnabled(false);
		}
		return btnFiltrarProcedimientos;
	}
	private JComboBox<String> getCbProcedimientos() {
		if (cbProcedimientos == null) {
			cbProcedimientos = new JComboBox();
			cbProcedimientos.setBounds(10, 32, 779, 21);
			
			
			String[] nombreProcedimientos = new String[procedimientos.size()];
			for (int i = 0; i < procedimientos.size(); i++) {
				nombreProcedimientos[i] = procedimientos.get(i).getNombre();
			}
			
			cbProcedimientos.setModel(new DefaultComboBoxModel<String>(nombreProcedimientos));
			
		}
		return cbProcedimientos;
	}
	private JButton getBtnProceder() {
		if (btnProceder == null) {
			btnProceder = new JButton("Asignar procedimiento");
			btnProceder.setBounds(598, 10, 192, 21);
			btnProceder.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					anadirProcedimiento();
					restaurarCbProcedimientos();
				}
			});
		}
		return btnProceder;
	}
	
	
	/**
	 * Botón que me activa el botón de filtrar los procedimientos
	 */
	protected void activarBotonFiltrarProcedimientos() {
		if (!btnFiltrarProcedimientos.isEnabled()) { // Si no estaba activado lo activamos
			btnFiltrarProcedimientos.setEnabled(true);
		}
	}
	
	
	
	/**
	 * Botón para filtrar los procedimientos
	 */
	protected void buscarProcedimiento() {
		if (!getTxtfFiltrarProcedimientos().getText().equals("")) { // Si hay algo escrito en el campo de texto
			String buscador = getTxtfFiltrarProcedimientos().getText().toLowerCase(); // Lo que ha buscado (lo pasamos a minuscula)
			boolean encontrado = false; // Para saber si encontró o no el procedimiento buscado
			
			for(int i = 0; i < procedimientos.size(); i++) {
				if (procedimientos.get(i).getNombre().toLowerCase().equals(buscador)) { // Si lo que está buscando lo hay en la lista de procedimientos
					cbProcedimientos.setSelectedIndex(i); // Lo mostramos en el cb
					encontrado = true; // lo encontró
				}
			}
			
			if (!encontrado) { // Si no encontró el diagnostico
				JOptionPane.showMessageDialog(null, "No hemos podido encontrar su procedimiento en este momento");
			}
		}
		else {
			JOptionPane.showMessageDialog(null, "No ha introducido nada en el buscador");		
		}
		
	}
	
	
	
	/**
	 * Método para añadir un procedimiento que se ha seleccionado
	 */
	protected void anadirProcedimiento() {
		int indice = cbProcedimientos.getSelectedIndex(); // el índice que hay seleccionado en el cb
		Procedimiento procedimiento = null;
		
		// Buscamos la vacuna que hay seleccionada en el cb
		int contador = 0;
		for(Procedimiento p : procedimientos) {
			if (indice == contador) {
				procedimiento = p;
			}
			contador = contador + 1;
		}
		
		Random r = new Random();
		String codAsigProcedimiento = "" + r.nextInt(99999999); // El código es aleatorio
		
		String nombreProcedimiento = procedimiento.getNombre();
		String nProcedimiento = procedimiento.getNumero(); // El identificador del procedimiento
		String nHistorial = paciente.getHistorial(); // El número de historial del paciente a quien le hemos asignado el procedimiento
		String codMedico = codmedico;
		Date fecha = new Date();	
		Time hora = new Time(new Date().getTime());
		AsignaProcedimiento ad = new AsignaProcedimiento(codAsigProcedimiento, nombreProcedimiento, nProcedimiento, nHistorial, codMedico, fecha, hora);

		asignaProcedimientosPaciente.add(ad);
		
		
		
		if (tablaProcedimientosLista == false) { // Para que no casque al pintar la tabla de los procedimientos
			tablaProcedimientosLista = true;
		}
		
		añadirFilasProcedimiento(); // Añadimos a la tabla que nos muestra los procedimientos que ya le hemos asignado
		
		limpiarPanelProcedimientos(); // Para ponerlo todo de 0
		
	}
	
	
	


	/**
	 * Método para volver a poner como nuevo el cb de los procedimientos
	 */
	protected void restaurarCbProcedimientos() {
		cbProcedimientos.setSelectedIndex(0);	
		txtfFiltrarProcedimientos.setText("");
		if(btnFiltrarProcedimientos.isEnabled()) {
			btnFiltrarProcedimientos.setEnabled(false);
		}
	}
	
	
	
	/**
	 * Método que me guarda definitivamente los procedimientos que se le han querido asignar al paciente
	 * @throws SQLException 
	 */
	private void guardarProcedimientos() throws SQLException {
		if (!asignaProcedimientosPaciente.isEmpty()) { // Que le hayamos asignado algun procedimiento
			guardarAccionProdecimientos();
			for (AsignaProcedimiento ap : asignaProcedimientosPaciente) { // Voy guardando cada uno de los procedimientos
				pbd.nuevoAsignaProcedimiento(ap);
			}
		}		
	}
	
	private void guardarAccionProdecimientos() throws SQLException {
		List<AccionEmpleado> devolverAccionesAdmin = pbd.devolverAccionesEmlpeado();
		int numeroAccion = 1;
		if(devolverAccionesAdmin.size()>0) {
			numeroAccion = devolverAccionesAdmin.size() + 1;
		}
		String naccion = "" +numeroAccion;
		
		String nombrePaciente = paciente.getNombre();
		String apellidoPaciente= paciente.getApellido();
		String codMed = codmedico;
		
		String nombre =pbd.devolverEmpleado(codMed).getNombre();
		String apellido =pbd.devolverEmpleado(codMed).getApellido();
		
		Date fecha = new Date();	
		Time hora = new Time(new Date().getTime());	
		
		String mensajePreinscripciones = "";
		for(int i =0;i <asignaProcedimientosPaciente.size();i++ ) {
			mensajePreinscripciones += asignaProcedimientosPaciente.get(i).getNombreProcedimiento() + ", ";
		}
		String mensajeAccion = "El médico " + nombre + " " +apellido  + " ha asignado al paciente " + nombrePaciente + " " 
		+ apellidoPaciente + " el siguiente prodecimiento" + mensajePreinscripciones;
				
		AccionEmpleado a = new AccionEmpleado(naccion, codMed,  fecha, hora, mensajeAccion);
		pbd.guardarAccionEmpleado(a);
		
	}

	private JPanel getPnAntecedentes() {
		if (pnAntecedentes == null) {
			pnAntecedentes = new JPanel();
			pnAntecedentes.setLayout(new BorderLayout(0, 0));
			pnAntecedentes.add(getPanel_17(), BorderLayout.CENTER);
			pnAntecedentes.add(getPanel_18(), BorderLayout.EAST);
		}
		return pnAntecedentes;
	}
	private JPanel getPanel_1_3() {
		if (panel_1 == null) {
			panel_1 = new JPanel();
		}
		return panel_1;
	}
	private JPanel getPanel_3_2() {
		if (panel_3 == null) {
			panel_3 = new JPanel();
		}
		return panel_3;
	}
	private JPanel getPanel_5_6() {
		if (panel_5 == null) {
			panel_5 = new JPanel();
		}
		return panel_5;
	}
	private JButton getBtnNewButton_1() {
		if (btnNewButton_1 == null) {
			btnNewButton_1 = new JButton("New button");
		}
		return btnNewButton_1;
	}
	private JPanel getPanel_17() {
		if (pnCentroA == null) {
			pnCentroA = new JPanel();
			pnCentroA.setLayout(new BorderLayout(0, 0));
			pnCentroA.add(getPnAccionesAntecedentes(), BorderLayout.CENTER);
		}
		return pnCentroA;
	}
	private JPanel getPanel_18() {
		if (pnDchaA == null) {
			pnDchaA = new JPanel();
			pnDchaA.setLayout(new BorderLayout(0, 0));
			pnDchaA.add(getPnResumenA(), BorderLayout.CENTER);
			pnDchaA.add(getPnBorrarAntecedente(), BorderLayout.SOUTH);
			pnDchaA.add(getPnResumenAntecedentes(), BorderLayout.NORTH);
		}
		return pnDchaA;
	}
	private JPanel getPnResumenA() {
		if (pnResumenA == null) {
			pnResumenA = new JPanel();
			pnResumenA.setLayout(new GridLayout(0, 1, 0, 0));
			pnResumenA.add(getScrollPTablaAntecedentes());
			//pnResumenA.add(getTableAntecedentes());
		}
		return pnResumenA;
	}
	private JPanel getPnBorrarAntecedente() {
		if (pnBorrarAntecedente == null) {
			pnBorrarAntecedente = new JPanel();
			FlowLayout flowLayout = (FlowLayout) pnBorrarAntecedente.getLayout();
			flowLayout.setAlignment(FlowLayout.RIGHT);
			pnBorrarAntecedente.add(getBtnBorrarAntecedente());
		}
		return pnBorrarAntecedente;
	}
	private JPanel getPnResumenAntecedentes() {
		if (pnResumenAntecedentes == null) {
			pnResumenAntecedentes = new JPanel();
			FlowLayout flowLayout = (FlowLayout) pnResumenAntecedentes.getLayout();
			flowLayout.setAlignment(FlowLayout.LEFT);
			pnResumenAntecedentes.add(getLblResumenAntecedentes());
		}
		return pnResumenAntecedentes;
	}
	private JScrollPane getScrollPTablaAntecedentes() {
		if (scrollPTablaAntecedentes == null) {
			scrollPTablaAntecedentes = new JScrollPane();
			
			scrollPTablaAntecedentes.setViewportView(getTableAntecedentes());

		}
		return scrollPTablaAntecedentes;
	}
	private JTable getTableAntecedentes() {
		if (tableAntecedentes == null) {
			String[] nombreColumnas= {"Nombre","Fecha"};
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
			
			añadirFilasAntecedente();
		}
		return tableAntecedentes;
	}


	private JButton getBtnBorrarAntecedente() {
		if (btnBorrarAntecedente == null) {
			btnBorrarAntecedente = new JButton("Borrar antecedente");
			btnBorrarAntecedente.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					borrarFilaAntecedente();
				}
			});
		}
		return btnBorrarAntecedente;
	}


	private JLabel getLblResumenAntecedentes() {
		if (lblResumenAntecedentes == null) {
			lblResumenAntecedentes = new JLabel("Antecedentes seleccionados:");
		}
		return lblResumenAntecedentes;
	}
	private JPanel getPnAccionesAntecedentes() {
		if (pnAccionesAntecedentes == null) {
			pnAccionesAntecedentes = new JPanel();
			pnAccionesAntecedentes.setLayout(new GridLayout(5, 0, 0, 0));
			pnAccionesAntecedentes.add(getPnNorteAntecedentes_1());
			pnAccionesAntecedentes.add(getPanel_19());
			pnAccionesAntecedentes.add(getPanel_20());
			pnAccionesAntecedentes.add(getPanel_21());
			pnAccionesAntecedentes.add(getPanel_22());
		}
		return pnAccionesAntecedentes;
	}
	private JPanel getPanel_19() {
		if (pnCbAntecedentes == null) {
			pnCbAntecedentes = new JPanel();
			pnCbAntecedentes.setLayout(null);
			pnCbAntecedentes.add(getCbAntecedentes());
		}
		return pnCbAntecedentes;
	}
	private JPanel getPanel_20() {
		if (pnAnadirAntecedente == null) {
			pnAnadirAntecedente = new JPanel();
			pnAnadirAntecedente.setLayout(null);
			pnAnadirAntecedente.add(getTxtFiltrarAntecedentes());
			pnAnadirAntecedente.add(getBtnFiltrarAntecedentes_1());
			pnAnadirAntecedente.add(getBtnAsignarAntecedente());
		}
		return pnAnadirAntecedente;
	}
	private JPanel getPanel_21() {
		if (pnVacío28 == null) {
			pnVacío28 = new JPanel();
			FlowLayout fl_pnVacío28 = (FlowLayout) pnVacío28.getLayout();
			fl_pnVacío28.setAlignment(FlowLayout.LEFT);
		}
		return pnVacío28;
	}
	private JPanel getPanel_22() {
		if (pnVerAntecedentes == null) {
			pnVerAntecedentes = new JPanel();
			pnVerAntecedentes.setLayout(null);
			pnVerAntecedentes.add(getBtnVerAnterioresAntecedentes());
		}
		return pnVerAntecedentes;
	}
	private JComboBox<String> getCbAntecedentes() {
		if (cbAntecedentes == null) {
			cbAntecedentes = new JComboBox();
			cbAntecedentes.setBounds(new Rectangle(10, 32, 797, 21));
			
			String[] nombreAntecedentes = new String[antecedentes.size()];
			
			for (int i = 0; i < antecedentes.size(); i++) {
				nombreAntecedentes[i] = antecedentes.get(i).getNombreAntecedente();
			}
			
			cbAntecedentes.setModel(new DefaultComboBoxModel<String>(nombreAntecedentes));
			
		}
		return cbAntecedentes;
	}
	private JButton getBtnAsignarAntecedente() {
		if (btnAsignarAntecedente == null) {
			btnAsignarAntecedente = new JButton("Asignar antecedente");
			btnAsignarAntecedente.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					anadirAntecedente();
					restaurarCbAntecedentes();
				}
			});
			btnAsignarAntecedente.setBounds(647, 11, 160, 21);
		}
		return btnAsignarAntecedente;
	}
	




	private JButton getBtnVerAnterioresAntecedentes() {
		if (btnVerAnterioresAntecedentes == null) {
			btnVerAnterioresAntecedentes = new JButton("Ver antecedentes anteriores");
			btnVerAnterioresAntecedentes.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						verAntecedentes();
					} catch (HeadlessException e1) {
						e1.printStackTrace();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
				
			});
			btnVerAnterioresAntecedentes.setBounds(138, 0, 199, 21);
		}
		return btnVerAnterioresAntecedentes;
	}



	private JTextField getTxtFiltrarAntecedentes() {
		if (txtFiltrarAntecedentes == null) {
			txtFiltrarAntecedentes = new JTextField();
			txtFiltrarAntecedentes.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					activarBotonFiltrarAntecedentes();
				}
			});
			txtFiltrarAntecedentes.setColumns(10);
			txtFiltrarAntecedentes.setBounds(137, 7, 202, 28);
		}
		return txtFiltrarAntecedentes;
	}


	private JButton getBtnFiltrarAntecedentes_1() {
		if (btnFiltrarAntecedentes == null) {
			btnFiltrarAntecedentes = new JButton("Buscar");
			btnFiltrarAntecedentes.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					buscarAntecedente();
				}
			});
			btnFiltrarAntecedentes.setEnabled(false);
			btnFiltrarAntecedentes.setBounds(349, 10, 90, 21);
		}
		return btnFiltrarAntecedentes;
	}
	
	
	


	/**
	 * Método que me guarda temporalmente un antecedente
	 */
	protected void anadirAntecedente() {
		int indice = cbAntecedentes.getSelectedIndex(); // el índice que hay seleccionado en el cb
		Antecedente antecedente = null;
		
		// Buscamos la vacuna que hay seleccionada en el cb
		int contador = 0;
		for(Antecedente a : antecedentes) {
			if (indice == contador) {
				antecedente = a;
			}
			contador = contador + 1;
		}
		
		Random r = new Random();
		String codAsigAntecedente = "" + r.nextInt(999999); // El código es aleatorio
		
		String nombreAntecedente = antecedente.getNombreAntecedente();
		String nHistorial = paciente.getHistorial(); // El número de historial del paciente a quien le hemos asignado el antecedente
		String nAntecedente = antecedente.getCodAntecedente(); // El identificador del antecedente
		String codMedico = codmedico;
		Date fecha = new Date();	
		Time hora = new Time(new Date().getTime());
		AsignaAntecedente aa = new AsignaAntecedente(codAsigAntecedente, nombreAntecedente, nAntecedente, nHistorial, codMedico, fecha, hora);

		asignaAntecedentesPaciente.add(aa);
		
		
		if (tablaAntecedentesLista == false) { // Para que no casque al pintar la tabla de los antecedentes
			tablaAntecedentesLista = true;
		}
		
		añadirFilasAntecedente(); // Añadimos a la tabla que nos muestra las preinscripciones que ya le hemos asignado
		
		limpiarPanelAntecedentes(); // Para ponerlo todo de 0
		
	}
	
	
	


	/**
	 * Método para volver a poner el cb bien
	 */
	protected void restaurarCbAntecedentes() {
		cambiarIndiceCBAntecedentes(0);		
		txtFiltrarAntecedentes.setText("");
		if(btnFiltrarAntecedentes.isSelected()) {
			btnFiltrarAntecedentes.setEnabled(true);
		}
	}
	
	
	/**
	 * Método para buscar un antecedente
	 */
	protected void activarBotonFiltrarAntecedentes() {
		if (!btnFiltrarAntecedentes.isEnabled()) { // Si no estaba activado lo activamos
			btnFiltrarAntecedentes.setEnabled(true);
		}	
	}
	
	
	/**
	 * Método para buscar un antecedente
	 */
	protected void buscarAntecedente() {
		if (!getTxtFiltrarAntecedentes().getText().equals("")) { // Si hay algo escrito en el campo de texto
			String buscador = getTxtFiltrarAntecedentes().getText().toLowerCase(); // Lo que ha buscado (lo pasamos a minuscula)
			boolean encontrado = false; // Para saber si encontró o no el antecedentes buscado
			
			for(int i = 0; i < antecedentes.size(); i++) {
				if (antecedentes.get(i).getNombreAntecedente().toLowerCase().equals(buscador)) { // Si lo que está buscando lo hay en la lista de diagnosticos
					
					cambiarIndiceCBAntecedentes(i);
					encontrado = true; // lo encontró
				}
			}
			
			if (!encontrado) { // Si no encontró el diagnostico
				JOptionPane.showMessageDialog(null, "No hemos podido encontrar su antecedente en este momento");
			}
		}
		else {
			JOptionPane.showMessageDialog(null, "No ha introducido nada en el buscador");		
		}
		
		
	}
	
	


	/**
	 * Método que me llama a una ventana nueva para crear un nuevo antecedente
	 */
	protected void crearAntecedente() {
		AnadirAntecedente ventanaAntecedente = new AnadirAntecedente(this);
		
		ventanaAntecedente.setLocationRelativeTo(this);
		ventanaAntecedente.setResizable(true);
		ventanaAntecedente.setModal(true); // hasta que no se cierre una ventana no se puede abrir otra
		ventanaAntecedente.setVisible(true);		
	}

	
	
	/**
	 * Método para vaciar el combo box de los antecedentes
	 */
	public void vaciarCBAntecedentes() {
		cbAntecedentes.removeAllItems();		
	}

	
	/**
	 * Método que me vuelve a rellenar el cb con los antecedentes actualizados
	 */
	public void rellenarCBAntecedentes() {
		for (int i = 0; i < nombresAntecedentes.size(); i++) {
			cbAntecedentes.insertItemAt(nombresAntecedentes.get(i), i);
		}		
	}

	
	/**
	 * Método que me actualiza los valores del cb
	 * @throws SQLException 
	 */
	public void ponerAntecedentes() throws SQLException {
		nombresAntecedentes = new ArrayList<>();
		List<String> antecedentes = pbd.buscarNombreTodosAntecedentes();
		for(int i =0; i< antecedentes.size(); i++) {
			nombresAntecedentes.add(antecedentes.get(i));
		}		
	}
	
	
	
	

	/**
	 * Método que me guarda definitivamente los antecedentes
	 * @throws SQLException 
	 */
	private void guardarAntecedentes() throws SQLException {
		if (!asignaAntecedentesPaciente.isEmpty()) { // Que le hayamos asignado algun antecedente
			guardarAccionAntecedentes();
			for (AsignaAntecedente aa : asignaAntecedentesPaciente) { // Voy guardando cada uno de los antecedentes
				pbd.nuevaAsignaAntecedente(aa);
			}
		}	
		
	}
	
	
	private void guardarAccionAntecedentes() throws SQLException {
		List<AccionEmpleado> devolverAccionesAdmin = pbd.devolverAccionesEmlpeado();
		int numeroAccion = 1;
		if(devolverAccionesAdmin.size()>0) {
			numeroAccion = devolverAccionesAdmin.size() + 1;
		}
		String naccion = "" +numeroAccion;
		
		String nombrePaciente = paciente.getNombre();
		String apellidoPaciente= paciente.getApellido();
		String codMed = codmedico;
		
		String nombre =pbd.devolverEmpleado(codMed).getNombre();
		String apellido =pbd.devolverEmpleado(codMed).getApellido();
		
		Date fecha = new Date();	
		Time hora = new Time(new Date().getTime());	
		
		String mensajePreinscripciones = "";
		for(int i =0;i <asignaAntecedentesPaciente.size();i++ ) {
			mensajePreinscripciones += asignaAntecedentesPaciente.get(i).getNombreAntecedente() + ", ";
		}
		String mensajeAccion = "El médico " + nombre + " " +apellido  + " ha asignado al paciente " + nombrePaciente + " " 
		+ apellidoPaciente + " el siguiente antecedente" + mensajePreinscripciones;
				
		AccionEmpleado a = new AccionEmpleado(naccion, codMed,  fecha, hora, mensajeAccion);
		pbd.guardarAccionEmpleado(a);
		
	}

	public void setAntecedente(Antecedente antecedente) {
		this.antecedente = antecedente;		
	}

	
	/**
	 * Me actualiza la ventana después de añadir un nuevo antecedente
	 */
	public void actualizarAntecedente() {
		try {
			antecedentes = pbd.cargarAntecedentes(); // Volvemos a cargar los antecedentes de la base de datos después de haber añadido
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		listoAntecedentes = false;
		cbAntecedentes.removeAllItems(); // Vacío todo el combo box
		
		// Cargo otra vez los antecedentes en el cb
		String[] nombreAntecedentes = new String[antecedentes.size()];
		for (int i = 0; i< antecedentes.size(); i++) {
			nombreAntecedentes[i] = antecedentes.get(i).getNombreAntecedente();
		}
		
		cbAntecedentes.setModel(new DefaultComboBoxModel<String>(nombreAntecedentes));				

		listoAntecedentes = true;
		
		this.revalidate();
		repaint();
		
		//cbAntecedentes.repaint();
		//cbAntecedentes.setSelectedIndex(0);
		int contador = 0;
		for (Antecedente a : antecedentes) {
			if (a.getNombreAntecedente().toLowerCase().equals(antecedente.getNombreAntecedente().toLowerCase())) {
				System.out.println("LO ENCONTRO");
				System.out.println("nombre: " + antecedente.getNombreAntecedente() + "contador : " + contador);
				cbAntecedentes.setSelectedIndex(contador); // Lo mostramos en el cb	

				//cambiarIndiceCBAntecedentes(contador);
				
			}
			contador = contador + 1;
		}
	}

	
	/**
	 * Método para cambiar el índice del combo box
	 * @param indice
	 */
	private void cambiarIndiceCBAntecedentes(int indice) {
		cbAntecedentes.setSelectedIndex(indice); // Lo mostramos en el cb	
	}
	
	
	
	/**
	 * Método para añadir las filas a nuestra tabla de antecedentes
	 */
	private void añadirFilasAntecedente() {
		borrarModeloTablaAntecedentes(); // Borramos todo antes de volver a pintar
		
		Object[] nuevaFila=new Object[2]; // 2 son las columnas
				
		
		if (tablaAntecedentesLista) {
			for (AsignaAntecedente a : asignaAntecedentesPaciente) {
				nuevaFila[0] = a.getNombreAntecedente(); // El nombre del antecedente
				nuevaFila[1] = a.getFecha();
				
				
				modeloTablaAntecedentes.addRow(nuevaFila); // Añado la fila				
			}		
		}		
	}

	
	/**
	 * Método para borrar el contenido de la tabla de antecedentes antes de pintarla
	 */
	private void borrarModeloTablaAntecedentes() {
		int filas = modeloTablaAntecedentes.getRowCount();
		
		for (int i = 0; i < filas; i++) {
			modeloTablaAntecedentes.removeRow(0);			
		}		
	}
	
	
	
	/**
	 * Método para dejar como nueva la pantalla de los antecedentes
	 */
	private void limpiarPanelAntecedentes() {
		cbAntecedentes.setSelectedIndex(0);
		//textArea.setText("");	
	}
	
	
	
	/**
	 * Método que me borra una fila seleccionada de la tabla de antecedentes
	 */
	protected void borrarFilaAntecedente() {
		int fila = tableAntecedentes.getSelectedRow();
		
		if (fila != -1) {
			int res = JOptionPane.showConfirmDialog(null, "¿Está seguro de que desea borrar el antecedente?","Mensaje de confirmación",JOptionPane.YES_NO_OPTION);
			if(res == JOptionPane.YES_OPTION) {	
					
					asignaAntecedentesPaciente.remove(tableAntecedentes.getSelectedRow());
					
					añadirFilasAntecedente();
			
			}						
		}	
	}
	

	private JPanel getPnNorteAntecedentes_1() {
		if (pnNuevoAntecedente == null) {
			pnNuevoAntecedente = new JPanel();
			pnNuevoAntecedente.setLayout(null);
			pnNuevoAntecedente.add(getBtnNuevoAntecedente());
		}
		return pnNuevoAntecedente;
	}
	private JButton getBtnNuevoAntecedente() {
		if (btnNuevoAntecedente == null) {
			btnNuevoAntecedente = new JButton("Crear nuevo antecedente");
			btnNuevoAntecedente.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					crearAntecedente();
				}
			});
			btnNuevoAntecedente.setBounds(136, 34, 202, 21);
		}
		return btnNuevoAntecedente;
	}
	private JPanel getPnCentroV() {
		if (pnCentroV == null) {
			pnCentroV = new JPanel();
			pnCentroV.setLayout(new BorderLayout(0, 0));
			pnCentroV.add(getPnAccionesVacunas(), BorderLayout.CENTER);
		}
		return pnCentroV;
	}
	private JPanel getPnDchaV() {
		if (pnDchaV == null) {
			pnDchaV = new JPanel();
			pnDchaV.setLayout(new BorderLayout(0, 0));
			pnDchaV.add(getPnResumenV(), BorderLayout.CENTER);
			pnDchaV.add(getPnBorrarVacuna(), BorderLayout.SOUTH);
			pnDchaV.add(getPnResumenVacunas(), BorderLayout.NORTH);
		}
		return pnDchaV;
	}
	private JPanel getPnAccionesVacunas() {
		if (pnAccionesVacunas == null) {
			pnAccionesVacunas = new JPanel();
			pnAccionesVacunas.setLayout(new GridLayout(5, 0, 0, 0));
			pnAccionesVacunas.add(getPnVacio29());
			pnAccionesVacunas.add(getPanel_6_1());
			pnAccionesVacunas.add(getPanel_7_2());
			pnAccionesVacunas.add(getPanel_8_1());
			pnAccionesVacunas.add(getPanel_9_1());
		}
		return pnAccionesVacunas;
	}
	private JPanel getPnVacio29() {
		if (pnVacio29 == null) {
			pnVacio29 = new JPanel();
		}
		return pnVacio29;
	}
	private JPanel getPanel_6_1() {
		if (pnCbVacunas == null) {
			pnCbVacunas = new JPanel();
			pnCbVacunas.setLayout(null);
			pnCbVacunas.add(getCbVacunas_1());
		}
		return pnCbVacunas;
	}
	private JPanel getPanel_7_2() {
		if (pnAnadirVacuna == null) {
			pnAnadirVacuna = new JPanel();
			pnAnadirVacuna.setLayout(null);
			pnAnadirVacuna.add(getTextfFiltrarVacunas());
			pnAnadirVacuna.add(getBtnFiltrarVacunas_1());
			pnAnadirVacuna.add(getBtnAsignarVacuna());
		}
		return pnAnadirVacuna;
	}
	private JPanel getPanel_8_1() {
		if (pnVacio30 == null) {
			pnVacio30 = new JPanel();
		}
		return pnVacio30;
	}
	private JPanel getPanel_9_1() {
		if (pnVacio31 == null) {
			pnVacio31 = new JPanel();
		}
		return pnVacio31;
	}
	private JTextField getTextfFiltrarVacunas() {
		if (textfFiltrarVacunas == null) {
			textfFiltrarVacunas = new JTextField();
			textfFiltrarVacunas.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					activarBotonFiltrarVacunas();
				}
			});
			textfFiltrarVacunas.setBounds(134, 6, 203, 28);
			textfFiltrarVacunas.setToolTipText("Introduzca el nombre de la vacuna para buscarla");
			textfFiltrarVacunas.setColumns(10);
		}
		return textfFiltrarVacunas;
	}


	private JButton getBtnFiltrarVacunas_1() {
		if (btnFiltrarVacunas == null) {
			btnFiltrarVacunas = new JButton("Buscar");
			btnFiltrarVacunas.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					buscarVacuna();
				}
			});
			btnFiltrarVacunas.setBounds(351, 9, 82, 21);
			btnFiltrarVacunas.setEnabled(false);
		}
		return btnFiltrarVacunas;
	}
	
	/**
	 * Método que me busca si hay la vacuna que quiere el médico. 
	 * 		- Si la hay, la pone en el comboBox automáticamente
	 * 		- Si no la hay, muestra un mensaje al usuario diciendole que no existe
	 */
	protected void buscarVacuna() {
		if (!getTextfFiltrarVacunas().getText().equals("")) { // Si hay algo escrito en el campo de texto
			String buscador = getTextfFiltrarVacunas().getText().toLowerCase(); // Lo que ha buscado (lo pasamos a minuscula)
			boolean encontrada = false; // Para saber si encontró o no la vacuna buscada
			
			for(int i = 0; i < vacunas.size(); i++) {
				if (vacunas.get(i).getNombreVacuna().toLowerCase().equals(buscador)) { // Si lo que está buscando lo hay en la lista de vacunas

					cbVacunas.setSelectedIndex(i); // Lo mostramos en el cb
					encontrada = true; // la encontró

					//vacunaBuscada

				}
			}
			
			if (!encontrada) { // Si no encontró la vacuna
				JOptionPane.showMessageDialog(null, "No hemos podido encontrar su vacuna en este momento");
			}
		}
		else {
			JOptionPane.showMessageDialog(null, "No ha introducido nada en el buscador");		
		}
	}
	private JComboBox<String> getCbVacunas_1() {
		if (cbVacunas == null) {
			cbVacunas = new JComboBox();
			cbVacunas.setBounds(10, 32, 783, 21);
			
			String[] nombreVacunas = new String[vacunas.size()];
			for (int i = 0; i < vacunas.size(); i++) {
				nombreVacunas[i] = vacunas.get(i).getNombreVacuna();
			}
			
			cbVacunas.setModel(new DefaultComboBoxModel<String>(nombreVacunas));
		}
		return cbVacunas;
	}
	private JButton getBtnAsignarVacuna() {
		if (btnAsignarVacuna == null) {
			btnAsignarVacuna = new JButton("Vacunar");
			btnAsignarVacuna.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					anadirVacuna();
					restaurarCbVacunas();
				}
			});
			btnAsignarVacuna.setBounds(625, 10, 170, 21);
		}
		return btnAsignarVacuna;
	}


	private JPanel getPnResumenV() {
		if (pnResumenV == null) {
			pnResumenV = new JPanel();
			pnResumenV.setLayout(new GridLayout(0, 1, 0, 0));
			pnResumenV.add(getScrollPTablaVacunas());
			//pnResumenV.add(getTableVacunas());
		}
		return pnResumenV;
	}
	private JPanel getPnBorrarVacuna() {
		if (pnBorrarVacuna == null) {
			pnBorrarVacuna = new JPanel();
			FlowLayout flowLayout = (FlowLayout) pnBorrarVacuna.getLayout();
			flowLayout.setAlignment(FlowLayout.RIGHT);
			pnBorrarVacuna.add(getBtnBorrarVacuna());
		}
		return pnBorrarVacuna;
	}
	private JPanel getPnResumenVacunas() {
		if (pnResumenVacunas == null) {
			pnResumenVacunas = new JPanel();
			FlowLayout flowLayout = (FlowLayout) pnResumenVacunas.getLayout();
			flowLayout.setAlignment(FlowLayout.LEFT);
			pnResumenVacunas.add(getLblResumenVacunas());
		}
		return pnResumenVacunas;
	}
	private JScrollPane getScrollPTablaVacunas() {
		if (scrollPTablaVacunas == null) {
			scrollPTablaVacunas = new JScrollPane();
			
			scrollPTablaVacunas.setViewportView(getTableVacunas());

		}
		return scrollPTablaVacunas;
	}
	private JTable getTableVacunas() {
		if (tableVacunas == null) {
			String[] nombreColumnas= {"Nombre"};
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
			
			//sorter.setSortKeys(sortKeys);
			
			añadirFilasVacuna();
		}
		return tableVacunas;
	}


	private JButton getBtnBorrarVacuna() {
		if (btnBorrarVacuna == null) {
			btnBorrarVacuna = new JButton("Borrar vacuna");
			btnBorrarVacuna.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					borrarFilaVacuna();
				}
			});
		}
		return btnBorrarVacuna;
	}


	private JLabel getLblResumenVacunas() {
		if (lblResumenVacunas == null) {
			lblResumenVacunas = new JLabel("Vacunas seleccionadas:");
		}
		return lblResumenVacunas;
	}
	
	
	/**
	 * Método para restaurar el panel de las vacunas
	 */
	protected void restaurarCbVacunas() {
		cbVacunas.setSelectedIndex(0);	
		textfFiltrarVacunas.setText("");
		if(btnFiltrarVacunas.isEnabled()) {
			btnFiltrarVacunas.setEnabled(false);
		}
		
	}
	
	
	/**
	 * Método que me activa el botón de filtrar vacunas si no estaba ya
	 */
	protected void activarBotonFiltrarVacunas() {
		if (!btnFiltrarVacunas.isEnabled()) { // Si no estaba activado lo activamos
			btnFiltrarVacunas.setEnabled(true);
		}	
	}
	
	
	
	/**
	 * Método para añadir las filas a la tabla de vacunas
	 */
	private void añadirFilasVacuna() {
		borrarModeloTablaVacunas(); // Borramos todo antes de volver a pintar
		
		Object[] nuevaFila=new Object[1]; // 1 columna
				
		
		if (tablaVacunasLista) {
			for (AsignaVacuna a : asignaVacunasPaciente) {
				nuevaFila[0] = a.getNombreVacuna(); // El nombre de la vacuna
				
				modeloTablaVacunas.addRow(nuevaFila); // Añado la fila
				
			}		
		}		
	}

	
	/**
	 * Método que me borra toda la tabla de vacunas
	 */
	private void borrarModeloTablaVacunas() {
		int filas = modeloTablaVacunas.getRowCount();
		
		for (int i = 0; i < filas; i++) {
			modeloTablaVacunas.removeRow(0);			
		}	
		
	}
	
	
	
	/**
	 * Método para borrar una vacuna que está seleccionada en la tabla de vacunas
	 */
	protected void borrarFilaVacuna() {
		
		int fila = tableVacunas.getSelectedRow();
		
		if (fila != -1) {
			int res = JOptionPane.showConfirmDialog(null, "¿Está seguro de que desea borrar la vacuna?","Mensaje de confirmación",JOptionPane.YES_NO_OPTION);
			if(res == JOptionPane.YES_OPTION) {	
					
					asignaVacunasPaciente.remove(tableVacunas.getSelectedRow());
					
					añadirFilasVacuna();			
			}						
		}
	}
	
	
	/**
	 * Método que me deja el panel de las vacunas como nuevo
	 */
	private void limpiarPanelVacunas() {
		cbVacunas.setSelectedIndex(0);
		
	}
	private JPanel getPnCentroD() {
		if (pnCentroD == null) {
			pnCentroD = new JPanel();
			pnCentroD.setLayout(new BorderLayout(0, 0));
			pnCentroD.add(getPnAccionesDiagnosticos(), BorderLayout.CENTER);
		}
		return pnCentroD;
	}
	private JPanel getPnDchaD() {
		if (pnDchaD == null) {
			pnDchaD = new JPanel();
			pnDchaD.setLayout(new BorderLayout(0, 0));
			pnDchaD.add(getPnResumenD(), BorderLayout.CENTER);
			pnDchaD.add(getPnBorrarDiagnostico(), BorderLayout.SOUTH);
			pnDchaD.add(getPnResumenDiagnosticos(), BorderLayout.NORTH);
		}
		return pnDchaD;
	}
	private JPanel getPnAccionesDiagnosticos() {
		if (pnAccionesDiagnosticos == null) {
			pnAccionesDiagnosticos = new JPanel();
			pnAccionesDiagnosticos.setLayout(new GridLayout(5, 0, 0, 0));
			pnAccionesDiagnosticos.add(getPnVacio32());
			pnAccionesDiagnosticos.add(getPnCbDiagnosticos());
			pnAccionesDiagnosticos.add(getPnAnadirDiagnostico());
			pnAccionesDiagnosticos.add(getPnVacio33());
			pnAccionesDiagnosticos.add(getPnVacio34());
		}
		return pnAccionesDiagnosticos;
	}
	private JPanel getPnVacio32() {
		if (pnVacio32 == null) {
			pnVacio32 = new JPanel();
		}
		return pnVacio32;
	}
	private JPanel getPnCbDiagnosticos() {
		if (pnCbDiagnosticos == null) {
			pnCbDiagnosticos = new JPanel();
			pnCbDiagnosticos.setLayout(null);
			pnCbDiagnosticos.add(getCbDiagnosticos());

		}
		return pnCbDiagnosticos;
	}
	private JPanel getPnAnadirDiagnostico() {
		if (pnAnadirDiagnostico == null) {
			pnAnadirDiagnostico = new JPanel();
			pnAnadirDiagnostico.setLayout(null);
			pnAnadirDiagnostico.add(getTxtFiltrarDiagnosticos());
			pnAnadirDiagnostico.add(getBtnFiltrarDiagnosticos());
			pnAnadirDiagnostico.add(getChckbxEdo());
			pnAnadirDiagnostico.add(getBtnDiagnosticar());

		}
		return pnAnadirDiagnostico;
	}
	private JPanel getPnVacio33() {
		if (pnVacio33 == null) {
			pnVacio33 = new JPanel();
		}
		return pnVacio33;
	}
	private JPanel getPnVacio34() {
		if (pnVacio34 == null) {
			pnVacio34 = new JPanel();
		}
		return pnVacio34;
	}
	private JPanel getPnResumenD() {
		if (pnResumenD == null) {
			pnResumenD = new JPanel();
			pnResumenD.setLayout(new GridLayout(1, 0, 0, 0));
			pnResumenD.add(getScrollPTablaDiagnosticos());
			//pnResumenD.add(getTableDiagnosticos());
		}
		return pnResumenD;
	}
	private JPanel getPnBorrarDiagnostico() {
		if (pnBorrarDiagnostico == null) {
			pnBorrarDiagnostico = new JPanel();
			FlowLayout flowLayout = (FlowLayout) pnBorrarDiagnostico.getLayout();
			flowLayout.setAlignment(FlowLayout.RIGHT);
			pnBorrarDiagnostico.add(getBtnBorrarDiagnostico());
		}
		return pnBorrarDiagnostico;
	}
	private JPanel getPnResumenDiagnosticos() {
		if (pnResumenDiagnosticos == null) {
			pnResumenDiagnosticos = new JPanel();
			FlowLayout flowLayout = (FlowLayout) pnResumenDiagnosticos.getLayout();
			flowLayout.setAlignment(FlowLayout.LEFT);
			pnResumenDiagnosticos.add(getLblResumenDiagnosticos());
		}
		return pnResumenDiagnosticos;
	}
	private JScrollPane getScrollPTablaDiagnosticos() {
		if (scrollPTablaDiagnosticos == null) {
			scrollPTablaDiagnosticos = new JScrollPane();
			
			scrollPTablaDiagnosticos.setViewportView(getTableDiagnosticos());

		}
		return scrollPTablaDiagnosticos;
	}
	private JTable getTableDiagnosticos() {
		if (tableDiagnosticos == null) {
			String[] nombreColumnas= {"Código","Nombre"};
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
			
			//sorter.setSortKeys(sortKeys);
			
			añadirFilasDiagnostico();
		}
		return tableDiagnosticos;
	}


	private JButton getBtnBorrarDiagnostico() {
		if (btnBorrarDiagnostico == null) {
			btnBorrarDiagnostico = new JButton("Borrar diagn\u00F3stico");
			btnBorrarDiagnostico.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					borrarFilaDiagnostico();
				}
			});
		}
		return btnBorrarDiagnostico;
	}


	private JLabel getLblResumenDiagnosticos() {
		if (lblResumenDiagnosticos == null) {
			lblResumenDiagnosticos = new JLabel("Diagn\u00F3sticos seleccionados:");
		}
		return lblResumenDiagnosticos;
	}
	
	
	/**
	 * Método para añadir las filas a la tabla de los diagnósticos
	 */
	private void añadirFilasDiagnostico() {
		borrarModeloTablaDiagnosticos(); // Borramos todo antes de volver a pintar
		
		Object[] nuevaFila=new Object[2]; // 1 columna
				
		
		if (tablaDiagnosticosLista) {
			for (AsignaDiagnostico a : asignaDiagnosticosPaciente) {
				nuevaFila[0] = a.getnDiagnostico(); // El código del diagnóstico
				nuevaFila[1] = a.getNombreDiagnostico(); // El nombre del diagnóstico
				
				modeloTablaDiagnosticos.addRow(nuevaFila); // Añado la fila
				
			}		
		}	
		
	}

	
	/**
	 * Método para borrar la tabla de los diagnósticos
	 */
	private void borrarModeloTablaDiagnosticos() {
		int filas = modeloTablaDiagnosticos.getRowCount();
		
		for (int i = 0; i < filas; i++) {
			modeloTablaDiagnosticos.removeRow(0);			
		}	
		
	}
	
	
	
	/**
	 * Método para borrar una fila de la tabla resumen de los diagnósticos
	 */
	protected void borrarFilaDiagnostico() {
		int fila = tableDiagnosticos.getSelectedRow();
		
		if (fila != -1) {
			int res = JOptionPane.showConfirmDialog(null, "¿Está seguro de que desea borrar el diagnóstico?","Mensaje de confirmación",JOptionPane.YES_NO_OPTION);
			if(res == JOptionPane.YES_OPTION) {	
					
					asignaDiagnosticosPaciente.remove(tableDiagnosticos.getSelectedRow());
					
					añadirFilasDiagnostico();
			
			}						
		}
	}
	
	
	
	/**
	 * Método para poner como nuevo el panel de los diagnósticos
	 */
	private void limpiarPanelDiagnosticos() {
		cbDiagnosticos.setSelectedIndex(0);	
	}
	private JPanel getPanelCentroPr() {
		if (panelCentroPr == null) {
			panelCentroPr = new JPanel();
			panelCentroPr.setLayout(new BorderLayout(0, 0));
			panelCentroPr.add(getPnAccionesProcedimientos(), BorderLayout.CENTER);
		}
		return panelCentroPr;
	}
	private JPanel getPnDchaPr() {
		if (pnDchaPr == null) {
			pnDchaPr = new JPanel();
			pnDchaPr.setLayout(new BorderLayout(0, 0));
			pnDchaPr.add(getPnResumenPr(), BorderLayout.CENTER);
			pnDchaPr.add(getPnBorrarProcedimiento(), BorderLayout.SOUTH);
			pnDchaPr.add(getPnResumenProcedimientos(), BorderLayout.NORTH);
		}
		return pnDchaPr;
	}
	private JPanel getPnAccionesProcedimientos() {
		if (pnAccionesProcedimientos == null) {
			pnAccionesProcedimientos = new JPanel();
			pnAccionesProcedimientos.setLayout(new GridLayout(5, 0, 0, 0));
			pnAccionesProcedimientos.add(getPnVacio35());
			pnAccionesProcedimientos.add(getPnCbProcedimientos());
			pnAccionesProcedimientos.add(getPnAnadirProcedimiento());
			pnAccionesProcedimientos.add(getPnVacio36());
			pnAccionesProcedimientos.add(getPnVacio37());
		}
		return pnAccionesProcedimientos;
	}
	private JPanel getPnVacio35() {
		if (pnVacio35 == null) {
			pnVacio35 = new JPanel();
		}
		return pnVacio35;
	}
	private JPanel getPnCbProcedimientos() {
		if (pnCbProcedimientos == null) {
			pnCbProcedimientos = new JPanel();
			pnCbProcedimientos.setLayout(null);
			pnCbProcedimientos.add(getCbProcedimientos());

		}
		return pnCbProcedimientos;
	}
	private JPanel getPnAnadirProcedimiento() {
		if (pnAnadirProcedimiento == null) {
			pnAnadirProcedimiento = new JPanel();
			pnAnadirProcedimiento.setLayout(null);
			pnAnadirProcedimiento.add(getTxtfFiltrarProcedimientos());
			pnAnadirProcedimiento.add(getBtnFiltrarProcedimientos());
			pnAnadirProcedimiento.add(getBtnProceder());

		}
		return pnAnadirProcedimiento;
	}
	private JPanel getPnVacio36() {
		if (pnVacio36 == null) {
			pnVacio36 = new JPanel();
		}
		return pnVacio36;
	}
	private JPanel getPnVacio37() {
		if (pnVacio37 == null) {
			pnVacio37 = new JPanel();
		}
		return pnVacio37;
	}
	private JPanel getPnResumenPr() {
		if (pnResumenPr == null) {
			pnResumenPr = new JPanel();
			pnResumenPr.setLayout(new GridLayout(1, 0, 0, 0));
			pnResumenPr.add(getScrollPTablaProcedimientos());
			//pnResumenPr.add(getTableProcedimientos());
		}
		return pnResumenPr;
	}
	private JPanel getPnBorrarProcedimiento() {
		if (pnBorrarProcedimiento == null) {
			pnBorrarProcedimiento = new JPanel();
			FlowLayout flowLayout = (FlowLayout) pnBorrarProcedimiento.getLayout();
			flowLayout.setAlignment(FlowLayout.RIGHT);
			pnBorrarProcedimiento.add(getBtnBorrarProcedimiento());
		}
		return pnBorrarProcedimiento;
	}
	private JPanel getPnResumenProcedimientos() {
		if (pnResumenProcedimientos == null) {
			pnResumenProcedimientos = new JPanel();
			FlowLayout flowLayout = (FlowLayout) pnResumenProcedimientos.getLayout();
			flowLayout.setAlignment(FlowLayout.LEFT);
			pnResumenProcedimientos.add(getLblResumenProcedimientos());
		}
		return pnResumenProcedimientos;
	}
	private JScrollPane getScrollPTablaProcedimientos() {
		if (scrollPTablaProcedimientos == null) {
			scrollPTablaProcedimientos = new JScrollPane();
			scrollPTablaProcedimientos.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
			
			scrollPTablaProcedimientos.setViewportView(getTableProcedimientos());

		}
		return scrollPTablaProcedimientos;
	}
	private JTable getTableProcedimientos() {
		if (tableProcedimientos == null) {
			String[] nombreColumnas= {"Código","Nombre"};
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
			
			añadirFilasProcedimiento();
		}
		return tableProcedimientos;
	}


	private JButton getBtnBorrarProcedimiento() {
		if (btnBorrarProcedimiento == null) {
			btnBorrarProcedimiento = new JButton("Borrar procedimiento");
			btnBorrarProcedimiento.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					borrarFilaProcedimiento();
				}
			});
		}
		return btnBorrarProcedimiento;
	}

	private JLabel getLblResumenProcedimientos() {
		if (lblResumenProcedimientos == null) {
			lblResumenProcedimientos = new JLabel("Procedimientos seleccionados:");
		}
		return lblResumenProcedimientos;
	}
	
	
	/**
	 * Método para añadir filas a la tabla de los procedimientos
	 */
	private void añadirFilasProcedimiento() {
		borrarModeloTablaProcedimientos(); // Borramos todo antes de volver a pintar
		
		Object[] nuevaFila=new Object[2]; // 2 columnas
				
		
		if (tablaProcedimientosLista) {
			for (AsignaProcedimiento a : asignaProcedimientosPaciente) {
				nuevaFila[0] = a.getCodigoProcedimiento(); // El codigo del procedimento
				nuevaFila[1] = a.getNombreProcedimiento(); // El nombre del procedimiento
				
				modeloTablaProcedimientos.addRow(nuevaFila); // Añado la fila
				
			}		
		}
		
	}

	/**
	 * Método para borrar toda la tabla de los procedimientos
	 */
	private void borrarModeloTablaProcedimientos() {
		int filas = modeloTablaProcedimientos.getRowCount();
		
		for (int i = 0; i < filas; i++) {
			modeloTablaProcedimientos.removeRow(0);			
		}			
	}
	
	
	/**
	 * Metodo que me deja el panel de los procedimientos como nuevo
	 */
	private void limpiarPanelProcedimientos() {
		cbProcedimientos.setSelectedIndex(0);		
	}
	
	
	/**
	 * Método para borrar 1 fila de la tabla resumen de procedimientos
	 */
	protected void borrarFilaProcedimiento() {
		int fila = tableProcedimientos.getSelectedRow();
		
		if (fila != -1) {
			int res = JOptionPane.showConfirmDialog(null, "¿Está seguro de que desea borrar el procedimiento?","Mensaje de confirmación",JOptionPane.YES_NO_OPTION);
			if(res == JOptionPane.YES_OPTION) {	
					
					asignaProcedimientosPaciente.remove(tableProcedimientos.getSelectedRow());
					
					añadirFilasProcedimiento();
			
			}						
		}
		
		
	}
	
	/**
	 * Método que me abre una nueva ventana para ver los antecedentes anteriores del paciente
	 * @throws SQLException 
	 * @throws HeadlessException 
	 */
	protected void verAntecedentes() throws HeadlessException, SQLException {
		
		if (pbd.tieneAntecedentes(paciente.getHistorial())){ // Si tiene algún antecedente abre la ventana
		
			VerAntecedentes va = new VerAntecedentes(this, paciente);
		
			va.setLocationRelativeTo(this);
			va.setResizable(true);
			va.setModal(true); // hasta que no se cierre una ventana no se puede abrir otra
			va.setVisible(true);	
		}
		else { // Si no tiene ningún antecedente muestra el mensaje y NO abre la ventana
			JOptionPane.showMessageDialog(null, "El paciente no tiene ningún antecedente previo.");
		}
	}
	
	
	private JCheckBox getChckbxEdo() {
		if (chckbxEdo == null) {
			chckbxEdo = new JCheckBox("Enfermedad EDO");
			chckbxEdo.setBounds(506, 9, 136, 21);
		}
		return chckbxEdo;
	}
	
	
	/**
	 * Método que me mira si es una enfermedad edo y en ese caso manda un correo
	 */
	protected void comprobarEDO() {
		if (chckbxEdo.isSelected()) {
			Email.enviarEDO("roloalvarez7@gmail.com", "sbeiaolebhiewuzz", "UO265210@uniovi.es", paciente, cita, (String)getCbDiagnosticos().getSelectedItem());
		}
	}



}