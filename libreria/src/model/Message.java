/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import enumerations.Operation;
import java.util.Objects;

/**
 * This class is for encapsulate the user we are going to send to the server
 * with the operation we are going with this info, and only have to send one
 * message
 *
 * @author iorit
 */
public class Message {

    /**
     * A user objet reference to send the user info to the server or obtain it
     * from the server
     */
    private User userData = null;
    /**
     * The operation that the server is going to do with this data
     */
    private Operation operation = null;

    /**
     * Empty constructor.
     */
    public Message() {

    }

    //Getters and Setters
    public User getUserData() {
        return userData;
    }

    public void setUserData(User userData) {
        this.userData = userData;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    //HashCode and equals
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.userData);
        hash = 89 * hash + Objects.hashCode(this.operation);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Message other = (Message) obj;
        if (!Objects.equals(this.userData, other.userData)) {
            return false;
        }
        if (this.operation != other.operation) {
            return false;
        }
        return true;
    }

}
