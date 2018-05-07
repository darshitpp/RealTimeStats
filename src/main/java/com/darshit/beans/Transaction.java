package com.darshit.beans;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Transaction {

	@JsonProperty("amount")
	private double amount;

	@JsonProperty("timestamp")
	private Timestamp transactionTime;

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Timestamp getTransactionTime() {
		return transactionTime;
	}

	public void setTransactionTime(Timestamp transactionTime) {
		this.transactionTime = transactionTime;
	}

	public Transaction(double amount, Timestamp transactionTime) {
		super();
		this.amount = amount;
		this.transactionTime = transactionTime;
	}

	public Transaction() {
		super();
	}

}
