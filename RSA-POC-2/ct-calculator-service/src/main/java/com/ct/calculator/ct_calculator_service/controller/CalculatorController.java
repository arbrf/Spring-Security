package com.ct.calculator.ct_calculator_service.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ct.calculator.ct_calculator_service.constans.EndPoint;
import com.ct.calculator.ct_calculator_service.controller.service.CalculatorService;
import com.ct.calculator.ct_calculator_service.controller.service.ValidateSignature;
import com.ct.calculator.ct_calculator_service.pojo.InputRequest;
import com.ct.calculator.ct_calculator_service.pojo.InputRequestDTO;
import com.ct.calculator.ct_calculator_service.pojo.ResultResponse;
import com.ct.calculator.ct_calculator_service.pojo.ResultResponseDTO;

@RestController
@RequestMapping(EndPoint.CALC_MAPPING)
public class CalculatorController {

    @Autowired
    private CalculatorService calculatorService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ValidateSignature signatureValid;

    @PostMapping(EndPoint.ADD_MAPPING)
    public ResponseEntity<ResultResponse> getSum(@RequestHeader("signature") String signature, @RequestBody InputRequest request) {
        try {
            // Validate the signature
            boolean isValidSignature = signatureValid.validateSignature(signature,request);
            if (!isValidSignature) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

            // Map request to DTO
            InputRequestDTO inputRequestDTO = modelMapper.map(request, InputRequestDTO.class);

            // Perform the addition operation
            ResultResponseDTO resultResponseDTO = calculatorService.addsum(inputRequestDTO);

            // Map the result DTO to the response entity
            ResultResponse result = modelMapper.map(resultResponseDTO, ResultResponse.class);
            return new ResponseEntity<>(result, HttpStatus.OK);

        } catch (Exception e) {
            // Handle exceptions and return appropriate response
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
