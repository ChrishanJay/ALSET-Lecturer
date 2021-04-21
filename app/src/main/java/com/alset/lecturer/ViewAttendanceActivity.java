package com.alset.lecturer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ViewAttendanceActivity extends AppCompatActivity {

    private String classId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_attendance);

        if (getIntent().hasExtra("classId")){
            classId = getIntent().getStringExtra("classId");
        }
    }
}