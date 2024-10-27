package com.cpt.payments.security.hmac.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.cpt.payments.config.CachedBodyHttpServletRequest;
import com.cpt.payments.constants.WrappedRequest;
import com.cpt.payments.security.hmac.HmacSecurityProvider;
import com.cpt.payments.security.hmac.HmacSecurityServiceProvider;
@Component
public class GetHmacServiceProvider implements HmacSecurityServiceProvider {
	private static final Logger LOGGER = LogManager.getLogger(GetHmacServiceProvider.class);
	@Override
	public boolean validate(WrappedRequest request, String signature) {
		// TODO Auto-generated method stub
		LOGGER.info("Entering GET method");
		return false;
	}

}
