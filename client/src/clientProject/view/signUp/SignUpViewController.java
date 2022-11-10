/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientProject.view.signUp;

import clientProject.logic.ClientSocket;
import enumerations.Operation;
import exceptions.ServerErrorException;
import exceptions.ServerFullException;
import exceptions.UserAlreadyExistsException;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
    private static Alert alert = null;
    private Pattern pattern = null;
    private Matcher matcher = null;
    private static final Logger LOG = Logger.getLogger("clientProject.view.signUp.SignUpViewController.class");
    private ClientSocket clientSocket;
    private Stage primaryStage = null;

    /**
     * This method starts the Sign Up window
     *
     * @param root The scene that is going to be loaded in the stage
     * @param primaryStage
     */
    public void initStage(Parent root, Stage primaryStage, ClientSocket clientSocket, String css) {
        LOG.info("Starting the window and setting the components on the screen");
        myScene = new Scene(root);
        myScene.getStylesheets().add(css);
        myStage = new Stage();
        primaryStage.hide();
        this.clientSocket = clientSocket;
        this.primaryStage = primaryStage;
        
        myStage.setOnShowing((event) -> {
            myStage.setTitle("Registro");
            myStage.setScene(myScene);
            myStage.setResizable(false);
            myStage.initModality(Modality.WINDOW_MODAL);

            btnSignUp.setDisable(true);
            btnSignUp.setDefaultButton(true);
            btnBack.setDisable(false);
            txtFullName.requestFocus();

            txtEmailError.setVisible(false);
            txtFullNameError.setVisible(false);
            txtPasswordError.setVisible(false);
            txtUsernameError.setVisible(false);
            txtPasswordConfirmError.setVisible(false);
        });

        LOG.info("Setting validator for the full name field");
        txtFullName.textProperty().addListener((Observable) -> {
            try {
                txtFullNameError.setVisible(false);

                String fullNamePattern
                        = "^([A-Z][a-z]*((\\s)))+[A-Z][a-z]*$";
                String fullName = txtFullName.getText();

                pattern = Pattern.compile(fullNamePattern);
                matcher = pattern.matcher(fullName);

                if (fullName.length() == 0 || fullName.length() < 5) {
                    throw new Exception("Este campo debe de tener mínimo 5 carácteres");
                } else if (fullName.length() > 100) {
                    txtFullName.setText(txtFullName.getText().substring(0, 100));
                    throw new Exception("Este campo no puede contener más de 100 carácteres");
                } else if (!matcher.matches()) {
                    throw new Exception("El nombre y apellido deben de estar separados con un espacio,"
                            + "\ncontener solo carácteres desde A-Z, y la primera letra en mayúscula ");
                }
                correctFullName = true;
                btnSignUp.setDisable(!(correctFullName && correctEmail && correctPassword && correctUserName && correctPasswordConfirmation));

            } catch (Exception e) {
                LOG.warning(e.getMessage());
                btnSignUp.setDisable(true);
                txtFullNameError.setVisible(true);
                txtFullNameError.setText(e.getMessage());
                correctFullName = false;
            }
        });

        LOG.info("Setting validator for the email field");
        txtEmail.textProperty().addListener((Observable) -> {
            try {
                txtEmailError.setVisible(false);

                //Validating that the text field is not empty
                if (txtEmail.getText().length() == 0) {
                    throw new Exception("El campo no puede estar vacio");
                } else if (txtEmail.getText().length() > 100) {
                    txtFullName.setText(txtFullName.getText().substring(0, 100));
                    throw new Exception("El campo no puede tener más de 100 carácteres");
                }
                correctEmail = emailValidator(txtEmail.getText());
                //if the email is not correct, show an error message
                if (!correctEmail) {
                    throw new Exception("El email no es correcto\nEj: usuario@ejemplo.com");
                }
                btnSignUp.setDisable(!(correctFullName && correctEmail && correctPassword && correctUserName && correctPasswordConfirmation));

            } catch (Exception e) {
                LOG.warning(e.getMessage());
                btnSignUp.setDisable(true);
                txtEmailError.setVisible(true);
                txtEmailError.setText(e.getMessage());
                correctEmail = false;
            }

        }
        );

        LOG.info("Setting validator for the username field");
        txtUsername.textProperty().addListener((Observable) -> {
            try {
                txtUsernameError.setVisible(false);
                String usernamePattern = "^[a-zA-Z0-9]+$";

                pattern = Pattern.compile(usernamePattern);
                matcher = pattern.matcher(txtUsername.getText());

                if (txtUsername.getText().length() < 5) {
                    throw new Exception("El nombre de usuario debe tener mínimo 5 carácteres");
                } else if (txtUsername.getText().length() > 50) {
                    txtUsername.setText(txtUsername.getText().substring(0, 50));
                    throw new Exception("El nombre de usuario no puede tener mas de 50 caracteres");
                } else if (!matcher.matches()) {
                    throw new Exception("El nombre de usuario solo puede tener carácteres alfanuméricos");
                }
                correctUserName = true;
                btnSignUp.setDisable(!(correctFullName && correctEmail && correctPassword && correctUserName && correctPasswordConfirmation));

            } catch (Exception e) {
                LOG.warning(e.getMessage());
                btnSignUp.setDisable(true);
                txtUsernameError.setVisible(true);
                txtUsernameError.setText(e.getMessage());
                correctUserName = false;
            }
        }
        );

        LOG.info("Setting validator for the password field");
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

            btnSignUp.setDisable(!(correctFullName && correctEmail && correctPassword && correctUserName && correctPasswordConfirmation));
        }
        );

        LOG.info("Setting validator to check both the password and the confirmation is the same");
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
                btnSignUp.setDisable(!(correctFullName && correctEmail && correctPassword && correctUserName && correctPasswordConfirmation));

            } catch (Exception e) {
                txtPasswordConfirmError.setVisible(true);
                txtPasswordConfirmError.setText(e.getMessage());
                correctPasswordConfirmation = false;
                btnSignUp.setDisable(true);
            }

        }
        );

        btnBack.setOnAction(
                (ActionEvent) -> {
                    LOG.info("Closing the window");
                    myStage.close();
                    primaryStage.show();
                }
        );

        btnSignUp.setOnAction(this::signUp);

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
     * This method validates that the introduced email is syntactically correct
     *
     * @param email The email we are going to check
     * @return Returns True if the email matches with the pattern, False if
     * doesn't match
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
     * @throws Exception if the password length is less than 8 characters or if
     * the password is not correct
     */
    private Boolean passwordValidator(String password) throws Exception {
        String PASSWORD_PATTERN
                = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!¡@#$%&¿?]).{8,100}$";

        if (password.length() < 8) {
            throw new Exception("La contraseña debe de tener al menos 8 caracteres");
        } else if (password.length() > 100) {
            throw new Exception("La contraseña no puede tener más de 100 carácteres");
        } else {
            pattern = Pattern.compile(PASSWORD_PATTERN);
            matcher = pattern.matcher(password);

            if (matcher.matches()) {
                return true;
            }
        }
        throw new Exception("La contraseña no es válida, debe tener al menos una mayúscula, \n una minúscula, un número y un carácter especial");
    }

    /**
     * This method sends de user to the server to register it
     *
     * @param event the event that method is going to be launched by
     */
    private void signUp(ActionEvent event) {
        LOG.info("Starting the sign up and setting up all equired objects");
        Message message;
        User user;

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

            switch (message.getOperation()) {
                case USER_EXISTS:
                    throw new UserAlreadyExistsException("El usuario ya existe, pruebe con otro");
                case OK:
                    alert = new Alert(Alert.AlertType.CONFIRMATION, "El usuario ha sido registrado correctamente", ButtonType.OK);
                    alert.showAndWait();
                    LOG.info("Se ha registrado el usuario correctamente. Saliendo al anterior ventana...");
                    this.myStage.close();
                    this.primaryStage.show();
                    break;
                case SERVER_FULL:
                    throw new ServerFullException("El servdor esta lleno, intentelo mas tarde");
            }
        } catch (ServerErrorException e) {
            LOG.severe(e.getMessage());
            alert = new Alert(Alert.AlertType.ERROR, "Error al conectarse con el servidor, intentelo de nuevo mas tarde", ButtonType.OK);
            alert.showAndWait();
        } catch (UserAlreadyExistsException ex) {
            LOG.warning(ex.getMessage());
            alert = new Alert(Alert.AlertType.ERROR, "El usuario ya existe, pruebe con otro", ButtonType.OK);
            alert.showAndWait();

        } catch (ServerFullException ex) {
            LOG.severe(ex.getMessage());
            alert = new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
    }
}
