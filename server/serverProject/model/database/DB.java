/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package serverProject.model.database;

import java.sql.Connection;

/**
 * 
 * @author yeguo
 */
public interface DB {

   /**
     * This method save the openned connection in the pool
     *
     * @return Returns true if add has worked, false if it not
     */
    public Boolean saveConnection();

    /**
     * This method obtain a connection from the pool, or if there is not any
     * connections in the pool creates a new one
     *
     * @return Returns the connection to the database
     */
    public Connection getConnection();

}
