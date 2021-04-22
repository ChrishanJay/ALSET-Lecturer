package com.alset.lecturer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alset.lecturer.api.ClassesResponse;
import com.alset.lecturer.api.LecturerResponse;
import com.alset.lecturer.api.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;
    private TextView lecId, lecName, lecEmail, lecPhone;
    private String username;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        count = 0;

        lecId = findViewById(R.id.lecIdTxt);
        lecName = findViewById(R.id.lecNameTxt);
        lecPhone = findViewById(R.id.lecPhoneTxt);
        lecEmail = findViewById(R.id.lecEmailTxt);

        if (getIntent().hasExtra("username")){
            username = getIntent().getStringExtra("username");
        }

        showProgress(this, "Getting Lecturer Profile Information...");
        getLecturers();

        findViewById(R.id.btnAddClass).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addClassIntent = new Intent(HomeActivity.this, NewClassActivity.class);
                addClassIntent.putExtra("username", username);
                startActivity(addClassIntent);
            }
        });

        findViewById(R.id.btnViewAttendance).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgress(HomeActivity.this, "Getting Available classes...");
                getClasses();
            }
        });
    }

    private void updateUI(LecturerResponse lecturer){
        lecId.setText(lecturer.getLecturerId());
        lecName.setText(lecturer.getName());
        lecPhone.setText(lecturer.getMobile());
        lecEmail.setText(lecturer.getEmail());
    }

    private void showDialog(List<ClassesResponse> classList) {

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

    private void getClasses() {
        Call<List<ClassesResponse>> call = RetrofitClient.getInstance().getAlsetAPI().getClasses();
        call.enqueue(new Callback<List<ClassesResponse>>() {
            @Override
            public void onResponse(Call<List<ClassesResponse>> call, Response<List<ClassesResponse>> response) {
                if (response.isSuccessful() && response.body() != null){
                    List<ClassesResponse> classList = response.body();
                    List<ClassesResponse> filteredClassList = new ArrayList<>();

                    for (ClassesResponse classResponse: classList) {
                        if (classResponse.getLecturerId().equalsIgnoreCase(username)) {
                            filteredClassList.add(classResponse);
                        }
                    }
                    showDialog(filteredClassList);
                } else {
                    Toast.makeText(HomeActivity.this, "Request Failed. Please try again!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<ClassesResponse>> call, Throwable t) {
                Toast.makeText(HomeActivity.this, "Request Failed. Please try again!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getLecturers(){
        Call<List<LecturerResponse>> call = RetrofitClient.getInstance().getAlsetAPI().getLecturers();
        call.enqueue(new Callback<List<LecturerResponse>>() {
            @Override
            public void onResponse(Call<List<LecturerResponse>> call, Response<List<LecturerResponse>> response) {
                hideProgress();
                if (response.isSuccessful() && response.body() != null) {

                    List<LecturerResponse> lecturerList = response.body();
                    for (LecturerResponse lecturer: lecturerList) {
                        if (lecturer.getLecturerId().equalsIgnoreCase(username)){
                            updateUI(lecturer);
                            break;
                        }
                    }
                } else {
                    goBackToLogin();
                }
            }

            @Override
            public void onFailure(Call<List<LecturerResponse>> call, Throwable t) {
                Log.e("ALSET", t.getLocalizedMessage());
                if (count > 2) {
                    count++;
                    Toast.makeText(HomeActivity.this, "Request Failed. Retrying again!", Toast.LENGTH_LONG).show();
                    getLecturers();
                } else {
                    goBackToLogin();
                }

            }
        });
    }

    private void goBackToLogin(){
        hideProgress();
        Toast.makeText(HomeActivity.this, "Request Failed. Please try again!", Toast.LENGTH_LONG).show();
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent mainIntent = new Intent(HomeActivity.this, LoginActivity.class);
                HomeActivity.this.startActivity(mainIntent);
                HomeActivity.this.finish();
            }
        }, 3000);
    }
}