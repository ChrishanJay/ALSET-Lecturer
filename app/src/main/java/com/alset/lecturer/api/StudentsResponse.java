package com.alset.lecturer.api;

import com.google.gson.annotations.SerializedName;

public class StudentsResponse {

    @SerializedName("student_id")
    private String studentId;

    @SerializedName("f_name")
    private String firstName;

    @SerializedName("l_name")
    private String lastName;

    @SerializedName("phone")
    private String phone;

    @SerializedName("district")
    private String district;

    @SerializedName("email")
    private String email;

    @SerializedName("source_image_key")
    private String sourceImageKey;

    @SerializedName("dob")
    private String dob;

    @SerializedName("course_id")
    private String courseId;

    public String getStudentId() {
        return studentId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public String getDistrict() {
        return district;
    }

    public String getEmail() {
        return email;
    }

    public String getSourceImageKey() {
        return sourceImageKey;
    }

    public String getDob() {
        return dob;
    }

    public String getCourseId() {
        return courseId;
    }
}
