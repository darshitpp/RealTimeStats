package com.darshit.beans;

import java.util.concurrent.atomic.AtomicLong;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.util.concurrent.AtomicDouble;

public class Statistics {

	@JsonProperty("sum")
	private AtomicDouble sum = new AtomicDouble();

	@JsonProperty("avg")
	private AtomicDouble avg = new AtomicDouble();

	@JsonProperty("min")
	private double min = Double.MAX_VALUE;

	@JsonProperty("max")
	private double max = Double.MIN_VALUE;

	@JsonProperty("count")
	private AtomicLong count = new AtomicLong();

	public AtomicDouble getSum() {
		return sum;
	}

	public void setSum(AtomicDouble sum) {
		this.sum = sum;
	}

	public AtomicDouble getAvg() {
		return avg;
	}

	public void setAvg(AtomicDouble avg) {
		this.avg = avg;
	}

	public double getMin() {
		return min;
	}

	public void setMin(double min) {
		this.min = min;
	}

	public double getMax() {
		return max;
	}

	public void setMax(double max) {
		this.max = max;
	}

	public AtomicLong getCount() {
		return count;
	}

	public void setCount(AtomicLong count) {
		this.count = count;
	}

	public Statistics() {
		super();
	}

	public Statistics(AtomicDouble sum, AtomicDouble avg, double min, double max, AtomicLong count) {
		super();
		this.sum = sum;
		this.avg = avg;
		this.min = min;
		this.max = max;
		this.count = count;
	}

	@Override
	public String toString() {
		return "Statistics [sum=" + sum + ", avg=" + avg + ", min=" + min + ", max=" + max + ", count=" + count + "]";
	}

}
