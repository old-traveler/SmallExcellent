package com.enjoy.hyc.personal;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.enjoy.R;
import com.enjoy.base.BaseFragment;
import com.enjoy.base.MvpFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * the personal information fragment  Created by hyc on 2017/4/18 11:17
 */

public class PersonalFragment extends MvpFragment<PersonalPresenter> implements PersonalContract{
    /**
     * Payroll options，click it will enter the activity of payroll
     */
    @Bind(R.id.rl_salary_bill)
    RelativeLayout rlSalaryBill;
    /**
     * record of work,click it will enter the activity of record
     */
    @Bind(R.id.rl_work_record)
    RelativeLayout rlWorkRecord;
    /**
     * footprint options
     */
    @Bind(R.id.rl_footprint)
    RelativeLayout rlFootprint;
    /**
     * the part-time information record of publish by me
     */
    @Bind(R.id.rl_my_publish)
    RelativeLayout rlMyPublish;
    /**
     * my resume options
     */
    @Bind(R.id.rl_my_resume)
    RelativeLayout rlMyResume;
    /**
     * setting options
     */
    @Bind(R.id.rl_setting)
    RelativeLayout rlSetting;

    /**
     * create the fragment view
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal, container, false);
        mvpPresenter=createPresenter();
        mvpPresenter.attachView(this);
        ButterKnife.bind(this, view);
        return view;
    }

    /**
     * initialize the presenter
     * @return presenter
     */
    @Override
    protected PersonalPresenter createPresenter() {
        return new PersonalPresenter();
    }

    /**
     * destroy the view
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    /**
     * event of controls's click
     * @param view controls
     */
    @OnClick({R.id.rl_salary_bill, R.id.rl_work_record,R.id.rl_footprint, R.id.rl_my_publish, R.id.rl_my_resume, R.id.rl_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_salary_bill:
                break;
            case R.id.rl_work_record:
                break;
            case R.id.rl_footprint:
                break;
            case R.id.rl_my_publish:
                break;
            case R.id.rl_my_resume:
                break;
            case R.id.rl_setting:
                break;
        }
    }

    /**
     * enter activity
     * @param activity
     */
    @Override
    public void startActivity(Class<?> activity) {
        getActivity().startActivity(new Intent(getActivity(),activity));
    }
}
