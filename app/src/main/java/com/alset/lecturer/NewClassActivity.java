package com.alset.lecturer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.alset.lecturer.api.AddClassRequest;
import com.alset.lecturer.api.AddClassResponse;
import com.alset.lecturer.api.ClassesResponse;
import com.alset.lecturer.api.LecturerResponse;
import com.alset.lecturer.api.LoginResponse;
import com.alset.lecturer.api.ModuleResponse;
import com.alset.lecturer.api.RetrofitClient;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewClassActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{

    private ProgressDialog progressDialog;
    private Toast errorToast;
    private Spinner moduleSpinner;
    private EditText etDate, etStartTime, etEndTme;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private ModuleResponse selectedModule = null;

    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_class);

        if (getIntent().hasExtra("username")){
            username = getIntent().getStringExtra("username");
        }

        errorToast = Toast.makeText(NewClassActivity.this, "Request Failed. Please try again!", Toast.LENGTH_LONG);
        moduleSpinner = findViewById(R.id.moduleSpinner);

        showProgress(NewClassActivity.this, "Getting Module Details...");
        getModules();

        etDate = findViewById(R.id.etDate);
        etStartTime = findViewById(R.id.etStartTime);
        etEndTme = findViewById(R.id.etEndTime);

        findViewById(R.id.btnCalender).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker();
            }
        });

        findViewById(R.id.btnStartTime).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePicker("Start");
            }
        });

        findViewById(R.id.btnEndTime).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePicker("End");
            }
        });

        findViewById(R.id.btnAddClass).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgress(NewClassActivity.this, "Creating new class...");
                if (selectedModule == null) {
                    Toast.makeText(NewClassActivity.this, "Please select a module.", Toast.LENGTH_LONG).show();
                } else if (etDate.getText().toString().isEmpty()) {
                    Toast.makeText(NewClassActivity.this, "Please select a date", Toast.LENGTH_LONG).show();
                } else if (etStartTime.getText().toString().isEmpty()) {
                    Toast.makeText(NewClassActivity.this, "Please select a start time", Toast.LENGTH_LONG).show();
                } else if (etEndTme.getText().toString().isEmpty()) {
                    Toast.makeText(NewClassActivity.this, "Please select a end time", Toast.LENGTH_LONG).show();
                } else {
                    createClass(selectedModule, etDate.getText().toString(), etStartTime.getText().toString(), etEndTme.getText().toString(), username);
                }
            }
        });

        moduleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedModule = (ModuleResponse) adapterView.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void showDatePicker(){
        Calendar now = Calendar.getInstance();
        if (datePickerDialog == null) {
            datePickerDialog = DatePickerDialog.newInstance(
                    NewClassActivity.this,
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DAY_OF_MONTH)
            );
        } else {
            datePickerDialog.initialize(
                    NewClassActivity.this,
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DAY_OF_MONTH)
            );
        }

        datePickerDialog.setMinDate(now);
        datePickerDialog.show(getSupportFragmentManager(), "DatePicker");
    }

    private void showTimePicker(String type) {
        Calendar now = Calendar.getInstance();
        if (timePickerDialog == null) {
            timePickerDialog = TimePickerDialog.newInstance(
                    NewClassActivity.this,
                    now.get(Calendar.HOUR_OF_DAY),
                    now.get(Calendar.MINUTE),
                    false
            );
        } else {
            timePickerDialog.initialize(
                    NewClassActivity.this,
                    now.get(Calendar.HOUR_OF_DAY),
                    now.get(Calendar.MINUTE),
                    now.get(Calendar.SECOND),
                    false
            );
        }

        timePickerDialog.setTimeInterval(1, 30);
        timePickerDialog.show(getSupportFragmentManager(), type);
    }

    private void showProgress(Context context, String msg){
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(msg);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        progressDialog.setCancelable(false);
    }

    private void hideProgress() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    private void updateModuleSpinner(List<ModuleResponse> classList){
        ArrayAdapter<ModuleResponse> adapter = new ArrayAdapter<ModuleResponse>(this, android.R.layout.simple_spinner_dropdown_item, classList);
        moduleSpinner.setAdapter(adapter);
    }

    private void createClass(ModuleResponse module, String date, String startTime, String endTime, String username) {
        long number = (long) Math.floor(Math.random() * 9_000_000L);
        AddClassRequest request = new AddClassRequest(String.valueOf(number));
        request.setDate(date);
        request.setEndTime(date + " " + endTime);
        request.setStartTime(date + " " + startTime);
        request.setLecturerId(username);
        request.setModuleId(module.getModuleId());

        Call<AddClassResponse> call = RetrofitClient.getInstance().getAlsetAPI().addClass(request);
        call.enqueue(new Callback<AddClassResponse>() {
            @Override
            public void onResponse(Call<AddClassResponse> call, Response<AddClassResponse> response) {
                hideProgress();
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(NewClassActivity.this, "Class Created.", Toast.LENGTH_LONG).show();

                    Intent mainIntent = new Intent(NewClassActivity.this, ViewAttendanceActivity.class);
                    mainIntent.putExtra("classId", response.body().getClassId());
                    mainIntent.putExtra("moduleName", module.getName());
                    mainIntent.putExtra("date", date);
                    mainIntent.putExtra("startTime", date + " " + startTime);
                    mainIntent.putExtra("endTime", date + " " + endTime);
                    NewClassActivity.this.startActivity(mainIntent);
                    NewClassActivity.this.finish();

                } else {
                    Toast.makeText(NewClassActivity.this, "Class creation failed. Please try again.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<AddClassResponse> call, Throwable t) {
                hideProgress();
                Toast.makeText(NewClassActivity.this, "Class creation failed. Please try again.", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getModules(){
        Call<List<ModuleResponse>> call = RetrofitClient.getInstance().getAlsetAPI().getModules();
        call.enqueue(new Callback<List<ModuleResponse>>() {
            @Override
            public void onResponse(Call<List<ModuleResponse>> call, Response<List<ModuleResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    hideProgress();
                    List<ModuleResponse> moduleList = response.body();
                    List<ModuleResponse> filteredModuleList = new ArrayList<>();

                    if (moduleList != null) {
                        for (ModuleResponse module: moduleList) {
                            if (module.getLecturerId().equalsIgnoreCase(username)){
                                filteredModuleList.add(module);
                            }
                        }
                    }
                    updateModuleSpinner(filteredModuleList);
                }
            }

            @Override
            public void onFailure(Call<List<ModuleResponse>> call, Throwable t) {
                hideProgress();
                errorToast.show();
            }
        });
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String dateString = String.format(Locale.getDefault(),"%d-%02d-%02d", year, (++monthOfYear), dayOfMonth);
        etDate.setText(dateString);
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        String timeString = String.format(Locale.getDefault(),"%02d:%02d:%02d", hourOfDay, minute, second);

        if (view.getTag() != null && view.getTag().equalsIgnoreCase("Start")) {
            etStartTime.setText(timeString);
        } else  if (view.getTag() != null && view.getTag().equalsIgnoreCase("End")) {
            etEndTme.setText(timeString);
        }
    }
}