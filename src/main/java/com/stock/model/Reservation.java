package com.stock.model;

import java.util.UUID;

public class Reservation {

	private String product;
	private int amount;
	private String key;

	public Reservation(String product, int amount) {
		this.product = product;
		this.amount = amount;
		this.key = UUID.randomUUID().toString();
	}

	public String getProduct() {
		return product;
	}

	public int getAmount() {
		return amount;
	}

	public String getKey() {
		return key;
	}
}
