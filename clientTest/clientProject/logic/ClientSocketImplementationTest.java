/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package clientProject.logic;

import enumerations.Operation;
import exceptions.ServerErrorException;
import exceptions.ServerFullException;
import java.rmi.ServerError;
import model.Message;
import model.User;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.runners.MethodSorters;

/**
 *
 * @author yeguo
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ClientSocketImplementationTest {

    private final ClientSocket clientSocket = ClientSocketFactory.getImplementation();
    private static User user;
    private Message msg;
    private Message response;

    @BeforeClass
    public static void beforeClass() {
        user = new User();
        user.setFullName("Eustaquio Habichuela");
        user.setEmail("perro.espitudo@meobligasasermalo.com");
        user.setLogin("agallas");
        user.setPassword("PerroEstupido69420?");

    }

    /**
     * Test of connectToServer method, of class ClientSocketImplementation.
     *
     * @throws exceptions.ServerErrorException
     * @throws exceptions.ServerFullException
     */
    @Test
    public void testA_SignUpOK() throws ServerErrorException, ServerFullException {
        msg = new Message();
        msg.setUserData(user);
        msg.setOperation(Operation.SING_UP);

        response = clientSocket.connectToServer(msg);
        assertEquals(Operation.OK, response.getOperation());
    }

    /**
     *
     *
     * @throws exceptions.ServerErrorException
     * @throws exceptions.ServerFullException
     */
    @Test
    @Ignore
    public void testB_SignInOK() throws ServerErrorException, ServerFullException {
        msg = new Message();
        msg.setUserData(user);
        msg.setOperation(Operation.SING_IN);

        response = clientSocket.connectToServer(msg);
        assertEquals(Operation.OK, msg.getOperation());

    }

}
