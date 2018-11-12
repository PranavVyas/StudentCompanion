package com.vyas.pranav.studentcompanion.toturial;

import android.content.Intent;
import android.os.Bundle;

import com.vyas.pranav.studentcompanion.R;
import com.vyas.pranav.studentcompanion.dashboard.DashboardActivity;
import com.vyas.pranav.studentcompanion.extrautils.Constances;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ToturialActivity extends AppCompatActivity {

    @BindView(R.id.viewpage_toturial)
    ViewPager toturialPager;
    @BindView(R.id.toolbar_toturial)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toturial);
        ButterKnife.bind(this);
        final ToturialPagerAdapter mAdapter = new ToturialPagerAdapter(getSupportFragmentManager());
        toturialPager.setAdapter(mAdapter);
        setSupportActionBar(mToolbar);
        mToolbar.setTitle("Tutorial");
    }

    @OnClick(R.id.button_tutorial_continue)
    void clickedContinueToApp() {
        startActivity(new Intent(this, DashboardActivity.class));
        finish();
    }

    class ToturialPagerAdapter extends FragmentPagerAdapter {

        ToturialPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            DashboardTutorialFragment frag = new DashboardTutorialFragment();
            Bundle data = new Bundle();
            data.putInt(Constances.SEND_DATA_TO_TUTORIAL_FRAG_FROM_ACTIVITY, position);
            frag.setArguments(data);
            return frag;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return "Tutorial";
        }
    }

}
