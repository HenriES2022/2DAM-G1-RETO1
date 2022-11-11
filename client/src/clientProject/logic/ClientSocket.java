/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientProject.logic;

import exceptions.ServerErrorException;
import exceptions.ServerFullException;
import model.Message;

/**
 *This is the cient Socket interface where there is the declaration of the client side methods to connect to server
 * @author Joritz
 */
public interface ClientSocket {
    /**
     * This method is to connect the client side to the server side
     * @param message The message that the client side need to send to the server with the user and the operation to do
     * @return The message with the result of the performed operation
     * @throws ServerErrorException when an any type of error has happen in the server trying to do the operation
     * @throws ServerFullException when the limit of how many users can be connected has been reached
     */
    public Message connectToServer(Message message) throws ServerErrorException, ServerFullException;
}
