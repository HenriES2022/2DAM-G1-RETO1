/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientProjectTest.controllerTest;

import clientProject.view.signUp.SignUpViewController;
import javafx.scene.Parent;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author GAME
 */
public class SignUpViewControllerTest {
    private static SignUpViewController myController = null;
    
    public SignUpViewControllerTest() {
        assertNotNull("Controller must not be null", myController);
    }
    
    @BeforeClass
    public static void setUpClass() {
        myController = new SignUpViewController();
    }

    /**
     * Test of initStage method, of class SignUpViewController.
     */
    @Test
    public void testInitStage() {
        System.out.println("initStage");
        Parent root = null;
        SignUpViewController instance = new SignUpViewController();
        instance.initStage(root);
        fail("The test case is a prototype.");
    }
    
}
