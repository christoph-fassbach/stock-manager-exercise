package com.stock.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.stock.model.StockModel;


@RestController
@RequestMapping(path = "/stock/refill")
public class RefillController {

	@Autowired
	private StockModel model;

	@RequestMapping(path = "/{product}")
	@ResponseBody
	public String refill(HttpServletRequest request, HttpServletResponse response, @PathVariable String product) throws IOException {
		return refill(request, response, product, new Integer(1));
	}

	@RequestMapping(path = "/{product}/{amount}")
	@ResponseBody
	public String refill(HttpServletRequest request, HttpServletResponse response, @PathVariable String product, @PathVariable Integer amount) throws IOException {
		model.refill(product, amount);
		response.setStatus(HttpServletResponse.SC_OK);
		return "Increased stock for " + product + " by " + amount + ".\n";
	}
}
