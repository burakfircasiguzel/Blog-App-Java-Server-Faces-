/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.util.List;

/**
 *
 * @author ELOCK2
 */
public class Blog {
    private long id;
    private String title;
    private String detail;
    private String image;
    private List<Category> blogCategories;

    
    public Blog() {
    }

    public Blog(long id, String title, String detail) {
        this.id = id;
        this.title = title;
        this.detail = detail;
    }
    
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Category> getBlogCategories() {
        return blogCategories;
    }

    public void setBlogCategories(List<Category> blogCategories) {
        this.blogCategories = blogCategories;
    }

    @Override
    public String toString() {
        return "Blog{" + "id=" + id + ", title=" + title + ", detail=" + detail + ", image=" + image + ", blogCategories=" + blogCategories + '}';
    }

   
    
    
}
