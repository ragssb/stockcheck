package com.retailer.stock.stockcheck.entity;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "Orders")
public class Orders {

	@Id
	@Column(name = "order_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer orderId;
	
	@Basic
	@Temporal(TemporalType.DATE)
	@Column(name="order_date")
	private Date orderDate;
	
	@ManyToOne
	@JoinColumn(name= "product_id")
	private Products product;
	
	@Column(name="order_quantity")
	private int orderQuantity;
	
	@Column(name="is_active")
	private boolean isActive;

	public Orders() {
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(final Integer orderId) {
		this.orderId = orderId;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(final Date orderDate) {
		this.orderDate = orderDate;
	}

	public Products getProduct() {
		return product;
	}

	public void setProduct(final Products product) {
		this.product = product;
	}

	public int getOrderQuantity() {
		return orderQuantity;
	}

	public void setOrderQuantity(final int orderQuantity) {
		this.orderQuantity = orderQuantity;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(final boolean isActive) {
		this.isActive = isActive;
	}
	
	
	
}
