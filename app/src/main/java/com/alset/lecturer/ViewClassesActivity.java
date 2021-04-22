package com.alset.lecturer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import com.alset.lecturer.api.ClassAdapter;
import com.alset.lecturer.api.ClassesResponse;
import com.alset.lecturer.api.ModuleResponse;
import com.alset.lecturer.api.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewClassesActivity extends AppCompatActivity {

    private String username;
    private ProgressDialog progressDialog;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_classes_actvity);

        showProgress(ViewClassesActivity.this, "Getting Available classes...");

        if (getIntent().hasExtra("username")){
            username = getIntent().getStringExtra("username");
        }

        recyclerView = findViewById(R.id.classesRecyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        getModules();
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

    private void getModules(){
        Call<List<ModuleResponse>> call = RetrofitClient.getInstance().getAlsetAPI().getModules();
        call.enqueue(new Callback<List<ModuleResponse>>() {
            @Override
            public void onResponse(Call<List<ModuleResponse>> call, Response<List<ModuleResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    hideProgress();
                    List<ModuleResponse> moduleList = response.body();
                    getClasses(moduleList);
                }
            }

            @Override
            public void onFailure(Call<List<ModuleResponse>> call, Throwable t) {
                hideProgress();
                Toast.makeText(ViewClassesActivity.this, "Request Failed. Please try again!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getClasses(List<ModuleResponse> moduleList) {
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

                    mAdapter = new ClassAdapter(ViewClassesActivity.this, filteredClassList, moduleList);
                    recyclerView.setAdapter(mAdapter);

                } else {
                    Toast.makeText(ViewClassesActivity.this, "Request Failed. Please try again!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<ClassesResponse>> call, Throwable t) {
                Toast.makeText(ViewClassesActivity.this, "Request Failed. Please try again!", Toast.LENGTH_LONG).show();
            }
        });
    }

}