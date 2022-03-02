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
import util.DbFunctions;

/**
 *
 * @author ELOCK2
 */
public class CategoryDao {

    private DocumentDao documentDao;

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
            Statement st = DbFunctions.connect().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM category ORDER BY id DESC");
            Category tmp;
            while (rs.next()) {
                tmp = new Category();
                tmp.setId(rs.getInt("id"));
                tmp.setName(rs.getString("name"));
                tmp.setBlogCount(getBlogCount(tmp.getId()));
                categoryList.add(tmp);
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(BlogDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return categoryList;
    }

    public Category find(int id) {
        Category c = null;

        try {
            Statement st = DbFunctions.connect().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM category WHERE id=" + id);
            rs.next();
            c = new Category();
            c.setId(rs.getInt("id"));
            c.setName(rs.getString("name"));
            rs.close();
            st.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return c;
    }

    List<Category> getBlogCategories(Long blog_id) {
        List<Category> blogCategories = new ArrayList<>();
        try {
            Statement st = DbFunctions.connect().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM blog_category WHERE blog_id=" + blog_id);
            while (rs.next()) {
                blogCategories.add(this.find(rs.getInt("category_id")));
            }
            rs.close();
            st.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return blogCategories;
    }

    public Blog findBlog(int id) {
        Blog b = null;
        try {
            Statement st = DbFunctions.connect().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM blog WHERE id=" + id);
            if (rs.next()) {
                b = new Blog();
                b.setTitle(rs.getString("title"));
                b.setDetail(rs.getString("detail"));
                b.setId(rs.getLong("id"));
                b.setBlogCategories(this.getBlogCategories(b.getId()));
                b.setImage(this.getDocumentDao().find((int) b.getId()));
            }
            rs.close();
            st.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return b;
    }

    public List<Blog> getCategoryBlogs(Category category) {
        List<Blog> blogCategories = new ArrayList<>();
        try {
            Statement st = DbFunctions.connect().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM blog_category WHERE category_id=" + category.getId());
            while (rs.next()) {
                blogCategories.add(this.findBlog(rs.getInt("blog_id")));
                //System.out.println(this.findBlog(rs.getInt("blog_id")).getTitle());
            }
            rs.close();
            st.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        //System.out.println(blogCategories.size());
        return blogCategories;
    }

    public int getBlogCount(int categoryId) {
        int count = 0;
        try {
            Statement st = DbFunctions.connect().createStatement();
            ResultSet rs = st.executeQuery("SELECT COUNT(*) as c FROM blog_category WHERE category_id=" + categoryId);
            while (rs.next()) {
                count = rs.getInt("c");
            }
            rs.close();
            st.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return count;
    }

    public void delete(Category category) {
        try {
            PreparedStatement pst = DbFunctions.connect().prepareStatement("DELETE FROM blog_category WHERE blog_id=" + category.getId());
            pst.executeUpdate();
            pst = DbFunctions.connect().prepareStatement("DELETE FROM category WHERE id=" + category.getId());
            pst.executeUpdate();
            pst.close();
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void update(Category category) {
        try {
            PreparedStatement pst = DbFunctions.connect().prepareStatement("UPDATE category SET name='" + category.getName() + "' WHERE id=" + category.getId());
            pst.executeUpdate();
            pst.close();
        } catch (SQLException ex) {
            Logger.getLogger(BlogDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void insert(Category category) {
        try {
            PreparedStatement pst = DbFunctions.connect().prepareStatement("INSERT INTO category (name) VALUES (?)");
            pst.setString(1, category.getName());
            pst.executeUpdate();
            pst.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
