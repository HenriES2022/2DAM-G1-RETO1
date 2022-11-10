/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverProject;

import enumerations.Operation;
import exceptions.IncorrectLoginException;
import exceptions.ServerErrorException;
import exceptions.ServerFullException;
import exceptions.UserAlreadyExistsException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import static java.lang.Thread.sleep;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Message;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import serverProject.model.dao.DAO;
import serverProject.model.dao.DAOFactory;
import static org.junit.Assert.*;
import serverProject.model.dao.DAOImplementationMysqlTest;
import serverProject.model.database.DB;
import serverProject.model.database.DBFactory;

/**
 *
 * @author Joritz
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MainTest {

    private static final Logger LOG = Logger.getLogger("serverProject.MainTest");
    private static final DAO DAO = DAOFactory.getDAO();
    private static ObjectOutputStream oos;
    private static ObjectInputStream ois;
    private Integer threadCount = 0;
    private static String login = "";
    private static ServerSocket serverTest = null;

    /**
     * Test of main method, of class Main.
     */
    private static final int PUERTO = Integer.parseInt(ResourceBundle.getBundle("serverProject.configServer").getString("port"));

    @AfterClass
    public static void after() {
        DB poolImpl = DBFactory.getDB();
        Connection con = poolImpl.getConnection();
        String delete = "DELETE FROM USER WHERE login = ?";
        try (PreparedStatement stat = con.prepareStatement(delete)) {
            stat.setString(1, login);
            stat.executeUpdate();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(DAOImplementationMysqlTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @BeforeClass
    public static void before() {
        try {
            serverTest = new ServerSocket(PUERTO);
        } catch (IOException ex) {
            Logger.getLogger(MainTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Message testRun() {
        Message response = new Message();
        try {
            threadCount++;
            if (threadCount > 10) {
                throw new ServerFullException();
            }
            // Retrieve Msg from the client
            Message msg = (Message) ois.readObject();
            if(msg.getUserData().getLogin() != null){
                login = msg.getUserData().getLogin();
            }
            // Check the operation request
            if (msg.getOperation().equals(Operation.SING_IN)) {
                response = DAO.signIn(msg.getUserData());
            } else {
                response = DAO.signUp(msg.getUserData());
            }

            // Send Message to the client
        } catch (UserAlreadyExistsException ex) {
            LOG.warning(ex.getMessage());
            response.setOperation(Operation.USER_EXISTS);
        } catch (IncorrectLoginException e) {
            LOG.severe(e.getMessage());
            response.setOperation(Operation.LOGIN_ERROR);
        } catch (ServerErrorException e) {
            LOG.severe(e.getMessage());
            response.setOperation(Operation.SERVER_ERROR);
        } catch (ServerFullException e) {
            LOG.warning(e.getMessage());
            response.setOperation(Operation.SERVER_FULL);
        } finally {
            return response;
        }

    }

    @Test
    public void testA_signupOK() throws InterruptedException {
        try {
            Socket skClientTest = serverTest.accept();
            oos = new ObjectOutputStream(skClientTest.getOutputStream());
            ois = new ObjectInputStream(skClientTest.getInputStream());
            Message response = testRun();
            sleep(1000);
            oos.writeObject(response);
            sleep(1000);
            assertEquals(Operation.OK, response.getOperation());
        } catch (IOException ex) {
            LOG.severe(ex.getMessage());
        }
    }

    @Test(expected = UserAlreadyExistsException.class)
    public void testB_signupUserExists() throws InterruptedException, UserAlreadyExistsException {
        try {
            Socket skClientTest = serverTest.accept();
            oos = new ObjectOutputStream(skClientTest.getOutputStream());
            ois = new ObjectInputStream(skClientTest.getInputStream());
            Message response = testRun();
            sleep(1000);
            oos.writeObject(response);
            sleep(1000);
            if (response.getOperation().equals(Operation.USER_EXISTS)) {
                throw new UserAlreadyExistsException();
            }
        } catch (IOException ex) {
            LOG.severe(ex.getMessage());
        }
    }
    
    @Test
    public void testC_signinOK() throws InterruptedException{
        try {
            Socket skClientTest = serverTest.accept();
            oos = new ObjectOutputStream(skClientTest.getOutputStream());
            ois = new ObjectInputStream(skClientTest.getInputStream());
            Message response = testRun();
            sleep(1000);
            oos.writeObject(response);
            sleep(1000);
            assertEquals(Operation.OK, response.getOperation());
        } catch (IOException ex) {
            LOG.severe(ex.getMessage());
        }
    }
    
    @Test(expected = IncorrectLoginException.class)
    public void testD_signinError() throws InterruptedException, IncorrectLoginException{
        try {
            Socket skClientTest = serverTest.accept();
            oos = new ObjectOutputStream(skClientTest.getOutputStream());
            ois = new ObjectInputStream(skClientTest.getInputStream());
            Message response = testRun();
            sleep(1000);
            oos.writeObject(response);
            sleep(1000);
            if (response.getOperation().equals(Operation.LOGIN_ERROR)) {
                throw new IncorrectLoginException();
            }
        } catch (IOException ex) {
            LOG.severe(ex.getMessage());
        }
    }
}
