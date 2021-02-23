package com.retailer.stock.stockcheck.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.retailer.stock.stockcheck.entity.Inventory;

/**
 * Inventory Repository.
 *
 */
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

}
