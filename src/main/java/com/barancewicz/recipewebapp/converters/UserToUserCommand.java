package com.barancewicz.recipewebapp.converters;

import com.barancewicz.recipewebapp.commands.UserCommand;
import com.barancewicz.recipewebapp.domain.User;
import com.barancewicz.recipewebapp.services.RoleService;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class UserToUserCommand implements Converter<User, UserCommand> {
    private final RoleService roleService;

    public UserToUserCommand(RoleService roleService) {
        this.roleService = roleService;
    }

    @Synchronized
    @Nullable
    @Override
    public UserCommand convert(User source) {
        if (source==null){
            return null;
        }
        final UserCommand user = new UserCommand();
        user.setId(source.getId());
        user.setFirstName(source.getFirstName());
        user.setLastName(source.getLastName());
        user.setPassword(source.getPassword());
        user.setUsername(source.getUsername());
        user.setAvatar("avatar.jpg");
        return user;
    }
}
