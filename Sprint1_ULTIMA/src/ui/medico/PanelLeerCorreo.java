package ui.medico;

import javax.swing.JPanel;

import logica.Correo;
import logica.servicios.ParserBaseDeDatos;
import ui.AnadirAntecedentesHistorial;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PanelLeerCorreo extends JPanel {
	
	private ParserBaseDeDatos pbd=new ParserBaseDeDatos();
	private VentanaCorreo ventanaCorreo;
	private Correo correo;
	
	
	private JPanel pnNorte;
	private JPanel pnSur;
	private JPanel pnCentro;
	private JButton btnResponder;
	private JButton btnEliminar;
	private JButton btnAtras;
	private JPanel pnRemitente;
	private JPanel pnContenidoCorreo;
	private JPanel pnAsunto;
	private JPanel pnMensaje;
	private JLabel lblAsunto;
	private JLabel lblElAsunto;
	private JLabel lblRemitente;
	private JScrollPane scrollPane;
	private JTextArea textArea;
	private JPanel pnNombreRemitente;
	private JPanel pnFechaYHora;
	private JLabel lblFecha;
	private JLabel lblHora;
	private JPanel panel;


	/**
	 * Construcctor para la clase PanelLeerCorreo
	 * 
	 * @param correo
	 * @param ventanaCorreo 
	 */
	public PanelLeerCorreo(Correo correo, VentanaCorreo ventanaCorreo) {
		this.ventanaCorreo = ventanaCorreo;
		this.correo = correo;
		
		setLayout(new BorderLayout(0, 0));
		add(getPnNorte(), BorderLayout.NORTH);
		add(getPnSur(), BorderLayout.SOUTH);
		add(getPnCentro(), BorderLayout.CENTER);
		// TODO Auto-generated constructor stub
	}

	private JPanel getPnNorte() {
		if (pnNorte == null) {
			pnNorte = new JPanel();
			FlowLayout flowLayout = (FlowLayout) pnNorte.getLayout();
			flowLayout.setAlignment(FlowLayout.RIGHT);
			pnNorte.add(getBtnResponder());
			pnNorte.add(getBtnEliminar());
		}
		return pnNorte;
	}
	private JPanel getPnSur() {
		if (pnSur == null) {
			pnSur = new JPanel();
			pnSur.add(getBtnAtras());
		}
		return pnSur;
	}
	private JPanel getPnCentro() {
		if (pnCentro == null) {
			pnCentro = new JPanel();
			pnCentro.setLayout(new BorderLayout(0, 0));
			pnCentro.add(getPnRemitente(), BorderLayout.NORTH);
			pnCentro.add(getPnContenidoCorreo(), BorderLayout.CENTER);
		}
		return pnCentro;
	}
	private JButton getBtnResponder() {
		if (btnResponder == null) {
			btnResponder = new JButton("Responder");
			btnResponder.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					responderCorreo();
				}
			});
		}
		return btnResponder;
	}

	private JButton getBtnEliminar() {
		if (btnEliminar == null) {
			btnEliminar = new JButton("Eliminar");
			btnEliminar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						eliminarCorreo();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			});
		}
		return btnEliminar;
	}


	private JButton getBtnAtras() {
		if (btnAtras == null) {
			btnAtras = new JButton("Atr\u00E1s");
			btnAtras.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					atras();
				}
			});
		}
		return btnAtras;
	}


	private JPanel getPnRemitente() {
		if (pnRemitente == null) {
			pnRemitente = new JPanel();
			pnRemitente.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
			pnRemitente.add(getPnNombreRemitente());
		}
		return pnRemitente;
	}
	private JPanel getPnContenidoCorreo() {
		if (pnContenidoCorreo == null) {
			pnContenidoCorreo = new JPanel();
			pnContenidoCorreo.setLayout(new BorderLayout(0, 0));
			pnContenidoCorreo.add(getPnAsunto(), BorderLayout.NORTH);
			pnContenidoCorreo.add(getPnMensaje(), BorderLayout.CENTER);
		}
		return pnContenidoCorreo;
	}
	private JPanel getPnAsunto() {
		if (pnAsunto == null) {
			pnAsunto = new JPanel();
			pnAsunto.setLayout(new GridLayout(0, 2, 0, 0));
			pnAsunto.add(getPanel());
			pnAsunto.add(getPnFechaYHora());
		}
		return pnAsunto;
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
	private JLabel getLblAsunto() {
		if (lblAsunto == null) {
			lblAsunto = new JLabel("Asunto:");
		}
		return lblAsunto;
	}
	private JLabel getLblElAsunto() {
		if (lblElAsunto == null) {
			lblElAsunto = new JLabel("");
			
			lblElAsunto.setText(correo.getAsunto());
		}
		return lblElAsunto;
	}
	private JLabel getLblRemitente() {
		if (lblRemitente == null) {
			lblRemitente = new JLabel("");
			lblRemitente.setFont(new Font("Tahoma", Font.PLAIN, 15));
			
			try {
				//lblRemitente.setText(buscarNombreMedico(correo.getCodMedicoOrigen()).toUpperCase());
				lblRemitente.setText(buscarNombreCompletoMedico(correo.getCodMedicoOrigen()).toUpperCase());
			} catch (SQLException e) {
				e.printStackTrace();
			}		
		}
		return lblRemitente;
	}

	
	/**
	 * Método para buscar en la lista de médicos, el nombre del médico del que le paso el nombre
	 * @param codMedicoOrigen
	 * @return
	 * @throws SQLException 
	 */
	private String buscarNombreMedico(String codMedicoOrigen) throws SQLException {
		return pbd.buscarNombreMedico(codMedicoOrigen);
	}
	
	/**
	 * Método para buscar en la lista de médicos, el nombre y apellido del médico del que le paso el nombre
	 * @param codMedicoOrigen
	 * @return
	 * @throws SQLException 
	 */
	private String buscarNombreCompletoMedico(String codMedicoOrigen) throws SQLException {
		return pbd.buscarEmpleadoPorCodigo(codMedicoOrigen);
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
			
			textArea.setText(correo.getMensaje());
		}
		return textArea;
	}
	
	
	/**
	 * Método para volver atrás, que me quita del panel el mensaje que estoy leyendo
	 */
	protected void atras() {
		
		ventanaCorreo.limpiarPanelCentro();
	}
	private JPanel getPnNombreRemitente() {
		if (pnNombreRemitente == null) {
			pnNombreRemitente = new JPanel();
			FlowLayout fl_pnNombreRemitente = (FlowLayout) pnNombreRemitente.getLayout();
			fl_pnNombreRemitente.setAlignment(FlowLayout.LEFT);
			pnNombreRemitente.add(getLblRemitente());
		}
		return pnNombreRemitente;
	}
	private JPanel getPnFechaYHora() {
		if (pnFechaYHora == null) {
			pnFechaYHora = new JPanel();
			FlowLayout flowLayout = (FlowLayout) pnFechaYHora.getLayout();
			flowLayout.setAlignment(FlowLayout.LEFT);
			pnFechaYHora.add(getLblFecha());
			pnFechaYHora.add(getLblHora());
		}
		return pnFechaYHora;
	}
	private JLabel getLblFecha() {
		if (lblFecha == null) {
			lblFecha = new JLabel("");
			
			lblFecha.setText("" + correo.getDate() + "  ");
		}
		return lblFecha;
	}
	private JLabel getLblHora() {
		if (lblHora == null) {
			lblHora = new JLabel("");
			
			lblHora.setText("" + correo.getTime());
		}
		return lblHora;
	}
	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			FlowLayout flowLayout = (FlowLayout) panel.getLayout();
			flowLayout.setAlignment(FlowLayout.LEFT);
			panel.add(getLblAsunto());
			panel.add(getLblElAsunto());
		}
		return panel;
	}
	
	
	
	/**
	 * Método para eliminar un correo
	 * @throws SQLException 
	 */
	protected void eliminarCorreo() throws SQLException {
		int dialogResult = JOptionPane.showConfirmDialog (null, "¿Desea eliminar el mensaje definitivamente?", "Eliminar Mensaje", JOptionPane.YES_NO_OPTION);
		
		if(dialogResult == JOptionPane.YES_OPTION){
		  	eliminar();
		}
		else if (dialogResult == JOptionPane.NO_OPTION){
			JOptionPane.showMessageDialog(null, "Su mensaje no ha sido eliminado");
		}
		
	}

	/**
	 * Método para eliminar definitivamente el correo
	 * 		1) Eliminarlo en la base de datos
	 * 		2) Actualizar los datos de lógica
	 * 	 	3) Quitar la vista del mensaje actual
	 * 		4) Despintarlo del panel de mensajes recibidos
	 * 		5) Decir que se ha eliminado con exito
	 * @throws SQLException 
	 */
	private void eliminar() throws SQLException {
		// 1
		pbd.eliminarMensaje(correo); // borramos de la base de datos

		
		// 2
		ventanaCorreo.actualizarLogica();
		
		
		// 3
		ventanaCorreo.limpiarPanelCentro();
		
		
		// 4
		ventanaCorreo.repintar();
		
		
		// 5
		JOptionPane.showMessageDialog(null, "El mensaje ha sido eliminado con éxito.");
		
	}
	
	
	/**
	 * Método para responder un correo
	 */
	protected void responderCorreo() {
		ventanaCorreo.responderCorreo(correo);	
	}

}
