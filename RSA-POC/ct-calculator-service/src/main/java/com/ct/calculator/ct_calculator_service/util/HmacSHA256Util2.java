package com.ct.calculator.ct_calculator_service.util;

import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;

import com.ct.calculator.ct_calculator_service.pojo.InputRequestDTO;
import com.ct.calculator.ct_calculator_service.pojo.SendRequestAddNums;
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
            String rsaPrivateSign="cKqH3vQkZfH1kPXtRATxWA8WjMQAVmvaiXKN1Obrx8ZhdGP+r9R3lQz/WdJlbwToEGIfeaAtb6T7ckbpTnrsqBkLnvuGhCRIlsVEytN7RzLDKtVca1RZ2zecPjU5oh1y0IscsADHwAqCE+9ABI1NRdlAsp+y+1LeQ2blzVL+49MigeU0xyOTIPuvapoRbaVlm5bN8xfOSZL+FPvrhLvC9P0jSa2hEOvt85nNdVOvYfg4jWJIeMBTc2+PGQuEcS6jFqZsEZVmkf6JG6ng7RwMWFA/Jv8dsIF4DCPrKUy776qQNb4/l4SgX3pPKVZAAzQweKKRCnQE+o0+VJCoFaDRVw\\u003d\\u003d";
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
