package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserService {
    private final String API_BASE_URL = "http://localhost:8080/users/";
    private final RestTemplate restTemplate = new RestTemplate();

    public static final int ACCOUNT_USER_CONVERTER = 1000;

    private String token = null;


    public String getToken() {
        return token;
    }

    public void setToken(String token){
        this.token = token;
    }



    public List<User> getAllUsers(){
        List<User> allUsers = null;

        try {
            ResponseEntity<User[]> response = restTemplate.exchange(API_BASE_URL,
                    HttpMethod.GET, makeAuthEntity(), User[].class);
            User[] userArr = response.getBody();
            if (userArr != null) {
                allUsers = Arrays.asList(userArr);
            }
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return allUsers;


    }


    public User getUserById(int id){
        User user = null;
        try {
            ResponseEntity<User> response = restTemplate.exchange(API_BASE_URL + id, HttpMethod.GET, makeAuthEntity(), User.class);
            user = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return user;

    }

    public String getUsernameById(int id){
        User user = null;
        try {
            ResponseEntity<User> response = restTemplate.exchange(API_BASE_URL + "/usernames/" + id, HttpMethod.GET, makeAuthEntity(), User.class);
            user = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return user != null ? user.getUsername() : null;

    }
    private HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        return new HttpEntity<>(headers);
    }

}
