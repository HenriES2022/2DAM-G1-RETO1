/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverProject;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Logger;
import serverProject.logic.WorkingThread;

/**
 *
 * @author Joritz
 * @author Henrique
 */
public class Main {

    private static final Logger LOG = Logger.getLogger("serverProject.Main");
    private static Boolean stop = false;
    static final int PUERTO = 5000;

    public Main() {
        try {
            KeyboardListener kbListener = new KeyboardListener(this);
            kbListener.start();
            LOG.info("Listening on port: " + PUERTO);
            ServerSocket skServidor = new ServerSocket(PUERTO);
            while (!stop) {
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
        final Main main = new Main();
    }

    private void shutdown() {
        System.exit(0);
    }

    private class KeyboardListener extends Thread {

        private final String EXIT = "exit";
        private final Main main;

        public KeyboardListener(Main main) {
            this.main = main;
        }

        @Override
        public void run() {
            Scanner sc = new Scanner(System.in);
            while (true) {
                LOG.info("Use 'EXIT' to close the server");
                String readText = sc.next();
                if (readText.equalsIgnoreCase(EXIT)) {
                    stop = true;
                    main.shutdown();
                    System.exit(0);
                } else {
                    LOG.info("Unknown command: To close the server you need to write 'EXIT'");
                }
            }
        }
    }
}
