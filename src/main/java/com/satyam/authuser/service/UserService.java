package com.satyam.authuser.service;

import java.util.List;

import com.satyam.authuser.model.User;

public interface UserService {

    User save(User user);
    List<User> findAll();
    void delete(long id);
}
