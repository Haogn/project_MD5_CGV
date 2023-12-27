package com.ra.repository;

import com.ra.entity.Categories;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICategoriesRepository extends JpaRepository<Categories, Long> {

    Page<Categories> findAllByNameContainingIgnoreCase(String name, Pageable pageable) ;
    Boolean existsByName(String name) ;
    List<Categories> findAllById(Long id) ;
}
