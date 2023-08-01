package com.techelevator.tenmo.handlers;

import com.techelevator.tenmo.App;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.services.AccountService;
import com.techelevator.tenmo.services.ConsoleService;
import com.techelevator.tenmo.services.TransferService;
import com.techelevator.tenmo.services.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TransferHandler {
    private TransferService transferService;
    private AccountService accountService;
    private UserService userService;
    private ConsoleService consoleService;

    private App app;

    public TransferHandler(TransferService transferService, AccountService accountService, UserService userService, ConsoleService consoleService, App app) {
        this.transferService = transferService;
        this.accountService = accountService;
        this.userService = userService;
        this.consoleService = consoleService;
        this.app = app;
    }

    public List<Transfer> viewTransferHistory(AuthenticatedUser currentUser){
        Account myAccount = accountService.getAccount(currentUser.getUser().getId());
        List<Transfer> allTransfers = transferService.getAllTransfers(myAccount);
        consoleService.displayAllTransfers(myAccount.getAccount_id(),allTransfers,"TO");
        consoleService.displayAllTransfers(myAccount.getAccount_id(),allTransfers,"FROM");
        return allTransfers;
    }

    public void viewIndividualTransferDetail(int selectedTransferID){
        Transfer returnedTransfer = transferService.getTransferByID(selectedTransferID);
        if(returnedTransfer != null){
            System.out.println(returnedTransfer);
        }
    }

    public List<Transfer> viewPendingTransferHistory(AuthenticatedUser currentUser){
        Account myAccount = currentUser.getUser().getAccount();
        List<Transfer> pendingTransfers = transferService.getAllPendingTransfers(myAccount);
        consoleService.displayPendingTransfers(pendingTransfers,myAccount.getAccount_id());
        return pendingTransfers;
    }

    public void sendBucks(double amountToSend, User currentUser, User recipientUser, boolean approveRequest){

        if(currentUser.getAccount().hasSufficientFunds(amountToSend) && (amountToSend > 0)){
            Account myAccount = currentUser.getAccount();
            Account recipientAccount = accountService.getAccount(recipientUser.getId());
            if(!approveRequest){
                transferService.sendMoney(amountToSend,myAccount,recipientAccount);
            }
            double myBalance = myAccount.getBalance();
            double recipientBalance = recipientAccount.getBalance();
            accountService.deductBalance(myBalance, amountToSend, currentUser);
            accountService.addBalance(recipientBalance, amountToSend, recipientUser);
            if(!approveRequest){
                System.out.println("You have sent " + "$" + amountToSend + " to " + recipientUser.getUsername());
            }
        } else if (amountToSend <= 0) {
            System.out.println("You cannot send zero or negative amount.");
        } else{
            System.out.println("You have insufficient funds.");
        }
    }

    public void requestBucks(AuthenticatedUser currentUser){
        List<User> allUsers = userService.getAllUsers();
        consoleService.displayAllUsers(allUsers,currentUser.getUser().getId());
        int userId = consoleService.promptForInt("Enter ID of user you are requesting from (0 to cancel): ");
        if(userId !=0){
            User recipientUser = userService.getUserById(userId);
            if(recipientUser != null){
                double amountToRequest = consoleService.promptForInt("Enter amount: ");
                if (amountToRequest > 0) {
                    Account myAccount = currentUser.getUser().getAccount();
                    Account recipientAccount = accountService.getAccount(recipientUser.getId());
                    transferService.requestMoney(amountToRequest,myAccount,recipientAccount);
                    System.out.println("You have requested $" + amountToRequest + " from " + recipientUser.getUsername());
                } else if (amountToRequest <= 0) {
                    System.out.println("You cannot request zero dollars.");
                }
            }else{
                System.out.println("That user ID is not valid.");
            }
        }
    }

    public int getValidTransferID(Set<Integer> validTransferIDs, String prompt){
        int selectedTransferID = -1;
        while(true){
            selectedTransferID = consoleService.promptForInt(prompt);
            if(selectedTransferID == 0){
                break;
            }else if(validTransferIDs.contains(selectedTransferID)){
                break;
            }
            System.out.println("The transfer id you entered is not valid.");
        }
        return selectedTransferID;
    }

    public static Set<Integer> convertTransferIDListToSet (List<Transfer> allTransfers) {
        Set<Integer> transferIDSet = new HashSet<Integer>();
        for (Transfer transfer : allTransfers) {
            transferIDSet.add(transfer.getId());
        }
        return transferIDSet;
    }

}
