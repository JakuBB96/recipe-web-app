package com.barancewicz.recipewebapp.converters;

import com.barancewicz.recipewebapp.commands.UserCommand;
import com.barancewicz.recipewebapp.domain.Role;
import com.barancewicz.recipewebapp.domain.User;
import com.barancewicz.recipewebapp.services.RoleService;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class UserCommandToUser implements Converter<UserCommand, User> {
    private final RoleService roleService;

    public UserCommandToUser(RoleService roleService) {
        this.roleService = roleService;
    }

    @Synchronized
    @Nullable
    @Override
    public User convert(UserCommand source) {
        if (source==null){
            return null;
        }
        final User user = new User();
        user.setId(source.getId());
        user.setFirstName(source.getFirstName());
        user.setLastName(source.getLastName());
        user.setPassword(source.getPassword());
        user.setUsername(source.getUsername());
        user.setAvatar("avatar.jpg");
        user.setRoles(Arrays.asList(roleService.findByRole("USER")));
        return user;
    }
}
