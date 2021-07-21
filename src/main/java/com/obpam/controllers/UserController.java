package com.obpam.controllers;

import com.obpam.dtos.UserDto;
import com.obpam.views.UserView;
import com.obpam.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.obpam.StringConstants.USER;
import static com.obpam.StringConstants.USERS;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping(USER + "/{id}")
    public UserView getUser(@PathVariable("id") long id) {
        return userService.findById(id);
    }

    @GetMapping(USERS)
    public List<UserView> getAllUsers() {
        return userService.findAll();
    }

    @PostMapping(USER)
    public UserDto addUser(@RequestBody @Valid UserDto user) {
        return userService.addUser(user);
    }

    @DeleteMapping(USER + "/{id}")
    public void removeUser(@PathVariable("id") long id) {
        userService.deleteById(id);
    }
}
