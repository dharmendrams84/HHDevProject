package com.homehardware.exception;

public final class AddUpdateProductException extends Exception {

	String code;
	String description;

	public AddUpdateProductException(final String code, final String description) {
		this.code = code;
		this.description = description;
	}

	public String toString() {
		return "Code " + code + " description " + description;
	}
}
