package com.example.demo.domain;

import java.util.Locale;

public enum ProductStatus {
	IN_PROCESSING, AVAILABLE, NOT_AVAILABLE;

	ProductStatus() {

	}

	@Override
	public String toString() {
		return super.toString().toLowerCase();
	}

	ProductStatus(String name) {
		ProductStatus.valueOf(name.toUpperCase());
	}
}
