package com.obpam.services;

import com.obpam.dtos.UserDto;
import com.obpam.views.UserView;

import java.util.List;

public interface UserService {
    UserView findById(long id);
    List<UserView> findAll();
    UserDto addUser(UserDto user);
    void deleteById(long id);
}
