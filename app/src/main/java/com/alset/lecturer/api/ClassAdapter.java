package com.alset.lecturer.api;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alset.lecturer.HomeActivity;
import com.alset.lecturer.NewClassActivity;
import com.alset.lecturer.R;
import com.alset.lecturer.ViewAttendanceActivity;

import java.util.List;
import java.util.Locale;

public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.ViewHolder>{

    private final List<ClassesResponse> classList;
    private final List<ModuleResponse> moduleList;
    private final Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView moduleName;
        public TextView date;
        public View layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView;
            moduleName = (TextView) itemView.findViewById(R.id.moduleName);
            date = (TextView) itemView.findViewById(R.id.date);
        }
    }

    public void add(int position, ClassesResponse item) {
        classList.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        classList.remove(position);
        notifyItemRemoved(position);
    }

    public ClassAdapter(Context context, List<ClassesResponse> classList, List<ModuleResponse> moduleList) {
        this.context = context;
        this.classList = classList;
        this.moduleList = moduleList;
    }

    private String getModuleName(String moduleId){
        for (ModuleResponse module: moduleList) {
            if (module.getModuleId().equalsIgnoreCase(moduleId)){
                return module.getName();
            }
        }
        return "";
    }

    @NonNull
    @Override
    public ClassAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.class_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ClassAdapter.ViewHolder holder, int position) {
        final ClassesResponse classesResponse = classList.get(position);
        String moduleName = getModuleName(classesResponse.getModuleId());
        holder.moduleName.setText(moduleName);
        holder.date.setText(String.format(Locale.getDefault(), "Date: %s from %s to %s",
                classesResponse.getDate(),
                classesResponse.getStartTime().split(" ")[1],
                classesResponse.getEndTime().split(" ")[1]));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewAttendanceActivity = new Intent(context, ViewAttendanceActivity.class);
                viewAttendanceActivity.putExtra("classId", classesResponse.getClassId());
                viewAttendanceActivity.putExtra("moduleName", moduleName);
                viewAttendanceActivity.putExtra("date", classesResponse.getDate());
                viewAttendanceActivity.putExtra("startTime", classesResponse.getStartTime());
                viewAttendanceActivity.putExtra("endTime", classesResponse.getEndTime());
                context.startActivity(viewAttendanceActivity);
            }
        });
    }

    @Override
    public int getItemCount() {
        return classList.size();
    }
}
