/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverProject.model.database;

import java.sql.Connection;
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
    
    public DBImplPoolMysqlTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of saveConnection method, of class DBImplPoolMysql.
     */
    @Test
    public void testSaveConnection() {
        System.out.println("saveConnection");
        DBImplPoolMysql instance = new DBImplPoolMysql();
        Boolean expResult = null;
        Boolean result = instance.saveConnection();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    /**
     * Test of getConnection method, of class DBImplPoolMysql.
     */
    @Test
    public void testGetConnection() {
        System.out.println("getConnection");
        DBImplPoolMysql instance = new DBImplPoolMysql();
        Connection expResult = null;
        Connection result = instance.getConnection();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    /**
     * Test of close method, of class DBImplPoolMysql.
     */
    @Test
    public void testClose() throws Exception {
        System.out.println("close");
        DBImplPoolMysql instance = new DBImplPoolMysql();
        instance.close();
        fail("The test case is a prototype.");
    }
    
}
