package com.techelevator.tenmo.model;

import java.util.HashMap;
import java.util.Map;

public class Transfer {



    //fields
    private int id;


    private int transferType;



    private int transferStatus;


    private int accountFrom;



    private int accountTo;

    private Map<Integer, String> transferTypeIntToStringMap;
    private Map<Integer, String> transferStatusIntToStringMap;



    private double amount;

    private String fromUsername = null;

    private String toUsername = null;



    public Transfer(int id, int transferType, int transferStatus, int accountFrom, int accountTo, double amount) {
        this.id = id;
        this.transferType = transferType;
        this.transferStatus = transferStatus;
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.amount = amount;
    }

    public Transfer(){
        transferTypeIntToStringMap = new HashMap<Integer, String>();
        transferTypeIntToStringMap.put(1,"Send");
        transferTypeIntToStringMap.put(2,"Request");
        transferStatusIntToStringMap = new HashMap<Integer, String>();
        transferStatusIntToStringMap.put(1,"Pending");
        transferStatusIntToStringMap.put(2,"Approved");
        transferStatusIntToStringMap.put(3,"Rejected");
    }

    public int getTransferType() {
        return transferType;
    }


    public int getTransferStatus() {
        return transferStatus;
    }

    public int getAccountFrom() {
        return accountFrom;
    }

    public double getAmount() {
        return amount;
    }
    public int getAccountTo() {
        return accountTo;
    }
    public int getId() {
        return id;
    }

    public String getFromUsername() {
        return fromUsername;
    }

    public void setFromUsername(String fromUsername) {
        this.fromUsername = fromUsername;
    }

    public String getToUsername() {
        return toUsername;
    }

    public void setToUsername(String toUsername) {
        this.toUsername = toUsername;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAccountTo(int accountTo) {
        this.accountTo = accountTo;
    }

    public void setTransferType(int transferType) {
        this.transferType = transferType;
    }

    public void setTransferStatus(int transferStatus) {
        this.transferStatus = transferStatus;
    }

    public void setAccountFrom(int accountFrom) {
        this.accountFrom = accountFrom;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }





}


