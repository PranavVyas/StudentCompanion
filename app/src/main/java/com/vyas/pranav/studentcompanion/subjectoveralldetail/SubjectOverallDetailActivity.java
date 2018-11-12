package com.vyas.pranav.studentcompanion.subjectoveralldetail;

import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.vyas.pranav.studentcompanion.R;
import com.vyas.pranav.studentcompanion.data.SharedPrefsUtils;
import com.vyas.pranav.studentcompanion.data.overallDatabase.OverallAttendanceEntry;
import com.vyas.pranav.studentcompanion.extrautils.Constances;
import com.vyas.pranav.studentcompanion.extrautils.Converters;

import java.util.Date;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SubjectOverallDetailActivity extends AppCompatActivity {

    @BindView(R.id.tv_overall_detail_subname)
    TextView tvSubName;
    @BindView(R.id.tv_overall_detail_available_to_bunk_days)
    TextView tvAvailableToBunk;
    @BindView(R.id.tv_overall_detail_bunked_days)
    TextView tvDaysAlreadyBunked;
    @BindView(R.id.tv_overall_detail_present_days)
    TextView tvPresentDays;
    @BindView(R.id.tv_overall_detail_present_percent)
    TextView tvPresentPercent;
    @BindView(R.id.tv_overall_detail_total_days)
    TextView tvTotalDays;
    @BindView(R.id.toolbar_overall_detail)
    Toolbar mToolbar;
    @BindView(R.id.tv_overall_detail_date)
    TextView tvDate;
    @BindView(R.id.tv_overall_detail_elapsed_days)
    TextView tvElaspsedDays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPrefsUtils.setThemeOfUser(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_overall_detail);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        if (getIntent().hasExtra(Constances.KEY_SEND_DATA_TO_OVERALL_DETAIL)) {
            Gson gson = new Gson();
            OverallAttendanceEntry overallAttendanceEntry = gson.fromJson(getIntent().getStringExtra(Constances.KEY_SEND_DATA_TO_OVERALL_DETAIL), OverallAttendanceEntry.class);
            int daysTotal = overallAttendanceEntry.getTotalDays();
            float percentPresent = overallAttendanceEntry.getPercentPresent();
            int daysPresent = (int) Math.ceil((percentPresent * daysTotal) / 100);
            int daysAlreadyBunked = overallAttendanceEntry.getDaysBunked();
            int daysAvailableToBunk = overallAttendanceEntry.getDaysAvailableToBunk();
            int daysElapsed = daysPresent + daysAlreadyBunked;

            tvSubName.setText(overallAttendanceEntry.getSubjectName());
            tvTotalDays.setText(String.format(Locale.US, "%d", daysTotal));
            tvPresentPercent.setText(String.format(Locale.US, "%s", percentPresent));
            tvDaysAlreadyBunked.setText(String.format(Locale.US, "%d", daysAlreadyBunked));
            tvAvailableToBunk.setText(String.format(Locale.US, "%d", daysAvailableToBunk));
            tvPresentDays.setText(String.format(Locale.US, "%d", daysPresent));
            tvDate.setText(Converters.convertDateToString(new Date()));
            tvElaspsedDays.setText(String.format(Locale.US, "%d", daysElapsed));
        } else {
            Logger.d("Error Occurred While setting overall attendance");
        }
    }
}
