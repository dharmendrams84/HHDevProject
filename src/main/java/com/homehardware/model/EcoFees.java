package com.homehardware.model;
// Generated Nov 24, 2017 12:40:40 PM by Hibernate Tools 5.2.6.Final

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

/**
 * Ecofees generated by hbm2java
 */
@Entity
@Table(name = "eco_fees", catalog = "ignitiv")
@CsvRecord(separator = "\\|", skipFirstLine = true)
public class EcoFees implements java.io.Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private Integer id;
  @DataField(pos = 1)
  private String item;
  @DataField(pos = 2)
  private String prov;
  @DataField(pos = 3)
  private Double feeAmt;
  @DataField(pos = 4)
  private String ecoFeeDesc;
  @DataField(pos = 5)
  private String ecoFeeDescFr;
  private String isProcessSuccess;
  private Integer errorCode;
  private String errorDesc;
  private String batchId;
  private String status;
  private String createBy;
  private Date createDate;
  private String updateBy;
  private Date updateDate;

  public EcoFees() {
  }

  public EcoFees(String item, String prov, Double feeAmt, String ecoFeeDesc, String ecoFeeDescFr,
      String isProcessSuccess, Integer errorCode, String errorDesc, String batchId, String status, String createBy,
      Date createDate, String updateBy, Date updateDate) {
    this.item = item;
    this.prov = prov;
    this.feeAmt = feeAmt;
    this.ecoFeeDesc = ecoFeeDesc;
    this.ecoFeeDescFr = ecoFeeDescFr;
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

  @Column(name = "ITEM", length = 64)
  public String getItem() {
    return this.item;
  }

  public void setItem(String item) {
    this.item = item;
  }

  @Column(name = "PROV", length = 64)
  public String getProv() {
    return this.prov;
  }

  public void setProv(String prov) {
    this.prov = prov;
  }

  @Column(name = "FEE_AMT", precision = 22, scale = 0)
  public Double getFeeAmt() {
    return this.feeAmt;
  }

  public void setFeeAmt(Double feeAmt) {
    this.feeAmt = feeAmt;
  }

  @Column(name = "ECO_FEE_DESC", length = 100)
  public String getEcoFeeDesc() {
    return this.ecoFeeDesc;
  }

  public void setEcoFeeDesc(String ecoFeeDesc) {
    this.ecoFeeDesc = ecoFeeDesc;
  }

  @Column(name = "ECO_FEE_DESC_FR", length = 100)
  public String getEcoFeeDescFr() {
    return this.ecoFeeDescFr;
  }

  public void setEcoFeeDescFr(String ecoFeeDescFr) {
    this.ecoFeeDescFr = ecoFeeDescFr;
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
