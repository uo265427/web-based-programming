package ui.inicio;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ui.AnadirAntecedentesHistorial;
import ui.auditor.VerAccionesAdmin;
import ui.auditor.VerAccionesEmpleado;

import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JLabel;
import java.awt.Font;

public class EscogerOpcionAuditor extends JDialog {

	private JPanel contentPane;
	private JPanel panel_2;
	private JButton btnCerrarSesin;
	private JLabel lblNewLabel;
	private JLabel label;
	private JPanel panel;
	private JButton btnAccionesAdmin;
	private JButton btnVerAccionEmpleado;
	private JPanel panel_1;
	private JLabel lblNewLabel_1;


	/**
	 * Create the frame.
	 */
	public EscogerOpcionAuditor() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(AnadirAntecedentesHistorial.class.getResource("/img/logop.jpg")));
		setTitle("Opciones auditor.");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.add(getPanel_2(), BorderLayout.NORTH);
		contentPane.add(getPanel(), BorderLayout.SOUTH);
		contentPane.add(getPanel_1(), BorderLayout.CENTER);
	}
	private JPanel getPanel_2() {
		if (panel_2 == null) {
			panel_2 = new JPanel();
			panel_2.setLayout(new GridLayout(0, 3, 0, 0));
			panel_2.add(getLblNewLabel());
			panel_2.add(getLabel());
			panel_2.add(getBtnCerrarSesin());
		}
		return panel_2;
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
		}
		return btnCerrarSesin;
	}
	
	
	private void ventanaInicio() {
		VentanaInicio vmc =new VentanaInicio();
		vmc.setLocationRelativeTo(null);
		vmc.setVisible(true);
} 
	
	
	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("Bienvenido/a Auditor");
			lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		}
		return lblNewLabel;
	}
	private JLabel getLabel() {
		if (label == null) {
			label = new JLabel("");
		}
		return label;
	}
	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.add(getBtnAccionesAdmin_1());
			panel.add(getBtnVerAccionEmpleado_1());
		}
		return panel;
	}
	private JButton getBtnAccionesAdmin_1() {
		if (btnAccionesAdmin == null) {
			btnAccionesAdmin = new JButton("Ver acciones admin");
			btnAccionesAdmin.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					abrirAccionesAdmin();
				}
			});
		}
		return btnAccionesAdmin;
	}
	protected void abrirAccionesAdmin() {
		VerAccionesAdmin vad = new VerAccionesAdmin();
		vad.setLocationRelativeTo(null);
		vad.setResizable(true);
		vad.setModal(true); // hasta que no se cierre una ventana no se puede abrir otra
		vad.setVisible(true);
		
	}
	private JButton getBtnVerAccionEmpleado_1() {
		if (btnVerAccionEmpleado == null) {
			btnVerAccionEmpleado = new JButton("Ver acciones empleado");
			btnVerAccionEmpleado.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					abrirAccionesEmpleado();
				}
			});
		}
		return btnVerAccionEmpleado;
	}
	protected void abrirAccionesEmpleado() {
		VerAccionesEmpleado vae = new VerAccionesEmpleado();
		vae.setLocationRelativeTo(null);
		vae.setResizable(true);
		vae.setModal(true); // hasta que no se cierre una ventana no se puede abrir otra
		vae.setVisible(true);
		
	}
	private JPanel getPanel_1() {
		if (panel_1 == null) {
			panel_1 = new JPanel();
			panel_1.setLayout(null);
			panel_1.add(getLblNewLabel_1());
		}
		return panel_1;
	}
	private JLabel getLblNewLabel_1() {
		if (lblNewLabel_1 == null) {
			lblNewLabel_1 = new JLabel("Selecciona las acciones que quieras ver.");
			lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblNewLabel_1.setBounds(76, 39, 319, 74);
		}
		return lblNewLabel_1;
	}
}
