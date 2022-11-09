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
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import junit.framework.TestCase;
import model.Message;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import serverProject.logic.WorkingThread;
import serverProject.model.dao.DAO;
import serverProject.model.dao.DAOFactory;
import static org.junit.Assert.*;
import org.junit.Ignore;

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

    /**
     * Test of main method, of class Main.
     */
    private static final int PUERTO = Integer.parseInt(ResourceBundle.getBundle("serverProject.configServer").getString("port"));
    
    public static void before() {
        try {
            ServerSocket serverTest = new ServerSocket(PUERTO);
            Socket skClientTest = serverTest.accept();
            oos = new ObjectOutputStream(skClientTest.getOutputStream());
            ois = new ObjectInputStream(skClientTest.getInputStream());
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
    public void testA_signupOK() {
        try {
            ServerSocket serverTest = new ServerSocket(PUERTO);
            Socket skClientTest = serverTest.accept();
            oos = new ObjectOutputStream(skClientTest.getOutputStream());
            ois = new ObjectInputStream(skClientTest.getInputStream());
            Message response = testRun();
            oos.writeObject(response);
            assertEquals(Operation.OK, response.getOperation());
        } catch (IOException ex) {
            LOG.severe(ex.getMessage());
        }
    }

    @Test
    @Ignore
    public void testB_signup() {

    }
}
