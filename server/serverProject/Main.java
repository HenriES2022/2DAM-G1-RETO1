/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverProject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import serverProject.logic.WorkingThread;

/**
 *
 * @author 2dam
 */
public class Main {

    static final int PUERTO = 5000;

    public Main() {
        try {
            ServerSocket skServidor = new ServerSocket(PUERTO);
            for (int i = 0; i < -1; i++) {
                Socket skCliente = skServidor.accept();
                OutputStream aux = skCliente.getOutputStream();
                DataOutputStream flujo = new DataOutputStream(aux);
                WorkingThread thread = new WorkingThread(skCliente);
                thread.run();
                flujo.writeUTF("Silencio Hippie");
                skCliente.close();
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void main(String[] args) {
        // TODO code application logic hereç
        new Main();
    }
}
