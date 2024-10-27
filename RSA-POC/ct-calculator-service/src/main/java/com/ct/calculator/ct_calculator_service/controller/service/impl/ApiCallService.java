package com.ct.calculator.ct_calculator_service.controller.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ct.calculator.ct_calculator_service.controller.service.ApiService;
import com.ct.calculator.ct_calculator_service.hhtp.HttpRequest;
import com.ct.calculator.ct_calculator_service.hhtp.HttpRestTemplateEngine;
import com.ct.calculator.ct_calculator_service.pojo.InputRequestDTO;
import com.ct.calculator.ct_calculator_service.pojo.SendRequestAddNums;
import com.ct.calculator.ct_calculator_service.util.LogMessage;
import com.ct.calculator.ct_calculator_service.util.SignatureCreator;
import com.google.gson.Gson;

@Service
public class ApiCallService implements ApiService {
	
    @Autowired
	private HttpRestTemplateEngine httpRestTemplateEngine;
    private static final Logger LOGGER = LogManager.getLogger(ApiCallService.class);
    @Autowired
    private SignatureCreator signatureCreator;
    private String url = "http://localhost:8081/calc/add";
    private  HttpMethod method = HttpMethod.POST;
	public ResponseEntity<String> executeCall(InputRequestDTO inputRequestDTO) throws Exception {
		// TODO Auto-generated method stub
		LogMessage.setLogMessagePrefix("ApiCallService");
		Gson gson=new Gson();
		String inputjson=gson.toJson(inputRequestDTO);
		
		String generateSignature = signatureCreator.generateSignature(inputjson);
		LogMessage.log(LOGGER, "generateSignature"+generateSignature);
		SendRequestAddNums sendRequestAddNums=new SendRequestAddNums();
		sendRequestAddNums.setInputRequestDTO(inputRequestDTO);
		sendRequestAddNums.setSignature(generateSignature);
		String requestJson = gson.toJson(sendRequestAddNums);
		HttpRequest httpRequest = new HttpRequest(url, requestJson, method);
		System.out.println("Input Reques"+httpRequest);
		httpRestTemplateEngine.execute(httpRequest);
		ResponseEntity<String> response = httpRestTemplateEngine.execute(httpRequest);
		LogMessage.log(LOGGER, "response from RSA-POC2"+response);
		return response;
		
	}

	
}
