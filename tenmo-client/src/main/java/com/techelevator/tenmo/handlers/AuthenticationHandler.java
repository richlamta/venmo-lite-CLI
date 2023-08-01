package com.techelevator.tenmo.handlers;

import com.techelevator.tenmo.App;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.EmailVerificationSender;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.UserCredentials;
import com.techelevator.tenmo.services.*;

import java.util.Scanner;

public class AuthenticationHandler {

    private AuthenticationService authenticationService;
    private ConsoleService consoleService;

    private AccountService accountService;

    private TransferService transferService;
    private UserService userService;




    public AuthenticationHandler(AuthenticationService authenticationService, ConsoleService consoleService, AccountService accountService, TransferService transferService, UserService userService) {
        this.authenticationService = authenticationService;
        this.consoleService = consoleService;
        this.accountService = accountService;
        this.transferService = transferService;
        this.userService = userService;
    }

    public void register(UserCredentials userCredentials){
        if (authenticationService.register(userCredentials)) {
            System.out.println("Registration successful. You can now login.");
        } else {
            consoleService.printErrorMessage();
        }
    }

    public void registerWithEmailVerification(UserCredentials userCredentials, String emailAddress){
        EmailVerificationSender emailVerificationSender = new EmailVerificationSender();
        String correctVerificationCode = emailVerificationSender.sendVerificationCode(emailAddress);
        System.out.println("A six digit verification code has been sent to your email. If it is not in the inbox, check your spam folder.");
        while(true){
            String enteredCode = consoleService.promptForString("Enter six digit verification code to register your account (Press 0 to cancel):");
            if(correctVerificationCode.equals(enteredCode)){
                if (authenticationService.register(userCredentials)) {
                    System.out.println("Registration successful. You can now login.");
                } else {
                    System.out.println("Something went wrong during registration.");;
                }
                break;
            }else if(enteredCode.equals("0")){
                break;
            }else{
                System.out.println("You did not enter the correct verification code. Please try again or press 0 to exit.");
            }
        }

    }

    public AuthenticatedUser login(UserCredentials credentials){
        AuthenticatedUser currentUser = authenticationService.login(credentials);
        if (currentUser == null ) {
            System.out.println("Wrong password or username.");
        }
        return currentUser;


    }

    public AuthenticatedUser logout(String choice, AuthenticatedUser currentUser){
        if(choice.equalsIgnoreCase("y")){
            currentUser = null;
            System.out.println("You have logged out.");
        }
        return currentUser;

    }

}
