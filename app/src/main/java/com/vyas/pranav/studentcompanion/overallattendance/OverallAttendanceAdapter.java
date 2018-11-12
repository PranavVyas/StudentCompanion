package com.vyas.pranav.studentcompanion.overallattendance;

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
import com.vyas.pranav.studentcompanion.extrautils.Constances;
import com.vyas.pranav.studentcompanion.subjectoveralldetail.SubjectOverallDetailActivity;

import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * The type Overall attendance adapter.
 * Adapter for recycler view that shows the overall attendance chart in overallAttendanceFragment
 */
public class OverallAttendanceAdapter extends RecyclerView.Adapter<OverallAttendanceAdapter.OverallAttendanceHolder> {

    private Context mContext;
    private List<OverallAttendanceEntry> attendanceData;

    /**
     * Instantiates a new Overall attendance adapter.
     *
     * @param mContext the m context
     */
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
        overallAttendanceHolder.chartSub.setValue(attendanceData.get(i).getPercentPresent());
        overallAttendanceHolder.tvPercent.setText(String.format(Locale.US, "%d %%", (int) attendanceData.get(i).getPercentPresent()));
        overallAttendanceHolder.tvSubName.setText(attendanceData.get(i).getSubjectName());
    }

    @Override
    public int getItemCount() {
        return (attendanceData == null) ? 0 : attendanceData.size();
    }

    /**
     * Sets attendance data.
     *
     * @param attendanceData the attendance data
     */
    void setAttendanceData(List<OverallAttendanceEntry> attendanceData) {
        this.attendanceData = attendanceData;
        notifyDataSetChanged();
    }

    /**
     * The type Overall attendance holder.
     */
    class OverallAttendanceHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        /**
         * The Chart of the subject.
         */
        @BindView(R.id.chart_recycler_overall_subject)
        FitChart chartSub;
        /**
         * The Tv percent.
         */
        @BindView(R.id.tv_recycler_overall_percent)
        TextView tvPercent;
        /**
         * The Tv sub name.
         */
        @BindView(R.id.tv_recycler_overall_sub_name)
        TextView tvSubName;

        /**
         * Instantiates a new Overall attendance holder.
         *
         * @param itemView the item view
         */
        OverallAttendanceHolder(@NonNull View itemView) {
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
