/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package serverProject.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Message;
import model.User;
import serverProject.model.database.DBFactory;
import serverProject.model.database.DB;
import enumerations.*;
import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author yeguo
 */
public class DAOImplementation implements DAO {

    // Looger
    private static final Logger LOG = Logger.getLogger("serverProject.model.dao.DAOImplementation");

    // Get the DB implementation from the factory
    private final DB dbImpl = DBFactory.getDB();

    // Statement for the login
    private final static String SIGN_IN
            = "SELECT * FROM USER WHERE USER_PASSWORD = MD5(?) AND LOGIN = ?";

    // Statement selects the user's logging history
    private final static String SIGN_IN_HISTORY
            = "SELECT * FROM sigin WHERE USER_ID = ?";

    // Statement for the sign up
    private final static String SIGN_UP
            = "INSERT INTO USER(login, email, full_name, user_password)"
            + "values(?,?,?,?,?,MD5(?))";

    // Message
    private Message msg;

    @Override
    public Message signIn(User user) {
        // Create the msg object
        msg = new Message();

        // Try-catch with resources
        try ( Connection con = dbImpl.getConnection();  PreparedStatement stat = con.prepareStatement(SIGN_IN);  PreparedStatement statHistory = con.prepareStatement(SIGN_IN_HISTORY)) {

            // Setting parameters for the select statement
            stat.setString(1, user.getPassword());
            stat.setString(2, user.getLogin());

            // Executing statement
            ResultSet rs = stat.executeQuery();

            // True if user and pass is correct
            if (rs.next()) {
                LOG.info("User logged successfully, retrieving data...");

                // Setting the user data
                user.setId(rs.getInt(1));
                user.setLogin(rs.getString(2));
                user.setEmail(rs.getString(3));
                user.setFullName(rs.getString(4));
                user.setStatus((rs.getString(5).equalsIgnoreCase(UserStatus.ENABLED.toString()) ? UserStatus.ENABLED : UserStatus.DISABLED));
                user.setPrivilege((rs.getString(6).equalsIgnoreCase(UserPrivilege.USER.toString()) ? UserPrivilege.USER : UserPrivilege.ADMIN));
                user.setPassword(rs.getString(7));
                user.setLastPasswordChange(rs.getTimestamp(8));

                // Getting SignIn history of the user
                ResultSet rsHistory = statHistory.executeQuery();

                // SignIn history
                List<Timestamp> history = user.getSignInHistory();
                while (rsHistory.next()) {
                    history.add(rsHistory.getTimestamp(1));
                }
                user.setSignInHistory(history);

                // Create the message
                msg.setUserData(user);
                msg.setOperation(Operation.OK);
                LOG.info("'OK': Message created with all the data of the user");
            } else { // User and/or pass is NOT correct
                msg.setOperation(Operation.LOGIN_ERROR);
                LOG.info("'LOGIN_ERROR': Username and/or password is not correct");
            }
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Error while trying to creating/executing the prepare statement", e);
            msg.setOperation(Operation.SERVER_ERROR);
        }
        return msg;
    }

    @Override
    public Message signUp(User user) {
        // Create msg object
        msg = new Message();

        // Try-catch with resources
        try ( Connection con = dbImpl.getConnection();  PreparedStatement stat = con.prepareStatement(SIGN_UP);) {
            // Setting user data for insert
            stat.setString(1, user.getLogin());
            stat.setString(2, user.getEmail());
            stat.setString(3, user.getFullName());
            stat.setString(4, user.getPassword());

            stat.execute(SIGN_IN);

        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Error while trying to creating/executing the prepare statement", e);
        }

        return msg;
    }

    @Override
    public Boolean checkSignInHistory() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
