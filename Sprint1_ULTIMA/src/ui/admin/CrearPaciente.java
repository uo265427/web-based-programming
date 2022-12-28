package ui.admin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import logica.Accion;
import logica.Acompañante;
import logica.HistorialMedico;
import logica.Paciente;
import logica.empleados.Medico;
import logica.servicios.ParserBaseDeDatos;
import ui.AnadirAntecedentesHistorial;

import javax.swing.JCheckBox;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.JTextPane;


public class CrearPaciente extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JPanel panelPaciente;
	private JPanel panelAceptarLista;
	private JPanel panelAcompañante;
	private JLabel lbPNombre;
	private JTextField txtPNombre;
	private JLabel lblPApellidos;
	private JTextField txtPApellidos;
	private JLabel lbPEmail;
	private JTextField txtPEmail;
	private JTextField txtPnumero;
	private JLabel lblPTelfono;
	private JCheckBox chcbkAcompañante;
	private JLabel lbAnombre;
	private JTextField txtAnombre;
	private JLabel lblAApellidos;
	private JTextField txtAtelefono;
	private JLabel lblATelfono;
	private JTextField txtAApellido;
	private JButton btnAadirAcompaante;
	private JScrollPane scrollPane;
	private JPanel panelLista;
	private JPanel panelAceptar;
	private JList<Acompañante> list;
	private JButton btnCancelar;
	private JButton btnPaciente;
	private DefaultListModel<Acompañante> modeloA;
	private ParserBaseDeDatos pbd= new ParserBaseDeDatos();
	private JLabel lbAEmail;
	private JTextField txtAEmail;
	private String codAdmin;


	/**
	 * Create the dialog.
	 */
	public CrearPaciente(String codAdmin) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(AnadirAntecedentesHistorial.class.getResource("/img/logop.jpg")));
		this.codAdmin = codAdmin;
		setTitle("Administrador:Crear paciente");
		setBounds(100, 100, 736, 524);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(3, 1, 0, 0));
		contentPanel.add(getPanelPaciente());
		contentPanel.add(getPanelAcompañante());
		contentPanel.add(getPanelAceptarLista());
	}

	private JPanel getPanelPaciente() {
		if (panelPaciente == null) {
			panelPaciente = new JPanel();
			panelPaciente.setBorder(new TitledBorder(null, "Datos del paciente", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panelPaciente.setLayout(null);
			panelPaciente.add(getLbPNombre());
			panelPaciente.add(getTxtPNombre());
			panelPaciente.add(getLblPApellidos());
			panelPaciente.add(getTxtPApellidos());
			panelPaciente.add(getLbPEmail());
			panelPaciente.add(getTxtPEmail());
			panelPaciente.add(getTxtPnumero());
			panelPaciente.add(getLblPTelfono());
			panelPaciente.add(getChcbkAcompañante());
		}
		return panelPaciente;
	}
	private JPanel getPanelAceptarLista() {
		if (panelAceptarLista == null) {
			panelAceptarLista = new JPanel();
			panelAceptarLista.setLayout(new GridLayout(0, 2, 0, 0));
			panelAceptarLista.add(getPanel_1());
			panelAceptarLista.add(getPanel_1_1());
		}
		return panelAceptarLista;
	}
	private JPanel getPanelAcompañante() {
		if (panelAcompañante == null) {
			panelAcompañante = new JPanel();
			panelAcompañante.setEnabled(false);
			panelAcompañante.setBorder(new TitledBorder(null, "A\u00F1adir acompa\u00F1ante", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panelAcompañante.setLayout(null);
			panelAcompañante.add(getLbAnombre());
			panelAcompañante.add(getTxtAnombre());
			panelAcompañante.add(getLblAApellidos());
			panelAcompañante.add(getTxtAtelefono());
			panelAcompañante.add(getLblATelfono());
			panelAcompañante.add(getTxtAApellido());
			panelAcompañante.add(getLbAEmail());
			panelAcompañante.add(getTxtAEmail());
			panelAcompañante.add(getBtnAadirAcompaante());
		}
		return panelAcompañante;
	}
	private JLabel getLbPNombre() {
		if (lbPNombre == null) {
			lbPNombre = new JLabel("Nombre:");
			lbPNombre.setBounds(12, 30, 56, 16);
		}
		return lbPNombre;
	}
	private JTextField getTxtPNombre() {
		if (txtPNombre == null) {
			txtPNombre = new JTextField();
			txtPNombre.setBounds(69, 27, 160, 25);
			txtPNombre.setColumns(10);
		}
		return txtPNombre;
	}
	private JLabel getLblPApellidos() {
		if (lblPApellidos == null) {
			lblPApellidos = new JLabel("Apellidos:");
			lblPApellidos.setBounds(241, 30, 56, 16);
		}
		return lblPApellidos;
	}
	private JTextField getTxtPApellidos() {
		if (txtPApellidos == null) {
			txtPApellidos = new JTextField();
			txtPApellidos.setBounds(304, 27, 341, 25);
			txtPApellidos.setColumns(10);
		}
		return txtPApellidos;
	}
	private JLabel getLbPEmail() {
		if (lbPEmail == null) {
			lbPEmail = new JLabel("Email: ");
			lbPEmail.setBounds(241, 82, 56, 16);
		}
		return lbPEmail;
	}
	private JTextField getTxtPEmail() {
		if (txtPEmail == null) {
			txtPEmail = new JTextField();
			txtPEmail.setColumns(10);
			txtPEmail.setBounds(295, 79, 350, 25);
		}
		return txtPEmail;
	}
	private JTextField getTxtPnumero() {
		if (txtPnumero == null) {
			txtPnumero = new JTextField();
			txtPnumero.setColumns(10);
			txtPnumero.setBounds(69, 79, 160, 25);
		}
		return txtPnumero;
	}
	private JLabel getLblPTelfono() {
		if (lblPTelfono == null) {
			lblPTelfono = new JLabel("Tel\u00E9fono: ");
			lblPTelfono.setBounds(12, 82, 70, 16);
		}
		return lblPTelfono;
	}
	
	private JCheckBox getChcbkAcompañante() {
		if (chcbkAcompañante == null) {
			chcbkAcompañante = new JCheckBox("A\u00F1adir acompa\u00F1ante");
			chcbkAcompañante.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					boolean seleccion=chcbkAcompañante.isSelected();
					panelAcompañante.setEnabled(seleccion);
					for (Component component : panelAcompañante.getComponents()) {
							   component.setEnabled(seleccion); 
							}
					panelLista.setEnabled(seleccion);
					for (Component component : panelLista.getComponents()) {
						   component.setEnabled(seleccion); 
						}
					
				
				}
			});
			chcbkAcompañante.setBounds(20, 121, 176, 25);
		}
		return chcbkAcompañante;
	}
	private JLabel getLbAnombre() {
		if (lbAnombre == null) {
			lbAnombre = new JLabel("Nombre:");
			lbAnombre.setBounds(18, 36, 50, 16);
			lbAnombre.setEnabled(false);
		}
		return lbAnombre;
	}
	private JTextField getTxtAnombre() {
		if (txtAnombre == null) {
			txtAnombre = new JTextField();
			txtAnombre.setBounds(80, 33, 150, 22);
			txtAnombre.setEnabled(false);
			txtAnombre.setColumns(10);
		}
		return txtAnombre;
	}
	private JLabel getLblAApellidos() {
		if (lblAApellidos == null) {
			lblAApellidos = new JLabel("Apellidos:");
			lblAApellidos.setBounds(244, 36, 56, 16);
			lblAApellidos.setEnabled(false);
		}
		return lblAApellidos;
	}
	private JTextField getTxtAtelefono() {
		if (txtAtelefono == null) {
			txtAtelefono = new JTextField();
			txtAtelefono.setBounds(80, 89, 150, 22);
			txtAtelefono.setEnabled(false);
			txtAtelefono.setColumns(10);
		}
		return txtAtelefono;
	}
	private JLabel getLblATelfono() {
		if (lblATelfono == null) {
			lblATelfono = new JLabel("Tel\u00E9fono: ");
			lblATelfono.setBounds(18, 92, 59, 16);
			lblATelfono.setEnabled(false);
		}
		return lblATelfono;
	}
	private JTextField getTxtAApellido() {
		if (txtAApellido == null) {
			txtAApellido = new JTextField();
			txtAApellido.setBounds(319, 33, 329, 22);
			txtAApellido.setEnabled(false);
			txtAApellido.setColumns(10);
		}
		return txtAApellido;
	}
	private JButton getBtnAadirAcompaante() {
		if (btnAadirAcompaante == null) {
			btnAadirAcompaante = new JButton("A\u00F1adir acompa\u00F1ante");
			btnAadirAcompaante.setBounds(482, 124, 166, 25);
			btnAadirAcompaante.setEnabled(false);
			btnAadirAcompaante.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(checkCamposAcomapñante()) {
						if(checkSoloLetras(txtAnombre.getText())||checkSoloLetras(txtAApellido.getText())) {
									añadirAcompañante();
						lblAApellidos.setForeground(Color.BLACK);
						lbAEmail.setForeground(Color.BLACK);
						lbAnombre.setForeground(Color.BLACK);
						}
						else
							JOptionPane.showConfirmDialog(null, "El nombre o apellido no puede contener caracteres distintos de letras");
					}
					else {
						JOptionPane.showConfirmDialog(null, "Por favor,rellene todos los campos obligatorios");
						lblAApellidos.setForeground(Color.RED);
						lbAEmail.setForeground(Color.RED);
						lbAnombre.setForeground(Color.RED);
					}
				}
				
			});
			
			
		}
		return btnAadirAcompaante;
	}
	
	
	private void añadirAcompañante() {
		int movil=0;
	if(checkSoloNumeros(txtAtelefono.getText()))
		try {
		 movil=Integer.parseInt(txtAtelefono.getText());
		}catch (NumberFormatException e) {
			e.printStackTrace();
		}
		
		
		Acompañante a= new Acompañante(txtAnombre.getText(),txtAApellido.getText(),movil,txtAEmail.getText());
		modeloA.addElement(a);
		list.setModel(modeloA);
		
		vaciarCampos();
			
		

		
	}
	
	private void vaciarCampos() {
		txtAApellido.setText("");
		txtAEmail.setText("");
		txtAnombre.setText("");
		txtAtelefono.setText("");
		
	}

	private boolean checkCamposAcomapñante() {
		return !(getTxtAnombre().getText().isEmpty()||txtAApellido.getText().isEmpty()||txtAEmail.getText().isEmpty());
		
	}
	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setBounds(new Rectangle(12, 23, 304, 119));
			scrollPane.setEnabled(false);
			scrollPane.setViewportView(getList());
		}
		return scrollPane;
	}
	private JPanel getPanel_1() {
		if (panelLista == null) {
			panelLista = new JPanel();
			panelLista.setEnabled(false);
			panelLista.setBorder(new TitledBorder(null, "Lista de acompa\u00F1antes ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panelLista.setLayout(null);
			panelLista.add(getScrollPane());
			
		}
		return panelLista;
	}
	private JPanel getPanel_1_1() {
		if (panelAceptar == null) {
			panelAceptar = new JPanel();
			panelAceptar.setLayout(null);
			panelAceptar.add(getBtnCancelar());
			panelAceptar.add(getBtnPaciente());
		}
		return panelAceptar;
	}
	private JList<Acompañante> getList() {
		if (list == null) {
			list = new JList<Acompañante>();
			modeloA=new DefaultListModel<Acompañante>();
			list.setModel(modeloA);
			list.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if(e.getClickCount()==2) {
						
						Acompañante a=(Acompañante) list.getSelectedValue();
						int res=JOptionPane.showConfirmDialog(null, "¿Esta seguro de que desea borrar este médico?","Confirmación de eliminación", JOptionPane.YES_NO_OPTION);
						if(res==JOptionPane.YES_OPTION)	
							modeloA.removeElement(a);
			}
				}
			});
			
		}
		return list;
	}
	private JButton getBtnCancelar() {
		if (btnCancelar == null) {
			btnCancelar = new JButton("Cancelar");
			btnCancelar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					dispose();
				}
			});
			btnCancelar.setBounds(248, 122, 97, 25);
		}
		return btnCancelar;
	}
	private JButton getBtnPaciente() {
		if (btnPaciente == null) {
			btnPaciente = new JButton("A\u00F1adir paciente");
			btnPaciente.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					
					if(checkCamposPaciente()) {
						if(checkSoloLetras(txtPNombre.getText())||checkSoloLetras(txtPNombre.getText())) {
									crearPaciente();
									lbPNombre.setForeground(Color.BLACK);
									lbPEmail.setForeground(Color.BLACK);
									lblPApellidos.setForeground(Color.BLACK);
									try {
										guardarAccion();
									} catch (SQLException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
						}
						else
							JOptionPane.showMessageDialog(null, "El nombre o apellido no puede contener caracteres distintos de letras");
					}
					else {
						JOptionPane.showMessageDialog(null, "Por favor,rellene todos los campos obligatorios");
						lbPNombre.setForeground(Color.RED);
						lblPApellidos.setForeground(Color.RED);
						
						
					}
						
				}

				

				
			});
			btnPaciente.setBounds(101, 122, 134, 25);
		}
		return btnPaciente;
	}
	protected void guardarAccion() throws SQLException {
		List<Accion> devolverAccionesAdmin = pbd.devolverAccionesAdmin();
		int numeroAccion = 1;
		if(devolverAccionesAdmin.size()>0) {
			numeroAccion = devolverAccionesAdmin.size() + 1;
		}
		String naccion = "" +numeroAccion;
		
		String nombrePaciente=txtPNombre.getText();
		String apellidoPaciente=txtPApellidos.getText();
		
		Date fecha = new Date();	
		Time hora = new Time(new Date().getTime());	
		
		
		String mensajeAccion = "El aministrador " + codAdmin + " ha registrado al paciente " + nombrePaciente + " " + apellidoPaciente;
		
		Accion a = new Accion(naccion, codAdmin,  fecha, hora, mensajeAccion);
		
		pbd.guardarAccion(a);
		
	}

	/**
	 * Devulve true si los campos estan cubiertos
	 * @return
	 */
	private boolean checkCamposPaciente() {
		return !(txtPNombre.getText().isEmpty()||txtPApellidos.getText().isEmpty());
		
	}
	private boolean checkSoloLetras(String cadena) {
		
		return cadena.matches("[a-zA-ZñÑáéíóúÁÉÍÓÚ\\s]+");
	}
	
