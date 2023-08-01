package com.techelevator.tenmo.model;

import com.techelevator.tenmo.App;
import com.techelevator.tenmo.services.ConsoleService;
import com.techelevator.tenmo.services.TransferService;

import java.util.List;

public class MainMenu extends Menu{

    private App app;

    public MainMenu(App app) {
        this.app = app;
    }



    @Override
    public void display() {
        int menuSelection = -1;
        while (menuSelection != 0) {
            int numberOfPendingRequests = app.getTransferService().getAllPendingTransfers(app.getCurrentUser().getUser().getAccount()).size();
            app.getConsoleService().printMainMenu(numberOfPendingRequests,app.getCurrentUser().getUser().getUsername());
            menuSelection = app.getConsoleService().promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                app.viewCurrentBalance();
            } else if (menuSelection == 2) {
                app.viewTransferHistory();
            } else if (menuSelection == 3) {
                app.viewPendingRequests();
            } else if (menuSelection == 4) {
                List<User> allUsers = app.getUserService().getAllUsers();
                app.getConsoleService().displayAllUsers(allUsers,app.getCurrentUser().getUser().getId());
                int selectedUserID = app.getConsoleService().promptForInt("Enter ID of user you are sending money to (0 to cancel): ");
                if(selectedUserID !=0){
                    User selectedUser = app.getUserService().getUserById(selectedUserID);
                    if(selectedUser != null){
                        double amountToSend = app.getConsoleService().promptForInt("Enter amount: ");
                        app.sendBucks(amountToSend,selectedUser,false);
                    }else{
                        System.out.println("That user ID is not valid.");
                    }
                }
            } else if (menuSelection == 5) {
                app.requestBucks();
            } else if(menuSelection == 6){
                String choice = app.getConsoleService().promptForString("Are you sure you want to log out? (y/n) ");
                boolean logOutStatus = app.handleLogout(choice);
                if(logOutStatus){
                    app.run();
                    break;
                }
            } else if (menuSelection == 0) {
                continue;
            } else {
                System.out.println("Invalid Selection");
            }
            app.getConsoleService().pause();
        }
    }
}
