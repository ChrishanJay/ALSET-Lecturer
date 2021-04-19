package com.alset.lecturer.api;

import com.google.gson.annotations.SerializedName;

public class CourseResponse {

    @SerializedName("course_id")
    private String courseId;

    @SerializedName("course_name")
    private String courseName;

    public String getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }
}
