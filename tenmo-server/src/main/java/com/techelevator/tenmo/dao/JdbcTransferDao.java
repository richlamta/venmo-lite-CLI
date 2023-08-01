package com.techelevator.tenmo.dao;


import com.techelevator.tenmo.exception.DaoException;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao{

    private final JdbcTemplate jdbcTemplate;



    public JdbcTransferDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }





    @Override
    public Transfer getTransferById(int transferID) {
        Transfer transfer = null;
        String sql = "SELECT *\n" +
                "FROM transfer\n" +
                "WHERE transfer_id = ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transferID);
            if (results.next()) {
                transfer = mapRowtoTransfer(results);
                JdbcUserDao jdbcUserDao = new JdbcUserDao(jdbcTemplate);
                initializeUsernames(transfer);


            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return transfer;
    }

    @Override
    public List<Transfer> getAllTransfers(int myAccountID){
        List<Transfer> allTransfers = new ArrayList<Transfer>();
        String sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, tenmo_user.user_id, username, amount\n" +
                "FROM transfer\n" +
                "JOIN account\n" +
                "ON transfer.account_from = account.account_id \n" +
                "JOIN tenmo_user\n" +
                "ON account.user_id = tenmo_user.user_id\n" +
                "WHERE (transfer.account_from = ? OR transfer.account_to = ?) AND transfer_status_id != 1 AND transfer_status_id != 3 " +
                "ORDER BY transfer_id";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql,myAccountID,myAccountID);
            while (results.next()) {
                Transfer transfer = mapRowtoTransfer(results);
                initializeUsernames(transfer);
                allTransfers.add(transfer);

            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return allTransfers;
    }

    @Override
    public List<Transfer> getAllPendingTransfers(int myAccountID){
        List<Transfer> allTransfers = new ArrayList<Transfer>();
        String sql = "SELECT * FROM transfer " +
                "WHERE account_to = ? AND transfer_status_id = 1 " +
                "ORDER BY transfer_id";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql,myAccountID);
            while (results.next()) {
                Transfer transfer = mapRowtoTransfer(results);
                initializeUsernames(transfer);
                allTransfers.add(transfer);

            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return allTransfers;
    }


    @Override
    public Transfer createTransfer(Transfer transfer) {
        String sql = "INSERT INTO transfer(transfer_type_id, transfer_status_id, account_from, account_to, amount) " +
                "VALUES (?, ?, ?, ?, ?) " +
                "RETURNING transfer_id;";
        Transfer newTransfer = null;
        try {
            int newTransferId = jdbcTemplate.queryForObject(sql, int.class,transfer.getTransferType(), transfer.getTransferStatus(),
            transfer.getAccountFrom(), transfer.getAccountTo(), transfer.getAmount());
            newTransfer = getTransferById(newTransferId);
            System.out.println("Successfully Transferred.");
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return newTransfer;
    }

    @Override
    public boolean updateTransfer(Transfer updatedTransfer) {
        String sql = "UPDATE transfer \n" +
                "SET transfer_type_id = ?,transfer_status_id = ?,account_from = ?,account_to = ?\n" +
                "WHERE transfer_id = ?;";
        boolean status = false;
        try {
            int numOfRowsChanged = jdbcTemplate.update(sql,updatedTransfer.getTransferType(),updatedTransfer.getTransferStatus(), updatedTransfer.getAccountFrom(),updatedTransfer.getAccountTo(),updatedTransfer.getId());
            if (numOfRowsChanged > 0) {
                status = true;
            }else{
                throw new DaoException("Zero rows affected, expected at least one");
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return status;



    }
    private void initializeUsernames(Transfer transfer){
        JdbcUserDao jdbcUserDao = new JdbcUserDao(jdbcTemplate);
        transfer.setFromUsername(jdbcUserDao.getUsernameByAccountID(transfer.getAccountFrom()));
        transfer.setToUsername(jdbcUserDao.getUsernameByAccountID(transfer.getAccountTo()));

    }




    private Transfer mapRowtoTransfer (SqlRowSet result) {
        Transfer transfer = new Transfer();
        transfer.setId(result.getInt("transfer_id"));
        transfer.setTransferType(result.getInt("transfer_type_id"));
        transfer.setTransferStatus(result.getInt("transfer_status_id"));
        transfer.setAccountFrom(result.getInt("account_from"));
        transfer.setAccountTo(result.getInt("account_to"));
        transfer.setAmount(result.getDouble("amount"));
        return transfer;
    }



}
