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
 * @author ELOCK2
 */
public class DbFunctions {

    private static Connection c = null;

    public static Connection connect() {
        if (c == null) {
            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                c = DriverManager.getConnection("jdbc:mysql://localhost:3306/jsfblog?user=root&password=");
            } catch (Exception ex) {
                Logger.getLogger(DbFunctions.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return c;
    }

}
