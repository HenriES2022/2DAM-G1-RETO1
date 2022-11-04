/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientProject;

import clientProject.view.signUp.SignUpViewController;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author iorit
 */
public class TestSignUpView extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Button btn = new Button();
        btn.setText("Test signUp");
        primaryStage.resizableProperty().setValue(false);
        btn.setOnAction((ActionEvent ActionEvent) -> {
            try {
                primaryStage.hide();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("view/signUp/SignUpView.fxml"));
                Parent root = (Parent) loader.load();
                SignUpViewController controller = ((SignUpViewController) loader.getController());
                //controller.initStage(root);
                
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

    public static void main(String[] args) {
        launch(args);
    }

}
