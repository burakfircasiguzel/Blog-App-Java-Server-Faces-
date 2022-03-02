/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Blog;
import entity.Category;
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
public class BlogDao {

    private CategoryDao categoryDao;
    private DocumentDao documentDao;

    public CategoryDao getCategoryDao() {
        if (this.categoryDao == null) {
            this.categoryDao = new CategoryDao();
        }
        return categoryDao;
    }

    public void setCategoryDao(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
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

    public List<Blog> getBlogs() {
        List<Blog> blogList = new ArrayList();
        try {
            Statement st = DbFunctions.connect().createStatement();;
            ResultSet rs = st.executeQuery("select * from blog order by id desc");
            while (rs.next()) {
                //System.out.println(rs.getString("name"));
                Blog tmp = new Blog();
                tmp.setTitle(rs.getString("title"));
                tmp.setDetail(rs.getString("detail"));
                tmp.setId(rs.getInt("id"));
                tmp.setBlogCategories(this.getCategoryDao().getBlogCategories(tmp.getId()));
                tmp.setImage(this.getDocumentDao().find((int) tmp.getId()));
                blogList.add(tmp);
            }

        } catch (SQLException ex) {
            Logger.getLogger(BlogDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return blogList;
    }

    public int create(Blog blog) {
        int blog_id = 0;
        try {
            PreparedStatement pst = DbFunctions.connect().prepareStatement("INSERT INTO blog (title, detail) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, blog.getTitle());
            pst.setString(2, blog.getDetail());
            pst.executeUpdate();

            ResultSet gk = pst.getGeneratedKeys();
            if (gk.next()) {
                blog_id = gk.getInt(1);
            }
            if (blog_id > 0) {
                for (Category c : blog.getBlogCategories()) {
                    pst = DbFunctions.connect().prepareStatement("INSERT INTO blog_category (blog_id, category_id) VALUES (?,?)");
                    pst.setInt(1, blog_id);
                    pst.setInt(2, c.getId());
                    pst.executeUpdate();
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return blog_id;
    }

    public int edit(Blog blog) {
        try {
            PreparedStatement pst = DbFunctions.connect().prepareStatement("UPDATE blog SET title='" + blog.getTitle() + "', detail='" + blog.getDetail() + "'  WHERE id=" + blog.getId());
            pst.executeUpdate();
            pst = DbFunctions.connect().prepareStatement("DELETE FROM blog_category WHERE blog_id=" + blog.getId());
            pst.executeUpdate();
            if (blog.getId() > 0) {
                for (Category c : blog.getBlogCategories()) {
                    pst = DbFunctions.connect().prepareStatement("INSERT INTO blog_category (blog_id, category_id) VALUES (?,?)");
                    pst.setInt(1, (int) blog.getId());
                    pst.setInt(2, c.getId());
                    pst.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(BlogDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return (int) blog.getId();
    }

    public void delete(Blog blog) {
        try {
            PreparedStatement pst = DbFunctions.connect().prepareStatement("DELETE FROM blog WHERE id=" + blog.getId());
            pst.executeUpdate();
            pst = DbFunctions.connect().prepareStatement("DELETE FROM blog_category WHERE blog_id=" + blog.getId());
            pst.executeUpdate();
            pst.close();
        } catch (SQLException ex) {
            Logger.getLogger(BlogDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
