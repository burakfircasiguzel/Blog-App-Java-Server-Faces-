/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.CategoryDao;
import entity.Blog;
import entity.Category;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author ELOCK2
 */
@SessionScoped
@ManagedBean(name = "CategoryController", eager = true)
public class CategoryController implements Serializable {

    private Category category;
    private CategoryDao categoryDao;
    private List<Category> categoryList;
    private List<Blog> categoryBlogList;
    
    
    
    public CategoryController() {
        this.categoryList = new ArrayList();
        this.categoryDao = new CategoryDao();
    }

    public CategoryController(CategoryDao categoryDao, List<Category> categoryList) {
        this.categoryDao = categoryDao;
        this.categoryList = categoryList;
    }

    public Category getCategory() {
        if (this.category == null) {
            this.category = new Category();
        }
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public CategoryDao getCategoryDao() {
        if (this.categoryDao == null) {
            this.categoryDao = new CategoryDao();
        }
        return categoryDao;
    }

    public void setCategoryDao(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    public List<Category> getCategoryList() {
        this.categoryList = this.getCategoryDao().getCategories();
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public String update(Category c) {
        this.category = c;
        System.out.println(c.toString());
        return "category?faces-redirect=true";
    }

    public List<Blog> getCategoryBlogList() {
        this.categoryBlogList = this.getCategoryDao().getCategoryBlogs(this.getCategory());
        return categoryBlogList;
    }

    public void setCategoryBlogList(List<Blog> categoryBlogList) {
        this.categoryBlogList = categoryBlogList;
    }

    public String onload() {
        if (this.getCategory().getName() == null) {
            return "index";
        } else {
            return "category";
        }
    }
}
