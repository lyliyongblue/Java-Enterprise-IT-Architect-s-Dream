package com.liyong.flux.entity;

import java.util.UUID;

public class User {
	private String id;
	private String name;
	private String email;

	public User() {
		this.id = UUID.randomUUID().toString();
	}

	public User(final String name, final String email) {
		this();
		this.name = name;
		this.email = email;
	}

	public String getId() {
		return this.id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email + "]";
	}
}
