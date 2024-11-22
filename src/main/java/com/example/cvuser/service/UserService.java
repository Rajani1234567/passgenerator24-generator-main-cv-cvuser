package com.example.cvuser.service;

import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.example.cvuser.model.UserModel;
import com.example.cvuser.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) 
    {
        this.userRepository = userRepository;
    }

    public UserModel registerUser(String login, String password, String email) 
    {
    if (login == null || password == null || email == null) 
    {
        throw new IllegalArgumentException("Please fill all fields.");
    }

    if (userRepository.existsByLogin(login) && (userRepository.existsByEmail(email))) 
    {
        throw new IllegalArgumentException("There already exists an account with this username and email.");
    }

    if (userRepository.existsByEmail(email)) 
    {
        throw new IllegalArgumentException("This email address is already in use.");
    }

    if (userRepository.existsByLogin(login)) 
    {
        throw new IllegalArgumentException("This username is already taken.");
    }

    try {
        UserModel userModel = new UserModel();
        userModel.setLogin(login);
        userModel.setPassword(password);
        userModel.setEmail(email);

        return userRepository.save(userModel);
    } catch (DataIntegrityViolationException e) {
        throw new IllegalArgumentException("The email address or username is already taken."); // Friendly error message
    }
}

    public UserModel authenticate(String login, String password) 
    {
        return userRepository.findByLoginAndPassword(login, password).orElse(null);
    }

    public UserModel findById(Integer userId) {
        Optional<UserModel> user = userRepository.findById(userId);
        return user.orElse(null); 
    }
}

