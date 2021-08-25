package org.magnitude.stock.module;

import java.util.Date;

/**
 * Pojo for StockTransaction entry
 * @author Ravi
 *
 */
public class StockTransaction {

	public enum TransactionType {
		// enum for transaction type to make value as predefined
		Buy, Sell
	}

	private final Date dateOfTransaction;
	private final String stock;
	private final TransactionType type;
	private final double price;
	private final int quantity;

	public StockTransaction(Date dateOfTransaction, String stock, TransactionType type, double price, int quantity) {
		this.dateOfTransaction = dateOfTransaction;
		this.stock = stock;
		this.type = type;
		this.price = price;
		this.quantity = quantity;
	}

	public Date getDateOfTransaction() {
		return dateOfTransaction;
	}

	public String getStock() {
		return stock;
	}

	public TransactionType getType() {
		return type;
	}

	public double getPrice() {
		return price;
	}

	public int getQuantity() {
		return quantity;
	}
}
