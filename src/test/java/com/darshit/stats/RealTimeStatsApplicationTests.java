package com.darshit.stats;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.darshit.beans.Statistics;
import com.darshit.beans.Transaction;
import com.darshit.repository.StatsDao;
import com.darshit.repository.StatsDaoImpl;
import com.google.common.util.concurrent.AtomicDouble;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RealTimeStatsApplicationTests {

	private static AtomicDouble sum = new AtomicDouble(0);
	private static AtomicDouble avg = new AtomicDouble(0);
	private static double max = 0;
	private static double min = 0;
	private static AtomicLong count = new AtomicLong(0);
	private static List<Double> amountList = new ArrayList<>();

	@Test
	public void insertValidTransaction() {
		for (int i = 0; i < 5; i++) {
			boolean valid = verifyTransactions(10000, 60000);
			assertTrue(valid);
		}
	}

	@Test
	public void getStatsWhenNothing() {

		StatsDao statsDaoImpl = new StatsDaoImpl();
		Statistics stats = statsDaoImpl.getStatistics();

		assertEquals(Double.parseDouble(stats.getSum().toString()), 0, 0);
		assertEquals(Double.parseDouble(stats.getAvg().toString()), 0, 0);
		assertEquals(Double.parseDouble(stats.getCount().toString()), 0, 0);
		assertEquals(stats.getMax(), 0, 0);
		assertEquals(stats.getMin(), 0, 0);

	}

	@Test
	public void insertInvalidTransaction() {

		for (int i = 0; i < 5; i++) {
			boolean valid = verifyTransactions(60001, 90000);
			assertFalse(valid);
		}
	}

	public boolean verifyTransactions(int lower, int upper) {
		long randomTime = ThreadLocalRandom.current().nextInt(lower, upper);

		Timestamp timestamp = new Timestamp(System.currentTimeMillis() - randomTime);

		double amount = ThreadLocalRandom.current().nextInt(0, 100);
		Transaction transaction = new Transaction(amount, timestamp);

		StatsDao statsDaoImpl = new StatsDaoImpl();

		boolean valid = statsDaoImpl.verifyTransaction(transaction);

		if (valid) {
			addTransaction(amount);
			Statistics stats = statsDaoImpl.getStatistics();

			assertEquals(Double.parseDouble(stats.getSum().toString()), Double.parseDouble(sum.toString()), 0);
			assertEquals(Double.parseDouble(stats.getAvg().toString()), Double.parseDouble(avg.toString()), 0);
			assertEquals(Double.parseDouble(stats.getCount().toString()), Double.parseDouble(count.toString()), 0);
			assertEquals(stats.getMax(), max, 0);
			assertEquals(stats.getMin(), min, 0);
		}
		return valid;
	}

	private void addTransaction(double amount) {

		amountList.add(amount);

		count.incrementAndGet();

		sum.addAndGet(amount);

		avg.set(sum.get() / count.get());

		max = Collections.max(amountList);

		min = Collections.min(amountList);
	}
}
