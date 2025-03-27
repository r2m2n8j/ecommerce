package com.ecommerce.service;

import com.ecommerce.model.Category;
import com.ecommerce.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryRepository categoryRepository;

//    List<Category> categories = new ArrayList<>();
    private Long nextId = 1L;

    @Override
    public List<Category> getAllCategories() {
//        return categories;
        return categoryRepository.findAll();
    }

    @Override
    public void createCategory(Category category) {
//        category.setCategoryId(nextId++);
//        categories.add(category);
        categoryRepository.save(category);
    }

    @Override
    public String deleteCategory(Long categoryID) {
        /* for(int i=0;i<categories.size();i++){
            Category category = categories.get(i);
            if(Objects.equals(category.getCategoryId(), categoryID)){
                categories.remove(category);
                return "Category deleted successfully";
            }
        }*/

        /*List<Category> categories = categoryRepository.findAll();

        Category category = categories.stream()
                .filter(cate -> cate.getCategoryId().equals(categoryID))
                .findFirst()
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category of categoryId : "+categoryID +" Not found !"));
*/
        /*if(category == null) return "Category of categoryId : " +categoryID +" Not found !";
        */
        Category category = categoryRepository.findById(categoryID)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category of categoryId : "+categoryID + " Not found !"));
//        categories.remove(category);

        categoryRepository.delete(category);
        return "Category of categoryId : "+ categoryID +" deleted Successfully !!";
    }

    @Override
    public Category updateCategory(Category category, Long categoryId) {
//        List<Category> categories = categoryRepository.findAll();
//        Optional<Category> optionalCategory = categories.stream()
//                .filter(cate -> cate.getCategoryId().equals(categoryId))
//                .findFirst();
//
//        if(optionalCategory.isPresent()){
//            Category presentCategory = optionalCategory.get();
//            presentCategory.setCategoryName(category.getCategoryName());
//            categoryRepository.save(presentCategory);
//            return presentCategory;
//        }else {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category is not present inside the list");
//        }
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);

        if(optionalCategory.isPresent()){
            Category presentCategory = optionalCategory.get();
            presentCategory.setCategoryName(category.getCategoryName());
            categoryRepository.save(presentCategory);
            return presentCategory;
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category is not present inside the List");
        }
    }
}
