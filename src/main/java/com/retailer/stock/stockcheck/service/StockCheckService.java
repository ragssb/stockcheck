package com.retailer.stock.stockcheck.service;

import java.util.List;

import com.retailer.stock.stockcheck.model.StockAdvice;

public interface StockCheckService {
	
	List<StockAdvice> stockCheckForAllProducts();
	
	StockAdvice stockCheckByProduct(String productName);
	
	List<StockAdvice> stockCheckForProductsToOrder();
	
	StockAdvice setReorderLevelForProduct(String productname, Integer quantity);
	
	StockAdvice blockProduct(String productname);
	
	StockAdvice setAdditionalVolumeForProduct(String productname, Integer additonalVolume);

}
