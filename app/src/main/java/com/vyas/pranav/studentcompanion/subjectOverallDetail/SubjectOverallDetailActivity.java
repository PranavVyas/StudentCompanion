package com.vyas.pranav.studentcompanion.subjectOverallDetail;

import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;
import com.vyas.pranav.studentcompanion.R;
import com.vyas.pranav.studentcompanion.data.overallDatabase.OverallAttendanceEntry;
import com.vyas.pranav.studentcompanion.extraUtils.Constances;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
            tvSubName.setText(overallAttendanceEntry.getSubjectName());
            tvTotalDays.setText(overallAttendanceEntry.getTotalDays() + "");
            tvPresentPercent.setText(overallAttendanceEntry.getPercentPresent() + "");
            tvDaysAlreadyBunked.setText(overallAttendanceEntry.getDaysBunked() + "");
            tvAvailableToBunk.setText(overallAttendanceEntry.getDaysAvailableToBunk() + "");
            double daysPresent = Math.ceil((overallAttendanceEntry.getPercentPresent() * overallAttendanceEntry.getTotalDays()) / 100);
            tvPresentDays.setText(daysPresent + "");
        }
    }
}
