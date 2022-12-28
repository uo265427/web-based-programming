package ui.gerente;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;

import logica.AsignaDiagnostico;
import logica.Cita;
import logica.Diagnostico;
import logica.servicios.ParserBaseDeDatos;
import ui.AnadirAntecedentesHistorial;
import ui.inicio.VentanaInicio;
import ui.medico.ModeloNoEditable;

import java.awt.GridLayout;
import java.awt.Toolkit;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JLabel;
import com.toedter.calendar.JDateChooser;
import java.util.Date;
import java.awt.Font;

public class DatosGerente extends JDialog {

	private JPanel contentPane;
	private JPanel panelDatos;
	private JPanel panelTabla;
	private JPanel panelGrafico;
	private JScrollPane scrollPane;
	private JTable table;
	private ModeloNoEditable modeloTabla;
	private ParserBaseDeDatos pbd = new ParserBaseDeDatos();
	private DefaultCategoryDataset dataset= new DefaultCategoryDataset();
	private JScrollPane scrollPaneGrafico;
	private JPanel panelFiltros;
	private JPanel panelBotones;
	private JButton btnCerrar;
	private JButton btnFiltrarPorNombre;
	private JLabel lblFechaIn;
	private JDateChooser dateChooser;
	private JLabel lblFechaIn_1;
	private JDateChooser dateChooser_1;
	private JButton btnFiltroFechas;
	private JButton btnTodas;
	private JPanel panel;
	private JPanel panel_3;
	private JLabel lblNewLabel_1;
	private JButton btnNewButton_2;

