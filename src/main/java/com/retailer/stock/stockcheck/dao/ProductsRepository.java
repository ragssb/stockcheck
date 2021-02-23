package com.retailer.stock.stockcheck.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.retailer.stock.stockcheck.entity.Products;

/**
 * Products Repository.
 *
 */
public interface ProductsRepository extends JpaRepository<Products, Long>{

	Products findByName(String productName);

}
