package com.stock.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

import com.stock.model.Reservation;
import com.stock.model.StockModel;

@WebAppConfiguration
@ContextConfiguration(classes={Config.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class ReservationControllerTest {

	@Autowired
	private ReservationController controller;

	@Autowired
	private StockModel model;

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testReserveUnit() throws Exception {
		doReturn(null).when(model).reserve(eq("yes"), anyInt());
		doReturn(mock(Reservation.class)).when(model).reserve(eq("no"), anyInt());
		HttpServletRequest request = new MockHttpServletRequest();
		HttpServletResponse response = new MockHttpServletResponse();

		controller.reserve(request, response, "yes", 8);
		assertEquals(HttpServletResponse.SC_NOT_FOUND, response.getStatus());
		controller.reserve(request, response, "no", 9);
		assertEquals(HttpServletResponse.SC_OK, response.getStatus());
	}

	@Test
	public void testBuyUnit() throws Exception {
		doReturn(false).when(model).buy(eq("yes"), eq("key0"), anyInt());
		doReturn(true).when(model).buy(eq("no"), eq("key1"), anyInt());
		HttpServletRequest request = new MockHttpServletRequest();
		HttpServletResponse response = new MockHttpServletResponse();

		controller.buy(request, response, "yes", "key0", 8);
		assertEquals(HttpServletResponse.SC_NOT_FOUND, response.getStatus());
		controller.buy(request, response, "no", "key1", 9);
		assertEquals(HttpServletResponse.SC_OK, response.getStatus());
	}

	@Test
	public void testReserveIntegration() throws Exception {
		doReturn(null).when(model).reserve(eq("yes"), anyInt());
		doReturn(mock(Reservation.class)).when(model).reserve(eq("no"), anyInt());

		mockMvc.perform(put("/stock/reserve/yes/7"))
			.andExpect(status().isNotFound());
		mockMvc.perform(put("/stock/reserve/no/7"))
			.andExpect(status().isOk());
	}

	@Test
	public void testBuyIntegration() throws Exception {
		doReturn(false).when(model).buy(eq("yes"), eq("key0"), anyInt());
		doReturn(true).when(model).buy(eq("no"), eq("key1"), anyInt());

		mockMvc.perform(put("/stock/reserve/yes/key0/7"))
			.andExpect(status().isNotFound());
		mockMvc.perform(put("/stock/reserve/no/key1/7"))
			.andExpect(status().isOk());
	}
}
