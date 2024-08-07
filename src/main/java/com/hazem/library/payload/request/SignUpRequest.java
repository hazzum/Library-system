package com.hazem.library.payload.request;

public class SignUpRequest {
    private String first_name;
    private String last_name;
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

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    
}
