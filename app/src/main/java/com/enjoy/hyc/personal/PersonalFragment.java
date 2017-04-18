package com.enjoy.hyc.personal;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.enjoy.R;
import com.enjoy.base.BaseFragment;

/**
 * Created by hyc on 2017/4/18 11:17
 */

public class PersonalFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_personal, container, false);
    }
}
