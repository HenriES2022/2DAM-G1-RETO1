/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enumerations;

/**
 * This is the enumeration where we have all the posible orders to the servers
 * and all the posible answers of the server
 * <ul>
 * <li>SING_UP - Is the order given by the client when the action to do is a
 * Sing UP</li>
 * <li>SING_IN - Is the order given by the client when the action to do is a
 * Sing IN</li>
 * <li>OK - Is the answer given by the server when the action has done
 * correctly</li>
 * <li>
 * USER_EXISTS - Is the answer given by the server when the action <br>
 * of the sign Up has throwed an UserAlreadyExistException because the user
 * exist</li>
 * <li>
 * LOGIN_ERROR - Is the answer given by the server when the action of the sign
 * In has trowed an IncorrectLoginException <br>
 * because the user or the password is not correct
 * </li>
 * <li>
 * SERVER_ERROR - Is the answer given by the server when the any actione done
 * with the server has trowed an ServerErrorException
 * </li>
 *
 * <li>SING_UP - Is the order given by the client when the action to do is a
 * Sing UP</li>
 * <li>SING_IN - Is the order given by the client when the action to do is a
 * Sing IN</li>
 * <li>OK - Is the answer given by the server when the action has done
 * correctly</li>
 * <li>
 * USER_EXISTS - Is the answer given by the server when the action <br>
 * of the sign Up has throwed an UserAlreadyExistException because the user
 * exist</li>
 * <li>
 * LOGIN_ERROR - Is the answer given by the server when the action of the sign
 * In has trowed an IncorrectLoginException <br>
 * because the user or the password is not correct
 * </li>
 * <li>
 * SERVER_ERROR - Is the answer given by the server when the any actione done
 * with the server has trowed an ServerErrorException
 * </li>
 * </ul>
 *
 * @author iorit
 */
public enum Operation {
    SING_UP,
    SING_IN,
    OK,
    USER_EXISTS,
    LOGIN_ERROR,
    SERVER_ERROR
}
