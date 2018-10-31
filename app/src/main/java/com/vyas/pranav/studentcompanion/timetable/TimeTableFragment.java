package com.vyas.pranav.studentcompanion.timetable;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vyas.pranav.studentcompanion.R;

import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;
public class TimeTableFragment extends Fragment {

    public TimeTableFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_time_table, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

}
