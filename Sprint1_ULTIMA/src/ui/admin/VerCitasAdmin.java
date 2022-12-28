package ui.admin;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import logica.Cita;
import logica.Paciente;
import logica.servicios.ParserBaseDeDatos;
import ui.AnadirAntecedentesHistorial;

import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridLayout;
import java.awt.JobAttributes;
import java.awt.Toolkit;

import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.awt.Component;
import javax.swing.Box;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;

public class VerCitasAdmin extends JDialog {
	private JTextField txtNombre;
	private JTextField txtApellido;

	private ParserBaseDeDatos pbd=new ParserBaseDeDatos();
	private JTextField txtCodPaciente;
	private JComboBox comboBox;
	
	/**
	 * Create the dialog.
	 */
	public VerCitasAdmin() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(AnadirAntecedentesHistorial.class.getResource("/img/logop.jpg")));
		setTitle("Ver citas");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Ver cita");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if (comprobarDatos()) {
								try {
									abrirCita();
								} catch (SQLException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
						} else {
							JOptionPane.showMessageDialog(null, "Rellene los campos");
						}

					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Volver");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		{
			JPanel panel = new JPanel();
			getContentPane().add(panel, BorderLayout.CENTER);
			panel.setLayout(new BorderLayout(0, 0));
			{
				JLabel lblDatosPaciente = new JLabel("Buscar paciente");
				panel.add(lblDatosPaciente, BorderLayout.NORTH);
			}
			{
				JPanel panel_1 = new JPanel();
				panel.add(panel_1);
				panel_1.setLayout(new GridLayout(0, 2, 0, 0));
				{
					Box horizontalBox = Box.createHorizontalBox();
					panel_1.add(horizontalBox);
				}
				{
					Component horizontalStrut = Box.createHorizontalStrut(20);
					panel_1.add(horizontalStrut);
				}
				{
					JLabel lblNombre = new JLabel("Nombre");
					panel_1.add(lblNombre);
				}
				{
					txtNombre = new JTextField();
					panel_1.add(txtNombre);
					txtNombre.setColumns(10);
				}
				{
					JLabel lblApellido = new JLabel("Apellido");
					panel_1.add(lblApellido);
				}
				{
					txtApellido = new JTextField();
					panel_1.add(txtApellido);
					txtApellido.setColumns(10);
				}
				{
					JLabel lblCodpaciente = new JLabel("CodPaciente");
					panel_1.add(lblCodpaciente);
				}
				{
					txtCodPaciente = new JTextField();
					panel_1.add(txtCodPaciente);
					txtCodPaciente.setColumns(10);
				}
				{
					comboBox = new JComboBox();
					panel_1.add(comboBox);
				}
				{
					JButton button = new JButton("Ok");
					button.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							if(comprobarDatos()) {
							List<Cita> citas=new ArrayList<Cita>();
							try {
								citas = devolverCitas();
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
							
							for (int i = 0; i < citas.size(); i++) {
								comboBox.insertItemAt(citas.get(i), i);
							}
						} else {
							JOptionPane.showMessageDialog(null, "Rellene los campos");
							
						
						}
						
						}});
					panel_1.add(button);
				}
				{
					Component horizontalStrut = Box.createHorizontalStrut(20);
					panel_1.add(horizontalStrut);
				}
			}
		}
	}

	protected List<Cita> devolverCitas() throws SQLException {
		String nombre = txtNombre.getText();
		String apellido = txtApellido.getText();
		String codPaciente = txtCodPaciente.getText();
		List<Cita> citasPaciente=new ArrayList<Cita>();
		
		Paciente paciente = pbd.devolverPaciente(nombre, apellido, codPaciente);
		if(paciente instanceof Paciente) {
		 citasPaciente = pbd.devolvercitas(paciente.getCodePaciente());
		}
		else
			JOptionPane.showMessageDialog(null, "El paciente no ha sido encontrado.Por favor vuelva a intentarlo");
		
		return citasPaciente;
	}

	protected void abrirCita() throws SQLException {
		dispose();
		String nombre = txtNombre.getText();
		String apellido = txtApellido.getText();
		String codpaciente = txtCodPaciente.getText();
		
		Paciente paciente = pbd.devolverPaciente(nombre, apellido, codpaciente);
		Cita cita = (Cita) comboBox.getSelectedItem();
		
//		InfoCitaAdmin ica = new InfoCitaAdmin(paciente, cita);
//		ica.setLocationRelativeTo(null);
//		ica.setResizable(true);
//		ica.setModal(true); // hasta que no se cierre una ventana no se puede abrir otra
//		ica.setVisible(true);
	}
	
	


	protected boolean comprobarDatos() {
		if (txtNombre.getText().isEmpty() || txtApellido.getText().isEmpty()) {
			return false;
		} else
			return true;
	}

}
