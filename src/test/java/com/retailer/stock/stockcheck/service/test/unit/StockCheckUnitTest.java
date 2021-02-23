package com.retailer.stock.stockcheck.service.test.unit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.retailer.stock.stockcheck.model.StockAdvice;
import com.retailer.stock.stockcheck.service.StockCheckService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class StockCheckUnitTest {

	@LocalServerPort
	private int port;
	
	@Autowired
	private StockCheckService stockCheckService;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void greetingShouldReturnDefaultMessage() throws Exception {
		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/",
				String.class)).contains("Hello, World");
	}
	
	@Test
	public void testStockCheckForAllProducts() {
      // Parsing mock file
      //MangaResult mRs = JsonUtils.jsonFile2Object("ken.json", MangaResult.class);
      List<StockAdvice> sal = addStockAdviceList();
      // Mocking remote service
      when(restTemplate.getForEntity(any(String.class), any(Class.class))).thenReturn(new ResponseEntity(sal, HttpStatus.OK));
      // I search for goku but system will use mocked response containing only ken, so I can check that mock is used.
      List<StockAdvice> stockAdviceList = stockCheckService.stockCheckForAllProducts();
      assertThat(stockAdviceList).isNotNull()
          .isNotEmpty()
          .allMatch(p -> p.getProductName()
              .toLowerCase()
              .contains("Mobile"));

	}
	
	private List<StockAdvice> addStockAdviceList()
	{
		List<StockAdvice> sal = new ArrayList<>();
		sal.add(addStockAdvice(1, "Mobile"));
		sal.add(addStockAdvice(2, "Tablet"));
		sal.add(addStockAdvice(3, "Smart Watch"));
		return sal;
	}
	
	private StockAdvice addStockAdvice(Integer id, String name)
	{
		StockAdvice s = new StockAdvice();
		s.setProductId(id);
		s.setProductName(name);
		s.setStockQuantity(10);
		s.setQuantityToOrder(10);
		s.setReorderLevel(10);
		return s;
	}
}
