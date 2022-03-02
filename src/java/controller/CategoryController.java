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

    public String edit(Category c) {
        this.category = c;
        return "edit-category";
    }

    public List<Blog> getCategoryBlogList() {
        this.categoryBlogList = this.getCategoryDao().getCategoryBlogs(this.getCategory());
        if (this.getCategoryDao().getCategoryBlogs(this.getCategory()).size() < 1 || this.getCategoryDao().getCategoryBlogs(this.getCategory()) == null) {
            this.category.setNotFoundRelatedBlog(true);
        }
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

    public String delete(Category category) {
        this.category = category;
        this.categoryDao.delete(category);
        clearCategory();
        return "categories.xhtml";
    }

    public void clearCategory() {
        this.category = new Category();
    }

    public String create() {
        this.categoryDao.insert(this.category);
        clearCategory();
        return "categories.xhtml";
    }

    public String selectCategory(Category category) {
        this.category = category;
        return "category";
    }

    public String update(Category category) {
        this.categoryDao.update(this.category);
        clearCategory();
        return "categories.xhtml";
    }
}
