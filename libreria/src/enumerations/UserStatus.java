/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enumerations;

/**
 *The enumeration where we indicate the user status
 * <ul>
 *  <li>ENABLED - If the user is active so can be used in the application</li>
 * <li>DISABLED - If the user is inactive so can't be used in the application</li>
 * </ul>
 *
 * @author iorit
 */
public enum UserStatus {
    /**
     * user is active so can be used in the application
     */
    ENABLED, 
    /**
     * user is inactive so can't be used in the application
     */
    DISABLED
}
