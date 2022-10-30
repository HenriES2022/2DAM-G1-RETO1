/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package serverProject.model.dao;

import exceptions.UserAlreadyExistsException;
import exceptions.IncorrectLoginException;
import exceptions.ServerErrorException;
import model.Message;
import model.User;

/**
 *
 * @author yeguo
 */
public interface DAO {

    /**
     * This method passes a User (Username and Password) to SignIn and returns
     * all the information of that user if the credentials are correct and a
     * message if the operation was done successfully
     *
     * @param user Pass the user with all the date for the signIn
     * @return Returns a message
     * @throws IncorrectLoginException
     * @throws exceptions.ServerErrorException
     */
    public Message signIn(User user) throws IncorrectLoginException, ServerErrorException;

    /**
     * This method passes a User (Username and Password) to SignUp and returns a
     * Message with a null user with a operation indicating the result of the
     * request
     *
     * @param user
     * @return Returns a {@code boolean} depending if the the signUp was done
     * successfully
     * @throws UserAlreadyExistsException
     * @throws exceptions.ServerErrorException
     */
    public Message signUp(User user) throws UserAlreadyExistsException, ServerErrorException;

}