private boolean checkSoloNumeros(String cadena) {
		
		return cadena.matches("[0-9]+");
	}
	private void crearPaciente() {
		HistorialMedico h= new HistorialMedico();
		int movil=0;
	if(checkSoloNumeros(txtPnumero.getText()))
		try {
		 movil=Integer.parseInt(txtPnumero.getText());
		}catch (NumberFormatException e) {
		
		}
		
		Paciente p=new Paciente(txtPNombre.getText(),txtPApellidos.getText(),movil,getTxtPEmail().getText(),h.getHistorial());
		try {
			pbd.crearHistorial(h.getHistorial());
			pbd.crearPaciente(p);
		} catch (SQLException e) {
	
			e.printStackTrace();
		}
		crearAcompañante(p);
		
		JOptionPane.showMessageDialog(null, "Se ha añadido correctamente el paciente "+ p.getNombre()+" "+ p.getApellido()+
				" \ncon número de historial "+p.getHistorial() );
		dispose();
		
	}
	private void crearAcompañante(Paciente p) {
		Object[] obj= modeloA.toArray();
		
		List<Acompañante>ap=new ArrayList<Acompañante>();
		for (int i = 0; i < obj.length; i++) {
			ap.add((Acompañante)obj[i]);
			
			
		}
		
		for (int i = 0; i < ap.size(); i++) {
			ap.get(i).setCodPaciente(p.getCodePaciente());
			
			try {
				pbd.crearAcompañante(ap.get(i));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

	private JLabel getLbAEmail() {
		if (lbAEmail == null) {
			lbAEmail = new JLabel("Email:");
			lbAEmail.setBounds(250, 92, 70, 16);
			lbAEmail.setEnabled(false);
		}
		return lbAEmail;
	}
	private JTextField getTxtAEmail() {
		if (txtAEmail == null) {
			txtAEmail = new JTextField();
			txtAEmail.setBounds(319, 89, 329, 22);
			txtAEmail.setEnabled(false);
			txtAEmail.setColumns(10);
		}
		return txtAEmail;
	}
}
