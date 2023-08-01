package com.techelevator.tenmo.model;

import com.techelevator.tenmo.App;
import com.techelevator.tenmo.services.ConsoleService;

public class LoginMenu extends Menu{
    private App app;


    public LoginMenu(App app) {
        this.app = app;
    }

    @Override
    public void display() {
        int menuSelection = -1;
        while (menuSelection != 0 && app.getCurrentUser() == null) {
            app.getConsoleService().printLoginMenu();
            menuSelection = app.getConsoleService().promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                System.out.println("Please register a new user account.");
                UserCredentials userCredentials = app.getConsoleService().promptForCredentials();
                app.handleRegister(userCredentials);
            } else if(menuSelection == 2){
                System.out.println("Please register a new user account.");
                UserCredentials userCredentials = app.getConsoleService().promptForCredentials();
                String email = app.getConsoleService().promptForString("Enter your email address: ");
                app.handleRegisterWithEmailVerification(userCredentials, email);
            } else if (menuSelection == 3) {
                UserCredentials userCredentials = app.getConsoleService().promptForCredentials();
                app.setCurrentUser(app.handleLogin(userCredentials));
                if(app.getCurrentUser() != null){
                    app.getTransferService().setToken(app.getCurrentUser().getToken());
                    app.getAccountService().setToken(app.getCurrentUser().getToken());
                    app.getUserService().setToken(app.getCurrentUser().getToken());
                    app.getConsoleService().setUserService(app.getUserService());
                    app.mainMenu();
                    break;
                }
            } else if (menuSelection != 0) {
                System.out.println("Invalid Selection");
                app.getConsoleService().pause();
            }
        }
    }
}
