/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 *This is the class were we are going to manage the user info
 * @author 2dam
 */
public class User {
    /**
     * The integer field used to identify the user
     */
    private Integer id;
    /**
     * The user login information
     */
    private String login;
    /**
     * The user email
     */
    private String email;
    /**
     * The user full name (the first name and last name)
     */
    private String fullName;
    /**
     * The user privilege level
     */
    private UserPrivilege privilege;
    /**
     * The user status in the application
     */
    private UserStatus status;
    /**
     * The user actual password
     */
    private String password;
    /**
     * The date and time of the user last password change
     */
    private Timestamp lastPasswordChange;
    /**
     * The history(date and time) of the user sign in
     */
    private List<Timestamp> signInHistory;

    /**
     * Empty contructor
     */
    public User() {
        signInHistory = new ArrayList<>();
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 13 * hash + Objects.hashCode(this.id);
        hash = 13 * hash + Objects.hashCode(this.login);
        hash = 13 * hash + Objects.hashCode(this.email);
        hash = 13 * hash + Objects.hashCode(this.fullName);
        hash = 13 * hash + Objects.hashCode(this.privilege);
        hash = 13 * hash + Objects.hashCode(this.status);
        hash = 13 * hash + Objects.hashCode(this.password);
        hash = 13 * hash + Objects.hashCode(this.lastPasswordChange);
        hash = 13 * hash + Objects.hashCode(this.signInHistory);
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
        final User other = (User) obj;
        if (!Objects.equals(this.login, other.login)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.fullName, other.fullName)) {
            return false;
        }
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (this.privilege != other.privilege) {
            return false;
        }
        if (this.status != other.status) {
            return false;
        }
        if (!Objects.equals(this.lastPasswordChange, other.lastPasswordChange)) {
            return false;
        }
        if (!Objects.equals(this.signInHistory, other.signInHistory)) {
            return false;
        }
        return true;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public UserPrivilege getPrivilege() {
        return privilege;
    }

    public void setPrivilege(UserPrivilege privilege) {
        this.privilege = privilege;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Timestamp getLastPasswordChange() {
        return lastPasswordChange;
    }

    public void setLastPasswordChange(Timestamp lastPasswordChange) {
        this.lastPasswordChange = lastPasswordChange;
    }

    public List<Timestamp> getSignInHistory() {
        return signInHistory;
    }

    public void setSignInHistory(List<Timestamp> signInHistory) {
        this.signInHistory = signInHistory;
    }
    
    
}
