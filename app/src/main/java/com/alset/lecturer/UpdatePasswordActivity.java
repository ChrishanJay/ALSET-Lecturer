package com.alset.lecturer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.alset.lecturer.api.LoginResponse;
import com.alset.lecturer.api.PasswordChangeResponse;
import com.alset.lecturer.api.PasswordRequest;
import com.alset.lecturer.api.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdatePasswordActivity extends AppCompatActivity {


    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);

        EditText etPassword = findViewById(R.id.etPassword);
        EditText etConfirmPassword = findViewById(R.id.etConfirmPassword);

        String session = getIntent().getStringExtra("session");
        String username = getIntent().getStringExtra("username");

        findViewById(R.id.btnGo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password = etPassword.getText().toString();
                String confirmPassword = etConfirmPassword.getText().toString();

                if (password.isEmpty() || confirmPassword.isEmpty()){
                    Toast.makeText(UpdatePasswordActivity.this, "Password can not be empty!", Toast.LENGTH_LONG).show();
                } else if (!password.equals(confirmPassword)) {
                    Toast.makeText(UpdatePasswordActivity.this, "Passwords are not matching", Toast.LENGTH_LONG).show();
                } else {
                    showProgress(UpdatePasswordActivity.this);
                    setPassword(password, username, session);
                }
            }
        });
    }

    private void showProgress(Context context){
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Updating Password...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        progressDialog.setCancelable(false);
    }

    private void hideProgress() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    private void setPassword(String password, String username, String session){
        PasswordRequest passwordRequest = new PasswordRequest(username, password, session);

        Call<PasswordChangeResponse> call = RetrofitClient.getInstance().getAlsetAPI().setPassword(passwordRequest);

        call.enqueue(new Callback<PasswordChangeResponse>() {
            @Override
            public void onResponse(Call<PasswordChangeResponse> call, Response<PasswordChangeResponse> response) {
                hideProgress();
                if (response != null && !response.body().getAccessToken().isEmpty()) {
                    Toast.makeText(UpdatePasswordActivity.this, "Update Successful", Toast.LENGTH_LONG).show();
                    new Handler().postDelayed(new Runnable(){
                        @Override
                        public void run() {
                            Intent mainIntent = new Intent(UpdatePasswordActivity.this, HomeActivity.class);
                            mainIntent.putExtra("username", username);
                            UpdatePasswordActivity.this.startActivity(mainIntent);
                            UpdatePasswordActivity.this.finish();
                        }
                    }, 3000);
                }
            }

            @Override
            public void onFailure(Call<PasswordChangeResponse> call, Throwable t) {
                hideProgress();
                Toast.makeText(UpdatePasswordActivity.this, "Password Update Failed. Please try again!", Toast.LENGTH_LONG).show();
            }
        });
    }
}