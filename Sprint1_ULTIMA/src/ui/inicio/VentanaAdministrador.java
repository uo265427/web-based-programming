package ui.inicio;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import logica.Administrativo;
import logica.servicios.ParserBaseDeDatos;
import ui.AnadirAntecedentesHistorial;
import ui.admin.AsignarVacaciones;
import ui.admin.AñadirEmpleado;
import ui.admin.CrearEquipo;
import ui.admin.CrearPaciente;
import ui.admin.PanelCitas;
import ui.admin.VentanaJornada;
import ui.admin.VentanaVerCita;
import ui.medico.VentanaMedicoCita;

import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;

import javax.swing.border.TitledBorder;

public class VentanaAdministrador extends JDialog {

	private JPanel contentPane;
	private JPanel pnSur;
	private JPanel pnCentro;
	private JButton btnCancelar;
	private JButton btnJornada;
	private JButton btnAsignarCitas;
	private JPanel panelPaciente;
	private JLabel lblSelecciona;
	private JButton btnCalendarioCitas;

	private String codAdmin;
	private JButton btnDarVacaciones;
	private JButton btnAadirPaciente;
	private JButton btnCrearEquipo;
	private JPanel panelEmpleados;
	private JPanel panel;
	private JPanel panel_1;
	private JLabel lblOpcionesDePacientes;
	private JLabel lblOpcionesDeEmpleados;
	private JPanel panel_2;
	private JLabel lblBienvenido;
	private JButton btnNewButton_2;
	private JPanel panel_3;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JButton btnAñadirEmpleado;

	/**
	 * Create the frame.
	 */
	public VentanaAdministrador(String codAdmin) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(AnadirAntecedentesHistorial.class.getResource("/img/logop.jpg")));
		this.codAdmin = codAdmin;
		setTitle("VentanaAdministrador");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1225, 667);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.controlHighlight);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.add(getPanel_2(), BorderLayout.NORTH);
		contentPane.add(getPnSur(), BorderLayout.SOUTH);
		contentPane.add(getPnCentro(), BorderLayout.CENTER);
	}

	private JPanel getPnSur() {
		if (pnSur == null) {
			pnSur = new JPanel();
			pnSur.setBackground(SystemColor.controlHighlight);
			FlowLayout flowLayout = (FlowLayout) pnSur.getLayout();
			flowLayout.setAlignment(FlowLayout.RIGHT);
			pnSur.add(getBtnCancelar());
		}
		return pnSur;
	}
	private JPanel getPnCentro() {
		if (pnCentro == null) {
			pnCentro = new JPanel();
			pnCentro.setBackground(SystemColor.controlHighlight);
			pnCentro.setLayout(new GridLayout(1, 2, 0, 0));
			pnCentro.add(getPanelPaciente());
			pnCentro.add(getPanel_9());
		}
		return pnCentro;
	}
	private JButton getBtnCancelar() {
		if (btnCancelar == null) {
			btnCancelar = new JButton("Cancelar");
			btnCancelar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					
					dispose();
					
				}
			});
		}
		return btnCancelar;
	}
	private JButton getBtnJornada() {
		if (btnJornada == null) {
			btnJornada = new JButton("Asignar jornadas laborales");
			btnJornada.setFont(new Font("Tahoma", Font.PLAIN, 18));
			btnJornada.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					
					try {
						asignarJornadaLaboral();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			});
		}
		return btnJornada;
	}
	private JButton getBtnAsignarCitas() {
		if (btnAsignarCitas == null) {
			btnAsignarCitas = new JButton("Asignar Citas");
			btnAsignarCitas.setFont(new Font("Tahoma", Font.PLAIN, 18));
			btnAsignarCitas.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					
					try {
						asignarCitas();
					} catch (SQLException e) {
						e.printStackTrace();
					}
					
				}
			});
		}
		return btnAsignarCitas;
	}
	private JPanel getPanelPaciente() {
		if (panelPaciente == null) {
			panelPaciente = new JPanel();
			panelPaciente.setFont(new Font("Tahoma", Font.PLAIN, 18));
			panelPaciente.setBackground(SystemColor.controlHighlight);
			panelPaciente.setLayout(new GridLayout(8, 1, 0, 0));
			panelPaciente.add(getPanel_10());
			panelPaciente.add(getBtnAadirPaciente());
			panelPaciente.add(getBtnAsignarCitas());
			panelPaciente.add(getBtnCalendarioCitas());
		}
		return panelPaciente;
	}
	private JLabel getLblSelecciona() {
		if (lblSelecciona == null) {
			lblSelecciona = new JLabel("Seleccione lo que desea hacer:");
			lblSelecciona.setFont(new Font("Tahoma", Font.PLAIN, 18));
		}
		return lblSelecciona;
	}
	
	
	/**
	 * Método para pasar a la ventana de la jornada laboral
	 * @throws SQLException 
	 */
	protected void asignarJornadaLaboral() throws SQLException {
		
		VentanaJornada vj = new VentanaJornada(codAdmin, null);
		vj.setLocationRelativeTo(null);
		vj.setResizable(true);
		vj.setModal(true); // hasta que no se cierre una ventana no se puede abrir otra
		vj.setVisible(true);
		
	}
	
	
	/**
	 * Método para pasar a la ventana de asignar citas
	 * @throws SQLException 
	 */
	protected void asignarCitas() throws SQLException {
		
		PanelCitas pc = new PanelCitas(codAdmin);
		pc.setLocationRelativeTo(null);
		pc.setResizable(true);
		pc.setModal(true); // hasta que no se cierre una ventana no se puede abrir otra
		pc.setVisible(true);
		
	}
	
	
	

	
