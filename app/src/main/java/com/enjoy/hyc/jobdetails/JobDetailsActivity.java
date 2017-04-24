package com.enjoy.hyc.jobdetails;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.TextView;

import com.enjoy.R;
import com.enjoy.base.LogUtils;
import com.enjoy.base.MvpActivity;
import com.enjoy.hyc.bean.Job;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hyc on 2017/4/24 15:57
 */

public class JobDetailsActivity extends MvpActivity<JobDetailsPresenter> implements JobDetailsContract {


    @Bind(R.id.tb_job_details)
    Toolbar tbJobDetails;
    @Bind(R.id.job_detail_name_txt)
    TextView jobDetailNameTxt;
    @Bind(R.id.job_salary_txt)
    TextView jobSalaryTxt;
    @Bind(R.id.tv_mode_txt)
    TextView tvModeTxt;
    @Bind(R.id.rmnumber_txt)
    TextView rmnumberTxt;
    @Bind(R.id.jregion_txt)
    TextView jregionTxt;
    @Bind(R.id.deadline_txt)
    TextView deadlineTxt;
    @Bind(R.id.work_time_txt)
    TextView workTimeTxt;
    @Bind(R.id.work_interval_txt)
    TextView workIntervalTxt;
    @Bind(R.id.publisher_txt)
    TextView publisherTxt;
    @Bind(R.id.contact_number_txt)
    TextView contactNumberTxt;
    @Bind(R.id.contact_address_txt)
    TextView contactAddressTxt;
    @Bind(R.id.job_describe_txt)
    TextView jobDescribeTxt;
    @Bind(R.id.job_demand_txt)
    TextView jobDemandTxt;
    @Bind(R.id.btn_apply_job)
    Button btnApplyJob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_job_details);
        initView();
        ButterKnife.bind(this);
        mvpPresenter.loadJobContent();
    }

    private void initView() {
        setToolBar(R.id.tb_job_details);
        initToolBarAsHome("兼职详情");
    }

    @Override
    protected JobDetailsPresenter createPresenter() {
        return new JobDetailsPresenter(this);
    }


    @Override
    public void loadJobContent(Job job) {

        jobSalaryTxt.setText(job.getJobSalary()+"");
        tvModeTxt.setText("元/" + job.getBalanceMode().split("结")[0]);
        rmnumberTxt.setText(job.getRecruitmentNumber()+"人");
        jregionTxt.setText(job.getJobRegion());
        deadlineTxt.setText(job.getDeadline());
        workTimeTxt.setText(job.getWorkInterval());
        publisherTxt.setText(job.getPublisher());
        contactNumberTxt.setText(job.getContactNumber()+"");
        contactAddressTxt.setText(job.getContactAddress());
        jobDescribeTxt.setText(job.getJobDescribe());
        jobDemandTxt.setText(job.getJobDemand());
        jobDetailNameTxt.setText(job.getJobDetailName());
        workIntervalTxt.setText(job.getWorkDayTime() + "天");
    }

    @OnClick(R.id.btn_apply_job)
    public void onViewClicked() {
    }
}
