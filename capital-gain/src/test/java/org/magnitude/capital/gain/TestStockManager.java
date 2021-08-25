package org.magnitude.capital.gain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.magnitude.stock.IStockManager;
import org.magnitude.stock.dao.impl.StockDAOImpl;
import org.magnitude.stock.exception.SystemFailureException;
import org.magnitude.stock.impl.StockManagerImpl;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TestStockManager {

	private IStockManager stockManager;

	@BeforeTest
	public void intialize() {
		StockManagerImpl stockManagerImpl = new StockManagerImpl();

		StockDAOImpl stockDAO = new StockDAOImpl();
		stockDAO.setJdbcTemplate(new JdbcTemplate(DataSourceInitializer.getDataSource()));
		stockManagerImpl.setStockDAO(stockDAO);
		stockManager = stockManagerImpl;
	}

	@Test(description = "Method to get Profit of FY 07-08")
	public void calcualteProfitFY07_08() throws SystemFailureException, ParseException {
		double profit = stockManager.calculateProfit(getDate("01/04/2007 00:00"), getDate("31/03/2008 23:59"));
		assert profit == 15465.53946685791;
	}

	@Test(description = "Method to get Profit of FY 17-18")
	public void calcualteProfitFY17_18() throws SystemFailureException, ParseException {
		double profit = stockManager.calculateProfit(getDate("01/04/2017 00:00"), getDate("31/03/2018 23:59"));
		assert profit == 0;
	}

	private static Date getDate(String date) throws ParseException {
		return new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(date);
	}
}
