/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientProject.logic;


import enumerations.Operation;

import exceptions.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Logger;
import model.Message;

/**
 *
 * @author Joritz
 */
public class ClientSocketImplementation implements ClientSocket {
    
    private static final Logger LOG = Logger.getLogger("clientProject.logic.ClientSocketImplementation");
    
    static final String HOST = "localhost";
    static final int PORT = 5000;
    
    @Override
    public Message connectToServer(Message message) throws ServerErrorException, ServerFullException {
        
        Socket socket = null;
        try {
            // Se crea la conexión al servidor
            socket = new Socket(HOST, PORT);

            // Se abren los streams de E/S
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            
            // Envia el mensaje al servidor
            oos.writeObject(message);

            // Recibe la respuesta del servidor
            Message respuesta = (Message) ois.readObject();
            
            switch (respuesta.getOperation()) {
                case SERVER_FULL:
                    throw new ServerFullException("El servdor esta lleno, intentelo mas tarde");
                case SERVER_ERROR:
                    throw new ServerErrorException("Error al conectarse con el servidor, intentelo de nuevo mas tarde");
                default:
                    return respuesta;
            }
        } catch (IOException | ClassNotFoundException ex) {
            LOG.severe(ex.getMessage());
        } finally {
            try {
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException ex) {
                LOG.severe(ex.getMessage());
            }
            
        }
        return null;
    }
}
