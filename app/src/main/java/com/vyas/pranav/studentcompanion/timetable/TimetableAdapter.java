package com.vyas.pranav.studentcompanion.timetable;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TimetableAdapter extends RecyclerView.Adapter<TimetableAdapter.TimeTableHolder> {

    Context mContext;

    public TimetableAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public TimeTableHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull TimeTableHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class TimeTableHolder extends RecyclerView.ViewHolder {
        public TimeTableHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
