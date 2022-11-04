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

            if (respuesta.getOperation().equals(Operation.OK)) {
                return respuesta;
            } else if (respuesta.getOperation().equals(Operation.SERVER_FULL)) {
                throw new ServerFullException();
            }
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(ClientSocketImplementation.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(ClientSocketImplementation.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return null;
    }
}
