/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientProject.logic;

import exceptions.ServerErrorException;
import exceptions.ServerFullException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Message;

/**
 *
 * @author Joritz
 */
public class ClientSocketImplementation implements ClientSocket {

    static final String HOST = "localhost";
    static final int PORT = 5000;

    @Override
    public Message connectToServer(Message message) throws ServerErrorException, ServerFullException {
        try {
            Socket socket = new Socket(HOST, PORT);
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            message = (Message) ois.readObject();
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(message);
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(ClientSocketImplementation.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClientSocketImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return message;
    }
    
    public void getImplementation(){
        
    }
}
