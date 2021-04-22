package com.alset.lecturer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import com.alset.lecturer.api.AttendanceResponse;
import com.alset.lecturer.api.ClassesResponse;
import com.alset.lecturer.api.RetrofitClient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewAttendanceActivity extends AppCompatActivity {

    private String classId, moduleName, date, startTime, endTime;
    private ProgressDialog progressDialog;
    private TextView count, moduleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_attendance);

        showProgress(this);

        if (getIntent().hasExtra("classId")){
            classId = getIntent().getStringExtra("classId");
            moduleName = getIntent().getStringExtra("moduleName");
            date = getIntent().getStringExtra("date");
            startTime = getIntent().getStringExtra("startTime");
            endTime = getIntent().getStringExtra("endTime");
        }

        moduleText = findViewById(R.id.module);
        count = findViewById(R.id.attendanceCount);
        moduleText.setText(String.format(Locale.getDefault(), "Students Attendance for the %S Class", moduleName));


        getAttendance(classId);
    }

    private void showProgress(Context context){
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Creating new class...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        progressDialog.setCancelable(false);
    }

    private void hideProgress() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    private boolean isActive() {
        Calendar startTimeCalendar = Calendar.getInstance();
        Calendar endTimeCalendar = Calendar.getInstance();
        Calendar currentTimeCalendar = Calendar.getInstance();

        startTimeCalendar.setTime(Objects.requireNonNull(stringToDate(startTime)));
        endTimeCalendar.setTime(Objects.requireNonNull(stringToDate(endTime)));

        if (currentTimeCalendar.after(startTimeCalendar) && currentTimeCalendar.before(endTimeCalendar)) {
            return true;
        }

        return false;
    }

    private Date stringToDate(String stringDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        try {
            Date date = format.parse(stringDate);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void getAttendance(String classId) {
        Call<List<AttendanceResponse>> call = RetrofitClient.getInstance().getAlsetAPI().getAttendance();
        call.enqueue(new Callback<List<AttendanceResponse>>() {
            @Override
            public void onResponse(Call<List<AttendanceResponse>> call, Response<List<AttendanceResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    hideProgress();
                    if (isActive()) {
                        new Handler().postDelayed(new Runnable(){
                            @Override
                            public void run() {
                                getAttendance(classId);
                            }
                        }, 10000);
                    }

                    count.setText(String.format(Locale.getDefault(), "Students Count: %02d", response.body().size()));

                } else {
                    Toast.makeText(ViewAttendanceActivity.this, "Request Failed. Please try again!", Toast.LENGTH_LONG).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<List<AttendanceResponse>> call, Throwable t) {
                Toast.makeText(ViewAttendanceActivity.this, "Request Failed. Please try again!", Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }
}