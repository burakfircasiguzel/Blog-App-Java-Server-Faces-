/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.util.List;

/**
 *
 * @author Burak Fircasiguzel < www.github.com/burakfircasiguzel >
 */
public class Category {

    private int id;
    private String name;
    private List<Blog> categoryBlogs;
    private boolean notFoundRelatedBlog = false;
    private int blogCount = 0;
    
    
    public Category() {
    }

    public Category(int id, String name) {
        this.id = id;
        this.name = name;
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Category other = (Category) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return name;
    }

    public List<Blog> getCategoryBlogs() {
        return categoryBlogs;
    }

    public void setCategoryBlogs(List<Blog> categoryBlogs) {
        this.categoryBlogs = categoryBlogs;
    }


    public boolean isNotFoundRelatedBlog() {
        return notFoundRelatedBlog;
    }

    public void setNotFoundRelatedBlog(boolean notFoundRelatedBlog) {
        this.notFoundRelatedBlog = notFoundRelatedBlog;
    }

    public int getBlogCount() {
        return blogCount;
    }

    public void setBlogCount(int blogCount) {
        this.blogCount = blogCount;
    }
    
    
}
