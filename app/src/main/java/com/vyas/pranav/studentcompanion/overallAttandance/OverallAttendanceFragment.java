package com.vyas.pranav.studentcompanion.overallAttandance;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.vyas.pranav.studentcompanion.R;
import com.vyas.pranav.studentcompanion.data.overallDatabase.OverallAttendanceDatabase;
import com.vyas.pranav.studentcompanion.extraUtils.Constances;
import com.vyas.pranav.studentcompanion.individualAttandance.IndividualAttendanceActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.vyas.pranav.studentcompanion.extraUtils.Converters.formatDateStringfromCalender;
import static com.vyas.pranav.studentcompanion.extraUtils.Converters.getDayOfWeek;

public class OverallAttendanceFragment extends Fragment {

    @BindView(R.id.calernder_overall_attendance_frag)
    CalendarView calendarView;
    @BindView(R.id.recycler_overall_attandance_frag_subjects)
    RecyclerView rvSubAttendance;
    OverallAttendanceAdapter mAdapter;
    OverallAttendanceDatabase mDb;

    public OverallAttendanceFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_overall_attendance, container, false);
        ButterKnife.bind(this, view);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                int date = i2;
                int month = i1 + 1;
                int year = i;
                String dateString = formatDateStringfromCalender(date, month, year);
                Logger.d("Date : "+dateString + "Day : " +getDayOfWeek(dateString));
                Toast.makeText(getContext(), dateString + " " +getDayOfWeek(dateString), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), IndividualAttendanceActivity.class);
                intent.putExtra(Constances.KEY_SEND_DATA_TO_INDIVIDUAL_ACTIVITY, dateString);
                getContext().startActivity(intent);
            }
        });
        setUpRecyclerView();
        Logger.addLogAdapter(new AndroidLogAdapter());
        mDb = OverallAttendanceDatabase.getInstance(getContext());
        loadDataInRecyclerView();
        return view;
    }

    private void setUpRecyclerView() {
        mAdapter = new OverallAttendanceAdapter(getContext());
        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        lm.setOrientation(RecyclerView.HORIZONTAL);
        rvSubAttendance.setLayoutManager(lm);
        rvSubAttendance.setAdapter(mAdapter);
    }

    private void loadDataInRecyclerView() {
        mAdapter.setAttendanceData(mDb.overallAttandanceDao().getAllOverallAttedance());
    }
}
