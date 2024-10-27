package com.cpt.payments.security.hmac;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.cpt.payments.config.CachedBodyHttpServletRequest;
import com.cpt.payments.constants.ErrorCodeEnum;
import com.cpt.payments.constants.WrappedRequest;
import com.cpt.payments.exceptions.ValidationException;
import com.cpt.payments.security.hmac.impl.GetHmacServiceProvider;
import com.cpt.payments.security.hmac.impl.PostHmacServiceProvider;
import com.cpt.payments.util.LogMessage;

@Component
public class HmacSecurityProvider {

    private static final Logger LOGGER = LogManager.getLogger(HmacSecurityProvider.class);
    @Autowired
    private ApplicationContext context;
    // Validation logic for HMAC signature
    public boolean verifyHmac(WrappedRequest wrappedRequest) {
    	String signature = wrappedRequest.getHeader("signature");

		if (null == signature) {
			LogMessage.log(LOGGER, ">> Signature is null " + signature);
			throw new ValidationException(HttpStatus.UNAUTHORIZED, ErrorCodeEnum.SIGNATURE_NOT_FOUND.getErrorCode(),
					ErrorCodeEnum.SIGNATURE_NOT_FOUND.getErrorMessage());
		}
    	String reqMethod=wrappedRequest.getMethod();
        // Log the entry into the method
        LOGGER.info("Entering doValidate method"+reqMethod);
        
        HmacSecurityServiceProvider serviceProvider=getSecurityProvider(reqMethod);
         
        // Implement your HMAC validation logic here
        boolean isValid = serviceProvider.validate(wrappedRequest, signature); // Placeholder, replace with actual validation

        // Log the validation result
        LOGGER.info("HMAC validation result: " + isValid);

        return isValid;
    }

	private HmacSecurityServiceProvider getSecurityProvider(String reqMethod) {
		// TODO Auto-generated method stub
		switch (reqMethod) {
	    case "GET":
	        return context.getBean(GetHmacServiceProvider.class);
	    case "POST":
	    	return context.getBean(PostHmacServiceProvider.class);
	    default:
	        return null; // Return null if no match
	}
		
	}
    
}
