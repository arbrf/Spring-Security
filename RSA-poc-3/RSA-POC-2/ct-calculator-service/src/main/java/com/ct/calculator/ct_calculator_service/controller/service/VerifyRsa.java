package com.ct.calculator.ct_calculator_service.controller.service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ct.calculator.ct_calculator_service.pojo.InputRequestDTO;
import com.ct.calculator.ct_calculator_service.util.RsaSignatureVerifier;
import com.google.gson.Gson;

@Component
public class VerifyRsa {

    private static final Logger LOGGER = LogManager.getLogger(VerifyRsa.class);

    @Autowired
    private RsaSignatureVerifier rsaSignatureVerifier;

    private static final String PUBLIC_KEY_PATH = "./src/main/resources/public_key.pem";

    public boolean verifyRsawithPublicKey(String signature, InputRequestDTO inputRequestDTO) {
        LOGGER.info("Signature: " + signature);
        LOGGER.info("InputRequestDTO: " + inputRequestDTO);

        try {
            // Read public key from PEM file
            String publicKeyPEM = new String(Files.readAllBytes(Paths.get(PUBLIC_KEY_PATH)))
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s+", ""); // Remove whitespace

            // Decode the Base64 encoded public key
            byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyPEM);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(publicKeyBytes));

            // Prepare the data to verify
            Gson gson = new Gson();
            String dataToVerify = gson.toJson(inputRequestDTO); // Add semicolon here

            // Verify the signature using RsaSignatureVerifier
            boolean isVerified = rsaSignatureVerifier.verifySignature(dataToVerify, signature, publicKey);
            LOGGER.info("Signature verification result: " + isVerified);

            return isVerified;

        } catch (Exception e) {
            LOGGER.error("Error during RSA signature verification", e);
            return false;
        }
    }
}
