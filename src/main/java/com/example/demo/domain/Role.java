package com.example.demo.domain;

public enum Role {
	MANAGER, CLIENT, COURIER;

	@Override
	public String toString() {
		return super.toString().toLowerCase();
	}
}
