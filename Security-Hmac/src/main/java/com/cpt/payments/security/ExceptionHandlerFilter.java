package com.cpt.payments.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.filter.OncePerRequestFilter;

import com.cpt.payments.exceptions.ValidationException;
import com.cpt.payments.pojo.PaymentError;
import com.cpt.payments.util.LogMessage;
import com.google.gson.Gson;

public class ExceptionHandlerFilter extends OncePerRequestFilter {
	private static final Logger LOGGER = LogManager.getLogger(ExceptionHandlerFilter.class);
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException
			 {
		// TODO Auto-generated method stub
		
		try {
			filterChain.doFilter(request, response);
		}
		catch (ValidationException ex) {LogMessage.log(LOGGER, " validation exception is -> " + ex.getErrorMessage());


		PaymentError paymentResponse = PaymentError.builder().errorCode(ex.getErrorCode())
				.errorMessage(ex.getErrorMessage()).build();


		LogMessage.log(LOGGER, " paymentResponse is -> " + paymentResponse);

		Gson gson = new Gson();

		response.setStatus(ex.getHttpStatus().value());
		response.setContentType("application/json");
		response.getWriter().write(gson.toJson(paymentResponse));
		response.getWriter().flush();}
		
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	

}
