package com.vyas.pranav.studentcompanion.overallAttandance;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.txusballesteros.widgets.FitChart;
import com.vyas.pranav.studentcompanion.R;
import com.vyas.pranav.studentcompanion.data.overallDatabase.OverallAttendanceEntry;
import com.vyas.pranav.studentcompanion.extraUtils.Constances;
import com.vyas.pranav.studentcompanion.subjectOverallDetail.SubjectOverallDetailActivity;

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
    public void onBindViewHolder(@NonNull OverallAttendanceHolder overallAttendanceHolder, final int i) {
        overallAttendanceHolder.chartSub.setValue((float) attendanceData.get(i).getPercentPresent());
        overallAttendanceHolder.tvPercent.setText((int) attendanceData.get(i).getPercentPresent()+" %");
        overallAttendanceHolder.tvSubName.setText(attendanceData.get(i).getSubjectName());
//        overallAttendanceHolder.chartSub.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Gson gson = new Gson();
//                String subDataJsongson = gson.toJson(attendanceData.get(i));
//                Intent intent = new Intent(mContext,SubjectOverallDetailActivity.class);
//                intent.putExtra(Constances.KEY_SEND_DATA_TO_OVERALL_DETAIL,subDataJsongson);
//                mContext.startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return (attendanceData == null) ? 0 : attendanceData.size();
    }

    public void setAttendanceData(List<OverallAttendanceEntry> attendanceData) {
        this.attendanceData = attendanceData;
        notifyDataSetChanged();
    }

    class OverallAttendanceHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.chart_recycler_overall_subject)
        FitChart chartSub;
        @BindView(R.id.tv_recycler_overall_percent)
        TextView tvPercent;
        @BindView(R.id.tv_recycler_overall_sub_name)
        TextView tvSubName;

        public OverallAttendanceHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Gson gson = new Gson();
            String subDataJsongson = gson.toJson(attendanceData.get(getAdapterPosition()));
            Intent intent = new Intent(mContext, SubjectOverallDetailActivity.class);
            intent.putExtra(Constances.KEY_SEND_DATA_TO_OVERALL_DETAIL, subDataJsongson);
            mContext.startActivity(intent);
        }
    }
}
