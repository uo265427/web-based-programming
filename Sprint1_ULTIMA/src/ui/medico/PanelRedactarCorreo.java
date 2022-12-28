package ui.medico;

import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import logica.Correo;
import logica.empleados.Medico;
import logica.servicios.ParserBaseDeDatos;
import ui.AnadirAntecedentesHistorial;

import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PanelRedactarCorreo extends JPanel {

	
	private String codMedico;
	private VentanaCorreo ventanaCorreo;
	private ParserBaseDeDatos pbd=new ParserBaseDeDatos();

	private List<Medico> medicos;
	private String[] nombreMedicos;
	private String[] nombres;
	
	private JPanel pnDestinatario;
	private JPanel pnEnviar;
	private JButton btnEnviar;
	private JLabel lblDestinatario;
	private JTextField txtFDestinatario;
	private JPanel pnCentro;
	private JPanel pnAsunto;
	private JLabel lblAsunto;
	private JTextField txtFAsunto;
	private JPanel pnMensaje;
	private JScrollPane scrollPane;
	private JTextArea textArea;
	private JPanel pnNombreDestinatario;
	private JComboBox cbMedicos;
	private JButton btnFiltrar;
	
	/**
	 * Costructor para la clase PanelRedactarCorreo
	 * 
	 * @param codMedico
	 * @param ventanaCorreo
	 */
	public PanelRedactarCorreo(String codMedico, VentanaCorreo ventanaCorreo) {
		try {
			this.medicos = pbd.buscarMedico(" ");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		this.nombreMedicos = new String[medicos.size()];
		this.nombres = new String[medicos.size()];
		for (int i = 0; i < medicos.size(); i++) {
			String codigo = medicos.get(i).getCodEmpleado();
			try {
				// cambiado
				nombres[i] = pbd.buscarNombreMedico(codigo);
				nombreMedicos[i] = pbd.buscarEmpleadoPorCodigo(codigo);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		this.codMedico = codMedico;
		this.ventanaCorreo = ventanaCorreo;
		setLayout(new BorderLayout(0, 0));
		try {
			add(getPnDestinatario(), BorderLayout.NORTH);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		add(getPnEnviar(), BorderLayout.SOUTH);
		add(getPnCentro(), BorderLayout.CENTER);
	}

	private JPanel getPnDestinatario() throws SQLException{
		if (pnDestinatario == null) {
			pnDestinatario = new JPanel();
			pnDestinatario.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
			pnDestinatario.add(getPnNombreDestinatario());
		}
		return pnDestinatario;
	}
	private JPanel getPnEnviar() {
		if (pnEnviar == null) {
			pnEnviar = new JPanel();
			FlowLayout flowLayout = (FlowLayout) pnEnviar.getLayout();
			pnEnviar.add(getBtnEnviar());
		}
		return pnEnviar;
	}
	private JButton getBtnEnviar() {
		if (btnEnviar == null) {
			btnEnviar = new JButton("Enviar");
			btnEnviar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						enviarMensaje();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			});
			btnEnviar.setHorizontalAlignment(SwingConstants.RIGHT);
		}
		return btnEnviar;
	}
	


	private JLabel getLblDestinatario() {
		if (lblDestinatario == null) {
			lblDestinatario = new JLabel("Destinatario: ");
		}
		return lblDestinatario;
	}
	private JTextField getTxtFDestinatario() {
		if (txtFDestinatario == null) {
			txtFDestinatario = new JTextField();
			txtFDestinatario.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					activarFiltrado();
				}
			});
	
			txtFDestinatario.setBounds(new Rectangle(0, 0, 6, 0));
			txtFDestinatario.setColumns(10);
		}
		return txtFDestinatario;
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
	
	
	/**
	 * Metodo para enviar un mensaje
	 * @throws SQLException 
	 */
	protected void enviarMensaje() throws SQLException {
		
		
		if (comprobarCampos()) { // Si ha rellenado todos los campos correctamente
			
			
			int indiceSeleccionado = cbMedicos.getSelectedIndex(); // El índice que hay seleccionado en el comboBox
			String nombreDelMedico = nombres[indiceSeleccionado]; // Cogemos lo que hay seleccionado en el comboBox
			
			String codMedicoDestino = buscarMedicoDestino(nombreDelMedico);
			if (!codMedicoDestino.equals("")) {  //Si existe el médico que ha escrito
		
				// Saco los datos que ha introducido
				String nombreDestinatario = txtFDestinatario.getText();
				String asunto = txtFAsunto.getText();
				String mensaje = textArea.getText();
			
				Random r = new Random();
				String codCorreo = "" + (int)r.nextInt(999);
			
				codMedicoDestino = buscarMedicoDestino(nombreDelMedico);
				String codMedicoOrigen = codMedico;
			
				Date date = new Date();	
				Time time = new Time(new Date().getTime());
			
				Correo nuevoCorreo = new Correo(codCorreo, codMedicoDestino, codMedicoOrigen, asunto, mensaje, date, time);
			
				pbd.crearCorreo(nuevoCorreo);
				
				JOptionPane.showMessageDialog(null, "Su mensaje a " + nombreDestinatario + " ha sido enviado con éxito.");
				
				limpiar();
			}
		}
	}

	




	/**
	 * Método para comprobar si el usario ha rellenado todos los campos correctamente
	 * @return
	 */
	private boolean comprobarCampos() {
		// Si ha dejado algún campo vacío
		if ((txtFDestinatario.getText().isEmpty()) || (txtFAsunto.getText().isEmpty()) || (textArea.getText().isEmpty())) {
			JOptionPane.showMessageDialog(null, "Compruebe que ha rellenado todos los campos."); // Le avisa si ha dejado algo sin rellenar
			return false;
		}
		return true;
	}
	
	
	/**
	 * Método que me busca en memoria el código del nombre del empleado que se le pasa
	 * @param nombreDestinatario
	 * @return
	 * @throws SQLException 
	 */
	private String buscarMedicoDestino(String nombreDestinatario) throws SQLException {
		
		String codMedicoDestino = pbd.buscarCodigoMedico(nombreDestinatario);

		
		if (codMedicoDestino == null) { // Si no lo encuentra
			JOptionPane.showMessageDialog(null, "El destinatario que ha introducido no existe.");
			return "";
		}
		
		// Si lo encuentra
		return codMedicoDestino;
	}
	
	
	/**
	 * Método para limpiar el panel donde se muestra la información
	 */
	private void limpiar() {
		ventanaCorreo.limpiarPanelCentro();
	}
	private JPanel getPnNombreDestinatario() {
		if (pnNombreDestinatario == null) {
			pnNombreDestinatario = new JPanel();
			FlowLayout flowLayout = (FlowLayout) pnNombreDestinatario.getLayout();
			flowLayout.setAlignment(FlowLayout.LEFT);
			pnNombreDestinatario.add(getLblDestinatario());
			pnNombreDestinatario.add(getTxtFDestinatario());
			pnNombreDestinatario.add(getBtnFiltrar());
			try {
				pnNombreDestinatario.add(getCbMedicos());
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return pnNombreDestinatario;
	}
	private JComboBox getCbMedicos() throws SQLException {
		if (cbMedicos == null) {
			cbMedicos = new JComboBox();
			cbMedicos.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					cambiarContenidoDestinatario();
				}
			});
			cbMedicos.setModel(new DefaultComboBoxModel<String>(nombreMedicos));	
			
		}
		return cbMedicos;
	}


	private JButton getBtnFiltrar() {
		if (btnFiltrar == null) {
			btnFiltrar = new JButton("Filtrar");
			btnFiltrar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					buscarDestinatario();
				}
			});
			btnFiltrar.setEnabled(false);
		}
		return btnFiltrar;
	}
	
	
	


	/**
	 * Método para activar el botón de filtrar
	 */
	protected void activarFiltrado() {
		if (!btnFiltrar.isEnabled()) { // Si no estaba ya activado
			btnFiltrar.setEnabled(true);
		}
	}
	
	
	/**
	 * Método que me busca un destinatario en el comboBox a partir del texto que hay seleccionado en el textField
	 */
	protected void buscarDestinatario() {
		if (!txtFDestinatario.getText().equals("")) { // Si hay alo escrito en el campo de texto
			String buscador  = txtFDestinatario.getText().toLowerCase(); // Lo que ha buscado (lo pasamos a minuscula)
			boolean encontrada = false; // Para saber si encontró o no el médico buscado
			
			for (int i = 0; i < nombreMedicos.length; i++) {
				if (nombreMedicos[i].toLowerCase().equals(buscador)) { // Si lo que está buscando lo hay en la lista de médicos
					cbMedicos.setSelectedIndex(i); // Lo mostramos en el cb
					
					encontrada = true; // la encontró
				}
			}
			
			if(!encontrada) { // Si no encontró el médico
				JOptionPane.showMessageDialog(null, "No hemos podido encontrar su destinatario");
			}
		}	
		
		else {
			JOptionPane.showMessageDialog(null, "No haintroducido nada en el buscador");
		}
	}
	
	
	
	/**
	 * Método que para cada vez que se seleccione un nuevo elemento en el comboBox, quede reflejado en el campo de texto
	 */
	protected void cambiarContenidoDestinatario() {
		int indiceSeleccionado = cbMedicos.getSelectedIndex(); // El índice que hay seleccionado en el comboBox
		
		String nombreDelMedico = nombreMedicos[indiceSeleccionado]; // Cogemos lo que hay seleccionado en el comboBox
		
		txtFDestinatario.setText(nombreDelMedico); // Cambiamos el contenido del campo de texto
	}
}
