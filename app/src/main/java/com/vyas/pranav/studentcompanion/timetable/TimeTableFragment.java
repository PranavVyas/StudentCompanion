package com.vyas.pranav.studentcompanion.timetable;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.evrencoskun.tableview.TableView;
import com.vyas.pranav.studentcompanion.R;
import com.vyas.pranav.studentcompanion.data.timetableDatabase.TimetableDatabase;
import com.vyas.pranav.studentcompanion.data.timetableDatabase.TimetableEntry;
import com.vyas.pranav.studentcompanion.extrautils.AppExecutors;
import com.vyas.pranav.studentcompanion.extrautils.Constances;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Fragment to show the timetable of lectures and faculties
 */
public class TimeTableFragment extends Fragment {

    @BindView(R.id.time_table_container)
    TableView tableView;
    private AppExecutors mExecutors;
    //For storing lectures header and faculty name header
    private List<String> mCH;
    //For storing days names
    private List<String> mRH;
    //For storing details of the lectures
    private List<List<String>> mC;

    public TimeTableFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_time_table, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    /**
     * @param view               Fragment View
     * @param savedInstanceState Saved state of fragment
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mExecutors = AppExecutors.getInstance();
        final TimetableAdapter mAdapter = new TimetableAdapter(getContext());
        tableView.setAdapter(mAdapter);
        //Execute the fetching of data from DB and setting it in the table view adapter in background
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mCH = new ArrayList<>();
                mRH = new ArrayList<>();
                mC = new ArrayList<>();

                List<TimetableEntry> fullTimetable = TimetableDatabase.getInstance(getContext()).timetableDao().getFullTimetable();
                int dayOfWeek = 1;
                for (TimetableEntry x :
                        fullTimetable) {
                    //
                    String dayTitle = x.getDay();
                    mRH.add(dayTitle);
                    List<String> dayWiseLacture = new ArrayList<>();
                    String lecture1 = "";
                    String lecture2 = "";
                    String lecture3 = "";
                    String lecture4 = "";
                    String faculty1 = "";
                    String faculty2 = "";
                    String faculty3 = "";
                    String faculty4 = "";

                    lecture1 = x.getLacture1Name();
                    faculty1 = x.getLacture1Faculty();
                    lecture2 = x.getLacture2Name();
                    faculty2 = x.getLacture2Faculty();
                    lecture3 = x.getLacture3Name();
                    faculty3 = x.getLacture3Faculty();
                    lecture4 = x.getLacture4Name();
                    faculty4 = x.getLacture4Faculty();

                    dayWiseLacture.add(lecture1);
                    dayWiseLacture.add(faculty1);
                    dayWiseLacture.add(lecture2);
                    dayWiseLacture.add(faculty2);
                    dayWiseLacture.add(lecture3);
                    dayWiseLacture.add(faculty3);
                    dayWiseLacture.add(lecture4);
                    dayWiseLacture.add(faculty4);
                    mC.add(dayWiseLacture);
                    dayOfWeek++;
                }
                for (int i = 1; i <= Constances.NO_OF_LECTURES_PER_DAY; i++) {
                    String lectureTitle = "Lecture" + i;
                    String facultyTitle = "Faculty Name";
                    mCH.add(lectureTitle);
                    mCH.add(facultyTitle);
                }
                AppExecutors.getInstance().mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        //Setting all items requires UI thread so in mainThread exec
                        mAdapter.setAllItems(mCH, mRH, mC);
                    }
                });
            }
        });
    }
}
