package com.vyas.pranav.studentcompanion.overallattendance;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

import com.vyas.pranav.studentcompanion.R;
import com.vyas.pranav.studentcompanion.data.overallDatabase.OverallAttendanceDatabase;
import com.vyas.pranav.studentcompanion.data.overallDatabase.OverallAttendanceEntry;
import com.vyas.pranav.studentcompanion.extrautils.Constances;
import com.vyas.pranav.studentcompanion.individualattendance.IndividualAttendanceActivity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.vyas.pranav.studentcompanion.extrautils.Converters.formatDateStringfromCalender;
import static com.vyas.pranav.studentcompanion.extrautils.Converters.getDayOfWeek;

/**
 * The type Overall attendance fragment.
 * Fragment that shows calenderview and Overall attendance recycerview
 */
public class OverallAttendanceFragment extends Fragment {

    /**
     * The Calendar view.
     */
    @BindView(R.id.calernder_overall_attendance_frag)
    CalendarView calendarView;
    /**
     * The Rv sub attendance.
     */
    @BindView(R.id.recycler_overall_attandance_frag_subjects)
    RecyclerView rvSubAttendance;
    private OverallAttendanceAdapter mAdapter;
    private OverallAttendanceDatabase mDb;
    private LiveData<List<OverallAttendanceEntry>> overallAttednanceList;

    /**
     * Instantiates a new Overall attendance fragment.
     */
    public OverallAttendanceFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_overall_attendance, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                int date = i2;
                int month = i1 + 1;
                int year = i;
                String dateString = formatDateStringfromCalender(date, month, year);
                //Logger.d("Date : "+dateString + "Day : " +getDayOfWeek(dateString));
                Toast.makeText(getContext(), dateString + " " +getDayOfWeek(dateString), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), IndividualAttendanceActivity.class);
                intent.putExtra(Constances.KEY_SEND_DATA_TO_INDIVIDUAL_ACTIVITY, dateString);
                getContext().startActivity(intent);
            }
        });
        setUpRecyclerView();
        mDb = OverallAttendanceDatabase.getInstance(getContext());
        loadDataInRecyclerView();
        //To observer changes happening in main DB and react to it by updating the adapter of recycler view
        overallAttednanceList = mDb.overallAttandanceDao().getAllOverallAttedance();
        overallAttednanceList.observe(this, new Observer<List<OverallAttendanceEntry>>() {
            @Override
            public void onChanged(List<OverallAttendanceEntry> overallAttendanceEntries) {
                mAdapter.setAttendanceData(overallAttendanceEntries);
            }
        });
    }

    /**
     * Remove observers in LiveData when view is destroyed
     */
    @Override
    public void onDestroyView() {
        overallAttednanceList.removeObservers(this);
        super.onDestroyView();
    }

    /**
     * Set up recyclerview
     */
    private void setUpRecyclerView() {
        mAdapter = new OverallAttendanceAdapter(getContext());
        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        lm.setOrientation(RecyclerView.HORIZONTAL);
        rvSubAttendance.setLayoutManager(lm);
        rvSubAttendance.setAdapter(mAdapter);
    }

    //Callback to notify if the overall attendance has been added/updated
    private void loadDataInRecyclerView() {
    }
}
