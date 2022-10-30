/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package serverProject.model.dao;

import enumerations.Operation;
import enumerations.UserPrivilege;
import enumerations.UserStatus;
import exceptions.IncorrectLoginException;
import exceptions.ServerErrorException;
import exceptions.UserAlreadyExistsException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Message;
import model.User;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;
import exceptions.ServerErrorException;
import org.junit.Ignore;

/**
 *
 * @author yeguo
 */
public class DAOImplementationMysqlTest {

    private static Connection con;
    private Message msg;
    private static DAOImplementationMysql dao;
    private static String username;
    private static String passwd;
    private static final String URL = ResourceBundle.getBundle("serverProject.config").getString("url");
    private static final String USER = ResourceBundle.getBundle("serverProject.config").getString("user");
    private static final String PASS = ResourceBundle.getBundle("serverProject.config").getString("pass");
    private static final Logger LOG = Logger.getLogger("serverProject.model.dao.DAOImplementationMysqlTest");

    // Statement for the login
    private final static String SIGN_IN
            = "SELECT * FROM USER WHERE USER_PASSWORD = MD5(?) AND LOGIN = ?";

    // Statement selects the user's logging history
    private final static String SIGN_IN_HISTORY
            = "SELECT * FROM SIGNIN WHERE USER_ID = ? ORDER BY LAST_SIGIN ASC";

    // Statement adds sign in history
    private final static String SIGN_IN_HISTORY_ADD
            = "INSERT INTO SIGNIN(USER_ID) VALUES(?)";

    // Statement delete sign in history
    private final static String SIGN_IN_HISTORY_DEL
            = "DELETE FROM SIGNIN WHERE USER_ID = ? ORDER BY LAST_SIGIN ASC LIMIT 1";

    // Statement for checking if user has more than 10 logins
    private final static String SIGN_IN_HISTORY_CHECK
            = "SELECT IF(count(LAST_SIGIN)>= 10, 'true', 'false') FROM signin WHERE USER_ID = ?";

    @BeforeClass
    public static void beforeClass() {
        username = "user1";
        passwd = "password1234";
        try {
            con = DriverManager.getConnection(URL, USER, PASS);
            dao = new DAOImplementationMysql(con);
        } catch (SQLException e) {
            LOG.severe(e.getMessage());
        }

    }

    @AfterClass
    public static void afterClass() {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(DAOImplementationMysqlTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Test of signIn method, of class DAOImplementationMysql.
     *
     */
    @Test
    public void testSignInOK() {
        try {
            User a = new User();
            a.setLogin(username);
            a.setPassword(passwd);
            Message signInDAO = dao.signIn(a);

            try ( PreparedStatement stat = con.prepareStatement(SIGN_IN);  PreparedStatement statHistory = con.prepareStatement(SIGN_IN_HISTORY_CHECK);  PreparedStatement statDel = con.prepareStatement(SIGN_IN_HISTORY_DEL);  PreparedStatement statAdd = con.prepareStatement(SIGN_IN_HISTORY_ADD)) {
                msg = new Message();
                stat.setString(1, username);
                stat.setString(2, passwd);

                ResultSet rs = stat.executeQuery();

                User user = new User();
                if (rs.next()) {
                    Integer id = rs.getInt(1);
                    user.setId(id);
                    user.setLogin(rs.getString(2));
                    user.setEmail(rs.getString(3));
                    user.setFullName(rs.getString(4));
                    user.setStatus((rs.getString(5).equalsIgnoreCase(UserStatus.ENABLED.toString()) ? UserStatus.ENABLED : UserStatus.DISABLED));
                    user.setPrivilege((rs.getString(6).equalsIgnoreCase(UserPrivilege.USER.toString()) ? UserPrivilege.USER : UserPrivilege.ADMIN));
                    user.setPassword(rs.getString(7));
                    user.setLastPasswordChange(rs.getTimestamp(8));

                    statAdd.setInt(1, id);
                    statAdd.executeUpdate();

                    statHistory.setInt(1, id);
                    ResultSet rsHistoryCheck = statHistory.executeQuery();

                    if (rsHistoryCheck.next()) {
                        if (rsHistoryCheck.getString(1).equalsIgnoreCase("true")) {
                            LOG.info("User already loged in 10 times, proceding to remove oldest record");
                            statDel.setInt(1, id);
                            statDel.executeUpdate();
                        }
                    }

                    msg.setUserData(user);
                    msg.setOperation(Operation.OK);

                }
                assertTrue(signInDAO.equals(msg));
            } catch (SQLException ex) {
                Logger.getLogger(DAOImplementationMysqlTest.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (IncorrectLoginException | ServerErrorException ex) {
            LOG.severe(ex.getMessage());
        }
    }

    /**
     * Test of signIn method, of class DAOImplementationMysql.
     *
     * @throws exceptions.IncorrectLoginException
     */
    @Test(expected = IncorrectLoginException.class)
    public void testSignInIncorrectUserPass() throws IncorrectLoginException {
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
     * Test of signIn method, of class DAOImplementationMysql.
     *
     * @throws exceptions.ServerErrorException
     */
    @Test(expected = ServerErrorException.class)
    public void testSignInServerError() throws ServerErrorException {
        try {
            con.close();
            Message signInDAO = dao.signIn(null);
        } catch (IncorrectLoginException | SQLException ex) {
            Logger.getLogger(DAOImplementationMysqlTest.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con = DriverManager.getConnection(URL, USER, PASS);
                dao = new DAOImplementationMysql(con);
            } catch (SQLException e) {
                LOG.severe(e.getMessage());
            }
        }

    }

    /**
     * Test of signUp method, of class DAOImplementationMysql.
     *
     */
    @Ignore
    @Test
    public void testSignUp() {

    }

}
