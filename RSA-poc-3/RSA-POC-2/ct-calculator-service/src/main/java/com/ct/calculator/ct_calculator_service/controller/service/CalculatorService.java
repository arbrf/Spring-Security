package com.ct.calculator.ct_calculator_service.controller.service;

import org.springframework.stereotype.Component;

import com.ct.calculator.ct_calculator_service.pojo.InputRequestDTO;
import com.ct.calculator.ct_calculator_service.pojo.ResultResponseDTO;

@Component
public interface CalculatorService {
	ResultResponseDTO addsum(InputRequestDTO inputRequestDTO);

}
