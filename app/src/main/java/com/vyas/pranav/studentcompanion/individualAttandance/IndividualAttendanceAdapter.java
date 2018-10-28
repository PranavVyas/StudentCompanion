package com.vyas.pranav.studentcompanion.individualAttandance;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.vyas.pranav.studentcompanion.R;
import com.vyas.pranav.studentcompanion.data.database.AttendanceIndividualEntry;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.vyas.pranav.studentcompanion.extraUtils.Constances.VALUE_PRESENT;

public class IndividualAttendanceAdapter extends RecyclerView.Adapter<IndividualAttendanceAdapter.AttandanceHolder>{

    Context mContext;
    List<AttendanceIndividualEntry> mAtttendances;
    public IndividualAttendanceAdapter(Context context) {
        this.mContext = context;
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    @NonNull
    @Override
    public AttandanceHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_holder_dashboard_attandance,viewGroup,false);
        return new AttandanceHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AttandanceHolder attandanceHolder, int i) {
        attandanceHolder.tvNo.setText((i+1) + ".");
        attandanceHolder.tvSubName.setText(mAtttendances.get(i).getSubName());
        attandanceHolder.tvFacultyName.setText(mAtttendances.get(i).getFacultyName());
        if(mAtttendances.get(i).getAttended().equals(VALUE_PRESENT)){
            Logger.d("Attended : Yes");
            attandanceHolder.swithPresent.setChecked(true);
        }else{
            Logger.d("Attended : No");
            attandanceHolder.swithPresent.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return (mAtttendances == null) ? 0 :  mAtttendances.size() ;
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

    public void setAttendanceForDate(List<AttendanceIndividualEntry> attendances){
        this.mAtttendances = attendances;
        Logger.d(attendances);
        notifyDataSetChanged();
    }

}
