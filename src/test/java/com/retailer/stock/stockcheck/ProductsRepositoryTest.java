package com.retailer.stock.stockcheck;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.retailer.stock.stockcheck.dao.ProductsRepository;
import com.retailer.stock.stockcheck.entity.Products;
 
@RunWith(SpringRunner.class)
@DataJpaTest
public class ProductsRepositoryTest 
{
    @Autowired
    ProductsRepository repository;
     
    @Test
    public void testRepositorySave() 
    {
        Products product = new Products();
        product.setName("A");
         
        repository.save(product);
         
        Assert.assertNotNull(product.getProductId());
    }
    
    @Test
    public void testRepositoryFindByName() 
    {
        Products product = new Products();
        product.setName("NewProduct");

        repository.save(product);
        Products newProduct = repository.findByName("NewProduct");
         
        Assert.assertNotNull(newProduct);
        Assert.assertEquals("NewProduct", newProduct.getName());
    }
}
