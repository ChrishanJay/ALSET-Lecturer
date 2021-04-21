package com.alset.lecturer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.alset.lecturer.api.ClassesResponse;
import com.alset.lecturer.api.LecturerResponse;
import com.alset.lecturer.api.ModuleResponse;
import com.alset.lecturer.api.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewClassActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;
    private Toast errorToast;
    private Spinner moduleSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_class);

        errorToast = Toast.makeText(NewClassActivity.this, "Request Failed. Please try again!", Toast.LENGTH_LONG);
        moduleSpinner = findViewById(R.id.moduleSpinner);

        showProgress(NewClassActivity.this);
        getModules();
    }

    private void showProgress(Context context){
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading Class Details...");
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

    private void getModules(){
        Call<List<ModuleResponse>> call = RetrofitClient.getInstance().getAlsetAPI().getModules();
        call.enqueue(new Callback<List<ModuleResponse>>() {
            @Override
            public void onResponse(Call<List<ModuleResponse>> call, Response<List<ModuleResponse>> response) {
                if (response != null && response.isSuccessful()) {
                    hideProgress();
                    List<ModuleResponse> moduleList = response.body();
                    updateModuleSpinner(moduleList);
                }
            }

            @Override
            public void onFailure(Call<List<ModuleResponse>> call, Throwable t) {
                hideProgress();
                errorToast.show();
            }
        });
    }
}