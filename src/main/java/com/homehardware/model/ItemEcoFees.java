package com.homehardware.model;
// Generated Nov 27, 2017 1:12:39 PM by Hibernate Tools 5.2.6.Final

import static javax.persistence.GenerationType.IDENTITY;

import com.hh.integration.constants.Constant;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

/**
 * ItemEcoFees generated by hbm2java.
 */
@Entity
@Table(name = "item_eco_fees", catalog = "ignitiv")
@CsvRecord(separator = "\\|", skipFirstLine = true)
public class ItemEcoFees implements java.io.Serializable {

        private static final long serialVersionUID = 1L;
        private Integer id;
        @DataField(pos = Constant.int_1)
        private String item;
        @DataField(pos = Constant.int_2)
        private String prov;
        @DataField(pos = Constant.int_3)
        private Double feeAmt;
        @DataField(pos = Constant.int_4)
        private String ecoFeeDesc;
        @DataField(pos = Constant.int_5)
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

        public ItemEcoFees() {
        }

        public ItemEcoFees(final String item, final String prov, final Double feeAmt,
                        final String ecoFeeDesc, final String ecoFeeDescFr,
                        final String isProcessSuccess, final Integer errorCode,
                        final String errorDesc, final String batchId, final String status,
                        final String createBy, final Date createDate, final String updateBy,
                        final Date updateDate) {
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
        public final Integer getId() {
                return this.id;
        }

        public final void setId(final Integer id) {
                this.id = id;
        }

        @Column(name = "ITEM", length = Constant.int_64)
        public final String getItem() {
                return this.item;
        }

        public final void setItem(final String item) {
                this.item = item;
        }

        @Column(name = "PROV", length = Constant.int_64)
        public final String getProv() {
                return this.prov;
        }

        public final void setProv(final String prov) {
                this.prov = prov;
        }

        @Column(name = "FEE_AMT", precision = Constant.int_22, scale = Constant.zero)
        public final Double getFeeAmt() {
                return this.feeAmt;
        }

        public final void setFeeAmt(final Double feeAmt) {
                this.feeAmt = feeAmt;
        }

        @Column(name = "ECO_FEE_DESC", length = Constant.int_100)
        public final String getEcoFeeDesc() {
                return this.ecoFeeDesc;
        }

        public final void setEcoFeeDesc(final String ecoFeeDesc) {
                this.ecoFeeDesc = ecoFeeDesc;
        }

        @Column(name = "ECO_FEE_DESC_FR", length = Constant.int_100)
        public final String getEcoFeeDescFr() {
                return this.ecoFeeDescFr;
        }

        public final void setEcoFeeDescFr(final String ecoFeeDescFr) {
                this.ecoFeeDescFr = ecoFeeDescFr;
        }

        @Column(name = "IS_PROCESS_SUCCESS", length = Constant.int_1)
        public final String getIsProcessSuccess() {
                return this.isProcessSuccess;
        }

        public final void setIsProcessSuccess(final String isProcessSuccess) {
                this.isProcessSuccess = isProcessSuccess;
        }

        @Column(name = "ERROR_CODE")
        public final Integer getErrorCode() {
                return this.errorCode;
        }

        public final void setErrorCode(final Integer errorCode) {
                this.errorCode = errorCode;
        }

        @Column(name = "ERROR_DESC", length = Constant.int_65535)
        public final String getErrorDesc() {
                return this.errorDesc;
        }

        public final void setErrorDesc(final String errorDesc) {
                this.errorDesc = errorDesc;
        }

        @Column(name = "BATCH_ID", length = Constant.int_50)
        public final String getBatchId() {
                return this.batchId;
        }

        public final void setBatchId(final String batchId) {
                this.batchId = batchId;
        }

        @Column(name = "STATUS", length = Constant.int_45)
        public final String getStatus() {
                return this.status;
        }

        public final void setStatus(final String status) {
                this.status = status;
        }

        @Column(name = "CREATE_BY", length = Constant.int_50)
        public final String getCreateBy() {
                return this.createBy;
        }

        public final void setCreateBy(final String createBy) {
                this.createBy = createBy;
        }

        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "CREATE_DATE", length = Constant.int_19)
        public final Date getCreateDate() {
                return this.createDate;
        }

        public final void setCreateDate(final Date createDate) {
                this.createDate = createDate;
        }

        @Column(name = "UPDATE_BY", length = Constant.int_50)
        public final String getUpdateBy() {
                return this.updateBy;
        }

        public final void setUpdateBy(final String updateBy) {
                this.updateBy = updateBy;
        }

        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "UPDATE_DATE", length = Constant.int_19)
        public final Date getUpdateDate() {
                return this.updateDate;
        }

        public final void setUpdateDate(final Date updateDate) {
                this.updateDate = updateDate;
        }

}
