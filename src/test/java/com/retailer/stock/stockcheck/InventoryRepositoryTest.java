package com.retailer.stock.stockcheck;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.retailer.stock.stockcheck.dao.InventoryRepository;
import com.retailer.stock.stockcheck.dao.ProductsRepository;
import com.retailer.stock.stockcheck.entity.Inventory;
import com.retailer.stock.stockcheck.entity.Products;

@RunWith(SpringRunner.class)
@DataJpaTest
public class InventoryRepositoryTest {

	@Autowired
    InventoryRepository repository;
	
	@Autowired
    ProductsRepository productsRepository;
     
    @Test
    public void testRepositorySave() 
    {
        Inventory inventory = new Inventory();
        inventory.setBlocked(false);
        inventory.setProduct(productsRepository.save(new Products(10, "NewP")));
        inventory.setReorderAdditionalVolume(0);
        inventory.setReorderLevel(10);
        inventory.setReorderQuantity(10);
        inventory.setStockQuantity(10);
         
        repository.save(inventory);
         
        Assert.assertNotNull(inventory.getInventoryId());
    }
    
}
