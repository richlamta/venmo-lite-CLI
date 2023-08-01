package com.techelevator.tenmo.services;


import com.techelevator.tenmo.App;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.UserCredentials;

import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class ConsoleService {

    private final Scanner scanner = new Scanner(System.in);

    private UserService userService;






    public int promptForMenuSelection(String prompt) {
        int menuSelection;
        System.out.print(prompt);
        try {
            menuSelection = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            menuSelection = -1;
        }
        return menuSelection;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void printGreeting() {
        System.out.println("*********************");
        System.out.println("* Welcome to TEnmo! *");
        System.out.println("*********************");
    }

    public void printLoginMenu() {
        System.out.println();
        System.out.println("1: Register");
        System.out.println("2. Register (requires email verification code)");
        System.out.println("3: Login");
        System.out.println("0: Exit");
        System.out.println();
    }
    public void printMainMenu(int numberOfPendingRequests, String currentUserName) {
        System.out.println();
        System.out.println();
        System.out.println("Welcome, " + currentUserName + ".");
        System.out.println();
        System.out.println("1: View your current balance");
        System.out.println("2: View your past transfers");
        String optionThreeNoPendingRequests = "3: View your pending requests";
        String optionThreeHasPendingRequests = "3: View your pending requests (" + numberOfPendingRequests + ")";
        String optionThree = numberOfPendingRequests > 0 ? optionThreeHasPendingRequests : optionThreeNoPendingRequests;
        System.out.println(optionThree);
        System.out.println("4: Send TE bucks");
        System.out.println("5: Request TE bucks");
        System.out.println("6. Log out");
        System.out.println("0: Exit");
        System.out.println();
    }

    public UserCredentials promptForCredentials() {
        String username = promptForString("Username: ");
        String password = promptForString("Password: ");
        return new UserCredentials(username, password);
    }

    public String promptForString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public int promptForInt(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            }
        }
    }

    public User promptToChooseUser(int myUserID) {
        List<User> allUsers = userService.getAllUsers();
        displayAllUsers(allUsers,myUserID);
        int userId = promptForInt("Enter ID of user you are requesting from (0 to cancel): ");
        User recipientUser = userService.getUserById(userId);
        return allUsers.contains(recipientUser) ? recipientUser : null;
    }

    public void displayUser(User recipientUser) {
        System.out.println("Recipient user: ");
        System.out.println("User id: " + recipientUser.getId());
        System.out.println("Username: " +recipientUser.getUsername());
    }

    public void pause() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    public void printErrorMessage() {
        System.out.println("An error occurred. Check the log for details.");
    }

    public void displayBalance(double balance) {
        System.out.println("Your current account balance is: $" + balance);
    }



    public void displayAllUsers(List<User> allUsers,int myUserID){
        System.out.println("-------------------------------------------");
        for(User user : allUsers){
            if(user.getId() != myUserID){
                System.out.println("User ID: " + user.getId());
                System.out.println("Name: " + user.getUsername());
            }
        }
    }
    public void displayAllTransfers(int myAccountID, List<Transfer> allTransfers, String type){
        for(int i=0; i < allTransfers.size(); i++){
            int accountID = type.equals("TO") ? allTransfers.get(i).getAccountTo() : allTransfers.get(i).getAccountFrom();
            String usernameFromAccountID = type.equals("TO") ? allTransfers.get(i).getToUsername() : allTransfers.get(i).getFromUsername();
            if(accountID != myAccountID){
                Transfer transfer = allTransfers.get(i);
                System.out.println("transferId: "+ transfer.getId() + " " + type + " " + usernameFromAccountID + " " + "Amount:" + transfer.getAmount());
            }
        }
        if(type.equals("TO")){
            System.out.println("------------------------------------------------------------");
        }

    }


    public void displayPendingTransfers(List<Transfer> pendingTransfers, int myAccountID) {
        for (Transfer transfer : pendingTransfers) {
            String type = myAccountID != transfer.getAccountFrom() ? "FROM" : "TO";
            String otherPersonUsername = type.equals("FROM") ? transfer.getFromUsername() : transfer.getToUsername();
            System.out.println("-------------------------------------");
            System.out.println("Pending Transfer ID: " +  transfer.getId());
            System.out.println("To: " + otherPersonUsername);
            System.out.println("Amount: " + transfer.getAmount());
            System.out.println("-------------------------------------");
        }
    }
    public int promptUserForApproveOrDeny(){
        int selectedChoice = -1;
        while(true){
            selectedChoice = promptForInt("Press 1 to Approve \n" +
                    "Press 2 to Reject \n" +
                    "Press 0 to exit");
            if(selectedChoice == 0){
                break;
            }else if(selectedChoice < 1 || selectedChoice > 2){
                System.out.println("You did not enter a valid choice.");
            }else{
                break;
            }
        }
        return selectedChoice;

    }


}
