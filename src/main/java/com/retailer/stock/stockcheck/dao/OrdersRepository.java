package com.retailer.stock.stockcheck.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.retailer.stock.stockcheck.entity.Products;

public interface OrdersRepository extends JpaRepository<Products, Long>{
	
//	Orders findByProductId(int productId);

}
