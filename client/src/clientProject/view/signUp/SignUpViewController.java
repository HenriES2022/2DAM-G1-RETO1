/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientProject.view.signUp;

import enumerations.Operation;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Message;
import model.User;

/**
 * FXML Controller class This is the SignUpView scene controller
 *
 * @author ioritz
 */
public class SignUpViewController {

    private Stage stage = new Stage();

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
    @FXML
    private Label txtFullNameError;
    @FXML
    private Label txtUsernameError;
    @FXML
    private Label txtEmailError;
    @FXML
    private Label txtPasswordError;
    @FXML
    private CheckBox checkShowPassword;
    @FXML
    private Label txtPasswordConfirmError;
    @FXML
    private PasswordField txtConfirmPassword;

    private Stage myStage = null;
    private Scene myScene = null;
    private Boolean correctEmail = false;
    private Boolean correctPassword = false;
    private Boolean correctFullName = false;
    private Boolean correctUserName = false;
    private Boolean correctPasswordConfirmation = false;
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

        myStage.setOnShowing((event) -> {
            myStage.setTitle("Registro");
            myStage.setScene(myScene);
            myStage.setResizable(false);
            myStage.initModality(Modality.WINDOW_MODAL);

            btnSignUp.setDisable(true);
            btnBack.setDisable(false);
            txtFullName.requestFocus();

            txtEmailError.setText("");
            txtFullNameError.setText("");
            txtPasswordError.setText("");
            txtUsernameError.setText("");
            txtPasswordConfirmError.setText("");
        });

        txtFullName.textProperty().addListener((Observable) -> {
            LOG.info("Validating the full name field");
            
            //Validating that the text of the full name don't have more than 100 characters or the text isn't empty
            if (txtFullName.getText().length() > 100) {
                btnSignUp.setDisable(true);
                txtFullNameError.setVisible(true);
                txtFullNameError.setText("El nombre no puede tener mas de 100 caracteres");

                correctFullName = false;
            } else if (txtFullName.getText().length() == 0) {
                correctFullName = false;
                txtFullNameError.setVisible(false);
            } else {
                correctFullName = true;
                txtFullNameError.setVisible(false);
            }
            if (correctFullName && correctEmail && correctPassword && correctUserName && correctPasswordConfirmation) {
                btnSignUp.setDisable(false);
            }
        });

        txtEmail.textProperty().addListener((Observable) -> {
            LOG.info("Validationg the email field");
            
            //Validating that the text field is not empty
            if (txtEmail.getText().length() > 0) {
                correctEmail = emailValidator(txtEmail.getText());
                //if the email is not correct, show an error message
                if (!correctEmail) {
                    txtEmailError.setVisible(true);
                    txtEmailError.setText("El email no es correcto");
                    btnSignUp.setDisable(true);
                    //if the validation was correct dont show nothing
                } else {
                    txtEmailError.setVisible(false);
                }
            } else {
                btnSignUp.setDisable(true);
            }

            if (correctFullName && correctEmail && correctPassword && correctUserName && correctPasswordConfirmation) {
                btnSignUp.setDisable(false);
            }
        });

        txtUsername.textProperty().addListener((Observable) -> {
            LOG.info("Validating the username field");
            
            //Validating if the text field has not more tha 50 characters
            if (txtUsername.getText().length() > 50) {
                btnSignUp.setDisable(true);

                txtUsernameError.setVisible(true);
                txtUsernameError.setText("El nombre de usuario debe de tener menos de 50 caracteres");

                correctUserName = false;
               
            } 
            //If the text is empty
            else if (txtUsername.getText().length() == 0) {
                correctUserName = false;
                txtUsernameError.setVisible(false);
            } 
            //If the text is correct
            else {
                correctUserName = true;
                txtUsernameError.setVisible(false);
            }

            if (correctFullName && correctEmail && correctPassword && correctUserName && correctPasswordConfirmation) {
                btnSignUp.setDisable(false);
            }
        });

        txtPassword.textProperty().addListener((Observable) -> {
            LOG.info("Validating the password field");
            
            //Validating that the field is not empty
            if (txtPassword.getText().length() > 0) {
                correctPassword = passwordValidator(txtPassword.getText());
                //If the password is not correct
                if (!correctPassword) {
                    btnSignUp.setDisable(true);

                }
            } else {
                correctPassword = false;
            }

            if (correctFullName && correctEmail && correctPassword && correctUserName && correctPasswordConfirmation) {
                btnSignUp.setDisable(false);
            }
        });
        
        txtConfirmPassword.textProperty().addListener((Observable) -> {
            LOG.info("Validating that the password and the password confirmation is the same");
            if (txtConfirmPassword.getText().equals(txtPassword.getText())) {
                correctPasswordConfirmation = true;
                txtPasswordConfirmError.setVisible(false);
            }
            else if(txtConfirmPassword.getText().length() == 0){
                correctPasswordConfirmation = false;
                btnSignUp.setDisable(true);
                txtPasswordConfirmError.setVisible(false);
            }
            else{
                correctPasswordConfirmation = false;
                txtPasswordConfirmError.setText("La contraseña no coincide");
                txtPasswordConfirmError.setVisible(true);
                btnSignUp.setDisable(true);
            }
            
            if (correctFullName && correctEmail && correctPassword && correctUserName && correctPasswordConfirmation) {
                btnSignUp.setDisable(false);
            }
        });
        

        btnBack.setOnAction((ActionEvent) -> {
            LOG.info("Closing the window");
            myStage.close();
        });

        btnSignUp.setOnAction((ActionEvent) -> {
            signUp();
        });
        //Atiende al evento de cerrar la ventana
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
        String PASSWORD_PATTERN
                = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!¡@#$%&¿?]).{8,100}$";
        Pattern pattern = null;
        Matcher matcher = null;

        if (password.length() < 8) {
            txtPasswordError.setVisible(true);
            txtPasswordError.setText("La contraseña debe de tener al mentos 8 caracteres");
        } else {
            pattern = Pattern.compile(PASSWORD_PATTERN);
            matcher = pattern.matcher(password);

            if (matcher.matches()) {
                txtPasswordError.setVisible(false);
                return true;
            } else {
                txtPasswordError.setVisible(true);
                txtPasswordError.setText("La contrasela no es valida, debe tener al mentos una mayuscula,\n una minuscula, un numero y un caracter especial");
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
        /*this.validateText(txtEmail);
        this.validateText(txtFullName);
        this.validateText(txtUsername);
        this.validateText(txtPassword);*/

        LOG.info("Setting up the required variables");
        //clientSocket = myFactory.getImplementation();
        user = new User();
        user.setFullName(txtFullName.getText());
        user.setEmail(txtEmail.getText());
        user.setPassword(txtPassword.getText());
        user.setLogin(txtUsername.getText().trim());

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
        /*} catch (ServerErrorException e) {
            LOG.severe(e.getMessage());
            alert = new Alert(Alert.AlertType.ERROR, "Error al conectarse con el servidor, intentelo de nuevo mas tarde", ButtonType.OK);
            alert.showAndWait();
        }*/

    }
}
