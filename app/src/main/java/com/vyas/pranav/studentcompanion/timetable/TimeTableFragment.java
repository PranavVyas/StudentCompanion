package com.vyas.pranav.studentcompanion.timetable;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.evrencoskun.tableview.TableView;
import com.orhanobut.logger.Logger;
import com.vyas.pranav.studentcompanion.R;
import com.vyas.pranav.studentcompanion.timetable.viewModels.Cell;
import com.vyas.pranav.studentcompanion.timetable.viewModels.ColumnHeader;
import com.vyas.pranav.studentcompanion.timetable.viewModels.RowHeader;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;

public class TimeTableFragment extends Fragment {

    @BindView(R.id.time_table_container)
    TableView tableView;

    public TimeTableFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_time_table, container, false);
        ButterKnife.bind(this, view);

        TimetableAdapter mAdapter = new TimetableAdapter(getContext());

        tableView.setAdapter(mAdapter);

        List<ColumnHeader> mCH = new ArrayList<>();
        List<RowHeader> mRH = new ArrayList<>();
        List<List<Cell>> mC = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            List<Cell> tempCellList = new ArrayList<>();
            ColumnHeader columnHeader = ViewModelProviders.of(this).get(ColumnHeader.class);
            columnHeader.setmData("Subject " + (i + 1));
            mCH.add(columnHeader);
            RowHeader rowHeader = ViewModelProviders.of(this).get(RowHeader.class);
            rowHeader.setmData("Day " + (i + 1));
            mRH.add(rowHeader);
            for (int j = 0; j < 4; j++) {
                Cell cell = ViewModelProviders.of(this).get(Cell.class);
                cell.setmData("L " + i + " " + j);
                tempCellList.add(cell);
            }
            mC.add(tempCellList);
        }

        Logger.d("Test Log");
        mAdapter.setAllItems(mCH, mRH, mC);
        return view;
    }

}
