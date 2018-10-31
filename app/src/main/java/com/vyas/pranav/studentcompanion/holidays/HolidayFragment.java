package com.vyas.pranav.studentcompanion.holidays;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vyas.pranav.studentcompanion.R;
import com.vyas.pranav.studentcompanion.data.holidayDatabase.HolidayDatabase;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HolidayFragment extends Fragment {

    @BindView(R.id.recycler_holiday_frag)
    RecyclerView rvHolidays;
    HolidaysAdapter mAdapter;
    HolidayDatabase mDb;

    public HolidayFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_holiday, container, false);
        ButterKnife.bind(this,view);
        setUpRecyclerView();
        mDb = HolidayDatabase.getsInstance(getContext());
        loadDataInRecyclerView();
        return view;
    }

    public void setUpRecyclerView(){
        mAdapter = new HolidaysAdapter(getContext());
        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        lm.setOrientation(RecyclerView.VERTICAL);
        rvHolidays.setAdapter(mAdapter);
        rvHolidays.setLayoutManager(lm);
    }

    public void loadDataInRecyclerView(){
        mAdapter.setmHolidays(mDb.holidayDao().getAllHolidays());
    }



}