	/**
	 * Create the frame.
	 */
	public DatosGerente() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(AnadirAntecedentesHistorial.class.getResource("/img/logop.jpg")));
		setTitle("Estad\u00EDstica diagn\u00F3sticos");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1300, 750);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.add(getPanelFiltros(), BorderLayout.NORTH);
		contentPane.add(getPanelDatos());
		contentPane.add(getPanelBotones(), BorderLayout.SOUTH);
	}

	private JPanel getPanelDatos() {
		if (panelDatos == null) {
			panelDatos = new JPanel();
			panelDatos.setLayout(new GridLayout(1, 0, 0, 0));
			panelDatos.add(getPanelTabla());
			panelDatos.add(getScrollPaneGrafico());
		}
		return panelDatos;
	}

	private JPanel getPanelTabla() {
		if (panelTabla == null) {
			panelTabla = new JPanel();
			panelTabla.setLayout(new GridLayout(0, 1, 0, 0));
			panelTabla.add(getScrollPane());
		}
		return panelTabla;
	}
	private JPanel getPanelGrafico() {
		if (panelGrafico == null) {
			panelGrafico = new JPanel();
		}
		añadirGrafico();
		return panelGrafico;
	}
	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(getTable());
			
		}
		return scrollPane;
	}
	
	private JScrollPane getScrollPaneGrafico() {
		if (scrollPaneGrafico == null) {
			scrollPaneGrafico = new JScrollPane();
			scrollPaneGrafico.setViewportView(getPanelGrafico());
		}
		return scrollPaneGrafico;
	}


	private JTable getTable() {
		if (table == null) {
			String[] nombreColumnas= {" Codigo diagnóstico ", " Nombre diagnóstico "," Nº veces ", "Porcentaje"};
			modeloTabla= new ModeloNoEditable(nombreColumnas,0);
			table = new JTable(modeloTabla);
			table.getTableHeader().setReorderingAllowed(false);//Evita que se pueda mpver las columnas
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			table.getTableHeader().setBackground(Color.LIGHT_GRAY);
			
			TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(table.getModel());
			table.setRowSorter(sorter);
			
			List<RowSorter.SortKey> sortKeys = new ArrayList<>();
			sortKeys.add(new RowSorter.SortKey(1, SortOrder.ASCENDING));
			sorter.setSortKeys(sortKeys);
//			for (int i = 2; i < 2; i++) {
//				table.getColumnModel().getColumn(i).setMinWidth(0);
//				table.getColumnModel().getColumn(i).setMaxWidth(0);
//				table.getColumnModel().getColumn(i).setWidth(0);
//			}
			
			añadirFilas(false);
		}
		
		return table;
	}



	private void añadirFilas(boolean dia) {
		borrarModeloTabla();
		Object[] nuevaFila=new Object[4];
		List<AsignaDiagnostico> diagnosticosAsignados = new ArrayList<AsignaDiagnostico>();
		List<AsignaDiagnostico> diagnosticosEstan = new ArrayList<AsignaDiagnostico>();
		try {
			diagnosticosAsignados = pbd.asignaDiagnostico();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		System.out.println(diagnosticosAsignados.size()+"");
//		List<Diagnostico> diagnosticos = new ArrayList<Diagnostico>();
//		try {
//			diagnosticos = pbd.listarDiagnosticos();
//		} catch (SQLException e) {
//
//			e.printStackTrace();
//		}
		dataset.clear();
		if(dia) {
			float total = 0;
			Date dateIn = getDateChooser().getDate();
			java.sql.Date sDateIn = new java.sql.Date(dateIn.getTime());
			
			Date dateFin = getDateChooser_1().getDate();
			java.sql.Date sDateFin = new java.sql.Date(dateFin.getTime());
			try {
				diagnosticosAsignados = pbd.buscarCodDiagnosticoPorFechas(sDateIn, sDateFin);
				total = pbd.calcularAsignDiagFecha(sDateIn, sDateFin);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			for(int i=0; i<diagnosticosAsignados.size(); i++) {
				int cant = 0;
				String numDiagnostico = "";
				try {
					numDiagnostico = pbd.buscarCodDiagnostico(diagnosticosAsignados.get(i).getNombreDiagnostico());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				nuevaFila[0] = numDiagnostico;
				nuevaFila[1]= diagnosticosAsignados.get(i).getNombreDiagnostico();
				cant = diagnosticosAsignados.get(i).getCantidad();
				nuevaFila[2]= cant + "";
				int datoss1 = cant;
				float porcentaje = ((cant/total) * 100);
				nuevaFila[3] = porcentaje + "%";
				dataset.setValue(datoss1,"", diagnosticosAsignados.get(i).getNombreDiagnostico());
				modeloTabla.addRow(nuevaFila);
			}
			
		}
		else {
			float total = 0;
			try {
				total = pbd.calcularAsignDiag();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			for(int i=0; i<diagnosticosAsignados.size(); i++) {
				int cant = 0;
				String numDiagnostico = "";
				try {
					numDiagnostico = pbd.buscarCodDiagnostico(diagnosticosAsignados.get(i).getNombreDiagnostico());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				nuevaFila[0] = numDiagnostico;
				nuevaFila[1]= diagnosticosAsignados.get(i).getNombreDiagnostico();
				cant = diagnosticosAsignados.get(i).getCantidad();
				nuevaFila[2]= cant;
				float porcentaje = ((cant/total) * 100);
				nuevaFila[3] = porcentaje + "%";
				int datoss1 = cant;
				
				dataset.setValue(datoss1,"", diagnosticosAsignados.get(i).getNombreDiagnostico());
				
				modeloTabla.addRow(nuevaFila);
				diagnosticosEstan.add(diagnosticosAsignados.get(i));
			}
		}
		
	}
	

	private boolean estaDiagnostico(List<AsignaDiagnostico> diagnosticosAsignados,
			List<AsignaDiagnostico> diagnosticosEstan) {
		boolean retornar = true;
		for(int i=0;i<0; diagnosticosAsignados.size()) {
			if(diagnosticosEstan.contains(diagnosticosAsignados.get(i))) {
				retornar = false;
			}
		}
		return retornar;
	}

	private void añadirGrafico() {
		JFreeChart chart = ChartFactory.createBarChart("Diagnósticos", "", "", dataset, PlotOrientation.VERTICAL, false, false, false);
		CategoryPlot catPlot = chart.getCategoryPlot();
		catPlot.setRangeGridlinePaint(Color.BLACK);
		
		ChartPanel chartPanel = new ChartPanel(chart);
		panelGrafico.add(chartPanel, BorderLayout.CENTER);
		
	}

	private void borrarModeloTabla() {
		int filas=modeloTabla.getRowCount();
			for (int i = 0; i <filas; i++) {
				modeloTabla.removeRow(0);
				
			}
	}
	private JPanel getPanelFiltros() {
		if (panelFiltros == null) {
			panelFiltros = new JPanel();
			panelFiltros.setLayout(new GridLayout(2, 1, 0, 0));
			panelFiltros.add(getPanel_3());
			panelFiltros.add(getPanel());
		}
		return panelFiltros;
	}
	private JPanel getPanelBotones() {
		if (panelBotones == null) {
			panelBotones = new JPanel();
			panelBotones.add(getBtnCerrar());
		}
		return panelBotones;
	}
	private JButton getBtnCerrar() {
		if (btnCerrar == null) {
			btnCerrar = new JButton("Cerrar");
			btnCerrar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
		}
		return btnCerrar;
	}



	private JLabel getLblFechaIn() {
		if (lblFechaIn == null) {
			lblFechaIn = new JLabel("Fecha inicio");
		}
		return lblFechaIn;
	}
	private JDateChooser getDateChooser() {
		if (dateChooser == null) {
			dateChooser = new JDateChooser(new Date());
		}
		return dateChooser;
	}
	private JLabel getLblFechaIn_1() {
		if (lblFechaIn_1 == null) {
			lblFechaIn_1 = new JLabel("Fecha fin");
		}
		return lblFechaIn_1;
	}
	private JDateChooser getDateChooser_1() {
		if (dateChooser_1 == null) {
			dateChooser_1 = new JDateChooser(new Date());
		}
		return dateChooser_1;
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
			btnTodas = new JButton("Todos los diagn\u00F3sticos");
			btnTodas.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					añadirFilas(false);
				}
			});
		}
		return btnTodas;
	}
	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.add(getLblFechaIn());
			panel.add(getDateChooser());
			panel.add(getLblFechaIn_1());
			panel.add(getDateChooser_1());
			panel.add(getBtnFiltroFechas());
			panel.add(getBtnTodas());
		}
		return panel;
	}
	private JPanel getPanel_3() {
		if (panel_3 == null) {
			panel_3 = new JPanel();
			panel_3.setLayout(null);
			panel_3.add(getLblNewLabel_1());
			panel_3.add(getBtnNewButton_2());
		}
		return panel_3;
	}
	private JLabel getLblNewLabel_1() {
		if (lblNewLabel_1 == null) {
			lblNewLabel_1 = new JLabel("Bienvenido/a Gerente");
			lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
			lblNewLabel_1.setBounds(12, -2, 212, 35);
		}
		return lblNewLabel_1;
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
			btnNewButton_2.setBounds(1108, 4, 152, 27);
		}
		return btnNewButton_2;
	}
	
	private void ventanaInicio() {
		VentanaInicio vmc =new VentanaInicio();
		vmc.setLocationRelativeTo(null);
		vmc.setVisible(true);
} 
}
