package com.alset.lecturer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.alset.lecturer.api.LoginResponse;
import com.alset.lecturer.api.PasswordRequest;
import com.alset.lecturer.api.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdatePasswordActivity extends AppCompatActivity {

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
                } else if (password != confirmPassword) {
                    Toast.makeText(UpdatePasswordActivity.this, "Passwords are not matching", Toast.LENGTH_LONG).show();
                } else {
                    setPassword(password, username, session);
                }
            }
        });
    }

    private void setPassword(String password, String username, String session){
        PasswordRequest passwordRequest = new PasswordRequest(username, password, session);

        Call<LoginResponse> call = RetrofitClient.getInstance().getAlsetAPI().setPassword(passwordRequest);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response != null && response.body().getSession().isEmpty()) {
                    Toast.makeText(UpdatePasswordActivity.this, "Update Successful", Toast.LENGTH_LONG).show();
                    new Handler().postDelayed(new Runnable(){
                        @Override
                        public void run() {
                            Intent mainIntent = new Intent(UpdatePasswordActivity.this, HomeActivity.class);
                            UpdatePasswordActivity.this.startActivity(mainIntent);
                            UpdatePasswordActivity.this.finish();
                        }
                    }, 3000);
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(UpdatePasswordActivity.this, "Password Update Failed. Please try again!", Toast.LENGTH_LONG).show();
            }
        });
    }
}