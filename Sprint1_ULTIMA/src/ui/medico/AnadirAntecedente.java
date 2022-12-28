/**
 * 
 */
package ui.medico;

import javax.swing.JDialog;

import logica.Antecedente;
import logica.Preinscripcion;
import logica.servicios.ParserBaseDeDatos;
import ui.AnadirAntecedentesHistorial;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Random;
import java.awt.event.ActionEvent;

/**
 * @author María
 *
 */
public class AnadirAntecedente extends JDialog{

	private ModificarMedicosNuevoCard modificarCitaMedico;
	private ParserBaseDeDatos pbd=new ParserBaseDeDatos();
	private JPanel pnSur;
	private JPanel pnCentral;
	private JButton btnGuardar;
	private JButton btnCancelar;
	private JPanel pnIzquierda;
	private JPanel pnDcha;
	private JLabel lblNewLabel;
	private JTextField txtfAntecedente;
	
	
	/**
	 * Constructor para la clase anadir antecedente
	 * @param modificarMedicosNuevoCard
	 */
	public AnadirAntecedente(ModificarMedicosNuevoCard modificarMedicosNuevoCard) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(AnadirAntecedentesHistorial.class.getResource("/img/logop.jpg")));
		this.modificarCitaMedico = modificarMedicosNuevoCard; // La ventana anterior

		
		setTitle("A\u00F1adir Antecedente");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 394, 218);
		getContentPane().add(getPnSur(), BorderLayout.SOUTH);
		getContentPane().add(getPnCentral(), BorderLayout.CENTER);
		// TODO Auto-generated constructor stub
	}
 
	private JPanel getPnSur() {
		if (pnSur == null) {
			pnSur = new JPanel();
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
			pnCentral.setLayout(new GridLayout(0, 2, 0, 0));
			pnCentral.add(getPnIzquierda());
			pnCentral.add(getPnDcha());
		}
		return pnCentral;
	}
	private JButton getBtnGuardar() {
		if (btnGuardar == null) {
			btnGuardar = new JButton("Guardar");
			btnGuardar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						guardar();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
//					modificarCitaMedico.vaciarCBAntecedentes();
//					modificarCitaMedico.rellenarCBAntecedentes();
				}
			});
		}
		return btnGuardar;
	}


	private JButton getBtnCancelar() {
		if (btnCancelar == null) {
			btnCancelar = new JButton("Cancelar");
			btnCancelar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
		}
		return btnCancelar;
	}
	private JPanel getPnIzquierda() {
		if (pnIzquierda == null) {
			pnIzquierda = new JPanel();
			pnIzquierda.setLayout(new GridLayout(0, 1, 0, 0));
			pnIzquierda.add(getLblNewLabel());
		}
		return pnIzquierda;
	}
	private JPanel getPnDcha() {
		if (pnDcha == null) {
			pnDcha = new JPanel();
			pnDcha.setLayout(new GridLayout(0, 1, 0, 0));
			pnDcha.add(getTxtfAntecedente());
		}
		return pnDcha;
	}
	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("Antecedente:");
		}
		return lblNewLabel;
	}
	private JTextField getTxtfAntecedente() {
		if (txtfAntecedente == null) {
			txtfAntecedente = new JTextField();
			txtfAntecedente.setColumns(10);
		}
		return txtfAntecedente;
	}
	
	/**
	 * Método para guardar el nuevo antecedente
	 * @throws SQLException 
	 */
	protected void guardar() throws SQLException {
		
		if (comprobarCampos()) { // Comprueba que al menos ha puesto el nombre y seleccionado el tipo de preinscripcion
			// Metemos el nuevo antecedente
			String nombre = getTxtfAntecedente().getText();
			
			Random random = new Random();
			String codigo = "" + random.nextInt(99999999);
			
			Antecedente antecedente = new Antecedente(codigo, nombre);
			
			pbd.nuevoAntecedenteB(antecedente); // Lo guardo en la base de datos
			
			modificarCitaMedico.setAntecedente(antecedente); // Le mandamos el antecedente a la ventana anterior
			
			actualizarCBAntecedentes(); // Método que me carga en el cb los valores actualizados
			
			modificarCitaMedico.actualizarAntecedente(); // Ponemos el comboBox que indica el nombre del antecedente con el que acaba de crear
			
			JOptionPane.showMessageDialog(null, "Se ha añadido correctamente su nuevo antecedente.");

			dispose();
		}
		
		
		else {  // Le avisa de que hay algo que no ha puesto
			JOptionPane.showMessageDialog(null, "Por favor compruebe que ha rellenado el nombre.");
		}
	}





	/**
	 * Método para que  
	 * @return
	 */
	private boolean comprobarCampos() {
		if (txtfAntecedente.getText().equals("")) { // Si ha dejado el nombre en blanco
			return false;
		}

		else { // Si estaba todo bien
			return true;
		}
	}
	
	
	

	
	
	/**
	 * Método para poner el nuevo antecedente
	 * @throws SQLException 
	 */
	private void actualizarCBAntecedentes() throws SQLException {
		modificarCitaMedico.ponerAntecedentes();		
	}
}
