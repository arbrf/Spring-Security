package com.cpt.payments.security.hmac;

import com.cpt.payments.config.CachedBodyHttpServletRequest;
import com.cpt.payments.constants.WrappedRequest;

public interface HmacSecurityServiceProvider {
	boolean validate(WrappedRequest wrappedRequest, String signature);
}
