package com.ct.calculator.ct_calculator_service.util;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class RsaSignatureVerifier {

    private static final Logger LOGGER = LogManager.getLogger(RsaSignatureVerifier.class);

    public boolean verifySignature(String data, String signature, PublicKey publicKey) { // Change parameter type
        try {
            // Initialize the Signature object
            Signature rsa = Signature.getInstance("SHA256withRSA");
            rsa.initVerify(publicKey);

            // Update the data to be verified
            rsa.update(data.getBytes());

            // Verify the signature
            boolean isVerified = rsa.verify(Base64.getDecoder().decode(signature));
            LOGGER.info("Signature verification result: " + isVerified);
            return isVerified;

        } catch (Exception e) {
            LOGGER.error("Error during RSA signature verification", e);
            return false;
        }
    }
}
