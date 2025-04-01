package com.ecommerce.service;

import com.ecommerce.exceptions.APIException;
import com.ecommerce.exceptions.ResourceNotFoundException;
import com.ecommerce.model.Category;
import com.ecommerce.payload.CategoryDTO;
import com.ecommerce.payload.CategoryResponse;
import com.ecommerce.repositories.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

//    List<Category> categories = new ArrayList<>();
    private Long nextId = 1L;

    @Override
    public CategoryResponse getAllCategories() {
//        return categories;
        List<Category> categories = categoryRepository.findAll();
        if(categories.isEmpty()){
            throw new APIException("Categories does not exist!");
        }
        List<CategoryDTO> categoryDTOList = categories.stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .toList();

        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setContent(categoryDTOList);
        return categoryResponse;
    }

    @Override
    public void createCategory(Category category) {
//        category.setCategoryId(nextId++);
//        categories.add(category);
        System.out.println("Inside the Service 1");
        Category savedCategory = categoryRepository.findByCategoryName(category.getCategoryName());
        if(savedCategory!=null){
            System.out.println("Inside the service if block 2");
            throw new APIException("Category "+ category.getCategoryName()+ " already exist!");
        }
        System.out.println("Inside the service 3");
        categoryRepository.save(category);
        System.out.println("Inside the service 4");
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
                .orElseThrow(()-> new ResourceNotFoundException("Category", "categoryId", categoryID));
//                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category of categoryId : "+categoryID + " Not found !"));
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
            throw new ResourceNotFoundException("Category", "categoryId", categoryId);
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category is not present inside the List");
        }
    }
}
