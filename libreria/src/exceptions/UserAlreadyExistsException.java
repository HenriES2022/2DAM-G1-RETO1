/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *This exception is throwed when a user is going to register with a user that actually exist
 * @author iorit
 */
public class UserAlreadyExistsException extends Exception {

    /**
     * Creates a new instance of <code>UserAlreadyExistsException</code> without
     * detail message.
     */
    public UserAlreadyExistsException() {
        super();
    }

    /**
     * Constructs an instance of <code>UserAlreadyExistsException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public UserAlreadyExistsException(String msg) {
        super(msg);
    }
}
