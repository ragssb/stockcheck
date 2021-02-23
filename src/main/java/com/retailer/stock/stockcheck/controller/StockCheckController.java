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
	
	@Autowired
	private StockCheckService stockCheckService;
	
	@GetMapping(path = "/forAllProducts")
	public List<StockAdvice> stockCheckForAllProducts()
	{
		return stockCheckService.stockCheckForAllProducts();
	}
	
	@GetMapping(path = "/forProduct/{productName}")
	public StockAdvice stockCheckByProduct(@PathVariable("productName") final String productName)
	{
		return stockCheckService.stockCheckByProduct(productName);
	}
	
	@GetMapping(path = "/forProductToOrder")
	public List<StockAdvice> stockCheckForProductsToOrder()
	{
		return stockCheckService.stockCheckForProductsToOrder();
	}
	
	@PostMapping(path = "/blockProduct")
	public StockAdvice blockProduct(@RequestBody StockAdvice stockAdvice)
	{
		return stockCheckService.blockProduct(stockAdvice.getProductName());
	}
	
	@PostMapping(path = "/additionalVolumeForProduct")
	public StockAdvice additionalVolumeForProduct(@RequestBody StockAdvice stockAdvice)
	{
		return stockCheckService.setAdditionalVolumeForProduct(stockAdvice.getProductName(), 
				stockAdvice.getAdditionalVolumeToOrder());
	}
	
	@PostMapping(path = "/setReorderLevelForProduct")
	public StockAdvice setReorderLevelForProduct(@RequestBody StockAdvice stockAdvice)
	{
		return stockCheckService.setReorderLevelForProduct(stockAdvice.getProductName(), 
				stockAdvice.getReorderLevel());
	}

}
