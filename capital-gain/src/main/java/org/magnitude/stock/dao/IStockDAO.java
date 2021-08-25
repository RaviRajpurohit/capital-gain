package org.magnitude.stock.dao;

import org.magnitude.stock.exception.SystemFailureException;

import java.util.Date;
import java.util.List;

/**
 * DAO to communicate with database.
 * @author Ravi
 *
 */
public interface IStockDAO {

	/**
	 * Method to lookup all the transaction for given FY.
	 * @param startingDate
	 * @param endingDate
	 * @return list of all transaction details.
	 * @throws SystemFailureException
	 */
    List<StockTransactionDTO> lookupAllStockTransaction(Date startingDate, Date endingDate) throws SystemFailureException;
}
