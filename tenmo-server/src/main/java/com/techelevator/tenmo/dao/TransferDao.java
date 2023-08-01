package com.techelevator.tenmo.dao;


import com.techelevator.tenmo.model.Transfer;

import java.util.List;
import java.util.Map;

public interface TransferDao {

    Transfer createTransfer(Transfer transfer);
    Transfer getTransferById(int id);

    List<Transfer> getAllTransfers(int myAccountID);
    List<Transfer> getAllPendingTransfers(int myAccountID);

    boolean updateTransfer(Transfer updatedTransfer);





}
