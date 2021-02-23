package com.retailer.stock.stockcheck.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.retailer.stock.stockcheck.entity.StockCheckAudit;

public interface StockCheckAuditRepository extends JpaRepository<StockCheckAudit, Long> {

}
