package com.alset.lecturer.api;

import com.google.gson.annotations.SerializedName;

public class PasswordChangeResponse {

    @SerializedName("AccessToken")
    private String accessToken;

    @SerializedName("ExpiresIn")
    private long expiresIn;

    @SerializedName("TokenType")
    private String tokenType;

    @SerializedName("RefreshToken")
    private String refreshToken;

    @SerializedName("IdToken")
    private String idToken;

    public String getAccessToken() {
        return accessToken;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public String getTokenType() {
        return tokenType;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getIdToken() {
        return idToken;
    }
}
