package com.techelevator.tenmo.controller;


import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path="/users")
@PreAuthorize("isAuthenticated()")
public class UserController {

    @Autowired
    private UserDao userDao;

    @RequestMapping(method = RequestMethod.GET)
    public List<User> getAllUsers() {
        return userDao.getUsers();
    }

    @RequestMapping(path="/{id}", method = RequestMethod.GET)
    public User getUserById (@PathVariable int id) {
        return userDao.getUserById(id);
    }


    @RequestMapping(path="/usernames/{id}", method = RequestMethod.GET)
    public String getUsernameByAccountID (@PathVariable int id) {
        return userDao.getUsernameByAccountID(id);
    }

}
