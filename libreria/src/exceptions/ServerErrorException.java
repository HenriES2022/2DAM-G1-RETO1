/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *This exception is throwed when an error has happened in the server
 * @author iorit
 */
public class ServerErrorException extends Exception {

    /**
     * Creates a new instance of <code>ServerErrorException</code> without
     * detail message.
     */
    public ServerErrorException() {
        super();
    }

    /**
     * Constructs an instance of <code>ServerErrorException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public ServerErrorException(String msg) {
        super(msg);
    }
}
