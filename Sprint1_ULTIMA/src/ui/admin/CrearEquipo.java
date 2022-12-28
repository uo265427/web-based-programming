package ui.admin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import logica.Accion;
import logica.Equipo;
import logica.empleados.Enfermero;
import logica.empleados.Medico;
import logica.servicios.ParserBaseDeDatos;

import java.awt.Dimension;
import java.awt.GridLayout;
import net.miginfocom.swing.MigLayout;
import ui.AnadirAntecedentesHistorial;

import javax.swing.JScrollPane;
import javax.swing.JTextField;

import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class CrearEquipo extends JDialog {

	private JPanel contentPane;
	private JPanel pnMedico;
	private JPanel pnDatosMedicoSeleccion;
	private JPanel pnDatosMedico;
	private JPanel pnMedicosSeleccionados;
	private JScrollPane scrollPane;
	private JPanel pnMedicosLista;
	private JScrollPane scrollPaneListaMedicos;
	private JTextField txtFieldApellidoMedicoFiltro;
	private JTextField txtFieldNombreMedicoFiltro;
	private JLabel lblNombreFiltroMedico;
	private JTextField textField;
	private JButton btnFiltrarNombreMedico;
	private JLabel lblApellidoMedicoFiltro;
	private JTextField textField_1;
	private JButton btnFiltrarApellidoMedico;
	private ParserBaseDeDatos pbd = new ParserBaseDeDatos();
	private DefaultListModel<Medico> modeloListaM;
	private JList<Medico> listMedicos;
	private ArrayList<Medico> medicos;
	private JList<Medico> list;
	private DefaultListModel<Medico> modeloMedSelec;
	private JPanel panel;
	private JButton btnCrearEquipo;
	private String codAdmin;
	private JPanel panel_1;
	private JPanel panel_2;
	private JPanel panelEnfermero;
	private JPanel panelFiltrosSeleccEnfermero;
	private JScrollPane scrollPaneListaEnfermeros;
	private JPanel panelFiltrosEnf;
	private JPanel panelEnfSeleccionados;
	private JList<Enfermero> list_1;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JTextField txtFieldFiltroNomEnf;
	private JTextField txtFieldFiltroApeEmp;
	private JButton btnFiltroNomEmp;
	private JButton btnFiltroApeNum;
	private JButton btnNewButton;
	private DefaultListModel<Enfermero> modeloListEnf;
	private DefaultListModel<Enfermero> modeloEnfSelec;
	private ArrayList<Enfermero> enfermeros;
	private JScrollPane scrrPaneSeleccionados;
	private JList listEnfSeleccionados;
	private JButton btnNewButton_1;

	/**
	 * Create the frame.
	 * @throws SQLException 
	 */
	public CrearEquipo(String codAdmin) throws SQLException {
		setIconImage(Toolkit.getDefaultToolkit().getImage(AnadirAntecedentesHistorial.class.getResource("/img/logop.jpg")));
		this.codAdmin = codAdmin;
		setTitle("Crear Equipo");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1050, 650);
		contentPane = new JPanel();
		contentPane.setToolTipText("");
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.add(getPnMedico(), BorderLayout.CENTER);
		contentPane.add(getPanel(), BorderLayout.SOUTH);
	}

	private JPanel getPnMedico() throws SQLException {
		if (pnMedico == null) {
			pnMedico = new JPanel();
			pnMedico.setSize(new Dimension(219, 200));
			pnMedico.setBorder(new TitledBorder(null, "Empleado", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			pnMedico.setLayout(new GridLayout(0, 2, 0, 0));
			pnMedico.add(getPnDatosMedicoSeleccion());
			pnMedico.add(getPnMedicosLista());
			pnMedico.add(getPanelEnfermero());
			pnMedico.add(getScrollPaneListaEnfermeros());
		}
		return pnMedico;
	}
	private JPanel getPnDatosMedicoSeleccion() {
		if (pnDatosMedicoSeleccion == null) {
			pnDatosMedicoSeleccion = new JPanel();
			pnDatosMedicoSeleccion.setLayout(new GridLayout(0, 1, 0, 0));
			pnDatosMedicoSeleccion.add(getPnDatosMedico());
			pnDatosMedicoSeleccion.add(getPnMedicosSeleccionados());
		}
		return pnDatosMedicoSeleccion;
	}
	private JPanel getPnDatosMedico() {
		if (pnDatosMedico == null) {
			pnDatosMedico = new JPanel();
			pnDatosMedico.setLayout(new MigLayout("", "[152px][152px][152px]", "[17px][17px][17px][17px][]"));
			pnDatosMedico.add(getLblNombreFiltroMedico(), "flowx,cell 0 1,grow");
			pnDatosMedico.add(getTextField_2(), "cell 1 1,grow");
			pnDatosMedico.add(getBtnFiltrarNombreMedico(), "cell 2 1,grow");
			pnDatosMedico.add(getLblApellidoMedicoFiltro(), "cell 0 3,grow");
			pnDatosMedico.add(getTextField_1_1(), "cell 1 3,grow");
			pnDatosMedico.add(getBtnFiltrarApellidoMedico(), "cell 2 3,grow");
			pnDatosMedico.add(getBtnNewButton_1(), "cell 2 4");
		}
		return pnDatosMedico;
	}

	
	private JPanel getPnMedicosSeleccionados() {
		if (pnMedicosSeleccionados == null) {
			pnMedicosSeleccionados = new JPanel();
			pnMedicosSeleccionados.setLayout(null);
			pnMedicosSeleccionados.setBorder(new TitledBorder(null, "M\u00E9dicos seleccionados", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			pnMedicosSeleccionados.add(getScrollPane());
		}
		return pnMedicosSeleccionados;
	}
	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setBounds(new Rectangle(12, 23, 476, 72));
			scrollPane.setViewportView(getList());
		}
		return scrollPane;
	}
	private JPanel getPnMedicosLista() throws SQLException {
		if (pnMedicosLista == null) {
			pnMedicosLista = new JPanel();
			pnMedicosLista.setLayout(null);
			pnMedicosLista.setBorder(new TitledBorder(null, "Seleccione los m\u00E9dicos", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			pnMedicosLista.add(getScrollPaneListaMedicos());
		}
		return pnMedicosLista;
	}
	private JScrollPane getScrollPaneListaMedicos() throws SQLException {
		if (scrollPaneListaMedicos == null) {
			scrollPaneListaMedicos = new JScrollPane();
			scrollPaneListaMedicos.setOpaque(false);
			scrollPaneListaMedicos.setBounds(21, 23, 453, 178);
			scrollPaneListaMedicos.setViewportView(getList_1());
		}
		return scrollPaneListaMedicos;
	}
	
	private JList<Medico> getList_1() throws SQLException {
		if (listMedicos == null) {
			listMedicos = new JList<Medico>();
			listMedicos.setBackground(Color.WHITE);
			modeloListaM(pbd.buscarMedico(""));
			listMedicos.setModel(modeloListaM);
			medicos = new ArrayList<Medico>();
			listMedicos.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent event) {
					
					@SuppressWarnings("deprecation")
					Object[] selectedValues = listMedicos.getSelectedValues();
					if (selectedValues.length >= 0) {
						for (int i = 0; i < selectedValues.length; i++) {
							medicos.add((Medico) selectedValues[i]);
						}
						modeloListaSeleccionados(medicos);
					camposCubiertos();
					System.out.println(medicos.size());
					}}
			});
			
		}

		return listMedicos;
	}
	
	private JList<Medico> getList() {
		if (list == null) {
			list = new JList<Medico>();
			modeloMedSelec= new DefaultListModel<Medico>();
			list.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if(e.getClickCount()==2) {
						
							Medico med=(Medico) list.getSelectedValue();
							int res=JOptionPane.showConfirmDialog(null, "¿Esta seguro de que desea borrar este médico?","Confirmación de eliminación", JOptionPane.YES_NO_OPTION);
							if(res==JOptionPane.YES_OPTION)	
								modeloMedSelec.removeElement(med);
				}
					}
			});
		
		}
		return list;
	}
	

	
	private void  modeloListaSeleccionados(List<Medico>medicos){
		if(medicos!=null) {
		
		 
		for (int i = 0; i < medicos.size(); i++) {
			if(!modeloMedSelec.contains(medicos.get(i)))
			modeloMedSelec.addElement(medicos.get(i));
			
		}
		list.setModel(modeloMedSelec);
		
		}
	
	}
	
	private JLabel getLblNombreFiltroMedico() {
		if (lblNombreFiltroMedico == null) {
			lblNombreFiltroMedico = new JLabel("Nombre:");
		}
		return lblNombreFiltroMedico;
	}
	private JTextField getTextField() {
		if (textField == null) {
			textField = new JTextField();
			textField.setColumns(10);
		}
		return textField;
	}
	
	private DefaultListModel<Medico> modeloListaM(List<Medico> medico) throws SQLException {
		modeloListaM = new DefaultListModel<Medico>();
		if(medico!=null) {
		List<Medico> medicos = medico;
		for (int i = 0; i < medicos.size(); i++) {
			modeloListaM.addElement(medicos.get(i));

		}
		listMedicos.setModel(modeloListaM);
		}
		if(modeloListaM.getSize()==0)
			JOptionPane.showMessageDialog(null, "No se ha encontrado ningún médico con esas características");
		return modeloListaM;
	}
	
	private JButton getBtnFiltrarNombreMedico() {
		if (btnFiltrarNombreMedico == null) {
			btnFiltrarNombreMedico = new JButton("Filtrar");
			btnFiltrarNombreMedico.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(txtFieldNombreMedicoFiltro.getText().equals(""))
						JOptionPane.showMessageDialog(null, "Por favor introduce un valor");
					else {	
					
						try {
							modeloListaM(pbd.devolverMedicoNombre(txtFieldNombreMedicoFiltro.getText()));
							txtFieldNombreMedicoFiltro.setText("");
							
						} catch (SQLException e1) {
							e1.printStackTrace();
				}
					}
			}});
			btnFiltrarNombreMedico.setEnabled(false);
		}
		return btnFiltrarNombreMedico;
	}
	private JLabel getLblApellidoMedicoFiltro() {
		if (lblApellidoMedicoFiltro == null) {
			lblApellidoMedicoFiltro = new JLabel("Apellido:");
		}
		return lblApellidoMedicoFiltro;
	}
	private JTextField getTextField_1() {
		if (textField_1 == null) {
			textField_1 = new JTextField();
			textField_1.setColumns(10);
		}
		return textField_1;
	}
	private JButton getBtnFiltrarApellidoMedico() {
		if (btnFiltrarApellidoMedico == null) {
			btnFiltrarApellidoMedico = new JButton("Filtrar");
			btnFiltrarApellidoMedico.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(txtFieldApellidoMedicoFiltro.getText().equals(""))
						JOptionPane.showMessageDialog(null, "Por favor introduce un valor");
					else {	
					
						try {
							modeloListaM(pbd.devolverMedicoApellido(txtFieldApellidoMedicoFiltro.getText()));
							txtFieldApellidoMedicoFiltro.setText("");
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						
					}
				}
			});
			btnFiltrarApellidoMedico.setEnabled(false);
		}
		return btnFiltrarApellidoMedico;
	}
	private JTextField getTextField_2() {
		if (txtFieldNombreMedicoFiltro == null) {
			txtFieldNombreMedicoFiltro = new JTextField();
			txtFieldNombreMedicoFiltro.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					btnFiltrarNombreMedico.setEnabled(true);
				}
			});
				
			
			
			txtFieldNombreMedicoFiltro.setColumns(10);
		}
		return txtFieldNombreMedicoFiltro;
	}
	
	private JTextField getTextField_1_1() {
		if (txtFieldApellidoMedicoFiltro == null) {
			txtFieldApellidoMedicoFiltro = new JTextField();
			txtFieldApellidoMedicoFiltro.setColumns(10);
			txtFieldApellidoMedicoFiltro.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					btnFiltrarApellidoMedico.setEnabled(true);
				}
				
			});
			
		}
		return txtFieldApellidoMedicoFiltro;
	}
	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.add(getButton_1());
		}
		return panel;
	}
	private JButton getButton_1() {
		if (btnCrearEquipo == null) {
			btnCrearEquipo = new JButton("Crear equipo");
			btnCrearEquipo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						crearEquipo();
						dispose();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
		}
		return btnCrearEquipo;
	}

	protected void guardarAccion(String numEquipo) throws SQLException {
		List<Accion> devolverAccionesAdmin = pbd.devolverAccionesAdmin();
		int numeroAccion = 1;
		if(devolverAccionesAdmin.size()>0) {
			numeroAccion = devolverAccionesAdmin.size() + 1;
		}
		String naccion = "" +numeroAccion;
		
		Date fecha = new Date();	
		Time hora = new Time(new Date().getTime());	
		String mensajeMedicos = "";
		for(int i = 0; i<medicos.size(); i++) {
			if(i == medicos.size()) {
				mensajeMedicos += medicos.get(i).toString();
			}
			else {
				mensajeMedicos += medicos.get(i).toString() + ", ";
			}
		}
		
		
		for(int i = 0; i<enfermeros.size(); i++) {
			if(i == enfermeros.size()) {
				mensajeMedicos += enfermeros.get(i).toString();
			}
			else {
				mensajeMedicos += enfermeros.get(i).toString() + ", ";
			}
		}
		
		String mensajeAccion = "El aministrador " + codAdmin + " ha creado el equipo " + numEquipo + " formado por " + mensajeMedicos;
		
		Accion a = new Accion(naccion, codAdmin,  fecha, hora, mensajeAccion);
		
		pbd.guardarAccion(a);
		
	}

	protected void crearEquipo() throws SQLException {
		List<Equipo> devolverEquipos = pbd.calcularEquipos();
		int numeroEquipos = devolverEquipos.size() + 1;
		System.out.println(numeroEquipos);
		 String numEquipo = "Equipo " +numeroEquipos ;
		 pbd.nuevoEquipo(numEquipo);
		 for(int i =0;i< medicos.size() ; i++) {
			 Random r = new Random();
			 String codequipo = "" + r.nextInt(1000);
			 String codEmpleado = medicos.get(i).getCodeEmpleado();
			 pbd.asignarEquipo(codequipo, numEquipo, codEmpleado);
		 }
		 for(int i =0;i< enfermeros.size() ; i++) {
			 Random r = new Random();
			 String codequipo = "" + r.nextInt(1000);
			 String codEmpleado = enfermeros.get(i).getCodeEmpleado();
			 pbd.asignarEquipo(codequipo, numEquipo, codEmpleado);
		 }
		 guardarAccion(numEquipo);
		 
	}
	
	private boolean camposCubiertos() {
		if (JlistMedicoFill() || JlistEnfermeoFill()) {
			btnCrearEquipo.setEnabled(true);
			return true;

		} else
			btnCrearEquipo.setEnabled(false);

		return false;

	}
	
	private boolean JlistMedicoFill() {
		if (medicos == null)
			return false;
		return medicos.size() > 0;
	}
	
	private boolean JlistEnfermeoFill() {
		if (enfermeros == null)
			return false;
		return enfermeros.size() > 0;
	}
	
	private JPanel getPanelEnfermero() {
		if (panelEnfermero == null) {
			panelEnfermero = new JPanel();
			panelEnfermero.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Enfermero", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
			panelEnfermero.setLayout(new GridLayout(0, 1, 0, 0));
			panelEnfermero.add(getPanelFiltrosSeleccEnfermero());
		}
		return panelEnfermero;
	}
	
	private JPanel getPanelFiltrosSeleccEnfermero() {
		if (panelFiltrosSeleccEnfermero == null) {
			panelFiltrosSeleccEnfermero = new JPanel();
			panelFiltrosSeleccEnfermero.setLayout(new GridLayout(2, 0, 0, 0));
			panelFiltrosSeleccEnfermero.add(getPanelFiltrosEnf());
			panelFiltrosSeleccEnfermero.add(getPanelEnfSeleccionados());
		}
		return panelFiltrosSeleccEnfermero;
	}
	private JScrollPane getScrollPaneListaEnfermeros() {
		if (scrollPaneListaEnfermeros == null) {
			scrollPaneListaEnfermeros = new JScrollPane();
			scrollPaneListaEnfermeros.setViewportView(getList_1_2());
			scrollPaneListaEnfermeros.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Seleccione el enfermero", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		}
		return scrollPaneListaEnfermeros;
	}
	
	private JPanel getPanelFiltrosEnf() {
		if (panelFiltrosEnf == null) {
			panelFiltrosEnf = new JPanel();
			panelFiltrosEnf.setLayout(new MigLayout("", "[68px][225px][89px]", "[23px][23px][]"));
			panelFiltrosEnf.add(getLblNewLabel_1(), "cell 0 0,growx,aligny center");
			panelFiltrosEnf.add(getTxtFieldFiltroNomEnf(), "cell 1 0,growx,aligny center");
			panelFiltrosEnf.add(getBtnFiltroNomEmp(), "cell 2 0,growx,aligny top");
			panelFiltrosEnf.add(getLblNewLabel_2(), "cell 0 1,growx,aligny center");
			panelFiltrosEnf.add(getTxtFieldFiltroApeEmp(), "cell 1 1,growx,aligny center");
			panelFiltrosEnf.add(getBtnFiltroApeNum(), "cell 2 1,growx,aligny top");
			panelFiltrosEnf.add(getBtnNewButton(), "cell 2 2");
		}
		return panelFiltrosEnf;
	}
	
	private JButton getBtnNewButton() {
		if (btnNewButton == null) {
			btnNewButton = new JButton("Quitar filtro");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						modeloListEnf.removeAllElements();
						modeloListEnf.addAll(pbd.listarEnfermero());
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
		}
		return btnNewButton;
	}
	
	private JPanel getPanelEnfSeleccionados() {
		if (panelEnfSeleccionados == null) {
			panelEnfSeleccionados = new JPanel();
			panelEnfSeleccionados.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Enfermeros seleccionados", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panelEnfSeleccionados.setLayout(null);
			panelEnfSeleccionados.add(getScrrPaneSeleccionados());
		}
		return panelEnfSeleccionados;
	}
	
	private JScrollPane getScrrPaneSeleccionados() {
		if (scrrPaneSeleccionados == null) {
			scrrPaneSeleccionados = new JScrollPane();
			scrrPaneSeleccionados.setBounds(10, 21, 478, 74);
			scrrPaneSeleccionados.setViewportView(getListEnfSeleccionados());
		}
		return scrrPaneSeleccionados;
	}
	
	private JList getListEnfSeleccionados() {
		if (listEnfSeleccionados == null) {
			listEnfSeleccionados = new JList();
			modeloEnfSelec= new DefaultListModel<Enfermero>();
			listEnfSeleccionados.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if(e.getClickCount()==2) {
						
							Enfermero enf=(Enfermero) listEnfSeleccionados.getSelectedValue();
							int res=JOptionPane.showConfirmDialog(null, "¿Esta seguro de que desea borrar este enfermero?","Confirmación de eliminación", JOptionPane.YES_NO_OPTION);
							if(res==JOptionPane.YES_OPTION)	
								modeloEnfSelec.removeElement(enf);
				}
					}
			});
		}
		return listEnfSeleccionados;
	}
	
	private JList<Enfermero> getList_1_2() {
		if (list_1 == null) {
			list_1 = new JList<Enfermero>();
			enfermeros = new ArrayList<Enfermero>();
			modeloListEnf = new DefaultListModel<Enfermero>();
			try {
				 modeloListEnf.addAll(pbd.listarEnfermero());
			} catch (SQLException e) {
				e.printStackTrace();
			}
			list_1.setModel(modeloListEnf);
			list_1.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent event) {
					
					@SuppressWarnings("deprecation")
					Object[] selectedValues = list_1.getSelectedValues();
					if (selectedValues.length >= 0) {
						for (int i = 0; i < selectedValues.length; i++) {
							enfermeros.add((Enfermero) selectedValues[i]);
						}

				
						for (int i = 0; i < enfermeros.size(); i++) {
							if(!modeloEnfSelec.contains(enfermeros.get(i)))
							modeloEnfSelec.addElement(enfermeros.get(i));
							
						}
						listEnfSeleccionados.setModel(modeloEnfSelec);
						camposCubiertos();
						}
					

					camposCubiertos();

					}

			});
		}
		return list_1;
	}
	
	
	private JLabel getLblNewLabel_1() {
		if (lblNewLabel_1 == null) {
			lblNewLabel_1 = new JLabel("Nombre:");
		}
		return lblNewLabel_1;
	}
	private JLabel getLblNewLabel_2() {
		if (lblNewLabel_2 == null) {
			lblNewLabel_2 = new JLabel("Apellidos:");
		}
		return lblNewLabel_2;
	}
	private JTextField getTxtFieldFiltroNomEnf() {
		if (txtFieldFiltroNomEnf == null) {
			txtFieldFiltroNomEnf = new JTextField();
			txtFieldFiltroNomEnf.setColumns(10);
			txtFieldFiltroNomEnf.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					btnFiltroNomEmp.setEnabled(true);
				}
			});
		}
		return txtFieldFiltroNomEnf;
	}
	private JTextField getTxtFieldFiltroApeEmp() {
		if (txtFieldFiltroApeEmp == null) {
			txtFieldFiltroApeEmp = new JTextField();
			txtFieldFiltroApeEmp.setColumns(10);
			txtFieldFiltroApeEmp.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					btnFiltroApeNum.setEnabled(true);
				}
			});
		}
		return txtFieldFiltroApeEmp;
	}
	private JButton getBtnFiltroNomEmp() {
		if (btnFiltroNomEmp == null) {
			btnFiltroNomEmp = new JButton("Filtrar");
			btnFiltroNomEmp.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						modeloListaEnfermero(pbd.buscarNombreEnfermero(txtFieldFiltroNomEnf.getText()));
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
			btnFiltroNomEmp.setEnabled(false);
		}
		return btnFiltroNomEmp;
	}
	
	private DefaultListModel<Enfermero> modeloListaEnfermero(List<Enfermero> enfermeros) throws SQLException {
		modeloListEnf = new DefaultListModel<Enfermero>();
		if(enfermeros!=null) {
		List<Enfermero> enfermero =enfermeros;
		for (int i = 0; i < enfermero.size(); i++) {
			modeloListEnf.addElement(enfermero.get(i));

		}
		list_1.setModel(modeloListEnf);
		}
		
		return modeloListEnf;
	}
	
	private JButton getBtnFiltroApeNum() {
		if (btnFiltroApeNum == null) {
			btnFiltroApeNum = new JButton("Filtrar");
			btnFiltroApeNum.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						modeloListaEnfermero(pbd.buscarApellidoEnfermero(txtFieldFiltroApeEmp.getText()));
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
			btnFiltroApeNum.setEnabled(false);
		}
		return btnFiltroApeNum;
	}
	
	private JButton getBtnNewButton_1() {
		if (btnNewButton_1 == null) {
			btnNewButton_1 = new JButton("Quitar filtro");
			btnNewButton_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						modeloListaM.removeAllElements();;
						modeloListaM(pbd.buscarMedico(""));
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
		}
		return btnNewButton_1;
	}
}
