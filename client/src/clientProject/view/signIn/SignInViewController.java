/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientProject.view.signIn;

import clientProject.view.signUp.SignUpViewController;
import enumerations.Operation;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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

        LOG.info("Validating the username field");
        txtUser.textProperty().addListener((Observable) -> {
            try {
                String usernamePattern = "^(?=.*[a-z])(?=.*[A-Z]).{0,50}$";
                pattern.compile(usernamePattern);
                matcher = pattern.matcher(txtUser.getText());
                if (txtUser.getText().length() > 50) {
                    throw new Exception("El nombre de usuario no puede tener mas de 50 caracteres");
                } else if (txtUser.getText().length() == 0) {
                    throw new Exception("El campo no puede estar vacio");
                }

                correctUserName = true;
            } catch (Exception ex) {
                btnSignIn.setDisable(true);
            }
            if (correctUserName && correctPassword) {
                btnSignIn.setDisable(false);
            }
        });

        LOG.info("Validating the password field");
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
            } catch (Exception ex) {
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
