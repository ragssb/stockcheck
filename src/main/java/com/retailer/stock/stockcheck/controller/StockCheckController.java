package com.retailer.stock.stockcheck.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.retailer.stock.stockcheck.model.StockAdvice;
import com.retailer.stock.stockcheck.service.StockCheckService;

@RestController
@RequestMapping("/stockcheck")
public class StockCheckController {
	
	/**
	 * Inject the StockCheckService.
	 */
	@Autowired
	private StockCheckService stockCheckService;
	
	/**
	 * Rest end point to check stock for all products.
	 * 
	 * @return List of StockAdvice 
	 */
	@GetMapping(path = "/forAllProducts")
	public List<StockAdvice> stockCheckForAllProducts()
	{
		return stockCheckService.stockCheckForAllProducts();
	}
	
	/**
	 * Rest end point to check stock for a specific product.
	 * 
	 * @param productName Name of the product to check stock for
	 * @return List of StockAdvice 
	 */
	@GetMapping(path = "/forProduct/{productName}")
	public StockAdvice stockCheckByProduct(@PathVariable("productName") final String productName)
	{
		return stockCheckService.stockCheckByProduct(productName);
	}
	
	/**
	 * Rest end point to check all products that should be ordered. This brings back 
	 * all the products that have current stock less than the required minimum level. 
	 * 
	 * @return List of StockAdvice 
	 */
	@GetMapping(path = "/forProductToOrder")
	public List<StockAdvice> stockCheckForProductsToOrder()
	{
		return stockCheckService.stockCheckForProductsToOrder();
	}
	
	/**
	 * Rest end point to mark a specific product as blocked.
	 * 
	 * @param StockAdvice contains Name of the product to be marked as blocked.
	 * @return StockAdvice StockAdvice 
	 */
	@PostMapping(path = "/blockProduct")
	public StockAdvice blockProduct(@RequestBody StockAdvice stockAdvice)
	{
		return stockCheckService.blockProduct(stockAdvice.getProductName());
	}
	
	/**
	 * Rest end point to mark add additional volume to order for a specific product.
	 * 
	 * @param StockAdvice contains the product name and additional volume to be added.
	 * @return StockAdvice StockAdvice 
	 */
	@PostMapping(path = "/additionalVolumeForProduct")
	public StockAdvice additionalVolumeForProduct(@RequestBody StockAdvice stockAdvice)
	{
		return stockCheckService.setAdditionalVolumeForProduct(stockAdvice.getProductName(), 
				stockAdvice.getAdditionalVolumeToOrder());
	}
	
	/**
	 * Rest end point to set reorder level (or minimum stock) for a specific product.
	 * 
	 * @param StockAdvice contains the product name and minimum stock to be set.
	 * @return StockAdvice StockAdvice 
	 */
	@PostMapping(path = "/setReorderLevelForProduct")
	public StockAdvice setReorderLevelForProduct(@RequestBody StockAdvice stockAdvice)
	{
		return stockCheckService.setReorderLevelForProduct(stockAdvice.getProductName(), 
				stockAdvice.getReorderLevel());
	}

}
