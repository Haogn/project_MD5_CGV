package com.ra.repository;

import com.ra.entity.RoleName;
import com.ra.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepository extends JpaRepository<Roles , Long> {
    Roles findRolesById (Long id) ;
    Roles findRolesByRoleName(RoleName roleName) ;
}
