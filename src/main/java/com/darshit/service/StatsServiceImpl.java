package com.darshit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.darshit.beans.Statistics;
import com.darshit.beans.Transaction;
import com.darshit.repository.StatsDaoImpl;

@Service
public class StatsServiceImpl implements StatsService {

	@Autowired
	StatsDaoImpl statsDaoImpl;

	@Override
	public boolean verifyTransaction(Transaction transaction) {
		return statsDaoImpl.verifyTransaction(transaction);
	}

	@Override
	public Statistics getStatistics() {
		return statsDaoImpl.getStatistics();
	}

}
