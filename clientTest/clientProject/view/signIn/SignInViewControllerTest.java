/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientProject.view.signIn;

import clientProject.Main;
import java.util.concurrent.TimeoutException;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.control.TextInputControlMatchers.hasText;

/**
 *
 * @author iorit
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SignInViewControllerTest extends ApplicationTest {

    private static SignInViewController myController = null;
    private static Parent root = null;
    private static Scene scene = null;
    private TextField txtUser;
    private PasswordField txtPassword;
    private Button btnSignIn;
    private Button btnSignUp;

    /**
     * Start the application test for the sign In window
     * @throws TimeoutException is thrown by the method
     * 
     */
    @BeforeClass
    public static void setUpClass() throws TimeoutException{
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(Main.class);
    }

    /**
     * Method to obtain the components to use in the test
     */
    private void getFields(){
        //Text fields
        txtUser = lookup("#txtUser").query();
        txtPassword = lookup("#txtPassword").query();
        btnSignIn = lookup("#btnSignIn").query();
        btnSignUp = lookup("#btnSignUp").query();
    }
    
    /**
     * Test of initStage method, of class SignInViewController.
     */
    @Test
    public void testA_InitStage() {
        this.getFields();
        
        //Verifying the textFields
        verifyThat(txtUser, hasText(""));
        verifyThat(txtUser, (t) -> t.isFocused());
        verifyThat(txtPassword, hasText(""));
        
        //Buttons
        verifyThat(btnSignIn, isDisabled());
        verifyThat(btnSignUp, isEnabled());
    }
    
    /**
     * Test of the SignUp window with correct data
     */
    @Test
    public void testB_correctLoginFields(){
        this.getFields();
        
        write("ioritz2002");
        clickOn(txtPassword);
        write("Abcd?1234");
        
        verifyThat(btnSignIn, isEnabled());
    }
    
    /**
     * Test of the SignUp window with incorrect user and password
     */
    @Test
    public void testC_IncorrectUserAndPassword(){
        this.getFields();
        clickOn(txtUser);
        eraseText(txtUser.getText().length());
        write("ioritz??");
        
        clickOn(txtPassword);
        eraseText(txtPassword.getText().length());
        write("Abcd1234");
        
        verifyThat(btnSignIn, isDisabled());
    }
}
