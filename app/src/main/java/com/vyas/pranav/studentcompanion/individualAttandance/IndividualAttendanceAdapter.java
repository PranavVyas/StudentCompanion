package com.vyas.pranav.studentcompanion.individualAttandance;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vyas.pranav.studentcompanion.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IndividualAttendanceAdapter extends RecyclerView.Adapter<IndividualAttendanceAdapter.AttandanceHolder>{

    Context mContext;
    public IndividualAttendanceAdapter(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public AttandanceHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dashboard_attandance_item_holder,viewGroup,false);
        return new AttandanceHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AttandanceHolder attandanceHolder, int i) {
        //TODO Binding Data Now..........
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class AttandanceHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tv_dashboard_recycler_no)
        TextView tvNo;
        @BindView(R.id.tv_dashboard_recycler_subname) TextView tvSubName;
        @BindView(R.id.tv_dashboard_recycler_facultyname) TextView tvFacultyName;
        @BindView(R.id.switch_dashboard_recycler_present) SwitchCompat swithPresent;
        public AttandanceHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
