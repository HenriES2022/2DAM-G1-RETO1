/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientProject.view.signIn;

import clientProject.logic.ClientSocket;
import clientProject.view.signUp.SignUpViewController;
import enumerations.Operation;
import exceptions.IncorrectLoginException;
import exceptions.ServerErrorException;
import exceptions.ServerFullException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Message;
import model.User;

/**
 * FXML Controller class
 *
 * @author Joritz
 */
public class SignInViewController {

    private static final Logger LOG = Logger.getLogger("vista.SignIn.SignInViewController");
    private Pattern pattern = null;
    private Matcher matcher = null;
    private Boolean correctUserName = false;
    private Boolean correctPassword = false;
    private ClientSocket clientSocket;

    @FXML
    private TextField txtUser;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Button btnSignIn;
    @FXML
    private Button btnSignUp;

    public void initStage(Parent root) {
        LOG.info("Initiating Sign In View stage");
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Iniciar Sesion");

        LOG.info("Setting validator for the username field");
        txtUser.textProperty().addListener((Observable) -> {
            try {
                String usernamePattern = "^(?=.*[a-z])(?=.*[A-Z])$";
                Pattern.compile(usernamePattern);
                matcher = pattern.matcher(txtUser.getText());
                if (txtUser.getText().length() > 50) {
                    txtUser.setText(txtUser.getText().substring(0, 50));
                    throw new Exception("El nombre de usuario no puede tener mas de 50 caracteres");
                } else if (txtUser.getText().length() == 0) {
                    throw new Exception("El campo no puede estar vacio");
                } else if (!matcher.matches()) {
                    throw new Exception("El nombre de usuario no puede tener carácteres especiales");
                }
                correctUserName = true;
            } catch (Exception ex) {
                btnSignIn.setDisable(true);
            }
            if (correctUserName && correctPassword) {
                btnSignIn.setDisable(false);
            }
        });

        LOG.info("Setting validaro the password field");
        txtPassword.textProperty().addListener((Observable) -> {
            try {
                correctPassword = passwordValidator(txtPassword.getText());
            } catch (Exception e) {
                btnSignIn.setDisable(true);
                correctPassword = false;
            }
            if (correctUserName && correctPassword) {
                btnSignIn.setDisable(false);
            }
        });

        stage.setOnCloseRequest((WindowEvent WindowEvent) -> {
            LOG.info("Opening exit alert confirmation");
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "¿Quieres cerrar el programa?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                Platform.exit();
            } else {
                WindowEvent.consume();
            }
        });

        stage.setOnShowing((Event) -> {
            LOG.info("Setting the status of the items shown on scene");
            txtUser.setDisable(false);
            txtPassword.setDisable(false);
            btnSignIn.setDisable(true);
            btnSignUp.setDisable(false);
            stage.setResizable(false);
        });

        btnSignIn.setOnAction(this::signIn);
        btnSignUp.setOnAction((Event) -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../signUp/SignUpView.fxml"));
                Parent rootSignUp = (Parent) loader.load();
                SignUpViewController signUp = ((SignUpViewController) loader.getController());
                signUp.initStage(rootSignUp, stage);
            } catch (IOException ex) {
                LOG.info("No se puede abrir la ventana " + ex.getLocalizedMessage());
            }
        });
        stage.show();
    }

    private void signIn(ActionEvent e) {
        LOG.info("Starting the sign in and looking for all equired objects");
        User user = new User();

        user.setFullName(txtUser.getText());
        user.setPassword(txtPassword.getText());

        Message message = new Message();
        message.setUserData(user);
        message.setOperation(Operation.SING_IN);

        try {
            message = clientSocket.connectToServer(message);
            if (message.getOperation().equals(Operation.LOGIN_ERROR)) {
                throw new IncorrectLoginException("Login Incorrecto, compruebe el usuario y/o la contraseña");
            } else if (message.getOperation().equals(Operation.OK)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Usuario loggeado correctamente", ButtonType.OK);
                alert.showAndWait();
            }
        } catch (ServerErrorException ex) {
            LOG.severe(ex.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error al conectarse con el servidor, intentelo de nuevo mas tarde", ButtonType.OK);
            alert.showAndWait();
        } catch (ServerFullException ex) {
            Logger.getLogger(SignInViewController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IncorrectLoginException ex) {
            LOG.warning(ex.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR, "Login Incorrecto, compruebe el usuario y/o la contraseña", ButtonType.OK);
            alert.showAndWait();
        }
    }

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
}
