package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

public class AccountService {

    private final String API_BASE_URL = "http://localhost:8080/accounts/";
    private final RestTemplate restTemplate = new RestTemplate();

    private String token = null;


    public double getBalance(AuthenticatedUser currentUser) {
        Account account = null;
        try {
            ResponseEntity<Account> response = restTemplate.exchange(API_BASE_URL  + currentUser.getUser().getId(),
                    HttpMethod.GET, AuthenticationService.makeAuthEntity(token), Account.class);
            account = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return account.getBalance();
    }




    public void deductBalance(double myBalance, double amountToSend, User user) {
        Account updatedAccount = user.getAccount();
        System.out.println("UserID: " + updatedAccount.getUser_id());            //test code
        System.out.println(updatedAccount);
        updatedAccount.setBalance(myBalance - amountToSend);
        System.out.println("Account: " + updatedAccount.getBalance());
        try{
            restTemplate.put(API_BASE_URL  + user.getId(),makeAuthEntity(updatedAccount));
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
    }

    public void addBalance(double recipientBalance, double amountToReceive, User recipientUser  ) {
        Account updatedAccount = getAccount(recipientUser.getId());
        updatedAccount.setBalance(recipientBalance + amountToReceive);
        try{
            restTemplate.put(API_BASE_URL  + recipientUser.getId(),makeAuthEntity(updatedAccount));

        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
    }

    public Account getAccount(int userId) {
        Account account = null;
        try {
            ResponseEntity<Account> response = restTemplate.exchange(API_BASE_URL  + userId,
                    HttpMethod.GET, AuthenticationService.makeAuthEntity(token), Account.class);
            account = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return account;
    }






    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }


    private HttpEntity<Account> makeAuthEntity(Account account) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        return new HttpEntity<>(account, headers);
    }
}


