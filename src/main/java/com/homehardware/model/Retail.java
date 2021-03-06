// default package
// Generated Dec 19, 2017 4:21:22 PM by Hibernate Tools 5.2.6.Final

package com.homehardware.model;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Retail generated by hbm2java
 */
@Entity
@Table(name = "retail", catalog = "ignitiv")
public class Retail implements java.io.Serializable {

	private Integer id;
	private String item;
	private String store;
	private BigDecimal retail;
	private String isProcessSuccess;
	private Integer errorCode;
	private String errorDesc;
	private String batchId;
	private String status;
	private String createBy;
	private Date createDate;
	private String updateBy;
	private Date updateDate;

	public Retail() {
	}

	public Retail(String item, String store, BigDecimal retail, String isProcessSuccess, Integer errorCode,
			String errorDesc, String batchId, String status, String createBy, Date createDate, String updateBy,
			Date updateDate) {
		this.item = item;
		this.store = store;
		this.retail = retail;
		this.isProcessSuccess = isProcessSuccess;
		this.errorCode = errorCode;
		this.errorDesc = errorDesc;
		this.batchId = batchId;
		this.status = status;
		this.createBy = createBy;
		this.createDate = createDate;
		this.updateBy = updateBy;
		this.updateDate = updateDate;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "ITEM", length = 64)
	public String getItem() {
		return this.item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	@Column(name = "STORE", length = 10)
	public String getStore() {
		return this.store;
	}

	public void setStore(String store) {
		this.store = store;
	}

	@Column(name = "RETAIL", precision = 10)
	public BigDecimal getRetail() {
		return this.retail;
	}

	public void setRetail(BigDecimal retail) {
		this.retail = retail;
	}

	@Column(name = "IS_PROCESS_SUCCESS", length = 1)
	public String getIsProcessSuccess() {
		return this.isProcessSuccess;
	}

	public void setIsProcessSuccess(String isProcessSuccess) {
		this.isProcessSuccess = isProcessSuccess;
	}

	@Column(name = "ERROR_CODE")
	public Integer getErrorCode() {
		return this.errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	@Column(name = "ERROR_DESC", length = 65535)
	public String getErrorDesc() {
		return this.errorDesc;
	}

	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}

	@Column(name = "BATCH_ID", length = 50)
	public String getBatchId() {
		return this.batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	@Column(name = "STATUS", length = 45)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "CREATE_BY", length = 50)
	public String getCreateBy() {
		return this.createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_DATE", length = 19)
	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Column(name = "UPDATE_BY", length = 50)
	public String getUpdateBy() {
		return this.updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATE_DATE", length = 19)
	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

}
