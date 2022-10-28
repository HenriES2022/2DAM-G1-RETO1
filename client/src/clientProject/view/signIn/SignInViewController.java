/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientProject.view.signIn;

import clientProject.view.signUp.SignUpViewController;
import enumerations.Operation;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
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
        txtUser.textProperty().addListener(this::campChanges);
        txtPassword.textProperty().addListener(this::campChanges);

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
        btnSignUp.setOnAction(this::signUp);
        stage.show();
    }

    private void campChanges(ObservableValue observable,
            String oldValue,
            String newValue) {
        if (this.txtUser.getText().trim().isEmpty() || this.txtPassword.getText().trim().isEmpty()) {
            btnSignIn.setDisable(true);
        } else {
            btnSignIn.setDisable(false);
        }
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

    private void signUp(ActionEvent e) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../signUp/SignUpView.fxml"));
            Parent rootSignUp = (Parent) loader.load();
            SignUpViewController signUp = ((SignUpViewController) loader.getController());
            signUp.initStage(rootSignUp);
        } catch (Exception ex) {
            LOG.info("No se puede abrir la ventana " + ex.getLocalizedMessage());
        }
    }
}
