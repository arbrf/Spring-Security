package com.ct.calculator.ct_calculator_service.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentError {
	private String errorCode;
	private String errorMessage;
}
