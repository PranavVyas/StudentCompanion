package com.vyas.pranav.studentcompanion.aboutapp;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vyas.pranav.studentcompanion.R;

import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;

/*
 * Fragment to show About app*/
public class AboutAppFragment extends Fragment {


    public AboutAppFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about_app, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

}
