/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientProject.view.signUp;

import enumerations.Operation;
import java.util.logging.Logger;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Message;
import model.User;

/**
 * FXML Controller class
 * This is the SignUpView scene controller 
 * @author ioritz
 */
public class SignUpViewController {

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
    private Boolean correctFullName = false;
    private Boolean correctUserName = false;
    /*private ClientSocketFactory myFactory;
    private ClientSocket clientSocket;*/
    private static Alert alert = null;
    private static final Logger LOG = Logger.getLogger("clientProject.view.signUp.SignUpViewController.class");

    /**
     * This method startes the Sign Up window
     *
     * @param root The scene that is going to be loaded in the stage
     */
    public void initStage(Parent root) {
        LOG.info("Starting the window and setting the components on the screen");
        myScene = new Scene(root);
        myStage = new Stage();

        myStage.setTitle("Registro");
        myStage.setScene(myScene);
        myStage.setResizable(false);
        myStage.initModality(Modality.WINDOW_MODAL);

        btnSignUp.setDisable(true);
        btnBack.setDisable(false);

        txtFullName.requestFocus();

        txtFullName.focusedProperty().addListener((Observable focusChanged) -> {
            validateText(txtFullName);
        });

        txtEmail.focusedProperty().addListener((Observable focusChanged) -> {
            validateText(txtEmail);
        });

        txtUsername.focusedProperty().addListener((Observable focusChanged) -> {
            validateText(txtUsername);
        });

        txtPassword.focusedProperty().addListener((Observable focusChanged) -> {
            validateText(txtPassword);
        });
        btnBack.setOnAction((ActionEvent) -> {
            LOG.info("Closing the window");
            myStage.close();
        });

        btnSignUp.setOnAction((ActionEvent) -> {
            signUp();
        });

        myStage.setOnCloseRequest((WindowEvent windowEvent) -> {
            LOG.info("Opening exit alert confirmation");
            alert = new Alert(Alert.AlertType.CONFIRMATION, "Quieres cerrar el programa?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();

            if (alert.getResult().equals(ButtonType.YES)) {
                LOG.info("Closing the application");
                Platform.exit();
            } else {
                LOG.info("Canceled application close");
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
        LOG.info("Validating the fields isn't empty");
        if (txtEmail.getText().trim().equalsIgnoreCase("")
                || txtPassword.getText().trim().equalsIgnoreCase("")
                || txtFullName.getText().trim().equalsIgnoreCase("")
                || txtUsername.getText().trim().equalsIgnoreCase("")) {

            btnSignUp.setDisable(true);
        }

        if (field.equals(txtFullName)) {
            LOG.info("Validating the full name field");
            if (field.getText().length() > 100) {
                btnSignUp.setDisable(true);
                alert = new Alert(Alert.AlertType.ERROR, "El nombre completo no puede tener mas de 100 caracteres");
                alert.showAndWait();
                txtFullName.setText("");
                correctFullName = false;
            } else if (field.getText().length() == 0) {
                correctFullName = false;
            } else {
                correctFullName = true;
            }

        } else if (field.equals(txtPassword)) {
            LOG.info("Validating the password field");
            if (field.getText().length() > 0) {
                correctPassword = passwordValidator(field.getText());
                if (!correctPassword) {
                    btnSignUp.setDisable(true);
                    txtPassword.setText("");
                }
            } else {
                correctPassword = false;
            }

        } else if (field.equals(txtEmail)) {
            LOG.info("Validationg the email field");

            if (field.getText().length() > 0) {
                correctEmail = emailValidator(field.getText());
                if (!correctEmail) {
                    alert = new Alert(Alert.AlertType.ERROR, "El email no es correcto", ButtonType.OK);
                    alert.showAndWait();
                    txtEmail.setText("");
                }
            } else {
                btnSignUp.setDisable(true);
            }

        } else if (field.equals(txtUsername)) {
            LOG.info("Validating the username field");
            if (field.getText().length() > 50) {
                btnSignUp.setDisable(true);
                alert = new Alert(Alert.AlertType.ERROR, "El nombre de usuario debe de tener menos de 50 caracteres", ButtonType.OK);
                alert.showAndWait();

                txtUsername.setText("");
                correctUserName = false;
            } else if (field.getText().length() == 0) {
                correctUserName = false;
            } else {
                correctUserName = true;
            }

        }
        if (correctFullName && correctEmail && correctPassword && correctUserName) {
            btnSignUp.setDisable(false);
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
        String passwordWithOutNumber = null;
        String passwordNumbers = "0123456789";
        String passwordWithOutSpecialCharacters = null;
        char[] passwordLowerCase;
        char[] passwordUpperCase;
        String specialCharacters = "!¡@#$%&¿?";
        boolean containsUpperCase = false;
        boolean containsLowerCase = false;
        boolean containsSpecialCharacters = false;
        boolean containsNumber = false;
        if (password.length() < 8) {
            alert = new Alert(Alert.AlertType.ERROR, "La contraseña debe de tener al mentos 8 caracteres", ButtonType.OK);
            alert.showAndWait();
        } else {
            passwordWithOutNumber = loadPasswordWithowNumbers(password, passwordNumbers);
            passwordWithOutSpecialCharacters = loadPasswordWithoutSpecialCharacters(passwordWithOutNumber, specialCharacters);

            passwordLowerCase = passwordWithOutSpecialCharacters.toLowerCase().toCharArray();
            passwordUpperCase = passwordWithOutSpecialCharacters.toUpperCase().toCharArray();

            containsSpecialCharacters = passwordHasSpecialCharacters(passwordWithOutNumber, specialCharacters);
            containsNumber = passwordHasNumbers(password.toLowerCase(), passwordNumbers.toLowerCase());
            containsUpperCase = passwordHasUpperCase(passwordWithOutSpecialCharacters, passwordUpperCase);
            containsLowerCase = passwordHasLowerCase(passwordWithOutSpecialCharacters, passwordLowerCase);

            if (containsLowerCase && containsNumber && containsSpecialCharacters && containsUpperCase) {
                return true;
            } else {
                alert = new Alert(Alert.AlertType.ERROR, "La contraseña tiene que tener como minimo una letra mayuscula, una minuscula y un caracter especial", ButtonType.OK);
                alert.showAndWait();
            }
        }

        return false;
    }

    /**
     * This method sends de user to the server to register it
     */
    private void signUp() {
        LOG.info("Starting the sign up and setting up all equired objects");
        Message message = null;
        User user = null;
        Operation operation = null;

        LOG.info("Setting up the required variables");
        //clientSocket = myFactory.getImplementation();
        user = new User();
        user.setFullName(txtFullName.getText());
        user.setEmail(txtEmail.getText());
        user.setPassword(txtPassword.getText());
        user.setLogin(txtUsername.getText());

        operation = Operation.SING_UP;

        message = new Message();
        message.setUserData(user);
        message.setOperation(operation);

        //try {
        /*message = clientSocket.connectToServer(message);

            operation = message.getOperation();

            if (operation.equals(Operation.USER_EXISTS)) {
                alert = new Alert(Alert.AlertType.ERROR, "El usuario ya existe, pruebe con otro", ButtonType.OK);
                alert.showAndWait();
                LOG.warning("The user attemped to sign up all ready exists");
            } else if (operation.equals(Operation.OK)) {
                alert = new Alert(Alert.AlertType.INFORMATION, "El usuario ha sido registrado correctamente", ButtonType.OK);
                alert.showAndWait();
                LOG.info("The sign up has be done correctly. Exiting method...");
            }*/
        System.out.println(user.getEmail());
        /*} catch (ServerErrorException e) {
            LOG.severe(e.getMessage());
            alert = new Alert(Alert.AlertType.ERROR, "Error al conectarse con el servidor, intentelo de nuevo mas tarde", ButtonType.OK);
            alert.showAndWait();
        }*/

    }
    /**
     * This method recovers an string with the passworn without the numbers 
     * @param password The password that has the numbers we are going to remove
     * @param passwordNumbers An array with the numers to check if the password has one of them
     * @return Returns a empty string if the original password has not numbers, a string with the password without numbers <br>
     * if the originial password has numbers
     */
    private String loadPasswordWithowNumbers(String password, String passwordNumbers) {
        String pass = "";
        Boolean isNumber;

        for (int i = 0; i < password.length(); i++) {
            isNumber = false;
            for (int j = 0; j < passwordNumbers.length(); j++) {
                if (password.charAt(i) == passwordNumbers.charAt(j)) {
                    isNumber = true;
                }
            }
            if (!isNumber) {
                pass = pass + password.charAt(i);
            }
        }

        return pass;
    }

    /**
     * This method obtains if the password has numbers or not
     * @param password The password were we are going to search for numbers
     * @param passwordNumbers An array with the numers to check if the password has one of them
     * @return Returns true if the password has numbers, false if has not
     */
    private boolean passwordHasNumbers(String password, String passwordNumbers) {
        for (int i = 0; i < password.length(); i++) {
            for (int j = 0; j < passwordNumbers.length(); j++) {
                if (password.charAt(i) == passwordNumbers.charAt(j)) {
                    return true;
                }
            }
        }

        return false;
    }
    /**
     * This method search if the password has any special character
     * @param passwordWithOutNumber The string with the password with out numbers
     * @param specialCharacters The array with the special characters to searh if the password has one<br>
     * of this character
     * @return Returns true if the password has any speciall character, false if has not
     */
    private boolean passwordHasSpecialCharacters(String passwordWithOutNumber, String specialCharacters) {
        String characters = specialCharacters.toString();
        for (int i = 0; i < passwordWithOutNumber.length(); i++) {
            for (int j = 0; j < characters.length(); j++) {
                if (passwordWithOutNumber.charAt(i) == characters.charAt(j)) {
                    return true;
                }
            }
        }

        return false;
    }
    /**
     * This method obtains the password without any special character from a password with out numbers
     * @param passwordWithOutNumber The string with the password without numbers
     * @param specialCharacters The string with the special characters to search if the password has any of these
     * @return Returns the password with out any special characters, or a empty string if the password has not any <br>
     * speciall character
     */
    private String loadPasswordWithoutSpecialCharacters(String passwordWithOutNumber, String specialCharacters) {
        String pass = "";
        boolean isSpecial;

        for (int i = 0; i < passwordWithOutNumber.length(); i++) {
            isSpecial = false;
            for (int j = 0; j < specialCharacters.length(); j++) {
                if (passwordWithOutNumber.charAt(i) == specialCharacters.charAt(j)) {
                    isSpecial = true;
                }

            }
            if (!isSpecial) {
                pass = pass + passwordWithOutNumber.charAt(i);
            }
        }

        return pass;
    }

    /**
     * This method check if the password has any lower case character
     * @param passwordWithOutSpecialCharacters The password with out special characters were we are going to search
     * @param passwordLowerCase An array with the password with out special characters in lower case to compare
     * @return Returns true if there is any character in lower case, false if there is not any
     */
    private boolean passwordHasLowerCase(String passwordWithOutSpecialCharacters, char[] passwordLowerCase) {
        for (int i = 0; i < passwordWithOutSpecialCharacters.length(); i++) {
            if (passwordWithOutSpecialCharacters.charAt(i) == passwordLowerCase[i]) {
                return true;
            }
        }
        
        return false;
    }
    /**
     * This methods checks if the password has any upper case character
     * @param passwordWithOutSpecialCharacters The password with out special characters were we are going to search
     * @param passwordUpperCase An array with the password with out special characters in upper case to compare
     * @return Returns true if there is any character in upper case, false if there is not any
     */
    private boolean passwordHasUpperCase(String passwordWithOutSpecialCharacters, char[] passwordUpperCase) {
        for (int i = 0; i < passwordWithOutSpecialCharacters.length(); i++) {
            if (passwordWithOutSpecialCharacters.charAt(i) == passwordUpperCase[i]) {
                return true;
            }
        }
        
        return false;
    }
}
