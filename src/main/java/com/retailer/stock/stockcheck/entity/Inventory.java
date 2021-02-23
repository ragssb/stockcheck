package com.retailer.stock.stockcheck.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;



@Entity
@Table(name = "Inventory")
public class Inventory {
	
	@Id
	@Column(name = "inventory_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer inventoryId;
	
	@ManyToOne
	@JoinColumn(name= "product_id")
	private Products product;
	
	@Column(name="stock_quantity")
	private int stockQuantity;
	
	@Column(name="reorder_level")
	private int reorderLevel;
	
	@Column(name="reorder_quantity")
	private int reorderQuantity;
	
	@Column(name="reorder_additional_volume")
	private int reorderAdditionalVolume;
	
	@Column(name="is_blocked")
	private boolean isBlocked;

	public Inventory() {
	}

	public Integer getInventoryId() {
		return inventoryId;
	}

	public void setInventoryId(final Integer inventoryId) {
		this.inventoryId = inventoryId;
	}

	public Products getProduct() {
		return product;
	}

	public void setProduct(final Products product) {
		this.product = product;
	}

	public int getStockQuantity() {
		return stockQuantity;
	}

	public void setStockQuantity(final int stockQuantity) {
		this.stockQuantity = stockQuantity;
	}

	public int getReorderLevel() {
		return reorderLevel;
	}

	public void setReorderLevel(final int reorderLevel) {
		this.reorderLevel = reorderLevel;
	}

	public int getReorderQuantity() {
		return reorderQuantity;
	}

	public void setReorderQuantity(final int reorderQuantity) {
		this.reorderQuantity = reorderQuantity;
	}

	public int getReorderAdditionalVolume() {
		return reorderAdditionalVolume;
	}

	public void setReorderAdditionalVolume(final int reorderAdditionalVolume) {
		this.reorderAdditionalVolume = reorderAdditionalVolume;
	}

	public boolean isBlocked() {
		return isBlocked;
	}

	public void setBlocked(final boolean isBlocked) {
		this.isBlocked = isBlocked;
	}
	
	

}
