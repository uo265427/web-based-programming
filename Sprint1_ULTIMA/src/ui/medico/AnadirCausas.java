package ui.medico;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import logica.HistorialMedico;
import logica.Preinscripcion;
import logica.servicios.ParserBaseDeDatos;
import ui.AnadirAntecedentesHistorial;

import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Date;
import java.util.Random;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.DropMode;
import javax.swing.JScrollPane;

public class AnadirCausas extends JDialog {

	private JPanel contentPane;
	private JPanel pnSur;
	private JPanel pnCentral;
	private JButton btnCancelar;
	private JButton btnGuardar;
	private JPanel pnIzq;
	private JPanel pnDcha;
	private JLabel lblPreinscripcion;
	private JTextField txtPreinscripcion;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	
	private ModificarMedicosNuevoCard mm;
	
	private ParserBaseDeDatos pbd=new ParserBaseDeDatos();



	/**
	 * Create the frame.
	 * @param modificarCitaMedico 
	 */
	public AnadirCausas(ModificarMedicosNuevoCard mm) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(AnadirAntecedentesHistorial.class.getResource("/img/logop.jpg")));
		this.mm = mm; // La ventana anterior
		
		setTitle("A\u00F1adir causa");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 394, 218);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.add(getPnSur(), BorderLayout.SOUTH);
		contentPane.add(getPnCentral(), BorderLayout.CENTER);
	}

	private JPanel getPnSur() {
		if (pnSur == null) {
			pnSur = new JPanel();
			pnSur.setBackground(Color.WHITE);
			FlowLayout flowLayout = (FlowLayout) pnSur.getLayout();
			flowLayout.setAlignment(FlowLayout.RIGHT);
			pnSur.add(getBtnGuardar());
			pnSur.add(getBtnCancelar());
		}
		return pnSur;
	}
	private JPanel getPnCentral() {
		if (pnCentral == null) {
			pnCentral = new JPanel();
			pnCentral.setBackground(Color.WHITE);
			pnCentral.setLayout(new GridLayout(0, 2, 0, 0));
			pnCentral.add(getPnIzq());
			pnCentral.add(getPnDcha());
		}
		return pnCentral;
	}
	private JButton getBtnCancelar() {
		if (btnCancelar == null) {
			btnCancelar = new JButton("Cancelar");
			btnCancelar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					dispose(); // Cerramos la ventana
				}
			});
		}
		return btnCancelar;
	}
	private JButton getBtnGuardar() {
		if (btnGuardar == null) {
			btnGuardar = new JButton("Guardar");
			btnGuardar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					try {
						guardar();
						mm.vaciarCBCausas();
						mm.rellenarCBCausas();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
			});
		}
		return btnGuardar;
	}


	private JPanel getPnIzq() {
		if (pnIzq == null) {
			pnIzq = new JPanel();
			pnIzq.setBackground(Color.WHITE);
			pnIzq.setLayout(new GridLayout(0, 1, 0, 0));
			pnIzq.add(getLblPreinscripcion());
		}
		return pnIzq;
	}
	private JPanel getPnDcha() {
		if (pnDcha == null) {
			pnDcha = new JPanel();
			pnDcha.setBackground(Color.WHITE);
			pnDcha.setLayout(new GridLayout(0, 1, 0, 0));
			pnDcha.add(getTxtPreinscripcion());
		}
		return pnDcha;
	}
	private JLabel getLblPreinscripcion() {
		if (lblPreinscripcion == null) {
			lblPreinscripcion = new JLabel("Motivo");
			lblPreinscripcion.setFont(new Font("Tahoma", Font.PLAIN, 13));
		}
		return lblPreinscripcion;
	}
	private JTextField getTxtPreinscripcion() {
		if (txtPreinscripcion == null) {
			txtPreinscripcion = new JTextField();
			txtPreinscripcion.setColumns(10);
		}
		return txtPreinscripcion;
	}
	
	
	
	/**
	 * Método para guardar la preinscripcion
	 * @throws SQLException 
	 */
	protected void guardar() throws SQLException {
		
		if (comprobarCampos()) { // Comprueba que al menos ha puesto el nombre y seleccionado el tipo de preinscripcion
			modificarAsignaCausas();
			nuevaCausa();
			dispose();
		}
		
		
		else {  // Le avisa de que hay algo que no ha puesto
			JOptionPane.showMessageDialog(null, "Por favor compruebe que ha rellenado el nombre y seleccionado el tipo de preinscripción.");
		}
		
		
	}

	
	private void nuevaCausa() throws SQLException {
		mm.ponerCausas();
		
	}

	protected void modificarAsignaCausas() throws SQLException {
		String causas = txtPreinscripcion.getText();		

		if(!causas.equals("")) {
			pbd.actualizarCausa(causas);
		}
		dispose();
	}
	
	
	/**
	 * Método para comprobar que ha rellenado los campos obligatorios
	 */
	private boolean comprobarCampos() {
		if (txtPreinscripcion.getText().equals("")) { // Si ha dejado el nombre en blanco
			return false;
		}

		else { // Si estaba todo bien
			return true;
		}
	}
}
