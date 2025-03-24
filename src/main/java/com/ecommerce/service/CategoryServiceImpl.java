package com.ecommerce.service;

import com.ecommerce.model.Category;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class CategoryServiceImpl implements CategoryService{
    List<Category> categories = new ArrayList<>();
    private Long nextId = 1L;
    @Override
    public List<Category> getAllCategories() {
        return categories;
    }

    @Override
    public void createCategory(Category category) {
        category.setCategoryId(nextId++);
        categories.add(category);
    }

    @Override
    public String deleteCategory(Long categoryID) {
        /*for(int i=0;i<categories.size();i++){
            Category category = categories.get(i);
            if(Objects.equals(category.getCategoryId(), categoryID)){
                categories.remove(category);
                return "Category deleted successfully";
            }
        }*/
        Category category = categories.stream()
                .filter(cate -> cate.getCategoryId().equals(categoryID))
                .findFirst()
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category of categoryId : "+categoryID +" Not found !"));

        /*if(category == null) return "Category of categoryId : "+categoryID +" Not found !";
        */
        categories.remove(category);
        return "Category of categoryId : "+ categoryID +" deleted Successfully !!";
    }
}
