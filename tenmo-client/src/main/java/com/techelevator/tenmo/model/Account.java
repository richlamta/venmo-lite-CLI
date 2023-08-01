package com.techelevator.tenmo.model;





public class Account {

    private double balance;
    private int account_id;
    private int user_id;

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public boolean hasSufficientFunds(double amountToSend){
        return balance >= amountToSend;
    }

    @Override
    public String toString() {
        return "Account{" +
                "balance=" + balance +
                ", account_id=" + account_id +
                ", user_id=" + user_id +
                '}';
    }
}
