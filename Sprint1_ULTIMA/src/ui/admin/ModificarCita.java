package ui.admin;

import java.awt.BorderLayout;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import logica.servicios.ParserBaseDeDatos;
import net.miginfocom.swing.MigLayout;
import ui.AnadirAntecedentesHistorial;
import ui.inicio.VentanaInicio;

import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.SpinnerDateModel;
import javax.swing.JSpinner;

import logica.Cita;
import logica.Email;
import logica.Paciente;
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
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import java.awt.FlowLayout;

import javax.swing.DefaultComboBoxModel;

public class ModificarCita extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JPanel pnMedico;
	private JPanel panelAbajo;
	private JPanel panelAbajo2;
	private JLabel lblHoraInicio;
	private JLabel lblHoraFin;
	private JLabel lblFecha;
	private ParserBaseDeDatos pbd = new ParserBaseDeDatos();
	private VentanaInicio vi;
	private JScrollPane scrollPane;
	private DefaultListModel<Medico> modeloListaM;
	private JList<Medico> list;
	private JDateChooser dateCita;
	private JScrollPane scrollPane_descripcion;
	private JTextArea textArea_descripcion;
	private JButton btnModificar;
	private JCheckBox chckbxEsUrgente;
	private ArrayList<Medico> medicos=new ArrayList<Medico>();
	private JSpinner timeSpinnerInicio;
	private JSpinner timeSpinnerFin;
	private Paciente pacienteCita;
	private JPanel panel;
	private JTextField textName;
	private JButton btnName;
	private JButton btnSurname;
	private JPanel panel_1;
	private JLabel lblEscogeLaSala;
	private JComboBox<String> cbSala;
	private Cita cita;
	private JTextField textSurname;
	private JButton btnNameSurname;
	private JButton btnNewButton;

	private VentanaVerCita vvc;
	private JLabel lblNombreSala;
	private JTextField txtNombreSala;
	private JButton btnFiltrar;
	
	List<String> salas;

	/**
	 * Create the dialog.
	 * @param c 
	 * @param p 
	 * 
	 * @throws SQLException
	 */
	public ModificarCita(VentanaVerCita vvc, Paciente p, Cita cita) throws SQLException {
		setIconImage(Toolkit.getDefaultToolkit().getImage(AnadirAntecedentesHistorial.class.getResource("/img/logop.jpg")));
		this.vvc = vvc;
		this.pacienteCita=p;
		this.cita=cita;
		setTitle("Administrativo: Modificar cita");
		setLocationRelativeTo(null);
		setModal(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 914, 554);
		getContentPane().setLayout(new GridLayout(0, 1, 0, 0));
		contentPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Citas", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(new GridLayout(2, 2, 0, 0));
		{
			JPanel panelArriba = new JPanel();
			contentPanel.add(panelArriba);
			panelArriba.setLayout(new GridLayout(0, 1, 0, 0));
			panelArriba.add(getPanel());
			panelArriba.add(getPnMedico());
		}
		contentPanel.add(getPanelAbajo());
	}



	private JPanel getPnMedico() throws SQLException {
		if (pnMedico == null) {
			pnMedico = new JPanel();
			pnMedico.setBorder(new TitledBorder(null, "M\u00E9dico", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			pnMedico.setSize(new Dimension(219, 200));
			pnMedico.setLayout(new BorderLayout(0, 0));
			pnMedico.add(getScrollPane_1(), BorderLayout.CENTER);
		}
		return pnMedico;
	}

	private JPanel getPanelAbajo() {
		if (panelAbajo == null) {
			panelAbajo = new JPanel();
			panelAbajo.setLayout(new GridLayout(0, 1, 0, 0));
			panelAbajo.add(getPanelAbajo2());
			panelAbajo.add(getPanel_1());
		}
		return panelAbajo;
	}

	private JPanel getPanelAbajo2() {
		if (panelAbajo2 == null) {
			panelAbajo2 = new JPanel();
			panelAbajo2.setBorder(new TitledBorder(null, "Opciones de fecha", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panelAbajo2.setLayout(null);
			panelAbajo2.add(getScrollPane_descripcion());
			panelAbajo2.add(getLblHoraInicio());

			panelAbajo2.add(getSpinnerInicio());

			panelAbajo2.add(getLblHoraFin());

			panelAbajo2.add(getTimeSpinnerF());

			panelAbajo2.add(getLblFecha());
			panelAbajo2.add(getDateCita());
			panelAbajo2.add(getChckbxEsUrgente());



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
			lblHoraInicio.setBounds(12, 66, 66, 16);
		}
		return lblHoraInicio;
	}

	private JSpinner getSpinnerInicio() {
		timeSpinnerInicio = new JSpinner(new SpinnerDateModel());
		timeSpinnerInicio.setBounds(97, 62, 81, 24);
		timeSpinnerInicio.setFont(new Font("Tahoma", Font.PLAIN, 14));
		JSpinner.DateEditor de_timeSpinnerInicio = new JSpinner.DateEditor(timeSpinnerInicio, "HH:mm");
		timeSpinnerInicio.setEditor(de_timeSpinnerInicio);
		timeSpinnerInicio.setValue(new Date());
		return timeSpinnerInicio;
	}

	private JLabel getLblHoraFin() {
		if (lblHoraFin == null) {
			lblHoraFin = new JLabel("Hora Fin:");
			lblHoraFin.setBounds(203, 66, 53, 16);
		}
		return lblHoraFin;
	}

	private JSpinner getTimeSpinnerF() {
		timeSpinnerFin = new JSpinner(new SpinnerDateModel());
		timeSpinnerFin.setBounds(267, 62, 81, 24);
		timeSpinnerFin.setFont(new Font("Tahoma", Font.PLAIN, 14));
		JSpinner.DateEditor de_timeSpinnerFin = new JSpinner.DateEditor(timeSpinnerFin, "HH:mm");
		timeSpinnerFin.setEditor(de_timeSpinnerFin);
		timeSpinnerFin.setValue(new Date());

		return timeSpinnerFin;

	}

	private JLabel getLblFecha() {
		if (lblFecha == null) {
			lblFecha = new JLabel("Fecha:");
			lblFecha.setBounds(381, 66, 39, 16);
		}
		return lblFecha;
	}

	private JScrollPane getScrollPane_1() throws SQLException {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setOpaque(false);
			scrollPane.setViewportView(getList_1());
		}
		return scrollPane;
	}

	private DefaultListModel<Medico> modeloListaM() throws SQLException {
		modeloListaM = new DefaultListModel<Medico>();
		List<Medico> medicos = pbd.buscarMedico("");
		for (int i = 0; i < medicos.size(); i++) {
			if(!modeloListaM.contains(medicos))
				modeloListaM.addElement(medicos.get(i));

		}
		return modeloListaM;
	}
	
	
	private DefaultListModel<Medico> modeloListaNombre(String name) throws SQLException {
		modeloListaM = new DefaultListModel<Medico>();
		List<Medico> medicos = pbd.devolverMedicoNombre(name);
		for (int i = 0; i < medicos.size(); i++) {
			modeloListaM.addElement(medicos.get(i));

		}
		return modeloListaM;
	}

	
	
	private DefaultListModel<Medico> modeloListaApellido(String apellido) throws SQLException {
		modeloListaM = new DefaultListModel<Medico>();
		List<Medico> medicos = pbd.devolverMedicoApellido(apellido);
		for (int i = 0; i < medicos.size(); i++) {
			modeloListaM.addElement(medicos.get(i));

		}
		return modeloListaM;
	}
	private DefaultListModel<Medico> modeloListaNombreApellido(String name,String apellido) {
		modeloListaM = new DefaultListModel<Medico>();
		List<Medico> medicos=new ArrayList<Medico>();
		try {
			medicos = pbd.devolverMedicoNombreApellido(name,apellido);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < medicos.size(); i++) {
			modeloListaM.addElement(medicos.get(i));

		}
		return modeloListaM;
	}
	private JDateChooser getDateCita() {
		if (dateCita == null) {
			dateCita = new JDateChooser();
			dateCita.setBounds(432, 60, 120, 27);
			dateCita.setDateFormatString("yyyy-MM-dd");
			dateCita.setDate(new Date());
		}
		return dateCita;
	}

	private JButton getBtnModificar() {
		if (btnModificar == null) {
			btnModificar =new JButton("Modificar cita");
			btnModificar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					
					if(hora()) {

						if (checkMedico()) {
							UpdateCita();
							if(cita.isUrgente()) {
								Email.enviarCorreo("raulalvarezips@gmail.com", "sbeiaolebhiewuzz", "UO266007@uniovi.es", pacienteCita, cita);
							}
							JOptionPane.showMessageDialog(null, "Su cita se ha modificado con éxito");
							
							vvc.añadirFilas(false);
							dispose();
							
						}
					}
				}
			});
		}
		return btnModificar;
	}

	private boolean checkMedico() {
		Date dateIncio = (Date) timeSpinnerInicio.getValue();
		Time timeInicio = new Time(dateIncio.getTime());

		Date dateFin = (Date) timeSpinnerFin.getValue();
		Time timeFin = new Time(dateFin.getTime());
		boolean cita = false;
		int jornadares = -1;
		int citares = -1;
		boolean jornada = false;

		for (int i = 0; i < medicos.size(); i++) {

			try {
				jornada = pbd.checkMedicoJornada(medicos.get(i).getCodeEmpleado(), timeInicio, timeFin);
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

				cita = pbd.checkMedicoCita(medicos.get(i).getCodeEmpleado(), timeInicio, timeFin);
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

	private JCheckBox getChckbxEsUrgente() {
		if (chckbxEsUrgente == null) {
			chckbxEsUrgente = new JCheckBox("Es urgente");
			chckbxEsUrgente.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {

					if(chckbxEsUrgente.isEnabled()) {
						System.out.println("Se ha enviado un correo al medico");
						JOptionPane.showMessageDialog(null, "Se ha enviado un correo al medico");
					}
				}
			});
			chckbxEsUrgente.setBounds(587, 57, 113, 25);
		}
		return chckbxEsUrgente;
	}

	private JList<Medico> getList_1() throws SQLException {
		if (list == null) {
			modeloListaM();
			list = new JList<Medico>();
			list.setOpaque(false);
			list.setModel(modeloListaM);
			list.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					@SuppressWarnings("deprecation")
					Object[] selectedValues = list.getSelectedValues();
					if (selectedValues.length >= 0) {
						System.err.println("Tamñao medicos "+medicos.size());
						for (int i = 0; i < selectedValues.length; i++) {
							medicos = new ArrayList<Medico>();
							medicos.add((Medico) selectedValues[i]);

						}    }
				}

			});

		}

		return list;
	}

	private boolean JlistMedicoFill() {
		if (medicos == null)
			return false;
		return medicos.size() > 0;
	}



	private boolean hora() {
		Date dateIncio = (Date) timeSpinnerInicio.getValue();
		Time timeInicio = new Time(dateIncio.getTime());

		Date dateFin = (Date) timeSpinnerFin.getValue();
		Time timeFin = new Time(dateFin.getTime());

		if (timeFin.compareTo(timeInicio) <= 0) {
			JOptionPane.showMessageDialog(null,
					"La fecha de inicio no puede ser igual o posterior a la fecha final.Por favor,modifíquelo y vuelva a intentarlo");
			btnModificar.setEnabled(false);
			return false;
		}
		return true;
	}



	//CAMBBIARRRRRRR

	/**
	 * Metodo que crea la cita si tiene los cmapos cubiertos
	 * 
	 * @throws SQLException
	 */
	private void UpdateCita() {
		String combo="";
		Date dateIncio = (Date) timeSpinnerInicio.getValue();
		Time timeInicio = new Time(dateIncio.getTime());

		Date dateFin = (Date) timeSpinnerFin.getValue();
		Time timeFin = new Time(dateFin.getTime());

		Date date = getDateCita().getDate();
		java.sql.Date sDate = new java.sql.Date(date.getTime());
			if(cbSala.getSelectedIndex()!=-1) {
		combo=cbSala.getModel().getElementAt(cbSala.getSelectedIndex());
			}
		
			
		if(medicos.size() > 0) {
			for (int i = 0; i < medicos.size(); i++) {

				Cita c = null;
				try {
					
					c = new Cita(cita.getCodCita(),pacienteCita.getCodePaciente(), medicos.get(i).getCodeEmpleado(), timeInicio, timeFin, sDate,combo,
							chckbxEsUrgente.isSelected());
					pbd.updateCita(c,cita.getCodMed());

				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		else {
			Cita c = null;
			try {
						
				c = new Cita(cita.getCodCita(),pacienteCita.getCodePaciente(), cita.getCodMed(), timeInicio, timeFin, sDate,combo,
					chckbxEsUrgente.isSelected());
					pbd.updateCita(c,cita.getCodMed());

		} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
		
		


	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setBorder(new TitledBorder(null, "Filtrar m\u00E9dico", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panel.setLayout(new MigLayout("", "[116px][grow][][][][][]", "[22px][][]"));
			panel.add(getTextName(), "cell 1 0,growx");
			panel.add(getBtnName(), "cell 4 0");
			panel.add(getTextSurname(), "cell 1 1,grow");
			panel.add(getBtnSurname(), "cell 4 1");
			panel.add(getBtnNameSurname(), "flowx,cell 1 2");
			panel.add(getBtnNewButton(), "cell 1 2");
		}
		return panel;
	}
	private JTextField getTextName() {
		if (textName == null) {
			textName = new JTextField();
			textName.setColumns(25);
		}
		return textName;
	}
	private JButton getBtnName() {
		if (btnName == null) {
			btnName = new JButton("Filtrar por nombre");
			btnName.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if(textName.getText().equals(""))
						JOptionPane.showMessageDialog(null, "Por favor introduce un valor");
					else {	
					try {
						modeloListaNombre(textName.getText());
						list.setModel(modeloListaM);
						
					} catch (SQLException e) {
						e.printStackTrace();
					}
					
				}
				}
			});
		}
		return btnName;
	}
	private JButton getBtnSurname() {
		if (btnSurname == null) {
			btnSurname = new JButton("Filtrar por apellido");
			btnSurname.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					
					if(textSurname.getText().equals(""))
						JOptionPane.showMessageDialog(null, "Por favor introduce un valor");
					else {	
					try {
						modeloListaNombre(textName.getText());
						list.setModel(modeloListaM);
						
					} catch (SQLException e) {
						e.printStackTrace();
					}
					
				}
			}});
		}
		return btnSurname;
	}
	private JPanel getPanel_1() {
		if (panel_1 == null) {
			panel_1 = new JPanel();
			panel_1.setLayout(new MigLayout("", "[109px][grow][][][][][][][][][][][grow][][][][][][][][][][][][][][][]", "[25px][][]"));
			panel_1.add(getLblEscogeLaSala(), "cell 0 1,alignx trailing");
			panel_1.add(getCbSala(), "cell 1 1 5 1");
			panel_1.add(getLblNombreSala(), "cell 10 1,aligny center");
			panel_1.add(getTxtNombreSala(), "cell 11 1 4 1,growx");
			panel_1.add(getBtnFiltrar(), "cell 15 1");
			panel_1.add(getBtnModificar(), "cell 26 2,alignx left,aligny top");
		}
		return panel_1;
	}
	private JLabel getLblEscogeLaSala() {
		if (lblEscogeLaSala == null) {
			lblEscogeLaSala = new JLabel("Escoge la sala:   ");
		}
		return lblEscogeLaSala;
	}
	private JComboBox<String> getCbSala() {
		if (cbSala == null) {
			cbSala = new JComboBox<String>();
			salas= rellenarSalas();
			String[] array = salas.toArray( new String[salas.size()] );
			 //Arrays.sort(array);
			cbSala.setModel(new DefaultComboBoxModel<String>(array));
			cbSala.setBounds(54, 47, 236, 22);
			
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
	
	private JTextField getTextSurname() {
		if (textSurname == null) {
			textSurname = new JTextField();
			textSurname.setColumns(20);
		}
		return textSurname;
	}
	private JButton getBtnNameSurname() {
		if (btnNameSurname == null) {
			btnNameSurname = new JButton("Filtrar por nombre y  apellido");
			btnNameSurname.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					
					if(textName.getText().equals("")|| textSurname.getText().equals(""))
						JOptionPane.showMessageDialog(null, "Por favor introduce un valor");
					else {	
					modeloListaNombreApellido(textName.getText(), textSurname.getText());
					list.setModel(modeloListaM);
					
				}
				}
			});
		}
		return btnNameSurname;
	}
	private JButton getBtnNewButton() {
		if (btnNewButton == null) {
			btnNewButton = new JButton("Todos los m\u00E9dicos");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						
						list.setModel(modeloListaM());
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					
				}
			});
		}
		return btnNewButton;
	}
	private JLabel getLblNombreSala() {
		if (lblNombreSala == null) {
			lblNombreSala = new JLabel("Nombre sala");
		}
		return lblNombreSala;
	}
	private JTextField getTxtNombreSala() {
		if (txtNombreSala == null) {
			txtNombreSala = new JTextField();
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
}
