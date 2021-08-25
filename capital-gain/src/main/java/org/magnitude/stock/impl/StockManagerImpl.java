package org.magnitude.stock.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;

import org.magnitude.stock.IStockManager;
import org.magnitude.stock.dao.IStockDAO;
import org.magnitude.stock.dao.StockTransactionDTO;
import org.magnitude.stock.exception.SystemFailureException;
import org.magnitude.stock.module.StockTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class StockManagerImpl implements IStockManager {

	private static final Logger logger = LoggerFactory.getLogger(StockManagerImpl.class);

	private IStockDAO stockDAO;

	@Autowired
	public void setStockDAO(IStockDAO stockDAO) {
		this.stockDAO = stockDAO;
	}

	@Override
	public double calculateProfit(Date startingDate, Date endingDate) throws SystemFailureException {
		logger.debug("Executing:: StockManagerImpl.calculateProfit(startingDate: {}, endingDate: {})...", startingDate,
				endingDate);

		// getting all the transaction
		List<StockTransactionDTO> transactionDTOs = stockDAO.lookupAllStockTransaction(startingDate, endingDate);

		// grouping all the transaction by stockName and collect into collection
		Map<String, Collection<StockTransactionDTO>> transactionGroupbyStock = transactionDTOs.stream()
				.collect(Collectors.groupingBy(StockTransactionDTO::getStockName, Collectors.toCollection(Stack::new)));

		// profit
		double profit = 0;

		// iteration on group collection
		for (String stockName : transactionGroupbyStock.keySet()) {

			Collection<StockTransactionDTO> stockTransactionDTOs = transactionGroupbyStock.get(stockName);
			double currentProfit = 0;
			// iteration on all transaction in one group entry
			for (StockTransactionDTO transactionDTO : stockTransactionDTOs) {
				// if transaction is Buy type then subtracting transaction amount else add into
				// profit
				currentProfit += transactionDTO.getType().equals(StockTransaction.TransactionType.Buy.name())
						? -(transactionDTO.getPrice() * transactionDTO.getQuantity())
						: (transactionDTO.getPrice() * transactionDTO.getQuantity());
			}
			// add all grouped transaction profit in over all profit
			profit += currentProfit;
		}

		logger.debug("Exiting:: StockManagerImpl.calculateProfit(profit: {})...", profit);
		return profit;

	}

	@Override
	public List<StockTransaction> lookupAllStockTransaction(Date startingDate, Date endingDate)
			throws SystemFailureException {
		logger.info("Executing::StockManagerImpl.lookupAllStockTransaction(startingDate: {}, endingDate: {})",
				startingDate, endingDate);

		// getting all the transaction
		List<StockTransactionDTO> transactionDTOs = stockDAO.lookupAllStockTransaction(startingDate, endingDate);

		List<StockTransaction> transactions = new ArrayList<>(transactionDTOs.size());
		if (!transactionDTOs.isEmpty()) {
			// Converting transaction dto to transaction
			for (StockTransactionDTO transactionDTO : transactionDTOs) {
				transactions
						.add(new StockTransaction(transactionDTO.getDateOfTransaction(), transactionDTO.getStockName(),
								StockTransaction.TransactionType.valueOf(transactionDTO.getType()),
								transactionDTO.getPrice(), transactionDTO.getQuantity()));
			}
		}

		logger.info("Exit::StockManagerImpl.lookupAllStockTransaction(numberOfTransaction: {})", transactions.size());
		return transactions;
	}
}
