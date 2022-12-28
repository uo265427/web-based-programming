package ui.admin;

import java.awt.BorderLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import logica.servicios.ParserBaseDeDatos;
import ui.AnadirAntecedentesHistorial;
import ui.inicio.VentanaInicio;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.JSpinner;

import logica.Accion;
import logica.AccionEmpleado;
import logica.Acompañante;
import logica.Cita;
import logica.Email;
import logica.Equipo;
import logica.Paciente;
import logica.empleados.Empleado;
import logica.empleados.Enfermero;
import logica.empleados.Medico;

import javax.swing.JComboBox;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.sql.Time;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import com.toedter.calendar.JDateChooser;
import javax.swing.JCheckBox;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import javax.swing.ListSelectionModel;
import net.miginfocom.swing.MigLayout;
import javax.swing.SwingConstants;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.border.LineBorder;

public class PanelCitas extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JPanel panelArriba_1;
	private JPanel pnMedico;
	private JPanel pnContactoFecha;
	private JPanel panelAbajo1;
	private JPanel panelAbajo2;
	private JLabel lblHoraInicio;
	private JLabel lblHoraFin;
	private JLabel lblFecha;
	private ParserBaseDeDatos pbd = new ParserBaseDeDatos();
	private VentanaInicio vi;
	private JScrollPane scrollPaneListaMedicos;
	private DefaultListModel<Medico> modeloListaM;
	private DefaultListModel<Paciente>modeloListaPaciente;
	private JList<Medico> listMedicos;
	private JDateChooser dateCita;
	private JScrollPane scrollPane_descripcion;
	private JTextArea textArea_descripcion;
	private JButton btnCrearCita;
	private JCheckBox chckbxEsUrgente;
	private ArrayList<Medico> medicos;
	private JSpinner timeSpinnerInicio;
	private JSpinner timeSpinnerFin;
	private JPanel panel;
	private JLabel lblNombreDatos;
	private JLabel lblApellidosDatos;
	private JLabel lblTelefonoDatos;
	private JLabel lblCorreoDatos;
	private JLabel lblInfoAdicionalDatos;
	private JTextField txtFieldCorreoDatos;
	private JTextField txtFieldTelefonoDatos;
	private JTextField txtFieldApellidosDatos;
	private JTextField txtFieldNombreDatos;
	private JScrollPane scrrlPaneInfoAdicional;
	private JTextArea txtAreaInfoAdicionalDatos;
	private JButton btnEditarTelefonoDatos;
	private JButton btnEditarCorreoDatos;
	private Paciente pacienteCita;
	private Acompañante acompañante;
	private DefaultListModel<Medico> modeloMedSelec;
	private DefaultListModel<Enfermero> modeloListEnf;
	private DefaultListModel<Enfermero> modeloEnfSelec;
	private ArrayList<Enfermero> enfermeros;
	
	List<String> salas;
	private String codAdmin;
	
	private JPanel panelEquipo;
	private JPanel panelDatosEquipoSeleccion;
	private JPanel pnDatosEquipo;
	private JPanel pnEquipoSeleccionado;
	private JPanel pnEquiposLista;
	private JScrollPane scrollPanelListaEquipos;
	private JList<Equipo> listEquipos;
	private JLabel lblNombreEquipo;
	private JButton btnFiltrarNombreEquipo;
	private JTextField txtNombreEquipo;
	private ArrayList<Equipo> equipos;
	private JScrollPane scrollPaneEquipoSeleccionado;
	private JList<Equipo> listEquipoSeleccionado;
	private DefaultListModel<Equipo> modeloEquipSelec;
	private DefaultListModel<Equipo> modeloListaE;
	private DefaultListModel<Equipo> modeloListaEquipo;
	private JButton quitarFiltroEquipo;
	 
	/**
	 * Create the dialog.
	 * 
	 * @throws SQLException
	 */

	public PanelCitas(String codAdmin) throws SQLException {
		setIconImage(Toolkit.getDefaultToolkit().getImage(AnadirAntecedentesHistorial.class.getResource("/img/logop.jpg")));
		this.codAdmin = codAdmin;

		setTitle("Administrativo: citas");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1055, 770);
		getContentPane().setLayout(new GridLayout(0, 1, 0, 0));
		getContentPane().add(getScrollPane_1_3());
		contentPanel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Citas", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		getContentPane().add(scrollPane_1);
		contentPanel.setLayout(new GridLayout(6, 1, 0, 0));
		/*{
			JPanel pnPacienteMedico = new JPanel();
			contentPanel.add(pnPacienteMedico);
			pnPacienteMedico.setLayout(new GridLayout(0, 1, 0, 0));
			{
				JPanel panelArriba_1 = new JPanel();
				panelArriba_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Paciente", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
				pnPacienteMedico.add(panelArriba_1);
				panelArriba_1.setLayout(new GridLayout(0, 2, 0, 0));
				panelArriba_1.add(getPnDatosPaciente());
				panelArriba_1.add(getScrollPane_1_2());
			}
			pnPacienteMedico.add(getPnMedico());
			pnPacienteMedico.add(getPanelEnfermero());
		}*/
		contentPanel.add(getPanelArriba_1());
		contentPanel.add(getPnMedico());
		contentPanel.add(getPanelEnfermero());
		contentPanel.add(getPanelEquipo());
		contentPanel.add(getPanelAbajo1());
		contentPanel.add(getPanelAbajo2());
		//contentPanel.add(getPnContactoFecha());
		
		
		//setContactData();
	}

	private JPanel getPanelArriba_1() {
		if(panelArriba_1 == null) {
			panelArriba_1 = new JPanel();
			panelArriba_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Paciente", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
			panelArriba_1.setLayout(new GridLayout(0, 2, 0, 0));
			panelArriba_1.add(getPnDatosPaciente());
			panelArriba_1.add(getScrollPane_1_2());
		}
		
		return panelArriba_1;
	}

	private JPanel getPnMedico() throws SQLException {
		if (pnMedico == null) {
			pnMedico = new JPanel();
			pnMedico.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Médico", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			pnMedico.setSize(new Dimension(219, 200));
			pnMedico.setLayout(new GridLayout(0, 2, 0, 0));
			pnMedico.add(getPnDatosMedicoSeleccion());
			pnMedico.add(getPnMedicosLista());
		}
		return pnMedico;
	}

	private JPanel getPnContactoFecha() {
		if (pnContactoFecha == null) {
			pnContactoFecha = new JPanel();
			pnContactoFecha.setLayout(new GridLayout(0, 1, 0, 0));
			pnContactoFecha.add(getPanelAbajo1());
			pnContactoFecha.add(getPanelAbajo2());
		}
		return pnContactoFecha;
	}

	private JPanel getPanelAbajo1() {
		if (panelAbajo1 == null) {
			panelAbajo1 = new JPanel();
			panelAbajo1.setLayout(new BorderLayout(0, 0));
			panelAbajo1.add(getPanel(), BorderLayout.CENTER);
		}
		return panelAbajo1;
	}

	private JPanel getPanelAbajo2() {
		if (panelAbajo2 == null) {
			panelAbajo2 = new JPanel();
			panelAbajo2.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Opciones de fecha", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panelAbajo2.setLayout(null);
			panelAbajo2.add(getScrollPane_descripcion());
			panelAbajo2.add(getBtnCrearCita());
			panelAbajo2.add(getLblHoraInicio());

			panelAbajo2.add(getSpinnerInicio());

			panelAbajo2.add(getLblHoraFin());

			panelAbajo2.add(getTimeSpinnerF());

			panelAbajo2.add(getLblFecha());
			panelAbajo2.add(getDateCita());
			panelAbajo2.add(getChckbxEsUrgente());
			panelAbajo2.add(getCbSala());
			panelAbajo2.add(getLblNewLabel());
			panelAbajo2.add(getLblNombreSala());
			panelAbajo2.add(getTxtNombreSala());
			panelAbajo2.add(getBtnFiltrar());

			

		}
		return panelAbajo2;
	}

	private JScrollPane getScrollPane_descripcion() {
		if (scrollPane_descripcion == null) {
			scrollPane_descripcion = new JScrollPane();
			scrollPane_descripcion.setBounds(0, 81, 409, -81);
			scrollPane_descripcion.setViewportView(getTextArea_1());
		}
		return scrollPane_descripcion;
	}

	private JTextArea getTextArea_1() {
		if (textArea_descripcion == null) {
			textArea_descripcion = new JTextArea();
			textArea_descripcion.setEditable(false);
			textArea_descripcion.setLineWrap(true);
			textArea_descripcion.setWrapStyleWord(true);
		}
		return textArea_descripcion;
	}

	private JLabel getLblHoraInicio() {
		if (lblHoraInicio == null) {
			lblHoraInicio = new JLabel("Hora Inicio:");
			lblHoraInicio.setBounds(12, 52, 66, 16);
		}
		return lblHoraInicio;
	}

	private JSpinner getSpinnerInicio() {
		timeSpinnerInicio = new JSpinner(new SpinnerDateModel());
		timeSpinnerInicio.setBounds(90, 48, 81, 24);
		timeSpinnerInicio.setFont(new Font("Tahoma", Font.PLAIN, 14));
		JSpinner.DateEditor de_timeSpinnerInicio = new JSpinner.DateEditor(timeSpinnerInicio, "HH:mm");
		timeSpinnerInicio.setEditor(de_timeSpinnerInicio);
		timeSpinnerInicio.setValue(new Date());
		timeSpinnerInicio.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if(camposCubiertos())
					btnCrearCita.setEnabled(true);
			}

		});
		return timeSpinnerInicio;
	}

	private JLabel getLblHoraFin() {
		if (lblHoraFin == null) {
			lblHoraFin = new JLabel("Hora Fin:");
			lblHoraFin.setBounds(205, 52, 53, 16);
		}
		return lblHoraFin;
	}

	private JSpinner getTimeSpinnerF() {
		timeSpinnerFin = new JSpinner(new SpinnerDateModel());
		timeSpinnerFin.setBounds(270, 48, 81, 24);
		timeSpinnerFin.setFont(new Font("Tahoma", Font.PLAIN, 14));
		JSpinner.DateEditor de_timeSpinnerFin = new JSpinner.DateEditor(timeSpinnerFin, "HH:mm");
		timeSpinnerFin.setEditor(de_timeSpinnerFin);
		timeSpinnerFin.setValue(new Date());
		timeSpinnerFin.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				if(camposCubiertos())
					btnCrearCita.setEnabled(true);
			}
		});

		return timeSpinnerFin;

	}

	private JLabel getLblFecha() {
		if (lblFecha == null) {
			lblFecha = new JLabel("Fecha:");
			lblFecha.setBounds(387, 52, 39, 16);
		}
		return lblFecha;
	}

	private JScrollPane getScrollPane_1() throws SQLException {
		if (scrollPaneListaMedicos == null) {
			scrollPaneListaMedicos = new JScrollPane();
			scrollPaneListaMedicos.setBounds(21, 23, 453, 178);
			scrollPaneListaMedicos.setOpaque(false);
			scrollPaneListaMedicos.setViewportView(getList_1());
		}
		return scrollPaneListaMedicos;
	}

	private DefaultListModel<Medico> modeloListaM(List<Medico> medico) throws SQLException {
		modeloListaM = new DefaultListModel<Medico>();
		if(medico!=null) {
		List<Medico> medicos = medico;
		for (int i = 0; i < medicos.size(); i++) {
			modeloListaM.addElement(medicos.get(i));

		}
		listMedicos.setModel(modeloListaM);
		}
		if(modeloListaM.getSize()==0)
			JOptionPane.showMessageDialog(null, "No se ha encontrado ningún médico con esas características");
		return modeloListaM;
	}
	
	private void  modeloListaSeleccionados(List<Medico>medicos){
		if(medicos!=null) {
		
		 
		for (int i = 0; i < medicos.size(); i++) {
			if(!modeloMedSelec.contains(medicos.get(i)))
			modeloMedSelec.addElement(medicos.get(i));
			
		}
		list.setModel(modeloMedSelec);
		
		}
		
		
	
	}
	
	
	private DefaultListModel<Paciente> modeloListaPaciente(List<Paciente> pacientes) throws SQLException {
		modeloListaPaciente = new DefaultListModel<Paciente>();
		if(pacientes!=null) {
		List<Paciente> paciente =pacientes;
		for (int i = 0; i < paciente.size(); i++) {
			modeloListaPaciente.addElement(paciente.get(i));

		}
		listPaciente.setModel(modeloListaPaciente);
		}
	if(modeloListaPaciente.getSize()==0)
			JOptionPane.showMessageDialog(null, "No se ha encontrado ningún paciente con esas características");
		
		return modeloListaPaciente;
	}

	private JDateChooser getDateCita() {
		if (dateCita == null) {
			dateCita = new JDateChooser();
			dateCita.setBounds(438, 42, 124, 30);
			dateCita.setDateFormatString("yyyy-MM-dd");
			dateCita.setDate(new Date());
			dateCita.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {

					if (dateCita.getDate() != null) {
						camposCubiertos();
					}
				}
			});

		}
		return dateCita;
	}

	private JButton getBtnCrearCita() {
		if (btnCrearCita == null) {
			btnCrearCita =new JButton("Crear cita");
			btnCrearCita.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {

					if (!camposCubiertos())
						JOptionPane.showMessageDialog(null,"Por favor,revise todos los campos han sido rellenados correctamente");
					else
						if(hora()) {
							if (checkMedico() && medicos.size() >0) {
								if(checkSala()) {
									crearCita();
									JOptionPane.showMessageDialog(null, "Su cita se ha generado con exito");
									dispose();
								}
							}
							if(equipos.size()>0){
								if(checkSala()) {
									crearCitaEquipo();
									JOptionPane.showMessageDialog(null, "Su cita se ha generado con exito");
									dispose();
								}
								
							}
							if(enfermeros.size()>0) {
								if(checkSala()) {
									crearCitaEnfermero();
									JOptionPane.showMessageDialog(null, "Su cita se ha generado con exito");
									dispose();
								}
							}
						}
				}
			});
			btnCrearCita.setEnabled(false);
			btnCrearCita.setBounds(779, 128, 97, 25);
		}
		return btnCrearCita;
	}

	protected void crearCitaEnfermero() {
		Date dateIncio = (Date) timeSpinnerInicio.getValue();
		Time timeInicio = new Time(dateIncio.getTime());

		Date dateFin = (Date) timeSpinnerFin.getValue();
		Time timeFin = new Time(dateFin.getTime());

		Date date = getDateCita().getDate();
		java.sql.Date sDate = new java.sql.Date(date.getTime());

		for (int i = 0; i < enfermeros.size(); i++) {
			
			String sala = (String) getCbSala().getSelectedItem();
			Cita c;
			try {
				c = new Cita(pacienteCita.getCodePaciente(), enfermeros.get(i).getCodeEmpleado(), timeInicio, timeFin, sDate, sala,
						chckbxEsUrgente.isSelected());
				pbd.crearCita(c);
				guardarAccionEnfermero();
				if(c.isUrgente())
					Email.enviarCorreo("roloalvarez7@gmail.com", "sbeiaolebhiewuzz", "UO266007@uniovi.es", pacienteCita, c);

			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		
	}
	
	
	
	
	private void guardarAccionEnfermero() throws SQLException {
		List<Accion> devolverAccionesAdmin = pbd.devolverAccionesAdmin();
		int numeroAccion = 1;
		if(devolverAccionesAdmin.size()>0) {
			numeroAccion = devolverAccionesAdmin.size() + 1;
		}
		String naccion = "" +numeroAccion;
		
		String nombrePaciente = pacienteCita.getNombre();
		String apellidoPaciente = pacienteCita.getApellido();
		
		Date fecha = new Date();	
		Time hora = new Time(new Date().getTime());	
		
		String infoMedicos  ="";
		
		for(int i =0; i<enfermeros.size(); i++) {
			if(i==enfermeros.size()) {
				infoMedicos += enfermeros.get(i).getNombre() + " " + enfermeros.get(i).getNombre();
			}
			else {
				infoMedicos += enfermeros.get(i).getNombre() + " " + enfermeros.get(i).getNombre() +",";
			}
			
		}
		
		String mensajeAccion = "Cita asignada a " + nombrePaciente + " " + apellidoPaciente + " con " + infoMedicos;
		
		Accion a = new Accion(naccion, codAdmin,  fecha, hora, mensajeAccion);
		
		pbd.guardarAccion(a);
	}

	private boolean checkMedico() {
		Date dateIncio = (Date) timeSpinnerInicio.getValue();
		Time timeInicio = new Time(dateIncio.getTime());
		List<Medico> medico= medicos= new ArrayList<Medico>();
		Object [] obj=modeloMedSelec.toArray();
		for (int i = 0; i < modeloMedSelec.getSize(); i++) {
			medico.add((Medico)obj[i]);
			
		}
		
		
		Date dateFin = (Date) timeSpinnerFin.getValue();
		Time timeFin = new Time(dateFin.getTime());
		boolean cita = false;
		int jornadares = -1;
		int citares = -1;
		boolean jornada = false;

		for (int i = 0; i < medico.size(); i++) {

			try {
				jornada = pbd.checkMedicoJornada(medico.get(i).getCodeEmpleado(), timeInicio, timeFin);
			} catch (SQLException e1) {
	
				e1.printStackTrace();
			}
			if (!jornada) {
				jornadares = JOptionPane.showConfirmDialog(null,
						"No hay médico disponible en esa jornada laboral,¿Desea crear la cita igualmente? ",
						"Advertencia: Creación cita", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

			}
			if (jornadares == 1)
				return false;
			try {

				cita = pbd.checkMedicoCita(medico.get(i).getCodeEmpleado(), timeInicio, timeFin);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if (cita) {
				citares = JOptionPane.showConfirmDialog(null,
						"No hay cita disponible en el hueco seleccionada, ¿Desea crear la cita igualmente? ",
						"Advertencia: Creación cita", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

			}
			if (citares == 1)
				return false;

		}
		return true;

	}
	
	private boolean checkSala(){
		Date dateIncio = (Date) timeSpinnerInicio.getValue();
		Time timeInicio = new Time(dateIncio.getTime());
		
		Date date = getDateCita().getDate();
		java.sql.Date sDate = new java.sql.Date(date.getTime());
		
		String sala = cbSala.getSelectedItem().toString();
		
		boolean haySala = false;
		
		int res = -1;
		
		try {
			haySala = pbd.buscarSalasIguales(timeInicio, sDate, sala);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(haySala) {
			System.out.println("sala igual");
			res = JOptionPane.showConfirmDialog(null,
					"No hay sala disponible en el hueco seleccionada, ¿Desea crear la cita igualmente? ",
					"Advertencia: Creación cita", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
		}
		
		if(res == 1) {
			return false;
		}
		return true;
		
	}

	private JCheckBox getChckbxEsUrgente() {
		if (chckbxEsUrgente == null) {
			chckbxEsUrgente = new JCheckBox("Es urgente");
			chckbxEsUrgente.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					
					if(chckbxEsUrgente.isSelected()) {
						System.out.println("Se ha enviado un correo al medico");
					JOptionPane.showMessageDialog(null, "Se ha enviado un correo al medico");
					}
				}
			});
			chckbxEsUrgente.setBounds(602, 48, 113, 25);
		}
		return chckbxEsUrgente;
	}

	private JList<Medico> getList_1() throws SQLException {
		if (listMedicos == null) {
			listMedicos = new JList<Medico>();
			listMedicos.setBackground(Color.WHITE);
			modeloListaM(pbd.buscarMedico(""));
			listMedicos.setModel(modeloListaM);
			listMedicos.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent event) {
					medicos = new ArrayList<Medico>();
					@SuppressWarnings("deprecation")
					Object[] selectedValues = listMedicos.getSelectedValues();
					if (selectedValues.length >= 0) {
						for (int i = 0; i < selectedValues.length; i++) {
							medicos.add((Medico) selectedValues[i]);
							

						}

				
						modeloListaSeleccionados(medicos);
					

					camposCubiertos();

					}}

			});
			
		}

		return listMedicos;
	}

	private boolean JlistMedicoFill() {
		if (medicos == null)
			return false;
		return medicos.size() > 0;
	}
	
	private boolean JlistEquipoFill() {
		if (equipos == null)
			return false;
		return equipos.size() > 0;
	}

	/*
	private boolean ComboBoxPacientes() {
		return comboBox.getSelectedItem() instanceof Paciente;

	}
	*/

	private boolean hora() {
		Date dateIncio = (Date) timeSpinnerInicio.getValue();
		Time timeInicio = new Time(dateIncio.getTime());

		Date dateFin = (Date) timeSpinnerFin.getValue();
		Time timeFin = new Time(dateFin.getTime());
		
		if (timeFin.compareTo(timeInicio) <= 0) {
			JOptionPane.showMessageDialog(null,
					"La fecha de inicio no puede ser igual o posterior a la fecha final.Por favor,modifíquelo y vuelva a intentarlo");
			btnCrearCita.setEnabled(false);
			return false;
		}
		return true;
	}

	
	
	private boolean ComboBoxSala() {
		return getCbSala().getSelectedItem() != null;

	}
	
	/**
	 * Metodo que comprueba que todos los cmapos obligatosio estan cubiertos y pone
	 * a enabled el boton de cita si asi es
	 * 
	 * @return
	 * @throws SQLException
	 */
	private boolean camposCubiertos() {
		if ((JlistMedicoFill()||JlistEquipoFill()||JlistEnfermeroFill()) &&ComboBoxSala() ) {
			btnCrearCita.setEnabled(true);
			return true;

		} else
			btnCrearCita.setEnabled(false);

		return false;

	}
	

	private boolean JlistEnfermeroFill() {
		if (enfermeros == null)
			return false;
		return enfermeros.size() > 0;
	}

	/**
	 * Metodo que crea la cita si tiene los cmapos cubiertos
	 * 
	 * @throws SQLException
	 */
	private void crearCita() {

		Date dateIncio = (Date) timeSpinnerInicio.getValue();
		Time timeInicio = new Time(dateIncio.getTime());

		Date dateFin = (Date) timeSpinnerFin.getValue();
		Time timeFin = new Time(dateFin.getTime());

		Date date = getDateCita().getDate();
		java.sql.Date sDate = new java.sql.Date(date.getTime());

		for (int i = 0; i < medicos.size(); i++) {
			System.out.println(medicos.size());
			
			String sala = (String) getCbSala().getSelectedItem();
			Cita c;
			try {
				c = new Cita(pacienteCita.getCodePaciente(), medicos.get(i).getCodeEmpleado(), timeInicio, timeFin, sDate, sala,
						chckbxEsUrgente.isSelected());
				pbd.crearCita(c);
				guardarAccion();
				if(c.isUrgente())
					Email.enviarCorreo("roloalvarez7@gmail.com", "sbeiaolebhiewuzz", "UO266007@uniovi.es", pacienteCita, c);

			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		
//		for (int i = 0; i < enfermeros.size(); i++) {
//			System.out.println(enfermeros.size());
//			
//			String sala = (String) getCbSala().getSelectedItem();
//			Cita c;
//			try {
//				c = new Cita(pacienteCita.getCodePaciente(), enfermeros.get(i).getCodeEmpleado(), timeInicio, timeFin, sDate, sala,
//						chckbxEsUrgente.isSelected());
//				pbd.crearCita(c);
//				guardarAccion();
//				if(c.isUrgente())
//					Email.enviarCorreo("roloalvarez7@gmail.com", "sbeiaolebhiewuzz", "UO266007@uniovi.es", pacienteCita, c);
//
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//
//		}
		
		
	}
	
	private void guardarAccionEquipo() throws SQLException {
		List<Accion> devolverAccionesAdmin = pbd.devolverAccionesAdmin();
		int numeroAccion = 1;
		if(devolverAccionesAdmin.size()>0) {
			numeroAccion = devolverAccionesAdmin.size() + 1;
		}
		String naccion = "" +numeroAccion;
		
		String nombrePaciente = pacienteCita.getNombre();
		String apellidoPaciente = pacienteCita.getApellido();
		
		Date fecha = new Date();	
		Time hora = new Time(new Date().getTime());	
		
		String infoMedicos  ="";
		
		for(int i =0; i<equipos.size(); i++) {
			if(i==equipos.size()) {
				infoMedicos += equipos.get(i).getNumEquipo();
			}
			else {
				infoMedicos += equipos.get(i).getNumEquipo() + ",";
			}
		}
		
		String mensajeAccion = "Cita asignada a " + nombrePaciente + " " + apellidoPaciente + " con " + infoMedicos;
		
		Accion a = new Accion(naccion, codAdmin,  fecha, hora, mensajeAccion);
		
		pbd.guardarAccion(a);
	}
	
	private void guardarAccion() throws SQLException {
		List<Accion> devolverAccionesAdmin = pbd.devolverAccionesAdmin();
		int numeroAccion = 1;
		if(devolverAccionesAdmin.size()>0) {
			numeroAccion = devolverAccionesAdmin.size() + 1;
		}
		String naccion = "" +numeroAccion;
		
		String nombrePaciente = pacienteCita.getNombre();
		String apellidoPaciente = pacienteCita.getApellido();
		
		Date fecha = new Date();	
		Time hora = new Time(new Date().getTime());	
		
		String infoMedicos  ="";
		
		for(int i =0; i<medicos.size(); i++) {
			if(i==equipos.size()) {
				infoMedicos += medicos.get(i).getNombre() + " " + medicos.get(i).getApellido();
			}
			else {
				infoMedicos += medicos.get(i).getNombre() + " " + medicos.get(i).getApellido() +",";
			}
			
		}
		
		String mensajeAccion = "Cita asignada a " + nombrePaciente + " " + apellidoPaciente + " con " + infoMedicos;
		
		Accion a = new Accion(naccion, codAdmin,  fecha, hora, mensajeAccion);
		
		pbd.guardarAccion(a);
	}

	
	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Datos de contacto", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
			panel.setLayout(null);
			panel.add(getLabel_1());
			panel.add(getLabel_5());
			panel.add(getLabel_2());
			panel.add(getLabel_3());
			panel.add(getLabel_4());
			panel.add(getTxtFieldCorreoDatos());
			panel.add(getTxtFieldTelefonoDatos());
			panel.add(getTxtFieldApellidosDatos());
			panel.add(getTxtFieldNombreDatos());
			//panel.add(getTextArea_1_1(), "flowx,cell 3 5 3 7");
			panel.add(getScrollPane_1_1());
			panel.add(getBtnEditarTelefonoDatos());
			panel.add(getBtnEditarCorreoDatos());
			panel.add(getBtnActualizarDatos());
		}
		return panel;
	}
	private JLabel getLabel_1() {
		if (lblNombreDatos == null) {
			lblNombreDatos = new JLabel("Nombre: ");
			lblNombreDatos.setBounds(27, 43, 54, 16);
		}
		return lblNombreDatos;
	}
	private JLabel getLabel_2() {
		if (lblApellidosDatos == null) {
			lblApellidosDatos = new JLabel("Apellidos:");
			lblApellidosDatos.setBounds(25, 86, 56, 16);
		}
		return lblApellidosDatos;
	}
	private JLabel getLabel_3() {
		if (lblTelefonoDatos == null) {
			lblTelefonoDatos = new JLabel("Tel\u00E9fono:");
			lblTelefonoDatos.setBounds(25, 129, 55, 16);
		}
		return lblTelefonoDatos;
	}
	private JLabel getLabel_4() {
		if (lblCorreoDatos == null) {
			lblCorreoDatos = new JLabel("Correo electr\u00F3nico:");
			lblCorreoDatos.setBounds(10, 173, 110, 16);
		}
		return lblCorreoDatos;
	}
	private JLabel getLabel_5() {
		if (lblInfoAdicionalDatos == null) {
			lblInfoAdicionalDatos = new JLabel("Informaci\u00F3n adicional:");
			lblInfoAdicionalDatos.setBounds(410, 43, 127, 16);
		}
		return lblInfoAdicionalDatos;
	}
	private JTextField getTxtFieldCorreoDatos() {
		if (txtFieldCorreoDatos == null) {
			txtFieldCorreoDatos = new JTextField();
			txtFieldCorreoDatos.setBounds(127, 170, 180, 22);
			txtFieldCorreoDatos.setEditable(false);
			txtFieldCorreoDatos.setColumns(10);
			
		}
		return txtFieldCorreoDatos;
	}
	private JTextField getTxtFieldTelefonoDatos() {
		if (txtFieldTelefonoDatos == null) {
			txtFieldTelefonoDatos = new JTextField();
			txtFieldTelefonoDatos.setBounds(127, 126, 180, 22);
			txtFieldTelefonoDatos.setEditable(false);
			txtFieldTelefonoDatos.setColumns(10);
		
		}
		return txtFieldTelefonoDatos;
	}
	private JTextField getTxtFieldApellidosDatos() {
		if (txtFieldApellidosDatos == null) {
			txtFieldApellidosDatos = new JTextField();
			txtFieldApellidosDatos.setBounds(127, 83, 180, 22);
			txtFieldApellidosDatos.setEditable(false);
			txtFieldApellidosDatos.setColumns(10);
			
		}
		return txtFieldApellidosDatos;
	}
	private JTextField getTxtFieldNombreDatos() {
		if (txtFieldNombreDatos == null) {
			txtFieldNombreDatos = new JTextField();
			txtFieldNombreDatos.setBounds(127, 40, 180, 22);
			txtFieldNombreDatos.setEditable(false);
			txtFieldNombreDatos.setColumns(10);
		}
		return txtFieldNombreDatos;
	}
	private JScrollPane getScrollPane_1_1() {
		if (scrrlPaneInfoAdicional == null) {
			scrrlPaneInfoAdicional = new JScrollPane();
			scrrlPaneInfoAdicional.setBounds(540, 28, 301, 161);
			scrrlPaneInfoAdicional.setViewportView(getTextArea_1_1());
		}
		return scrrlPaneInfoAdicional;
	}
	private JTextArea getTextArea_1_1() {
		if (txtAreaInfoAdicionalDatos == null) {
			txtAreaInfoAdicionalDatos = new JTextArea();
			txtAreaInfoAdicionalDatos.setLineWrap(true);
			txtAreaInfoAdicionalDatos.setWrapStyleWord(true);
		}
		return txtAreaInfoAdicionalDatos;
	}
	private JButton getBtnEditarTelefonoDatos() {
		if (btnEditarTelefonoDatos == null) {
			btnEditarTelefonoDatos = new JButton("Editar");
			btnEditarTelefonoDatos.setBounds(317, 125, 65, 25);
			btnEditarTelefonoDatos.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					txtFieldTelefonoDatos.setEditable(true);
				}
			});
		}
		return btnEditarTelefonoDatos;
	}
	private JButton getBtnEditarCorreoDatos() {
		if (btnEditarCorreoDatos == null) {
			btnEditarCorreoDatos = new JButton("Editar");
			btnEditarCorreoDatos.setBounds(321, 169, 65, 25);
			btnEditarCorreoDatos.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					txtFieldCorreoDatos.setEditable(true);
				}
			});
		}
		return btnEditarCorreoDatos;
	}
	
	/**
	 * Comprobamos si se han cambiado los campos de contacto
	 * @return
	 */
	
	private boolean checkCambiosInfo() {
		if(!(pacienteCita instanceof Paciente))
			return false;
		
		if(!getTxtFieldTelefonoDatos().getText().equals(""))
			return Integer.parseInt(getTxtFieldTelefonoDatos().getText()) != pacienteCita.getMovil() ||
			!(getTxtFieldCorreoDatos().getText().equals(pacienteCita.getEmail()));
		return false;
	}
	
	private void setContactData()  {
		if(acompañante instanceof Acompañante) {
			
			getTxtFieldNombreDatos().setText(acompañante.getNombre());
			getTxtFieldApellidosDatos().setText(acompañante.getApellido());
			getTxtFieldTelefonoDatos().setText(acompañante.getMovil()+"");
			getTxtFieldCorreoDatos().setText(acompañante.getEmail());
			//getTextArea_1_1().setText(acompañante.getInfo());
		}
		else {
			getTxtFieldNombreDatos().setText(pacienteCita.getNombre());
			getTxtFieldApellidosDatos().setText(pacienteCita.getApellido());
			getTxtFieldTelefonoDatos().setText(pacienteCita.getMovil()+"");
			getTxtFieldCorreoDatos().setText(pacienteCita.getEmail());
			}
	
	}
	
	
	private JButton btnActualizarDatos;
	private JPanel pnDatosPaciente;
	private JPanel pnDatosMedico;
	private JComboBox cbSala;
	private JLabel lblFiltroNombre;
	private JTextField txtFieldNombreFiltro;
	private JButton btnFiltrarNombre;
	private JLabel lblApellidoFiltro;
	private JTextField txtFieldApellidoFiltro;
	private JButton btnFiltrarApellidos;
	private JLabel lblNewLabel;
	private JScrollPane scrollPanePacientes;
	private JLabel lblCodHistorial;
	private JTextField txtFieldCodHistorial;
	private JButton btnFiltrarHistorial;
	private JLabel lblNombreFiltroMedico;
	private JLabel lblApellidoMedicoFiltro;
	private JTextField txtFieldNombreMedicoFiltro;
	private JTextField txtFieldApellidoMedicoFiltro;
	private JButton btnFiltrarNombreMedico;
	private JButton btnFiltrarApellidoMedico;
	private JList<Paciente> listPaciente;
	private JLabel lblNombreSala;
	private JTextField txtNombreSala;
	private JButton btnFiltrar;
	private JPanel pnMedicosLista;
	private JPanel pnDatosMedicoSeleccion;
	private JPanel panel_5;
	private JPanel pnMedicosSeleccionados;
	private JScrollPane scrollPane;
	private JList<Medico> list;
	private JScrollPane scrollPane_1;
	private JPanel panelEnfermero;
	private JPanel panelFiltrosSeleccEnfermero;
	private JScrollPane scrollPaneListaEnfermeros;
	private JList<Enfermero> list_1;
	private JPanel panelFiltrosEnf;
	private JPanel panelEnfSeleccionados;
	private JScrollPane scrrPaneSeleccionados;
	private JList listEnfSeleccionados;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JTextField txtFieldFiltroNomEnf;
	private JTextField txtFieldFiltroApeEmp;
	private JButton btnFiltroNomEmp;
	private JButton btnFiltroApeNum;
	private JButton btnQuitarFiltroPac;
	private JButton btnQuitarFiltro;
	private JButton btnNewButton;
	
	
	private JButton getBtnActualizarDatos() {
		if (btnActualizarDatos == null) {
			btnActualizarDatos = new JButton("Actualizar");
			btnActualizarDatos.setBounds(880, 179, 89, 25);
			btnActualizarDatos.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(checkCambiosInfo()) {
						if(pacienteCita instanceof Paciente)
							
						
							try {
								if(!getTxtFieldTelefonoDatos().getText().equals(""))
								ParserBaseDeDatos.updateDatosContacto(Integer.parseInt(getTxtFieldTelefonoDatos().getText()), getTxtFieldCorreoDatos().getText(),
										getTextArea_1_1().getText(),pacienteCita.getCodePaciente());
							} catch (NumberFormatException | SQLException e1) {
								e1.printStackTrace();
							}
						
					}
				}
			});
		}
		return btnActualizarDatos;
	}
	private JComboBox<String> getCbSala() {
		if (cbSala == null) {
			cbSala = new JComboBox<String>();
			cbSala.setBounds(90, 103, 236, 22);
			salas= rellenarSalas();
			String[] array = salas.toArray( new String[salas.size()] );
			cbSala.setModel(new DefaultComboBoxModel<String>(array));
			
			
			}
		
		return cbSala;
	}
	
	private List<String> rellenarSalas() {
		List<String> salas = new ArrayList<String>();
		for(int i = 1; i < 4; i++) {
			salas.add("Quirófano " + i);
			salas.add("Sala de rayos " + i);
			salas.add("Pediatría " + i);
		}

		salas.add("Digestivo");
		salas.add("Oncología");
		salas.add("Urología");
		salas.add("Cardiología");
		return salas;
	}
	
	private JPanel getPnDatosPaciente() {
		if (pnDatosPaciente == null) {
			pnDatosPaciente = new JPanel();
			pnDatosPaciente.setLayout(new MigLayout("", "[93px][222px][82px]", "[23px][][23px][23px][]"));
			pnDatosPaciente.add(getLblFiltroNombre(), "cell 0 1,alignx center,aligny center");
			pnDatosPaciente.add(getTxtFieldNombreFiltro(), "cell 1 1,growx,aligny top");
			pnDatosPaciente.add(getBtnFiltrarNombre(), "cell 2 1,grow");
			pnDatosPaciente.add(getLblApellidoFiltro(), "cell 0 2,alignx center,aligny center");
			pnDatosPaciente.add(getTxtFieldApellidoFiltro(), "cell 1 2,growx,aligny top");
			pnDatosPaciente.add(getBtnFiltrarApellidos(), "cell 2 2,grow");
			pnDatosPaciente.add(getLblCodHistorial(), "cell 0 3,alignx left,aligny center");
			pnDatosPaciente.add(getTxtFieldCodHistorial(), "cell 1 3,growx,aligny top");
			pnDatosPaciente.add(getBtnFiltrarHistorial(), "cell 2 3,grow");
			pnDatosPaciente.add(getBtnQuitarFiltroPac(), "cell 2 4");
		}
		return pnDatosPaciente;
	}
	private JPanel getPnDatosMedico() {
		if (pnDatosMedico == null) {
			pnDatosMedico = new JPanel();
			pnDatosMedico.setLayout(new MigLayout("", "[152px][152px][152px]", "[17px][17px][17px][17px]"));
			pnDatosMedico.add(getLblNombreFiltroMedico(), "flowx,cell 0 1,grow");
			pnDatosMedico.add(getTextField_2(), "cell 1 1,grow");
			pnDatosMedico.add(getBtnFiltrarNombreMedico(), "cell 2 1,grow");
			pnDatosMedico.add(getLblApellidoMedicoFiltro(), "cell 0 2,grow");
			pnDatosMedico.add(getTextField_1_1(), "cell 1 2,grow");
			pnDatosMedico.add(getBtnFiltrarApellidoMedico(), "cell 2 2,grow");
			pnDatosMedico.add(getBtnQuitarFiltro(), "cell 2 3");
		}
		return pnDatosMedico;
	}
	private JLabel getLblFiltroNombre() {
		if (lblFiltroNombre == null) {
			lblFiltroNombre = new JLabel("Nombre:");
		}
		return lblFiltroNombre;
	}
	private JTextField getTxtFieldNombreFiltro() {
		if (txtFieldNombreFiltro == null) {
			txtFieldNombreFiltro = new JTextField();
			txtFieldNombreFiltro.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
						btnFiltrarNombre.setEnabled(true);
				}
			});
			txtFieldNombreFiltro.setColumns(10);
		}
		return txtFieldNombreFiltro;
	}
	private JButton getBtnFiltrarNombre() {
		if (btnFiltrarNombre == null) {
			btnFiltrarNombre = new JButton("Filtrar");
			btnFiltrarNombre.setEnabled(false);
			btnFiltrarNombre.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						modeloListaPaciente(pbd.buscarPacienteNombre(txtFieldNombreFiltro.getText()));
						txtFieldNombreFiltro.setText("");
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					
				}
			});
		}
		return btnFiltrarNombre;
	}
	private JLabel getLblApellidoFiltro() {
		if (lblApellidoFiltro == null) {
			lblApellidoFiltro = new JLabel("Apellido:");
		}
		return lblApellidoFiltro;
	}
	private JTextField getTxtFieldApellidoFiltro() {
		if (txtFieldApellidoFiltro == null) {
			txtFieldApellidoFiltro = new JTextField();
			txtFieldApellidoFiltro.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {						btnFiltrarApellidos.setEnabled(true);
					
				}
			});
			txtFieldApellidoFiltro.setColumns(10);
		}
		return txtFieldApellidoFiltro;
	}
	private JButton getBtnFiltrarApellidos() {
		if (btnFiltrarApellidos == null) {
			btnFiltrarApellidos = new JButton("Filtrar");
			btnFiltrarApellidos.setEnabled(false);
			btnFiltrarApellidos.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try {
						modeloListaPaciente(pbd.buscarPacienteApellido(txtFieldApellidoFiltro.getText()));
						txtFieldApellidoFiltro.setText("");
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
		}
		return btnFiltrarApellidos;
	}
	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("Sala:");
			lblNewLabel.setBounds(32, 107, 46, 14);
		}
		return lblNewLabel;
	}
	private JScrollPane getScrollPane_1_2() {
		if (scrollPanePacientes == null) {
			scrollPanePacientes = new JScrollPane();
			scrollPanePacientes.setViewportView(getList_1_1());
			scrollPanePacientes.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Seleccione al paciente", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		}
		return scrollPanePacientes;
	}
	private JLabel getLblCodHistorial() {
		if (lblCodHistorial == null) {
			lblCodHistorial = new JLabel("Codigo historial:");
		}
		return lblCodHistorial;
	}
	private JTextField getTxtFieldCodHistorial() {
		if (txtFieldCodHistorial == null) {
			txtFieldCodHistorial = new JTextField();
			txtFieldCodHistorial.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {					
					btnFiltrarHistorial.setEnabled(true);
				}
			});
			txtFieldCodHistorial.setColumns(10);
		}
		return txtFieldCodHistorial;
	}
	private JButton getBtnFiltrarHistorial() {
		if (btnFiltrarHistorial == null) {
			btnFiltrarHistorial = new JButton("Filtrar");
			btnFiltrarHistorial.setEnabled(false);
			btnFiltrarHistorial.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					try {
					List<Paciente> p=new ArrayList<Paciente>();
					p.add(pbd.pacienteHistorial(txtFieldCodHistorial.getText()));
						modeloListaPaciente(p);
						txtFieldCodHistorial.setText("");
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			});
		}
		return btnFiltrarHistorial;
	}
	private JLabel getLblNombreFiltroMedico() {
		if (lblNombreFiltroMedico == null) {
			lblNombreFiltroMedico = new JLabel("Nombre:");
		}
		return lblNombreFiltroMedico;
	}
	private JLabel getLblApellidoMedicoFiltro() {
		if (lblApellidoMedicoFiltro == null) {
			lblApellidoMedicoFiltro = new JLabel("Apellido:");
		}
		return lblApellidoMedicoFiltro;
	}
	private JTextField getTextField_2() {
		if (txtFieldNombreMedicoFiltro == null) {
			txtFieldNombreMedicoFiltro = new JTextField();
			txtFieldNombreMedicoFiltro.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					btnFiltrarNombreMedico.setEnabled(true);
				}
			});
				
			
			
			txtFieldNombreMedicoFiltro.setColumns(10);
		}
		return txtFieldNombreMedicoFiltro;
	}
	private JTextField getTextField_1_1() {
		if (txtFieldApellidoMedicoFiltro == null) {
			txtFieldApellidoMedicoFiltro = new JTextField();
			txtFieldApellidoMedicoFiltro.setColumns(10);
			txtFieldApellidoMedicoFiltro.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					btnFiltrarApellidoMedico.setEnabled(true);
				}
				
			});
			
		}
		return txtFieldApellidoMedicoFiltro;
	}
	private JButton getBtnFiltrarNombreMedico() {
		if (btnFiltrarNombreMedico == null) {
			btnFiltrarNombreMedico = new JButton("Filtrar");
			btnFiltrarNombreMedico.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(txtFieldNombreMedicoFiltro.getText().equals(""))
						JOptionPane.showMessageDialog(null, "Por favor introduce un valor");
					else {	
					
						try {
							modeloListaM(pbd.devolverMedicoNombre(txtFieldNombreMedicoFiltro.getText()));
							txtFieldNombreMedicoFiltro.setText("");
							
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						
						
					
				}
				}});
			btnFiltrarNombreMedico.setEnabled(false);
		}
		return btnFiltrarNombreMedico;
	}
	private JButton getBtnFiltrarApellidoMedico() {
		if (btnFiltrarApellidoMedico == null) {
			btnFiltrarApellidoMedico = new JButton("Filtrar");
			btnFiltrarApellidoMedico.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					if(txtFieldApellidoMedicoFiltro.getText().equals(""))
					JOptionPane.showMessageDialog(null, "Por favor introduce un valor");
				else {	
				
					try {
						modeloListaM(pbd.devolverMedicoApellido(txtFieldApellidoMedicoFiltro.getText()));
						txtFieldApellidoMedicoFiltro.setText("");
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					
				}
				}
			});
			btnFiltrarApellidoMedico.setEnabled(false);
		}
		return btnFiltrarApellidoMedico;
	}
	private JList<Paciente> getList_1_1() {
		if (listPaciente == null) {
			listPaciente = new JList<Paciente>();
			modeloListaPaciente=new DefaultListModel<Paciente>();
			try {
				modeloListaPaciente(pbd.buscarPaciente(""));
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			listPaciente.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			try {
				modeloListaPaciente(pbd.buscarPaciente(""));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			listPaciente.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					
					 pacienteCita= listPaciente.getSelectedValue();
					 try {
						acompañante = pbd.getAcompañante(pacienteCita.getCodePaciente());
					} catch (SQLException e) {
						e.printStackTrace();
					}
					 setContactData();
				}
			
		});
		}
		return listPaciente;
	}
	private JLabel getLblNombreSala() {
		if (lblNombreSala == null) {
			lblNombreSala = new JLabel("Nombre sala");
			lblNombreSala.setBounds(399, 103, 72, 22);
		}
		return lblNombreSala;
	}
	private JTextField getTxtNombreSala() {
		if (txtNombreSala == null) {
			txtNombreSala = new JTextField();
			txtNombreSala.setBounds(483, 102, 147, 25);
			txtNombreSala.setColumns(10);
		}
		return txtNombreSala;
	}
	private JButton getBtnFiltrar() {
		if (btnFiltrar == null) {
			btnFiltrar = new JButton("Filtrar");
			btnFiltrar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(txtNombreSala.getText().equals(""))
						JOptionPane.showMessageDialog(null, "Por favor introduce un valor");
					else {	
						filtrarPorNombre(txtNombreSala.getText());
				}
				}
			});
			btnFiltrar.setHorizontalAlignment(SwingConstants.RIGHT);
			btnFiltrar.setBounds(642, 103, 66, 23);
		}
		return btnFiltrar;
	}
	
	private void filtrarPorNombre(String name) {
		for(int i=0; i < salas.size(); i++) {
			if(salas.get(i).equals(name)) {
				cbSala.setSelectedIndex(i);
			}
		}
	}
	private JPanel getPnMedicosLista() throws SQLException {
		if (pnMedicosLista == null) {
			pnMedicosLista = new JPanel();
			pnMedicosLista.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Seleccione los médicos", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			pnMedicosLista.setLayout(null);
			pnMedicosLista.add(getScrollPane_1());
		}
		return pnMedicosLista;
	}
	private JPanel getPnDatosMedicoSeleccion() {
		if (pnDatosMedicoSeleccion == null) {
			pnDatosMedicoSeleccion = new JPanel();
			pnDatosMedicoSeleccion.setLayout(new GridLayout(2, 0, 0, 0));
			pnDatosMedicoSeleccion.add(getPnDatosMedico());
			pnDatosMedicoSeleccion.add(getPnMedicosSeleccionados());
		}
		return pnDatosMedicoSeleccion;
	}
	
	private JPanel getPnMedicosSeleccionados() {
		if (pnMedicosSeleccionados == null) {
			pnMedicosSeleccionados = new JPanel();
			pnMedicosSeleccionados.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Médicos seleccionados", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			pnMedicosSeleccionados.setLayout(null);
			pnMedicosSeleccionados.add(getScrollPane());
			
		}
		return pnMedicosSeleccionados;
	}
	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setBounds(new Rectangle(12, 23, 476, 72));
			scrollPane.setViewportView(getList());
		}
		return scrollPane;
	}
	private JList<Medico> getList() {
		if (list == null) {
			list = new JList<Medico>();
			modeloMedSelec= new DefaultListModel<Medico>();
			list.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if(e.getClickCount()==2) {
						
							Medico med=(Medico) list.getSelectedValue();
							int res=JOptionPane.showConfirmDialog(null, "¿Esta seguro de que desea borrar este médico?","Confirmación de eliminación", JOptionPane.YES_NO_OPTION);
							if(res==JOptionPane.YES_OPTION)	
								modeloMedSelec.removeElement(med);
				}
					}
			});
		
		}
		return list;
	}
	private JScrollPane getScrollPane_1_3() {
		if (scrollPane_1 == null) {
			scrollPane_1 = new JScrollPane();
			scrollPane_1.setViewportView(contentPanel);
			scrollPane_1.getVerticalScrollBar().setUnitIncrement(16);
		}
		return scrollPane_1;
	}
	private JPanel getPanelEnfermero() {
		if (panelEnfermero == null) {
			panelEnfermero = new JPanel();
			panelEnfermero.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Enfermero", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
			panelEnfermero.setLayout(new GridLayout(0, 2, 0, 0));
			panelEnfermero.add(getPanelFiltrosSeleccEnfermero());
			panelEnfermero.add(getScrollPaneListaEnfermeros());
		}
		return panelEnfermero;
	}
	private JPanel getPanelFiltrosSeleccEnfermero() {
		if (panelFiltrosSeleccEnfermero == null) {
			panelFiltrosSeleccEnfermero = new JPanel();
			panelFiltrosSeleccEnfermero.setLayout(new GridLayout(2, 0, 0, 0));
			panelFiltrosSeleccEnfermero.add(getPanelFiltrosEnf());
			panelFiltrosSeleccEnfermero.add(getPanelEnfSeleccionados());
		}
		return panelFiltrosSeleccEnfermero;
	}
	private JScrollPane getScrollPaneListaEnfermeros() {
		if (scrollPaneListaEnfermeros == null) {
			scrollPaneListaEnfermeros = new JScrollPane();
			scrollPaneListaEnfermeros.setViewportView(getList_1_2());
			scrollPaneListaEnfermeros.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Seleccione el enfermero", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		}
		return scrollPaneListaEnfermeros;
	}
	
	private JList<Enfermero> getList_1_2() {
		if (list_1 == null) {
			list_1 = new JList<Enfermero>();
			modeloListEnf = new DefaultListModel<Enfermero>();
			enfermeros = new ArrayList<Enfermero>();
			try {
				 modeloListEnf.addAll(pbd.listarEnfermero());
			} catch (SQLException e) {
				e.printStackTrace();
			}
			list_1.setModel(modeloListEnf);
			list_1.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent event) {
					
					@SuppressWarnings("deprecation")
					Object[] selectedValues = list_1.getSelectedValues();
					if (selectedValues.length >= 0) {
						for (int i = 0; i < selectedValues.length; i++) {
							enfermeros.add((Enfermero) selectedValues[i]);
						}

				
						for (int i = 0; i < enfermeros.size(); i++) {
							if(!modeloEnfSelec.contains(enfermeros.get(i)))
							modeloEnfSelec.addElement(enfermeros.get(i));
							
						}
						listEnfSeleccionados.setModel(modeloEnfSelec);
						
						}
					

					camposCubiertos();

					}

			});
		}
		return list_1;
	}
	
	private JPanel getPanelFiltrosEnf() {
		if (panelFiltrosEnf == null) {
			panelFiltrosEnf = new JPanel();
			panelFiltrosEnf.setLayout(new MigLayout("", "[68px][225px][89px]", "[23px][23px][]"));
			panelFiltrosEnf.add(getLblNewLabel_1(), "cell 0 0,growx,aligny center");
			panelFiltrosEnf.add(getTxtFieldFiltroNomEnf(), "cell 1 0,growx,aligny center");
			panelFiltrosEnf.add(getBtnFiltroNomEmp(), "cell 2 0,growx,aligny top");
			panelFiltrosEnf.add(getLblNewLabel_2(), "cell 0 1,growx,aligny center");
			panelFiltrosEnf.add(getTxtFieldFiltroApeEmp(), "cell 1 1,growx,aligny center");
			panelFiltrosEnf.add(getBtnFiltroApeNum(), "cell 2 1,growx,aligny top");
			panelFiltrosEnf.add(getBtnNewButton(), "cell 2 2");
		}
		return panelFiltrosEnf;
	}
	private JPanel getPanelEnfSeleccionados() {
		if (panelEnfSeleccionados == null) {
			panelEnfSeleccionados = new JPanel();
			panelEnfSeleccionados.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Enfermeros seleccionados", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panelEnfSeleccionados.setLayout(null);
			panelEnfSeleccionados.add(getScrrPaneSeleccionados());
		}
		return panelEnfSeleccionados;
	}
	private JScrollPane getScrrPaneSeleccionados() {
		if (scrrPaneSeleccionados == null) {
			scrrPaneSeleccionados = new JScrollPane();
			scrrPaneSeleccionados.setBounds(10, 21, 478, 74);
			scrrPaneSeleccionados.setViewportView(getListEnfSeleccionados());
		}
		return scrrPaneSeleccionados;
	}
	private JList getListEnfSeleccionados() {
		if (listEnfSeleccionados == null) {
			listEnfSeleccionados = new JList();
			modeloEnfSelec= new DefaultListModel<Enfermero>();
			listEnfSeleccionados.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if(e.getClickCount()==2) {
						
							Enfermero enf=(Enfermero) listEnfSeleccionados.getSelectedValue();
							int res=JOptionPane.showConfirmDialog(null, "¿Esta seguro de que desea borrar este enfermero?","Confirmación de eliminación", JOptionPane.YES_NO_OPTION);
							if(res==JOptionPane.YES_OPTION)	
								modeloEnfSelec.removeElement(enf);
				}
					}
			});
		}
		return listEnfSeleccionados;
	}
	private JLabel getLblNewLabel_1() {
		if (lblNewLabel_1 == null) {
			lblNewLabel_1 = new JLabel("Nombre:");
		}
		return lblNewLabel_1;
	}
	private JLabel getLblNewLabel_2() {
		if (lblNewLabel_2 == null) {
			lblNewLabel_2 = new JLabel("Apellidos:");
		}
		return lblNewLabel_2;
	}
	private JTextField getTxtFieldFiltroNomEnf() {
		if (txtFieldFiltroNomEnf == null) {
			txtFieldFiltroNomEnf = new JTextField();
			txtFieldFiltroNomEnf.setColumns(10);
			txtFieldFiltroNomEnf.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					btnFiltroNomEmp.setEnabled(true);
				}
			});
		}
		return txtFieldFiltroNomEnf;
	}
	private JTextField getTxtFieldFiltroApeEmp() {
		if (txtFieldFiltroApeEmp == null) {
			txtFieldFiltroApeEmp = new JTextField();
			txtFieldFiltroApeEmp.setColumns(10);
			txtFieldFiltroApeEmp.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					btnFiltroApeNum.setEnabled(true);
				}
			});
		}
		return txtFieldFiltroApeEmp;
	}
	private JButton getBtnFiltroNomEmp() {
		if (btnFiltroNomEmp == null) {
			btnFiltroNomEmp = new JButton("Filtrar");
			btnFiltroNomEmp.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						modeloListaEnfermero(pbd.buscarNombreEnfermero(txtFieldFiltroNomEnf.getText()));
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
			btnFiltroNomEmp.setEnabled(false);
		}
		return btnFiltroNomEmp;
	}
	
	private DefaultListModel<Enfermero> modeloListaEnfermero(List<Enfermero> enfermeros) throws SQLException {
		modeloListEnf = new DefaultListModel<Enfermero>();
		if(enfermeros!=null) {
		List<Enfermero> enfermero =enfermeros;
		for (int i = 0; i < enfermero.size(); i++) {
			modeloListEnf.addElement(enfermero.get(i));

		}
		list_1.setModel(modeloListEnf);
		}
	if(modeloListaPaciente.getSize()==0)
			JOptionPane.showMessageDialog(null, "No se ha encontrado ningún paciente con esas características");
		
		return modeloListEnf;
	}
	
	private JButton getBtnFiltroApeNum() {
		if (btnFiltroApeNum == null) {
			btnFiltroApeNum = new JButton("Filtrar");
			btnFiltroApeNum.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						modeloListaEnfermero(pbd.buscarApellidoEnfermero(txtFieldFiltroApeEmp.getText()));
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
			btnFiltroApeNum.setEnabled(false);
		}
		return btnFiltroApeNum;
	}
	
	private JPanel getPanelEquipo() throws SQLException {
		if (panelEquipo == null) {
			panelEquipo = new JPanel();
			panelEquipo.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Equipo", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
			panelEquipo.setLayout(new GridLayout(1, 0, 0, 0));
			panelEquipo.add(getPanelDatosEquipoSeleccion());
			panelEquipo.add(getPnEquiposLista());
		}
		return panelEquipo;
	}
	private JPanel getPanelDatosEquipoSeleccion() {
		if (panelDatosEquipoSeleccion == null) {
			panelDatosEquipoSeleccion = new JPanel();
			panelDatosEquipoSeleccion.setLayout(new GridLayout(2, 0, 0, 0));
			panelDatosEquipoSeleccion.add(getPnDatosEquipo());
			panelDatosEquipoSeleccion.add(getPnEquipoSeleccionado());
		}
		return panelDatosEquipoSeleccion;
	}
	private JPanel getPnDatosEquipo() {
		if (pnDatosEquipo == null) {
			pnDatosEquipo = new JPanel();
			pnDatosEquipo.setLayout(new MigLayout("", "[152px][152px][152px]", "[17px][17px][17px][17px]"));
			pnDatosEquipo.add(getLblNombreEquipo(), "flowx,cell 0 1,grow");
			pnDatosEquipo.add(getTxtNombreEquipo(), "cell 1 1,grow");
			pnDatosEquipo.add(getBtnFiltrarNombreEquipo(), "cell 2 1,grow");
			pnDatosEquipo.add(getBtnQuitarFiltrarEquipo(), "cell 4 1,grow");
		}
		return pnDatosEquipo;
	}
	private JPanel getPnEquipoSeleccionado() {
		if (pnEquipoSeleccionado == null) {
			pnEquipoSeleccionado = new JPanel();
			pnEquipoSeleccionado.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Equipo seleccionado", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
			pnEquipoSeleccionado.setLayout(null);
			pnEquipoSeleccionado.add(getScrollPaneEquipoSeleccionado());
		}
		return pnEquipoSeleccionado;
	}
	private JPanel getPnEquiposLista() throws SQLException {
		if (pnEquiposLista == null) {
			pnEquiposLista = new JPanel();
			pnEquiposLista.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Seleccione el equipo", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			pnEquiposLista.setLayout(null);
			pnEquiposLista.add(getScrollPanelListaEquipos());
		}
		return pnEquiposLista;
	}
	private JScrollPane getScrollPanelListaEquipos() throws SQLException {
		if (scrollPanelListaEquipos == null) {
			scrollPanelListaEquipos = new JScrollPane();
			scrollPanelListaEquipos.setOpaque(false);
			scrollPanelListaEquipos.setBounds(21, 23, 453, 178);
			scrollPanelListaEquipos.setViewportView(getList_1_4());
		}
		return scrollPanelListaEquipos;
	}
	private JList<Equipo> getList_1_4() throws SQLException {
		if (listEquipos == null) {
			listEquipos = new JList<Equipo>();
			listEquipos.setBackground(Color.WHITE);
			modeloListaE(pbd.buscarEquipo(""));
			listEquipos.setModel(modeloListaE);
			equipos = new ArrayList<Equipo>();
			listEquipos.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent event) {
					
					@SuppressWarnings("deprecation")
					Object[] selectedValues = listEquipos.getSelectedValues();
					if (selectedValues.length >= 0) {
						for (int i = 0; i < selectedValues.length; i++) {
							equipos.add((Equipo) selectedValues[i]);
						}
						modeloListaSeleccionadosEquipos(equipos);
					camposCubiertos();
					}}
			});
			
		}
		return listEquipos;
	}
	
	protected void modeloListaSeleccionadosEquipos(List<Equipo> equipos) {
		if(equipos!=null) {
			for (int i = 0; i < equipos.size(); i++) {
				if(!modeloEquipSelec.contains(equipos.get(i)))
					modeloEquipSelec.addElement(equipos.get(i));
				
			}
			listEquipoSeleccionado.setModel(modeloEquipSelec);
			}
	}



	private DefaultListModel<Equipo> modeloListaE(List<Equipo> equipo) throws SQLException {
		modeloListaE = new DefaultListModel<Equipo>();
		if(equipo!=null) {
		List<Equipo> equipos = equipo;
		for (int i = 0; i < equipos.size(); i++) {
			modeloListaE.addElement(equipos.get(i));

		}
		listEquipos.setModel(modeloListaE);
		}
		if(modeloListaE.getSize()==0)
			JOptionPane.showMessageDialog(null, "No se ha encontrado ningún equipo con esas características");
		return modeloListaE;
	}
	private JScrollPane getScrollPaneEquipoSeleccionado() {
		if (scrollPaneEquipoSeleccionado == null) {
			scrollPaneEquipoSeleccionado = new JScrollPane();
			scrollPaneEquipoSeleccionado.setBounds(new Rectangle(12, 23, 476, 72));
			scrollPaneEquipoSeleccionado.setViewportView(getList_1_3());
			
		}
		return scrollPaneEquipoSeleccionado;
	}
	
	
	private JList<Equipo> getList_1_3() {
		if (listEquipoSeleccionado == null) {
			listEquipoSeleccionado = new JList<Equipo>();
			modeloEquipSelec= new DefaultListModel<Equipo>();
			listEquipoSeleccionado.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if(e.getClickCount()==2) {
						
							Equipo equip=(Equipo) listEquipoSeleccionado.getSelectedValue();
							int res=JOptionPane.showConfirmDialog(null, "¿Esta seguro de que desea borrar este equipo?","Confirmación de eliminación", JOptionPane.YES_NO_OPTION);
							if(res==JOptionPane.YES_OPTION)	{
								modeloEquipSelec.removeElement(equip);
								equipos.remove(equip);
							}
							
				}
					}
			});
		}
		return listEquipoSeleccionado;
	}
	private JLabel getLblNombreEquipo() {
		if (lblNombreEquipo == null) {
			lblNombreEquipo = new JLabel("Nombre equipo:");
		}
		return lblNombreEquipo;
	}
	private JTextField getTxtNombreEquipo() {
		if (txtNombreEquipo == null) {
			txtNombreEquipo = new JTextField();
			txtNombreEquipo.setColumns(10);
		}
		return txtNombreEquipo;
	}
	private JButton getBtnFiltrarNombreEquipo() {
		if (btnFiltrarNombreEquipo == null) {
			btnFiltrarNombreEquipo = new JButton("Filtrar");
			btnFiltrarNombreEquipo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(txtNombreEquipo.getText().equals(""))
						JOptionPane.showMessageDialog(null, "Por favor introduce un valor");
					else {	
					
						try {
							modeloListaEquipo(pbd.devolverEquipoNombre(txtNombreEquipo.getText()));
							txtNombreEquipo.setText("");
							
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
				}
			});
		}
		return btnFiltrarNombreEquipo;
	}
	
	private DefaultListModel<Equipo> modeloListaEquipo(List<Equipo> equipo) throws SQLException {
		modeloListaEquipo = new DefaultListModel<Equipo>();
		if(equipo!=null) {
		List<Equipo> equipos = equipo;
		for (int i = 0; i < equipos.size(); i++) {
			modeloListaEquipo.addElement(equipos.get(i));

		}
		listEquipos.setModel(modeloListaEquipo);
		}
		if(modeloListaEquipo.getSize()==0)
			JOptionPane.showMessageDialog(null, "No se ha encontrado ningún médico con esas características");
		return modeloListaEquipo;
	}
	
	
	
	/**
	 * Metodo que crea la cita si tiene los cmapos cubiertos
	 * 
	 * @throws SQLException
	 */
	private void crearCitaEquipo(){

		Date dateIncio = (Date) timeSpinnerInicio.getValue();
		Time timeInicio = new Time(dateIncio.getTime());

		Date dateFin = (Date) timeSpinnerFin.getValue();
		Time timeFin = new Time(dateFin.getTime());

		Date date = getDateCita().getDate();
		java.sql.Date sDate = new java.sql.Date(date.getTime());

		for (int i = 0; i < equipos.size(); i++) {
			String sala = (String) getCbSala().getSelectedItem();
			Cita c;
			try {
				c = new Cita(pacienteCita.getCodePaciente(), timeInicio, timeFin, sDate, sala,
						chckbxEsUrgente.isSelected(), equipos.get(i).getNumEquipo());
				pbd.crearCitaEquipo(c);
				guardarAccionEquipo();
				if(c.isUrgente())
					Email.enviarCorreo("roloalvarez7@gmail.com", "sbeiaolebhiewuzz", "UO266007@uniovi.es", pacienteCita, c);

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	private JButton getBtnQuitarFiltroPac() {
		if (btnQuitarFiltroPac == null) {
			btnQuitarFiltroPac = new JButton("Quitar filtro");
			btnQuitarFiltroPac.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						modeloListaPaciente(pbd.buscarPaciente(""));
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
		}
		return btnQuitarFiltroPac;
	}
	private JButton getBtnQuitarFiltro() {
		if (btnQuitarFiltro == null) {
			btnQuitarFiltro = new JButton("Quitar filtro");
			btnQuitarFiltro.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						modeloListaM.removeAllElements();
						modeloListaM(pbd.buscarMedico(""));
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
		}
		return btnQuitarFiltro;
	}
	private JButton getBtnNewButton() {
		if (btnNewButton == null) {
			btnNewButton = new JButton("Quitar filtro");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						modeloListEnf.removeAllElements();
						modeloListEnf.addAll(pbd.listarEnfermero());
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
		}
		return btnNewButton;
	}
	
	private JButton getBtnQuitarFiltrarEquipo() {
		if (quitarFiltroEquipo == null) {
			quitarFiltroEquipo = new JButton("Quitar filtro");
			quitarFiltroEquipo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						modeloListaE(pbd.buscarEquipo(""));
						listEquipos.setModel(modeloListaE);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
		}
		return quitarFiltroEquipo;
	}
}
