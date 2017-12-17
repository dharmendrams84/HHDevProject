// default package
// Generated Dec 12, 2017 12:25:57 PM by Hibernate Tools 5.2.6.Final

package com.homehardware.model;

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




/**.
 * Images generated by hbm2java
 */
@Entity
@Table(name = "images", catalog = "ignitiv")
public class Images implements java.io.Serializable {

	private Integer id;
	private String item;
	private Integer imageId;
	private Integer imageSeqNbr;
	private String furnitureInd;
	private String isProcessSuccess;
	private Integer errorCode;
	private String errorDesc;
	private String batchId;
	private String status;
	private String createBy;
	private Date createDate;
	private String updateBy;
	private Date updateDate;

	public Images() {
	}

	/**
	 * @param item.
	 * @param imageId.
	 * @param imageSeqNbr.
	 * @param furnitureInd.
	 * @param isProcessSuccess.
	 * @param errorCode.
	 * @param errorDesc.
	 * @param batchId.
	 * @param status.
	 * @param createBy.
	 * @param createDate.
	 * @param updateBy.
	 * @param updateDate.
	 */
	public Images(final String item, 
			final Integer imageId,
			final Integer imageSeqNbr, final String furnitureInd,
			final String isProcessSuccess,
			final Integer errorCode, final String errorDesc,
			final String batchId, final String status, 
			final String createBy, final Date createDate,
			final String updateBy, final Date updateDate) {
		this.item = item;
		this.imageId = imageId;
		this.imageSeqNbr = imageSeqNbr;
		this.furnitureInd = furnitureInd;
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

	@Column(name = "IMAGE_ID")
	public final Integer getImageId() {
		return this.imageId;
	}

	public final void setImageId(final Integer imageId) {
		this.imageId = imageId;
	}

	@Column(name = "IMAGE_SEQ_NBR")
	public final Integer getImageSeqNbr() {
		return this.imageSeqNbr;
	}

	public final void setImageSeqNbr(final Integer imageSeqNbr) {
		this.imageSeqNbr = imageSeqNbr;
	}

	@Column(name = "FURNITURE_IND", length = Constant.int_1)
	public final String getFurnitureInd() {
		return this.furnitureInd;
	}

	public final void setFurnitureInd(final String furnitureInd) {
		this.furnitureInd = furnitureInd;
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
