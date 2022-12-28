/**
 * 
 */
package ui.medico;

import javax.swing.JPanel;

import logica.Correo;
import logica.servicios.ParserBaseDeDatos;
import ui.AnadirAntecedentesHistorial;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.FlowLayout;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Date;
import java.util.Random;

import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * @author María
 *
 */
public class PanelResponderCorreo extends JPanel{

	private ParserBaseDeDatos pbd=new ParserBaseDeDatos();
	private String codMedicoDestino;
	private String codMedicoOrigen;
	private VentanaCorreo ventanaCorreo;
	
	private JPanel pnDestinatario;
	private JPanel pnEnviar;
	private JPanel pnCentro;
	private JLabel lblDestinatario;
	private JTextField txtFDestinatario;
	private JPanel pnAsunto;
	private JLabel lblAsunto;
	private JTextField txtFAsunto;
	private JPanel pnMensaje;
	private JScrollPane scrollPane;
	private JTextArea textArea;
	private JButton btnEnviar;
	private JButton btnCancelar;
	
	
	
	
	/**
	 * Constructor para la clase PanelResponderCorreo
	 * @param codMedicoOrigen
	 * @param codMedico
	 * @param ventanaCorreo
	 */
	public PanelResponderCorreo(String codMedicoDestino, String codMedicoOrigen, VentanaCorreo ventanaCorreo) {
		this.codMedicoDestino = codMedicoDestino;
		this.codMedicoOrigen = codMedicoOrigen;
		this.ventanaCorreo = ventanaCorreo;
		
		setLayout(new BorderLayout(0, 0));
		add(getPnDestinatario(), BorderLayout.NORTH);
		add(getPnEnviar(), BorderLayout.SOUTH);
		add(getPnCentro(), BorderLayout.CENTER);
	}

	private JPanel getPnDestinatario() {
		if (pnDestinatario == null) {
			pnDestinatario = new JPanel();
			FlowLayout flowLayout = (FlowLayout) pnDestinatario.getLayout();
			flowLayout.setAlignment(FlowLayout.LEFT);
			pnDestinatario.add(getLblDestinatario());
			pnDestinatario.add(getTxtFDestinatario());
		}
		return pnDestinatario;
	}
	private JPanel getPnEnviar() {
		if (pnEnviar == null) {
			pnEnviar = new JPanel();
			pnEnviar.add(getBtnEnviar());
			pnEnviar.add(getBtnCancelar());
		}
		return pnEnviar;
	}
	private JPanel getPnCentro() {
		if (pnCentro == null) {
			pnCentro = new JPanel();
			pnCentro.setLayout(new BorderLayout(0, 0));
			pnCentro.add(getPnAsunto(), BorderLayout.NORTH);
			pnCentro.add(getPnMensaje(), BorderLayout.CENTER);
		}
		return pnCentro;
	}
	private JLabel getLblDestinatario() {
		if (lblDestinatario == null) {
			lblDestinatario = new JLabel("Destinatario:  ");
		}
		return lblDestinatario;
	}
	private JTextField getTxtFDestinatario() {
		if (txtFDestinatario == null) {
			txtFDestinatario = new JTextField();
			txtFDestinatario.setEditable(false);
			txtFDestinatario.setColumns(10);
			
			
			try {
				txtFDestinatario.setText(pbd.buscarEmpleadoPorCodigo(codMedicoDestino));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return txtFDestinatario;
	}
	private JPanel getPnAsunto() {
		if (pnAsunto == null) {
			pnAsunto = new JPanel();
			FlowLayout flowLayout = (FlowLayout) pnAsunto.getLayout();
			flowLayout.setAlignment(FlowLayout.LEFT);
			pnAsunto.add(getLblAsunto());
			pnAsunto.add(getTxtFAsunto());
		}
		return pnAsunto;
	}
	private JLabel getLblAsunto() {
		if (lblAsunto == null) {
			lblAsunto = new JLabel("Asunto:           ");
		}
		return lblAsunto;
	}
	private JTextField getTxtFAsunto() {
		if (txtFAsunto == null) {
			txtFAsunto = new JTextField();
			txtFAsunto.setColumns(10);
		}
		return txtFAsunto;
	}
	private JPanel getPnMensaje() {
		if (pnMensaje == null) {
			pnMensaje = new JPanel();
			pnMensaje.setLayout(new GridLayout(0, 1, 0, 0));
			pnMensaje.add(getScrollPane());
			//pnMensaje.add(getTextArea());
		}
		return pnMensaje;
	}
	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			
			scrollPane.setViewportView(getTextArea());
		}
		return scrollPane;
	}
	private JTextArea getTextArea() {
		if (textArea == null) {
			textArea = new JTextArea();
		}
		return textArea;
	}
	private JButton getBtnEnviar() {
		if (btnEnviar == null) {
			btnEnviar = new JButton("Enviar");
			btnEnviar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						enviarRespuesta();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			});
		}
		return btnEnviar;
	}


	private JButton getBtnCancelar() {
		if (btnCancelar == null) {
			btnCancelar = new JButton("Cancelar");
			btnCancelar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					cancelarRespuesta();
				}
			});
		}
		return btnCancelar;
	}

	/**
	 * Método para cancelar la respuesta. Nos pinta la ventana de los correos sin nada en el centro
	 */
	protected void cancelarRespuesta() {
		ventanaCorreo.limpiarPanelCentro();		
	}
	
	
	/**
	 * Método para confirmar el envío de una respuesta
	 * @throws SQLException 
	 */
	protected void enviarRespuesta() throws SQLException {
		if (comprobarCampos()) { // Si ha rellenado todos los campos correctamente
			
		
				// Saco los datos que ha introducido
				String nombreDestinatario = pbd.buscarEmpleadoPorCodigo(codMedicoDestino);
				String asunto = txtFAsunto.getText();
				String mensaje = textArea.getText();
			
				Random r = new Random();
				String codCorreo = "" + (int)r.nextInt(999);
			
				Date date = new Date();	
				Time time = new Time(new Date().getTime());
			
				Correo nuevoCorreo = new Correo(codCorreo, codMedicoDestino, codMedicoOrigen, asunto, mensaje, date, time);
			
				pbd.crearCorreo(nuevoCorreo);
				
				JOptionPane.showMessageDialog(null, "Su mensaje a " + nombreDestinatario + " ha sido enviado con éxito.");
				
				limpiar();
			
		}
		
	}
	
	


	/**
	 * Método para comprobar si el usario ha rellenado todos los campos correctamente
	 * @return
	 */
	private boolean comprobarCampos() {
		// Si ha dejado algún campo vacío
		if ((txtFAsunto.getText().isEmpty()) || (textArea.getText().isEmpty())) {
			JOptionPane.showMessageDialog(null, "Compruebe que ha rellenado todos los campos."); // Le avisa si ha dejado algo sin rellenar
			return false;
		}
		return true;
	}
	
	
	/**
	 * Método para 
	 */
	private void limpiar() {
		ventanaCorreo.limpiarPanelCentro();	
	}
}
