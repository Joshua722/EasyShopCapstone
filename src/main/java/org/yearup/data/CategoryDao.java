package org.yearup.data;

import org.yearup.models.Category;
import org.yearup.models.Product;

import java.util.List;

public interface CategoryDao
{
    List<Category> getAllCategories();
    Category getById(int categoryId);
//    List<Product> getByProductId(int categoryId);
    Category create(Category category);
    void update(int categoryId, Category category);
    void delete(int categoryId);
}
