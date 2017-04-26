package com.enjoy.hyc.personal;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.enjoy.R;
import com.enjoy.base.LogUtils;
import com.enjoy.base.MvpFragment;
import com.enjoy.hyc.bean.User;
import com.enjoy.hyc.footprint.FootprintActivity;
import com.enjoy.hyc.login.LoginActivity;
import com.enjoy.hyc.record.RecordActivity;
import com.enjoy.hyc.resume.ResumeActivity;
import com.enjoy.hyc.setting.SettingActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * the personal information fragment  Created by hyc on 2017/4/18 11:17
 */

public class PersonalFragment extends MvpFragment<PersonalPresenter> implements PersonalContract {
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

    @Bind(R.id.cv_my_head)
    CircleImageView cvMyHead;
    @Bind(R.id.tv_my_name)
    TextView tvMyName;


    /**
     * create the fragment view
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal, container, false);
        mvpPresenter = createPresenter();
        mvpPresenter.attachView(this);
        ButterKnife.bind(this, view);
        initView();

        return view;
    }

    private void initView() {
        User user=BmobUser.getCurrentUser(User.class);
        if (user==null){
            return;
        }
        String path = user.getHeadImageUrl();
        if (!TextUtils.isEmpty(path)) {
            Glide.with(getActivity())
                    .load(path)
                    .into(cvMyHead);
        }
        if (!TextUtils.isEmpty(user.getName())){
            tvMyName.setText(user.getName());
        }
    }

    /**
     * initialize the presenter
     *
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
     *
     * @param view controls
     */
    @OnClick({R.id.rl_salary_bill, R.id.rl_work_record, R.id.rl_footprint, R.id.rl_my_publish, R.id.rl_my_resume, R.id.rl_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_salary_bill:
                break;
            case R.id.rl_work_record:
                startActivity(RecordActivity.class);
                break;
            case R.id.rl_footprint:
                startActivity(FootprintActivity.class);
                break;
            case R.id.rl_my_publish:
                break;
            case R.id.rl_my_resume:
                startActivity(ResumeActivity.class);
                break;
            case R.id.rl_setting:
                startActivity(SettingActivity.class);
                break;
        }
    }

    /**
     * enter activity
     *
     * @param activity
     */
    @Override
    public void startActivity(Class<?> activity) {
        if (!mvpPresenter.isLogin()){
            return;
        }else {
            getActivity().startActivity(new Intent(getActivity(), activity));
        }

    }

    @Override
    public void enterLogin() {
        new AlertDialog.Builder(mActivity)
                .setIcon(R.drawable.ic_alert)
                .setTitle("未登录")
                .setMessage("是否去登录?")
                .setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mActivity.startActivity(new Intent(mActivity,LoginActivity.class));
                    }
                }).setNegativeButton("关闭",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }
}
