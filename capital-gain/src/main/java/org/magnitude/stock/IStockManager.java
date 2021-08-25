package org.magnitude.stock;

import java.util.Date;
import java.util.List;

import org.magnitude.stock.exception.SystemFailureException;
import org.magnitude.stock.module.StockTransaction;

/**
 * Manager to handle business logic for requirement.
 * 
 * @author Ravi
 *
 */
public interface IStockManager {

	/**
	 * Method to calculate the profit for give FY.
	 * 
	 * @param startingDate
	 * @param endingDate
	 * @return amount after all transaction settle down for give FY: positive value
	 *         means profit, native value means loss.
	 * @throws SystemFailureException
	 */
	double calculateProfit(Date startingDate, Date endingDate) throws SystemFailureException;

	/**
	 * Method to lookup all the transaction done in given FY.
	 * 
	 * @param startingDate
	 * @param endingDate
	 * @return list of all transaction details.
	 * @throws SystemFailureException
	 */
	List<StockTransaction> lookupAllStockTransaction(Date startingDate, Date endingDate) throws SystemFailureException;
}
