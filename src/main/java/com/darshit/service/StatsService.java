package com.darshit.service;

import com.darshit.beans.Statistics;
import com.darshit.beans.Transaction;

public interface StatsService {

	public boolean verifyTransaction(Transaction transaction);

	public Statistics getStatistics();
}
