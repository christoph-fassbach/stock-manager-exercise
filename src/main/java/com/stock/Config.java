package com.stock;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.stock.model.StockModel;


@Configuration
public class Config {

	@Bean
	public StockModel stockModel() {
		return new StockModel();
	}
}
