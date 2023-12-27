package com.ra.repository;

import com.ra.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMovieRepository extends JpaRepository<Movie , Long> {
    Page<Movie> findAllByNameContainingIgnoreCase(String name , Pageable pageable) ;
    Boolean existsByName(String name) ;
}
