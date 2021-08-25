package org.magnitude.capital.gain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.magnitude.stock.dao.IStockDAO;
import org.magnitude.stock.dao.StockTransactionDTO;
import org.magnitude.stock.dao.impl.StockDAOImpl;
import org.magnitude.stock.exception.SystemFailureException;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

@Configurable
public class TestStockDAOImpl {

	private IStockDAO stockDAO;

	@BeforeTest
	public void initialize() {
		StockDAOImpl stockDAOImpl = new StockDAOImpl();
		JdbcTemplate jdbcTemplate = new JdbcTemplate(DataSourceInitializer.getDataSource());
		stockDAOImpl.setJdbcTemplate(jdbcTemplate);
		stockDAO = stockDAOImpl;
	}

	@Test(description = "lookup data for FY 07-08 and data size is 1080")
	public void getAllTransactionFY07_08() throws ParseException, SystemFailureException {
		List<StockTransactionDTO> transactionDTOS = stockDAO.lookupAllStockTransaction(getDate("01/04/2007 00:00"),
				getDate("31/03/2008 23:59"));
		assert !transactionDTOS.isEmpty();
		assert transactionDTOS.size() == 1080;
	}

	@Test(description = "lookup data for FY 17-18 and data size is 0")
	public void getAllTransactionFY17_18() throws ParseException, SystemFailureException {
		List<StockTransactionDTO> transactionDTOS = stockDAO.lookupAllStockTransaction(getDate("01/04/2017 00:00"),
				getDate("31/03/2018 23:59"));
		assert transactionDTOS.isEmpty();
	}

	private static Date getDate(String date) throws ParseException {
		return new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(date);
	}
}