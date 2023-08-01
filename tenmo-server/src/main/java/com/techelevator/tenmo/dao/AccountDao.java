package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

public interface AccountDao {
    Account getAccount(int userId);
    Account updateAccount(Account account);

}



