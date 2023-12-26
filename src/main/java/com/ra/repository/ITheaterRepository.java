package com.ra.repository;

import com.ra.entity.Theater;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITheaterRepository extends JpaRepository<Theater, Long> {

    Page<Theater> findAllByName (String name, Pageable pageable) ;
    Boolean existsByName (String name) ;
}
