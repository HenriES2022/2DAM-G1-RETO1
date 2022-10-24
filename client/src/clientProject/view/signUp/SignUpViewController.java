/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientProject.view.signUp;

import com.sun.istack.internal.logging.Logger;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

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
    private Boolean correctEmail = false;
    private Boolean correctPassword = false;
    private static Alert alert = null;
    private static final Logger LOG = Logger.getLogger(clientProject.view.signUp.SignUpViewController.class);

    ;

    /**
     * This method startes the Sign Up window
     * @param root The scene that is going to be loaded in the stage
     */
    public void initStage(Parent root) {
        myScene = new Scene(root);
        myStage = new Stage();

        myStage.setTitle("Registro");
        myStage.setScene(myScene);
        myStage.setResizable(false);

        btnSignUp.setDisable(true);
        btnBack.setDisable(false);

        txtFullName.requestFocus();

        txtFullName.focusedProperty().addListener((Observable focusChanged) -> {
            validateText(txtFullName);
        });

        txtUsername.focusedProperty().addListener((Observable focusChanged) -> {
            validateText(txtUsername);
        });

        txtEmail.focusedProperty().addListener((Observable focusChanged) -> {
            validateText(txtEmail);
        });

        txtPassword.focusedProperty().addListener((Observable focusChanged) -> {
            validateText(txtPassword);
        });

        myStage.setOnCloseRequest((WindowEvent windowEvent) -> {
            LOG.info("Opening exit alert confirmation");
            alert = new Alert(Alert.AlertType.CONFIRMATION, "Quieres cerrar el programa?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                Platform.exit();
            } else {
                windowEvent.consume();
            }
        });
        myStage.showAndWait();
    }

    /**
     * This methods validates that the text in the text fields are correct
     *
     * @param field The text field that we are going to check
     */
    private void validateText(TextField field) {

        if (txtEmail.getText().trim().equalsIgnoreCase("")
                || txtPassword.getText().trim().equalsIgnoreCase("")
                || txtFullName.getText().trim().equalsIgnoreCase("")
                || txtUsername.getText().trim().equalsIgnoreCase("")) {

            btnSignUp.setDisable(true);
        }

        if (field.equals(txtFullName) && field.getText().length() > 100) {
            btnSignUp.setDisable(true);
            alert = new Alert(Alert.AlertType.ERROR, "El nombre completo no puede tener mas de 100 caracteres");
        }

        if (field.equals(txtPassword)) {
            correctPassword = passwordValidator(txtPassword.getText());
            btnSignUp.setDisable(true);
        }
        if (field.equals(txtEmail)) {
            correctEmail = emailValidator(txtEmail.getText());
            if (correctEmail) {

            }
        }

    }

    /**
     * This method validates that the introduced email is sintacticaly correct
     *
     * @param email The email we are going to check
     * @return Returns True if the email matches the pattern we use to check the
     * email, False if dosen't match
     */
    private Boolean emailValidator(String email) {
        if (email.length() <= 100) {
            return Pattern.compile("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                    + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")
                    .matcher(email)
                    .matches();
        }
        return false;
    }

    /**
     * this method validates that the password is correct
     *
     * @param password The password we are going to validate
     * @return Returns True if the password is correct, False if is not.
     */
    private Boolean passwordValidator(String password) {
        char[] passwordLowerCase = password.toLowerCase().toCharArray();
        char[] passwordUpperCase = password.toUpperCase().toCharArray();
        char[] specialCharacters = {'!', '¡', '@', '#', '$', '%', '&', '¿', '?', '"'};
        boolean containsUpperCase = false;
        boolean containsLowerCase = false;
        boolean containsSpecialCharacters = false;
        if (password.length() < 8) {
            alert = new Alert(Alert.AlertType.ERROR, "La contraseña debe de tener al mentos 8 caracteres", ButtonType.OK);
        } else {
            for (int i = 0; i < password.length(); i++) {
                if (password.charAt(i) == passwordUpperCase[i]) {
                    containsUpperCase = true;
                }
                if (password.charAt(i) == passwordLowerCase[i]) {
                    containsLowerCase = true;
                }

                for (int j = 0; j < specialCharacters.length; j++) {
                    if (password.charAt(i) == specialCharacters[j]) {
                        containsSpecialCharacters = true;
                    }
                }
            }

            if (containsSpecialCharacters && containsLowerCase && containsUpperCase) {
                return true;
            }
            alert = new Alert(Alert.AlertType.ERROR, "La contraseña tiene que tener como minimo una letra mayuscula, una minuscula y un caracter especial", ButtonType.OK);

        }
        return false;
    }

}
