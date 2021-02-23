package com.retailer.stock.stockcheck;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.retailer.stock.stockcheck.dao.StockCheckAuditRepository;
import com.retailer.stock.stockcheck.entity.StockCheckAudit;

@RunWith(SpringRunner.class)
@DataJpaTest
public class StockCheckAuditRepositoryTest {
	
	@Autowired
	StockCheckAuditRepository repository;
	
	@Test
    public void testRepositorySave() 
    {
		StockCheckAudit stockCheckAudit = new StockCheckAudit();
		stockCheckAudit.setCheckedBy("user");
         
        repository.save(stockCheckAudit);
         
        Assert.assertNotNull(stockCheckAudit.getAuditId());
    }

}
