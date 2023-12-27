package com.ra.repository;

import com.ra.entity.Location;
import com.sun.mail.imap.protocol.BODY;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ILocationRepository extends JpaRepository<Location , Long> {
    Page<Location> findAllByNameContainingIgnoreCase(String name, Pageable pageable) ;
    Boolean existsByName (String name) ;
}
