package com.retailer.stock.stockcheck;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.retailer.stock.stockcheck.dao.ProductsRepository;
import com.retailer.stock.stockcheck.dao.RecommendedPurchaseHistoryRepository;
import com.retailer.stock.stockcheck.dao.StockCheckAuditRepository;
import com.retailer.stock.stockcheck.entity.Products;
import com.retailer.stock.stockcheck.entity.RecommendedPurchaseHistory;
import com.retailer.stock.stockcheck.entity.StockCheckAudit;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RecommendedPurchaseHistoryRepositoryTest {
	
	@Autowired
	RecommendedPurchaseHistoryRepository repository;
	
	@Autowired
    ProductsRepository productsRepository;
	
	@Autowired
	StockCheckAuditRepository scar;
	
	@Test
    public void testRepositorySave() 
    {
		RecommendedPurchaseHistory rph = new RecommendedPurchaseHistory();
		rph.setProduct(productsRepository.save(new Products(11, "NewProduct")));
		rph.setBlocked(false);
		rph.setStockQuantity(10);
		rph.setReorderLevel(10);
		rph.setReorderQuantity(10);
		StockCheckAudit sca = new StockCheckAudit();
		sca.setCheckedBy("user");
		rph.setStockCheckAudit(scar.save(sca));
         
        repository.save(rph);
         
        Assert.assertNotNull(rph.getRphId());
    }

}
