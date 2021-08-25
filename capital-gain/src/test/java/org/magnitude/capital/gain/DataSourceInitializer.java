package org.magnitude.capital.gain;

import javax.sql.DataSource;

import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

/**
 * Class to initialize datasource for unit testing with h2 database.
 * @author Ravi
 *
 */
public class DataSourceInitializer {

	private static DataSource dataSource;
	
	public static DataSource getDataSource() {
		if(dataSource != null) {
			return dataSource;
		}
		dataSource = createDataSource();
		DatabasePopulatorUtils.execute(createDatabasePopulator(), dataSource);
		return dataSource;
	}

	private static DatabasePopulator createDatabasePopulator() {
		ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
		databasePopulator.setContinueOnError(true);
		databasePopulator.addScript(new ClassPathResource("stock-db.sql"));
		return databasePopulator;
	}

	private static SimpleDriverDataSource createDataSource() {
		SimpleDriverDataSource simpleDriverDataSource = new SimpleDriverDataSource();
		simpleDriverDataSource.setDriverClass(org.h2.Driver.class);
		simpleDriverDataSource.setUrl("jdbc:h2:./target/stock;AUTO_RECONNECT=TRUE");
		simpleDriverDataSource.setUsername("sa");
		simpleDriverDataSource.setPassword("");
		return simpleDriverDataSource;
	}
	
}
