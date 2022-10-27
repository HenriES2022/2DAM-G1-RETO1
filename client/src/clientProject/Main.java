/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientProject;

import clientProject.view.signIn.SignInViewController;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

/**
 *
 * @author 2dam
 */
public class Main extends Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            primaryStage.hide();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("view/signIn/SignInView.fxml"));
            Parent root = (Parent) loader.load();
            SignInViewController controller = ((SignInViewController) loader.getController());
            controller.initStage(root);
            controller.setStage(primaryStage);
            primaryStage.show();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
