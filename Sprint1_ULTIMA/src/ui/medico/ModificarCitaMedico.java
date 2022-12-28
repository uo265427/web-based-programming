package ui.medico;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.sun.org.apache.xml.internal.serialize.XHTMLSerializer;

import logica.AsignaPreinscripcion;
import logica.Cita;
import logica.HistorialMedico;
import logica.Paciente;
import logica.Preinscripcion;
import logica.servicios.ParserBaseDeDatos;
import ui.AnadirAntecedentesHistorial;

import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JScrollBar;
import javax.swing.JTextPane;
import javax.swing.SpinnerDateModel;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.awt.event.ActionEvent;
import javax.swing.JSpinner;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;
import javax.swing.JRadioButton;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ModificarCitaMedico extends JDialog {

	
	private Cita cita; // El paciente del que estamos modificando la cita
	private Preinscripcion preinscripcion; // La preinscripcion
	private List<Preinscripcion> preinscripciones;
	private Paciente paciente;
	
	
	private JPanel contentPane;
	private JPanel pnSur;
	private JPanel pnCentro;
	private JButton btnGuardar;
	private JButton btnCancelar;
	private JPanel pnIzq;
	private JPanel pnDcha;
	private JLabel lblNombre;
	private JLabel lblApellidos;
	private JLabel lblHoraEntrada;
	private JLabel lblHoraSalida;
	private JLabel lblPreinscripcin;
	private JLabel lblAcudiALa;
	private JTextField txtNombre;
	private JTextField txtApellidos;
	private JPanel pnPreinscripcionPrincipal;
	private JButton btPreinscripcion;
	private JComboBox<Preinscripcion> cbPreinscripciones;
	private JSpinner timeSpinnerFin;
	private JSpinner timeSpinnerInicio;
	private JLabel lblNombre_1;
	private JButton btnSeleccionar;
	private JLabel label;
	private JLabel label_1;
	private JLabel label_2;
	private JLabel label_3;
	private JPanel pnPreinscripcionCantidad;
	private JSpinner spinnerCantidad;
	private JLabel lblCantidad;
	private JPanel pnDuracion;
	private JLabel lblDuracinenDas;
	private JSpinner spinnerDuracion;
	private JPanel pnIntervalo;
	private JLabel lblIntervalo;
	private JSpinner spinnerIntervalo;
	private JPanel pnInstrucciones;
	private JLabel lblInstrucciones;

	private JScrollPane scrollPane;
	private JTextArea textArea;

	
	
	private ParserBaseDeDatos pbd=new ParserBaseDeDatos();
	private JLabel lblCausas;
	private JTextField txtCausas;
	private JPanel panelAcudio;
	private Component horizontalStrut;
	private JRadioButton rdbtnAcudio;
	private JRadioButton rdbtnNoAcudio;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	
	
	
	
//	/**
//	 * Launch the application.
//	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					ModificarCitaMedico2 frame = new ModificarCitaMedico2(paciente);
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 * @throws SQLException 
	 */
	public ModificarCitaMedico(Paciente paciente, Cita cita) throws SQLException {
		setIconImage(Toolkit.getDefaultToolkit().getImage(AnadirAntecedentesHistorial.class.getResource("/img/logop.jpg")));
		this.paciente = paciente;
		this.cita = cita; // Nuestra cita
		preinscripciones = pbd.listarPrescripciones();
		
		
		setTitle("Modificar Cita");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 923, 557);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.add(getPnSur(), BorderLayout.SOUTH);
		contentPane.add(getPnCentro(), BorderLayout.CENTER);		
	}
	private JPanel getPnSur() {
		if (pnSur == null) {
			pnSur = new JPanel();
			FlowLayout flowLayout = (FlowLayout) pnSur.getLayout();
			flowLayout.setAlignment(FlowLayout.RIGHT);
			pnSur.setBackground(Color.WHITE);
			pnSur.add(getBtnGuardar());
			pnSur.add(getBtnCancelar());
		}
		return pnSur;
	}
	private JPanel getPnCentro() {
		if (pnCentro == null) {
			pnCentro = new JPanel();
			pnCentro.setBackground(Color.WHITE);
			pnCentro.setLayout(new GridLayout(0, 2, 0, 0));
			pnCentro.add(getPnIzq());
			pnCentro.add(getPnDcha());
		}
		return pnCentro;
	}
	private JButton getBtnGuardar() {
		if (btnGuardar == null) {
			btnGuardar = new JButton("Guardar");
			btnGuardar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					
					try {
						guardar();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			});
			
			
		}
		return btnGuardar;
	}
	private JButton getBtnCancelar() {
		if (btnCancelar == null) {
			btnCancelar = new JButton("Cancelar");
		}
		return btnCancelar;
	}
	private JPanel getPnIzq() {
		if (pnIzq == null) {
			pnIzq = new JPanel();
			pnIzq.setBackground(Color.WHITE);
			pnIzq.setLayout(new GridLayout(0, 1, 0, 0));
			pnIzq.add(getLblNombre());
			pnIzq.add(getLblApellidos());
			pnIzq.add(getLblHoraEntrada());
			pnIzq.add(getLblHoraSalida());
			pnIzq.add(getLblPreinscripcin());
			pnIzq.add(getLabel());
			pnIzq.add(getLabel_1());
			pnIzq.add(getLabel_2());
			pnIzq.add(getLabel_3());
			pnIzq.add(getLblAcudiALa());
			pnIzq.add(getLblCausas());
		}
		return pnIzq;
	}
	private JPanel getPnDcha() {
		if (pnDcha == null) {
			pnDcha = new JPanel();
			pnDcha.setBackground(Color.WHITE);
			pnDcha.setLayout(new GridLayout(0, 1, 0, 0));
			pnDcha.add(getTxtNombre());
			pnDcha.add(getTxtApellidos());
			pnDcha.add(getTimeSpinnerInicio());
			pnDcha.add(getTimeSpinnerFin());
			pnDcha.add(getPnPreinscripcionPrincipal());
			pnDcha.add(getPnPreinscripcionCantidad());
			pnDcha.add(getPnDuracion());
			pnDcha.add(getPnIntervalo());
			pnDcha.add(getPnInstrucciones());
			pnDcha.add(getPanelAcudio());
			pnDcha.add(getTxtCausas());
		}
		return pnDcha;
	}
	private JLabel getLblNombre() {
		if (lblNombre == null) {
			lblNombre = new JLabel("Nombre:");
		}
		return lblNombre;
	}
	private JLabel getLblApellidos() {
		if (lblApellidos == null) {
			lblApellidos = new JLabel("Apellidos:");
		}
		return lblApellidos;
	}
	private JLabel getLblHoraEntrada() {
		if (lblHoraEntrada == null) {
			lblHoraEntrada = new JLabel("Hora entrada:");
		}
		return lblHoraEntrada;
	}
	private JLabel getLblHoraSalida() {
		if (lblHoraSalida == null) {
			lblHoraSalida = new JLabel("Hora salida:");
		}
		return lblHoraSalida;
	}
	private JLabel getLblPreinscripcin() {
		if (lblPreinscripcin == null) {
			lblPreinscripcin = new JLabel("Preinscripci\u00F3n:");
		}
		return lblPreinscripcin;
	}
	private JLabel getLblAcudiALa() {
		if (lblAcudiALa == null) {
			lblAcudiALa = new JLabel("Acudi\u00F3 a la cita:");
		}
		return lblAcudiALa;
	}
	private JTextField getTxtNombre() {
		if (txtNombre == null) {		
			txtNombre = new JTextField();
			
			txtNombre.setText(paciente.getNombre());
			
			txtNombre.setEditable(false);
			txtNombre.setColumns(10);
		}
		return txtNombre;
	}
	private JTextField getTxtApellidos() {
		if (txtApellidos == null) {
			txtApellidos = new JTextField();
			
			txtApellidos.setText(paciente.getApellido());
			
			txtApellidos.setEditable(false);
			txtApellidos.setColumns(10);
		}
		return txtApellidos;
	}
	private JPanel getPnPreinscripcionPrincipal() {
		if (pnPreinscripcionPrincipal == null) {
			pnPreinscripcionPrincipal = new JPanel();
			pnPreinscripcionPrincipal.setBackground(Color.WHITE);
			pnPreinscripcionPrincipal.setLayout(new GridLayout(0, 4, 0, 0));
			pnPreinscripcionPrincipal.add(getLblNombre_1());
			pnPreinscripcionPrincipal.add(getCbPreinscripciones());
			pnPreinscripcionPrincipal.add(getBtnSeleccionar());
			pnPreinscripcionPrincipal.add(getBtPreinscripcion());
		}
		return pnPreinscripcionPrincipal;
	}
	private JButton getBtPreinscripcion() {
		if (btPreinscripcion == null) {
			btPreinscripcion = new JButton("A\u00F1adir nueva");
			btPreinscripcion.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					
					preinscripcion();
					
				}
			});
		}
		return btPreinscripcion;
	}

	private JComboBox getCbPreinscripciones() {
		if (cbPreinscripciones == null) {
			cbPreinscripciones = new JComboBox();
			
			// Rellenamos el cb con las preinscripciones
			for (int i = 0; i< preinscripciones.size(); i++) {
				
				cbPreinscripciones.insertItemAt(preinscripciones.get(i), i);
			}
			
		}
		return cbPreinscripciones;
	}
	
	
	
	
	/**
	 * Método para guardar todos los campos que hemos modificado
	 * @throws SQLException 
	 */
	private void guardar() throws SQLException {
	
		
		
		
			if (preinscripcion != null) {
				
				anadirLaPreinscripcion();
				
			}
			
			if(buttonGroup.getSelection().isSelected()) {
				if(getRdbtnAcudio().isSelected()) {
					cita.setAcudio(true);
				}
				else if (getRdbtnNoAcudio().isSelected()) {
					cita.setAcudio(false);
				}
			}
			
			// Guardamos otra vez la hora de inicio por si se ha modificado
			Date dateInicio = (Date) timeSpinnerInicio.getValue();
			Time horaDeInicio = new Time(dateInicio.getTime());
			
			
			// Guardamos otra vez la hora de fin por si se ha modificado
			Date dateFin = (Date) timeSpinnerFin.getValue();
			Time horaDeFin = new Time(dateFin.getTime());
			
			
			// Guardamos en la base de datos
			//pbd.actualizarCita(horaDeInicio, horaDeFin, cita.isAcudio(), cita.getCodCita());
		
		modificarCausas();
		
		dispose();
	}

	

	
	protected void modificarCausas() throws SQLException {
		HistorialMedico hm = pbd.verHistorial(paciente.getHistorial());
		String causas = txtCausas.getText();
		Time hora =  cita.gethInicio();
		
		java.sql.Date horas = new java.sql.Date(hora.getTime());
		
		Time hour = new Time(horas.getTime());
		
		Date fecha = (Date) cita.getDate();
		
		java.sql.Date sDate = new java.sql.Date(fecha.getTime());
		

		if(!causas.equals("")) {
			Random r = new Random();
			String codcausa = "" + r.nextInt(300);
			//pbd.actualizarCausas(codcausa,causas, sDate, hour, cita.getCodMed());
		}
		dispose();
		}
	

	private JSpinner getTimeSpinnerFin() {
		timeSpinnerFin = new JSpinner(new SpinnerDateModel());
		timeSpinnerFin.setBounds(88, 19, 81, 24);
		timeSpinnerFin.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JSpinner.DateEditor de_timeSpinnerInicio = new JSpinner.DateEditor(timeSpinnerFin, "HH:mm");
		timeSpinnerFin.setEditor(de_timeSpinnerInicio);
		
		//timeSpinnerFin.setValue(new Date());
		timeSpinnerFin.setValue(cita.gethFin()); // Le ponemos la hora que tenía asignada del administrador
		
		return timeSpinnerFin;
	}
	
	

	private JSpinner getTimeSpinnerInicio() {
		timeSpinnerInicio = new JSpinner(new SpinnerDateModel());
		timeSpinnerInicio.setBounds(88, 19, 81, 24);
		timeSpinnerInicio.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JSpinner.DateEditor de_timeSpinnerInicio = new JSpinner.DateEditor(timeSpinnerInicio, "HH:mm");
		timeSpinnerInicio.setEditor(de_timeSpinnerInicio);
		
		//timeSpinnerInicio.setValue(new Date());
		timeSpinnerInicio.setValue(cita.gethInicio()); // Le ponemos la hora que tenía asignada del administrador
		
		return timeSpinnerInicio;
	}
	
	
	
	/**
	 * Método que me lleva a la ventana para crear una nueva preinscripcion. Pasamos a la ventana de crear una nueva preinscripcion
	 */
	protected void preinscripcion() {
		
//		AnadirPreinscripcion ventanaPreinscripcion = new AnadirPreinscripcion(this);
//		
//		
//		ventanaPreinscripcion.setLocationRelativeTo(null);
//		ventanaPreinscripcion.setResizable(true);
//		ventanaPreinscripcion.setModal(true); // hasta que no se cierre una ventana no se puede abrir otra
//		ventanaPreinscripcion.setVisible(true);

		
	}
	
	
	private JLabel getLblNombre_1() {
		if (lblNombre_1 == null) {
			lblNombre_1 = new JLabel("Nombre:");
		}
		return lblNombre_1;
	}
	private JButton getBtnSeleccionar() {
		if (btnSeleccionar == null) {
			btnSeleccionar = new JButton("Seleccionar");
			btnSeleccionar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					
					seleccionarPreinscripcion();
					
				}
			});
		}
		return btnSeleccionar;
	}

	private JLabel getLabel() {
		if (label == null) {
			label = new JLabel("");
		}
		return label;
	}
	private JLabel getLabel_1() {
		if (label_1 == null) {
			label_1 = new JLabel("");
		}
		return label_1;
	}
	private JLabel getLabel_2() {
		if (label_2 == null) {
			label_2 = new JLabel("");
		}
		return label_2;
	}
	private JLabel getLabel_3() {
		if (label_3 == null) {
			label_3 = new JLabel("");
		}
		return label_3;
	}
	private JPanel getPnPreinscripcionCantidad() {
		if (pnPreinscripcionCantidad == null) {
			pnPreinscripcionCantidad = new JPanel();
			pnPreinscripcionCantidad.setLayout(new GridLayout(0, 2, 0, 0));
			pnPreinscripcionCantidad.add(getLblCantidad());
			pnPreinscripcionCantidad.add(getSpinnerCantidad());
		}
		return pnPreinscripcionCantidad;
	}
	private JSpinner getSpinnerCantidad() {
		if (spinnerCantidad == null) {
			spinnerCantidad = new JSpinner();
			spinnerCantidad.setModel(new SpinnerNumberModel(new Integer(1), null, null, new Integer(1)));
		}
		return spinnerCantidad;
	}
	private JLabel getLblCantidad() {
		if (lblCantidad == null) {
			lblCantidad = new JLabel("Cantidad:");
			lblCantidad.setBackground(Color.WHITE);
		}
		return lblCantidad;
	}
	private JPanel getPnDuracion() {
		if (pnDuracion == null) {
			pnDuracion = new JPanel();
			pnDuracion.setLayout(new GridLayout(0, 2, 0, 0));
			pnDuracion.add(getLblDuracinenDas());
			pnDuracion.add(getSpinnerDuracion());
		}
		return pnDuracion;
	}
	private JLabel getLblDuracinenDas() {
		if (lblDuracinenDas == null) {
			lblDuracinenDas = new JLabel("Duraci\u00F3n (en d\u00EDas):");
		}
		return lblDuracinenDas;
	}
	private JSpinner getSpinnerDuracion() {
		if (spinnerDuracion == null) {
			spinnerDuracion = new JSpinner();
			spinnerDuracion.setModel(new SpinnerNumberModel(new Integer(1), null, null, new Integer(1)));
		}
		return spinnerDuracion;
	}
	private JPanel getPnIntervalo() {
		if (pnIntervalo == null) {
			pnIntervalo = new JPanel();
			pnIntervalo.setLayout(new GridLayout(0, 2, 0, 0));
			pnIntervalo.add(getLblIntervalo());
			pnIntervalo.add(getSpinnerIntervalo());
		}
		return pnIntervalo;
	}
	private JLabel getLblIntervalo() {
		if (lblIntervalo == null) {
			lblIntervalo = new JLabel("Intervalo (en horas):");
		}
		return lblIntervalo;
	}
	private JSpinner getSpinnerIntervalo() {
		if (spinnerIntervalo == null) {
			spinnerIntervalo = new JSpinner();
		}
		return spinnerIntervalo;
	}
	private JPanel getPnInstrucciones() {
		if (pnInstrucciones == null) {
			pnInstrucciones = new JPanel();
			pnInstrucciones.setLayout(new GridLayout(0, 2, 0, 0));
			pnInstrucciones.add(getLblInstrucciones());
			pnInstrucciones.add(getScrollPane());
			//pnInstrucciones.add(getScrollPane());
			//pnInstrucciones.add(getTextArea());
		}
		return pnInstrucciones;
	}
	private JLabel getLblInstrucciones() {
		if (lblInstrucciones == null) {
			lblInstrucciones = new JLabel("Instrucciones:");
		}
		return lblInstrucciones;
	}


	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			//scrollPane.setBounds(571, 24, 190, 112);
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
	
	
	/**
	 * Metodo que me devuelve la preinscripcion
	 * @return the preinscripcion
	 */
	public Preinscripcion getPreinscripcion() {
		return preinscripcion;
	}
	
	
	/**
	 * Método que me permite guardar la preinscripcion
	 * @param preinscripcion the preinscripcion to set
	 */
	public void setPreinscripcion(Preinscripcion preinscripcion) {
		this.preinscripcion = preinscripcion;
	}
	
	
	/**
	 * Método para seleccionar una preinscripcion
	 */
	protected void seleccionarPreinscripcion() {
		Preinscripcion pSeleccionada = (Preinscripcion)getCbPreinscripciones().getSelectedItem(); // La preinscripcion que hay seleccionada en el comboBox
		preinscripcion = pSeleccionada;	
	}
	
	
	
	
	

	/**
	 * Método que me toma los datos de la preinscripcion y me guarda una nueva asignaPreinscripcion
	 * @throws SQLException 
	 * 
	 */
	private void anadirLaPreinscripcion() throws SQLException {
		
		Random r = new Random();
		String codAsignaPreinscripcion = "" + r.nextInt(800);
		
		String codigoHistorial = paciente.getHistorial();
		String codigoPreinscripcion = preinscripcion.getNombre();
		
		boolean medicamento = preinscripcion.isMedicamento();
		
		int cantidad = 0;
		int intervalo = 0;
		int duracion = 0;
		
		if (medicamento) {
			cantidad = (int) getSpinnerCantidad().getValue();
			duracion= (int) getSpinnerDuracion().getValue();
			intervalo = (int)getSpinnerIntervalo().getValue();	
		}
		
		String instrucciones = getTextArea().getText();	
		
		Date date = new Date();	
		Time time = new Time(new Date().getTime());
		
		AsignaPreinscripcion ap = new AsignaPreinscripcion(codAsignaPreinscripcion, codigoHistorial, cita.getCodMed(),codigoPreinscripcion, cantidad, intervalo, duracion, instrucciones, date, time);
		
		pbd.nuevaAsignaPreinscripcion(ap);
		
	}
	
	
	private String darCausas() throws SQLException {
		
		HistorialMedico hm = pbd.verHistorial(paciente.getHistorial());
		String causas = pbd.verCausas(hm.getHistorial());
		return causas;
		
	}
	
	private JLabel getLblCausas() {
		if (lblCausas == null) {
			lblCausas = new JLabel("Causas:");
		}
		return lblCausas;
	}
	private JTextField getTxtCausas()  {
		if (txtCausas == null) {
			txtCausas = new JTextField();
			txtCausas.setColumns(10);
			
			
			try {
				if (!darCausas().equals("")) {
					txtCausas.setText(darCausas());
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return txtCausas;
	}
	
	
	
	private JPanel getPanelAcudio() {
		if (panelAcudio == null) {
			panelAcudio = new JPanel();
			panelAcudio.add(getRdbtnAcudio());
			panelAcudio.add(getHorizontalStrut_1());
			panelAcudio.add(getRdbtnNoAcudio());
		}
		return panelAcudio;
	}
	private Component getHorizontalStrut_1() {
		if (horizontalStrut == null) {
			horizontalStrut = Box.createHorizontalStrut(40);
		}
		return horizontalStrut;
	}
	
	
	private JRadioButton getRdbtnAcudio() {
		if (rdbtnAcudio == null) {
			rdbtnAcudio = new JRadioButton("Acudio");
			rdbtnAcudio.addMouseListener(new MouseAdapter() {
				int clickCountAcudio = 0;
				@Override
				public void mouseClicked(MouseEvent e) {
					
					if(rdbtnAcudio.isSelected() && ++clickCountAcudio % 2 == 0) {
						buttonGroup.clearSelection();
						clickCountAcudio = 0;
					}
					
				}
			});
			buttonGroup.add(rdbtnAcudio);
		}
		return rdbtnAcudio;
	}
	private JRadioButton getRdbtnNoAcudio() {
		if (rdbtnNoAcudio == null) {
			rdbtnNoAcudio = new JRadioButton("No Acudio");
			rdbtnNoAcudio.addMouseListener(new MouseAdapter() {
				int clickCountNoAcudio = 0;
				@Override
				public void mouseClicked(MouseEvent e) {
					
					if(rdbtnNoAcudio.isSelected() && ++clickCountNoAcudio % 2 == 0) {
						buttonGroup.clearSelection();
						clickCountNoAcudio = 0;
					}
					
				}
			});
			buttonGroup.add(rdbtnNoAcudio);
		}
		return rdbtnNoAcudio;
	}
}