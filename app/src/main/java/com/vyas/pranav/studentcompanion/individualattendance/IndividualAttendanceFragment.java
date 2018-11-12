package com.vyas.pranav.studentcompanion.individualattendance;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vyas.pranav.studentcompanion.R;
import com.vyas.pranav.studentcompanion.data.attendenceDatabase.AttendanceIndividualDatabase;
import com.vyas.pranav.studentcompanion.data.attendenceDatabase.AttendanceIndividualEntry;
import com.vyas.pranav.studentcompanion.extrautils.AppExecutors;
import com.vyas.pranav.studentcompanion.extrautils.Constances;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.vyas.pranav.studentcompanion.extrautils.Converters.convertStringToDate;

public class IndividualAttendanceFragment extends Fragment {

    @BindView(R.id.recycler_individual_attendance_frag)
    RecyclerView rvIndividualAttendance;
    @BindView(R.id.constraint_individual_holiday)
    ConstraintLayout holidayTemplate;
    private IndividualAttendanceAdapter mAdapter;
    private AttendanceIndividualDatabase mDb;
    private String dateString = "";
    private AppExecutors mExecutors;
    private List<AttendanceIndividualEntry> attendances;

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
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpRecyclerView();
        mDb = AttendanceIndividualDatabase.getInstance(getContext());
        loadDateAttendance();
    }

    private void setUpRecyclerView(){
        mAdapter = new IndividualAttendanceAdapter(getContext());
        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        lm.setOrientation(RecyclerView.VERTICAL);
        mAdapter.setHasStableIds(true);
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
                if (!attendances.isEmpty()) {
                    mExecutors.mainThread().execute(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.setAttendanceForDate(attendances);
                            holidayTemplate.setVisibility(View.GONE);
                            rvIndividualAttendance.setVisibility(View.VISIBLE);
                        }
                    });
                } else {
                    mExecutors.mainThread().execute(new Runnable() {
                        @Override
                        public void run() {
                            holidayTemplate.setVisibility(View.VISIBLE);
                            rvIndividualAttendance.setVisibility(View.GONE);
                        }
                    });
                }
            }
        });
    }
}
