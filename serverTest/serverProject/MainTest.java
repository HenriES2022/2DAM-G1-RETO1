/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverProject;

import enumerations.Operation;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import junit.framework.TestCase;
import model.Message;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.BeforeClass;
import serverProject.logic.WorkingThread;

/**
 *
 * @author Joritz
 */
public class MainTest extends TestCase {

    public MainTest(String testName) {
        super(testName);
    }

    /**
     * Test of main method, of class Main.
     */
    private static int PUERTO = 5000;

    @BeforeClass
    public static void beforeClass() {
        try {
            ServerSocket serverTest = new ServerSocket(PUERTO);
            Socket skClientTest = serverTest.accept();
            WorkingThread thread = new WorkingThread(skClientTest);
            thread.start();
        } catch (IOException ex) {
            Logger.getLogger(MainTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void testMain() {
        Message msg = new Message();
        msg.getOperation();

        if (!msg.getOperation().equals(Operation.SING_IN)) {
            fail("Statement didn't retrieve the correct operation");
        }
        assertTrue(Operation.OK.equals(msg.getOperation()));
    }
}
