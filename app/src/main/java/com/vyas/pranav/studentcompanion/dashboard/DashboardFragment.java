package com.vyas.pranav.studentcompanion.dashboard;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vyas.pranav.studentcompanion.R;
import com.vyas.pranav.studentcompanion.extraUtils.Constances;
import com.vyas.pranav.studentcompanion.extraUtils.Converters;
import com.vyas.pranav.studentcompanion.individualAttandance.IndividualAttendanceFragment;

import java.util.Date;
import java.util.Set;

import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.vyas.pranav.studentcompanion.extraUtils.Constances.KEY_SMART_CARD_DETAILS;

public class DashboardFragment extends Fragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    @BindView(R.id.linear_dashboard_frag_smart_card_container)
    LinearLayout containerSmartCard;
    SharedPreferences mPref;

    public DashboardFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        ButterKnife.bind(this, view);
        mPref = PreferenceManager.getDefaultSharedPreferences(getContext());
        showSmartCard();
        showIndividualAttendance();
        return view;
    }

    private void showIndividualAttendance() {
        IndividualAttendanceFragment todayAttendanceFrag = new IndividualAttendanceFragment();
        Bundle dataToSend = new Bundle();
        Date date = new Date();
        //Logger.d("Date String is : " + Converters.convertDateToString(date));
        dataToSend.putString(Constances.KEY_SEND_DATA_TO_INDIVIDUAL_FRAG, Converters.convertDateToString(date));
        todayAttendanceFrag.setArguments(dataToSend);
        getChildFragmentManager().beginTransaction()
                .replace(R.id.frame_dashboard_frag, todayAttendanceFrag)
                .commit();
    }

    /*
     * */
    private void showSmartCard() {
        Set<String> warnings = mPref.getStringSet(KEY_SMART_CARD_DETAILS, null);
        String warning = "";
        for (String x :
                warnings) {
//            TextView tv = new TextView(getContext());
//            tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
//            tv.setText(x);
//            containerSmartCard.addView(tv);
            warning = warning + x + "\n\n* ";
        }
        TextView tv = new TextView(getContext());
        tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        tv.setText(warning.trim());
        containerSmartCard.removeAllViews();
        containerSmartCard.addView(tv);
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        if (s.equals(KEY_SMART_CARD_DETAILS)) {
            showSmartCard();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mPref.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        mPref.unregisterOnSharedPreferenceChangeListener(this);
    }
}
