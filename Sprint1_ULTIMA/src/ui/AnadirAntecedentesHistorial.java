/**
 * 
 */
package ui;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import logica.Antecedente;
import logica.AsignaAntecedente;
import logica.HistorialMedico;
import logica.Paciente;
import logica.servicios.ParserBaseDeDatos;
import ui.medico.AnadirAntecedente;
import ui.medico.ModeloNoEditable;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Rectangle;
import java.awt.Toolkit;

/**
 * @author María
 *
 */
public class AnadirAntecedentesHistorial extends JDialog{

	private ParserBaseDeDatos pbd=new ParserBaseDeDatos();

	private List<Antecedente> antecedentes; // Los antecedentes que tenemos en la base de datos
	private ModeloNoEditable modeloTabla;
	private boolean tablaLista = false;
	private List<AsignaAntecedente> asignaAntecedentesPaciente = new ArrayList<AsignaAntecedente>();
	private Paciente paciente;
	private Antecedente antecedente; // El antecedente
	private List<String> nombresAntecedentes;
	private boolean listoAntecedentes = true;



	private MostrarHistorial mostrarHistorial;
	private HistorialMedico historial;
	private String codempleado;
	private JPanel pnSur;
	private JPanel pnDcha;
	private JPanel pnCentro;
	private JButton btnAtras;
	private JButton btnGuardar;
	private JButton btnCancelar;
	private JPanel pnAcciones;
	private JPanel pnNuevo;
	private JPanel pnCb;
	private JPanel pnAnadir;
	private JPanel pnVacio;
	private JButton btnNuevo;
	private JComboBox cbAntecedentes;
	private JTextField txtFieldFiltrar;
	private JButton btnBuscar;
	private JButton btnAsignar;
	private JPanel pnResumen;
	private JPanel pnBorrar;
	private JPanel pnSeleccionados;
	private JLabel lblSeleccionados;
	private JButton btnBorrarAntecedente;
	private JScrollPane scrollPane;
	private JTable table;
	
	
	
	
	/**
	 * @param mostrarHistorial
	 * @param historial
	 * @param codempleado
	 */
	public AnadirAntecedentesHistorial(MostrarHistorial mostrarHistorial, HistorialMedico historial, String codempleado) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(AnadirAntecedentesHistorial.class.getResource("/img/logop.jpg")));
		setModal(true);
		setTitle("A\u00F1adir antecedente");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1100, 500);
		this.mostrarHistorial = mostrarHistorial;
		this.historial = historial;
		this.codempleado = codempleado;
		
		try {
			paciente = pbd.buscarPacienteConHistorial(historial.getHistorial());
			antecedentes = pbd.cargarAntecedentes();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		getContentPane().add(getPnSur(), BorderLayout.SOUTH);
		getContentPane().add(getPnDcha(), BorderLayout.EAST);
		getContentPane().add(getPnCentro(), BorderLayout.CENTER);
	}
	
	
	private JPanel getPnSur() {
		if (pnSur == null) {
			pnSur = new JPanel();
			FlowLayout flowLayout = (FlowLayout) pnSur.getLayout();
			flowLayout.setAlignment(FlowLayout.RIGHT);
			pnSur.add(getBtnAtras());
			pnSur.add(getBtnGuardar());
			pnSur.add(getBtnCancelar());
		}
		return pnSur;
	}
	private JPanel getPnDcha() {
		if (pnDcha == null) {
			pnDcha = new JPanel();
			pnDcha.setLayout(new BorderLayout(0, 0));
			pnDcha.add(getPnResumen(), BorderLayout.NORTH);
			pnDcha.add(getPnBorrar(), BorderLayout.SOUTH);
			pnDcha.add(getPnSeleccionados(), BorderLayout.CENTER);
		}
		return pnDcha;
	}
	private JPanel getPnCentro() {
		if (pnCentro == null) {
			pnCentro = new JPanel();
			pnCentro.setLayout(new BorderLayout(0, 0));
			pnCentro.add(getPnAcciones(), BorderLayout.CENTER);
		}
		return pnCentro;
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
	private JButton getBtnGuardar() {
		if (btnGuardar == null) {
			btnGuardar = new JButton("Guardar");
			btnGuardar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					try {
						guardar();
						JOptionPane.showMessageDialog(null, "Usted guardado correctamente los antecedentes.");	
						mostrarHistorial.repintarTablaAntecedentes();
						dispose();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			});
		}
		return btnGuardar;
	}
	
	
	
	protected void guardar() throws SQLException {
		if (!asignaAntecedentesPaciente.isEmpty()) { // Que le hayamos asignado algun antecedente
			//guardarAccionAntecedentes();
			for (AsignaAntecedente aa : asignaAntecedentesPaciente) { // Voy guardando cada uno de los antecedentes
				pbd.nuevaAsignaAntecedente(aa);
			}
		}		
	}


	private JButton getBtnCancelar() {
		if (btnCancelar == null) {
			btnCancelar = new JButton("Cancelar");
			btnCancelar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					cancelar();
				}
			});
		}
		return btnCancelar;
	}
	
	/**
	 * Método para cancelar la operación
	 */
	protected void cancelar() {
		JOptionPane.showMessageDialog(null, "Usted ha cancelado la operación.");	
		dispose();
	}


	private JPanel getPnAcciones() {
		if (pnAcciones == null) {
			pnAcciones = new JPanel();
			pnAcciones.setLayout(new GridLayout(4, 0, 0, 0));
			pnAcciones.add(getPnNuevo());
			pnAcciones.add(getPnCb());
			pnAcciones.add(getPnAnadir());
			pnAcciones.add(getPnVacio());
		}
		return pnAcciones;
	}
	private JPanel getPnNuevo() {
		if (pnNuevo == null) {
			pnNuevo = new JPanel();
			pnNuevo.setLayout(null);
			pnNuevo.add(getBtnNuevo());
		}
		return pnNuevo;
	}
	private JPanel getPnCb() {
		if (pnCb == null) {
			pnCb = new JPanel();
			pnCb.setLayout(null);
			pnCb.add(getCbAntecedentes());
		}
		return pnCb;
	}
	private JPanel getPnAnadir() {
		if (pnAnadir == null) {
			pnAnadir = new JPanel();
			pnAnadir.setLayout(null);
			pnAnadir.add(getTxtFieldFiltrar());
			pnAnadir.add(getBtnBuscar());
			pnAnadir.add(getBtnAsignar());
		}
		return pnAnadir;
	}
	private JPanel getPnVacio() {
		if (pnVacio == null) {
			pnVacio = new JPanel();
		}
		return pnVacio;
	}
	private JButton getBtnNuevo() {
		if (btnNuevo == null) {
			btnNuevo = new JButton("Crear nuevo antecedente");
			btnNuevo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					crearAntecedente();					
				}
			});
			btnNuevo.setBounds(45, 53, 187, 21);
		}
		return btnNuevo;
	}
	
	
	/**
	 * Método para crear un nuevo antecedente
	 */
	protected void crearAntecedente() {
		
		AnadirAntecedenteHistorial va = new AnadirAntecedenteHistorial(this);
		
		va.setLocationRelativeTo(this);
		va.setResizable(true);
		va.setModal(true); // hasta que no se cierre una ventana no se puede abrir otra
		va.setVisible(true);
	}


	private JComboBox getCbAntecedentes() {
		if (cbAntecedentes == null) {
			cbAntecedentes = new JComboBox();
			cbAntecedentes.setBounds(48, 42, 576, 21);
			
			
			String[] nombreAntecedentes = new String[antecedentes.size()];
			
			for (int i = 0; i < antecedentes.size(); i++) {
				nombreAntecedentes[i] = antecedentes.get(i).getNombreAntecedente();
			}
			
			cbAntecedentes.setModel(new DefaultComboBoxModel<String>(nombreAntecedentes));
		}
		return cbAntecedentes;
	}
	private JTextField getTxtFieldFiltrar() {
		if (txtFieldFiltrar == null) {
			txtFieldFiltrar = new JTextField();
			txtFieldFiltrar.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					activarBotonFiltrarAntecedentes();
				}
			});
			txtFieldFiltrar.setBounds(50, 6, 189, 28);
			txtFieldFiltrar.setColumns(10);
		}
		return txtFieldFiltrar;
	}
	
	/**
	 * Activa el botón para filtrar
	 */
	protected void activarBotonFiltrarAntecedentes() {
		if (!btnBuscar.isEnabled()) { // Si no estaba activado lo activamos
			btnBuscar.setEnabled(true);
		}			
	}


	private JButton getBtnBuscar() {
		if (btnBuscar == null) {
			btnBuscar = new JButton("Buscar");
			btnBuscar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					buscarAntecedente();
				}
			});
			btnBuscar.setBounds(261, 9, 109, 21);
			btnBuscar.setEnabled(false);
		}
		return btnBuscar;
	}
	
	/**
	 * Método que me busca un antecedente
	 */
	protected void buscarAntecedente() {
		if (!txtFieldFiltrar.getText().equals("")) { // Si hay algo escrito en el campo de texto
			String buscador = txtFieldFiltrar.getText().toLowerCase(); // Lo que ha buscado (lo pasamos a minuscula)
			boolean encontrado = false; // Para saber si encontró o no el antecedentes buscado
			
			for(int i = 0; i < antecedentes.size(); i++) {
				if (antecedentes.get(i).getNombreAntecedente().toLowerCase().equals(buscador)) { // Si lo que está buscando lo hay en la lista de diagnosticos
					
					cambiarIndiceCBAntecedentes(i);
					encontrado = true; // lo encontró
				}
			}
			
			if (!encontrado) { // Si no encontró el diagnostico
				JOptionPane.showMessageDialog(null, "No hemos podido encontrar su antecedente en este momento");
			}
		}
		else {
			JOptionPane.showMessageDialog(null, "No ha introducido nada en el buscador");		
		}
		
	}


	/**
	 * Método que me cambia el índice del cb
	 * @param i
	 */
	private void cambiarIndiceCBAntecedentes(int indice) {
		cbAntecedentes.setSelectedIndex(indice); // Lo mostramos en el cb	
	}


	private JButton getBtnAsignar() {
		if (btnAsignar == null) {
			btnAsignar = new JButton("Asignar antecedente");
			btnAsignar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					anadirAntecedente();
					cambiarIndiceCBAntecedentes(0);
				}
			});
			btnAsignar.setBounds(414, 9, 210, 21);
		}
		return btnAsignar;
	}
	
	
	/**
	 * Método para añadir asignar un antecedente
	 */
	protected void anadirAntecedente() {
		int indice = cbAntecedentes.getSelectedIndex(); // el índice que hay seleccionado en el cb
		Antecedente antecedente = null;
		
		// Buscamos la vacuna que hay seleccionada en el cb
		int contador = 0;
		for(Antecedente a : antecedentes) {
			if (indice == contador) {
				antecedente = a;
			}
			contador = contador + 1;
		}
		
		Random r = new Random();
		String codAsigAntecedente = "" + r.nextInt(999999); // El código es aleatorio
		
		String nombreAntecedente = antecedente.getNombreAntecedente();
		String nHistorial = historial.getHistorial(); // El número de historial del paciente a quien le hemos asignado el antecedente
		String nAntecedente = antecedente.getCodAntecedente(); // El identificador del antecedente
		String codEmpleado = codempleado;
		Date fecha = new Date();	
		Time hora = new Time(new Date().getTime());
		AsignaAntecedente aa = new AsignaAntecedente(codAsigAntecedente, nombreAntecedente, nAntecedente, nHistorial, codEmpleado, fecha, hora);

		asignaAntecedentesPaciente.add(aa);
		
		
		if (tablaLista == false) { // Para que no casque al pintar la tabla de los antecedentes
			tablaLista = true;
		}
		
		añadirFilas(); // Añadimos a la tabla que nos muestra las preinscripciones que ya le hemos asignado
		
		limpiarPanel(); // Para ponerlo todo de 0
	}


	
	/**
	 * Método para limpiar el panel
	 */
	private void limpiarPanel() {
		
	}


	private JPanel getPnResumen() {
		if (pnResumen == null) {
			pnResumen = new JPanel();
			FlowLayout flowLayout = (FlowLayout) pnResumen.getLayout();
			flowLayout.setAlignment(FlowLayout.LEFT);
			pnResumen.add(getLblSeleccionados());
		}
		return pnResumen;
	}
	private JPanel getPnBorrar() {
		if (pnBorrar == null) {
			pnBorrar = new JPanel();
			FlowLayout flowLayout = (FlowLayout) pnBorrar.getLayout();
			flowLayout.setAlignment(FlowLayout.RIGHT);
			pnBorrar.add(getBtnBorrarAntecedente());
		}
		return pnBorrar;
	}
	private JPanel getPnSeleccionados() {
		if (pnSeleccionados == null) {
			pnSeleccionados = new JPanel();
			pnSeleccionados.setLayout(new GridLayout(1, 0, 0, 0));
			pnSeleccionados.add(getScrollPane());
			//pnSeleccionados.add(getTable());
		}
		return pnSeleccionados;
	}
	private JLabel getLblSeleccionados() {
		if (lblSeleccionados == null) {
			lblSeleccionados = new JLabel("Antecedentes seleccionados:");
		}
		return lblSeleccionados;
	}
	private JButton getBtnBorrarAntecedente() {
		if (btnBorrarAntecedente == null) {
			btnBorrarAntecedente = new JButton("Borrar antecedente");
			btnBorrarAntecedente.setBounds(new Rectangle(0, 0, 300, 21));
			btnBorrarAntecedente.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					borrarFilaAntecedente();
				}
			});
		}
		return btnBorrarAntecedente;
	}
	
	
	
	
	/**
	 * Método que me borra una fila seleccionada de la tabla de antecedentes
	 */
	protected void borrarFilaAntecedente() {
		int fila = table.getSelectedRow();
		
		if (fila != -1) {
			int res = JOptionPane.showConfirmDialog(null, "¿Está seguro de que desea borrar el antecedente?","Mensaje de confirmación",JOptionPane.YES_NO_OPTION);
			if(res == JOptionPane.YES_OPTION) {	
					
					asignaAntecedentesPaciente.remove(table.getSelectedRow());
					
					añadirFilas();
			}						
		}	
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
			String[] nombreColumnas= {"Nombre","Fecha"};
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
			
			
			añadirFilas();
		}
		return table;
	}


	/**
	 * Método para añadir filas a la tabla
	 */
	void añadirFilas() {
		borrarModeloTabla(); // Borramos todo antes de volver a pintar
		
		Object[] nuevaFila=new Object[2]; // 2 son las columnas
				
		
		if (tablaLista) {
			for (AsignaAntecedente a : asignaAntecedentesPaciente) {
				nuevaFila[0] = a.getNombreAntecedente(); // El nombre del antecedente
				nuevaFila[1] = a.getFecha();
				
				modeloTabla.addRow(nuevaFila); // Añado la fila				
			}		
		}
	}


	/**
	 * Borra la tabla antes de pintarla
	 */
	private void borrarModeloTabla() {
		int filas = modeloTabla.getRowCount();
		
		for (int i = 0; i < filas; i++) {
			modeloTabla.removeRow(0);			
		}		
	}


	
	/**
	 * Método para cambiar el antecedente
	 * @param antecedente
	 */
	public void setAntecedente(Antecedente antecedente) {
		this.antecedente = antecedente;	
	}


	
	/**
	 * Método para poner los antecedentes en el cb
	 * @throws SQLException 
	 */
	public void ponerAntecedentes() throws SQLException {
		nombresAntecedentes = new ArrayList<>();
		List<String> antecedentes = pbd.buscarNombreTodosAntecedentes();
		for(int i =0; i< antecedentes.size(); i++) {
			nombresAntecedentes.add(antecedentes.get(i));
		}	
	}


	
	/**
	 * Método para actualizar el antecedente
	 */
	public void actualizarAntecedente() {
		try {
			antecedentes = pbd.cargarAntecedentes(); // Volvemos a cargar los antecedentes de la base de datos después de haber añadido
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		listoAntecedentes = false;
		cbAntecedentes.removeAllItems(); // Vacío todo el combo box
		
		// Cargo otra vez los antecedentes en el cb
		String[] nombreAntecedentes = new String[antecedentes.size()];
		for (int i = 0; i< antecedentes.size(); i++) {
			nombreAntecedentes[i] = antecedentes.get(i).getNombreAntecedente();
		}
		
		cbAntecedentes.setModel(new DefaultComboBoxModel<String>(nombreAntecedentes));				

		listoAntecedentes = true;
		
		//this.revalidate();
	//	repaint();
		
		int contador = 0;
		for (Antecedente a : antecedentes) {
			if (a.getNombreAntecedente().toLowerCase().equals(antecedente.getNombreAntecedente().toLowerCase())) {
				cambiarIndiceCBAntecedentes(contador);
				
			}
			contador = contador + 1;
		}
	}


	/**
	 * Método que me pinta la ventana nueva
	 */
	public void limpiar() {
		txtFieldFiltrar.setText("");
		
		if (btnBuscar.isEnabled()) {
			btnBuscar.setEnabled(false);
		}
	}
}
