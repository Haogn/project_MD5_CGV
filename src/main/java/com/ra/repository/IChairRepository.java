package com.ra.repository;

import com.ra.entity.Chair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IChairRepository extends JpaRepository<Chair, Long> {
    Boolean existsByName (String name) ;
}
