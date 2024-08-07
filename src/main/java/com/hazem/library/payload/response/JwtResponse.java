package com.hazem.library.payload.response;

public class JwtResponse {
  private String authToken;
  private String type = "Bearer";
  private Long id;
  private String username;

  public JwtResponse(String authToken, Long id, String username) {
    this.id = id;
    this.authToken = authToken;
    this.username = username;
  }

  public String getAuthToken() {
    return authToken;
  }

  public void setAuthToken(String authToken) {
    this.authToken = authToken;
  }

  public String getTokenType() {
    return type;
  }

  public void setTokenType(String tokenType) {
    this.type = tokenType;
  }

  public Long getUser_id() {
    return id;
  }

  public void setUser_id(Long id) {
    this.id = id;
  }


  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

}
