package com.retailer.stock.stockcheck.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.retailer.stock.stockcheck.entity.RecommendedPurchaseHistory;

/**
 * RecommendedPurchaseHistory Repository.
 *
 */
public interface RecommendedPurchaseHistoryRepository extends JpaRepository<RecommendedPurchaseHistory, Long> {

}
