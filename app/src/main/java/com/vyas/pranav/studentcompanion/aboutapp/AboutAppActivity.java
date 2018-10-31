package com.vyas.pranav.studentcompanion.aboutapp;

import android.os.Bundle;

import com.vyas.pranav.studentcompanion.R;
import com.vyas.pranav.studentcompanion.extraUtils.ViewsUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutAppActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_about_app) Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        ViewsUtils.buildNavigationDrawer(this,mToolbar);
    }
}
