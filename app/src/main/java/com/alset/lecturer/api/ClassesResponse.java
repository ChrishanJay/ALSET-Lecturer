package com.alset.lecturer.api;

import com.google.gson.annotations.SerializedName;

public class ClassesResponse {

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

    public String getClassId() {
        return classId;
    }

    public String getLecturerId() {
        return lecturerId;
    }

    public String getDate() {
        return date;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getModuleId() {
        return moduleId;
    }
}
