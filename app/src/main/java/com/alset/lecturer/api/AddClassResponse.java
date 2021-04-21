package com.alset.lecturer.api;

import com.google.gson.annotations.SerializedName;

public class AddClassResponse {

    @SerializedName("message")
    private String message;

    @SerializedName("class_id")
    private String classId;

    @SerializedName("lecturer_id")
    private String lecturerId;

    public String getMessage() {
        return message;
    }

    public String getClassId() {
        return classId;
    }

    public String getLecturerId() {
        return lecturerId;
    }
}
