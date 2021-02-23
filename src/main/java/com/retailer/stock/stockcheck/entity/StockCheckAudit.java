package com.retailer.stock.stockcheck.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "stock_check_audit")
public class StockCheckAudit {

	@Id
	@Column(name = "audit_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer auditId;
	
	@Basic
	@Temporal(TemporalType.DATE)
	@Column(name="checked_date")
	private Date checkedDate;
	
	@Column(name = "checked_by")
	private String checkedBy;
	
	@OneToMany(mappedBy = "stockCheckAudit")
	private List<RecommendedPurchaseHistory> recommendedPurchaseHistory = new ArrayList<>();

	public StockCheckAudit() {
		
	}

	public Integer getAuditId() {
		return auditId;
	}

	public void setAuditId(final Integer auditId) {
		this.auditId = auditId;
	}

	public Date getCheckedDate() {
		return checkedDate;
	}

	public void setCheckedDate(final Date checkedDate) {
		this.checkedDate = checkedDate;
	}

	public String getCheckedBy() {
		return checkedBy;
	}

	public void setCheckedBy(final String checkedBy) {
		this.checkedBy = checkedBy;
	}

	public List<RecommendedPurchaseHistory> getRecommendedPurchaseHistory() {
		return recommendedPurchaseHistory;
	}

	public void setRecommendedPurchaseHistory(final List<RecommendedPurchaseHistory> recommendedPurchaseHistory) {
		this.recommendedPurchaseHistory = recommendedPurchaseHistory;
	}
	
	
	
}
