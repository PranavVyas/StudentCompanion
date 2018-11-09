package com.vyas.pranav.studentcompanion.holidays;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vyas.pranav.studentcompanion.R;
import com.vyas.pranav.studentcompanion.data.holidayDatabase.HolidayDatabase;
import com.vyas.pranav.studentcompanion.data.holidayDatabase.HolidayEntry;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * The type Holiday fragment.
 */
public class HolidayFragment extends Fragment {

    /**
     * The recyclerview of holidays.
     */
    @BindView(R.id.recycler_holiday_frag)
    RecyclerView rvHolidays;
    private HolidaysAdapter mAdapter;
    private HolidayDatabase mDb;

    /**
     * Instantiates a new Holiday fragment.
     */
    public HolidayFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_holiday, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpRecyclerView();
        mDb = HolidayDatabase.getsInstance(getContext());
        loadDataInRecyclerView();
    }

    private void setUpRecyclerView() {
        mAdapter = new HolidaysAdapter(getContext());
        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        lm.setOrientation(RecyclerView.VERTICAL);
        DividerItemDecoration decoration = new DividerItemDecoration(getContext(), lm.getOrientation());
        rvHolidays.addItemDecoration(decoration);
        rvHolidays.setAdapter(mAdapter);
        rvHolidays.setLayoutManager(lm);
    }

    private void loadDataInRecyclerView() {
        final LiveData<List<HolidayEntry>> holidayLive = mDb.holidayDao().getAllHolidays();
        holidayLive.observe(this, new Observer<List<HolidayEntry>>() {
            @Override
            public void onChanged(List<HolidayEntry> holidayEntries) {
                holidayLive.removeObservers(HolidayFragment.this);
                mAdapter.setmHolidays(holidayEntries);
            }
        });
    }


}
