package com.stock.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ProductTest {

	@Test
	public void testCreation() {
		Product product = new Product("stuff");
		assertEquals("stuff", product.getName());
		assertEquals(0, product.getCount());
	}

	@Test
	public void testReserve() {
		Product product = new Product("stuff");
		assertEquals("stuff", product.getName());
		assertEquals(0, product.getCount());
		assertEquals(0, product.getAvailable());

		product.refill(10);
		assertEquals(10, product.getCount());
		assertEquals(10, product.getAvailable());

		Reservation reservation0 = product.reserve(5);
		assertNotNull(reservation0);
		assertEquals(product.getName(), reservation0.getProduct());
		assertNotNull(reservation0.getKey());
		assertEquals(5, reservation0.getAmount());
		assertEquals(10, product.getCount());
		assertEquals(5, product.getAvailable());

		assertNull(product.reserve(6));
		assertEquals(10, product.getCount());
		assertEquals(5, product.getAvailable());

		Reservation reservation1 = product.reserve(2);
		assertNotNull(reservation1);
		assertEquals(product.getName(), reservation1.getProduct());
		assertNotNull(reservation1.getKey());
		assertEquals(2, reservation1.getAmount());
		assertEquals(10, product.getCount());
		assertEquals(3, product.getAvailable());
	}

	@Test
	public void testBuy1() {
		Product product = new Product("stuff");
		assertEquals("stuff", product.getName());
		assertEquals(0, product.getCount());
		assertEquals(0, product.getAvailable());

		product.refill(10);
		assertEquals(10, product.getCount());
		assertEquals(10, product.getAvailable());

		assertTrue(product.buy(5));
		assertEquals(5, product.getCount());
		assertEquals(5, product.getAvailable());

		assertFalse(product.buy(6));
		assertEquals(5, product.getCount());
		assertEquals(5, product.getAvailable());

		assertTrue(product.buy(5));
		assertEquals(0, product.getCount());
		assertEquals(0, product.getAvailable());

		assertFalse(product.buy(1));
		assertEquals(0, product.getCount());
		assertEquals(0, product.getAvailable());
	}

	@Test
	public void testBuy2() {
		Product product = new Product("stuff");
		assertEquals("stuff", product.getName());
		assertEquals(0, product.getCount());
		assertEquals(0, product.getAvailable());

		product.refill(10);
		assertEquals(10, product.getCount());
		assertEquals(10, product.getAvailable());

		assertFalse(product.buy("test", 2));
		assertEquals(10, product.getCount());
		assertEquals(10, product.getAvailable());

		Reservation reservation0 = product.reserve(5);
		assertNotNull(reservation0);
		assertEquals(product.getName(), reservation0.getProduct());
		assertNotNull(reservation0.getKey());
		assertEquals(5, reservation0.getAmount());
		assertEquals(10, product.getCount());
		assertEquals(5, product.getAvailable());

		assertFalse(product.buy(reservation0.getKey(), 6));
		assertEquals(10, product.getCount());
		assertEquals(5, product.getAvailable());

		assertTrue(product.buy(reservation0.getKey(), 4));
		assertEquals(6, product.getCount());
		assertEquals(6, product.getAvailable());

		assertFalse(product.buy(reservation0.getKey(), 1));
		assertEquals(6, product.getCount());
		assertEquals(6, product.getAvailable());
	}
}
