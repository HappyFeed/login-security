package model;

import java.sql.*;

public class Conexion {
	
	public static Connection obtenerConexion() {
		
		String url = "jdbc:sqlserver://localhost:1433;"
				+"database=login-security;"
				+"user=postgres;"
				+"password=postgres;";
		 try {
		    	Connection con = DriverManager.getConnection(url);
		    	return con;
				
			} catch (SQLException ex) {
			
				System.out.println(ex.toString());
				return null;
			}
	}
}