package com.barancewicz.recipewebapp.services;

import com.barancewicz.recipewebapp.commands.UserCommand;
import com.barancewicz.recipewebapp.converters.UserCommandToUser;
import com.barancewicz.recipewebapp.converters.UserToUserCommand;
import com.barancewicz.recipewebapp.domain.User;
import com.barancewicz.recipewebapp.exceptions.NotMatchingPswdException;
import com.barancewicz.recipewebapp.exceptions.UserAlreadyExistException;
import com.barancewicz.recipewebapp.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserCommandToUser userCommandToUser;
    private final UserToUserCommand userToUserCommand;
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, UserCommandToUser userCommandToUser, UserToUserCommand userToUserCommand) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userCommandToUser = userCommandToUser;
        this.userToUserCommand = userToUserCommand;
    }

    public List<User> listAll() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }

    public User getById(Long id) {
        return userRepository.findById(id).get();
    }

    public User saveOrUpdate(User domainObject) {
        if(domainObject.getPassword() != null){
            domainObject.setEncryptedPassword(passwordEncoder.encode(domainObject.getPassword()));
        }
        return userRepository.save(domainObject);
    }

    @Override
    public void delete(User object) {
        userRepository.delete(object);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserCommand findCommandByUsername(String username) {
        return userToUserCommand.convert(findByUsername(username));
    }

    private boolean usernameExist(String username) {
        return userRepository.findByUsername(username) != null;
    }
    @Transactional
    @Override
    public User registerNewUserAccount(UserCommand userDto)throws UserAlreadyExistException {
        if (usernameExist(userDto.getUsername())) {
            throw new UserAlreadyExistException(
                    "There is an account with that email address: "
                            +  userDto.getUsername());
        }
        return saveOrUpdate(userCommandToUser.convert(userDto));
    }
    @Transactional
    @Override
    public UserCommand changePswd(UserCommand userDto) {
        User user = null;
        if (userRepository.findById(userDto.getId()).isPresent()) {
            if (!userDto.getPassword().equals(userDto.getMatchingPassword())){
                throw new NotMatchingPswdException(
                        "Passwords are not the same");
            }
           log.debug("User found, updating password");
           user = userRepository.findById(userDto.getId()).get();
           user.setPassword(userDto.getPassword());
           user.setEncryptedPassword(passwordEncoder.encode(user.getPassword()));
        }
        return userToUserCommand.convert(userRepository.save(user));
    }
}
