package com.alset.lecturer.api;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("username")
    private String username;

    @SerializedName("session")
    private String session;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }
}
