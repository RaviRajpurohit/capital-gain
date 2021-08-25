package org.magnitude.stock.dao;

import java.util.Date;

/**
 * DTO to communicate with DAO layer.
 * 
 * @author Ravi
 *
 */
public class StockTransactionDTO {

	private Date dateOfTransaction;
	private String stockName;
	private String type;
	private double price;
	private int quantity;

	public StockTransactionDTO(Date dateOfTransaction, String stockName, String type, double price, int quantity) {
		this.dateOfTransaction = dateOfTransaction;
		this.stockName = stockName;
		this.type = type;
		this.price = price;
		this.quantity = quantity;
	}

	public Date getDateOfTransaction() {
		return dateOfTransaction;
	}

	public String getStockName() {
		return stockName;
	}

	public String getType() {
		return type;
	}

	public double getPrice() {
		return price;
	}

	public int getQuantity() {
		return quantity;
	}
}
