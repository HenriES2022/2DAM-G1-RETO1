/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientProject.logic;


import exceptions.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import model.Message;

/**
 *
 * @author Joritz
 */
public class ClientSocketImplementation implements ClientSocket {

    private static final Logger LOG = Logger.getLogger("clientProject.logic.ClientSocketImplementation");
    private static final String HOST = ResourceBundle.getBundle("clientProject.configClient").getString("ip_address");
    private static final Integer PORT = Integer.parseInt(ResourceBundle.getBundle("clientProject.configClient").getString("port"));

    @Override
    public Message connectToServer(Message message) throws ServerErrorException, ServerFullException {
        Socket socket = null;
        try {
            LOG.info("Establishing the connection to the server");
            // Se crea la conexión al servidor
            socket = new Socket(HOST, PORT);

            LOG.info("Connected: Opening I/O streams");
            // Se abren los streams de E/S
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

            LOG.info("Sending message to the server");
            // Envia el mensaje al servidor
            oos.writeObject(message);

            LOG.info("Reading message from the server");
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
        } catch (IOException | ClassNotFoundException | NullPointerException ex) {
            LOG.severe(ex.getMessage());
            throw new ServerErrorException("Se ha cerrado la conexión con el servidor de forma inesperada, intentelo de nuevo más tarde");
        } finally {
            try {
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException ex) {
                LOG.severe(ex.getMessage());
            }

        }
    }
}
