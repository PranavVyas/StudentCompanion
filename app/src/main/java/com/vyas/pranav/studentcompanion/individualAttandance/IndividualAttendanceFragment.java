package com.vyas.pranav.studentcompanion.individualAttandance;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vyas.pranav.studentcompanion.R;
import com.vyas.pranav.studentcompanion.data.attendenceDatabase.AttendanceIndividualDatabase;
import com.vyas.pranav.studentcompanion.data.attendenceDatabase.AttendanceIndividualEntry;
import com.vyas.pranav.studentcompanion.extraUtils.AppExecutors;
import com.vyas.pranav.studentcompanion.extraUtils.Constances;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
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
    AppExecutors mExecutors;
    List<AttendanceIndividualEntry> attendances;

    public IndividualAttendanceFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle receivedData = getArguments();
        if (receivedData != null) {
            if (receivedData.getString(Constances.KEY_SEND_DATA_TO_INDIVIDUAL_FRAG) != null) {
                dateString = receivedData.getString(Constances.KEY_SEND_DATA_TO_INDIVIDUAL_FRAG);
            }
        }
        mExecutors = AppExecutors.getInstance();
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
        DividerItemDecoration decoration = new DividerItemDecoration(getContext(), lm.getOrientation());
        rvIndividualAttendance.addItemDecoration(decoration);
        rvIndividualAttendance.setAdapter(mAdapter);
        rvIndividualAttendance.setLayoutManager(lm);
    }

    private void loadDateAttendance(){
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                attendances = mDb.attendanceIndividualDao().getAttendanceForDate(convertStringToDate(dateString));
                mExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.setAttendanceForDate(attendances);
                    }
                });
            }
        });
    }
}
