package com.vyas.pranav.studentcompanion.individualAttandance;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vyas.pranav.studentcompanion.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IndividualAttendanceFragment extends Fragment {

    @BindView(R.id.recycler_individual_attendance_frag)
    RecyclerView rvIndividualAttendance;
    IndividualAttendanceAdapter mAdapter;

    public IndividualAttendanceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_individual_attendance, container, false);
        ButterKnife.bind(this, view);
        mAdapter = new IndividualAttendanceAdapter(getContext());
        return view;
    }

}
