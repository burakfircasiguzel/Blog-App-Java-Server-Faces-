/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 * @author Burak Fircasiguzel < www.github.com/burakfircasiguzel >
 */
public class User {
    private int id;
    private String name;
    private String password;
    private boolean auth = false;

    public User() {
    }

    public User(String name, String password, boolean auth) {
        this.name = name;
        this.password = password;
        this.auth = auth;
    }

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAuth() {
        return auth;
    }

    public void setAuth(boolean auth) {
        this.auth = auth;
    }
    
    
}
