/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientProject.logic;

import java.util.ResourceBundle;

/**
 *This is the factory that creates  ClientSocket implementations 
 * @author Joritz
 */
public class ClientSocketFactory {
    /**
     * The string with the connection type that the client side will use when is going to connect to the server. Obtained from a properties file.
     */
    private static final String CONNECTION_TYPE = ResourceBundle.getBundle("clientProject.configClient").getString("connection_type");
    /**
     * To compare if obtained connection type from the properties is the same to this.
     */
    private static final String CONNECTION = "socket";
    /**
     * Obtains the implementation of Client Socket interface
     * @return Returns a new ClientSocketImplementation if CONNECTION_TYPE is the same to the connection type we want, <br>
     * or null if the readed CONNECTION_TYPE don't exists.
     */
    public static ClientSocket getImplementation() {
        if (CONNECTION_TYPE.equalsIgnoreCase(CONNECTION)) {
            return new ClientSocketImplementation();
        }
        return null;
    }
}
