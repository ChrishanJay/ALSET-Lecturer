package com.alset.lecturer.api;

import com.google.gson.annotations.SerializedName;

public class ModuleResponse {

    @SerializedName("module_id")
    private String moduleId;

    @SerializedName("name")
    private String name;

    @SerializedName("lecturer_id")
    private String lecturerId;


    public String getModuleId() {
        return moduleId;
    }

    public String getName() {
        return name;
    }

    public String getLecturerId() {
        return lecturerId;
    }

    @Override
    public String toString() {
        return name;
    }
}
