package logica;

import java.sql.SQLException;
import java.util.List;

import logica.servicios.ParserBaseDeDatos;

public class Equipo {

	private String numEquipo;
	public ParserBaseDeDatos pbd = new ParserBaseDeDatos();
	
	public Equipo(String numEquipo) {
		this.numEquipo = numEquipo;
	}

	public String getNumEquipo() {
		return numEquipo;
	}

	public void setNumEquipo(String numEquipo) {
		this.numEquipo = numEquipo;
	}
	
	@Override
	public String toString() {
		String mensaje = "";
		try {
			mensaje += numEquipo + ": " + miembrosEquipo();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mensaje;
	}
	
	public String miembrosEquipo() throws SQLException {
		String mensaje = "";
		List<AsignaEquipo> miembros = pbd.buscarAsignaEquipo(numEquipo);
		
		for(int i=0;i<miembros.size();i++) {
			if(i==miembros.size()-1) {
				mensaje += pbd.devolverEmpleado(miembros.get(i).getCodempleado());
			}
			else
				mensaje += pbd.devolverEmpleado(miembros.get(i).getCodempleado()) + ", ";
		}
		
		
		return mensaje;
	}
	
}
