package com.ct.calculator.ct_calculator_service.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class AppConfig {

	@Bean
	public ModelMapper getModelMapper() {
		return new ModelMapper();
	}
}
