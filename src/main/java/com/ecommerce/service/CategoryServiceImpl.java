package com.ecommerce.service;

import com.ecommerce.exceptions.APIException;
import com.ecommerce.exceptions.ResourceNotFoundException;
import com.ecommerce.model.Category;
import com.ecommerce.payload.CategoryDTO;
import com.ecommerce.payload.CategoryResponse;
import com.ecommerce.repositories.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
//        return categories;
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page<Category> categoryPage = categoryRepository.findAll(pageDetails);
        List<Category> categories = categoryPage.getContent();
//        List<Category> categories = categoryRepository.findAll();
        if(categories.isEmpty()){
            throw new APIException("Categories does not exist!");
        }
        List<CategoryDTO> categoryDTOList = categories.stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .toList();

        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setContent(categoryDTOList);
        //Now I have to set all the pagination metadata inside the categoryResponse
        categoryResponse.setPageNumber(categoryPage.getNumber());
        categoryResponse.setPageSize(categoryPage.getSize());
        categoryResponse.setTotalElements(categoryPage.getTotalElements());
        categoryResponse.setTotalPages(categoryPage.getTotalPages());
        categoryResponse.setLastPage(categoryPage.isLast());
        return categoryResponse;
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
//        category.setCategoryId(nextId++);
//        categories.add(category);
        Category category = modelMapper.map(categoryDTO, Category.class);
        Category categoryFound = categoryRepository.findByCategoryName(category.getCategoryName());
        if(categoryFound!=null){
            throw new APIException("Category "+ category.getCategoryName()+ " already exist!");
        }
        Category savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryDTO.class);
    }

    @Override
    public CategoryDTO deleteCategory(Long categoryID) {
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
//        return "Category of categoryId : "+ categoryID +" deleted Successfully !!";
        return modelMapper.map(category, CategoryDTO.class);
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId) {
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

        Category category = modelMapper.map(categoryDTO, Category.class);
        if(optionalCategory.isPresent()){
            Category presentCategory = optionalCategory.get();
            presentCategory.setCategoryName(category.getCategoryName());
            categoryRepository.save(presentCategory);
//            return presentCategory;
            return modelMapper.map(presentCategory, CategoryDTO.class);
        }else{
            throw new ResourceNotFoundException("Category", "categoryId", categoryId);
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category is not present inside the List");
        }
    }
}
