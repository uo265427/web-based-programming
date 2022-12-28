/**
 * 
 */
package ui.medico;

import javax.swing.JDialog;

import logica.AsignaAntecedente;
import logica.AsignaPreinscripcion;
import logica.Paciente;
import logica.Preinscripcion;
import logica.servicios.ParserBaseDeDatos;
import ui.AnadirAntecedentesHistorial;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JButton;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import java.awt.GridLayout;
import java.awt.Toolkit;

/**
 * @author María
 *
 */
public class VerAntecedentes extends JDialog{

	private ModeloNoEditable modeloTabla;
	private boolean tablaLista = false;
	
	private ParserBaseDeDatos pbd=new ParserBaseDeDatos();
	private ModificarMedicosNuevoCard modificarMedicosNuevoCard;
	private Paciente paciente;
	
	List<AsignaAntecedente> antecedentesAsignados;
	
	
	private JPanel pnSur;
	private JButton btnAtras;
	private JPanel pnCentro;
	private JPanel pnNorte;
	private JLabel lblPaciente;
	private JScrollPane scrollPane;
	private JTable table;
	
	/**
	 * Constructor para la clase Ver Antecedentes
	 * @param modificarMedicosNuevoCard
	 * @param paciente
	 */
	public VerAntecedentes(ModificarMedicosNuevoCard modificarMedicosNuevoCard, Paciente paciente) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(AnadirAntecedentesHistorial.class.getResource("/img/logop.jpg")));
		setTitle("Antecedentes");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 600, 350);
		this.modificarMedicosNuevoCard = modificarMedicosNuevoCard;
		this.paciente = paciente;
		
		try {
			this.antecedentesAsignados = pbd.listarAntecedentesAsignados(paciente.getHistorial());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		getContentPane().add(getPnSur(), BorderLayout.SOUTH);
		getContentPane().add(getPnCentro(), BorderLayout.CENTER);
		getContentPane().add(getPnNorte(), BorderLayout.NORTH);
	}

	private JPanel getPnSur() {
		if (pnSur == null) {
			pnSur = new JPanel();
			FlowLayout flowLayout = (FlowLayout) pnSur.getLayout();
			flowLayout.setAlignment(FlowLayout.RIGHT);
			pnSur.add(getBtnAtras());
		}
		return pnSur;
	}
	private JButton getBtnAtras() {
		if (btnAtras == null) {
			btnAtras = new JButton("Atr\u00E1s");
			btnAtras.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
		}
		return btnAtras;
	}
	private JPanel getPnCentro() {
		if (pnCentro == null) {
			pnCentro = new JPanel();
			pnCentro.setLayout(new GridLayout(0, 1, 0, 0));
			pnCentro.add(getScrollPane());
			//pnCentro.add(getTable());
		}
		return pnCentro;
	}
	private JPanel getPnNorte() {
		if (pnNorte == null) {
			pnNorte = new JPanel();
			FlowLayout flowLayout = (FlowLayout) pnNorte.getLayout();
			flowLayout.setAlignment(FlowLayout.LEFT);
			pnNorte.add(getLblPaciente());
		}
		return pnNorte;
	}
	private JLabel getLblPaciente() {
		if (lblPaciente == null) {
			lblPaciente = new JLabel("");
			
			lblPaciente.setText("Antecedentes del paciente " + paciente.getNombre() + ":");

		}
		return lblPaciente;
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
			String[] nombreColumnas= {"Antecedente","Fecha", "Empleado"};
			modeloTabla= new ModeloNoEditable(nombreColumnas,0);
			table = new JTable(modeloTabla);
			table.getTableHeader().setReorderingAllowed(false);//Evita que se pueda mpver las columnas
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			table.getTableHeader().setBackground(Color.LIGHT_GRAY);
			TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(table.getModel());
			table.setRowSorter(sorter);
			
			List<RowSorter.SortKey> sortKeys = new ArrayList<>();
			sortKeys.add(new RowSorter.SortKey(4, SortOrder.ASCENDING));
			sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
			
			//sorter.setSortKeys(sortKeys);
			
			try {
				añadirFilas();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return table;
	}

	
	/**
	 * Método para añadir las filas a la tabla
	 * @throws SQLException 
	 */
	private void añadirFilas() throws SQLException {
		borrarModeloTabla(); // Borramos todo antes de volver a pintar
		
		Object[] nuevaFila=new Object[3]; // 3 son las columnas
				
		
			for (AsignaAntecedente a : antecedentesAsignados) {
				nuevaFila[0] = a.getNombreAntecedente(); // El nombre del antecedendente
				nuevaFila[2] = pbd.buscarEmpleadoPorCodigo(a.getCodEmpleado());
				nuevaFila[1] = a.getFecha(); // La fecha en la que se le asigno
				//nuevaFila[2] = a.getCodEmpleado(); // El empleado que se lo asigno

				
				modeloTabla.addRow(nuevaFila); // Añado la fila
			}	
	
		
	}

	
	/**
	 * Método para borrar toda la tabla
	 */
	private void borrarModeloTabla() {
		int filas = modeloTabla.getRowCount();
		for (int i = 0; i < filas; i++) {
			modeloTabla.removeRow(0);			
		}	
		
	}
}
