package com.ra.service.impl;

import com.ra.entity.RoleName;
import com.ra.entity.Roles;
import com.ra.repository.IRoleRepository;
import com.ra.service.interfaces.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleSerive implements IRoleService {
    @Autowired
    private IRoleRepository roleRepository ;

    @Override
    public Roles findByRoleName(RoleName roleName) {
        return roleRepository.findRolesByRoleName(roleName);
    }
}
