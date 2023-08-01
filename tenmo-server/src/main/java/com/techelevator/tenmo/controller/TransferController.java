package com.techelevator.tenmo.controller;


import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/transfers")
@PreAuthorize("isAuthenticated()")
public class TransferController {


    @Autowired
    private TransferDao transferDao;





    @RequestMapping(path = "/{id}",method = RequestMethod.POST)
    public Transfer createTransfer(@RequestBody Transfer transfer) {
        return transferDao.createTransfer(transfer);
    }

    @RequestMapping(path = "/{id}",method = RequestMethod.PUT)
    public boolean updateTransfer(@RequestBody Transfer transfer) {
        return transferDao.updateTransfer(transfer);
    }

    @RequestMapping(path = "/{id}",method = RequestMethod.GET)
    public Transfer getTransferByID(@PathVariable int id) {
        return transferDao.getTransferById(id);
    }

    @RequestMapping(path = "/account/{id}", method = RequestMethod.GET)
    public List<Transfer> getAllTransfers(@PathVariable int id, UserDao userDao){
        return transferDao.getAllTransfers(id);
    }

    @RequestMapping(path = "/account/pending/{id}", method = RequestMethod.GET)
    public List<Transfer> getAllPendingTransfers(@PathVariable int id){
        return transferDao.getAllPendingTransfers(id);
    }





}
