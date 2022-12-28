package ui.admin;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import com.toedter.calendar.JDateChooser;

import logica.Accion;
import logica.AsignaVacuna;
import logica.Cita;
import logica.Paciente;
import logica.Vacaciones;
import logica.empleados.Empleado;
import logica.empleados.Medico;
import logica.servicios.ParserBaseDeDatos;
import ui.AnadirAntecedentesHistorial;

import javax.swing.JTextField;

public class AsignarVacaciones extends JDialog{

	private JPanel contentPane;
	private JPanel panelSeleccionEmpleado;
	private JComboBox<Empleado> cmboBoxEmpleado;
	private JPanel panelJornada;
	private JDateChooser chooseDFin;
	private JPanel panelDia;
	private JLabel lblDiaInicio;
	private JLabel lblDiaFin;
	private JDateChooser chooseDInicio;
	private ParserBaseDeDatos pbd = new ParserBaseDeDatos();
	//private final ButtonGroup buttonGroup = new ButtonGroup();
	private List<JToggleButton> diasSeleccionados = new ArrayList<JToggleButton>();
	private JButton btnAsignar;
	private JButton btnCancelar;
	private DefaultComboBoxModel<Medico> modeloListaM;
	
	List<Cita> citasBorrar;
	private String codAdmin;
	private JLabel lblNombre;
	private JTextField txtNombre;
	private JButton btnFiltrar;
	List<Empleado> empleados;


