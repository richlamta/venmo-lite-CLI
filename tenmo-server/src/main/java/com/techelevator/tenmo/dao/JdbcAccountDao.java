package com.techelevator.tenmo.dao;


import com.techelevator.tenmo.exception.DaoException;
import com.techelevator.tenmo.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;


@Component
public class JdbcAccountDao implements AccountDao{

    JdbcTemplate jdbcTemplate;

    public JdbcAccountDao (JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


@Override
    public Account getAccount(int userId) {
        Account account = null;
        String sql = "SELECT *\n" +
                "FROM account\n" +
                "WHERE user_id = ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
            if (results.next()) {
                account = mapRowtoAccount(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return account;
    }

@Override
    public Account updateAccount(Account account) {
        String sql = "UPDATE account\n" +
                "SET balance = ?\n" +
                "WHERE user_id = ?";
        try {
            int numOfRowsChanged = jdbcTemplate.update(sql,account.getBalance(), account.getUser_id());
            if (numOfRowsChanged == 0) {
                throw new DaoException("Zero rows affected, expected at least one");
            }
        } catch (CannotGetJdbcConnectionException e) {
        throw new DaoException("Unable to connect to server or database", e);
        }
        return getAccount(account.getUser_id());
    }



    private Account mapRowtoAccount (SqlRowSet result) {
        Account account = new Account();
        account.setAccount_id(result.getInt("account_id"));
        account.setUser_id(result.getInt("user_id"));
        account.setBalance(result.getDouble("balance"));
        return account;
    }

}



