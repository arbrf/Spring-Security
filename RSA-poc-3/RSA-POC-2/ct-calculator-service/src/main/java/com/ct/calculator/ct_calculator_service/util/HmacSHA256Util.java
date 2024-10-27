package com.ct.calculator.ct_calculator_service.util;

import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;

import com.ct.calculator.ct_calculator_service.pojo.InputRequestDTO;
import com.google.gson.Gson;
@Component
public class HmacSHA256Util {

    private static final String HMAC_SHA256 = "HmacSHA256";

    public static String calculateHMAC(String data) throws Exception {
    	String secret = "testKey";
    	 System.out.println("Secret: " + secret);
        SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(), HMAC_SHA256);
        Mac mac = Mac.getInstance(HMAC_SHA256);
        mac.init(secretKeySpec);
        byte[] rawHmac = mac.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(rawHmac);
    }

    public static void main(String[] args) {
        try {
            InputRequestDTO inputRequestDTO = new InputRequestDTO();
            inputRequestDTO.setNumber1(10);
            inputRequestDTO.setNumber2(20);
            
            Gson gson = new Gson();
            String data = gson.toJson(inputRequestDTO);
            
            String hmac = calculateHMAC(data);

            System.out.println("Data: " + data);
           
            System.out.println("HMAC: " + hmac);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
