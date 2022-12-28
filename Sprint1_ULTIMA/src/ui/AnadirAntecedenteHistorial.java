/**
 * 
 */
package ui;

import javax.swing.JDialog;

import logica.Antecedente;
import logica.servicios.ParserBaseDeDatos;
import ui.medico.ModificarMedicosNuevoCard;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Random;
import java.awt.event.ActionEvent;

/**
 * @author María
 *
 */
public class AnadirAntecedenteHistorial extends JDialog{
	
	private AnadirAntecedentesHistorial anadirAntecedentesHistorial;
	private ParserBaseDeDatos pbd=new ParserBaseDeDatos();
	private JPanel pnSur;
	private JPanel pnCentro;
	private JButton btnGuardar;
	private JButton btnCancelar;
	private JPanel pnIzquierda;
	private JPanel pnDcha;
	private JLabel lblAntecedente;
	private JTextField txtfAntecedente;
	
	
	
	
	/**
	 * 
	 * Constructor
	 * @param anadirAntecedentesHistorial
	 */
	public AnadirAntecedenteHistorial(AnadirAntecedentesHistorial anadirAntecedentesHistorial) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(AnadirAntecedentesHistorial.class.getResource("/img/logop.jpg")));
		setModal(true);
		setTitle("A\u00F1adir Antecedente");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 394, 218);
		
		this.anadirAntecedentesHistorial = anadirAntecedentesHistorial;
		getContentPane().add(getPnSur(), BorderLayout.SOUTH);
		getContentPane().add(getPnCentro(), BorderLayout.CENTER);
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
	private JPanel getPnCentro() {
		if (pnCentro == null) {
			pnCentro = new JPanel();
			pnCentro.setLayout(new GridLayout(0, 2, 0, 0));
			pnCentro.add(getPnIzquierda());
			pnCentro.add(getPnDcha());
		}
		return pnCentro;
	}
	private JButton getBtnGuardar() {
		if (btnGuardar == null) {
			btnGuardar = new JButton("Guardar");
			btnGuardar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						guardar();
						limpiar();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			});
		}
		return btnGuardar;
	}
	
	
	/**
	 * Método para dejar la ventana anterior limpia
	 */
	protected void limpiar() {
		
		anadirAntecedentesHistorial.limpiar();
	}




	/**
	 * Método para guardar la nueva
	 * @throws SQLException 
	 */
	protected void guardar() throws SQLException {
		if (txtfAntecedente.getText() != "") { // Comprueba que al menos ha puesto el nombre
			
			// Metemos el nuevo antecedente
			String nombre = getTxtfAntecedente().getText();

			Random random = new Random();
			String codigo = "" + random.nextInt(99999999);
			
			Antecedente antecedente = new Antecedente(codigo, nombre);
			
			pbd.nuevoAntecedenteB(antecedente); // Lo guardo en la base de datos
			
			anadirAntecedentesHistorial.setAntecedente(antecedente); // Le mandamos el antecedente a la ventana anterior
			
			actualizarCBAntecedentes(); // Método que me carga en el cb los valores actualizados
			
			anadirAntecedentesHistorial.actualizarAntecedente(); // Ponemos el comboBox que indica el nombre del antecedente con el que acaba de crear
			//anadirAntecedentesHistorial.añadirFilas();
			
			JOptionPane.showMessageDialog(null, "Se ha añadido correctamente su nuevo antecedente.");
			
			dispose();
		}
		
		
		else {  // Le avisa de que hay algo que no ha puesto
			JOptionPane.showMessageDialog(null, "Por favor compruebe que ha rellenado el nombre.");
		}
		
	}




	/**
	 * Método para actualizar el cb de los antecedentes
	 */
	private void actualizarCBAntecedentes() {
		try {
			anadirAntecedentesHistorial.ponerAntecedentes();
		} catch (SQLException e) {
			e.printStackTrace();
		}		
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
			pnIzquierda.add(getLblAntecedente());
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
	private JLabel getLblAntecedente() {
		if (lblAntecedente == null) {
			lblAntecedente = new JLabel("Antecedente:");
		}
		return lblAntecedente;
	}
	private JTextField getTxtfAntecedente() {
		if (txtfAntecedente == null) {
			txtfAntecedente = new JTextField();
			txtfAntecedente.setColumns(10);
		}
		return txtfAntecedente;
	}
}
