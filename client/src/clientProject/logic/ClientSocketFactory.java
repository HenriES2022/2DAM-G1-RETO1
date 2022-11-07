/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientProject.logic;

import java.util.ResourceBundle;

/**
 *
 * @author Joritz
 */
public class ClientSocketFactory {

    private static final String CONNECTION_TYPE = ResourceBundle.getBundle("clientProject").getString("connection_type");
    private static final String CONNECTION = "socket";

    public static ClientSocket getImplementation() {
        if (CONNECTION_TYPE.equalsIgnoreCase(CONNECTION)) {
            return new ClientSocketImplementation();
        }
        return null;
    }
}
