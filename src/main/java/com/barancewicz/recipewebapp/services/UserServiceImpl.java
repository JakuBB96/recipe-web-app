package com.barancewicz.recipewebapp.services;

import com.barancewicz.recipewebapp.commands.UserCommand;
import com.barancewicz.recipewebapp.converters.UserCommandToUser;
import com.barancewicz.recipewebapp.domain.User;
import com.barancewicz.recipewebapp.exceptions.UserAlreadyExistException;
import com.barancewicz.recipewebapp.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserCommandToUser userCommandToUser;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, UserCommandToUser userCommandToUser) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userCommandToUser = userCommandToUser;
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
}
