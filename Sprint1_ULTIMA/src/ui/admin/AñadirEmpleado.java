package ui.admin;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;
import ui.AnadirAntecedentesHistorial;
import ui.gerente.DatosGerente;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.TitledBorder;

import logica.empleados.Empleado;
import logica.empleados.Enfermero;
import logica.empleados.Medico;
import logica.servicios.ParserBaseDeDatos;

import javax.swing.border.EtchedBorder;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Random;
import java.awt.event.ActionEvent;

public class AñadirEmpleado extends JDialog {

	private JPanel contentPane;
	private JPanel panelDatos;
	private JLabel lblNombre;
	private JTextField txtFieldNombre;
	private JLabel lblApellidos;
	private JTextField txtFieldApellidos;
	private JLabel lblDni;
	private JTextField txtFieldDni;
	private JLabel lblCorreo;
	private JTextField txtFieldCorreo;
	private JPanel panelProfesion;
	private JRadioButton rdbtnMedico;
	private JRadioButton rdbtnEnfermero;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JButton btnContinuar;
	private JButton btnCancelar;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JPasswordField psswdField;
	private JPasswordField psswdFieldRepetir;
	private ParserBaseDeDatos pbd = new ParserBaseDeDatos();
	private Empleado em;
	private String codAdmin;

