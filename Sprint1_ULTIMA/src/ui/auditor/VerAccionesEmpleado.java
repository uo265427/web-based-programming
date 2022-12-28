package ui.auditor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.toedter.calendar.JDateChooser;

import logica.Accion;
import logica.AccionEmpleado;
import logica.Cita;
import logica.servicios.ParserBaseDeDatos;
import ui.AnadirAntecedentesHistorial;
import ui.medico.ModeloNoEditable;
import javax.swing.JTextField;
import java.awt.GridLayout;
import java.awt.Toolkit;

public class VerAccionesEmpleado extends JDialog {

	private JPanel contentPane;
	private JPanel panelBotones;
	private JPanel panelFiltros;
	private JScrollPane scrollPane;
	private JTable tablaCita;
	private ModeloNoEditable modeloTabla;
	private JLabel lblFechaIn;
	private JDateChooser dateChooser;
	private ParserBaseDeDatos pbd=new ParserBaseDeDatos();
	private List<Cita> codcitas= new ArrayList<Cita>();
	private List<Accion> accionesAdmin = new ArrayList<Accion>();
	private JButton btnNewButton;
	private JDateChooser dateChooser_1;
	private JLabel lblFechaIn_1;
	private JButton btnFiltroFechas;
	private JButton btnTodas;
	private JButton btnNewButton_1;
	private JButton btnNewButton_2;
	private JTextField txtNombreDeUsuario;



