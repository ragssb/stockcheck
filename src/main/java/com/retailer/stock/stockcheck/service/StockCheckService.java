package com.retailer.stock.stockcheck.service;

import java.util.List;

import com.retailer.stock.stockcheck.model.StockAdvice;

public interface StockCheckService {
	
	/**
	 * Method to return stock check for all the products.
	 * 
	 * @return List of StockAdvice 
	 */
	List<StockAdvice> stockCheckForAllProducts();
	
	/**
	 * Method to return stock check for a specific product.
	 * 
	 * @return List of StockAdvice 
	 */
	StockAdvice stockCheckByProduct(String productName);
	
	/**
	 * Method to return all the products to be ordered.
	 * 
	 * @return List of StockAdvice 
	 */
	List<StockAdvice> stockCheckForProductsToOrder();
	
	/**
	 * Method to set the reorder level (minimum stock) quantity for a specific product.
	 * 
	 * @param productName String
	 * @param quantity Integer
	 * @return StockAdvice StockAdvice
	 */
	StockAdvice setReorderLevelForProduct(String productname, Integer quantity);
	
	/**
	 * Method to set a specific product as blocked.
	 * 
	 * @param productName String
	 * @return StockAdvice StockAdvice
	 */
	StockAdvice blockProduct(String productname);
	
	/**
	 * Method to set the Additional Volume to be ordered for a specific product.
	 * 
	 * @param productName String
	 * @param quantity Integer
	 * @return StockAdvice StockAdvice
	 */
	StockAdvice setAdditionalVolumeForProduct(String productname, Integer additonalVolume);

}
