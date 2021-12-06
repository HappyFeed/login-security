package model;

import java.sql.*;

/**
 * Clase encargada de la conexión.
 */
public class Conexion {
	
	/**
     * Método realiza la conexion con la base de datos.
     * 
     * @throws SQLException
     */
	public static Connection obtenerConexion() {
		
		String url = "jdbc:sqlserver://localhost:1434;"
				+"database=login-security;"
				+"user=sa;"
				+"password=1234;";
		 try {
		    	Connection con = DriverManager.getConnection(url);
		    	return con;
				
			} catch (SQLException ex) {
			
				System.out.println(ex.toString());
				return null;
			}
	}
}
