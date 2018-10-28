package com.vyas.pranav.studentcompanion.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vyas.pranav.studentcompanion.R;
import com.vyas.pranav.studentcompanion.data.database.AttendanceIndividualDatabase;
import com.vyas.pranav.studentcompanion.data.database.AttendanceIndividualEntry;
import com.vyas.pranav.studentcompanion.extraUtils.Constances;
import com.vyas.pranav.studentcompanion.individualAttandance.IndividualAttendanceFragment;

import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.vyas.pranav.studentcompanion.extraUtils.Constances.VALUE_ABSENT;
import static com.vyas.pranav.studentcompanion.extraUtils.Constances.VALUE_PRESENT;
import static com.vyas.pranav.studentcompanion.extraUtils.Converters.convertStringToDate;

public class DashboardFragment extends Fragment {
    AttendanceIndividualDatabase mDb;

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

    @OnClick(R.id.testButton)
    public void testDatabase(){
        AttendanceIndividualEntry test1 = new AttendanceIndividualEntry();
        test1.setAttended(VALUE_PRESENT);
        test1.setDate(convertStringToDate("01/01/2018"));
        test1.setDurationInMillis(3600);
        test1.setFacultyName("N/A");
        test1.setLactureNo(1);
        test1.setLactureType("Lacture");
        test1.setSubName("MATHS");
        AttendanceIndividualEntry test2 = new AttendanceIndividualEntry();
        test2.setAttended(VALUE_ABSENT);
        test2.setDate(convertStringToDate("01/01/2018"));
        test2.setDurationInMillis(3600);
        test2.setFacultyName("N/A");
        test2.setLactureNo(2);
        test2.setLactureType("Lacture");
        test2.setSubName("SCIENCE");
        if(mDb.attendanceIndividualDao().getAttendanceForDate(convertStringToDate("01/01/2018")).size() > 1){
            mDb.attendanceIndividualDao().updateAttendance(test1);
            mDb.attendanceIndividualDao().updateAttendance(test2);
        }else{
            mDb.attendanceIndividualDao().insertAttendance(test1);
            mDb.attendanceIndividualDao().insertAttendance(test2);
        }
        showIndividualAttendance();
    }

    @OnClick(R.id.buttonTesting2)
    public void deleteAtten(){
        mDb.attendanceIndividualDao().deleteAllAttendance();
        showIndividualAttendance();
    }

    private void showIndividualAttendance(){
        IndividualAttendanceFragment todayAttandanceFrag = new IndividualAttendanceFragment();
        Bundle dataToSend = new Bundle();
        dataToSend.putString(Constances.KEY_SEND_DATA_TO_INDIVIDUAL_FRAG,"01/01/2018");
        todayAttandanceFrag.setArguments(dataToSend);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_dashboard_frag,todayAttandanceFrag)
                .commit();
    }
}
