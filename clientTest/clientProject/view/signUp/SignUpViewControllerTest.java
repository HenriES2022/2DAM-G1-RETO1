/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package clientProject.view.signUp;

import clientProject.Main;
import java.util.concurrent.TimeoutException;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.junit.Assert;
import org.testfx.framework.junit.ApplicationTest;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.api.FxToolkit;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.NodeMatchers.isInvisible;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.testfx.matcher.control.TextInputControlMatchers.hasText;

/**
 *
 * @author yeguo
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SignUpViewControllerTest extends ApplicationTest {

    private static TextField txtFullName;
    private static TextField txtUsername;
    private static TextField txtEmail;
    private static PasswordField txtPassword;
    private static PasswordField txtConfirmPassword;
    private static Button btnSignUp;
    private static Button btnBack;
    private static Label txtFullNameError;
    private static Label txtUsernameError;
    private static Label txtEmailError;
    private static Label txtPasswordError;
    private static Label txtPasswordConfirmError;

    /**
     * Start the application test for the sign Up window
     *
     * @throws TimeoutException is thrown by the method
     * {@link FxToolkit#registerPrimaryStage()} and
     * {@link FxToolkit#setupApplication()}
     */
    @BeforeClass
    public static void setUpClass() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(Main.class);
    }

    /**
     * Query the nodes of the window for later use
     */
    private void getFields() {
        // Text fields
        txtFullName = lookup("#txtFullName").query();
        txtUsername = lookup("#txtUsername").query();
        txtEmail = lookup("#txtEmail").query();
        txtPassword = lookup("#txtPassword").query();
        txtConfirmPassword = lookup("#txtConfirmPassword").query();
        // Labels
        txtFullNameError = lookup("#txtFullNameError").query();
        txtUsernameError = lookup("#txtUsernameError").query();
        txtEmailError = lookup("#txtEmailError").query();
        txtPasswordError = lookup("#txtPasswordError").query();
        txtPasswordConfirmError = lookup("#txtPasswordConfirmError").query();
        // Buttons
        btnSignUp = lookup("#btnSignUpView").query();
        btnBack = lookup("#btnBack").query();

    }

    /**
     * Test of initStage method, of class SignUpViewController.
     */
    @Test
    public void testA_initializationSignUp() {
        clickOn("#btnSignUpView");
        this.getFields();
        // Text fields
        verifyThat(txtFullName, hasText(""));
        verifyThat(txtFullName, (TextField t) -> t.isFocused());
        verifyThat(txtUsername, hasText(""));
        verifyThat(txtEmail, hasText(""));
        verifyThat(txtPassword, hasText(""));
        verifyThat(txtConfirmPassword, hasText(""));
        //Labels
        verifyThat(txtFullNameError, isInvisible());
        verifyThat(txtUsernameError, isInvisible());
        verifyThat(txtEmailError, isInvisible());
        verifyThat(txtPasswordError, isInvisible());
        verifyThat(txtPasswordConfirmError, isInvisible());
        // Buttons
        verifyThat(btnSignUp, isDisabled());
        verifyThat(btnBack, isEnabled());

    }

    /**
     * Test method of a not correct sign up case were the full name is not
     * correct
     */
    @Test
    public void testB_fullNameNotCorrect() {
        clickOn(txtFullName);
        //Text Fields
        write("Ioritz");
        clickOn(txtEmail);
        write("ioritz2002@hotmail.com");
        clickOn(txtUsername);
        write("ioritz");
        clickOn(txtPassword);
        write("Abcd?1234");
        clickOn(txtConfirmPassword);
        write("Abcd?1234");

        //Validating the text and labels
        Assert.assertNotEquals("", txtFullNameError.getText());
        Assert.assertNotEquals("", txtEmail.getText());
        Assert.assertNotEquals("", txtUsername.getText());
        Assert.assertNotEquals("", txtPassword.getText());
        Assert.assertNotEquals("", txtConfirmPassword.getText());

        verifyThat(txtFullNameError, isVisible());
        verifyThat(txtUsernameError, isInvisible());
        verifyThat(txtEmailError, isInvisible());
        verifyThat(txtPasswordError, isInvisible());
        verifyThat(txtPasswordConfirmError, isInvisible());

        //Buttons
        verifyThat(btnSignUp, isDisabled());
        verifyThat(btnBack, isEnabled());

    }

    /**
     * Test method of a not correct sign up case were the user name is not
     * correct
     */
    @Test
    public void testC_UsernameNotCorrect() {
        //Clearing the fields
        clickOn(txtConfirmPassword);
        eraseText(txtConfirmPassword.getText().length());

        clickOn(txtPassword);
        eraseText(txtPassword.getText().length());

        clickOn(txtUsername);
        eraseText(txtUsername.getText().length());

        clickOn(txtEmail);
        eraseText(txtEmail.getText().length());
        clickOn(txtEmail);
        eraseText(txtEmail.getText().length());

        clickOn(txtFullName);
        eraseText(txtFullName.getText().length());

        //Text Fields
        write("Ioritz Urkiri");
        clickOn(txtEmail);
        write("ioritz2002@hotmail.com");
        clickOn(txtUsername);
        write("ioritz??");
        clickOn(txtPassword);
        write("Abcd?1234");
        clickOn(txtConfirmPassword);
        write("Abcd?1234");

        //Validating the text and labels
        Assert.assertNotEquals("", txtFullNameError.getText());
        Assert.assertNotEquals("", txtEmail.getText());
        Assert.assertNotEquals("", txtUsername.getText());
        Assert.assertNotEquals("", txtPassword.getText());
        Assert.assertNotEquals("", txtConfirmPassword.getText());

        verifyThat(txtFullNameError, isInvisible());
        verifyThat(txtUsernameError, isVisible());
        verifyThat(txtEmailError, isInvisible());
        verifyThat(txtPasswordError, isInvisible());
        verifyThat(txtPasswordConfirmError, isInvisible());

        //Buttons
        verifyThat(btnSignUp, isDisabled());
        verifyThat(btnBack, isEnabled());

    }

    /**
     * Test method of a not correct sign up case were the email is not correct
     */
    @Test
    public void testD_EmailNotCorrect() {
        //Clearing the fields
        clickOn(txtConfirmPassword);
        eraseText(txtConfirmPassword.getText().length());

        clickOn(txtPassword);
        eraseText(txtPassword.getText().length());

        clickOn(txtUsername);
        eraseText(txtUsername.getText().length());

        clickOn(txtEmail);
        eraseText(txtEmail.getText().length());
        clickOn(txtEmail);
        eraseText(txtEmail.getText().length());

        clickOn(txtFullName);
        eraseText(txtFullName.getText().length());

        //Text Fields
        write("Ioritz Urkiri");
        clickOn(txtEmail);
        write("ioritz2002");
        clickOn(txtUsername);
        write("ioritz");
        clickOn(txtPassword);
        write("Abcd?1234");
        clickOn(txtConfirmPassword);
        write("Abcd?1234");

        //Validating the text and labels
        Assert.assertNotEquals("", txtFullNameError.getText());
        Assert.assertNotEquals("", txtEmail.getText());
        Assert.assertNotEquals("", txtUsername.getText());
        Assert.assertNotEquals("", txtPassword.getText());
        Assert.assertNotEquals("", txtConfirmPassword.getText());

        verifyThat(txtFullNameError, isInvisible());
        verifyThat(txtUsernameError, isInvisible());
        verifyThat(txtEmailError, isVisible());
        verifyThat(txtPasswordError, isInvisible());
        verifyThat(txtPasswordConfirmError, isInvisible());

        //Buttons
        verifyThat(btnSignUp, isDisabled());
        verifyThat(btnBack, isEnabled());

    }

    /**
     * Test method of a not correct sign up case were the password is not
     * correct
     */
    @Test
    public void testE_PasswordNotCorrect() {
        //Clearing the fields
        clickOn(txtConfirmPassword);
        eraseText(txtConfirmPassword.getText().length());

        clickOn(txtPassword);
        eraseText(txtPassword.getText().length());

        clickOn(txtUsername);
        eraseText(txtUsername.getText().length());

        clickOn(txtEmail);
        eraseText(txtEmail.getText().length());

        clickOn(txtFullName);
        eraseText(txtFullName.getText().length());

        //Text Fields
        write("Ioritz Urkiri");
        clickOn(txtEmail);
        write("ioritz2002@hotmail.com");
        clickOn(txtUsername);
        write("ioritz");
        clickOn(txtPassword);
        write("Abcd1234");
        clickOn(txtConfirmPassword);
        write("Abcd1234");

        //Validating the text and labels
        Assert.assertNotEquals("", txtFullNameError.getText());
        Assert.assertNotEquals("", txtEmail.getText());
        Assert.assertNotEquals("", txtUsername.getText());
        Assert.assertNotEquals("", txtPassword.getText());
        Assert.assertNotEquals("", txtConfirmPassword.getText());

        verifyThat(txtFullNameError, isInvisible());
        verifyThat(txtUsernameError, isInvisible());
        verifyThat(txtEmailError, isInvisible());
        verifyThat(txtPasswordError, isVisible());
        verifyThat(txtPasswordConfirmError, isInvisible());

        //Buttons
        verifyThat(btnSignUp, isDisabled());
        verifyThat(btnBack, isEnabled());

    }

    /**
     * Test method of a not correct sign up case were the password confirmation
     * is not correct
     */
    @Test
    public void testF_PasswordConfirmationNotCorrect() {
        //Clearing the fields
        clickOn(txtConfirmPassword);
        eraseText(txtConfirmPassword.getText().length());

        clickOn(txtPassword);
        eraseText(txtPassword.getText().length());

        //Text Fields
        write("Abcd?1234");
        clickOn(txtConfirmPassword);
        write("Abcd1234");

        //Validating the text and labels
        Assert.assertNotEquals("", txtFullNameError.getText());
        Assert.assertNotEquals("", txtEmail.getText());
        Assert.assertNotEquals("", txtUsername.getText());
        Assert.assertNotEquals("", txtPassword.getText());
        Assert.assertNotEquals("", txtConfirmPassword.getText());

        verifyThat(txtFullNameError, isInvisible());
        verifyThat(txtUsernameError, isInvisible());
        verifyThat(txtEmailError, isInvisible());
        verifyThat(txtPasswordError, isInvisible());
        verifyThat(txtPasswordConfirmError, isVisible());

        //Buttons
        verifyThat(btnSignUp, isDisabled());
        verifyThat(btnBack, isEnabled());

    }

    /**
     * Test method of a correct sign up cas
     */
    @Test
    public void testG_correctSignUp() {
        //Clearing the fields
        clickOn(txtConfirmPassword);
        eraseText(txtConfirmPassword.getText().length());

        clickOn(txtPassword);
        eraseText(txtPassword.getText().length());

        clickOn(txtUsername);
        eraseText(txtUsername.getText().length());

        clickOn(txtEmail);
        eraseText(txtEmail.getText().length());
        clickOn(txtEmail);
        eraseText(txtEmail.getText().length());

        clickOn(txtFullName);
        eraseText(txtFullName.getText().length());

        //Text Fields
        write("Ioritz Urkiri");
        clickOn(txtEmail);
        write("ioritz2002@hotmail.com");
        clickOn(txtUsername);
        write("ioritz");
        clickOn(txtPassword);
        write("Abcd?1234");
        clickOn(txtConfirmPassword);
        write("Abcd?1234");

        //Validating the text and labels
        Assert.assertNotEquals("", txtFullNameError.getText());
        Assert.assertNotEquals("", txtEmail.getText());
        Assert.assertNotEquals("", txtUsername.getText());
        Assert.assertNotEquals("", txtPassword.getText());
        Assert.assertNotEquals("", txtConfirmPassword.getText());

        verifyThat(txtFullNameError, isInvisible());
        verifyThat(txtUsernameError, isInvisible());
        verifyThat(txtEmailError, isInvisible());
        verifyThat(txtPasswordError, isInvisible());
        verifyThat(txtPasswordConfirmError, isInvisible());

        //Buttons
        verifyThat(btnSignUp, isEnabled());
        verifyThat(btnBack, isEnabled());

        clickOn(btnSignUp);
        verifyThat("Aceptar", isVisible());
        clickOn("Aceptar");
    }
}
