package conexion;



import java.sql.*;

public class Conexion {
	
	private  Connection con;
	private static final String CONNECTION_JDBC_STRING="jdbc:hsqldb:hsql://localhost/ips";
	private static final String USERNAME_JDBC="SA";
	private static final String PASSWORD_JDBC=""; 

	

	
		
public  Connection getConnectionJDBC() throws SQLException {
		
	 DriverManager.registerDriver(new org.hsqldb.jdbc.JDBCDriver());
	con=DriverManager.getConnection(CONNECTION_JDBC_STRING,USERNAME_JDBC,PASSWORD_JDBC);
		
	return con;
	
}
	
	



}
