package com.vyas.pranav.studentcompanion.toturial;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vyas.pranav.studentcompanion.R;
import com.vyas.pranav.studentcompanion.extraUtils.Constances;

import androidx.fragment.app.Fragment;

public class DashboardTutorialFragment extends Fragment {


    public DashboardTutorialFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (getArguments() != null) {
            switch (getArguments().getInt(Constances.SEND_DATA_TO_TUTORIAL_FRAG_FROM_ACTIVITY)) {
                case 1:
                    return inflater.inflate(R.layout.fragment_dashboard_tutorial, container, false);

                case 0:
                    return inflater.inflate(R.layout.fragment_main_tutorial, container, false);
            }
        }
        return inflater.inflate(R.layout.fragment_dashboard_tutorial, container, false);
    }
}
