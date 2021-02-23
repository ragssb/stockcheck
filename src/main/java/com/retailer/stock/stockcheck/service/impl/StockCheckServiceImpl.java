package com.retailer.stock.stockcheck.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.retailer.stock.stockcheck.dao.ProductsRepository;
import com.retailer.stock.stockcheck.dao.RecommendedPurchaseHistoryRepository;
import com.retailer.stock.stockcheck.dao.StockCheckAuditRepository;
import com.retailer.stock.stockcheck.entity.Inventory;
import com.retailer.stock.stockcheck.entity.Products;
import com.retailer.stock.stockcheck.entity.RecommendedPurchaseHistory;
import com.retailer.stock.stockcheck.entity.StockCheckAudit;
import com.retailer.stock.stockcheck.model.StockAdvice;
import com.retailer.stock.stockcheck.service.StockCheckService;

@Service
public class StockCheckServiceImpl implements StockCheckService {

	@Autowired
	private StockCheckAuditRepository stockCheckAuditRepository;
	
	@Autowired
	private ProductsRepository productsRepository;
	
	@Autowired
	private RecommendedPurchaseHistoryRepository rphr;
	
	@Override
	public List<StockAdvice> stockCheckForAllProducts() {
		List<StockAdvice> s = mapToStockAdviceList(productsRepository.findAll());
		saveAuditOfStockCheckAndRecommendedPurchaseHistory(s);
		return s;
	}
	
	@Override
	public StockAdvice stockCheckByProduct(final String productName) {
		StockAdvice s = mapToStockAdvice(productsRepository.findByName(productName));
		saveAuditOfStockCheckAndRecommendedPurchaseHistory(s);
		return s;
	}
	
	@Override
	public List<StockAdvice> stockCheckForProductsToOrder() {
		
		List<StockAdvice> s =  mapToStockAdviceList(productsRepository.findAll())
				.stream()
				.filter(stockAdvice -> !stockAdvice.isBlocked() 
						&& stockAdvice.getReorderLevel() < stockAdvice.getStockQuantity())
				.collect(Collectors.toList());
		saveAuditOfStockCheckAndRecommendedPurchaseHistory(s);
		return s;
	}

	private List<StockAdvice> mapToStockAdviceList(final List<Products> products) {
		List<StockAdvice> stockCheckForAllProducts = new ArrayList<>();
		for (Products p : products)
		{
			stockCheckForAllProducts.add(mapToStockAdvice(p));
		}
		return stockCheckForAllProducts;
	}

	private StockAdvice mapToStockAdvice(final Products product) {
		if (product != null)
		{
			StockAdvice s = new StockAdvice();
			s.setProductId(product.getProductId());
			s.setProductName(product.getName());
			Inventory inventory = product.getInventory().get(0);
			s.setBlocked(inventory.isBlocked());
			//s.setQuantityOnOrder(inventory.get);
			s.setReorderLevel(inventory.getReorderLevel());
			s.setQuantityToOrder(inventory.getReorderLevel());
			s.setAdditionalVolumeToOrder(inventory.getReorderAdditionalVolume());
			s.setStockQuantity(inventory.getStockQuantity());
			return s;	
		}
		return null;
	}

	@Override
	public StockAdvice setReorderLevelForProduct(final String productname, final Integer quantity) {
		Products product = productsRepository.findByName(productname);
		Inventory inventory = product.getInventory().get(0);
		inventory.setReorderLevel(quantity);
		productsRepository.save(product);
		return mapToStockAdvice(product);
	}

	@Override
	public StockAdvice blockProduct(final String productname) {
		Products product = productsRepository.findByName(productname);
		Inventory inventory = product.getInventory().get(0);
		inventory.setBlocked(true);
		productsRepository.save(product);
		return mapToStockAdvice(product);
	}

	@Override
	public StockAdvice setAdditionalVolumeForProduct(final String productname, final Integer additonalVolume) {
		Products product = productsRepository.findByName(productname);
		Inventory inventory = product.getInventory().get(0);
		inventory.setReorderAdditionalVolume(additonalVolume);
		productsRepository.save(product);
		return mapToStockAdvice(product);
	}
	
	private void saveAuditOfStockCheckAndRecommendedPurchaseHistory(final List<StockAdvice> stockAdviceList)
	{
		if (stockAdviceList.size() > 0)
		{
			StockCheckAudit sca = new StockCheckAudit();
			sca.setCheckedBy("user");
			for (StockAdvice s : stockAdviceList)
			{
				sca.getRecommendedPurchaseHistory().add(mapToRecommendedPurchaseHistory(s, sca));
			}
			stockCheckAuditRepository.save(sca);
			rphr.saveAll(sca.getRecommendedPurchaseHistory());
		}		
	}
	
	private void saveAuditOfStockCheckAndRecommendedPurchaseHistory(final StockAdvice stockAdvice)
	{
		if (stockAdvice != null && stockAdvice.getProductId() != 0)
		{
			StockCheckAudit sca = new StockCheckAudit();
			sca.setCheckedBy("user");
			sca.getRecommendedPurchaseHistory().add(mapToRecommendedPurchaseHistory(stockAdvice, sca));
			stockCheckAuditRepository.save(sca);
			rphr.saveAll(sca.getRecommendedPurchaseHistory());
		}		
	}

	private RecommendedPurchaseHistory mapToRecommendedPurchaseHistory(final StockAdvice s, final StockCheckAudit sca) 
	{
		RecommendedPurchaseHistory r = new RecommendedPurchaseHistory();
		r.setProduct(productsRepository.findByName(s.getProductName()));
		r.setBlocked(s.isBlocked());
		r.setReorderAdditionalVolume(s.getAdditionalVolumeToOrder());
		r.setReorderLevel(s.getReorderLevel());
		r.setReorderQuantity(s.getQuantityToOrder());
		r.setStockQuantity(s.getStockQuantity());
		r.setStockCheckAudit(sca);
		return r;
	}

}
