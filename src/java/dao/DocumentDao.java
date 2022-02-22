/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Document;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.DbConnection;

/**
 *
 * @author ELOCK2
 */
public class DocumentDao {

    private DbConnection db;
    private Connection c;

    public DocumentDao() {
    }
    
    public DbConnection getDb() {
        if (this.db == null) {
            this.db = new DbConnection();
        }
        return db;
    }

    public void setDb(DbConnection db) {
        this.db = db;
    }

    public Connection getC() {
        if (this.c == null) {
            this.c = getDb().connect();
        }
        return c;
    }

    public void setC(Connection c) {
        this.c = c;
    }

    public List<Document> findAll() {
        List<Document> dList = new ArrayList();
        try {
            PreparedStatement pst = this.getC().prepareStatement("SELECT * FROM document");
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Document d = new Document();
                d.setId(rs.getInt("id"));
                d.setFilePath(rs.getString("path"));
                d.setFileName(rs.getString("name"));
                d.setFileType(rs.getString("type"));
                dList.add(d);
            }

        } catch (Exception e) {
        }
        return dList;
    }

    public void insert(Document d) {
        String query = "INSERT INTO document (path, name, type) VALUES (?,?,?)";
        try {
            PreparedStatement pst = this.getC().prepareStatement(query);
            pst.setString(1, d.getFilePath());
            pst.setString(2, d.getFileName());
            pst.setString(3, d.getFileType());
            pst.executeUpdate();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public int insertWithBlogId(Document d, int blogId) {
        String query = "INSERT INTO document (path, name, type, blog_id) VALUES (?,?,?,?)";
        int document_id = 0;
        try {
            PreparedStatement pst = this.getC().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, d.getFilePath());
            pst.setString(2, d.getFileName());
            pst.setString(3, d.getFileType());
            pst.setInt(4, blogId);
            pst.executeUpdate();
            System.out.println("document_id: " + document_id);
            ResultSet gk = pst.getGeneratedKeys();
            if (gk.next()) {
                document_id = gk.getInt(1);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return document_id;
    }
    
    public String find(int blogId){
        String s = "";
        try {
            PreparedStatement pst = this.getC().prepareStatement("SELECT * FROM document WHERE blog_id="+blogId);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                s = rs.getString("name"); ;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DocumentDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return s;
    }

}
