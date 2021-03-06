package com.retailer.stock.stockcheck.model;

/**
 * Stock Advice return object. 
 *
 */
public class StockAdvice {
	
	/** The product Id. */ 
	private Integer productId;
	
	/** The product Name. */
	private String productName;
	
	/** The current stock of the product. */
	private int stockQuantity;
	
	/** The minimum stock for the product. */
	private int reorderLevel;
	
	/** The quantity on order which is active. */
	private int quantityOnOrder;
	
	/** The recommended quantity to be ordered. */
	private int quantityToOrder;
	
	/** The additional volume that needs to be ordered. */
	private int additionalVolumeToOrder;	
	
	/** Marks a product as blocked. */
	private boolean isBlocked;

	public StockAdvice() {
		
	}	

	public StockAdvice(final Integer productId, final String productName, final int stockQuantity, 
			final int reorderLevel, final int quantityOnOrder, final int quantityToOrder, 
			final int additionalVolumeToOrder, final boolean isBlocked) 
	{
		super();
		this.productId = productId;
		this.productName = productName;
		this.stockQuantity = stockQuantity;
		this.reorderLevel = reorderLevel;
		this.quantityOnOrder = quantityOnOrder;
		this.quantityToOrder = quantityToOrder;
		this.additionalVolumeToOrder = additionalVolumeToOrder;
		this.isBlocked = isBlocked;
	}

	public int getReorderLevel() {
		return reorderLevel;
	}

	public void setReorderLevel(final int reorderLevel) {
		this.reorderLevel = reorderLevel;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(final Integer productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(final String productName) {
		this.productName = productName;
	}

	public int getStockQuantity() {
		return stockQuantity;
	}

	public void setStockQuantity(final int stockQuantity) {
		this.stockQuantity = stockQuantity;
	}

	public int getQuantityOnOrder() {
		return quantityOnOrder;
	}

	public void setQuantityOnOrder(final int quantityOnOrder) {
		this.quantityOnOrder = quantityOnOrder;
	}

	public int getQuantityToOrder() {
		return quantityToOrder;
	}

	public void setQuantityToOrder(final int quantityToOrder) {
		this.quantityToOrder = quantityToOrder;
	}
	
	public int getAdditionalVolumeToOrder() {
		return additionalVolumeToOrder;
	}

	public void setAdditionalVolumeToOrder(final int additionalVolumeToOrder) {
		this.additionalVolumeToOrder = additionalVolumeToOrder;
	}


	public boolean isBlocked() {
		return isBlocked;
	}

	public void setBlocked(final boolean isBlocked) {
		this.isBlocked = isBlocked;
	}
	
	

}
