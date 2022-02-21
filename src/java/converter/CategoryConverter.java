/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import dao.CategoryDao;
import entity.Category;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author ELOCK2
 */
@FacesConverter(value="categoryConverter")
public class CategoryConverter implements Converter {

    private CategoryDao categoryDao;

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uıc, String string) {
        return this.getCategoryDao().find(Integer.valueOf(string));
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uıc, Object o) {
        Category c = (Category) o;
        String a = String.valueOf(c.getId());
        return a;
    }

    public CategoryDao getCategoryDao() {
        if (this.categoryDao == null) {
            this.categoryDao = new CategoryDao();
        }
        return categoryDao;
    }

}
