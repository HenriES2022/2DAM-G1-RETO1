/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *This exception is trowed when the login data(user or password) is incorrect
 * @author iorit
 */
public class IncorrectLoginException extends Exception {

    /**
     * Creates a new instance of <code>IncorrectLoginException</code> without
     * detail message.
     */
    public IncorrectLoginException() {
        super();
    }

    /**
     * Constructs an instance of <code>IncorrectLoginException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public IncorrectLoginException(String msg) {
        super(msg);
    }
}
