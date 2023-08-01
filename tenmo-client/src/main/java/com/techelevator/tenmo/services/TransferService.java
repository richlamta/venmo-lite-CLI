package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TransferService {
    private final String API_BASE_URL = "http://localhost:8080/transfers/";
    private final RestTemplate restTemplate = new RestTemplate();

    private String token = null;


    public Transfer sendMoney(double amount, Account myAccount, Account recipientAccount){
        //transferType: 1 is send
        //transferType: 2 is request
        //transferStatus: 1 is pending
        //transferStatus: 2 is approved
        //transferStatus: 3 is rejected
        Transfer transfer = new Transfer(1,2,myAccount.getAccount_id(),recipientAccount.getAccount_id(), amount);
        Transfer returnedTransfer = null;
        try {
            returnedTransfer = restTemplate.postForObject(API_BASE_URL + myAccount.getAccount_id(),makeAuthEntity(transfer), Transfer.class);
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return returnedTransfer;
    }

    public boolean updateTransfer(Transfer updatedTransfer) {

        //String url
        String url = API_BASE_URL + updatedTransfer.getId();
        HttpEntity<Transfer> entity = makeAuthEntity(updatedTransfer);

        //Try catch & return
        boolean updateAttempt = false;
        try {
            restTemplate.put(url, entity);
            updateAttempt = true;
        } catch (RestClientResponseException ex) {  //We find the server, but ends an error back (no permission, bad data, etc.)
            BasicLogger.log(ex.getRawStatusCode() + ":" + ex.getStatusText());
        }
        catch (ResourceAccessException ex) {      //For when we cannot access the external server we are calling
            BasicLogger.log(ex.getMessage());
        }
        return updateAttempt;
    }



    public List<Transfer> getAllTransfers(Account myAccount){
        List<Transfer> allTransfers = null;

        try {
            ResponseEntity<Transfer[]> response = restTemplate.exchange(API_BASE_URL + "account/" + myAccount.getAccount_id(),
                    HttpMethod.GET, AuthenticationService.makeAuthEntity(token), Transfer[].class);
            Transfer[] transfersArr = response.getBody();
            if (transfersArr != null) {
                allTransfers = Arrays.asList(transfersArr);
            }
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return allTransfers;
    }

    public List<Transfer> getAllPendingTransfers(Account myAccount){
        List<Transfer> allPendingTransfers = null;

        try {
            ResponseEntity<Transfer[]> response = restTemplate.exchange(API_BASE_URL + "account/pending/" + myAccount.getAccount_id(),
                    HttpMethod.GET, AuthenticationService.makeAuthEntity(token), Transfer[].class);
            Transfer[] transfersArr = response.getBody();
            if (transfersArr != null) {
                allPendingTransfers = Arrays.asList(transfersArr);
            }
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return allPendingTransfers;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private HttpEntity<Transfer> makeAuthEntity(Transfer transfer) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        return new HttpEntity<>(transfer, headers);
    }

    public Transfer getTransferByID(int transferID){
        Transfer transfer = null;
        try {
            ResponseEntity<Transfer> response = restTemplate.exchange(API_BASE_URL  + transferID,
                    HttpMethod.GET, AuthenticationService.makeAuthEntity(token), Transfer.class);
            transfer = response.getBody();

        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return transfer;
    }

    public Transfer requestMoney(double amount, Account myAccount, Account recipientAccount){
        //transferType: 1 is send
        //transferType: 2 is request
        //transferStatus: 1 is pending
        //transferStatus: 2 is approved
        //transferStatus: 3 is rejected
        Transfer transfer = new Transfer(2,1,myAccount.getAccount_id(),recipientAccount.getAccount_id(), amount);
        Transfer requestTransfer = null;
        try {
            requestTransfer = restTemplate.postForObject(API_BASE_URL + myAccount.getAccount_id(),makeAuthEntity(transfer), Transfer.class);
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return requestTransfer;
    }


}
