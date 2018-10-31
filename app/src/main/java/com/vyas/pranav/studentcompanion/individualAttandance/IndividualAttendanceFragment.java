package com.vyas.pranav.studentcompanion.individualAttandance;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vyas.pranav.studentcompanion.R;
import com.vyas.pranav.studentcompanion.data.attendenceDatabase.AttendanceIndividualDatabase;
import com.vyas.pranav.studentcompanion.data.attendenceDatabase.AttendanceIndividualEntry;
import com.vyas.pranav.studentcompanion.extraUtils.Constances;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.vyas.pranav.studentcompanion.extraUtils.Converters.convertStringToDate;

public class IndividualAttendanceFragment extends Fragment {

    @BindView(R.id.recycler_individual_attendance_frag)
    RecyclerView rvIndividualAttendance;
    IndividualAttendanceAdapter mAdapter;
    AttendanceIndividualDatabase mDb;
    private String dateString;

    public IndividualAttendanceFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle receiveddata = getArguments();
        if(receiveddata != null){
            if(receiveddata.getString(Constances.KEY_SEND_DATA_TO_INDIVIDUAL_FRAG) != null){
                dateString = receiveddata.getString(Constances.KEY_SEND_DATA_TO_INDIVIDUAL_FRAG);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_individual_attendance, container, false);
        ButterKnife.bind(this, view);
        setUpRecyclerView();
        mDb = AttendanceIndividualDatabase.getInstance(getContext());
        loadDateAttendance();
        return view;
    }

    private void setUpRecyclerView(){
        mAdapter = new IndividualAttendanceAdapter(getContext());
        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        lm.setOrientation(RecyclerView.VERTICAL);
        rvIndividualAttendance.setAdapter(mAdapter);
        rvIndividualAttendance.setLayoutManager(lm);
    }

    private void loadDateAttendance(){
        List<AttendanceIndividualEntry> attendences = mDb.attendanceIndividualDao().getAttendanceForDate(convertStringToDate(dateString));
        mAdapter.setAttendanceForDate(attendences);
    }
}