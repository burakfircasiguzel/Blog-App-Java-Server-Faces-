/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Blog;
import entity.Category;
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
public class CategoryDao {

    private DbConnection db;
    private Connection c;
    private DocumentDao documentDao;

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

    public DocumentDao getDocumentDao() {
        if (this.documentDao == null) {
            this.documentDao = new DocumentDao();
        }
        return documentDao;
    }

    public void setDocumentDao(DocumentDao documentDao) {
        this.documentDao = documentDao;
    }

    public List<Category> getCategories() {
        List<Category> categoryList = new ArrayList();
        try {
            Statement st = this.getC().createStatement();
            ResultSet rs = st.executeQuery("select * from category");
            while (rs.next()) {
                Category tmp = new Category(rs.getInt("id"), rs.getString("name"));
                categoryList.add(tmp);
            }

        } catch (SQLException ex) {
            Logger.getLogger(BlogDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return categoryList;
    }

    public Category find(int id) {
        Category c = null;

        try {
            Statement st = this.getC().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM category WHERE id=" + id);
            rs.next();
            c = new Category();
            c.setId(rs.getInt("id"));
            c.setName(rs.getString("name"));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return c;
    }

    List<Category> getBlogCategories(Long blog_id) {
        List<Category> blogCategories = new ArrayList<>();
        try {
            Statement st = this.getC().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM blog_category WHERE blog_id=" + blog_id);

            while (rs.next()) {
                blogCategories.add(this.find(rs.getInt("category_id")));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return blogCategories;
    }

    public Blog findBlog(int id) {
        Blog b = null;
        try {
            Statement st = this.getC().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM blog WHERE id=" + id);
            if (rs.next()) {
                b = new Blog();
                b.setTitle(rs.getString("title"));
                b.setDetail(rs.getString("detail"));
                b.setId(rs.getLong("id"));
                b.setBlogCategories(this.getBlogCategories(b.getId()));
                b.setImage(this.getDocumentDao().find((int) b.getId()));
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return b;
    }

    public List<Blog> getCategoryBlogs(Category category) {
        List<Blog> blogCategories = new ArrayList<>();
        try {
            Statement st = this.getC().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM blog_category WHERE category_id=" + category.getId());

            while (rs.next()) {
                blogCategories.add(this.findBlog(rs.getInt("blog_id")));
                //System.out.println(this.findBlog(rs.getInt("blog_id")).getTitle());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        //System.out.println(blogCategories.size());
        return blogCategories;
    }

    public void delete(Category category) {
        try {
            PreparedStatement pst = this.getC().prepareStatement("DELETE FROM blog_category WHERE blog_id=" + category.getId());
            pst.executeUpdate();
            pst = this.getC().prepareStatement("DELETE FROM category WHERE id=" + category.getId());
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void update(Category category) {
        try {
            PreparedStatement pst = this.getC().prepareStatement("UPDATE category SET name='" + category.getName() + "' WHERE id=" + category.getId());
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(BlogDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void insert(Category category) {
        try {
            PreparedStatement pst = this.getC().prepareStatement("INSERT INTO category (name) VALUES (?)");
            pst.setString(1, category.getName());
            pst.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
