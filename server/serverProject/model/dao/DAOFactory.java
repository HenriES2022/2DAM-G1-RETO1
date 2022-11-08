/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package serverProject.model.dao;

import java.util.ResourceBundle;

/**
 *
 * @author yeguo
 */
public abstract class DAOFactory {

    private static final String DB_TYPE = ResourceBundle.getBundle("serverProject.config").getString("model");
    private static final String MYSQL = "mysql";

    public static DAO getDAO() {
        if (DB_TYPE.equalsIgnoreCase(MYSQL)) {
            return new DAOImplementationMysql();
        }
        return null;
    }

}
