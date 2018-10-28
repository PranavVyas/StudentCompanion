package com.vyas.pranav.studentcompanion.overallAttandance;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.txusballesteros.widgets.FitChart;
import com.vyas.pranav.studentcompanion.R;
import com.vyas.pranav.studentcompanion.data.overallDatabase.OverallAttendanceEntry;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class OverallAttendanceAdapter extends RecyclerView.Adapter<OverallAttendanceAdapter.OverallAttendanceHolder> {

    Context mContext;
    List<OverallAttendanceEntry> attendanceData;

    public OverallAttendanceAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public OverallAttendanceHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View singleView = LayoutInflater.from(mContext).inflate(R.layout.item_holder_overall_attendance, viewGroup, false);
        return new OverallAttendanceHolder(singleView);
    }

    @Override
    public void onBindViewHolder(@NonNull OverallAttendanceHolder overallAttendanceHolder, int i) {
        overallAttendanceHolder.chartSub.setValue((float) attendanceData.get(i).getPercentPresent());
        overallAttendanceHolder.tvPercent.setText((int) attendanceData.get(i).getPercentPresent()+" %");
        overallAttendanceHolder.tvSubName.setText(attendanceData.get(i).getSubjectName());
    }

    @Override
    public int getItemCount() {
        return (attendanceData == null) ? 0 : attendanceData.size();
    }

    public void setAttendanceData(List<OverallAttendanceEntry> attendanceData) {
        this.attendanceData = attendanceData;
        notifyDataSetChanged();
    }

    class OverallAttendanceHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.chart_recycler_overall_subject)
        FitChart chartSub;
        @BindView(R.id.tv_recycler_overall_percent)
        TextView tvPercent;
        @BindView(R.id.tv_recycler_overall_sub_name)
        TextView tvSubName;

        public OverallAttendanceHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
