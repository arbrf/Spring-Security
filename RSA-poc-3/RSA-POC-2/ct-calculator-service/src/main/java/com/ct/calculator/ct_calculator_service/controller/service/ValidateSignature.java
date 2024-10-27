package com.ct.calculator.ct_calculator_service.controller.service;

import com.ct.calculator.ct_calculator_service.util.pojo.SendRequestAddNums;

public interface ValidateSignature {

	boolean validateSignature(String signature, SendRequestAddNums request);

}
