/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverProject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Message;
import serverProject.logic.WorkingThread;

/**
 *
 * @author Joritz
 */
public class Main {

    static final int PUERTO = 5000;

    public Main() {
        try {
            ServerSocket skServidor = new ServerSocket(PUERTO);
            while (true) {
                Socket skCliente = skServidor.accept();
                WorkingThread thread = new WorkingThread(skCliente);
                thread.start();
                ObjectInputStream ois = new ObjectInputStream(skCliente.getInputStream());
                Message message = (Message) ois.readObject();
                System.out.println(message);
                ObjectOutputStream oos = new ObjectOutputStream(skCliente.getOutputStream());
                oos.writeObject(message);
                skCliente.close();
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        // TODO code application logic here
        new Main();
    }
}
