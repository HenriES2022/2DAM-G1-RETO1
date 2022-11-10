/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package serverProject.model.dao;

import java.util.ResourceBundle;

/**
 * This is the dao factory where dao implementations are going to be created
 * @author yeguo
 */
public abstract class DAOFactory {
    /**
     * The database type is going to use
     */
    private static final String DB_TYPE = ResourceBundle.getBundle("serverProject.configServer").getString("model");
    /**
     * The database we are expecting
     */
    private static final String MYSQL = "mysql";

    /**
     * Creates dao implementations
     * @return Returns the dao implementation if and only if the database type and the expecting database are equals <br>
     * else returns null
     */
    public static DAO getDAO() {
        if (DB_TYPE.equalsIgnoreCase(MYSQL)) {
            return new DAOImplementationMysql();
        }
        return null;
    }

}
