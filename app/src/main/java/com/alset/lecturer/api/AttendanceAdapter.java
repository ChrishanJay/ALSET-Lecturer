package com.alset.lecturer.api;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alset.lecturer.R;

import java.util.List;
import java.util.Locale;

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.ViewHolder> {

    private final List<AttendanceResponse> attendanceList;
    private final List<StudentsResponse> studentList;
    private final Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView studentName;
        public TextView time;
        public View layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView;
            studentName = (TextView) itemView.findViewById(R.id.studentName);
            time = (TextView) itemView.findViewById(R.id.time);
        }
    }

    public AttendanceAdapter(Context context, List<AttendanceResponse> attendanceList, List<StudentsResponse> studentList) {
        this.attendanceList = attendanceList;
        this.studentList = studentList;
        this.context = context;
    }

    private StudentsResponse getStudent(String studentId) {
        for (StudentsResponse student: studentList) {
            if (student.getStudentId().equalsIgnoreCase(studentId)){
                return student;
            }
        }
        return null;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.attendance_item, parent, false);
        AttendanceAdapter.ViewHolder vh = new AttendanceAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AttendanceResponse attendance = this.attendanceList.get(position);
        StudentsResponse student = getStudent(attendance.getStudentId());
        if (student != null) {
            holder.studentName.setText(String.format(Locale.getDefault(), "%s %s", student.getFirstName(), student.getLastName()));
            holder.time.setText(attendance.getAttendedTime());
        } else {
            holder.studentName.setText("");
            holder.time.setText("");
        }
    }

    @Override
    public int getItemCount() {
        return attendanceList.size();
    }



}
