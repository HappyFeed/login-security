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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.StageStyle;
import model.Conexion;
import model.PasswordGenerator;

/**
 * Clase controladora del login.
 */
public class LoginController {
	
	@FXML
	private Label advertenciaText;

    @FXML
    private Button loginBtn;

    @FXML
    private PasswordField passText;

    @FXML
    private TextField userText;

    /**
     * M�todo que realiza el login de un usuario.
     * 
     * @param event Cuando se presiona el bot�n login
     * 
     * @throws NullPointerException
     * @throws Exception
     */
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
    			
    			String userBD = rs.getString("userName");
    			String passBD = rs.getString("password");
    			if(passBD.equals("")) {
    				rs.close();
					ps.close();
					con.close();
    				throw new NullPointerException();
    			}
    			String privi = rs.getString("privilege");
    			
    			if (PasswordGenerator.validatePassword(pass, passBD)) {
    				rs.close();
					ps.close();
					con.close();
    				if (privi.equals("admin")) {
    					
    					nextToStageAdmin();
						
					}else {
						
						nextToStageUser(userBD);
						
					}
					
				}else {
					
					advertenciaText.setText("Wrong Password");
					advertenciaText.setVisible(true);
					rs.close();
					ps.close();
					con.close();
				}
    	
		
    		}else {
		
    			advertenciaText.setVisible(true);
		
    		}
    
    	} catch (NullPointerException n) {
    		alert();
    		
    	} catch (/*SQLException | NoSuchAlgorithmException | InvalidKeySpecException */ Exception ex) {
    		System.out.println(ex.toString());
    	}

    }
    
    /**
     * M�todo genera una alerta.
     */
    void alert() {
    	Alert info = new Alert(AlertType.INFORMATION);
        info.setTitle("Information");
        info.setHeaderText(null);
        info.initStyle(StageStyle.UTILITY);
        info.setContentText("Tu contrase�a fue eliminada por el administrador");
        info.show();
    }
    
    /**
     * M�todo que inicializa la ventana.
     */
    @FXML
    void initialize() {
    	
    	advertenciaText.setVisible(false);
    	
    }
    
    /**
     * M�todo que abre la ventana del administrador.
     * 
     * @throws IOException
     */
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
    
    /**
     * M�todo que abre la ventana del usuario com�n.
     * 
     * @param user Nombre del usuario que hace login
     * 
     * @throws IOException
     */
    void nextToStageUser(String user) {
        try {
            FXMLLoader loader= new FXMLLoader(getClass().getResource("usersDashBoard.fxml"));
            Parent root=loader.load();
            UserController scc= (UserController) loader.getController();         
            scc.recibirUser(user);
            Scene scene = new Scene(root);
            Main.stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
