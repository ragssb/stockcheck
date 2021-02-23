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

/**
 * Stock Check Service.
 * 
 * @author ragssb
 *
 */
@Service
public class StockCheckServiceImpl implements StockCheckService {

	/** Inject StockCheckAuditRepository.*/
	@Autowired
	private StockCheckAuditRepository stockCheckAuditRepository;
	
	/** Inject ProductsRepository.*/
	@Autowired
	private ProductsRepository productsRepository;
	
	/** Inject RecommendedPurchaseHistoryRepository.*/
	@Autowired
	private RecommendedPurchaseHistoryRepository rphr;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<StockAdvice> stockCheckForAllProducts() {
		/**
		 * Gets all the products repository and maps it to the business model.
		 */
		List<StockAdvice> s = mapToStockAdviceList(productsRepository.findAll());
		/**
		 * Calls method to Save the Audit and recommended purchase history for the stock check done.
		 */
		saveAuditOfStockCheckAndRecommendedPurchaseHistory(s);
		/** returns StockAvice.*/ 
		return s;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public StockAdvice stockCheckByProduct(final String productName) {
		/**
		 * Gets the specific product from repository and maps it to the business model.
		 */
		StockAdvice s = mapToStockAdvice(productsRepository.findByName(productName));
		/**
		 * Calls method to Save the Audit and recommended purchase history for the stock check done.
		 */
		saveAuditOfStockCheckAndRecommendedPurchaseHistory(s);
		/** returns StockAvice.*/
		return s;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<StockAdvice> stockCheckForProductsToOrder() {
		/**
		 * Gets all the products repository to be ordered and maps it to the business model. 
		 * While doing that makes sure the blocked products are excluded and products whose 
		 * current stock level is less than the required minimum are returned.
		 */
		List<StockAdvice> s =  mapToStockAdviceList(productsRepository.findAll())
				.stream()
				.filter(stockAdvice -> !stockAdvice.isBlocked() 
						&& stockAdvice.getReorderLevel() < stockAdvice.getStockQuantity())
				.collect(Collectors.toList());
		/**
		 * Calls method to Save the Audit and recommended purchase history for the stock check done.
		 */
		saveAuditOfStockCheckAndRecommendedPurchaseHistory(s);
		/** returns StockAvice.*/
		return s;
	}

	/**
	 * Mapper method to map from DAO object (product) to Business object (StockAdvice).
	 * 
	 * @param products List products
	 * @return List of StockAdvice
	 */
	private List<StockAdvice> mapToStockAdviceList(final List<Products> products) {
		List<StockAdvice> stockCheckForAllProducts = new ArrayList<>();
		for (Products p : products)
		{
			/**
			 * Calls method to map each Product to StockAdvice.
			 */
			stockCheckForAllProducts.add(mapToStockAdvice(p));
		}
		return stockCheckForAllProducts;
	}

	/**
	 * Helper method to map from DAO object (product) to Business object (StockAdvice).
	 *  
	 * @param product Products
	 * @return StockAdvice StockAdvice
	 */
	private StockAdvice mapToStockAdvice(final Products product) {
		/**
		 * Only if product is not null. 
		 */
		if (product != null)
		{
			StockAdvice s = new StockAdvice();
			s.setProductId(product.getProductId());
			s.setProductName(product.getName());
			/**
			 * Gets inventory.
			 */
			Inventory inventory = product.getInventory().get(0);
			s.setBlocked(inventory.isBlocked());
			/**
			 * To set the quantity on order, here we get the Sum of quantity on order for a product 
			 * and while doing that make sure we pick the Order that is active.
			 */
			s.setQuantityOnOrder(product.getOrders().stream()
				.filter(order -> order.isActive())
				.mapToInt(order -> order.getOrderQuantity())
				.sum());
			s.setReorderLevel(inventory.getReorderLevel());
			s.setQuantityToOrder(inventory.getReorderLevel());
			s.setAdditionalVolumeToOrder(inventory.getReorderAdditionalVolume());
			s.setStockQuantity(inventory.getStockQuantity());
			return s;	
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StockAdvice setReorderLevelForProduct(final String productname, final Integer quantity) {
		Products product = productsRepository.findByName(productname);
		Inventory inventory = product.getInventory().get(0);
		inventory.setReorderLevel(quantity);
		productsRepository.save(product);
		return mapToStockAdvice(product);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StockAdvice blockProduct(final String productname) {
		Products product = productsRepository.findByName(productname);
		Inventory inventory = product.getInventory().get(0);
		inventory.setBlocked(true);
		productsRepository.save(product);
		return mapToStockAdvice(product);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StockAdvice setAdditionalVolumeForProduct(final String productname, final Integer additonalVolume) {
		Products product = productsRepository.findByName(productname);
		Inventory inventory = product.getInventory().get(0);
		inventory.setReorderAdditionalVolume(additonalVolume);
		productsRepository.save(product);
		return mapToStockAdvice(product);
	}
	
	/**
	 * Method to Save the Audit and recommended purchase history for the stock check done.
	 * 
	 * @param stockAdviceList List of stockAdviceList
	 */
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
	
	/**
	 * Method to Save the Audit and recommended purchase history for the stock check done.
	 * 
	 * @param stockAdvice stockAdvice
	 */
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

	/**
	 * Helper Method to map the recommended purchase history for the stock check done.
	 * 
	 * @param s stockAdvice
	 * @param sca StockCheckAudit
	 */
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
