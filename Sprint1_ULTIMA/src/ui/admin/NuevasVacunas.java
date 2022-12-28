package ui.admin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import logica.AsignaPreinscripcion;
import logica.AsignaVacuna;
import logica.Preinscripcion;
import ui.AnadirAntecedentesHistorial;
import ui.medico.ModeloNoEditable;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RowSorter;
import javax.swing.SortOrder;

public class NuevasVacunas extends JDialog{

	private JPanel contentPane;
	private JScrollPane scrollPane;
	private JTable table;
	private ModeloNoEditable modeloTable;
	private List<AsignaVacuna> vacunas;
 
	/**
	 * Launch the application.
	 */
	/*
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NuevasVacunas frame = new NuevasVacunas();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
*/
	/**
	 * Create the frame.
	 */
	public NuevasVacunas(List<AsignaVacuna> vn) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(AnadirAntecedentesHistorial.class.getResource("/img/logop.jpg")));
		setTitle("Nuevas vacunas");
		vacunas = vn;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 830, 448);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.add(getScrollPane(), BorderLayout.CENTER);
	}

	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(getTable());
			
		}
		return scrollPane;
	}
	private JTable getTable() {
		if (table == null) {
			modeloTable= new ModeloNoEditable(nombresVacunas(),0);
			table = new JTable(modeloTable);
			table.getTableHeader().setReorderingAllowed(false);//Evita que se pueda mpver las columnas
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			table.getTableHeader().setBackground(Color.LIGHT_GRAY);
			TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(table.getModel());
			table.setRowSorter(sorter);
			
			/*
			List<RowSorter.SortKey> sortKeys = new ArrayList<>();
			sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
			sortKeys.add(new RowSorter.SortKey(5, SortOrder.ASCENDING));
			
			sorter.setSortKeys(sortKeys);
			*/
			añadirFilas();
		}
		return table;
	}
	
	private String[] nombresVacunas(){
		String[] nombres = {"", "Codigo vacuna", "Codigo empleado", "Fecha", "Hora"};
		
		return nombres;
	}
	
	private void añadirFilas() {
		borrarModeloTabla(); // Borramos todo antes de volver a pintar
	
		Object[] nuevaFila=new Object[6]; 
		
		for (AsignaVacuna a : vacunas) {
			nuevaFila[0] = a.getNombreVacuna(); // El nombre de la preinscripcion

			nuevaFila[1] = "" + a.getCodVacuna();
			nuevaFila[2] = "" + a.getCodEmpleado();
			nuevaFila[3] = "" + a.getDate();
			nuevaFila[4] = "" + a.getTime();
			
			modeloTable.addRow(nuevaFila); // Añado la fila
		}
	}
	
	private void borrarModeloTabla() {
		int filas = modeloTable.getRowCount();
		for (int i = 0; i < filas; i++) {
			modeloTable.removeRow(0);			
		}		
	}
	
}
