/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package serverProject.model.database;

import java.util.ResourceBundle;

/**
 *
 * @author yeguo
 */
public abstract class DBFactory {

    private static final String DB_TYPE = ResourceBundle.getBundle("serverProject.config").getString("model");
    private static final String MYSQL = "mysql";
    
    /**
     * This method is to get the connection pool implementation
     * @return Returns null if the type of the implementation is not the one of the condition, <br>
     * or the corresponding implementation of DB interface
     */
    public static DB getDB() {
        if (DB_TYPE.equalsIgnoreCase(MYSQL)) {
            return new DBImplPoolMysql();
        }
        return null;
    }

}
