package com.barancewicz.recipewebapp.services;

import com.barancewicz.recipewebapp.domain.Role;
import com.barancewicz.recipewebapp.repositories.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> listAll() {
        List<Role> roles = new ArrayList<>();
        roleRepository.findAll().forEach(roles::add);
        return roles;
    }

    public Role getById(Long id) {
        return roleRepository.findById(id).get();
    }
    public Role saveOrUpdate(Role domainObject) {
        return roleRepository.save(domainObject);
    }
    public Role findByRole(String roleName){
       return listAll()
                .stream()
                .filter(role -> roleName.equals(role.getRole()))
                .findFirst()
                .orElseThrow(()-> new NullPointerException("Role not found"));
    }
    @Override
    public void delete(Role object) {
        roleRepository.delete(object);
    }

    @Override
    public void deleteById(Long id) {
        roleRepository.deleteById(id);
    }
}

