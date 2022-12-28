/**
 * 
 */
package ui.medico;

import javax.swing.JPanel;

import logica.Correo;
import logica.servicios.ParserBaseDeDatos;
import ui.AnadirAntecedentesHistorial;

import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

/**
 * @author María
 *
 */
public class PanelElementoListaCorreo extends JPanel{

	
	private ParserBaseDeDatos pbd=new ParserBaseDeDatos();

	
	private Correo correo;
	private VentanaCorreo ventanaCorreo; // La ventana anterior
	
	private JPanel pnInformacion;
	private JLabel lblAsunto;
	private JLabel lblMedicoOrigen;
	private JPanel pnVerMensaje;
	private JButton btnNewButton;
	private Panel pnVacio;
	
	/**
	 * Constructor para el panel de cada elemento correo que tengo en la lista de recibidos
	 * @param correo
	 * @param ventanaCorreo 
	 */
	public PanelElementoListaCorreo(Correo correo, VentanaCorreo ventanaCorreo) {
		this.ventanaCorreo = ventanaCorreo;
		this.correo = correo;
		setLayout(new GridLayout(0, 2, 0, 0));
		add(getPnInformacion());
		add(getPnVerMensaje());
	}

	
	
	
	
	
	private JPanel getPnInformacion() {
		if (pnInformacion == null) {
			pnInformacion = new JPanel();
			pnInformacion.setLayout(new GridLayout(3, 0, 0, 0));
			pnInformacion.add(getLblMedicoOrigen());
			pnInformacion.add(getLblAsunto());
			pnInformacion.add(getPnVacio());
		}
		return pnInformacion;
	}
	private JLabel getLblAsunto() {
		if (lblAsunto == null) {
			lblAsunto = new JLabel("");
			
			lblAsunto.setText(correo.getAsunto());
		}
		return lblAsunto;
	}
	private JLabel getLblMedicoOrigen() {
		if (lblMedicoOrigen == null) {
			lblMedicoOrigen = new JLabel("");
			
			try {
				lblMedicoOrigen.setText(nombreMedico());
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return lblMedicoOrigen;
	}




	private JPanel getPnVerMensaje() {
		if (pnVerMensaje == null) {
			pnVerMensaje = new JPanel();
			pnVerMensaje.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
			pnVerMensaje.add(getBtnNewButton());
		}
		return pnVerMensaje;
	}
	private JButton getBtnNewButton() {
		if (btnNewButton == null) {
			btnNewButton = new JButton("Ver");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					verCorreo();
				}
			});
		}
		return btnNewButton;
	}



	private Panel getPnVacio() {
		if (pnVacio == null) {
			pnVacio = new Panel();
		}
		return pnVacio;
	}
	
	
	
	/**
	 * Método para que se abra el panel que me muestra el contenido del mensaje que he seleccionado
	 */
	protected void verCorreo() {
		ventanaCorreo.verCorreo(correo); // Le paso el correo que seleccionó para que se visualice
	}
	
	
	/**
	 * Método para poner el nombre del médico
	 * @return
	 * @throws SQLException 
	 */
	private String nombreMedico() throws SQLException {
		String nombreMedico = pbd.buscarNombreMedico(correo.getCodMedicoOrigen());
		
		return nombreMedico;				
	}
}
