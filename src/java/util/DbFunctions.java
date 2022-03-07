/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Burak Fircasiguzel < www.github.com/burakfircasiguzel >
 */
public class DbFunctions {

    private static Connection c = null;
    private final static String DB_URL = "localhost"; //db url default localhost or 127.0.0.1
    private final static String DB_PORT = "3306";  //default MySQL port 3306
    private final static String DB_NAME = "jsfblog"; //database name in .sql file
    private final static String DB_USERNAME = "root"; //database username: default root
    private final static String DB_PASSWORD = ""; //database password
    
    public static Connection connect() {
        if (c == null) {
            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                c = DriverManager.getConnection("jdbc:mysql://"+DB_URL+":"+DB_PORT+"/"+DB_NAME+"?user="+DB_USERNAME+"&password="+DB_PASSWORD+"");
            } catch (Exception ex) {
                Logger.getLogger(DbFunctions.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return c;
    }

}
