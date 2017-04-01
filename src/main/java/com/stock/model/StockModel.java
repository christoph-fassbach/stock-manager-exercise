package com.stock.model;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class StockModel {

	private static final Gson GSON = new Gson();
	private static final Logger LOG = LogManager.getLogger();
	private static final String FILE_NAME = "stock.json";
	private static final Path FILE_PATH = FileSystems.getDefault().getPath(FILE_NAME);
	private static final Type STOCK_TYPE = new TypeToken<HashMap<String, Product>>(){}.getType();

	private HashMap<String, Product> stock;

	public synchronized void refill(String product, int amount) throws IOException {
		Product item = getStock(product);
		item.refill(amount);
		save();
		LOG.debug("Refilling {} {}, now there are {}, {} available.", amount, product, item.getCount(), item.getAvailable());
	}

	public synchronized boolean buy(String product, int amount) throws IOException {
		Product item = getStock(product);
		boolean success = item.buy(amount);
		save();
		LOG.debug("Tried buying {} {}, success {}.", amount, product, success);
		return success;
	}

	public synchronized boolean buy(String product, String reservationKey, int amount) throws IOException {
		Product item = getStock(product);
		boolean success = item.buy(reservationKey, amount);
		save();
		LOG.debug("Tried buying {} {} from reservation {}, success {}.", amount, product, reservationKey, success);
		return success;
	}

	public synchronized Reservation reserve(String product, int amount) throws IOException {
		Product item = getStock(product);
		Reservation reservation = item.reserve(amount);
		save();
		LOG.debug("Tried reserving {} {}, result {}.", amount, product, reservation);
		return reservation;
	}

	private HashMap<String, Product> getStock() throws IOException {
		if (null == stock) {
			try {
				byte[] bytes = Files.readAllBytes(FILE_PATH);
				String json = new String(bytes);
				stock = GSON.fromJson(json, STOCK_TYPE);
			} catch(NoSuchFileException e) {
				stock = new HashMap<String, Product>();
			}
		}
		return stock;
	}

	private Product getStock(String product) throws IOException {
		Product item = getStock().get(product);
		if (null == item) {
			item = new Product(product);
			getStock().put(product.toLowerCase(), item);
		}
		return item;
	}

	private void save() throws IOException {
		String json = GSON.toJson(getStock());
		byte[] bytes = json.getBytes();
		Files.write(FILE_PATH, bytes);
	}
}
