package org.magnitude.stock.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.magnitude.stock.IStockManager;
import org.magnitude.stock.exception.SystemFailureException;
import org.magnitude.stock.module.StockTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Service for Stock transaction.
 * 
 * @author Ravi
 *
 */
@SpringBootApplication
@RestController
@RequestMapping("/stock")
public class StockService {

	private static final Logger logger = LoggerFactory.getLogger(StockService.class);

	@Autowired
	IStockManager stockManager;

	/**
	 * Service method to lookup all the transaction for given FY.
	 * 
	 * @param financialYear
	 * @return list of all transaction details.
	 * @throws SystemFailureException
	 */
	@GetMapping("/{fy}")
	@ResponseBody
	public List<StockTransaction> lookupAllTransactions(@PathVariable("fy") String financialYear)
			throws SystemFailureException {
		logger.info("Executing::StockService.lookupAllTransactions(financialYear: {})", financialYear);
		Date[] dates = this.getFinancialDate(financialYear);
		List<StockTransaction> trnsactions = stockManager.lookupAllStockTransaction(dates[0], dates[1]);
		logger.info("Exiting::StockService.lookupAllTransactions(NumberOfTransaction: {})", trnsactions.size());
		return trnsactions;

	}

	/**
	 * Method to get profit for given FY.
	 * 
	 * @param financialYear
	 * @return amount after all transaction settle down for give FY: positive value
	 *         means profit, native value means loss.
	 * @throws SystemFailureException
	 */
	@GetMapping(value = { "/profit/{fy}", "/loss/{fy}" })
	@ResponseBody
	public double calcualteProfit(@PathVariable("fy") String financialYear) throws SystemFailureException {

		logger.info("Executing:: StockService.calcualteProfit(financialYear: {})", financialYear);
		Date[] dates = this.getFinancialDate(financialYear);
		double profit = stockManager.calculateProfit(dates[0], dates[1]);
		logger.info("Exiting:: StockService.calcualteProfit(profit: {})", profit);
		return profit;
	}

	/**
	 * Method to get financial year date.
	 * 
	 * @param financialYear
	 * @return
	 */
	private Date[] getFinancialDate(String financialYear) {
 
		if (financialYear == null || financialYear.isEmpty() || !financialYear.contains("-") || financialYear.split("-").length != 2) {
			logger.error("To start the Capital Gain Calculator, you have to pass FY as parameter. example: 20-21");
			throw new IllegalArgumentException(
					"To start the Capital Gain Calculator, you have to pass FY as parameter. example: 20-21");
		}

		String[] years = financialYear.split("-");
		Date startingDate = getDate("01/04/20" + years[0] + " 00:00");
		Date endingDate = getDate("31/03/20" + years[1] + " 23:59");

		if (!startingDate.before(endingDate)) {
			logger.error("Invalid financial year. Please provid correct year.");
			throw new IllegalArgumentException("Invalid financial year. Please provid correct year.");
		}
		return new Date[] { startingDate, endingDate };
	}

	private static Date getDate(String date) {
		try {
			return new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(date);
		} catch (ParseException e) {
			logger.error("Invalid finalcial year.", e);
			throw new IllegalArgumentException("Invalid finalcial year.", e);
		}
	}

}
