/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package clientProject;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import model.User;
import clientProject.view.logged.LoggedViewController;
import javafx.fxml.FXMLLoader;

/**
 *
 * @author yeguo
 */
public class TestLoggedView extends Application {

    @Override
    public void start(Stage primaryStage) {
        
        Button btn = new Button();
        btn.setText("Test login");
        primaryStage.resizableProperty().setValue(false);
        btn.setOnAction((ActionEvent ActionEvent) -> {
            try {
                primaryStage.hide();
                User user = new User();
                user.setLogin("Hola");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("view/logged/LoggedView.fxml"));
                Parent root = (Parent) loader.load();
                LoggedViewController controller = ((LoggedViewController) loader.getController());
                controller.initStage(root, user);
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
