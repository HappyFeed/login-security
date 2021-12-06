package ui;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.stage.StageStyle;
import model.Conexion;
import model.PasswordGenerator;

/**
 * Clase controladora de la ventana del usuario común.
 */
public class UserController {

    @FXML
    private Button buttonSave;

    @FXML
    private ImageView imagePanel;

    @FXML
    private Label labelError;

    @FXML
    private TextField textFechaAccesp;

    @FXML
    private PasswordField textNewPassword;
    
    private Connection con;
    
    private PreparedStatement ps;
    
    private String user;
    
    private Date date;

    /**
     * Método que actualiza la contraseña del usuario.
     * 
     * @param event Cuando se presiona el botón guardar
     * 
     * @throws Exception
     */
    @FXML
    void saveNewPassword(ActionEvent event) {
    	String consulta = "SELECT password FROM usuario WHERE userName = '"+user+"'";
    	
    	try {
    		ps = con.prepareStatement(consulta);
        	ResultSet rs = ps.executeQuery();
    		if (rs.next()) {
    			
    			String passBD = rs.getString("password");
    			if(PasswordGenerator.validatePassword(textNewPassword.getText(), passBD) || textNewPassword.getText().equals("")) {
    				alert();
    				textNewPassword.clear();
    			} else {
    				String passCifrada = PasswordGenerator.generateStrongPasswordHash(textNewPassword.getText());
    				String setPass = "Update usuario set password = " + "'"+ passCifrada +"'" + " where userName= '" + user + "'";
    	    		Statement st =con.createStatement();
    	            st.execute(setPass);
        		}
    		}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    /**
     * Método que conecta la aplicación a la base de datos.
     * 
     * @throws IOException
     */
    void conect() {
    	String consulta = "SELECT userName FROM usuario WHERE privilege = 'normal'";
    	try {
			con= Conexion.obtenerConexion();
			ps = con.prepareStatement(consulta);
			ResultSet rs = ps.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    /**
     * Método genera una alerta.
     */
    void alert() {
    	Alert info = new Alert(AlertType.INFORMATION);
        info.setTitle("Information");
        info.setHeaderText(null);
        info.initStyle(StageStyle.UTILITY);
        info.setContentText("No se puede usar la misma contraseña");
        info.show();
    }
    
    /**
     * Método que actualiza la contraseña del usuario.
     * 
     * @param user Nombre del usuario actual
     */
    void recibirUser(String user) {
    	System.out.println(user);
    	this.user = user;
    }
    
    /**
     * Método que inicializa la ventana.
     */
    @FXML
    void initialize() {
    	
    	labelError.setVisible(false);
    	textFechaAccesp.setText(new Date().toString());
    	conect();
    	
    }

}
