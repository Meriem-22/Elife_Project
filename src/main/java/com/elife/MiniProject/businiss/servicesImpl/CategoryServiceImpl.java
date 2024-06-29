package com.elife.MiniProject.businiss.servicesImpl;

import com.elife.MiniProject.businiss.services.CategoryService;
import com.elife.MiniProject.dao.entities.Category;
import com.elife.MiniProject.dao.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category getCategoryById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        return category.orElse(null);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

   @Override
    public Category updateCategory(Long id, Category newCategory) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isPresent()) {
            Category existingCategory = optionalCategory.get();
            existingCategory.setName(newCategory.getName());

            if (existingCategory.getTrainings() == null) {
                existingCategory.setTrainings(new HashSet<>());
            }

            existingCategory.getTrainings().clear();
            if (newCategory.getTrainings() != null) {
                existingCategory.getTrainings().addAll(newCategory.getTrainings());
            }

            return categoryRepository.save(existingCategory);
        }
        return null;
    }

}
