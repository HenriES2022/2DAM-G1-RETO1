/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package serverProject.model.database;

import java.sql.Connection;
import java.util.Stack;

/**
 *
 * @author yeguo
 */
public class DBImplPool implements DB, AutoCloseable {

    private Stack pool = new Stack();
    private Stack usingConnections = new Stack();
    private String url;
    private String user;
    private String password;
    
    public DBImplPool(String url, String user, String password, Stack pool){
        this.url = url;
        this.user = user;
        this.password = password;
        this.pool = pool;
    }

    @Override
    public synchronized Boolean saveConnection() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public synchronized Connection getConnection() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void close() throws Exception {
        this.saveConnection();
    }

}
