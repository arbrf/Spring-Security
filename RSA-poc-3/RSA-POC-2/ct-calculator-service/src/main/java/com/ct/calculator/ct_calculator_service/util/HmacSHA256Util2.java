package com.ct.calculator.ct_calculator_service.util;

import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;

import com.ct.calculator.ct_calculator_service.pojo.InputRequestDTO;
import com.ct.calculator.ct_calculator_service.util.pojo.SendRequestAddNums;
import com.google.gson.Gson;
@Component
public class HmacSHA256Util2 {

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
            inputRequestDTO.setNumber1(25);
            inputRequestDTO.setNumber2(25);
            SendRequestAddNums sendRequestAddNums=new SendRequestAddNums();
            sendRequestAddNums.setInputRequestDTO(inputRequestDTO);
            String rsaPrivateSign="AkjgxSWaTE40OszsUOxkxn8zQORI2YyHCbVRAPaPkry6rG/IedhWgxHvScJbctFiCZrqKwnB/k+gShNwmZ2u/ZKJp+3DmkqsQuYjTyJL1GDmT1V4hNUAV6mqpeQV3HzEW2RQZgYSDhZmZT89HLFNhBlMfoN4NsECyz3SFKc3LN1uQhPBya99vXsHa9fOjRYjgRk1HH99ixlhi5ii3fRMjbaJV1oGMk4vbw9S2se726cViFR38ttJjkQTgUBGS4+hpBJ/e4+aVDkYTI3ZVyTDgiZs9jCnXW/I5TKi8Bap7Cbo15nru9QWCwBSySRPVh3T0n/Ui+ngI9DkL+ods4IUOQ==";
            sendRequestAddNums.setSignature(rsaPrivateSign);
            Gson gson = new Gson();
            String data = gson.toJson(sendRequestAddNums);
            
            String hmac = calculateHMAC(data);

            System.out.println("Data: " + data);
           
            System.out.println("HMAC: " + hmac);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
