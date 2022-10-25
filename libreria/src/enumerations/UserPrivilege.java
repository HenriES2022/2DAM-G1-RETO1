/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enumerations;

/**
 * This enumeration indicates the possible values of the user privileges
 * <ul>
 * <li>USER - If the user is a common user</li>
 * <li>ADMIN - If the user is an administrator</li>
 * </ul>
 *
 * @author iorit
 */
public enum UserPrivilege {
    /**
     * User is a common user
     */
    USER,
    /**
     * User is an administrator
     */
    ADMIN
}
