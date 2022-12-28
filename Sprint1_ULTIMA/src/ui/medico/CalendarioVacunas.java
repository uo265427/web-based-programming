package ui.medico;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import logica.AsignaVacuna;
import logica.Paciente;
import logica.Vacuna;
import logica.servicios.ParserBaseDeDatos;
import ui.AnadirAntecedentesHistorial;
import ui.admin.NuevasVacunas;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JLabel;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CalendarioVacunas extends JDialog {

	private JPanel contentPane;
	private JPanel panel;
	private JTable table;
	private JScrollPane scrollPane;
	private JTable tablaVacunas;
	private ModeloNoEditable modeloTabla;
	private ModeloNoEditable modeloNombres;
	private JLabel lblPaciente;
	private Paciente paciente;
	private ParserBaseDeDatos pbd = new ParserBaseDeDatos();
	private List<AsignaVacuna> vacunas = new ArrayList<AsignaVacuna>();
	
	private String[] nombreVacunas = {"Hepatitis B", "Difteria, tétanos y tosferina", "Poliomelitis", "Influenza", "Neumococo", "Rotavirus", "Menungococo B", "Meningococo C y ACWY",
			"Sarampión, rubeola y parotiditis", "Varicela", "Virus del papiloma humano"};
	private JPanel panel_1;
	private JPanel panelOtras;
	private JButton btnNewButton;
	
	private List<AsignaVacuna> vacunasNuevas = new ArrayList<AsignaVacuna>();
	
	/**
	 * Launch the application.
	 */
	/*
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CalendarioVacunas frame = new CalendarioVacunas();
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
	public CalendarioVacunas(Paciente p) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(AnadirAntecedentesHistorial.class.getResource("/img/logop.jpg")));
		paciente = p;
		try {
			vacunas = pbd.verVacunasPaciente(paciente.getHistorial());
			sacarVacunasNuevas();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		setTitle("Calendario de vacunas");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1106, 466);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.add(getPanel(), BorderLayout.NORTH);
		contentPane.add(getScrollPane(), BorderLayout.CENTER);
		contentPane.add(getPanelOtras(), BorderLayout.SOUTH);
		//contentPane.add(getTable_1(), BorderLayout.WEST);
		
	}
	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.add(getLblPaciente());
			panel.add(getPanel_1());
		}
		return panel;
	}
	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(getTablaVacunas());
		}
		return scrollPane;
	}
	private JTable getTablaVacunas() {
		if (tablaVacunas == null) {
			String[] edad = {"VACUNAS", "2 meses", "3 meses", "4 meses", "5 meses", "11 meses", "12 meses", "15 meses", "3-4 años", "6 años", "12 años", "14 años", "15-18 años"};
			//
			modeloTabla = new ModeloNoEditable(edad, 11);
			
			tablaVacunas = new JTable(modeloTabla) {
				@Override
				public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
					Component comp = super.prepareRenderer(renderer, row, col);
					Object value = getModel().getValueAt(row, col);
					
					if(value != null) {
						for (int i = 0; i < vacunas.size(); i++) {
							
							if(value.toString().equals(vacunas.get(i).getNombreVacuna())) {
							
									System.out.println("Correcta: " + vacunas.get(i).getNombreVacuna());
									
									comp.setBackground(Color.GREEN);
								
							}
						}
//						if (isVaccinated(value.toString())) {
//							comp.setBackground(Color.GREEN);
//						} 
//						else
//							comp.setBackground(null);
					}
					else
						comp.setBackground(null);
					return comp;
				}
			};

		    
			tablaVacunas.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			tablaVacunas.setRowHeight(30);
			tablaVacunas.setEnabled(false);
			
			tablaVacunas.setValueAt("Hepatitis B", 0, 0);
			tablaVacunas.setValueAt("Difteria, tétanos y tosferina", 1, 0);
			tablaVacunas.setValueAt("Poliomelitis", 2, 0);
			tablaVacunas.setValueAt("Haemophilus influenzae tipo b", 3, 0);
			tablaVacunas.setValueAt("Neumococo", 4, 0);
			tablaVacunas.setValueAt("Rotavirus", 5, 0);
			tablaVacunas.setValueAt("Meningococo B", 6, 0);
			tablaVacunas.setValueAt("Meningococos C y ACWY", 7, 0);
			tablaVacunas.setValueAt("Sarampión, rubeola y parotiditis", 8, 0);
			tablaVacunas.setValueAt("Varicela", 9, 0);
			tablaVacunas.setValueAt("Virus del papiloma humano", 10, 0);
			
			tablaVacunas.setValueAt("HB", 0, 1);
			tablaVacunas.setValueAt("HB", 0, 3);
			tablaVacunas.setValueAt("HB", 0, 5);
			
			tablaVacunas.setValueAt("DTPa", 1, 1);
			tablaVacunas.setValueAt("DTPa", 1, 3);
			tablaVacunas.setValueAt("DTPa", 1, 5);
			tablaVacunas.setValueAt("DTPa", 1, 9);
			tablaVacunas.setValueAt("Tdpa", 1, 10);
			tablaVacunas.setValueAt("Tdpa", 1, 11);
			
			tablaVacunas.setValueAt("VPI", 2, 1);
			tablaVacunas.setValueAt("VPI", 2, 3);
			tablaVacunas.setValueAt("VPI", 2, 5);
			tablaVacunas.setValueAt("VPI", 2, 9);
			
			tablaVacunas.setValueAt("Hib", 3, 1);
			tablaVacunas.setValueAt("Hib", 3, 3);
			tablaVacunas.setValueAt("Hib", 3, 5);
			
			tablaVacunas.setValueAt("VNC", 4, 1);
			tablaVacunas.setValueAt("VNC", 4, 3);
			tablaVacunas.setValueAt("VNC", 4, 5);
			
			tablaVacunas.setValueAt("RV", 5, 1);
			tablaVacunas.setValueAt("RV", 5, 2);
			tablaVacunas.setValueAt("RV", 5, 3);
			
			tablaVacunas.setValueAt("MenB", 6, 2);
			tablaVacunas.setValueAt("MenB", 6, 4);
			tablaVacunas.setValueAt("MenB", 6, 6);
			tablaVacunas.setValueAt("MenB", 6, 6);
			
			tablaVacunas.setValueAt("MenC", 7, 3);
			tablaVacunas.setValueAt("MenACWY", 7, 6);
			tablaVacunas.setValueAt("MenACWY", 7, 10);
			tablaVacunas.setValueAt("MenACWY", 7, 11);
			tablaVacunas.setValueAt("MenACWY", 7, 12);
			
			tablaVacunas.setValueAt("SRP", 8, 6);
			tablaVacunas.setValueAt("SRP Var/SRPV", 8, 8);
			
			tablaVacunas.setValueAt("Var", 9, 7);
			tablaVacunas.setValueAt("SRP", 9, 8);
			
			tablaVacunas.setValueAt("VPH", 10, 10);
			
		}
		return tablaVacunas;
	}
	private JLabel getLblPaciente() {
		if (lblPaciente == null) {
			lblPaciente = new JLabel("");
			lblPaciente.setText(paciente.getNombre() + " " +  paciente.getApellido());
		}
		return lblPaciente;
	}
	
	
	
	private boolean isVaccinated(String vacuna) {
		boolean vaccinated = false;
		System.out.println("Vacuna en tabla: " + vacuna);
			for (int i = 0; i < vacunas.size(); i++) {
				
				if(vacuna.equals(vacunas.get(i).getNombreVacuna())) {
					System.out.println("Correcta: " + vacunas.get(i).getNombreVacuna());
					
					vaccinated = true;
					
				}
				else
					vaccinated=false;
			}
			System.out.println();
			System.out.println(vaccinated + "");
		return vaccinated;
	}
	
	private JPanel getPanel_1() {
		if (panel_1 == null) {
			panel_1 = new JPanel();
		}
		return panel_1;
	}
	private JPanel getPanelOtras() {
		if (panelOtras == null) {
			panelOtras = new JPanel();
			panelOtras.add(getBtnNewButton());
		}
		return panelOtras;
	}
	private JButton getBtnNewButton() {
		if (btnNewButton == null) {
			btnNewButton = new JButton("Otras vacunas");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					mostrarVentanaNueva();
					
				}
			});
		}
		return btnNewButton;
	}
	
	private void sacarVacunasNuevas(){
		for(int i=0; i< vacunas.size(); i++) {
			/*
			for(String nombre : nombreVacunas) {
				if(!(nombre.equals(vacunas.get(i).getNombreVacuna()))) {
					vacunasNuevas.add(vacunas.get(i));
					System.out.print(vacunas.get(i).getNombreVacuna());
				}
			}
			*/
			if(!(Arrays.asList(nombreVacunas).contains(vacunas.get(i).getNombreVacuna()))) {
				vacunasNuevas.add(vacunas.get(i));
				/*
				System.out.println("Vacunas: " + vacunas.size());
				System.out.println("Nombre: " + nombreVacunas.length);
				System.out.println(vacunas.get(i).getNombreVacuna());
				*/
			}
		}
		
	}
	
	private void mostrarVentanaNueva() {
		NuevasVacunas nv = new NuevasVacunas(vacunasNuevas);
		nv.setModal(true);
		nv.setVisible(true);
		nv.setLocationRelativeTo(this);
		nv.setResizable(true);
	}
}
