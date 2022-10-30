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

    // TODO- This is a connection object for testing previous to pool impl
    private Connection con;

    public DAOImplementationMysql() {
    }

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

    // Statement for checking if user has more than 10 logins
    private final static String SIGN_IN_HISTORY_CHECK
            = "SELECT IF(count(LAST_SIGIN)>= 10, true, false) FROM signin WHERE USER_ID = ?";

    // Statement for the sign up
    private final static String SIGN_UP
            = "INSERT INTO USER(login, email, full_name, user_password)"
            + "values(?,?,?,?,?,MD5(?))";

    // Statement checks if user already exists
    private final static String SIGN_UP_USER_CHECK
            = "SELECT IF(LOGIN = ?, true, false) FROM user";

    // Message
    private Message msg;

    /**
     *
     * @param user
     * @return
     * @throws IncorrectLoginException
     * @throws ServerErrorException
     */
    @Override
    public Message signIn(User user) throws IncorrectLoginException, ServerErrorException {
        // Try-catch with resources
        try ( //Connection con = dbImpl.getConnection();  
                 PreparedStatement stat = con.prepareStatement(SIGN_IN);  PreparedStatement statHistory = con.prepareStatement(SIGN_IN_HISTORY_CHECK);  PreparedStatement statDel = con.prepareStatement(SIGN_IN_HISTORY_DEL);  PreparedStatement statAdd = con.prepareStatement(SIGN_IN_HISTORY_ADD)) {

            // Create the msg object
            msg = new Message();

            // Setting parameters for the select statement
            stat.setString(1, user.getPassword());
            stat.setString(2, user.getLogin());

            // Executing statement
            ResultSet rs = stat.executeQuery();

            // True if user and pass is correct
            if (rs.next()) {
                LOG.info("User logged successfully, retrieving data...");

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
                statHistory.setInt(1, id);
                ResultSet rsHistoryCheck = statHistory.executeQuery();

                if (rsHistoryCheck.next()) {
                    if (rsHistoryCheck.getInt(1) == TRUE) {
                        LOG.info("User already loged in 10 times, proceding to remove oldest record");
                        statDel.setInt(1, id);
                        statDel.executeUpdate();
                    }
                }

                // TODO REMOVE THIS COMMENT
                // SignIn history
                /**
                 * List<Timestamp> history = user.getSignInHistory(); while
                 * (rsHistory.next()) { // Loop to save login history
                 * history.add(rsHistory.getTimestamp(1)); }
                 * user.setSignInHistory(history);
                 */
                // Create the message
                msg.setUserData(user);
                msg.setOperation(Operation.OK);
                LOG.info("Data from user retrieved correctly");
                return msg;
            } else { // User and/or pass is NOT correct
                throw new exceptions.IncorrectLoginException("Incorrect username and/or password");
            }
        } catch (SQLException e) {
            throw new ServerErrorException("Error while trying to creating/executing the prepare statement.");
        }

    }

    @Override
    public Message signUp(User user) throws UserAlreadyExistsException, ServerErrorException {
        // TODO check if username exists
        // Try-catch with resources
        try ( //Connection con = dbImpl.getConnection();  
                 PreparedStatement stat = con.prepareStatement(SIGN_UP);
                PreparedStatement statCheckUser = con.prepareStatement(SIGN_UP_USER_CHECK)) {
            // Create msg object
            msg = new Message();

            // Setting user data for insert
            stat.setString(1, user.getLogin());
            stat.setString(2, user.getEmail());
            stat.setString(3, user.getFullName());
            stat.setString(4, user.getPassword());

            // Execute update
            if (stat.executeUpdate() <= 0) {
                throw new UserAlreadyExistsException("Error can't register a existent user");
            }

            msg.setOperation(Operation.OK);
            LOG.info("'OK': User created correctly");
            return msg;

        } catch (SQLException e) {
            throw new ServerErrorException("Error while trying to creating/executing the prepare statement");
        }

    }

}
