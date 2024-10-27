package com.ct.calculator.ct_calculator_service.controller;

import org.modelmapper.ModelMapper;
import org.modelmapper.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ct.calculator.ct_calculator_service.constans.EndPoint;
import com.ct.calculator.ct_calculator_service.controller.service.CalculatorService;
import com.ct.calculator.ct_calculator_service.controller.service.ValidateSignature;
import com.ct.calculator.ct_calculator_service.controller.service.VerifyRsa;
import com.ct.calculator.ct_calculator_service.pojo.InputRequest;
import com.ct.calculator.ct_calculator_service.pojo.InputRequestDTO;
import com.ct.calculator.ct_calculator_service.pojo.ResultResponse;
import com.ct.calculator.ct_calculator_service.pojo.ResultResponseDTO;
import com.ct.calculator.ct_calculator_service.util.HmacSHA256Util2;
import com.ct.calculator.ct_calculator_service.util.pojo.SendRequestAddNums;

@RestController
@RequestMapping(EndPoint.CALC_MAPPING)
public class CalculatorController {

    @Autowired
    private CalculatorService calculatorService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ValidateSignature signatureValid;

    @Autowired
    private HmacSHA256Util2 hmacSHa;
    
    @Autowired
    private VerifyRsa verifyRsa;
    @PostMapping(EndPoint.ADD_MAPPING)
    public ResponseEntity<ResultResponse> getSum(@RequestHeader("signature") String signature, @RequestBody SendRequestAddNums request) {
        // Validate the signature
        boolean isValidSignature = signatureValid.validateSignature(signature, request);
        if (!isValidSignature) {
            // Throw your custom ValidationException with appropriate parameters
        throw new 
        com.ct.calculator.ct_calculator_service.exception.
        ValidationException(HttpStatus.ACCEPTED, "2000","Data Altered");
        }
         String rsaSignature=request.getSignature();
         
         
        // Map request to DTO
        InputRequestDTO inputRequestDTO = request.getInputRequestDTO();
        boolean verifyRsawithPublicKey = verifyRsa.verifyRsawithPublicKey(rsaSignature,inputRequestDTO);
        // Perform the addition operation
        //System.out.println("RSA----->"+verifyRsawithPublicKey);
        if (!verifyRsawithPublicKey) {
            // Throw your custom ValidationException with appropriate parameters
        throw new 
        com.ct.calculator.ct_calculator_service.exception.
        ValidationException(HttpStatus.ACCEPTED, "2002","Signature Altered");
        }
        ResultResponseDTO resultResponseDTO = calculatorService.addsum(inputRequestDTO);

        // Map the result DTO to the response entity
        ResultResponse result = modelMapper.map(resultResponseDTO, ResultResponse.class);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    
    @GetMapping(EndPoint.CALL_SERVICE)
    public String getCall() {
	   return "RSA-POC-2";
   }
}
