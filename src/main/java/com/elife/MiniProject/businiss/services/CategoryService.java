package com.elife.MiniProject.businiss.services;

import com.elife.MiniProject.dao.entities.Category;
import java.util.List;

public interface CategoryService {
    Category getCategoryById(Long id);
    List<Category> getAllCategories();
    Category saveCategory(Category category);
    void deleteCategory(Long id);
    Category updateCategory(Long id, Category category);
}
