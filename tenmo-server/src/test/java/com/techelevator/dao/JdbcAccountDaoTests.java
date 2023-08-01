package com.techelevator.dao;

import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

public class JdbcAccountDaoTests extends BaseDaoTests {

    protected Account ACCOUNT_1 = new Account();
    protected Account ACCOUNT_2 = new Account();
    private Account ACCOUNT_3 = new Account();


    private JdbcAccountDao sut;

    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcAccountDao(jdbcTemplate);

    }

    @Test
    public void getAccount_given_valid_userId() {
        ACCOUNT_1.setUser_id(1001);
        ACCOUNT_1.setBalance(1000);
        ACCOUNT_1.setAccount_id(2001);
        Account actualAccount = sut.getAccount(ACCOUNT_1.getUser_id());
        Assert.assertEquals(ACCOUNT_1.getUser_id(), actualAccount.getUser_id());
    }

    @Test
    public void getAccount_given_invalid_userId() {
        ACCOUNT_1.setUser_id(9999);
        Account actualAccount = sut.getAccount(ACCOUNT_1.getUser_id());
        Assert.assertNull(actualAccount);
    }
}



