package ui.medico;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.TabableView;

import logica.Accion;
import logica.AccionEmpleado;
import logica.AsignaEquipo;
import logica.Cita;
import logica.Equipo;
import logica.HistorialMedico;
import logica.Paciente;
import logica.empleados.Empleado;
import logica.empleados.Medico;
import logica.servicios.ParserBaseDeDatos;
import ui.AnadirAntecedentesHistorial;
import ui.MostrarHistorial;
import ui.inicio.VentanaInicio;

import javax.swing.JScrollPane;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import com.toedter.calendar.JDateChooser;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import java.awt.Font;

public class VentanaMedicoCita extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JScrollPane scrollPane;
	private JTable tablacita;
	private ModeloNoEditable modeloTabla;
	private ParserBaseDeDatos pbd=new ParserBaseDeDatos();
	private JPanel panel;
	private JPanel panelCita;
	private JLabel lblNewLabel;
	private JDateChooser dateChooser;
	private JButton btnIr;
	private String codmedico;
	private List<Cita> codcitas= new ArrayList<Cita>();
	private JPanel panelBotones;
	private JButton btnhistorial;
	private JButton btnmodifica;
	private JButton btnTodas;
	private JTextField textHistorial;
	private JButton irHistorial;
	private JButton btnBuscarPorFecha;
	private JButton btnCorreo;
	private JPanel panel_1;
	private JPanel panel_2;
	private JLabel lblBienvenidoa;
	private JButton btnCerrarSesin;
	


	/**
	 * Create the frame.
	 * @param codmedico 
	 */
	public VentanaMedicoCita(String codmedico) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(AnadirAntecedentesHistorial.class.getResource("/img/logop.jpg")));
		setTitle("M\u00E9dico: Ver citas");
		this.codmedico=codmedico;
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 889, 521);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(1, 1, 0, 0));
		contentPane.add(getPanel());
	}

	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(getTableCita());
		}
		return scrollPane;
	}
	private JTable getTableCita() {
			if (tablacita == null) {
				String[] nombreColumnas= {"Nombre "," Apellido  ","Hora inicio"," Hora fin","Fecha ","Sala","Urgencia","Codcita","CodPaciente","CodMed","Numequipo"};
				modeloTabla= new ModeloNoEditable(nombreColumnas,0);
				tablacita = new JTable(modeloTabla);
				tablacita.getTableHeader().setReorderingAllowed(false);//Evita que se pueda mpver las columnas
				tablacita.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				tablacita.getTableHeader().setBackground(Color.LIGHT_GRAY);
				TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tablacita.getModel());
				tablacita.setRowSorter(sorter);

				List<RowSorter.SortKey> sortKeys = new ArrayList<>();
				sortKeys.add(new RowSorter.SortKey(6, SortOrder.ASCENDING));
				sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
				
				sorter.setSortKeys(sortKeys);
				for (int i = 7; i < 10; i++) {
					tablacita.getColumnModel().getColumn(i).setMinWidth(0);
					tablacita.getColumnModel().getColumn(i).setMaxWidth(0);
					tablacita.getColumnModel().getColumn(i).setWidth(0);
				}
				añadirFilas(false);
				
				tablacita.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent arg0) {
						btnhistorial.setEnabled(true);
						btnmodifica.setEnabled(true);
					}
				});
			
				
				
				
			
			}
			return tablacita;
		}
	
	private void borrarModeloTabla() {
		int filas=modeloTabla.getRowCount();
			for (int i = 0; i <filas; i++) {
				modeloTabla.removeRow(0);
				
			}
	}
	
	
	private void añadirFilas(boolean dia)  {
		borrarModeloTabla();
		Object[] nuevaFila=new Object[11];
		List<Cita> citas = new ArrayList<Cita>();
	if(dia) {

		Date date = getDateChooser().getDate();
		java.sql.Date sDate = new java.sql.Date(date.getTime());
		try {
			
			citas = pbd.devolvercitasMedicoPorFecha(sDate,codmedico);
			System.err.print(citas.size());
		} catch (SQLException e) {

			e.printStackTrace();
		}
		
	}
	else {
		try {
			citas = pbd.devolvercitasMedicoPorFechaActual(codmedico);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		try {
			añadirCitasDeEquipo(citas);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
		for(Cita c:citas) {
			Paciente p = null;
			Equipo equipo = null;
			try {
				if(c.getCodMed()==null) {
					p = pbd.devolverPacientesEquipo(c.getCodPaciente());
					equipo = pbd.devolverEquipo(c.getNumequipo());
				}
				else {
					p = pbd.devolverPacientesMedico(c.getCodCita());
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		
			nuevaFila[0] = p.getNombre();
			nuevaFila[1]= p.getApellido();
			nuevaFila[2] = c.gethInicio();
			nuevaFila[3] =c.gethFin();
			nuevaFila[4] =c.getDate();
			nuevaFila[5]=c.getUbicacion();
			nuevaFila[6] = c.isUrgente();
			nuevaFila[7]=c.getCodCita();
			nuevaFila[8]=c.getCodPaciente();
			nuevaFila[9]=codmedico;
			if(equipo!=null) {
				nuevaFila[10] = equipo.getNumEquipo();
			}
			else {
				nuevaFila[10] = "";
			}
			modeloTabla.addRow(nuevaFila);
			
		}
		
		}
		
	
	private void añadirCitasDeEquipo(List<Cita> citas) throws SQLException {
		List<AsignaEquipo> asignaequipo = pbd.buscarAsignaEquipoPorCodMed(codmedico);
		for(int i=0; i<asignaequipo.size(); i++) {
			System.out.println(asignaequipo.get(i).getNumequipo());
			añadirCitas(asignaequipo.get(i).getNumequipo(), citas);
		}
	}

	private void añadirCitas(String numequipo, List<Cita> citas) throws SQLException {
		List<Cita> citasEquipo = pbd.devolverCitasEquipoNumEquip(numequipo);
		for(int i=0; i<citasEquipo.size(); i++) {
			System.out.println(citasEquipo.get(i).getCodCita());
			citas.add(citasEquipo.get(i));
		}
	}

	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setLayout(new BorderLayout(0, 0));
			panel.add(getPanelBotones(), BorderLayout.SOUTH);
			panel.add(getPanelCita(), BorderLayout.NORTH);
			panel.add(getScrollPane(), BorderLayout.CENTER);
		}
		return panel;
	}
	private JPanel getPanelCita() {
		if (panelCita == null) {
			panelCita = new JPanel();
			panelCita.setLayout(new GridLayout(0, 1, 0, 0));
			panelCita.add(getPanel_2());
			panelCita.add(getPanel_1());
		}
		return panelCita;
	}
	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("Escoga el  d\u00EDa de la cita que desea ver");
			lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		}
		return lblNewLabel;
	}
	private JDateChooser getDateChooser() {
		if (dateChooser == null) {
			dateChooser = new JDateChooser(new Date());
		}
		return dateChooser;
	}
	private JButton getBtnIr() {
		if (btnIr == null) {
			btnIr = new JButton("Ir");
			btnIr.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					añadirFilas(true);
				}
			});
		}
		return btnIr;
	}
	private JPanel getPanelBotones() {
		if (panelBotones == null) {
			panelBotones = new JPanel();
			panelBotones.add(getBtnhistorial());
			panelBotones.add(getBtnmodifica());
			panelBotones.add(getBtnCorreo());
		}
		return panelBotones;
	}
	private JButton getBtnhistorial() {
		if (btnhistorial == null) {
			btnhistorial = new JButton("Ver historial");
			btnhistorial.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					mostrarHistorial();
				}
			});
			btnhistorial.setEnabled(false);
		}
		return btnhistorial;
	}
	
	protected void mostrarHistorial() {
		int fila=tablacita.getSelectedRow();
		if(fila!=-1) {
			
			String codcita=(String) tablacita.getValueAt(tablacita.getSelectedRow(), 7);
			String codPaciente=(String) tablacita.getValueAt(tablacita.getSelectedRow(), 8);
			System.out.println("codcita" + codcita);
			System.out.println("codPaciente" + codPaciente);
			System.out.println("codmedico" + codmedico);
		try {
			
			HistorialMedico hm = pbd.HistorialCita(codcita,codPaciente);
			System.out.println("Historial" + hm.getHistorial());
			MostrarHistorial mh = new MostrarHistorial(hm, codmedico);
			mh.setLocationRelativeTo(null);
			mh.setResizable(true);
			mh.setModal(true); // hasta que no se cierre una ventana no se puede abrir otra
			mh.setVisible(true);
			guardarAccionHist();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}
	
	private void guardarAccionHist() throws SQLException {
		List<AccionEmpleado> devolverAccionesAdmin = pbd.devolverAccionesEmlpeado();
		int numeroAccion = 1;
		if(devolverAccionesAdmin.size()>0) {
			numeroAccion = devolverAccionesAdmin.size() + 1;
		}
		String naccion = "" + numeroAccion;
		System.out.println("Numero acciones " + naccion);
		
		String nombrePaciente=(String) tablacita.getValueAt(tablacita.getSelectedRow(), 0);
		String apellidoPaciente=(String) tablacita.getValueAt(tablacita.getSelectedRow(), 1);
		String codMed = (String) tablacita.getValueAt(tablacita.getSelectedRow(), 9);
		
		String nombre =pbd.devolverEmpleado(codMed).getNombre();
		String apellido =pbd.devolverEmpleado(codMed).getApellido();
		
		Date fecha = new Date();	
		Time hora = new Time(new Date().getTime());	
		
		
		String mensajeAccion = "El médico " + nombre + " " +apellido  + " ha visto el historial del paciente " + nombrePaciente + " " + apellidoPaciente;
		
		AccionEmpleado a = new AccionEmpleado(naccion, codMed,  fecha, hora, mensajeAccion);
		
		pbd.guardarAccionEmpleado(a);
		
	}
	
	private JButton getBtnmodifica() {
		if (btnmodifica == null) {
			btnmodifica = new JButton("Atender consulta");
			btnmodifica.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int fila=tablacita.getSelectedRow();
					if(fila!=-1) {
					abrirModificarCita();
					}
				}
			});
			btnmodifica.setEnabled(false);
		}
		return btnmodifica;
	}
	
	protected void abrirModificarCita() {
		Paciente p;
		try {
			
			if(!tablacita.getValueAt(tablacita.getSelectedRow(),10).equals("")) {
				String numequipo = (String)tablacita.getValueAt(tablacita.getSelectedRow(),10);
				System.out.println("Equipin" + numequipo);
				p=pbd.devolverPacientesEquipo((String)tablacita.getValueAt(tablacita.getSelectedRow(),8));
				System.out.println("Paciente " + p.getNombre());
			}
			else {
				p=pbd.devolverPacientesMedico((String)tablacita.getValueAt(tablacita.getSelectedRow(),7));
			}
			
			Cita c=pbd.citaCod((String)tablacita.getValueAt(tablacita.getSelectedRow(),7),
					(String)tablacita.getValueAt(tablacita.getSelectedRow(),8));
			
			
			
			ModificarMedicosNuevoCard mc = new ModificarMedicosNuevoCard(codmedico, p, c);
			mc.setLocationRelativeTo(this);
			mc.setResizable(true);
			mc.setModal(true); // hasta que no se cierre una ventana no se puede abrir otra
			mc.setVisible(true);
		}  catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}


	}
	
	private JButton getBtnTodas() {
		if (btnTodas == null) {
			btnTodas = new JButton("Todas las citas");
			btnTodas.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					añadirFilas(false);
				}
			});
		}
		return btnTodas;
	}
	private JTextField getTextHistorial() {
		if (textHistorial == null) {
			textHistorial = new JTextField();
			textHistorial.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					
					
					textHistorial.setText("");
				}
			});
			textHistorial.setText("N\u00BA de historial");
			textHistorial.setColumns(10);
		}
		return textHistorial;
	}
	private JButton getIrHistorial() {
		if (irHistorial == null) {
			irHistorial = new JButton("Ir");
			irHistorial.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if(textHistorial.getText().equals("")|| textHistorial.getText().equals("N\u00BA de historial"))
						JOptionPane.showMessageDialog(null, "Por favor, introduzca un número de historial válido");
					else
					añadirFilasHistorial();
					
				}
			});
		}
		return irHistorial;
	}
	private void añadirFilasHistorial()  {
		borrarModeloTabla();
		Object[] nuevaFila=new Object[10];
		List<Cita> citas = new ArrayList<Cita>();
		try {
			citas = pbd.devolvercitasHistorialMed(textHistorial.getText(),codmedico);
		} catch (SQLException e) {

			e.printStackTrace();
		}
		
	
	
		for(Cita c:citas) {
			Paciente p = null;
			Empleado empleado=null;
			try {
				p = pbd.devolverPacientesMedico(c.getCodCita());
				empleado=pbd.devolverEmpleado(c.getCodMed());
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
	
			nuevaFila[0] = p.getNombre();
			nuevaFila[1]= p.getApellido();
			nuevaFila[2] = c.gethInicio();
			nuevaFila[3] =c.gethFin();
			nuevaFila[4] =c.getDate();
			nuevaFila[5] =c.getUbicacion();
			nuevaFila[6] = c.isUrgente();
			nuevaFila[7]=c.getCodCita();
			nuevaFila[8]=c.getCodPaciente();
			nuevaFila[9]=c.getCodMed();
			modeloTabla.addRow(nuevaFila);
			
		}
		
		}
	private void añadirFilasHistorialFecha()  {
		borrarModeloTabla();
		Object[] nuevaFila=new Object[10];
		List<Cita> citas = new ArrayList<Cita>();
		try {
			citas = pbd.devolvercitasHistorialFechasMedico(textHistorial.getText(),dateChooser.getDate(),codmedico);
		} catch (SQLException e) {

			e.printStackTrace();
		}
		
	
	
		for(Cita c:citas) {
			Paciente p = null;
			Empleado empleado=null;
			try {
				p = pbd.devolverPacientesMedico(c.getCodCita());
				empleado=pbd.devolverEmpleado(c.getCodMed());
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
	
			nuevaFila[0] = p.getNombre();
			nuevaFila[1]= p.getApellido();
			nuevaFila[2] = c.gethInicio();
			nuevaFila[3] =c.gethFin();
			nuevaFila[4] =c.getDate();
			nuevaFila[5] =c.getUbicacion();
			nuevaFila[6] = c.isUrgente();
			nuevaFila[7]=c.getCodCita();
			nuevaFila[8]=c.getCodPaciente();
			nuevaFila[9]=c.getCodMed();
			modeloTabla.addRow(nuevaFila);
			
		}
		
		}
	
			
		
	private JButton getBtnBuscarPorFecha() {
		if (btnBuscarPorFecha == null) {
			btnBuscarPorFecha = new JButton("Fecha e historial");
			btnBuscarPorFecha.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(textHistorial.getText().equals("")|| textHistorial.getText().equals("N\u00BA de historial"))
						JOptionPane.showMessageDialog(null, "Por favor, introduzca un número de historial válido o una fecha válida");
					else {
					añadirFilasHistorialFecha();
					 textHistorial.getText().equals("N\u00BA de historial");
					}
				}
			});
		}
		return btnBuscarPorFecha;
	}
	private JButton getBtnCorreo() {
		if (btnCorreo == null) {
			btnCorreo = new JButton("Correo");
			btnCorreo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					abrirCorreo();
				}
			});
		}
		return btnCorreo;
	}

	
	/**
	 * Método para abrir la ventana de correo, donde podrá:
	 * 		- Ver los mensajes recibidos
	 * 		- Borrar mensajes
	 * 		- Redactar y enviar mensajes
	 */
	protected void abrirCorreo() {
		VentanaCorreo vc = new VentanaCorreo(codmedico);
		vc.setLocationRelativeTo(this);
		vc.setResizable(true);
		vc.setModal(true);
		vc.setVisible(true);
	}
	private JPanel getPanel_1() {
		if (panel_1 == null) {
			panel_1 = new JPanel();
			panel_1.add(getLblNewLabel());
			panel_1.add(getDateChooser());
			panel_1.add(getBtnIr());
			panel_1.add(getBtnTodas());
			panel_1.add(getTextHistorial());
			panel_1.add(getIrHistorial());
			panel_1.add(getBtnBuscarPorFecha());
		}
		return panel_1;
	}
	private JPanel getPanel_2() {
		if (panel_2 == null) {
			panel_2 = new JPanel();
			panel_2.setLayout(null);
			panel_2.add(getLblBienvenidoa());
			panel_2.add(getBtnCerrarSesin());
		}
		return panel_2;
	}
	private JLabel getLblBienvenidoa() {
		if (lblBienvenidoa == null) {
			Medico m=getMedico();
			if(m!=null) {
			lblBienvenidoa = new JLabel("Bienvenido/a "+m.getNombre()+", "+m.getApellido());
			}
			lblBienvenidoa.setBounds(12, 7, 434, 21);
			lblBienvenidoa.setFont(new Font("Tahoma", Font.PLAIN, 17));
		}
		return lblBienvenidoa;
	}
	
	private Medico getMedico() {
		Medico m=null;
		try {
			m=pbd.buscarMedicoCodigo(codmedico);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return m;
	}
	private JButton getBtnCerrarSesin() {
		if (btnCerrarSesin == null) {
			btnCerrarSesin = new JButton("Cerrar sesi\u00F3n");
			btnCerrarSesin.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ventanaInicio();
					dispose();
				}
			});
			btnCerrarSesin.setBounds(740, 7, 109, 25);
		}
		return btnCerrarSesin;
	}
	
	private void ventanaInicio() {
		VentanaInicio vmc =new VentanaInicio();
		vmc.setLocationRelativeTo(null);
		vmc.setVisible(true);
	

	
}
}
