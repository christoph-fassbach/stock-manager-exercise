package com.stock.controller;

import static org.mockito.Mockito.mock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.stock.model.StockModel;

@Configuration
public class Config {

	@Autowired
	private WebApplicationContext wac;

	@Bean
	public MockMvc mockMvc() {
		return MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Bean
	public StockModel stockModel() {
		return mock(StockModel.class);
	}

	@Bean
	public BuyController buyController() {
		return new BuyController();
	}

	@Bean
	public RefillController refillController() {
		return new RefillController();
	}

	@Bean
	public ReservationController reservationController() {
		return new ReservationController();
	}
}
