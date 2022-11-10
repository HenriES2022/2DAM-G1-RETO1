/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package serverProject.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;
import model.Message;
import model.User;
import serverProject.model.database.DBFactory;
import serverProject.model.database.DB;
import enumerations.*;
import exceptions.IncorrectLoginException;
import exceptions.ServerErrorException;
import exceptions.UserAlreadyExistsException;
import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author yeguo
 */
public class DAOImplementationMysql implements DAO {

    /**
     * The connection
     */
    private Connection con;

    /**
     * The empty contructor
     */
    public DAOImplementationMysql() {
    }

    /**
     * The constructor with params
     * @param con a connection object
     */
    public DAOImplementationMysql(Connection con) {
        this.con = con;
    }

    // Constants for conditions
    private static final byte TRUE = 1;
    private static final byte FALSE = 0;

    // Looger
    private static final Logger LOG = Logger.getLogger("serverProject.model.dao.DAOImplementation");

    // Get the DB implementation from the factory
    private final DB dbImpl = DBFactory.getDB();

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

    // Statement checks if user has more than 10 logins
    private final static String SIGN_IN_HISTORY_CHECK
            = "SELECT IF(count(LAST_SIGIN)>= 10, true, false) FROM signin WHERE USER_ID = ?";

    // Statement for the sign up
    private final static String SIGN_UP
            = "INSERT INTO USER(login, email, full_name, user_password)"
            + "values(?,?,?,MD5(?))";

    // Statement checks if user already exists
    private final static String SIGN_UP_USER_CHECK
            = "SELECT login FROM user WHERE login like ?";

    // Message
    private Message msg;

    /**
     * This method is for the SignIn, passes through parameter a user with the
     * Login Username and Password.
     * <br>
     * Sequence of the statements
     * <ul> 
     * <li>{@link #SIGN_IN} "Login" statement, retrieves the data of the logged
     * user</li>
     * <li>{@link #SIGN_IN_HISTORY_ADD} inserts a new row with the
     * TimeStamp</li>
     * <li>{@link #SIGN_IN_HISTORY_CHECK} checks if the user has already logged
     * in 10 times</li>
     * <li>{@link #SIGN_IN_HISTORY_DEL} deletes the oldest history login</li>
     * <li>{@link #SIGN_IN_HISTORY} retrieves the list of times that the user
     * has logged in</li>
     * </ul>
     *
     * 
     */
    @Override
    public Message signIn(User user) throws IncorrectLoginException, ServerErrorException {
        if (con == null) {
            con = dbImpl.getConnection();
        }

        // Try-catch with resources
        try (PreparedStatement stat = con.prepareStatement(SIGN_IN);
                PreparedStatement statAdd = con.prepareStatement(SIGN_IN_HISTORY_ADD);
                PreparedStatement statHistoryCheck = con.prepareStatement(SIGN_IN_HISTORY_CHECK);
                PreparedStatement statDel = con.prepareStatement(SIGN_IN_HISTORY_DEL);
                PreparedStatement statHistory = con.prepareStatement(SIGN_IN_HISTORY)) {

            // Create the msg object
            msg = new Message();

            // Setting parameters for the select statement
            stat.setString(1, user.getPassword());
            stat.setString(2, user.getLogin());

            // Executing statement
            ResultSet rs = stat.executeQuery();

            // True if user and pass is correct
            if (rs.next()) {
                LOG.info("User logged in successfully, retrieving data...");

                // Setting the user data
                Integer id = rs.getInt(1);
                user.setId(id);
                user.setLogin(rs.getString(2));
                user.setEmail(rs.getString(3));
                user.setFullName(rs.getString(4));
                user.setStatus((rs.getString(5).equalsIgnoreCase(UserStatus.ENABLED.toString()) ? UserStatus.ENABLED : UserStatus.DISABLED));
                user.setPrivilege((rs.getString(6).equalsIgnoreCase(UserPrivilege.USER.toString()) ? UserPrivilege.USER : UserPrivilege.ADMIN));
                user.setPassword(rs.getString(7));
                user.setLastPasswordChange(rs.getTimestamp(8));

                // Add SignIn to the History
                statAdd.setInt(1, id);
                statAdd.executeUpdate();

                // Getting SignIn history of the user
                statHistoryCheck.setInt(1, id);
                ResultSet rsHistoryCheck = statHistoryCheck.executeQuery();
                rsHistoryCheck.next();

                if (rsHistoryCheck.getByte(1) == TRUE) {
                    LOG.info("User already loged in 10 times, proceding to remove oldest record");
                    statDel.setInt(1, id);
                    statDel.executeUpdate();
                }

                // SignIn history
                statHistory.setInt(1, id);
                ResultSet rsHistory = statHistory.executeQuery();
                List<Timestamp> loginHistory = user.getSignInHistory();
                while (rsHistory.next()) { // Loop to save login history
                    loginHistory.add(rsHistory.getTimestamp(1));
                }
                user.setSignInHistory(loginHistory);

                // Create the message
                msg.setUserData(user);
                msg.setOperation(Operation.OK);
                LOG.info("Data from user retrieved correctly");
                return msg;
            }
            // User and/or pass is NOT correct
            throw new exceptions.IncorrectLoginException("Incorrect username and/or password");

        } catch (SQLException e) {
            throw new ServerErrorException("Error while trying to creating/executing the prepare statement.");
        } catch (NullPointerException e) {
            throw new ServerErrorException("Error while trying to creating/executing the prepare statement.");
        } finally {
            dbImpl.saveConnection();
        }
    }

    
    @Override
    public Message signUp(User user) throws UserAlreadyExistsException, ServerErrorException {
        if (con == null) {
            con = dbImpl.getConnection();
        }

        // Try-catch with resources
        try (PreparedStatement statSignUp = con.prepareStatement(SIGN_UP); PreparedStatement statCheckUser = con.prepareStatement(SIGN_UP_USER_CHECK)) {

            // Create msg object
            msg = new Message();

            statCheckUser.setString(1, user.getLogin());
            ResultSet rsCheckUser = statCheckUser.executeQuery();
            if (rsCheckUser.next()) {
                if (rsCheckUser.getString(1).equalsIgnoreCase(user.getLogin())) {
                    throw new UserAlreadyExistsException("Error user already register");
                }
            }

            // Setting user data for insert
            statSignUp.setString(1, user.getLogin());
            statSignUp.setString(2, user.getEmail());
            statSignUp.setString(3, user.getFullName());
            statSignUp.setString(4, user.getPassword());

            // Execute Insert
            statSignUp.executeUpdate();

            msg.setOperation(Operation.OK);
            LOG.info("'OK': User created correctly");
            return msg;
        } catch (SQLException e) {
            throw new ServerErrorException("Error while trying to creating/executing the prepare statement");
        } catch (NullPointerException e){
            throw new ServerErrorException("Error while trying to creating/executing the prepare statement");
        } 
        finally {
            dbImpl.saveConnection();

        }

    }

}