protected void verCalendarioCitas() {
		
		VentanaVerCita vca = new VentanaVerCita(codAdmin);
		vca.setVisible(true);
		vca.setLocationRelativeTo(this);
		
	}
	private JButton getBtnCalendarioCitas() {
		if (btnCalendarioCitas == null) {
			btnCalendarioCitas = new JButton("Calendario citas");
			btnCalendarioCitas.setFont(new Font("Tahoma", Font.PLAIN, 18));
			btnCalendarioCitas.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					verCalendarioCitas();
				}
			});
		}
		return btnCalendarioCitas;
	}
	private JButton getBtnNewButton_1_1() {
		if (btnDarVacaciones == null) {
			btnDarVacaciones = new JButton("Dar vacaciones");
			btnDarVacaciones.setFont(new Font("Tahoma", Font.PLAIN, 18));
			btnDarVacaciones.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						abrirVacaciones();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
		}
		return btnDarVacaciones;
	}
	protected void abrirVacaciones() throws SQLException {
		AsignarVacaciones av = new AsignarVacaciones(codAdmin);
		av.setVisible(true);
		av.setLocationRelativeTo(null);
		av.setResizable(true);
		av.setModal(true);
	}
	private JButton getBtnAadirPaciente() {
		if (btnAadirPaciente == null) {
			btnAadirPaciente = new JButton("A\u00F1adir Paciente");
			btnAadirPaciente.setFont(new Font("Tahoma", Font.PLAIN, 18));
			btnAadirPaciente.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					añdirPaciente();
					
				}
			});
		}
		return btnAadirPaciente;
	}
	
