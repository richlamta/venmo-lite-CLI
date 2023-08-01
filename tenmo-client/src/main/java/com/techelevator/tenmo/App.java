package com.techelevator.tenmo;

import com.techelevator.tenmo.handlers.AuthenticationHandler;
import com.techelevator.tenmo.handlers.TransferHandler;
import com.techelevator.tenmo.model.*;
import com.techelevator.tenmo.services.*;

import javax.sql.DataSource;
import java.util.*;

public class App {

    private static final String API_BASE_URL = "http://localhost:8080/";




    //services
    private final UserService userService = new UserService();
    private ConsoleService consoleService = new ConsoleService();
    private final TransferService transferService = new TransferService();
    private final AuthenticationService authenticationService = new AuthenticationService(API_BASE_URL);
    private AccountService accountService = new AccountService();

    private AuthenticatedUser currentUser = null;

    //handlers
    private final AuthenticationHandler authenticationHandler = new AuthenticationHandler(authenticationService,consoleService,accountService,transferService,userService);
    private final TransferHandler transferHandler = new TransferHandler(transferService,accountService,userService,consoleService,this);


    private Menu loginMenu = new LoginMenu(this);
    private Menu mainMenu = new MainMenu(this);







    //constructor
    public App(){

    }



    public static void main(String[] args) {


        App app = new App();
        app.run();
    }


    public void run() {
        consoleService.printGreeting();
        loginMenu();
        if (currentUser != null) {
            mainMenu();
        }
    }

    private void loginMenu() {
        loginMenu.display();;
    }

    public void handleRegister(UserCredentials userCredentials) {
        authenticationHandler.register(userCredentials);
    }
    public void handleRegisterWithEmailVerification(UserCredentials userCredentials, String email){
        authenticationHandler.registerWithEmailVerification(userCredentials,email);
    }

    public AuthenticatedUser handleLogin(UserCredentials userCredentials) {
        return authenticationHandler.login(userCredentials);
    }

    public boolean handleLogout(String choice){
        currentUser = authenticationHandler.logout(choice,currentUser);
        return currentUser == null;
    }

    public void mainMenu() {
       mainMenu.display();
    }

	public void viewCurrentBalance() {
		double balance = accountService.getBalance(currentUser);
        consoleService.displayBalance(balance);

	}

	public void viewTransferHistory() {
        List<Transfer> allTransfers = transferHandler.viewTransferHistory(currentUser);
        Set<Integer> validTransferIDs = TransferHandler.convertTransferIDListToSet(allTransfers);
        String prompt = "Please enter transfer ID to view details (0 to cancel):  ";
        int validTransferID = transferHandler.getValidTransferID(validTransferIDs,prompt);
        if(validTransferID != 0){
            viewIndividualTransferDetail(validTransferID);
        }

    }
    private void viewIndividualTransferDetail(int selectedTransferID){
        transferHandler.viewIndividualTransferDetail(selectedTransferID);
    }


	public void viewPendingRequests() {
        List<Transfer> allPendingTransfers = transferHandler.viewPendingTransferHistory(currentUser);
        Set<Integer> validPendingTransferIDs = TransferHandler.convertTransferIDListToSet(allPendingTransfers);
        String prompt = "Please enter transfer ID to approve/reject (0 to cancel): ";
        int validTransferID = transferHandler.getValidTransferID(validPendingTransferIDs,prompt);
        if(validTransferID != 0){
            Transfer selectedTransfer = transferService.getTransferByID(validTransferID);
            int selectedChoice = consoleService.promptUserForApproveOrDeny();
            if(selectedChoice != 0){
                int recipientAccountID = selectedTransfer.getAccountFrom();
                User recipientUser = userService.getUserById(recipientAccountID-UserService.ACCOUNT_USER_CONVERTER);
                currentUser.processChoiceApproveDeny(selectedChoice,selectedTransfer.getAmount(),recipientUser,selectedTransfer,this);
            }
        }
    }
    public void sendBucks(double amountToSend, User recipientUser, boolean approveRequest) {
        transferHandler.sendBucks(amountToSend,currentUser.getUser(),recipientUser,approveRequest);
    }
    public void requestBucks() {
        transferHandler.requestBucks(currentUser);

    }

    public TransferService getTransferService() {
        return transferService;
    }

    public AuthenticatedUser getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(AuthenticatedUser currentUser) {
        this.currentUser = currentUser;
    }
    public ConsoleService getConsoleService() {
        return consoleService;
    }

    public UserService getUserService() {
        return userService;
    }
    public AccountService getAccountService() {
        return accountService;
    }
}




