package com.vyas.pranav.studentcompanion.timetable.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class RowHeader extends AndroidViewModel {

    private String mData;

    public RowHeader(@NonNull Application application) {
        super(application);
    }

    public String getmData() {
        return mData;
    }

    public void setmData(String mData) {
        this.mData = mData;
    }
}
