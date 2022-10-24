/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.signUp;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.Observable;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ioritz
 */
public class SignUpViewController {

   // private static final Logger logger = Logger.getLogger(type);
    
    @FXML
    private TextField txtFullName;
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtEmail;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Button btnSignUp;
    @FXML
    private Button btnBack;
    
    private Stage myStage = null;
    private Scene myScene = null;
    
    public void initStage(Parent root){
        myScene = new Scene(root);
        myStage = new Stage();
        
        myStage.setTitle("Registro");
        myStage.setScene(myScene);
        myStage.setResizable(false);
        myStage.showAndWait();
        
        btnSignUp.setDisable(true);
        btnBack.setDisable(false);
        
        txtFullName.focusedProperty().addListener((Observable focusChanged) -> {
            validateText();
        });
    }

    private void validateText() {
        
    }
    
}
