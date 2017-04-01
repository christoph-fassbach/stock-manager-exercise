package com.stock.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
public class RefillControllerTest {

	@Autowired
	private RefillController controller;

	@Autowired
	private StockModel model;

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testRefillUnit() throws Exception {
		HttpServletRequest request = new MockHttpServletRequest();
		HttpServletResponse response = new MockHttpServletResponse();

		controller.refill(request, response, "one");
		assertEquals(HttpServletResponse.SC_OK, response.getStatus());
		verify(model, times(1)).refill(eq("one"), eq(1));
		controller.refill(request, response, "two", 2);
		assertEquals(HttpServletResponse.SC_OK, response.getStatus());
		verify(model, times(1)).refill(eq("two"), eq(2));
	}

	@Test
	public void testRefillIntegration() throws Exception {
		mockMvc.perform(get("/stock/refill/three/7"))
			.andExpect(status().isOk());
		verify(model, times(1)).refill(eq("three"), eq(7));
	}
}
