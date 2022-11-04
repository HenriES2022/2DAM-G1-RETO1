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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yeguo
 */
public class DBImplPoolMysql implements DB {

    private static Stack pool = new Stack();
    private static final Logger LOG = Logger.getLogger("serverProject.model.database.DBImplPoolMysql");
    private static final String URL = ResourceBundle.getBundle("serverProject.config").getString("url");
    private static final String USER = ResourceBundle.getBundle("serverProject.config").getString("user");
    private static final String PASSWORD = ResourceBundle.getBundle("serverProject.config").getString("pass");
    private Connection conex;
    @Override
    public synchronized Boolean saveConnection() {
        return pool.add(conex);
    }

    @Override
    public synchronized Connection getConnection() {
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
