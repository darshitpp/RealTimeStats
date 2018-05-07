package com.darshit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.darshit.beans.Statistics;
import com.darshit.beans.Transaction;
import com.darshit.service.StatsServiceImpl;

@RestController
public class StatsController {

	@Autowired
	private StatsServiceImpl statsServiceImpl;

	/**
	 * POST /transactions
	 * 
	 * @param Transaction
	 * @return HttpStatus 201 if a valid transaction, HttpStatus 204 otherwise
	 */
	@PostMapping("/transactions")
	public ResponseEntity<?> postTransactions(@RequestBody Transaction transaction) {

		boolean valid = statsServiceImpl.verifyTransaction(transaction);

		return valid == true ? new ResponseEntity<>(HttpStatus.CREATED) : new ResponseEntity<>(HttpStatus.NO_CONTENT);

	}

	/**
	 * GET /statistics
	 * 
	 * @return Statistics of the last 60 seconds
	 */
	@GetMapping("/statistics")
	public ResponseEntity<Statistics> getStatistics() {

		Statistics statistics = statsServiceImpl.getStatistics();

		return new ResponseEntity<Statistics>(statistics, HttpStatus.OK);

	}
}
