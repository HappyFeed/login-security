package ui;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.Conexion;
import model.PasswordGenerator;

public class LoginController {
	
	@FXML
	private Label advertenciaText;

    @FXML
    private Button loginBtn;

    @FXML
    private PasswordField passText;

    @FXML
    private TextField userText;

    @FXML
    void tryLogin(ActionEvent event) {
    	
    	String user = userText.getText();
    	String pass = passText.getText();
    	
    	String consulta = "SELECT userName, password, privilege FROM usuario WHERE userName = '"+user+"'";
    
    	try {
    	
    		Connection con= Conexion.obtenerConexion();
    		PreparedStatement ps = con.prepareStatement(consulta);
    		ResultSet rs = ps.executeQuery();
    		
    		//Con esto verificamos si el usuario existe en la base de datos
    		if (rs.next()) {
    			
    			//String userBD = rs.getString("userName");
    			String passBD = rs.getString("password");
    			String privi = rs.getString("privilege");
    			
    			if (PasswordGenerator.validatePassword(pass, passBD)) {
    				
    				if (privi.equals("admin")) {
    					
    					nextToStageAdmin();
						
					}else {
						
						nextToStageUser();
						
					}
					
				}else {
					
					advertenciaText.setText("Wrong Password");
					advertenciaText.setVisible(true);
				}
    	
		
    		}else {
		
    			advertenciaText.setVisible(true);
		
    		}
    
    	} catch (/*SQLException | NoSuchAlgorithmException | InvalidKeySpecException */ Exception ex) {
    		System.out.println(ex.toString());
    	}

    }
    
    @FXML
    void initialize() {
    	
    	advertenciaText.setVisible(false);
    	
    }
    
   
    void nextToStageAdmin() {
        try {
            FXMLLoader loader= new FXMLLoader(getClass().getResource("admiDashBoard.fxml"));
            Parent root=loader.load();
            Scene scene = new Scene(root);
            Main.stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    
   
    void nextToStageUser() {
        try {
            FXMLLoader loader= new FXMLLoader(getClass().getResource("usersDashBoard.fxml"));
            Parent root=loader.load();
            Scene scene = new Scene(root);
            Main.stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
