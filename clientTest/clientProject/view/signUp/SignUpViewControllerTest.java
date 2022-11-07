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
import static org.testfx.matcher.control.ButtonMatchers.isCancelButton;
import static org.testfx.matcher.control.ButtonMatchers.isDefaultButton;
import static org.testfx.matcher.control.ComboBoxMatchers.hasSelectedItem;
import static org.testfx.matcher.control.TextInputControlMatchers.hasText;

/**
 *
 * @author yeguo
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SignUpViewControllerTest extends ApplicationTest {

    private TextField txtFullName;
    private TextField txtUsername;
    private TextField txtEmail;
    private PasswordField txtPassword;
    private PasswordField txtConfirmPassword;
    private Button btnSignUp;
    private Button btnBack;
    private Label txtFullNameError;
    private Label txtUsernameError;
    private Label txtEmailError;
    private Label txtPasswordError;
    private Label txtPasswordConfirmError;

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
        btnSignUp = lookup("#btnSignUp").query();
        btnBack = lookup("#btnBack").query();

    }

    /**
     * Test of initStage method, of class SignUpViewController.
     */
    @Test
    public void testA_initializationSignUp() {
        clickOn("#btnSignUp");
        this.getFields();
        // Text fields
        verifyThat("#txtFullName", hasText(""));
        verifyThat("#txtFullName", (TextField t) -> t.isFocused());
        verifyThat("#txtUsername", hasText(""));
        verifyThat("#txtEmail", hasText(""));
        verifyThat("#txtPassword", hasText(""));
        verifyThat("#txtConfirmPassword", hasText(""));
        //Labels
        verifyThat("#txtFullNameError", isInvisible());
        verifyThat("#txtUsernameError", isInvisible());
        verifyThat("#txtEmailError", isInvisible());
        verifyThat("#txtPasswordError", isInvisible());
        verifyThat("#txtPasswordConfirmError", isInvisible());
        // Buttons
        verifyThat("#btnSignUp", isDisabled());
        verifyThat("#btnBack", isEnabled());
    }

}
