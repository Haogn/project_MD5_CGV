package com.ra.mapper;

import com.ra.dto.request.CategoryRequest;
import com.ra.dto.response.CategoryResponse;
import com.ra.entity.Categories;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
    public CategoryResponse toCategoryResponse(Categories categories) {
        return CategoryResponse.builder()
                .id(categories.getId())
                .name(categories.getName())
                .build();
    }

    public Categories toEntity(CategoryRequest categoryRequest) {
        return Categories.builder()
                .name(categoryRequest.getName())
                .build();
    }
}
