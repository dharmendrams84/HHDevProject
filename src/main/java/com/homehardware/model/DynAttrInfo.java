// default package
// Generated Dec 14, 2017 12:11:48 PM by Hibernate Tools 5.2.6.Final
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
 * DynAttrInfo generated by hbm2java
 */
@Entity
@Table(name = "dyn_attr_info", catalog = "ignitiv")
public class DynAttrInfo implements java.io.Serializable {

	private Integer id;
	private Integer dynAttrId;
	private String dynAttrDesc;
	private String dynAttrDescFr;
	private Integer imageId;
	private String language;
	private Integer errorCode;
	private String errorDesc;
	private String batchId;
	private String status;
	private String createBy;
	private Date createDate;
	private String updateBy;
	private Date updateDate;

	public DynAttrInfo() {
	}

	public DynAttrInfo(Integer dynAttrId, String dynAttrDesc, String dynAttrDescFr, Integer imageId, String language,
			Integer errorCode, String errorDesc, String batchId, String status, String createBy, Date createDate,
			String updateBy, Date updateDate) {
		this.dynAttrId = dynAttrId;
		this.dynAttrDesc = dynAttrDesc;
		this.dynAttrDescFr = dynAttrDescFr;
		this.imageId = imageId;
		this.language = language;
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

	@Column(name = "DYN_ATTR_ID")
	public Integer getDynAttrId() {
		return this.dynAttrId;
	}

	public void setDynAttrId(Integer dynAttrId) {
		this.dynAttrId = dynAttrId;
	}

	@Column(name = "DYN_ATTR_DESC", length = 100)
	public String getDynAttrDesc() {
		return this.dynAttrDesc;
	}

	public void setDynAttrDesc(String dynAttrDesc) {
		this.dynAttrDesc = dynAttrDesc;
	}

	@Column(name = "DYN_ATTR_DESC_FR", length = 100)
	public String getDynAttrDescFr() {
		return this.dynAttrDescFr;
	}

	public void setDynAttrDescFr(String dynAttrDescFr) {
		this.dynAttrDescFr = dynAttrDescFr;
	}

	@Column(name = "IMAGE_ID")
	public Integer getImageId() {
		return this.imageId;
	}

	public void setImageId(Integer imageId) {
		this.imageId = imageId;
	}

	@Column(name = "LANGUAGE", length = 100)
	public String getLanguage() {
		return this.language;
	}

	public void setLanguage(String language) {
		this.language = language;
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
