package com.spring.microservice.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Login response.
 */
public class LoginResponse {

    @JsonProperty("access_token")
    private String accessToken;

    public LoginResponse(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
