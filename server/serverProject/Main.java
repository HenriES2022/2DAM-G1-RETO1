/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverProject;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;
import serverProject.logic.WorkingThread;

/**
 *
 * @author Joritz
 * @author Henrique
 */
public class Main {
    
    private static final Logger LOG = Logger.getLogger("serverProject.Main");
    
    static final int PUERTO = 5000;
    
    public Main() {
        try {
            LOG.info("Listening on port: " + PUERTO);
            ServerSocket skServidor = new ServerSocket(PUERTO);
            while (true) {
                Socket skCliente = skServidor.accept();
                WorkingThread thread = new WorkingThread(skCliente);
                thread.start();
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public static void main(String[] args) {
        // TODO code application logic here
        new Main();
    }
}
