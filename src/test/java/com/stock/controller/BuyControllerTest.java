package com.stock.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.stock.model.StockModel;

@WebAppConfiguration
@ContextConfiguration(classes={Config.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class BuyControllerTest {

	@Autowired
	private BuyController controller;

	@Autowired
	private StockModel model;

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testBuyUnit() throws Exception {
		doReturn(false).when(model).buy(eq("false"), anyInt());
		doReturn(true).when(model).buy(eq("true"), anyInt());
		HttpServletRequest request = new MockHttpServletRequest();
		HttpServletResponse response = new MockHttpServletResponse();

		controller.buy(request, response, "false");
		assertEquals(HttpServletResponse.SC_NOT_FOUND, response.getStatus());
		controller.buy(request, response, "true");
		assertEquals(HttpServletResponse.SC_OK, response.getStatus());
	}

	@Test
	public void testBuyIntegration() throws Exception {
		doReturn(false).when(model).buy(eq("false"), anyInt());
		doReturn(true).when(model).buy(eq("true"), anyInt());

		mockMvc.perform(post("/stock/buy/false/7"))
			.andExpect(status().isNotFound());
		mockMvc.perform(post("/stock/buy/true/7"))
			.andExpect(status().isOk());
	}
}
