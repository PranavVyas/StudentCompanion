package com.vyas.pranav.studentcompanion.dashboard;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vyas.pranav.studentcompanion.R;
import com.vyas.pranav.studentcompanion.extrautils.Constances;
import com.vyas.pranav.studentcompanion.extrautils.Converters;
import com.vyas.pranav.studentcompanion.individualattendance.IndividualAttendanceFragment;

import java.util.Date;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.vyas.pranav.studentcompanion.extrautils.Constances.KEY_SMART_CARD_DETAILS;

/**
 * The type Dashboard fragment.
 */
public class DashboardFragment extends Fragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    /**
     * The Container smart card.
     */
    @BindView(R.id.linear_dashboard_frag_smart_card_container)
    LinearLayout containerSmartCard;
    private SharedPreferences mPref;

    /**
     * Instantiates a new Dashboard fragment.
     */
    public DashboardFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPref = PreferenceManager.getDefaultSharedPreferences(getContext());
        showSmartCard();
        showIndividualAttendance();
    }

    /**
     * Shows attendance fragment
     */
    private void showIndividualAttendance() {
        IndividualAttendanceFragment todayAttendanceFrag = new IndividualAttendanceFragment();
        Bundle dataToSend = new Bundle();
        Date date = new Date();
        dataToSend.putString(Constances.KEY_SEND_DATA_TO_INDIVIDUAL_FRAG, Converters.convertDateToString(date));
        todayAttendanceFrag.setArguments(dataToSend);
        getChildFragmentManager().beginTransaction()
                .replace(R.id.frame_dashboard_frag, todayAttendanceFrag)
                .commit();
    }

    /**
     * For showing smart card
     * getting details from shred prefefrnces and showing to smart card
     */
    private void showSmartCard() {
        Set<String> warnings = mPref.getStringSet(KEY_SMART_CARD_DETAILS, null);
        containerSmartCard.removeAllViews();
        for (String x :
                warnings) {
            TextView tv = new TextView(getContext());
            tv.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            tv.setText(Html.fromHtml(x));
            containerSmartCard.addView(tv);
        }
    }

    /**
     * For each change in shared preference check if the changed preference is the smart card changed
     * If smart card is changed than refresh the showing smart card
     * @param sharedPreferences shardPrefernce
     * @param s changed prefernce key
     */
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        if (s.equals(KEY_SMART_CARD_DETAILS)) {
            showSmartCard();
        }
    }

    /**
     * Register listener for listening to changes in shared prefences
     */
    @Override
    public void onResume() {
        super.onResume();
        mPref.registerOnSharedPreferenceChangeListener(this);
    }

    /**
     * Unregister listener for listening to changes in shared prefences
     */
    @Override
    public void onPause() {
        super.onPause();
        mPref.unregisterOnSharedPreferenceChangeListener(this);
    }
}
