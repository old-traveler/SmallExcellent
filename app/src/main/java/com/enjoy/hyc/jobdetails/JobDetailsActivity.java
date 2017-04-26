package com.enjoy.hyc.jobdetails;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.enjoy.R;
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
    @Bind(R.id.rv_detail_loading)
    RelativeLayout rvDetailLoading;
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
    @Bind(R.id.sv_details)
    ScrollView svDetails;

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

        jobSalaryTxt.setText(job.getJobSalary() + "");
        tvModeTxt.setText("元/" + job.getBalanceMode().split("结")[0]);
        rmnumberTxt.setText(job.getRecruitmentNumber() + "人");
        jregionTxt.setText(job.getJobRegion());
        deadlineTxt.setText(job.getDeadline());
        workTimeTxt.setText(job.getWorkInterval());
        publisherTxt.setText(job.getPublisher());
        contactNumberTxt.setText(job.getContactNumber() + "");
        contactAddressTxt.setText(job.getContactAddress());
        jobDescribeTxt.setText(job.getJobDescribe());
        jobDemandTxt.setText(job.getJobDemand());
        jobDetailNameTxt.setText(job.getJobDetailName());
        workIntervalTxt.setText(job.getWorkDayTime() + "天");
    }

    @Override
    public void applySuccess() {
        Toast.makeText(mActivity, "申请成功", Toast.LENGTH_SHORT).show();
        btnApplyJob.setText("已申请");
    }

    @Override
    public void applyFail(String error) {
        btnApplyJob.setText("申请兼职");
        btnApplyJob.setClickable(true);
        Toast.makeText(mActivity, error + "申请失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void applying() {
        btnApplyJob.setText("申请中...");
        btnApplyJob.setClickable(false);
    }

    @Override
    public void notLogin() {
        btnApplyJob.setText("请先登录");
        btnApplyJob.setClickable(false);
    }

    @Override
    public void haveApply() {
        rvDetailLoading.setVisibility(View.GONE);
        svDetails.setVisibility(View.VISIBLE);
        btnApplyJob.setText("已申请");
        btnApplyJob.setClickable(false);
    }

    @Override
    public void verifyHaveApply() {
        rvDetailLoading.setVisibility(View.VISIBLE);
        svDetails.setVisibility(View.GONE);
    }

    @Override
    public void haveNotApply() {
        rvDetailLoading.setVisibility(View.GONE);
        svDetails.setVisibility(View.VISIBLE);
    }

    @Override
    public void verifyError() {
        rvDetailLoading.setVisibility(View.GONE);
        Toast.makeText(mActivity, "网络不给力", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.btn_apply_job)
    public void onViewClicked() {
        mvpPresenter.applyJob();
    }
}
