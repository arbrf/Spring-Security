package com.ct.calculator.ct_calculator_service.controller.service.impl;
import org.springframework.stereotype.Component;

import com.ct.calculator.ct_calculator_service.controller.service.CalculatorService;
import com.ct.calculator.ct_calculator_service.pojo.InputRequestDTO;
import com.ct.calculator.ct_calculator_service.pojo.ResultResponseDTO;
@Component
public class CalculatorServiceImp implements CalculatorService {

	@Override
	public ResultResponseDTO addsum(InputRequestDTO inputRequestDTO) {
		int sum=inputRequestDTO.getNumber1()+inputRequestDTO.getNumber2();
		ResultResponseDTO response=new ResultResponseDTO();
		response.setSum(sum);
		return response;
	}

}
