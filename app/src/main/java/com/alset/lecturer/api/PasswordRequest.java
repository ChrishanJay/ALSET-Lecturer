package com.alset.lecturer.api;

import com.google.gson.annotations.SerializedName;

public class PasswordRequest {

    @SerializedName("username")
    private String username;

    @SerializedName("newPassword")
    private String password;

    @SerializedName("session")
    private String session;

    public PasswordRequest(String username, String password, String session) {
        this.username = username;
        this.password = password;
        this.session = session;
    }
}
