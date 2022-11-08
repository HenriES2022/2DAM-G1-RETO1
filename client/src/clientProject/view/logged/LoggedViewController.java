/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package clientProject.view.logged;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.User;

/**
 * FXML Controller class
 *
 * @author yeguo
 */
public class LoggedViewController {

    private static final Logger LOG = Logger.getLogger("view.logged.LoggedViewController");

    @FXML
    private Label txtSaludo;
    @FXML
    private Button btnLogOut;
    @FXML
    private Label txtUser;

    /**
     * This method initializes the stage {@code LoggedView}
     *
     * @param root The parent stage for this view
     * @param user Object user who logged in
     */
    public void initStage(Parent root, User user, Stage primaryStage) {
        LOG.info("Initiating Logged View stage");

        primaryStage.hide();

        // Create scene associated with the parent scene
        Scene scene = new Scene(root);

        // Sets the window properties
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Bienvenido");
        stage.initModality(Modality.WINDOW_MODAL);

        // Set confirm alert when clicking the window X button
        stage.setOnCloseRequest((WindowEvent WindowEvent) -> {
            LOG.info("Opening exit alert confirmation");
            Alert alert = new Alert(AlertType.CONFIRMATION, "¿Quieres cerrar el programa?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                Platform.exit();
            } else {
                WindowEvent.consume();
            }
        });

        // On showing stage
        stage.setOnShowing((event) -> {
            LOG.info("Setting the status of the items shown on scene");
            btnLogOut.setDisable(false);
            stage.setResizable(false);
            txtUser.setText(user.getFullName());
            btnLogOut.setDefaultButton(true);
        });
        btnLogOut.setOnAction((ActionEvent) -> {
            stage.close();
            primaryStage.show();

        });
        stage.showAndWait();
    }

}
