package com.ra.controller;

import com.ra.dto.request.CategoryRequest;
import com.ra.exception.CustomException;
import com.ra.service.interfaces.ICategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/categories")
public class CategoriesController {

    @Autowired
    private ICategoriesService categoriesService ;

    @GetMapping
    public ResponseEntity<?> getAllCategories(@RequestParam(defaultValue = "") String name ,
                                              @PageableDefault(size =2, page = 0, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return new ResponseEntity<>(categoriesService.findAllCategory(name, pageable), HttpStatus.OK) ;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id ) throws CustomException {
        return new ResponseEntity<>(categoriesService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> createCategory(@RequestBody CategoryRequest categoryRequest) throws CustomException {
        return new ResponseEntity<>(categoriesService.save(categoryRequest), HttpStatus.CREATED) ;
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> updateCategory(@PathVariable Long id , @RequestBody CategoryRequest categoryRequest) throws CustomException {
       return new ResponseEntity<>(categoriesService.update(id, categoryRequest), HttpStatus.OK) ;
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id ) throws CustomException {
        categoriesService.delete(id);
        String successMessage = "Category deleted successfully.";
         return new ResponseEntity<>(successMessage, HttpStatus.OK);
    }
}
