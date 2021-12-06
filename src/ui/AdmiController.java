package ui;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.StageStyle;
import model.Conexion;

/**
 * Clase controladora de la ventana del administrador.
 */
public class AdmiController {
	
	 public static final String PATH = "img/admi.png";

    @FXML
    private Button buttonDelete;

    @FXML
    private Button buttonSetPassword;

    @FXML
    private ComboBox<String> comboBoxUsers;

    @FXML
    private ImageView imageAdmi;
    
    private Connection con;
    
    private PreparedStatement ps;

    /**
     * Método que elimina a un usuario.
     * 
     * @param event Cuando se presiona el botón eliminar
     * 
     * @throws NullPointerException
     * @throws SQLException
     */
    @FXML
    void deleteUser(ActionEvent event) {
    	try {
    		if(comboBoxUsers.getValue().equals("")) {
    			throw new NullPointerException();
    		}
    		String delete = "DELETE FROM usuario WHERE userName = '" + comboBoxUsers.getValue() + "'";
    		Statement st =con.createStatement();
            st.execute(delete);
            updateCombo();
    	} catch (NullPointerException npe){
    		alert();
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    /**
     * Método que elimina la contraseña de un usuario.
     * 
     * @param event Cuando se presiona el botón setPassword
     * 
     * @throws NullPointerException
     * @throws SQLException
     */
    @FXML
    void setUserPassword(ActionEvent event) {
    	try {
        		if(comboBoxUsers.getValue().equals("")) {
        			throw new NullPointerException();
        		}
    		String setPass = "Update usuario set password = " + "''" + " where userName= '" + comboBoxUsers.getValue() + "'";
    		Statement st =con.createStatement();
            st.execute(setPass);
    	} catch (NullPointerException npe){
    		alert();
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
        info.setContentText("No ha escogido un usuario");
        info.show();
    }
    
    /**
     * Método que conecta la aplicación a la base de datos.
     * 
     * @throws IOException
     */
    void conect() {
    	String consulta = "SELECT userName FROM usuario WHERE privilege = 'normal'";
    	comboBoxUsers.getItems().clear();
    	try {
			con= Conexion.obtenerConexion();
			ps = con.prepareStatement(consulta);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				String user = rs.getString("userName");
				comboBoxUsers.getItems().add(user);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    /**
     * Método que actualiza la lista de usuarios.
     * 
     * @throws SQLException
     */
    void updateCombo() {
    	String consulta = "SELECT userName FROM usuario WHERE privilege = 'normal'";
    	comboBoxUsers.getItems().clear();
    	try {
			ps = con.prepareStatement(consulta);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				String user = rs.getString("userName");
				comboBoxUsers.getItems().add(user);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    }
    
    /**
     * Método que inicializa la ventana.
     */
    @FXML
    void initialize() {
		conect();
    	
    }
    

}
