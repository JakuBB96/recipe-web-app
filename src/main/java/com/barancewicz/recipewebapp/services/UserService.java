package com.barancewicz.recipewebapp.services;

import com.barancewicz.recipewebapp.commands.UserCommand;
import com.barancewicz.recipewebapp.domain.User;
import com.barancewicz.recipewebapp.exceptions.UserAlreadyExistException;

public interface UserService extends CrudService<User>{
    User findByUsername(String username);
    UserCommand findCommandByUsername(String username);
    User registerNewUserAccount(UserCommand userDto) throws UserAlreadyExistException;
    UserCommand changePswd(UserCommand userDto);
}