package com.vyas.pranav.studentcompanion.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.vyas.pranav.studentcompanion.R;

import butterknife.ButterKnife;

public class RegistrationActivity extends AppCompatActivity{

//    @BindView(R.id.tv_registration_no_of_lactures) TextView tvNoOfLactures;
//    @BindView(R.id.tv_registration_no_of_subjects) TextView tvNoOfSubjects;
//    @BindView(R.id.et_registration_no_of_lactures) EditText etNoOfLactures;
//    @BindView(R.id.et_registration_no_of_subjects) EditText etNoOfSubjects;
//    @BindView(R.id.frame_registration_fragment_container) FrameLayout frameFragmentContainer;
//    @BindView(R.id.btn_registration_next) Button btnNext;
//
//    private int currentDay = 1;
//    private int daysInWeek = 5;
//
//    private int totalLacturesPerDay;
//    private int totalSubjectsInSem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

//    @OnClick(R.id.btn_registration_next)
//    public void showFragments(){
//        totalLacturesPerDay = Integer.parseInt(etNoOfLactures.getText().toString());
//        totalSubjectsInSem = Integer.parseInt(etNoOfSubjects.getText().toString());
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.frame_registration_fragment_container,getNewFragment())
//                .commit();
//        btnNext.setVisibility(View.GONE);
//        currentDay++;
//    }
//
//
//    @Override
//    public void subjectsCompleted(List<String> subjectNames) {
//        Logger.d(subjectNames);
//        Logger.d("current Day = "+currentDay+" TAotal dfays = "+daysInWeek);
//        if(currentDay <= daysInWeek){
//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.frame_registration_fragment_container,getNewFragment())
//                    .commit();
//            currentDay++;
//        }else{
//            Toast.makeText(this, "Finished setting Up", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    private AddSubjetcsFragment getNewFragment(){
//        AddSubjetcsFragment addSubjetcsFragment = new AddSubjetcsFragment();
//        Bundle bundle = new Bundle();
//        bundle.putInt(KEY_REG_TO_FRAG_ARGS_LECTURES,totalLacturesPerDay);
//        bundle.putInt(KEY_REG_TO_FRAG_ARGS_SUBJECTS,totalSubjectsInSem);
//        bundle.putInt(KEY_REG_TO_FRAG_ARGS_CURR_DATE,currentDay);
//        addSubjetcsFragment.setArguments(bundle);
//        return addSubjetcsFragment;
//    }
}
