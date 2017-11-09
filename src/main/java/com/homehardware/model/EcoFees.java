package com.homehardware.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ecofees")
public final class EcoFees implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;

	@Column(name = "ITEM")
	private int item;

	@Column(name = "PROV")
	private String province;

	@Column(name = "FEE_AMT")
	private double feeAmt;

	public final Integer getId() {
		return id;
	}

	public final void setId(final Integer id) {
		this.id = id;
	}

	public final int getItem() {
		return item;
	}

	public final void setItem(final int item) {
		this.item = item;
	}

	public final String getProvince() {
		return province;
	}

	public final void setProvince(final String province) {
		this.province = province;
	}

	public final double getFeeAmt() {
		return feeAmt;
	}

	public final void setFeeAmt(final double feeAmt) {
		this.feeAmt = feeAmt;
	}

	/**
	 * Return string representation of EcoFees object.
	 * 
	 */
	public final String toString() {
		return " id :" + id + " : item : " + item + " : province : " + province;
	}

}
