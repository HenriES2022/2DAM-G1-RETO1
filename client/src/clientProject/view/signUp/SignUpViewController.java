/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientProject.view.signUp;

import enumerations.Operation;
import exceptions.ServerErrorException;
import exceptions.ServerFullException;
import exceptions.UserAlreadyExistsException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
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
    private ClientSocketFactory myFactory;
    private ClientSocket clientSocket;
    private static Alert alert = null;
    private Pattern pattern = null;
    private Matcher matcher = null;
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

        LOG.info("Validating the full name field");
        txtFullName.textProperty().addListener((Observable) -> {
            try {
                String fullNamePattern
                        = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[\\u0020]).{0,100}$";
                String fullName = txtFullName.getText();

                pattern = Pattern.compile(fullNamePattern);
                matcher = pattern.matcher(fullName);
                if (txtFullName.getText().length() == 0) {
                    throw new Exception("El nombre no puede estar vacio");
                }
                if (!matcher.matches()) {
                    throw new Exception("El nombre tiene que tener el formato de Nombre, un espacio y apellido, comenzando con mayusculas \n y con menos de 100 caracteres");
                }
                correctFullName = true;
                txtFullNameError.setVisible(false);

                if (correctFullName && correctEmail && correctPassword && correctUserName && correctPasswordConfirmation) {
                    btnSignUp.setDisable(false);
                }
            } catch (Exception e) {
                LOG.warning(e.getMessage());
                btnSignUp.setDisable(true);
                txtFullNameError.setVisible(true);
                txtFullNameError.setText(e.getMessage());
                correctFullName = false;
            }
        });
        LOG.info("Validationg the email field");
        txtEmail.textProperty().addListener((Observable) -> {
            try {
                txtEmailError.setVisible(false);
                //Validating that the text field is not empty
                if (txtEmail.getText().length() == 0) {
                    throw new Exception("El campo no puede estar vacio");
                }
                correctEmail = emailValidator(txtEmail.getText());
                //if the email is not correct, show an error message
                if (!correctEmail) {
                    throw new Exception("El email no es correcto");
                }
            } catch (Exception e) {
                txtEmailError.setVisible(true);
                txtEmailError.setText(e.getMessage());
                btnSignUp.setDisable(true);
            }

            if (correctFullName && correctEmail && correctPassword && correctUserName && correctPasswordConfirmation) {
                btnSignUp.setDisable(false);
            }
        }
        );

        LOG.info("Validating the username field");
        txtUsername.textProperty().addListener((Observable) -> {
            try {
                txtUsernameError.setVisible(false);
                String usernamePattern = "^(?=.*[a-z])(?=.*[A-Z]).{0,50}$";
                pattern.compile(usernamePattern);
                matcher = pattern.matcher(txtUsername.getText()); //Validating if the text field has not more tha 50 characters
                if (txtUsername.getText().length() > 50) {
                    throw new Exception("El nombre de usuario no puede tener mas de 50 caracteres");

                } else if (txtUsername.getText().length() == 0) {
                    throw new Exception("El campo no puede estar vacio");
                }

                correctUserName = true;

            } catch (Exception e) {
                btnSignUp.setDisable(true);
                txtUsernameError.setVisible(true);
                txtUsernameError.setText(e.getMessage());
                correctUserName = false;
            }
            if (correctFullName && correctEmail && correctPassword && correctUserName && correctPasswordConfirmation) {
                btnSignUp.setDisable(false);
            }
        }
        );

        LOG.info("Validating the password field");
        txtPassword.textProperty().addListener((Observable) -> {
            try {
                txtPasswordError.setVisible(false);
                correctPassword = passwordValidator(txtPassword.getText());
            } catch (Exception e) {
                btnSignUp.setDisable(true);
                txtPasswordError.setVisible(true);
                txtPasswordError.setText(e.getMessage());
                correctPassword = false;
            }

            if (correctFullName && correctEmail && correctPassword && correctUserName && correctPasswordConfirmation) {
                btnSignUp.setDisable(false);
            }
        }
        );

        LOG.info("Validating that the password and the password confirmation is the same");
        txtConfirmPassword.textProperty().addListener((Observable) -> {
            try {
                txtPasswordConfirmError.setVisible(false);

                if (!txtConfirmPassword.getText().equals(txtPassword.getText())) {
                    throw new Exception("La contraseña no coincide");
                }
                if (txtConfirmPassword.getText().length() == 0) {
                    throw new Exception("El campo no puede estar vacio");
                }

                correctPasswordConfirmation = true;
            } catch (Exception e) {
                txtPasswordConfirmError.setVisible(true);
                txtPasswordConfirmError.setText(e.getMessage());
                correctPasswordConfirmation = false;
                btnSignUp.setDisable(true);
            }

            if (correctFullName && correctEmail && correctPassword && correctUserName && correctPasswordConfirmation) {
                btnSignUp.setDisable(false);
            }
        }
        );

        btnBack.setOnAction(
                (ActionEvent) -> {
                    LOG.info("Closing the window");
                    myStage.close();
                }
        );

        btnSignUp.setOnAction(
                this::signUp);

        //Atiende al evento de cerrar la ventana
        myStage.setOnCloseRequest(
                (WindowEvent windowEvent) -> {
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
                }
        );
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
     *@throws Exception if the password length is less than 8 characters or if the password is not correct
     */
    private Boolean passwordValidator(String password) throws Exception {
        String PASSWORD_PATTERN
                = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!¡@#$%&¿?]).{8,100}$";

        if (password.length() < 8) {
            throw new Exception("La contraseña debe de tener al mentos 8 caracteres");
        } else {
            pattern = Pattern.compile(PASSWORD_PATTERN);
            matcher = pattern.matcher(password);

            if (matcher.matches()) {
                return true;
            }
        }
        throw new Exception("La contraseña no es valida, debe tener al menos una mayuscula, \n una minuscula, un numero y un caracter especial");
    }

    /**
     * This method sends de user to the server to register it
     * @param event the event that method is going to be launched by
     */
    private void signUp(ActionEvent event) {
        LOG.info("Starting the sign up and setting up all equired objects");
        Message message = null;
        User user = null;

        LOG.info("Setting up the required variables");
        clientSocket = myFactory.getImplementation();
        user = new User();
        user.setFullName(txtFullName.getText());
        user.setEmail(txtEmail.getText());
        user.setPassword(txtPassword.getText());
        user.setLogin(txtUsername.getText().trim());

        message = new Message();
        message.setUserData(user);
        message.setOperation(Operation.SING_UP);

        try {
            message = clientSocket.connectToServer(message);

            if (message.getOperation().equals(Operation.USER_EXISTS)) {
                throw new UserAlreadyExistsException("El usuario ya existe, pruebe con otro");
            } else if (message.getOperation().equals(Operation.OK)) {
                alert = new Alert(Alert.AlertType.INFORMATION, "El usuario ha sido registrado correctamente", ButtonType.OK);
                alert.showAndWait();
                LOG.info("The sign up has be done correctly. Exiting method...");
            } else if (message.getOperation().equals(Operation.SERVER_FULL)) {
                throw new ServerFullException("El servdor esta lleno, intentelo mas tarde");
            }
        } catch (ServerErrorException e) {
            LOG.severe(e.getMessage());
            alert = new Alert(Alert.AlertType.ERROR, "Error al conectarse con el servidor, intentelo de nuevo mas tarde", ButtonType.OK);
            alert.showAndWait();
        } catch (UserAlreadyExistsException ex) {
            LOG.warning(ex.getMessage());
            alert  = new Alert(Alert.AlertType.ERROR, "El usuario ya existe, pruebe con otro", ButtonType.OK);
            alert.showAndWait();
            
        } catch (ServerFullException ex) {
            LOG.severe(ex.getMessage());
            alert = new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK);
        }
    }
}
