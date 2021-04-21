package com.alset.lecturer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alset.lecturer.api.LecturerResponse;
import com.alset.lecturer.api.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;
    private TextView lecId, lecName, lecEmail, lecPhone;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        lecId = findViewById(R.id.lecIdTxt);
        lecName = findViewById(R.id.lecNameTxt);
        lecPhone = findViewById(R.id.lecPhoneTxt);
        lecEmail = findViewById(R.id.lecEmailTxt);

        if (getIntent().hasExtra("username")){
            username = getIntent().getStringExtra("username");
        }

        showProgress(this);
        getLecturers();

        findViewById(R.id.btnAddClass).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addClassIntent = new Intent(HomeActivity.this, NewClassActivity.class);
                startActivity(addClassIntent);
            }
        });
    }

    private void updateUI(LecturerResponse lecturer){
        lecId.setText(lecturer.getLecturerId());
        lecName.setText(lecturer.getName());
        lecPhone.setText(lecturer.getMobile());
        lecEmail.setText(lecturer.getEmail());
    }

    private void showProgress(Context context){
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Getting Lecturer Profile Information...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        progressDialog.setCancelable(false);
    }

    private void hideProgress() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
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
                        }
                    }
                } else {
                    Toast.makeText(HomeActivity.this, "Request Failed. Please try again!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<LecturerResponse>> call, Throwable t) {
                hideProgress();
                Toast.makeText(HomeActivity.this, "Request Failed. Please try again!", Toast.LENGTH_LONG).show();
            }
        });
    }
}