protected void añdirPaciente() {
		
		CrearPaciente cp= new CrearPaciente(codAdmin);
cp.setLocationRelativeTo(null);
		cp.setResizable(true);
		cp.setModal(true); // hasta que no se cierre una ventana no se puede abrir otra
		cp.setVisible(true);
		
	}
	private JButton getBtnCrearEquipo() {
		if (btnCrearEquipo == null) {
			btnCrearEquipo = new JButton("Crear Equipo");
			btnCrearEquipo.setFont(new Font("Tahoma", Font.PLAIN, 18));
			btnCrearEquipo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						abrirCrearEquipo();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
		}
		return btnCrearEquipo;
	}
	
	private void abrirCrearEquipo() throws SQLException {
		CrearEquipo cp= new CrearEquipo(codAdmin);
		cp.setLocationRelativeTo(null);
		cp.setResizable(true);
		cp.setModal(true); // hasta que no se cierre una ventana no se puede abrir otra
		cp.setVisible(true);
	}
	private JPanel getPanel_9() {
		if (panelEmpleados == null) {
			panelEmpleados = new JPanel();
			panelEmpleados.setFont(new Font("Tahoma", Font.PLAIN, 18));
			panelEmpleados.setBackground(SystemColor.controlHighlight);
			panelEmpleados.setLayout(new GridLayout(8, 1, 0, 0));
			panelEmpleados.add(getPanel_1_1());
			panelEmpleados.add(getBtnCrearEquipo());
			panelEmpleados.add(getBtnJornada());
			panelEmpleados.add(getBtnNewButton_1_1());
			panelEmpleados.add(getBtnAñadirEmpleado());
		}
	
		return panelEmpleados;
	}
	private JPanel getPanel_10() {
		if (panel == null) {
			panel = new JPanel();
			panel.setBackground(SystemColor.controlHighlight);
			panel.setLayout(null);
			panel.add(getLblOpcionesDePacientes());
		}
		return panel;
	}
	private JPanel getPanel_1_1() {
		if (panel_1 == null) {
			panel_1 = new JPanel();
			panel_1.setBackground(SystemColor.controlHighlight);
			panel_1.setLayout(null);
			panel_1.add(getLblOpcionesDeEmpleados());
		}
		return panel_1;
	}
	private JLabel getLblOpcionesDePacientes() {
		if (lblOpcionesDePacientes == null) {
			lblOpcionesDePacientes = new JLabel("Opciones de pacientes");
			lblOpcionesDePacientes.setForeground(SystemColor.windowBorder);
			lblOpcionesDePacientes.setFont(new Font("Tahoma", Font.BOLD, 19));
			lblOpcionesDePacientes.setBounds(159, 13, 252, 43);
		}
		return lblOpcionesDePacientes;
	}
	private JLabel getLblOpcionesDeEmpleados() {
		if (lblOpcionesDeEmpleados == null) {
			lblOpcionesDeEmpleados = new JLabel("Opciones de empleados");
			lblOpcionesDeEmpleados.setBounds(166, 13, 250, 43);
			lblOpcionesDeEmpleados.setForeground(SystemColor.windowBorder);
			lblOpcionesDeEmpleados.setFont(new Font("Tahoma", Font.BOLD, 19));
		}
		return lblOpcionesDeEmpleados;
	}
	private JPanel getPanel_2() {
		if (panel_2 == null) {
			panel_2 = new JPanel();
			panel_2.setLayout(new GridLayout(0, 3, 0, 0));
			panel_2.add(getLblSelecciona());
			panel_2.add(getLblBienvenido());
			panel_2.add(getPanel_3());
		}
		return panel_2;
	}
	private JLabel getLblBienvenido() {
		if (lblBienvenido == null) {
			Administrativo admin=getAdmin();
			lblBienvenido = new JLabel("Bienvenido/a "+admin.getNombre()+", "+admin.getApelliod());
			lblBienvenido.setFont(new Font("Tahoma", Font.PLAIN, 17));
		}
		return lblBienvenido;
	}
	
	private Administrativo getAdmin() {
		Administrativo admin= null;
		ParserBaseDeDatos pbd = new ParserBaseDeDatos();
		try {
			admin =pbd.getAdmin(codAdmin);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.err.println(admin);
		return admin;
		
	}
	private JButton getBtnNewButton_2() {
		if (btnNewButton_2 == null) {
			btnNewButton_2 = new JButton("Cerrar sesi\u00F3n");
			btnNewButton_2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ventanaInicio();
					dispose();
							}
				
			});
		}
		return btnNewButton_2;
	}
	
	
	
	private void ventanaInicio() {
			VentanaInicio vmc =new VentanaInicio();
			vmc.setLocationRelativeTo(null);
			vmc.setVisible(true);
	}
		
	private JPanel getPanel_3() {
		if (panel_3 == null) {
			panel_3 = new JPanel();
			panel_3.setLayout(new GridLayout(0, 3, 0, 0));
			panel_3.add(getLabel_1());
			panel_3.add(getLabel_2());
			panel_3.add(getBtnNewButton_2());
		}
		return panel_3;
	}
	private JLabel getLabel_1() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("");
		}
		return lblNewLabel;
	}
	private JLabel getLabel_2() {
		if (lblNewLabel_1 == null) {
			lblNewLabel_1 = new JLabel("");
		}
		return lblNewLabel_1;
	}
	private JButton getBtnAñadirEmpleado() {
		if (btnAñadirEmpleado == null) {
			btnAñadirEmpleado = new JButton("A\u00F1adir empleado a la plantilla");
			btnAñadirEmpleado.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					abrirVentanaAñadir();
				}
			});
			btnAñadirEmpleado.setFont(new Font("Tahoma", Font.PLAIN, 18));
		}
		return btnAñadirEmpleado;
	}
	
	private void abrirVentanaAñadir() {
		AñadirEmpleado ae = new AñadirEmpleado(codAdmin);
		ae.setModal(true);
		ae.setVisible(true);
		ae.setLocationRelativeTo(this);
		ae.setResizable(true);
	}
}
