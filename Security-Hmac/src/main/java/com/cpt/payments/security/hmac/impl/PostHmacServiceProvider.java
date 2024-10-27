package com.cpt.payments.security.hmac.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.cpt.payments.constants.WrappedRequest;
import com.cpt.payments.security.hmac.HmacSecurityServiceProvider;
import com.cpt.payments.util.HmacSha256;
import com.cpt.payments.util.HmacUtils;
import com.cpt.payments.util.LogMessage;

@Component
public class PostHmacServiceProvider implements HmacSecurityServiceProvider {

    private static final Logger LOGGER = LogManager.getLogger(PostHmacServiceProvider.class);

    @Autowired
    private HmacSha256 hmacSha256;

    @Value("${payment.signatureKey}")
    private String signatureKey;

    @Override
    public boolean validate(WrappedRequest request, String signature) {
        try {
            String message = request.getBody();

            // Digest is calculated using a public shared secret
            boolean verifySignatureStatus = HmacUtils.sign(signatureKey,signature, message);
            LogMessage.log(LOGGER, ">> verifySignatureStatus is  " + verifySignatureStatus);
            
            return true;
        } catch (Exception e) {
            LogMessage.log(LOGGER, "Exception in generateHmac256: " + e);
            return false;
        }
    }
}
