package com.hazem.library.payload.request;

public class LoginRequest {
	private String user_name;

	private String password;

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String username) {
		this.user_name = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
