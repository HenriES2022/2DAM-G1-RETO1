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
 *
 * @author Joritz
 */
public interface ClientSocket {
    public Message connectToServer(Message message) throws ServerErrorException, ServerFullException;
}
