package com.techelevator.tenmo.model;

import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import java.util.Properties;

public class EmailVerificationSender {


    public String sendVerificationCode(String recipientEmail){
        String code = generateSixDigitVerificationCode();
        String verificationCode = "Verification code: " + code;
        String api_key = "441EF16CCF58E9681189646E3C09988AFBFBD774334E2253CF2B940F307B3893BB21D73D2910EF20F80B1BC314A003B2";
        String url = "https://api.elasticemail.com/v2/email/send?apikey="+api_key+ "&subject=verification&from=tvv71412@gmail.com&to=" +recipientEmail +"&bodyText=" + verificationCode;
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<?> requestEntity = new HttpEntity<>(null);
        try{
            ResponseEntity<String> response = restTemplate.exchange(url,HttpMethod.POST,requestEntity,String.class);
        }catch(Exception ex){
            System.out.println("Something went wrong when sending verification code.");
        }

        return code;

    }



    private String generateSixDigitVerificationCode(){
        String code = "";
        for(int i=0; i<6; i++){
            code += String.valueOf((int)(Math.random() * 9) + 1);
        }
        return code;
    }


}
