package com.alset.lecturer.api;

import com.google.gson.annotations.SerializedName;

public class LecturerResponse {

    @SerializedName("lecturer_id")
    private String lecturerId;

    @SerializedName("f_name")
    private String firstName;

    @SerializedName("l_name")
    private String lastName;

    @SerializedName("mobile")
    private String mobile;

    @SerializedName("email")
    private String email;

    @SerializedName("gender")
    private String gender;

    public String getLecturerId() {
        return lecturerId;
    }

    public String getName() {
        return firstName + " " + lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMobile() {
        return mobile;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }
}
