package com.darshit.repository;

import com.darshit.beans.Statistics;
import com.darshit.beans.Transaction;

public interface StatsDao {

	public boolean verifyTransaction(Transaction transaction);

	public Statistics getStatistics();

}
