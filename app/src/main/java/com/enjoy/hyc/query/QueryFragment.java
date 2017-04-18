package com.enjoy.hyc.query;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.enjoy.R;

/**
 * Created by hyc on 2017/4/18 12:17
 */

public class QueryFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_query, container, false);
    }
}
