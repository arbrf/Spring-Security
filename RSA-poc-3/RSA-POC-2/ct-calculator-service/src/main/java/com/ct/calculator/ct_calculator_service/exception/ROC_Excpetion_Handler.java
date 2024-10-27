package com.ct.calculator.ct_calculator_service.exception;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ct.calculator.ct_calculator_service.pojo.PaymentError;

@ControllerAdvice
public class ROC_Excpetion_Handler {
	private static final Logger LOGGER = LogManager.getLogger(ROC_Excpetion_Handler.class);

@ExceptionHandler(ValidationException.class)
public ResponseEntity<PaymentError> handleValidationException(ValidationException ex) {
	PaymentError paymentResponse = PaymentError.builder().errorCode(ex.getErrorCode())
			.errorMessage(ex.getErrorMessage()).build();
	return new ResponseEntity<>(paymentResponse, ex.getHttpStatus());
}

@ExceptionHandler(Exception.class)
public ResponseEntity<PaymentError> handleGenericException(Exception ex) {
	PaymentError paymentResponse = PaymentError.builder()
			.errorCode("20001")
			.errorMessage("SOMETHING WENT WRONG")
			.build();
	return new ResponseEntity<>(paymentResponse, HttpStatus.INTERNAL_SERVER_ERROR);
}}

