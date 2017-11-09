package com.homehardware.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "store")
public final class Store implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;

	@Column(name = "JOB_ID")
	private Integer jobId;

	@Column(name = "STORE_NUMBER")
	private int storeNumber;

	@Column(name = "STORE_NAME")
	private String storeName;

	@Column(name = "STORE_ADD1")
	private String storeAdd1;

	@Column(name = "STORE_ADD2")
	private String storeAdd2;

	//@Column(name = "STORE_ADD3")
//	private String storeAdd3;

	/*public final String getStoreAdd3() {
		return storeAdd3;
	}

	public final void setStoreAdd3(final String storeAdd3) {
		this.storeAdd3 = storeAdd3;
	}

	public final String getStoreAdd4() {
		return storeAdd4;
	}

	public final void setStoreAdd4(final String storeAdd4) {
		this.storeAdd4 = storeAdd4;
	}

*/	public final double getLongitude() {
		return longitude;
	}

	public final void setLongitude(final double longitude) {
		this.longitude = longitude;
	}

	//@Column(name = "STORE_ADD4")
	//private String storeAdd4;

	@Column(name = "CITY")
	private String city;

	@Column(name = "PROVINCE")
	private String province;

	@Column(name = "POSTAL_CODE")
	private String postalCode;

	@Column(name = "PHONE_NUMBER")
	private String phoneNumber;

	/*
	 * @Column(name = "PROMO_ZONE") private int promoZone;
	 */

	@Column(name = "WHSE")
	private int whse;

	@Column(name = "STORE_FORMAT")
	private String storeFormat;

	@Column(name = "ECOMMERCE_IND")
	private String ecommerceInd;

	@Column(name = "store_services")
	private String storeServices;

	@Column(name = "regular_hours")
	private String regularHours;

	@Column(name = "store_content")
	private String storeContent;

	@Column(name = "latitude")
	private double latitude;

	/*@Column(name = "country_code")
	private String countryCode;

	public final String getCountryCode() {
		return countryCode;
	}

	public final void setCountryCode(final String countryCode) {
		this.countryCode = countryCode;
	}
*/
	public final Integer getId() {
		return id;
	}

	public final void setId(final Integer id) {
		this.id = id;
	}

	public final int getStoreNumber() {
		return storeNumber;
	}

	public final void setStoreNumber(final int storeNumber) {
		this.storeNumber = storeNumber;
	}

	public final String getStoreName() {
		return storeName;
	}

	public final void setStoreName(final String storeName) {
		this.storeName = storeName;
	}

	public final String getStoreAdd1() {
		return storeAdd1;
	}

	public final void setStoreAdd1(final String storeAdd1) {
		this.storeAdd1 = storeAdd1;
	}

	public final String getStoreAdd2() {
		return storeAdd2;
	}

	public final void setStoreAdd2(final String storeAdd2) {
		this.storeAdd2 = storeAdd2;
	}

	public final String getCity() {
		return city;
	}

	public final void setCity(final String city) {
		this.city = city;
	}

	public final String getProvince() {
		return province;
	}

	public final void setProvince(final String province) {
		this.province = province;
	}

	public final String getPostalCode() {
		return postalCode;
	}

	public final void setPostalCode(final String postalCode) {
		this.postalCode = postalCode;
	}

	public final String getPhoneNumber() {
		return phoneNumber;
	}

	public final void setPhoneNumber(final String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public final int getWhse() {
		return whse;
	}

	public final void setWhse(final int whse) {
		this.whse = whse;
	}

	public final String getStoreFormat() {
		return storeFormat;
	}

	public final void setStoreFormat(final String storeFormat) {
		this.storeFormat = storeFormat;
	}

	public final String getEcommerceInd() {
		return ecommerceInd;
	}

	public final void setEcommerceInd(final String ecommerceInd) {
		this.ecommerceInd = ecommerceInd;
	}

	public final String getStoreServices() {
		return storeServices;
	}

	public final void setStoreServices(final String storeServices) {
		this.storeServices = storeServices;
	}

	public final String getRegularHours() {
		return regularHours;
	}

	public final void setRegularHours(final String regularHours) {
		this.regularHours = regularHours;
	}

	public final String getStoreContent() {
		return storeContent;
	}

	public final void setStoreContent(final String storeContent) {
		this.storeContent = storeContent;
	}

	public final double getLatitude() {
		return latitude;
	}

	public final void setLatitude(final double latitude) {
		this.latitude = latitude;
	}

	public final double getLongitiude() {
		return longitude;
	}

	public final void setLongitiude(final double longitiude) {
		this.longitude = longitiude;
	}

	public final String getStoreWebsiteUrl() {
		return storeWebsiteUrl;
	}

	public final void setStoreWebsiteUrl(final String storeWebsiteUrl) {
		this.storeWebsiteUrl = storeWebsiteUrl;
	}

	public final Integer getJobId() {
		return jobId;
	}

	public final void setJobId(final Integer jobId) {
		this.jobId = jobId;
	}

	@Column(name = "longitude")
	private double longitude;

	@Column(name = "store_website_url")
	private String storeWebsiteUrl;

	/**
	 * Return string representation of store object.
	 * 
	 */
	public final String toString() {
		final String s = "ID : " + id + " job id : " + jobId + " store number : ";

		return s;

	}

}
