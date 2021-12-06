package ui;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.PasswordGenerator;

/**
 * Clase main de la aplicación.
 */
public class Main extends Application{
	
	

    public static Stage stage;

    /**
     * Método que imprime las contraseñas cifradas de los usuarios.
     * 
     * @param args Argumentos del programa
     */
	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException {
        System.out.println(PasswordGenerator.generateStrongPasswordHash("123456"));
        System.out.println(PasswordGenerator.generateStrongPasswordHash("654321"));
        System.out.println(PasswordGenerator.generateStrongPasswordHash("123123"));
        System.out.println(PasswordGenerator.generateStrongPasswordHash("456456"));
        
		launch(args);
        
    }

	 /**
     * Método que inicia la ventana login.
     * 
     * @param stage
     */
    @Override
    public void start(Stage stage) throws Exception {
    	
    	Main.stage= new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));

        Scene scene = new Scene(root);
        Main.stage.setTitle("Login Security");
        Main.stage.setScene(scene);
        Main.stage.show();
    }
}