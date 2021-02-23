package com.retailer.stock.stockcheck;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.retailer.stock.stockcheck.dao.ProductsRepository;
import com.retailer.stock.stockcheck.dao.RecommendedPurchaseHistoryRepository;
import com.retailer.stock.stockcheck.dao.StockCheckAuditRepository;
import com.retailer.stock.stockcheck.entity.Inventory;
import com.retailer.stock.stockcheck.entity.Products;
import com.retailer.stock.stockcheck.entity.StockCheckAudit;
import com.retailer.stock.stockcheck.model.StockAdvice;
import com.retailer.stock.stockcheck.service.StockCheckService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StockCheckServiceTest {
	
  @Autowired
  private StockCheckService stockCheckServiceToTest;
  
  @MockBean
  private RecommendedPurchaseHistoryRepository rphr;

  @MockBean
  private ProductsRepository productsRepository;

  @MockBean
  private StockCheckAuditRepository stockCheckAuditRepository;

  public void setUp()  {
	  List<Products> list = new ArrayList<>();
      Products p1 = new Products(1, "A");
      Inventory inventory1 = new Inventory();
      inventory1.setInventoryId(1);
      p1.getInventory().add(inventory1);
      p1.getInventory().get(0).setStockQuantity(5);
      p1.getInventory().get(0).setReorderLevel(4);
      p1.getInventory().get(0).setBlocked(false);
      Products p2 = new Products(2, "B");
      Inventory inventory2 = new Inventory();
      inventory2.setInventoryId(1);
      p2.getInventory().add(inventory2);
      p2.getInventory().get(0).setBlocked(false);
      p2.getInventory().get(0).setStockQuantity(5);
      p2.getInventory().get(0).setReorderLevel(4);
      Products p3 = new Products(3, "C");
      Inventory inventory3 = new Inventory();
      inventory3.setInventoryId(1);
      p3.getInventory().add(inventory3);
      p3.getInventory().get(0).setBlocked(true);
      p3.getInventory().get(0).setStockQuantity(5);
      p3.getInventory().get(0).setReorderLevel(4);
       
      list.add(p1);
      list.add(p2);
      list.add(p3);
      
      when(productsRepository.findAll()).thenReturn(list);
      when(productsRepository.findByName(any(String.class))).thenReturn(p1);
      when(stockCheckAuditRepository.save(any(StockCheckAudit.class))).thenReturn(null);
      when(rphr.saveAll(any(List.class))).thenReturn(null);

  }

  @Test
  public void testStockCheckForAllProductsShouldSucceed(){
	  setUp();
	  List<StockAdvice> returnList = stockCheckServiceToTest.stockCheckForAllProducts();
      
      Assert.assertEquals(3, returnList.size());
  }
  
  @Test
  public void testStockCheckByProductShouldSucceed(){
	  setUp();
	  StockAdvice returnVal = stockCheckServiceToTest.stockCheckByProduct("A");
      
      Assert.assertEquals(returnVal.getProductName(), "A");
  }
  
  @Test
  public void testStockCheckForProductsToOrderShouldSucceed(){
	  setUp();
	  List<StockAdvice> returnList = stockCheckServiceToTest.stockCheckForProductsToOrder();
      
      Assert.assertEquals(2, returnList.size());
  }
  
  @Test
  public void testBlockProductShouldSucceed(){
	  setUp();
	  StockAdvice returnVal = stockCheckServiceToTest.blockProduct("A");
      
      Assert.assertEquals(returnVal.getProductName(), "A");
      Assert.assertEquals(returnVal.isBlocked(), true);
  }
  
  @Test
  public void testSetAdditionalVolumeForProductShouldSucceed(){
	  setUp();
	  StockAdvice returnVal = stockCheckServiceToTest.setAdditionalVolumeForProduct("A", 20);
      
      Assert.assertEquals(returnVal.getProductName(), "A");
      Assert.assertEquals(returnVal.isBlocked(), false);
      Assert.assertEquals(returnVal.getAdditionalVolumeToOrder(), 20);
  }
  
  @Test
  public void testSetReorderLevelForProductShouldSucceed(){
	  setUp();
	  StockAdvice returnVal = stockCheckServiceToTest.setReorderLevelForProduct("A", 10);
      
      Assert.assertEquals(returnVal.getProductName(), "A");
      Assert.assertEquals(returnVal.isBlocked(), false);
      Assert.assertEquals(returnVal.getReorderLevel(), 10);
  }

}
