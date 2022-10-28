/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exceptions;

/**
 *
 * @author yeguo
 */
public class ServerFullException extends Exception {

    /**
     * Creates a new instance of <code>ServerFullException</code> without detail
     * message.
     */
    public ServerFullException() {
        super();
    }

    /**
     * Constructs an instance of <code>ServerFullException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public ServerFullException(String msg) {
        super(msg);
    }

}
