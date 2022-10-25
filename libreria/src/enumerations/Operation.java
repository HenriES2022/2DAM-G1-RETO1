/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enumerations;

/**
 * This is the enumeration where we have all the possible orders to the servers
 * and all the possible answers of the server
 * <ul>
 * <li>SING_UP - Is the order given by the client when the action to do is a
 * Sing UP</li>
 * <li>SING_IN - Is the order given by the client when the action to do is a
 * Sing IN</li>
 * <li>OK - Is the answer given by the server when the action has done
 * correctly</li>
 * <li>
 * USER_EXISTS - Is the answer given by the server when the action of the sign
 * Up has threw an UserAlreadyExistException because the user exist</li>
 * <li>
 * LOGIN_ERROR - Is the answer given by the server when the action of the sign
 * In has threw an IncorrectLoginException <br>
 * because the user or the password is not correct
 * </li>
 * <li>
 * SERVER_ERROR - Is the answer given by the server when the any actions done
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
 * of the sign Up has threw an UserAlreadyExistException because the user
 * exist</li>
 * <li>
 * LOGIN_ERROR - Is the answer given by the server when the action of the sign
 * In has trowed an IncorrectLoginException <br>
 * because the user or the password is not correct
 * </li>
 * <li>
 * SERVER_ERROR - Is the answer given by the server when the any actions done
 * with the server has threw n ServerErrorException
 * </li>
 * </ul>
 *
 * @author iorit
 */
public enum Operation {
    /**
     * Order given by the client when the action to do is a Sing UP
     */
    SING_UP,
    /**
     * Order given by the client when the action to do is a Sing IN
     */
    SING_IN,
    /**
     * Answer given by the server when the action has done correctly
     */
    OK,
    /**
     * Answer given by the server when the action of the sign Up has threw an
     * UserAlreadyExistException because the user exist
     */
    USER_EXISTS,
    /**
     * Answer given by the server when the action of the sign In has threw an
     * IncorrectLoginException because the user or the password is not correct
     */
    LOGIN_ERROR,
    /**
     * Answer given by the server when the any actions done with the server has
     * trowed an ServerErrorException
     */
    SERVER_ERROR
}
