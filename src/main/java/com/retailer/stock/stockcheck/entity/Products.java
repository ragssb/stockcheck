package com.retailer.stock.stockcheck.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Products")
public class Products {

	@Id
	@Column(name = "product_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer productId;

	@Column(name = "product_name")
	private String name;

	@OneToMany(mappedBy = "product")
	private List<Inventory> inventory = new ArrayList<>();
	
	public Products() {
		super();
		// TODO Auto-generated constructor stub
	}	

	public Products(final Integer productId, final String name) {
		super();
		this.productId = productId;
		this.name = name;
	}

	public List<Inventory> getInventory() {
		return inventory;
	}


	public void setInventory(final List<Inventory> inventory) {
		this.inventory = inventory;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(final Integer productId) {
		this.productId = productId;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	

}
