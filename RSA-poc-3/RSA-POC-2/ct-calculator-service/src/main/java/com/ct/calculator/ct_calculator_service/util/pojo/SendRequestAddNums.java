package com.ct.calculator.ct_calculator_service.util.pojo;

import com.ct.calculator.ct_calculator_service.pojo.InputRequestDTO;

import lombok.Data;

@Data
public class SendRequestAddNums {
private InputRequestDTO inputRequestDTO;
private String signature;
}
