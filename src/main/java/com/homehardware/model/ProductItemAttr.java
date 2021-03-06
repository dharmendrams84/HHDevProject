// default package
// Generated Jan 8, 2018 4:31:12 PM by Hibernate Tools 5.2.6.Final
package com.homehardware.model;

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
 * ProductItemAttr generated by hbm2java
 */
@Entity
@Table(name = "product_item_attr", catalog = "ignitiv")
public class ProductItemAttr implements java.io.Serializable {

	private Integer id;
	private int productAttrId;
	private String item;
	private String attributeValue;
	private String attributeValueFr;
	private String isProcessSuccess;
	private Integer errorCode;
	private String errorDesc;
	private String batchId;
	private String status;
	private String createBy;
	private Date createDate;
	private String updateBy;
	private Date updateDate;

	public ProductItemAttr() {
	}

	public ProductItemAttr(int productAttrId, String item) {
		this.productAttrId = productAttrId;
		this.item = item;
	}

	public ProductItemAttr(int productAttrId, String item, String attributeValue, String attributeValueFr,
			String isProcessSuccess, Integer errorCode, String errorDesc, String batchId, String status,
			String createBy, Date createDate, String updateBy, Date updateDate) {
		this.productAttrId = productAttrId;
		this.item = item;
		this.attributeValue = attributeValue;
		this.attributeValueFr = attributeValueFr;
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

	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "PRODUCT_ATTR_ID", nullable = false)
	public int getProductAttrId() {
		return this.productAttrId;
	}

	public void setProductAttrId(int productAttrId) {
		this.productAttrId = productAttrId;
	}

	@Column(name = "ITEM", nullable = false, length = 64)
	public String getItem() {
		return this.item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	@Column(name = "ATTRIBUTE_VALUE", length = 64)
	public String getAttributeValue() {
		return this.attributeValue;
	}

	public void setAttributeValue(String attributeValue) {
		this.attributeValue = attributeValue;
	}

	@Column(name = "ATTRIBUTE_VALUE_FR", length = 64)
	public String getAttributeValueFr() {
		return this.attributeValueFr;
	}

	public void setAttributeValueFr(String attributeValueFr) {
		this.attributeValueFr = attributeValueFr;
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
