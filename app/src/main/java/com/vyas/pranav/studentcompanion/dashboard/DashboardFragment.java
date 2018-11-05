package com.vyas.pranav.studentcompanion.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vyas.pranav.studentcompanion.R;
import com.vyas.pranav.studentcompanion.data.attendenceDatabase.AttendanceIndividualDatabase;
import com.vyas.pranav.studentcompanion.extraUtils.Constances;
import com.vyas.pranav.studentcompanion.extraUtils.Converters;
import com.vyas.pranav.studentcompanion.individualAttandance.IndividualAttendanceFragment;

import java.util.Date;

import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;

public class DashboardFragment extends Fragment {
    private AttendanceIndividualDatabase mDb;

    public DashboardFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        ButterKnife.bind(this,view);
        mDb = AttendanceIndividualDatabase.getInstance(getContext());
        showIndividualAttendance();
        return view;
    }

    private void showIndividualAttendance(){
        IndividualAttendanceFragment todayAttendanceFrag = new IndividualAttendanceFragment();
        Bundle dataToSend = new Bundle();
        Date date = new Date();
        //Logger.d("Date String is : " + Converters.convertDateToString(date));
        dataToSend.putString(Constances.KEY_SEND_DATA_TO_INDIVIDUAL_FRAG, Converters.convertDateToString(date));
        todayAttendanceFrag.setArguments(dataToSend);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_dashboard_frag, todayAttendanceFrag)
                .commit();
    }

}
