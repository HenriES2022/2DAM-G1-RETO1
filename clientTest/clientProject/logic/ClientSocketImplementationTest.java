/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package clientProject.logic;

import enumerations.Operation;
import exceptions.ServerErrorException;
import exceptions.ServerFullException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import model.Message;
import model.User;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.FixMethodOrder;
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
     * Test of connectToServer method, of class ClientSocketImplementation.
     *
     * @throws exceptions.ServerErrorException
     * @throws exceptions.ServerFullException
     */
    @Test
    public void testB_SignUpUserExists() throws ServerErrorException, ServerFullException {
        msg = new Message();
        msg.setUserData(user);
        msg.setOperation(Operation.SING_UP);

        response = clientSocket.connectToServer(msg);
        assertEquals(Operation.USER_EXISTS, response.getOperation());
    }

    /**
     *
     *
     * @throws exceptions.ServerErrorException
     * @throws exceptions.ServerFullException
     */
    @Test
    public void testC_SignInOK() throws ServerErrorException, ServerFullException {
        msg = new Message();
        msg.setUserData(user);
        msg.setOperation(Operation.SING_IN);

        response = clientSocket.connectToServer(msg);
        assertEquals(Operation.OK, response.getOperation());
        assertEquals(getMd5(user.getPassword()), response.getUserData().getPassword());
    }

    /**
     *
     *
     * @throws exceptions.ServerErrorException
     * @throws exceptions.ServerFullException
     */
    @Test
    public void testD_SignInLoginError() throws ServerErrorException, ServerFullException {
        User incorrectUser = new User();
        incorrectUser.setLogin("Eustaqiohabichuela1111");
        incorrectUser.setPassword("HolaBuenosDias");
        msg = new Message();
        msg.setUserData(incorrectUser);
        msg.setOperation(Operation.SING_IN);

        response = clientSocket.connectToServer(msg);
        assertEquals(Operation.LOGIN_ERROR, response.getOperation());
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
