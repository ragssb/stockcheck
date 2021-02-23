package com.retailer.stock.stockcheck;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.retailer.stock.stockcheck.controller.StockCheckController;
import com.retailer.stock.stockcheck.model.StockAdvice;
import com.retailer.stock.stockcheck.service.StockCheckService;

@WebMvcTest(StockCheckController.class)
public class WebMockTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private StockCheckService service;
	
	@Test
	public void stockCheckForAllProductsShouldReturnStockAdviceList() throws Exception {
		List<StockAdvice> sal = addStockAdviceList();
		when(service.stockCheckForAllProducts()).thenReturn(sal);
		this.mockMvc.perform(get("/stockcheck/forAllProducts"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.*.productName", hasItem(is("Tablet"))));
	}
	
	@Test
	public void stockCheckByProductShouldReturnStockAdvice() throws Exception {
		StockAdvice sa = addStockAdvice(1, "Mobile");
		when(service.stockCheckByProduct(any(String.class))).thenReturn(sa);
		this.mockMvc.perform(MockMvcRequestBuilders.get("/stockcheck/forProduct/{productName}", sa.getProductName())
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.productName").isNotEmpty());
	}
	
	@Test
	public void stockCheckForProductsToOrderShouldReturnStockAdviceList() throws Exception {
		List<StockAdvice> sal = addStockAdviceList();
		when(service.stockCheckForProductsToOrder()).thenReturn(sal);
		this.mockMvc.perform(get("/stockcheck/forProductToOrder"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.*.productName", hasItem(is("Tablet"))));
	}
	
	@Test
	public void blockProductShouldBlock() throws Exception {
		StockAdvice sa = addStockAdvice(1, "Mobile");
		sa.setBlocked(true);
		StockAdvice input = new StockAdvice();
		input.setProductName(sa.getProductName());
		when(service.blockProduct(any(String.class))).thenReturn(sa);
		this.mockMvc.perform(post("/stockcheck/blockProduct")
				.content(asJsonString(input))
				.characterEncoding("utf-8")
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.productName").isNotEmpty());
	}
	
	@Test
	public void additionalVolumeForProductShouldSetAdditionalVolume() throws Exception {
		StockAdvice sa = addStockAdvice(1, "Mobile");
		sa.setAdditionalVolumeToOrder(20);
		StockAdvice input = new StockAdvice();
		input.setProductName(sa.getProductName());
		input.setAdditionalVolumeToOrder(sa.getAdditionalVolumeToOrder());
		when(service.setAdditionalVolumeForProduct(any(String.class), any(Integer.class))).thenReturn(sa);
		this.mockMvc.perform(post("/stockcheck/additionalVolumeForProduct")
				.content(asJsonString(input))
				.characterEncoding("utf-8")
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.productName").isNotEmpty());
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
	
	public static String asJsonString(final Object obj) {
	    try {
	        return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}
}
