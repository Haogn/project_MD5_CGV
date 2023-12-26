package com.ra.service.impl;

import com.ra.dto.request.CategoryRequest;
import com.ra.dto.response.CategoryResponse;
import com.ra.entity.Categories;
import com.ra.exception.CustomException;
import com.ra.mapper.CategoryMapper;
import com.ra.repository.ICategoriesRepository;
import com.ra.service.interfaces.ICategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CategoryService implements ICategoriesService {

    @Autowired
    private ICategoriesRepository categoriesRepository;

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public Page<CategoryResponse> findAllCategory(String name, Pageable pageable) {
        Page<Categories> categories;
        if (name == null || name.isEmpty()) {
            categories = categoriesRepository.findAll(pageable);
        } else {
            categories = categoriesRepository.findAllByName(name, pageable);
        }
        return categories.map(item -> categoryMapper.toCategoryResponse(item));
    }

    @Override
    public CategoryResponse findById(Long id) throws CustomException {
        Categories categories = categoriesRepository.findById(id).orElseThrow(() -> new CustomException("Categories not found"));
        return categoryMapper.toCategoryResponse(categories);
    }

    @Override
    public CategoryResponse save(CategoryRequest categoryRequest) throws CustomException {
        if (categoriesRepository.existsByName(categoryRequest.getName())) {
            throw new CustomException("Exists CategoriesName");
        }
        Categories categories = categoriesRepository.save(categoryMapper.toEntity(categoryRequest));
        return categoryMapper.toCategoryResponse(categories);
    }

    @Override
    public CategoryResponse update(Long id, CategoryRequest categoryRequest) throws CustomException {
        Categories categories = categoriesRepository.findById(id).orElseThrow(() -> new CustomException("Categorise Not Found"));
        if (!categoryRequest.getName().equalsIgnoreCase(categories.getName()) && categoriesRepository.existsByName(categoryRequest.getName())) {
            throw new CustomException("Exits CategoryName");
        }
        categories.setId(id);
        categories.setName(categoryRequest.getName());
        return categoryMapper.toCategoryResponse(categoriesRepository.save(categories));
    }

    @Override
    public void delete(Long id) throws CustomException {
        Categories categories = categoriesRepository.findById(id).orElseThrow(() -> new CustomException("Categorise Not Found"));
        if (categories != null) {
            categoriesRepository.deleteById(id);
        }
    }
}
