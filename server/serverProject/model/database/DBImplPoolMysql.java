/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package serverProject.model.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Stack;
import java.util.logging.Logger;

/**
 *
 * @author yeguo This class is the implementation of the connection pool
 */
public class DBImplPoolMysql implements DB {

    /**
     * The stack where the connections are saved
     */
    private static Stack pool = new Stack();
    /**
     * The connection object
     */
    private Connection conex;
    /**
     * The url of the database readed from a properties file
     */
    private static final String URL = ResourceBundle.getBundle("serverProject.configServer").getString("url");
    /**
     * The user of the database readed from a properties file
     */
    private static final String USER = ResourceBundle.getBundle("serverProject.configServer").getString("user");
    /**
     * The password of the database readed from a properties file
     */
    private static final String PASSWORD = ResourceBundle.getBundle("serverProject.configServer").getString("pass");
    /**
     * The log object for saving logs of this class
     */
    private static final Logger LOG = Logger.getLogger("serverProject.model.database.DBImplPoolMysql");

    /**
     * This method ssave the openned connection in the pool
     *
     * @return Returns true if add has worked, false if it not
     */
    @Override
    public synchronized Boolean saveConnection() {
        LOG.info("Saving the connection");
        return pool.add(conex);
    }

    /**
     * This method obtain a connection from the pool, or if there is not any
     * connections in the pool creates a new one
     *
     * @return Returns the connection to the database
     */
    @Override
    public synchronized Connection getConnection() {
        LOG.info("Obtaining a connection");
        try {
            if (pool.isEmpty()) {
                conex = DriverManager.getConnection(URL, USER, PASSWORD);
                return conex;
            }

            conex = (Connection) pool.pop();
        } catch (SQLException e) {
            LOG.severe(e.getMessage());
        }
        return conex;
    }

}
