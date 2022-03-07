/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Blog;
import entity.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.DbFunctions;

/**
 *
 * @author Burak Fircasiguzel < www.github.com/burakfircasiguzel >
 */
public class UserDao {

    public boolean loginControl(User user) {
        try {
            Statement st = DbFunctions.connect().createStatement();
            // using MD5 for password encryption
            ResultSet rs = st.executeQuery("SELECT * FROM admin WHERE username='"+user.getName()+"' AND password=md5('"+user.getPassword()+"') ");
            while (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(BlogDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
