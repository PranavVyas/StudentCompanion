package com.vyas.pranav.studentcompanion.overallAttandance;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vyas.pranav.studentcompanion.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OverallAttendanceAdapter extends RecyclerView.Adapter<OverallAttendanceAdapter.OverallAttendanceHolder>{

    Context mContext;

    public OverallAttendanceAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public OverallAttendanceHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View singleView = LayoutInflater.from(mContext).inflate(R.layout.overall_attendance_item_holder,viewGroup,false);
        return new OverallAttendanceHolder(singleView);
    }

    @Override
    public void onBindViewHolder(@NonNull OverallAttendanceHolder overallAttendanceHolder, int i) {
        //TODO Bind Items Here
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class OverallAttendanceHolder extends RecyclerView.ViewHolder{
        public OverallAttendanceHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
