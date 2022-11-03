/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientProject.logic;

import model.Message;

/**
 *
 * @author Joritz
 */
public class ClientSocketFactory {

    public static ClientSocket getSocket() {
        return new ClientSocketImplementation();
    }

    public ClientSocket getImplementation() {
        ClientSocketImplementation csi = new ClientSocketImplementation();
        csi.getImplementation();
        return csi;
    }
}