	/**
	 * Create the frame.
	 * @throws SQLException 
	 */
	public AsignarVacaciones(String codAdmin) throws SQLException {
		setIconImage(Toolkit.getDefaultToolkit().getImage(AnadirAntecedentesHistorial.class.getResource("/img/logop.jpg")));
		empleados = pbd.buscarEmpleados();
		this.codAdmin = codAdmin;
		citasBorrar = new ArrayList<Cita>();
		setTitle("Asignar Vacaciones");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 739, 382);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getPanelSeleccionEmpleado());
		contentPane.add(getPanelJornada());
	}
	

	private JPanel getPanelSeleccionEmpleado() throws SQLException {
		if (panelSeleccionEmpleado == null) {
			panelSeleccionEmpleado = new JPanel();
			panelSeleccionEmpleado.setBorder(new TitledBorder(new LineBorder(new Color(192, 192, 192)), "Seleccionar empleado:", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
			panelSeleccionEmpleado.setBounds(10, 11, 703, 101);
			panelSeleccionEmpleado.setLayout(null);
			panelSeleccionEmpleado.add(getCmboBoxEmpleado());
			panelSeleccionEmpleado.add(getLblNombre());
			panelSeleccionEmpleado.add(getTxtNombre());
			panelSeleccionEmpleado.add(getBtnFiltrar());
		}
		return panelSeleccionEmpleado;
	}
	private JComboBox<Empleado> getCmboBoxEmpleado() throws SQLException {
		if (cmboBoxEmpleado == null) {
			cmboBoxEmpleado = new JComboBox<Empleado>();
			cmboBoxEmpleado.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						activarBoton();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
			cmboBoxEmpleado.setBounds(341, 34, 352, 32);
			
			List<Empleado> empleados = pbd.buscarEmpleados();
			for (int i = 0; i < empleados.size(); i++) {
				cmboBoxEmpleado.insertItemAt(empleados.get(i), i);
			}
			//activarBoton();
			cmboBoxEmpleado.setSelectedIndex(0);
		}
		return cmboBoxEmpleado;
	}
	
	private DefaultComboBoxModel<Medico> modeloListaM(List<Medico> medico) throws SQLException {
		modeloListaM = new DefaultComboBoxModel<Medico>();
		List<Medico> medicos = medico;
		for (int i = 0; i < medicos.size(); i++) {
			modeloListaM.addElement(medicos.get(i));

		}
		return modeloListaM;
	}
	private JPanel getPanelJornada() {
		if (panelJornada == null) {
			panelJornada = new JPanel();
			panelJornada.setBounds(0, 123, 674, 177);
			panelJornada.setBorder(new TitledBorder(new LineBorder(new Color(192, 192, 192)), "Selecciona horarios:", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
			panelJornada.setLayout(null);
			panelJornada.add(getPanelDia());
			panelJornada.add(getBtnAsignar());
			panelJornada.add(getBtnCancelar());
		}
		return panelJornada;
	}
	



	private JDateChooser getchooseDFin() {
		if (chooseDFin == null) {
			chooseDFin = new JDateChooser();
//			chooseDFin.addFocusListener(new FocusAdapter() {
//				@Override
//				public void focusLost(FocusEvent e) {
//					try {
//						//activarBoton();
//					} catch (SQLException e1) {
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//					}
//				}
//			});
			
			
			chooseDFin.setBounds(364, 23, 178, 37);
			chooseDFin.setDateFormatString("yyyy-MM-dd");
			chooseDFin.setDate(new Date());
			

		}
		return chooseDFin;
	}
	private JPanel getPanelDia() {
		if (panelDia == null) {
			panelDia = new JPanel();
			panelDia.setLayout(null);
			panelDia.setBorder(new TitledBorder(new LineBorder(new Color(192, 192, 192)), "Dia", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
			panelDia.setBounds(24, 26, 552, 71);
			panelDia.add(getLblDiaInicio());
			panelDia.add(getLblDiaFin());
			panelDia.add(getchooseDFin());
			panelDia.add(getChooseDInicio());
		}
		return panelDia;
	}
	private JLabel getLblDiaInicio() {
		if (lblDiaInicio == null) {
			lblDiaInicio = new JLabel("Dia inicio:");
			lblDiaInicio.setBounds(10, 29, 60, 14);
		}
		return lblDiaInicio;
	}
	private JLabel getLblDiaFin() {
		if (lblDiaFin == null) {
			lblDiaFin = new JLabel("Dia fin:");
			lblDiaFin.setBounds(302, 26, 67, 14);
		}
		return lblDiaFin;
	}
	private JDateChooser getChooseDInicio() {
		if (chooseDInicio == null) {
			chooseDInicio = new JDateChooser();
//			chooseDInicio.addFocusListener(new FocusAdapter() {
//				@Override
//				public void focusLost(FocusEvent e) {
//					try {
//						activarBoton();
//					} catch (SQLException e1) {
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//					}
//				}
//			});
			
			
			chooseDInicio.setBounds(80, 23, 169, 37);
			chooseDInicio.setDateFormatString("yyyy-MM-dd");
			chooseDInicio.setDate(new Date());
		}
		return chooseDInicio;
	}
	

	private boolean comprobarEmpleadoSelected() throws SQLException {
		return getCmboBoxEmpleado().getSelectedItem() instanceof Empleado;
		
	}
	


	private String selectDates() {
		//List<JToggleButton> dias = (List<JToggleButton>) buttonGroup.getElements();
		String cadenaDias = "";
		for(int i=0; i < diasSeleccionados.size()-1 ;i++) {
			cadenaDias += diasSeleccionados.get(i).getText() + ",";
		}
		cadenaDias += diasSeleccionados.get(diasSeleccionados.size()-1).getText();
		return cadenaDias;
	}
	
	private String getSelectedEmpleadoCodigo() throws SQLException {
		if(getCmboBoxEmpleado().getSelectedIndex()!=-1) {
			Empleado e = (Empleado) getCmboBoxEmpleado().getSelectedItem();
			return e.getCodeEmpleado();
		}
		return "";
	}
	
	private void activarBoton() throws SQLException {
		if(comprobarEmpleadoSelected()) {
			if(!getBtnAsignar().isEnabled())
				getBtnAsignar().setEnabled(true);
		}
	}
	
	private JButton getBtnAsignar() {
		if (btnAsignar == null) {
			btnAsignar = new JButton("Asignar");
			btnAsignar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						if(comprobarEmpleadoSelected() && comprobarFechas() <= 0) {
							System.out.print("funciona");
							if(comprobarCitas()) {
								//preguntarVacaciones();
								int res = JOptionPane.showConfirmDialog(null,
										 "El médico tiene una cita en el periodo indicado. " + "\n" + 
													"¿Desea continuar igualmente?"  ,

										 "Advertencia: Dar vacaciones", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
								if(res == JOptionPane.YES_OPTION) {
									asignarVacaciones();
									mensajeVacaciones();
									dispose();
								}
							}
							else {
								asignarVacaciones();
								mensajeVacaciones();
								dispose();
							}
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
		}
			});
			btnAsignar.setBounds(456, 139, 89, 23);
		}
		return btnAsignar;
	}

	private void mensajeVacaciones() {
		JOptionPane.showMessageDialog(null, "Vacaciones dadas correctamente");
	}
	
	
	protected void asignarVacaciones() throws SQLException {
		Date d1 = getChooseDInicio().getDate();
		java.sql.Date dIn = new java.sql.Date(d1.getTime());
		Date d2 = getchooseDFin().getDate();
		java.sql.Date dFin = new java.sql.Date(d2.getTime());
		
		String codEmpleado = getSelectedEmpleadoCodigo();
		Random r = new Random();
		String codVac = "va" + r.nextInt(300);
		Vacaciones vacaciones = new Vacaciones(codVac, codEmpleado, codAdmin, dIn, dFin);
				
		pbd.asignarVacaciones(vacaciones);
		
		//guardar accion realizada
		guardarAccion(codEmpleado);
	}
	
	
	private void guardarAccion(String codEmpleado) throws SQLException {
		List<Accion> devolverAccionesAdmin = pbd.devolverAccionesAdmin();
		int numeroAccion = 1;
		if(devolverAccionesAdmin.size()>0) {
			numeroAccion = devolverAccionesAdmin.size() + 1;
		}
		String naccion = "" +numeroAccion;
		Empleado empleado = pbd.devolverEmpleado(codEmpleado);
		String nombre = empleado.getNombre();
		String apellido = empleado.getApellido();
		
		
		Date fecha = new Date();	
		Time hora = new Time(new Date().getTime());	
		
		
		String mensajeAccion = "Vacaciones asignadas a " + nombre + " " + apellido;
		
		Accion a = new Accion(naccion, codAdmin,  fecha, hora, mensajeAccion);
		
		pbd.guardarAccion(a);
	}


	
	// si sale true es que todo bien
		private boolean comprobarCitas() throws SQLException {
				boolean devolver = false;
				String codEmpleado =getSelectedEmpleadoCodigo1();
				List<Cita> citas = pbd.devolvercitasMedico(codEmpleado);
				for(int i=0; i<citas.size(); i++) {
					Date dateCita = citas.get(i).getDate();
					if(comprobarFechasCitas(dateCita)) {
						devolver = true;
						citasBorrar.add(citas.get(i));
					}
				}
			return devolver;
		}
		

		
		
	private boolean comprobarFechasCitas(Date date) {
			if(comprobarFechasCitasInicio(date) <= 0 && comprobarFechasCitasFinal(date) <= 0) {
				return true;
			}
			return false;
		}


	private int comprobarFechasCitasInicio(Date date) {
		return getChooseDInicio().getDate().compareTo(date);
	}
	
	private int comprobarFechasCitasFinal(Date date) {
		return date.compareTo(getchooseDFin().getDate());
	}	
		
	private int comprobarFechas() {
		return getChooseDInicio().getDate().compareTo(getchooseDFin().getDate());
	}


	private String getSelectedEmpleadoCodigo1() throws SQLException {
		if(getCmboBoxEmpleado().getSelectedIndex()!=-1) {
			Empleado e = (Empleado) getCmboBoxEmpleado().getSelectedItem();
			return e.getCodeEmpleado();
		}
		return "";
	}


	private JButton getBtnCancelar() {
		if (btnCancelar == null) {
			btnCancelar = new JButton("Cancelar");
			btnCancelar.setBounds(575, 139, 89, 23);
			btnCancelar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
		}
		return btnCancelar;
	}
	
	public void borrarCitas() {
		
	}
	private JLabel getLblNombre() {
		if (lblNombre == null) {
			lblNombre = new JLabel("Nombre");
			lblNombre.setBounds(10, 39, 58, 22);
		}
		return lblNombre;
	}
	private JTextField getTxtNombre() {
		if (txtNombre == null) {
			txtNombre = new JTextField();
			txtNombre.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					btnFiltrar.setEnabled(true);
				}
			});
			txtNombre.setBounds(78, 40, 114, 26);
			txtNombre.setColumns(10);
		}
		return txtNombre;
	}
	private JButton getBtnFiltrar() {
		if (btnFiltrar == null) {
			btnFiltrar = new JButton("Filtrar");
			btnFiltrar.setBounds(202, 39, 89, 23);
			btnFiltrar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(txtNombre.getText().equals(""))
						JOptionPane.showMessageDialog(null, "Por favor introduce un valor");
					else {	
						filtrarPorNombre(txtNombre.getText());
				}
				}
			});
		}
		return btnFiltrar;
	}
	
	
	private void filtrarPorNombre(String name) {
		for(int i=0; i < empleados.size(); i++) {
			if(empleados.get(i).getNombre().equals(name)) {
				cmboBoxEmpleado.setSelectedIndex(i);
			}
		}
	}


	protected void modeloListaPaciente(List<Medico> buscarPacienteNombre) {
		cmboBoxEmpleado.removeAll();
		for(int i=0; i<buscarPacienteNombre.size(); i++) {
			cmboBoxEmpleado.insertItemAt(buscarPacienteNombre.get(i), i);
		}
		
	}
}
