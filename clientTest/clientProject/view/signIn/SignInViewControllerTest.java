/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientProject.view.signIn;

import clientProject.view.signIn.SignInViewController;
import clientProject.view.signUp.SignUpViewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import org.junit.BeforeClass;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

/**
 *
 * @author iorit
 */
public class SignInViewControllerTest extends ApplicationTest {

    private static SignInViewController myController = null;
    private static Parent root = null;
    private static Scene scene = null;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/signIn/SignInView.fxml"));
        root = (Parent) loader.load();
        myController = ((SignInViewController) loader.getController());
        myController.initStage(root, clientProject.logic.ClientSocketFactory.getImplementation());
    }

    public SignInViewControllerTest() {
        assertNotNull("Controller must not be null", myController);
    }

    @BeforeClass
    public static void setUpClass() {
        FXMLLoader loader = new FXMLLoader();
        myController = new SignInViewController();

        Scene scene = new Scene(root);
    }

    /**
     * Test of initStage method, of class SignUpViewController.
     */
    @Test
    public void testInitStage() {

        fail("The test case is a prototype.");
    }
}
