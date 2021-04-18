package com.alset.lecturer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.alset.lecturer.api.LoginRequest;
import com.alset.lecturer.api.LoginResponse;
import com.alset.lecturer.api.RetrofitClient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        EditText etUserName = findViewById(R.id.etUserName);
        EditText etPassword = findViewById(R.id.etPassword);

        findViewById(R.id.btnGo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = etUserName.getText().toString();
                String password = etPassword.getText().toString();

                if (username.isEmpty() && password.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Email or Password can not be empty!", Toast.LENGTH_LONG).show();
                } else {
                    login(username, password);
                }
            }
        });

    }

    private void login(String username, String password) {
        LoginRequest loginRequest = new LoginRequest(username, password);
        Call<LoginResponse> call = RetrofitClient.getInstance().getAlsetAPI().login(loginRequest);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response != null && response.body().getSession().isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
                    new Handler().postDelayed(new Runnable(){
                        @Override
                        public void run() {
                            Intent mainIntent = new Intent(LoginActivity.this, HomeActivity.class);
                            LoginActivity.this.startActivity(mainIntent);
                            LoginActivity.this.finish();
                        }
                    }, 3000);
                } else if (response != null && !response.body().getSession().isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Login Successful. Please Update the password!", Toast.LENGTH_LONG).show();
                    new Handler().postDelayed(new Runnable(){
                        @Override
                        public void run() {
                            Intent passwordIntent = new Intent(LoginActivity.this, UpdatePasswordActivity.class);
                            passwordIntent.putExtra("username", response.body().getUsername());
                            passwordIntent.putExtra("session", response.body().getSession());
                            LoginActivity.this.startActivity(passwordIntent);
                            LoginActivity.this.finish();
                        }
                    }, 3000);
                }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Login Failed. Please try again!", Toast.LENGTH_LONG).show();
            }
        });
    }
}