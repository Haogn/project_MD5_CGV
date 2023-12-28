package com.ra.service.interfaces;

import com.ra.dto.request.CategoryRequest;
import com.ra.dto.response.CategoryResponse;
import com.ra.entity.Categories;
import com.ra.exception.CustomException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICategoriesService {
    Page<CategoryResponse> findAllCategory(String name , Pageable pageable) ;
    CategoryResponse findById(Long id) throws CustomException;
    CategoryResponse save(CategoryRequest categoryRequest) throws CustomException;
    CategoryResponse update(Long id , CategoryRequest categoryRequest) throws CustomException;

}
