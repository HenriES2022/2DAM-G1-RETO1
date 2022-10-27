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
     * This method will save the connections into a pool to later use
     *
     * @return Boolean
     */
    public Boolean saveConnection();

    /**
     * This method returns a connection already opened from the pool or creates
     * a new one if no connection is available
     *
     * @return Connection
     */
    public Connection getConnection();

}
