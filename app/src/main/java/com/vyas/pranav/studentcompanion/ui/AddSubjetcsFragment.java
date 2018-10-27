package com.vyas.pranav.studentcompanion.ui;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.vyas.pranav.studentcompanion.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.vyas.pranav.studentcompanion.extraUtils.Constances.KEY_REG_TO_FRAG_ARGS_LECTURES;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddSubjetcsFragment extends Fragment {

    @BindView(R.id.linear_addsubfrag_containner) LinearLayout container;

    private int noOfLectures;
    private List<String> subList = new ArrayList<>();
    private onFragmentNextClicked mCallback;

    public AddSubjetcsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //Setting callback to go to parent activity
        mCallback = (onFragmentNextClicked) context;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Getting Arguments from parent Activity
        Bundle args = getArguments();
        if (args != null) {
            noOfLectures = args.getInt(KEY_REG_TO_FRAG_ARGS_LECTURES);
        }
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_subjetcs, container, false);
        ButterKnife.bind(this,view);
        addSubjectsToView();
        return view;
    }

    public interface onFragmentNextClicked {
        void subjectsCompleted(List<String> subjectNames);
    }

    // Method to programatically add views in the Linearlayout
    private void addSubjectsToView(){
        for(int i = 0; i < noOfLectures; i++){
            EditText text = new EditText(getContext());
            text.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
            text.setTag("SUBJECT"+i);
            container.addView(text);
        }
    }

    //OnClickListener for Next Button
    @OnClick(R.id.btn_addsubfrag_next)
    public void nextButtonClicked(){
        for (int i = 0; i < noOfLectures; i++){
            subList.add(((EditText)container.findViewWithTag("SUBJECT"+i)).getText().toString());
        }
        mCallback.subjectsCompleted(subList);
    }
}
