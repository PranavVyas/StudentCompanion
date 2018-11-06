package com.vyas.pranav.studentcompanion.individualAttandance;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vyas.pranav.studentcompanion.R;
import com.vyas.pranav.studentcompanion.data.attendenceDatabase.AttendanceIndividualDatabase;
import com.vyas.pranav.studentcompanion.data.attendenceDatabase.AttendanceIndividualEntry;
import com.vyas.pranav.studentcompanion.extraUtils.Converters;
import com.vyas.pranav.studentcompanion.services.AddOverallAttendanceForDayIntentService;

import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.vyas.pranav.studentcompanion.extraUtils.Constances.KEY_SEND_END_DATE_TO_SERVICE_OVERALL;
import static com.vyas.pranav.studentcompanion.extraUtils.Constances.VALUE_ABSENT;
import static com.vyas.pranav.studentcompanion.extraUtils.Constances.VALUE_PRESENT;

public class IndividualAttendanceAdapter extends RecyclerView.Adapter<IndividualAttendanceAdapter.AttendanceHolder> {

    Context mContext;
    List<AttendanceIndividualEntry> mAtttendances;
    AttendanceIndividualDatabase mAttendanceDb;
    public IndividualAttendanceAdapter(Context context) {
        this.mContext = context;
        mAttendanceDb = AttendanceIndividualDatabase.getInstance(mContext);
    }

    @NonNull
    @Override
    public AttendanceHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_holder_dashboard_attandance,viewGroup,false);
        return new AttendanceHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AttendanceHolder attandanceHolder, final int i) {
        attandanceHolder.tvNo.setText((i+1) + ".");
        attandanceHolder.tvSubName.setText(mAtttendances.get(i).getSubName());
        attandanceHolder.tvFacultyName.setText(mAtttendances.get(i).getFacultyName());
        if (mAtttendances.get(i).getAttended() == null) {
            attandanceHolder.swithPresent.setChecked(false);
        } else {
            if (mAtttendances.get(i).getAttended().equals(VALUE_PRESENT)) {
                //Logger.d("Attended : Yes");
                attandanceHolder.swithPresent.setChecked(true);
            } else {
                //Logger.d("Attended : No");
                attandanceHolder.swithPresent.setChecked(false);
            }
        }
        attandanceHolder.swithPresent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (attandanceHolder.swithPresent.isChecked()) {
                    mAtttendances.get(i).setAttended(VALUE_PRESENT);
                } else {
                    mAtttendances.get(i).setAttended(VALUE_ABSENT);
                }
                mAttendanceDb.attendanceIndividualDao().insertAttendance(mAtttendances.get(i));
                Intent updateDataInOverallDatabase = new Intent(mContext, AddOverallAttendanceForDayIntentService.class);
                //TODO Replace the date with today's Date Now
                updateDataInOverallDatabase.putExtra(KEY_SEND_END_DATE_TO_SERVICE_OVERALL, Converters.convertDateToString(new Date()));
                mContext.startService(updateDataInOverallDatabase);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (mAtttendances == null) ? 0 :  mAtttendances.size() ;
    }

    public void setAttendanceForDate(List<AttendanceIndividualEntry> attendances) {
        this.mAtttendances = attendances;
        //TODO BUG Called Multiple times Logger.d(attendances);
        notifyDataSetChanged();
    }

    class AttendanceHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_dashboard_recycler_no)
        TextView tvNo;
        @BindView(R.id.tv_dashboard_recycler_subname) TextView tvSubName;
        @BindView(R.id.tv_dashboard_recycler_facultyname) TextView tvFacultyName;
        @BindView(R.id.switch_dashboard_recycler_present) SwitchCompat swithPresent;

        public AttendanceHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

}
