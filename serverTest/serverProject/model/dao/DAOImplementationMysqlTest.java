/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package serverProject.model.dao;

import enumerations.Operation;
import exceptions.IncorrectLoginException;
import exceptions.UserAlreadyExistsException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Message;
import model.User;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import exceptions.ServerErrorException;
import java.math.BigInteger;
import java.security.*;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.FixMethodOrder;
import org.junit.Ignore;

import serverProject.model.database.DB;
import serverProject.model.database.DBFactory;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * The naming in this test is test + "letter" + what it does, I added a letter
 * so the {@code @FixMethodOrder} annotation with
 * {@link MethodSorters}.Name_ASCENDING works as intended
 *
 * @author yeguo
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DAOImplementationMysqlTest {

    private static Connection con;
    private Message msg;
    private static DAOImplementationMysql dao;
    private static String username;
    private static String passwd;
    private static DB poolImpl;
    private static final String URL = ResourceBundle.getBundle("serverProject.configServer").getString("url");
    private static final String USER = ResourceBundle.getBundle("serverProject.configServer").getString("user");
    private static final String PASS = ResourceBundle.getBundle("serverProject.configServer").getString("pass");
    private static final Logger LOG = Logger.getLogger("serverProject.model.dao.DAOImplementationMysqlTest");

    private static final String DATA_TEST = "insert into user(login, email, full_name, user_password) values('user1', 'user1@example.com', 'User pepe', MD5('password1234'))";

    /**
     * BeforeClass opens the connection to the database
     */
    @BeforeClass
    public static void beforeClass() {
        poolImpl = DBFactory.getDB();
        username = "user1";
        passwd = "Password1234?";
        try {
            con = poolImpl.getConnection();
            dao = new DAOImplementationMysql();

            PreparedStatement stat = con.prepareStatement(DATA_TEST);
            stat.executeUpdate();
        } catch (SQLException e) {
            LOG.severe(e.getMessage());
        }

    }

    /**
     * AfterClass closes the connection to the database
     */
    @AfterClass
    public static void afterClass() {
        if (con != null) {
            try {

                PreparedStatement stat = con.prepareCall("DELETE FROM USER WHERE login = 'user1'");
                stat.executeUpdate();

                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(DAOImplementationMysqlTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Test of signIn method, of class DAOImplementationMysql. Testing a normal
     * signIn with correct data and asserting the Message if its okay
     *
     */
    @Test

    public void testASignInOK() {
        try {
            User userLogin = new User();
            userLogin.setLogin(username);
            userLogin.setPassword(passwd);
            msg = dao.signIn(userLogin);

            User msgUser = msg.getUserData();
            if (!msgUser.getLogin().equals(username) && !msgUser.getPassword().equals(getMd5(passwd))) {
                fail("Statement didn't retrieve the correct data");
            }
            assertTrue(Operation.OK.equals(msg.getOperation()));

        } catch (IncorrectLoginException | ServerErrorException ex) {
            //LOG.severe(ex.getMessage());
            fail("Statement throwed an exception");
        }
    }

    /**
     * Test of signIn method, with incorrect username and password, testing both
     * because with any incorrect parameter it will throw the
     * {@code IncorrecLoginException}
     *
     * @throws exceptions.IncorrectLoginException
     */
    @Test(expected = IncorrectLoginException.class)

    public void testBSignInIncorrectUserPass() throws IncorrectLoginException {
        try {
            User userTest = new User();
            userTest.setLogin("awa");
            userTest.setPassword("uwu");

            Message signInDAO = dao.signIn(userTest);
        } catch (ServerErrorException ex) {
            LOG.severe(ex.getMessage());
        }
    }

    /**
     * Test of signIn method, with the connection to the database closed.
     *
     * @throws exceptions.ServerErrorException
     */
    @Test(expected = ServerErrorException.class)

    public void testCSignInServerError() throws ServerErrorException {
        try {
            con.close();
            Message signInDAO = dao.signIn(null);
        } catch (IncorrectLoginException ex) {
            Logger.getLogger(DAOImplementationMysqlTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DAOImplementationMysqlTest.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            con = poolImpl.getConnection();
            dao = new DAOImplementationMysql(con);
        }

    }

    /**
     * Test of signUp method with correct data.
     *
     */
    @Test
    public void testDSignUp() {
        String login = "user2";
        String email = "hola@example.com";
        String fullname = "patimicola";
        String password = "xdddd";
        try {
            User user = new User();
            user.setLogin(login);
            user.setEmail(email);
            user.setFullName(fullname);
            user.setPassword(password);
            msg = dao.signUp(user);

            assertTrue(Operation.OK.equals(msg.getOperation()));
        } catch (ServerErrorException | UserAlreadyExistsException e) {
            LOG.severe(e.getMessage());
        }
    }

    /**
     * Test of signUp method with a user that already exists
     *
     * @throws UserAlreadyExistsException if user exists
     */
    @Test(expected = UserAlreadyExistsException.class)
    public void testESignUpExists() throws UserAlreadyExistsException {
        String login = "user2";
        try {
            User user = new User();
            user.setLogin(login);
            msg = dao.signUp(user);

        } catch (ServerErrorException e) {
            LOG.severe(e.getMessage());
        } finally {
            try (PreparedStatement deleteUser = con.prepareStatement("delete from user where login = ?")) {
                deleteUser.setString(1, login);
                deleteUser.executeUpdate();
            } catch (Exception e) {
                LOG.severe(e.getMessage());
            }

        }
    }

    /**
     * Test of signUp method with the connection of the database closed.
     *
     * @throws ServerErrorException if server is closed
     */
    @Test(expected = ServerErrorException.class)
    public void testFSignUpServerError() throws ServerErrorException {
        try {
            con.close();
            Message signInDAO = dao.signUp(null);
        } catch (SQLException | UserAlreadyExistsException ex) {
            Logger.getLogger(DAOImplementationMysqlTest.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            con = poolImpl.getConnection();
            dao = new DAOImplementationMysql(con);
        }
    }

    /**
     * This method is only used to convert the password to a MD5 hash, to
     * compare with the password retrieved from the sign in
     *
     * @param input
     */
    private static String getMd5(String input) {
        try {

            // Static getInstance method is called with hashing MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // digest() method is called to calculate message digest
            // of an input digest() return array of byte
            byte[] messageDigest = md.digest(input.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

}
