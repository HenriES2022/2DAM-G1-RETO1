/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientProject.view.signIn;

import clientProject.Main;
import java.util.concurrent.TimeoutException;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.testfx.matcher.control.TextInputControlMatchers.hasText;

/**
 *
 * @author ioritz
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SignInViewControllerTest extends ApplicationTest {

    private TextField txtUser;
    private PasswordField txtPassword;
    private Button btnSignIn;
    private Button btnSignUp;
    private Button btnAceptar;
    private Label txtUserLogged;

    /**
     * Start the application test for the sign In window
     *
     * @throws TimeoutException is thrown by the method
     *
     */
    @BeforeClass
    public static void setUpClass() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(Main.class);
    }

    /**
     * Method to obtain the components to use in the test
     */
    private void getFields() {
        //Text fields
        txtUser = lookup("#txtUser").query();
        txtPassword = lookup("#txtPassword").query();
        btnSignIn = lookup("#btnSignIn").query();
        btnSignUp = lookup("#btnSignUpView").query();
    }

    /**
     * Test of initStage method, of class SignInViewController.
     */
    @Test
    public void testA_InitStage() {
        this.getFields();

        //Verifying the textFields
        verifyThat(txtUser, hasText(""));
        verifyThat(txtPassword, hasText(""));

        //Buttons
        verifyThat(btnSignIn, isDisabled());
        verifyThat(btnSignUp, isEnabled());
    }

    /**
     * Test to open the Sign Up window
     */
    @Test
    public void testB_SignUpView() {
        this.getFields();

        verifyThat(btnSignUp, isEnabled());
        clickOn(btnSignUp);

        try {
            btnAceptar = lookup("Aceptar").query();
            if (btnAceptar.isVisible()) {
                clickOn(btnAceptar);
            } else {
                clickOn("Volver");
            }
        } catch (NullPointerException e) {
            clickOn("Volver");
        }
    }

    /**
     * Test that checks button SignIn as disable if the fields are empty
     */
    @Test
    public void testC_SignInDisable() {
        this.getFields();
        clickOn(txtUser);
        write("Mondongo");

        verifyThat(btnSignIn, isDisabled());
        eraseText(8);

        clickOn(txtPassword);
        write("Abcd?1234");

        verifyThat(btnSignIn, isDisabled());
        eraseText(9);

        verifyThat(btnSignIn, isDisabled());
    }

    /**
     * Test that checks button SignIn as enable if the fields are full
     */
    @Test
    public void testD_SignInEnable() {
        this.getFields();

        clickOn(txtUser);
        write("Mondongo");

        clickOn(txtPassword);
        write("Abcd?1234");

        verifyThat(btnSignIn, isEnabled());

        clickOn(txtUser);
        eraseText(8);

        clickOn(txtPassword);
        eraseText(9);
    }

    /**
     * Test of the SignIn window with incorrect user
     */
    @Test
    public void testE_IncorrectUser() {
        this.getFields();
        clickOn(txtUser);
        write("ioritz??");

        clickOn(txtPassword);
        write("Abcd?1234");

        clickOn(btnSignIn);
        verifyThat(btnSignIn, isEnabled());
        verifyThat("Aceptar", isVisible());
        clickOn("Aceptar");
    }

    /**
     * Test of the SignIn window with incorrect password
     */
    @Test
    public void testF_IncorrectPasswords() {
        this.getFields();
        clickOn(txtUser);
        eraseText(txtUser.getText().length());
        write("ioritz");

        clickOn(txtPassword);
        eraseText(txtPassword.getText().length());
        write("Abcd1234");

        clickOn(btnSignIn);
        verifyThat(btnSignIn, isEnabled());
        verifyThat("Aceptar", isVisible());
        clickOn("Aceptar");

        clickOn(txtUser);
        eraseText(txtUser.getText().length());

        clickOn(txtPassword);
        eraseText(txtPassword.getText().length());
    }

    /**
     * Test of the SignIn window with correct data
     */
    @Test
    public void testG_correctLoginFields() {
        this.getFields();

        clickOn(txtUser);
        write("ioritz");

        clickOn(txtPassword);
        write("Abcd?1234");

        verifyThat(btnSignIn, isEnabled());
        clickOn(btnSignIn);

        try {
            btnAceptar = lookup("Aceptar").query();
            if (btnAceptar.isVisible()) {
                clickOn(btnAceptar);
            } else {
                clickOn("Cerrar Sesion");
            }
        } catch (NullPointerException e) {
            clickOn("Cerrar Sesion");
        }
    }

    /**
     * Test that check the user length
     */
    @Test
    public void testH_UserLength() {
        this.getFields();
        clickOn(txtPassword);
        write("Abcd?1234");

        clickOn(txtUser);
        write("hola");

        verifyThat(btnSignIn, isDisabled());

        clickOn(txtUser);
        eraseText(4);
        write("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

        verifyThat(btnSignIn, isDisabled());
        eraseText(txtUser.getText().length());

        clickOn(txtPassword);
        eraseText(txtPassword.getText().length());
    }

    /**
     * Test that check the user length
     */
    @Test
    public void testI_PasswordLength() {
        this.getFields();
        clickOn(txtUser);
        write("ioritz");

        clickOn(txtPassword);
        write("Abc123");

        verifyThat(btnSignIn, isDisabled());

        eraseText(6);
        write("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

        verifyThat(btnSignIn, isDisabled());
    }
}
