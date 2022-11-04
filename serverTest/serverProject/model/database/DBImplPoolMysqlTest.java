/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverProject.model.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author iorit
 */
public class DBImplPoolMysqlTest {

    private static DB poolImpl = null;
    private static Connection conex;
    private static final String SELECT_TEST_CONNECTION = "SELECT * FROM user";

    public DBImplPoolMysqlTest() {
        assertNotNull("The implementation of the pool must not be null", poolImpl);
    }

    @BeforeClass
    public static void setUpClass() {
        poolImpl = DBFactory.getDB();
    }

    @Before
    public void setUp() {
        conex = poolImpl.getConnection();
    }

    @After
    public void tearDown() {
        try {
            conex.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBImplPoolMysqlTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Test of saveConnection method, of class DBImplPoolMysql.
     */
    @Test
    public void testSaveConnection() {
        try {
            assertEquals("The return must be true", true, poolImpl.saveConnection());
            PreparedStatement stmt = conex.prepareStatement(SELECT_TEST_CONNECTION);
            ResultSet rs = stmt.executeQuery();

        } catch (SQLException ex) {
            Logger.getLogger(DBImplPoolMysqlTest.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                
                assertTrue("The connection must be closed", conex.isClosed());
            } catch (SQLException ex) {
                Logger.getLogger(DBImplPoolMysqlTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    /**
     * Test of getConnection method, of class DBImplPoolMysql.
     */
    @Test
    public void testGetConnection() {
        try {
            ResultSet rs = null;
            PreparedStatement stmt = null;

            assertNotNull("The connection must not be null", conex);
            assertTrue("The connection hast to be opened", !conex.isClosed());

            stmt = conex.prepareStatement(SELECT_TEST_CONNECTION);
            rs = stmt.executeQuery();

        } catch (SQLException ex) {
            Logger.getLogger(DBImplPoolMysqlTest.class.getName()).log(Level.SEVERE, null, ex);
            fail("The connection is not correctly obtained");
        }
    }

    /**
     * Test of close method, of class DBImplPoolMysql.
     */
    @Test
    public void testClose() throws Exception {

    }

}
