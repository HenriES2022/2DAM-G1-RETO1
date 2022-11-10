/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientProject.view.signIn;

import clientProject.view.logged.LoggedViewController;
import clientProject.view.signUp.SignUpViewController;
import clientProject.logic.ClientSocket;
import enumerations.Operation;
import exceptions.IncorrectLoginException;
import exceptions.ServerErrorException;
import exceptions.ServerFullException;
import java.io.IOException;
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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Message;
import model.User;

/**
 * FXML Controller class
 *
 * @author Joritz
 * @author Henrique
 */
public class SignInViewController {

    private static final Logger LOG = Logger.getLogger("vista.SignIn.SignInViewController");
    private Pattern pattern = null;
    private Matcher matcher = null;
    private Boolean usernameFilled = false;
    private Boolean passwordFilled = false;
    private final Tooltip userTooltip = new Tooltip();
    private final Tooltip passTooltip = new Tooltip();
    private final Double offset = 35d;
    private Stage primaryStage;
    private ClientSocket clientSocket;
    private String css = null;

    @FXML
    private TextField txtUser;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Button btnSignIn;
    @FXML
    private Button btnSignUpView;
    @FXML
    private Label txtLogInError;

    public void initStage(Parent root, ClientSocket clientSocket, String css) {
        LOG.info("Initiating Sign In View stage");
        this.css = css;
        
        Scene scene = new Scene(root);
        scene.getStylesheets().add(css);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Iniciar Sesion");
        this.primaryStage = stage;
        this.clientSocket = clientSocket;

        LOG.info("Setting validator for the username field");
        txtUser.textProperty().addListener((Observable) -> {
            try {
                String error;
                if (txtUser.getText().length() == 0) {
                    userTooltip.hide();
                    throw new Exception("El nombre de usuario no puede estar vacío");
                } else if (txtUser.getText().length() < 5) {
                    error = "El nombre de usuario debe tener mínimo 5 carácteres ";
                    showTooltip(stage, txtUser, error, userTooltip);
                    throw new Exception(error);
                } else if (txtUser.getText().length() > 50) {
                    txtUser.setText(txtUser.getText().substring(0, 50));
                    error = "El nombre de usuario no puede tener más de 50 carácteres";
                    showTooltip(stage, txtUser, error, userTooltip);
                    throw new Exception(error);
                }
                userTooltip.hide();
                usernameFilled = true;

                btnSignIn.setDisable(!(passwordFilled && usernameFilled));

            } catch (Exception ex) {
                //LOG.info(ex.getMessage());
                btnSignIn.setDisable(true);
                usernameFilled = false;
            }

        });

        LOG.info("Setting validator for the password field");
        txtPassword.textProperty().addListener((Observable) -> {
            try {
                String error;

                if (txtPassword.getText().length() == 0) {
                    passTooltip.hide();
                    throw new Exception("La contraseña no puede estar vacío");
                } else if (txtPassword.getText().length() < 8) {
                    error = "La contraseña debe tener mínimo 8 carácteres";
                    showTooltip(stage, txtPassword, error, passTooltip);
                    throw new Exception(error);
                } else if (txtPassword.getText().length() > 100) {
                    txtPassword.setText(txtPassword.getText().substring(0, 100));
                    error = "La contraseña no puede tener más de 100 carácteres";
                    showTooltip(stage, txtPassword, error, passTooltip);
                    throw new Exception(error);
                }
                passTooltip.hide();
                passwordFilled = true;

                btnSignIn.setDisable(!(usernameFilled && passwordFilled));

            } catch (Exception e) {
                //LOG.info(e.getMessage());
                btnSignIn.setDisable(true);
                passwordFilled = false;
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
            btnSignIn.setDefaultButton(true);
            btnSignUpView.setDisable(false);
            txtLogInError.setVisible(false);
            txtLogInError.setText("LogIn incorrecto, usuario y/o contraseña incorrecto");
            stage.setResizable(false);

        });

        btnSignIn.setOnAction(this::signIn);
        btnSignUpView.setOnAction((Event) -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../signUp/SignUpView.fxml"));
                Parent rootSignUp = (Parent) loader.load();
                SignUpViewController signUp = ((SignUpViewController) loader.getController());
                signUp.initStage(rootSignUp, stage, this.clientSocket, css);
            } catch (IOException ex) {
                LOG.info("No se puede abrir la ventana " + ex.getLocalizedMessage());
            }
        });
        stage.show();
    }

    /**
     *
     *
     */
    private void signIn(ActionEvent e) {
        try {
            LOG.info("Starting the sign in and getting data from required fields");
            User user = new User();

            user.setLogin(txtUser.getText());
            user.setPassword(txtPassword.getText());

            Message message = new Message();
            message.setUserData(user);
            message.setOperation(Operation.SING_IN);

            String error = "Login incorrecto, compruebe el usuario y/o la contraseña";

            String usernamePattern = "^[a-zA-Z0-9]+$";
            pattern = Pattern.compile(usernamePattern);
            matcher = pattern.matcher(txtUser.getText());

            if (!matcher.matches()) {
                LOG.info(error);
                throw new IncorrectLoginException(error);
            }

            String PASSWORD_PATTERN
                    = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!¡@#$%&¿?]).{8,100}$";
            pattern = Pattern.compile(PASSWORD_PATTERN);
            matcher = pattern.matcher(txtPassword.getText());

            if (!matcher.matches()) {
                LOG.info("La contraseña no es válida, debe tener al menos una mayuscula, una minuscula, un número y un caracter especial");
                throw new IncorrectLoginException(error);
            }

            message = clientSocket.connectToServer(message);

            if (message.getOperation().equals(Operation.LOGIN_ERROR)) {
                throw new IncorrectLoginException("Login Incorrecto, compruebe el usuario y/o la contraseña");
            } else if (message.getOperation().equals(Operation.OK)) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../logged/LoggedView.fxml"));
                Parent root = (Parent) loader.load();
                LoggedViewController controller = ((LoggedViewController) loader.getController());
                controller.initStage(root, message.getUserData(), primaryStage, this.css);
                txtUser.setText("");
                txtPassword.setText("");
            }
        } catch (ServerErrorException | ServerFullException ex) {
            LOG.severe(ex.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK);
            alert.showAndWait();
        } catch (IncorrectLoginException ex) {
            LOG.warning(ex.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK);
            alert.showAndWait();
        } catch (IOException ex) {
            LOG.severe(ex.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR, "Ha ocurrido un error al iniciar la ventana de inicio de sesión", ButtonType.OK);
            alert.showAndWait();
        }
    }

    private void showTooltip(Stage stage, TextField txtFfield, String text, Tooltip tp) {

        tp.setText(text);

        txtFfield.setTooltip(tp);
        tp.setAutoHide(true);

        tp.show(stage,
                txtFfield.getLayoutX() + txtFfield.getScene().getX() + txtFfield.getScene().getWindow().getX(),
                txtFfield.getLayoutY() + txtFfield.getScene().getY() + txtFfield.getScene().getWindow().getY() + offset);
    }

}
