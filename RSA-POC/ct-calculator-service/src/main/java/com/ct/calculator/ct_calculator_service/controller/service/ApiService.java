package com.ct.calculator.ct_calculator_service.controller.service;

import org.springframework.http.ResponseEntity;

import com.ct.calculator.ct_calculator_service.pojo.InputRequestDTO;

public interface ApiService {
public  ResponseEntity<String>   executeCall(InputRequestDTO inputRequestDTO) throws Exception;
}
