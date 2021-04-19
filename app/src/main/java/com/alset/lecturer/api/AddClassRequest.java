package com.alset.lecturer.api;

import com.google.gson.annotations.SerializedName;

public class AddClassRequest {

    @SerializedName("class_id")
    private String classId;

    @SerializedName("lecturer_id")
    private String lecturerId;

    @SerializedName("date")
    private String date;

    @SerializedName("start_time")
    private String startTime;

    @SerializedName("end_time")
    private String endTime;

    @SerializedName("module_id")
    private String moduleId;


    public void setClassId(String classId) {
        this.classId = classId;
    }

    public void setLecturerId(String lecturerId) {
        this.lecturerId = lecturerId;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }
}
