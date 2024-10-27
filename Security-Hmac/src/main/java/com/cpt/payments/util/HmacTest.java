package com.cpt.payments.util;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Component;

import com.cpt.payments.pojo.Payment;
import com.cpt.payments.pojo.PaymentRequest;
import com.cpt.payments.pojo.User;
import com.google.gson.Gson;

@Component
public class HmacTest {

    
    private static final String signatureKey = "cptTraining";
    private static final byte[] sigtest = signatureKey.getBytes(); // Use getBytes()

   


	public void runTest(HmacSha256 hmacSha256) {
        // Create User object and set the properties
        User user = new User();
        user.setFirstName("john2");
        user.setLastName("peter2");
        user.setEmail("johnpeter4@gmail.com");
        user.setPhoneNumber("+919393939393");

        // Create Payment object and set the properties
        Payment payment = new Payment();
        payment.setPaymentMethod("APM");
        payment.setPaymentType("SALE");
        payment.setAmount("18.00");
        payment.setCurrency("EUR");
        payment.setMerchantTransactionReference("ct_test62");
        payment.setProviderId("Trustly");
        payment.setCreditorAccount("4242424242424242");
        payment.setDebitorAccount("4111111111111111");

        // Create PaymentRequest and assign User and Payment
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setPayment(payment);
        paymentRequest.setUser(user);

        Gson gson = new Gson();
        String messageDigest;
		try {
			System.out.println(gson.toJson(paymentRequest)+"----");
			messageDigest = hmacSha256.generateHmac256(gson.toJson(paymentRequest), sigtest);
			System.out.println("Generated HMAC: " + messageDigest);
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        // Output the HMAC result
        
    }

    public static void main(String[] args) {
        // Initialize Spring application context
    	HmacSha256 hmacSha256=new HmacSha256();
        HmacTest hmacTest = new HmacTest();
        hmacTest.runTest(hmacSha256);
    }
}
