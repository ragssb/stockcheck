package com.retailer.stock.stockcheck;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.retailer.stock.stockcheck.controller.StockCheckController;

@SpringBootTest
public class SmokeTest {

	@Autowired
	private StockCheckController controller;

	@Test
	public void contextLoads() throws Exception {
		assertThat(controller).isNotNull();
	}
}
