package com.alset.lecturer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        findViewById(R.id.btnAddClass).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addClassIntent = new Intent(HomeActivity.this, NewClassActivity.class);
                startActivity(addClassIntent);
            }
        });
    }
}