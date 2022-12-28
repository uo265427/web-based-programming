package ui.admin;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.toedter.calendar.JDateChooser;

import logica.Accion;
import logica.Paciente;
import logica.empleados.Empleado;
import logica.empleados.Enfermero;
import logica.empleados.Medico;
import logica.servicios.ParserBaseDeDatos;
import ui.AnadirAntecedentesHistorial;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.JToggleButton;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.sql.Time;

import javax.swing.JButton;
import javax.swing.SpinnerModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ButtonGroup;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class VentanaJornada extends JDialog{

	private JPanel contentPane;
	private JPanel panelSeleccionEmpleado;
	private JComboBox<Empleado> cmboBoxEmpleado;
	private JPanel panelJornada;
	private JPanel panelHora;
	private JLabel lblHInicio;
	private JSpinner spinnerHInicio;
	private JLabel lblHFin;
	private JSpinner spinnerHFin;
	private JDateChooser chooseDFin;
	private JPanel panelDias;
	private JToggleButton tglbtnLunes;
	private JToggleButton tglbtnMartes;
	private JToggleButton tglbtnMiercoles;
	private JToggleButton tglbtnJueves;
	private JToggleButton tglbtnViernes;
	private JToggleButton tglbtnSabado;
	private JToggleButton tglbtnDomingo;
	private JButton btnAsignar;
	private JPanel panelDia;
	private JLabel lblDiaInicio;
	private JLabel lblDiaFin;
	private JDateChooser chooseDInicio;
	private ParserBaseDeDatos pbd = new ParserBaseDeDatos();
	//private final ButtonGroup buttonGroup = new ButtonGroup();
	private List<JToggleButton> diasSeleccionados = new ArrayList<JToggleButton>();

	private String codAdmin;
	private Empleado em;


	/**
	 * Create the frame.
	 * @throws SQLException 
	 */
	public VentanaJornada(String codAdmin, Empleado em) throws SQLException {
		setIconImage(Toolkit.getDefaultToolkit().getImage(AnadirAntecedentesHistorial.class.getResource("/img/logop.jpg")));
		this.codAdmin = codAdmin;
		this.em=em;
		setTitle("Asignar Jornada");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 650, 590);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getPanelSeleccionEmpleado());
		contentPane.add(getPanelJornada());
		contentPane.add(getBtnAsignar());
		//activarBoton();
	}
	

	private JPanel getPanelSeleccionEmpleado() throws SQLException {
		if (panelSeleccionEmpleado == null) {
			panelSeleccionEmpleado = new JPanel();
			panelSeleccionEmpleado.setBorder(new TitledBorder(new LineBorder(new Color(192, 192, 192)), "Seleccionar empleado:", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
			panelSeleccionEmpleado.setBounds(10, 11, 614, 101);
			panelSeleccionEmpleado.setLayout(null);
			panelSeleccionEmpleado.add(getCmboBoxEmpleado());
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
			cmboBoxEmpleado.setBounds(71, 43, 482, 22);
			List<Empleado> empleados = pbd.buscarEmpleados();
			for (int i = 0; i < empleados.size(); i++) {
				cmboBoxEmpleado.insertItemAt(empleados.get(i), i);
			}
			if(em!=null) {
				cmboBoxEmpleado.setSelectedItem(em);
			}
			//activarBoton();
			
		}
		return cmboBoxEmpleado;
	}
	private JPanel getPanelJornada() {
		if (panelJornada == null) {
			panelJornada = new JPanel();
			panelJornada.setBorder(new TitledBorder(new LineBorder(new Color(192, 192, 192)), "Selecciona horarios:", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
			panelJornada.setBounds(10, 132, 614, 374);
			panelJornada.setLayout(null);
			panelJornada.add(getPanelHora());
			panelJornada.add(getPanelDias());
			panelJornada.add(getPanelDia());
		}
		return panelJornada;
	}
	private JPanel getPanelHora() {
		if (panelHora == null) {
			panelHora = new JPanel();
			panelHora.setBorder(new TitledBorder(new LineBorder(new Color(192, 192, 192)), "Hora", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
			panelHora.setBounds(23, 32, 552, 71);
			panelHora.setLayout(null);
			panelHora.add(getLblHInicio());
			panelHora.add(getSpinnerHInicio());
			panelHora.add(getLblHFin());
			panelHora.add(getSpinnerHFin());
		}
		return panelHora;
	}
	private JLabel getLblHInicio() {
		if (lblHInicio == null) {
			lblHInicio = new JLabel("Hora inicio:");
			lblHInicio.setBounds(10, 32, 67, 14);
			
		}
		return lblHInicio;
	}
	private JSpinner getSpinnerHInicio() {
		if (spinnerHInicio == null) {
			spinnerHInicio = new JSpinner(new SpinnerDateModel());
			spinnerHInicio.addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent e) {
					try {
						activarBoton();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
			
			
			spinnerHInicio.setBounds(81, 26, 63, 27);
			JSpinner.DateEditor de_spinnerHInicio = new JSpinner.DateEditor(spinnerHInicio, "HH:mm");
			spinnerHInicio.setEditor(de_spinnerHInicio);
			spinnerHInicio.setValue(new Date());
			
		}
		return spinnerHInicio;
	}
	private JLabel getLblHFin() {
		if (lblHFin == null) {
			lblHFin = new JLabel("Hora fin:");
			lblHFin.setBounds(222, 32, 67, 14);
		}
		return lblHFin;
	}
	private JSpinner getSpinnerHFin() {
		if (spinnerHFin == null) {
			spinnerHFin = new JSpinner(new SpinnerDateModel());
			spinnerHFin.addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent e) {
					try {
						activarBoton();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
			
			
			spinnerHFin.setBounds(289, 26, 63, 27);
			JSpinner.DateEditor de_spinnerHFin = new JSpinner.DateEditor(spinnerHFin, "HH:mm");
			spinnerHFin.setEditor(de_spinnerHFin);
			spinnerHFin.setValue(new Date());
			
		}
		return spinnerHFin;
	}
	private JDateChooser getchooseDFin() {
		if (chooseDFin == null) {
			chooseDFin = new JDateChooser();
			chooseDFin.addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent e) {
					try {
						activarBoton();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
			
			
			chooseDFin.setBounds(289, 26, 120, 27);
			chooseDFin.setDateFormatString("yyyy-MM-dd");
			chooseDFin.setDate(new Date());
			

		}
		return chooseDFin;
	}

	private JPanel getPanelDias() {
		if (panelDias == null) {
			panelDias = new JPanel();
			panelDias.setBorder(new TitledBorder(new LineBorder(new Color(192, 192, 192)), "Dias de la jornada", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panelDias.setBounds(23, 202, 552, 150);
			panelDias.setLayout(null);
			panelDias.add(getTglbtnLunes());
			panelDias.add(getTglbtnMartes());
			panelDias.add(getTglbtnMiercoles());
			panelDias.add(getTglbtnJueves());
			panelDias.add(getTglbtnViernes());
			panelDias.add(getTglbtnSabado());
			panelDias.add(getTglbtnDomingo());
		}
		return panelDias;
	}
	private JToggleButton getTglbtnLunes() {
		if (tglbtnLunes == null) {
			tglbtnLunes = new JToggleButton("Lunes");
			tglbtnLunes.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(tglbtnLunes.isSelected())
						diasSeleccionados.add(tglbtnLunes);
					else
						diasSeleccionados.remove(tglbtnLunes);
						
				}
			});
			//buttonGroup.add(tglbtnLunes);
			tglbtnLunes.setBounds(27, 43, 90, 23);
		}
		return tglbtnLunes;
	}
	private JToggleButton getTglbtnMartes() {
		if (tglbtnMartes == null) {
			tglbtnMartes = new JToggleButton("Martes");
			tglbtnMartes.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(tglbtnMartes.isSelected())
						diasSeleccionados.add(tglbtnMartes);
					else
						diasSeleccionados.remove(tglbtnMartes);
				}
			});
			//buttonGroup.add(tglbtnMartes);
			tglbtnMartes.setBounds(127, 43, 90, 23);
		}
		return tglbtnMartes;
	}
	private JToggleButton getTglbtnMiercoles() {
		if (tglbtnMiercoles == null) {
			tglbtnMiercoles = new JToggleButton("Mi\u00E9rcoles");
			tglbtnMiercoles.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(tglbtnMiercoles.isSelected())
						diasSeleccionados.add(tglbtnMiercoles);
					else
						diasSeleccionados.remove(tglbtnMiercoles);
				}
			});
			//buttonGroup.add(tglbtnMiercoles);
			tglbtnMiercoles.setBounds(227, 43, 90, 23);
		}
		return tglbtnMiercoles;
	}
	private JToggleButton getTglbtnJueves() {
		if (tglbtnJueves == null) {
			tglbtnJueves = new JToggleButton("Jueves");
			tglbtnJueves.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(tglbtnJueves.isSelected())
						diasSeleccionados.add(tglbtnJueves);
					else
						diasSeleccionados.remove(tglbtnJueves);
				}
			});
			//buttonGroup.add(tglbtnJueves);
			tglbtnJueves.setBounds(327, 43, 90, 23);
		}
		return tglbtnJueves;
	}
	private JToggleButton getTglbtnViernes() {
		if (tglbtnViernes == null) {
			tglbtnViernes = new JToggleButton("Viernes");
			tglbtnViernes.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(tglbtnViernes.isSelected())
						diasSeleccionados.add(tglbtnViernes);
					else
						diasSeleccionados.remove(tglbtnViernes);
				}
			});
			//buttonGroup.add(tglbtnViernes);
			tglbtnViernes.setBounds(427, 43, 90, 23);
		}
		return tglbtnViernes;
	}
	private JToggleButton getTglbtnSabado() {
		if (tglbtnSabado == null) {
			tglbtnSabado = new JToggleButton("S\u00E1bado");
			tglbtnSabado.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(tglbtnSabado.isSelected())
						diasSeleccionados.add(tglbtnSabado);
					else
						diasSeleccionados.remove(tglbtnSabado);
				}
			});
			//buttonGroup.add(tglbtnSabado);
			tglbtnSabado.setBounds(165, 89, 90, 23);
		}
		return tglbtnSabado;
	}
	private JToggleButton getTglbtnDomingo() {
		if (tglbtnDomingo == null) {
			tglbtnDomingo = new JToggleButton("Domingo");
			tglbtnDomingo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(tglbtnDomingo.isSelected())
						diasSeleccionados.add(tglbtnDomingo);
					else
						diasSeleccionados.remove(tglbtnDomingo);
				}
			});
			//buttonGroup.add(tglbtnDomingo);
			tglbtnDomingo.setBounds(265, 89, 90, 23);
		}
		return tglbtnDomingo;
	}
	private JButton getBtnAsignar() {
		if (btnAsignar == null) {
			btnAsignar = new JButton("Asignar");
			btnAsignar.setEnabled(false);
			btnAsignar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						asignarJornada();
						dispose();
						JOptionPane.showMessageDialog(null, "La jornada se ha asignado correctamente");
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			});
			
			btnAsignar.setBounds(535, 517, 89, 23);
		}
		return btnAsignar;
	}
	private JPanel getPanelDia() {
		if (panelDia == null) {
			panelDia = new JPanel();
			panelDia.setLayout(null);
			panelDia.setBorder(new TitledBorder(new LineBorder(new Color(192, 192, 192)), "Dia", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
			panelDia.setBounds(23, 120, 552, 71);
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
			lblDiaInicio.setBounds(10, 29, 67, 14);
		}
		return lblDiaInicio;
	}
	private JLabel getLblDiaFin() {
		if (lblDiaFin == null) {
			lblDiaFin = new JLabel("Dia fin:");
			lblDiaFin.setBounds(227, 29, 67, 14);
		}
		return lblDiaFin;
	}
	private JDateChooser getChooseDInicio() {
		if (chooseDInicio == null) {
			chooseDInicio = new JDateChooser();
			chooseDInicio.addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent e) {
					try {
						activarBoton();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
			
			
			chooseDInicio.setBounds(80, 26, 120, 27);
			chooseDInicio.setDateFormatString("yyyy-MM-dd");
			chooseDInicio.setDate(new Date());
		}
		return chooseDInicio;
	}
	
	private void asignarJornada() throws SQLException {
			Date d1 = (Date)getSpinnerHInicio().getValue();
			Time inicio = new Time(d1.getTime());
			
			Date d2 = (Date)getSpinnerHFin().getValue();
			Time fin = new Time(d2.getTime());
			
			System.out.print(selectDates());
			
			pbd.updateJornada(inicio, fin, new java.sql.Date(getChooseDInicio().getDate().getTime()), new java.sql.Date(getchooseDFin().getDate().getTime()),
					selectDates(), getSelectedEmpleadoCodigo());
			
			guardarAccion(getSelectedEmpleadoCodigo());
		
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
		
		
		String mensajeAccion = "Jornada laboral asignada a " + nombre + " " + apellido;
		
		Accion a = new Accion(naccion, codAdmin,  fecha, hora, mensajeAccion);
		
		pbd.guardarAccion(a);
	}

	private boolean comprobarEmpleadoSelected() throws SQLException {
		return getCmboBoxEmpleado().getSelectedItem() instanceof Empleado;
		
	}
	
	private int comprobarFechas() {
		return getChooseDInicio().getDate().compareTo(getchooseDFin().getDate());
	}
	
	private int comprobarHoras() {
		Date d1 = (Date)getSpinnerHInicio().getValue();
		Time t1 = new Time(d1.getTime());
		
		Date d2 = (Date)getSpinnerHFin().getValue();
		Time t2 = new Time(d2.getTime());
		
		return t1.compareTo(t2);
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
}
