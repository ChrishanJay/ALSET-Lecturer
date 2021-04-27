package com.alset.lecturer.api;

import com.google.gson.annotations.SerializedName;

public class AttendanceResponse {

    @SerializedName("student_id")
    private String studentId;

    @SerializedName("attended_time")
    private String attendedTime;

    @SerializedName("attended")
    private int attended;

    @SerializedName("attendance_id")
    private String attendanceId;

    @SerializedName("class_id")
    private String classId;

    public String getStudentId() {
        return studentId;
    }

    public String getAttendedTime() {
        return attendedTime;
    }

    public int getAttended() {
        return attended;
    }

    public String getAttendanceId() {
        return attendanceId;
    }

    public String getClassId() {
        return classId;
    }
}
