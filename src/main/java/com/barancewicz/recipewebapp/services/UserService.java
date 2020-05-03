package com.barancewicz.recipewebapp.services;

import com.barancewicz.recipewebapp.commands.UserCommand;
import com.barancewicz.recipewebapp.domain.User;
import com.barancewicz.recipewebapp.exceptions.UserAlreadyExistException;

import java.util.List;

public interface UserService extends CrudService<User>{
    User findByUsername(String username);
    UserCommand findUserById(Long id);
    UserCommand findCommandByUsername(String username);
    public List<UserCommand> listAllUserCommand();
    User registerNewUserAccount(UserCommand userDto) throws UserAlreadyExistException;
    UserCommand changePswd(UserCommand userDto);
}