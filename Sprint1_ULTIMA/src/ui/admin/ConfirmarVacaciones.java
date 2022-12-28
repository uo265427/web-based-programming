package ui.admin;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ui.AnadirAntecedentesHistorial;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Toolkit;

public class ConfirmarVacaciones extends JFrame {

	private JPanel contentPane;
	private JButton btnConfirmar;
	private JButton btnCancelar;

	private AsignarVacaciones av;
	private JLabel lblMensaje;
	private JLabel lblNewLabel;

	/**
	 * Create the frame.
	 */
	public ConfirmarVacaciones(AsignarVacaciones av) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(AnadirAntecedentesHistorial.class.getResource("/img/logop.jpg")));
		setTitle("Confirmar Vacaciones");
		this.av = av;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getBtnConfirmar());
		contentPane.add(getBtnCancelar());
		contentPane.add(getLblMensaje());
		contentPane.add(getLblNewLabel());
	}

	private JButton getBtnConfirmar() {
		if (btnConfirmar == null) {
			btnConfirmar = new JButton("Confirmar");
			btnConfirmar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					av.borrarCitas();
				}
			});
			btnConfirmar.setBounds(200, 227, 105, 23);
		}
		return btnConfirmar;
	}
	private JButton getBtnCancelar() {
		if (btnCancelar == null) {
			btnCancelar = new JButton("Cancelar");
			btnCancelar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			btnCancelar.setBounds(322, 227, 89, 23);
		}
		return btnCancelar;
	}
	private JLabel getLblMensaje() {
		if (lblMensaje == null) {
			lblMensaje = new JLabel("El m\u00E9dico tiene una cita en el periodo indicado.  A\u00F1ada un ");
			lblMensaje.setFont(new Font("Tahoma", Font.BOLD, 13));
			lblMensaje.setBounds(10, 11, 414, 64);
		}
		return lblMensaje;
	}
	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("m\u00E9dico nuevo a la cita.");
			lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
			lblNewLabel.setBounds(10, 48, 414, 45);
		}
		return lblNewLabel;
	}
}
