package com.stock.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.stock.model.Reservation;
import com.stock.model.StockModel;


@RestController
@RequestMapping(path = "/stock/reserve")
public class ReservationController {

	@Autowired
	private StockModel model;

	@RequestMapping(path = "/{product}/{key}/{amount}")
	@ResponseBody
	public String buy(HttpServletRequest request, HttpServletResponse response, @PathVariable String product, @PathVariable String key, @PathVariable Integer amount) throws IOException {
		boolean success = model.buy(product, key, amount);
		if(success) {
			response.setStatus(HttpServletResponse.SC_OK);
			return "Decreased stock for " + product + " by " + amount + ".\n";
		}
		response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		return "Could not deliver " + amount + " of " + product + ". Are you sure about your reservation?\n";
	}

	@RequestMapping(path = "/{product}/{amount}")
	@ResponseBody
	public String reserve(HttpServletRequest request, HttpServletResponse response, @PathVariable String product, @PathVariable Integer amount) throws IOException {
		Reservation reservation = model.reserve(product, amount);
		if(null != reservation) {
			response.setStatus(HttpServletResponse.SC_OK);
			return "Reserved " + amount +" " + product + " for " + reservation.getKey() + ".\n";
		}
		response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		return "Could not reserve " + amount + " of " + product + ".\n";
	}

}
