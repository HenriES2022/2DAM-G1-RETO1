/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package serverProject.logic;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Logger;
import model.Message;
import serverProject.model.dao.*;
import exceptions.*;
import enumerations.Operation;

/**
 *
 * @author yeguo
 */
public class WorkingThread extends Thread {
    
    private static int threadCount = 0;
    private static final Logger LOG = Logger.getLogger("serverProject.logic.WorkingThread");
    private static final DAO DAO = DAOFactory.getDAO();
    private final Socket sc;
    private static Message response = new Message();

    public WorkingThread(Socket sc) {
        super();
        this.sc = sc;
    }
    
    public static int getThreadCount() {
        return threadCount;
    }
    
    @Override
    public void run() {
        try ( ObjectOutputStream oos = new ObjectOutputStream(sc.getOutputStream());  ObjectInputStream ois = new ObjectInputStream(sc.getInputStream());) {
            //sleep(1000);
            try {
                sleep(100);
                LOG.info("Opening I/O streams");
                threadCount++;
                sleep(100);
                LOG.info("Starting the " + threadCount + " thread");
                sleep(100);
                if (threadCount > 1) {
                    throw new ServerFullException("Servidor lleno, no se puede atender a más usuarios");
                }
                // Retrieve Msg from the client
                sleep(100);
                Message msg = (Message) ois.readObject();

                // Check the operation request
                if (msg.getOperation().equals(Operation.SING_IN)) {
                    response = DAO.signIn(msg.getUserData());
                } else {
                    response = DAO.signUp(msg.getUserData());
                }

                // Send Message to the client
                oos.writeObject(response);
            } catch (UserAlreadyExistsException ex) {
                LOG.warning(ex.getMessage());
                response.setOperation(Operation.USER_EXISTS);
            } catch (IncorrectLoginException e) {
                LOG.severe(e.getMessage());
                response.setOperation(Operation.LOGIN_ERROR);
            } catch (ServerErrorException | InterruptedException e) {
                LOG.severe(e.getMessage());
                response.setOperation(Operation.SERVER_ERROR);
            } catch (ServerFullException e) {
                LOG.warning(e.getMessage());
                response.setOperation(Operation.SERVER_FULL);
            } finally {
                oos.writeObject(response);
                threadCount--;
            }
            
        } catch (IOException | ClassNotFoundException exc) {
            LOG.severe(exc.getMessage());
        }
    }
    
}