	/**
	 * Launch the application.
	 */
	/*
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AñadirEmpleado frame = new AñadirEmpleado();
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
	public AñadirEmpleado(String codAdmin) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(AnadirAntecedentesHistorial.class.getResource("/img/logop.jpg")));
		setResizable(false);
		this.codAdmin=codAdmin;
		setTitle("A\u00F1adir empleado");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 730, 460);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getPanelDatos());
		contentPane.add(getPanelProfesion());
		contentPane.add(getBtnContinuar());
		contentPane.add(getBtnCancelar());
	}
	private JPanel getPanelDatos() {
		if (panelDatos == null) {
			panelDatos = new JPanel();
			panelDatos.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Datos personales", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panelDatos.setBounds(10, 11, 694, 250);
			panelDatos.setLayout(null);
			panelDatos.add(getLblNombre());
			panelDatos.add(getTxtFieldNombre());
			panelDatos.add(getLblApellidos());
			panelDatos.add(getTxtFieldApellidos());
			panelDatos.add(getLblDni());
			panelDatos.add(getTxtFieldDni());
			panelDatos.add(getLblCorreo());
			panelDatos.add(getTxtFieldCorreo());
			panelDatos.add(getLblNewLabel());
			panelDatos.add(getPsswdField());
			panelDatos.add(getLblNewLabel_1());
			panelDatos.add(getPsswdFieldRepetir());
		}
		return panelDatos;
	}
	private JLabel getLblNombre() {
		if (lblNombre == null) {
			lblNombre = new JLabel("Nombre:");
			lblNombre.setBounds(21, 39, 96, 14);
		}
		return lblNombre;
	}
	private JTextField getTxtFieldNombre() {
		if (txtFieldNombre == null) {
			txtFieldNombre = new JTextField();
			txtFieldNombre.setBounds(127, 33, 178, 27);
			txtFieldNombre.setColumns(10);
		}
		return txtFieldNombre;
	}
	private JLabel getLblApellidos() {
		if (lblApellidos == null) {
			lblApellidos = new JLabel("Apellidos:");
			lblApellidos.setBounds(351, 36, 58, 14);
		}
		return lblApellidos;
	}
	private JTextField getTxtFieldApellidos() {
		if (txtFieldApellidos == null) {
			txtFieldApellidos = new JTextField();
			txtFieldApellidos.setBounds(419, 33, 240, 27);
			txtFieldApellidos.setColumns(10);
		}
		return txtFieldApellidos;
	}
	private JLabel getLblDni() {
		if (lblDni == null) {
			lblDni = new JLabel("DNI:");
			lblDni.setBounds(21, 77, 96, 14);
		}
		return lblDni;
	}
	private JTextField getTxtFieldDni() {
		if (txtFieldDni == null) {
			txtFieldDni = new JTextField();
			txtFieldDni.setBounds(127, 71, 178, 27);
			txtFieldDni.setColumns(10);
		}
		return txtFieldDni;
	}
	private JLabel getLblCorreo() {
		if (lblCorreo == null) {
			lblCorreo = new JLabel("Correo elect\u00F3nico:");
			lblCorreo.setBounds(21, 135, 96, 14);
		}
		return lblCorreo;
	}
	private JTextField getTxtFieldCorreo() {
		if (txtFieldCorreo == null) {
			txtFieldCorreo = new JTextField();
			txtFieldCorreo.setBounds(169, 129, 240, 27);
			txtFieldCorreo.setColumns(10);
		}
		return txtFieldCorreo;
	}
	private JPanel getPanelProfesion() {
		if (panelProfesion == null) {
			panelProfesion = new JPanel();
			panelProfesion.setBorder(new TitledBorder(null, "Profesi\u00F3n", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panelProfesion.setBounds(10, 272, 694, 104);
			panelProfesion.setLayout(null);
			panelProfesion.add(getRdbtnMedico());
			panelProfesion.add(getRdbtnEnfermero());
		}
		return panelProfesion;
	}
	private JRadioButton getRdbtnMedico() {
		if (rdbtnMedico == null) {
			rdbtnMedico = new JRadioButton("M\u00E9dico");
			buttonGroup.add(rdbtnMedico);
			rdbtnMedico.setBounds(35, 44, 109, 23);
			rdbtnMedico.setSelected(true);
		}
		return rdbtnMedico;
	}
	private JRadioButton getRdbtnEnfermero() {
		if (rdbtnEnfermero == null) {
			rdbtnEnfermero = new JRadioButton("Enfermero");
			buttonGroup.add(rdbtnEnfermero);
			rdbtnEnfermero.setBounds(146, 44, 109, 23);
		}
		return rdbtnEnfermero;
	}
	private JButton getBtnContinuar() {
		if (btnContinuar == null) {
			btnContinuar = new JButton("Continuar");
			btnContinuar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					guardarEmpleado();
					
				}
			});
			btnContinuar.setBounds(486, 387, 89, 23);
		}
		return btnContinuar;
	}
	private JButton getBtnCancelar() {
		if (btnCancelar == null) {
			btnCancelar = new JButton("Cancelar");
			btnCancelar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			btnCancelar.setBounds(596, 387, 89, 23);
		}
		return btnCancelar;
	}
	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("Contrase\u00F1a:");
			lblNewLabel.setBounds(21, 173, 96, 14);
		}
		return lblNewLabel;
	}
	private JLabel getLblNewLabel_1() {
		if (lblNewLabel_1 == null) {
			lblNewLabel_1 = new JLabel("Repetir contrase\u00F1a:");
			lblNewLabel_1.setBounds(21, 209, 120, 14);
		}
		return lblNewLabel_1;
	}
	private JPasswordField getPsswdField() {
		if (psswdField == null) {
			psswdField = new JPasswordField();
			psswdField.setBounds(169, 167, 178, 27);
		}
		return psswdField;
	}
	private JPasswordField getPsswdFieldRepetir() {
		if (psswdFieldRepetir == null) {
			psswdFieldRepetir = new JPasswordField();
			psswdFieldRepetir.setBounds(169, 203, 178, 27);
		}
		return psswdFieldRepetir;
	}
	
	private void guardarEmpleado() {
		if(comprobarCamposLlenos()){
			if(getTipoEmpleado().equals("Médico")) {
				em = new Medico(generateCode(), getTxtFieldNombre().getText(), getTxtFieldApellidos().getText(), getSha256(new String(getPsswdField().getPassword())),
						null, null, null, null, "");
				
			}
			else if(getTipoEmpleado().equals("Enfermero")) {
				em = new Enfermero(generateCode(), getTxtFieldNombre().getText(), getTxtFieldApellidos().getText(), getSha256(new String(getPsswdField().getPassword())),
						null, null, null, null, "");
			}
			try {
				pbd.addEmpleado(em, getTipoEmpleado());
				int seleccionJornada = JOptionPane.showConfirmDialog(null, "¿Desea añdir una jornada laboral?", "Añadir jornada", JOptionPane.YES_NO_OPTION);
				if(seleccionJornada == JOptionPane.YES_OPTION) {
					VentanaJornada vj= new VentanaJornada(codAdmin, em);
					vj.setModal(true);
					vj.setVisible(true);
					vj.setLocationRelativeTo(null);
					vj.setResizable(true);
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			dispose();
		}
	}
	
	private boolean comprobarCamposLlenos() {
		boolean flag = false;
		if(getTxtFieldNombre().getText().equals("")) {
			JOptionPane.showMessageDialog(null, "Por favor escriba su nombre", "Error: introduza nombre", JOptionPane.ERROR_MESSAGE);
			flag = false;
			
		}
		else if(getTxtFieldApellidos().getText().equals("")) {
			JOptionPane.showMessageDialog(null, "Por favor escriba sus apellidos", "Error: introduza apellidos", JOptionPane.ERROR_MESSAGE);
			flag = false;
			
		}
		else if(getTxtFieldDni().getText().equals("")) {
			JOptionPane.showMessageDialog(null, "Por favor escriba su DNI", "Error: introduza DNI", JOptionPane.ERROR_MESSAGE);
			flag = false;
			
		}
		else if(getTxtFieldCorreo().getText().equals("")) {
			JOptionPane.showMessageDialog(null, "Por favor escriba su correo", "Error: introduza correo", JOptionPane.ERROR_MESSAGE);
			flag = false;
			
		}
		else if(getPsswdField().getPassword() == null) {
			JOptionPane.showMessageDialog(null, "Rellene las contraseñas", "Error: introduza contraseña", JOptionPane.ERROR_MESSAGE);
			flag = false;
			
		}
		else if(!(new String(getPsswdField().getPassword()).equals(new String(getPsswdFieldRepetir().getPassword())))) {
			JOptionPane.showMessageDialog(null, "Las contraseñas deben coincidir", "Error: introduza contraseña", JOptionPane.ERROR_MESSAGE);
			flag = false;
			
		}
		else if(getTipoEmpleado().equals("")) {
			JOptionPane.showMessageDialog(null, "Seleccione un tipo de empleado", "Error: introduza tipo de empleado", JOptionPane.ERROR_MESSAGE);
			flag = false;
			
		}
		else {
			flag = true;
			
		}
		return flag;
	}
	
	private String getTipoEmpleado() {
		 for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
	            AbstractButton button = buttons.nextElement();

	            if (button.isSelected()) {
	                return button.getText();
	            }
	        }
		 return "";
	}
	
	private String generateCode() {
		Random r = new Random();
		int code = r.nextInt(1000000);
		return Integer.toString(code);
	}
	
	public static String getSha256(String value) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(value.getBytes());
			return bytesToHex(md.digest());
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	private static String bytesToHex(byte[] bytes) {
		StringBuffer result = new StringBuffer();
		for (byte b : bytes)
			result.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
		return result.toString();
	}

	public String get_SHA_512_SecurePassword(String passwordToHash, String salt){
		String generatedPassword = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			md.update(salt.getBytes(StandardCharsets.UTF_8));
			byte[] bytes = md.digest(passwordToHash.getBytes(StandardCharsets.UTF_8));
			StringBuilder sb = new StringBuilder();
			for(int i=0; i< bytes.length ;i++){
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			generatedPassword = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return generatedPassword;
	}
}
