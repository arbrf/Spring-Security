package com.ct.calculator.ct_calculator_service.controller.service;

import com.ct.calculator.ct_calculator_service.pojo.InputRequest;

public interface ValidateSignature {

	boolean validateSignature(String signature, InputRequest request);

}