	/**
	 * Create the frame.
	 */
	public VerAccionesEmpleado() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(AnadirAntecedentesHistorial.class.getResource("/img/logop.jpg")));
		setTitle("Registro de acciones");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 950, 770);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.add(getPanelBotones(), BorderLayout.SOUTH);
		contentPane.add(getPanelFiltros(), BorderLayout.NORTH);
		contentPane.add(getScrollPane(), BorderLayout.CENTER);
	}
	
	private JPanel getPanelBotones() {
		if (panelBotones == null) {
			panelBotones = new JPanel();
			panelBotones.add(getBtnNewButton());
		}
		return panelBotones;
	}
	private JPanel getPanelFiltros() {
		if (panelFiltros == null) {
			panelFiltros = new JPanel();
			panelFiltros.setLayout(new GridLayout(0, 9, 0, 0));
			panelFiltros.add(getLblFechaIn());
			panelFiltros.add(getDateChooser());
			panelFiltros.add(getLblFechaIn_1());
			panelFiltros.add(getDateChooser_1());
			panelFiltros.add(getBtnFiltroFechas());
			panelFiltros.add(getBtnTodas());
			panelFiltros.add(getTxtNombreDeUsuario());
			panelFiltros.add(getBtnNewButton_1());
			panelFiltros.add(getBtnNewButton_2());
		}
		return panelFiltros;
	}
	
	private JDateChooser getDateChooser() {
		if (dateChooser == null) {
			dateChooser = new JDateChooser(new Date());
			
		}
		return dateChooser;
	}
	
	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(getTablaCita());
		}
		return scrollPane;
	}
	private JTable getTablaCita() {
		if (tablaCita == null) {
			String[] nombreColumnas= {"Numero acción", "Nombre usuario","Fecha", "Hora","Acción "};
			modeloTabla= new ModeloNoEditable(nombreColumnas,0);
			tablaCita = new JTable(modeloTabla);
			tablaCita.getTableHeader().setReorderingAllowed(false);//Evita que se pueda mpver las columnas
			tablaCita.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			tablaCita.getTableHeader().setBackground(Color.LIGHT_GRAY);
			
			TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tablaCita.getModel());
			tablaCita.setRowSorter(sorter);
			
			List<RowSorter.SortKey> sortKeys = new ArrayList<>();
			sortKeys.add(new RowSorter.SortKey(3, SortOrder.ASCENDING));
			sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
			sortKeys.add(new RowSorter.SortKey(2, SortOrder.ASCENDING));
			sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
			sorter.setSortKeys(sortKeys);
			
			añadirFilas(false);
			tablaCita.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					
					int fila=tablaCita.getSelectedRow();
					if(fila!=-1) {
						String mensaje = (String) tablaCita.getValueAt(tablaCita.getSelectedRow(), 4);
						JOptionPane.showMessageDialog(null,
								 mensaje);
					}
				}
			});
			
		}
		return tablaCita;
	}
	
	public void añadirFilas(boolean dia)  {
		borrarModeloTabla();
		Object[] nuevaFila=new Object[11];
		List<AccionEmpleado> accionesEmpleados = new ArrayList<AccionEmpleado>();
	if(dia) {
		Date dateIn = getDateChooser().getDate();
		java.sql.Date sDateIn = new java.sql.Date(dateIn.getTime());
		
		Date dateFin = getDateChooser_1().getDate();
		java.sql.Date sDateFin = new java.sql.Date(dateFin.getTime());
		try {
			accionesEmpleados = pbd.devolverAccionesEmpleadoPorFecha(sDateIn, sDateFin);
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}
	else {
		try {
			accionesEmpleados = pbd.devolverAccionesEmlpeado();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
		for(AccionEmpleado a:accionesEmpleados) {
			nuevaFila[0]= a.getNaccion();
			nuevaFila[1]= a.getCodempleado();
			nuevaFila[2] = a.getDate();
			nuevaFila[3] =a.getHora();
			nuevaFila[4] =a.getMensajeAccion();
			modeloTabla.addRow(nuevaFila);
		
		}
	}
	
	private void borrarModeloTabla() {
		int filas=modeloTabla.getRowCount();
			for (int i = 0; i <filas; i++) {
				modeloTabla.removeRow(0);
				
			}
	}
	private JLabel getLblFechaIn() {
		if (lblFechaIn == null) {
			lblFechaIn = new JLabel("Fecha inicio");
		}
		return lblFechaIn;
	}
	private JButton getBtnNewButton() {
		if (btnNewButton == null) {
			btnNewButton = new JButton("Cerrar");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
		}
		return btnNewButton;
	}
	private JDateChooser getDateChooser_1() {
		if (dateChooser_1 == null) {
			dateChooser_1 = new JDateChooser(new Date());
		}
		return dateChooser_1;
	}
	private JLabel getLblFechaIn_1() {
		if (lblFechaIn_1 == null) {
			lblFechaIn_1 = new JLabel("Fecha fin");
		}
		return lblFechaIn_1;
	}
	private JButton getBtnFiltroFechas() {
		if (btnFiltroFechas == null) {
			btnFiltroFechas = new JButton("Ir");
			btnFiltroFechas.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					añadirFilas(true);
				}
			});
		}
		return btnFiltroFechas;
	}
	private JButton getBtnTodas() {
		if (btnTodas == null) {
			btnTodas = new JButton("Todas las acciones");
			btnTodas.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					añadirFilas(false);
				}
			});
		}
		return btnTodas;
	}
	private JButton getBtnNewButton_1() {
		if (btnNewButton_1 == null) {
			btnNewButton_1 = new JButton("Ir");
			btnNewButton_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(txtNombreDeUsuario.getText().equals("")|| txtNombreDeUsuario.getText().equals("Nombre de usuario"))
						JOptionPane.showMessageDialog(null, "Por favor, introduzca un nombre de usuario válido");
					else
						añadirFilasNombre();
					
					txtNombreDeUsuario.setText("Nombre de usuario");
				}
			});
		}
		return btnNewButton_1;
	}
	protected void añadirFilasNombre() {
		borrarModeloTabla();
		Object[] nuevaFila=new Object[11];
		List<AccionEmpleado> acciones = new ArrayList<AccionEmpleado>();
		try {
			acciones = pbd.devolverAccionesEmpleadoNombre(txtNombreDeUsuario.getText());
		} catch (SQLException e) {

			e.printStackTrace();
		}
	
		for(AccionEmpleado a:acciones) {
			nuevaFila[0]= a.getNaccion();
			nuevaFila[1]= a.getCodempleado();
			nuevaFila[2] = a.getDate();
			nuevaFila[3] =a.getHora();
			nuevaFila[4] =a.getMensajeAccion();
			modeloTabla.addRow(nuevaFila);
		
		}
		
	}

	private JButton getBtnNewButton_2() {
		if (btnNewButton_2 == null) {
			btnNewButton_2 = new JButton("Fecha y nombre");
			btnNewButton_2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(txtNombreDeUsuario.getText().equals("")|| txtNombreDeUsuario.getText().equals("Nombre de usuario"))
						JOptionPane.showMessageDialog(null, "Por favor, introduzca un nombre de usuario válido");
					else
					añadirFilasNombreFecha();
					
					txtNombreDeUsuario.setText("Nombre de usuario");
				}
			});
		}
		return btnNewButton_2;
	}
	protected void añadirFilasNombreFecha() {
		borrarModeloTabla();
		Object[] nuevaFila=new Object[11];
		List<AccionEmpleado> acciones = new ArrayList<AccionEmpleado>();
		Date dateIn = getDateChooser().getDate();
		java.sql.Date sDateIn = new java.sql.Date(dateIn.getTime());
		
		Date dateFin = getDateChooser_1().getDate();
		java.sql.Date sDateFin = new java.sql.Date(dateFin.getTime());
		try {
			acciones = pbd.devolverAccionesEmpleadoFechaNombre(txtNombreDeUsuario.getText(), sDateIn, sDateFin);
		} catch (SQLException e) {

			e.printStackTrace();
		}
	
		for(AccionEmpleado a:acciones) {
			nuevaFila[0]= a.getNaccion();
			nuevaFila[1]= a.getCodempleado();
			nuevaFila[2] = a.getDate();
			nuevaFila[3] =a.getHora();
			nuevaFila[4] =a.getMensajeAccion();
			modeloTabla.addRow(nuevaFila);
		
		}
		
	}

	private JTextField getTxtNombreDeUsuario() {
		if (txtNombreDeUsuario == null) {
			txtNombreDeUsuario = new JTextField();
			txtNombreDeUsuario.setText("Nombre de usuario");
			txtNombreDeUsuario.setColumns(10);
			txtNombreDeUsuario.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					txtNombreDeUsuario.setText("");
				}
				});
		}
		return txtNombreDeUsuario;
	}
}
