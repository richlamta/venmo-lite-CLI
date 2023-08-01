package com.techelevator.tenmo.model;

import com.techelevator.tenmo.services.UserService;

import java.util.HashMap;
import java.util.Map;

public class Transfer {
    //fields


    private int id;



    private int transferType;



    private int transferStatus;


    private int accountFrom;



    private int accountTo;



    private double amount;

    private Map<Integer, String> transferTypeIntToStringMap = new HashMap<>();
    private Map<Integer, String> transferStatusIntToStringMap = new HashMap<>();

    private String fromUsername = null;

    private String toUsername = null;


    public Transfer(int transferType, int transferStatus, int accountFrom, int accountTo, double amount) {
        this.transferType = transferType;
        this.transferStatus = transferStatus;
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.amount = amount;

    }
    public Transfer(){
        transferTypeIntToStringMap.put(1,"Send");
        transferTypeIntToStringMap.put(2,"Request");
        transferStatusIntToStringMap.put(1,"Pending");
        transferStatusIntToStringMap.put(2,"Approved");
        transferStatusIntToStringMap.put(3,"Rejected");

    }

    public int getTransferType() {
        return transferType;
    }

    public String getTransferTypeString(){
        return transferTypeIntToStringMap.get(this.transferType);
    }

    public void setTransferType(int transferType) {
        this.transferType = transferType;
    }

    public int getTransferStatus() {
        return transferStatus;
    }

    public String getTransferStatusString(){
        return transferStatusIntToStringMap.get(this.getTransferStatus());
    }

    public void setTransferStatus(int transferStatus) {
        this.transferStatus = transferStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public int getAccountFrom() {
        return accountFrom;
    }

    public void setAccountFrom(int accountFrom) {
        this.accountFrom = accountFrom;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(int accountTo) {
        this.accountTo = accountTo;
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

    public String determineToOrFrom(String toOrFrom){
        return toOrFrom;
    }

    @Override
    public String toString() {
        String transferDetails = "Transfer Details \n " +
                "--------------------------------------- \n" +
                "Id: " + id + "\n" +
                "FROM: " + fromUsername + "\n" +
                "TO: " + toUsername + "\n" +
                "Type: " + getTransferTypeString() + "\n" +
                "Status: " + getTransferStatusString() + "\n" +
                "Amount: " + amount;

        return transferDetails;


    }
}
