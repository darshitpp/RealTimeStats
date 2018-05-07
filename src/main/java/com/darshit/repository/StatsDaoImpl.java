package com.darshit.repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

import org.springframework.stereotype.Repository;

import com.darshit.beans.Statistics;
import com.darshit.beans.Transaction;
import com.google.common.util.concurrent.AtomicDouble;

import net.jodah.expiringmap.ExpirationListener;
import net.jodah.expiringmap.ExpirationPolicy;
import net.jodah.expiringmap.ExpiringMap;

@Repository
public class StatsDaoImpl implements StatsDao {

	private static AtomicDouble sum = new AtomicDouble(0);
	private static AtomicDouble avg = new AtomicDouble(0);
	private static double max = 0;
	private static double min = 0;
	private static AtomicLong count = new AtomicLong(0);
	private static List<Double> amountList = new ArrayList<>();

	private static final Logger LOGGER = Logger.getLogger(StatsDaoImpl.class.getName());

	/**
	 * Returns true if the transaction is valid, false otherwise
	 */
	@Override
	public boolean verifyTransaction(Transaction transaction) {

		double amount = transaction.getAmount();
		Timestamp transactionTimestamp = transaction.getTransactionTime();

		long transactionTime = transactionTimestamp.getTime();
		long currentTime = System.currentTimeMillis();

		int timeDifference = (int) ((currentTime - transactionTime) / 1000);
		LOGGER.info("Cache for timestamp: " + transactionTimestamp + " will expire in " + (60 - timeDifference)
				+ " seconds");

		// If transaction is older than 60 seconds, return false
		if (currentTime - transactionTime > 60000) {
			return false;
		}

		// Used an ExpirationListener which will listen for transaction expiry
		ExpirationListener<Timestamp, Double> expirationListener = (key, value) -> {
			expireTransaction(value);
			LOGGER.info("Cache expired for timestamp: " + key + " and amount: " + value);
		};

		// Create ExpiringMap with the listener defined above
		ExpiringMap<Timestamp, Double> expiringMap = ExpiringMap.builder().variableExpiration()
				.expirationListener(expirationListener).build();

		// Adds transactions to an Expiring Map for 60 seconds from their generated time
		expiringMap.put(transactionTimestamp, amount, ExpirationPolicy.CREATED, 60 - timeDifference, TimeUnit.SECONDS);

		this.addTransaction(amount);

		LOGGER.info("Added transaction for timestamp: " + transactionTimestamp + " and amount: " + amount);
		return true;
	}

	/**
	 * Adds transaction and generates statistics
	 * 
	 * @param amount
	 */
	private void addTransaction(Double amount) {

		amountList.add(amount);

		count.incrementAndGet();

		sum.addAndGet(amount);

		avg.set(sum.get() / count.get());

		max = Collections.max(amountList);

		min = Collections.min(amountList);

	}

	/**
	 * Generates statistics after transaction is expired
	 * 
	 * @param removedAmount
	 */
	private void expireTransaction(Double removedAmount) {

		amountList.remove(removedAmount);

		count.decrementAndGet();

		sum.addAndGet(-removedAmount);

		avg.set(count.get() != 0 ? (sum.get() / count.get()) : 0);

		max = count.get() != 0 ? Collections.max(amountList) : 0;

		min = count.get() != 0 ? Collections.min(amountList) : 0;

	}

	/**
	 * Returns statistics for the transactions generated in the last 60 seconds
	 * Executes in O(1) time and space as per requirement
	 */
	@Override
	public Statistics getStatistics() {
		Statistics statistics = new Statistics(sum, avg, min, max, count);
		return statistics;
	}

}
