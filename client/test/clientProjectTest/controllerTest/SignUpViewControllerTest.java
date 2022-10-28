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
 * @author iorit
 */
public class SignUpViewControllerTest {
    
    public SignUpViewControllerTest() {
        SignUpViewController controller = new SignUpViewController();
        assertNotNull("The instance of the controller must not be null", controller);
    }
    
    @BeforeClass
    public static void setUpClass() {
        
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
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
