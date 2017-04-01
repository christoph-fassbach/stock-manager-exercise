package com.stock.model;

import java.util.HashMap;

public class Product {
	
	private String name;
	private int count;
	private HashMap<String, Reservation> reservations = new HashMap<String, Reservation>();

	public Product(String name) {
		this.name = name;
		this.count = 0;
	}

	public int getAvailable() {
		int reserved = 0;
		for (Reservation reservation : reservations.values()) {
			reserved += reservation.getAmount();
		}
		return count - reserved;
	}

	public String getName() {
		return name;
	}

	public int getCount() {
		return count;
	}

	public void refill(int amount) {
		count += amount;
	}

	public boolean buy(int amount) {
		if (getAvailable() >= amount) {
			count -= amount;
			return true;
		}
		return false;
	}

	public boolean buy(String reservationKey, int amount) {
		Reservation reservation = reservations.get(reservationKey);
		if ((null == reservation) || (reservation.getAmount() < amount)) {
			return false;
		}
		count -= amount;
		reservations.remove(reservationKey);
		return true;
	}

	public Reservation reserve(int amount) {
		if (getAvailable() >= amount) {
			Reservation reservation = new Reservation(getName(), amount);
			reservations.put(reservation.getKey(), reservation);
			return reservation;
		}
		return null;
	}
}
