package logica.servicios;

import java.sql.ResultSet;
import java.sql.SQLException;

import logica.Cita;

public class DtoCita {
	
	
	public static Cita cita(ResultSet rs) throws SQLException {
		boolean res=false;
		if(rs.getByte("urgencia")==1)
			res=true;
		return new Cita(rs.getString("codcita"),rs.getString("codpaciente"),rs.getString("codmedico"),rs.getTime("hinicio"), 
				rs.getTime("hfin"),rs.getDate("fecha"),rs.getString("ubicacion"),res);
		
	}

}
