package com.vyas.pranav.studentcompanion.timetable;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.evrencoskun.tableview.TableView;
import com.orhanobut.logger.Logger;
import com.vyas.pranav.studentcompanion.R;
import com.vyas.pranav.studentcompanion.data.timetableDatabase.TimetableDatabase;
import com.vyas.pranav.studentcompanion.data.timetableDatabase.TimetableEntry;
import com.vyas.pranav.studentcompanion.extraUtils.AppExecutors;
import com.vyas.pranav.studentcompanion.extraUtils.Constances;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class TimeTableFragment extends Fragment {

    @BindView(R.id.time_table_container)
    TableView tableView;
    AppExecutors mExecutors;
    //    List<ColumnHeader> mCH;
//    List<RowHeader> mRH;
//    List<List<Cell>> mC;
    List<String> mCH;
    List<String> mRH;
    List<List<String>> mC;

    public TimeTableFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_time_table, container, false);
        ButterKnife.bind(this, view);
        mExecutors = AppExecutors.getInstance();
        final TimetableAdapter mAdapter = new TimetableAdapter(getContext());
        tableView.setAdapter(mAdapter);
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
                    //List<List<Cell>> allLectures = new ArrayList<>();
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
                for (int i = 0; i < Constances.NO_OF_LECTURES_PER_DAY; i++) {
                    String lectureTitle = "Lecture " + i;
                    String facultyTitle = "Faculty Name";
                    mCH.add(lectureTitle);
                    mCH.add(facultyTitle);
                }
                Logger.d("Test Log");
                AppExecutors.getInstance().mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.setAllItems(mCH, mRH, mC);
                    }
                });
            }
        });
        return view;
    }

}
