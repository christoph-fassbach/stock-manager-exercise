package com.stock.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class StockModelTest {

	@SuppressWarnings("rawtypes")
	@Test
	public void testBuy2() throws Exception {
		deleteStockFile();

		StockModel model0 = new StockModel();
		assertFalse(model0.buy("strawberry", 1));
		model0.refill("strawberry", 5);
		assertTrue(model0.buy("strawberry", 1));

		StockModel model1 = new StockModel();
		assertTrue(model1.buy("strawberry", 1));
		HashMap map1 = (HashMap) ReflectionTestUtils.getField(model1, "stock");
		Product product1 = (Product) map1.get("strawberry");
		assertNotNull(product1);
		assertEquals(3, product1.getCount());
		assertEquals(3, product1.getAvailable());
		assertEquals("strawberry", product1.getName());
	}

	@SuppressWarnings("rawtypes")
	@Test
	public void testBuy3() throws Exception {
		deleteStockFile();

		StockModel model0 = new StockModel();
		assertFalse(model0.buy("fluffy", "wrongKey", 1));
		model0.refill("fluffy", 5);
		assertFalse(model0.buy("fluffy", "wrongKey", 1));
		Reservation reservation = model0.reserve("fluffy", 2);
		assertNotNull(reservation);

		StockModel model1 = new StockModel();
		assertTrue(model1.buy("fluffy", reservation.getKey(), 1));

		StockModel model2 = new StockModel();
		assertFalse(model2.buy("fluffy", reservation.getKey(), 1));

		StockModel model3 = new StockModel();
		assertFalse(model3.buy("fluffy", reservation.getKey(), 1));
		HashMap map3 = (HashMap) ReflectionTestUtils.getField(model3, "stock");
		Product product3 = (Product) map3.get("fluffy");
		assertNotNull(product3);
		assertEquals(4, product3.getCount());
		assertEquals(4, product3.getAvailable());
		assertEquals("fluffy", product3.getName());
	}

	@SuppressWarnings("rawtypes")
	@Test
	public void testReserve() throws Exception {
		deleteStockFile();

		StockModel model0 = new StockModel();
		assertNull(model0.reserve("stuff", 1));

		model0.refill("stuff", 3);
		assertNotNull(model0.reserve("stuff", 1));
		HashMap map0 = (HashMap) ReflectionTestUtils.getField(model0, "stock");
		Product product0 = (Product) map0.get("stuff");
		assertNotNull(product0);
		assertEquals(3, product0.getCount());
		assertEquals(2, product0.getAvailable());
		assertEquals("stuff", product0.getName());

		StockModel model1 = new StockModel();
		assertNotNull(model1.reserve("stuff", 1));
		HashMap map1 = (HashMap) ReflectionTestUtils.getField(model1, "stock");
		Product product1 = (Product) map1.get("stuff");
		assertNotNull(product1);
		assertEquals(3, product1.getCount());
		assertEquals(1, product1.getAvailable());
		assertEquals("stuff", product1.getName());
	}

	@SuppressWarnings("rawtypes")
	@Test
	public void testRefill() throws Exception {
		deleteStockFile();

		StockModel model0 = new StockModel();
		model0.refill("something", 23);
		HashMap map0 = (HashMap) ReflectionTestUtils.getField(model0, "stock");
		Product product0 = (Product) map0.get("something");
		assertNotNull(product0);
		assertEquals(23, product0.getCount());
		assertEquals("something", product0.getName());

		StockModel model1 = new StockModel();
		model1.refill("something", 1);
		HashMap map1 = (HashMap) ReflectionTestUtils.getField(model1, "stock");
		Product product1 = (Product) map1.get("something");
		assertNotNull(product1);
		assertEquals(24, product1.getCount());
		assertEquals("something", product1.getName());
	}

	private void deleteStockFile() throws Exception {
		Path path = (Path) ReflectionTestUtils.getField(StockModel.class, "FILE_PATH");
		Files.deleteIfExists(path);
	}
}
