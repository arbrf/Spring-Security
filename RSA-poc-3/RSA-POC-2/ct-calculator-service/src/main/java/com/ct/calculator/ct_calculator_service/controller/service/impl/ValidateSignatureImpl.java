package com.ct.calculator.ct_calculator_service.controller.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ct.calculator.ct_calculator_service.controller.service.ValidateSignature;
import com.ct.calculator.ct_calculator_service.pojo.InputRequestDTO;
import com.ct.calculator.ct_calculator_service.util.HmacSHA256Util2;
import com.ct.calculator.ct_calculator_service.util.pojo.SendRequestAddNums;
import com.google.gson.Gson;
@Component
public class ValidateSignatureImpl implements  ValidateSignature {
	
	@Autowired
    private HmacSHA256Util2 hmacSHa;
	@Override
	public boolean validateSignature(String signature,SendRequestAddNums request) {
		// TODO Auto-generated method stub
		
		Gson gson= new Gson();
		String data=gson.toJson(request);
		try {
			System.out.println("HMAC0 "+signature);
			String generatedSignature=hmacSHa.calculateHMAC(data);
			System.out.println("HMAC  1  " +generatedSignature);
			if(generatedSignature !=null && generatedSignature.equals(signature)) {
				return true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}

}
