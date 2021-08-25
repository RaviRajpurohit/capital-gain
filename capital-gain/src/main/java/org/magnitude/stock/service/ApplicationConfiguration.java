package org.magnitude.stock.service;

import org.magnitude.stock.IStockManager;
import org.magnitude.stock.dao.IStockDAO;
import org.magnitude.stock.dao.impl.StockDAOImpl;
import org.magnitude.stock.impl.StockManagerImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {
    @Bean
    public IStockDAO getStockDAO(){
        return new StockDAOImpl();
    }
    
    @Bean
    public IStockManager getStockManager() {
    	return new StockManagerImpl();
    }
}
