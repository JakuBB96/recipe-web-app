package com.barancewicz.recipewebapp.services;

import com.barancewicz.recipewebapp.domain.Role;

import java.util.List;

public interface RoleService extends CrudService<Role>{
    public Role findByRole(String roleName);
}
