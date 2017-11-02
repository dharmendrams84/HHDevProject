package com.homehardware.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "EMPLOYEE")
public final class Employee implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;

	@Column(name = "FIRSTNAME")
	private String firstname;

	@Column(name = "LASTNAME")
	private String lastname;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "TELEPHONE")
	private String telephone;

	public String getEmail() {
		return email;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public void setTelephone(final String telephone) {
		this.telephone = telephone;
	}

	public String getFirstname() {
		return firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setFirstname(final String firstname) {
		this.firstname = firstname;
	}

	public void setLastname(final String lastname) {
		this.lastname = lastname;
	}

	public Integer getId() {
		return id;
	}

	public void setId(final Integer id) {
		this.id = id;
	}
}