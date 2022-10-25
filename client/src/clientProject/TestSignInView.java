/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientProject;

import clientProject.view.SignIn.SignInViewController;
import clientProject.view.logged.LoggedViewController;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import model.User;

/**
 *
 * @author 2dam
 */
public class TestSignInView extends Application {

    @Override
    public void start(Stage primaryStage) {
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        primaryStage.resizableProperty().setValue(false);
        btn.setOnAction((ActionEvent ActionEvent) -> {
            try {
                primaryStage.hide();
                User user = new User();
                user.setLogin("Hola");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("view/signIn/SignInView.fxml"));
                Parent root = (Parent) loader.load();
                SignInViewController controller = ((SignInViewController) loader.getController());
                controller.initStage(root);
                primaryStage.show();
            } catch (IOException ex) {
                Logger.getLogger(TestLoggedView.class.getName()).log(Level.SEVERE, null, ex);
            }

        });

        StackPane root = new StackPane();
        root.getChildren().add(btn);

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
