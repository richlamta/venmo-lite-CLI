package com.techelevator.dao;


import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.dao.JdbcTransferDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class JdbcTransferDaoTests extends  BaseDaoTests{

    protected Transfer TRANSFER_1 = new Transfer();

    private JdbcTransferDao sut;

    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcTransferDao(jdbcTemplate);
    }


    //INSERT INTO transfer (transfer_type_id, transfer_status_id,
    // account_from, account_to, amount) VALUES (1,1,2001,2002, 10);
    @Test
    public void getTransferById_given_valid_Id() {
        TRANSFER_1.setId(3001);
        Transfer transfer = sut.getTransferById(TRANSFER_1.getId());
        Assert.assertEquals(TRANSFER_1.getId(), transfer.getId());
    }

    @Test
    public void getTransferById_given_invalid_Id() {
        TRANSFER_1.setId(5);
        Transfer transfer = sut.getTransferById(TRANSFER_1.getId());
        Assert.assertNull(transfer);
    }

    @Test
    public void getAllTransfer_given_accountId() {
        int expectedSize = 1;
        List<Transfer> allTransfers = sut.getAllTransfers(2001);
        Assert.assertEquals(expectedSize, allTransfers.size());

        List<Transfer> allPendingTransfers = sut.getAllPendingTransfers(2001);
        Assert.assertEquals(expectedSize, allPendingTransfers.size());
    }
}
