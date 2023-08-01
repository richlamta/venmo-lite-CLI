package com.techelevator.tenmo.controller;


import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(path="/accounts")
@PreAuthorize("isAuthenticated()")
public class AccountController {

    @Autowired
    private AccountDao accountDao;

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public Account getBalance(@PathVariable int id) {
        return accountDao.getAccount(id);
    }


    @RequestMapping(path = "/{id}", method =RequestMethod.PUT)
    public Account updateAccount(@RequestBody Account account) {
        return accountDao.updateAccount(account);
    }



}

